
import javax.microedition.lcdui.Command;

/**
 *
 * @author iddi
 */
public class PCommand extends Command {

    public static final int OFFENCE_DETAILS = 97;
    public static final int PAYMENT_DETAILS = 98;
    public static final int CONFIRM_DETAILS = 99;
    public static final int SEARCH_OFFENCE = 100;
    public static final int BACK_TRAFFIC = 101;
    public static final int GET_MAIN_MENU = 102;
    public static final int OFFENCE_DETAILS_AMOUNT = 103;
    private int pCommandType;
    /* Login into Police Mobile application after inserting username and password*/
    public static final int LOGIN = 91;
    public static final int MAIN_MENU_SELECTION = 92;
    public static final int TRAFFIC_MENU_SELECTION = 93;
    public static final int TRAFFIC_MENU_SUBMIT_DETAILS = 94;
    public static final int START_CAMERA = 95;
    public static final int CAPTURE_PICTURE = 96;

    public PCommand(String label, int commandType, int pcommandType, int priority) {
        super(label, commandType, priority);
        pCommandType = pcommandType;
    }

    public PCommand(String shortLabel, String longLabel, int commandType, int pcommandType, int priority) {
        super(shortLabel, longLabel, commandType, priority);
        pCommandType = pcommandType;
    }

    public PCommand(String label, int commandType, int priority) {
        super(label, commandType, priority);
        pCommandType = commandType;
    }

    public PCommand(String shortLabel, String longLabel, int commandType, int priority) {
        super(shortLabel, longLabel, commandType, priority);
        pCommandType = commandType;
    }

    public int getPCommandType() {
        return pCommandType;
    }
}
