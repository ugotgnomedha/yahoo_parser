package starterPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigGetter {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void configGet(String configPath){
        ConfigGetSet configGetSet = new ConfigGetSet();

        try {
            FileInputStream fis = new FileInputStream(configPath);
            Properties prop = new Properties();
            prop.load(fis);

            ///
            configGetSet.setDbUrl(prop.getProperty("DBurl"));
            configGetSet.setDbUser(prop.getProperty("DBuser"));
            configGetSet.setDbPass(prop.getProperty("DBpassword"));
            configGetSet.setTickerTable(prop.getProperty("DBtickertable"));
            ///

            fis.close();
        } catch (IOException ex) {
            logger.error("Could not parse config file.\n" + ex);
            System.exit(555);
        }
    }
}
