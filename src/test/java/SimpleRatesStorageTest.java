import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Test;
import data.CountryRate;
import storage.SimpleRatesStorage;

import java.util.*;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

public class SimpleRatesStorageTest extends AbstractRateTest {

    private static final String ANOTHER_RATE_NAME = "another";

    private SimpleRatesStorage storage;

    @Before
    public void setUp() throws Exception {
        storage = new SimpleRatesStorage();
        storage.add(createSimpleCountryRate("CZ", RATE_NAME, 1.));
        storage.add(createSimpleCountryRate("SK", RATE_NAME, 3.));

        // record with 2 rate types
        Map<String, Double> rates = new HashMap<>(1);
        rates.put(RATE_NAME, 2.);
        rates.put(ANOTHER_RATE_NAME, 10.);
        List<CountryRate.Period> periods = new LinkedList<>();
        periods.add(new CountryRate.Period(new Date(), rates));
        CountryRate austriaRates = new CountryRate("A", "A", "A", periods);
        storage.add(austriaRates);
    }

    @Test
    public void getRatesShouldReturnAll() {
        assertThat(storage.getRates()).hasSize(3)
                .extracting(CountryRate::getCode, cr -> cr.getLastRates().get(RATE_NAME))
                .contains(Tuple.tuple("CZ", 1.), Tuple.tuple("SK", 3.), Tuple.tuple("A", 2.));
    }

    @Test
    public void addRateShouldIgnoreDoubleCountries() {
        assertFalse("Storage must return false in case of double",
                storage.add(createSimpleCountryRate("CZ", RATE_NAME, 3.)));
        assertTrue(storage.add(createSimpleCountryRate("D", RATE_NAME, 1.)));

        assertThat(storage.getRates()).hasSize(4)
                .extracting(CountryRate::getCode)
                .contains("CZ", "SK", "A", "D");
    }

    @Test
    public void getRatesSortedByShouldSortProperly() {
        assertThat(storage.getRatesSortedBy(RATE_NAME, true))
                .extracting(CountryRate::getCode, cr -> cr.getLastRates().get(RATE_NAME))
                .containsExactly(Tuple.tuple("CZ", 1.), Tuple.tuple("A", 2.), Tuple.tuple("SK", 3.));

        assertThat(storage.getRatesSortedBy(RATE_NAME, false))
                .extracting(CountryRate::getCode, cr -> cr.getLastRates().get(RATE_NAME))
                .containsExactly(Tuple.tuple("SK", 3.), Tuple.tuple("A", 2.), Tuple.tuple("CZ", 1.));
    }

    @Test
    public void getRatesSortedByShouldSkipCountriesWithoutRate() {
        assertThat(storage.getRatesSortedBy(ANOTHER_RATE_NAME, true))
                .extracting(CountryRate::getCode, cr -> cr.getLastRates().get(ANOTHER_RATE_NAME))
                .containsOnly(Tuple.tuple("A", 10.));
    }
}