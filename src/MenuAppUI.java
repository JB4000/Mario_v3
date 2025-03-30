import java.io.IOException;
import java.util.*;

public class MenuAppUI {

    public static final String layoutFormatStringMenuLevel1 = "%-35s %-35s\n";

    private final Scanner scanner;
    OrderManager orderManager;


    public MenuAppUI(OrderManager orderManager, Scanner scanner) {
        this.scanner = scanner;
        this.orderManager = orderManager;
    }

    public void run() throws IOException, ClassNotFoundException {

        boolean exitUI = false;


        while (!exitUI) {

            orderManager.updatePizzaProductionSequence(true, true);


            System.out.println("\n******************** Hovedmenu ********************");
            System.out.printf(layoutFormatStringMenuLevel1, "1. Vis menukort", "5. Rediger menu");
            System.out.printf(layoutFormatStringMenuLevel1, "2. Opret ordre", "6. Se statistik");
            System.out.printf(layoutFormatStringMenuLevel1, "3. Find/vis ordre", "7. Afslut dagen");
            System.out.println("4. Ændre pizza status");
            System.out.println("0. Exit (Save dagens uafsluttede og afsluttede ordrer)");

            switch(Utility.getChoiceIntValidated(0, 8, false, scanner)) {
                case 0:
                    orderManager.saveActiveOrdersOfToday();
                    orderManager.saveCompletedOrdersOfToday();

                    exitUI = true;
                    break;

                case 1:
                    orderManager.printSortedPizzaMenu();
                    break;

                case 2:
                    orderManager.editOrder(new Order());
                    orderManager.saveActiveOrdersOfToday();

                    break;

                case 3:
                    menuFindOrEditOrders();
                    break;

                case 4:
                    orderManager.markPizzaOnProductionSequenceReady();
                    break;

                case 5:
                    menuEditPizzaMenuAndIngredients();
                    break;

                case 6:
                    menuStatistics();

                    break;

                case 7:
                    if(orderManager.endTodayAndClearForTomorrow()) {
                        exitUI = true;
                    }
                    break;

                case 8:
                    menuDeveloper();
                    break;

                default:
                    System.err.println("switch fejl");
                    break;
            }
        }
    }


    public void menuFindOrEditOrders() throws IOException, ClassNotFoundException {
        boolean goBack = false;

        while (!goBack) {

            System.out.println("\n1. Udlever ordre ud fra telefonnummer");
            System.out.println("2. Vis telefonnumre på ordre der er klar til afhentning");
            System.out.println("3. Se alle uafsluttede ordre for dagen");
            System.out.println("4. Se alle afsluttede ordre for dagen som endnu ikke er foert til historik");
            System.out.println("5. Se alle ordre i historik");
            System.out.println("0. Exit");

            switch (Utility.getChoiceIntValidated(0, 5, false, scanner)) {
                case 0:
                    goBack = true;
                    break;

                case 1:
                    orderManager.updatePizzaProductionSequence(false, true);
                    orderManager.getPaymentAndCompleteOrder();
                    break;

                case 2:
                    orderManager.updatePizzaProductionSequence(false, true);
                    break;

                case 3:
                    orderManager.printActiveOrdersOfTodaySorted();
                    break;

                case 4:
                    orderManager.printCompletedOrdersOfTodayNotYetMovedToPast();
                    break;

                case 5:
                    orderManager.printAllOrdersInThePastSorted();
                    break;

                default:
                    System.err.println("switch fejl");
                    break;
            }
        }
    }

    public void menuEditPizzaMenuAndIngredients() {

        boolean flagContinue = false;
        boolean flagStepBackInMenu = false;

        while (true) {
            System.out.println("1: Rediger menukort");
            System.out.println("2: Rediger ingredienser");
            System.out.println("0: Vend tilbage til hovedmenu");
            System.out.println("Vælg en mulighed");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        while (true) {
                            flagContinue = false;

                            System.out.println("1: Tilføj pizza");
                            System.out.println("2: Slet pizza");
                            System.out.println("3: Vis menukort");
                            System.out.println("0: Tilbage til rediger menu");
                            System.out.println("Vælg en mulighed");

                            try {
                                choice = scanner.nextInt();

                                switch (choice) {
                                    case 1:
                                        orderManager.getPizzaMenu().createNewPizzaForMenu();

                                        break;

                                    case 2:
                                        orderManager.getPizzaMenu().deletePizzaFromPizzaMenu();

                                        break;

                                    case 3:
                                        orderManager.getPizzaMenu().printSortedPizzaMenu();
                                        break;

                                    case 0:
                                        flagContinue = true;
                                        break;

                                    default:
                                        System.err.println("Indtast venligst en gyldig mulighed");
                                        break;
                                }
                            } catch (Exception e) {
                                System.err.println("Indtast venligst en gyldig mulighed");
                                scanner.nextLine();
                            }

                            if (flagContinue) {
                                break;
                            }
                        }
                        break;
                    case 2:
                        scanner.nextLine();
                        menuEditListOfAvailableIngredients();
                        break;

                    case 0:
                        flagStepBackInMenu = true;
                        break;

                    default:
                        System.out.println("Indtast venligst en gyldig mulighed");
                        break;
                }

            } catch (Exception e) {
                System.err.println("Indtast venligst et tal");
                scanner.nextLine();
            }

