package ua.r4mstein.converterlab.presentation.adapters.home;

import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

public interface IHomeItemActionsListener {

    void openOrganizationDetail(String _key);

    void openOrganizationLink(String _link);

    void openOrganizationLocation(OrganizationModel _model);

    void openOrganizationPhone(String _phone);
}
