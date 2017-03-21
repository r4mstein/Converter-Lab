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
import ua.r4mstein.converterlab.util.OnBackPressedListener;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;
import ua.r4mstein.converterlab.util.map_api.MapApi;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_FRAGMENT_BUNDLE_KEY;

public final class DetailFragment extends BaseFragment<MainActivity> implements OnBackPressedListener {

    private static final String TAG = "DetailFragment";

    private OrganizationModel mOrganizationModel;
    private ArrayList<String> mStrings = new ArrayList<>();
    private Logger mLogger;
    private MapApi mMapApi;
    private String mRequest;
    private ProgressDialogFragment mProgressDialogFragment;
    private FloatingActionMenu mFloatingActionMenu;

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
    public void onCreate(@Nullable Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(final View _view, @Nullable Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        mLogger = LogManager.getLogger();
        mMapApi = new MapApi();
        mProgressDialogFragment = new ProgressDialogFragment();


        RecyclerView recyclerView = (RecyclerView) _view.findViewById(R.id.detail_recycler_view);
        mAdapter = new DetailItemAdapter(getActivityGeneric());
        recyclerView.setAdapter(mAdapter);

        String key = getArguments().getString(DETAIL_FRAGMENT_BUNDLE_KEY);
        mOrganizationModel = getActivityGeneric().getOrganizationModelFromDB(key);

        getActivityGeneric().setToolbarIconBack(false);
        getActivityGeneric().setToolbarTitle(mOrganizationModel.getTitle());
        getActivityGeneric().setToolbarSubTitle(mOrganizationModel.getCity());

        updateDataAdapter(mOrganizationModel);

        mLogger.d(TAG, "onViewCreated");

        SwipeRefreshLayout refreshLayout =
                (SwipeRefreshLayout) _view.findViewById(R.id.detail_swipe_refresh);
        swipeRefreshListener(refreshLayout);

        initFloatingActionMenu(_view, mOrganizationModel);

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

    private void updateDataAdapter(final OrganizationModel _organizationModel) {
        List<DataHolderBase> objectList = new ArrayList<>();

        String currencyHeader = "currencyHeader";
        List<CurrenciesModel> currenciesModels = getActivityGeneric().getCurrenciesDataFromDB(_organizationModel.getId());

        objectList.add(new OrganizationDataHolder(_organizationModel));
        objectList.add(new CurrencyHeaderDataHolder(currencyHeader));

        for (CurrenciesModel model : currenciesModels) {
            objectList.add(new CurrencyDataHolder(model));
        }

        mAdapter.updateData(objectList);
    }

    private void initFloatingActionMenu(final View _view, final OrganizationModel _organizationModel) {
        mFloatingActionMenu = (FloatingActionMenu) _view.findViewById(R.id.floating_action_menu);
        FloatingActionButton mapFAB = (FloatingActionButton) _view.findViewById(R.id.floating_action_menu_map);
        FloatingActionButton linkFAB = (FloatingActionButton) _view.findViewById(R.id.floating_action_menu_link);
        FloatingActionButton phoneFAB = (FloatingActionButton) _view.findViewById(R.id.floating_action_menu_phone);

        createCustomAnimation(mFloatingActionMenu);

        mapFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivityGeneric().showProgressDialog(mProgressDialogFragment);
                mRequest = mMapApi.getRequest(_organizationModel);
                mMapApi.getCoordinates(mRequest, getActivityGeneric());
                mLogger.d(TAG, "initFloatingActionMenu: map FAB clicked: address: " + mRequest);
            }
        });

        linkFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(_organizationModel.getLink()));
                getActivityGeneric().startActivity(intent);
                mLogger.d(TAG, "initFloatingActionMenu: link FAB clicked: Link: " + _organizationModel.getLink());
            }
        });

        phoneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + _organizationModel.getPhone()));
                getActivityGeneric().startActivity(intent);
                mLogger.d(TAG, "initFloatingActionMenu: phone FAB clicked: Phone: " + _organizationModel.getPhone());
            }
        });
    }

    private void swipeRefreshListener(final SwipeRefreshLayout _refreshLayout) {
        _refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateDataAdapter(mOrganizationModel);
                _refreshLayout.setRefreshing(false);
            }
        });
    }

    private void createCustomAnimation(final FloatingActionMenu _actionMenu) {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(_actionMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(_actionMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(_actionMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(_actionMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                _actionMenu.getMenuIconView().setImageResource(_actionMenu.isOpened()
                        ? R.drawable.ic_close : R.drawable.ic_fab_menu);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        _actionMenu.setIconToggleAnimatorSet(set);
    }

    @Override
    public void onCreateOptionsMenu(final Menu _menu, final MenuInflater _inflater) {
        super.onCreateOptionsMenu(_menu, _inflater);
        _inflater.inflate(R.menu.menu_share, _menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem _item) {

        if (_item.getItemId() == R.id.action_share) {
            getActivityGeneric().showDetailDialog(mStrings);
        }

        return super.onOptionsItemSelected(_item);
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

            mStrings.add(model.getNameKey().trim() + "\t\t\t\t\t\t\t" + ask + "/" + bid);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFloatingActionMenu.isOpened()) {
            mFloatingActionMenu.close(true);
            getActivityGeneric().setToolbarIconBack(true);
        } else {
            getActivityGeneric().getSupportFragmentManager().popBackStack();
            getActivityGeneric().getToolbar().setNavigationIcon(null);
        }
    }
}
