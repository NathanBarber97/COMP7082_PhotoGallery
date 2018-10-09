package com.example.nbar.photogallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageSearchPage extends AppCompatActivity {
    public static final String EXTRA_SEARCH_RESULT_IMAGES = "com.example.nbar.photogallery.IMAGES";
    File[] images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_search_page);
        Intent callingIntent = getIntent();
        String[] filePaths = callingIntent.getStringArrayExtra(ImageSelectionPage.EXTRA_IMAGES_LIST);
        images= new File[filePaths.length];
        for(int i  = 0; i < filePaths.length; i++)
            images[i] = new File(filePaths[i]);
        /*Button searchGoButton = findViewById(R.id.search_go_button);
        searchGoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                searchGoClicked(view);
            }
        });*/
    }

    public void cancelClicked(View view) {
        finish();
    }

    public void searchGoClicked(View view) {
        String startTime, endTime;
        SimpleDateFormat df = new SimpleDateFormat(getResources().getString(R.string.input_date_format));
        Date startDate = null, endDate = null, imageDate;
        List<File> validImages = new ArrayList<File>();
        startTime = ((EditText)findViewById(R.id.time_start_edittext)).getText().toString();
        endTime = ((EditText)findViewById(R.id.time_stop_edittext)).getText().toString();
        if(startTime != "") {
            try {
                startDate = df.parse(startTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(startDate == null)
            startDate = new Date(0);

        if(endTime != "") {
            try {
                endDate = df.parse(endTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(endDate == null)
            endDate = new Date();

        df.applyPattern("'JPEG_'yyyyMMdd_HHmmss'.jpg'");

        for(File image : images) {
            imageDate = null;
            try {
                imageDate = df.parse(image.getName());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (imageDate != null)
                if(imageDate.after(startDate) && imageDate.before(endDate))
                    validImages.add(image);
        }

        Intent intent = new Intent(this, ImageSelectionPage.class);
        String[] filePaths = new String[validImages.size()];
        for (int i = 0; i < validImages.size(); i++)
            filePaths[i] = validImages.get(i).getAbsolutePath();

        intent.putExtra(EXTRA_SEARCH_RESULT_IMAGES, filePaths);
        setResult(RESULT_OK, intent);
        finish();
    }
}
