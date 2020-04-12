package earth.sochi.pili;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.ArrayList;
import java.util.List;
import earth.sochi.pili.data.model.Catalog;
import earth.sochi.pili.data.model.Point;
import earth.sochi.pili.data.model.Route;

public class PointAdapter  extends RecyclerView.Adapter<PointAdapter.PointHolder> {
    Context c;
    final String TAG = "PA";
    public LayoutInflater mLayoutInflator;

    private List<Point> mPoint; //Cached points,Catalog and Route
    private List<Catalog> mCatalog;
    private List<Route> mRoute;
    private RequestQueue requestQueue;
    private LruCache<String,Bitmap> imageCacheAdapter;
    private ImageLoader mImageLoader;

    class PointHolder extends RecyclerView.ViewHolder {
        private final NetworkImageView img;
        private final TextView name;
        public LinearLayout linearLayout;

        public PointHolder(View itemView) {
            super(itemView);
            img = (NetworkImageView) itemView.findViewById(R.id.photo);
            name = (TextView) itemView.findViewById(R.id.name);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
    public PointAdapter (Context context) {
        mLayoutInflator=LayoutInflater.from(context);
        this.c = context;
    }
    public PointAdapter (Context context,ArrayList<Point> points) {
        this.c = context;
        //this.points = points;
        mLayoutInflator = LayoutInflater.from(context);
    }

    @Override
    public PointHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View itemView  = mLayoutInflator.inflate(
                R.layout.pointscard, parent,false);

        return new PointHolder(itemView);
        }
    @Override
    public void onBindViewHolder(PointHolder holder, final int position) {
        if (mPoint.size() !=0) {
            Point current = mPoint.get(position);
            holder.name.setText(current.getName());
            try {
                String url = "https://"+current.getImageUrl();
                mImageLoader = CustomVolleyRequestQueue.getInstance(this.c)
                        .getImageLoader();
                mImageLoader.get(url, ImageLoader.getImageListener(
                        holder.img,
                        R.mipmap.ic_launcher,
                        android.R.drawable
                                .ic_dialog_alert));
                holder.img.setImageUrl(url, mImageLoader);
                //holder.img.setImageBitmap(loadImage(current.getSmallImageUrl()));
            } catch ( Exception e) {
//                Log.d(TAG,e.toString());
            }
            holder.linearLayout.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void  onClick (View v) {
                    Intent intent = new Intent(c,PointActivity.class);
                    intent.putExtra("callRule",2);
                    intent.putExtra("pos",position);
                    intent.putExtra("name",mPoint.get(position).getName());
                    intent.putExtra("description",mPoint.get(position).getDescription());
                    intent.putExtra("latitude",mPoint.get(position).getLatitude());
                    intent.putExtra("longitude",mPoint.get(position).getLongitude());
                    intent.putExtra("imageUrl",mPoint.get(position).getImageUrl());
                    c.startActivity(intent);
                };
            });
        } else {
            holder.name.setText("No data found");
        }
        }
    void setPoints(List<Point> points) {
        mPoint = points;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (mPoint!=null) {
            return mPoint.size();
        } else return 0;
    }
}
