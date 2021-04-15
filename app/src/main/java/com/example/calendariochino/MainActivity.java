package com.example.calendariochino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    protected void initView() {

    }

    protected void initData(){


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    public void calendar (View view){

        Intent myIntent;
        myIntent = new Intent(view.getContext(), Calendario.class);
        startActivity(myIntent);

    }

}