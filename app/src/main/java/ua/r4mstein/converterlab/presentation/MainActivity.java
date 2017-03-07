package ua.r4mstein.converterlab.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.api.RetrofitManager;
import ua.r4mstein.converterlab.api.models.RootResponse;
import ua.r4mstein.converterlab.database.DataSource;
import ua.r4mstein.converterlab.presentation.base.BaseActivity;
import ua.r4mstein.converterlab.presentation.fragments.OrganizationFragment;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.services.DataService;
import ua.r4mstein.converterlab.util.converter.Converter;
import ua.r4mstein.converterlab.util.converter.IConverter;

import static ua.r4mstein.converterlab.util.Constants.SERVICE_START;

public class MainActivity extends BaseActivity  {

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

        Intent intent = new Intent(this, DataService.class);
        intent.setAction(SERVICE_START);
        startService(intent);

        if (savedInstanceState == null) {
            OrganizationFragment organizationFragment = new OrganizationFragment();
            addFragment(organizationFragment);
        }
    }

    public void parseData(final DataCallback callback) {
        retrofitManager.getResponse(new RetrofitManager.RCallback() {
            @Override
            public void onSuccess(RootResponse response) {

                IConverter converter = new Converter();

                converter.convert(response);
                List<OrganizationModel> organizationModels = converter.getOrganizationModels();
                List<CurrenciesModel> currenciesModels = converter.getCurrencies();

                mDataSource.insertOrUpdateOrganizations(organizationModels);
                mDataSource.insertOrUpdateCurrencies(currenciesModels);

                callback.onSuccess("Success");
                logger.d(TAG, "loadDataFromServer: onSuccess");
            }

            @Override
            public void onError(String message) {
                callback.onError("Error");
                logger.d(TAG, "loadDataFromServer: onError");
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
