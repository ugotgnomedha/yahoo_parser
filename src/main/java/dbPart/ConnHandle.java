package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnHandle {
    private static final Logger logger = LogManager.getLogger(ConnHandle.class);

    public static Connection connOpener(String DBurl, String DBuser, String DBpass) {
        ConnGetSet connGetSet = new ConnGetSet();
        try {
            connGetSet.setConnection(DriverManager.getConnection(DBurl, DBuser, DBpass));
        } catch (SQLException ex) {
            logger.error("Could not open connection to database.\n" + ex);
        }
        return connGetSet.getConnection();
    }
}
