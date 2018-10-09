package photodatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Photo.class, Caption.class}, version = 1)
public abstract class PhotoDatabase extends RoomDatabase {
    public abstract PhotoDAO getPhotoDAO();
}
