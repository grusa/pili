package earth.sochi.pili.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import earth.sochi.pili.data.model.Point;

@Dao
public interface PointsDao {
    @Query("SELECT id,name, description,lat,lon,cat_id,route_id,smallimagerl,imageurl FROM points ORDER BY name ASC")
    LiveData<List<Point>> getAllPoints();
    //@Query("SELECT id,name,description,lat,lon,photo,cat_id,route_id FROM points WHERE cat_id=1 ORDER BY name ASC")
    @Query("SELECT * FROM points WHERE cat_id=1 ORDER BY name ASC")
    LiveData<List<Point>> getDolmenPoints();
    //@Query("SELECT id,name,description,lat,lon,photo,cat_id,route_id FROM points WHERE id in (:ids) ORDER BY name ASC")
    @Query("SELECT * FROM points WHERE name in (:names) ORDER BY name ASC")
    LiveData<List<Point>> getPointsByNames(List<String> names);
    @Query("SELECT * FROM points WHERE id = :id ORDER BY name ASC")
    LiveData<List<Point>> getPointsById(int id);
    @Query("SELECT * FROM points WHERE id = :id ORDER BY name ASC")
    Point getPointById(int id);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Point point);
    @Query("SELECT COUNT(*) FROM points")
    int getPointsCount();
    @Query("DELETE FROM points")
    int deletePoints();
}
