package ua.r4mstein.converterlab.api;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.r4mstein.converterlab.models.MainModel;

import static ua.r4mstein.converterlab.presentation.MainActivity.BASE_URL;

public class RetrofitManager {

    private static final String TAG = "RetrofitManager";

    final HttpLoggingInterceptor loggingBODY = new HttpLoggingInterceptor();
    loggingBODY.setLevel(HttpLoggingInterceptor.Level.BODY);

    final HttpLoggingInterceptor loggingHEADERS = new HttpLoggingInterceptor();
    loggingHEADERS.setLevel(HttpLoggingInterceptor.Level.HEADERS);

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(loggingHEADERS)
            .addInterceptor(loggingBODY)
            .build();

    final GsonConverterFactory factory = GsonConverterFactory.create();

    Type listType = new TypeToken<List<MainModel.City>>(){}.getType();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(factory)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()

                    .registerTypeAdapter(listType, new JsonDeserializer<JSONObject>() {
                        @Override
                        public JSONObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                            return null ; //context.deserialize(json, MainModel.City.class);

                        }
                    }).create())
            )
            .build();

    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    Call<MainModel> modelCall = apiInterface.getMainModel();
    modelCall.enqueue(new Callback<MainModel>()

    {
        @Override
        public void onResponse(Call<MainModel> call, Response<MainModel> response) {
        Log.d(TAG, "onResponse - Response Code: " + response.code());

    }

        @Override
        public void onFailure(Call<MainModel> call, Throwable t) {

    }
    }

    );
}
