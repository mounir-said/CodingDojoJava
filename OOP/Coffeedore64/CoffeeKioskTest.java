public class CoffeeKioskTest {
  public static void main(String[] args) {
      CoffeeKiosk kiosk = new CoffeeKiosk();
      
      kiosk.addMenuItem("Banana", 2.00);
      kiosk.addMenuItem("Coffee", 1.50);
      kiosk.addMenuItem("Latte", 4.50);
      kiosk.addMenuItem("Cappuccino", 3.00);
      kiosk.addMenuItem("Muffin", 4.00);
      
      kiosk.newOrder();
  }
}
