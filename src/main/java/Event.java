public class Event extends Task {
    protected String start, end;

    public Event (String desc, String start, String end) {
        super(desc);
        this.start = start;
        this.end = end;
    }

    public String getFileFormat() {
        return " | " + this.start + " | " + this.end + " | " + (this.isDone() ? "1" : "0");
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(From: " + this.start + " to: " + this.end + ")";
    }
}