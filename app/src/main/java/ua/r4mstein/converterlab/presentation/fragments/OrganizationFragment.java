package ua.r4mstein.converterlab.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.MainActivity;
import ua.r4mstein.converterlab.presentation.adapters.HomeItemAdapter;
import ua.r4mstein.converterlab.presentation.base.BaseFragment;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

public class OrganizationFragment extends BaseFragment<MainActivity> {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_organization;
    }

    // Required empty public constructor
    public OrganizationFragment() {
        super();
    }

    private HomeItemAdapter adapter;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.organization_recycler_view);
        adapter = new HomeItemAdapter();
        recyclerView.setAdapter(adapter);

        getData();

        SwipeRefreshLayout refreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.organization_swipe_refresh);

        swipeRefreshListener(refreshLayout);
    }

    private void swipeRefreshListener(final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void getData() {
        getActivityGeneric().parseData(new MainActivity.DataCallback() {
            @Override
            public void onSuccess(String message) {
                updateDataAdapter(adapter);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void updateDataAdapter(HomeItemAdapter adapter) {
        List<OrganizationModel> models = getActivityGeneric().getOrganizationDataFromDB();
        adapter.updateData(models);
    }
}
