import java.util.Scanner;

public class UI {
    private UI(){}
    private static class holder{
        private static final UI INSTANCE = new UI();
    }
    public static UI getInstance(){
        return UI.holder.INSTANCE;
    }

    private Taskboard taskboard = Taskboard.getInstance();
    private SunnyVoice sunnyVoice = SunnyVoice.getInstance();
    private UserParser userParser = UserParser.getInstance();
    Scanner scanner = new Scanner(System.in);
    boolean toggle = true;

    public void goodbye(){
        scanner.close();
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("goodbyes");
        System.out.println("____________________________________________________________");
        toggle = false;
    }

    public void list(){
        int i = 0;
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("listRemarks");
        while (i < taskboard.getTaskCount()) {
            System.out.println(i + 1 + "." + taskboard.getTask(i));
            i += 1;
        }
        System.out.println("____________________________________________________________");
    }

    public void mark(int index1){
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("taskMark");
        System.out.println(index1 + "." + taskboard.getTask(index1));
        System.out.println("____________________________________________________________");
    }

    public void unmark(int index2){
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("taskUnmark");
        System.out.println(index2 + "." + taskboard.getTask(index2));
        System.out.println("____________________________________________________________");
    }

    public void delete(Task temp){
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("deleting");
        System.out.println("    " + temp);
        sunnyVoice.speakListNum("listNumberRemarks", taskboard.getTaskCount() + 1);
        System.out.println("____________________________________________________________");
    }

    public void task(Task temp){
        System.out.println("____________________________________________________________");
        sunnyVoice.speak("taskAddRemarks");
        System.out.println("    " + temp);
        sunnyVoice.speakListNum("listNumberRemarks", taskboard.getTaskCount() + 1);
        sunnyVoice.speak("taskRemarks");
        System.out.println("____________________________________________________________");
    }

    public void run(){
        while (toggle) {
            try{
                if (taskboard.getTaskCount() == 101){
                    throw new tooManyTasksException(sunnyVoice.getException("tooMany"));
                }
                String input = scanner.nextLine();

                userParser.userParse(input, this);
            }
            catch (UnrecognisedTaskException | TaskEmptyDescException | insufficientInfoException | tooManyTasksException | taskOutOfBoundsException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
            }
            catch (NumberFormatException e) { //if something other than an integer was used, or the integer is too large/small
                System.out.println("____________________________________________________________");
                sunnyVoice.speak("notInteger");
                System.out.println("____________________________________________________________");
            } //no catch for out of bounds to see if code was the issue rather than user
            catch (SunnyException e) {
                throw new RuntimeException(e);
            }
        }
    }

}