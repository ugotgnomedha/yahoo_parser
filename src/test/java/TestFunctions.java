import dbPart.ConnHandle;
import dbPart.TickerGetter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import parserPart.ThreadCreator;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestFunctions {
    private static final Logger logger = LogManager.getLogger(TestFunctions.class);

    @Test
    public void testRandom(){
        Map<String, String> aaa = new HashMap<>();
        aaa.put("Jack", "Black");
        aaa.put("Rock", "Big");
        System.out.println(aaa.keySet().toString().replaceAll("\\[", "").replace("]", ""));
    }

    @Test
    public void testTickerGetter() {
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "postgres";
        String pass = "gagarin11";
        String testTickerTable = "test_tickers_";

        // Open connection and statement to database separately
        // since this is a part of a test which is separate from main code.

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            logger.error("Could not connect to database while testing tickerGetter.\n" + ex);
        }

        // Create test table.
        assert connection != null;
        List<String> testTickers = dbPart.TableCreatorRmver.testTableCreate(connection, testTickerTable);

        assertEquals("Testing ticker getter failed.", testTickers, TickerGetter.getTickers(connection, testTickerTable));

        // Rmv test table.
        dbPart.TableCreatorRmver.dropTable(connection, testTickerTable);

        // Close separately created connection/statement to database.
        try {
            connection.close();
        } catch (SQLException ex) {
            logger.error("Could not close statement/connection to database while resting tickerGetter.\n" + ex);
        }
        logger.info("Test on getting tickers to parse from the database.\n" + "Passed!");
    }
}
