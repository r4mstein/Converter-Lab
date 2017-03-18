package ua.r4mstein.converterlab.api;


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
import ua.r4mstein.converterlab.api.models.RootResponse;
import ua.r4mstein.converterlab.api.models.cities.CitiesDeserializer;
import ua.r4mstein.converterlab.api.models.cities.City;
import ua.r4mstein.converterlab.api.models.currencies.CurrenciesDeserializer;
import ua.r4mstein.converterlab.api.models.currencies.Currency;
import ua.r4mstein.converterlab.api.models.regions.Region;
import ua.r4mstein.converterlab.api.models.regions.RegionsDeserializer;
import ua.r4mstein.converterlab.util.logger.LogManager;
import ua.r4mstein.converterlab.util.logger.Logger;

public class RetrofitManager {

    private static final String TAG = "RetrofitManager";
    private static final String BASE_URL = "http://resources.finance.ua/";

    private final Logger mLogger;

    private static RetrofitManager retrofitManager;
    private ApiInterface apiInterface;

    private RetrofitManager() {
        mLogger = LogManager.getLogger();
    }

    public static RetrofitManager getInstance() {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
        }

        return retrofitManager;
    }

    public void init() {
        mLogger.d(TAG, "init");

        OkHttpClient okHttpClient = initOkHttpClient();

        GsonConverterFactory factory = initGsonConverterFactory();

        Retrofit retrofit = initRetrofit(okHttpClient, factory);

        apiInterface = retrofit.create(ApiInterface.class);

    }

    public OkHttpClient initOkHttpClient() {
        mLogger.d(TAG, "initOkHttpClient");

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
        mLogger.d(TAG, "initGsonConverterFactory");

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
        mLogger.d(TAG, "initRetrofit");

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(factory)
                .build();

        return retrofit;
    }


    public void getResponse(final RCallback callback) {
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
                                  mLogger.d(TAG, t.getMessage(), t);
                                  callback.onError("error");
                              }
                          }
        );
    }

    public interface RCallback {

        void onSuccess(RootResponse response);

        void onError(String message);
    }
}
