package ua.r4mstein.converterlab.api.models.regions;

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

public final class RegionsDeserializer implements JsonDeserializer<List<Region>> {

    @Override
    public List<Region> deserialize(final JsonElement _json, final Type _typeOfT, final JsonDeserializationContext _context) throws JsonParseException {

        final List<Region> regions = new ArrayList<>();

        final Type type = new TypeToken<Map<String, String>>() {
        }.getType();

        final Map<String, String> regionMap =
                Collections.checkedMap(new Gson().<Map<String, String>>fromJson(_json, type),
                        String.class, String.class);

        if (regionMap != null && !regionMap.isEmpty()) {
            for (final String key : regionMap.keySet()) {
                final Region region = new Region();
                region.id = key;
                region.name = regionMap.get(key);
                regions.add(region);
            }
        }

        return regions;
    }

}
