package calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.text.DecimalFormat;

public class Controller {
    @FXML
    private Button btnOne;

    @FXML
    private Button btnTwo;

    @FXML
    private Button btnThree;

    @FXML
    private Button btnFour;

    @FXML
    private Button btnFive;

    @FXML
    private Button btnSix;

    @FXML
    private Button btnSeven;

    @FXML
    private Button btnEight;

    @FXML
    private Button btnNine;

    @FXML
    private Button btnZero;

    @FXML
    private Button divideOperator;

    @FXML
    private Button multiplyOperator;

    @FXML
    private Button addOperator;

    @FXML
    private Button subtractOperator;

    @FXML
    private Button equals;

    @FXML
    private Button decimalPoint;

    @FXML
    private Button delete;

    @FXML
    private Button clearScreen;

    @FXML
    private Label resultDisplay;


    private int operator; // determines the operator for calculations  ( 1 = +, 2 = -, 3 = * and 4 = / )
    private String[] operatorStorage = {null, null}; // stores operators user has pressed
    private Double firstOperand = null; // stores user defined operand
    private Double secondOperand = null; // stores user defined operand
    private Double finalResult; // the final result of calculation to be displayed to user
    private boolean intermediateOperandRequired; // handles whether intermediate calculation required
    private boolean operatorPressed; // for determining whether the button pressed is an operator or not
    private boolean isInitialized; // handles displaying zero prior to any user calculations
    private int digitCounter; // the number of digits in the number on the text field
    private final double MAX_VALUE = 9_999_999_999_999_999D;
    private final double MIN_VALUE = 0.00000000000001;

    /**
     * Handles all button click events (clicking number buttons and operator buttons).
     * @param event Button click event
     */
    @FXML
    public void buttonClicked(ActionEvent event){
        // Ensures clear screen for next calculation after division by zero or invalid syntax
        if(resultDisplay.getText().equals("UNDEFINED") || resultDisplay.getText().equals("INVALID SYNTAX")
                || resultDisplay.getText().equals("CALC LIMIT HIT") ||isInitialized){
            isInitialized = false;
            clearScreen();
        }

        // Statements that handle the user pressing the number buttons
        if(event.getSource() == btnOne && !isMaxInputReached()){
            resultDisplay.setText(resultDisplay.getText() + "1");
            formatUserInput();
        }else if(event.getSource() == btnTwo && !isMaxInputReached()){
            resultDisplay.setText(resultDisplay.getText() + "2");
            formatUserInput();
        } else if(event.getSource() == btnThree && !isMaxInputReached()){
            resultDisplay.setText(resultDisplay.getText() + "3");
            formatUserInput();
        } else if(event.getSource() == btnFour && !isMaxInputReached()){
            resultDisplay.setText(resultDisplay.getText() + "4");
            formatUserInput();
        } else if(event.getSource() == btnFive && !isMaxInputReached()){
            resultDisplay.setText(resultDisplay.getText() + "5");
            formatUserInput();
        } else if(event.getSource() == btnSix && !isMaxInputReached()){
            resultDisplay.setText(resultDisplay.getText() + "6");
            formatUserInput();
        } else if(event.getSource() == btnSeven && !isMaxInputReached()){
            resultDisplay.setText(resultDisplay.getText() + "7");
            formatUserInput();
        } else if(event.getSource() == btnEight && !isMaxInputReached()){
            resultDisplay.setText(resultDisplay.getText() + "8");
            formatUserInput();
        } else if(event.getSource() == btnNine && !isMaxInputReached()){
            resultDisplay.setText(resultDisplay.getText() + "9");
            formatUserInput();
        } else if(event.getSource() == btnZero && !isMaxInputReached()){
            resultDisplay.setText(resultDisplay.getText() + "0");
            formatUserInput();
        } else if(event.getSource() == decimalPoint){
            resultDisplay.setText(resultDisplay.getText() + ".");

        // add operator pressed
        } else if(event.getSource() == addOperator){
            System.out.println("\n+ was clicked\n");
            operator = 1;
            operatorPressed = true;
            setOperand();
            equalsCalculation();

        // subtract operator pressed
        } else if(event.getSource() == subtractOperator){
            System.out.println("- was clicked");
            operator = 2;
            operatorPressed = true;
            setOperand();
            equalsCalculation();

        // multiply operator pressed
        } else if(event.getSource() == multiplyOperator){
            System.out.println("* was clicked");
            operator = 3;
            operatorPressed = true;
            setOperand();
            equalsCalculation();

        // divide operator pressed
        } else if(event.getSource() == divideOperator){
            System.out.println("/ was clicked");
            operator = 4;
            operatorPressed = true;
            setOperand();
            equalsCalculation();

        // decimal point button pressed
        } else if(event.getSource() == decimalPoint){
            System.out.println(". was clicked");

        // equals button pressed
        } else if(event.getSource() == equals){
            System.out.println("\n= was clicked\n");
            operatorPressed = false;
            setOperand();
            equalsCalculation();
            displayResult();
            clear();

        // delete button pressed
        } else if(event.getSource() == delete){
            System.out.println("DEL was clicked");
            delete(); // deletes last number clicked

        // clear screen button pressed
        } else if(event.getSource() == clearScreen){
            System.out.println("CLEAR was clicked");
            clearScreen();
            clear();
            initialize();
        }
    }

