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
      
   
         int space1 = input.indexOf(" ");
         String second_part = input.substring(space1+1);
         int space2 = second_part.indexOf(" ");
         String operator = input.charAt(space1+1) + "";
         String first_number = input.substring(0,space1);
         String second_number = second_part.substring(space2+1);

         int whole1 = getWhole(first_number);
         int num1 = getNum(first_number);
         int den1 = getDen(first_number);

         int whole2 = getWhole(second_number);
         int num2 = getNum(second_number);
         int den2 = getDen(second_number);

         int improper_num1 = 0;
         int improper_num2 = 0;

         improper_num1 = Math.abs(whole1) * den1 + Math.abs(num1);
         if ((whole1<0) || first_number.startsWith("-")){
            improper_num1 *=-1;
         }
         if (num1<0 && whole1 == 0){
            improper_num1 = Math.abs(num1) * -1;
         }
         if (den1<0){
            improper_num1 *=-1;
            den1 *=-1;
         }

         improper_num2 = Math.abs(whole2) * den1 + Math.abs(num2);
         if ((whole2<0) || second_number.startsWith("-")){
            improper_num2 *=-1;
         }
         if (num2<0 && whole2 == 0){
            improper_num2 = Math.abs(num2) * -1;
         }
         if (den2<0){
            improper_num2 *=-1;
            den2 *=-1;
         }
         

         int finalNum = 0;
         int finalDen = 0;

         if (operator.equals("+")){
            finalNum = improper_num1 * den2 + improper_num2 * den1;
            finalDen = den1 * den2;
         }

         if (finalNum==0){
            return "0";
         }

         int lcd = getLCD(Math.abs(finalNum), finalDen);
         if (lcd==0){
            lcd = 1;
         }

         if (finalDen !=1 && lcd !=0){
         finalNum = finalNum/lcd;
         finalDen = finalDen/lcd;
         }
         
         int whole = finalNum/finalDen;
         int remainder = Math.abs(finalNum)%finalDen;

         if (remainder==0){
            return whole + "";
         }
         else if (whole==0){

            if (finalNum<0){
            return "-" + remainder + "/" + finalDen;
            }
            else{
            return remainder + "/" + finalDen;
            }
         }
         else{
            return whole + " " + remainder + "/" + finalDen;
         }
   
         

        // return input is the description of the SECOND number
        // parsed into four parts: operator, whole number, numerator, and denominator.
         //return "Op:" + operator + " Whole:" + whole + " Num:" + num + " Den:" + den; 

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

   
      public static int getLCD(int a, int b){
         int gcf = 1;
      for (int i=1; i<=Math.min(a,b); i++){
         if (a%i == 0 && b%i == 0){
            gcf = i;
         }
      }
      return gcf;
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

