package com.example.calendariochino;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button mTypeBtn;
    private Button mTypeBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mTypeBtn = (Button) findViewById(R.id.btnSatelite);
        mTypeBtn2 = (Button) findViewById(R.id.btnHibrido);

        mTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        mTypeBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sevilla = new LatLng(37.356403, -5.981611);
        mMap.addMarker(new MarkerOptions().position(sevilla).title("Marker en Benito Villamarín"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla));
    }
}