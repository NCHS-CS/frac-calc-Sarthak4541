// Sarthak Kansal
// Period 6
// Fraction Calculator Project

import java.util.*;

// This is a fraction calculator that can perform arithmetic operations. The user will type in
// two numbers with an operator and the program will perform the math and output the answer.

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
      
      // This gets the input and returns the value
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
   
   // Initalizes variables by calling respective methods
      String first_number = getFirstNumber(input);
      String operator = getOperator(input);
      String second_number = getSecondNumber(input);

      // Converts the numbers to improper fractions by calling methods
      String frac1 = getImproperFrac(first_number);
      String frac2 = getImproperFrac(second_number);

      //Stores numerator and denominator of each fraction
      int improper_num1 = getNumerator(frac1);
      int improper_num2 = getNumerator(frac2);

      int den1 = getDenominator(frac1);
      int den2 = getDenominator(frac2);

      // Calls the performOperations method to return the final fraction
      String resultFrac = performOperations(improper_num1, improper_num2, den1, den2, operator);

      // Gets numerator and denominator of the final fraction
      int finalNum = getNumerator(resultFrac);
      int finalDen = getDenominator(resultFrac);

      // Calls the formatFraction method to reduce to lowest terms,
      //  handle edge cases, and give the fraction as a mixed number
      return formatFraction(finalNum, finalDen);
   }

   // The getWhole method returns the whole number part of an input
    // getWhole takes 1 parameter
    // String input - The method returns the whole number part of the actual parameter
    // return type is int, since it needs to return an integer (whole number)
    
   public static int getWhole(String input){

      // If the input contains a "_", meaning it's a mixed number
      if (input.indexOf("_")>=0){
         return Integer.parseInt(input.substring(0,input.indexOf("_")));
      }
      // If the input contains a "/", meaning it's a fraction
      else if (input.indexOf("/")>=0){
         return 0;
      }
      // If input is a whole numer
      else{ 
         return Integer.parseInt(input);
      }
   } 

   // The getNum method returns the numerator of an input
   // getNum takes 1 parameter
   // String input - The method returns the numerator part of the actual parameter
   // return type is int, since it needs to return an integer (numerator of the fraction)

   public static int getNum(String input){

      // If the input contains a "_", meaning it's a mixed number
      if (input.indexOf("_")>=0){
         return Integer.parseInt(input.substring(input.indexOf("_")+1,input.indexOf("/")));
      }
      // If the input contains a "/", meaning it's a fraction
      else if (input.indexOf("/")>=0){//fraction
         return Integer.parseInt(input.substring(0, input.indexOf("/")));
      }
      // If input is a whole number
      else{ 
         return 0;
      }
   }
   
   // The getDen method returns the denominator of an input
   // getDen takes 1 parameter
   // String input - The method returns the denominator part of the actual parameter
   // return type is int, since it needs to return an integer (denominator of the fraction)

   public static int getDen(String input){

      // If the input contains a "/", meaning it's a fraction
      if (input.indexOf("/")>=0){
         return Integer.parseInt(input.substring(input.indexOf("/")+1));
      }
      // If it is a whole number ( this method doesn't need to worry about mixed numbers,
      // since it is only called for improper fractions)
      else{
         return 1;
      }
   } 

   // The getGCF method returns the greatest commmon factor of two numbers
   //  It is used for reducing fractions to lowest tems
   // getGCF takes 2 parameters
   // These inputs are the two numbers it takes a GCF of
   // return type is int, since it needs to return an integer (GCF of the numbers)

   public static int getGCF(int a, int b){

      // Sets gcf to 1 initially
      int gcf = 1;

      // For all value less than the lower number of a and b
      // If any number i is divisible by both a and b, it sets gcf to i
      for (int i=1; i<=Math.min(a,b); i++){
         if (a%i == 0 && b%i == 0){
            gcf = i;
         }
      }
      // Returns gcf
      return gcf;
   }
   
   // The getImproperFrac method returns a mixed number as an improper fraction
   // getImproperFrac takes 1 parameter
   // String input - The method turns the value of this parameter as an improper fraction
   // return type is String, since it needs to return a fraction (such as 72/15, 56/11, etc.)

   public static String getImproperFrac(String input){

      // Initalizes variables by calling respective methods
      int whole = getWhole(input);
      int num = getNum(input);
      int den = getDen(input);

      // If denominator is 0, it prevents an error by returning 0/0 which later gets handled by the formatFraction method
      if (den==0){
         return "0/0";
      }
      // Gets improper fraction
      int improperNum = Math.abs(whole) * den + num;

      // Handles the case if whole number is negative
      if (whole<0){
         improperNum *=-1;
      }
      // Handles the case if denominator
      if (den<0){
         den*=-1;
         improperNum *=-1;
      }
      
      // Returns the improper fraction as a String
      return improperNum + "/" + den;
   }

   // The performOperations method handles most of the math in the program
   // getImproperFrac takes 5 parameters
   // int num1 - This is the numerator of the first fraction
   // int num2 - This is the numerator of the second fraction
   // int den1 - This is the denominator of the first fraction
   // int den2 - This is the denominator of the second fraction
   // String operator - This is the operation that needs to be performed (+, -, *, /)
   // return type is String, since it needs to return a fraction after operations are performed

   public static String performOperations(int num1, int num2, int den1, int den2, String operator){
      
      // Initalizes numerator and denominator
      int num;
      int den;

      // Performs math for each operator
      // Assigns values to num and den based on the values of the fraction
      if (operator.equals("+")){ // addition
         num = num1 * den2 + num2 * den1;
         den = den1 * den2;
      } else if (operator.equals("-")){ // subtraction
         num = num1 * den2 - num2 * den1;
         den = den1 * den2;
      } else if (operator.equals("*")){ // multiplication
         num = num1 * num2;
         den = den1 * den2;
      } else{ // division
         // If any denominators are 0, it returns 0/0 (preventing an error) which is later handled by formatFraction
         if (den1 == 0 || den2==0){
            return "0/0";
         }
         // Otherwise, it performs division like normal
         num = num1 * den2;
         den = den1 * num2;
      }

      // Returns an improperFraction as a String
      return num + "/" + den;
   }

   // The getOperator method returns the operation which needs to be performed (+, -, *, /)
   // getImproperFrac takes 1 parameters
   // String input - This is the user's input containing both fractions and an operator (for example: -2_1/4 + 4_1/3)
   // return type is String, since it needs to return an operator

   public static String getOperator(String input){

      // Finds the index position of the first space
      int space1 = input.indexOf(" ");

      // Takes a substring from space1+1 to space1+2, which saves the character directly after space1 as the operator
      String operator = input.substring(space1 + 1, space1 + 2);

      // returns operator as a String
      return operator;

   }

   // The getFirstNumber method returns the first number from the user's input
   // getFirstNumber takes 1 parameters
   // String input - This is the user's input containing both fractions and an operator (for example: -2_1/4 + 4_1/3)
   // return type is String, since it needs to return the first number from the input
   public static String getFirstNumber(String input){

      // Finds the index position of the first space
      int space1 = input.indexOf(" ");

      // Takes a substring from 0 to space1, which will give all characters before space1
      // Assigns it to firstNumber
      String firstNumber = input.substring(0, space1);

      // Returns firstNumber as a String
      return firstNumber;
   }

   // The getSecondNumber method returns the second number from the user's input
   // getSecondNumber takes 1 parameter
   // String input - This is the user's input containing both fractions and an operator (for example: -2_1/4 + 4_1/3)
   // return type is String, since it needs to return the second number from the input
   public static String getSecondNumber(String input){

      // Finds the index position of the first space
      int space1 = input.indexOf(" ");

      // Assuming user's input is well formed, space 2 is 2 characters after space 1
      // Assigns space 2 to space1 + 2
      int space2 = space1 + 2;

      // The second number is anything after space2
      // Assigns any characters after space2 to secondNumber
      String secondNumber = input.substring(space2 + 1);

      // Returns secondNumber as a String
      return secondNumber;
   }

   // The getNumerator method returns the numerator of a fraction
   // getNumerator takes 1 parameter
   // String frac - This is the improper fraction returned by the getImproperFrac method
   // This method returns the numerator of that fraction
   // return type is int, since it needs to return the numerator

   public static int getNumerator(String frac) {

      // Finds the index position of the slash character and stores it in indexOfSlash
      int indexOfSlash = frac.indexOf("/");

      // Takes a substring from 0 to the slash character, which is equivalent to the numerator
      // Assigns it to the String numerator
      String numerator = frac.substring(0, indexOfSlash);

      // Returns numerator as an integer (Implicitly casts from String to int)
      return Integer.parseInt(numerator);
   }

   // The getDenominator method returns the denominator of a fraction
   // getDenominator takes 1 parameter
   // String frac - This is the improper fraction returned by the getImproperFrac method
   // This method returns the denominator of that fraction
   // return type is int, since it needs to return the denominator

   public static int getDenominator(String frac) {

      // Finds the index position of the slash character and stores it in indexOfSlash
      int indexOfSlash = frac.indexOf("/");

      // Takes a substring from the slash chracter to the end which is equivalent to the denominator
      // Assigns it to the String denominator
      String denominator = frac.substring(indexOfSlash+ 1);

      // Returns denominator as an integer (Implicitly casts from String to int)
      return Integer.parseInt(denominator);
   }
   
   // The formatFraction method returns the finalFraction, after handling all exceptions and simplifying to lowest terms
   // formatFraction takes 2 parameters
   // int finalNum - This variable stores the numerator of the final fraction
   // int finalDen - This variable stores the denominator of the final fraction
   // return type is String, since it returns the final fraction

   public static String formatFraction(int finalNum, int finalDen) {

      // Prevents division by 0
      if (finalDen == 0) {
         return "ERROR: Division by zero";
      }
      // If answer is a whole number, returns 0
      if (finalNum == 0) {
         return "0";
      }
      // Normalizes fraction by preventing negative denominators
      if (finalDen < 0) {
         finalDen *= -1;
         finalNum *= -1;
      }
      // Simplifies to lowest terms by finding gcf & diving finalNum and finalDen by gcf
      int gcf = getGCF(Math.abs(finalNum), Math.abs(finalDen));
      finalNum /= gcf;
      finalDen /= gcf;

      // Rewrites improper fraction as mixed number
      int whole = finalNum / finalDen;
      int remainder = Math.abs(finalNum) % finalDen;

      // Calls the handleEdgeCases and assigns result to String output
      String output = handleEdgeCases(whole, remainder, finalDen, finalNum);

      // Returns the final output
      return output;
   }

   // The handleEdgeCases method handles a few edge cases 
   // when converting the improper fraction back to a mixed number
   // handleEdgeCases takes 4 parameters
   // int whole - This variable stores the whole number part of the final fraction
   // int remainder - This variable stores the remainder (which is actually just the final numerator)
   // int finalNum - This variable stores the numerator of the improper fraction
   // int finalDen - This variable stores the denominator of the final fraction
   // return type is String, since it returns the final fraction

   public static String handleEdgeCases(int whole, int remainder, int finalDen, int finalNum) {
      
      // If the answer is just a whole number, it only returns the whole
      if (remainder == 0) {
         // returns whole as a String (implicitly casts from int to String)
         return whole + "";
      }

      // If there is no whole number (just a fraction)
      if (whole == 0) {

         // If the numerator is negative, it adds a minus sign
         if (finalNum < 0) {
               return "-" + remainder + "/" + finalDen;
         } 
         // Else, it just returns the fraction without a minus sign
         else {
               return remainder + "/" + finalDen;
         }
      }

      // Returns the final fraction
      return whole + " " + remainder + "/" + finalDen;
   }

   
   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
   
      // Gives the user some helpful tips to use the calculator easily
      String help = "\nFraction Calculator Help\n";
      help+= "Enter 2 numbers seperated by an arithmetic operator \n";
      help+= "Make sure to put underscores to seperate mixed numbers\n";
      help+= "Make sure to seperate fractions with slashes\n";
      help+= "Try to format the mixed numbers in the following form: 2_1/2, 3/4, etc. For example, -2_1/4 + 2_1/2.";
      help+= "Commands: 'help' - show help, 'quit' - exit program";

      return help;
   }
}