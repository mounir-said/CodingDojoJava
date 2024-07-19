public class DeviceTest {
  public static void main(String[] args) {
      Phone myPhone = new Phone();

      // Make three phone calls
      myPhone.makeCall();
      myPhone.makeCall();
      myPhone.makeCall();

      // Play a game twice
      myPhone.playGame();
      myPhone.playGame();

      // Charge the phone
      myPhone.charge();
  }
}
