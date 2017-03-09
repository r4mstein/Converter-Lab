package ua.r4mstein.converterlab.presentation.adapters.home;

public interface IHomeItemActionsListener {

    void openOrganizationDetail(String key);

    void openOrganizationLink(String link);

    void openOrganizationLocation();

    void openOrganizationPhone(String phone);
}