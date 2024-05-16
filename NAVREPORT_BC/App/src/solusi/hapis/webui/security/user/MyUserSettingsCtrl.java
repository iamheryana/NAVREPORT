/**
 * Copyright 2010 the original author or authors.
 *
 * This file is part of openTruuls™. http://www.opentruuls.org/
 *
 * openTruuls™ community edition is free software: you can 
 * redistribute it and/or modify it under the terms of the 
 * openTruuls™ public License which is based on the Apache 
 * License 2.0 from the Apache Foundation. The only permission
 * for using openTruuls™ is that a working as a SaaS solution
 * in a mass hosting way is not allowed without approval from the 
 * name holder of openTruuls™.  
 *
 * openTruuls™ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * openTruuls™ public License for more details.
 *
 * You should have received a copy of the openTruuls™ public License
 * along with openTruuls™.  If not, see <http://www.opentruuls.org/licenses/>.
 */

/**
 * Copyright 2010 the original author or authors.
 *
 * This file is part of openTruuls™. http://www.opentruuls.org/
 *
 * openTruuls™ community edition is free software: you can 
 * redistribute it and/or modify it under the terms of the 
 * openTruuls™ public License which is based on the Apache 
 * License 2.0 from the Apache Foundation. The only permission
 * for using openTruuls™ is that a working as a SaaS solution
 * in a mass hosting way is not allowed without approval from the 
 * name holder of openTruuls™.  
 *
 * openTruuls™ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * openTruuls™ public License for more details.
 *
 * You should have received a copy of the openTruuls™ public License
 * along with openTruuls™.  If not, see <http://www.opentruuls.org/licenses/>.
 */
package solusi.hapis.webui.security.user;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecLog;
import solusi.hapis.backend.model.SecUser;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.backend.security.service.UserService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.policy.model.UserImpl;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * EN: Controller for the <b>My User Settings</b>.<br>
 * For checking the re-typed password we use an own local bean Pwd with a
 * property 'retypePassword' that is filled at start with the users password.<br>
 * DE: Controller fuer das Modul <b>User Einstellungen</b>. <br>
 * Um das re-typed Passwort zu pruefen, verwenden wir eine eigene locale Klasse
 * Pwd deren property 'retypePassword' beim Start gefuellt wird.<br>
 * <br>
 * zul-file: /WEB-INF/pages/security/mySettings/mySettings.zul.<br>
 * <br>
 *
 * @author Stephan Gerth
 */
