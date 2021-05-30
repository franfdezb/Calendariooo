package com.example.calendariochino;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Lantern extends AppCompatActivity {
    private CameraManager cameraManager;
    private String cameraID;
    ImageButton switchOff,switchOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linterna);

        switchOff = findViewById(R.id.switch_off);
        switchOn = findViewById(R.id.switch_on);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            cameraID = cameraManager.getCameraIdList()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        switchOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cameraManager.setTorchMode(cameraID, true);
                    switchOff.setVisibility(View.GONE);
                    switchOn.setVisibility(View.VISIBLE);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        switchOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cameraManager.setTorchMode(cameraID, true);
                    switchOn.setVisibility(View.GONE);
                    switchOff.setVisibility(View.VISIBLE);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}