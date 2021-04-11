package com.example.calendariochino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void calendar (View view){

        Intent myIntent;
        myIntent = new Intent(view.getContext(), Calendario.class);
        startActivity(myIntent);

    }

}