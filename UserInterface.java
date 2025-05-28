/**
 * Provides a command-line interface for interacting with the spreadsheet processor.
 * <p>
 * Handles user input for spreadsheet dimensions, populates the spreadsheet with values,
 * and triggers calculation and output of the processed spreadsheet. Validates user input
 * and manages the main application flow.
 * </p>
 */
import java.util.Scanner;
import java.util.function.Predicate;

public class UserInterface {
    
    /**
     * Starts the user interface and manages the main workflow for spreadsheet creation and processing.
     * Prompts the user for spreadsheet dimensions, populates the spreadsheet, performs calculations, and prints the result.
     */
    public void run() {

        try (Scanner scanner = new Scanner(System.in)) {
            int columns = getValidatedInt(scanner, "How many columns do you want this spreadsheet to have?", 
                num -> num > 0);
            int rows = getValidatedInt(scanner, "How many rows do you want this spreadsheet to have?",
                num -> num > 0);
            SpreadsheetProcessor spreadsheetProcessor = new SpreadsheetProcessor(columns, rows);
            populateSpreadsheet(spreadsheetProcessor, scanner);
            spreadsheetProcessor.calculate();
            spreadsheetProcessor.print();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Prompts the user for an integer input, validates it using the provided predicate, and returns the valid integer.
     *
     * @param scanner   the Scanner to read user input
     * @param prompt    the prompt message to display
     * @param validator a predicate to validate the input
     * @return a validated integer input from the user
     */
    public static int getValidatedInt(Scanner scanner, String prompt, Predicate<Integer> validator) {
        int number;
        while (true) {
            System.out.println(prompt);
            try {
                number = Integer.parseInt(scanner.nextLine());
                if (validator.test(number)) {
                    return number;
                } else {
                    System.out.println("Input does not meet the required condition. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    /**
     * Populates the spreadsheet with user-provided values for each cell.
     * Prompts the user for each cell value and validates numeric input.
     *
     * @param spreadsheetProcessor the spreadsheet processor to populate
     * @param scanner              the Scanner to read user input
     */
    public void populateSpreadsheet(SpreadsheetProcessor spreadsheetProcessor, Scanner scanner) {
        for (int i = 1; i <= spreadsheetProcessor.getRows(); i++) {
            for (int j = 1; j <= spreadsheetProcessor.getColumns(); j++) {
                System.out.printf("Enter value for cell (%d, %d): ", i, j);
                try {
                    double value = scanner.nextDouble();
                    spreadsheetProcessor.setCellValue(i - 1, j - 1, value); // Adjusting for zero-based index
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); // Clear the invalid input
                    j--; // redo the current cell input
                }
            }
        }
    }
}
