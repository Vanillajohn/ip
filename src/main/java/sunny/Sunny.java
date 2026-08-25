package sunny;

import sunny.taskboard.Taskboard;
import sunny.ui.UI;

import java.util.Scanner;

/**
 * The main class that begins the program
 */
public class Sunny {
        /**
         * Runs the main program
         *
         * @param args command-line arguments passed to the program
         */
        public static void main(String[] args) {
                // more specific error handling that tells user what to do
                // add comments to explain things
                // find based on date
                Taskboard taskboard = Taskboard.getInstance();
                UI ui = UI.getInstance();
                SunnyVoice sunnyVoice = SunnyVoice.getInstance();
                Scanner scanner = new Scanner(System.in);

                taskboard.load();

                System.out.println("____________________________________________________________");
                sunnyVoice.speak("greetings");
                System.out.println("____________________________________________________________");

                ui.run(scanner);
                scanner.close();

    }
}
