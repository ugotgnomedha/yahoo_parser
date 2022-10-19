// _______       ___      .______     .___________. __    __
//|       \     /   \     |   _  \    |           ||  |  |  |
//|  .--.  |   /  ^  \    |  |_)  |   `---|  |----`|  |__|  |
//|  |  |  |  /  /_\  \   |      /        |  |     |   __   |
//|  '--'  | /  _____  \  |  |\  \----.   |  |     |  |  |  |
//|_______/ /__/     \__\ | _| `._____|   |__|     |__|  |__|

package starterPart;

import dbPart.TickerGetter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import parserPart.ThreadCreator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        // Get config values.
        String configPath = "/Users/darthvader/Desktop/yahoo_parser/src/main/resources/testConfig.properties";
        //String configPath = System.getProperty("log_parser_config");

        ConfigGetSet configGetter = new ConfigGetSet();

        try {
            FileInputStream fis = new FileInputStream(configPath);
            Properties prop = new Properties();
            prop.load(fis);

            ///
            configGetter.setDbUrl(prop.getProperty("DBurl"));
            configGetter.setDbUser(prop.getProperty("DBuser"));
            configGetter.setDbPass(prop.getProperty("DBpassword"));
            configGetter.setTickerTable(prop.getProperty("DBtickertable"));
            ///

            fis.close();
        } catch (IOException ex) {
            logger.error("Could not parse config file.\n" + ex);
            System.exit(555);
        }

        // Get ticker list from db and parse them from yahoo.
        List<String> tickers = TickerGetter.getTickers(configGetter.getDbUrl(),
                configGetter.getDbUser(), configGetter.getDbPass(), configGetter.getTickerTable());

        // Start threads for each individual ticker.
        ThreadCreator.createThreads(tickers);

        System.out.println("Number of threads " + Thread.activeCount());
    }
}