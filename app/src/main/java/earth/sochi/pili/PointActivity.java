package earth.sochi.pili;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class PointActivity extends AppCompatActivity {
    static String TAG = "PAC";
    NetworkImageView photo; ImageLoader imageLoader;

    TextView descriptionTextView,coordinatesTextView;
    Double latitude,longitude;
    String name,description,imageUrl;
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
        descriptionTextView.setText(description);

        coordinatesTextView = findViewById(R.id.coordinate);
        coordinatesTextView.
                setText(""+latitude.toString()+"\n"+longitude.toString());

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
