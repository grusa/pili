package earth.sochi.pili;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import earth.sochi.pili.data.PointViewModel;
import earth.sochi.pili.data.model.Point;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MAIN";
    boolean finish_boolean = false;
    RecyclerView rv,dv;
    PointAdapter pointAdapter;
    //ArrayList<Point> points = new ArrayList<>();
    Bitmap pointBitmap;
    RequestQueue queue;
    PointViewModel mPointViewModel;
    Button sportButton;
    AdView mAdView;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//TODO v3 add sport points
//        sportButton = findViewById(R.id.btSport);
//        sportButton.setVisibility(View.INVISIBLE);
//--------------------------
        rv = findViewById(R.id.points_recyclerview);
        linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(linearLayoutManager);
        pointAdapter = new PointAdapter(this);
        rv.setAdapter(pointAdapter);

        mPointViewModel = new ViewModelProvider(this).get(PointViewModel.class);
        mPointViewModel.getPoints().observe(this,
                new androidx.lifecycle.Observer<List<Point>>() {
            @Override
            public void onChanged(List<Point> points) {
                pointAdapter.setPoints(points);
            }
        });
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
    }
    @Override
    public  boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu,menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (finish_boolean) {
        finish();} else
        {
            finish_boolean = true;
            Toast.makeText(this,R.string.exitText,Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.Loading:
                mPointViewModel.loadDataFromSite();
                break;
            case R.id.About:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setDataAndType(
                        Uri.parse("https://sochi.earth/data/about.html"),
                        "text/html");
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void btCategoryPressed(View view) {
        Toast.makeText(this, ""+view.getId(), Toast.LENGTH_LONG);
        switch (view.getId()) {
            case (R.id.btAll):
                mPointViewModel.catalogId=0;
                break;
            case (R.id.btDolmens):
                mPointViewModel.catalogId=1;
                break;
            case (R.id.btChurches):
                mPointViewModel.catalogId=2;
                break;
            case (R.id.btNature):
                mPointViewModel.catalogId=3;
                break;
            case (R.id.btCulture):
                mPointViewModel.catalogId=4;
                break;
            case (R.id.btSport):
                mPointViewModel.catalogId=5;
                break;
        }

        //mPointViewModel.getAllPoint();
        mPointViewModel.getPoints();//Mediator
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
