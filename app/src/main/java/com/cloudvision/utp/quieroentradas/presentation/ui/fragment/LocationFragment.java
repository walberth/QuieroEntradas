package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudvision.utp.quieroentradas.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

/**
 * Created by Walberth Gutierrez Telles on 01,July,2018
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback {
    private Double latitud;
    private Double longitud;
    private View mView;
    private String eventName;
    private String eventGroup;

    public LocationFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_location, container, false);

        if (getArguments() != null) {
            Bundle mBundle = getArguments();
            latitud = Double.parseDouble(mBundle.getString("latitud"));
            longitud = Double.parseDouble(mBundle.getString("longitud"));
            eventName = mBundle.getString("eventName");
            eventGroup = mBundle.getString("eventGroup");
        }

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapView mMapView = mView.findViewById(R.id.mapView);

        if(mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(Objects.requireNonNull(getContext()));

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(eventName).snippet(eventGroup));

        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(latitud, longitud)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
