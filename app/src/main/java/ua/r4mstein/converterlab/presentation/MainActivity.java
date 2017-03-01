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

        retrofitManager.init(); // todo remove.

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        retrofitManager.getResponse(new RetrofitManager.RCallback() {
                            @Override
                            public void onSuccess(RootResponse response) {

                                Converter converter = new Converter();

                                converter.convert(response);
                                List<OrganizationModel> organizationModels = converter.getOrganizationModels();
                                List<CurrenciesModel> currenciesModels = converter.getCurrencies();

                                OrganizationModel organizationModel = organizationModels.get(0);

                                mTitleTextView.setText(organizationModel.getTitle());
                                mRegionTextView.setText(organizationModel.getRegion());
                                mCityTextView.setText(organizationModel.getCity());
                                mPhoneTextView.setText(organizationModel.getPhone());
                                mAddressTextView.setText(organizationModel.getAddress());

                                for (int i = 0; i < 7; i++) {
                                    mCurrencyTextView.append(currenciesModels.get(i).getId() + "\n");
                                }
                            }

                            @Override
                            public void onError(String message) {

                            }
                        });
                    }
                }
        );
    }
}
