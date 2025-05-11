import javax.speech.*;
import javax.speech.recognition.*;
import java.util.Locale;

public class VoiceControlledWheelchair extends ResultAdapter {
    static Recognizer recognizer;

    public static void main(String[] args) {
        try {
            // Setup recognizer
            recognizer = Central.createRecognizer(new EngineModeDesc(Locale.ENGLISH));
            recognizer.allocate();

            // Create a rule grammar
            RuleGrammar grammar = recognizer.newRuleGrammar("commands");
            grammar.setRule("moveCommands",
                    RuleGrammar.ruleForJSGF("forward | backward | left | right | stop"),
                    true);

            grammar.setEnabled(true);
            recognizer.addResultListener(new VoiceControlledWheelchair());
            recognizer.commitChanges();
            recognizer.requestFocus();
            recognizer.resume();

            System.out.println("Say a command: forward, backward, left, right, or stop");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // On recognizing a result
    public void resultAccepted(ResultEvent e) {
        FinalResult result = (FinalResult) e.getSource();
        String command = result.getBestFinalResultNoFiller();
        System.out.println("Command: " + command);

        switch (command.toLowerCase()) {
            case "forward":
                System.out.println("Moving forward...");
                break;
            case "backward":
                System.out.println("Moving backward...");
                break;
            case "left":
                System.out.println("Turning left...");
                break;
            case "right":
                System.out.println("Turning right...");
                break;
            case "stop":
                System.out.println("Stopping...");
                break;
            default:
                System.out.println("Invalid command.");
        }
    }
}
