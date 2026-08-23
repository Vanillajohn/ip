package sunnyexception;

import java.util.List;
public class TaskEmptyDescException extends SunnyException{
    public TaskEmptyDescException (List<String> message, String type){
        String temp = message.get(0) + type + message.get(1);
        super(temp);
    }
}