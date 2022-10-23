package parserPart;

import starterPart.GettersSetters;

import java.util.List;

public class ThreadCreator {
    public static void createThreads(GettersSetters gettersSetters, List<String> tickers) {

        //Thread[] thread = new Thread[10];

        Thread thread = new Thread(new YahooParser("INTC", gettersSetters));
        thread.start();
//        for (int i = 0; i < tickers.size(); i++) {
//            thread[i] = new Thread(new YahooParser(tickers.get(i), gettersSetters));
//            thread[i].start();
//        }

    }
}
