public class Stack {
    private Object[] stack;
    private int top;
    private int size;

    // Constructor to initialize the stack with a fixed capacity
    public Stack(int capacity) {
        stack = new Object[capacity];
        top = -1; // Indicates that the stack is empty
        size = 0;
    }

    // Push an element onto the stack
    public void push(Object value) {
        if (size == stack.length) {
            // Stack is full; ignore the push operation
            return;
        }
        stack[++top] = value;
        size++;
    }

    // Pop an element from the stack
    public Object pop() {
        if (isEmpty()) {
            return null; // Stack is empty
        }
        Object value = stack[top--];
        size--;
        return value;
    }

    // Peek at the top element of the stack without removing it
    public Object peek() {
        if (isEmpty()) {
            return null; // Stack is empty
        }
        return stack[top];
    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Get the current size of the stack
    public int size() {
        return size;
    }
}

// Example usage
public class Main {
    public static void main(String[] args) {
        Stack stack = new Stack(10); // Create a stack with a capacity of 10

        // Test pushing elements
        stack.push("Hello");
        stack.push(123);
        stack.push(45.67);

        // Test peeking and popping elements
        System.out.println("Top element is: " + stack.peek()); // Output: 45.67
        System.out.println("Stack size is: " + stack.size()); // Output: 3

        System.out.println("Popped element is: " + stack.pop()); // Output: 45.67
        System.out.println("Stack size after pop: " + stack.size()); // Output: 2

        // Test popping from an empty stack
        stack.pop(); // Pop remaining elements
        stack.pop(); // Pop from empty stack
        System.out.println("Popped from empty stack: " + stack.pop()); // Output: null
    }
}
