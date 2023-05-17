package statistic_service;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class Date {

    private LocalDate now = LocalDate.now();

    /**
     * 
     * @return the list of dates for the last seven days
     */

    public List<LocalDate> getDatesLastWeek() {
        LocalDate startDate = now.minusDays(7);
        return startDate.datesUntil(now).collect(Collectors.toList());
    }

    /**
     * 
     * @return the list of dates for the last month, last thirty days from now
     */

    public List<LocalDate> getDatesLastMonth() {
        LocalDate startDate = now.minusMonths(1);
        return startDate.datesUntil(now).collect(Collectors.toList());
    }

    /**
     * 
     * @return the list of dates for the last ninety days from now
     */

    public List<LocalDate> getDatesLastThreeMonths() {
        LocalDate startDate = now.minusMonths(3);
        return startDate.datesUntil(now).collect(Collectors.toList());
    }

    /**
     * 
     * @return the list of dates for the last three hundred and sixty five days
     */
    public List<LocalDate> getDatesLastYear() {
        LocalDate startDate = now.minusYears(1);
        return startDate.datesUntil(now).collect(Collectors.toList());
    }
    
}
