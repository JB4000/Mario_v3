import java.util.*;

public class ComparatorPizzaFrequency implements Comparator<Map.Entry<String, Integer>> {

    public int compare(Map.Entry<String, Integer> freq1, Map.Entry<String, Integer> freq2) {
        if (freq2.getValue().equals(freq1.getValue())) {
            return freq1.getKey().compareTo(freq2.getKey());
        }
        return freq2.getValue().compareTo(freq1.getValue());
    }
}

