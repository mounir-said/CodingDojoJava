import java.util.ArrayList;

public class ListsOfExceptions {
    public static void main(String[] args) {
        // Create an ArrayList with both numbers and strings in it
        ArrayList<Object> myList = new ArrayList<Object>();
        myList.add("13");
        myList.add("hello world");
        myList.add(48);
        myList.add("Goodbye World");

        // Loop through the list and try assigning each item to an int variable
        for (int i = 0; i < myList.size(); i++) {
            try {
                Integer castedValue = (Integer) myList.get(i);
                System.out.println("Casted value: " + castedValue);
            } catch (ClassCastException e) {
                System.out.println("ClassCastException: " + e.getMessage());
                System.out.println("Index: " + i);
                System.out.println("Value: " + myList.get(i));
            }
        }
    }
}
