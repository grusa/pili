package earth.sochi.pili.data;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import earth.sochi.pili.data.model.Point;

public class PointViewModel extends AndroidViewModel {
    final String TAG = "PVM";
    private PointRepository mPointRepository;
    private LiveData<List<Point>> mAllPoint;

    public PointViewModel (Application application) {
        super(application);
        mPointRepository = new PointRepository(application);
        mAllPoint = mPointRepository.getPoints();
        if (mAllPoint.getValue() == null){
            loadDataFromSite();
            mAllPoint = mPointRepository.getPoints();
        }
        }
    public LiveData<List<Point>> getAllPoint() {
        return mAllPoint;
    }
    public LiveData<List<Point>> getDolmenPoint() {

        return null;
    }
    public void insert (Point point) {mPointRepository.insert(point);}
    public Point getPointById (int id) {return mAllPoint.getValue().get(id);}
    public void loadDataFromSite () {
        mPointRepository.getDataFromSite(getApplication());
    }
}
