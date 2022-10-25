package parserPart;

import dbPart.TableCreatorRmver;
import starterPart.GettersSetters;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadCreator {
    public static void createThreads(GettersSetters gettersSetters, List<String> tickers) {
        // Check if yahoo_parser table exists.
        TableCreatorRmver.createYahooParserTable(gettersSetters.getConnection());

        //Start a thread for each ticker.
        ExecutorService executorService = Executors.newFixedThreadPool(10);


        for (int i = 0; i < tickers.size(); i++) {
            executorService.execute(new YahooParser(tickers.get(i), gettersSetters, i));
        }

        executorService.shutdown();
    }
}
