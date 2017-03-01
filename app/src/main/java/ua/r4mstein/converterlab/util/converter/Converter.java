package ua.r4mstein.converterlab.util.converter;

import java.util.ArrayList;
import java.util.List;

import ua.r4mstein.converterlab.api.models.RootResponse;
import ua.r4mstein.converterlab.api.models.cities.City;
import ua.r4mstein.converterlab.api.models.organizations.Organization;
import ua.r4mstein.converterlab.api.models.regions.Region;
import ua.r4mstein.converterlab.presentation.ui_models.HomeModel;

public final class Converter implements IConverter {

    private List<HomeModel> mHomeModels = new ArrayList<>();

    @Override
    public void convert(RootResponse response) {
        List<Organization> organizations = response.getOrganizations();
        List<Region> regions = response.getRegions();
        List<City> cities = response.getCities();

        for (Organization organization : organizations) {
            HomeModel homeModel = new HomeModel();

            homeModel.setId(organization.id);
            homeModel.setTitle(organization.title);

            String regionUI = null;
            for (Region region : regions) {
                if (region.id.equals(organization.regionId)) {
                    regionUI = region.name;
                    break;
                }
            }
            homeModel.setRegion(regionUI);

            String cityUI = null;
            for (City city : cities) {
                if (city.id.equals(organization.cityId)) {
                    cityUI = city.name;
                    break;
                }
            }
            homeModel.setCity(cityUI);

            homeModel.setPhone(organization.phone);
            homeModel.setAddress(organization.address);

            mHomeModels.add(homeModel);
        }
    }

    @Override
    public List<HomeModel> getHomeModels() {
        return mHomeModels;
    }

    @Override
    public List<?> getCurrensies() {
        return null;
    }
}
