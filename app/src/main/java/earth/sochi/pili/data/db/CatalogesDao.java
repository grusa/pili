package earth.sochi.pili.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

import earth.sochi.pili.data.model.Catalog;

@Dao
public interface CatalogesDao {
    @Query("SELECT id,nameRu,nameEn from cataloges ORDER BY nameRu ASC")
    LiveData<List<Catalog>> getCataloges();
}
