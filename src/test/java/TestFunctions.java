import dbPart.TickerGetter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestFunctions {
    private static final Logger logger = LogManager.getLogger(TestFunctions.class);

    private Connection connection;
    private Statement statement;

    private Map<Connection, Statement> connStatOpener(String url, String user, String pass) {
        Map<Connection, Statement> connStat = new HashMap<>();
        try {
            connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            logger.error("Could not open connection to daatabase.\n" + ex);
        }
        return connStat;
    }

    private void connStatClose() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            logger.error("Could not close statement/connection to database.\n" + ex);
        }
    }

    private void testTableRmv(String testTickerTable) {
        try {
            statement.executeUpdate("DROP TABLE " + testTickerTable + "");
        } catch (SQLException ex) {
            logger.error("Could not drop a test tickers table.\n" + ex);
        }
    }

    private List<String> testTableCreate(String url, String user, String pass, String testTickerTable) {
        try {
            // Open connection and statement to database.
            connStatOpener(url, user, pass);

            statement.execute("CREATE TABLE IF NOT EXISTS " + testTickerTable + "(ticker varchar PRIMARY KEY)");
            statement.executeUpdate("INSERT INTO " + testTickerTable + "(ticker) VALUES ('ALNY'), ('BAND'), " +
                    "('BRKR'), ('BUD'), ('CAH'), ('CB'), ('CBRL') ON CONFLICT (ticker) DO NOTHING");


        } catch (SQLException ex) {
            logger.error("Could not create test tickers table.\n" + ex);
        }

        return Arrays.asList("ALNY", "BAND", "BRKR", "BUD", "CAH", "CB", "CBRL");
    }

    @Test
    public void testTickerGetter() {
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "postgres";
        String pass = "gagarin11";
        String testTickerTable = "test_tickers_";

        // Create test table.
        List<String> testTickers = testTableCreate(url, user, pass, testTickerTable);

        assertEquals("Testing ticker getter failed.", testTickers, TickerGetter.getTickers(url, user, pass, testTickerTable));

        // Rmv test table.
        testTableRmv(testTickerTable);

        // Close connection/statement to database.
        connStatClose();
    }
}
