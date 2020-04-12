package earth.sochi.pili.data;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import earth.sochi.pili.data.db.CatalogesDao;
import earth.sochi.pili.data.db.PointsDao;
import earth.sochi.pili.data.db.RoutesDao;
import earth.sochi.pili.data.model.Catalog;
import earth.sochi.pili.data.model.Converter;
import earth.sochi.pili.data.model.Point;
import earth.sochi.pili.data.model.Route;

@Database(
        entities = {Point.class, Route.class, Catalog.class},
        version=1,exportSchema = false
)
@TypeConverters({Converter.class})
public abstract class PointRoomDatabase extends RoomDatabase {
    public abstract PointsDao pointDao();
    public abstract RoutesDao routesDao();
    public abstract CatalogesDao catalogesDao();
    private static String TAG = "DBRoom";

    private static volatile PointRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static PointRoomDatabase getDatabase(final Context context) {
        if (INSTANCE==null) {
            synchronized (PointRoomDatabase.class) {
                if (INSTANCE==null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                            PointRoomDatabase.class,"points_database")
                            .addCallback(sPointRoomDatabase)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static PointRoomDatabase.Callback sPointRoomDatabase =
            new PointRoomDatabase.Callback()
    {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
