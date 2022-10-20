package parserPart;

public class YahooParser implements Runnable {
    private final String ticker;

    public YahooParser(String ticker) {
        this.ticker = ticker;
    }

    @Override
    public void run() {
        String url = "https://finance.yahoo.com/quote/" + ticker + "/key-statistics";


    }
}
