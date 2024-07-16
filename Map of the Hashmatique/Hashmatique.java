<<<<<<< HEAD
<<<<<<< HEAD
import java.util.HashMap;
import java.util.Map;

public class Hashmatique {
    public static void main(String[] args) {
        // Create a HashMap to store track titles and lyrics
        HashMap<String, String> trackList = new HashMap<String, String>();

        // Add at least 4 songs to the trackList
        trackList.put("Bohemian Rhapsody", "Is this the real life? Is this just fantasy?");
        trackList.put("Hotel California", "Welcome to the Hotel California. Such a lovely place.");
        trackList.put("Stairway to Heaven", "There's a lady who's sure all that glitters is gold.");
        trackList.put("Imagine", "Imagine all the people living life in peace.");

        // Pull out one of the songs by its track title
        String track = "Hotel California";
        String lyrics = trackList.get(track);
        System.out.println("Track: " + track + ", Lyrics: " + lyrics);

        // Print out all the track names and lyrics in the format 'Track: Lyrics'
        for (Map.Entry<String, String> entry : trackList.entrySet()) {
            System.out.println("Track: " + entry.getKey() + ", Lyrics: " + entry.getValue());
        }
    }
}
=======
import java.util.HashMap;
import java.util.Map;

public class Hashmatique {
    public static void main(String[] args) {
        // Create a HashMap to store track titles and lyrics
        HashMap<String, String> trackList = new HashMap<String, String>();

        // Add at least 4 songs to the trackList
        trackList.put("Bohemian Rhapsody", "Is this the real life? Is this just fantasy?");
        trackList.put("Hotel California", "Welcome to the Hotel California. Such a lovely place.");
        trackList.put("Stairway to Heaven", "There's a lady who's sure all that glitters is gold.");
        trackList.put("Imagine", "Imagine all the people living life in peace.");

        // Pull out one of the songs by its track title
        String track = "Hotel California";
        String lyrics = trackList.get(track);
        System.out.println("Track: " + track + ", Lyrics: " + lyrics);

        // Print out all the track names and lyrics in the format 'Track: Lyrics'
        for (Map.Entry<String, String> entry : trackList.entrySet()) {
            System.out.println("Track: " + entry.getKey() + ", Lyrics: " + entry.getValue());
        }
    }
}
>>>>>>> 02642f764018d800f456aa4d27eeaef442bb6628
=======
import java.util.HashMap;
import java.util.Map;

public class Hashmatique {
    public static void main(String[] args) {
        // Create a HashMap to store track titles and lyrics
        HashMap<String, String> trackList = new HashMap<String, String>();

        // Add at least 4 songs to the trackList
        trackList.put("Bohemian Rhapsody", "Is this the real life? Is this just fantasy?");
        trackList.put("Hotel California", "Welcome to the Hotel California. Such a lovely place.");
        trackList.put("Stairway to Heaven", "There's a lady who's sure all that glitters is gold.");
        trackList.put("Imagine", "Imagine all the people living life in peace.");

        // Pull out one of the songs by its track title
        String track = "Hotel California";
        String lyrics = trackList.get(track);
        System.out.println("Track: " + track + ", Lyrics: " + lyrics);

        // Print out all the track names and lyrics in the format 'Track: Lyrics'
        for (Map.Entry<String, String> entry : trackList.entrySet()) {
            System.out.println("Track: " + entry.getKey() + ", Lyrics: " + entry.getValue());
        }
    }
}
>>>>>>> c476ca249ee6d09e22b01edece3f1b50367937bd
