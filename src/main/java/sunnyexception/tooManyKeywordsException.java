package sunnyexception;

/**
 * Indicates that the user entered more than one keyword for the find command.
 */
public class tooManyKeywordsException extends SunnyException {

    /**
     * Constructor for the exception that prints a message.
     */
    public tooManyKeywordsException(String message) {
        super(message);
    }
}
