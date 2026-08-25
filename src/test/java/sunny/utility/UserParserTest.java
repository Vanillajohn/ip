package sunny.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sunny.taskboard.Taskboard;
import sunny.ui.UI;
import sunnyexception.SunnyException;
import sunnyexception.TaskEmptyDescException;
import sunnyexception.insufficientInfoException;
import sunnyexception.taskOutOfBoundsException;
import task.Deadline;
import task.Task;
import task.ToDo;

public class UserParserTest {
    private UI ui = UI.getInstance();

    @BeforeEach
    public void resetTaskboard() {
        Taskboard taskboard = Taskboard.getInstance();
        taskboard.clearTasks();
    }


    @Test
    public void todoWithoutDescriptionThrowsException() {
        UserParser parser = UserParser.getInstance();

        assertThrows(
                TaskEmptyDescException.class,
                () -> parser.userParse("todo", ui)
        );
    }

    @Test
    public void deadlineWithoutDescriptionThrowsException() {
        UserParser parser = UserParser.getInstance();

        assertThrows(
                TaskEmptyDescException.class,
                () -> parser.userParse("deadline", ui)
        );
        assertThrows(
                insufficientInfoException.class,
                () -> parser.userParse("deadline submit report", ui)
        );
    }

    @Test
    public void eventWithoutDescriptionThrowsException() {
        UserParser parser = UserParser.getInstance();

        assertThrows(
                TaskEmptyDescException.class,
                () -> parser.userParse("event", ui)
        );
        assertThrows(
                insufficientInfoException.class,
                () -> parser.userParse("event meeting", ui)
        );
    }

    @Test
    public void todoCommandCreatesTodo() throws SunnyException {
        UserParser parser = UserParser.getInstance();
        Taskboard taskboard = Taskboard.getInstance();

        int originalCount = taskboard.getTaskCount();

        parser.userParse("todo buy milk", ui);

        assertEquals(originalCount + 1, taskboard.getTaskCount());

        Task task = taskboard.getTask(taskboard.getTaskCount() - 1);

        assertTrue(task instanceof ToDo);
        assertEquals(" buy milk", task.getDesc());
    }

    @Test
    public void markCommandMarksTask() throws SunnyException {
        UserParser parser = UserParser.getInstance();
        Taskboard taskboard = Taskboard.getInstance();

        Task task = new ToDo("buy milk");
        taskboard.addTask(task);
        taskboard.setTaskCount(1);

        parser.userParse("mark 1", ui);

        assertTrue(taskboard.getTask(0).isDone());
    }

    @Test
    public void unmarkCommandUnmarksTask() throws SunnyException {
        UserParser parser = UserParser.getInstance();
        Taskboard taskboard = Taskboard.getInstance();

        Task task = new ToDo("buy milk");
        task.mark();

        taskboard.addTask(task);
        taskboard.setTaskCount(1);

        parser.userParse("unmark 1", ui);

        assertFalse(taskboard.getTask(0).isDone());
    }

    @Test
    public void markInvalidIndexThrowsException() {
        UserParser parser = UserParser.getInstance();

        assertThrows(
                taskOutOfBoundsException.class,
                () -> parser.userParse("mark 999", ui)
        );
    }

    @Test
    public void deleteInvalidIndexThrowsException() {
        UserParser parser = UserParser.getInstance();

        assertThrows(
                taskOutOfBoundsException.class,
                () -> parser.userParse("delete 999", ui)
        );
    }

    @Test
    public void markWithNonNumberThrowsNumberFormatException() {
        UserParser parser = UserParser.getInstance();

        assertThrows(
                NumberFormatException.class,
                () -> parser.userParse("mark banana", ui)
        );
    }

    @Test
    public void deadlineCommandCreatesDeadline() throws SunnyException {
        UserParser parser = UserParser.getInstance();
        Taskboard taskboard = Taskboard.getInstance();

        int originalCount = taskboard.getTaskCount();

        parser.userParse(
                "deadline submit report /by 24/08/2026",
                ui
        );

        assertEquals(originalCount + 1, taskboard.getTaskCount());

        Task task = taskboard.getTask(taskboard.getTaskCount() - 1);

        assertTrue(task instanceof Deadline);
        assertEquals("submit report", task.getDesc());
    }
}
