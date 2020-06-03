package earth.sochi.pili.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

import earth.sochi.pili.data.model.Point;

public class PointViewModel extends AndroidViewModel {
    final String TAG = "PVM";
    private PointRepository mPointRepository;
    private LiveData<List<Point>> mAllPoint;
    private MediatorLiveData<List<Point>> mMediatorAllPoint;
    public int catalogId = 0; //0-All,1-Dolmen,2-Churches,3-Nature,4-Culture,5-Sport
    private androidx.lifecycle.Observer<List<Point>> observer;

    public PointViewModel (Application application) {
        super(application);
        mPointRepository = new PointRepository(application);
        mMediatorAllPoint = new MediatorLiveData<>();
        observer = new androidx.lifecycle.Observer<List<Point>>() {
            @Override
            public void onChanged(List<Point> points) {
                mMediatorAllPoint.setValue(points);
            }
        };
        if (mMediatorAllPoint.getValue() == null){
            loadDataFromSite();
        }
        }
    public LiveData<List<Point>> getAllPoint() {
        mAllPoint = null;
        if (catalogId==0) {
            mAllPoint = mPointRepository.getPoints();
        } else {
            mAllPoint = mPointRepository.getCatalogPoints(1);
        }
        return mAllPoint;
    }
    public MediatorLiveData<List<Point>> getPoints() {
        removeSource();
        switch (catalogId) {
            case  0: { //all
                mMediatorAllPoint.addSource(mPointRepository.getPoints(),observer);
                break;
            }
            case 1:{ //dolmen
                mMediatorAllPoint.addSource(mPointRepository.getDolmenPoints(),observer);
                break;
            }
            case 2: { //2-Churches
                mMediatorAllPoint.addSource(mPointRepository.getChurchePoints(),observer);
                break;
            }
            case 3: { //3-Nature
                mMediatorAllPoint.addSource(mPointRepository.getNaturePoints(),observer);
                break;
            }
            case 4: { //4-Culture
                mMediatorAllPoint.addSource(mPointRepository.getCulturePoints(),observer);
                break;
            }
            case 5: { //5-Sport
                mMediatorAllPoint.addSource(mPointRepository.getSportPoints(),observer);
            }
        }
        return mMediatorAllPoint;
    }
    private void removeSource() {
        mMediatorAllPoint.removeSource(mPointRepository.getPoints());
        mMediatorAllPoint.removeSource(mPointRepository.getDolmenPoints());
        mMediatorAllPoint.removeSource(mPointRepository.getNaturePoints());
        mMediatorAllPoint.removeSource(mPointRepository.getChurchePoints());
        mMediatorAllPoint.removeSource(mPointRepository.getCulturePoints());
    }
    public void insert (Point point) {mPointRepository.insert(point);}
    public Point getPointById (int id) {return mAllPoint.getValue().get(id);}
    public void loadDataFromSite () {
        mPointRepository.getDataFromSite(getApplication());
    }
}
