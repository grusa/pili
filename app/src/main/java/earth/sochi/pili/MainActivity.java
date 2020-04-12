package earth.sochi.pili;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.android.volley.RequestQueue;
import java.util.ArrayList;
import java.util.List;
import earth.sochi.pili.data.PointViewModel;
import earth.sochi.pili.data.model.Point;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MAIN";
    RecyclerView rv;
    PointAdapter pointAdapter;
    ArrayList<Point> points = new ArrayList<>();
    Bitmap pointBitmap;
    RequestQueue queue;
    PointViewModel mPointViewModel;
    PointAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rv = findViewById(R.id.points_recyclerview);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));
        adapter = new PointAdapter(this);
        rv.setAdapter(adapter);
        mPointViewModel = new ViewModelProvider(this).get(PointViewModel.class);
        mPointViewModel.getAllPoint().observe(this, new Observer<List<Point>>() {
            @Override
            public void onChanged(List<Point> points) {
                adapter.setPoints(points);
            }
        });
    }
    @Override
    public  boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu,menu);
        return true;
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
/*            case null:
                //TODO get only Dolmens point
                break;
*/        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
