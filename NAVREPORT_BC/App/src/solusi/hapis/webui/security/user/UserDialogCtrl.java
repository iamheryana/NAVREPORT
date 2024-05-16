/**
 * Copyright 2010 the original author or authors.
 *
 * This file is part of Zksample2. http://zksample2.sourceforge.net/
 *
 * Zksample2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Zksample2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zksample2.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package solusi.hapis.webui.security.user;

import java.io.Serializable;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecUser;
import solusi.hapis.backend.security.service.UserService;
import solusi.hapis.webui.security.user.model.UserRolesListModelItemRenderer;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 * This is the controller class for the /WEB-INF/pages/sec_user/userDialog.zul
 * file.<br>
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<br>
 *
 * @author bbruhns
 * @author sgerth
 * @changes 05/15/2009: sge Migrating the list models for paging. <br>
 * 07/24/2009: sge changes for clustering.<br>
 * 10/12/2009: sge changings in the saving routine.<br>
 * 11/07/2009: bbr changed to extending from GFCBaseCtrl<br>
 * (GenericForwardComposer) for spring-managed creation.<br>
 */
public class UserDialogCtrl extends GFCBaseCtrl implements Serializable {

    private static final long serialVersionUID = -546886879998950467L;

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window modifUserDialogWindow; // autowired

    // panel account details
    protected Textbox txt_Loginname; // autowired
    protected Textbox txt_Password; // autowired
    protected Textbox txt_PasswordRetype; // autowired
    protected Textbox txt_kodekaryawan;
    protected Textbox txt_namakaryawan;
    protected Textbox txt_kodesubunit;
    protected Textbox txt_namasubunit;
    protected Button btnSearchKaryawanLOV;
    protected Button btnSearchSubunitLOV;

    // panel granted roles
    protected Listbox listBoxDetails_UserRoles; // autowired
    protected Listheader listheader_UserDialog_UserRoleId; // autowired
    protected Listheader listheader_UserDialog_UserRoleShortDescription; // autowired

    // Button controller for the CRUD buttons
//    private transient final String btnCtroller_ClassPrefix = "button_UserDialog_";
    protected Button btnNew; // autowired
    protected Button btnDelete; // autowired
    protected Button btnSave; // autowired
    protected Button btnClose; // autowired

    // ServiceDAOs
    private transient UserService userService;

    
    private SecUser selectedSecUser;

    /**
     * default constructor.<br>
     */
    public UserDialogCtrl() {
        super();
    }

