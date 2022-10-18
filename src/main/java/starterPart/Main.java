package starterPart;
// _______       ___      .______     .___________. __    __
//|       \     /   \     |   _  \    |           ||  |  |  |
//|  .--.  |   /  ^  \    |  |_)  |   `---|  |----`|  |__|  |
//|  |  |  |  /  /_\  \   |      /        |  |     |   __   |
//|  '--'  | /  _____  \  |  |\  \----.   |  |     |  |  |  |
//|_______/ /__/     \__\ | _| `._____|   |__|     |__|  |__|

import dbPart.TickerGetter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import parserPart.YahooParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {

        // Get config values.
        String configPath = "path/to/config";
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
        } catch (IOException ex){
            logger.error("Could not parse config file.\n" + ex);
        }


        // Get ticker list from db and parse them from yahoo.
        YahooParser.parseYahoo(TickerGetter.getTickers(configGetter.getDbUrl(),
                configGetter.getDbUser(), configGetter.getDbPass(), configGetter.getTickerTable()));

    }
}