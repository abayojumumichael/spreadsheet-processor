# Spreadsheet Processor

A simple Java command-line application for creating, processing, and displaying spreadsheets with statistical calculations.

## Features
- User-defined spreadsheet size (rows and columns)
- Input of numeric values for each cell
- Automatic calculation of mean and standard deviation for each row
- Highlights the maximum value in the spreadsheet
- Nicely formatted console output

## How It Works
1. The user is prompted to enter the number of columns and rows.
2. The user enters values for each cell in the spreadsheet.
3. The application calculates the mean and standard deviation for each row.
4. The spreadsheet is printed, including the calculated statistics and highlighting the maximum value.

## Usage

### Compile
```sh
javac Main.java UserInterface.java SpreadsheetProcessor.java
```

### Run
```sh
java Main
```

## Example Output
```
How many columns do you want this spreadsheet to have?
3
How many rows do you want this spreadsheet to have?
2
Enter value for cell (1, 1): 5
Enter value for cell (1, 2): 7
Enter value for cell (1, 3): 9
Enter value for cell (2, 1): 2
Enter value for cell (2, 2): 4
Enter value for cell (2, 3): 6
     |   1    2    3   Mean StdDev  
-------------------------------------
 1 |  5.0  7.0  *9.0*  7.0   1.6329  
 2 |  2.0  4.0   6.0   4.0   1.6329  
```

## Files
- `Main.java`: Entry point for the application.
- `UserInterface.java`: Handles user interaction and input validation.
- `SpreadsheetProcessor.java`: Manages spreadsheet data and calculations.

## License
This project is licensed under the MIT License.