package com.Calculator;


import java.util.ArrayList;
import java.util.List;

public class Calculator {
 private List<Double> operands;
 private List<String> operations;
 private double currentResult;
 private boolean isLastInputOperator;

 public Calculator() {
     this.operands = new ArrayList<>();
     this.operations = new ArrayList<>();
     this.currentResult = 0;
     this.isLastInputOperator = true; // Initially set to true to accept the first operand
 }

 public void performOperation(double operand) {
     if (isLastInputOperator) {
         operands.add(operand);
         isLastInputOperator = false;
     } else {
         System.out.println("Invalid input sequence: operand should follow an operator.");
     }
 }

 public void performOperation(String operation) {
     if (operation.equals("=")) {
         processOperations();
     } else if (operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/")) {
         if (!isLastInputOperator) {
             operations.add(operation);
             isLastInputOperator = true;
         } else {
             System.out.println("Invalid input sequence: operator should follow an operand.");
         }
     } else {
         System.out.println("Invalid operation.");
     }
 }

 public double getResults() {
     return currentResult;
 }

 private void processOperations() {
     List<Double> tempOperands = new ArrayList<>(operands);
     List<String> tempOperations = new ArrayList<>(operations);

     // Process multiplication and division first
     for (int i = 0; i < tempOperations.size(); i++) {
         String op = tempOperations.get(i);
         if (op.equals("*") || op.equals("/")) {
             double left = tempOperands.get(i);
             double right = tempOperands.get(i + 1);
             double result = (op.equals("*")) ? left * right : left / right;
             tempOperands.set(i + 1, result);
             tempOperands.remove(i);
             tempOperations.remove(i);
             i--; // Adjust the index after removal
         }
     }

     // Process addition and subtraction
     currentResult = tempOperands.get(0);
     for (int i = 0; i < tempOperations.size(); i++) {
         String op = tempOperations.get(i);
         double nextOperand = tempOperands.get(i + 1);
         if (op.equals("+")) {
             currentResult += nextOperand;
         } else if (op.equals("-")) {
             currentResult -= nextOperand;
         }
     }
 }
}
