package ua.r4mstein.converterlab.presentation.adapters.home;

import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

public interface IHomeItemActionsListener {

    void openOrganizationDetail(String key);

    void openOrganizationLink(String link);

    void openOrganizationLocation(OrganizationModel model);

    void openOrganizationPhone(String phone);
}
