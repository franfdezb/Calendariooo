package com.example.calendariochino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

public class Chronometer extends AppCompatActivity {

    Button btn_start,btn_stop,btn_reset;
    android.widget.Chronometer cronometro;
    Boolean correr = false;
    long detenerse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_activity_chronomoter);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        btn_reset = findViewById(R.id.btn_reset);
        cronometro = findViewById(R.id.cronometro);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCronometro();
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopCronometro();
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetCronometro();
            }
        });
    }

    private void resetCronometro() {

        cronometro.setBase(SystemClock.elapsedRealtime());
        detenerse = 0;
    }

    private void stopCronometro() {

        if(correr){

            cronometro.stop();
            detenerse = SystemClock.elapsedRealtime() - cronometro.getBase();
            correr = false;
        }
    }

    private void startCronometro() {

        if(!correr){

            cronometro.setBase(SystemClock.elapsedRealtime() - detenerse);
            cronometro.start();
            correr = true;
        }
    }
}