package ua.r4mstein.converterlab.models.regions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class RegionsDeserializer implements JsonDeserializer<List<Region>> {
    @Override
    public List<Region> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final List<Region> regions = new ArrayList<>();
        return regions;
    }
}
