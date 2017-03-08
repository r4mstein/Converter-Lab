package ua.r4mstein.converterlab.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ua.r4mstein.converterlab.R;

public final class DetailOrganizationViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public TextView regionTextView;
    public TextView cityTextView;
    public TextView phoneTextView;
    public TextView addressTextView;

    public DetailOrganizationViewHolder(View itemView) {
        super(itemView);

        titleTextView = (TextView) itemView.findViewById(R.id.detail_organization_title);
        regionTextView = (TextView) itemView.findViewById(R.id.detail_organization_region);
        cityTextView = (TextView) itemView.findViewById(R.id.detail_organization_city);
        phoneTextView = (TextView) itemView.findViewById(R.id.detail_organization_phone);
        addressTextView = (TextView) itemView.findViewById(R.id.detail_organization_address);
    }
}
