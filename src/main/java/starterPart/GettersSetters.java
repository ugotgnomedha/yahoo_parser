package starterPart;

import java.sql.Connection;

public class GettersSetters {
    private String dbUrl;
    private String dbUser;
    private String dbPass;
    private String tickerTable;
    private Connection connection;
    private Boolean yahooTableCreated = false;
    private Integer threadPoolSize;

    public Integer getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(Integer threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public Boolean getYahooTableCreated() {
        return yahooTableCreated;
    }

    public void setYahooTableCreated(Boolean yahooTableCreated) {
        this.yahooTableCreated = yahooTableCreated;
    }

    public String getTickerTable() {
        return tickerTable;
    }

    public void setTickerTable(String tickerTable) {
        this.tickerTable = tickerTable;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }

    public void setDbPass(String dbPass) {
        this.dbPass = dbPass;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
