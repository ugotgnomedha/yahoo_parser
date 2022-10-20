package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import starterPart.ConfigGetSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnHandle {
    private static final Logger logger = LogManager.getLogger(ConnHandle.class);

    public static Connection connOpener() {
        ConnGetSet connGetSet = new ConnGetSet();
        ConfigGetSet configGetSet = new ConfigGetSet();
        try {
            connGetSet.setConnection(DriverManager.getConnection(configGetSet.getDbUrl(),
                    configGetSet.getDbUser(), configGetSet.getDbPass()));
        } catch (SQLException ex) {
            logger.error("Could not open connection to database.\n" + ex);
        }

        return connGetSet.getConnection();
    }
}
