package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime start, end;
    protected String notStart, notEnd;
    private final DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Event (String desc, LocalDateTime start, LocalDateTime end, String notStart, String notEnd) {
        super(desc);
        this.start = start;
        this.end = end;
        this.notStart = notStart;
        this.notEnd = notEnd;
    }

    public String getFileFormat() {
        if (this.notStart.equals("") && this.notEnd.equals("")){
            return " | " + this.start.format(customFormatter) + " | " + this.end.format(customFormatter) + " | " + (this.isDone() ? "1" : "0");
        } else if (this.notStart.equals("")) {
            return " | " + this.start.format(customFormatter) + " | " + this.notEnd + " | " + (this.isDone() ? "1" : "0");
        } else if (this.notEnd.equals("")) {
            return " | " + this.notStart + " | " + this.end.format(customFormatter) + " | " + (this.isDone() ? "1" : "0");
        } else {
            return " | " + this.notStart + " | " + this.notEnd + " | " + (this.isDone() ? "1" : "0");
        }
    }

    @Override
    public String toString() {
        if (this.notStart.equals("") && this.notEnd.equals("")){
            return "[E]" + super.toString() + "(From: " + this.start.format(customFormatter) + " to: " + this.end.format(customFormatter) + ")";
        } else if (this.notStart.equals("")) {
            return "[E]" + super.toString() + "(From: " + this.start.format(customFormatter) + " to: " + this.notEnd + ")";
        } else if (this.notEnd.equals("")) {
            return "[E]" + super.toString() + "(From: " + this.notStart + " to: " + this.end.format(customFormatter) + ")";
        } else {
            return "[E]" + super.toString() + "(From: " + this.notStart + " to: " + this.notEnd + ")";
        }
    }
}