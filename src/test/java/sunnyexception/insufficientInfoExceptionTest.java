package sunnyexception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class insufficientInfoExceptionTest {
    @Test
    public void exceptionStoresMessage() {
        insufficientInfoException exception = new insufficientInfoException("Very funny. Not enough info and I won't help you!");

        assertEquals("Very funny. Not enough info and I won't help you!", exception.getMessage());
    }
}
