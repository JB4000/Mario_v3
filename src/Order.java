import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;


public class Order implements Serializable {

    private LocalDateTime dateTimeOrderPlaced;
    private LocalDateTime dateTimeOrderPickup;
    private DateTimeFormatter formatDK;
    private ArrayList<Pizza> pizzasOfThisOrder;
    private boolean orderIsCompleted;
    private String orderComment;
    private double totalPriceOfOrder;
    private String phoneNumberCustomer;


    public Order() {
        this.dateTimeOrderPlaced = LocalDateTime.now();
        this.dateTimeOrderPickup = null;
        this.pizzasOfThisOrder = new ArrayList<>();
        this.orderIsCompleted = false;
        this.orderComment = "Ingen kommentarer";
        this.totalPriceOfOrder = 0.0;
        this.phoneNumberCustomer = "Endnu ikke opgivet";
    }

    public LocalDateTime getDateTimeOrderPlaced() {
        return this.dateTimeOrderPlaced;
    }

    public LocalDateTime getDateTimeOrderPickup() {
        return this.dateTimeOrderPickup;
    }

    public DateTimeFormatter getFormatDK() {
        return this.formatDK;
    }

    public boolean getOrderIsCompleted() {
        return this.orderIsCompleted;
    }

        public String getOrderComment() {
        return this.orderComment;
    }

    public double getTotalPriceOfOrder() {
        return this.totalPriceOfOrder;
    }

    public String getPhoneNumberCustomer() {
        return this.phoneNumberCustomer;
    }

    public ArrayList<Pizza> getPizzasOfThisOrder() {
        return pizzasOfThisOrder;
    }

    public void setDateTimeOrderPlaced(LocalDateTime dateTimeOrderPlaced) {
        this.dateTimeOrderPlaced = dateTimeOrderPlaced;
    }

    public void setDateTimeOrderPickup(LocalDateTime dateTimeOrderPickup) {
        this.dateTimeOrderPickup = dateTimeOrderPickup;
    }

    public void setFormatDK(DateTimeFormatter formatDK) {
        this.formatDK = formatDK;
    }

    public void setOrderIsCompleted(boolean orderIsCompleted) {
        this.orderIsCompleted = orderIsCompleted;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public void setTotalPriceOfOrder(double totalPriceOfOrder) {
        this.totalPriceOfOrder = totalPriceOfOrder;
    }

    public void setPhoneNumberCustomer(String phoneNumberCustomer) {
        this.phoneNumberCustomer = phoneNumberCustomer;
    }

    public void addPizzaToOrder(Pizza pizza) {
        pizzasOfThisOrder.add(pizza);
        updateTotalPriceOrder();

    }

    public void clearPizzasOfThisOrder() {
        pizzasOfThisOrder.clear();
    }

    public void printPizzasOfOrderWithID() {
        for (int i = 0; i < pizzasOfThisOrder.size(); i++) {
            System.out.printf("[%2d] %s\n", i + 1, pizzasOfThisOrder.get(i).toString());
        }
    }

    public void sortOrderPizzaList() {
        pizzasOfThisOrder.sort(Comparator.comparingInt(Pizza::getPizzaNumber));
    }
    public void updateTotalPriceOrder() {
        totalPriceOfOrder = 0;
        for (Pizza pizza : pizzasOfThisOrder) {
            totalPriceOfOrder += pizza.getPriceOfPizza();
        }
    }

    @Override
    public String toString() {
        StringBuilder tempString = new StringBuilder();
        tempString.append(String.format("\n%-35s %s", "Tidspunkt for ordrebestilling:", Utility.formatDKDateTime(dateTimeOrderPlaced)));

        tempString.append(String.format("\n%-35s %s", "Afhentningstidspunkt:", (dateTimeOrderPickup != null) ? Utility.formatDKDateTime(dateTimeOrderPickup) : "Endnu ikke fastsat"));

        tempString.append(String.format("\n%-35s %s", "Status for ordre:", (!orderIsCompleted) ? "Igangværende" : "Afsluttet"));

        tempString.append(String.format("\n%-35s %s", "Kommentar:", orderComment));

        tempString.append(String.format("\n%-35s %s", "Ordre pris:", totalPriceOfOrder));

        tempString.append(String.format("\n%-35s %s", "Kunde telefonnummer:", phoneNumberCustomer));
        tempString.append("\n\n");
        tempString.append("Nr Pizza                        Ingredienser                                                                                   Pris   Status\n");

        for (int i = 0; i < pizzasOfThisOrder.size(); i++) {
            tempString.append(pizzasOfThisOrder.get(i).toString()).append("\n");
        }
        tempString.append("\n---------------------------------------------------------------------------------------------------------------------------------------------\n");
        return tempString.toString();
    }

    public boolean anyUnfinishedPizzasInOrder() {

        boolean flagUnfinishedPizzasThisOrder = false;

        for (int i = 0; i < pizzasOfThisOrder.size(); i++) {
            if (!pizzasOfThisOrder.get(i).getPizzaIsReadyForPickup()) {
                flagUnfinishedPizzasThisOrder = true;
            }
        }
        return flagUnfinishedPizzasThisOrder;
    }
}
