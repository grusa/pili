package earth.sochi.pili;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class PointActivity extends AppCompatActivity {
    static String TAG = "PAC";
    NetworkImageView photo; ImageLoader imageLoader;

    TextView descriptionTextView,coordinatesTextView;
    Double latitude,longitude;
    String name,description,imageUrl;
    Palette p;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        //int i = intent.getIntExtra("pos",0);
        description = intent.getStringExtra("description");
        name = intent.getStringExtra("name");
        setTitle(name);
        latitude = intent.getDoubleExtra("latitude",0.0);
        longitude = intent.getDoubleExtra("longitude",0.0);
        photo = findViewById(R.id.photo);
        photo.setContentDescription(name);
        imageUrl = "https://"+intent.getStringExtra("imageUrl");
        try {
            imageLoader = CustomVolleyRequestQueue.getInstance(this).getImageLoader();
            imageLoader.get(imageUrl,ImageLoader.getImageListener(
                    photo,R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert
            ));
            photo.setImageUrl(imageUrl,imageLoader);
        } catch (Exception e ) {
//            Log.d(TAG,getResources().getString(R.string.noInternet));
        }
        descriptionTextView = findViewById(R.id.description);
        descriptionTextView.setMovementMethod(LinkMovementMethod.getInstance());
        descriptionTextView.setText(description);

        Linkify.addLinks(descriptionTextView,Linkify.ALL);
        coordinatesTextView = findViewById(R.id.coordinate);
        coordinatesTextView.
                setText(getResources().getString(R.string.onMap)+":"+latitude.toString()+"\n"+longitude.toString());
//init MobileAds
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus
                                                         initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //init MobileAds
        //get palette and set backgroundB
        //TODO get image and set palette to description and coordinate
        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.dolmen1);
        if (bitmap != null) {
            Palette.from(bitmap).generate(
                    new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(@Nullable Palette palette) {
                            if (palette != null) {
                            Palette.Swatch vibrant = palette.getVibrantSwatch();
                            descriptionTextView.setBackgroundColor(
                                    palette.getVibrantSwatch().getTitleTextColor());
                            descriptionTextView.setBackgroundColor(
                                        palette.getVibrantSwatch().getBodyTextColor());
                            //if (vibrant != null) {
                            //    descriptionTextView.setBackgroundColor(vibrant.getRgb());
                            //    descriptionTextView.setTextColor(vibrant.getBodyTextColor());
                            //    }
                            }
                        }
                    });
            //descriptionTextView.setBackgroundColor(
            //p.getVibrantColor((getResources().getColor(defaultColor))));
        }*/
        //=======================================
    }
    @Override
    public void onDestroy () {
        super.onDestroy();
    }
    public void startMap (View view)          {
        // Start Google MAps with Name and Latitude? Longitude
        if (latitude!= 0.0 || longitude!=0.0) {
            String cord = "<" + latitude.toString() + ">,<" +
                    longitude.toString() + ">";
            Uri gmmIntentUri =
                    Uri.parse("geo:" + cord + "?q=" + cord + "(" + name.toString() + ")");
//            Log.d(TAG, gmmIntentUri.toString());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                try {
                    startActivity(mapIntent);
                } catch (Exception e) {
//                    Log.d(TAG, e.toString());
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.notStartMaps),
                        Toast.LENGTH_LONG).show();
            }
        } else Toast.makeText(this, getResources().getString(R.string.notHaveCoordinate),
                Toast.LENGTH_LONG).show();
    }
}
