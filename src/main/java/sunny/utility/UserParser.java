package sunny.utility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sunny.SunnyVoice;
import sunny.storage.Storage;
import sunny.taskboard.Taskboard;
import sunny.ui.UI;
import sunnyexception.SunnyException;
import sunnyexception.TaskEmptyDescException;
import sunnyexception.UnrecognisedTaskException;
import sunnyexception.InsufficientInfoException;
import sunnyexception.TaskOutOfBoundsException;
import sunnyexception.TooManyKeywordsException;
import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;

public class UserParser {
    private UserParser(){}
    private static class Holder {
        private static final UserParser INSTANCE = new UserParser();
    }
    /**
     * Returns the singleton instance of UserParser.
     *
     * @return the UserParser singleton instance
     */
    public static UserParser getInstance() {
        return Holder.INSTANCE;
    }

    private Storage storer = Storage.getInstance();
    private DateParser parser = DateParser.getInstance();
    private Taskboard taskboard = Taskboard.getInstance();
    private SunnyVoice sunnyVoice = SunnyVoice.getInstance();
    private String commandType;

    /**
     * Parses the user's input and prints to the CLI the chatbot's response while
     * managing data required for the GUI.
     *
     * @param input the user's input.
     * @param ui the UI object some data is sent to.
     * @throws SunnyException indicates something wrong with the user's input.
     */
    public void parseUserInput(String input, UI ui) throws SunnyException {
        if (input.trim().isEmpty()) {
            ui.setLastResponse(sunnyVoice.getException("unrecognised"));
            throw new UnrecognisedTaskException(sunnyVoice.getException("unrecognised"));
        }
        String[] parts = input.split(" ");
        String command = parts[0].toLowerCase();
        Task temp = null;
        switch (command) {
            case "bye":
                ui.replyGoodbye();
                commandType = "goodbye";
                return;
            case "list":
                ui.replyList();
                commandType = "list";
                return;
            case "mark":
                if (parts.length == 1) {
                    ui.setLastResponse(sunnyVoice.getException("insufficient"));
                    commandType = "error";
                    throw new InsufficientInfoException(sunnyVoice.getException("insufficient"));
                }

                int index1;
                try {
                    index1 = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException e) {
                    ui.setLastResponse(sunnyVoice.getException("notInteger"));
                    commandType = "error";
                    throw new NumberFormatException();
                }

                if (index1 >= 0 && index1 < taskboard.getTaskCount() && taskboard.getTask(index1) != null){ //if tasks at index has something
                    Task relavantTask = taskboard.getTask(index1);
                    if (relavantTask.isDone()) {
                        ui.replyAlreadyDone("marked", index1);
                        commandType = "alreadyDone";
                        return;
                    } else {
                        relavantTask.mark();
                        storer.saveTasks(taskboard.getTasks());
                        ui.replyMark(index1);
                        commandType = "mark";
                        return;
                    }
                } else {//if there's nothing at index, either null or out of bounds
                    ui.setLastResponse(sunnyVoice.getException("missingTask"));
                    commandType = "error";
                    throw new TaskOutOfBoundsException(sunnyVoice.getException("missingTask"));
                }
            case "unmark":
                if (parts.length == 1) {
                    ui.setLastResponse(sunnyVoice.getException("insufficient"));
                    commandType = "error";
                    throw new InsufficientInfoException(sunnyVoice.getException("insufficient"));
                }
                int index2;
                try {
                    index2 = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException e) {
                    ui.setLastResponse(sunnyVoice.getException("notInteger"));
                    commandType = "error";
                    throw new NumberFormatException();
                }

                if (index2 >= 0 && index2 < taskboard.getTaskCount() && taskboard.getTask(index2) != null) {//if tasks at index has something
                    Task relavantTask = taskboard.getTask(index2);
                    if (!relavantTask.isDone()) {
                        ui.replyAlreadyDone("unmarked", index2);
                        commandType = "alreadyDone";
                        return;
                    } else {
                        relavantTask.unmark();
                        storer.saveTasks(taskboard.getTasks());
                        ui.replyUnmark(index2);
                        commandType = "unmark";
                        return;
                    }
                } else {//if there's nothing at index, either null or out of bounds
                    ui.setLastResponse(sunnyVoice.getException("missingTask"));
                    commandType = "error";
                    throw new TaskOutOfBoundsException(sunnyVoice.getException("missingTask"));
                }
            case "todo":
                if (parts.length == 1) {
                    List<String> remarks = sunnyVoice.getDescEmptyRemarks();
                    ui.setLastResponse(remarks.get(0) +  "todo" + remarks.get(1));
                    commandType = "error";
                    throw new TaskEmptyDescException(sunnyVoice.getListException("descEmpty"), "todo");
                }
                temp = new ToDo(String.join(" ", input.substring(4)));
                commandType = "task";
                break;
            case "deadline":
                if (parts.length == 1) {
                    List<String> remarks = sunnyVoice.getDescEmptyRemarks();
                    ui.setLastResponse(remarks.get(0) +  "deadline" + remarks.get(1));
                    commandType = "error";
                    throw new TaskEmptyDescException(sunnyVoice.getListException("descEmpty"), "deadline");
                }
                int slashIndex = input.indexOf("/by");
                if (slashIndex == -1) {
                    ui.setLastResponse(sunnyVoice.getException("insufficient"));
                    commandType = "error";
                    throw new InsufficientInfoException(sunnyVoice.getException("insufficient"));
                }
                String description = String.join(" ", input.substring(9, slashIndex)).trim();

                Optional<LocalDateTime> dateOpt = parser.parse(input.substring(slashIndex + 3).trim());
                commandType = "task";

                if (dateOpt.isPresent()) {
                    LocalDateTime actualDate = dateOpt.get();
                    temp = new Deadline(description, actualDate, "");
                    break;
                } else {
                    temp = new Deadline(description, null, input.substring(slashIndex + 3).trim());
                    break;
                }

            case "event":
                if (parts.length == 1) {
                    List<String> remarks = sunnyVoice.getDescEmptyRemarks();
                    ui.setLastResponse(remarks.get(0) +  "event" + remarks.get(1));
                    commandType = "error";
                    throw new TaskEmptyDescException(sunnyVoice.getListException("descEmpty"), "event");
                }
                int firstSlash = input.indexOf("/from");
                int secondSlash = input.indexOf("/to");
                if (firstSlash == -1 || secondSlash == -1 || firstSlash > secondSlash){
                    ui.setLastResponse(sunnyVoice.getException("insufficient"));
                    commandType = "error";
                    throw new InsufficientInfoException(sunnyVoice.getException("insufficient"));
                }
                String desc = input.substring(6, firstSlash);
                String startString = input.substring(firstSlash + 5, secondSlash).trim();
                String endString = input.substring(secondSlash + 3).trim();
                Optional<LocalDateTime> startOpt = parser.parse(startString);
                Optional<LocalDateTime> endOpt = parser.parse(endString);
                commandType = "task";

                if (startOpt.isPresent() && endOpt.isPresent()) {
                    LocalDateTime startActual = startOpt.get();
                    LocalDateTime endActual = endOpt.get();
                    temp = new Event(desc, startActual, endActual, "", "");
                    break;
                } else if (startOpt.isPresent()) {
                    LocalDateTime startActual = startOpt.get();
                    temp = new Event(desc, startActual, null, "", endString);
                    break;
                } else if (endOpt.isPresent()) {
                    LocalDateTime endActual = endOpt.get();
                    temp = new Event(desc, null, endActual, startString, "");
                    break;
                } else {
                    temp = new Event(desc, null, null, startString, endString);
                    break;
                }
            case "delete":
                if (parts.length == 1) {
                    ui.setLastResponse(sunnyVoice.getException("insufficient"));
                    commandType = "error";
                    throw new InsufficientInfoException(sunnyVoice.getException("insufficient"));
                }
                int index3;
                try {
                    index3 = Integer.parseInt(parts[1]) - 1;
                } catch (NumberFormatException e) {
                    ui.setLastResponse(sunnyVoice.getException("notInteger"));
                    commandType = "error";
                    throw new NumberFormatException();
                }

                if (index3 >= 0 && index3 < taskboard.getTaskCount() && taskboard.getTask(index3) != null) {//if tasks at index has something
                    temp = taskboard.getTask(index3);
                    taskboard.removeTask(index3);
                    storer.saveTasks(taskboard.getTasks());

                    ui.replyDelete(temp);
                    commandType = "delete";
                    return;
                } else {//if there's nothing at index, either null or out of bounds
                    ui.setLastResponse(sunnyVoice.getException("missingTask"));
                    commandType = "error";
                    throw new TaskOutOfBoundsException(sunnyVoice.getException("missingTask"));
                }
            case "find":
                if (parts.length == 1){
                    ui.setLastResponse(sunnyVoice.getException("insufficient"));
                    commandType = "error";
                    throw new InsufficientInfoException(sunnyVoice.getException("insufficient"));
                }
                if (parts.length > 2){
                    ui.setLastResponse(sunnyVoice.getException("tooManyKeywords"));
                    commandType = "error";
                    throw new TooManyKeywordsException(sunnyVoice.getException("tooManyKeywords"));
                }
                String keyword = parts[1];
                ArrayList<Task> foundTasks = new ArrayList<>();
                for (Task task : taskboard.getTasks()){
                    if (task.getDesc().contains(keyword)){
                        foundTasks.add(task);
                    }
                }

                ui.replyFind(foundTasks);
                commandType = "find";
                return;
        }
        if (temp == null) {
            ui.setLastResponse(sunnyVoice.getException("unrecognised"));
            commandType = "error";
            throw new UnrecognisedTaskException(sunnyVoice.getException("unrecognised"));
        }

        assert temp != null : "Task must exist before adding it to taskboard";
        taskboard.addTask(temp);
        storer.saveTasks(taskboard.getTasks());

        ui.replyTask(temp);
    }

    /**
     * Returns the type of command the user's input gave.
     *
     * @return the command type from the user's input.
     */
    public String getCommandType() {
        return commandType;
    }

    /**
     * Sets the type of command the user's input gave.
     *
     * @param text the command type of the user's input to be set.
     */
    public void setCommandType(String text){
        commandType = text;
    }

}
