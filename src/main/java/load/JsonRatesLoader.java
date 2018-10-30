package load;

import com.google.gson.*;
import data.CountryRate;

import java.io.*;
import java.util.function.Consumer;

public abstract class JsonRatesLoader implements RatesLoader {

    private String source;
    private Gson gson = new Gson();
    private TypeAdapter<CountryRate> countryRateAdapter = gson.getAdapter(CountryRate.class).nullSafe();

    public JsonRatesLoader(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    abstract protected Reader getReader() throws IOException;

    @Override
    public void load(Consumer<CountryRate> consumer) throws IOException, IllegalArgumentException {
        getRatesArray(getReader())
                .forEach(element -> {
                    try {
                        consumer.accept(countryRateAdapter.fromJsonTree(element));
                    } catch (JsonIOException | JsonSyntaxException ex) {
                        throw new IllegalArgumentException("Could not parse country rate element");
                    }
                });
    }

    private JsonArray getRatesArray(Reader reader) throws IllegalArgumentException {
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(reader);
        if (!jsonTree.isJsonObject()) {
            throw new IllegalArgumentException("Not a JSON object");
        }
        JsonObject jsonObject = jsonTree.getAsJsonObject();

        JsonElement ratesElement = jsonObject.get("rates");
        if (ratesElement == null || !ratesElement.isJsonArray()) {
            throw new IllegalArgumentException("Cannot find rates array");
        }
        return ratesElement.getAsJsonArray();
    }

}
