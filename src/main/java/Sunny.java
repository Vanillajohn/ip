import java.util.Random;
import java.util.List;
import java.util.Scanner;

public class Sunny {
        public static void main(String[] args) {

        List<String> greetings = List.of("Yeah, it's me, Sunny.\nWhat do you want?", "What now?\nCan you go bother someone else?");
        List<String> goodbyes = List.of("Don't tell anyone I helped you, got it?", "See you never.", "Jeez, you really depend on me, don't you?");
        List<String> taskRemarks = List.of("", "I'm only helping you because you now owe me.", "I could be doing so much more right now.",
                                           "What a waste of time.", "Can't you do this yourself?");
        List<String> taskMark = List.of("Yeah, yeah. Marked it already", "Done. Can you leave me alone now?");
        List<String> taskUnmark = List.of("Can't you do this yourself?", "Done. Can you leave me alone now?");
        List<String> listRemarks = List.of("Here's your LL (lame list):", "Go write this down so I don't have to show it to you again:");
        List<List<String>> listNumberRemarks = List.of(List.of("Now you have ", " tasks in your list. Whoop de doo."), List.of("Go do your ", " tasks already!"));
        List<String> taskAddRemarks = List.of("Task added. Can I go now?", "Task added. Appreciation ignored.");
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
            Task temp = null;

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
                        System.out.println(i + "." + tasks[i]);
                        i += 1;
                    }
                    System.out.println("____________________________________________________________");
                    continue;
                case "mark":
                    int index1 = Integer.parseInt(parts[1]);
                    tasks[index1].mark();
                    System.out.println("____________________________________________________________");
                    speak(taskMark);
                    System.out.println(index1 + "." + tasks[index1]);
                    System.out.println("____________________________________________________________");
                    continue;
                case "unmark":
                    int index2 = Integer.parseInt(parts[1]);
                    tasks[index2].unmark();
                    System.out.println("____________________________________________________________");
                    speak(taskUnmark);
                    System.out.println(index2 + "." + tasks[index2]);
                    System.out.println("____________________________________________________________");
                    continue;
                case "todo":
                    temp = new ToDo(String.join(" ", input.substring(4)));
                    break;
                case "deadline":
                    int slashIndex = input.indexOf("/");
                    temp = new Deadline(String.join(" ", input.substring(9)), input.substring(slashIndex + 4).trim());
                    break;
                case "event":
                    int firstSlash = input.indexOf("/");
                    int secondSlash = input.indexOf("/", firstSlash + 1);

                    String desc = input.substring(6, firstSlash).trim();
                    String start = input.substring(firstSlash + 5, secondSlash).trim();
                    String end = input.substring(secondSlash + 4).trim();

                    temp = new Event(desc, start, end);
                    break;
            }
            tasks[taskCount] = temp;
            taskCount += 1;
            System.out.println("____________________________________________________________");
            speak(taskAddRemarks);
            System.out.println("    " + temp);
            speakListNum(listNumberRemarks, taskCount);
            speak(taskRemarks);
            System.out.println("____________________________________________________________");
        }
    }

    private static void speakListNum(List<List<String>> remarks, int i){
            Random rand = new Random();
            int index = rand.nextInt(remarks.size());
            String start = remarks.get(index).get(0);
            String end = remarks.get(index).get(1);
            System.out.println(start + (i - 1) + end);
    }

    private static void speak(List<String> greetings) {
            Random rand = new Random();
            int index = rand.nextInt(greetings.size());
            System.out.println(greetings.get(index));
    }
}
