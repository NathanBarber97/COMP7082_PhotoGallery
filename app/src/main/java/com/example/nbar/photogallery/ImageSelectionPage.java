package com.example.nbar.photogallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageSelectionPage extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_SEARCH = 2;
    public static final String EXTRA_IMAGES_LIST = "com.example.nbar.photogallery.IMAGES";
    int currentImageNum;
    ImageView mainImage;
    String currentPhotoPath, currentFileName;
    TextView imageInfo;
    File[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection_page);
        mainImage = findViewById(R.id.main_image_view);
        imageInfo = findViewById(R.id.image_info_text);
        updateImageList();
        setToLatestImage();
    }

    private void setToLatestImage() {
        currentImageNum = images.length - 1;
        updateImage();
    }

    private void updateImageList() {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        images = storageDir.listFiles();
    }

    private void updateImage() {
        Bitmap imageBitmap = BitmapFactory.decodeFile(images[currentImageNum].getAbsolutePath());
        mainImage.setImageBitmap(imageBitmap);
        imageInfo.setText(images[currentImageNum].getName());
    }



    public void snapClicked(View view) {
        this.dispatchTakePictureIntent();
    }

    public void leftClicked(View view) {
        if (currentImageNum < images.length - 1) {
            currentImageNum ++;
        }

        updateImage();
    }

    public void rightClicked(View view) {
        if (currentImageNum > 0) {
            currentImageNum --;
        }

        updateImage();
    }

    public void optionsClicked(View view) {

    }

    public void searchClicked(View view) {
        Intent intent = new Intent(this, ImageSearchPage.class);
        String[] filePaths = new String[images.length];
        for (int i = 0; i < images.length; i++)
            filePaths[i] = images[i].getAbsolutePath();

        intent.putExtra(EXTRA_IMAGES_LIST, filePaths);
        startActivityForResult(intent, REQUEST_IMAGE_SEARCH);
    }

    //code taken from https://developer.android.com/training/camera/photobasics
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.nbar.photogallery",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    //code taken from https://developer.android.com/training/camera/photobasics
    //well.. for the most part except for the part where it tried to read data which was always null
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            imageInfo.setText(currentFileName);
            mainImage.setImageBitmap(imageBitmap);
            galleryAddPic();
            updateImageList();
            setToLatestImage();
        }

        if (requestCode == REQUEST_IMAGE_SEARCH && resultCode == RESULT_OK) {
            String[] filePaths = data.getStringArrayExtra(ImageSearchPage.EXTRA_SEARCH_RESULT_IMAGES);
            if(filePaths != null) {
                images = new File[filePaths.length];
                for (int i = 0; i < filePaths.length; i++)
                    images[i] = new File(filePaths[i]);
            }
            setToLatestImage();
        }
    }

    //code taken from https://developer.android.com/training/camera/photobasics
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    //code taken from https://developer.android.com/training/camera/photobasics
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat(getResources().getString(R.string.save_date_format)).format(new Date());
        currentFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, currentFileName);

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //if I need to check if there is storage space go to https://developer.android.com/training/data-storage/files
}
