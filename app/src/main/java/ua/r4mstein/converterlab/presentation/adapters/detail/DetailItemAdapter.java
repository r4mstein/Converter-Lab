package ua.r4mstein.converterlab.presentation.adapters.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.adapters.detail.base.DataHolderBase;
import ua.r4mstein.converterlab.presentation.adapters.detail.base.ViewHolderBase;
import ua.r4mstein.converterlab.presentation.adapters.detail.data_holders.CurrencyDataHolder;
import ua.r4mstein.converterlab.presentation.adapters.detail.data_holders.CurrencyHeaderDataHolder;
import ua.r4mstein.converterlab.presentation.adapters.detail.data_holders.OrganizationDataHolder;
import ua.r4mstein.converterlab.presentation.adapters.detail.view_holders.CurrencyHeaderViewHolder;
import ua.r4mstein.converterlab.presentation.adapters.detail.view_holders.CurrencyViewHolder;
import ua.r4mstein.converterlab.presentation.adapters.detail.view_holders.OrganizationViewHolder;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_ADAPTER_CURRENCY;
import static ua.r4mstein.converterlab.util.Constants.DETAIL_ADAPTER_CURRENCY_HEADER;
import static ua.r4mstein.converterlab.util.Constants.DETAIL_ADAPTER_ORGANIZATION;

public final class DetailItemAdapter extends RecyclerView.Adapter<ViewHolderBase> {

    private static final String TAG = "DetailItemAdapter";

    private Logger mLogger;
    private Context mContext;

    private List<DataHolderBase> mItemsList = new ArrayList<>();

    public DetailItemAdapter(final Context _context) {
        mContext = _context;
        mLogger = LogManager.getLogger();
    }

    public final void updateData(final List<DataHolderBase> _itemsList) {
        mItemsList.clear();
        mItemsList = _itemsList;
        notifyDataSetChanged();
        mLogger.d(TAG, "updateData");
    }

    @Override
    public int getItemViewType(final int _position) {
        return mItemsList.get(_position).itemViewType();
    }

    @Override
    public ViewHolderBase onCreateViewHolder(final ViewGroup _parent, final int _viewType) {

        ViewHolderBase viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(_parent.getContext());

        switch (_viewType) {
            case DETAIL_ADAPTER_ORGANIZATION:
                View organizationView = inflater.inflate(R.layout.detail_organization_item, _parent, false);
                viewHolder = new OrganizationViewHolder(organizationView);
                break;
            case DETAIL_ADAPTER_CURRENCY_HEADER:
                View currencyHeaderView = inflater.inflate(R.layout.detail_currency_header_item, _parent, false);
                viewHolder = new CurrencyHeaderViewHolder(currencyHeaderView);
                break;
            case DETAIL_ADAPTER_CURRENCY:
                View currencyView = inflater.inflate(R.layout.detail_currency_item, _parent, false);
                viewHolder = new CurrencyViewHolder(currencyView, mContext);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBase _holder, final int _position) {

        switch (_holder.getItemViewType()) {
            case DETAIL_ADAPTER_ORGANIZATION:
                ((OrganizationViewHolder) _holder).setData((OrganizationDataHolder) mItemsList.get(_position));
                break;
            case DETAIL_ADAPTER_CURRENCY_HEADER:
                ((CurrencyHeaderViewHolder) _holder).setData((CurrencyHeaderDataHolder) mItemsList.get(_position));
                break;
            case DETAIL_ADAPTER_CURRENCY:
                ((CurrencyViewHolder) _holder).setData((CurrencyDataHolder) mItemsList.get(_position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }
}
