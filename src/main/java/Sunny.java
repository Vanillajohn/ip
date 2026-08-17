import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Sunny {
        public static void main(String[] args) {

        List<String> greetings = List.of("Yeah, it's me, Sunny.\nWhat do you want?", "What now?\nCan you go bother someone else?");
        List<String> goodbyes = List.of("Ugh, I can't believe I helped you.", "See you never.");
        
        System.out.println("____________________________________________________________");
        speak(greetings);
        speak(goodbyes);
        System.out.println("____________________________________________________________");
    }

    private static void speak(List<String> greetings) {
            Random rand = new Random();
            int index = rand.nextInt(greetings.size());
            System.out.println("____________________________________________________________");
            System.out.println(greetings.get(index));
    }
}
