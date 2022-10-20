// _______       ___      .______     .___________. __    __
//|       \     /   \     |   _  \    |           ||  |  |  |
//|  .--.  |   /  ^  \    |  |_)  |   `---|  |----`|  |__|  |
//|  |  |  |  /  /_\  \   |      /        |  |     |   __   |
//|  '--'  | /  _____  \  |  |\  \----.   |  |     |  |  |  |
//|_______/ /__/     \__\ | _| `._____|   |__|     |__|  |__|

package starterPart;

import dbPart.ConnHandle;
import dbPart.TickerGetter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import parserPart.ThreadCreator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        // Get config values.
        String configPath = "/Users/darthvader/Desktop/yahoo_parser/src/main/resources/testConfig.properties";
        //String configPath = System.getProperty("log_parser_config");

        // Parse config file.
        ConfigGetter.configGet(configPath);

        ConfigGetSet configGetSet = new ConfigGetSet();

        // Get ticker list from db and parse them from yahoo.
        List<String> tickers = TickerGetter.getTickers(ConnHandle.connOpener(), configGetSet.getTickerTable());

        // Start threads for each individual ticker.
        ThreadCreator.createThreads(tickers);

        System.out.println("Number of threads " + Thread.activeCount());
    }
}