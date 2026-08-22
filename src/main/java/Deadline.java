import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    protected String notDate;
    private final DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Deadline(String description, LocalDateTime by, String notDate) {
        super(description);
        this.by = by;
        this.notDate = notDate;
    }

    public String getFileFormat(){
        if (this.notDate.equals("")){
            return " | " + this.by.format(customFormatter) + " | " + (this.isDone() ? "1" : "0");
        } else {
            return " | " + this.notDate + " | " + (this.isDone() ? "1" : "0");
        }
    }

    @Override
    public String toString() {
        if (this.notDate.equals("")){
            return "[D]" + super.toString() + " (by: " + this.by.format(customFormatter) + ")";
        } else {
            return "[D]" + super.toString() + " (by: " + this.notDate + ")";
        }
    }
}