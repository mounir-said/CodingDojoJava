public class CafeJava {
    public static void main(String[] args) {
        // APP VARIABLES
        // Lines of text that will appear in the app.
        String generalGreeting = "Welcome to Cafe Java, ";
        String pendingMessage = ", your order will be ready shortly";
        String readyMessage = ", your order is ready";
        String displayTotalMessage = "Your total is $";

        // Menu variables
        double mochaPrice = 3.5;
        double dripCoffeePrice = 2.5;
        double lattePrice = 4.0;
        double cappuccinoPrice = 4.5;

        // Customer name variables
        String customer1 = "Cindhuri";
        String customer2 = "Sam";
        String customer3 = "Jimmy";
        String customer4 = "Noah";

        // Order completions
        boolean isReadyOrder1 = false;
        boolean isReadyOrder2 = true;
        boolean isReadyOrder3 = false;
        boolean isReadyOrder4 = true;

        // APP INTERACTION SIMULATION
        System.out.println(generalGreeting + customer1); // Displays "Welcome to Cafe Java, Cindhuri"
        // Cindhuri ordered a coffee. Based on her order status, print the correct status message to the console.
        if (isReadyOrder1) {
            System.out.println(customer1 + readyMessage);
        } else {
            System.out.println(customer1 + pendingMessage);
        }

        // Noah ordered a cappuccino. Use an if statement to check the status of his order and print the correct status message.
        System.out.println(generalGreeting + customer4); // Displays "Welcome to Cafe Java, Noah"
        if (isReadyOrder4) {
            System.out.println(customer4 + readyMessage);
            System.out.println(displayTotalMessage + cappuccinoPrice);
        } else {
            System.out.println(customer4 + pendingMessage);
        }

        // Sam just ordered 2 lattes, print the message to display their total.
        System.out.println(generalGreeting + customer2); // Displays "Welcome to Cafe Java, Sam"
        double samTotal = lattePrice * 2;
        System.out.println(displayTotalMessage + samTotal);
        // Use an if statement to print the appropriate order status message.
        if (isReadyOrder2) {
            System.out.println(customer2 + readyMessage);
        } else {
            System.out.println(customer2 + pendingMessage);
        }

        // Jimmy just realized he was charged for a coffee but had ordered a latte.
        System.out.println(generalGreeting + customer3); // Displays "Welcome to Cafe Java, Jimmy"
        double jimmyTotal = lattePrice - dripCoffeePrice;
        System.out.println(displayTotalMessage + jimmyTotal);
    }
}

