package nus.iss.sdf;

import java.io.Console;

public class Main 
{
    public static void main( String[] args )
    {
        double last = 0;
        double x;
        double y;
        String operator;
        
        Console cons = System.console();
        System.out.println("Welcome.");
        String input = "";
        

        while (true) {
            input = cons.readLine(">").toLowerCase();

            // break if input is exit
            if (input.equals("exit")) {
                System.out.println("Byebye.");
                break;
            }

            String[] inputArr = input.split(" ");
            // input validation of array length 3
            if (inputArr.length != 3) {
                System.out.println("Invalid input. Please enter an expression in the format: <number><space><operator><space><number>");
                continue;
            }

            operator = inputArr[1];
            try {
                if (inputArr[0].equals("$last")) {
                    x = last;
                } else {
                    x = Double.parseDouble(inputArr[0]);
                }
    
                if (inputArr[2].equals("$last")) {
                    y = last;
                } else {
                    y = Double.parseDouble(inputArr[2]);
                }
                
                switch (operator){
                    case "+":
                        last = x + y;
                        System.out.println(last);
                        break;
                    case "-":
                        last = x - y;
                        System.out.println(last);
                        break;
                    case "/":
                        if (y == 0) {
                            System.out.println("Error: Division by zero.");
                        } else {
                            last = x / y;
                            System.out.println(last);
                            break;
                        }
                    case "x":
                        last = x * y;
                        System.out.println(last);
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a valid operator +-x/.");
                }
            // handles invalid input if user enters a character instead of no
            } catch (NumberFormatException e) {
                System.out.println("Please input a valid number.");
            }
            
        }

    }
}
