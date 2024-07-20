package com.dataStructures;


public class ListTester {
 public static void main(String[] args) {
     SinglyLinkedList sll = new SinglyLinkedList();
     sll.add(3);
     sll.add(4);
     sll.add(10);
     sll.add(5);
     sll.add(15);
     sll.add(2);

     System.out.println("Initial List:");
     sll.printValues();

     sll.remove();
     sll.remove();

     System.out.println("List after removing two elements:");
     sll.printValues();

     System.out.println("Finding value 10:");
     Node foundNode = sll.find(10);
     if (foundNode != null) {
         System.out.println("Found: " + foundNode.value);
     } else {
         System.out.println("Value not found.");
     }

     sll.removeAt(1);

     System.out.println("List after removing the element at index 1:");
     sll.printValues();
 }
}
