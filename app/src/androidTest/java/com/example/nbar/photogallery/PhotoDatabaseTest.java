package com.example.nbar.photogallery;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.room.Room;
import photodatabase.Caption;
import photodatabase.Photo;
import photodatabase.PhotoDatabase;
import photodatabase.PhotoDAO;

@RunWith(AndroidJUnit4.class)
public class PhotoDatabaseTest {
    private PhotoDatabase db;
    private PhotoDAO dao;
    private String testFile, testCaption;
    private Calendar testDate;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, PhotoDatabase.class).build();
        dao = db.getPhotoDAO();

        testFile = "file1";
        testCaption = "Caption";
        testDate = Calendar.getInstance();
        testDate.set(2018, 9, 27);
        float testLat = 1000;
        float testLong = 2000;

        Photo photo = new Photo(testFile, testDate.getTimeInMillis(), testLat, testLong);
        Caption caption  = new Caption(testCaption, testFile);

        dao.insertPhoto(photo);
        dao.insertCaption(caption);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void readPhoto() throws IOException {
        Photo byFileName = dao.getPhoto(testFile);
        assert(byFileName.fileName.equals(testFile));
    }

    @Test
    public void readCaption() throws IOException {
        List<String> byFileName = dao.getCaptionsOfPhoto(testFile);
        assert(byFileName.get(0).equals(testCaption));
    }

    @Test
    public void searchByDate() throws IOException {
        List<Photo> byDate = dao.searchByDate(testDate.getTimeInMillis() - 100, testDate.getTimeInMillis() + 100);
        assert(byDate.get(0).fileName.equals(testFile));
    }

    @Test
    public void searchByLocation() throws IOException {
        List<Photo> byLocation = dao.searchByLocation(900, 1100, 1900, 2100);
        assert(byLocation.get(0).fileName.equals(testFile));
    }

    @Test
    public void searchByCaption() throws IOException {
        List<Photo> byDate = dao.searchByCaption(testCaption);
        assert(byDate.get(0).fileName.equals(testFile));
    }

    @Test
    public void searchByAll() throws IOException {
        List<Photo> byAll = dao.searchByAll(testDate.getTimeInMillis() - 100,
                testDate.getTimeInMillis() + 100,
                900, 1100, 1900, 2100 ,testCaption);
        assert(byAll.get(0).fileName.equals(testFile));
    }
}
