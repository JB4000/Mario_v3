import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        run();

    }

    public static void run() {

        Scanner scanner = new Scanner(System.in);

        PizzaMenu mariosPizzabarPizzaMenu = null;

        try {
            mariosPizzabarPizzaMenu = new PizzaMenu("mariosPizzabarPizzaMenuFile.txt", "mariosPizzabarPizzaMenuIngrediensFile.txt", scanner);

        } catch (FileNotFoundException e) {
            System.err.println("Pizza Menu File Not Found Error");
            e.printStackTrace();
        }

        OrderManager mariosPizzabar = null;

        try {
            mariosPizzabar = new OrderManager(mariosPizzabarPizzaMenu, "activeOrdersOfTodayFile", "completedOrdersOfTodayFile", "ordersInThePastFile", scanner);
        } catch (ClassNotFoundException e1) {
            System.err.println("ClassNotFoundException");
        } catch (IOException e2) {
            System.err.println("IOException");
        }

        MenuAppUI menuAppMariosPizzabar = new MenuAppUI(mariosPizzabar, scanner);

        try {
            menuAppMariosPizzabar.run();
        } catch (IOException e) {
            System.err.println("IOException from running app menu");
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException from running app menu");
        }
    }
}
