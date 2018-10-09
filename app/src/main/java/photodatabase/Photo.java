package photodatabase;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "photos")
public class Photo {
    @PrimaryKey
    @ColumnInfo(name = "file_name")
    public String fileName;

    @ColumnInfo(name = "date_time")
    public Date dateTime;

    public float latitude;
    public float longitude;

    public Photo(String fileName, Date dateTime, float latitude, float longitude) {
        this.fileName = fileName;
        this.dateTime = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
