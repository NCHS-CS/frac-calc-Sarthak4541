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
      // if the user types in "quit", it closes the program
      System.out.println("Goodbye!");
      console.close();
   }

   // Prompt the user with a simple, "Enter: " and get the line of input.
   // Return the full line that the user typed in.
   public static String getInput() {
      
      System.out.println("Enter: ");
      return console.nextLine();

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
    String first_number = getFirstNumber(input);
    String operator = getOperator(input);
    String second_number = getSecondNumber(input);

    String frac1 = getImproperFrac(first_number);
    String frac2 = getImproperFrac(second_number);

    int improper_num1 = getNumerator(frac1);
    int improper_num2 = getNumerator(frac2);

    int den1 = getDenominator(frac1);
    int den2 = getDenominator(frac2);

    String resultFrac = performOperations(improper_num1, improper_num2, den1, den2, operator);

    int finalNum = getNumerator(resultFrac);
    int finalDen = getDenominator(resultFrac);

    return formatFraction(finalNum, finalDen);
   }

   public static int getWhole(String second_number){
      if (second_number.indexOf("_")>=0){//mixed number
         return Integer.parseInt(second_number.substring(0,second_number.indexOf("_")));
      }
      else if (second_number.indexOf("/")>=0){//fraction
         return 0;
      }
      else{ 
         return Integer.parseInt(second_number);
      }
   } 

   public static int getNum(String second_number){
      if (second_number.indexOf("_")>=0){//mixed number
         return Integer.parseInt(second_number.substring(second_number.indexOf("_")+1,second_number.indexOf("/")));
      }
      else if (second_number.indexOf("/")>=0){//fraction
         return Integer.parseInt(second_number.substring(0, second_number.indexOf("/")));
      }
      else{ 
         return 0;
      }
   }
      
   public static int getDen(String second_number){
      if (second_number.indexOf("/")>=0){
         return Integer.parseInt(second_number.substring(second_number.indexOf("/")+1));
      }
      else{
         return 1;
      }
   } 

   
   public static int getGCF(int a, int b){
      int gcf = 1;
      for (int i=1; i<=Math.min(a,b); i++){
         if (a%i == 0 && b%i == 0){
            gcf = i;
         }
      }
      return gcf;
   }

   public static String getImproperFrac(String output){
      int whole = getWhole(output);
      int num = getNum(output);
      int den = getDen(output);

      if (den==0){
         return "0/0";
      }

      int improperNum = Math.abs(whole) * den + num;

      if (whole<0){
         improperNum *=-1;
      } 

      if (den<0){
         den*=-1;
         improperNum *=-1;
      }
      
      return improperNum + "/" + den;
   }

   public static String performOperations(int num1, int num2, int den1, int den2, String operator){
      
      int num;
      int den;

      if (operator.equals("+")){
         num = num1 * den2 + num2 * den1;
         den = den1 * den2;
      } else if (operator.equals("-")){
         num = num1 * den2 - num2 * den1;
         den = den1 * den2;
      } else if (operator.equals("*")){
         num = num1 * num2;
         den = den1 * den2;
      } else{
         if (num2==0){
            return "0/0";
         }
         num = num1 * den2;
         den = den1 * num2;
      }
      return num + "/" + den;
   }

   public static String getOperator(String input){
      int space1 = input.indexOf(" ");
      return input.substring(space1 + 1, space1 + 2);

   }

   public static String getFirstNumber(String input){
      int space1 = input.indexOf(" ");
      return input.substring(0, space1);
   }

   public static String getSecondNumber(String input){
      int space1 = input.indexOf(" ");
      
      int space2 = space1 + 1;
      while (space2 < input.length() && input.charAt(space2) != ' ') {
         space2++;
      }

      return input.substring(space2 + 1);
   }

   public static int getNumerator(String frac) {
      return Integer.parseInt(frac.substring(0, frac.indexOf("/")));
   }

   public static int getDenominator(String frac) {
      return Integer.parseInt(frac.substring(frac.indexOf("/") + 1));
   }
   
   public static String formatFraction(int finalNum, int finalDen) {

    if (finalDen == 0) {
        return "ERROR: Division by zero";
    }
    if (finalNum == 0) {
        return "0";
    }
    if (finalDen < 0) {
        finalDen *= -1;
        finalNum *= -1;
    }

    int gcf = getGCF(Math.abs(finalNum), Math.abs(finalDen));
    finalNum /= gcf;
    finalDen /= gcf;

    int whole = finalNum / finalDen;
    int remainder = Math.abs(finalNum) % finalDen;

    String output = handleEdgeCases(whole, remainder, finalDen, finalNum);

    return output;
    
   }

   public static String handleEdgeCases(int whole, int remainder, int finalDen, int finalNum) {
      
      if (remainder == 0) {
         return whole + "";
      }

      if (whole == 0) {
         if (finalNum < 0) {
               return "-" + remainder + "/" + finalDen;
         } else {
               return remainder + "/" + finalDen;
         }
      }

      return whole + " " + remainder + "/" + finalDen;
   }

   
   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
   
     
      String help = "Enter 2 numbers seperated by an arithmetic operator \n";
      help += "Try to format the mixed numbers in the following form: 2_1/2, 3/4, etc. For example, -2_1/4 + 2_1/2.";
      
      return help;
   }
}

