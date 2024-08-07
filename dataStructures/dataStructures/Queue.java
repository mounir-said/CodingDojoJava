public class Queue {
  private Node front;
  private Node rear;
  private int size;

  private class Node {
      Object data;
      Node next;

      Node(Object data) {
          this.data = data;
          this.next = null;
      }
  }

  public Queue() {
      this.front = null;
      this.rear = null;
      this.size = 0;
  }

  public void enqueue(Object data) {
      Node newNode = new Node(data);
      if (rear == null) {
          front = rear = newNode;
      } else {
          rear.next = newNode;
          rear = newNode;
      }
      size++;
  }

  public Object dequeue() {
      if (front == null) {
          throw new IllegalStateException("Queue is empty");
      }
      Object data = front.data;
      front = front.next;
      if (front == null) {
          rear = null;
      }
      size--;
      return data;
  }

  public Object peek() {
      if (front == null) {
          throw new IllegalStateException("Queue is empty");
      }
      return front.data;
  }

  public boolean isEmpty() {
      return size == 0;
  }

  public int size() {
      return size;
  }

  public static void main(String[] args) {
      Queue queue = new Queue();
      queue.enqueue(1);
      queue.enqueue(2);
      queue.enqueue(3);
      System.out.println(queue.dequeue()); // Output: 1
      System.out.println(queue.peek()); // Output: 2
      System.out.println(queue.isEmpty()); // Output: false
      System.out.println(queue.size()); // Output: 2
  }
}
