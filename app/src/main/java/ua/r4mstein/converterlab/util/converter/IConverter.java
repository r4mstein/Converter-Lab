package ua.r4mstein.converterlab.util.converter;

import java.util.List;

import ua.r4mstein.converterlab.api.models.RootResponse;
import ua.r4mstein.converterlab.presentation.ui_models.HomeModel;

public interface IConverter {

    void convert(RootResponse rootResponse);
    List<HomeModel> getHomeModels();
    List<?> getCurrensies();
}
