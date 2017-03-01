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
import ua.r4mstein.converterlab.api.models.cities.City;
import ua.r4mstein.converterlab.api.models.organizations.Organization;
import ua.r4mstein.converterlab.api.models.regions.Region;
import ua.r4mstein.converterlab.presentation.base.BaseActivity;
import ua.r4mstein.converterlab.presentation.ui_models.HomeModel;
import ua.r4mstein.converterlab.util.converter.Converter;

public class MainActivity extends BaseActivity {

    public static final String BASE_URL = "http://resources.finance.ua/";

    private static final String TAG = "MainActivity";

    private TextView mTitleTextView;
    private TextView mRegionTextView;
    private TextView mCityTextView;
    private TextView mPhoneTextView;
    private TextView mAddressTextView;

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
                                List<HomeModel> homeModels = converter.getHomeModels();

                                HomeModel homeModel = homeModels.get(0);

                                mTitleTextView.setText(homeModel.getTitle());
                                mRegionTextView.setText(homeModel.getRegion());
                                mCityTextView.setText(homeModel.getCity());
                                mPhoneTextView.setText(homeModel.getPhone());
                                mAddressTextView.setText(homeModel.getAddress());
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
