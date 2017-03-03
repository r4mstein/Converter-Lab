package ua.r4mstein.converterlab.presentation.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.MainActivity;
import ua.r4mstein.converterlab.presentation.adapters.HomeItemAdapter;
import ua.r4mstein.converterlab.presentation.base.BaseFragment;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

public class OrganizationFragment extends BaseFragment<MainActivity> {

    private static final String BUNDLE_KEY = "bundle_key";

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_organization;
    }

    // Required empty public constructor
    public OrganizationFragment() {
        super();
    }

    public static OrganizationFragment newInstance(ArrayList<OrganizationModel> list) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_KEY, list);

        OrganizationFragment fragment = new OrganizationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<OrganizationModel> models = getArguments().getParcelableArrayList(BUNDLE_KEY);

        HomeItemAdapter adapter = new HomeItemAdapter(getActivityGeneric(), models);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.organization_recycler_view);

        recyclerView.setAdapter(adapter);
    }


}
