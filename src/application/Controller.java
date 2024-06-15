package application;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField display;
    
    
    @FXML
    
    private void handleButtonAction(javafx.event.ActionEvent event) {
        // Get the source of the button click
        Button clickedButton = (Button) event.getSource();
        // Get the button text
        String buttonText = clickedButton.getText();
        System.out.println("Button clicked: " + buttonText);

        // Check if the display is not empty and the last character is an operator
        if (!display.getText().isEmpty() && isOperator(display.getText().charAt(display.getText().length() - 1)) && isOperator(buttonText)) {
            return; // Do not append the button text
        }

        // Append the button text to the display
        display.appendText(buttonText);
    }

    // Method to check if a character is an operator
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Method to check if a string is an operator
    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }
    
   @FXML
	private void handleEqualButton(javafx.event.ActionEvent event)
    {
		
	   try {
           String input = display.getText();
           double result = evaluateExpression(input);
           display.setText(String.valueOf(result));
       } catch (Exception e) {
           display.setText("Error");
       }
    		
    }

   private double evaluateExpression(String expression) {
       // Splitting the expression based on spaces
	   List<String> postfix = infixToPostfix(expression);
       return evaluatePostfix(postfix);
   }
   
   private List<String> infixToPostfix(String expression) {
	    List<String> output = new ArrayList<>();
	    Stack<String> operators = new Stack<>();
	    String[] tokens = expression.split("(?<=[-+*/()])|(?=[-+*/()])");

	    for (String token : tokens) {
	        if (token.isEmpty()) {
	            continue;
	        }

	        if (token.matches("\\d+")) {
	            output.add(token);
	        } else if (token.equals("(")) {
	            operators.push(token);
	        } else if (token.equals(")")) {
	            while (!operators.isEmpty() && !operators.peek().equals("(")) {
	                output.add(operators.pop());
	            }
	            if (!operators.isEmpty()) {
	                operators.pop(); // Remove the '(' from stack
	            }
	        } else if (token.matches("[-+*/]")) {
	            while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
	                output.add(operators.pop());
	            }
	            operators.push(token);
	        }
	    }

	    while (!operators.isEmpty()) {
	        output.add(operators.pop());
	    }

	    return output;
	}

   
   private int precedence(String operator) {
       switch (operator) {
           case "+":
           case "-":
               return 1;
           case "*":
           case "/":
               return 2;
           default:
               return -1;
       }
   }
  
   
   private double evaluatePostfix(List<String> postfix) 
   {
       Stack<Double> stack = new Stack<>();

       for (String token : postfix) 
       {
           if (token.matches("\\d+")) 
           {
               stack.push(Double.parseDouble(token));
           } 
           else if (token.matches("[-+*/]")) 
           {
               double b = stack.pop();
               double a = stack.pop();
               switch (token) 
               {
                   case "+":
                       stack.push(a + b);
                       break;
                   case "-":
                       stack.push(a - b);
                       break;
                   case "*":
                       stack.push(a * b);
                       break;
                   case "/":
                       stack.push(a / b);
                       break;
               }
           }
       }

       return stack.pop();
   }
   
   
   @FXML
   private void cButton()
   {
	   display.clear();
	   
   }
   
   @FXML
   private void backSpaceButton()
   {
	   
	   String currentText = display.getText();
       if (currentText.length() > 0) 
       {
           display.setText(currentText.substring(0, currentText.length() - 1));
       }
   }



}