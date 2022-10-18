package dbPart;

import java.sql.Connection;
import java.sql.Statement;

public class ConnStatGetSet {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    private Statement statement;
}
