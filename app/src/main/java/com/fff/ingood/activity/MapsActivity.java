package com.fff.ingood.activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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

    public static final String INTENT_PUT_EXTRA_KEY = "map_address";
    private MapView mMapView;
    private String mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null) {
            mAddress = bundle.getString(INTENT_PUT_EXTRA_KEY);

            mMapView = findViewById(R.id.mapview);
            if (mMapView != null) {
                mMapView.onCreate(savedInstanceState);
                mMapView.getMapAsync(this);
            }
        }
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Geocoder geoCoder = new Geocoder(getApplication(), Locale.getDefault());
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
        } else{
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
    @Override
    public void onBackPressed() {
        IgActivityDetailActivity.DoRefreshInResume = false;
        super.onBackPressed();
    }
}

