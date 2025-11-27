package violet.action.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter YYYYMMDD_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String getNowDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(YYYYMMDD_FORMATTER);
    }
}
