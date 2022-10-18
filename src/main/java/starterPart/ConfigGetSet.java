package starterPart;

public class ConfigGetSet {
    private String dbUrl;
    private String dbUser;
    private String dbPass;
    private String tickerTable;

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
}
