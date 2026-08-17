import java.util.Random;
import java.util.List;
import java.util.Scanner;

public class Sunny {
        public static void main(String[] args) {

        List<String> greetings = List.of("Yeah, it's me, Sunny.\nWhat do you want?", "What now?\nCan you go bother someone else?");
        List<String> goodbyes = List.of("Ugh, I can't believe I helped you.", "See you never.");
        List<String> echoRemarks = List.of("", "", "Is this funny to you?", "What a waste of time.");
        Scanner scanner = new Scanner(System.in);

        System.out.println("____________________________________________________________");
        speak(greetings);
        System.out.println("____________________________________________________________");

        while (true){
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                scanner.close();
                break;
            }
            System.out.println(input);
            speak(echoRemarks);
            System.out.println("____________________________________________________________");
        }

        System.out.println("____________________________________________________________");
        speak(goodbyes);
        System.out.println("____________________________________________________________");
    }

    private static void speak(List<String> greetings) {
            Random rand = new Random();
            int index = rand.nextInt(greetings.size());
            System.out.println(greetings.get(index));
    }
}
