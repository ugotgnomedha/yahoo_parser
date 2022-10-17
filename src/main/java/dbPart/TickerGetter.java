package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import starterPart.ConfigGetter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TickerGetter {
    private static final Logger logger = LogManager.getLogger(TickerGetter.class);

    public static List<String> getTickers(ConfigGetter configGetter) {

        List<String> tickers = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(configGetter.getDbUrl(), configGetter.getDbUser(), configGetter.getDbPass());

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ticker FROM tickers");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tickers.add(resultSet.getString(1));
            }

            resultSet.close();
        } catch (SQLException ex) {
            logger.error("Could not get ticker list from database.\n" + ex);
        }

        return tickers;
    }
}
