package earth.sochi.pili.data.model;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;
import androidx.room.Entity;
import androidx.room.ColumnInfo;

@Entity(tableName = "points")
    public class Point {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        private int id;
        @NonNull
        @ColumnInfo( name = "name")
        private String mName;
        @ColumnInfo(name = "description")
        private String mDescription;
        @ColumnInfo(name = "lat")
        private Double mLatitude;
        @ColumnInfo(name = "lon")
        private Double mLongitude;
        @ColumnInfo(name = "cat_id")
        private int mCatId;
        @ColumnInfo(name = "route_id")
        private int mRouteId;
        @ColumnInfo(name = "smallimagerl")
        private String smallImageUrl;
        @ColumnInfo(name = "imageurl")
        private String imageUrl;

        public Point(int id,
                     String name,
                     String description,
                     Double latitude,
                     Double longitude,
                     int catId,int routeId,
                     String smallImageUrl,
                     String imageUrl) {
            this.id = id;
            this.mName = name;
            this.mDescription = description;
            this.mLatitude = latitude;
            this.mLongitude = longitude;
            this.smallImageUrl=smallImageUrl;
            this.imageUrl = imageUrl;
            //this.pointDate=pointDate;
            this.mCatId = catId;
            this.mRouteId = routeId;
        }
        public Point getPoint() {return this;}

        public int getId() {return id;}

        public void setId(Integer id) {this.id = id;}


        public String getName() {
        return mName;
    }
        public void setName(String name) {
        this.mName = name;
    }

        public String getDescription() { return mDescription;}
        public void setDescription(String description) { this.mDescription = description;}

        public Double getLatitude() { return mLatitude;}
        public void setLatitude (Double latitude) {this.mLatitude = latitude;}

        public Double getLongitude() {return mLongitude;}
        public void setLongitude (Double longitude) {this.mLongitude = longitude;}

        public String getSmallImageUrl() {
            return smallImageUrl;
        }
        public void setSmallImageUrl(String smallImageUrl) {
            this.smallImageUrl = smallImageUrl;
        }
        public String getImageUrl() {
            return imageUrl;
        }
        public void setImageUrl(String ImageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getCatId() { return mCatId; }
        public void setCatId(int catId) { this.mCatId = catId;}

        public int getRouteId() {return mRouteId; }
        public void setRouteId(int routeId) { this.mRouteId = routeId;}
}
