package sunny.utility;

import java.time.LocalDateTime;
import java.util.Optional;
import sunny.storage.Storage;
import sunny.taskboard.Taskboard;
import sunny.SunnyVoice;
import sunny.ui.UI;
import sunnyexception.*;
import task.*;

public class UserParser {
    private UserParser(){}
    private static class holder{
        private static final UserParser INSTANCE = new UserParser();
    }
    public static UserParser getInstance(){
        return UserParser.holder.INSTANCE;
    }

    private Storage storer = Storage.getInstance();
    private dateParser parser = dateParser.getInstance();
    private Taskboard taskboard = Taskboard.getInstance();
    private SunnyVoice sunnyVoice = SunnyVoice.getInstance();

    public void userParse(String input, UI ui) throws SunnyException{
        String[] parts = input.split(" ");
        String command = parts[0].toLowerCase();
        Task temp = null;
        switch (command) {
            case "bye":
                ui.goodbye();
                return;
            case "list":
                ui.list();
                return;
            case "mark":
                if (parts.length == 1){
                    throw new insufficientInfoException(sunnyVoice.getException("insufficient"));
                }
                int index1 = Integer.parseInt(parts[1]) - 1;
                if (index1 >= 0 && index1 < taskboard.getTaskCount() && taskboard.getTask(index1) != null){ //if tasks at index has something
                    taskboard.getTask(index1).mark();
                    storer.saveTasks(taskboard.getTasks());

                    ui.mark(index1);
                    return;
                } else {//if there's nothing at index, either null or out of bounds
                    throw new taskOutOfBoundsException(sunnyVoice.getException("missingTask"));
                }
            case "unmark":
                if (parts.length == 1){
                    throw new insufficientInfoException(sunnyVoice.getException("insufficient"));
                }
                int index2 = Integer.parseInt(parts[1]) - 1;
                if (index2 >= 0 && index2 < taskboard.getTaskCount() && taskboard.getTask(index2) != null) {//if tasks at index has something
                    taskboard.getTask(index2).unmark();
                    storer.saveTasks(taskboard.getTasks());

                    ui.unmark(index2);
                    return;
                } else {//if there's nothing at index, either null or out of bounds
                    throw new taskOutOfBoundsException(sunnyVoice.getException("missingTask"));
                }
            case "todo":
                if (parts.length == 1){
                    throw new TaskEmptyDescException(sunnyVoice.getListException("descEmpty"), "todo");
                }
                temp = new ToDo(String.join(" ", input.substring(4)));
                break;
            case "deadline":
                if (parts.length == 1){
                    throw new TaskEmptyDescException(sunnyVoice.getListException("descEmpty"), "deadline");
                }
                int slashIndex = input.indexOf("/by");
                if (slashIndex == -1){
                    throw new insufficientInfoException(sunnyVoice.getException("insufficient"));
                }
                String description = String.join(" ", input.substring(9, slashIndex)).trim();

                Optional<LocalDateTime> dateOpt = parser.parse(input.substring(slashIndex + 3).trim());

                if (dateOpt.isPresent()) {
                    LocalDateTime actualDate = dateOpt.get();
                    temp = new Deadline(description, actualDate, "");
                    break;
                } else {
                    temp = new Deadline(description, null, input.substring(slashIndex + 3).trim());
                    break;
                }

            case "event":
                if (parts.length == 1){
                    throw new TaskEmptyDescException(sunnyVoice.getListException("descEmpty"), "event");
                }
                int firstSlash = input.indexOf("/from");
                int secondSlash = input.indexOf("/to");
                if (firstSlash == -1 || secondSlash == -1 || firstSlash > secondSlash){
                    throw new insufficientInfoException(sunnyVoice.getException("insufficient"));
                }
                String desc = input.substring(6, firstSlash);
                String startString = input.substring(firstSlash + 5, secondSlash).trim();
                String endString = input.substring(secondSlash + 3).trim();
                Optional<LocalDateTime> startOpt = parser.parse(startString);
                Optional<LocalDateTime> endOpt = parser.parse(endString);

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
                if (parts.length == 1){
                    throw new insufficientInfoException(sunnyVoice.getException("insufficient"));
                }
                int index3 = Integer.parseInt(parts[1]) - 1;
                if (index3 >= 0 && index3 < taskboard.getTaskCount() && taskboard.getTask(index3) != null) {//if tasks at index has something
                    temp = taskboard.getTask(index3);
                    taskboard.removeTask(index3);
                    taskboard.setTaskCount(-1);
                    storer.saveTasks(taskboard.getTasks());

                    ui.delete(temp);
                    return;
                } else {//if there's nothing at index, either null or out of bounds
                    throw new taskOutOfBoundsException(sunnyVoice.getException("missingTask"));
                }
        }
        if (temp == null) {
            throw new UnrecognisedTaskException(sunnyVoice.getException("unrecognised"));
        }
        taskboard.addTask(temp);
        taskboard.setTaskCount(1);
        storer.saveTasks(taskboard.getTasks());

        ui.task(temp);
    }

}