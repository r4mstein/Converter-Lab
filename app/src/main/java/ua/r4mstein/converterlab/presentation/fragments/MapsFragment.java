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
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

import static ua.r4mstein.converterlab.util.Constants.MAPS_FRAGMENT_ADDRESS;
import static ua.r4mstein.converterlab.util.Constants.MAPS_FRAGMENT_LATITUDE;
import static ua.r4mstein.converterlab.util.Constants.MAPS_FRAGMENT_LONGITUDE;

public final class MapsFragment extends BaseFragment<MainActivity> implements OnMapReadyCallback {

    private static final String TAG = "MapsFragment";

    private double mLatitude;
    private double mLongitude;
    private String mAddress;
    private Logger mLogger;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_maps;
    }

    public MapsFragment() {
        super();
        mLogger = LogManager.getLogger();
        // Required empty public constructor
    }

    public static MapsFragment newInstance(final double _latitude, final double _longitude, final String _address) {
        MapsFragment mapsFragment = new MapsFragment();

        Bundle bundle = new Bundle();
        bundle.putDouble(MAPS_FRAGMENT_LATITUDE, _latitude);
        bundle.putDouble(MAPS_FRAGMENT_LONGITUDE, _longitude);
        bundle.putString(MAPS_FRAGMENT_ADDRESS, _address);

        mapsFragment.setArguments(bundle);
        return mapsFragment;
    }

    @Override
    public void onViewCreated(final View _view, @Nullable final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);

        mLatitude = getArguments().getDouble(MAPS_FRAGMENT_LATITUDE);
        mLongitude = getArguments().getDouble(MAPS_FRAGMENT_LONGITUDE);
        mAddress = getArguments().getString(MAPS_FRAGMENT_ADDRESS);

        MapView mapView = (MapView) _view.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mLogger.d(TAG, "onMapReady: marker on coordinates: " + mLatitude + " -- " + mLongitude);

        MapsInitializer.initialize(getActivityGeneric());

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(mLatitude, mLongitude))
                .title(mAddress));

        CameraPosition position = CameraPosition.builder().target(new LatLng(mLatitude, mLongitude))
                .zoom(18).bearing(0).tilt(20).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }
}
