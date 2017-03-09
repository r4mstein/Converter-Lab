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

import java.util.ArrayList;
import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.MainActivity;
import ua.r4mstein.converterlab.presentation.adapters.home.HomeItemAdapter;
import ua.r4mstein.converterlab.presentation.adapters.home.IHomeItemActionsListener;
import ua.r4mstein.converterlab.presentation.base.BaseFragment;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

import static ua.r4mstein.converterlab.util.Constants.SERVICE_MESSAGE_ERROR;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_MESSAGE_KEY;
import static ua.r4mstein.converterlab.util.Constants.SERVICE_MESSAGE_SUCCESS;

public class OrganizationFragment extends BaseFragment<MainActivity> implements SearchView.OnQueryTextListener {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivityGeneric()).registerReceiver(
                mMessageReceiver, new IntentFilter("DataService"));

    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivityGeneric()).unregisterReceiver(
                mMessageReceiver);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivityGeneric().getSupportActionBar().setTitle(R.string.app_name);
        getActivityGeneric().getSupportActionBar().setSubtitle(null);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.organization_recycler_view);
        mAdapter = new HomeItemAdapter();
        mAdapter.setActionsListener(mHomeItemActionsListener);
        recyclerView.setAdapter(mAdapter);

        updateDataAdapter(mAdapter);

        SwipeRefreshLayout refreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.organization_swipe_refresh);

        swipeRefreshListener(refreshLayout);

    }

    private final IHomeItemActionsListener mHomeItemActionsListener = new IHomeItemActionsListener() {
        @Override
        public void openOrganizationDetail(String key) {
            getActivityGeneric().openDetailFragment(key);
        }

        @Override
        public void openOrganizationLink(String link) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            getActivityGeneric().startActivity(intent);
        }

        @Override
        public void openOrganizationLocation() {

        }

        @Override
        public void openOrganizationPhone(String phone) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            getActivityGeneric().startActivity(intent);
        }
    };

    private void swipeRefreshListener(final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateDataAdapter(mAdapter);
                refreshLayout.setRefreshing(false);
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
                } else if (message.equals(SERVICE_MESSAGE_ERROR)) {

                }
            }
        }
    };

    private void updateDataAdapter(HomeItemAdapter adapter) {
        List<OrganizationModel> models = getActivityGeneric().getOrganizationDataFromDB();
        adapter.updateData(models);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();

        List<OrganizationModel> modelList = getActivityGeneric().getOrganizationDataFromDB();
        ArrayList<OrganizationModel> newModelList = new ArrayList<>();

        for (OrganizationModel model : modelList) {
            String title = model.getTitle().toLowerCase();
            String region = model.getRegion().toLowerCase();
            String city = model.getCity().toLowerCase();

            if (title.contains(newText)) newModelList.add(model);
            else if (city.contains(newText)) newModelList.add(model);
            else if (region.contains(newText)) newModelList.add(model);
        }

        mAdapter.setFilter(newModelList);
        return true;
    }
}
