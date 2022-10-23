package parserPart;

public class DataTransformers {
    public static String translateDateToDB(String date) {
        String transformed_date = "";
        String month = "";

        if (date.contains("Jan")) {
            month = "1";
        } else if (date.contains("Feb")) {
            month = "2";
        } else if (date.contains("Mar")) {
            month = "3";
        } else if (date.contains("Apr")) {
            month = "4";
        } else if (date.contains("May")) {
            month = "5";
        } else if (date.contains("Jun")) {
            month = "6";
        } else if (date.contains("Jul")) {
            month = "7";
        } else if (date.contains("Aug")) {
            month = "8";
        } else if (date.contains("Sep")) {
            month = "9";
        } else if (date.contains("Oct")) {
            month = "10";
        } else if (date.contains("Nov")) {
            month = "11";
        } else if (date.contains("Dec")) {
            month = "12";
        }
        if (date.equals("N/A")) {
            transformed_date = "NULL";
        } else {
            transformed_date = date.substring(date.indexOf(", ") + 2) + "-" + month + "-" + date.substring(date.indexOf(" ") + 1, date.indexOf(","));
        }
        return transformed_date;
    }

    public static String translateDataToDB(String data) {
        float numbers = Float.parseFloat(data.substring(0, data.length() - 1));
        String transformed_data = "";
        if (data.contains("K")) {
            transformed_data = String.valueOf(numbers * 1000);
        } else if (data.contains("M")) {
            transformed_data = String.valueOf(numbers * 1000000);
        } else if (data.contains("B")){
            transformed_data = String.valueOf(numbers * 1000000000);
        } else if (data.contains("T")){
            transformed_data = String.valueOf(numbers * 1000000000000L);
        }
        return transformed_data;
    }

    public static String translateNameToDB(String header) {
        header = header.replace(" ", "_");
        header = header.replace("(", "");
        header = header.replace(")", "");
        header = header.replace(".", "");
        header = header.replace("/", "_");
        header = header.replace("-", "_");
        header = header.replace("&", "_and_");
        header = header.replace("%", "percent");
        header = header.replace(",", "");
        header = header.replace("1", "one_");
        header = header.replace("2", "two_");
        header = header.replace("3", "three_");
        header = header.replace("4", "four_");
        header = header.replace("5", "five_");
        header = header.replace("6", "six_");
        header = header.replace("7", "seven_");
        header = header.replace("8", "eight_");
        header = header.replace("9", "nine_");
        return header;
    }
}
