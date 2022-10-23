package parserPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import starterPart.GettersSetters;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class YahooParser implements Runnable {
    private static final Logger logger = LogManager.getLogger(YahooParser.class);

    private final String ticker;
    private final GettersSetters gettersSetters;

    public YahooParser(String ticker, GettersSetters gettersSetters) {
        this.ticker = ticker;
        this.gettersSetters = gettersSetters;
    }

    private HashMap<String, String> tickerData (Elements elements) {
        HashMap<String, String> tickerData = new HashMap<>();

        int i = 0;
        String header = "";
        for (Element value : elements.select("td")) {
            if (i % 2 == 0) { // Header
                header = value.text();
            } else { // Value
                tickerData.put(header, value.text());
            }
            i++;
        }
        return tickerData;
    }

    @Override
    public void run() {
        String url = "https://finance.yahoo.com/quote/" + ticker + "/key-statistics";
        try {
            Document document = Jsoup.connect(url)
                    .header("Cookie",
                            "...")
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .get();

            Elements elements = document.getElementsByAttributeValue("class", "W(100%) Bdcl(c) ");
            System.out.println(url);

            System.out.println(tickerData(elements));

        } catch (IOException ex) {
            logger.error("Could not parse " + url + ".\n" + ex);
        }

        try {
            gettersSetters.getConnection().close();
        } catch (SQLException ignored) {
        }
    }
}
