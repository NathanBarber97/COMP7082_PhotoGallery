package com.example.nbar.photogallery;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import androidx.room.Room;
import photodatabase.Photo;
import photodatabase.PhotoDatabase;
import photodatabase.PhotoDAO;

@RunWith(AndroidJUnit4.class)
public class PhotoDatabaseTest {
    private PhotoDatabase db;
    private PhotoDAO dao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, PhotoDatabase.class).build();
        dao = db.getPhotoDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeAndReadPhoto() throws IOException {
        String testFile = "file1";
        Date testDate = new Date(0);
        float testLat = 1;
        float testLong = 2;

        Photo photo = new Photo(testFile, testDate, testLat, testLong);

        dao.insertPhoto(photo);

        Photo byFileName = dao.getPhoto(testFile);
        assert(byFileName.fileName.equals(testFile));
    }
}