    /**
     * Before binding the data and calling the dialog window we check, if the
     * zul-file is called with a parameter for a selected user object in a Map.
     *
     * @param event
     * @throws Exception
     */
    public void onCreate$modifUserDialogWindow(Event event) throws Exception {
        // get the params map that are overhanded by creation.
        Map<String, Object> args = getCreationArgsMap(event);

        if (args.get("user") != null) {
        	SecUser editUser = (SecUser) args.get("user");
            
            txt_Loginname.setValue(editUser.getUsrLoginname());
            txt_Password.setValue(editUser.getUsrPassword());
            txt_PasswordRetype.setValue(editUser.getUsrPassword());

            
            // Set the ListModel and the itemRenderer.
            listBoxDetails_UserRoles.setModel(new ListModelList(getUserService().getRolesByUser(getSelectedSecUser())));
            listBoxDetails_UserRoles.setItemRenderer(new UserRolesListModelItemRenderer());
            
            setSelectedSecUser(editUser);
        }
        
        /**
         * check if the user is new ( means: userID == Long.MIN_VALUE )<br>
         * If new than it's not a transient hibernate object and an error occur
         * by using this ID for a hibernate query<br>
         */
        

//        doShowDialog(getSelectedSecUser());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++ Components events +++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /**
     * If we close the dialog window. <br>
     *
     * @param event
     * @throws Exception
     */
    public void onClose$modifUserDialogWindow(Event event) throws Exception {
        doClose(event);
    }

    /**
     * when the "save" button is clicked. <br>
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnSave(Event event) throws InterruptedException {
        doSave();
    }

    /**
     * when the "new" button is clicked. <br>
     *
     * @param event
     */
    public void onClick$btnNew(Event event) {
    	SecUser newUser = new SecUser();
    	
    	txt_Loginname.setValue(null);
        txt_Password.setValue(null);
        txt_PasswordRetype.setValue(null);
        txt_kodekaryawan.setValue(null);
    	txt_namakaryawan.setValue(null);
    	txt_kodesubunit.setValue(null);
    	txt_namasubunit.setValue(null);
        
        // Set the ListModel and the itemRenderer.
        listBoxDetails_UserRoles.setModel(new ListModelList());
        listBoxDetails_UserRoles.setItemRenderer(new UserRolesListModelItemRenderer());
        
        setSelectedSecUser(newUser);
    }

    /**
     * when the "delete" button is clicked. <br>
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnDelete(Event event) throws InterruptedException {
        doDelete();
    }

    /**
     * when the "close" button is clicked. <br>
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnClose(Event event) throws InterruptedException {
        try {
            doClose(event);
        } catch (final Exception e) {
        	modifUserDialogWindow.onClose();
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++ GUI operations +++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /**
     * Closes the dialog window. <br>
     * <br>
     * Before closing we check if there are unsaved changes in <br>
     * the components and ask the user if saving the modifications. <br>
     */
    private void doClose(Event event) throws Exception {
    	modifUserDialogWindow.onClose();
    }

    /**
     * Opens the Dialog window modal.
     * <p/>
     * It checks if the dialog opens with a new or existing object and set the
     * readOnly mode accordingly.
     *
     * @param anUser
     * @throws InterruptedException
     */
    public void doShowDialog(SecUser anUser) throws InterruptedException {

        // if aUser == null then we opened the Dialog without
        // args for a given entity, so we get a new Obj().
        if (anUser == null) {
            /** !!! DO NOT BREAK THE TIERS !!! */
            // We don't create a new DomainObject() in the frontend.
            // We GET it from the backend.
            anUser = getUserService().getNewUser();
        }

        try {
            modifUserDialogWindow.doModal(); // open the dialog in modal mode
        } catch (final Exception e) {
            Messagebox.show(e.toString());
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++ crud operations +++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /**
     * Deletes a secUser object from database.<br>
     *
     * @throws InterruptedException
     */
    private void doDelete() throws InterruptedException {

        final SecUser anUser = getSelectedSecUser();

        // Show a confirm box
        String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + anUser.getUsrLoginname() + " | " + anUser.getUsrFirstname() + " ,"
                + anUser.getUsrLastname();
        String title = Labels.getLabel("message.Deleting.Record");

        MultiLineMessageBox.doSetTemplate();
        if (MultiLineMessageBox.show(msg, title, MultiLineMessageBox.YES | MultiLineMessageBox.NO, MultiLineMessageBox.QUESTION, true, new EventListener() {
            @Override
            public void onEvent(Event evt) {
                switch (((Integer) evt.getData()).intValue()) {
                    case MultiLineMessageBox.YES:
                        deleteUser();
                    case MultiLineMessageBox.NO:
                        break; //
                }
            }

            private void deleteUser() {

                /**
                 * Prevent the deleting of the demo users
                 */
                try {
                    if (anUser.getId() <= 14 && anUser.getId() >= 10) {
                        ZksampleMessageUtils.doShowNotAllowedForDemoRecords();
                        return;
                    } else {

                        // delete from database
                        try {
                            getUserService().delete(anUser);
                        } catch (DataAccessException e) {
                            ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
                        }
                    }
                } catch (final Exception e) {
                }

                modifUserDialogWindow.onClose(); // close
            }
        }

        ) == MultiLineMessageBox.YES) {
        }

    }

    /**
     * Saves the components to table. <br>
     *
     * @throws InterruptedException
     */
    public void doSave() throws InterruptedException {
        // save it to database
        try {
            getUserService().saveOrUpdate(getSelectedSecUser());
        } catch (DataAccessException e) {
            ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

            return;
        }
    }
    
  
    

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    // ++++++++++++++++++ getter / setter +++++++++++++++++++//
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//

    public UserService getUserService() {
        return this.userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

	public SecUser getSelectedSecUser() {
		return selectedSecUser;
	}

	public void setSelectedSecUser(SecUser selectedSecUser) {
		this.selectedSecUser = selectedSecUser;
	}

}