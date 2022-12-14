package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TickerGetter {
    private static final Logger logger = LogManager.getLogger(TickerGetter.class);

    public static List<String> getTickers(Connection connection, String tickerTable) {

        List<String> tickers = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ticker FROM " + tickerTable + "");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tickers.add(resultSet.getString(1));
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException ex) {
            logger.error("Could not get ticker list from database.\n" + ex);
        }

        return tickers;
    }
}
