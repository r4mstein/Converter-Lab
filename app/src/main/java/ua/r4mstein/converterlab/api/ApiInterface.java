package ua.r4mstein.converterlab.api;


import retrofit2.Call;
import retrofit2.http.GET;
import ua.r4mstein.converterlab.models.RootResponse;

public interface ApiInterface {

    @GET("ru/public/currency-cash.json")
    Call<RootResponse> getMainModel();
}
