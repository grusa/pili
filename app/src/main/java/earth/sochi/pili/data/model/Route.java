package earth.sochi.pili.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="routes")
public class Route {
    @PrimaryKey
    @ColumnInfo(name="id")
    private int id ;
    @ColumnInfo(name = "nameru")
    private String nameRu;
    @ColumnInfo(name="nameen")
    private String nameEn;

    public Route(@NonNull int id,
                 @NonNull String nameRu,
                 @NonNull String nameEn) {
        this.id = id;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
    }
    public int getId () {return id;}
    public void setId (int id) {this.id = id;}
    public String getNameRu() {return nameRu;}
    public void setNameRu(String nameRu) {this.nameRu=nameRu;}
    public String getNameEn() {return nameEn;}
    public void setNameEn(String nameEn) {this.nameRu=nameEn;}


}
