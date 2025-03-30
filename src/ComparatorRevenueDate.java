import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;

public class ComparatorRevenueDate implements Comparator<Map.Entry<LocalDate, Double>> {

    public int compare(Map.Entry<LocalDate, Double> date1, Map.Entry<LocalDate, Double> date2) {
        return date2.getKey().compareTo(date1.getKey());
    }
}


