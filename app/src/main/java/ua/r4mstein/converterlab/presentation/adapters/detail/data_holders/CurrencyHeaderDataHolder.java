package ua.r4mstein.converterlab.presentation.adapters.detail.data_holders;

import ua.r4mstein.converterlab.presentation.adapters.detail.base.DataHolderBase;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_ADAPTER_CURRENCY_HEADER;

public final class CurrencyHeaderDataHolder extends DataHolderBase {

    public String header;

    public CurrencyHeaderDataHolder(String header) {
        super();
        this.header = header;
    }

    @Override
    public int itemViewType() {
        return DETAIL_ADAPTER_CURRENCY_HEADER;
    }
}
