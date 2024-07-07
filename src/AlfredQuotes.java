import java.util.Date;
import java.text.SimpleDateFormat;

public class AlfredQuotes {

    public String basicGreeting() {
        return "Hello, lovely to see you. How are you?";
    }

    public String guestGreeting(String name) {
        return String.format("Hello, %s. Lovely to see you.", name);
    }

    public String dateAnnouncement() {
        Date date = new Date();
        return "It is currently " + date.toString();
    }

    public String respondBeforeAlexis(String conversation) {
        if (conversation.indexOf("Alexis") > -1) {
            return "Right away, sir. She certainly isn't sophisticated enough for that.";
        } else if (conversation.indexOf("Alfred") > -1) {
            return "At your service. As you wish, naturally.";
        } else {
            return "Right. And with that I shall retire.";
        }
    }

    // Ninja Bonus: Overloaded guestGreeting method
    public String guestGreeting(String name, String dayPeriod) {
        return String.format("Good %s, %s. Lovely to see you.", dayPeriod, name);
    }

    // Sensei Bonus: Overloaded guestGreeting method using Date
    public String guestGreeting() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(dateFormat.format(date));
        String dayPeriod;

        if (hour < 12) {
            dayPeriod = "morning";
        } else if (hour < 18) {
            dayPeriod = "afternoon";
        } else {
            dayPeriod = "evening";
        }

        return String.format("Good %s. Lovely to see you.", dayPeriod);
    }

    // Sensei Bonus: Custom method using String methods
    public String alfredYell(String phrase) {
        return phrase.toUpperCase() + "!!!";
    }
}
