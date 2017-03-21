package ua.r4mstein.converterlab.presentation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.database.DataSource;
import ua.r4mstein.converterlab.presentation.base.BaseActivity;
import ua.r4mstein.converterlab.presentation.fragments.DetailDialogFragment;
import ua.r4mstein.converterlab.presentation.fragments.DetailFragment;
import ua.r4mstein.converterlab.presentation.fragments.MapsFragment;
import ua.r4mstein.converterlab.presentation.fragments.OrganizationFragment;
import ua.r4mstein.converterlab.presentation.fragments.ProgressDialogFragment;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.services.DataService;
import ua.r4mstein.converterlab.util.OnBackPressedListener;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_FRAGMENT_BUNDLE_KEY;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_START;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private DataSource mDataSource;
    private Toolbar mToolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContainerResId() {
        return R.id.fragment_container;
    }

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDataSource = DataSource.getDataSource(this);

        if(!mDataSource.isOpen()) mDataSource.open();
        mDataSource.inUse = true;
        logger.d(TAG, "onCreate: Open DB");

        Intent intent = new Intent(this, DataService.class);
        intent.setAction(SERVICE_START);
        startService(intent);

        if (_savedInstanceState == null) {
            openOrganizationFragment();
        }

    }

    private void openOrganizationFragment() {
        logger.d(TAG, "openOrganizationFragment");
        OrganizationFragment organizationFragment = new OrganizationFragment();
        addFragment(organizationFragment);
    }

    public final void openDetailFragment(final String _key) {
        logger.d(TAG, "openDetailFragment: with _key: " + _key);
        DetailFragment detailFragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString(DETAIL_FRAGMENT_BUNDLE_KEY, _key);

        detailFragment.setArguments(bundle);
        addFragmentWithBackStack(detailFragment);
    }

    public final void openMapsFragment(final double _latitude, final double _longitude, final String _address) {
        logger.d(TAG, "openMapsFragment: with coordinates: " + _latitude + " -- " + _longitude);
        MapsFragment mapsFragment = MapsFragment.newInstance(_latitude, _longitude, _address);
        addFragmentWithBackStack(mapsFragment);
    }

    public final List<OrganizationModel> getOrganizationDataFromDB() {
        logger.d(TAG, "getOrganizationDataFromDB: get all OrganizationModel");
        return mDataSource.getAllOrganizationItems();
    }

    public final OrganizationModel getOrganizationModelFromDB(final String _key) {
        logger.d(TAG, "getOrganizationModelFromDB: with _key: " + _key);
        return mDataSource.getOrganizationItem(_key);
    }

    public final List<CurrenciesModel> getCurrenciesDataFromDB(final String _organizationId) {
        logger.d(TAG, "getCurrenciesDataFromDB: get all CurrenciesModel with _organizationId: " + _organizationId);
        return mDataSource.getCurrenciesItemsForOrganization(_organizationId);
    }

    public final void setToolbarTitle(final String _title) {
        mToolbar.setTitle(_title);
        logger.d(TAG, "setToolbarTitle: " + _title);
    }

    public final void setToolbarSubTitle(final String _subTitle) {
        mToolbar.setSubtitle(_subTitle);
        logger.d(TAG, "setToolbarSubTitle: " + _subTitle);
    }

    public final void setToolbarIconBack(final boolean isMenuOpen) {

        //todo - use not deprecated method "getDrawable" for newer api
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMenuOpen) onBackPressed();
                else {
                    mToolbar.setNavigationIcon(null);
                    onBackPressed();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        OnBackPressedListener backPressedListener = null;
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment instanceof OnBackPressedListener) {
                backPressedListener = (OnBackPressedListener) fragment;
                break;
            }
        }

        if (backPressedListener != null) {
            backPressedListener.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    public final void showDetailDialog(final ArrayList<String> _strings) {
        FragmentManager manager = getSupportFragmentManager();
        DetailDialogFragment dialogFragment = DetailDialogFragment.newInstance(_strings);
        dialogFragment.show(manager, "dialog");
        logger.d(TAG, "showDetailDialog");
    }

    public final void showProgressDialog(final ProgressDialogFragment _fragment) {
        FragmentManager manager = getSupportFragmentManager();
        _fragment.show(manager, "ProgressDialog");
        logger.d(TAG, "showProgressDialog");
    }

    public final void cancelProgressDialog(final ProgressDialogFragment _fragment) {
        _fragment.dismiss();
        logger.d(TAG, "cancelProgressDialog");
    }

    @Override
    protected void onStart() {
        super.onStart();

        //no need to open DB in on Start if its done in onCreate;, also better to close it in onDestroy;

        if (Build.VERSION.SDK_INT >= 23) checkPermissions();
        logger.d(TAG, "onStart: Build.VERSION: " + Build.VERSION.SDK_INT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataSource.close();
        mDataSource.inUse = false;
        mDataSource = null;
        logger.d(TAG, "onStop: Close DB");
    }

    private void checkPermissions() {
        List<String> permissionList = new ArrayList<>();
        String[] permissions;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (permissionList.size() > 0) {
            permissions = new String[permissionList.size()];
            for (int i = 0; i < permissionList.size(); i++) {
                permissions[i] = permissionList.get(i);
            }

            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //todo - check matching requestCode;

        int count = 0;
        if (grantResults.length > 0) {
            for (int i : grantResults) {
                if (i == PackageManager.PERMISSION_GRANTED) count++;
            }

            if (count == grantResults.length) {
                Toast.makeText(this, "You have all permissions", Toast.LENGTH_SHORT).show();
            } else finish();

            logger.d(TAG, "onRequestPermissionsResult: count = " + count);
            logger.d(TAG, "onRequestPermissionsResult: grantResults.length = " + grantResults.length);
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
