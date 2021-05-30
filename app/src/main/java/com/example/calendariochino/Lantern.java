package com.example.calendariochino;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.os.Bundle;

public class Lantern extends AppCompatActivity {
    ImageButton switchOff,switchOn;
    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linterna);

        switchOff = findViewById(R.id.switch_off);
        switchOn = findViewById(R.id.switch_on);

        camera = Camera.open();
        final Camera.Parameters parameters = camera.getParameters();

        switchOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchOff.setVisibility(View.GONE);
                switchOn.setVisibility(View.VISIBLE);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
            }
        });

        switchOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchOff.setVisibility(View.VISIBLE);
                switchOn.setVisibility(View.GONE);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.stopPreview();

            }
        });
    }
}