package ua.r4mstein.converterlab.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.detail_recycler_view);
        mAdapter = new DetailItemAdapter();
        recyclerView.setAdapter(mAdapter);

        updateDataAdapter(getArguments().getString(DETAIL_FRAGMENT_BUNDLE_KEY));
    }

    private void updateDataAdapter(String key) {
        List<Object> objectList = new ArrayList<>();

        OrganizationModel organizationModel = getActivityGeneric().getOrganizationModelFromDB(key);
        String currencyHeader = "currencyHeader";
        List<CurrenciesModel> currenciesModels = getActivityGeneric().getCurrenciesDataFromDB(organizationModel.getId());

        objectList.add(organizationModel);
        objectList.add(currencyHeader);

        for (CurrenciesModel model : currenciesModels) {
            objectList.add(model);
        }

        mAdapter.updateData(objectList);
    }
}
