package sunnyexception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class tooManyTasksExceptionTest {

    @Test
    public void exceptionStoresMessage() {
        tooManyTasksException exception = new tooManyTasksException("Your taskboard can only hold so many!");

        assertEquals("Your taskboard can only hold so many!", exception.getMessage());
    }
}
