package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ConnStatHandle {
    private static final Logger logger = LogManager.getLogger(ConnStatHandle.class);

    private Map<Connection, Statement> connStatOpener(String DBurl, String DBuser, String DBpass) {

        ConnStatGetSet connStatGetSet = new ConnStatGetSet();
        Map<Connection, Statement> connStat = new HashMap<>();

        try {
            connStatGetSet.setConnection(DriverManager.getConnection(DBurl, DBuser, DBpass));
            connStatGetSet.setStatement(connStatGetSet.getConnection().createStatement());
        } catch (SQLException ex) {
            logger.error("Could not open connection to daatabase.\n" + ex);
        }
        return connStat;
    }

    private void connStatClose() {
        ConnStatGetSet connStatGetSet = new ConnStatGetSet();
        try {
            connStatGetSet.getStatement().close();
            connStatGetSet.getConnection().close();
        } catch (SQLException ex) {
            logger.error("Could not close statement/connection to database.\n" + ex);
        }
    }
}
