package photodatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "captions", foreignKeys = @ForeignKey(entity = Photo.class,
                                                            parentColumns = "fileName",
                                                            childColumns = "fileName"),
        indices = {@Index(value = {"caption"}, unique = true)})
public class Caption {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String caption;

    @NonNull
    @ColumnInfo(name = "file_name")
    public String fileName;
}
