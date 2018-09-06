package com.fff.ingood.activity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.fff.ingood.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private MapView mMapView;
    private String mAddress;
    private GoogleMap mGoogleMap;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = (Context)getApplication();
        setContentView(R.layout.activity_maps);
        mMapView = (MapView)findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        Bundle bundle =this.getIntent().getExtras();
        mAddress = bundle.getString("address");


        try
        {
            initilizeMap();
        }
        catch (Exception e)
        {
        }

    }

    private void initilizeMap()
    {
        if ( mGoogleMap == null )
        {
            mMapView.getMapAsync(this);
        }
    }

    @Override
    protected void onResume()
    {
        mMapView.onResume();
        super.onResume();

        initilizeMap();
    }

    @Override
    public void onMapReady(GoogleMap map)
    {
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressLocation = null;
        try {
            addressLocation = geoCoder.getFromLocationName(mAddress, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addressLocation != null && addressLocation.size() > 0){
            double latitude = addressLocation.get(0).getLatitude();
            double longitude = addressLocation.get(0).getLongitude();
            LatLng location = new LatLng(latitude, longitude);
            map.addMarker(new MarkerOptions().position(location).title("Marker in Sydney"));
            map.moveCamera(CameraUpdateFactory.newLatLng(location));map.addMarker(new MarkerOptions()
                    .position(location)
                    .title("Marker"));
        }
        else{

            LatLng location = new LatLng(25, 121);
            map.addMarker(new MarkerOptions().position(location).title("Marker in Sydney"));
            map.moveCamera(CameraUpdateFactory.newLatLng(location));map.addMarker(new MarkerOptions()
                    .position(location)
                    .title("Marker"));
        }
        CameraUpdate zoom =CameraUpdateFactory.zoomTo(15);
        map.animateCamera(zoom);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}


