package ua.r4mstein.converterlab.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.MainActivity;
import ua.r4mstein.converterlab.presentation.base.BaseFragment;

import static ua.r4mstein.converterlab.util.Constants.MAPS_FRAGMENT_ADDRESS;
import static ua.r4mstein.converterlab.util.Constants.MAPS_FRAGMENT_LATITUDE;
import static ua.r4mstein.converterlab.util.Constants.MAPS_FRAGMENT_LONGITUDE;

public class MapsFragment extends BaseFragment<MainActivity> implements OnMapReadyCallback {

    private double mLatitude;
    private double mLongitude;
    private String mAddress;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_maps;
    }

    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance(double latitude, double longitude, String address) {
        MapsFragment mapsFragment = new MapsFragment();

        Bundle bundle = new Bundle();
        bundle.putDouble(MAPS_FRAGMENT_LATITUDE, latitude);
        bundle.putDouble(MAPS_FRAGMENT_LONGITUDE, longitude);
        bundle.putString(MAPS_FRAGMENT_ADDRESS, address);

        mapsFragment.setArguments(bundle);
        return mapsFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLatitude = getArguments().getDouble(MAPS_FRAGMENT_LATITUDE);
        mLongitude = getArguments().getDouble(MAPS_FRAGMENT_LONGITUDE);
        mAddress = getArguments().getString(MAPS_FRAGMENT_ADDRESS);

        MapView mapView = (MapView) view.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getActivityGeneric());

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(mLatitude, mLongitude))
                .title(mAddress));

        CameraPosition position = CameraPosition.builder().target(new LatLng(mLatitude, mLongitude))
                .zoom(18).bearing(0).tilt(20).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }
}
