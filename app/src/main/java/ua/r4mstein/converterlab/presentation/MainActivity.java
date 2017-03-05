package ua.r4mstein.converterlab.presentation;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.api.RetrofitManager;
import ua.r4mstein.converterlab.api.models.RootResponse;
import ua.r4mstein.converterlab.database.DataSource;
import ua.r4mstein.converterlab.presentation.base.BaseActivity;
import ua.r4mstein.converterlab.presentation.fragments.OrganizationFragment;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.converter.Converter;

public class MainActivity extends BaseActivity  {
//    implements SearchView.OnQueryTextListener

    private static final String TAG = "MainActivity";

    private DataSource mDataSource;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDataSource = new DataSource(this);

        retrofitManager.init();

        if (savedInstanceState == null) {
            OrganizationFragment organizationFragment = new OrganizationFragment();
            addFragment(organizationFragment);
        }
    }

    public void parseData(final DataCallback callback) {
        retrofitManager.getResponse(new RetrofitManager.RCallback() {
            @Override
            public void onSuccess(RootResponse response) {

                Converter converter = new Converter();

                converter.convert(response);
                List<OrganizationModel> organizationModels = converter.getOrganizationModels();
                List<CurrenciesModel> currenciesModels = converter.getCurrencies();

                mDataSource.insertOrUpdateOrganizations(organizationModels);
                mDataSource.insertOrUpdateCurrencies(currenciesModels);

                callback.onSuccess("Success");
                logger.d(TAG, "parseData: onSuccess");
            }

            @Override
            public void onError(String message) {
                callback.onError("Error");
                logger.d(TAG, "parseData: onError");
            }
        });
    }

    public interface DataCallback {

        void onSuccess(String message);

        void onError(String message);
    }

    public List<OrganizationModel> getOrganizationDataFromDB() {
        logger.d(TAG, "getOrganizationDataFromDB");
        return mDataSource.getAllOrganizationItems();
    }

    public List<CurrenciesModel> getCurrenciesDataFromDB(String[] currencyId) {
        logger.d(TAG, "getCurrenciesDataFromDB");
        return mDataSource.getCurrenciesItemsForOrganization(currencyId);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//
//        MenuItem menuItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        searchView.setOnQueryTextListener(this);
//
//        return true;
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        return false;
//    }

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
