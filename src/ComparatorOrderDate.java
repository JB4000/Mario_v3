import java.util.Comparator;

public class ComparatorOrderDate implements Comparator<Order> {

    public int compare(Order order1, Order order2) {
        return order1.getDateTimeOrderPlaced().compareTo(order2.getDateTimeOrderPlaced());

    }
}
