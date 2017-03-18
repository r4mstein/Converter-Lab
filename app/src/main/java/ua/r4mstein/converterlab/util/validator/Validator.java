package ua.r4mstein.converterlab.util.validator;

import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.logger.LogManager;

public final class Validator implements IValidator {

    private static final String TAG = "Validator";

    @Override
    public OrganizationModel validateOrganizationModel(OrganizationModel model) {

        if (model.getTitle() == null) model.setTitle("");
        if (model.getRegion() == null) model.setRegion("");
        if (model.getCity() == null) model.setCity("");
        if (model.getPhone() == null) model.setPhone("Нет информации");
        if (model.getAddress() == null) model.setAddress("Нет информации");
        if (model.getLink() == null) model.setLink("");

        LogManager.getLogger().d(TAG, "validateOrganizationModel: title: " + model.getTitle());

        return model;
    }

    @Override
    public CurrenciesModel validateCurrenciesModel(CurrenciesModel model) {

        if (model.getAsk() == null) model.setAsk("0");
        if (model.getBid() == null) model.setBid("0");

        LogManager.getLogger().d(TAG, "validateCurrenciesModel: id: " + model.getId());

        return model;
    }
}
