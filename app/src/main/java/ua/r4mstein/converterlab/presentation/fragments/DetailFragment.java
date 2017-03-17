package ua.r4mstein.converterlab.presentation.fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.MainActivity;
import ua.r4mstein.converterlab.presentation.adapters.detail.DetailItemAdapter;
import ua.r4mstein.converterlab.presentation.adapters.detail.base.DataHolderBase;
import ua.r4mstein.converterlab.presentation.adapters.detail.data_holders.CurrencyDataHolder;
import ua.r4mstein.converterlab.presentation.adapters.detail.data_holders.CurrencyHeaderDataHolder;
import ua.r4mstein.converterlab.presentation.adapters.detail.data_holders.OrganizationDataHolder;
import ua.r4mstein.converterlab.presentation.base.BaseFragment;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;
import ua.r4mstein.converterlab.util.map_api.MapApi;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_FRAGMENT_BUNDLE_KEY;

public class DetailFragment extends BaseFragment<MainActivity> {

    private static final String TAG = "DetailFragment";

    private OrganizationModel mOrganizationModel;
    private ArrayList<String> mStrings = new ArrayList<>();
    private Logger mLogger;
    private MapApi mMapApi;
    private String mRequest;
    private ProgressDialogFragment mProgressDialogFragment;

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
        mLogger = LogManager.getLogger();
        mMapApi = new MapApi();
        mProgressDialogFragment = new ProgressDialogFragment();

        getActivityGeneric().setToolbarIconBack();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.detail_recycler_view);
        mAdapter = new DetailItemAdapter(getActivityGeneric());
        recyclerView.setAdapter(mAdapter);

        String key = getArguments().getString(DETAIL_FRAGMENT_BUNDLE_KEY);
        mOrganizationModel = getActivityGeneric().getOrganizationModelFromDB(key);
        updateDataAdapter(mOrganizationModel);
        mLogger.d(TAG, "onViewCreated");

        SwipeRefreshLayout refreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.detail_swipe_refresh);
        swipeRefreshListener(refreshLayout);

        initFloatingActionMenu(view, mOrganizationModel);

        initDataForDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapApi.setMapApiCallback(mMapApiCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapApi.setMapApiCallback(null);
    }

    private MapApi.MapApiCallback mMapApiCallback = new MapApi.MapApiCallback() {
        @Override
        public void onSuccess(List<Double> coordinates) {
            getActivityGeneric().cancelProgressDialog(mProgressDialogFragment);
            getActivityGeneric().openMapsFragment(coordinates.get(0), coordinates.get(1), mRequest);
        }

        @Override
        public void onError(String message) {
            getActivityGeneric().cancelProgressDialog(mProgressDialogFragment);
            Toast.makeText(getActivityGeneric(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private void updateDataAdapter(OrganizationModel organizationModel) {
        List<DataHolderBase> objectList = new ArrayList<>();

        getActivityGeneric().setToolbarTitle(organizationModel.getTitle());
        getActivityGeneric().setToolbarSubTitle(organizationModel.getCity());

        String currencyHeader = "currencyHeader";
        List<CurrenciesModel> currenciesModels = getActivityGeneric().getCurrenciesDataFromDB(organizationModel.getId());

        objectList.add(new OrganizationDataHolder(organizationModel));
        objectList.add(new CurrencyHeaderDataHolder(currencyHeader));

        for (CurrenciesModel model : currenciesModels) {
            objectList.add(new CurrencyDataHolder(model));
        }

        mAdapter.updateData(objectList);
    }

    private void initFloatingActionMenu(View view, final OrganizationModel organizationModel) {
        final FloatingActionMenu actionMenu = (FloatingActionMenu) view.findViewById(R.id.floating_action_menu);
        FloatingActionButton mapFAB = (FloatingActionButton) view.findViewById(R.id.floating_action_menu_map);
        FloatingActionButton linkFAB = (FloatingActionButton) view.findViewById(R.id.floating_action_menu_link);
        FloatingActionButton phoneFAB = (FloatingActionButton) view.findViewById(R.id.floating_action_menu_phone);

        createCustomAnimation(actionMenu);

        mapFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivityGeneric().showProgressDialog(mProgressDialogFragment);
                mRequest = mMapApi.getRequest(organizationModel);
                mMapApi.getCoordinates(mRequest, getActivityGeneric());
            }
        });

        linkFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(organizationModel.getLink()));
                getActivityGeneric().startActivity(intent);
            }
        });

        phoneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + organizationModel.getPhone()));
                getActivityGeneric().startActivity(intent);
            }
        });
    }

    private void swipeRefreshListener(final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateDataAdapter(mOrganizationModel);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void createCustomAnimation(final FloatingActionMenu actionMenu) {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(actionMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(actionMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(actionMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(actionMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                actionMenu.getMenuIconView().setImageResource(actionMenu.isOpened()
                        ? R.drawable.ic_close : R.drawable.ic_fab_menu);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        actionMenu.setIconToggleAnimatorSet(set);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_share) {
            getActivityGeneric().showDialog(mStrings);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initDataForDialog() {
        List<CurrenciesModel> currenciesModels = getActivityGeneric().getCurrenciesDataFromDB(mOrganizationModel.getId());

        mStrings.add(mOrganizationModel.getTitle());
        mStrings.add(mOrganizationModel.getRegion());
        mStrings.add(mOrganizationModel.getCity());

        for (CurrenciesModel model : currenciesModels) {
            DecimalFormat format = new DecimalFormat("00.00");
            String ask = format.format(Double.parseDouble(model.getAsk())).trim();
            String bid = format.format(Double.parseDouble(model.getBid())).trim();

            mStrings.add(model.getName_key().trim() + "\t\t\t\t\t\t\t" + ask + "/" + bid);
        }
    }
}
