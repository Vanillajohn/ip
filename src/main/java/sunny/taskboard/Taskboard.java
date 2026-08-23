package sunny.taskboard;

import java.util.ArrayList;
import sunny.storage.Storage;
import task.*;

public class Taskboard {
    private Taskboard(){}
    private static class holder{
        private static final Taskboard INSTANCE = new Taskboard();
    }
    public static Taskboard getInstance(){
        return Taskboard.holder.INSTANCE;
    }

    Storage storer = Storage.getInstance();
    ArrayList<Task> tasks = new ArrayList<>(101);
    int taskCount;

    public void load(){
        storer.createDataDirectory();
        storer.loadTasks(tasks);
        taskCount = tasks.size();
    }

    public int getTaskCount() {
        return taskCount;
    }
    public void setTaskCount(int value) {
        taskCount += value;
    }
    public Task getTask(int index){
        return tasks.get(index);
    }
    public void removeTask(int index){
        tasks.remove(index);
    }
    public void addTask(Task task){
        tasks.add(task);
    }
    public ArrayList<Task> getTasks(){
        return tasks;
    }
}