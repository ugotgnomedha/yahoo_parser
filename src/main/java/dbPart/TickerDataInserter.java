package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import starterPart.GettersSetters;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class TickerDataInserter {

    private static final Logger logger = LogManager.getLogger(TickerDataInserter.class);
    private static List<Map<String, String>> listOfData = new ArrayList<>(50);
    private static int counter = 0;

    private static List<String> getValuesToList(Map<String, String> map){
        List<String> result = new ArrayList<>();
        Collection<String> collection = map.values();

        for (int i = 0; i < collection.size(); i ++){
            result.add((String) collection.toArray()[i]);
        }

        return result;
    }

    private static String insertSQLBuilder(Map<String, String> map){
        String result;
        if (map.keySet().size() > 1){
            result = "INSERT INTO yahoo_parser(" + cleanKeySet(map.keySet()) + ") VALUES(";
            result = result + ("?,".repeat(map.keySet().size()));
            result = result.substring(0, result.length() - 1);
            result = result + ");";
        } else {
            result = "";
        }
        return result;
    }

    private static String cleanKeySet(Set<String> keys){
        return keys.toString().replaceAll("\\[", "").replace("]", "");
    }

    private static void insertBulk(GettersSetters gettersSetters) {
        try {
            gettersSetters.getConnection().setAutoCommit(false);
            PreparedStatement preparedStatement = null;
            for (Map<String, String> listOfDatum : listOfData) {
                String insertSQL = insertSQLBuilder(listOfDatum);
                if (!insertSQL.equals("")){
                    preparedStatement = gettersSetters.getConnection().prepareStatement(insertSQL);
                    List<String> values = getValuesToList(listOfDatum);
                    for (int i = 0; i < values.size(); i ++) {
                        String value = values.get(i);
                        preparedStatement.setString(i+1, value);
                    }
                    preparedStatement.addBatch();
                    System.out.println(preparedStatement);
                }
            }
            assert preparedStatement != null;
            preparedStatement.executeBatch();
            gettersSetters.getConnection().commit();
            gettersSetters.getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            logger.error("Error occurred during bulk insert into yahoo_parser\n" + ex );
        }
    }

    public static void insertParsedData(GettersSetters gettersSetters, Map<String, String> data) {
        if (counter == 1000) {
            try {
                System.out.println("Sleeping 8 minutes");
                Thread.sleep(480000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter = 0;
        }
        System.out.println(counter);
        System.out.println("Array size: " + listOfData.size());
        if (listOfData.size() < 51) {
            listOfData.add(data);
        } else {
            insertBulk(gettersSetters);
            listOfData.clear();
        }
        counter++;
    }
}
