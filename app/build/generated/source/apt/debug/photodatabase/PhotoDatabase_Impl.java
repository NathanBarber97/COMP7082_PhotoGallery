package photodatabase;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class PhotoDatabase_Impl extends PhotoDatabase {
  private volatile PhotoDAO _photoDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `photos` (`file_name` TEXT NOT NULL, `date_time` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, PRIMARY KEY(`file_name`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `captions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `caption` TEXT NOT NULL, `file_name` TEXT NOT NULL, FOREIGN KEY(`file_name`) REFERENCES `photos`(`file_name`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE UNIQUE INDEX `index_captions_caption` ON `captions` (`caption`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"acb8747cab0724c892bee337e08cec2e\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `photos`");
        _db.execSQL("DROP TABLE IF EXISTS `captions`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPhotos = new HashMap<String, TableInfo.Column>(4);
        _columnsPhotos.put("file_name", new TableInfo.Column("file_name", "TEXT", true, 1));
        _columnsPhotos.put("date_time", new TableInfo.Column("date_time", "INTEGER", true, 0));
        _columnsPhotos.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0));
        _columnsPhotos.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPhotos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPhotos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPhotos = new TableInfo("photos", _columnsPhotos, _foreignKeysPhotos, _indicesPhotos);
        final TableInfo _existingPhotos = TableInfo.read(_db, "photos");
        if (! _infoPhotos.equals(_existingPhotos)) {
          throw new IllegalStateException("Migration didn't properly handle photos(photodatabase.Photo).\n"
                  + " Expected:\n" + _infoPhotos + "\n"
                  + " Found:\n" + _existingPhotos);
        }
        final HashMap<String, TableInfo.Column> _columnsCaptions = new HashMap<String, TableInfo.Column>(3);
        _columnsCaptions.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsCaptions.put("caption", new TableInfo.Column("caption", "TEXT", true, 0));
        _columnsCaptions.put("file_name", new TableInfo.Column("file_name", "TEXT", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCaptions = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCaptions.add(new TableInfo.ForeignKey("photos", "NO ACTION", "NO ACTION",Arrays.asList("file_name"), Arrays.asList("file_name")));
        final HashSet<TableInfo.Index> _indicesCaptions = new HashSet<TableInfo.Index>(1);
        _indicesCaptions.add(new TableInfo.Index("index_captions_caption", true, Arrays.asList("caption")));
        final TableInfo _infoCaptions = new TableInfo("captions", _columnsCaptions, _foreignKeysCaptions, _indicesCaptions);
        final TableInfo _existingCaptions = TableInfo.read(_db, "captions");
        if (! _infoCaptions.equals(_existingCaptions)) {
          throw new IllegalStateException("Migration didn't properly handle captions(photodatabase.Caption).\n"
                  + " Expected:\n" + _infoCaptions + "\n"
                  + " Found:\n" + _existingCaptions);
        }
      }
    }, "acb8747cab0724c892bee337e08cec2e", "efe705274ad4f8c9b8b888e2a117bce8");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "photos","captions");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `captions`");
      _db.execSQL("DELETE FROM `photos`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public PhotoDAO getPhotoDAO() {
    if (_photoDAO != null) {
      return _photoDAO;
    } else {
      synchronized(this) {
        if(_photoDAO == null) {
          _photoDAO = new PhotoDAO_Impl(this);
        }
        return _photoDAO;
      }
    }
  }
}
