import dbPart.TickerGetter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestFunctions {
    private static final Logger logger = LogManager.getLogger(TestFunctions.class);

    @Test
    public void testTickerGetter() {
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "postgres";
        String pass = "gagarin11";
        String testTickerTable = "test_tickers_";

        // Open connection and statement to database separately
        // since this is a part of a test which is separate from main code.

        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            logger.error("Could not connect to database while testing tickerGetter.\n" + ex);
        }

        // Create test table.
        List<String> testTickers = dbPart.TableCreatorRmver.testTableCreate(statement, testTickerTable);

        assertEquals("Testing ticker getter failed.", testTickers, TickerGetter.getTickers(url, user, pass, testTickerTable));

        // Rmv test table.
        dbPart.TableCreatorRmver.dropTable(statement, testTickerTable);

        // Close separately created connection/statement to database.
        try {
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            logger.error("Could not close statement/connection to database while resting tickerGetter.\n" + ex);
        }
    }
}
