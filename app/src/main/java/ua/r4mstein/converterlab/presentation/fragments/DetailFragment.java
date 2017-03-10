package ua.r4mstein.converterlab.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.MainActivity;
import ua.r4mstein.converterlab.presentation.adapters.detail.DetailItemAdapter;
import ua.r4mstein.converterlab.presentation.base.BaseFragment;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_FRAGMENT_BUNDLE_KEY;

public class DetailFragment extends BaseFragment<MainActivity> {

    private String mKey;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_detail;
    }

    // Required empty public constructor
    public DetailFragment() {
        super();
    }

    private DetailItemAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.detail_recycler_view);
        mAdapter = new DetailItemAdapter(getActivityGeneric());
        recyclerView.setAdapter(mAdapter);

        mKey = getArguments().getString(DETAIL_FRAGMENT_BUNDLE_KEY);
        updateDataAdapter(mKey);

        SwipeRefreshLayout refreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.detail_swipe_refresh);
        swipeRefreshListener(refreshLayout);
    }

    private void updateDataAdapter(String key) {
        List<Object> objectList = new ArrayList<>();

        OrganizationModel organizationModel = getActivityGeneric().getOrganizationModelFromDB(key);

        getActivityGeneric().getSupportActionBar().setTitle(organizationModel.getTitle());
        getActivityGeneric().getSupportActionBar().setSubtitle(organizationModel.getCity());

        String currencyHeader = "currencyHeader";
        List<CurrenciesModel> currenciesModels = getActivityGeneric().getCurrenciesDataFromDB(organizationModel.getId());

        objectList.add(organizationModel);
        objectList.add(currencyHeader);

        for (CurrenciesModel model : currenciesModels) {
            objectList.add(model);
        }

        mAdapter.updateData(objectList);
    }

    private void swipeRefreshListener(final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateDataAdapter(mKey);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_share) {
            Toast.makeText(getActivityGeneric(), "Share menu", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
