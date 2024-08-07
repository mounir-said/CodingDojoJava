public class DoublyLinkedList {
  private Node head;
  private Node tail;
  private int size;

  private class Node {
      Object data;
      Node prev;
      Node next;

      Node(Object data) {
          this.data = data;
          this.prev = null;
          this.next = null;
      }
  }

  public DoublyLinkedList() {
      this.head = null;
      this.tail = null;
      this.size = 0;
  }

  // Add element to the end of the list
  public void add(Object data) {
      Node newNode = new Node(data);
      if (head == null) {
          head = tail = newNode;
      } else {
          tail.next = newNode;
          newNode.prev = tail;
          tail = newNode;
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
          if (head == null) {
              head = tail = newNode;
          } else {
              newNode.next = head;
              head.prev = newNode;
              head = newNode;
          }
      } else if (index == size) {
          tail.next = newNode;
          newNode.prev = tail;
          tail = newNode;
      } else {
          Node current = head;
          for (int i = 0; i < index - 1; i++) {
              current = current.next;
          }
          newNode.next = current.next;
          current.next.prev = newNode;
          current.next = newNode;
          newNode.prev = current;
      }
      size++;
  }

  // Delete element from the list
  public void delete(Object data) {
      Node current = head;
      while (current != null && !current.data.equals(data)) {
          current = current.next;
      }

      if (current == null) {
          throw new IllegalArgumentException("Element not found: " + data);
      }

      if (current == head) {
          head = head.next;
          if (head != null) {
              head.prev = null;
          } else {
              tail = null;
          }
      } else if (current == tail) {
          tail = tail.prev;
          if (tail != null) {
              tail.next = null;
          } else {
              head = null;
          }
      } else {
          current.prev.next = current.next;
          current.next.prev = current.prev;
      }
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
          string.append(current.data).append(" <-> ");
          current = current.next;
      }
      if (string.length() > 0) {
          string.setLength(string.length() - 5); // Remove the last " <-> "
      }
      return string.toString();
  }

  public static void main(String[] args) {
      DoublyLinkedList dll = new DoublyLinkedList();
      dll.add(1);
      dll.add(2);
      dll.add(3);
      System.out.println(dll); // Output: 1 <-> 2 <-> 3

      dll.insert(1, 4);
      System.out.println(dll); // Output: 1 <-> 4 <-> 2 <-> 3

      dll.delete(2);
      System.out.println(dll); // Output: 1 <-> 4 <-> 3

      System.out.println(dll.search(4)); // Output: 1
      System.out.println(dll.isEmpty()); // Output: false

      dll.delete(1);
      dll.delete(4);
      dll.delete(3);
      System.out.println(dll.isEmpty()); // Output: true
  }
}
