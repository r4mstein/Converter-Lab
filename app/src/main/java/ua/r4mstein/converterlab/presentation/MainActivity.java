package ua.r4mstein.converterlab.presentation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.api.RetrofitManager;
import ua.r4mstein.converterlab.models.RootResponse;
import ua.r4mstein.converterlab.models.cities.City;
import ua.r4mstein.converterlab.models.organizations.Organization;
import ua.r4mstein.converterlab.models.regions.Region;
import ua.r4mstein.converterlab.presentation.base.BaseActivity;

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
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       retrofitManager.getResponse(new RetrofitManager.RCallback() {
                                           @Override
                                           public void onSuccess(RootResponse response) {
                                               List<Organization> organizations = response.getOrganizations();
                                               Organization organization = organizations.get(0);

                                               List<Region> regions = response.getRegions();
                                               String regionUI = null;
                                               for (Region region : regions) {
                                                   if (region.id.equals(organization.regionId)) {
                                                       regionUI = region.name;
                                                   }
                                               }

                                               List<City> cities = response.getCities();
                                               String cityUI = null;
                                               for (City city : cities) {
                                                   if (city.id.equals(organization.cityId)) {
                                                       cityUI = city.name;
                                                   }
                                               }

                                               mTitleTextView.setText(organization.title);
                                               mRegionTextView.setText(regionUI);
                                               mCityTextView.setText(cityUI);
                                               mPhoneTextView.setText(organization.phone);
                                               mAddressTextView.setText(organization.address);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
