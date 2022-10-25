package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import starterPart.GettersSetters;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class TableCreatorRmver {
    private static final Logger logger = LogManager.getLogger(TableCreatorRmver.class);

    public static void createYahooParserColumns(Connection connection, String column) {
        try {
            Statement statement = connection.createStatement();
            System.out.println("----------");
            System.out.println(column);
            switch (column) {
                case "Date", "Fiscal_Year_Ends", "Most_Recent_Quarter" ->
                        statement.executeUpdate("ALTER TABLE yahoo_parser ADD COLUMN IF NOT EXISTS " + column + " DATE;");
                case "Last_Split_Factor" ->
                        statement.executeUpdate("ALTER TABLE yahoo_parser ADD COLUMN IF NOT EXISTS " + column + " VARCHAR;");
                default ->
                        statement.executeUpdate("ALTER TABLE yahoo_parser ADD COLUMN IF NOT EXISTS " + column + " NUMERIC;");
            }
            statement.close();
        } catch (SQLException ex){
            logger.error("Could not create columns for yahoo_parser table.\n" + ex);
        }
    }

    public static void createYahooParserTable(Connection connection) {
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet table = dbm.getTables(null, null, "yahoo_parser", null);
            Statement statement = connection.createStatement();
            if (!table.next()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS yahoo_parser(ticker VARCHAR PRIMARY KEY, upload_date DATE DEFAULT CURRENT_TIMESTAMP)");
            }
            table.close();
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
