package ua.r4mstein.converterlab.api.models.cities;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class CitiesDeserializer implements JsonDeserializer<List<City>> {

    @Override
    public List<City> deserialize(final JsonElement _json, final Type _typeOfT, final JsonDeserializationContext _context) throws JsonParseException {

        final List<City> cities = new ArrayList<>();

        final Type type = new TypeToken<Map<String, String>>() {
        }.getType();

        final Map<String, String> cityMap =
                Collections.checkedMap(new Gson().<Map<String, String>>fromJson(_json, type),
                        String.class, String.class);

        if (cityMap != null && !cityMap.isEmpty()) {
            for (final String key : cityMap.keySet()) {
                final City city = new City();
                city.id = key;
                city.name = cityMap.get(key);
                cities.add(city);
            }
        }

        return cities;
    }
}
