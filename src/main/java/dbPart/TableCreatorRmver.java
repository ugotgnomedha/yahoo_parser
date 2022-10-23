package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import starterPart.GettersSetters;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class TableCreatorRmver {
    private static final Logger logger = LogManager.getLogger(TableCreatorRmver.class);

    // Synchronized means that ONLY one thread can enter this function at a time.
    public synchronized static void createYahooParserTable(Connection connection, String column) {
        try {
            System.out.println("I'm creating a table. " + column);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS yahoo_parser()");
            ///
            switch (column) {
                case "Date", "Fiscal_Year_Ends", "Most_Recent_Quarter" -> {
                    statement.executeUpdate("ALTER TABLE yahoo_parser ADD COLUMN IF NOT EXISTS " + column + " DATE;");
                }
                default -> statement.executeUpdate("ALTER TABLE yahoo_parser ADD COLUMN IF NOT EXISTS " + column + " NUMERIC;");
            }
            ///
            statement.close();
        } catch (SQLException ex) {
            logger.error("Could not create yahoo_parser table.\n" + ex);
        }
    }

    public static void dropTable(Connection connection, String tableName) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE " + tableName + "");
            statement.close();
        } catch (SQLException ex) {
            logger.error("Could not drop a test tickers table.\n" + ex);
        }
    }

    public static List<String> testTableCreate(Connection connection, String testTickerTable) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS " + testTickerTable + "(ticker varchar PRIMARY KEY)");
            statement.executeUpdate("INSERT INTO " + testTickerTable + "(ticker) VALUES ('ALNY'), ('BAND'), " +
                    "('BRKR'), ('BUD'), ('CAH'), ('CB'), ('CBRL') ON CONFLICT (ticker) DO NOTHING");
            statement.close();
        } catch (SQLException ex) {
            logger.error("Could not create test tickers table.\n" + ex);
        }

        return Arrays.asList("ALNY", "BAND", "BRKR", "BUD", "CAH", "CB", "CBRL");
    }
}
