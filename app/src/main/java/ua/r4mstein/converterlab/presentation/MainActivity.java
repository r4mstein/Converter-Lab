package ua.r4mstein.converterlab.presentation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.api.RetrofitManager;
import ua.r4mstein.converterlab.api.models.RootResponse;
import ua.r4mstein.converterlab.database.DataSource;
import ua.r4mstein.converterlab.presentation.base.BaseActivity;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.converter.Converter;

public class MainActivity extends BaseActivity {

    public static final String BASE_URL = "http://resources.finance.ua/";

    private static final String TAG = "MainActivity";

    private TextView mTitleTextView;
    private TextView mRegionTextView;
    private TextView mCityTextView;
    private TextView mPhoneTextView;
    private TextView mAddressTextView;
    private TextView mCurrencyTextView;

    private DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTitleTextView = (TextView) findViewById(R.id.title);
        mRegionTextView = (TextView) findViewById(R.id.region);
        mCityTextView = (TextView) findViewById(R.id.city);
        mPhoneTextView = (TextView) findViewById(R.id.phone);
        mAddressTextView = (TextView) findViewById(R.id.address);
        mCurrencyTextView = (TextView) findViewById(R.id.currency);

        mDataSource = new DataSource(this);
        mDataSource.open();

        retrofitManager.init(); // todo remove.

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        //update
//                        OrganizationModel model = new OrganizationModel();
//                        model.setId("7oiylpmiow8iy1smdqi");
//                        model.setTitle("My bank");
//                        model.setRegion("my region");
//                        model.setCity("my City");
//                        model.setPhone("my Phone");
//                        model.setAddress("my Address");
//                        model.setLink("my Link");
//                        mDataSource.updateOrganizationItem(model);

                        //query
//                        List<CurrenciesModel> list = mDataSource.getAllCurrenciesItems();
//                        CurrenciesModel item = list.get(2);
//
//                        logger.d(TAG, "List size: " + String.valueOf(list.size()));
//                        logger.d(TAG, item.getId() + ", " + item.getName() + ", " + item.getAsk()
//                                + ", " + item.getBid());

//                        mTitleTextView.setText(organizationModel.getTitle());
//                        mRegionTextView.setText(organizationModel.getRegion());
//                        mCityTextView.setText(organizationModel.getCity());
//                        mPhoneTextView.setText(organizationModel.getPhone());
//                        mAddressTextView.setText(organizationModel.getAddress());

                        retrofitManager.getResponse(new RetrofitManager.RCallback() {
                            @Override
                            public void onSuccess(RootResponse response) {

                                Converter converter = new Converter();

                                converter.convert(response);
                                List<OrganizationModel> organizationModels = converter.getOrganizationModels();
                                List<CurrenciesModel> currenciesModels = converter.getCurrencies();

//                                for (OrganizationModel model : organizationModels) {
//                                    mDataSource.insertOrUpdateOrganizationItem(model);
//                                }
//
//                                for (CurrenciesModel model : currenciesModels) {
//                                    mDataSource.insertOrUpdateCurrenciesItem(model);
//                                }

                                mDataSource.insertOrUpdateOrganizations(organizationModels);
//                                mDataSource.insertOrUpdateCurrencies(currenciesModels);

                                OrganizationModel organizationModel = organizationModels.get(0);

                                mTitleTextView.setText(organizationModel.getTitle());
                                mRegionTextView.setText(organizationModel.getRegion());
                                mCityTextView.setText(organizationModel.getCity());
                                mPhoneTextView.setText(organizationModel.getPhone());
                                mAddressTextView.setText(organizationModel.getAddress());
                            }

                            @Override
                            public void onError(String message) {

                            }
                        });
                    }
                }
        );
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
