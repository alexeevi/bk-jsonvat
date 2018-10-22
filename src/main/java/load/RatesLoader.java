package src.main.java.load;

import src.main.java.data.CountryRate;

import java.io.IOException;
import java.util.function.Consumer;

public interface RatesLoader {

    /**
     * Processes raw data as set of {@link CountryRate} records
     * @param consumer callback called for each record
     * @throws IOException when an issue occur while accessing data source
     * @throws IllegalArgumentException when an issue occur while parsing specific country rate record
     */
    void load(Consumer<CountryRate> consumer) throws IOException, IllegalArgumentException;
}
