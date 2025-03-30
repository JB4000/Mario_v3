import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class PizzaMenuTextFileManager {

    private final String pizzaMenuFileName;
    private final String pizzaMenuIngredientsFileName;

    public PizzaMenuTextFileManager(String pizzaMenuFileName, String pizzaMenuIngredientsFileName) {

        this.pizzaMenuFileName = pizzaMenuFileName;
        this.pizzaMenuIngredientsFileName = pizzaMenuIngredientsFileName;
    }


    public ArrayList<Pizza> getPizzaMenuFromFile() throws FileNotFoundException {

        ArrayList<Pizza> pizzaMenu = new ArrayList<>();

        File myObj = new File(pizzaMenuFileName);
        Scanner myReader = new Scanner(myObj);

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (data.isEmpty()) {
                continue;
            }

            String[] split = data.split(", ");

            String nummer = split[0].trim();
            String navn = split[1].trim();
            String pris = split[2].trim();

            String[] ingredienser = new String[split.length - 4];
            for (int i = 0; i < split.length - 4; i++) {
                ingredienser[i] = split[3 + i];
            }

            String kommentar = split[split.length - 1].trim();

            pizzaMenu.add(new Pizza(Integer.parseInt(nummer), navn, Double.parseDouble(pris), ingredienser, kommentar));
        }

        return pizzaMenu;
    }

    public void saveAppendNewPizzaToPizzaMenuFile(Pizza newPizza, String[] array) {
        try {
            FileWriter myWriter = new FileWriter(pizzaMenuFileName, true);

            myWriter.write("\n" + newPizza.getPizzaNumber() + ", " + newPizza.getPizzaName() + ", " + newPizza.getPriceOfPizza() + ", ");
            for (int j = 0; j < array.length; j++) {
                myWriter.write(array[j] + ", ");
            }
            myWriter.write(newPizza.getPizzaComment());

            myWriter.close();
            System.out.println("Menuen er opdateret");
        } catch (IOException e) {
            System.out.println("En fejl er opstået.");
            e.printStackTrace();
        }
    }

    public void savePizzaMenuToPizzaMenuFile(ArrayList<Pizza> pizzaMenuList) {

        try {
            FileWriter myWriter = new FileWriter(pizzaMenuFileName);
            for (int i = 0; i < pizzaMenuList.size(); i++) {

                myWriter.write(pizzaMenuList.get(i).getPizzaNumber() + ", " + pizzaMenuList.get(i).getPizzaName() + ", " + pizzaMenuList.get(i).getPriceOfPizza() + ", ");

                for (int j = 0; j < pizzaMenuList.get(i).getIngredientsOfPizza().length; j++) {
                    myWriter.write(pizzaMenuList.get(i).getIngredientsOfPizza()[j] + ", ");
                }
                myWriter.write(pizzaMenuList.get(i).getPizzaComment() + "\n");
            }
            myWriter.close();
            System.out.println("Menuen er opdateret");
        } catch (IOException e) {
            System.out.println("En fejl er opstået.");
            e.printStackTrace();
        }
    }

    public void saveAvailableIngredientsToFile(ArrayList<String> ingredienser) {

        try {
            System.out.println(pizzaMenuIngredientsFileName);
            FileWriter myWriter = new FileWriter(pizzaMenuIngredientsFileName);

            for (int i = 0; i < ingredienser.size(); i++) {
                myWriter.write(ingredienser.get(i) + "\n");
            }

            myWriter.close();

        } catch (IOException e) {
            System.out.println("En fejl er opstået.");
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAvailableIngredientsFromFile() {

        ArrayList<String> ingredienser = new ArrayList<>();

        try {

            File myObj = new File(pizzaMenuIngredientsFileName);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String ingrediens = myReader.nextLine();
                ingredienser.add(ingrediens);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Der er sket en fejl under læsning af ingredienser.");
            e.printStackTrace();
        }
        return ingredienser;
    }
}
