package com.example.calendariochino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    protected void initView() {

    }

    protected void initData(){


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_activity_main);
        //setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        initView();
        initData();

    }

    public void calendar (View view){

        Intent myIntent;

        myIntent = new Intent(view.getContext(), Calendario.class);

        startActivity(myIntent);

    }

    public void weather (View view){

        Intent myIntent;

        myIntent = new Intent(view.getContext(), Weather.class);

        startActivity(myIntent);

    }

    public void Chronometer (View view){

        Intent myIntent;

        myIntent = new Intent(view.getContext(), Chronometer.class);

        startActivity(myIntent);

    }

    public void MapsActivity (View view){

        Intent myIntent;

        myIntent = new Intent(view.getContext(), MapsActivity.class);

        startActivity(myIntent);

    }



}
