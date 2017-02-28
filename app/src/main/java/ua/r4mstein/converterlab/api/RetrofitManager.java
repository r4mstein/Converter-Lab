package ua.r4mstein.converterlab.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.r4mstein.converterlab.models.RootResponse;
import ua.r4mstein.converterlab.models.cities.CitiesDeserializer;
import ua.r4mstein.converterlab.models.cities.City;
import ua.r4mstein.converterlab.models.currencies.CurrenciesDeserializer;
import ua.r4mstein.converterlab.models.currencies.Currency;
import ua.r4mstein.converterlab.models.regions.Region;
import ua.r4mstein.converterlab.models.regions.RegionsDeserializer;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

import static ua.r4mstein.converterlab.presentation.MainActivity.BASE_URL;

public class RetrofitManager {

    private static final String TAG = "RetrofitManager";

    private final Logger mLogger = LogManager.getLogger();

    private static RetrofitManager retrofitManager;

    private RetrofitManager() {
    }

    public static RetrofitManager getInstance() {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
        } else {
            return retrofitManager;
        }
        return retrofitManager;
    }

    public void test() {

        OkHttpClient okHttpClient = initOkHttpClient();

//        GsonBuilder gsonBuilder = initCityGsonBuilder();
        GsonBuilder gsonBuilder = initCurrencyGsonBuilder();
//        GsonBuilder gsonBuilder = initRegionGsonBuilder();

        GsonConverterFactory factory = initGsonConverterFactory(gsonBuilder);

        Retrofit retrofit = initRetrofit(okHttpClient, factory);

        final ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        getResponse(apiInterface);
    }

    public OkHttpClient initOkHttpClient() {
        final HttpLoggingInterceptor loggingBODY = new HttpLoggingInterceptor();
        loggingBODY.setLevel(HttpLoggingInterceptor.Level.BODY);

        final HttpLoggingInterceptor loggingHEADERS = new HttpLoggingInterceptor();
        loggingHEADERS.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingHEADERS)
                .addInterceptor(loggingBODY)
                .build();

        return okHttpClient;
    }

    public GsonBuilder initRegionGsonBuilder() {
        final Type type = new TypeToken<List<Region>>() {
        }.getType();

        final GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(type, new RegionsDeserializer());

        return gsonBuilder;
    }

    public GsonBuilder initCityGsonBuilder() {
        final Type type = new TypeToken<List<City>>() {
        }.getType();

        final GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(type, new CitiesDeserializer());

        return gsonBuilder;
    }

    public GsonBuilder initCurrencyGsonBuilder() {
        final Type type = new TypeToken<List<Currency>>() {
        }.getType();

        final GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(type, new CurrenciesDeserializer());

        return gsonBuilder;
    }

    public GsonConverterFactory initGsonConverterFactory(GsonBuilder gsonBuilder) {
        final Gson gson = gsonBuilder.create();

        final GsonConverterFactory factory = GsonConverterFactory.create(gson);

        return factory;
    }

    public Retrofit initRetrofit(OkHttpClient okHttpClient, GsonConverterFactory factory) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(factory)
                .build();

        return retrofit;
    }

    public void getResponse(ApiInterface apiInterface) {
        final Call<RootResponse> modelCall = apiInterface.getMainModel();

        modelCall.enqueue(new Callback<RootResponse>() {
                              @Override
                              public void onResponse(Call<RootResponse> call, Response<RootResponse> response) {
                                  mLogger.d(TAG, "onResponse - Response Code: " + response.code());

                              }

                              @Override
                              public void onFailure(Call<RootResponse> call, Throwable t) {

                              }
                          }
        );
    }
}
