package sunnyexception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class taskOutOfBoundsExceptionTest {

    @Test
    public void exceptionStoresMessage() {
        taskOutOfBoundsException exception = new taskOutOfBoundsException("There's no task there!");

        assertEquals("There's no task there!", exception.getMessage());
    }
}
