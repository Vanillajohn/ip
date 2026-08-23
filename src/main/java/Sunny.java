public class Sunny {
        public static void main(String[] args) {
                // more specific error handling that tells user what to do
                // add comments to explain things
                // find based on date
                Storage storer = Storage.getInstance();
                dateParser parser = dateParser.getInstance();
                Taskboard taskboard = Taskboard.getInstance();
                UserParser userParser = UserParser.getInstance();
                UI ui = UI.getInstance();
                SunnyVoice sunnyVoice = SunnyVoice.getInstance();

                taskboard.load();

                System.out.println("____________________________________________________________");
                sunnyVoice.speak("greetings");
                System.out.println("____________________________________________________________");

                ui.run();
    }
}
