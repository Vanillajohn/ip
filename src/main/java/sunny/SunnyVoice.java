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
    private SunnyVoice() {}
    private static class Holder {
        private static final SunnyVoice INSTANCE = new SunnyVoice();
    }
    /**
     * Returns the singleton instance of SunnyVoice.
     *
     * @return the SunnyVoice singleton instance
     */
    public static SunnyVoice getInstance(){
        return Holder.INSTANCE;
    }

    List<String> greetings = List.of(
            "Yeah, it's me, Sunny☀.\nHow can I annoy you today?",
            "What now?\nCan you go bother someone else?",
            "It's me, Sunny☀! \nNow can you do me a favour and buzz off?",
            "It's me, Sunny☀! \nHow ironic.☀☀☀");
    List<String> goodbyes = List.of(
            "Don't tell anyone I helped you, got it?",
            "I can't believe I get worried when I'm not there to help you.",
            "Jeez, you really depend on me, don't you?");
    List<String> taskRemarks = List.of(
            "I'm only helping you because you now owe me.",
            "I could be doing so much more right now.",
            "What a waste of time.",
            "Can't you do this yourself?",
            "I'd better not see my name in any of these.");
    List<String> taskMarkRemarks = List.of(
            "Yeah, yeah. Marked it already",
            "Done. Can you leave me alone now?",
            "You better help me when I need it!");
    List<String> taskUnmarkRemarks = List.of(
            "Can't you unmark this yourself?",
            "Done. Can you leave me alone now?",
            "You better help me when I need it!");
    List<String> listRemarks = List.of(
            "Here's your LL (lame list):",
            "Go write this down so I don't have to show it to you again:",
            "☀☀List deleted☀☀. Kidding. Here you go:");
    List<List<String>> listNumberRemarks = List.of(
            List.of("Now you have ", " tasks in your list. Whoop de doo."),
            List.of("Go do your ", " tasks already!"));
    List<String> taskAddRemarks = List.of(
            "Task added. Can I go now?",
            "Task added. Appreciation assumed, accepted and ignored.",
            "Can I don't? Fine, fine, I'll add your task.");
    List<List<String>> descEmptyRemarks = List.of(
            List.of("A "," description can't be empty, dummy!"),
            List.of("What am I supposed to do if your "," task has no description?"),
            List.of("A ","? For what???"));
    List<String> unrecognisedRemarks = List.of(
            "Is that a joke? What does that mean?",
            "I normally don't understand you, but now I really don't.",
            "Say something weird again and Sunny's gonna get Stormy.");
    List<String> insufficientRemarks = List.of(
            "Very funny. Not enough info and I won't help you!",
            "You didn't give me enough info! Don't test me!");
    List<String> tooManyRemarks = List.of(
            "Your taskboard can only hold so many!",
            "I can't add any more!",
            "Go get a bigger taskboard!");
    List<String> deletingRemarks = List.of(
            "If you want this deleted, why did you add it?",
            "I've added it and now you want me to remove it?",
            "Now I'm DELETING for you too?");
    List<String> missingTaskRemarks = List.of(
            "There's no task there!",
            "I ain't doing anything if nothing's there!",
            "Do you need your scanner checked? That index has no task!",
            "How is it that you see worse with two eyes than I, with none?");
    List<String> notIntegerRemarks = List.of(
            "I need a VALID INTEGER doofus!",
            "You're in CS and you don't know what a VALID INTEGER is?");
    List<String> tooManyKeywordsRemarks = List.of(
            "If you've got more than one keyword, search it yourself!",
            "I'll tolerate at most one keyword!",
            "Uh oh, you've exceeded the number of keywords I can care about!",
            "ONE keyword! Keep it simple, stupid!");
    List<String> foundTasksRemarks = List.of(
            "Here's the tasks you want. I should remove them while you're not looking.",
            "Found these so far. Am I your butler or something?",
            "Are these what you're looking for? I sure hope not.");
    List<List<String>> alreadyDoneRemarks = List.of(
            List.of("It's already ",". Are you blind or something?"),
            List.of("Did you forget I've "," it already? No wonder you need a taskboard."));
    List<String> noFoundTasksRemarks = List.of(
            "Can't find anything. You'd better not be wasting my time with misinputs!",
            "There isn't anything with that keyword. This isn't some excuse to spend time with me, is it?");
    List<String> noListRemarks = List.of(
            "Your list's empty. Are you procrastinating or just plain lazy?",
            "Wow. No tasks. Shocker. I'm appalled.");
    List<String> noTasksLeft = List.of(
            "That was your last task. I can go now, right?",
            "Your list is empty now. I'm shaking with excitement.");
    List<String> incorrectDateFormat = List.of(
            "Aww, look who can't give a date in dd/mm/yyyy or dd-mm-yyyy format!",
            "The only date I'm interested in has the format dd/mm/yyyy or dd-mm-yyyy!");
    List<String> haveEvents = List.of(
            "Well aren't you busy with all these tasks on that day:",
            "I'm DEFINITELY not bummed that I can't see you that day with these tasks:");
    List<String> haveDeadlines = List.of(
            "By the way, you still have deadlines due:",
            "Don't forget about these deadlines, you nimrod:");
    List<String> noEvents = List.of(
            "Nothing's happening on that day. Sure you didn't input a task wrongly, you dummy?",
            "Seems like you're free that day. And yes, I DON'T want to see you either!");
    List<String> noDeadlines = List.of(
            "Huh, no deadlines. Sure you inputted all your tasks correctly, you idiot?",
            "A lack of deadlines is probably a lack of productivity, or a misinput!");

    private Random rand = new Random();

    /**
     * Returns a list of exception remarks, usually because data is needed for the remark.
     *
     * @param pool the name of the List<String> remarks available above.
     * @return a List<String> of exception remarks.
     */
    public List<String> getListException (String pool) {
        int index;
        switch (pool){
            case "descEmpty":
                index = this.rand.nextInt(descEmptyRemarks.size());
                return descEmptyRemarks.get(index);
            case "listNumber":
                index = this.rand.nextInt(listNumberRemarks.size());
                return listNumberRemarks.get(index);
        }
        assert false : "pool should trigger a case";
        return null;
    }

    /**
     * Returns an exception remark.
     *
     * @param pool the name of the List<String> remarks available above.
     * @return a String exception remark.
     */
    public String getException(String pool) {
        switch(pool){
            case "tooMany":
                return getExceptionHelper(tooManyRemarks);
            case "insufficient":
                return getExceptionHelper(insufficientRemarks);
            case "missingTask":
                return getExceptionHelper(missingTaskRemarks);
            case "unrecognised":
                return getExceptionHelper(unrecognisedRemarks);
            case "notInteger":
                return getExceptionHelper(notIntegerRemarks);
            case "tooManyKeywords":
                return getExceptionHelper(tooManyKeywordsRemarks);
            case "incorrectDateFormat":
                return getExceptionHelper(incorrectDateFormat);
        }
        return "The input text is wrong, you dumb developer!";
    }

    private String getExceptionHelper(List<String> text) {
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
                speakHelper(taskMarkRemarks);
                break;
            case "taskUnmark":
                speakHelper(taskUnmarkRemarks);
                break;
            case "listRemarks":
                speakHelper(listRemarks);
                break;
            case "taskAddRemarks":
                speakHelper(taskAddRemarks);
                break;
            case "deleting":
                speakHelper(deletingRemarks);
                break;
            case "notInteger":
                speakHelper(notIntegerRemarks);
                break;
            case "foundTasks":
                speakHelper(foundTasksRemarks);
                break;
            case "noFoundTasks":
                speakHelper(noFoundTasksRemarks);
                break;
            case "noList":
                speakHelper(noListRemarks);
                break;
            case "noTasksLeft":
                speakHelper(noTasksLeft);
                break;
            case "haveEvents":
                speakHelper(haveEvents);
                break;
            case "haveDeadlines":
                speakHelper(haveDeadlines);
                break;
            case "noEvents":
                speakHelper(noEvents);
                break;
            case "noDeadlines":
                speakHelper(noDeadlines);
                break;
            default:
                System.out.println("The input text is wrong, you dumb developer!");
        }
    }

    /**
     * Returns a remark based on the specified pool.
     *
     * @param pool the name of the List<String> remarks available above.
     * @return the remark chosen from the List<String>
     */
    public String getText(String pool) {//when a remark is given
        switch(pool) {
            case "greetings":
                return getTextHelper(greetings);
            case "goodbyes":
                return getTextHelper(goodbyes);
            case "taskRemarks":
                return getTextHelper(taskRemarks);
            case "taskMark":
                return getTextHelper(taskMarkRemarks);
            case "taskUnmark":
                return getTextHelper(taskUnmarkRemarks);
            case "listRemarks":
                return getTextHelper(listRemarks);
            case "taskAddRemarks":
                return getTextHelper(taskAddRemarks);
            case "deleting":
                return getTextHelper(deletingRemarks);
            case "foundTasks":
                return getTextHelper(foundTasksRemarks);
            case "noFoundTasks":
                return getTextHelper(noFoundTasksRemarks);
            case "noList":
                return getTextHelper(noListRemarks);
            case "noTasksLeft":
                return getTextHelper(noTasksLeft);
            case "haveEvents":
                return getTextHelper(haveEvents);
            case "haveDeadlines":
                return getTextHelper(haveDeadlines);
            case "noEvents":
                return getTextHelper(noEvents);
            case "noDeadlines":
                return getTextHelper(noDeadlines);
        }
        return "The input text is wrong, you dumb developer!";
    }

    /**
     * Returns the components of a specific listNumberRemark remark.
     *
     * @return the components of the remark as a List<String>.
     */
    public List<String> getListNumberRemarks() {
        int index = this.rand.nextInt(listNumberRemarks.size());
        return listNumberRemarks.get(index);
    }

    /**
     * Returns the components of a specific descEmptyRemarks remark.
     *
     * @return the components of the remark as a List<String>.
     */
    public List<String> getDescEmptyRemarks() {
        int index = this.rand.nextInt(descEmptyRemarks.size());
        return descEmptyRemarks.get(index);
    }

    /**
     * Returns the components of a specific getAlreadyDoneRemarks remark.
     *
     * @return the components of the remark as a List<String>.
     */
    public List<String> getAlreadyDoneRemarks() {
        int index = this.rand.nextInt(alreadyDoneRemarks.size());
        return alreadyDoneRemarks.get(index);
    }

    private String getTextHelper(List<String> text) {
        assert !text.isEmpty() : "text should be non-empty";
        int index = this.rand.nextInt(text.size());
        return text.get(index);
    }

    private void speakHelper(List<String> text) {
        assert !text.isEmpty() : "text should be non-empty";
        int index = this.rand.nextInt(text.size());
        System.out.println(text.get(index));
    }
}