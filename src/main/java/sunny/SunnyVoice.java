package sunny;

import java.util.List;
import java.util.Random;

/**
 * Handles the "speaking" part of the chatbot.
 * A remark is the text the chatbot would speak.
 * <p>This class follows the singleton pattern and can be accessed
 *  * through {@link #getInstance()}.</p>
 */
public class SunnyVoice {
    private SunnyVoice(){}
    private static class holder{
        private static final SunnyVoice INSTANCE = new SunnyVoice();
    }
    /**
     * Returns the singleton instance of SunnyVoice.
     *
     * @return the SunnyVoice singleton instance
     */
    public static SunnyVoice getInstance(){
        return SunnyVoice.holder.INSTANCE;
    }

    List<String> greetings = List.of("Yeah, it's me, Sunny.\nWhat do you want?", "What now?\nCan you go bother someone else?");
    List<String> goodbyes = List.of("Don't tell anyone I helped you, got it?", "See you never.", "Jeez, you really depend on me, don't you?");
    List<String> taskRemarks = List.of("", "I'm only helping you because you now owe me.", "I could be doing so much more right now.",
            "What a waste of time.", "Can't you do this yourself?");
    List<String> taskMark = List.of("Yeah, yeah. Marked it already", "Done. Can you leave me alone now?", "You better help me when I need it!");
    List<String> taskUnmark = List.of("Can't you unmark this yourself?", "Done. Can you leave me alone now?", "You better help me when I need it!");
    List<String> listRemarks = List.of("Here's your LL (lame list):", "Go write this down so I don't have to show it to you again:");
    List<List<String>> listNumberRemarks = List.of(List.of("Now you have ", " tasks in your list. Whoop de doo."), List.of("Go do your ", " tasks already!"));
    List<String> taskAddRemarks = List.of("Task added. Can I go now?", "Task added. Appreciation ignored.");
    List<List<String>> descEmpty = List.of(List.of("A "," description can't be empty, dummy!"), List.of("What am I supposed to do if your "," task has no description?"));
    List<String> unrecognised = List.of("Is that a joke? What does that mean?", "I normally don't understand you, but now I really don't.");
    List<String> insufficient = List.of("Very funny. Not enough info and I won't help you!", "You didn't give me enough info! Don't test me!");
    List<String> tooMany = List.of("Your taskboard can only hold so many!", "I can't add any more!", "Go get a bigger taskboard!");
    List<String> deleting = List.of("If you want this deleted, why did you add it?", "I've added it and now you want me to remove it?", "Now I'm DELETING for you too?");
    List<String> missingTask = List.of("There's no task there!", "I ain't doing anything if nothing's there!");
    List<String> notInteger = List.of("I need a VALID INTEGER doofus!", "You're in CS and you don't know what a VALID INTEGER is?");
    List<String> tooManyKeywords = List.of("If you've got more than one keyword, search it yourself!", "I'll tolerate at most one keyword!");
    List<String> foundTasks = List.of("Here's the tasks you want. I should remove them while you're not looking.", "Found these so far. Am I your butler or something?");

    private Random rand = new Random();

    /**
     * Prints a remark that requires data in the form of an integer.
     * Immediately prints as opposed to returning remarks as strings.
     *
     * @param pool the name of the List<String> remarks available above.
     * @param i the integer required.
     */
    public void speakListNum(String pool, int i){
        int index;
        switch(pool){
            case "listNumberRemarks":
                index = this.rand.nextInt(listNumberRemarks.size());
                String start = listNumberRemarks.get(index).get(0);
                String end = listNumberRemarks.get(index).get(1);
                System.out.println(start + (i - 1) + end);
        }
    }

    /**
     * Returns a list of exception remarks, usually because data is needed for the remark.
     *
     * @param pool the name of the List<String> remarks available above.
     * @return a List<String> of exception remarks.
     */
    public List<String> getListException (String pool){
        int index;
        switch (pool){
            case "descEmpty":
                index = this.rand.nextInt(descEmpty.size());
                return descEmpty.get(index);
        }
        return null;
    }

    /**
     * Returns an exception remark.
     *
     * @param pool the name of the List<String> remarks available above.
     * @return a String exception remark.
     */
    public String getException(String pool){
        switch(pool){
            case "tooMany":
                return getExceptionHelper(tooMany);
            case "insufficient":
                return getExceptionHelper(insufficient);
            case "missingTask":
                return getExceptionHelper(missingTask);
            case "unrecognised":
                return getExceptionHelper(unrecognised);
            case "notInteger":
                return getExceptionHelper(notInteger);
            case "tooManyKeywords":
                return getExceptionHelper(tooManyKeywords);
        }
        return null;
    }

    private String getExceptionHelper(List<String> text){
        int index = this.rand.nextInt(text.size());
        return text.get(index);
    }

    /**
     * Prints a remark based on the specified pool.
     * Immediately prints as opposed to returning remarks as strings.
     *
     * @param pool the name of the List<String> remarks available above.
     */
    public void speak(String pool) {//when a remark is given
        switch(pool) {
            case "greetings":
                speakHelper(greetings);
                break;
            case "goodbyes":
                speakHelper(goodbyes);
                break;
            case "taskRemarks":
                speakHelper(taskRemarks);
                break;
            case "taskMark":
                speakHelper(taskMark);
                break;
            case "taskUnmark":
                speakHelper(taskUnmark);
                break;
            case "listRemarks":
                speakHelper(listRemarks);
                break;
            case "taskAddRemarks":
                speakHelper(taskAddRemarks);
                break;
            case "deleting":
                speakHelper(deleting);
                break;
            case "notInteger":
                speakHelper(notInteger);
                break;
            case "foundTasks":
                speakHelper(foundTasks);
                break;
        }
    }

    private void speakHelper(List<String> text){
        int index = this.rand.nextInt(text.size());
        System.out.println(text.get(index));
    }
}