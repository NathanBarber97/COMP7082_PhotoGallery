package photodatabase;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class PhotoDAO_Impl implements PhotoDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPhoto;

  private final EntityInsertionAdapter __insertionAdapterOfCaption;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPhoto;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfCaption;

  public PhotoDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPhoto = new EntityInsertionAdapter<Photo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `photos`(`file_name`,`date_time`,`latitude`,`longitude`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Photo value) {
        if (value.fileName == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.fileName);
        }
        stmt.bindLong(2, value.dateTime);
        stmt.bindDouble(3, value.latitude);
        stmt.bindDouble(4, value.longitude);
      }
    };
    this.__insertionAdapterOfCaption = new EntityInsertionAdapter<Caption>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `captions`(`id`,`caption`,`file_name`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Caption value) {
        stmt.bindLong(1, value.id);
        if (value.caption == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.caption);
        }
        if (value.fileName == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.fileName);
        }
      }
    };
    this.__deletionAdapterOfPhoto = new EntityDeletionOrUpdateAdapter<Photo>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `photos` WHERE `file_name` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Photo value) {
        if (value.fileName == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.fileName);
        }
      }
    };
    this.__deletionAdapterOfCaption = new EntityDeletionOrUpdateAdapter<Caption>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `captions` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Caption value) {
        stmt.bindLong(1, value.id);
      }
    };
  }

  @Override
  public long insertPhoto(Photo photo) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfPhoto.insertAndReturnId(photo);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long[] insertAllPhotos(Photo... photos) {
    __db.beginTransaction();
    try {
      long[] _result = __insertionAdapterOfPhoto.insertAndReturnIdsArray(photos);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertCaption(Caption caption) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCaption.insert(caption);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAllCaptions(Caption... captions) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCaption.insert(captions);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletePhotos(Photo... photos) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPhoto.handleMultiple(photos);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCaptions(Caption... captions) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfCaption.handleMultiple(captions);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Photo> getAllPhotos() {
    final String _sql = "SELECT * FROM photos ORDER BY date_time, file_name DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfFileName = _cursor.getColumnIndexOrThrow("file_name");
      final int _cursorIndexOfDateTime = _cursor.getColumnIndexOrThrow("date_time");
      final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
      final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
      final List<Photo> _result = new ArrayList<Photo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Photo _item;
        final String _tmpFileName;
        _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
        final long _tmpDateTime;
        _tmpDateTime = _cursor.getLong(_cursorIndexOfDateTime);
        final float _tmpLatitude;
        _tmpLatitude = _cursor.getFloat(_cursorIndexOfLatitude);
        final float _tmpLongitude;
        _tmpLongitude = _cursor.getFloat(_cursorIndexOfLongitude);
        _item = new Photo(_tmpFileName,_tmpDateTime,_tmpLatitude,_tmpLongitude);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Photo getPhoto(String fileName) {
    final String _sql = "SELECT * FROM photos WHERE file_name=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (fileName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fileName);
    }
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfFileName = _cursor.getColumnIndexOrThrow("file_name");
      final int _cursorIndexOfDateTime = _cursor.getColumnIndexOrThrow("date_time");
      final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
      final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
      final Photo _result;
      if(_cursor.moveToFirst()) {
        final String _tmpFileName;
        _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
        final long _tmpDateTime;
        _tmpDateTime = _cursor.getLong(_cursorIndexOfDateTime);
        final float _tmpLatitude;
        _tmpLatitude = _cursor.getFloat(_cursorIndexOfLatitude);
        final float _tmpLongitude;
        _tmpLongitude = _cursor.getFloat(_cursorIndexOfLongitude);
        _result = new Photo(_tmpFileName,_tmpDateTime,_tmpLatitude,_tmpLongitude);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Photo> searchByDate(long start, long end) {
    final String _sql = "SELECT * FROM photos WHERE date_time BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    _argIndex = 2;
    _statement.bindLong(_argIndex, end);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfFileName = _cursor.getColumnIndexOrThrow("file_name");
      final int _cursorIndexOfDateTime = _cursor.getColumnIndexOrThrow("date_time");
      final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
      final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
      final List<Photo> _result = new ArrayList<Photo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Photo _item;
        final String _tmpFileName;
        _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
        final long _tmpDateTime;
        _tmpDateTime = _cursor.getLong(_cursorIndexOfDateTime);
        final float _tmpLatitude;
        _tmpLatitude = _cursor.getFloat(_cursorIndexOfLatitude);
        final float _tmpLongitude;
        _tmpLongitude = _cursor.getFloat(_cursorIndexOfLongitude);
        _item = new Photo(_tmpFileName,_tmpDateTime,_tmpLatitude,_tmpLongitude);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Photo> searchByLocation(long topLeftLat, long botRightLat, long topLeftLong,
      long botRightLong) {
    final String _sql = "SELECT * FROM photos WHERE (latitude BETWEEN ? AND ?) AND (longitude BETWEEN ? AND ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, topLeftLat);
    _argIndex = 2;
    _statement.bindLong(_argIndex, botRightLat);
    _argIndex = 3;
    _statement.bindLong(_argIndex, topLeftLong);
    _argIndex = 4;
    _statement.bindLong(_argIndex, botRightLong);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfFileName = _cursor.getColumnIndexOrThrow("file_name");
      final int _cursorIndexOfDateTime = _cursor.getColumnIndexOrThrow("date_time");
      final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
      final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
      final List<Photo> _result = new ArrayList<Photo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Photo _item;
        final String _tmpFileName;
        _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
        final long _tmpDateTime;
        _tmpDateTime = _cursor.getLong(_cursorIndexOfDateTime);
        final float _tmpLatitude;
        _tmpLatitude = _cursor.getFloat(_cursorIndexOfLatitude);
        final float _tmpLongitude;
        _tmpLongitude = _cursor.getFloat(_cursorIndexOfLongitude);
        _item = new Photo(_tmpFileName,_tmpDateTime,_tmpLatitude,_tmpLongitude);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Photo> searchByCaption(String caption) {
    final String _sql = "SELECT * FROM photos WHERE file_name IN (SELECT DISTINCT file_name FROM captions WHERE caption=?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (caption == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, caption);
    }
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfFileName = _cursor.getColumnIndexOrThrow("file_name");
      final int _cursorIndexOfDateTime = _cursor.getColumnIndexOrThrow("date_time");
      final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
      final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
      final List<Photo> _result = new ArrayList<Photo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Photo _item;
        final String _tmpFileName;
        _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
        final long _tmpDateTime;
        _tmpDateTime = _cursor.getLong(_cursorIndexOfDateTime);
        final float _tmpLatitude;
        _tmpLatitude = _cursor.getFloat(_cursorIndexOfLatitude);
        final float _tmpLongitude;
        _tmpLongitude = _cursor.getFloat(_cursorIndexOfLongitude);
        _item = new Photo(_tmpFileName,_tmpDateTime,_tmpLatitude,_tmpLongitude);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Photo> searchByAll(long start, long end, long topLeftLat, long botRightLat,
      long topLeftLong, long botRightLong, String caption) {
    final String _sql = "SELECT * FROM photos WHERE (date_time BETWEEN ? AND ?) AND (latitude BETWEEN ? AND ?) AND (longitude BETWEEN ? AND ?)AND (file_name IN (SELECT DISTINCT file_name FROM captions WHERE caption=?))";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 7);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    _argIndex = 2;
    _statement.bindLong(_argIndex, end);
    _argIndex = 3;
    _statement.bindLong(_argIndex, topLeftLat);
    _argIndex = 4;
    _statement.bindLong(_argIndex, botRightLat);
    _argIndex = 5;
    _statement.bindLong(_argIndex, topLeftLong);
    _argIndex = 6;
    _statement.bindLong(_argIndex, botRightLong);
    _argIndex = 7;
    if (caption == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, caption);
    }
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfFileName = _cursor.getColumnIndexOrThrow("file_name");
      final int _cursorIndexOfDateTime = _cursor.getColumnIndexOrThrow("date_time");
      final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
      final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
      final List<Photo> _result = new ArrayList<Photo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Photo _item;
        final String _tmpFileName;
        _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
        final long _tmpDateTime;
        _tmpDateTime = _cursor.getLong(_cursorIndexOfDateTime);
        final float _tmpLatitude;
        _tmpLatitude = _cursor.getFloat(_cursorIndexOfLatitude);
        final float _tmpLongitude;
        _tmpLongitude = _cursor.getFloat(_cursorIndexOfLongitude);
        _item = new Photo(_tmpFileName,_tmpDateTime,_tmpLatitude,_tmpLongitude);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<String> getCaptionsOfPhoto(String fileName) {
    final String _sql = "SELECT caption FROM captions WHERE file_name=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (fileName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fileName);
    }
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        _item = _cursor.getString(0);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public void deleteOldCaptions(String[] currentCaptions) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("DELETE FROM captions WHERE caption NOT IN (");
    final int _inputSize = currentCaptions.length;
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
    int _argIndex = 1;
    for (String _item : currentCaptions) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }
}
