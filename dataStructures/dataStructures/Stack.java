public class Stack {
  private Node top;
  private int size;

  private class Node {
      Object data;
      Node next;

      Node(Object data) {
          this.data = data;
          this.next = null;
      }
  }

  public Stack() {
      this.top = null;
      this.size = 0;
  }

  public void push(Object data) {
      Node newNode = new Node(data);
      newNode.next = top;
      top = newNode;
      size++;
  }

  public Object pop() {
      if (top == null) {
          throw new IllegalStateException("Stack is empty");
      }
      Object data = top.data;
      top = top.next;
      size--;
      return data;
  }

  public Object peek() {
      if (top == null) {
          throw new IllegalStateException("Stack is empty");
      }
      return top.data;
  }

  public boolean isEmpty() {
      return size == 0;
  }

  public int size() {
      return size;
  }

  public static void main(String[] args) {
      Stack stack = new Stack();
      stack.push(1);
      stack.push(2);
      stack.push(3);
      System.out.println(stack.pop()); // Output: 3
      System.out.println(stack.peek()); // Output: 2
      System.out.println(stack.isEmpty()); // Output: false
      System.out.println(stack.size()); // Output: 2
  }
}
