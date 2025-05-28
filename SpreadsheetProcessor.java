/**
 * SpreadsheetProcessor manages a 2D spreadsheet of numeric values and provides methods
 * for setting cell values, calculating row means and standard deviations, and printing the spreadsheet.
 * <p>
 * The spreadsheet creates additional columns for mean and standard deviation calculations.
 * </p>
 */
public class SpreadsheetProcessor {
    private int columns;
    private int rows;
    private double[][] spreadsheet;

    /**
     * Constructs a SpreadsheetProcessor with the specified number of columns and rows.
     * Adds two extra columns for mean and standard deviation.
     *
     * @param columns the number of data columns
     * @param rows    the number of rows
     */
    public SpreadsheetProcessor(int columns, int rows) {
        int additionalColumns = 2; 
        this.columns = columns;
        this.rows = rows;
        this.spreadsheet = new double[rows][columns + additionalColumns];
    }

    /**
     * Sets the value of a specific cell in the spreadsheet.
     *
     * @param row    the row index (zero-based)
     * @param column the column index (zero-based)
     * @param value  the value to set
     * @throws IndexOutOfBoundsException if the cell position is invalid
     */
    public void setCellValue(int row, int column, double value) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IndexOutOfBoundsException("Invalid cell position: (" + row + ", " + column + ")");
        }
        spreadsheet[row][column] = value;
    }

    /**
     * Returns the number of data columns in the spreadsheet.
     *
     * @return the number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Returns the number of rows in the spreadsheet.
     *
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the maximum value found in the data cells of the spreadsheet.
     *
     * @return the maximum value
     */
    public double getMaxValue() {
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (spreadsheet[i][j] > max) {
                    max = spreadsheet[i][j];
                }
            }
        }
        return max;
    }

    /**
     * Calculates and stores the mean of each row in the penultimate column.
     */
    public void setMeanValues() {
        int meanColumnIndex = columns; // Mean will be stored in the penultimate column
        for (int i = 0; i < rows; i++) {
            double sum = 0;
            for (int j = 0; j < columns; j++) {
                sum += spreadsheet[i][j];
            }
            double mean = sum / columns;
            spreadsheet[i][meanColumnIndex] = mean; 
        }
    }

    /**
     * Calculates and stores the standard deviation of each row in the last column.
     */
    public void setStandardDeviationValues() {
        int stdDevColumnIndex = columns + 1; // Standard deviation will be stored in the last column
        for (int i = 0; i < rows; i++) {
            double sumSquares = 0;
            double mean = spreadsheet[i][columns];
            for (int j = 0; j < columns; j++) {
                sumSquares += Math.pow(spreadsheet[i][j], 2);
            }
            double stdDev = Math.sqrt((sumSquares / columns) - Math.pow(mean, 2));;
            spreadsheet[i][stdDevColumnIndex] = stdDev; 
        }
    }

    /**
     * Calculates mean and standard deviation for each row.
     */
    public void calculate() {
        setMeanValues();
        setStandardDeviationValues();
    }

    /**
     * Prints the spreadsheet, including data, mean, and standard deviation columns.
     * Highlights the maximum value in the data columns.
     */
    public void print() {
        int stdDevColumnIndex = columns + 1;
        String doubleSpace = "  ";
        double maxValue = getMaxValue();
        int widestWidth = getWidthOfWidestValue(maxValue);

        printHeaderRow(widestWidth);
        printSeparatorRow(widestWidth);
        for (int i = 0; i < rows; i++) {
            String rowHeaderString = Integer.toString(i + 1);
            System.out.printf(pad(rowHeaderString, widestWidth) + " | ");
            // Iterate through each column in the current row.
            for (int j = 0; j <= stdDevColumnIndex; j++) {
                // Print the value of the current cell with formatting.
                if (spreadsheet[i][j] == maxValue && j < columns) {
                    String newString = "*" + format(spreadsheet[i][j]) + "*";
                    System.out.printf(pad(newString, widestWidth) + doubleSpace);

                } else {
                    System.out.printf(pad(format(spreadsheet[i][j]), widestWidth) + doubleSpace);
                }
            }

            // Move to the next line after printing all columns in the current row.
            System.out.println();
        }
    }

    /**
     * Formats a double value to four decimal places as a string.
     *
     * @param value the value to format
     * @return the formatted string
     */
    public String format(double value) {
        double multiplier = Math.pow(10.0, 4.0);
        value *= multiplier;
        long roundedValue = (long) Math.round(value);
        double adjustedValue = roundedValue / multiplier;
        String stringValue = String.valueOf(adjustedValue);
        return stringValue;
    }

    /**
     * Returns the width of the widest value in the spreadsheet for formatting purposes.
     *
     * @param maxValue the maximum value in the spreadsheet
     * @return the width of the widest value
     */
    public int getWidthOfWidestValue(double maxValue) {
        int widestWidth = 0; 
        int stdDevColumnIndex = columns + 1; 
        int headerMaxLength = 6; // Minimum width to accommodate "StdDev" header.

        for (int i = 0; i < rows; i++) {
            // Loop through each column, including the mean and standard deviation columns.
            for (int j = 0; j <= stdDevColumnIndex; j++) {
                double cellValue = spreadsheet[i][j];
                // Format the value as a string and determine its length.
                int cellValueWidth = format(cellValue).length();

                // If the current cell contains the maximum value, give it extra padding.
                if (cellValue == maxValue && cellValueWidth >= widestWidth) {
                    widestWidth = cellValueWidth + 2; 
                }
                // Otherwise, update widestWidth if the current value's width is greater.
                else if (cellValueWidth > widestWidth) {
                    widestWidth = cellValueWidth;
                }
            }
        }

        // Ensure the width is at least as wide as the header labels.
        if (widestWidth <= headerMaxLength) {
            return headerMaxLength; // Return the minimum width to accommodate headers.
        } else {
            return widestWidth; // Return the calculated widest width.
        }
    }

    /**
     * Pads a string on the left with spaces to reach the specified width.
     *
     * @param oldString the original string
     * @param maxWidth  the desired width
     * @return the padded string
     */
    public String pad(String oldString, int maxWidth) {
        int stringWidth = oldString.length(); 
        String newString = ""; 

        // Add spaces to the left of the original string until its width matches maxWidth.
        while (stringWidth < maxWidth) {
            newString += " "; 
            stringWidth += 1; 
        }
        newString += oldString;
        return newString;
    }

    /**
     * Prints the header row for the spreadsheet, including column numbers and labels.
     *
     * @param widestWidth the width to use for each column
     */
    public void printHeaderRow(int widestWidth) {
        int count = widestWidth; // The width of the widest value determines the padding for alignment.
        String space = " "; // Single space for padding.
        String doubleSpace = "  "; // Double space for separation between columns.

        // Print leading spaces for alignment, followed by a vertical bar "|".
        System.out.printf(space.repeat(count) + " | ");

        for (int i = 1; i <= columns; i++) {
            String columnHeaderString = String.valueOf(i);
            System.out.printf(pad(columnHeaderString, widestWidth) + doubleSpace);
        }
        System.out.printf(pad("Mean", widestWidth) + doubleSpace);
        System.out.printf(pad("StdDev", widestWidth) + doubleSpace);
        System.out.println();
    }

    /**
     * Prints a separator row for the spreadsheet display.
     *
     * @param widestWidth the width to use for each column
     */
    public void printSeparatorRow(int widestWidth) {
        // Calculate the total length of the separator row:
        // - `numberOfColumns * (widestWidth + 2)` accounts for the width of each column plus the double space between them.
        // - `widestWidth` accounts for the leading space before the column headers.
        // - `+ 1` accounts for the vertical bar and any additional spacing.
        int count = ((columns + 2) * (widestWidth + 2)) + widestWidth + 1;
        String line = "-";
        String separatorString = line.repeat(count);
        System.out.println(separatorString);
    }
}
