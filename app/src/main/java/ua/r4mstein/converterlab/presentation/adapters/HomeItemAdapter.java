package ua.r4mstein.converterlab.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

public final class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.HomeViewHolder> {

    private Context mContext;
    private List<OrganizationModel> mOrganizationList;

    public HomeItemAdapter(Context context, List<OrganizationModel> organizationList) {
        mContext = context;
        mOrganizationList = organizationList;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.home_item, parent, false);
        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        OrganizationModel model = mOrganizationList.get(position);

        holder.mTitleTextView.setText(model.getTitle());
        holder.mRegionTextView.setText(model.getRegion());
        holder.mCityTextView.setText(model.getCity());
        holder.mPhoneTextView.setText(model.getPhone());
        holder.mAddressTextView.setText(model.getAddress());
    }

    @Override
    public int getItemCount() {
        return mOrganizationList.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;
        public TextView mRegionTextView;
        public TextView mCityTextView;
        public TextView mPhoneTextView;
        public TextView mAddressTextView;

        public HomeViewHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.home_title);
            mRegionTextView = (TextView) itemView.findViewById(R.id.home_region);
            mCityTextView = (TextView) itemView.findViewById(R.id.home_city);
            mPhoneTextView = (TextView) itemView.findViewById(R.id.home_phone);
            mAddressTextView = (TextView) itemView.findViewById(R.id.home_address);
        }
    }
}
