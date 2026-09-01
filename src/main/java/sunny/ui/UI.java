package sunny.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sunny.SunnyVoice;
import sunny.taskboard.Taskboard;
import sunny.utility.UserParser;
import sunnyexception.SunnyException;
import sunnyexception.TaskEmptyDescException;
import sunnyexception.UnrecognisedTaskException;
import sunnyexception.InsufficientInfoException;
import sunnyexception.TaskOutOfBoundsException;

import sunnyexception.TooManyKeywordsException;
import sunnyexception.TooManyTasksException;
import task.Task;

/**
 * Handles the interactions with the user.
 * <p>This class follows the singleton pattern and can be accessed
 * through {@link #getInstance()}.</p>
 */
public class UI {
    private UI() {}
    private static class Holder {
        private static final UI INSTANCE = new UI();
    }
    /**
     * Returns the singleton instance of UI.
     *
     * @return the UI singleton instance
     */
    public static UI getInstance() {
        return Holder.INSTANCE;
    }

    private Taskboard taskboard = Taskboard.getInstance();
    private SunnyVoice sunnyVoice = SunnyVoice.getInstance();
    private UserParser userParser = UserParser.getInstance();
    boolean isRunning = true;
    private String lastResponse;

    /**
     * Prints a goodbye remark and sets isRunning to false, usually to exit the program
     * by stopping run().
     */
    public void replyGoodbye() {
        lastResponse = sunnyVoice.getText("goodbyes");
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("goodbyes");
        System.out.println("____________________________________________________________");
        isRunning = false;
    }

    /**
     * Prints a listRemark and the tasks in the taskboard.
     * The numbering is the order the tasks were added in.
     * If there are none, it just prints the remark.
     */
    public void replyList() {
        if (taskboard.getTaskCount() == 0) {
            System.out.println("____________________________________________________________");
            sunnyVoice.speak("noList");
            System.out.println("____________________________________________________________");
            lastResponse = sunnyVoice.getText("noList");
        } else {
            String response = "";
            int i = 0;
            while (i < taskboard.getTaskCount()) {
                System.out.println(i + 1 + "." + taskboard.getTask(i));
                response += i + 1 + "." + taskboard.getTask(i) + "\n";
                i += 1;
            }
            System.out.println("____________________________________________________________");
            sunnyVoice.speak("listRemarks");
            System.out.println("____________________________________________________________");
            lastResponse = sunnyVoice.getText("listRemarks") + "\n" + response;
        }
    }

