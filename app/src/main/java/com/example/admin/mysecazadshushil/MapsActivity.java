package com.example.admin.mysecazadshushil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.GooglePlayServicesUtil;

import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapView;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Dialog mydial;
    RelativeLayout errormassege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mydial=new Dialog(this);
        errormassege=(RelativeLayout)findViewById(R.id.errormassegemap);
        if(isNetworkAvailable()==false)
        {
            errormassege.setVisibility(View.VISIBLE);
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
        LatLng sec = new LatLng(24.911766,91.9041066);
        LatLng ambarkhan = new LatLng(24.9053964,91.8689633);
        LatLng bondorbazar = new LatLng(24.8919596,91.8725366);
        LatLng baluchor = new LatLng(24.903207,91.8954367);
        LatLng tilagor = new LatLng(24.8961414,91.9001386);
        mMap.addMarker(new MarkerOptions().position(sec).title("SEC"));
        mMap.addMarker(new MarkerOptions().position(ambarkhan).title("AmbarKhana Point"));
        mMap.addMarker(new MarkerOptions().position(bondorbazar).title("Bondor Bazar"));
        mMap.addMarker(new MarkerOptions().position(baluchor).title("Baluchor Point"));
        mMap.addMarker(new MarkerOptions().position(tilagor).title("TilaGhar Point"));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(sec, 15f)
        );
        final PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(bondorbazar);
        polylineOptions.add(ambarkhan);
        polylineOptions.add(baluchor);
        polylineOptions.add(sec);
        polylineOptions.width(7f);
        polylineOptions.color(Color.RED);
        polylineOptions.zIndex(1000);
        mMap.addPolyline(polylineOptions);



        final PolylineOptions polylineOptions1=new PolylineOptions();
        polylineOptions1.add(bondorbazar);
        polylineOptions1.add(tilagor);
        polylineOptions1.add(baluchor);
        polylineOptions1.add(sec);
        polylineOptions1.width(6f);
        polylineOptions1.color(Color.BLACK);
        polylineOptions1.zIndex(1000);
        mMap.addPolyline(polylineOptions1);


        final CircleOptions circleOptions=new CircleOptions();
        circleOptions.center(sec).radius(300).strokeWidth(4f).strokeColor(Color.BLUE).fillColor(Color.argb(70,150,50,70));
        mMap.addCircle(circleOptions);


        final CircleOptions circleOptions2=new CircleOptions();
        circleOptions2.center(ambarkhan).radius(200).strokeWidth(4f).strokeColor(Color.BLUE).fillColor(Color.argb(70,150,50,70));
        mMap.addCircle(circleOptions2);

        final CircleOptions circleOptions3=new CircleOptions();
        circleOptions3.center(bondorbazar).radius(230).strokeWidth(4f).strokeColor(Color.BLUE).fillColor(Color.argb(70,150,50,70));
        mMap.addCircle(circleOptions3);

        final CircleOptions circleOptions4=new CircleOptions();
        circleOptions4.center(baluchor).radius(180).strokeWidth(4f).strokeColor(Color.BLUE).fillColor(Color.argb(70,150,50,70));
        mMap.addCircle(circleOptions4);

        final CircleOptions circleOptions5=new CircleOptions();
        circleOptions5.center(tilagor).radius(200).strokeWidth(4f).strokeColor(Color.BLUE).fillColor(Color.argb(70,150,50,70));
        mMap.addCircle(circleOptions5);

    }

    public void Helpdialogue(View view) {
        mydial.setContentView(R.layout.helpdialoguebox);
        mydial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydial.show();
    }

    public void backfrommaps(View view) {
        Intent intent=new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }
}
