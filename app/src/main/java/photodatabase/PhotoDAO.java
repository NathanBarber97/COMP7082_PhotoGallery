package photodatabase;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PhotoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertPhoto(Photo photo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertAllPhotos(Photo... photos);

    @Delete
    public void deletePhotos(Photo... photos);

    @Query("SELECT * FROM photos ORDER BY date_time, file_name DESC")
    public List<Photo> getAllPhotos();

    @Query("SELECT * FROM photos WHERE file_name=:fileName")
    public Photo getPhoto(String fileName);

    @Insert
    public void insertCaption(Caption caption);

    @Insert
    public void insertAllCaptions(Caption... captions);

    @Delete
    public void deleteCaptions(Caption... captions);

    @Query("DELETE FROM captions WHERE caption NOT IN :currentCaptions")
    public void deleteOldCaptions(String[] currentCaptions);

    @Query("SELECT caption FROM captions WHERE file_name=:fileName")
    public List<String> getCaptionsOfPhoto(String fileName);

}
