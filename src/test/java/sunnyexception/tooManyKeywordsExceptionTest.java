package sunnyexception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class tooManyKeywordsExceptionTest {

    @Test
    public void exceptionStoresMessage() {
        tooManyKeywordsException exception = new tooManyKeywordsException("I'll tolerate at most one keyword!");

        assertEquals("I'll tolerate at most one keyword!", exception.getMessage());
    }
}
