
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;
import javax.microedition.midlet.*;
import org.kobjects.base64.Base64;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransport;

/**
 * @author iddi Halfani from Apex Solutions
 */
public class PoliceMobile extends MIDlet implements CommandListener {

    public Display display; 
    private Displayable lastDisplay = null;
    private PCommand exit = new PCommand("Exit", PCommand.CANCEL, 1);
    private PCommand back = new PCommand("Back", Command.BACK, 3);
    private Player player;
    private VideoControl videoControl;
    private Image capturedImage;
    private byte[] capturedImageBytes;
    private String SUGAR_LOCATION = "http://www.apexsolutions.co.tz/demo/";
    private String IMAGE_SERVER = SUGAR_LOCATION + "images";
    private String SOAP_URL = SUGAR_LOCATION + "soap.php/";
    private final String NAMESPACE = "http://www.sugarcrm.com/sugarcrm";
    private HttpTransport httpt = new HttpTransport(SOAP_URL);
    private SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    private String sessionID = "-2";
    private Hashtable ofl = new Hashtable() {
        {
            put("AC20", "Failing to give particulars or to report an accident within 24 hours");
            put("DD10", "Driving in a dangerous manner");
            put("LC50", "Driving after a licence has been revoked or refused on medical ground");
            put("MS50", "Motor racing on the highway");
            put("MW10", "Contravention of Special Roads Regulations (excluding speed limits)");
            put("AC10", "Failing to stop after an accident");
            put("AC30", "Undefined accident offence");
            put("BA10", "Driving while disqualified by order of Court");
            put("BA20", "Driving while disqualified as under age");
            put("BA30", "Attempting to drive while disqualified by order of Court");
            put("CD10", "Driving without due care and attention");
            put("CD20", "Driving without reasonable consideration for other road users");
            put("CD30", "Driving without due care and attention/reasonable consideration");
            put("CD40", "Causing death through careless driving when unfit through drink Up to 10");
            put("CD50", "Causing death by careless driving when unfit through drugs Up to 10");
            put("CD60", "Causing death by careless driving when alcohol level above limit Up to 10");
            put("CD70", "Causing death by careless driving then failing to supply a specimen Up to 10");
            put("CU10", "Using a vehicle with defective brakes");
            put("CU20", "Using a vehicle with defective parts or accessories");
            put("CU30", "Using a vehicle with defective tyres");
            put("CU40", "Using a vehicle with defective steering");
            put("CU50", "Causing or likely to cause danger by reason of load or passengers");
            put("CU60", "Undefined failure to comply with Construction and Use Regulations");
            put("CU80", "Using a mobile phone while driving a motor vehicle");
            put("DD20", "Driving at a dangerous speed");
            put("DD30", "Reckless Driving or Dangerous Driving");
            put("DD40", "Driving in a dangerous manner at a dangerous speed or recklessly");
            put("DD50", "Causing death by dangerous driving");
            put("DD60", "Manslaughter or culpable homicide while driving a vehicle");
            put("DD70", "Causing death by reckless driving");
            put("DD80", "Causing death by dangerous driving");
            put("DR10", "Driving or attempting to drive with alcohol concentration above limit");
            put("DR20", "Driving or attempting to drive when unfit through drink or drugs");
            put("DR30", "Driving or attempting to drive then refusing to provide a specimen");
            put("DR40", "In charge of a vehicle with alcohol concentration above limit");
            put("DR50", "In charge of a vehicle while unfit through drink or drugs");
            put("DR60", "Failure to provide a specimen other than driving/ attempting to drive");
            put("DR70", "Failing to provide specimen for breath test");
            put("DR80", "Driving or attempting to drive when unfit through drugs");
            put("DR90", "In charge of a vehicle when unfit through drugs");
            put("IN10", "Using a vehicle uninsured against third party risks");
            put("LC10", "Driving without a licence");
            put("LC20", "Driving otherwise than in accordance with a licence");
            put("LC30", "Driving after making a false declaration about fitness (Licence Application)");
            put("LC40", "Driving a vehicle having failed to notify a disability");
            put("MS10", "Leaving a vehicle in a dangerous position");
            put("MS20", "Unlawful pillion riding");
            put("MS30", "Playstreet Offence");
            put("MS40", "Driving with uncorrected defective eyesight or refusing eyesight test");
            put("MS60", "Offences not covered by other codes");
            put("MS70", "Driving with uncorrected defective eyesight");
            put("MS80", "Refusing to submit to an eyesight test");
            put("MS90", "Failing to give information as to identity of driver etc.");
            put("PC10", "Undefined contravention of Pedestrian crossing Regulations");
            put("PC20", "Contravention of Pedestrian crossing Regulations with moving vehicle");
            put("PC30", "Contravention of Pedestrian crossing Regulations Stationary vehicle");
            put("PL10", "Provisional driver driving without 'L' Plates");
            put("PL20", "Provisional driver not accompanied by a qualified person");
            put("PL30", "Carrying a person not qualified");
            put("PL40", "Drawing an unauthorised trailer");
            put("PL50", "Undefined failure to comply with the conditions a Provisional Licence");
            put("SC19", "Section 19 (Transport Act 1981) Disqualification");
            put("SC35", "Section 35 (Transport Act 1981) Disqualification");
            put("SP10", "Exceeding goods vehicle speed limit");
            put("SP20", "Exceeding speed limit for type of vehicle");
            put("SP30", "Exceeding statutory speed limit on a public road");
            put("SP40", "Exceeding passenger vehicle speed limit");
            put("SP50", "Exceeding speed limit on a motorway");
            put("SP60", "Undefined speed limit offence");
            put("TS10", "Failing to comply with traffic light signals");
            put("TS20", "Failing to comply with double white lines");
            put("TS30", "Failing to comply with a 'Stop' sign");
            put("TS40", "Failing to comply with directions of a constable or traffic warden");
            put("TS50", "Failing to comply with a traffic sign (Excluding the above signs)");
            put("TS60", "Failing to comply with a school crossing patrol sign");
            put("TS70", "Undefined failure to comply with a traffic direction or sign");
            put("TT99", "Disqualified for having more than 12 points in the “totting up” process");
            put("UT10", "Taking and driving away a vehicle without consent or attempt there at");
            put("UT20", "Stealing or attempting to steal a vehicle");
            put("UT30", "Going equipped for stealing or taking a motor vehicle");
            put("UT40", "Taking or attempting to take a vehicle without consent");
            put("UT50", "Aggravated Taking of a vehicle");
            put("XX99", "Disqualified under 'totting-up' procedure");
            put("**99", "Non-endorsable offence for which you can be disqualified (Criminal)");
        }
    };

    public PoliceMobile() {
    }

    public void startApp() {
        display = Display.getDisplay(this);
        display.setCurrent(getLoginForm());
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }

    public void capturePicture() {
        if (checkCameraSupport() == false) {
            //  showAlert("Error", "Camera is not supported!", null);
            return;
        }

        if (checkPngEncodingSupport() == false) {
            //  showAlert("Error", "Png encoding is not supported!", null);
            return;
        }
    }

    private boolean checkPngEncodingSupport() {
        String encodings = System.getProperty("video.snapshot.encodings");
        return (encodings != null) && (encodings.indexOf("jpeg") != -1);
    }

    private boolean checkCameraSupport() {
        String propValue = System.getProperty("supports.video.capture");
        return (propValue != null) && propValue.equals("true");
    }

