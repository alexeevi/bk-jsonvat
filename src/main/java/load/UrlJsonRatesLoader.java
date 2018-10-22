package src.main.java.load;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class UrlJsonRatesLoader extends JsonRatesLoader {

    public UrlJsonRatesLoader(String source) {
        super(source);
    }

    @Override
    protected Reader getReader() throws IOException {
        URL url = new URL(getSource());
        return new BufferedReader(new InputStreamReader(url.openStream()));
    }

}
