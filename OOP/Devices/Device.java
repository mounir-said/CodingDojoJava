public class Device {
  protected int battery;

  public Device() {
      this.battery = 100;
  }

  public void showBattery() {
      System.out.println("Battery remaining: " + this.battery);
  }
}
