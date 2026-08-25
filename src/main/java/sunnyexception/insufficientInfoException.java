package sunnyexception;

/**
 * Indicates that the user provided insufficient info for a task.
 * This class extends {@link SunnyException}.
 */
public class insufficientInfoException extends SunnyException{
    /**
     * Constructs the exception with the specified message.
     *
     * @param message the message describing the exception.
     */
    public insufficientInfoException (String message){
        super(message);
    }
}