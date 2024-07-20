package com.Calculator;


public class Calculator {
 private double operandOne;
 private double operandTwo;
 private String operation;
 private double result;

 public Calculator() {
     // Initialize default values if needed
     this.operandOne = 0;
     this.operandTwo = 0;
     this.operation = "";
     this.result = 0;
 }

 public void setOperandOne(double operandOne) {
     this.operandOne = operandOne;
 }

 public void setOperandTwo(double operandTwo) {
     this.operandTwo = operandTwo;
 }

 public void setOperation(String operation) {
     this.operation = operation;
 }

 public void performOperation() {
     switch (operation) {
         case "+":
             result = operandOne + operandTwo;
             break;
         case "-":
             result = operandOne - operandTwo;
             break;
         default:
             throw new IllegalArgumentException("Invalid operation. Please use '+' or '-'.");
     }
 }

 public double getResults() {
     return result;
 }
}
