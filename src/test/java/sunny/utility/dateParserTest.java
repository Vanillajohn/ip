package sunny.utility;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class dateParserTest {

    @Test
    public void getInstanceReturnsSameInstance() {
        dateParser first = dateParser.getInstance();
        dateParser second = dateParser.getInstance();

        assertSame(first, second);
    }

    @Test
    public void parseValidDate() {
        dateParser parser = dateParser.getInstance();

        Optional<LocalDateTime> result = parser.parse("24/08/2026");

        assertTrue(result.isPresent());
        assertEquals(
                LocalDateTime.of(2026, 8, 24, 23, 59),
                result.get()
        );
    }

    @Test
    public void parseNullThrowsCorrectException() {
        dateParser parser = dateParser.getInstance();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse(null)
        );

        assertEquals("Input string cannot be null", exception.getMessage());
    }

    @Test
    public void parseSlashDate() {
        dateParser parser = dateParser.getInstance();

        Optional<LocalDateTime> result = parser.parse("24/08/2026");

        assertTrue(result.isPresent());
        assertEquals(
                LocalDateTime.of(2026, 8, 24, 23, 59),
                result.get()
        );
    }

    @Test
    public void parseDashDate() {
        dateParser parser = dateParser.getInstance();

        Optional<LocalDateTime> result = parser.parse("24-08-2026");

        assertTrue(result.isPresent());
        assertEquals(
                LocalDateTime.of(2026, 8, 24, 23, 59),
                result.get()
        );
    }

    @Test
    public void parseDateWithTime() {
        dateParser parser = dateParser.getInstance();

        Optional<LocalDateTime> result = parser.parse("24/08/2026 1430");

        assertTrue(result.isPresent());
        assertEquals(
                LocalDateTime.of(2026, 8, 24, 14, 30),
                result.get()
        );
    }

    @Test
    public void parseTrimsWhitespace() {
        dateParser parser = dateParser.getInstance();

        Optional<LocalDateTime> result =
                parser.parse("   24/08/2026   ");

        assertTrue(result.isPresent());
        assertEquals(
                LocalDateTime.of(2026, 8, 24, 23, 59),
                result.get()
        );
    }

    @Test
    public void parseInvalidCalendarDateReturnsEmpty() {
        dateParser parser = dateParser.getInstance();

        Optional<LocalDateTime> result =
                parser.parse("29/02/2026");

        assertTrue(result.isEmpty());
    }
}
