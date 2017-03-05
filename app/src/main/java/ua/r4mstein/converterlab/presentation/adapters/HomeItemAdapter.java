package ua.r4mstein.converterlab.presentation.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

        holder.titleTextView.setText(model.getTitle());
        holder.regionTextView.setText(model.getRegion());
        holder.cityTextView.setText(model.getCity());
        holder.phoneTextView.setText(model.getPhone());
        holder.addressTextView.setText(model.getAddress());

        holder.linkImageButton.setOnClickListener(linkClickListener(model));
        holder.locationImageButton.setOnClickListener(locationClickListener(model));
        holder.phoneImageButton.setOnClickListener(phoneClickListener(model));
        holder.nextImageButton.setOnClickListener(nextClickListener(model));

    }

    @Override
    public int getItemCount() {
        return mOrganizationList.size();
    }

    @NonNull
    private View.OnClickListener nextClickListener(final OrganizationModel model) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Next", Toast.LENGTH_SHORT).show();
                model.getCity();
            }
        };
    }

    @NonNull
    private View.OnClickListener phoneClickListener(final OrganizationModel model) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + model.getPhone()));
                v.getContext().startActivity(intent);
            }
        };
    }

    @NonNull
    private View.OnClickListener locationClickListener(final OrganizationModel model) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Location", Toast.LENGTH_SHORT).show();
                model.getAddress();
            }
        };
    }

    @NonNull
    private View.OnClickListener linkClickListener(final OrganizationModel model) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getLink()));
                v.getContext().startActivity(intent);
            }
        };
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView regionTextView;
        public TextView cityTextView;
        public TextView phoneTextView;
        public TextView addressTextView;

        public ImageButton linkImageButton;
        public ImageButton locationImageButton;
        public ImageButton phoneImageButton;
        public ImageButton nextImageButton;

        public HomeViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.home_title);
            regionTextView = (TextView) itemView.findViewById(R.id.home_region);
            cityTextView = (TextView) itemView.findViewById(R.id.home_city);
            phoneTextView = (TextView) itemView.findViewById(R.id.home_phone);
            addressTextView = (TextView) itemView.findViewById(R.id.home_address);

            linkImageButton = (ImageButton) itemView.findViewById(R.id.home_link_ib);
            locationImageButton = (ImageButton) itemView.findViewById(R.id.home_location_ib);
            phoneImageButton = (ImageButton) itemView.findViewById(R.id.home_phone_ib);
            nextImageButton = (ImageButton) itemView.findViewById(R.id.home_next_ib);

        }
    }
}
