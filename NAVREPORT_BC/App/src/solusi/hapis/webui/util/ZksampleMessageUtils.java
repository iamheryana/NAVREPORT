package solusi.hapis.webui.util;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;

import java.io.Serializable;

/**
 * EN: Util Class.<br>
 * DE: Utility Klasse.<br>
 * <p/>
 * 1. doShowNotImplementedMessage / Shows a messagebox.<br>
 * 2. doShowNotAllowedInDemoModeMessage / Shows a messagebox.<br>
 * 3. doShowNotAllowedForDemoRecords / Shows a messagebox.<br>
 * 4. doShowOutOfOrderMessage / Shows a messagebox.<br>
 * 6. showErrorMessage / shows a multiline errormessage.<br>
 *
 * @author bbruhns
 * @author sge
 */
public class ZksampleMessageUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    public ZksampleMessageUtils() {
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++ GUI Methods +++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * EN: Shows a messagebox with text: 'Not implemented yet'.<br>
     * DE: Zeigt eine MessageBox: 'Noch nicht implementiert'.<br>
     *
     * @throws InterruptedException
     */
    public static void doShowNotImplementedMessage() throws InterruptedException {

        final String message = Labels.getLabel("message.Not_Implemented_Yet");
        final String title = Labels.getLabel("message.Information");
        MultiLineMessageBox.doSetTemplate();
        MultiLineMessageBox.show(message, title, MultiLineMessageBox.OK, "INFORMATION", true);
    }

    /**
     * EN: Shows a messagebox with text: 'Not allowed in demo mode'.<br>
     * DE: Zeigt eine MessageBox: 'Im Demo Modus nicht erlaubt'.<br>
     *
     * @throws InterruptedException
     */
    public static void doShowNotAllowedInDemoModeMessage() throws InterruptedException {

        final String message = Labels.getLabel("message.Not_Allowed_In_Demo_Mode");
        final String title = Labels.getLabel("message.Information");
        MultiLineMessageBox.doSetTemplate();
        MultiLineMessageBox.show(message, title, MultiLineMessageBox.OK, "INFORMATION", true);
    }

    /**
     * EN: Shows a messagebox with text: 'Not allowed in demo mode'.<br>
     * DE: Zeigt eine MessageBox: 'Im Demo Modus nicht erlaubt'.<br>
     *
     * @throws InterruptedException
     */
    public static void doShowNotAllowedForDemoRecords() throws InterruptedException {

        final String message = Labels.getLabel("message.Not_Allowed_On_System_Objects");
        final String title = Labels.getLabel("message.Information");
        MultiLineMessageBox.doSetTemplate();
        MultiLineMessageBox.show(message, title, MultiLineMessageBox.OK, "INFORMATION", true);
    }

    /**
     * EN: Shows a messagebox with text: 'temporarely out of order'.<br>
     * DE: Zeigt eine MessageBox: 'Noch nicht implementiert'.<br>
     *
     * @throws InterruptedException
     */
    public static void doShowOutOfOrderMessage() throws InterruptedException {

        final String message = Labels.getLabel("message.Information.OutOfOrder");
        final String title = Labels.getLabel("message.Information");
        MultiLineMessageBox.doSetTemplate();
        MultiLineMessageBox.show(message, title, MultiLineMessageBox.OK, "INFORMATION", true);
    }

    /**
     * EN: Shows a multiline ErrorMessage.<br>
     * DE: Zeigt eine Fehlermeldung an.<br>
     *
     * @param e
     * @throws InterruptedException
     */
    public static void showErrorMessage(String e) throws InterruptedException {
        String errorMsg = "";
        if (StringUtils.containsIgnoreCase(e, "SqlIntegrityConstraintViolationException") || StringUtils.containsIgnoreCase(e, "SQLCODE=-803")) {
            errorMsg = "Terjadi Duplikasi Data";//\n"+e;
        } else {
            errorMsg = e;
        }
        final String title = Labels.getLabel("message.Error");
        MultiLineMessageBox.doSetTemplate();
        MultiLineMessageBox.show(errorMsg, title, MultiLineMessageBox.OK, "ERROR", true);
    }
    
    public static void showInformationMessage(String e) throws InterruptedException {
        String errorMsg = "";
        if (StringUtils.containsIgnoreCase(e, "SqlIntegrityConstraintViolationException") || StringUtils.containsIgnoreCase(e, "SQLCODE=-803")) {
            errorMsg = "Terjadi Duplikasi Data";//\n"+e;
        } else {
            errorMsg = e;
        }
        final String title = Labels.getLabel("message.Information");
        MultiLineMessageBox.doSetTemplate();
        MultiLineMessageBox.show(errorMsg, title, MultiLineMessageBox.OK, "INFORMATION", true);
    }

}
