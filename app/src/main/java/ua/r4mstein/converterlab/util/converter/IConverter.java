package ua.r4mstein.converterlab.util.converter;

import java.util.List;

import ua.r4mstein.converterlab.api.models.RootResponse;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

public interface IConverter {

    void convert(RootResponse _rootResponse);
    List<OrganizationModel> getOrganizationModels();
    List<CurrenciesModel> getCurrencies();
}
