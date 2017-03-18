package ua.r4mstein.converterlab.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        logger.d(TAG, "onCreate");

        mDataSource = new DataSource(this);

        Intent intent = new Intent(this, DataService.class);
        intent.setAction(SERVICE_START);
        startService(intent);

        if (savedInstanceState == null) {
            openOrganizationFragment();
        }

    }

    private void openOrganizationFragment() {
        logger.d(TAG, "openOrganizationFragment");
        OrganizationFragment organizationFragment = new OrganizationFragment();
        addFragment(organizationFragment);
    }

    public void openDetailFragment(String key) {
        logger.d(TAG, "openDetailFragment: with key: " + key);
        DetailFragment detailFragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString(DETAIL_FRAGMENT_BUNDLE_KEY, key);

        detailFragment.setArguments(bundle);
        addFragmentWithBackStack(detailFragment);
    }

    public void openMapsFragment(double latitude, double longitude, String address) {
        logger.d(TAG, "openMapsFragment: with coordinates: " + latitude + " -- " + longitude);
        MapsFragment mapsFragment = MapsFragment.newInstance(latitude, longitude, address);
        addFragmentWithBackStack(mapsFragment);
    }

    public List<OrganizationModel> getOrganizationDataFromDB() {
        logger.d(TAG, "getOrganizationDataFromDB: get all OrganizationModel");
        return mDataSource.getAllOrganizationItems();
    }

    public OrganizationModel getOrganizationModelFromDB(String key) {
        logger.d(TAG, "getOrganizationModelFromDB: with key: " + key);
        return mDataSource.getOrganizationItem(key);
    }

    public List<CurrenciesModel> getCurrenciesDataFromDB(String organizationId) {
        logger.d(TAG, "getCurrenciesDataFromDB: get all CurrenciesModel with organizationId: " + organizationId);
        return mDataSource.getCurrenciesItemsForOrganization(organizationId);
    }

    public void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
        logger.d(TAG, "setToolbarTitle: " + title);
    }

    public void setToolbarSubTitle(String subTitle) {
        mToolbar.setSubtitle(subTitle);
        logger.d(TAG, "setToolbarSubTitle: " + subTitle);
    }

    public void setToolbarIconBack() {
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbar.setNavigationIcon(null);
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mToolbar.getNavigationIcon() != null) {
            mToolbar.setNavigationIcon(null);
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    public void showDetailDialog(ArrayList<String> strings) {
        FragmentManager manager = getSupportFragmentManager();
        DetailDialogFragment dialogFragment = DetailDialogFragment.newInstance(strings);
        dialogFragment.show(manager, "dialog");
        logger.d(TAG, "showDetailDialog");
    }

    public void showProgressDialog(ProgressDialogFragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        fragment.show(manager, "ProgressDialog");
        logger.d(TAG, "showProgressDialog");
    }

    public void cancelProgressDialog(ProgressDialogFragment fragment) {
        fragment.dismiss();
        logger.d(TAG, "cancelProgressDialog");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDataSource.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDataSource.open();
    }
}