public class MyUserSettingsCtrl extends GFCBaseCtrl implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */

    protected Window windowMyUserSettings; // autowired

    // Button controller for the CRUD buttons
    protected Button btnSave; // autowire
    protected Button btnClose; // autowire

    // ServiceDAOs / Domain Classes
    private transient UserService userService;

    // databinding
    protected transient AnnotateDataBinder binder;
    
    private SecUser selectedUser;

    
    protected Textbox txtb_UserLoginname;
    protected Textbox txtb_UserPassword;
    protected Textbox txtb_UserPasswordRetype;
    protected Textbox txtb_UserPasswordOld;
    
    protected Combobox cmb_masaBerlaku;

    private SecurityService securityService = (SecurityService) SpringUtil.getBean("securityService");
	

    /**
     * default constructor.<br>
     */
    public MyUserSettingsCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        /**
         * Set an 'alias' for this composer name in the zul file for access.<br>
         * Set the parameter 'recurse' to 'false' to avoid problems with
         * managing more than one zul-file in one page. Otherwise it would be
         * overridden and can ends in curious error messages.
         */
        this.self.setAttribute("controller", this, false);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++ Component Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * Automatically called method from zk.
     *
     * @param event
     * @throws Exception
     */
    public void onCreate$windowMyUserSettings(Event event) throws Exception {
        binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);

        // Get the logged in user
        SecUser anUser = ((UserImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSecUser();

        if (anUser != null) {
            setSelectedUser(anUser);

        }

        binder.loadAll();

        windowMyUserSettings.doModal();

    }

    /**
     * When the "save" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnSave(Event event) throws InterruptedException {
    	getBinder().saveAll();
    	

    	SecUser usrOld = getUserService().getUserByLoginname(getSelectedUser().getUsrLoginname());
    	String passOld = ".";
    	if(usrOld != null){
    		passOld = usrOld.getUsrPassword();
    	}
    	
    	PasswordEncoder pe = new ShaPasswordEncoder();
    	
    	
    	
      	String passOldEntry = ".";    	
    	if (CommonUtils.isNotEmpty(txtb_UserPasswordOld.getValue())){
    		passOldEntry = txtb_UserPasswordOld.getValue();
    	
    		passOldEntry = pe.encodePassword(passOldEntry, getSelectedUser().getUsrLoginname());
    	} else {
    		throw new WrongValueException(txtb_UserPasswordOld, "Old Password " +Labels.getLabel("message.error.mandatory"));
    	}
    	
    	String passNew = ".";
    	String passNewNonEncryp = ".";
    	if (CommonUtils.isNotEmpty(txtb_UserPassword.getValue())){
    		passNewNonEncryp = txtb_UserPassword.getValue();
    	
    		passNew = pe.encodePassword(passNewNonEncryp, getSelectedUser().getUsrLoginname());
    	} else {
    		throw new WrongValueException(txtb_UserPassword, "New Password " +Labels.getLabel("message.error.mandatory"));
    	}
    	
    	String passRetype = ".";
    	if (CommonUtils.isNotEmpty(txtb_UserPasswordRetype.getValue())){
    		passRetype = txtb_UserPasswordRetype.getValue();
    	
    		passRetype = pe.encodePassword(passRetype, getSelectedUser().getUsrLoginname());
    	}  else {
    		throw new WrongValueException(txtb_UserPasswordRetype, "Retype Password " +Labels.getLabel("message.error.mandatory"));
    	}
    	
    	int masaBerlaku = 0;
    	if(cmb_masaBerlaku.getSelectedIndex() >= 0){
    		masaBerlaku = cmb_masaBerlaku.getSelectedIndex()+1;
    	} else {
    		throw new WrongValueException(cmb_masaBerlaku, "Masa Berlaku Password " +Labels.getLabel("message.error.mandatory"));
    	}
    	
    	//Cek apakah Passwod lama yang di masukan benar
    	if(StringUtils.equals(passOldEntry, passOld) == false){
    		throw new WrongValueException(txtb_UserPasswordOld, Labels.getLabel("message.error.OldPasswordInvalid"));
    	} else {
    	
	    	// Cek apakah Passwod baru sama dengan password yang lama
	    	if(StringUtils.equals(passNew, passOld)){
	    		throw new WrongValueException(txtb_UserPassword, Labels.getLabel("message.error.SamePassword"));
	    	    
	    	} else {
	    	
		    	// Cek Format Password
		    	if(isPasswordValid(passNewNonEncryp) == false){
		    		throw new WrongValueException(txtb_UserPassword, Labels.getLabel("message.error.FormatPassword"));
		    	} else {
		    		// check if Pasword equals ReTyped password
		            if (!StringUtils.equals(passNew, passRetype)) {
		                throw new WrongValueException(txtb_UserPasswordRetype, Labels.getLabel("message.error.RetypedPasswordMustBeSame"));
		            } else {
		            	getSelectedUser().setUsrPassword(passNew);
		            	
		            	Date expiredDate = new Date();	            	
		            	Calendar calendar = Calendar.getInstance();
		        		calendar.setTime(expiredDate);
		            	if(cmb_masaBerlaku.getSelectedIndex() >= 0){
		            		calendar.set(Calendar.HOUR_OF_DAY, 0);
			        		calendar.set(Calendar.MINUTE, 0);
			        		calendar.set(Calendar.SECOND, 0);
			        		calendar.set(Calendar.MILLISECOND, 0);
			        		calendar.add(Calendar.MONTH, masaBerlaku);
			        		
			        		getSelectedUser().setExpiredDate(calendar.getTime());
		            	}
		            	
		            	
		            }
		    	}
	    	}
    	}
    	
        

        try {
        	
        	SecLog newLog = new SecLog("Change Password", getSelectedUser().getUsrLoginname(), new Date(), CommonUtils.convertClientAddress(SecurityContextHolder.getContext().getAuthentication()));
			securityService.save(newLog);
        	
            // save it
            getUserService().saveOrUpdate(getSelectedUser());
                   
            windowMyUserSettings.onClose();
        } catch (Exception e) {
            ZksampleMessageUtils.showErrorMessage(e.getMessage());
        }
    }
    
    private boolean isPasswordValid (String password){
    	int countNumber = 0;
    	
    	if(password != null){
    		if(password.length() < 6){
    			return false;
    		} else {
    			for (int j = 0; j < password.length(); j++) {
				        if (	password.charAt(j) == '0' || password.charAt(j) == '1' || 
				        		password.charAt(j) == '2' || password.charAt(j) == '3' || 
				        		password.charAt(j) == '4' || password.charAt(j) == '5' || 
				        		password.charAt(j) == '6' || password.charAt(j) == '7' || 
				        		password.charAt(j) == '8' || password.charAt(j) == '9') {
				        	countNumber++;
				        }
    			}
    		    	
    			if (countNumber == 0 || countNumber == password.length()) {
    		        	return false;
    			} else {
    				return true;
    			}
    		}
    	} else {    	
        	return false;
        }
    }

    /**
     * When the "close" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnClose(Event event) throws InterruptedException {
    	windowMyUserSettings.onClose();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * Set all components in readOnly/disabled modus.
     * <p/>
     * true = all components are readOnly or disabled.<br>
     * false = all components are accessable.<br>
     *
     * @param b
     */
    public void doReadOnlyMode(boolean b) {
    	txtb_UserPassword.setReadonly(b);
        txtb_UserPasswordRetype.setReadonly(b);

    }

	

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setSelectedUser(SecUser selectedUser) {
        this.selectedUser = selectedUser;
    }

    public SecUser getSelectedUser() {
        return selectedUser;
    }

    public AnnotateDataBinder getBinder() {
        return binder;
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }


}
