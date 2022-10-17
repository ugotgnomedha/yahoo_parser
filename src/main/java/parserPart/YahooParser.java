package parserPart;

import java.util.List;

public class YahooParser {
    public static void parseYahoo(List<String> tickers){
        for (String ticker : tickers) {
            System.out.println(ticker);
        }
    }
}
