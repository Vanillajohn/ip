package sunny.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import sunny.utility.DateParser;
import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

/**
 * Handles storing and retrieving data related to the taskboard.
 * <p>This class follows the singleton pattern and can be accessed
 * through {@link #getInstance()}.</p>
 */
public class Storage {
    private Storage() {}
    private static class Holder {
        private static final Storage INSTANCE = new Storage();
    }
    /**
     * Returns the singleton instance of Storage.
     *
     * @return the Storage singleton instance
     */
    public static Storage getInstance() {
        return Holder.INSTANCE;
    }

    private static String filePath = "./data/Sunny'sAmazingTaskboard(ForHerBothersomeUser).txt";
    static DateParser dateParser = DateParser.getInstance();

    /**
     * Writes each task in the taskboard to the designated filepath based on the
     * format specified in convertTaskToFileFormat.
     * If an IOException occurs when writing, a message will be printed.
     *
     * @param tasks the taskboard as an ArrayList<Task> to write into a file.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Task task : tasks) {
                writer.write(convertTaskToFileFormat(task));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Returns the task object formatted as a string for writing into a file.
     *
     * @param task the task object to be formatted.
     * @return a String that is the formatted task object.
     */
    private static String convertTaskToFileFormat(Task task) {
        if (task instanceof ToDo) {
            return "T | " + task.getDesc() + " | " + (task.isDone() ? "1" : "0");
        }
        if (task instanceof Deadline deadline) {
            return "D | " + deadline.getDesc() + deadline.getFileFormat();
        }
        if (task instanceof Event event) {
            return "E | " + event.getDesc() + event.getFileFormat();
        }
        assert false : "Tasks must be ToDo, Deadline or Event";
        return "";
    }

    /**
     * Loads pre-existing tasks from the data file into the given task list.
     * It creates and appends task objects based on convertTaskFromFileFormat.
     * If the data file is corrupted, it is deleted using deleteDateFile() and an empty task list is used.
     * If an IOException occurs while reading the file, an error message is printed.
     *
     * @param tasks the taskboard as an ArrayList<Task> to be written to from a file.
     */
    public void loadTasks(ArrayList<Task> tasks) {
        ArrayList<Task> loadedTasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Task task = convertTaskFromFileFormat(line);
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

    /**
     * Reads a String and returns a task based on the formatting described in the method.
     *
     * @param line the String to be read and formatted into a task object.
     * @return the task the String is formatted into.
     * @throws IllegalArgumentException if the line has an invalid or unknown task format.
     */
    private static Task convertTaskFromFileFormat(String line) {
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
                Deadline deadline = null;

                Optional<LocalDateTime> dateOpt = dateParser.parse(parts[2]);

                if (dateOpt.isPresent()) {
                    LocalDateTime actualDate = dateOpt.get();
                    deadline = new Deadline(parts[1], actualDate, "");
                } else {
                    deadline = new Deadline(parts[1], null, parts[2]);
                }

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
                Event event = null;

                Optional<LocalDateTime> startOpt = dateParser.parse(parts[2]);
                Optional<LocalDateTime> endOpt = dateParser.parse(parts[3]);

                if (startOpt.isPresent() && endOpt.isPresent()) {
                    LocalDateTime startActual = startOpt.get();
                    LocalDateTime endActual = endOpt.get();
                    event = new Event(parts[1], startActual, endActual, "", "");
                } else if (startOpt.isPresent()) {
                    LocalDateTime startActual = startOpt.get();
                    event = new Event(parts[1], startActual, null, "", parts[3]);
                } else if (endOpt.isPresent()) {
                    LocalDateTime endActual = endOpt.get();
                    event = new Event(parts[1], null, endActual, parts[2], "");
                } else {
                    event = new Event(parts[1], null, null, parts[2], parts[3]);
                }

                if (parts[4].equals("1")) {
                    event.mark();
                }
                return event;

            default:
                throw new IllegalArgumentException("Unknown task type");
        }
    }

    /**
     * Creates a folder for a file to contain tasks.
     * If it cannot be created, an error message is printed.
     */
    public void createDataDirectory() {
        try {
            Path dataDirectory = Paths.get("./data");
            Files.createDirectories(dataDirectory);
        } catch (IOException e) {
            System.out.println("Could not create data directory: " + e);
        }
    }

    /**
     * Removes the file at the specified path.
     * If it cannot be removed, an error message is printed.
     */
    private static void deleteDataFile() {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Could not delete corrupted data file: " + e);
        }
    }

    /**
     * Sets a filepath to be used for testing purposes.
     *
     * @param filePath the path that a file will be created for storing task objects.
     */
    public void setFilePath(String filePath) {
        assert filePath != null : "filePath cannot be null";
        Storage.filePath = filePath;
    }
}
