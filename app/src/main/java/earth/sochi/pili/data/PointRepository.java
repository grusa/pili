package earth.sochi.pili.data;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import earth.sochi.pili.GetData;
import earth.sochi.pili.data.db.CatalogesDao;
import earth.sochi.pili.data.db.PointsDao;
import earth.sochi.pili.data.db.RoutesDao;
import earth.sochi.pili.data.model.Catalog;
import earth.sochi.pili.data.model.Point;
import earth.sochi.pili.data.model.Route;

public class PointRepository {
    //LOADER
    private static String TAG = "LOAD";
    static private RequestQueue queue;
    static List<Point> listPoints;
    static String url = "https://www.sochi.earth/data/points.json";
    //LOADER
    private PointsDao mPointDao;
    private CatalogesDao mCatalogesDao;
    private RoutesDao mRoutesDao;
    private LiveData<List<Point>> mAllPoint;
    private LiveData<List<Catalog>> mAllCataloges;
    private LiveData<List<Route>> mAllRoutes;

    public PointRepository(Application application) {
        PointRoomDatabase db = PointRoomDatabase.getDatabase(application);
        mPointDao = db.pointDao();
      //  mCatalogesDao = db.catalogesDao();
        //mRoutesDao = db.routesDao();
        try {
            mAllPoint = mPointDao.getAllPoints();
        } catch (Exception e) {
//            Log.d(TAG,e.toString());
        }
        //mAllCataloges = mCatalogesDao.getCataloges();
        //mAllRoutes = mRoutesDao.getRoutes();
    }
    public LiveData<List<Point>> getPoints() {
     return mAllPoint;
    }
    public LiveData<List<Point>> getPointById(int id) {
        //COMPLETED get point by name
        return mPointDao.getPointsById(id);
    }
    public int getPointsCount() {
        return mPointDao.getPointsCount();
    }
    public LiveData<List<Point>> getPoint(List<String> names) {
        //COMPLETED get point by name
        return mPointDao.getPointsByNames(names);
    }
    LiveData<List<Catalog>> getCataloges () {
        return mAllCataloges;
    }
    LiveData<List<Route>> getRoutes () {
        return mAllRoutes;
    }

    void insert(Point point) {
        if (point != null) {
            PointRoomDatabase.databaseWriteExecutor.execute(() ->
                    mPointDao.insert(point));
        }
    }
    void getDataFromSite(Context c) {
        //loading dataset from sochi.earth
        if (ContextCompat.checkSelfPermission(c, Manifest.permission.INTERNET) ==
                PackageManager.PERMISSION_GRANTED) {
            PointRoomDatabase.databaseWriteExecutor.execute(()->
                    mPointDao.deletePoints());
            queue = Volley.newRequestQueue(c);
            listPoints = new ArrayList<Point>();
//            Log.d(TAG, "Start loading");
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                JSONObject jsonObject;
                                for (int i = 0; i < response.length(); i++) {
                                    jsonObject = response.getJSONObject(i);
                                    try {
                                        GetData getDataSmallImage = new GetData();
                                        GetData getDataImage = new GetData();
                                        Point point = new Point(
                                                i,
                                                jsonObject.getString("name"),
                                                jsonObject.getString("description"),
                                                jsonObject.getDouble("latitude"),
                                                jsonObject.getDouble("longitude"),
                                                jsonObject.getInt("cat_id"),
                                                jsonObject.getInt("route_id"),
                                                jsonObject.getString("smallimageurl"),
                                                jsonObject.getString("imageurl"));
                                        listPoints.add(point);
                                        insert(point);
                                    } catch (Exception e) {
//                                        Log.d(TAG, "Error: " + e.getMessage());
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {error.printStackTrace();}
                    });
            queue.add(request);
        } else {
            Toast.makeText(c, "Not granted INTERNET permission", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap getBitmapFromUrl(String urlString) {
        if (urlString == null) return null;
        else {
            try {
                urlString = "http://" + urlString;
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                return null;
            }
        }
    }

}
