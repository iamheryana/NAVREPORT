package solusi.hapis.webui.security.user;

import java.io.Serializable;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecUser;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SecUsersDetailCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -314412836744618160L;

	protected Window windowSecUsersDetail;

	protected Groupbox groupbox_listroles;

	protected Textbox txt_Loginname;
	protected Textbox txt_Password;
	protected Textbox txt_FirstName;
	protected Textbox txt_LastName;
	protected Textbox txt_Email;
	
//	protected Textbox txt_AccCabang;
//	protected Textbox txt_AccGolongan;
	
//	protected Checkbox chbAccCabang;
//	protected Checkbox chbAccGolongan;

//	protected Radio rdAccCabangAll;
//	protected Radio rdAccCabangDetail;
//	
//	protected Radio rdAccGolonganAll;
//	protected Radio rdAccGolonganDetail;
	
	protected Row row_password;
	protected Row row_firstName;
	protected Row row_lastName;
	protected Row row_email;




	protected transient AnnotateDataBinder binder;

	private SecUsersMainCtrl secUsersMainCtrl;

	private String requestStatus;

	public SecUsersDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		this.self.setAttribute("controller", this, false);

		if (arg.containsKey("ModuleMainController")) {
			setSecUsersMainCtrl((SecUsersMainCtrl) arg
					.get("ModuleMainController"));
			getSecUsersMainCtrl().setSecUsersDetailCtrl(this);
		}

		windowSecUsersDetail.addEventListener(Events.ON_OK, onEnterForm());
	}



	private EventListener onEnterForm() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				getSecUsersMainCtrl().onClick$btnSave(event);
			}
		};
	}

	public void onCreate$windowSecUsersDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);
		doFillListbox();
		binder.loadAll();
	}

	public void doFillListbox() {
		doFitSize();
	}

	public void doFitSize() {
		windowSecUsersDetail.invalidate();
	}

	public void doRenderMode(String pMode) {
		requestStatus = pMode;
		if (CommonUtils.isNotEmpty(pMode)) {
			if (pMode.equals("View")) {
				txt_Loginname.setReadonly(true);
				txt_FirstName.setReadonly(true);
				txt_LastName.setReadonly(true);
				txt_Email.setReadonly(true);
				
//				chbAccCabang.setDisabled(true);
//				chbAccGolongan.setDisabled(true);
				
				row_password.setVisible(false);
				row_firstName.setVisible(true);
				row_lastName.setVisible(true);
				row_email.setVisible(true);
				
			}

			if (pMode.equals("New")) {
				txt_Loginname.setReadonly(false);
				txt_FirstName.setReadonly(false);
				txt_LastName.setReadonly(false);
				txt_Email.setReadonly(false);
				
//				chbAccCabang.setDisabled(false);
//				chbAccGolongan.setDisabled(false);

				row_password.setVisible(true);
				row_firstName.setVisible(true);
				row_lastName.setVisible(true);
				row_email.setVisible(true);

				// set focus
				txt_Loginname.setFocus(true);
			}

			if (pMode.equals("Edit")) {
				// set read only key
				txt_Loginname.setReadonly(true);

				// set read only other
				txt_FirstName.setReadonly(false);
				txt_LastName.setReadonly(false);
				txt_Email.setReadonly(false);
				
//				chbAccCabang.setDisabled(false);
//				chbAccGolongan.setDisabled(false);

				row_password.setVisible(false);
				row_firstName.setVisible(true);
				row_lastName.setVisible(true);
				row_email.setVisible(true);

				// set focus
				txt_FirstName.setFocus(true);
			}

			if (pMode.equals("Reactivate")) {
				// set read only key
				txt_Loginname.setReadonly(true);

				// set read only other
				txt_FirstName.setReadonly(true);
				txt_LastName.setReadonly(true);
				txt_Email.setReadonly(true);
				
//				chbAccCabang.setDisabled(true);
//				chbAccGolongan.setDisabled(true);

				row_password.setVisible(true);
				row_firstName.setVisible(false);
				row_lastName.setVisible(false);
				row_email.setVisible(false);
			}
		}
	}



	public String validasiBusinessRules() {
		if (CommonUtils.isNotEmpty(txt_Loginname.getValue()) == false) {
			return "Login Name " + Labels.getLabel("message.error.mandatory");
		} else {
			if (requestStatus.equals("New")) {
				String newUser = txt_Loginname.getValue();
				newUser = newUser.trim().toUpperCase();

				SecUser usr = getSecUsersMainCtrl().getUserService()
						.getUserByLoginname(newUser);
				if (usr != null) {
					return "Login Name sudah ada";
				}
			}
		}

		if (CommonUtils.isNotEmpty(txt_FirstName.getValue()) == false) {
			return "First Name " + Labels.getLabel("message.error.mandatory");
		}

		if (CommonUtils.isNotEmpty(txt_LastName.getValue()) == false) {
			return "Last Name " + Labels.getLabel("message.error.mandatory");
		}

		return null;
	}
	
	public void doRenderCheckBox(){
		SecUser usr = getSecUser();
		if (usr != null){
//			if(CommonUtils.isNotEmpty(usr.getAccessCabang()) == true){
//				if(usr.getAccessCabang().equals("A") == true)
//					chbAccCabang.setChecked(true);
//				else
//					chbAccCabang.setChecked(false);
//			} else {
//				chbAccCabang.setChecked(false);
//			}	
//			
//			if(CommonUtils.isNotEmpty(usr.getAccessGolongan()) == true){
//				if(usr.getAccessGolongan().equals("A") == true)
//					chbAccGolongan.setChecked(true);
//				else
//					chbAccGolongan.setChecked(false);
//			} else {
//				chbAccGolongan.setChecked(false);
//			}	
		}
	}
	
	
//	public void onCheck$chbAccCabang(Event event) throws Exception {
//		SecUser usr = getSecUser();
//		usr.setAccessCabang(chbAccCabang.isChecked()?"A":"D");
//		
//		txt_AccCabang.setValue(usr.getAccessCabang());
//		
//		setSecUser(usr);
//	}
//	
//	public void onCheck$chbAccGolongan(Event event) throws Exception {
//		SecUser usr = getSecUser();
//		usr.setAccessGolongan(chbAccGolongan.isChecked()?"A":"D");
//		
//		txt_AccGolongan.setValue(usr.getAccessGolongan());
//		
//		setSecUser(usr);
//	}
	
	public SecUsersMainCtrl getSecUsersMainCtrl() {
		return secUsersMainCtrl;
	}

	public void setSecUsersMainCtrl(SecUsersMainCtrl secUsersMainCtrl) {
		this.secUsersMainCtrl = secUsersMainCtrl;
	}

	public SecUser getSecUser() {
		return secUsersMainCtrl.getSelectedSecUser();
	}

	public void setSecUser(SecUser secUser) {
		secUsersMainCtrl.setSelectedSecUser(secUser);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

}
