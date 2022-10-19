package dbPart;

import java.sql.Connection;

public class ConnGetSet {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
