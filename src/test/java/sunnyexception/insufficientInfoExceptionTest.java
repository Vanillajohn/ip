package sunnyexception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class insufficientInfoExceptionTest {
    @Test
    public void exceptionStoresMessage() {
        insufficientInfoException exception = new insufficientInfoException("Very funny. Not enough info and I won't help you!");

        assertEquals("Very funny. Not enough info and I won't help you!", exception.getMessage());
    }
}
