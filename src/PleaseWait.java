
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author iddi
 */
public class PleaseWait implements Runnable {

    public Display display;
    private PoliceMobile MIDlet;

    public void run() {
        Form waitForm = new Form("Please Wait");
        waitForm.append("WE ARE WAITING");
     //   System.out.println("We are strating");
     //   Alert pleaseWait = new Alert("Please Wait", "Contacting server", null, AlertType.INFO);
     //   pleaseWait.setTimeout(Alert.FOREVER);
        
        display.setCurrent(waitForm);

    }

    public void start() {
        Thread thread = new Thread(this);
        try {
            thread.start();
            System.out.println("Thread Start...");
        } catch (Exception error) {
        }
    }

    public PleaseWait(Display display) {
        this.display = display;
    }

    public PleaseWait(PoliceMobile MIDlet) {
        this.MIDlet = MIDlet;
        this.display = MIDlet.display;
    }
}
