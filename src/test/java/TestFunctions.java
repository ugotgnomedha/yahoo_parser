import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import starterPart.ConfigGetter;
import starterPart.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestFunctions {
    private static final Logger logger = LogManager.getLogger(TestFunctions.class);

    @Test
    public void testTickerGetter() {
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "postgres";
        String pass = "gagarin11";

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE tickers_test(ticker varchar PRIMARY KEY)");
            statement.execute("INSERT INTO tickers_test(ticker) VALUES ('ALNY'), ('BAND'), ('BRKR'), ('BUD'), ('CAH'), ('CB'), ('CBRL')");

            statement.close();
            connection.close();
        } catch (SQLException ex) {
            logger.error("Testing tickerGetter failed.\n" + ex);
        }
    }
}
