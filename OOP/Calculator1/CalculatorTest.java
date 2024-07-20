package com.Calculator;


public class CalculatorTest {
 public static void main(String[] args) {
     Calculator calculator = new Calculator();
     
     // Test Addition
     calculator.setOperandOne(10.5);
     calculator.setOperandTwo(5.2);
     calculator.setOperation("+");
     calculator.performOperation();
     System.out.println("Addition Result: " + calculator.getResults()); // Should print 15.7

     // Test Subtraction
     calculator.setOperandOne(10.5);
     calculator.setOperandTwo(5.2);
     calculator.setOperation("-");
     calculator.performOperation();
     System.out.println("Subtraction Result: " + calculator.getResults()); // Should print 5.3
 }
}
