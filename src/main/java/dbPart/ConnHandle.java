package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import starterPart.GettersSetters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnHandle {
    private static final Logger logger = LogManager.getLogger(ConnHandle.class);

    public static Connection connOpener(GettersSetters gettersSetters) {
        try {
            gettersSetters.setConnection(DriverManager.getConnection(gettersSetters.getDbUrl(),
                    gettersSetters.getDbUser(), gettersSetters.getDbPass()));
        } catch (SQLException ex) {
            logger.error("Could not open connection to database.\n" + ex);
        }

        return gettersSetters.getConnection();
    }
}
