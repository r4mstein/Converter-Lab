package ua.r4mstein.converterlab.util.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ua.r4mstein.converterlab.api.models.RootResponse;
import ua.r4mstein.converterlab.api.models.cities.City;
import ua.r4mstein.converterlab.api.models.organizations.Organization;
import ua.r4mstein.converterlab.api.models.regions.Region;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;
import ua.r4mstein.converterlab.util.validator.IValidator;
import ua.r4mstein.converterlab.util.validator.Validator;

public final class Converter implements IConverter {

    private List<OrganizationModel> mOrganizationModels = new ArrayList<>();
    private List<CurrenciesModel> mCurrenciesModels = new ArrayList<>();

    @Override
    public void convert(RootResponse response) {
        mOrganizationModels.clear();
        mCurrenciesModels.clear();

        List<Organization> organizations = response.getOrganizations();
        List<Region> regions = response.getRegions();
        List<City> cities = response.getCities();

        for (Organization organization : organizations) {
            OrganizationModel organizationModel = new OrganizationModel();

            organizationModel.setId(organization.id);
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

            IValidator validator = new Validator();
            validator.validateOrganizationModel(organizationModel);

            mOrganizationModels.add(organizationModel);

            //
            Map<String, Organization.Currency> currenciesMap = organization.currencies;

            for (String key: currenciesMap.keySet()) {
                CurrenciesModel currenciesModel = new CurrenciesModel();

                currenciesModel.setId(organization.id + key);
                currenciesModel.setName(key);
                currenciesModel.setAsk(currenciesMap.get(key).ask);
                currenciesModel.setBid(currenciesMap.get(key).bid);

                mCurrenciesModels.add(currenciesModel);
            }

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
