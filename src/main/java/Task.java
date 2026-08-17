public class Task{
    private String desc;
    private boolean marked;

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
}