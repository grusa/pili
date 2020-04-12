package earth.sochi.pili.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cataloges")
public class Catalog {
    @PrimaryKey
    private int id ;
    private String nameRu;
    private String nameEn;
    public Catalog (@NonNull int id, @NonNull String nameRu, String nameEn) {
        this.id = id;
        this.nameRu=nameRu;
        this.nameEn=nameEn;
    }
    public int getId() {return this.id;}
    public String getNameRu() {
        return this.nameRu;
    }
    public String getNameEn() {
        return this.nameEn;
    }

}