    /**
     * Displays '0' prior to any calculations
     */
    public void initialize(){
        isInitialized = true;
        resultDisplay.setText("0");
    }

    /**
     * Calculates the final result. Calculates intermediate operand(s) for calculations
     * involving three or more terms before calculating the final solution.
     * @exception NullPointerException when calculation is attempted but both operand variables
     * have not yet been assigned.
     */
    private void equalsCalculation() {
        try {
            operator = determineOperator();
            switch(operator) {
                case 1:
                    // addition case
                    if(intermediateOperandRequired) {
                        if(!isMaxValueReached(firstOperand + secondOperand) &&
                                !isMinValueReached(firstOperand + secondOperand)){
                            firstOperand = firstOperand + secondOperand;
                            prepareForNextCalc();
                            equalsCalculation();
                        }
                    }else {
                        if(!isMaxValueReached(firstOperand + secondOperand) &&
                                !isMinValueReached(firstOperand + secondOperand) ){
                            finalResult = firstOperand + secondOperand;
                        }
                        break;
                    }
                    break;
                case 2:
                    // subtraction case
                    if(intermediateOperandRequired) {
                        if(!isMaxValueReached(firstOperand - secondOperand) &&
                                !isMinValueReached(firstOperand - secondOperand)){
                            firstOperand = firstOperand - secondOperand;
                            prepareForNextCalc();
                            equalsCalculation();
                        }
                    }else{
                        if(!isMaxValueReached(firstOperand - secondOperand)  &&
                                !isMinValueReached(firstOperand - secondOperand)){
                            finalResult = firstOperand - secondOperand;
                        }
                        break;
                    }
                    break;
                case 3:
                    // multiplication case
                    if(intermediateOperandRequired){
                        if(!isMaxValueReached(firstOperand * secondOperand) &&
                                !isMinValueReached(firstOperand * secondOperand)){
                            firstOperand = firstOperand * secondOperand;
                            prepareForNextCalc();
                            equalsCalculation();
                        }
                    }else{
                        if(!isMaxValueReached(firstOperand * secondOperand) &&
                                !isMinValueReached(firstOperand * secondOperand)){
                            finalResult = firstOperand * secondOperand;
                        }
                        break;
                    }
                    break;
                case 4:
                    // division case (division by zero prevented)
                    if (secondOperand == 0) {
                        resultDisplay.setText("UNDEFINED");
                    } else {
                        if(intermediateOperandRequired){
                            if(!isMaxValueReached(firstOperand / secondOperand)  &&
                                    !isMinValueReached(firstOperand / secondOperand)){
                                firstOperand = firstOperand / secondOperand;
                                prepareForNextCalc();
                                equalsCalculation();
                            }
                        } else {
                            if(!isMaxValueReached(firstOperand / secondOperand) &&
                                    !isMinValueReached(firstOperand / secondOperand)){
                                finalResult = firstOperand / secondOperand;
                            }
                            break;
                        }
                    }
                    break;
            }
        }catch(NullPointerException e){
            System.out.println("equalsCalculation Error: " + e.getMessage());
        }
    }

    /**
     * Sets the operands to be used in calculation. Operands are assigned to one of two variables.
     * Operands are assigned to the variables based on whether the variables are null or already assigned.
     * @exception NumberFormatException when the text in user input box cannot be parsed as Double.
     */
    private void setOperand(){
        String rawNumber = extractRawNumber();
        if(firstOperand == null && secondOperand == null) {
            try {
                firstOperand = Double.valueOf(rawNumber);
                clearScreen();
            } catch(NumberFormatException e){
                resultDisplay.setText("INVALID SYNTAX");
                clear();
            }

        }else if(secondOperand == null){
            try {
                secondOperand = Double.valueOf(rawNumber);
                clearScreen();
            } catch(NumberFormatException e){
                resultDisplay.setText("INVALID SYNTAX");
                clear();
            }
        }
        // 'operatorPressed' for handling user performing calculation with 2 or more operators (e.g. 1 + 2 - 3, etc)
        if(firstOperand != null && secondOperand != null && operatorPressed){
            intermediateOperandRequired = true;
        }
    }

    /**
     * Stores the operator the clicked, for calculations involving two or more operators (e.g. 1 + 2 -3)
     * @return The operator to be used in current calculation
     */
    public Integer determineOperator(){
        // saves first operator
        if(firstOperand != null && secondOperand == null && operatorStorage[0] == null){
            operatorStorage[0] = String.valueOf(operator);
        }
        // saves the second operator when a calculation involving 2  or more operators is executed
        if(intermediateOperandRequired){
            operatorStorage[1] = String.valueOf(operator);
        }
        try{
        // the operator to be used in calculation is always the [0] element of 'operatorStorage' array
            return Integer.valueOf(operatorStorage[0]);
        } catch (NumberFormatException e){
            System.out.println("ERROR Determining Operator: " + e.getMessage());
        }
        return null;
    }


