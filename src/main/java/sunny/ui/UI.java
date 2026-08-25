package sunny.ui;

import java.util.ArrayList;
import java.util.Scanner;

import sunny.SunnyVoice;
import sunny.taskboard.Taskboard;
import sunny.utility.UserParser;
import sunnyexception.SunnyException;
import sunnyexception.TaskEmptyDescException;
import sunnyexception.UnrecognisedTaskException;
import sunnyexception.insufficientInfoException;
import sunnyexception.taskOutOfBoundsException;

import sunnyexception.tooManyKeywordsException;
import sunnyexception.tooManyTasksException;
import task.Task;

/**
 * Handles the interactions with the user.
 * <p>This class follows the singleton pattern and can be accessed
 * through {@link #getInstance()}.</p>
 */
public class UI {
    private UI(){}
    private static class holder{
        private static final UI INSTANCE = new UI();
    }
    /**
     * Returns the singleton instance of UI.
     *
     * @return the UI singleton instance
     */
    public static UI getInstance(){
        return UI.holder.INSTANCE;
    }

    private Taskboard taskboard = Taskboard.getInstance();
    private SunnyVoice sunnyVoice = SunnyVoice.getInstance();
    private UserParser userParser = UserParser.getInstance();
    boolean toggle = true;

    /**
     * Prints a goodbye remark and sets toggle to false, usually to exit the program
     * by stopping run().
     */
    public void goodbye(){
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("goodbyes");
        System.out.println("____________________________________________________________");
        toggle = false;
    }

    /**
     * Prints a listRemark and the tasks in the taskboard.
     * The numbering is the order the tasks were added in.
     * If there are none, it just prints the remark.
     */
    public void list(){
        int i = 0;
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("listRemarks");
        while (i < taskboard.getTaskCount()) {
            System.out.println(i + 1 + "." + taskboard.getTask(i));
            i += 1;
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints a taskMark remark and the task selected by the index.
     *
     * @param index1 the index that selects the task to be marked.
     */
    public void mark(int index1){
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("taskMark");
        System.out.println(index1 + "." + taskboard.getTask(index1));
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints a taskUnmark remark and the task selected by the index.
     *
     * @param index2 the index that selects the task to be unmarked.
     */
    public void unmark(int index2){
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("taskUnmark");
        System.out.println(index2 + "." + taskboard.getTask(index2));
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints a deleting remark, the task selected by the index, a listNumberRemark and
     * the number of tasks in the taskboard.
     *
     * @param temp the task to be deleted.
     */
    public void delete(Task temp){
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("deleting");
        System.out.println("    " + temp);
        sunnyVoice.speakListNum("listNumberRemarks", taskboard.getTaskCount() + 1);
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints a taskAddRemark, the task to be added, a listNumberRemark, the number of tasks
     * in the taskboard, and a taskRemark.
     *
     * @param temp the task to be added to the taskboard.
     */
    public void task(Task temp){
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("taskAddRemarks");
        System.out.println("    " + temp);
        sunnyVoice.speakListNum("listNumberRemarks", taskboard.getTaskCount() + 1);
        sunnyVoice.speak("taskRemarks");
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints the tasks with a specific keyword entered.
     *
     * @param foundTask the tasks that contain the keyword.
     */
    public void find(ArrayList<Task> foundTask){
        int i = 0;
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("foundTasks");
        while (i < foundTask.size()) {
            System.out.println(i + 1 + "." + foundTask.get(i));
            i += 1;
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * Continuously reads user input and sends it to UserParser for processing until the UI is stopped.
     * Handles exceptions resulting from invalid input.
     *
     * @param scanner a scanner inputted by a different class.
     */
    public void run(Scanner scanner){
        while (toggle) {
            try{
                if (taskboard.getTaskCount() == 101){
                    throw new tooManyTasksException(sunnyVoice.getException("tooMany"));
                }
                String input = scanner.nextLine();

                userParser.userParse(input, this);
            }
            catch (UnrecognisedTaskException | TaskEmptyDescException | insufficientInfoException | tooManyTasksException | taskOutOfBoundsException | tooManyKeywordsException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
            }
            catch (NumberFormatException e) { //if something other than an integer was used, or the integer is too large/small
                System.out.println("____________________________________________________________");
                sunnyVoice.speak("notInteger");
                System.out.println("____________________________________________________________");
            } //no catch for out of bounds to see if code was the issue rather than user
            catch (SunnyException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Resets the class by setting toggle to true, thereby allowing run() to run.
     */
    public void reset() {
        toggle = true;
    }

}
