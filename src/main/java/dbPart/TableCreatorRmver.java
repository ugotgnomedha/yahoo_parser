package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class TableCreatorRmver {
    private static final Logger logger = LogManager.getLogger(TableCreatorRmver.class);

    public static void dropTable(Statement statement, String tableName) {
        try {
            statement.executeUpdate("DROP TABLE " + tableName + "");
        } catch (SQLException ex) {
            logger.error("Could not drop a test tickers table.\n" + ex);
        }
    }

    public static List<String> testTableCreate(Statement statement, String testTickerTable) {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS " + testTickerTable + "(ticker varchar PRIMARY KEY)");
            statement.executeUpdate("INSERT INTO " + testTickerTable + "(ticker) VALUES ('ALNY'), ('BAND'), " +
                    "('BRKR'), ('BUD'), ('CAH'), ('CB'), ('CBRL') ON CONFLICT (ticker) DO NOTHING");
        } catch (SQLException ex) {
            logger.error("Could not create test tickers table.\n" + ex);
        }

        return Arrays.asList("ALNY", "BAND", "BRKR", "BUD", "CAH", "CB", "CBRL");
    }
}
