package sunnyexception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class tooManyTasksExceptionTest {

    @Test
    public void exceptionStoresMessage() {
        tooManyTasksException exception = new tooManyTasksException("Your taskboard can only hold so many!");

        assertEquals("Your taskboard can only hold so many!", exception.getMessage());
    }
}
