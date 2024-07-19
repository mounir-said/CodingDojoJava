public class Phone extends Device {

  public void makeCall() {
      if (this.battery >= 5) {
          this.battery -= 5;
          System.out.println("You make a call.");
          showBattery();
      } else {
          System.out.println("Battery too low to make a call.");
      }
  }

  public void playGame() {
      if (this.battery < 25) {
          System.out.println("Battery too low to play a game.");
      } else {
          this.battery -= 20;
          System.out.println("You play a game.");
          showBattery();
      }
  }

  public void charge() {
      this.battery = Math.min(this.battery + 50, 100); // Ensure battery does not exceed 100
      System.out.println("You charge the phone.");
      showBattery();
  }

  @Override
  public void showBattery() {
      if (this.battery < 10) {
          System.out.println("Battery critical!");
      }
      super.showBattery();
  }
}