    /**
     * Sets relevant variables ready for next calculation, after an intermediate calculation is executed.
     */
    private void prepareForNextCalc(){
        secondOperand = null;
        intermediateOperandRequired = false;
        operatorStorage[0] = operatorStorage[1];
    }

    /**
     * Determines whether user has input the maximum number the calculator is programmed
     * to deal with.
     * @return True if the max number is reached, false otherwise
     */
    private boolean isMaxInputReached(){
        if(digitCounter == 15){
            System.out.println("digit counter: " + digitCounter);
            return true;
        } else {
            digitCounter++;
            System.out.println("digit counter: " + digitCounter);
            return false;
        }
    }

    /**
     * Determines whether the maximum value the calculator is programmed
     * to handle has been reach.
     * @param calculatedValue - the value from current calculation
     * @return true if max value reached, false otherwise
     */
    private boolean isMaxValueReached(double calculatedValue){
        if(calculatedValue > MAX_VALUE){
            resultDisplay.setText("CALC LIMIT HIT");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines whether the minimum value the calculator is programmed
     * to handle has been reach.
     * @param calculatedValue - the value from current calculation
     * @return true if minimum value reached, false otherwise
     */
    private boolean isMinValueReached(double calculatedValue){
        if(calculatedValue <= MIN_VALUE){
            resultDisplay.setText("CALC LIMIT HIT");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Cleans screen for user to supply next input.
     */
    private void clearScreen(){
        resultDisplay.setText("");
        digitCounter = 0;
    }

    /**
     * Clears calculation variables of their contents to prepare for new calculation.
     */
    private void clear(){
        firstOperand = null;
        secondOperand = null;
        finalResult = null;
        operatorStorage[0] = null;
        operatorStorage[1] = null;
    }

    /**
     * deletes last clicked number from screen display.
     */
    private void delete(){
        String numberDelete = resultDisplay.getText();
        if(numberDelete.length() != 0){
            // outputs the first and next to last number of original number (deletes last digit)
            resultDisplay.setText(numberDelete.substring(0, numberDelete.length() - 1));
            digitCounter--;
            System.out.println("digits counter: " + digitCounter);
        }
    }

    /**
     * Displays final result of calculation to the user.
     */
    private void displayResult(){
        if(finalResult != null) {
            DecimalFormat df1 = new DecimalFormat("#,###,###,###,###,###.###############");
            DecimalFormat df2 = new DecimalFormat("################.###############");
            String unformattedResult  = String.valueOf(df2.format(finalResult));
            StringBuilder formattedResult = new StringBuilder();
            double displayResult;
            int digits = 0;
            int i = 0;
            if(unformattedResult.length() >= 15){
                while(digits != 15){
                    if(unformattedResult.charAt(i) != '.'){
                        formattedResult.append(unformattedResult.charAt(i));
                        digits++;
                        i++;
                    } else {
                        formattedResult.append(unformattedResult.charAt(i));
                        i++;
                    }
                }
                displayResult = Double.valueOf(formattedResult.toString());
                resultDisplay.setText(df1.format(displayResult));
            } else {
                resultDisplay.setText(df1.format(finalResult));
            }

        }
    }

    /**
     * Formats numbers clicked into text-field to have #,###,###,###,###,### format.
     * If the number in the text field contains a decimal point then  the formatting stops.
     */
    public void formatUserInput(){
        try{
            String rawNumber = extractRawNumber();
            Double inputAsNumber = Double.valueOf(rawNumber);
            boolean containsDecimalPoint = false;
            for(int i = 0; i < rawNumber.length(); i++){
                if(rawNumber.charAt(i) == '.'){
                    containsDecimalPoint = true;
                }
            }
            if(!containsDecimalPoint){
                DecimalFormat df = new DecimalFormat("###,###,###,###,###.##############");
                resultDisplay.setText(df.format(inputAsNumber));
            }
        } catch(NumberFormatException e) {
            System.out.println("Input Formatting Error: " + e.getMessage());
        }
    }

    /**
     * Extracts the number from the text field (which has format #,###,###.##)
     * and converts it into its raw form, e.g. 4,0535.60 becomes '40535.60',
     * and returns this as a string, so that it can be used in calculations.
     * @return A string of the number in the text field, in its raw form (no comma separators).
     */
    public String extractRawNumber(){
        String userInput = resultDisplay.getText();
        StringBuilder rawNumber = new StringBuilder();
        for(int i = 0; i < userInput.length(); i++ ){
            if(userInput.charAt(i) != ','){
                rawNumber.append(userInput.charAt(i));
            }
        }
        return rawNumber.toString();
    }
}