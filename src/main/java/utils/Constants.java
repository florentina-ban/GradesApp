package utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Constants {
    public static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
    public static DateTimeFormatter DATE_TIME_FORMATER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static String FIRST_DAY2019 = "30-09-2019";
    public static String FIRST_DAY2020 = "04-10-2019";
}