    private void DoCaptureImage() {
        try {
            String type = System.getProperty("video.snapshot.encodings");
            byte[] imageData = videoControl.getSnapshot("encoding=jpeg");
            capturedImageBytes = imageData;
            capturedImage = Image.createImage(imageData, 0, imageData.length);
            PCommand cmdCapture = new PCommand("Retake photo", Command.OK, PCommand.CAPTURE_PICTURE, 0);
            Form imageFrm = new Form("Captured Image");
            imageFrm.append(capturedImage);
            //  imageFrm.addCommand(exit);
            imageFrm.addCommand(back);
            imageFrm.addCommand(cmdCapture);

            // lastDisplay = display.getCurrent();
            imageFrm.setCommandListener(this);
            display.setCurrent(imageFrm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void startCamera() throws IOException, MediaException {
        if (player.getState() == Player.PREFETCHED) {
            player.start();
        }
    }

    public void showCamera() {
        if (checkCameraSupport() == false) {
            // showAlert("Error", "Camera is not supported!", null);
            return;
        }

        if (checkPngEncodingSupport() == false) {
            // showAlert("Error", "Png encoding is not supported!", null);
            return;
        }

        Form cameraForm = new Form("Camera");
        try {
            createCamera();
            cameraForm.append((Item) videoControl.initDisplayMode(VideoControl.USE_GUI_PRIMITIVE, null));
            //   addCameraToForm();
            startCamera();
        } catch (Exception e) {
        }

        PCommand cmdCapture = new PCommand("Capture", Command.OK, PCommand.CAPTURE_PICTURE, 0);
        //    PCommand cmdExit = new PCommand("Exit", Command.EXIT, 0);
        PCommand cmdAnotherPhoto = new PCommand("Another Photo", Command.HELP, 0);
        cameraForm.addCommand(cmdCapture);
        cameraForm.addCommand(back);
        //  cameraForm.addCommand(cmdAnotherPhoto);
        cameraForm.setCommandListener(this);
        lastDisplay = display.getCurrent();
        display.setCurrent(cameraForm);
    }

    private void createCamera() throws IOException, MediaException {
        player = Manager.createPlayer("capture://video");
        player.realize();
        player.prefetch();
        videoControl = (VideoControl) player.getControl("VideoControl");
    }
    private TextField userName = null;
    private TextField password = null;

    public void getPleaseWait(Displayable curr) {
        Form pleaseWait = new Form("Please Wait");
        pleaseWait.append("Please Wait - contacting server...");
        lastDisplay = curr;
        pleaseWait.setCommandListener(this);
        display.setCurrent(pleaseWait);
    }
    private PCommand login;

    public Form getLoginForm() {
        Form loginForm = new Form("Welcome to Police Mobile");
        userName = new TextField("Police No", "", 30, TextField.ANY);
        password = new TextField("Password", "", 30, TextField.PASSWORD);

        login = new PCommand("Login", PCommand.SCREEN, PCommand.LOGIN, 2);
        try {
            Image imge = Image.createImage("/Police.png");
            loginForm.append(imge);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        loginForm.append(userName);
        loginForm.append(password);
        loginForm.addCommand(exit);
        loginForm.addCommand(login);
        loginForm.setCommandListener(this);
        lastDisplay = display.getCurrent();
        return loginForm;
    }
    private List servicesList = null;

    public List getMainMenuForm() {
        servicesList = new List("Please select service", List.EXCLUSIVE);
        servicesList.append("Traffic Services", null);
        servicesList.append("Police Station Services", null);
        servicesList.append("Other Services", null);
        servicesList.append("Help", null);

        PCommand next = new PCommand("Next", PCommand.OK, PCommand.MAIN_MENU_SELECTION, 2);

        servicesList.addCommand(next);
        servicesList.addCommand(exit);

        servicesList.setCommandListener(this);
        lastDisplay = display.getCurrent();
        display.setCurrent(servicesList);
        return servicesList;
    }
    private List traffic_services_list;

    public void getTrafficForm() {
        traffic_services_list = new List("Please select service", List.EXCLUSIVE);
        traffic_services_list.append("Register offence", null);
        traffic_services_list.append("Search previous offences", null);

        PCommand next = new PCommand("Next", PCommand.SCREEN, PCommand.TRAFFIC_MENU_SELECTION, 2);

        traffic_services_list.addCommand(back);
        traffic_services_list.addCommand(next);

        traffic_services_list.setCommandListener(this);
        lastDisplay = display.getCurrent();
        display.setCurrent(traffic_services_list);
    }
    private TextField firstName = null;
    private TextField lastName = null;
    private TextField mobileNo = null;
    private TextField address = null;
    private TextField city = null;
    private TextField driverNo = null;
    private TextField plateNo = null;

    public void getOffenderDetails() {
        Form registerForm = new Form("Driver details");
        firstName = new TextField("First name:", "", 30, TextField.ANY);
        lastName = new TextField("Last name:", "", 30, TextField.ANY);
        mobileNo = new TextField("Mobile no:", "", 30, TextField.NUMERIC);
        address = new TextField("Residential address #:", "", 30, TextField.ANY);
        city = new TextField("Region / City:", "", 30, TextField.ANY);
        driverNo = new TextField("Driver Licence #:", "", 30, TextField.ANY);
        plateNo = new TextField("Car registration #:", "", 30, TextField.ANY);
        /* TextField offenceCode = new TextField("Offence code:", "", 30, TextField.ANY);
         TextField offence = new TextField("Offence details:", "", 30, TextField.ANY);
         TextField fineAmount = new TextField("Fine amount:", "", 30, TextField.ANY);*/

        PCommand next = new PCommand("Next", PCommand.SCREEN, PCommand.OFFENCE_DETAILS, 2);
        PCommand takePicture = new PCommand("Capture Image", PCommand.SCREEN, PCommand.START_CAMERA, 2);

        registerForm.append(firstName);
        registerForm.append(lastName);
        registerForm.append(mobileNo);
        registerForm.append(address);
        registerForm.append(city);
        registerForm.append(driverNo);
        registerForm.append(plateNo);

        registerForm.addCommand(takePicture);
        registerForm.addCommand(back);
        registerForm.addCommand(next);

        registerForm.setCommandListener(this);
        lastDisplay = display.getCurrent();
        display.setCurrent(registerForm);
    }
    private TextField offenceCode = null;
    private TextField offence = null;
    private TextField fineAmount = null;
    private List offencelist;

    public void getOffenceList() {
        offencelist = new List("Offence List", Choice.IMPLICIT);

        Enumeration keys = ofl.keys();

        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();

            offencelist.append(key + "|" + (String) ofl.get(key), null);
        }

        PCommand next = new PCommand("Next", PCommand.SCREEN, PCommand.OFFENCE_DETAILS_AMOUNT, 2);

        offencelist.addCommand(back);
        offencelist.addCommand(next);

        offencelist.setCommandListener(this);
        lastDisplay = display.getCurrent();
        display.setCurrent(offencelist);
    }

    public void getOffenceDetails() {

        Form offenceForm = new Form("Offence details");
        offenceCode = new TextField("Offence code:", "", 30, TextField.ANY);
        offence = new TextField("Offence details:", "", 30, TextField.ANY);
        fineAmount = new TextField("Fine amount:", "", 30, TextField.NUMERIC);

        offenceForm.append(fineAmount);
        PCommand next = new PCommand("Next", PCommand.SCREEN, PCommand.PAYMENT_DETAILS, 2);

        offenceForm.addCommand(back);
        offenceForm.addCommand(next);

        offenceForm.setCommandListener(this);
        lastDisplay = display.getCurrent();
        display.setCurrent(offenceForm);
    }
    private List paymentList = null;

    public void GetNoSearch() {
        Form nosearch = new Form("No Details found");
    }

    public void getPaymentOptions() {
        paymentList = new List("Payment Options", List.EXCLUSIVE);
        paymentList.append("Cash", null);
        paymentList.append("Other - MPesa, Airtel Money, Tigo Pesa", null);

        PCommand next = new PCommand("Next", PCommand.SCREEN, PCommand.CONFIRM_DETAILS, 2);

        paymentList.addCommand(back);
        paymentList.addCommand(next);
        paymentList.setCommandListener(this);
        lastDisplay = display.getCurrent();
        display.setCurrent(paymentList);
    }
    private PCommand submit;
    private String offenceCodeTxt = null;
    private String offenceDescription = null;

    public Form getConfirmationPage() {
        submit = new PCommand("Submit", PCommand.SCREEN, PCommand.TRAFFIC_MENU_SUBMIT_DETAILS, 2);
        Form confirmationPage = new Form("Confirm details");
        if (capturedImage != null) {
            confirmationPage.append(createThumbnail(capturedImage));
        }
        confirmationPage.append(new StringItem("First name: ", firstName.getString()));
        confirmationPage.append(new StringItem("Last name: ", lastName.getString()));
        confirmationPage.append(new StringItem("Mobile no: ", mobileNo.getString()));
        confirmationPage.append(new StringItem("Residential Address: ", address.getString()));
        confirmationPage.append(new StringItem("Region / City: ", city.getString()));
        confirmationPage.append(new StringItem("Driver licence #: ", driverNo.getString()));
        confirmationPage.append(new StringItem("Car registration no: ", plateNo.getString()));
        confirmationPage.append(new StringItem("", ""));
        String sel = offencelist.getString(offencelist.getSelectedIndex());

        int separator = sel.indexOf("|");
        offenceCodeTxt = sel.substring(0, separator);
        offenceDescription = sel.substring(separator + 1);



        confirmationPage.append(new StringItem("Offence code: ", offenceCodeTxt));
        confirmationPage.append(new StringItem("Offence details: ", offenceDescription));
        confirmationPage.append(new StringItem("Fine amount: ", fineAmount.getString()));
        confirmationPage.append(new StringItem("", ""));


        confirmationPage.append(new StringItem("Payment type ", paymentList.getString(paymentList.getSelectedIndex())));
        confirmationPage.append(new StringItem("", ""));
        confirmationPage.append(new StringItem("", ""));


        confirmationPage.addCommand(submit);
        confirmationPage.addCommand(back);
        confirmationPage.setCommandListener(this);
        lastDisplay = display.getCurrent();
        display.setCurrent(confirmationPage);
        return confirmationPage;

    }

    public void DoShow(Form dsp) {
        dsp.setCommandListener(this);
        display.setCurrent(dsp);
    }

    public void _DoHistory(String driverNo) {
        lastDisplay = display.getCurrent();
        //display.setCurrent(pleaseWait());
        SearchOffences process = new SearchOffences(this, driverNo);
    }

    public void getPreviousOffences(String driverNo) {
        try {
            String date_entered = "";
            String reference_c = "";
            String points_c = "";
            String opp_id = "";
            String name = "";
            String METHOD_NAME = "get_entry_list";
            String SOAP_ACTION = SOAP_URL + METHOD_NAME;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("session", sessionID);
            request.addProperty("module_name", "Accounts");
            request.addProperty("query", "driverno_c = '" + driverNo + "'");

            Vector oppOptions = new Vector();
            oppOptions.addElement("name");
            oppOptions.addElement("date_entered");
            oppOptions.addElement("id");
            oppOptions.addElement("points_c");

            request.addProperty("select_fields", oppOptions);
            envelope.setOutputSoapObject(request);
            httpt.call(SOAP_ACTION, envelope);
            SoapObject body = (SoapObject) envelope.getResponse();
            //System.out.println(body);

            Vector field_list = (Vector) body.getProperty("entry_list");
            String id = "";
            for (int i = 0; i < field_list.size(); i++) {
                SoapObject entry_list = (SoapObject) field_list.elementAt(i);
                //System.out.println(entry_list);
                // SoapObject entry_value = (SoapObject) entry_list.getProperty("entry_value");
                // System.out.println(entry_value);
                id = (String) entry_list.getProperty("id");

                //System.out.println(id);
                String module_name = (String) entry_list.getProperty("module_name");
                //System.out.println(module_name);
                Vector name_value_list = (Vector) entry_list.getProperty("name_value_list");
                // System.out.println(name_value_list);
                // SoapObject reference = name_value_list.elements().

                for (int j = 0; j < name_value_list.size(); j++) {
                    SoapObject name_value = (SoapObject) name_value_list.elementAt(j);
                    //System.out.println(name_value);

                    String prop = (String) name_value.getProperty("name");

                    if (prop.equalsIgnoreCase("date_entered")) {
                        date_entered = (String) name_value.getProperty("value");
                    } else if (prop.equalsIgnoreCase("name")) {
                        name = (String) name_value.getProperty("value");
                    } else if (prop.equalsIgnoreCase("points_c")) {
                        points_c = (String) name_value.getProperty("value");
                    } /* else if (prop.equalsIgnoreCase("id")) {
                     opp_id = (String) name_value.getProperty("value");
                     }*/

                }
            }

            Form previousOffencePage = new Form("Previous Offences");
            previousOffencePage.append("Name: " + name + '\r' + '\n');
            previousOffencePage.append("License no: " + driverNo + '\r' + '\n');
            previousOffencePage.append("Points: " + points_c + '\r' + '\n');
            previousOffencePage.append(" " + '\r' + '\n');
            previousOffencePage.append("Previous Offences:" + '\r' + '\n');

            METHOD_NAME = "get_relationships";
            SOAP_ACTION = SOAP_URL + METHOD_NAME;
            request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("session", sessionID);
            request.addProperty("module_name", "Accounts");
            request.addProperty("module_id", id);
            request.addProperty("related_module", "Opportunities");
            request.addProperty("related_module_query", "");
            request.addProperty("deleted", "0");

            //    request.addProperty("related_module_query", "driverno_c = '" + driverNo + "'");
            httpt.reset();
            envelope.setOutputSoapObject(request);

            httpt.call(SOAP_ACTION, envelope);
            body = (SoapObject) envelope.getResponse();
            //System.out.println(body);

            field_list = (Vector) body.getProperty("ids");

            String id_list = "";

            for (int i = 0; i < field_list.size(); i++) {
                SoapObject entry_list = (SoapObject) field_list.elementAt(i);
                //System.out.println(entry_list);
                // SoapObject entry_value = (SoapObject) entry_list.getProperty("entry_value");
                // System.out.println(entry_value);
                //System.out.println(entry_list);
                id = (String) entry_list.getProperty("id");
                id_list += "'" + id + "'";
                if (i < field_list.size() - 2) {
                    id_list += ",";
                }
            }

            METHOD_NAME = "get_entry_list";
            SOAP_ACTION = SOAP_URL + METHOD_NAME;
            request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("session", sessionID);
            request.addProperty("module_name", "Opportunities");
            request.addProperty("query", " opportunities.id IN  (" + id_list + ")");

            oppOptions = new Vector();
            oppOptions.addElement("name");
            oppOptions.addElement("date_entered");
            oppOptions.addElement("id");
            oppOptions.addElement("description");
            oppOptions.addElement("points_c");
            oppOptions.addElement("sales_stage");

            request.addProperty("select_fields", oppOptions);
            envelope.setOutputSoapObject(request);
            httpt.call(SOAP_ACTION, envelope);
            body = (SoapObject) envelope.getResponse();
            //System.out.println(body);

            field_list = (Vector) body.getProperty("entry_list");
            //String id = "";
            for (int i = 0; i < field_list.size(); i++) {
                SoapObject entry_list = (SoapObject) field_list.elementAt(i);
                Vector name_value_list = (Vector) entry_list.getProperty("name_value_list");
                previousOffencePage.append("***************" + '\r' + '\n');
                for (int j = 0; j < name_value_list.size(); j++) {



                    SoapObject name_value = (SoapObject) name_value_list.elementAt(j);
                    //System.out.println(name_value);

                    String prop = (String) name_value.getProperty("name");
                    if (prop.equalsIgnoreCase("referencetxt_c")) {
                        previousOffencePage.append("Reference: " + name_value.getProperty("value") + '\r' + '\n');
                    } else if (prop.equalsIgnoreCase("sales_stage")) {
                        previousOffencePage.append("Status: " + name_value.getProperty("value") + '\r' + '\n');
                    } else if (prop.equalsIgnoreCase("amount")) {
                        previousOffencePage.append("Amount: " + name_value.getProperty("value") + '\r' + '\n');
                    } else if (prop.equalsIgnoreCase("date_entered")) {
                        previousOffencePage.append("Offence Date: " + name_value.getProperty("value") + '\r' + '\n');
                    } else if (prop.equalsIgnoreCase("description")) {
                        previousOffencePage.append("Description: " + name_value.getProperty("value") + '\r' + '\n');
                    }
                }
            }


            previousOffencePage.addCommand(back);
            previousOffencePage.setCommandListener(this);
            lastDisplay = display.getCurrent();
            display.setCurrent(previousOffencePage);
        } catch (Exception ex) {
            ex.printStackTrace();
            display.setCurrent(lastDisplay);
        }

    }

    public boolean isError(SoapObject body) {
        boolean flag = false;
        try {
            SoapObject error = (SoapObject) body.getProperty("error");
            System.out.print(error);
            String error_value = (String) error.getProperty("description");
            String error_number = (String) error.getProperty("number");
            String error_name = (String) error.getProperty("name");
            int errNo = Integer.parseInt(error_number);
            if ((errNo) > 0) {
                flag = true;
                showAlert(error_name, error_value, AlertType.ERROR);
                /*Alert thisAlert =
                 new Alert(error_name, error_value, null, AlertType.ERROR);
                 thisAlert.setTimeout(Alert.FOREVER);
                 display.setCurrent(thisAlert);*/
            }

        } catch (Exception e) {
        }
        return flag;
    }

    public void showAlert(String error_name, String error_value, AlertType type) {
        Alert thisAlert = new Alert(error_name, error_value, null, type);
        thisAlert.setTimeout(Alert.FOREVER);
        display.setCurrent(thisAlert);
    }

    public void getReferencePage(String opportunityID) {
        try {
            String date_entered = "";
            String reference_c = "";
            String METHOD_NAME = "get_entry";
            String SOAP_ACTION = SOAP_URL + METHOD_NAME;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("session", sessionID);
            request.addProperty("module_name", "Opportunities");
            request.addProperty("id", opportunityID);

            Vector oppOptions = new Vector();
            oppOptions.addElement("referencetxt_c");
            oppOptions.addElement("date_entered");
            oppOptions.addElement("id");

            request.addProperty("select_fields", oppOptions);
            envelope.setOutputSoapObject(request);
            httpt.call(SOAP_ACTION, envelope);
            SoapObject body = (SoapObject) envelope.getResponse();
            System.out.println(body);

            if (isError(body)) {
                return;
            }

            Vector field_list = (Vector) body.getProperty("entry_list");
            for (int i = 0; i < field_list.size(); i++) {
                SoapObject entry_list = (SoapObject) field_list.elementAt(i);
                System.out.println(entry_list);
                // SoapObject entry_value = (SoapObject) entry_list.getProperty("entry_value");
                // System.out.println(entry_value);
                String id = (String) entry_list.getProperty("id");
                System.out.println(id);
                String module_name = (String) entry_list.getProperty("module_name");
                System.out.println(module_name);
                Vector name_value_list = (Vector) entry_list.getProperty("name_value_list");
                System.out.println(name_value_list);
                // SoapObject reference = name_value_list.elements().

                for (int j = 0; j < name_value_list.size(); j++) {
                    SoapObject name_value = (SoapObject) name_value_list.elementAt(j);
                    System.out.println(name_value);

                    String prop = (String) name_value.getProperty("name");

                    if (prop.equalsIgnoreCase("date_entered")) {
                        date_entered = (String) name_value.getProperty("value");

                    } else if (prop.equalsIgnoreCase("referencetxt_c")) {
                        reference_c = (String) name_value.getProperty("value");
                    }

                }
            }

            Form referencePage = new Form("Details submitted successfully");
            referencePage.append("Details have been submitted successfully.");
            referencePage.append(" Payments should be made against the reference number ");
            referencePage.append(reference_c + " and submitted within the next 7 days. ");

            PCommand bckMain = new PCommand("Back to Main", "Back to Main",
                    PCommand.SCREEN, PCommand.GET_MAIN_MENU, 0);

            referencePage.addCommand(bckMain);
            referencePage.addCommand(exit);
            referencePage.setCommandListener(this);
            display.setCurrent(referencePage);
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert thisAlert = new Alert("Police Mobile", ex.getMessage(), null, AlertType.ERROR);
            thisAlert.setTimeout(Alert.FOREVER);
            display.setCurrent(thisAlert, getMainMenuForm());
        }
    }

    private void Threads() {
        // Process process = new Process(this);
    }

    private void __DoSubmit() {
        //  display.setCurrent(pleaseWait());
        SubmitData process = new SubmitData(this);

    }

    private void _DoSubmit() {
        try {
            //   Alert wait = new Alert("Sending details", "Sending details please wait...", null, AlertType.INFO);
            //  success.setImage(img2);
            String filename = "driver.jpg";
            //   display.setCurrent(wait, disp);
            String METHOD_NAME = "set_entry";
            String SOAP_ACTION = SOAP_URL + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("session", sessionID);
            request.addProperty("module_name", "Opportunities");

            SoapObject name_item = new SoapObject(NAMESPACE, "name_value");
            name_item.addProperty("name", "name");
            name_item.addProperty("value", offenceCode.getString());

            SoapObject amount = new SoapObject(NAMESPACE, "name_value");
            amount.addProperty("name", "amount");
            amount.addProperty("value", fineAmount.getString());

            //SoapObject account_id_item = new SoapObject(NAMESPACE, "name_value");
            //account_id_item.addProperty("name", "account_id");
            //    account_id_item.addProperty("value", "34e739ee-7a60-2c1c-2546-50c6598588e1");
            //account_id_item.addProperty("value", "154d155e-d8ee-5a24-daff-50d82fbc7a6c");

            SoapObject dl_item = new SoapObject(NAMESPACE, "name_value");
            dl_item.addProperty("name", "driverno_c");
            dl_item.addProperty("value", driverNo.getString());

            SoapObject driver_name_item = new SoapObject(NAMESPACE, "name_value");
            driver_name_item.addProperty("name", "drivername_c");
            driver_name_item.addProperty("value", firstName.getString() + " " + lastName.getString());

            /*
             SoapObject dl_item = new SoapObject(NAMESPACE, "name_value");
             dl_item.addProperty("name", "driverno_c");
             dl_item.addProperty("value", driverNo.getString());
             */

            SoapObject description = new SoapObject(NAMESPACE, "name_value");
            description.addProperty("name", "description");
            description.addProperty("value", offence.getString());

            /*
             String sales_stage_txt = "FineUnpaid";
             if (paymentList.getSelectedIndex() == 0) {
             sales_stage_txt = "FinePaid";
             }
           
             SoapObject sales_stage = new SoapObject(NAMESPACE, "name_value");
             sales_stage.addProperty("name", "sales_stage");
             sales_stage.addProperty("value", sales_stage_txt);
             */
            SoapObject mobile_no = new SoapObject(NAMESPACE, "name_value");
            mobile_no.addProperty("name", "mobile_c");
            mobile_no.addProperty("value", mobileNo.getString());

            SoapObject offence_code = new SoapObject(NAMESPACE, "name_value");
            offence_code.addProperty("name", "offence_code_c");
            offence_code.addProperty("value", offenceCode.getString());

            SoapObject payment_option = new SoapObject(NAMESPACE, "name_value");
            payment_option.addProperty("name", "payment_option_c");
            payment_option.addProperty("value", paymentList.getString(paymentList.getSelectedIndex()));

            SoapObject name_value_list = new SoapObject(NAMESPACE, "name_value_list");
            name_value_list.addProperty("name", name_item);
            name_value_list.addProperty("driverno_c", dl_item);
            name_value_list.addProperty("drivername_c", driver_name_item);


            name_value_list.addProperty("amount", amount);
            name_value_list.addProperty("description", description);
            //  name_value_list.addProperty("sales_stage", sales_stage);
            name_value_list.addProperty("offence_code_c", offence_code);
            name_value_list.addProperty("mobile_c", mobile_no);

            name_value_list.addProperty("payment_option_c", payment_option);

            request.addProperty("name_value_list", name_value_list);

            envelope.setOutputSoapObject(request);
            httpt.call(SOAP_ACTION, envelope);
            SoapObject body = (SoapObject) envelope.getResponse();

            String opportunityID = (String) body.getProperty("id");

            System.out.println(body);
            System.out.println(envelope.getResponse());

            String file_base64 = getBase64File();

            if (file_base64 != null) {
                // DoUpload(capturedImage, filename);

                SoapObject noteRequest = new SoapObject(NAMESPACE, METHOD_NAME);
                noteRequest.addProperty("session", sessionID);
                noteRequest.addProperty("module_name", "Notes");

                SoapObject note_name = new SoapObject(NAMESPACE, "name_value");
                note_name.addProperty("name", "name");
                note_name.addProperty("value", "Captured Image1");

                SoapObject note_description = new SoapObject(NAMESPACE, "name_value");
                note_description.addProperty("name", "description");
                note_description.addProperty("value", "Captured Image1");

                SoapObject note_parentType = new SoapObject(NAMESPACE, "name_value");
                note_parentType.addProperty("name", "parent_type");
                note_parentType.addProperty("value", "Opportunities");

                SoapObject note_parentID = new SoapObject(NAMESPACE, "name_value");
                note_parentID.addProperty("name", "parent_id");
                note_parentID.addProperty("value", opportunityID);

                SoapObject notes_name_value_list = new SoapObject(NAMESPACE, "name_value_list");
                notes_name_value_list.addProperty("name", note_name);
                notes_name_value_list.addProperty("description", note_description);
                notes_name_value_list.addProperty("parent_type", note_parentType);
                notes_name_value_list.addProperty("parent_id", note_parentID);

                noteRequest.addProperty("name_value_list", notes_name_value_list);

                envelope.setOutputSoapObject(noteRequest);
                httpt.call(SOAP_ACTION, envelope);
                body = (SoapObject) envelope.getResponse();

                String noteID = (String) body.getProperty("id");

                System.out.println(body);

                SoapObject set_noteRequest = new SoapObject(NAMESPACE, "set_note_attachment");
                set_noteRequest.addProperty("session", sessionID);

                SoapObject set_notes_name_value_list = new SoapObject(NAMESPACE, "note_attachment");

                set_notes_name_value_list.addProperty("id", noteID);
                set_notes_name_value_list.addProperty("filename", noteID + ".jpg");
                set_notes_name_value_list.addProperty("file", file_base64);

                set_noteRequest.addProperty("note", set_notes_name_value_list);

                envelope.setOutputSoapObject(set_noteRequest);
                SOAP_ACTION = SOAP_URL + "set_note_attachment";
                httpt.call(SOAP_ACTION, envelope);
                body = (SoapObject) envelope.getResponse();

                String set_noteID = (String) body.getProperty("id");

                System.out.println(body);
            }
            getReferencePage(opportunityID);
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getClass() + " | " + ex.getMessage());
            display.setCurrent(lastDisplay);
        }


    }

    private void DoLogin(String username, String password) {


        //  Form form = (Form) Display.getDisplay(this).getCurrent();

        //  Ticker ticker = new Ticker("Please Wait...");
        //  form.setTicker(ticker);
        //    PleaseWait wait = new PleaseWait(this);
        //    wait.start();

        String responseBack = "Please try again";

        //  display.setCurrent(form);
        try {
            String SOAP_ACTION = SOAP_URL + "login";
            String METHOD_NAME = "login";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            // Creating input parameters

            SoapObject userAuth = new SoapObject(NAMESPACE, "user_auth");
            userAuth.addProperty("user_name", username);

            //  password = "B1nadamu";
            MD5 md5 = new MD5();
            md5.Update(password);
            System.out.println(password);
            System.out.println(md5.asHex());

            userAuth.addProperty("password", md5.asHex());
            userAuth.addProperty("version", "1.0");

            request.addProperty("user_auth", userAuth);
            envelope.setOutputSoapObject(request);
            httpt.call(SOAP_ACTION, envelope);
            SoapObject body = (SoapObject) envelope.getResponse();
            responseBack = body.toString();
            for (int i = 0; i < body.getPropertyCount(); i++) {
                System.out.println(body.getProperty(i));
            }
            sessionID = (String) body.getProperty("id");
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getClass() + " | " + ex.getMessage());
        }
        Alert thisAlert =
                new Alert("Police Mobile", responseBack, null, AlertType.INFO);
        thisAlert.setTimeout(Alert.FOREVER);
        display.setCurrent(thisAlert, display.getCurrent());

    }

    private Displayable pleaseWait() {
        Gauge gaProgress = new Gauge(null, false, Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING);
        gaProgress.setLayout(Gauge.LAYOUT_DEFAULT);


        Alert pleaseWait = new Alert("Please Wait", "Please Wait...", null, AlertType.INFO);
        pleaseWait.setIndicator(gaProgress);
        pleaseWait.setTimeout(Alert.FOREVER);
        pleaseWait.addCommand(new Command("\u200B", Command.OK, 1));
        pleaseWait.setCommandListener(new CommandListener() {
            public void commandAction(Command cmd, Displayable disp) {
            }
        });


        // display.setCurrent(pleaseWait);


        /* Form pleaseWait = new Form("Please Wait");
         pleaseWait.append(gaProgress);*/
        //currentForm.setCommandListener(this);
        //   display.setCurrent(pleaseWait);
        return pleaseWait;
    }

    public void checkEmpty() {
    }

    public void commandAction(Command c, Displayable d) {

        PCommand p = (PCommand) c;
        int commandType = p.getPCommandType();
        switch (commandType) {
            case PCommand.CANCEL: {
                destroyApp(true);
                notifyDestroyed();
                break;
            }
            case PCommand.BACK: {
                display.setCurrent(lastDisplay);
                break;
            }
            case PCommand.TRAFFIC_MENU_SUBMIT_DETAILS: {
                display.setCurrent(pleaseWait());
                //   pleaseWait.removeCommand(submit);
                //   pleaseWait.removeCommand(back);
                new SubmitData(this);
//                DoSubmit();
                //   pleaseWait.addCommand(submit);
                //   pleaseWait.addCommand(back);

                break;
            }

            case PCommand.START_CAMERA: {
                showCamera();
                break;
            }
            case PCommand.CAPTURE_PICTURE: {
                DoCaptureImage();
                break;
            }
            case PCommand.OFFENCE_DETAILS_AMOUNT: {
                getOffenceDetails();
                // getOffenceList();
                break;
            }

            case PCommand.OFFENCE_DETAILS: {
                //   getOffenceDetails();
                getOffenceList();
                break;
            }
            case PCommand.PAYMENT_DETAILS: {
                getPaymentOptions();
                break;
            }
            case PCommand.CONFIRM_DETAILS: {
                getConfirmationPage();
                break;
            }
            case PCommand.LOGIN: {
                display.setCurrent(pleaseWait());
                new Authenticate(this, userName.getString(), password.getString());

                //   pleaseWait.removeCommand(login);
                //DoValidateLogin(userName.getString(), password.getString());
                //   pleaseWait.addCommand(login);


                break;
            }
            case PCommand.GET_MAIN_MENU: {
                getMainMenuForm();
                break;
            }

            case PCommand.BACK_TRAFFIC: {
                getTrafficForm();
                break;
            }

            case PCommand.SEARCH_OFFENCE: {
                display.setCurrent(pleaseWait());
                new SearchOffences(this, searchField.getString());
                //DoHistory(searchField.getString());
                break;
            }

            case PCommand.TRAFFIC_MENU_SELECTION: {
                List list = (List) d;
                int selectedIndex = list.getSelectedIndex();
                switch (selectedIndex) {
                    case 0: { // Register Offence
                        getOffenderDetails();
                        break;
                    }
                    case 1: { // Search Offence
                        // DoHistory("4000145391");
                        DoSearch();
                        break;
                    }
                }
                break;
            }

            case PCommand.MAIN_MENU_SELECTION: {
                List list = (List) d;
                int selectedIndex = list.getSelectedIndex();
                switch (selectedIndex) {
                    case 0: { // Traffic Offence
                        getTrafficForm();
                        break;
                    }
                    case 1: { // Traffic Offence
                        //   getPolicePostForm();
                        break;
                    }
                    case 2: { // Traffic Offence
                        //     getOtherServicesForm();
                        break;
                    }
                }
                break;
            }
        }

        //  display.setCurrent(d);
        /* 
         if (d instanceof Form) {
         // currentForm.delete(0);
         }*/
    }

    public void _DoValidateLogin(String name, String password) {
        try {
            //     Alert alert = (Alert) pleaseWait();
            //    display = Display.getDisplay(this);
            //  display.setCurrent(pleaseWait());
            //   Authenticate process = new Authenticate(this, name, password);
            // process.currentThread.start();
            //   process.currentThread.join();
        /*    System.out.println("Funga Kazi");

             try {
             process.currentThread.join();
             } catch (InterruptedException ex) {
             ex.printStackTrace();
             }
             if (!sessionID.equalsIgnoreCase("-1")) {
             getMainMenuForm();
             } else {
             Alert thisAlert =
             new Alert("Police Mobile", "Error OCcured", null, AlertType.ERROR);
             thisAlert.setTimeout(Alert.FOREVER);
             display.setCurrent(thisAlert, getLoginForm());
             }*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showMsg(Displayable form) {
        Alert success = new Alert("Login Successfully", "Your Login Process is completed!", null, AlertType.INFO);
        //  success.setImage(img2);
        Form login = (Form) form;
        /*  TextField userName = (TextField) login.get(0);
         TextField password = (TextField) login.get(1);
         userName.setString("");
         password.setString("");*/
        for (int i = 0; i < login.size(); i++) {
            if (login.get(i) instanceof TextField) {
                TextField textfield = (TextField) login.get(i);
                textfield.setString("");
                /* if (textfield.getLabel().equalsIgnoreCase("")) {
                 }
                 if (textfield.getLabel().equalsIgnoreCase("")) {
                 }*/
            }
        }

        display.setCurrent(success, form);
    }

    public void tryAgain(Displayable form) {
        Alert error = new Alert("Login Incorrect", "Please try again", null, AlertType.ERROR);
        error.setTimeout(900);
        //    error.setImage(imge);

        Form login = (Form) form;
        TextField userName;
        TextField password;
        for (int i = 0; i < login.size(); i++) {
            if (login.get(i) instanceof TextField) {
                TextField textfield = (TextField) login.get(i);
                textfield.setString("");
                /* if (textfield.getLabel().equalsIgnoreCase("")) {
                 }
                 if (textfield.getLabel().equalsIgnoreCase("")) {
                 }*/
            }
        }
        /*  userName = (TextField) login.get(0);
         password = (TextField) login.get(1);

         userName.setString("");
         password.setString("");*/
        display.setCurrent(error, form);
    }

    private String getBase64File() {
        try {
            // return "/9j/4AAQSkZJRgABAQEASABIAAD//gBDRmlsZSBzb3VyY2U6IGh0dHA6Ly9jb21tb25zLndpa2ltZWRpYS5vcmcvd2lraS9GaWxlOlJvb25leV9DTC5qcGf/2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFxQYGBcUFhYaHSUfGhsjHBYWICwgIyYnKSopGR8tMC0oMCUoKSj/2wBDAQcHBwoIChMKChMoGhYaKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCj/wAARCAFvAQ4DASIAAhEBAxEB/8QAHAAAAgIDAQEAAAAAAAAAAAAABAUDBgECBwAI/8QASBAAAQMDAgQEAwYEAwUHAwUAAQIDEQAEIQUxBhJBURMiYXEHgZEUIzKhscFCUtHwFWLhFiQzQ/EIJVNjcoKSF8LSZHOisrP/xAAbAQACAwEBAQAAAAAAAAAAAAACAwEEBQAGB//EADcRAAICAQQABAMFBgYDAAAAAAABAhEDBBIhMQUTQVEiMmEUcYGh8DNCkbHB0QYVI2Lh8UNSwv/aAAwDAQACEQMRAD8AUOGduma1OUGAaxyHIzHeKwSYBqge7fJG6mSdz71qcpMZ7TXnJnaP7zWT+CB12qUBIFUkjaZqBafNAHXajijmBnFDrSAYEe1SKkgecgziBipJTknfbFaqTB3gYrUk8oJEe/vRoUzMAKxicRU6DIH0odCYO8EZrbn5TAOe1cyIoNbIwOpP505ev0sWaWGlEY5Ux19arzSXFmeu9SrQtRlZMdjQxY/sIA5pUFTUqSUjInpQORgGR1nFEIeMQR16VNDlNE3jRBiY6RWC4O8+teBCknImt/s3PsMen7VAVsjAyB6xFZSyT+ERjNTps1gkpMxUrZU2YWknpzdalAuXuQJYUSCR+dENsJTuO8CKlDqDGwPtWq1JGRE71NHWmarUlCJAydooJS+fmMD/AEqS4+8Pz22qDlmYJjp2riGagwTBifWK3bfUmAZqEYmfmSKypSRIOK4iwkvJUMmDvNaFYBJB9qEVkmIrEKIIMdq47cHC5g9aIbuYSOY70qK4G8e1YU5yn8VdQW/3HiLhJ657VIkhW/8A1qvfaDtM/PNG213G5HzqCbT6HHhJVMR7ivKskLBJT8+9a2jqXCenU5pk3mCfpNSkBJGtk14II/s0Y7cJYbBWsJ6SoxULflPSp3rVl+2Bu1wyCIHcmc1LdFTVZPLxOSKm6kAjA3x3qEmNx1+VTu+Y7+9RFJ5ZHypJeYO7JAk1gGAI/WKy+e05rIGAOvpXUAzRajjMe2agWATnJ32qZwZEyT7/AL1AuCZkwNyaNCZEK8AiRtv1qKcwSO+KIVJOYBNDrBGBHeiQiT5MJXOAAMZnpU7DJUqczWGWjAxJNHW7apSEgE9KF2MgvcP023BJkDlAjaaDukw+sAA09ZZSzbqKxkg+kmlDhBdKqlLgeDBBkycYx0ipPC7nPbpWQ6Ix0rCrjcRPpFHQDPIZhcgwPX+/7iikXIb/ABQfWoLeXQFkgNzBUcUru9UtkK5WlKcdg4ii22ilm1+PA6b5LEi6bXBRkkwBGfaty9zJJ5fKP4opVw7rrTDqVut+CoAEpUnK46j+5q1I4wt1s+EyhtAxyEoESJoXGXoip/na9IlbuXJBgEJPcUAtxyTyqPsKtrmvXDt3zrQy6lKfwuIgbdPT3owv2L6Cf8NbbUtMylPLPqI9KipIleLwl3FooYuFzCgaIbdBSZM/OrN/s5Z3LS1WzymVkfdyoKQr96r2paddacS3dsuNzsogcp9QalIt4dbDJ8jIvEB6iDW3IDtj1oBK1JVgmOnapUXBTvke36120srMn2ElBSTmO2dqHWszArc3PNIKgCdq8Fp5Zj2qGhiafRBOQZFRLV19faiHEiY6EYBoRYPN+9SgXwe59+84xW6HIEpJ+fWhyCCN56wayDnArqI30NrS7Ug743inFnqY5gFmI9aqyXOUkRPpUyXCFSMiuoYsifZfbdxLo8u53mvXyFqcbbbZQ6eXmIUrAz71WbDUC0BnHUHrTS71ZQCHLdtKlEcpK+w/eaiS4KHiavA2LVZJ3isK2xIG1bEe/wA6icPQ4O4NJNVkLx83TcitikcgjvUbpKoO09J/OpE/h96kBkK/pB+lRGdjPeBUq1Ccfmc1Aue2Bn/rRITI0cJEgEn1istp7kAzWMxBzmpUAgEA7etGhPbJEpJICf8AWrFpGmqADru2+RtQ/DOnm6f5lYSnanutXibNktNwCMVCV8sOPdCXWbiPIknGKS5IyT+tTOulxalLOT2qFedzj0OKkceKSM7HapbGz+0uqUskMt5WQfyoUrJTvJpzqrS7HTra2SnlcIDjqhkydgfYUS9jN8Q1HkY+O30VrXL9bxUhv7thuAADiJjp1/uaUNqSFeIgQtRHvPoaLdBC0pWpSiIAnM70G4sJd5EiXATIQZkxkiKb6Hk5Sbdsnc1ByfDUlKlp3WOud/enHDpN7qQbDClz+JQHp5jS3SrBTlyqEhRGVEmQBH5V03gTT0NXQDSQVryqen9KCc0uhuODbLLw3wmr7M2txkL8YgwR+EZOfnA+tOE6M0mwSy4yFBucgZJ2Skepq4oT9ntWg2JSBCwN4j863U6kkLbaCUgc3Lso+tIZcUEcnv8ATFaeH22gSooKlBWOTzYE94/Sqm5qi0KLQi7Ynw3Aoykkb4O9d31XTbHUmls3CAlsjIGCf36VQNS+GK/H8XSHEsIMjlBAx9KlTrsXLHJO4lAVpFjqJ5rBarZ3YtLMhKj0HWDSXVNLu9NdKbltSRsD0NdWf4DugtlxxUuISRzpEzVhb4XW/o6GdSQFqyEOHMp7fIVYwpZeI9lmGtnj/aco+d4KjO3Sty4obzJxvV14y4Od0ordtkkszJT2HpVT0qwVqNyWkk80SBOany5btrXJqQ1MZQ3xfAMXeckzWJzsM0Trei3mmLlxBU0dljp70qbfg7iOuaBwaH49TGfKZMsFKSRMelY3VtP71sHAoY29DvWMKyCQemTQobafRnfHrW6fKkAHpjNRpVyTn/rW3POJx6GaKiLJ0rM+0e9GsqXdpbQ2oIU0kweWcEigEgkZP1qZp11oqLThbJOcxUNC88PNxuD9Rwy50V9fWsupSc4jO2agIg+VUVgLM/XE1WNM0cT5jFbLBDYKM+lYX1J+VeCYTBHXvUi2COFUwRE9u1RrJJMgb9BvRziZhPbrUKgkHGBOKJCZIgaTKgVD1olIKRntvWqACZBE0U0BEc2NqJikuS98NW32fTErKQCUycR61VNed8a9VscnY10WwYSvRhyjJR09q55f2xN47G00T4ig8PLYmVI2E9ZqJXNkiIjv601VbACCcbb0M4wAqRgR17UI9oJ4Vsk3/ENlbupJaCvEcKf5Uid/kKO43fSm85AoF5QlRP8AAnJAT74zRXA1uhN9fXDpWks2x5SATBUQJNVnUZU+9ePodRPNCHCCSBgH0H9a6PzHmvGJ3kUfZCPWOVEBLqUpKZViY2/1oFord5Q0hIUshMdf9axrDiOdJJiE56Tig2C7cKCGJC3BKc+b1+fpVj0MWuS6aWlstptkrLaRha0pkq+VdJ4JS03eByChhGBzbqJ2A/WubaHpiGghzUXTMeS1SrzrPYnoKvejXXI62pDam0DyhCTPJ3xVefJZx8HX0qhoKQoEbqHWo03ARJKYVuYzFI9I1EOtlKQrsVEZJoq+uFtgKba5zgEAxjvS3KkW4jZtSgQeYBXL/cVIm4klJI8RQMRgj/SlLV3BASvmUYBJTkGt0tvOoEJVzkkcsiY9/pQuZI/YeHKE+Xbaa2U6HGVNlI5c/wDtPelFuHW0pStBmPNipS/CSAFJnvORTIZdrTQEoJ8FO4v1izXZ3Ns7yh1tRSpJOQa41prnha+yu2iPGgAbEE1d/idw5dP65c6haJ5kOoQVoIiVAR+1UrRE+Drtml4FPI6MHvWhPI8rTqh+njCGOSi7O1u6SxqenBL7Y5iNyK5Bxrwe7pby3rdBLOSR2HpXc9NIVaNlORFR6vpzd/aLaWmSQQDWjnwRy/eZOHVSwz+h8uJUeZMQZ7dTNOH9OuLdhLykczZ3IG1ScYaQrSNVcQAQhZkR0NdZ4d0lvU+HGftLY8VTSZJG+KoYtP5tx6aNieveOMZx5TOLSCBMTvtWyEfhOPc0+4t4fe0e8UtCVFkn6f6UjbOBOZ71WlFxdPs08OeOaO6LJEjl3gYnatpgd+leA51YBkd+taqSTAmoHPoZK2jHtvUeZ6EmmareQSIiOlDuMgHO1VPvL7BATIIO/wCdTpUnlExjtWrjRmUnA/pW5ACEifSpBZhK0zJj3n9KysIVkRvmhnFZBmoecpIHSiQmQYUI7wTUjbWYnpnFL03EqE4GJk0Y2/IyRNE+hSfJ1HhS6Q9pCUqyeWDmqtr7fg3ygkRJx0r3B1+G7hbBMJVkZ+tM+KbcLT4yemTRXcfuJx/DP7yrzIickVGtBnt6HFbpcScYma8pYIgkD8qgtDXR3E2Oh6pdqUlCzyoQonEDKsdT+9UjiW8aXyssIWQv7wpUR+I58312q0cwZs2FKSFpdfjlTurlG3tIyegpfpPDb3Ezt4+Liy0nSLVfg3GoXB8gcIkoSJHOqD6QDvtQqk7PJeItz1Ekv1wUm3tlPvJddhYTJDe5JptaNmxWVrcQh5R5Z/8ADT/rVqRwFYPt+Fw3xbp+pXDaSVIU3yKOdxBIikOscJ6tot021qqTdF5MtuNr5kKE4BA7djRLJGXFlOeGUO0Duv8AlK2kvJbSeRKxJKz3J/KPSrHw45d3LimrZ7ZX3ilGQJ6CqsW7u6VbtLdHhBRQlH8pGJ9+xrrnAOmMtsKHhoHhkQQCSr1NRNnY1uY70Rl23aQtxa+cJgBR6enankuLQEqRzQZk9/6VopAacJSQBEmfw1E9r1hYKlSp5QRE9aTRbXAexauKUVKZAMyMxPY+lEOlprDi2kuIED7zNc84g49vFpcGn2/Kk4BUreud8R8RcQN24uFfYU52cVKvYSRQ1bpAyyJH0azf8h87nMNitKgofOmKXWikqQpM7jG9fJuj8d6/Z3PM/wCCW1/wIEA/3FdTPFqmNJtb+6fCZ5QpsHYxNS7jwzlNSOja5cWzTYU/BbUeWYxNcW47VZi78azUErB8pBojVOPF67o13Y/ZrlKSkqQ4lPlJBwQe9UJYcklQUD6g/rWnDNuxKDXQemw3J5LOx8CcVt3dulh8hLyQApJP51f2nELRKDI3mvl1i4dYeDjKihYOIq7aF8Qbizb5byVY/En+lX8OpTVTK2p0LvdAs3xA0hq+1q0QQPxpWcbgdKu+l2qbWxQhAjygYrkrHFp1Tii1KxyokjmV17V2K1cS5bIUk7iJp+Fxk5SiU88Z40oyEnEmlNajarQtIKo7b1wvW9OXpV8tkiG+by42r6LeG9cy+J+mJLPjoSAQOadvlStXhUo712ix4fqXjnt9Gc4bVkD1zipVqBHU+k0CDk53qZL3lEwY7j+lZTienhlT7LghcEAbTWHkhQnY9xXhgGelYnG0/vVM12gJ4HbcdztQ6+blnof0pg8gKyDt3qHwypMAEetcA0LHJBzIqE7g0e7blOYn5RUCm1TG0VKYmUQMp5iDit0FQBxB7UQpuAe574qNDcA80n0NHYhrkmtLtds+h4HYzg9KvrV0i+sRMERVA5ARAx3MU74fvSwvwnCOU7VCdMYlZBqDBYfPVNCFxWN/rVm1K1S+iRmevWqzdWrjKilaVY/SubHoD1a7cRaIKXOWZbICc8pyZPbFWO00w69wbwVpSX1tWZLlw8lOCta1qJk/lSJxjxNNu1PJ5mkRIndRwM/WrZ8O7psaY5aJKFP2KucCI8ismPYk/SoyfLweczVDWy+7+gr4i4Tt+GrtvUrbVLfTba3yStcKP+UDdU7RTJnVrrWWVWiwtSQOUOhMx86QcY8O3ur3Tz186o2qHCUtpkqI6flXUPh1qFiOE7GwcbKL1CCH0LEKmcH1ERmq8VxYGbc+GciuWl6ZcqbgJWghCQd1mY37kGa6twVcNuWiXC4kqCQFAHrFKfiFw0rUNSsblhSG0NkKWIyoA9Om1D8P6e9pdw+EvKCXVFSW1boHuelObtFSEaZfXnStCUAqUB3+lVbW+GL+9DhtXQ2qZ5l5j5CmVtcFYRzyVbz0p5Z6mhCQmAU9lf1pb7GuNnHda+G+rFhb1zxMErMwhDCgD6FRM1TdQ+GmtXSh/hi2bklX4lPAg5yqckGvqFOoW7wyJ9TB+VSWrbDZUtplpA3hKAmT6xRLLKPQp6eMuzh/CXwjubcsP6/cgpSQfBbJ2B2Kqsvxx4MN/wAC2V7w+CyvTnB4zCPKl1tZCZMdUmPkTXQnrlpy4UhLnMpOFcokD36UdZFD7JYWjxLdaeVxBThYO4NTBty3MJ4lt2o5l8L+Ebix0yNXuWrrnSCgpXzcoOeUelW9/hPT3kkFpOR2qkajeajwVxLcWF0rxbRR8W2UBEtE4j22I7irxoPFdnqLaYcSVRnO1b2B4pxUUkVc+HLjW9dCPUfhzYvgltAB9MVUNY+HF1bgqtlkiDhYmu3tutOJlCgR6Vq4kKEGCKY9PjfpQmOryw9T5lu9Lv8ATHAX2lIKYVzJnBFdU4G4pRcsJYuVcrqcET+dXS/0e0vG+VxsT3iuYcV8IOaa4bvTSW1AzIoVCeB7lyix50NUts+GdSK0LTzJPMD+dUz4hONDTIWRJBNU7TuNru0YLdwhalJwSP3pJxBxBcauSlRKGz06mjnqISg1H1Aho5wnb6K/AJ+VPtC0dV8wtatubB717ReHbjUFIPKpLZzOxq4W13pekWqWwpbqB5CppMgH5xI9Rg1Uh5eNqWZ0v5mrCOfU3DTRtrv6ClbJVMGoynlGYFSJUYgYPb0qNxZO9Y561o1UnG2R2rZICgD1ArUEwJ/SstDE/vUANGSUkZA2odxCMnp6VIcnbzRGa8GjEH8qKxbQItPODkbb/wCtRKYUNhj6UcW+U4GP9a1WOeR6RiiTESiBpakDOfXvUyEAEQqCM4ORWHGiJI23rAwrO/WamrBTosuk3bbraW3CJ6RTdzT7e5ag8s96pLZWF8yDnvvNWfRNQ5wELUAquXsM3WhfqGjONWl5bBBU26iUhPcbCqrwQ6+niNlLPMh5wcm46/t6V2JlCXkhKgFDoe1U7T+H29O+JzKE832Z5hV2IP4SDCsdp+eaJrhmD4jifmLNH8S8aPf2+mOLa1dSrUggNqUQpk/+kx5fY1LdW+iocc1NSrRp9KCpK0PHlnaSBjNL9da8cLa5VFKuk9KqDXASr59a0vLRakZESPkP7ikOIjcn2WPWH1Xd20ltJcHIlREn3ipENHn8RXPyHlBUkenakIU5ZOvAPALSCmDnA/fFN29SQ+R4riUKVCS5mEzkRFS2KGCiQ3y8uIyYiP72rRszDbagUjAjMmtXLgknxHEqkyIOa9YFclJCCgSAlPSlslMdWK0pUnlbSpwRuMe9Mbu4WpIDawCTAgb0BbKSpHMnyqMYnbFNLfwm0h14TAwP3oWGmUO/4xtOHLi+TqRDZbPMlOxWk/xDvVJu/job67U3pFmq3bRP3rpAQod9663rul6FrXK3qNjb3PLJSl1AXE9RVetOA9CtdTZvLPTrZtxlQKkcqSkCdyk9PWmwnHoXNyfRQuLOK3eJGNML6C3cMpWVFWCAqCB+U/OscPWhuyS06pDoIkpVBTQvxFQ+zx7rYfSpKzcc6ZMykpEH2illo+9bPpeYWUupOFAxjtV3FUatWjShBvFSOj+LxHpSZQoXLQ2mQT86yj4jP2p8O9tXm3OoNNeBeI0aqz4NylPiJwob/Onmu8M6bd2ynH/CbQR+JZAH51qJS27sU+PqYk68zZkhb+hVP/qZbwCUuR6ppdqvxCavbZTXhrIUPQfrSnWeDuZ5Q0t5LiSRPMYT9f6UZpWi2GiWdy5erau9RWAluUyhodSJ67Z7VSy6+WNO5fyNjS+CvK01Br7+PyK0xp15qi13DDISwoyXXDyo+Xf5Uws9HtrJwOXVwl9af4EJhIPz3pjdagt9sNrcjkHKnsBSRLinHVJUcjtWRPXzv4OD1GLwfBBf6i3MeXutupQllrlaYUmE8o69jFIzqhdJ8VSiUmMnI9KFbJV4rS8wZHtS+zK/tL5IJHrmq08ksjubs0IQjiSjjVIvRt0weWDUKrcEkZP5V5N0JmM+lYVchKZknNXTPIXGSnqP61o2sBMdO5Nbrd50ioE7Hf3qAaN1PISZAgTvWirsAQnYA71CtHNIHc71CpBECBPQVNgNE5ugYwRUZuREQfpWiW4JjY1qUAjIAHr1qUxckYVdiBHsTFR+PJBMiKw4gSYyBvQ6kEAjp2o0xEkFC8hIEH5YqRu+U2tKkkg/rS0g9M1tzZ6zNTQu2jo/DmvpfAadw4O9Wi3t0XWs2d2UjxG2XW+YjEEAg/rXHLZ4supWhUKGRXVeANRVqTVxAH3DfnMfzGP61O7imVtXG8bYxeaS88pPJJTmDmfY/tUzj6kW6W04QoEpO2BUrktuLWSDnASY26maXaktzkdRz9RGIEfLelmMUfittSrovDmSRJSCTjv7+1LbbVENteGPuCQVkqOTH6U119fjNr5AeYSSZxjE+uf0qgvuKadCeefMfKo7UBCfoXq11vxHUJieaFJhJwQOh96sNncnkSSUqaKTOc+5+dchtb8sXS21qTMT5yT6xHzpxaaypxSUlcmOVCEnCcTPpQtHM7Dp+poCkhRQVKMhOJNQa7qCnlobtlyVECEjYbTFc/Y11Pg/eqEpPIkjPznaf6020i6ZXdB5Dp+0LknPTtHeokuCE+SR/hK5tm3nFcb6m00vzKTb2jaVD05s/tSocLWPMGrLjq9TeKSQhN2ylQcV0BIIMYzV/NgnU7VYW7C1Jydo6fI1XLfgF5jVm7hTgcDagpMie/WihNoPldFZ49Zu9S4jtVONoF0nT2TdLKoQkiQVKUdh69aT2y9CD3gqu7u8dGFm0Zhsf+45PvFXDiLh+44j1J57V75drojZAbZYIDtyoYKlH+EDYbn2qJtux01r7Ppdqi3ZHRMkn1J3J96GeeVUj1vhmihLFFyV8GuhNadpz4u7JdxzEEJCyOU+/WmeoajcXjiXXnSopEDsB6DpSe4cCkzACiZkUGbtSEwFqj3pUs+SS2t8Gvj0mHFLdCKT9xtc3yikJ5lTGZpVcL5xkz0mhzcSckx7Vo5cxjBPWkNtlhUiF4KKzyTPSsFHI6lx5xDYjZRyoe1TId5UpgDmUd4kf31+lEhpLSkupSCo/iVuT7mhoGUvYXcokOtNPOc/lkjlH51oi2caJLbTLZV1ysn54psUcrhQQSFjymO+1achUgJEBST2nFSkA7JVtKTJ5ag5CSYmf0qwllMZAioV2iCMDMVouJnXYk5YB6fnWwAMpORMetFvNBMkCfagyJUY2nFCQzYoEd60UoRtWjiyB3/KgnXDHlPrU2Awh1Y39Zih1uRvtUJUTJwT0mtFCVTkYmd6mxcjy3hvvjBqFb536+1bLb5uhP71kWjqvMltZBxt+lSmIkDl3+XNaBwpn9utMEaa+rBb9xHemdhw05dLCQk5zgR9TR7uBbhJ9CvS7dd9eN26IlZyoYgdTXYvhwNOsrvWLQPgv3JZ+zpUkBJbQmChPrzFSvWfSq5omk6dpTjgWvnuAnII/LNAapdKF14zKi04ghSFIwUkdRSJZfi+hb+wedgeOXDZ0nXGg2Fq85AmQmJiq/enxWVBClAKAAJxy7D2o3RtZHEOlF1YQi8tsPIE5383sfyNAvJEgBBUomQVzmO9FdnlsmKWKThJU0VfUWw2paXwFTICiYmPziqRxAw628rk8yQOfnODn+ldBvkNpUsK8vMk46+4nrVW1W3Q+mFArBEJUOp9R0NcuGImvU5+88Aoyvzk4JA9MioxcuFxJQvlJyDG5jIBpxfaMslRCSJGSAPoRFIL6zcYTzI5hGIIj60zaLUn6htrrTqUBKllKUpkK6A/sf6U/wBJ4iQ0tIBBXEFU+VQ9K57cXKS5BScjGMRUClqSrmbJSk4IVtHvU7UwefQ73o/G4bfCHlySnc7b1erTipp1ttJcSVLwDMznpXy1pqdUfdSmyZfdOAFAwPqavPD9lqzTyXtRdbbbbyltK+Yz6dBSptR5svabSanUcQg2vfpfxOja5qQFwthv8CDA5TgTmq+9dKPWO/ehbi4kqJMknM9fnQLtwT1qn2e+wx8rHGHskGO3RCiAevfagl3UmCR7TQrr3Seu9Bvu77V1WRPJQwN3Cd89M1gXcmCY7mkzjp5CobDMCoRcwISTKjiN6nYIeoosdrcFx+QTzJEbd/7FPy6FNQTPoBVAtL1SboLOEFUbGrbbXaXmSc7eYUEo0Mx5VMa4VbJIMcpj2ry/4XBPmGY70Nbu/dFM4M1JbuJcQpC4JBnNDQ0dvXCSTyn2zUJuZO5HelniLPUz716cd46960bKO0LfdCk70EVQZGT615RIUcj0mvIgEkbz1FD2c0QP+YbChSgGSYzR6glW4wK0UhIE79M11ANAAaGDPfei9Ls2rm8S2+qEEde9eUmCf1netSSknMSO1ShUo2XJOnaVbtg8yTj3qF+509tJ5eXA70js9Kvb0BQSppkmfEeJSPkNz8qaN2lrYQbdJeeG7rg/QbCpnmjH0Ow6Sc2EBtkkLuybVgRCikyZ2x6+tD6/eu2Ngw3aJbNup5PiujJInAI9a3UTeWL7bilLV1JO9JtOuw4y5bXXmTHhGRMDoaq5MzlwamLTRx8knEV0be+tbu3WpfjYUSd4HX1od248dIcQqR+lC60lTdjyKUVLYWlSSe21QIeDS+c/gV+KOh7xSkxrVcDTTNSutJvm72yVC0iCk7LSd0q9DV9YuWNX09F5bBPm/EAqSD1Sr51zK6UGZWtQDfc/tUWg8Q3Wm6iHG1KRZKVDrA3cHc+o3FOxyMXxXRLOt8PnX5/r0Og39skuKWsyDiYBHbPX6UjvbJTRPONxEiCkj0/WrQopuGQ8haXEKEo5FSCk7RS26a5EugjnwMEEj6U88i2U27PICVJCm1Eg7gg+/X0qn6+pBb5hv+HJBx7Vc78ApJaJJTvjA9PSqLrKStThEHzEkjH1psCtNlXVbG5fJSSSo5Harvp3DbWmNMLfQl27caDqlLE8gOwAOxgVHwPoqbjUEOOAwFT2n0q58TISNZWEkENtoSYOxic/Wg1DqNGx4BijPU3LmkxbbMQOYmRWbi4AwMfPpUDj8DAn0oR5xSdlQo7dh61Sqz2zlRI5cZMHO5xtQrr0n1qJRgRuZ+dQuKCTJAHyokhMpm4cTMzBoO7dBEDJJjet3XY+eIpXcPfexOBRpFTLlpUMrchZUkn8Q7fnSxUocUkkYPUxtXtO1BIvQhSTEwCa31AITdrHSTsNqmmmV5TU4qSCNN5XrdTP8e6czneKZ2F0UlAOcZntSNuWVtugeipxBpklxJWFjCVdO1C0OxTpItdq+FJSnBmIzUlu4ApRnJ3mlFo9LcpMgDNHWjiRzc0DbelNF9Ssfu6fdtKldusxnGagJUkQtJHuIrpNtxLpNwkeKG/Wan8fQLoZLWekitTyU+pIwvt+SPzwZy+ZEddsGsJPmMSM99q6YvRuHrokpW0D1ggVTOI9Mt9Pvii1cCkSfLMxQzwSirdNfRj8Oujle2mmJFSTgfTFeAOSD1yRV04U4esdUswu5eVz5kBURFP3+E9GtGC4oBxQwhEyVE9K5YZOO70+8CWtgsnlpNs5lZWL9+8GrZJKsSdgPnVma0uz0lCVrSLm5UYClbJPoKPsktNLdTbIS22lRA5cUu1N3xLxIGUNiB796qTn7Griw/FyZu7pRXBUVGMR0pRcvK2JPyoh5zmJiPc0BcGdv6Cq8nZfikiawdjnSevpSd9vwdWcT/CtPN/WimHS2sSIB6Vi/So3Vu6lPMrKTAnH/WhJ9SDUmnbu0AaHn2PrnP6UKFcroYQhKj/GtWQP6miF3Li7vkcADKcDlxP9RU10kF494kY3FSKbsHuLEKUIhSgMDoR6Dak11bKQpRSkhQ71ZWvvmMkhaBOetbP26Lxsnlh0GJA3qVKgJRTRBwfxCLRBsrtZTbEyhRH/AAVdj/kP5GrXcLKoCFKKVCUZnmBHeuc3doth0rSClQiQaX3DOr2yV3PDd87buzLtmqFNOHuEnAPpiasQmumzzniXhUsjeXD36r3+pZddV4CStXlPYdfn6VTn1l+55DzKUokkpwY9fSsXXGOu3KPD1LSbV5aZBUzLR7GQZFAs6++l7xBpLpx+FbsA/MCY9KtRaSPOS0me62nUeDLFttptSxyyJCSOncmqf/iqdU1rXr60cLjSb9SUBWxTypAH5Uivtd4j1S3VaJS1Y2JEKbtx5ljsVHJ+UVL8PLBadJ1FDyOUv3KwAfQCD9aVOnF8mv4Tp8uHOnJdpliLrakBYSUqV+FLggc3Yz1oJ5RQVhc88+Yq70eGEFo8yXFMLT521beoHYg0vumyySzdqUtj/lvjJSOk9xVZd0ep3NrkFUvJjv26UJcPQPSprth21bC1AqbVhLicoPz/AGNKH3zPy/60aRUy5NvZMXgVnsJOaS3Lw5lE7kxPb1ozxfKtWRJgClFwVNLyCQciDTYR5MzUZWTWCuR5JzzTHqPanGoK8R+T1GZ3pJbE+K323wKa3TiSgKSSMQDM101yThfwUEBfM2SfNIhQ/etmHSlBEk8ue+J7UKw4pCyCqQce9TqhDoKRCVbigosqXqO7G4SlwJWYKhGD0pu2pbYwogHYgbiqyy7BRJ2xt0p1aXKPDCVwANppUl7F3FO+CxpUZj6VIHOZOenpQaD9exNZ58HAxn2q8V0EeJBjI9jWA6qZ5j33mogTGT7044f0N3UiLi4UWLAKhTuxcI3CO/v0qHS5CS5C+GtNu9ScLiXnbezQfO6kkSf5U9z69Kt7qkMpShqUoQITJJP1O9bXF2wxatW9o14Nu0nlQkdBVdv74rUc47VWnOx+DC29zQS7coZSQjrmaUP3HOokb0I/ckqyRv8AWhlPbSYpDdmhGkFOP4gf60I66VJJxWhdnc/So1uco848pwaAncbKVKQenWpXH+VDYGFK8sn6f1oaSMBXMDie4odwq5ms/gXEVyIk+BhrTKF2bbzSQFiBjr6Vq2S9b2738YBQZ/KvPL5tJdKo8kH86i4edCrVTatkr5T13qfQXdMOaQELSsfhVPX60Q22EryQUL2n161oMNuNYnKh0zUIIcYkkylWSD0riGyVbLdwTa3Z5Hf+W6Np/lPvVffYXY3nI6kjMKEdKe3QK20OEzjMbTQtyE3KUs3SuXmjwnydj/Ko/v8AWuXBD5FzjDbxUHUpDkYX0PaaU3CLphSv+6HVp6LQ4gpV+f7U4eZetV+G+lSXG8d5HeiErVyczaoP70SBlHd9CuNWl5dED7K1aoO61q5yPlgfrTiyYTbpQ3bIJSjBUs/iPU+9EqJeTC1kfKPzqVtARuQI7VNsiOJRM21qVl9KgOQEKEdCRmgk260uu2RA5sluRM+n99qsliAiwQsjzOuFRT/k2obVrIKCHUghxpUY9MiouySs29x9me+y3LSAlfTlkKH6flS7iDhpta1f4dyofjmSyT5HB/kPQ+hq0azpqNStFlAhxSeZJ6hUTQGjPi+sCxcCbq3gKTOSO47HFSpNcoXOEZ/BP8Dk+orWwpTDiVJUg+ZKhB9jQPiF2CufQAV1TirhlnWrYOJUlF2kQ2/0X/kX/WuZu2jtrcqt7lvw3kGFpVuD79auY5xkuOzA1elyYp88xfRllf3skAkbUzclQbBIyZI7RQTVscnMDJj9f1otpPMsq5ZCRyj361zr0Jxprhm6CeYNzuZFFD7xjAPMOlBvIUkBSTKgcZ3xRDClJPOBI/liga9R0XTphLKhyicpOIpxYrSpEeGVHt+9Il8ohQgJPbMe9F2N0+w4XGioEpg8vv8A6V2OMXJbuh8cjj0XlKTnGN68kE7R9al5O0GawEElIQASeh71YY08lVuwyq7vifsyFcoQmeZ5X8g/c9vU1fdMt327Bu+1jlac5B4VuMIt0dEx1MdKonDirS61dzVtSXGkaTCGEHPivHaB1P8AEflVm1LUn7kJudR+55ss2wOUg9VetVMk7ZaxwfSJNQvvEKiPw0hurlRBmtLy735zk70pubnmJ9OlJ7LVqKJ3LmScdK0+1dsD0pY87vn/AK1D43kUQTjsamhfmDpNx1MR70QHAUAwFJImKQ2j4WtbU7pkVPplx43jW8jmTkYoXEmOSwi3uEt6gqyWTCk+IyfTqPlRTiwl1aSTkBQqtaxclp6zuAohTTg6dDinFysqumVEkhTY9t65r1Ijlu17DVLiTaOoWYDpKR64pZw64otXoJyFjH50tv7pS9ctmEKhDHnUO5NH8Jfem+BV5VOqABHYVNUmwPMUp0iyeJIac3ChkzP971kAIf5CJSoQekUNalSrco35TzTRCyVMtLJzkE+ooBpI2mQ82U7eYfvQlw2FsGcgdCOlEklLyXDAChP13rVDfK6puU+YQcVxwGi6H2dLF4nxGE4S5upsdvVNaKt3LVxJPKthf4FpyCK88yOYpT5Z9djUNpeqsy4xcNly0XlTZGR6jtUoi6CSCYiM5qa1ZFw8lo4QT51dhUfIFBKrZYW2r8JO4PY0xQ2liw8NshTxV94rvUhOQXBW++lHMGwkBA/yisqhxsSB50xJzkVhKwl5lYgcyYMVkSltczzIVOB8jUIAXqUWXG1/wE8hHr3qvawg6Lr7V+0n7p2A4kbEHcVarlpLrTyRzHmHOj0zSjWWDeaH5oLjXl33FcuCJLglebT4hS2eZp1Pit8vUbwPWM/Wqhxfp6Ly3+0ISDc2/UD8SP8ATcU4sL5Q0Vsn/iWTwIzuk9P1oTUXkodIglCSpIHdMyPyMVMW4uwZ1khtZQXlhtpeRAHMRPT1qW2Ry24UQFkjmmlmvs/YtResws5ckH/JuP1j5UztyfCAlIgTtVxr4UzChNvI4v0MrEggIJ9J3qNpRbUEkSD36ipoKY5ogZiNhUCz5ykAmOvWhXIx8cheSCDBmcz1rzCikFKwMdD0rVog4kTOJ/etLhBSvyTn0oV7BNvtHTgsECcxUV2SLRzwlBCyClKlDCJ3UfYSa1SuQQr6ChtYvm7HTVuqAW8V8rSDsTvn0GP0qxN1Hgtwau2a/bm9M+yJYYC7lCYsbRQnwQf+c4OritwOgInpRSS82FPXz6n7peVKUaWcMWUNv6rqKzsVuOKMkTsAf5jUd3qP2lSnAAlJ2TOwqj3wXIypWwq5us7x86BeuJnb1nAoN64ioH3oQjmMEyYokhU8pM48YjuDImtLdwLFyj/yyoT0oFx0Eb7YOa209zmee6S0siPaiapCPNuVEtvdeHe2riiQJ5VT2/pRFrcm24mQk4CzykdKQrcAUhQVMKBE1PqD8a3auf5hv6xip22JWelfs0MuJjyPPNifK6APrTW5uEtKYWZATbpM/M4pDxM5z6uUDq4n9qL1skpthsFMJSY/9RoGuEPWSpTaIbB1T2qeK4DLsq36dKs3BhAt2lmR4xccz6mBVQL/AIIvHUQShsIQOyjsP3q4aGjwG7O3T+JtkJ/rUT+ULTv4v1+vQd2cIeWDsoQYoppMtuNhUgCRNQsNkKMkCBjpUyZTcxkyYNKLxsDzMZ3QfyrFx/y3ACMZ+VZaSQ6Wz/FgA96w5IYKYykzJ7VJBC4pKnkkpjmzFCuMBT5SQO8+tErH3SVAHBio7kqDjakJJUogJHczj9a5K+iHXZHpyBbvqSMhQKfSN4o6zkhxJyo9fWr/AGXwh1dzlcu9QsbdRg8iELcj0JxXk/CvVEkv2OpWFyMwFJW2D88z71a+yZDN/wA30d1v/J/2KSkj7KhRyUmCJ+lEhyXkkDyupipNV0q80a9fsNRZLVwUeIlJUFJWg7KSRuMflQXNzWqTPmBj1NV5RcXTNCE4zipRdpk3iAJCo/CYMUM8kDxkkeUjze1a3rgceQ0l3kS6OZxQVBSgb+0mBPrTv/Zm5d09L9sGPFKOY2kqCwM4nIJIG0zJjpSMmaGKnN1YOTNDH8zOaOtm3fvbVQHnbKY9s0u1B7mTarkkqRkHA/DH7VY9WZ51NvieZCgheN0nY/t8qqOoEIWw3kKQhYIJx+KBViPIvK9oi4kZQ7xAhS05NujI715OG2zH4cyK1v3fF1hSxnlCUT1xvW+QISAUgn61a5UUjHdPJNr1ZIDtIE7ARvUTpAWFKTA2itjhoHt3O81gQtPKcyPrQhvlGzakieXb50VyJdQE+YRnahmkY3AAopslCQU43xNQw4fUvQBwTuD0pPc23+Ka+W3VpbsrBkO3DyxCWwcmfU7AdaetgKMQmTjO1E22muandWdho1ozd3t9cH7Kw8mWnXgAVXD4O7TKClXLBClKQmDCgbKxvK9kf+ic+ojpcTzT5r0936L9egg1P7dqltbOIatdL4fkrtXNSu27QXP/AJiQo8zk90pIGwNL39NvlW7j9mm01K3aTzOuaZdt3ZaT/MtCTzpHqUx61cNGHw9Pxf06z1O81Lia5Wv7I/e3jaDb3V0rnQtbnOSS2nyhCUAAQDzK3Kn4jXfw0Dbi/hvbPadrlleAIuUruUl1IOV26gopwf5uXGR66cfD8CWxp37/AKf9DzEvHtW5Obar2oqDboeKVJWCDkFJ3rS/d++SkHYbfvRX25vXdOudTS2hrVbMBeoNNoCEXTRUE/aUpAhK0qKQ4BAIUFgA81JL90/askwlCSRNZebTPFOvQ3NN4hHU4d8eH6omcdPNMwRtJNb6c7/3gUgky2pJjpINAvKIUnzfiGKxpzhVfIUcnIn5UprgYsvxpGFuFTaDkkwZj61LdLKtTtgk82UmY3/uaVtumEifN2qdhYc1ZIJ8qROfSj2lZZd1L3aG+ouh7iFUKMIMmpb/AFNN1p1nc26FecFtCCfxEKMUkuXlf74/kqUkhJI74/et0vJbQxbtGVMNBEkzBO8etCocK/Qe9Q02vf8Av/2ONPt1XDrTElbaF87iui3Dv8gNq6NwVo15xJxKzY6f5VLSpbjvJzBlpP4lEDfcADqSK59pLvI0pRB5Wk7/AOY/2a7N/wBn3irR+H9Wvka083a/bGUIaunTCUFClEoJ6c3NMnHl9qGEFOaUizLLLFp5TxK5V/x+RXNN1QXEhfKFKV5E7qOcCB12q6aTwPxBqX+8Kshp1okSbjUF+EAAN+X8X1AHrVt4k+InB3D1y4/w7a2F9qjsnmsmUIBJ3K3QOp7Sa5XxLxnrfFD5Oq3Z+y80ptGzytJ7Y/iPqqaZkx4MT9/oTg1er1SWyGxe8uX+C4/PgfaovhrRnuVp641+/SZ5m1m3tEmImUyteexg9xSBt0uOcyhBUM0pXlIydqKQrlLZBwaqZJ7+lRp4sbh3Jt/X+3S/BBQIU2tMbZqG7WoWYUmfuvMPlkfpW6SA6UxhWInpUbqS4w80ckgiY3oIummMatUfXB5Lm3IMlt1GYMYI7/OkegsLRql6bmTcsDwC4VH7xGClUbDHbrNE8I3YvuFNGuQoKL1myskd+QT+dRm6YtuIrnxHEthbbKfOY5lEqGO5HlnsCK3D5nJOLaOffG6zLa9K1FAkIdXbOQcgLHOk/VKh865eF/8AESmciRX0D8UdP/xHgXVUJSC4w39pb9C2Qr9AR86+em1JK0xPIpNZ2shU93uex8Azb9Nsf7r/ACfP9wjSFn/FLUo5ipUoMJBJzgZ6kqj9xXRba+vrt+4TpyCLC0Ph+A6zCwlODCQrnSob/hImBFct0+4VbX7LzchbbiVAGROdp+lXe8BVpN7dWiR5bpi7bQlOUeJ5VD0II3BiK814picpRl+H5/8AI7X425qS/XP/ACLOOdJQG0ay0tH2S8UEOoR1JyVpO3ZRHQzXF+I/uNVuU7qQlCD0yZNdt4rtnXrPX2EOp8OzWHUJ5lElCgFHrETJB33FcM11XjcSPkHy8wWexwP9KveFTc8XLuv5cNfkyFkbwpPl3/QEXZlCeY8xc/EYoNaiVkE9T7Cjrh3nbj+ED6H+xS64y6CiSFmTNa657K06XROjCDOEmRWrcpME7HY4qJxwNhPUjPepEAqhZgTia4i7fARuTvB29K2BQE/eEnrnyj8q1IhOCf5jms8sxEjH970I06Ao+RUylH8Z/lSMq/IH60RqektahdpTpvEB0/U7DT1OOui7CeRaFlarYMJHiFZJLoXJCoAAwKBv3OXSrvkHmLap9o2+cflVI465/wDa3V0twQ5cF5CgOiwlaVSM7EbVreHtbpyXaoxf8ROSx4oejt/yPajwRreluPHUNPcLNuQl8tDxUtKLSHOVRGxCXUT0BMSTSNTKbeEqWFQchIyn37Guu2Duqae81p/CPFDet26n0K+zX4SppfhpSUr8VJ5gOdBhJjytTkASj1m9tHUqY400N5jWnGEqNyltJcf5yo+MCggI80n8KipKUpxk1oyp8nlrK3wxctt8VaOtcqZuHvsb6DgqZdlpYPyWaT6ilbF0llwy4lsNKJG5SSk/mKvtlwppR1ZrWNI1Uv6TZak0vkcA5k26U+Os8xIJ5AlSFQndM7KBqj684ty6ZecBS44jxFCNiolRH5ms/WJJJGx4S38bXXH6/mROKLlqgxlO9a6ceW9aGQebftWGDzM8oAHZJEGo7Vf+8tHmEcwjFZ1cUbal8SYElXK+UyYk4j1orSZN5dujcHlBPShH1EX64OeYiYzM0RpboaZcUYHOpRwPX/SmNccFXHJeZz6Wbak94NuQmSZBjtGf1NQacfIkkkqzM5zUV2747ykzsnHp/eKZaDapU6p5RCmWfMSRuroP77VDW2AUW8ubjoeIm3tm2shxXnV7nYVO0tauQBSgPTr2paXVOvKJVJO5Ao1SwlKYgeaYAwPrVZo14T9uh7aK5EfxLUdzP9xRrDiZnqdzNI2HFLjfO5jNM7dRgTjM7bmkyRoY8l9DfxMAZ/pRmOVOTilTS+ZW8mKO8SUx0/vNBRaTDnVErlIwYrYqKXlAdZih1OgoSrGcGtvEHiIgY6+lQEfQPwV1Fq84FtrRKh4+nuLtnUA5A5ipBjsUkfQ1LriW7+/R9u03UOUPOJSGrpaUlKTBWQE7HlTXDeGeJLvhvWVXlmnxmnEeFc2pXyi4bzif4VCSUq6GRsTXedEvLXiTSG73hW9b+zH/AIjb63C405/KtPNKSO3XcSCDWvhyLJD6nh/FdFLTZnNfLJ/pDtF23qnDjj7ramWn7dYUhw5SOUgg/nXy/bOzbWypnygTG9de+LfFDGhaK/oen3C16veNJRcQ6pYt2iPMckwpQkAbwZ7TxFt8hlKRA5T7VV1k02or0Nb/AA/hljxyyS6lVfgFOnkusTgnar7wPf2t3Z/ZLlIcWoFIQTAdRuU7HIIn2E9KoDqg46JzI6DepNFvRblf36rdaFgh1JIKScTjIz+tZepwLUY3Bm3qMSyxaH3xEXcW5ulov5srthfiNpgQpKwEGRuOTAB2zuTNcQubgKvbu4Oy3CE+wx+1XXizV7q5VcP3LrzxSkBbjggEg4Cdpycn0rnd0ShlobmST5adoNP5GNQfZlZV5MVH2DFr5kqIjHfFCOArQZwJx0mokLKxB6bCOlEJSScQZEZrQqivu3gaQpxwSSB7E/WmjSIbOVExUbTYBUogTOTRAEGT8sfvQt2MxwaI5PICMgY22FSNgqJH8XXMVrkqO30qRKSoASn2oQ0XnXSLZtFqB5oCnM/QfT9arGraY9rNrbr05C3dXsGPCXbpMuXVsj8DiBupSE+RaRnlShQBHNDbUXi8864TKiqSf2+VKbkJJBX/AAkKSQSFJUMggjII7ij0uoeGd+/YzxXQx1uPZdNdMqttcrTdm5tH12r6QQl1pwoWJEEcwyMYP0o681HVdd1pouuu6tqTiEMNhKed1aUiEpSlIzA9PU0/uL66uXOa9Vp+pK/8XULBt50/+pwcq1e6iaw9c3SLN5jx0W1q6OVbFhbotEODssoHMseilEVpvXYqtHk4+B6pyppV73+n+RAllnSLO5sEONXGqXYS3qD7SgpDDYIULZCxhSiQC4oGPKEAnzUi4hQV3Kkmd4md6Mb5UPtpSAlAgAJGB9KF1jN+7zAZUYHeqM80smTcbePRw02n2Lnnli+xUAkoO+0z+VaphN0gzuoTBrzJKHoMgbetedATdIP4hI3kzmoFJ8IC1AQ9zmACTWEqDVuhJxAk1nUlglATMqJzMzWzFum9uEMhUJMSJnFMT4VlSV72o9mmj2T2oXQShJyZNWm78CzaRaW5lKD5iP41d6MQ01o+n+E2B4yhkjoO1JlkuqUTOeopMpubv0NPHgWmht/efZJb8szAM/nRiQC3gAkGd+vzoNsYnfc7USHQlP4irqc9KXLkdB0hlbJnJT/T6U0thkYkUiZugkkRjamDWoYiOUmSBmlSiy9iyRQ2QscySTtnJMx71Mu4kjfek6HlqAOY2gdf7NWZ3gfi9tKObhrWNthb83ywamGGU/lHS1EIfM0vvBzc/diB9a3D/wCDIECpP9kOKxHPw3rUA5P2RZ/SimuEeKBBVw7rGNv90X/Sp+z5PY5avE/3l/FAoVzOZGDkVNpGsahoWp/btHu3bO5KeQraI8yeykmQodpGNxFMEcLcRBYKtA1ee/2Jz+lQO8Ma8hedD1fGJNk7/wDjXRw5Yu0mTPJhnHbKSaf1QqW87cPXD7zi3XnCVuOLWVKWo7knqa0C/u4kb996Ic06/YEP6ffNqg/jtXEx9RQV0laGipxtxuDnmQR+tQ8OT1QcZxqosLac8yM4qMlv/EG0Of8ACuQWj2k7fnFLmrpsqELQeXEhQxWusPFphDgChyOJWn69KDy5J8o6U6jYp1zmbYNutRUfFgyTsBNJ3m/EDSSRME567U24mc8XU1BOxUV7d6hs2GnksKcBKQtQxIMEbe1Mi6Rn5I75NIBZs4VAHKYk70Qm1gJMQnrTs2CHnOS3CkN8kqIM4mgvsiUOGSopnEmZz+lFvOWDaAFvODJjfb5VgNlIJABnIHf2qRxs8oUoqPUCa0T+IKBMkRXWwWke5UqWFEp5Tknt6VKgKcB5Ao9Ry9BWOULUOffqI39/6UQEJSkBRJjp1FQ3QUYje+WGzyHAO+aVypwyZA6RipCftC1OOGZOwFROmCNo60C4LU5XyZQYVk4O9ZdUSggEHrE1FzSJIjp0xW1uCpRBiYgHepAt9AQBFylSeaQoYnagtRJN67CoTzHI3imD6IeA3JUIJpbdEKunSSAkrIimRfNlLN8tfUAcSErCiSfWorhcOIE9e8RUzqimZ3mll0797JGAD+VWIqzJzT2ELrhcuhBKiDAGdzVs4c00aawq+vY8dY8iP5felnCrTLHPduQpwJ8s/wAP+tHXt44+4STjoANqjJJv4EN0mOMEs8+W+kb3j5uH1c6on1zmojOJMZrVtHJnJM9OtY58kp6bRS/oWJSbdslkbnOe/WpUye8DeT9KDSvnOFSBkUcy3IzHX0qHwFB30ENo5JJmaIYBWoFPNvsT0qJDfOYyZzHSjElLeEkZ6xS2y5BBAMZ5syAOuZruvxl474h0HjZuw0XVXLS2TZNOLbDLaxzkqz5kk7AVxPRbf7TrOmsqHlcu2U/IuJronx8TPxQvcgkW7AAI/wAppkG1jbQnNjjl1OOM1aSb5/ADe+K3G4hKNeVvE/ZGf/xp9ecbfEPS+H9E1e71tH2XVkOLYT9la5gEn+LyDcEKEdK5ppemu63q9jplp/x719DCVbxzGCfYCT8q+k/jLw6y/wDDXksWxGilt1lIyQ2gcih/8ST8qPFulBuxOp+zYM+PHsj8T54XX/ZzBHxX4yKkpOqtep+xt/0p3p3F/wAUb+0bvbJh66s3QVNut6ehSVAGMRncVyeQHRmY6j+tdI+B+o6ojj+109i9uRpi7d9blsXCpowAQQk4SeYjIzv60GDJKU9smXNZpsWHC8kMcePdDC8+JHxB0kJGq2TVtzyEKu9NW2FECSAecA0GfjdxSzhy00Z5HWWnE/os0i+I+sX+p8bawze3dw7b2t441bsqXKGgny+VOwON9zNNeBOHNKY4fvOMeLmfH0m2UUWtmpMi6cBiSDhQ5vKAcSCTgCiU5SyNJ8IRLBpoadZcuNW6pK+W+kSp+M9tdEDVeENEuyTBKXRn/wCSDQHG+t8I8QcDay7Z8JI03UUMoUzcW6k+GlanAkDyEZ/FjlzFGj4vK+1Ns6lwzov+z6VcrlohrnWhvqQSOUkDpygHbFZ+OK7dbXEibQISyE6UGvDSEp5YcIgdMEfIUx5bj8LK3lLHLa8bg3/ub9Vx+ZwTUVeLfuKOYIA+QoxlJTbpPZQP50FcgK1B0ESAv6008Pk04khMlPNjpVRuqNPErcmMbdc2hQCQg5VG57CpA0U2ynVDOYA+tQWgKXQIPmM+tHao6kW3KMiAlIH5mhbLiSq2V14QmFjmPYA0GQJJTE9AKbXFvicH36igVNmMgbbUSZUyRdmGt8zHrW5TzCA5H1qNpPMd56dql5inAyO1SQugt5YYQEJMnYn1oILKhPXtTm6eS4P96sUrT/4rJj9MUIm2tnzNu/yKP8Doj89qDrssSjufwsXBRSqCUxtynrUtuqFSoGPapr/Tn2wCW89IyDQdir77kWDvRdqxL3Qkkwi7ISQuNiBVau1j7Q8JAIWoj0zvVlv0/dolOxmqhqCiLp+P5yDJ6TR4lZS18ttEVw8CPfsN6XNgvXPLgpGwJmt7l2EyNzRWjWxKVOEe1W0tsbMKTeXIohtsyUJKU7qBn3otlqIUompbdCeZB3BzHWsvogkCeopEpWzWx41GJCtY2BzOTUWDgkT7bVt4cEgE8x6Cant7VSyCSqCOma58EJSk6N7dolW0jMTR6UJSn0j3rzLPhnrNTcpGwyOppTkXYY9qMIPIkE4Pr2rZsFRJIIA/OsAHYTyx02rdAiNsdzEUNjUh/wAHthzirQWycq1C3SI//dTV2+PKp+J+qT0aY/8A8xVQ+H1qbvjvhxid9QYkg7AK5v2qwfGdTh+J2u85khbSAR2Daaav2TEW/tkV/tf80Ov+z/p6F8VX+v3oAstFs1uqX2cWCPyQHPqK6J8F9eTxXpvEum6pClvXDlyUE7s3EykDsCCPmKR8Fs8P8O/BppHFV1cWbfEri3SbdCi6pGOUCASByJBOP4j3qbgjVPhroHETN1o+t6um6dH2bluW3PDUFkQFS2IEgZnFWYJQSTZn6mtR5strb4SaVrjvn6nINU0x7RtZutKucvWbqmCqfxQYCvmIPzq8/BF0p+JVgNudi4Tt/lB/am3/AGgdANnxJba00IZv2w256OoGPqmP/iarvwfeFv8AErRCrZa3Wh82l/vVbZszo1nl+06CU/eL/ikK/iSDb8fcRmMpu3Fge4CqvHxaI034fcFaIx5EeCl9YjdSW0iT7lajVU+NDXhfEHX0hIBUpCveWkmrh8cmkXGkcIaiwfuHbVTYjbKUKT+U0TXExN7npb6r/wCeDjVwjmbPUdh1pmHFXnBmvh91x15u4sYU4sqMArRy56ARA6RT34f2/CmqXjel8TsakLu6uUtW1zbvcraOYAJSobyVTmDuNs08+JfCWhcL2mr6ZoS75dyLNm/uRcOBaUIS+Ep5cA83mVPpFLji+DfY3PqYeYsLTT49OKs4IsFT7qgBlZPvmrDdtBGnOEYCEBMepxSO3QVPtpBgFWcz1qwagR4KGBufOv8AYUtvksYI/BJk7KArwlx/DB96H1BR8RKQCQVFX7b0x09HjWXMBKgZGaCWjnvHlZhBCAf1ofUsSXBFClNnJB60E6gJbUpUAkkCPzot1JKg3MgmTFRXCRzhIkgZAHX0rkLmuAEpKTkAkDrW7cpBwArqBUykZMkE9SdgahUsocUV83NOaKxNUUhi+vdOXNncOtmfwz5foad2fF6HFJTq9mmZkvMiFD5VXnyYmf8AT2+tCLk4nJ3jrV944zXKPLQ1WXA/glx7eh1LTNRt7nOlaghQP/JcMKHyNEXX2dZAvLUsOA+Vxvb+hrkAJSQpJIUNo6U607ijU7JPIXhcMnBQ8ObHvSZ6V9xNPB42vlzR/qdA1ZlK7QuWyg4P8u6flVA1cKZvVFxMNuedKjjf+zRFjxAEXK/+KhCspKVQUnqJ7Uv1bUHbxspUpLiQZ5zlWfWow4pQlTB12txZ8e6L5AWEG6uUpMR+lWKzZCFBGIIgClGkI5VlZIkVYEo2kZ707I+aKejx8b32GNoI5QBnrWHW5OAJ+tTtpkJgScdPSpfAUsmET6j+lVW+TbULVIBatVKIJGaOatwmOXfG1bpZWj8SDtvFblLik55sDehcmw4Y1EjIlPL26VvyeUnEjFZSjJI39qyVeQ9D37VAyiLH/StkfjlRE9prRXVKSB85qVpuY2neT1riEXT4UJU58RuG0o3F8CfWEqJp1xnpVxxV8atT0llKvEub4NFQ/gbCEhaz2ASCZ7xSX4QvJtviZw3KoT9oUVEnZIaXNXK/tPiOzx9xNqfBdi8jT9SuTFwEMK8ZCT5SnxDIG+29WsMbgZ2pzrHqbTSe3i3Suz3/AGhbxKOKNN0xlpTNjY2KE26QCEkE55fYJSPSK5YHAQohfKkjJmIEd66bxFwt8UOI9PZt9Xsrq6aad8ZCVrtkQuCJ8pHc0lsfhtxzZXlvctaA8HmHEutkuMrAUkhQkc0ESNjg1E8M5S3D9HqMeHCscpxte0kdb177Xxf8DLR+4t3V6omzavUo5Tzrcb3MbypIUY9a5R8LlF/4lcOpaBUVXJdI3ISG1kn0FXUap8Zm7kLXpKXfNJSbViD8wsGhdCb+J+kXGpXLGhBt/ULhdy+4bVpaypUSAQuQMbf1p8sdyUmuippZTxYsmLdH4rr4urF/xyQB8RrwLAh62ZdRP8Q5Sk//ANae6a8eMPgW9YtBTuq8PqTDYypSUSUkD1bKh7pNK+J3fiDrFkqz1bh1b6DHK83pf3rcKB8q0k8sxmBtVc4WveJOBtdXqNtpN6gEeG8xcWjqEOomYJ5cEHY9PUSKVskpvh0yyoSlp4RUlvhVU07r+4p4Ohzjbh5Cdl6hbwRmfvAf2roXxMUbnjj4ggmUscNEY6EFpX71qjjrgix1D/HLTg19jXpKsuJQhC1YKkyYBMnIROardtrlzxA38S+Ibllts3GkBgobVzJQXFpQhIPWAiZxXOChHagM0suXJ5soOKSrn3ckco07l/xBgwFJSqY6GmDqipyVA8yjnP8AfpQuh2zjrpcQnGyZ6n+5NSLMPQoHfv0qm+zWxJqF+5YdJuE2+mKdVJgqzQCQoMpIACiZPeT/ANaT3Gorbt0s45IUYnrTPTrhLraCkhQgQY6RUU1yGsqk9pMLZSEKJUknoT2oZQRPmUnmH0pwEAIJ5QRttSi6TyKlJgTjpQphzW1EbiR5jIj3oNaeYDzDm6iiXFeUicH0/OoSgLEkD3oytPk564raAMZkH0odRJIED51I5J9Z69KjAKciI75rTR4uTbNFAgem8VoU+YAVITvmSd61Kcx0BiisWb2zXjPNon8SgPaiktBy4WhMCDO/SsabypvmOY4KontTC2SC44qESScgGaXKVNlrFi3JfeT2yA2mfL2x2pi1BKZVvmtGWYESAeucUUhnkOYyNjVeUrNnDjaQVbpPiCIzmBuKnHNJVJB/StLdB5wIJjB6f2KOaaCsgCY3nFV2+TShG0Bh59JyZipBelUBYA9CN6nLcDlIACfXatTbgjzAAnsN6jgOpLpkS3gobCOuYrRat5iZ3BrJbLagEgTnG9e/4vLyT1qeAG2+yNCUnM+tEAgbDNeSkAYTA9K8TGAZ7ATUNkpUSac89aXqbm2edYuEgpS62qCmRB9sSKs1vxfxKwwlpriDVEIT0S/A/SqzbpPNtgd+tFQeSDkVzk0R5GOfxTim/qkWD/bbitCgEcS6tI//AFJPy2rKuOOKxylPEur83o/I/Sq2ck5gTPpW34c5kZ7Zrt8vcn7Ph/8ARfwRbUfEHjDrxLqGN8oP/wBtZtviHxilwg8SX5z/ABch/wDsqotY9jjeKkZJDoz6DNd5kvclabA/3F/BF4R8TeNWiOXX3F/+q3ZUD/8Awpgx8YOMWY572xeH/mWgE/8AxIqgggrGQa1XhSjkZxHWu86a9TpaHTP/AMa/gjqTfxt1xYCL3SNGuUnBkOJn6lVCcR/ELTNZ4U1XTLfhi30u7vvDK3rZaORZQrmHOAlJPUfOuaAnn9DvianbPK42YyCJ60XnzaqxcfDtNGSlGNV9X/ci09CmGyt9SVLKYShAhKAd4pXrDJSA8jYdqapbeeUXDISSSnHStiyHWC2sAk4waDp2Xttx2lMuDzo5iMHrOxrGl3f2e4DZMoUTynse1FXdqbW4cZUPu1fhPaldxaqaSkzPn39809U1RlZN2OW5eh0nSnEXFuJ/FH0oLV7ctoKkjEkJg7+tI+G9VNs+lL2JgKE9DV6uGm7m1UkeYEeXvFVZJwlya+KSz4+OykKWOWJAz32rWCpIkgHvOK9qLTlq/wAqo7RQxulNoT6/zCmpX0UZy2umUI5GTORJ6VGVKkkYipiZiIziD2qJQVkACPQVp2eNZoTIjYTJk14JkSSQdqwSVZ3E7CvEnPWuBRMlZSnmGFJIO3UU30z71xRGUlZ9N6RqUQkhO24NPuFxztKnMKjvSsiqNl3SvdkUR+0khWBkxAqVPoMDEj9ayygFRRMAiPKI+VZUFpKkkEFOIiqjPQxVIJskgnpj0otwFlQWJg/SoNPzMx8/WmD6AUgcuIpUuy3BXE8E+KgKEk79q18PeY+fStbRZbVyqHlNGrSknmxHWoGpWrF7zQUCCPWewoVTfh8xGMzNNlNmZV9e1C3LcgRHsP2rkwZQ9QRKkqODB/esJEnA3qN8KQqQDHTNSsr5kR/Eek70TE3bpkrXSPedz86kUsgCDnaTUcwCCDO596yJJyDAMdRUDFwqMyCRzD3ivSInaT9fasL6REisc0TIMRmoOs3b7SD881uhRLg960Gw7DcmvJKgsGJx06VJKGLeVAxg9/ftWrgKVKg4HpvWWokHvitroGTuD3IoRr6BCOVYAE+9G2YJeQOUfiEA7fOgl4CTED9KYafm5ZGJ8RIP1FcAgriC/OlXiG9Qt0Ntu4aeZVzNLPUSQCD6H86EOoWziSpKxPvXVNV0C1v7R61u7ZC2lSChwDPzrjnGHB95w+l280kuXVk2OZbBlTjSf5kn+IDtuPWn+XGXXZlYvFpr9qvxN7lm2vwG1mCATzjcCk15pj6AhaZWyPNB3I7mkrGtLdQssLJHLB9p60ajii5bgKCXEjuIxXeXOPBZetwZF8TNXEgqkeRY67RVk4Y1ja0uVjm/gV+1IjrljeAfa2C2vbmQZ/KoH7dJ+9sHg4mZCdlD5VDjvVS4Ix5ljlvxOy96rYC9QSlILoEYG8VTLy0cbUUFAkHM1YeF9bTdj7K+oouE4SFfxe9OrmzYucupAUDv3pCk8b2yNGeKGqj5kDgfNt5h9a8okpk8p6e1aieU5gjesk4AUTn9K2aPn9nlEek/vWkkZ6HfNbEgCcRWijg9M11AmVHE5j3p7woY8QQMqFID9f3qxcOpDaW1GYVvQZF8Ja0X7ZMsqCUgnY4yTvRjrZdYS6kDG9CBMtqzORvRLHnYPNBIGR3mqEuD1OP2NWHORSSYjuetPU8qmOYSe00gfaUFFC4K0bHvTbSHi4ypJmQKCS9UOwyabizRSJVPUdZom3fJTyk/1qG4lpwgg/XpXmwMFIkzAoaHLhhiogJJEelRXCQBMYzInetULPlION62eViCZgwajoK+Bc42Co7FNCHmQowRiSKOuBGAO0e9BLCSrzCTmjRVnwTJWTuqT1E1IJMdSRtMYodsjAk8tTpiMEZ/SuZMXZuuOTEHHbf0qPrzSCPQ71uuMgE+o9axEc0kxkk9ahBUbg5HLvuM15BPMc/n+9YXjc5PWstjmI7nNdR3qHsHOInse1TXYIgxEifSoLYq5QTBii7sQ0CdhjFD6j10LTJEevejtOUfFYJV/EDPzFAlQKjO2DFGWigFJydwflRAI7ai4SoEpXztgdpNDvfZ3SZKQrbJ3FIrZ51pYVbOqlW6TsR2NTLurd59CHUrbeJwEnE0/aeRTZXda+HPDOp3C3WWHLG4M8zluvl5p7pgpikbnwebQSbTWHFk7eMymB8wRXQ7vSrp1tRtHYdgwhRie4muc32v3Wm3a2nluJWgwQFT+dMU5PgJfDyga4+EF8oKUm/tQenlWmk1x8L+I7fmVYuWV0UboaehQj/1AVZ2+L9QW3I5ineQaJ0jjS9Zvk/aENrbIySM0Tc66JeRvk5HqbeqaNdhOq2rtq+kwHFDB/8AcN6v/C+vNapaEPuJFw3+IkjI71bNS1LTNbtnW7lo8ysE8tce13TDpOorOnLLaVYIGx+VBLHHNGqpljR+I5NLO3ymf//Z";
            return Base64.encode(capturedImageBytes);
        } catch (Exception e) {
        }

        return null;
    }

    private Image GetThumbnamil(Image image) {

        return null;
    }

    private Image createThumbnail(Image image) {
        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();

        int thumbWidth = 200;
        int thumbHeight = -1;

        if (thumbHeight == -1) {
            thumbHeight = thumbWidth * sourceHeight / sourceWidth;
        }

        Image thumb = Image.createImage(thumbWidth, thumbHeight);
        Graphics g = thumb.getGraphics();

        for (int y = 0; y < thumbHeight; y++) {
            for (int x = 0; x < thumbWidth; x++) {
                g.setClip(x, y, 1, 1);
                int dx = x * sourceWidth / thumbWidth;
                int dy = y * sourceHeight / thumbHeight;
                g.drawImage(image, x - dx, y - dy, Graphics.LEFT | Graphics.TOP);
            }
        }

        Image immutableThumb = Image.createImage(thumb);

        return immutableThumb;
    }
    TextField searchField = null;

    public Form DoSearch() {
        //4000145391
        Form src = new Form("Search Form");
        searchField = new TextField("Driving License", "4000145391", 200, TextField.ANY);
        src.append(searchField);
        //   PCommand back = new PCommand("Back", "Back", PCommand.SCREEN, PCommand.TRAFFIC_MENU_SELECTION, 0);

        src.addCommand(back);

        PCommand search = new PCommand("Search", "Search", PCommand.SCREEN, PCommand.SEARCH_OFFENCE, 0);

        src.addCommand(search);
        src.setCommandListener(this);
        lastDisplay = display.getCurrent();
        display.setCurrent(src);
        return src;
    }

    class SubmitData extends Process {

        public SubmitData(PoliceMobile policeMobile) {
            super(policeMobile);
            Thread thread = new Thread(this);
            thread.start();
            System.out.println("Thread Start...");
        }

        public void run() {
            DoSubmit();
        }
    }

    class SearchOffences extends Process {

        private String drvno = null;

        public SearchOffences(PoliceMobile policeMobile, String drvno) {
            super(policeMobile);
            this.drvno = drvno;
            Thread thread = new Thread(this);
            thread.start();
            System.out.println("Thread Start...");
        }

        public void run() {
            DoPrevious(drvno);
        }
    }

    class Authenticate extends Process {

        protected String username, password;

        public Authenticate(PoliceMobile policeMobile, String username, String password) {
            super(policeMobile);

            this.username = username;
            this.password = password;
            currentThread = new Thread(this);
            currentThread.start();
            System.out.println("Thread Start...");
        }

        public void run() {

            DoLogin(username, password);

        }
    }

    class Process implements Runnable {

        protected PoliceMobile policeMobile;
        public Thread currentThread;

        public void run() {
            // DoLogin(username, password);
        }

        public Process(PoliceMobile policeMobile) {
            this.policeMobile = policeMobile;
            System.out.println("Login In...");
        }

        public void DoPrevious(String driverNo) {
            try {
                String date_entered = "";
                String reference_c = "";
                String points_c = "";
                String opp_id = "";
                String name = "";
                String METHOD_NAME = "get_entry_list";
                String SOAP_ACTION = SOAP_URL + METHOD_NAME;
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("session", sessionID);
                request.addProperty("module_name", "Accounts");
                request.addProperty("query", "driverno_c = '" + driverNo + "'");

                Vector oppOptions = new Vector();
                oppOptions.addElement("name");
                oppOptions.addElement("date_entered");
                oppOptions.addElement("id");
                oppOptions.addElement("points_c");

                request.addProperty("select_fields", oppOptions);
                envelope.setOutputSoapObject(request);
                httpt.call(SOAP_ACTION, envelope);
                SoapObject body = (SoapObject) envelope.getResponse();
                //System.out.println(body);

                Vector field_list = (Vector) body.getProperty("entry_list");
                String id = "";
                for (int i = 0; i < field_list.size(); i++) {
                    SoapObject entry_list = (SoapObject) field_list.elementAt(i);
                    id = (String) entry_list.getProperty("id");
                    String module_name = (String) entry_list.getProperty("module_name");
                    Vector name_value_list = (Vector) entry_list.getProperty("name_value_list");
                    for (int j = 0; j < name_value_list.size(); j++) {
                        SoapObject name_value = (SoapObject) name_value_list.elementAt(j);
                        String prop = (String) name_value.getProperty("name");

                        if (prop.equalsIgnoreCase("date_entered")) {
                            date_entered = (String) name_value.getProperty("value");
                        } else if (prop.equalsIgnoreCase("name")) {
                            name = (String) name_value.getProperty("value");
                        } else if (prop.equalsIgnoreCase("points_c")) {
                            points_c = (String) name_value.getProperty("value");
                        }
                    }
                }

                Form previousOffencePage = new Form("Previous Offences");
                previousOffencePage.append("Name: " + name + '\r' + '\n');
                previousOffencePage.append("License no: " + driverNo + '\r' + '\n');
                previousOffencePage.append("Points: " + points_c + '\r' + '\n');
                previousOffencePage.append(" " + '\r' + '\n');
                previousOffencePage.append("Previous Offences:" + '\r' + '\n');

                METHOD_NAME = "get_relationships";
                SOAP_ACTION = SOAP_URL + METHOD_NAME;
                request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("session", sessionID);
                request.addProperty("module_name", "Accounts");
                request.addProperty("module_id", id);
                request.addProperty("related_module", "Opportunities");
                request.addProperty("related_module_query", "");
                request.addProperty("deleted", "0");

                //    request.addProperty("related_module_query", "driverno_c = '" + driverNo + "'");
                httpt.reset();
                envelope.setOutputSoapObject(request);

                httpt.call(SOAP_ACTION, envelope);
                body = (SoapObject) envelope.getResponse();
                //System.out.println(body);
                field_list = (Vector) body.getProperty("ids");

                String id_list = "";

                for (int i = 0; i < field_list.size(); i++) {
                    SoapObject entry_list = (SoapObject) field_list.elementAt(i);
                    //System.out.println(entry_list);
                    // SoapObject entry_value = (SoapObject) entry_list.getProperty("entry_value");
                    // System.out.println(entry_value);
                    //System.out.println(entry_list);
                    id = (String) entry_list.getProperty("id");
                    id_list += "'" + id + "'";
                    if (i < field_list.size() - 2) {
                        id_list += ",";
                    }
                }

                METHOD_NAME = "get_entry_list";
                SOAP_ACTION = SOAP_URL + METHOD_NAME;
                request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("session", sessionID);
                request.addProperty("module_name", "Opportunities");
                request.addProperty("query", " opportunities.id IN  (" + id_list + ")");

                oppOptions = new Vector();
                oppOptions.addElement("name");
                oppOptions.addElement("date_entered");
                oppOptions.addElement("id");
                oppOptions.addElement("description");
                oppOptions.addElement("points_c");
                oppOptions.addElement("sales_stage");

                request.addProperty("select_fields", oppOptions);
                envelope.setOutputSoapObject(request);
                httpt.call(SOAP_ACTION, envelope);
                body = (SoapObject) envelope.getResponse();
                //System.out.println(body);

                field_list = (Vector) body.getProperty("entry_list");
                //String id = "";
                for (int i = 0; i < field_list.size(); i++) {
                    SoapObject entry_list = (SoapObject) field_list.elementAt(i);
                    Vector name_value_list = (Vector) entry_list.getProperty("name_value_list");
                    previousOffencePage.append("***************" + '\r' + '\n');
                    for (int j = 0; j < name_value_list.size(); j++) {



                        SoapObject name_value = (SoapObject) name_value_list.elementAt(j);
                        //System.out.println(name_value);

                        String prop = (String) name_value.getProperty("name");
                        if (prop.equalsIgnoreCase("referencetxt_c")) {
                            previousOffencePage.append("Reference: " + name_value.getProperty("value") + '\r' + '\n');
                        } else if (prop.equalsIgnoreCase("sales_stage")) {
                            previousOffencePage.append("Status: " + name_value.getProperty("value") + '\r' + '\n');
                        } else if (prop.equalsIgnoreCase("amount")) {
                            previousOffencePage.append("Amount: " + name_value.getProperty("value") + '\r' + '\n');
                        } else if (prop.equalsIgnoreCase("date_entered")) {
                            previousOffencePage.append("Offence Date: " + name_value.getProperty("value") + '\r' + '\n');
                        } else if (prop.equalsIgnoreCase("description")) {
                            previousOffencePage.append("Description: " + name_value.getProperty("value") + '\r' + '\n');
                        }
                    }
                }


                //     previousOffencePage.addCommand(back);
                PCommand next = new PCommand("Back", PCommand.SCREEN, PCommand.BACK_TRAFFIC, 2);
                previousOffencePage.addCommand(next);
                policeMobile.DoShow(previousOffencePage);
                return;
                // return previousOffencePage;
                /* previousOffencePage.setCommandListener(this);
                 lastDisplay = display.getCurrent();
                 display.setCurrent(previousOffencePage);*/

            } catch (Exception ex) {
                ex.printStackTrace();


                //  display.setCurrent(DoSearch());
            }
            Alert alrt = new Alert("No details", "No record found", null, AlertType.INFO);
            alrt.setTimeout(Alert.FOREVER);
            display.setCurrent(alrt, policeMobile.getMainMenuForm());
            // policeMobile.getMainMenuForm();
            //DoHistory(driverNo);
            return;

        }

        public void DoLogin(String username, String password) {
            System.out.println("We are logging in");
            String responseBack = "Please try again";
            try {
                String SOAP_ACTION = SOAP_URL + "login";
                String METHOD_NAME = "login";

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                SoapObject userAuth = new SoapObject(NAMESPACE, "user_auth");
                userAuth.addProperty("user_name", username);

                //  password = "B1nadamu";
                MD5 md5 = new MD5();
                md5.Update(password);
                System.out.println(password);
                System.out.println(md5.asHex());

                userAuth.addProperty("password", md5.asHex());
                userAuth.addProperty("version", "1.0");

                request.addProperty("user_auth", userAuth);
                envelope.setOutputSoapObject(request);

                httpt.call(SOAP_ACTION, envelope);
                SoapObject body = (SoapObject) envelope.getResponse();
                responseBack = body.toString();
                /*for (int i = 0; i < body.getPropertyCount(); i++) {
                 System.out.println(body.getProperty(i));
                 }*/

                policeMobile.sessionID = (String) body.getProperty("id");

                if (!policeMobile.sessionID.equalsIgnoreCase("-1")) {
                    policeMobile.getMainMenuForm();
                    this.currentThread.interrupt();
                    return;
                }
                SoapObject error = (SoapObject) body.getProperty("error");
                System.out.print(error);
                String error_value = (String) error.getProperty("description");
                System.out.print(responseBack);
                responseBack = error_value;
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getClass() + " | " + ex.getMessage());
                responseBack = ex.getMessage();
                policeMobile.sessionID = "-1";
            }

            Alert thisAlert =
                    new Alert("Police Mobile", responseBack, null, AlertType.ERROR);
            thisAlert.setTimeout(Alert.FOREVER);
            display.setCurrent(thisAlert, policeMobile.getLoginForm());
        }

        public void DoSubmit() {
            String error_message = "An error has occured please try again";
            try {
                String filename = "driver.jpg";
                //   display.setCurrent(wait, disp);
                String METHOD_NAME = "set_entry";
                String SOAP_ACTION = SOAP_URL + METHOD_NAME;

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("session", sessionID);
                request.addProperty("module_name", "Opportunities");

                SoapObject name_item = new SoapObject(NAMESPACE, "name_value");
                name_item.addProperty("name", "name");
                name_item.addProperty("value", offenceCode.getString());

                SoapObject amount = new SoapObject(NAMESPACE, "name_value");
                amount.addProperty("name", "amount");
                amount.addProperty("value", fineAmount.getString());

                SoapObject dl_item = new SoapObject(NAMESPACE, "name_value");
                dl_item.addProperty("name", "driverno_c");
                dl_item.addProperty("value", driverNo.getString());

                SoapObject driver_name_item = new SoapObject(NAMESPACE, "name_value");
                driver_name_item.addProperty("name", "drivername_c");
                driver_name_item.addProperty("value", firstName.getString() + " " + lastName.getString());

                SoapObject description = new SoapObject(NAMESPACE, "name_value");
                description.addProperty("name", "description");
                description.addProperty("value", offenceDescription);

                SoapObject mobile_no = new SoapObject(NAMESPACE, "name_value");
                mobile_no.addProperty("name", "mobile_c");
                mobile_no.addProperty("value", mobileNo.getString());

                SoapObject offence_code = new SoapObject(NAMESPACE, "name_value");
                offence_code.addProperty("name", "offence_code_c");
                offence_code.addProperty("value", offenceCodeTxt);

                SoapObject payment_option = new SoapObject(NAMESPACE, "name_value");
                payment_option.addProperty("name", "payment_option_c");
                payment_option.addProperty("value", paymentList.getString(paymentList.getSelectedIndex()));

                SoapObject name_value_list = new SoapObject(NAMESPACE, "name_value_list");
                name_value_list.addProperty("name", name_item);
                name_value_list.addProperty("driverno_c", dl_item);
                name_value_list.addProperty("drivername_c", driver_name_item);


                name_value_list.addProperty("amount", amount);
                name_value_list.addProperty("description", description);
                //  name_value_list.addProperty("sales_stage", sales_stage);
                name_value_list.addProperty("offence_code_c", offence_code);
                name_value_list.addProperty("mobile_c", mobile_no);

                name_value_list.addProperty("payment_option_c", payment_option);

                request.addProperty("name_value_list", name_value_list);

                envelope.setOutputSoapObject(request);
                httpt.call(SOAP_ACTION, envelope);
                SoapObject body = (SoapObject) envelope.getResponse();
                if (isError(body)) {
                    getMainMenuForm();
                    return;
                }
                String opportunityID = (String) body.getProperty("id");

                System.out.println(body);
                System.out.println(envelope.getResponse());

                String file_base64 = getBase64File();

                if (file_base64 != null) {

                    SoapObject noteRequest = new SoapObject(NAMESPACE, METHOD_NAME);
                    noteRequest.addProperty("session", sessionID);
                    noteRequest.addProperty("module_name", "Notes");

                    SoapObject note_name = new SoapObject(NAMESPACE, "name_value");
                    note_name.addProperty("name", "name");
                    note_name.addProperty("value", "Captured Image1");

                    SoapObject note_description = new SoapObject(NAMESPACE, "name_value");
                    note_description.addProperty("name", "description");
                    note_description.addProperty("value", "Captured Image1");

                    SoapObject note_parentType = new SoapObject(NAMESPACE, "name_value");
                    note_parentType.addProperty("name", "parent_type");
                    note_parentType.addProperty("value", "Opportunities");

                    SoapObject note_parentID = new SoapObject(NAMESPACE, "name_value");
                    note_parentID.addProperty("name", "parent_id");
                    note_parentID.addProperty("value", opportunityID);

                    SoapObject notes_name_value_list = new SoapObject(NAMESPACE, "name_value_list");
                    notes_name_value_list.addProperty("name", note_name);
                    notes_name_value_list.addProperty("description", note_description);
                    notes_name_value_list.addProperty("parent_type", note_parentType);
                    notes_name_value_list.addProperty("parent_id", note_parentID);

                    noteRequest.addProperty("name_value_list", notes_name_value_list);

                    envelope.setOutputSoapObject(noteRequest);
                    httpt.call(SOAP_ACTION, envelope);
                    body = (SoapObject) envelope.getResponse();

                    if (isError(body)) {
                    } else {
                        String noteID = (String) body.getProperty("id");
                        System.out.println(body);
                        SoapObject set_noteRequest = new SoapObject(NAMESPACE, "set_note_attachment");
                        set_noteRequest.addProperty("session", sessionID);

                        SoapObject set_notes_name_value_list = new SoapObject(NAMESPACE, "note_attachment");

                        set_notes_name_value_list.addProperty("id", noteID);
                        set_notes_name_value_list.addProperty("filename", noteID + ".jpg");
                        set_notes_name_value_list.addProperty("file", file_base64);

                        set_noteRequest.addProperty("note", set_notes_name_value_list);

                        envelope.setOutputSoapObject(set_noteRequest);
                        SOAP_ACTION = SOAP_URL + "set_note_attachment";
                        httpt.call(SOAP_ACTION, envelope);
                        body = (SoapObject) envelope.getResponse();
                        if (isError(body)) {
                            // do nothing   
                        }
                        String set_noteID = (String) body.getProperty("id");
                        System.out.println(body);
                    }
                }
                getReferencePage(opportunityID);
                return;
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getClass() + " | " + ex.getMessage());
                error_message = ex.getMessage();
            }
            Alert thisAlert = new Alert("Police Mobile", error_message, null, AlertType.ERROR);
            thisAlert.setTimeout(Alert.FOREVER);
            display.setCurrent(thisAlert, policeMobile.getMainMenuForm());
        }
    }
}