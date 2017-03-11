package ua.r4mstein.converterlab.util.validator;

import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

public final class Validator implements IValidator {

    @Override
    public OrganizationModel validateOrganizationModel(OrganizationModel model) {

        if (model.getTitle() == null) model.setTitle("");
        if (model.getRegion() == null) model.setRegion("");
        if (model.getCity() == null) model.setCity("");
        if (model.getPhone() == null) model.setPhone("Нет информации");
        if (model.getAddress() == null) model.setAddress("Нет информации");
        if (model.getLink() == null) model.setLink("");

        return model;
    }

    @Override
    public CurrenciesModel validateCurrenciesModel(CurrenciesModel model) {

        if (model.getAsk() == null) model.setAsk("0");
        if (model.getBid() == null) model.setBid("0");

        return model;
    }
}
