package ua.r4mstein.converterlab.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import ua.r4mstein.converterlab.presentation.adapters.HomeItemAdapter;
import ua.r4mstein.converterlab.presentation.base.BaseFragment;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

public class OrganizationFragment extends BaseFragment<MainActivity> implements SearchView.OnQueryTextListener {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

        adapter.setFilter(newModelList);
        return true;
    }
}
