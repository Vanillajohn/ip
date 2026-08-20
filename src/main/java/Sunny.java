import java.util.Random;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Sunny {
    private static final String FILE_PATH = "./data/Sunny'sAmazingTaskboard(ForHerBothersomeUser).txt";

        public static void main(String[] args) {
            // more specific error handling that tells user what to do
            // add comments to explain things

        List<String> greetings = List.of("Yeah, it's me, Sunny.\nWhat do you want?", "What now?\nCan you go bother someone else?");
        List<String> goodbyes = List.of("Don't tell anyone I helped you, got it?", "See you never.", "Jeez, you really depend on me, don't you?");
        List<String> taskRemarks = List.of("", "I'm only helping you because you now owe me.", "I could be doing so much more right now.",
                                           "What a waste of time.", "Can't you do this yourself?");
        List<String> taskMark = List.of("Yeah, yeah. Marked it already", "Done. Can you leave me alone now?", "You better help me when I need it!");
        List<String> taskUnmark = List.of("Can't you unmark this yourself?", "Done. Can you leave me alone now?", "You better help me when I need it!");
        List<String> listRemarks = List.of("Here's your LL (lame list):", "Go write this down so I don't have to show it to you again:");
        List<List<String>> listNumberRemarks = List.of(List.of("Now you have ", " tasks in your list. Whoop de doo."), List.of("Go do your ", " tasks already!"));
        List<String> taskAddRemarks = List.of("Task added. Can I go now?", "Task added. Appreciation ignored.");
        List<List<String>> descEmpty = List.of(List.of("A "," description can't be empty, dummy!"), List.of("What am I supposed to do if your "," task has no description?"));
        List<String> unrecognised = List.of("Is that a joke? What does that mean?", "I normally don't understand you, but now I really don't.");
        List<String> insufficient = List.of("Very funny. Not enough info and I won't help you!", "You didn't give me enough info! Don't test me!");
        List<String> tooMany = List.of("Your taskboard can only hold so many!", "I can't add any more!", "Go get a bigger taskboard!");
        List<String> deleting = List.of("If you want this deleted, why did you add it?", "I've added it and now you want me to remove it?", "Now I'm DELETING for you too?");
        List<String> missingTask = List.of("There's no task there!", "I ain't doing anything if nothing's there!");
        List<String> notInteger = List.of("I need a VALID INTEGER doofus!", "You're in CS and you don't know what a VALID INTEGER is?");

        ArrayList<Task> tasks = new ArrayList<>(101);
        createDataDirectory();
        loadTasks(tasks);
        int taskCount = tasks.size();
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
                String command = parts[0].toLowerCase();
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
                            System.out.println(i + 1 + "." + tasks.get(i));
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
                        if (index1 >= 0 && index1 < tasks.size() && tasks.get(index1) != null){ //if tasks at index has something
                            tasks.get(index1).mark();
                            saveTasks(tasks);

                            System.out.println("____________________________________________________________");
                            speak(taskMark);
                            System.out.println(index1 + "." + tasks.get(index1));
                            System.out.println("____________________________________________________________");
                            continue;
                        } else {//if there's nothing at index, either null or out of bounds
                            int index = uniRand.nextInt(missingTask.size());
                            throw new taskOutOfBoundsException(missingTask.get(index));
                        }
                    case "unmark":
                        if (parts.length == 1){
                            int index = uniRand.nextInt(insufficient.size());
                            throw new insufficientInfoException(insufficient.get(index));
                        }
                        int index2 = Integer.parseInt(parts[1]) - 1;
                        if (index2 >= 0 && index2 < tasks.size() && tasks.get(index2) != null) {//if tasks at index has something
                            tasks.get(index2).unmark();
                            saveTasks(tasks);

                            System.out.println("____________________________________________________________");
                            speak(taskUnmark);
                            System.out.println(index2 + "." + tasks.get(index2));
                            System.out.println("____________________________________________________________");
                            continue;
                        } else {//if there's nothing at index, either null or out of bounds
                            int index = uniRand.nextInt(missingTask.size());
                            throw new taskOutOfBoundsException(missingTask.get(index));
                        }
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
                        int slashIndex = input.indexOf("/by");
                        if (slashIndex == -1){
                            int index = uniRand.nextInt(insufficient.size());
                            throw new insufficientInfoException(insufficient.get(index));
                        }
                        temp = new Deadline(String.join(" ", input.substring(9, slashIndex)).trim(), input.substring(slashIndex + 3).trim());
                        break;
                    case "event":
                        if (parts.length == 1){
                            int index = uniRand.nextInt(descEmpty.size());
                            throw new TaskEmptyDescException(descEmpty.get(index), "event");
                        }
                        int firstSlash = input.indexOf("/from");
                        int secondSlash = input.indexOf("/to");
                        if (firstSlash == -1 || secondSlash == -1 || firstSlash > secondSlash){
                            int index = uniRand.nextInt(insufficient.size());
                            throw new insufficientInfoException(insufficient.get(index));
                        }
                        String desc = input.substring(6, firstSlash);
                        String start = input.substring(firstSlash + 5, secondSlash).trim();
                        String end = input.substring(secondSlash + 3).trim();

                        temp = new Event(desc, start, end);
                        break;
                    case "delete":
                        if (parts.length == 1){
                            int index = uniRand.nextInt(insufficient.size());
                            throw new insufficientInfoException(insufficient.get(index));
                        }
                        int index3 = Integer.parseInt(parts[1]) - 1;
                        if (index3 >= 0 && index3 < tasks.size() && tasks.get(index3) != null) {//if tasks at index has something
                            temp = tasks.get(index3);
                            tasks.remove(index3);
                            taskCount -= 1;
                            saveTasks(tasks);

                            System.out.println("____________________________________________________________");
                            speak(deleting);
                            System.out.println("    " + temp);
                            speakListNum(listNumberRemarks, taskCount + 1);
                            System.out.println("____________________________________________________________");
                            continue;
                        } else {//if there's nothing at index, either null or out of bounds
                            int index = uniRand.nextInt(missingTask.size());
                            throw new taskOutOfBoundsException(missingTask.get(index));
                        }
                }
                if (temp == null) {
                    int index = uniRand.nextInt(unrecognised.size());
                    throw new UnrecognisedTaskException(unrecognised.get(index));
                }
                tasks.add(temp);
                taskCount += 1;
                saveTasks(tasks);

                System.out.println("____________________________________________________________");
                speak(taskAddRemarks);
                System.out.println("    " + temp);
                speakListNum(listNumberRemarks, taskCount + 1);
                speak(taskRemarks);
                System.out.println("____________________________________________________________");
            }
            catch (UnrecognisedTaskException | TaskEmptyDescException | insufficientInfoException | tooManyTasksException | taskOutOfBoundsException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
            }
            catch (NumberFormatException e) { //if something other than an integer was used, or the integer is too large/small
                int index = uniRand.nextInt(notInteger.size());
                System.out.println("____________________________________________________________");
                System.out.println(notInteger.get(index));
                System.out.println("____________________________________________________________");
            } //no catch for out of bounds to see if code was the issue rather than user
        }
    }

    private static void speakListNum(List<List<String>> remarks, int i){//specifically if some data is needed in a remark
            Random rand = new Random();
            int index = rand.nextInt(remarks.size());
            String start = remarks.get(index).get(0);
            String end = remarks.get(index).get(1);
            System.out.println(start + (i - 1) + end);
    }

    private static void speak(List<String> greetings) {//when a remark is given
            Random rand = new Random();
            int index = rand.nextInt(greetings.size());
            System.out.println(greetings.get(index));
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (Task task : tasks) {
                writer.write(taskToFileFormat(task));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static String taskToFileFormat(Task task) {
        if (task instanceof ToDo) {
            return "T | " + task.getDesc() + " | " + (task.isDone() ? "1" : "0");
        }
        if (task instanceof Deadline deadline) {
            return "D | " + deadline.getDesc() + deadline.getFileFormat();
        }
        if (task instanceof Event event) {
            return "E | " + event.getDesc() + event.getFileFormat();
        }

        return "";
    }

    private static void loadTasks(ArrayList<Task> tasks) {
        ArrayList<Task> loadedTasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Task task = taskFromFileFormat(line);
                loadedTasks.add(task);
            }
            tasks.addAll(loadedTasks);// Only modify the real task list if the ENTIRE file was valid

        } catch (FileNotFoundException e) {
            // Fine to be empty on first time
        } catch (IllegalArgumentException e) {
            System.out.println("Data file is corrupted.");
            System.out.println("Starting with an empty task list.");

            deleteDataFile();
        } catch (IOException e) {
            System.out.println("Error reading data file: " + e.getMessage());
        }
    }

    private static Task taskFromFileFormat(String line) {
        String[] parts = line.split(" \\| ", -1);
        if (parts.length == 0) {
            throw new IllegalArgumentException("Empty task line");
        }

        String type = parts[0];
        switch (type) {

            case "T":
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Invalid ToDo format");
                }
                if (!parts[2].equals("0") && !parts[2].equals("1")) {
                    throw new IllegalArgumentException("Invalid completion status");
                }
                ToDo todo = new ToDo(parts[1]);

                if (parts[2].equals("1")) {
                    todo.mark();
                }
                return todo;

            case "D":
                if (parts.length != 4) {
                    throw new IllegalArgumentException("Invalid Deadline format");
                }
                if (!parts[3].equals("0") && !parts[3].equals("1")) {
                    throw new IllegalArgumentException("Invalid completion status");
                }
                Deadline deadline = new Deadline(parts[1], parts[2]);

                if (parts[3].equals("1")) {
                    deadline.mark();
                }
                return deadline;

            case "E":
                if (parts.length != 5) {
                    throw new IllegalArgumentException("Invalid Event format");
                }
                if (!parts[4].equals("0") && !parts[4].equals("1")) {
                    throw new IllegalArgumentException("Invalid completion status");
                }
                Event event = new Event(parts[1], parts[2], parts[3]);

                if (parts[4].equals("1")) {
                    event.mark();
                }
                return event;

            default:
                throw new IllegalArgumentException("Unknown task type");
        }
    }

    private static void createDataDirectory() {
        try {
            Path dataDirectory = Paths.get("./data");
            Files.createDirectories(dataDirectory);
        } catch (IOException e) {
            System.out.println("Could not create data directory.");
        }
    }

    private static void deleteDataFile() {
        try {
            Files.deleteIfExists(Paths.get(FILE_PATH));
        } catch (IOException e) {
            System.out.println("Could not delete corrupted data file: " + e);
        }
    }
}
