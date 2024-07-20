package com.dataStructures;


public class DLLTester {
 public static void main(String[] args) {
     DoublyLinkedList dll = new DoublyLinkedList();
     
     dll.add(1);
     dll.add(2);
     dll.add(3);
     dll.add(2);
     dll.add(1);

     System.out.println("List values:");
     dll.printValuesBackward();

     System.out.println("Popped value: " + dll.pop().value);

     System.out.println("Contains 2: " + dll.contains(2));
     System.out.println("Contains 5: " + dll.contains(5));

     System.out.println("List size: " + dll.size());

     Node newNode = new Node(4);
     dll.insertAt(newNode, 2);
     System.out.println("List after inserting 4 at index 2:");
     dll.printValuesBackward();

     dll.removeAt(1);
     System.out.println("List after removing node at index 1:");
     dll.printValuesBackward();

     System.out.println("Is palindrome: " + dll.isPalindrome());
 }
}
