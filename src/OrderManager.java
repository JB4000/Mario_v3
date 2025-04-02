import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class OrderManager {

    private final Scanner scanner;

    private final FileByteManager activeOrdersOfTodayFile;
    private final FileByteManager completedOrdersOfTodayFile;
    private final FileByteManager ordersInThePastFile;

    private ArrayList<Order> activeOrdersOfToday;
    private ArrayList<Order> completedOrdersOfToday;
    private ArrayList<Order> ordersInThePast;

    private ArrayList<Pizza> pizzaProductionSequence;

    private PizzaMenu pizzaMenu;


    public OrderManager(PizzaMenu pizzaMenu, String fileNameActiveOrdersOfToday, String fileNameCompletedOrdersOfToday, String fileNameOrdersInThePast, Scanner scanner) throws IOException, ClassNotFoundException {
        this.pizzaMenu = pizzaMenu;

        this.activeOrdersOfTodayFile = new FileByteManager(fileNameActiveOrdersOfToday, false);
        this.completedOrdersOfTodayFile = new FileByteManager(fileNameCompletedOrdersOfToday, false);
        this.ordersInThePastFile = new FileByteManager(fileNameOrdersInThePast, true);

        this.scanner = scanner;

        this.activeOrdersOfToday = new ArrayList<>();
        this.activeOrdersOfToday = this.activeOrdersOfTodayFile.readArrayListFromFile();

        this.completedOrdersOfToday = new ArrayList<>();
        this.completedOrdersOfToday = this.completedOrdersOfTodayFile.readArrayListFromFile();

        this.ordersInThePast = new ArrayList<>();

        this.pizzaProductionSequence = new ArrayList<>();

    }

    public ArrayList<Order> getActiveOrdersOfToday() {
        return activeOrdersOfToday;
    }

    public PizzaMenu getPizzaMenu() {
        return this.pizzaMenu;
    }

    public void clearActiveOrdersOfToday() {
        activeOrdersOfToday.clear();
    }

    public void clearActiveOrdersOfTodayFile() throws IOException {
        activeOrdersOfTodayFile.clearByteFile();
    }

    public boolean isActiveOrdersOfTodayEmpty() {
        return activeOrdersOfToday.isEmpty();
    }

    public void loadActiveOrdersOfToday() throws IOException, ClassNotFoundException {
        activeOrdersOfTodayFile.readArrayListFromFile();
    }

    public void saveActiveOrdersOfToday() throws IOException {
        if (!isActiveOrdersOfTodayEmpty()) {
            activeOrdersOfTodayFile.writeArrayListToFile(activeOrdersOfToday);
        } else {
            clearActiveOrdersOfTodayFile();
        }
    }

    public void clearCompletedOrdersOfTodayFile() throws IOException {
        completedOrdersOfTodayFile.clearByteFile();
    }

    public void clearCompletedOrdersOfToday() {
        completedOrdersOfToday.clear();
    }

    public boolean isCompletedOrdersOfTodayEmpty() {
        return completedOrdersOfToday.isEmpty();
    }
    public void loadCompletedOrdersOfToday() throws IOException, ClassNotFoundException {
        completedOrdersOfTodayFile.readArrayListFromFile();
    }

    public void saveCompletedOrdersOfToday() throws IOException {
        if (!isCompletedOrdersOfTodayEmpty()) {
            completedOrdersOfTodayFile.writeArrayListToFile(completedOrdersOfToday);
        } else {
            clearCompletedOrdersOfTodayFile();
        }
    }

    public void saveCompletedOrdersOfTodayToOrdersInThePast() throws IOException {
        if (!isCompletedOrdersOfTodayEmpty()) {
            ordersInThePastFile.writeArrayListToFile(completedOrdersOfToday);
        }
    }

    public void clearOrdersInThePastFile() throws IOException {
        ordersInThePastFile.clearByteFile();
    }

    public boolean isOrdersInThePastEmpty() {
        return ordersInThePast.isEmpty();
    }

    public void loadOrdersInThePast() throws IOException, ClassNotFoundException {
        ordersInThePast = ordersInThePastFile.readArrayListFromFile();
    }

    public void saveOrdersInThePast() throws IOException {
        ordersInThePastFile.writeArrayListToFile(ordersInThePast);
    }

    public void clearOrdersInThePast() {
        ordersInThePast.clear();
    }

    public void clearPizzaProductionSequence() {
        pizzaProductionSequence.clear();
    }

    public boolean isPizzaProductionSequenceEmpty() {
        return pizzaProductionSequence.isEmpty();
    }

    public void printSortedPizzaMenu() {
        pizzaMenu.printSortedPizzaMenu();
    }

    public void editOrder(Order order) {

        boolean orderAccept = false;
        boolean cancel = false;

        int[][] userChoiceOfPizza;

        int numberOfEqualPizzas;

        String commentPizza;
        String commentOrder;
        String phoneNumberCustomer;

        Pizza tempPizza;
        Pizza choosenPizza;

        int tempChoice;

        LocalDateTime dateTimeOrderPlacedValidated;

        while (!orderAccept) {

            System.out.println("\n1. Tilføj pizza til ordre");
            System.out.println("2. Fjern en pizza fra ordre");
            System.out.println("3. Fjern samtlige pizza fra ordre");
            System.out.println();
            System.out.println("4. Tilføj/ændre kommentar til enkelte pizzaer");

            System.out.println("5. Tilføj tlf nummer til afhentning");
            System.out.println("6. Tilføj/ret afhentningstidspunkt");
            System.out.println("7. Tilføj kommentar til samlet ordre");
            System.out.println();
            System.out.println("8. Sæt ordren i gang");
            System.out.println();
            System.out.println();
            System.out.println("0. Exit uden at gennemføre ordre");


            switch (Utility.getChoiceIntValidated(0, 8, false, scanner)) {
                case 0:


                    orderAccept = true;
                    cancel = true;
                    break;

                case 1:
                    printSortedPizzaMenu();

                    userChoiceOfPizza = Utility.getArrayChoicesOfPizzasThatExists(pizzaMenu, scanner);

                    if (userChoiceOfPizza.length == 1 && userChoiceOfPizza[0][1] == 1) {

                        choosenPizza = pizzaMenu.getPizzaMenuList().get(Utility.getPizzaMenuListIndexFromPizzaNumber(pizzaMenu, userChoiceOfPizza[0][0])).copyOf();

                        System.out.println("Tilføj kommentar til pizza. Max 40 karakterer (Enter, hvis ingen kommentarer:");

                        commentPizza = Utility.getTextValidatedLength(40, true, scanner);

                        if (!commentPizza.isEmpty()) {
                            choosenPizza.setPizzaComment(commentPizza);
                        }

                        order.addPizzaToOrder(choosenPizza);

                    } else {

                        for (int y = 0; y < userChoiceOfPizza.length; y++) {

                            choosenPizza = pizzaMenu.getPizzaMenuList().get(Utility.getPizzaMenuListIndexFromPizzaNumber(pizzaMenu, userChoiceOfPizza[y][0])).copyOf();

                            for (int i = 0; i < userChoiceOfPizza[y][1]; i++) {
                                order.addPizzaToOrder(new Pizza(choosenPizza));
                            }
                        }
                    }

                    order.sortOrderPizzaList();

                    order.updateTotalPriceOrder();

                    break;

                case 2:

                    if (order.getPizzasOfThisOrder().isEmpty()) {
                        System.out.println("Du har ikke tilføjet pizza til ordren");
                    } else {
                        order.printPizzasOfOrderWithID();

                        System.out.println("Enter [nummer] på pizza som du vil slette fra ordre (0 for exit)");

                        tempChoice = Utility.getChoiceIntValidated(0, order.getPizzasOfThisOrder().size(), false, scanner);

                        if (tempChoice != 0) {
                            order.getPizzasOfThisOrder().remove(tempChoice - 1);
                            order.updateTotalPriceOrder();

                        }
                    }

                    break;

                case 3:
                    System.out.println("Er du sikker på at du vil fjerne alle pizzaerne fra denne ordre?  (1)NEJ (2)JA");

                    if (Utility.getChoiceIntValidated(1, 2, false, scanner) == 2) {
                        order.clearPizzasOfThisOrder();
                    }

                    order.updateTotalPriceOrder();
                    break;

                case 4:
                    if (order.getPizzasOfThisOrder().isEmpty()) {
                        System.out.println("Du har ikke tilføjet pizza til ordren");
                    } else {

                        order.printPizzasOfOrderWithID();

                        System.out.println("Enter [nummer] på pizza som du vil ændre kommentar på (0 for exit)");

                        tempChoice = Utility.getChoiceIntValidated(0, order.getPizzasOfThisOrder().size(), false, scanner);

                        if (tempChoice != 0) {
                            System.out.println("Tilføj kommentar til pizza. Max 40 karakterer (Enter, hvis ingen kommentarer:");

                            commentPizza = Utility.getTextValidatedLength(40, true, scanner);

                            order.getPizzasOfThisOrder().get(tempChoice - 1).setPizzaComment(commentPizza);
                        }
                    }
                    break;

                case 5:
                    System.out.printf("Dette er det nuværende registrerede tlf nummer:\n%s\n", order.getPhoneNumberCustomer());
                    System.out.println("Indtast et dansk telefonnummer (Enter for exit):");

                    phoneNumberCustomer = Utility.getPhoneNumberDKValidated(true, scanner);

                    if (!phoneNumberCustomer.isEmpty()) {
                        order.setPhoneNumberCustomer(phoneNumberCustomer);
                    }
                    break;

                case 6:
                    if (order.getDateTimeOrderPickup() != null) {
                        System.out.printf("Nuværende afhentning er angivet til: %s \n", order.getDateTimeOrderPickup().format(Utility.formatDKTime));
                    }

                    dateTimeOrderPlacedValidated = Utility.getTimeLaterValidated(true, scanner);

                    order.setDateTimeOrderPickup(dateTimeOrderPlacedValidated);
                    break;

                case 7:
                    if (!order.getOrderComment().isEmpty()) {
                        System.out.printf("Nuværende kommentar til ordren er:\n%s\n\n", order.getOrderComment());
                    }

                    System.out.println("Indtast en kommentar til den samlede ordre (max 80 karakterer. Enter for exit ændringer): ");

                    commentOrder = Utility.getTextValidatedLength(80, true, scanner);

                    if (!commentOrder.isEmpty()) {
                        order.setOrderComment(commentOrder);
                    }
                    break;

                case 8:
                    if (order.getPizzasOfThisOrder().isEmpty()) {
                        System.err.println("Du mangler at tilføje pizza. Enter for at komme tilbage");

                        scanner.nextLine();
                        continue;

                    } else if (order.getPhoneNumberCustomer().equals("Endnu ikke opgivet")) {
                        System.err.println("Du har ikke registreret tlf nummer. Enter for at komme tilbage");

                        scanner.nextLine();
                        continue;

                    } else if (order.getDateTimeOrderPickup() == null) {
                        System.out.println("Du har ikke angivet afhentningstidspunkt");
                        System.out.println("(1) for hurtigst muligt (2) hvis det er en fejl");

                        if (Utility.getChoiceIntValidated(1, 2, false, scanner) == 1) {
                            order.setDateTimeOrderPickup(LocalDateTime.now());
                            orderAccept = true;
                        } else {
                            continue;
                        }
                    }

                    orderAccept = true;
                    break;

                default:
                    System.out.println("not done or out of range");
                    break;
            }

            if (!orderAccept) {
                System.out.println(order);
            }
        }

        if (!cancel) {
            activeOrdersOfToday.add(order);
        }
    }

    public void getPaymentAndCompleteOrder() throws IOException {

        System.out.println("Indtast tlf nummer tilknyttet ordren:");
        String soegTlfNummer = scanner.nextLine();

        for (int i = 0; i < activeOrdersOfToday.size(); i++) {

            if (activeOrdersOfToday.get(i).getPhoneNumberCustomer().matches(soegTlfNummer)) {
                System.out.println("Resultat fundet: " + activeOrdersOfToday.get(i).getPhoneNumberCustomer());
                System.out.println(activeOrdersOfToday.get(i));

                if (activeOrdersOfToday.get(i).anyUnfinishedPizzasInOrder()) {
                    System.out.println("Ikke klar til udlevering endnu. Ordren er ikke faerdig.");
                    System.out.println("Indtast hvad som helst for at vende tilbage til menu");
                    scanner.nextLine();
                } else {

                    System.out.println("Bekræft betaling (1) ja, (2) slet ordren (3) tilbage: ");

                    int valg = Utility.getChoiceIntValidated(1, 3, false, scanner);

                    if (valg ==1) {
                        activeOrdersOfToday.get(i).setOrderIsCompleted(true);
                        completedOrdersOfToday.add(activeOrdersOfToday.remove(i));
                        System.out.println("Betaling gennemført, udlever ordre");

                        saveActiveAndCompletedOrdersToFile();

                    } else if (valg ==2){
                        System.out.println("Betaling afbrudt");
                        activeOrdersOfToday.remove(i);

                        saveActiveAndCompletedOrdersToFile();

                    } else if(valg ==3) {
                        break;
                    }
                }
            } else {
                System.out.println("Telefonnummeret findes ikke på listen");
            }
        }
    }

    public void markPizzaOnProductionSequenceReady() throws IOException {

        int tempChoice;

        updatePizzaProductionSequence(false, false);

        if (isPizzaProductionSequenceEmpty()) {
            System.out.println("Alle pizzaer er allerede klar\n");
            return;
        }

        for (int i = 0; i < pizzaProductionSequence.size(); i++) {
            System.out.printf("[%2d] %s\n", i + 1, pizzaProductionSequence.get(i).toString());

        }

        System.out.println("Enter [nummer] på pizza som du vil markere som klar (0 for exit)");

        tempChoice = Utility.getChoiceIntValidated(0, pizzaProductionSequence.size(), false, scanner);

        if (tempChoice == 0) {
            return;
        }

        pizzaProductionSequence.get(tempChoice - 1).setPizzaIsReadyForPickup(true);

        updatePizzaProductionSequence(false, false);

        saveActiveOrdersOfToday();
    }

    public void updatePizzaProductionSequence(boolean printList, boolean printPhoneNumberOfOrdersReadyForPickup) {

        StringBuilder ordersWithAllPizzasReady = new StringBuilder();

        if (!isPizzaProductionSequenceEmpty()) {
            clearPizzaProductionSequence();
        }

        activeOrdersOfToday.sort(Comparator.comparing(Order::getDateTimeOrderPickup));

        boolean flagPizzaProductionSequenceHeaderAlreadyPrinted = false;

        boolean flagTestedOrderHasAllPizzasReady;

        for (Order order : activeOrdersOfToday) {
            flagTestedOrderHasAllPizzasReady = true;

            for (Pizza pizza : order.getPizzasOfThisOrder()) {

                if (!pizza.getPizzaIsReadyForPickup()) {

                    if (!flagPizzaProductionSequenceHeaderAlreadyPrinted && printList) {
                        System.out.println("Afhentningstidspunkt\n  |");
                    }

                    pizzaProductionSequence.add(pizza);

                    if (printList) {
                        System.out.printf("%s  %s\n", Utility.formatDkTime(order.getDateTimeOrderPickup()), pizza.toString());
                    }

                    flagPizzaProductionSequenceHeaderAlreadyPrinted = true;

                    flagTestedOrderHasAllPizzasReady = false;
                }
            }

            if (flagTestedOrderHasAllPizzasReady) {
                ordersWithAllPizzasReady.append("  ").append(order.getPhoneNumberCustomer());
            }
        }

        if (printPhoneNumberOfOrdersReadyForPickup && !ordersWithAllPizzasReady.toString().isEmpty()) {
            System.out.printf("\nTlf nummer på ordre som er klar til udlevering: %s\n\n", ordersWithAllPizzasReady.toString());
        } else {
            System.out.println("Der er ingen ordrer klar til udlevering");
        }
    }


    public void printActiveOrdersOfTodaySorted() {
        if (isActiveOrdersOfTodayEmpty()) {
            System.out.println("Der er ingen uafsluttede ordre for dagen at printe");
        } else {
            Collections.sort(activeOrdersOfToday, new ComparatorOrderDate());
            activeOrdersOfToday.forEach(System.out::println);
        }
    }

    public void printCompletedOrdersOfTodayNotYetMovedToPast() {
        if (isCompletedOrdersOfTodayEmpty()) {
            System.out.println("Der er ingen afsluttede ordre for dagen at printe");
        } else {
            Collections.sort(completedOrdersOfToday, new ComparatorOrderDate());
            completedOrdersOfToday.forEach(System.out::println);
        }
    }

    public void printAllOrdersInThePastSorted() throws IOException, ClassNotFoundException {

        loadOrdersInThePast();

        if (isOrdersInThePastEmpty()) {
            System.out.println("Der er ingen ordre i historikken at printe");
        } else {
            Collections.sort(ordersInThePast, new ComparatorOrderDate());
            System.out.println(ordersInThePast);
        }
    }

    public void saveActiveAndCompletedOrdersToFile() throws IOException {
        if (isActiveOrdersOfTodayEmpty()) {
            clearActiveOrdersOfTodayFile();
        } else {
            saveActiveOrdersOfToday();
        }

        if (isCompletedOrdersOfTodayEmpty()) {
            clearCompletedOrdersOfTodayFile();
        } else {
            saveCompletedOrdersOfToday();
        }
    }

    public double getTotalRevenueOfToday() {

        double totalRevenueOfToday = 0;

        for (Order order : completedOrdersOfToday) {
            totalRevenueOfToday += order.getTotalPriceOfOrder();
        }
        return totalRevenueOfToday;
    }

    public void printStatisticsOfToday() {
        if (!isCompletedOrdersOfTodayEmpty()) {

            System.out.printf("Dagens omsætning: %.2f kr.\n", getTotalRevenueOfToday());

            printHistogramFrequencyOfFromListOfOrders(completedOrdersOfToday);
        } else {
            System.out.println("Der er endnu ingen afsluttede ordrer i dag, så ingen omsætning\n");
        }
    }

    public void printStatisticsOfPast() throws IOException, ClassNotFoundException {

        loadOrdersInThePast();

        if (!isCompletedOrdersOfTodayEmpty()) {
            ordersInThePast.addAll(completedOrdersOfToday);
        }

        printHistogramRevenuePerDate();

        System.out.printf("Total omsætning:          %10.2f kr.\n(Historik inklusiv dagens afsluttede ordre)\n", getTotalRevenueOrdersPast());

        System.out.println("--------------------------------------------------------------------------");

        System.out.println("\nAll time (historik inklusiv dagens afsluttede ordre)");

        printHistogramFrequencyOfFromListOfOrders(ordersInThePast);

        System.out.println("--------------------------------------------------------------------------");

        clearOrdersInThePast();
    }


    public static void printHistogramFrequencyOfFromListOfOrders(ArrayList<Order> aListOfOrders) {

        if (aListOfOrders.isEmpty()) {
            System.out.println("Ingen pizza objekter i denne liste");
            return;
        }

        Map<String, Integer> mapForHistogramPizzaNames = new HashMap<String, Integer>();

        for (Order order : aListOfOrders) {

            for (int p = 0; p < order.getPizzasOfThisOrder().size(); p++) {

                if (mapForHistogramPizzaNames.containsKey(order.getPizzasOfThisOrder().get(p).getPizzaName())) {

                    mapForHistogramPizzaNames.put(order.getPizzasOfThisOrder().get(p).getPizzaName(),
                            mapForHistogramPizzaNames.get(order.getPizzasOfThisOrder().get(p).getPizzaName()) + 1);
                } else {
                    mapForHistogramPizzaNames.put(order.getPizzasOfThisOrder().get(p).getPizzaName(), 1);
                }
            }
        }

        ArrayList<Map.Entry<String, Integer>> listForSetsOfHistogramMap = new ArrayList<Map.Entry<String, Integer>>(mapForHistogramPizzaNames.entrySet());

        Collections.sort(listForSetsOfHistogramMap, new ComparatorPizzaFrequency());

        System.out.println("mest populære pizzaer\n\nNavn                 Antal solgte");

        for (int i = 0; i < listForSetsOfHistogramMap.size(); i++) {
            System.out.printf("%-20s %d\n", listForSetsOfHistogramMap.get(i).getKey(), listForSetsOfHistogramMap.get(i).getValue());
        }
    }

    public void printHistogramRevenuePerDate() {

        LocalDateTime orderPickupDateAndTime;
        LocalDate orderPickupDate;

        if (isOrdersInThePastEmpty()) {
            System.out.println("Ingen pizza objekter i denne liste");
            return;
        }

        Map<LocalDate, Double> mapForHistogramRevenuePerDate = new HashMap<LocalDate, Double>();

        for (Order order : ordersInThePast) {
            orderPickupDateAndTime = order.getDateTimeOrderPickup();
            orderPickupDate = orderPickupDateAndTime.toLocalDate();

            if (mapForHistogramRevenuePerDate.containsKey(orderPickupDate)) {
                mapForHistogramRevenuePerDate.put(orderPickupDate,
                        (mapForHistogramRevenuePerDate.get(orderPickupDate) + order.getTotalPriceOfOrder()));
            } else {
                mapForHistogramRevenuePerDate.put(orderPickupDate, order.getTotalPriceOfOrder());
            }
        }

        ArrayList<Map.Entry<LocalDate, Double>> listForSetsOfHistogramMap = new ArrayList<Map.Entry<LocalDate, Double>>(mapForHistogramRevenuePerDate.entrySet());

        Collections.sort(listForSetsOfHistogramMap, new ComparatorRevenueDate());

        System.out.println("\nUgedag     Dato               Datoens totale omsætning");

        for (int i = 0; i < listForSetsOfHistogramMap.size(); i++) {
            System.out.printf("%-10s %10s     %10.2f kr.\n",
                    Utility.translateWeekdayEngToDK(String.valueOf(listForSetsOfHistogramMap.get(i).getKey().getDayOfWeek())),
                    Utility.formatDate(listForSetsOfHistogramMap.get(i).getKey()),
                    listForSetsOfHistogramMap.get(i).getValue());
        }

        System.out.println();
    }

    public double getTotalRevenueOrdersPast() {
        double totalRevenueOrdersPast = 0;

        for (Order order : ordersInThePast) {
            totalRevenueOrdersPast += order.getTotalPriceOfOrder();
        }
        return totalRevenueOrdersPast;
    }

    public boolean endTodayAndClearForTomorrow() throws IOException {

        if (!isActiveOrdersOfTodayEmpty()) {
            System.out.println("Du har stadig uafsluttede ordrer som først skal behandles");
            return false;
        } else if (!isCompletedOrdersOfTodayEmpty()) {
            saveCompletedOrdersOfTodayToOrdersInThePast();
            clearCompletedOrdersOfToday();
            clearCompletedOrdersOfTodayFile();

            System.out.println("Dagens afsluttede ordrer blev overført til historik\nAlt er klart til at starte forfra i morgen");
            return true;
        } else {
            return true;
        }
    }

    public void setAllPizzasInActiveOrdersOfTodayAsReady() {
        for (Order order : activeOrdersOfToday) {
            for (Pizza pizza : order.getPizzasOfThisOrder()) {
                pizza.setPizzaIsReadyForPickup(true);
            }
        }
    }

    public void moveAllActiveOrdersOfTodayToCompletedOrdersOfToday() throws IOException {
        if (!isActiveOrdersOfTodayEmpty()) {

            for (int i = 0; i < activeOrdersOfToday.size(); i++) {
                activeOrdersOfToday.get(i).setOrderIsCompleted(true);
                completedOrdersOfToday.add(activeOrdersOfToday.remove(i));

            }

            saveCompletedOrdersOfToday();
        }
    }

    public void changeDateOnAllCompletedOrders() {

        System.out.println("Dag i måneden: ");
        int day = Utility.getChoiceIntValidated(1, 28, false, scanner);

        System.out.println("Måned: ");
        int month = Utility.getChoiceIntValidated(1, 12, false, scanner);

        System.out.println("Årstal: ");
        int year = Utility.getChoiceIntValidated(2023, 2025, false, scanner);

        LocalDateTime tempDateTimeOrderPickup = LocalDateTime.now();
        tempDateTimeOrderPickup = tempDateTimeOrderPickup.withDayOfMonth(day);
        tempDateTimeOrderPickup = tempDateTimeOrderPickup.withMonth(month);
        tempDateTimeOrderPickup = tempDateTimeOrderPickup.withYear(year);

        LocalDateTime tempDateTimeOrderPlaced = tempDateTimeOrderPickup.minusHours(1);

        for (Order order : completedOrdersOfToday) {
            order.setDateTimeOrderPlaced(tempDateTimeOrderPlaced);
            order.setDateTimeOrderPickup(tempDateTimeOrderPickup);
        }
    }


}
