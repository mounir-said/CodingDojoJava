import java.util.ArrayList;

public class CafeUtil {

    // Method to calculate the streak goal
    public int getStreakGoal() {
        int sum = 0;
        for (int i = 1; i <= 10; i++) {
            sum += i;
        }
        return sum;
    }

    // Method with parameter for streak goal
    public int getStreakGoal(int numWeeks) {
        int sum = 0;
        for (int i = 1; i <= numWeeks; i++) {
            sum += i;
        }
        return sum;
    }

    // Method to calculate the total order price
    public double getOrderTotal(double[] prices) {
        double total = 0;
        for (double price : prices) {
            total += price;
        }
        return total;
    }

    // Method to display the menu
    public void displayMenu(ArrayList<String> menuItems) {
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.println(i + " " + menuItems.get(i));
        }
    }

    // Method to add a customer
    public void addCustomer(ArrayList<String> customers) {
        System.out.println("Please enter your name:");
        String username = System.console().readLine();
        System.out.println("Hello, " + username + "!");
        System.out.println("There are " + customers.size() + " people in front of you");
        customers.add(username);
        System.out.println(customers);
    }

    // Ninja Bonus 1: Method to print price chart
    public void printPriceChart(String product, double price, int maxQuantity) {
        System.out.println(product);
        for (int i = 1; i <= maxQuantity; i++) {
            System.out.printf("%d - $%.2f\n", i, price * i);
        }
    }

    // Ninja Bonus 3: Overloaded displayMenu method
    public boolean displayMenu(ArrayList<String> menuItems, ArrayList<Double> prices) {
        if (menuItems.size() != prices.size()) {
            return false;
        }
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.printf("%d %s -- $%.2f\n", i, menuItems.get(i), prices.get(i));
        }
        return true;
    }

    // Ninja Bonus 4: Method to add multiple customers
    public void addCustomers(ArrayList<String> customers) {
        while (true) {
            System.out.println("Please enter customer name (or type 'q' to quit):");
            String customer = System.console().readLine();
            if (customer.equals("q")) {
                break;
            }
            customers.add(customer);
            System.out.println("Added: " + customer);
        }
        System.out.println("Customer list: " + customers);
    }
}
