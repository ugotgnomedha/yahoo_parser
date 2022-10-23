package parserPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import starterPart.GettersSetters;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class YahooParser implements Runnable {
    private static final Logger logger = LogManager.getLogger(YahooParser.class);

    private final String ticker;
    private final GettersSetters gettersSetters;

    public YahooParser(String ticker, GettersSetters gettersSetters) {
        this.ticker = ticker;
        this.gettersSetters = gettersSetters;
    }

    @Override
    public void run() {
        // https://finance.yahoo.com/quote/INTC/key-statistics?btn+primary=agree&amp;guccounter=1
        // https://finance.yahoo.com/quote/INTC/key-statistics?lang=en-US&amp;btn+primary=agree&amp;amp;guccounter=1&amp;guccounter=2
        String url = "https://finance.yahoo.com/quote/" + ticker + "/key-statistics";
        try {

            Document document = Jsoup.connect(url)
                    .header("Cookie",
                            "...")
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .get();

            Elements data = document.getElementsByAttributeValue("class", "W(100%) Bdcl(c) ");
            System.out.println(url);
            for (Element value : data.select("td")) {
                //System.out.println(value.text());
            }
        } catch (IOException ex) {
            logger.error("Could not parse " + url + ".\n" + ex);
        }

        try {
            gettersSetters.getConnection().close();
        } catch (SQLException ignored) {
        }
    }
}
