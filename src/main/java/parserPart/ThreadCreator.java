package parserPart;

import java.util.List;

public class ThreadCreator {
    public static void createThreads(List<String> tickers) {

        Thread[] thread = new Thread[10];

        for (int i = 0; i < tickers.size(); i++) {
            thread[i] = new Thread(new YahooParser(tickers.get(i)));
            thread[i].start();
        }


    }
}
