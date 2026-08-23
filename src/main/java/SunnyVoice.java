import java.util.List;
import java.util.Random;

public class SunnyVoice {
    private SunnyVoice(){}
    private static class holder{
        private static final SunnyVoice INSTANCE = new SunnyVoice();
    }
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

    private Random rand = new Random();

    public void speakListNum(String pool, int i){//specifically if some data is needed in a remark
        int index;
        switch(pool){
            case "listNumberRemarks":
                index = this.rand.nextInt(listNumberRemarks.size());
                String start = listNumberRemarks.get(index).get(0);
                String end = listNumberRemarks.get(index).get(1);
                System.out.println(start + (i - 1) + end);
        }
    }

    public List<String> getListException (String pool){
        int index;
        switch (pool){
            case "descEmpty":
                index = this.rand.nextInt(descEmpty.size());
                return descEmpty.get(index);
        }
        return null;
    }

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
        }
        return null;
    }

    private String getExceptionHelper(List<String> text){
        int index = this.rand.nextInt(text.size());
        return text.get(index);
    }

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
        }
    }

    private void speakHelper(List<String> text){
        int index = this.rand.nextInt(text.size());
        System.out.println(text.get(index));
    }
}