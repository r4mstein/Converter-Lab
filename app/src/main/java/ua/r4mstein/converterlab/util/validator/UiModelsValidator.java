package ua.r4mstein.converterlab.util.validator;

import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

public final class UiModelsValidator implements IUiModelsValidator {

    private static final String TAG = "UiModelsValidator";

    private Logger mLogger;

    public UiModelsValidator() {
        mLogger = LogManager.getLogger();
    }

    @Override
    public OrganizationModel validateOrganizationModel(OrganizationModel _model) {

        if (_model.getTitle() == null) _model.setTitle("");
        if (_model.getRegion() == null) _model.setRegion("");
        if (_model.getCity() == null) _model.setCity("");
        if (_model.getPhone() == null) _model.setPhone("Нет информации");
        if (_model.getAddress() == null) _model.setAddress("Нет информации");
        if (_model.getLink() == null) _model.setLink("");

        mLogger.d(TAG, "validateOrganizationModel: title: " + _model.getTitle());

        return _model;
    }

    @Override
    public CurrenciesModel validateCurrenciesModel(CurrenciesModel _model) {

        if (_model.getAsk() == null) _model.setAsk("0");
        if (_model.getBid() == null) _model.setBid("0");

        mLogger.d(TAG, "validateCurrenciesModel: id: " + _model.getId());

        return _model;
    }
}