            if (flagStepBackInMenu) {
                scanner.nextLine();
                break;
            }
        }
    }


    public void menuEditListOfAvailableIngredients() {

        orderManager.getPizzaMenu().printListOfAvailableIngredientsSorted();

        boolean redigerVidere = false;

        while (!redigerVidere) {
            System.out.println("\n1. Tilføj ny ingrediens");
            System.out.println("2. Slet ingrediens fra listen");
            System.out.println("3. Vis ingrediensliste");
            System.out.println("0. Exit");

            switch (Utility.getChoiceIntValidated(0, 3, false, scanner)) {

                case 0:
                    redigerVidere = true;
                    break;

                case 1:
                    orderManager.getPizzaMenu().addIngredientToListOfAvailable();
                    break;

                case 2:
                    orderManager.getPizzaMenu().deleteIngredientFromListOfAvailable();
                    break;

                case 3:
                    orderManager.getPizzaMenu().printListOfAvailableIngredientsSorted();
                    break;
            }
        }
    }

    public void menuStatistics() throws IOException, ClassNotFoundException {

        boolean flagStepBackInMenu = false;

        while (!flagStepBackInMenu) {
            System.out.println("1. Se omsætning for dagens afsluttede ordre og ");
            System.out.println("   dagens salgstal for pizzaer ud fra afsluttede ordre");
            System.out.println();
            System.out.println("2. Se totale omsætning per dato i historik over tid inklusiv dagens afsluttede ordrer");
            System.out.println("   og salgstal for pizzaer i hele perioden i historik inklusiv dagens afsluttede ordrer");
            System.out.println("0. Exit");

            switch(Utility.getChoiceIntValidated(0, 2, false, scanner)) {

                case 1:
                    orderManager.printStatisticsOfToday();
                    break;

                case 2:
                    orderManager.printStatisticsOfPast();
                    break;

                case 0:
                    flagStepBackInMenu = true;
                    break;
            }
        }
    }

    public void menuDeveloper() throws IOException, ClassNotFoundException {

        boolean flagStepBackInMenu = false;

        while (!flagStepBackInMenu) {
            System.out.println("1. Tøm uafsluttede ordre liste for ordre og clear fil");
            System.out.println("2. Tøm dagens afsluttede ordre liste for ordre og clear fil");
            System.out.println("3. Gem uafsluttede ordre til egen backup fil");
            System.out.println("4. Hent uafsluttede ordre fra egen backup fil");
            System.out.println("5. Gem dagens afsluttede ordre til egen backup fil");
            System.out.println("6. Hent dagens afsluttede ordre fra egen backup fil");
            System.out.println("7. Goer samtlige pizzaer i uafsluttede ordre klar");
            System.out.println("8. Flyt ordre fra uafsluttede til afsluttede ordre");
            System.out.println("9. Aendre dato paa alle dagens afsluttede ordre");
            System.out.println("10. Flyt afsluttede ordre til ordre historik og clear afsluttede array og fil");
            System.out.println("0. Exit");

            switch (Utility.getChoiceIntValidated(0, 10, false, scanner)) {

                case 1:
                    orderManager.clearActiveOrdersOfToday();
                    orderManager.clearActiveOrdersOfTodayFile();

                    break;

                case 2:
                    orderManager.clearCompletedOrdersOfToday();
                    orderManager.clearCompletedOrdersOfTodayFile();

                    break;

                case 3:
                    orderManager.saveActiveOrdersOfToday();

                    break;

                case 4:
                    orderManager.loadActiveOrdersOfToday();
                    break;

                case 5:
                    orderManager.saveCompletedOrdersOfToday();

                    break;

                case 6:
                    orderManager.loadCompletedOrdersOfToday();
                    break;

                case 7:
                    orderManager.setAllPizzasInActiveOrdersOfTodayAsReady();
                    break;

                case 8:
                    orderManager.moveAllActiveOrdersOfTodayToCompletedOrdersOfToday();
                    break;

                case 9:
                    orderManager.changeDateOnAllCompletedOrders();
                    break;

                case 10:
                    orderManager.saveCompletedOrdersOfTodayToOrdersInThePast();
                    orderManager.clearCompletedOrdersOfToday();
                    orderManager.clearCompletedOrdersOfTodayFile();
                    break;

                case 0:
                    flagStepBackInMenu = true;
                    break;

                default:
                    System.err.println("switch error");
                    break;
            }
        }
    }
}
