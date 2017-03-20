package ua.r4mstein.converterlab.presentation.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.MainActivity;
import ua.r4mstein.converterlab.presentation.adapters.home.HomeItemAdapter;
import ua.r4mstein.converterlab.presentation.adapters.home.IHomeItemActionsListener;
import ua.r4mstein.converterlab.presentation.base.BaseFragment;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;
import ua.r4mstein.converterlab.util.map_api.MapApi;

import static ua.r4mstein.converterlab.util.Constants.SERVICE_MESSAGE_ERROR;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_MESSAGE_KEY;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_MESSAGE_SUCCESS;

public final class OrganizationFragment extends BaseFragment<MainActivity> implements SearchView.OnQueryTextListener {

    private static final String TAG = "OrganizationFragment";

    private Logger mLogger;
    private String mRequest;
    private MapApi mMapApi;
    private ProgressDialogFragment mProgressDialogFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_organization;
    }

    // Required empty public constructor
    public OrganizationFragment() {
        super();
    }

    private HomeItemAdapter mAdapter;

    @Override
    public void onCreate(@Nullable final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivityGeneric()).registerReceiver(
                mMessageReceiver, new IntentFilter("DataService"));

        mMapApi.setMapApiCallback(mMapApiCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivityGeneric()).unregisterReceiver(
                mMessageReceiver);

        mMapApi.setMapApiCallback(null);
    }

    @Override
    public void onViewCreated(final View _view, @Nullable final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        mLogger = LogManager.getLogger();
        mMapApi = new MapApi();
        mProgressDialogFragment = new ProgressDialogFragment();

        getActivityGeneric().setToolbarTitle(getResources().getString(R.string.app_name));
        getActivityGeneric().setToolbarSubTitle(null);

        RecyclerView recyclerView = (RecyclerView) _view.findViewById(R.id.organization_recycler_view);
        mAdapter = new HomeItemAdapter();
        mAdapter.setActionsListener(mHomeItemActionsListener);
        recyclerView.setAdapter(mAdapter);

        updateDataAdapter(mAdapter);

        SwipeRefreshLayout refreshLayout =
                (SwipeRefreshLayout) _view.findViewById(R.id.organization_swipe_refresh);

        swipeRefreshListener(refreshLayout);

    }

    private MapApi.MapApiCallback mMapApiCallback = new MapApi.MapApiCallback() {
        @Override
        public void onSuccess(final List<Double> _coordinates) {
            getActivityGeneric().cancelProgressDialog(mProgressDialogFragment);
            getActivityGeneric().openMapsFragment(_coordinates.get(0), _coordinates.get(1), mRequest);
            mLogger.d(TAG, "mMapApiCallback: onSuccess: coordinates " + _coordinates.get(0) +
                    " - " + _coordinates.get(1));
        }

        @Override
        public void onError(final String _message) {
            getActivityGeneric().cancelProgressDialog(mProgressDialogFragment);
            Toast.makeText(getActivityGeneric(), _message, Toast.LENGTH_SHORT).show();
            mLogger.d(TAG, "MapApiCallback: onError: " + _message);
        }
    };

    private final IHomeItemActionsListener mHomeItemActionsListener = new IHomeItemActionsListener() {
        @Override
        public void openOrganizationDetail(final String _key) {
            getActivityGeneric().openDetailFragment(_key);
            mLogger.d(TAG, "openOrganizationDetail with Key: " + _key);
        }

        @Override
        public void openOrganizationLink(final String _link) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(_link));
            getActivityGeneric().startActivity(intent);
            mLogger.d(TAG, "openOrganizationLink: Link: " + _link);
        }

        @Override
        public void openOrganizationLocation(final OrganizationModel _model) {
            getActivityGeneric().showProgressDialog(mProgressDialogFragment);
            mRequest = mMapApi.getRequest(_model);
            mMapApi.getCoordinates(mRequest, getActivityGeneric());
            mLogger.d(TAG, "openOrganizationLocation: Address: " + mRequest);
        }

        @Override
        public void openOrganizationPhone(final String _phone) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + _phone));
            getActivityGeneric().startActivity(intent);
            mLogger.d(TAG, "openOrganizationPhone: Phone: " + _phone);
        }
    };

    private void swipeRefreshListener(final SwipeRefreshLayout _refreshLayout) {
        _refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateDataAdapter(mAdapter);
                _refreshLayout.setRefreshing(false);
            }
        });
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message;
            if (intent != null) {
                message = intent.getStringExtra(SERVICE_MESSAGE_KEY);

                if (message.equals(SERVICE_MESSAGE_SUCCESS)) {
                    updateDataAdapter(mAdapter);
                    mLogger.d(TAG, "mMessageReceiver: BroadcastReceiver got message: " + SERVICE_MESSAGE_SUCCESS);
                } else if (message.equals(SERVICE_MESSAGE_ERROR)) {
                    mLogger.d(TAG, "mMessageReceiver: BroadcastReceiver got message: " + SERVICE_MESSAGE_ERROR);
                }
            }
        }
    };

    private void updateDataAdapter(final HomeItemAdapter _adapter) {
        List<OrganizationModel> models = getActivityGeneric().getOrganizationDataFromDB();
        _adapter.updateData(models);
    }

    @Override
    public void onCreateOptionsMenu(final Menu _menu, final MenuInflater _inflater) {
        super.onCreateOptionsMenu(_menu, _inflater);
        _inflater.inflate(R.menu.menu_main, _menu);

        MenuItem menuItem = _menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(final String _query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String _newText) {
        _newText = _newText.toLowerCase();

        List<OrganizationModel> modelList = getActivityGeneric().getOrganizationDataFromDB();
        ArrayList<OrganizationModel> newModelList = new ArrayList<>();

        for (OrganizationModel model : modelList) {
            String title = model.getTitle().toLowerCase();
            String region = model.getRegion().toLowerCase();
            String city = model.getCity().toLowerCase();

            if (title.contains(_newText)) newModelList.add(model);
            else if (city.contains(_newText)) newModelList.add(model);
            else if (region.contains(_newText)) newModelList.add(model);
        }

        mLogger.d(TAG, "onQueryTextChange: text for search: " + _newText);
        mAdapter.setFilter(newModelList);
        return true;
    }
}
