package load;

import java.io.*;

public class StringJsonRatesLoader extends JsonRatesLoader {

    public StringJsonRatesLoader(String source) {
        super(source);
    }

    @Override
    protected Reader getReader() throws IOException {
        return new BufferedReader(new StringReader(getSource()));
    }
}
