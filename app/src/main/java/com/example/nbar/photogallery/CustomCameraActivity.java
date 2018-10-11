package com.example.nbar.photogallery;

import android.hardware.Camera;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class CustomCameraActivity extends AppCompatActivity {

    Camera camera;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);
        frameLayout = findViewById(R.id.frame_layout);

        camera = Camera.open();

    }
}
