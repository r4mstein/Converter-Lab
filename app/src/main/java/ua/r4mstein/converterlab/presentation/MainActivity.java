package ua.r4mstein.converterlab.presentation;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

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

public class MainActivity extends BaseActivity {

    public static final String BASE_URL = "http://resources.finance.ua/";

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
        mDataSource.open();

        retrofitManager.init(); // todo remove.

        OrganizationFragment organizationFragment = new OrganizationFragment();
        addFragment(organizationFragment);

//        String[] strings = new String[]{"7oiylpmiow8iy1smazeEUR", "7oiylpmiow8iy1smazeRUB", "7oiylpmiow8iy1smazeUSD"};
//        List<CurrenciesModel> models = getCurrenciesDataFromDB(strings);
//        for (CurrenciesModel model : models) {
//            logger.d(TAG, model.getName() + "--" + model.getAsk());
//        }

//        parseData();

    }

    public void parseData(final SuccessDataCallback callback) {
        retrofitManager.getResponse(new RetrofitManager.RCallback() {
            @Override
            public void onSuccess(RootResponse response) {

                Converter converter = new Converter();

                converter.convert(response);
                List<OrganizationModel> organizationModels = converter.getOrganizationModels();
                List<CurrenciesModel> currenciesModels = converter.getCurrencies();

//                OrganizationFragment organizationFragment =
//                        OrganizationFragment.newInstance((ArrayList<OrganizationModel>) organizationModels);
//                addFragment(organizationFragment);
//                addFragmentWithBackStack(organizationFragment);

                mDataSource.insertOrUpdateOrganizations(organizationModels);
                mDataSource.insertOrUpdateCurrencies(currenciesModels);

                callback.onSuccess("Success");
            }

            @Override
            public void onError(String message) {
                callback.onError("Error");
            }
        });
    }

    public interface SuccessDataCallback {

        void onSuccess(String message);

        void onError(String message);
    }

    public List<OrganizationModel> getOrganizationDataFromDB() {
        return mDataSource.getAllOrganizationItems();
    }

    public List<CurrenciesModel> getCurrenciesDataFromDB(String[] currencyId) {
        return mDataSource.getCurrenciesItemsForOrganization(currencyId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }
}
