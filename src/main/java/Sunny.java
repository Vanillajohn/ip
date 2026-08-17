import java.util.Random;
import java.util.List;
import java.util.Scanner;

public class Sunny {
        public static void main(String[] args) {

        List<String> greetings = List.of("Yeah, it's me, Sunny.\nWhat do you want?", "What now?\nCan you go bother someone else?");
        List<String> goodbyes = List.of("Ugh, I can't believe I helped you.", "See you never.");
        List<String> taskRemarks = List.of("", "I'm only helping you because you now owe me", "I could be doing so much more right now.",
                                           "What a waste of time.", "Can't you do this yourself?");
        List<String> taskMark = List.of("Yeah, yeah. Marked it already", "Done. Can you leave me alone now?");
        List<String> taskUnmark = List.of("Can't you do this yourself?", "Done. Can you leave me alone now?");
        List<String> listRemarks = List.of("Here's your LL (lame list)", "Go write this down so I don't have to show it to you again");
        Task[] tasks = new Task[101];
        int taskCount = 1;
        Scanner scanner = new Scanner(System.in);

        System.out.println("____________________________________________________________");
        speak(greetings);
        System.out.println("____________________________________________________________");

        while (true){
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String command = parts[0];

            switch (command){
                case "bye":
                    scanner.close();
                    System.out.println("____________________________________________________________");
                    speak(goodbyes);
                    System.out.println("____________________________________________________________");
                    return;
                case "list":
                    int i = 1;
                    System.out.println("____________________________________________________________");
                    speak(listRemarks);
                    while (tasks[i] != null) {
                        System.out.println(i + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].getDesc());
                        i += 1;
                    }
                    System.out.println("____________________________________________________________");
                    continue;
                case "mark":
                    int index1 = Integer.parseInt(parts[1]);
                    tasks[index1].mark();
                    System.out.println("____________________________________________________________");
                    speak(taskMark);
                    System.out.println(index1 + ".[" + tasks[index1].getStatusIcon() + "] " + tasks[index1].getDesc());
                    System.out.println("____________________________________________________________");
                    continue;
                case "unmark":
                    int index2 = Integer.parseInt(parts[1]);
                    tasks[index2].unmark();
                    System.out.println("____________________________________________________________");
                    speak(taskUnmark);
                    System.out.println(index2 + ".[" + tasks[index2].getStatusIcon() + "] " + tasks[index2].getDesc());
                    System.out.println("____________________________________________________________");
                    continue;
            }
            tasks[taskCount] = new Task(input);
            taskCount += 1;
            System.out.println("added: " + input);
            speak(taskRemarks);
            System.out.println("____________________________________________________________");
        }
    }

    private static void speak(List<String> greetings) {
            Random rand = new Random();
            int index = rand.nextInt(greetings.size());
            System.out.println(greetings.get(index));
    }
}
