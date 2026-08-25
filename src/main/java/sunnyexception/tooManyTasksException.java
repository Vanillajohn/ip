package sunnyexception;

/**
 * Indicates that the user attempted adding more tasks than the taskboard allows.
 * This class extends {@link SunnyException}.
 */
public class tooManyTasksException extends SunnyException{
    /**
     * Constructs the exception with the specified message.
     *
     * @param message the message describing the exception.
     */
    public tooManyTasksException (String message){
        super(message);
    }
}