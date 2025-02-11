package com.example.uaoremoto;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UbicacionAula extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String idaula, lat, lon;
    public double latitud = 0;
    public double longitud = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_aula);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
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

        idaula = getIntent().getStringExtra("idaula");
        lat = getIntent().getStringExtra("latitud");
        lon = getIntent().getStringExtra("longitud");

        latitud = Double.parseDouble(lat);
        longitud = Double.parseDouble(lon);

        // Add a marker in Sydney and move the camera
        Log.i("latitud2", ""+latitud);
        Log.i("longitud2", ""+longitud);
        LatLng aula = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(aula).title("Marcado en: " + idaula));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aula, 19));
    }

}