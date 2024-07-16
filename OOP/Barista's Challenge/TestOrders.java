public class TestOrders {
  public static void main(String[] args) {
      
      // Create 2 orders for unspecified guests
      Order order1 = new Order();
      Order order2 = new Order();
      
      // Create 3 orders using the overloaded constructor
      Order order3 = new Order("Alice");
      Order order4 = new Order("Bob");
      Order order5 = new Order("Charlie");
      
      // Create items
      Item item1 = new Item("drip coffee", 1.50);
      Item item2 = new Item("cappuccino", 3.50);
      Item item3 = new Item("latte", 4.00);
      Item item4 = new Item("mocha", 4.50);
      Item item5 = new Item("espresso", 2.50);
      
      // Add items to the orders
      order1.addItem(item1);
      order1.addItem(item2);
      
      order2.addItem(item3);
      order2.addItem(item4);
      
      order3.addItem(item1);
      order3.addItem(item5);
      
      order4.addItem(item2);
      order4.addItem(item4);
      
      order5.addItem(item3);
      order5.addItem(item5);
      
      // Test getStatusMessage method
      order1.setReady(true);
      order2.setReady(false);
      order3.setReady(true);
      order4.setReady(false);
      order5.setReady(true);
      
      System.out.println(order1.getStatusMessage());
      System.out.println(order2.getStatusMessage());
      System.out.println(order3.getStatusMessage());
      System.out.println(order4.getStatusMessage());
      System.out.println(order5.getStatusMessage());
      
      // Test getOrderTotal method
      System.out.println(order1.getOrderTotal());
      System.out.println(order2.getOrderTotal());
      System.out.println(order3.getOrderTotal());
      System.out.println(order4.getOrderTotal());
      System.out.println(order5.getOrderTotal());
      
      // Test display method
      order1.display();
      order2.display();
      order3.display();
      order4.display();
      order5.display();
  }
}
