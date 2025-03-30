import java.io.Serializable;

public class Pizza implements Serializable {

    private int pizzaNumber;
    private String pizzaName;
    private double priceOfPizza;
    private String[] ingredientsOfPizza;
    private String pizzaComment;
    private boolean pizzaIsReadyForPickup;

    public Pizza(int pizzaNumber, String pizzaName, double priceOfPizza, String[] ingredientsOfPizza, String pizzaComment) {

        this.pizzaNumber = pizzaNumber;
        this.pizzaName = pizzaName;
        this.priceOfPizza = priceOfPizza;
        this.ingredientsOfPizza = ingredientsOfPizza;
        this.pizzaComment = pizzaComment;
        this.pizzaIsReadyForPickup = false;
    }

    public Pizza(Pizza that) {

        this.pizzaNumber = that.pizzaNumber;
        this.pizzaName = that.pizzaName;
        this.priceOfPizza = that.priceOfPizza;
        this.ingredientsOfPizza = new String[that.ingredientsOfPizza.length];

        for (int i = 0; i < that.ingredientsOfPizza.length; i++) {
            this.ingredientsOfPizza[i] = (that.ingredientsOfPizza[i]);
        }

        this.pizzaComment = that.pizzaComment;
        this.pizzaIsReadyForPickup = false;
    }

    public boolean getPizzaIsReadyForPickup() {
        return this.pizzaIsReadyForPickup;
    }

    public void setPizzaIsReadyForPickup(boolean pizzaIsReadyForPickup) {
        this.pizzaIsReadyForPickup = pizzaIsReadyForPickup;
    }

    public int getPizzaNumber() {
        return pizzaNumber;
    }

    public void setPizzaNumber(int pizzaNumber) {
        this.pizzaNumber = pizzaNumber;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public double getPriceOfPizza() {
        return priceOfPizza;
    }

    public void setPriceOfPizza(int priceOfPizza) {
        this.priceOfPizza = priceOfPizza;
    }

    public String[] getIngredientsOfPizza() {
        return ingredientsOfPizza;
    }

    public void setIngredientsOfPizza(String[] ingredients) {
        this.ingredientsOfPizza = ingredients;
    }

    public String getPizzaComment() {
        return pizzaComment;
    }

    public void setPizzaComment(String pizzaComment) {
        this.pizzaComment = pizzaComment;
    }

    public Pizza copyOf() {
        return new Pizza(this);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append(String.format("%2d: %s", pizzaNumber, pizzaName));

        while (0 < 32 - string.length()) {
            string.append(" ");
        }

        for (int i = 0; i < ingredientsOfPizza.length; i++) {
            string.append(ingredientsOfPizza[i]);

            if (i < ingredientsOfPizza.length - 2) {
                string.append(", ");
            } else if (i < ingredientsOfPizza.length - 1) {
                string.append(" og ");
            }
        }

        while (125 - string.length() > 0) {
            string.append(".");
        }

        if (priceOfPizza < 100) {
            string.append(".");
        }

        string.append(String.format("%3.2f kr.", priceOfPizza));

        string.append((pizzaIsReadyForPickup) ? "  Klar" : "");

        if (!pizzaComment.equals("null")) {
            string.append("\n       Kommentarer: " + pizzaComment);
        }

        return string.toString();
    }
}
