package parserPart;

import dbPart.TableCreatorRmver;
import dbPart.TickerDataInserter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import starterPart.GettersSetters;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Semaphore;

public class YahooParser implements Runnable {
    private static final Logger logger = LogManager.getLogger(YahooParser.class);

    private final String ticker;
    private final GettersSetters gettersSetters;
    private int counter;
    Semaphore s = new Semaphore(1);
    private List<Map<String, String>> listOfData = new ArrayList<>(50);

    public YahooParser(String ticker, GettersSetters gettersSetters, int i) {
        this.ticker = ticker;
        this.gettersSetters = gettersSetters;
        this.counter = i;
    }

    private HashMap<String, String> tickerData(Elements elements) {
        HashMap<String, String> tickerData = new HashMap<>();

        int i = 0;
        String header = "";
        for (Element value : elements.select("td")) {
            if (i % 2 == 0) { // Header
                header = value.text();
            } else { // Value
                if (header.contains("Date") || header.contains("Fiscal Year Ends") || header.contains("Most Recent Quarter")) {
                    tickerData.put(DataTransformers.translateNameToDB(header), DataTransformers.translateDateToDB(value.text()));
                } else if (header.contains("Last Split Factor")) {
                    tickerData.put(DataTransformers.translateNameToDB(header), value.text());
                } else {
                    tickerData.put(DataTransformers.translateNameToDB(header), DataTransformers.translateDataToDB(value.text()));
                }
            }
            i++;
        }
        return tickerData;
    }

    private synchronized void columnHandle(Set<String> columns) {
        if (!gettersSetters.getYahooTableCreated()) {
            gettersSetters.setYahooTableCreated(true);
            for (String column : columns) {
                TableCreatorRmver.createYahooParserColumns(gettersSetters.getConnection(),
                        DataTransformers.translateNameToDB(column));
            }
        }
    }

    @Override
    public void run() {
        String url = "https://finance.yahoo.com/quote/" + ticker + "/key-statistics";
        try {
            Document document = Jsoup.connect(url)
                    .header("Cookie",
                            "A1=d=AQABBHNyVWMCEN6v5IN43o8Dy6GruY94djoFEgABBwG_VmOEY_Qsb2UB9iMAAAcIZXJVY53QTiM&S=AQAAArMYbLxmBV3k6XR_xCfUcNo;")
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .get();

            Elements elements = document.getElementsByAttributeValue("class", "W(100%) Bdcl(c) ");
            System.out.println(url);

            // Only one thread creates/checks a table.
            s.acquire();
            synchronized (this) {
                if (!elements.isEmpty()){
                    columnHandle(tickerData(elements).keySet());
                }
            }
            s.release();

            System.out.println(tickerData(elements));

            if (listOfData.size() < 50) {
                listOfData.add(tickerData(elements));
            } else {
                TickerDataInserter.insertParsedData(gettersSetters, listOfData);
                listOfData.clear();
            }


            // Yahoo likes to put bots like this in timeout, so after 1000 requests it's better for us to stop for a little.
            if (counter == 2){
                try {
                    Thread.sleep(1000);
                    counter = 0;
                } catch (InterruptedException ignored) {
                }
            }
            System.out.println(counter);

        } catch (IOException | InterruptedException ex) {
            logger.error("Could not parse " + url + ".\n" + ex);
        }

        try {
            gettersSetters.getConnection().close();
        } catch (SQLException ignored) {
        }
    }
}
