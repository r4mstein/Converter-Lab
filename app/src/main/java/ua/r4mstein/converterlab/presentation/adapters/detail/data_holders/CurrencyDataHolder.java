package ua.r4mstein.converterlab.presentation.adapters.detail.data_holders;

import ua.r4mstein.converterlab.presentation.adapters.detail.base.DataHolderBase;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_ADAPTER_CURRENCY;

public final class CurrencyDataHolder extends DataHolderBase {

    public CurrenciesModel currenciesModel;

    public CurrencyDataHolder(final CurrenciesModel _currenciesModel) {
        super();
        this.currenciesModel = _currenciesModel;
    }

    @Override
    public int itemViewType() {
        return DETAIL_ADAPTER_CURRENCY;
    }
}
