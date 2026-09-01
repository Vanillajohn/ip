package sunny.taskboard;

import java.util.ArrayList;

import sunny.storage.Storage;
import task.Task;

/**
 * Handles the operations of the taskboard and its associated tasks.
 * <p>This class follows the singleton pattern and can be accessed
 * through {@link #getInstance()}.</p>
 */
public class Taskboard {
    private Taskboard() {}
    private static class Holder {
        private static final Taskboard INSTANCE = new Taskboard();
    }
    /**
     * Returns the singleton instance of Taskboard.
     *
     * @return the Taskboard singleton instance
     */
    public static Taskboard getInstance(){
        return Holder.INSTANCE;
    }

    Storage storer = Storage.getInstance();
    ArrayList<Task> tasks = new ArrayList<>(101);
    int taskCount;

    /**
     * Creates the taskboard directory, loads any pre-existing tasks from storage and sets
     * taskCount to the current taskboard size.
     */
    public void load() {
        storer.createDataDirectory();
        storer.loadTasks(tasks);
        taskCount = tasks.size();
    }

    /**
     * Returns the number of tasks in the taskboard.
     *
     * @return taskCount, the number of tasks in the taskboard.
     */
    public int getTaskCount() {
        return taskCount;
    }

    /**
     * Increases or decreases taskCount based on the specified value.
     *
     * @param value the value to add to taskCount.
     */
    public void updateTaskCount(int value) {
        taskCount += value;
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index the index of the task to be retrieved.
     * @return the task at index.
     */
    public Task getTask(int index){
        return tasks.get(index);
    }

    /**
     * Removes the task at the specified index.
     *
     * @param index the index of the task to be removed.
     */
    public void removeTask(int index){
        tasks.remove(index);
    }

    /**
     * Appends a task to the taskboard.
     *
     * @param task the task to be appended.
     */
    public void addTask(Task task){
        tasks.add(task);
    }

    /**
     * Returns the lists of tasks in the taskboard as an ArrayList<Task>.
     *
     * @return the taskboard.
     */
    public ArrayList<Task> getTasks(){
        return tasks;
    }

    /**
     * Removes all tasks and sets taskCount to 0.
     * For testing purposes only.
     */
    public void clearTasks() {
        tasks.clear();
        taskCount = 0;
    }
}
