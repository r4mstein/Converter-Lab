package ua.r4mstein.converterlab.presentation.adapters.detail.data_holders;

import ua.r4mstein.converterlab.presentation.adapters.detail.base.DataHolderBase;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_ADAPTER_ORGANIZATION;

public final class OrganizationDataHolder extends DataHolderBase {

    public OrganizationModel organizationModel;

    public OrganizationDataHolder(final OrganizationModel _organizationModel) {
        super();
        organizationModel = _organizationModel;
    }

    @Override
    public int itemViewType() {
        return DETAIL_ADAPTER_ORGANIZATION;
    }
}
