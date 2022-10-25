// _______       ___      .______     .___________. __    __
//|       \     /   \     |   _  \    |           ||  |  |  |
//|  .--.  |   /  ^  \    |  |_)  |   `---|  |----`|  |__|  |
//|  |  |  |  /  /_\  \   |      /        |  |     |   __   |
//|  '--'  | /  _____  \  |  |\  \----.   |  |     |  |  |  |
//|_______/ /__/     \__\ | _| `._____|   |__|     |__|  |__|

package starterPart;

import dbPart.ConnHandle;
import dbPart.TickerGetter;
import parserPart.ThreadCreator;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Get config values.
        String configPath = "/Users/darthvader/Desktop/yahoo_parser/src/main/resources/testConfig.properties";
        //String configPath = System.getProperty("log_parser_config");

        // Parse config file.
        GettersSetters gettersSetters = ConfigGetter.configGet(configPath);

        // Get ticker list from db and parse them from yahoo.
        List<String> tickers = TickerGetter.getTickers(ConnHandle.connOpener(gettersSetters), gettersSetters.getTickerTable());

        // Start threads for each individual ticker.
        ThreadCreator.createThreads(gettersSetters, tickers);

        System.out.println("Number of threads " + Thread.activeCount());
    }
}