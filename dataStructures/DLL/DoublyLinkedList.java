package com.dataStructures;

public class DoublyLinkedList {
 private Node head;
 private Node tail;

 public DoublyLinkedList() {
     this.head = null;
     this.tail = null;
 }

 public void add(int value) {
     Node newNode = new Node(value);
     if (head == null) {
         head = newNode;
         tail = newNode;
     } else {
         tail.next = newNode;
         newNode.prev = tail;
         tail = newNode;
     }
 }

 public void printValuesBackward() {
     Node runner = tail;
     while (runner != null) {
         System.out.println(runner.value);
         runner = runner.prev;
     }
 }

 public Node pop() {
     if (tail == null) return null;
     Node poppedNode = tail;
     if (tail == head) {
         head = null;
         tail = null;
     } else {
         tail = tail.prev;
         tail.next = null;
     }
     poppedNode.prev = null;
     return poppedNode;
 }

 public boolean contains(int value) {
     Node runner = head;
     while (runner != null) {
         if (runner.value == value) {
             return true;
         }
         runner = runner.next;
     }
     return false;
 }

 public int size() {
     int count = 0;
     Node runner = head;
     while (runner != null) {
         count++;
         runner = runner.next;
     }
     return count;
 }

 public void insertAt(Node newNode, int index) {
     if (index == 0) {
         if (head == null) {
             head = newNode;
             tail = newNode;
         } else {
             newNode.next = head;
             head.prev = newNode;
             head = newNode;
         }
         return;
     }

     Node runner = head;
     for (int i = 0; i < index - 1; i++) {
         if (runner == null) return;
         runner = runner.next;
     }

     if (runner.next == null) {
         runner.next = newNode;
         newNode.prev = runner;
         tail = newNode;
     } else {
         newNode.next = runner.next;
         newNode.prev = runner;
         runner.next.prev = newNode;
         runner.next = newNode;
     }
 }

 public void removeAt(int index) {
     if (index == 0) {
         if (head == null) return;
         if (head.next == null) {
             head = null;
             tail = null;
         } else {
             head = head.next;
             head.prev = null;
         }
         return;
     }

     Node runner = head;
     for (int i = 0; i < index - 1; i++) {
         if (runner == null || runner.next == null) return;
         runner = runner.next;
     }

     if (runner.next == null) return;
     if (runner.next.next == null) {
         runner.next = null;
         tail = runner;
     } else {
         runner.next = runner.next.next;
         runner.next.prev = runner;
     }
 }

 public boolean isPalindrome() {
     if (head == null) return true;

     Node frontRunner = head;
     Node backRunner = tail;

     while (frontRunner != backRunner && frontRunner.prev != backRunner) {
         if (frontRunner.value != backRunner.value) {
             return false;
         }
         frontRunner = frontRunner.next;
         backRunner = backRunner.prev;
     }

     return true;
 }
}
