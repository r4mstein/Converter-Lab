package ua.r4mstein.converterlab.util.validator;

import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

public interface IUiModelsValidator {

    OrganizationModel validateOrganizationModel(OrganizationModel _model);
    CurrenciesModel validateCurrenciesModel(CurrenciesModel _model);
}
