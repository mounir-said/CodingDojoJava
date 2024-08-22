public class Queue {
    private Object[] queue;
    private int head;
    private int tail;
    private int size;
    private int capacity;

    // Constructor to initialize the queue with a default capacity
    public Queue(int capacity) {
        this.capacity = capacity;
        queue = new Object[capacity];
        head = 0;
        tail = -1;
        size = 0;
    }

    // Enqueue an element to the end of the queue
    public boolean enqueue(Object value) {
        if (size == capacity) {
            return false; // Queue is full
        }
        tail = (tail + 1) % capacity;
        queue[tail] = value;
        size++;
        return true;
    }

    // Dequeue an element from the front of the queue
    public Object dequeue() {
        if (isEmpty()) {
            return null; // Queue is empty
        }
        Object value = queue[head];
        head = (head + 1) % capacity;
        size--;
        return value;
    }

    // Peek at the front element of the queue without removing it
    public Object peek() {
        if (isEmpty()) {
            return null; // Queue is empty
        }
        return queue[head];
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Get the current size of the queue
    public int size() {
        return size;
    }

    // Example usage
    public static void main(String[] args) {
        Queue queue = new Queue(10); // Create a queue with a capacity of 10

        // Test enqueuing elements
        System.out.println("Enqueue 'Hello': " + queue.enqueue("Hello")); // Output: true
        System.out.println("Enqueue 123: " + queue.enqueue(123)); // Output: true
        System.out.println("Enqueue 45.67: " + queue.enqueue(45.67)); // Output: true

        // Test peeking and dequeuing elements
        System.out.println("Front element is: " + queue.peek()); // Output: Hello
        System.out.println("Queue size is: " + queue.size()); // Output: 3

        System.out.println("Dequeued element is: " + queue.dequeue()); // Output: Hello
        System.out.println("Queue size after dequeue: " + queue.size()); // Output: 2

        // Test dequeuing from an empty queue
        queue.dequeue(); // Dequeue remaining elements
        queue.dequeue(); // Dequeue from empty queue
        System.out.println("Dequeued from empty queue: " + queue.dequeue()); // Output: null
    }
}
