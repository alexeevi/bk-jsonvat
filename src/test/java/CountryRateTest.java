import org.junit.Before;
import org.junit.Test;
import data.CountryRate;

import java.util.*;

import static org.junit.Assert.*;

public class CountryRateTest extends AbstractRateTest {

    private CountryRate rate;

    @Before
    public void init() {
        long millis = new Date().getTime();

        List<CountryRate.Period> periods = new LinkedList<>();
        periods.add(createPeriod(new Date(millis - 100), RATE_NAME, 10.));
        periods.add(createPeriod(new Date(millis), RATE_NAME, 20.));
        periods.add(createPeriod(new Date(millis - 200), RATE_NAME, 30.));

        rate = new CountryRate("Czech", "CZ", "CZK", periods);
    }

    @Test
    public void gettingFieldsShouldReturnProperValues() {
        assertEquals("Czech", rate.getName());
        assertEquals("CZ", rate.getCode());
        assertEquals("CZK", rate.getCountryCode());
    }

    @Test
    public void getLastRatesShouldReturnTheMostRecent() {
        assertEquals(new Double(20.), rate.getLastRates().get(RATE_NAME));
    }

}