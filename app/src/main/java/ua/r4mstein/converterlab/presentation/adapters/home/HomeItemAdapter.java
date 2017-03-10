package ua.r4mstein.converterlab.presentation.adapters.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

public final class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.HomeViewHolder> {

    private static final String TAG = "HomeItemAdapter";

    private List<OrganizationModel> mOrganizationList = new ArrayList<>();

    private Logger mLogger;

    private IHomeItemActionsListener mActionsListener;

    public HomeItemAdapter() {
        mLogger = LogManager.getLogger();
    }

    public void updateData(List<OrganizationModel> organizationList) {
        mOrganizationList.clear();
        mOrganizationList = organizationList;
        notifyDataSetChanged();
        mLogger.d(TAG, "updateData");
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.home_item, parent, false);
        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        final OrganizationModel model = mOrganizationList.get(position);
        holder.bindData(model);
        holder.setActionsListener(mActionsListener);

    }

    @Override
    public void onViewRecycled(HomeViewHolder holder) {
        super.onViewRecycled(holder);
        holder.unbindData();
    }

    @Override
    public int getItemCount() {
        return mOrganizationList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setActionsListener(IHomeItemActionsListener actionsListener) {
        mActionsListener = actionsListener;
    }

    public void setFilter(ArrayList<OrganizationModel> newList) {
        mOrganizationList.clear();
        mOrganizationList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;
        TextView regionTextView;
        TextView cityTextView;
        TextView phoneTextView;
        TextView addressTextView;

        ImageButton linkImageButton;
        ImageButton locationImageButton;
        ImageButton phoneImageButton;
        ImageButton nextImageButton;

        private OrganizationModel mModel;
        private IHomeItemActionsListener mActionsListener;

        public HomeViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.detail_organization_title);
            regionTextView = (TextView) itemView.findViewById(R.id.detail_organization_region);
            cityTextView = (TextView) itemView.findViewById(R.id.detail_organization_city);
            phoneTextView = (TextView) itemView.findViewById(R.id.detail_organization_phone);
            addressTextView = (TextView) itemView.findViewById(R.id.detail_organization_address);

            linkImageButton = (ImageButton) itemView.findViewById(R.id.home_link_ib);
            locationImageButton = (ImageButton) itemView.findViewById(R.id.home_location_ib);
            phoneImageButton = (ImageButton) itemView.findViewById(R.id.home_phone_ib);
            nextImageButton = (ImageButton) itemView.findViewById(R.id.home_next_ib);

            linkImageButton.setOnClickListener(this);
            locationImageButton.setOnClickListener(this);
            phoneImageButton.setOnClickListener(this);
            nextImageButton.setOnClickListener(this);

        }

        public void setActionsListener(IHomeItemActionsListener actionsListener) {
            mActionsListener = actionsListener;
        }

        public void bindData(final OrganizationModel _model) {
            mModel = _model;

            titleTextView.setText(mModel.getTitle());
            regionTextView.setText(mModel.getRegion());
            cityTextView.setText(mModel.getCity());
            phoneTextView.setText(mModel.getPhone());
            addressTextView.setText(mModel.getAddress());
        }

        public void unbindData() {
            mModel = null;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.home_next_ib:
                    mActionsListener.openOrganizationDetail(mModel.getId());
                    break;
                case R.id.home_link_ib:
                    mActionsListener.openOrganizationLink(mModel.getLink());
                    break;
                case R.id.home_location_ib:
                    String request = mModel.getRegion() + " " + mModel.getCity() + " " +
                            mModel.getAddress();
                    mActionsListener.openOrganizationLocation(request);
                    break;
                case R.id.home_phone_ib:
                    mActionsListener.openOrganizationPhone(mModel.getPhone());
            }
        }
    }
}
