import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Menu {
    public void mainMenu() {
        System.out.println("OPCJE:\n\n" +
                "1. Wybierz rodzaj posiłku\n" +
                "2. Zmień częstotliwość powiadomień\n" +
                "3. Odrzuć składniki\n" +
                "4. Wybierz przepis na teraz\n" +
                "5. Resetuj ustawienia\n" +
                "6. Wyjście\n");

        receiveInput(6);
    }
    private void chooseMeal() {
        System.out.println("Wybierz posiłek:\n\n" +
                "1. Śniadanie\n" +
                "2. Przekąski\n" +
                "3. Obiady\n" +
                "4. Powrót\n");

        receiveInput(4);
    }

    private void notificationSchedule() {
        System.out.println("Wpisz częstotliwość powiadomień (w dniach):\n");
        receiveInput(7);
    }

    private void chooseIngredients(List<String> ingredientList) {
        ingredientList = new ArrayList<>();

        System.out.println("Wybierz składniki, które nie będą uwzględniane:\n\n" +
                "");
    }

    private int receiveInput(int maxValue) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int input = scanner.nextInt();
            if (1 <= input && input <= maxValue) {
                return input;
            }

            System.out.println("Wybierz wartość pomiędzy " + (1) + " a " + (maxValue) + ".\n");
        }
    }
}
