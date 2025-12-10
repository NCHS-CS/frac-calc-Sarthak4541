// Sarthak Kansal
// Period 6
// Fraction Calculator Project

import java.util.*;

// This is a fraction calculator that can perform arithmetic operations. The user will type in
// two numbers with an operator and the program will perform the math and output the answer

public class FracCalc {

   // It is best if we have only one console object for input
   public static Scanner console = new Scanner(System.in);
   
   // This main method will loop through user input and then call the
   // correct method to execute the user's request for help, test, or
   // the mathematical operation on fractions. or, quit.
   // DO NOT CHANGE THIS METHOD!!
   public static void main(String[] args) {

      // initialize to false so that we start our loop
      boolean done = false;
      
      // When the user types in "quit", we are done.
      while (!done) {
         // prompt the user for input
         String input = getInput();
         
         // special case the "quit" command
         if (input.equalsIgnoreCase("quit")) {
            done = true;
         } else if (!UnitTestRunner.processCommand(input, FracCalc::processCommand)) {
        	   // We allowed the UnitTestRunner to handle the command first.
            // If the UnitTestRunner didn't handled the command, process normally.
            String result = processCommand(input);
            
            // print the result of processing the command
            System.out.println(result);
         }
      }
      
      System.out.println("Goodbye!");
      console.close();
   }

   // Prompt the user with a simple, "Enter: " and get the line of input.
   // Return the full line that the user typed in.
   public static String getInput() {
      
      Scanner console = new Scanner(System.in);
      System.out.println("Enter: ");
      String input = console.nextLine();
      return input;

   }
   
   // processCommand will process every user command except for "quit".
   // It will return the String that should be printed to the console.
   // This method won't print anything.
   // DO NOT CHANGE THIS METHOD!!!
   public static String processCommand(String input) {

      if (input.equalsIgnoreCase("help")) {
         return provideHelp();
      }
      
      // if the command is not "help", it should be an expression.
      // Of course, this is only if the user is being nice.
      return processExpression(input);
   }
   
   // Lots work for this project is handled in here.
   // Of course, this method will call LOTS of helper methods
   // so that this method can be shorter.
   // This will calculate the expression and RETURN the answer.
   // This will NOT print anything!
   // Input: an expression to be evaluated
   //    Examples: 
   //        1/2 + 1/2
   //        2_1/4 - 0_1/8
   //        1_1/8 * 2
   // Return: the fully reduced mathematical result of the expression
   //    Value is returned as a String. Results using above examples:
   //        1
   //        2 1/8
   //        2 1/4
   public static String processExpression(String input) {
      
   
         int space1 = input.indexOf(" ");
         String second_part = input.substring(space1+1);
         int space2 = second_part.indexOf(" ");
         String operator = input.charAt(space1+1) + "";
         String second_number = second_part.substring(space2+1);

         String whole;
         String num;
         String den;

         int underscoreIndex = second_number.indexOf("_");
         int division = second_number.indexOf("/");

         if (underscoreIndex>=0){//mixed number
            whole = second_number.substring(0,underscoreIndex);
            num = second_number.substring(underscoreIndex+1, division);
            den = second_number.substring(division+1);
         }
         else if (division>=0){//fraction
            whole = "0";
            num = second_number.substring(0, division);
            den = second_number.substring(division+1);
         }
         else{ 
            whole = second_number;
            num = "0";
            den = "1";
         }
         getWhole(whole, second_number, underscoreIndex, division);
         getNum(num, second_number, underscoreIndex, division);
         getDen(den, second_number, underscoreIndex, division);

         if (Integer.parseInt(num)<0 && Integer.parseInt(den)<0){
            
            num = Integer.parseInt(num)*-1 + "";
            den = Integer.parseInt(den)*-1 + "";
         }
         else if (Integer.parseInt(den)<0){
            num = "-"+num;
            den = "-" + den;
         }

        // return input is the description of the SECOND number
        // parsed into four parts: operator, whole number, numerator, and denominator.
         return "Op:" + operator + " Whole:" + whole + " Num:" + num + " Den:" + den; 

   }
   public static int getWhole(String whole, String second_number, int underscoreIndex, int division){
           if (underscoreIndex>=0){//mixed number
            whole = second_number.substring(0,underscoreIndex);
         }
         else if (division>=0){//fraction
            whole = "0";
         }
         else{ 
            whole = second_number;
         }
         return Integer.parseInt(whole);
      } 

   public static int getNum(String num, String second_number, int underscoreIndex, int division){
      if (underscoreIndex>=0){//mixed number
            num = second_number.substring(underscoreIndex+1, division);

         }
         else if (division>=0){//fraction
            num = second_number.substring(0, division);
         }
         else{ 
            num = "0";
         }

         return Integer.parseInt(num);
      }
      
   public static int getDen(String den, String second_number, int underscoreIndex, int division){
      if (underscoreIndex>=0){//mixed number
            den = second_number.substring(division+1);
         }
         else if (division>=0){//fraction
            den = second_number.substring(division+1);
         }
         else{ 
            den = "1";
         }

         return Integer.parseInt(den);
      } 

   
      public static int getGCD(int a, int b){
         int gcf = 1;
      for (int i=1; i>=Math.min(a,b); i++){
         if (a%i == b%i && a%i == 0){
            gcf = i;
         }
      }
      return a * b / gcf;
   }

   public static void getImproperFrac(String a, String b){
      
   }

   
   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
   
     
      String help = "Enter 2 numbers seperated by an arithmetic operator \n";
      help += "Try to format the mixed numbers in the following form: 2_1/2, 3/4, etc.";
      
      return help;
   }
}

