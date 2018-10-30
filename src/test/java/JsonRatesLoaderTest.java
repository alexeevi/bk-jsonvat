import org.junit.Test;
import data.CountryRate;
import load.StringJsonRatesLoader;
import load.UrlJsonRatesLoader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class JsonRatesLoaderTest {

    @Test
    public void shouldProcess() throws Exception {
        List<CountryRate> countryRates = new LinkedList<>();
        new UrlJsonRatesLoader(getClass().getClassLoader().getResource("good-data.json").toString())
                .load(countryRates::add);
        assertThat(countryRates).hasSize(28);
    }

    @Test(expected = IOException.class)
    public void shouldRaiseErrorForUnknownUrl() throws Exception {
        new UrlJsonRatesLoader("abcd").load(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseErrorForDummyContent() throws Exception {
        new StringJsonRatesLoader("abracadabra").load(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseErrorWhenRatesArrayNotFound() throws Exception {
        new StringJsonRatesLoader("{\"version\":321}").load(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseErrorWhenRatesIsNotArray() throws Exception {
        new StringJsonRatesLoader("{\"rates\":123}").load(null);
    }

}