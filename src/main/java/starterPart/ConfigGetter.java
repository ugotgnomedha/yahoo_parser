package starterPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigGetter {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static GettersSetters configGet(String configPath){
        GettersSetters gettersSetters = new GettersSetters();

        try {
            FileInputStream fis = new FileInputStream(configPath);
            Properties prop = new Properties();
            prop.load(fis);

            ///
            gettersSetters.setDbUrl(prop.getProperty("DBurl"));
            gettersSetters.setDbUser(prop.getProperty("DBuser"));
            gettersSetters.setDbPass(prop.getProperty("DBpassword"));
            gettersSetters.setTickerTable(prop.getProperty("DBtickertable"));
            ///

            fis.close();
        } catch (IOException ex) {
            logger.error("Could not parse config file.\n" + ex);
            System.exit(555);
        }
        return gettersSetters;
    }
}
