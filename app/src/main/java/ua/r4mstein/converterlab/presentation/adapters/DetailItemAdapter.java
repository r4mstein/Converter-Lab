package ua.r4mstein.converterlab.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

public final class DetailItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ORGANIZATION = 0;
    private static final int CURRENCY_HEADER = 1;
    private static final int CURRENCY = 2;

    private List<Object> mItemsList;

    public DetailItemAdapter(List<Object> itemsList) {
        this.mItemsList = itemsList;
    }

    @Override
    public int getItemViewType(int position) {
        if (mItemsList.get(position) instanceof OrganizationModel) {
            return ORGANIZATION;
        } else if (mItemsList.get(position) instanceof String) {
            return CURRENCY_HEADER;
        } else if (mItemsList.get(position) instanceof CurrenciesModel) {
            return CURRENCY;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ORGANIZATION:
                View organizationView = inflater.inflate(R.layout.detail_organization_item, parent, false);
                viewHolder = new DetailOrganizationViewHolder(organizationView);
                break;
            case CURRENCY_HEADER:
                View currencyHeaderView = inflater.inflate(R.layout.detail_currency_header_item, parent, false);
                viewHolder = new DetailCurrencyHeaderViewHolder(currencyHeaderView);
                break;
            case CURRENCY:
                View currencyView = inflater.inflate(R.layout.detail_currency_item, parent, false);
                viewHolder = new DetailCurrencyViewHolder(currencyView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case ORGANIZATION:
                DetailOrganizationViewHolder organizationViewHolder = (DetailOrganizationViewHolder) holder;
                configureOrganizationViewHolder(organizationViewHolder, position);
                break;
            case CURRENCY_HEADER:
//                DetailCurrencyHeaderViewHolder currencyHeaderViewHolder = (DetailCurrencyHeaderViewHolder) holder;
                break;
            case CURRENCY:
                DetailCurrencyViewHolder currencyViewHolder = (DetailCurrencyViewHolder) holder;
                configureCurrencyViewHolder(currencyViewHolder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    private void configureOrganizationViewHolder(DetailOrganizationViewHolder organizationViewHolder, int position) {
        OrganizationModel model = (OrganizationModel) mItemsList.get(position);

        if (model != null) {
            organizationViewHolder.titleTextView.setText(model.getTitle());
            organizationViewHolder.regionTextView.setText(model.getRegion());
            organizationViewHolder.cityTextView.setText(model.getCity());
            organizationViewHolder.phoneTextView.setText(model.getPhone());
            organizationViewHolder.addressTextView.setText(model.getAddress());
        }
    }

    private void configureCurrencyViewHolder(DetailCurrencyViewHolder currencyViewHolder, int position) {
        CurrenciesModel model = (CurrenciesModel) mItemsList.get(position);

        if (model != null) {
            currencyViewHolder.nameTextView.setText(model.getName());
            currencyViewHolder.askTextView.setText(model.getAsk());
            currencyViewHolder.bidTextView.setText(model.getBid());
        }
    }
}
