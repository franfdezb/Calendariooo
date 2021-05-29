package com.example.calendariochino;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.os.Bundle;

public class Linterna extends AppCompatActivity {

    Button btnlinterna;
    Camera camera;
    Camera.Parameters parameters;
    boolean isFlash = false;
    boolean isOn = false;

    @Override
    protected void onStop() {
        super.onStop();
        if(camera != null){
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnlinterna = findViewById(R.id.btn_linterna);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,}, 1000);
        } else {


            if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                camera = Camera.open();
                parameters = camera.getParameters();
                isFlash = true;
            }


            btnlinterna.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (isFlash) {

                        if (!isOn) {
                            //btnlinterna.setImageResource(R.drawable.on);
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            camera.setParameters(parameters);
                            camera.startPreview();
                            isOn = true;
                        } else {
                            //btnlinterna.setImageResource(R.drawable.off);
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            camera.setParameters(parameters);
                            camera.stopPreview();
                            isOn = false;

                        }


                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Linterna.this);
                        builder.setTitle("Error");
                        builder.setMessage("flash no compatible");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }

                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                }


            });

        }
    }
}