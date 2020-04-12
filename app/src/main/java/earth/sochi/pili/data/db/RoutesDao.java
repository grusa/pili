package earth.sochi.pili.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;
import earth.sochi.pili.data.model.Route;

@Dao
public interface RoutesDao {
    @Query("SELECT id,nameRu,nameen FROM routes ORDER BY nameRu ASC")
    LiveData<List<Route>> getRoutes();
}
