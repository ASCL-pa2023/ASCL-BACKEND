package esgi.ascl.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {

    /**
     * Get list of day occurrences between two dates
     * @param targetDay target day
     * @param startDate start date
     * @param endDate end date
     * @return list of day occurrences
     */
    public static List<LocalDate> geListOfDayOccurrences(DayOfWeek targetDay, LocalDate startDate, LocalDate endDate) {
        var result = new ArrayList<LocalDate>();
        LocalDate currentDate = startDate;

        while (currentDate.isBefore(endDate)) {
            if (currentDate.getDayOfWeek() == targetDay) {
                result.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }
        return result;
    }

}
