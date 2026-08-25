package sunnyexception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SunnyExceptionTest {

    @Test
    public void exceptionStoresMessage() {
        SunnyException exception = new SunnyException("A %$^@#&!^@ DATE???");

        assertEquals("A %$^@#&!^@ DATE???", exception.getMessage());
    }
}