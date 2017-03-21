package ua.r4mstein.converterlab.util.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ua.r4mstein.converterlab.api.models.RootResponse;
import ua.r4mstein.converterlab.api.models.cities.City;
import ua.r4mstein.converterlab.api.models.currencies.Currency;
import ua.r4mstein.converterlab.api.models.organizations.Organization;
import ua.r4mstein.converterlab.api.models.regions.Region;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.validator.IUiModelsValidator;
import ua.r4mstein.converterlab.util.validator.UiModelsValidator;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_FRAGMENT_COLOR_GREEN;

public final class Converter implements IConverter {

    private List<OrganizationModel> mOrganizationModels = new ArrayList<>();
    private List<CurrenciesModel> mCurrenciesModels = new ArrayList<>();

    @Override
    public void convert(final RootResponse _rootResponse) {
        mOrganizationModels.clear();
        mCurrenciesModels.clear();

        List<Organization> organizations = _rootResponse.getOrganizations();
        List<Region> regions = _rootResponse.getRegions();
        List<City> cities = _rootResponse.getCities();
        List<Currency> currencyList = _rootResponse.getCurrencies();
        String date = _rootResponse.getDate();

        for (Organization organization : organizations) {
            OrganizationModel organizationModel = new OrganizationModel();

            organizationModel.setId(organization.id);
            organizationModel.setDate(date);
            organizationModel.setTitle(organization.title);

            String regionUI = null;
            for (Region region : regions) {
                if (region.id.equals(organization.regionId)) {
                    regionUI = region.name;
                    break;
                }
            }
            organizationModel.setRegion(regionUI);

            String cityUI = null;
            for (City city : cities) {
                if (city.id.equals(organization.cityId)) {
                    cityUI = city.name;
                    break;
                }
            }
            organizationModel.setCity(cityUI);

            organizationModel.setPhone(organization.phone);
            organizationModel.setAddress(organization.address);
            organizationModel.setLink(organization.link);

            IUiModelsValidator validator = new UiModelsValidator();
            validator.validateOrganizationModel(organizationModel);

            //
            Map<String, Organization.Currency> currenciesMap = organization.currencies;

            for (String key: currenciesMap.keySet()) {
                CurrenciesModel currenciesModel = new CurrenciesModel();

                currenciesModel.setId(organization.id + key);
                currenciesModel.setOrganizationId(organization.id);

                String currenciesName = null;
                for (Currency currency : currencyList) {
                    if (currency.id.equals(key)) {
                        currenciesName = currency.name;
                        break;
                    }
                }
                currenciesModel.setName(currenciesName);
                currenciesModel.setNameKey(key);

                currenciesModel.setAsk(currenciesMap.get(key).ask);
                currenciesModel.setBid(currenciesMap.get(key).bid);

                currenciesModel.setAskColor(DETAIL_FRAGMENT_COLOR_GREEN);
                currenciesModel.setBidColor(DETAIL_FRAGMENT_COLOR_GREEN);

                validator.validateCurrenciesModel(currenciesModel);

                mCurrenciesModels.add(currenciesModel);
            }

            mOrganizationModels.add(organizationModel);
        }
    }

    @Override
    public List<OrganizationModel> getOrganizationModels() {
        return mOrganizationModels;
    }

    @Override
    public List<CurrenciesModel> getCurrencies() {
        return mCurrenciesModels;
    }
}
