import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class PizzaMenu {

    private final Scanner scanner;

    private ArrayList<Pizza> pizzaMenuList;
    private ArrayList<String> listOfAvailableIngredients;

    private String pizzaMenuFileName;
    private String pizzaMenuIngredientsFileName;
    private PizzaMenuTextFileManager pizzaMenuFileManager;


    public PizzaMenu(String pizzaMenuFileName, String pizzaMenuIngredientsFileName, Scanner scanner) throws FileNotFoundException {
        this.scanner = scanner;
        this.pizzaMenuList = new ArrayList<>();
        this.listOfAvailableIngredients = new ArrayList<>();
        this.pizzaMenuFileName = pizzaMenuFileName;
        this.pizzaMenuIngredientsFileName = pizzaMenuIngredientsFileName;
        this.pizzaMenuFileManager = new PizzaMenuTextFileManager(pizzaMenuFileName, pizzaMenuIngredientsFileName);
        this.pizzaMenuList = this.pizzaMenuFileManager.getPizzaMenuFromFile();
        this.listOfAvailableIngredients = this.pizzaMenuFileManager.getAvailableIngredientsFromFile();
    }

    public ArrayList<String> getListOfAvailableIngredients() {
        return listOfAvailableIngredients;
    }

    public ArrayList<Pizza> getPizzaMenuList() {
        return pizzaMenuList;
    }

    public void printSortedPizzaMenu() {

        sortPizzaMenu("nummer");
    }

    public void sortPizzaMenu(String sortBy) {
        if (sortBy.equals("nummer")) {
            pizzaMenuList.sort(Comparator.comparingInt(Pizza::getPizzaNumber));

            for (Pizza s : pizzaMenuList) {
                System.out.println(s);
            }
        }
    }

    public boolean pizzaMenuHasPizzaNumber(int pizzaNumber) {
        boolean flagPizzaMenuHasThisPizzaNumber = false;

        for (Pizza pizza : pizzaMenuList) {
            if (pizza.getPizzaNumber() == pizzaNumber) {
                flagPizzaMenuHasThisPizzaNumber = true;
                break;
            }
        }
        return flagPizzaMenuHasThisPizzaNumber;
    }

    public void createNewPizzaForMenu() {

        int nummer = 0;
        double pris = 0.0;
        int answer = 0;
        int input = 0;
        String navn = "";
        boolean ifNavn = true;
        boolean ifExists = true;
        boolean ifNegative = true;
        boolean ifNextLine = true;
        boolean ifBreak = true;
        ArrayList<String> ingredients = new ArrayList<>();

        System.out.println("Hvad er nummeret på den pizza du ønsker at tilføje?");

        while (true) {

            if (scanner.hasNextInt()) {

                nummer = scanner.nextInt();

                for (int i = 0; i < pizzaMenuList.size(); i++) {

                    if (nummer == (pizzaMenuList.get(i).getPizzaNumber())) {
                        System.out.println("Nummeret er allerede i brug. Vælg venligst et andet");
                        ifExists = false;
                        ifNextLine = false;

                        break;

                    } else if (nummer < 1) {
                        System.out.println("Tallet er negativt. Vælg venligst et andet");
                        ifNegative = false;
                        ifNextLine = false;

                        break;

                    } else {
                        ifExists = true;
                        ifNegative = true;
                    }
                }

                if (ifExists && ifNegative) {
                    break;
                }
            } else {

                if (!ifNextLine) {
                    scanner.nextLine();
                }

                String error = scanner.nextLine();
                System.out.println("\"" + error + "\"" + " er ikke et tal");
                ifNextLine = true;
            }
        }

        scanner.nextLine();

        System.out.println("Hvad er navnet på den pizza du ønsker at tilføje?");

        while (true) {

            navn = scanner.nextLine().toUpperCase();

            for (int i = 0; i < pizzaMenuList.size(); i++) {

                if (pizzaMenuList.get(i).getPizzaName().equals(navn)) {
                    System.out.println("Navnet er allerede i brug. Vælg venligst et andet.");
                    ifNavn = false;

                    break;

                } else {
                    ifNavn = true;
                }
            }

            if (ifNavn) {
                break;
            }
        }

        System.out.println("Hvad er prisen på den pizza du ønsker at tilføje?");

        while (true) {

            if (scanner.hasNextDouble()) {

                pris = scanner.nextDouble();

                for (int i = 0; i < pizzaMenuList.size(); i++) {

                    if (pris < 0.0) {
                        System.out.println("Tallet er negativt. Vælg venligst et andet");
                        ifNegative = false;
                        ifNextLine = false;
                        break;

                    } else {
                        ifNegative = true;
                    }
                }

                if (ifNegative) {
                    break;
                }
            } else {

                if (!ifNextLine) {
                    scanner.nextLine();

                }

                String error = scanner.nextLine();
                System.out.println("\"" + error + "\"" + " er ikke et tal");
                ifNextLine = true;

            }
        }

        while (true) {

            while (true) {

                try {
                    for (int i = 0; i < listOfAvailableIngredients.size(); i++) {

                        if (!ingredients.contains(listOfAvailableIngredients.get(i))) {
                            System.out.println((i + 1) + ": " + listOfAvailableIngredients.get(i));
                        }
                        if (ingredients.contains(listOfAvailableIngredients.get(i))) {
                            System.out.println((i + 1) + ": " + listOfAvailableIngredients.get(i) + " (Allerede tilføjet. Vælg igen for at fjerne)");
                        }
                    }

                    System.out.println("Hvilken ingrediens ønsker du at tilføje til pizzaen?");

                    input = scanner.nextInt();

                    if (input <= listOfAvailableIngredients.size() && input > 0) {
                        break;
                    } else {
                        System.out.println("Fejl. Det indtastede er ikke en mulighed");
                    }
                } catch (Exception e) {
                    System.out.println("Fejl. Det indtastede er ikke en mulighed");
                    scanner.nextLine();
                }
            }

            if (ingredients.contains(listOfAvailableIngredients.get(input - 1))) {
                ingredients.remove(listOfAvailableIngredients.get(input - 1));
                System.out.println(listOfAvailableIngredients.get(input - 1) + " fjernet fra pizza");
            } else if (!ingredients.contains(listOfAvailableIngredients.get(input - 1))) {
                ingredients.add(listOfAvailableIngredients.get(input - 1));
                System.out.println(listOfAvailableIngredients.get(input - 1) + " tilføjet til pizza");
            }

            while (true) {

                try {
                    System.out.println("Vil du tilføje flere ingredienser?");
                    System.out.println("1: Ja");
                    System.out.println("2: Nej");

                    answer = scanner.nextInt();

                    if (answer == 1) {

                        break;

                    } else if (answer == 2) {

                        ifBreak = false;
                        break;

                    } else {
                        System.out.println("Fejl. Det indtastede er ikke en mulighed");
                    }

                } catch (Exception e) {
                    System.out.println("Fejl. Det indtastede er ikke en mulighed");
                    scanner.nextLine();
                }
            }
            if (!ifBreak) {

                break;
            }
        }

        String[] array = new String[ingredients.size()];

        for (int i = 0; i < ingredients.size(); i++) {
            array[i] = ingredients.get(i);
        }

        Pizza newPizza = new Pizza(nummer, navn, pris, array, "null");
        pizzaMenuList.add(newPizza);

        pizzaMenuFileManager.saveAppendNewPizzaToPizzaMenuFile(newPizza, array);
    }

    public void deletePizzaFromPizzaMenu() {

        boolean ifPizza = true;
        int answer = 0;

        for (Pizza s : pizzaMenuList) {
            System.out.println(s);
        }

        System.out.println("Vælg den pizza du ønsker at slette");

        while (true) {

            try {

                answer = scanner.nextInt();

                for (int i = 0; i < pizzaMenuList.size(); i++) {

                    if (answer == pizzaMenuList.get(i).getPizzaNumber()) {
                        System.out.println(pizzaMenuList.get(i).getPizzaName() + " er blevet slettet fra menukortet");
                        pizzaMenuList.remove(i);
                        ifPizza = false;
                    }
                }

                if (!ifPizza) {
                    break;
                }

                System.out.println("Der findes ikke en pizza med det indtastede nummer. Prøv igen.");

            } catch (Exception e) {
                System.out.println("Fejl. Det indtastede er ikke en mulighed");
                scanner.nextLine();
            }
        }
        pizzaMenuFileManager.savePizzaMenuToPizzaMenuFile(pizzaMenuList);
    }

    public void printListOfAvailableIngredientsSorted() {

        Collections.sort(listOfAvailableIngredients);
        System.out.println("Ingrediensliste:");

        for (String ingredient : listOfAvailableIngredients) {
            System.out.println(ingredient);
        }
    }

    public void addIngredientToListOfAvailable() {
        System.out.println("\nIndtast ny ingrediens: ");
        String ingrediens = scanner.nextLine();

        if (ingrediens.isEmpty()) {
            System.out.println("Du indtastede ikke noget, ingrediens ikke tilføjet");
            return;
        }

        for (String i : listOfAvailableIngredients) {
            if (i.equals(ingrediens)) {
                System.out.println("\nIngrediens findes allerede på listen");
                return;
            }
        }

        listOfAvailableIngredients.add(ingrediens);

        pizzaMenuFileManager.saveAvailableIngredientsToFile(listOfAvailableIngredients);

        System.out.println("\nIngrediensen '" + ingrediens + "' er blevet tilføjet til listen.");
    }

    public void deleteIngredientFromListOfAvailable() {
        System.out.println("\nHvilken ingrediens vil du slette?");
        for (int i = 0; i < listOfAvailableIngredients.size(); i++) {
            System.out.println((i + 1) + ". " + listOfAvailableIngredients.get(i));
        }

        System.out.println("\nIndtast nummeret på den ingrediens, som du ønsker at slette (0 for at fortryde): ");
        int valg;

        try {
            valg = Integer.parseInt(scanner.nextLine());

            if (valg == 0) {
                System.out.println("\nAnnulleret");
                return;
            }
            if (valg < 1 || valg > listOfAvailableIngredients.size()) {
                System.out.println("\nUgyldigt valg. Prøv igen");
                return;
            }

            String fjernet = listOfAvailableIngredients.remove(valg - 1);

            pizzaMenuFileManager.saveAvailableIngredientsToFile(listOfAvailableIngredients);

            System.out.println("\nIngrediensen '" + fjernet + "' er blevet fjernet");
        } catch (NumberFormatException e) {
            System.out.println("\nDu skal skrive et tal");
        }
    }
}
