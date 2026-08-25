package task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {
    @Test
    public void ToDotoStringTest(){
        ToDo temp = new ToDo("date Sunny");
        assertEquals("[T][ ] date Sunny", temp.toString());
    }
}