    /**
     * Prints a taskMark remark and the task selected by the index.
     *
     * @param index the index that selects the task to be marked.
     */
    public void replyMark(int index) {
        lastResponse = sunnyVoice.getText("taskMark") + "\n" + (index + 1) + "." + taskboard.getTask(index);
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("taskMark");
        System.out.println(index + "." + taskboard.getTask(index));
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints a taskUnmark remark and the task selected by the index.
     *
     * @param index the index that selects the task to be unmarked.
     */
    public void replyUnmark(int index) {
        lastResponse = sunnyVoice.getText("taskUnmark") + "\n" + (index + 1) + "." + taskboard.getTask(index);
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("taskUnmark");
        System.out.println(index + "." + taskboard.getTask(index));
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints an alreadyDone remark and the task selected by the index.
     *
     * @param index the index that selects the task.
     */
    public void replyAlreadyDone(String task, int index) {
        List<String> remarks = sunnyVoice.getAlreadyDoneRemarks();
        lastResponse = remarks.get(0) + task + remarks.get(1) + "\n" + (index + 1) + "." + taskboard.getTask(index);
        System.out.println("____________________________________________________________");
        sunnyVoice.speak(remarks.get(0) + task + remarks.get(1));
        System.out.println(index + "." + taskboard.getTask(index));
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints a deleting remark, the task selected by the index, a listNumberRemark and
     * the number of tasks in the taskboard.
     *
     * @param temp the task to be deleted.
     */
    public void replyDelete(Task temp) {
        if (taskboard.getTaskCount() == 0) {
            lastResponse = sunnyVoice.getText("deleting") + "\n" + "    " + temp + "\n" + sunnyVoice.getText("noTasksLeft");
            System.out.println("____________________________________________________________");
            sunnyVoice.speak("deleting");
            System.out.println("    " + temp);
            sunnyVoice.speak("noTasksLeft");
            System.out.println("____________________________________________________________");
        } else {
            List<String> remarks = sunnyVoice.getListNumberRemarks();
            lastResponse = sunnyVoice.getText("deleting") + "\n" + "    " + temp + "\n" + remarks.get(0) + taskboard.getTaskCount() + remarks.get(1);
            System.out.println("____________________________________________________________");
            sunnyVoice.speak("deleting");
            System.out.println("    " + temp);
            System.out.println(remarks.get(0) + taskboard.getTaskCount() + remarks.get(1));
            System.out.println("____________________________________________________________");
        }
    }

    /**
     * Prints a taskAddRemark, the task to be added, a listNumberRemark, the number of tasks
     * in the taskboard, and a taskRemark.
     *
     * @param temp the task to be added to the taskboard.
     */
    public void replyTask(Task temp) {
        List<String> remarks = sunnyVoice.getListNumberRemarks();
        lastResponse = sunnyVoice.getText("taskAddRemarks") + "\n" + "    " + temp + "\n" + remarks.get(0) + taskboard.getTaskCount() + remarks.get(1);
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("taskAddRemarks");
        System.out.println("    " + temp);
        System.out.println(remarks.get(0) + taskboard.getTaskCount() + remarks.get(1));
        sunnyVoice.speak("taskRemarks");
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints the tasks with a specific keyword entered.
     *
     * @param foundTask the tasks that contain the keyword.
     */
    public void replyFind(ArrayList<Task> foundTask) {
        String response = "";
        int i = 0;
        while (i < foundTask.size()) {
            System.out.println(i + 1 + "." + foundTask.get(i));
            response += i + 1 + "." + foundTask.get(i) + "\n";
            i += 1;
        }
        if (response.isEmpty()) {
            System.out.println("____________________________________________________________");
            sunnyVoice.speak("noFoundTasks");
            System.out.println("____________________________________________________________");
            lastResponse = sunnyVoice.getText("noFoundTasks");
        } else {
            System.out.println("____________________________________________________________");
            sunnyVoice.speak("foundTasks");
            System.out.println("____________________________________________________________");
            lastResponse = sunnyVoice.getText("foundTasks") + "\n" + response;
        }
    }

    /**
     * Continuously reads user input and sends it to UserParser for processing until the UI is stopped.
     * Handles exceptions resulting from invalid input.
     *
     * @param scanner a scanner inputted by a different class.
     */
    public void run(Scanner scanner) {
        while (isRunning) {
            try{
                if (taskboard.getTaskCount() == 101){
                    throw new TooManyTasksException(sunnyVoice.getException("tooMany"));
                }
                String input = scanner.nextLine();

                userParser.parseUserInput(input, this);
            }
            catch (UnrecognisedTaskException | TaskEmptyDescException | InsufficientInfoException |
                   TooManyTasksException |
                   TaskOutOfBoundsException | TooManyKeywordsException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
                lastResponse = e.getMessage();
            }
            catch (NumberFormatException e) { //if something other than an integer was used, or the integer is too large/small
                System.out.println("____________________________________________________________");
                sunnyVoice.speak("notInteger");
                System.out.println("____________________________________________________________");
                lastResponse = sunnyVoice.getText("notInteger");
            } //no catch for out of bounds to see if code was the issue rather than user
            catch (SunnyException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Resets the class by setting isRunning to true, thereby allowing run() to run.
     */
    public void reset() {
        isRunning = true;
    }

    /**
     * Sets the lastResponse attribute.
     *
     * @param text the text to set the lastResponse to.
     */
    public void setLastResponse(String text) {
        lastResponse = text;
    }

    /**
     * Returns the LastResponse attribute as a String.
     *
     * @return the String representation of the lastResponse.
     */
    public String getLastResponse() {
        return lastResponse;
    }

}
