package sunny.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.Optional;

public class dateParser{
    private dateParser(){}
    private static class holder{
        private static final dateParser INSTANCE = new dateParser();
    }

    public static dateParser getInstance(){
        return dateParser.holder.INSTANCE;
    }
    private static final DateTimeFormatter FLEXIBLE_FORMATTER = new DateTimeFormatterBuilder()
            // 1. Handle the date part variants
            .appendPattern("[dd/MM/uuuu][dd-MM-uuuu]")
            // 2. Handle the optional space and time part
            .appendPattern("[ HHmm]")
            // 3. Fallback to midnight if the time pattern is missing
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 23)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 59)
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);

    public Optional<LocalDateTime> parse(String inputDate){//dd/mm/yyyy, dd-mm-yyyy, either two with time
        if (inputDate == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        try {
            return Optional.of(LocalDateTime.parse(inputDate.trim(), FLEXIBLE_FORMATTER));
        } catch (Exception e) {
            return Optional.empty(); // Not a date format
        }
    }
}