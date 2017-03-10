package ua.r4mstein.converterlab.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.database.DataSource;
import ua.r4mstein.converterlab.presentation.base.BaseActivity;
import ua.r4mstein.converterlab.presentation.fragments.DetailFragment;
import ua.r4mstein.converterlab.presentation.fragments.OrganizationFragment;
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

        mDataSource = new DataSource(this);

        Intent intent = new Intent(this, DataService.class);
        intent.setAction(SERVICE_START);
        startService(intent);

        if (savedInstanceState == null) {
            openOrganizationFragment();
        }
    }

    private void openOrganizationFragment() {
        OrganizationFragment organizationFragment = new OrganizationFragment();
        addFragment(organizationFragment);
    }

    public void openDetailFragment(String key) {
        DetailFragment detailFragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString(DETAIL_FRAGMENT_BUNDLE_KEY, key);

        detailFragment.setArguments(bundle);
        addFragmentWithBackStack(detailFragment);
    }

    public List<OrganizationModel> getOrganizationDataFromDB() {
        logger.d(TAG, "getOrganizationDataFromDB");
        return mDataSource.getAllOrganizationItems();
    }

    public OrganizationModel getOrganizationModelFromDB(String key) {
        logger.d(TAG, "getOrganizationModelFromDB");
        return mDataSource.getOrganizationItem(key);
    }

    public List<CurrenciesModel> getCurrenciesDataFromDB(String organizationId) {
        logger.d(TAG, "getCurrenciesDataFromDB");
        return mDataSource.getCurrenciesItemsForOrganization(organizationId);
    }

    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }

    public void setToolbarSubTitle(String subTitle) {
        mToolbar.setSubtitle(subTitle);
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
