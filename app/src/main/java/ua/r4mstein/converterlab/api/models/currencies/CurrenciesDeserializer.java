package ua.r4mstein.converterlab.api.models.currencies;

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

public final class CurrenciesDeserializer implements JsonDeserializer<List<Currency>> {

    @Override
    public List<Currency> deserialize(final JsonElement _json, final Type _typeOfT, final JsonDeserializationContext _context) throws JsonParseException {

        final List<Currency> currencies = new ArrayList<>();

        final Type type = new TypeToken<Map<String, String>>() {
        }.getType();

        final Map<String, String> currencyMap =
                Collections.checkedMap(new Gson().<Map<String, String>>fromJson(_json, type),
                        String.class, String.class);

        if (currencyMap != null && !currencyMap.isEmpty()) {
            for (final String key : currencyMap.keySet()) {
                final Currency currency = new Currency();
                currency.id = key;
                currency.name = currencyMap.get(key);
                currencies.add(currency);
            }
        }

        return currencies;
    }
}
