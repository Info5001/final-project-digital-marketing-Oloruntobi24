package model.Validation;

import java.util.Scanner;

public class InputValidation {
    public static int getValidIntInput(String message, int min, int max) {
        Scanner scanner = new Scanner(System.in);
        int input;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                }
            }
            System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
            scanner.nextLine(); // clear invalid input
        }
    }
}
