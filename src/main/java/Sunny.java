import java.util.Random;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Sunny {
        public static void main(String[] args) {
            // exception if trying to delete things that aren't there, mark things that aren't there
            // more specific error handling that tells user what to do
            // add comments to explain things

        List<String> greetings = List.of("Yeah, it's me, Sunny.\nWhat do you want?", "What now?\nCan you go bother someone else?");
        List<String> goodbyes = List.of("Don't tell anyone I helped you, got it?", "See you never.", "Jeez, you really depend on me, don't you?");
        List<String> taskRemarks = List.of("", "I'm only helping you because you now owe me.", "I could be doing so much more right now.",
                                           "What a waste of time.", "Can't you do this yourself?");
        List<String> taskMark = List.of("Yeah, yeah. Marked it already", "Done. Can you leave me alone now?");
        List<String> taskUnmark = List.of("Can't you do this yourself?", "Done. Can you leave me alone now?");
        List<String> listRemarks = List.of("Here's your LL (lame list):", "Go write this down so I don't have to show it to you again:");
        List<List<String>> listNumberRemarks = List.of(List.of("Now you have ", " tasks in your list. Whoop de doo."), List.of("Go do your ", " tasks already!"));
        List<String> taskAddRemarks = List.of("Task added. Can I go now?", "Task added. Appreciation ignored.");
        List<List<String>> descEmpty = List.of(List.of("A "," description can't be empty, dummy!"), List.of("What am I supposed to do if your "," task has no description?"));
        List<String> unrecognised = List.of("Is that a joke? What does that mean?", "I normally don't understand you, but now I really don't.");
        List<String> insufficient = List.of("Very funny. Not enough info and I won't help you!", "You didn't give me enough info! Don't test me!");
        List<String> tooMany = List.of("Your taskboard can only hold so many!", "I can't add any more!");
        List<String> deleting = List.of("If you want this deleted, why did you add it?", "I've added it and now you want me to remove it?");

        ArrayList<Task> tasks = new ArrayList<>(101);
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);
        Random uniRand = new Random();

        System.out.println("____________________________________________________________");
        speak(greetings);
        System.out.println("____________________________________________________________");

        while (true) {
            try{
                if (taskCount == 101){
                    int index = uniRand.nextInt(tooMany.size());
                    throw new tooManyTasksException(tooMany.get(index));
                }
                String input = scanner.nextLine();
                String[] parts = input.split(" ");
                String command = parts[0];
                Task temp = null;

                switch (command) {
                    case "bye":
                        scanner.close();
                        System.out.println("____________________________________________________________");
                        speak(goodbyes);
                        System.out.println("____________________________________________________________");
                        return;
                    case "list":
                        int i = 0;
                        System.out.println("____________________________________________________________");
                        speak(listRemarks);
                        while (i < taskCount) {
                            System.out.println(i + "." + tasks.get(i));
                            i += 1;
                        }
                        System.out.println("____________________________________________________________");
                        continue;
                    case "mark":
                        if (parts.length == 1){
                            int index = uniRand.nextInt(insufficient.size());
                            throw new insufficientInfoException(insufficient.get(index));
                        }
                        int index1 = Integer.parseInt(parts[1]) - 1;
                        tasks.get(index1).mark();
                        System.out.println("____________________________________________________________");
                        speak(taskMark);
                        System.out.println(index1 + "." + tasks.get(index1));
                        System.out.println("____________________________________________________________");
                        continue;
                    case "unmark":
                        if (parts.length == 1){
                            int index = uniRand.nextInt(insufficient.size());
                            throw new insufficientInfoException(insufficient.get(index));
                        }
                        int index2 = Integer.parseInt(parts[1]) - 1;
                        tasks.get(index2).unmark();
                        System.out.println("____________________________________________________________");
                        speak(taskUnmark);
                        System.out.println(index2 + "." + tasks.get(index2));
                        System.out.println("____________________________________________________________");
                        continue;
                    case "todo":
                        if (parts.length == 1){
                            int index = uniRand.nextInt(descEmpty.size());
                            throw new TaskEmptyDescException(descEmpty.get(index), "todo");
                        }
                        temp = new ToDo(String.join(" ", input.substring(4)));
                        break;
                    case "deadline":
                        if (parts.length == 1){
                            int index = uniRand.nextInt(descEmpty.size());
                            throw new TaskEmptyDescException(descEmpty.get(index), "deadline");
                        }
                        int slashIndex = input.indexOf("/");
                        if (slashIndex == -1){
                            int index = uniRand.nextInt(insufficient.size());
                            throw new insufficientInfoException(insufficient.get(index));
                        }
                        temp = new Deadline(String.join(" ", input.substring(9)), input.substring(slashIndex + 4).trim());
                        break;
                    case "event":
                        if (parts.length == 1){
                            int index = uniRand.nextInt(descEmpty.size());
                            throw new TaskEmptyDescException(descEmpty.get(index), "event");
                        }
                        int firstSlash = input.indexOf("/");
                        int secondSlash = input.indexOf("/", firstSlash + 1);
                        if (firstSlash == -1 || secondSlash == -1){
                            int index = uniRand.nextInt(insufficient.size());
                            throw new insufficientInfoException(insufficient.get(index));
                        }

                        String desc = input.substring(6, firstSlash).trim();
                        String start = input.substring(firstSlash + 5, secondSlash).trim();
                        String end = input.substring(secondSlash + 4).trim();

                        temp = new Event(desc, start, end);
                        break;
                    case "delete":
                        if (parts.length == 1){
                            int index = uniRand.nextInt(insufficient.size());
                            throw new insufficientInfoException(insufficient.get(index));
                        }
                        int index3 = Integer.parseInt(parts[1]) - 1;
                        temp = tasks.get(index3);
                        tasks.remove(index3);
                        taskCount -= 1;
                        System.out.println("____________________________________________________________");
                        speak(deleting);
                        System.out.println("    " + temp);
                        speakListNum(listNumberRemarks, taskCount + 1);
                        System.out.println("____________________________________________________________");
                        continue;

                }
                if (temp == null) {
                    int index = uniRand.nextInt(unrecognised.size());
                    throw new UnrecognisedTaskException(unrecognised.get(index));
                }
                tasks.add(temp);
                taskCount += 1;
                System.out.println("____________________________________________________________");
                speak(taskAddRemarks);
                System.out.println("    " + temp);
                speakListNum(listNumberRemarks, taskCount + 1);
                speak(taskRemarks);
                System.out.println("____________________________________________________________");
            }
            catch (UnrecognisedTaskException | TaskEmptyDescException | insufficientInfoException | tooManyTasksException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
            }
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
