package task;

public class Task{
    protected String desc;
    protected boolean marked;

    public Task (String desc){
        this.desc = desc;
    }
    public String getStatusIcon() {
        return (marked ? "X" : " ");
    }
    public String getDesc(){
        return this.desc;
    }
    public void mark(){
        this.marked = true;
    }
    public void unmark(){
        this.marked = false;
    }
    public boolean isDone(){
        return marked;
    }
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.desc;
    }
}