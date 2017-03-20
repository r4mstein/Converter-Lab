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

    public OrganizationViewHolder(final View _itemView) {
        super(_itemView);

        titleTextView = (TextView) _itemView.findViewById(R.id.detail_organization_title);
        regionTextView = (TextView) _itemView.findViewById(R.id.detail_organization_region);
        cityTextView = (TextView) _itemView.findViewById(R.id.detail_organization_city);
        phoneTextView = (TextView) _itemView.findViewById(R.id.detail_organization_phone);
        addressTextView = (TextView) _itemView.findViewById(R.id.detail_organization_address);
    }

    @Override
    public void setData(final OrganizationDataHolder _data) {
        super.setData(_data);
        configureOrganizationViewHolder(_data.organizationModel);
    }

    private void configureOrganizationViewHolder(final OrganizationModel _model) {

        if (_model != null) {
            titleTextView.setText(_model.getTitle());
            regionTextView.setText(_model.getRegion());
            cityTextView.setText(_model.getCity());
            phoneTextView.setText(String.format("%s %s", "Тел.: ", _model.getPhone()));
            addressTextView.setText(String.format("%s %s", "Адрес: ", _model.getAddress()));
        }
    }
}
