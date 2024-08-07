public class LinkedList {
  private Node head;
  private int size;

  // Node class
  private class Node {
      Object data;
      Node next;

      Node(Object data) {
          this.data = data;
          this.next = null;
      }
  }

  public LinkedList() {
      this.head = null;
      this.size = 0;
  }

  // Add element to the end of the list
  public void add(Object data) {
      Node newNode = new Node(data);
      if (head == null) {
          head = newNode;
      } else {
          Node current = head;
          while (current.next != null) {
              current = current.next;
          }
          current.next = newNode;
      }
      size++;
  }

  // Insert element at a specific index
  public void insert(int index, Object data) {
      if (index < 0 || index > size) {
          throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      }

      Node newNode = new Node(data);
      if (index == 0) {
          newNode.next = head;
          head = newNode;
      } else {
          Node current = head;
          for (int i = 0; i < index - 1; i++) {
              current = current.next;
          }
          newNode.next = current.next;
          current.next = newNode;
      }
      size++;
  }

  // Delete element from the list
  public void delete(Object data) {
      if (head == null) {
          throw new IllegalArgumentException("Element not found: " + data);
      }

      if (head.data.equals(data)) {
          head = head.next;
          size--;
          return;
      }

      Node current = head;
      while (current.next != null && !current.next.data.equals(data)) {
          current = current.next;
      }

      if (current.next == null) {
          throw new IllegalArgumentException("Element not found: " + data);
      }

      current.next = current.next.next;
      size--;
  }

  // Search for an element and return its index
  public int search(Object data) {
      Node current = head;
      int index = 0;
      while (current != null) {
          if (current.data.equals(data)) {
              return index;
          }
          current = current.next;
          index++;
      }
      return -1;
  }

  // Check if the list is empty
  public boolean isEmpty() {
      return size == 0;
  }

  // Get the size of the list
  public int size() {
      return size;
  }

  // Convert the list to a string representation
  public String toString() {
      StringBuilder string = new StringBuilder();
      Node current = head;
      while (current != null) {
          string.append(current.data).append(" -> ");
          current = current.next;
      }
      if (string.length() > 0) {
          string.setLength(string.length() - 4); // Remove the last " -> "
      }
      return string.toString();
  }

  public static void main(String[] args) {
      LinkedList ll = new LinkedList();
      ll.add(1);
      ll.add(2);
      ll.add(3);
      System.out.println(ll); // Output: 1 -> 2 -> 3

      ll.insert(1, 4);
      System.out.println(ll); // Output: 1 -> 4 -> 2 -> 3

      ll.delete(2);
      System.out.println(ll); // Output: 1 -> 4 -> 3

      System.out.println(ll.search(4)); // Output: 1
      System.out.println(ll.isEmpty()); // Output: false

      ll.delete(1);
      ll.delete(4);
      ll.delete(3);
      System.out.println(ll.isEmpty()); // Output: true
  }
}
