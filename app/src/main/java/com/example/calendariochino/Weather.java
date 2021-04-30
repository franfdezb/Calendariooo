package com.example.calendariochino;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Weather extends AppCompatActivity {

    TextView latitud;
    TextView  longitud;
    Button searchButton;
    TextView result;

    class Tiempo extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... address) {
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                String content = "";
                char ch;
                while (data != -1){
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                return content;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    public void search(View view){

        latitud = findViewById(R.id.latitud);
        longitud = findViewById(R.id.longitud);
        searchButton = findViewById(R.id.searchButton);
        result = findViewById(R.id.result);

        String lat = latitud.getText().toString();
        String lon = longitud.getText().toString();


        String content;
        Tiempo weather = new Tiempo();
        try {
            content = weather.execute("https://fcc-weather-api.glitch.me/api/current?lat="+lat+"&lon="+lon).get(); // 37.3826 -5.99629"

            Log.i("content",content);

            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String Dtemperatura = jsonObject.getString("main");
            String Dpais = jsonObject.getString("sys");

            Log.i("weatherData",weatherData);
            JSONArray array = new JSONArray(weatherData);

            String main = "";
            String pais= "";
            String temperatura= "";


            for(int i=0; i<array.length(); i++){
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                //description = weatherPart.getString("description");
            }

            JSONObject tem = new JSONObject(Dtemperatura);
            temperatura = tem.getString("temp");

            JSONObject pa = new JSONObject(Dpais);
            pais = pa.getString("country");


            Log.i("main",main);
            Log.i("sys",pais);
            Log.i("temp",temperatura);

            String resultado = "Tiempo: "+main+"\nPaís: " +pais+"\nTemperatura: " +temperatura;;

            result.setText(resultado);

        } catch (Exception e){
            e.printStackTrace();
        }


    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

    /*
        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://fcc-weather-api.glitch.me/api/current?lat=37.3826&lon=-5.99629").get();
            Log.i("content",content);
            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            Log.i("weatherData",weatherData);
            JSONArray array = new JSONArray(weatherData);
            String main = "";
            String description= "";
            for(int i=0; i<array.length(); i++){
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");
            }
            Log.i("main",main);
            Log.i("description",description);
        } catch (Exception e){
            e.printStackTrace();
        }
    */
    }



}