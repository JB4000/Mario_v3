import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Utility {

    static final DateTimeFormatter formatDKDateTime = DateTimeFormatter.ofPattern("ddMMyy HH:mm");
    static final DateTimeFormatter formatDKTime = DateTimeFormatter.ofPattern("HH:mm");
    static final DateTimeFormatter formatDKDateYear = DateTimeFormatter.ofPattern("dd MM  yyyy");

    static final String[][] weekDaysEngToDK = {
            {"MONDAY", "Man"},
            {"TUESDAY", "Tir"},
            {"WEDNESDAY", "Ons"},
            {"THURSDAY", "Tor"},
            {"FRIDAY", "Fre"},
            {"SATURDAY", "Lør"},
            {"SUNDAY", "Søn"}
    };


    public static String formatDKDateTime(LocalDateTime time) {
        return time.format(formatDKDateTime);
    }


    public static String formatDkTime(LocalDateTime time) {
        return time.format(formatDKTime);
    }


    public static String formatDkDateYear(LocalDate dato) {
        return dato.format(formatDKDateYear);
    }

    public static int[][] getArrayChoicesOfPizzasThatExists(PizzaMenu pizzaMenu, Scanner scanner) {
        String[] pizzaChoiceTokenz;

        int[][] pizzaCoiceArray;
        String[] tempArrayForMulti;
        boolean flagSyntaxOk;
        boolean flagPizzaExists;

        do {
            flagSyntaxOk = true;
            flagPizzaExists = true;

            String tempString = scanner.nextLine();

            pizzaChoiceTokenz = tempString.split("\\s+");

            pizzaCoiceArray = new int[pizzaChoiceTokenz.length][2];

            for (int i = 0; i < pizzaChoiceTokenz.length; i++) {

                if (pizzaChoiceTokenz[i].matches("\\d+")) {

                    pizzaCoiceArray[i][0] = Integer.parseInt(pizzaChoiceTokenz[i]);
                    pizzaCoiceArray[i][1] = 1;

                } else if (pizzaChoiceTokenz[i].matches("(\\d+)\\*(\\d+)")) {

                    tempArrayForMulti = pizzaChoiceTokenz[i].split("\\*");
                    pizzaCoiceArray[i][0] = Integer.parseInt(tempArrayForMulti[1]);
                    pizzaCoiceArray[i][1] = Integer.parseInt(tempArrayForMulti[0]);

                } else {
                    flagSyntaxOk = false;
                }

                if (!pizzaMenu.pizzaMenuHasPizzaNumber(pizzaCoiceArray[i][0])) {
                    flagPizzaExists = false;
                }
            }

            if (!flagSyntaxOk) {
                System.err.println("Der er en syntax fejl i indtastningen");
            } else if (!flagPizzaExists) {
                System.err.println("Du har indtastet et forkert pizza nummer");
            }
        } while (!flagPizzaExists || !flagSyntaxOk);

        return pizzaCoiceArray;
    }

    public static int getChoiceIntValidated(int min, int max, boolean useEmpty, Scanner scanner) {

        String tempInput;
        int number;

        while (true) {
            tempInput = scanner.nextLine();

            if (tempInput.isEmpty()) {
                if (useEmpty) {
                    return Integer.MIN_VALUE;
                } else {
                    System.err.println("Du bliver nødt til at vaelge noget her");
                }
            } else if (tempInput.matches("-?\\d+")) {

                number = Integer.parseInt(tempInput);

                if (number >= min && number <= max) {
                    return number;
                } else {
                    System.err.printf("Tallet du har indtastet er uden for mulighederne.\nTallet skal være mellem %d og %d.\nPrøv igen\n", min, max);
                }

            } else {
                System.err.println("Det er ikke et tal du har indtastet. Prøv igen");
            }
        }
    }

    public static String getPhoneNumberDKValidated(boolean useEmpty, Scanner scanner) {

        boolean isValid = false;
        String userInput;

        do {
            userInput = scanner.nextLine();

            if (userInput.isEmpty() && useEmpty) {

                isValid = true;
            }

            if (!userInput.matches("[2-9]\\d{7}") && !userInput.isEmpty()) {
                System.err.println("Det indtastede er ikke et dansk tlf nummer. Prøv igen");
            } else {
                isValid = true;
            }
        } while (!isValid);

        return userInput;
    }

    public static int getPizzaMenuListIndexFromPizzaNumber(PizzaMenu pizzaMenu, int pizzaNumber) {

        for (int i = 0; i < pizzaMenu.getPizzaMenuList().size(); i++) {
            if (pizzaMenu.getPizzaMenuList().get(i).getPizzaNumber() == pizzaNumber) {
                return i;
            }
        }

        System.err.println("Pizza nr findes ikke i menu");
        return -1;
    }


    public static String getTextValidatedLength(int max, boolean useEmpty, Scanner in) {
        String tempString;
        boolean isValidated = false;

        do {
            tempString = in.nextLine();
            if (tempString.length() > max) {
                System.err.printf("Din tekst er for lang.\nDen må max være %d karakterer.\nPrøv igen\n", max);
            } else {
                if (tempString.isEmpty() && !useEmpty) {
                    System.err.println("Du indtastede ingenting");
                } else {
                    isValidated = true;
                }
            }

        } while (!isValidated);
        return tempString;
    }

    public static LocalDateTime getTimeLaterValidated(boolean askDiff, Scanner scanner) {

        String userInput;
        String[] tempArrayHM;
        boolean timeFormatValid;
        boolean timeIsLater;
        boolean timeDiffOk;
        LocalDateTime basisTime;

        timeDiffOk = true;

        basisTime = LocalDateTime.now();

        do {
            System.out.println("Indtast tidspunkt for afhentning (HH:MM format):");

            timeIsLater = false;

            while (!timeIsLater) {

                timeFormatValid = false;

                while (!timeFormatValid) {

                    userInput = scanner.nextLine();

                    if (userInput.matches("^([0-1][0-9]|2[0-3]|[0-9]):[0-5][0-9]")) {

                        tempArrayHM = userInput.split(":");
                        basisTime = basisTime.withHour(Integer.parseInt(tempArrayHM[0]));
                        basisTime = basisTime.withMinute(Integer.parseInt(tempArrayHM[1]));
                        timeFormatValid = true;

                        if (!basisTime.isAfter(LocalDateTime.now())) {
                            System.err.println("Klokken er allerede mere end det indtastede");
                        } else {
                            timeIsLater = true;
                        }
                    } else {
                        System.err.println("Syntax fejl i indtastede tidspunkt. Prøv igen");
                    }
                }
            }

            if (askDiff) {

                Duration d = Duration.between(LocalDateTime.now(), basisTime);

                long timer = d.toHours();
                long minutes = d.toMinutes();

                System.out.printf("Det er om %2d:%02d\nPasser det? (1)Ja 2(Nej)\n", timer, minutes % 60);

                if (getChoiceIntValidated(1, 2, false, scanner) == 2) {
                    timeDiffOk = false;
                } else {
                    timeDiffOk = true;
                }
            }
        } while (!timeDiffOk);

        return basisTime;
    }

    public static String translateWeekdayEngToDK(String weekday) {
        for (int i = 0; i < weekDaysEngToDK.length; i++) {
            if (weekday.equalsIgnoreCase(weekDaysEngToDK[i][0])) {
                weekday = weekDaysEngToDK[i][1];
                break;
            }
        }
        return weekday;
    }

    public static String formatDate(LocalDate dato) {
        return dato.format(formatDKDateYear);
    }
}
