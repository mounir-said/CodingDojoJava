package com.Calculator;

public class CalculatorTest {
 public static void main(String[] args) {
     Calculator calculator = new Calculator();
     
     calculator.performOperation(10.5);
     calculator.performOperation("+");
     calculator.performOperation(5.2);
     calculator.performOperation("*");
     calculator.performOperation(10);
     calculator.performOperation("=");
     System.out.println("Result: " + calculator.getResults()); // Should print 62.5
 }
}

