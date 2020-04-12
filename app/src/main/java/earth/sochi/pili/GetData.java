package earth.sochi.pili;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetData extends AsyncTask<String, Void, Bitmap> {
    static String TAG = "GD";
    @Override
    protected Bitmap doInBackground(String... urlString) {
        Bitmap myBitmap = null;
        if (urlString != null) {
            for (int i = 0; i < urlString.length; i++) {
                try {
                    urlString[i] = "http://" + urlString[i];
                    URL url = new URL(urlString[i]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                } catch (Exception e) {
                    //Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        }
        return myBitmap;
    }
}
