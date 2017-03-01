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
    private ApiInterface apiInterface;

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

    public void init() {

        OkHttpClient okHttpClient = initOkHttpClient();

        GsonConverterFactory factory = initGsonConverterFactory();

        Retrofit retrofit = initRetrofit(okHttpClient, factory);

        apiInterface = retrofit.create(ApiInterface.class);

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

    public GsonConverterFactory initGsonConverterFactory() {
        final Type typeRegions = new TypeToken<List<Region>>() {
        }.getType();

        final Type typeCurrencies = new TypeToken<List<Currency>>() {
        }.getType();


        final Type type = new TypeToken<List<City>>() {
        }.getType();

        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(typeRegions, new RegionsDeserializer())
                .registerTypeAdapter(typeCurrencies, new CurrenciesDeserializer())
                .registerTypeAdapter(type, new CitiesDeserializer());

        final GsonConverterFactory factory = GsonConverterFactory.create(gsonBuilder.create());

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


    public void getResponse(final MCallback callback) {
        final Call<RootResponse> modelCall = apiInterface.getMainModel();

        modelCall.enqueue(new Callback<RootResponse>() {
                              @Override
                              public void onResponse(Call<RootResponse> call, Response<RootResponse> response) {
                                  mLogger.d(TAG, "onResponse - Response Code: " + response.code());

                                  if (response.isSuccessful()) callback.onSuccess(response.body());
                                  else callback.onError(response.errorBody().toString());
                              }

                              @Override
                              public void onFailure(Call<RootResponse> call, Throwable t) {
                                  callback.onError("error");
                              }
                          }
        );
    }

    public interface MCallback {

        void onSuccess(RootResponse response);

        void onError(String message);
    }
}
