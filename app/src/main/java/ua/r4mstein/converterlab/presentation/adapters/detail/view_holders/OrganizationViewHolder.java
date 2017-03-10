package ua.r4mstein.converterlab.presentation.adapters.detail.view_holders;

import android.view.View;
import android.widget.TextView;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.adapters.detail.base.ViewHolderBase;
import ua.r4mstein.converterlab.presentation.adapters.detail.data_holders.OrganizationDataHolder;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

public final class OrganizationViewHolder extends ViewHolderBase<OrganizationDataHolder> {

    private TextView titleTextView;
    private TextView regionTextView;
    private TextView cityTextView;
    private TextView phoneTextView;
    private TextView addressTextView;

    public OrganizationViewHolder(View itemView) {
        super(itemView);

        titleTextView = (TextView) itemView.findViewById(R.id.detail_organization_title);
        regionTextView = (TextView) itemView.findViewById(R.id.detail_organization_region);
        cityTextView = (TextView) itemView.findViewById(R.id.detail_organization_city);
        phoneTextView = (TextView) itemView.findViewById(R.id.detail_organization_phone);
        addressTextView = (TextView) itemView.findViewById(R.id.detail_organization_address);
    }

    @Override
    public void setData(OrganizationDataHolder data) {
        super.setData(data);
        configureOrganizationViewHolder(data.organizationModel);
    }

    private void configureOrganizationViewHolder(OrganizationModel model) {

        if (model != null) {
            titleTextView.setText(model.getTitle());
            regionTextView.setText(model.getRegion());
            cityTextView.setText(model.getCity());
            phoneTextView.setText(model.getPhone());
            addressTextView.setText(model.getAddress());
        }
    }
}
