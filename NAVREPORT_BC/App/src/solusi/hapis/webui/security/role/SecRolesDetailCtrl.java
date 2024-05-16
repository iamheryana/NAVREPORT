package solusi.hapis.webui.security.role;

 
import java.io.Serializable;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecRole;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;



public class SecRolesDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowsecRolesDetail;
	
	protected Borderlayout borderlayout_secRolesDetail;
	
	// Data Entry Component
	protected Textbox txt_Short;
	protected Textbox txt_Long;
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private SecRolesMainCtrl SecRolesMainCtrl;
	
	/**
	 * default constructor.<br>
	 */
	public SecRolesDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setSecRolesMainCtrl((SecRolesMainCtrl) arg
					.get("ModuleMainController"));

			getSecRolesMainCtrl().setSecRolesDetailCtrl(this);
		}
		
		windowsecRolesDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowsecRolesDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		doFitSize(event);
	}

	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_secRolesDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowsecRolesDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				txt_Short.setReadonly(true);
				txt_Long.setReadonly(true);
			}
			
			if(pMode.equals("New")){
				txt_Short.setReadonly(false);
				txt_Long.setReadonly(false);
				
				// set focus 
				txt_Short.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				
				// set read only key
				txt_Short.setReadonly(true);
				
				// set read only other
				txt_Long.setReadonly(false);
				
				// set focus 
				txt_Long.setFocus(true);				
			}
		}
	}
	
	
	public String validasiBusinessRules(){		
		if(CommonUtils.isNotEmpty(txt_Short.getValue()) == false){
			return "Role Name "+Labels.getLabel("message.error.mandatory");
		}
		
		if(CommonUtils.isNotEmpty(txt_Long.getValue()) == false){
			return "Description "+Labels.getLabel("message.error.mandatory");
		}
		
	
		return null;
	}
	

    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getSecRolesMainCtrl().onClick$btnSave(event);
			}
		};
    }
	
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public SecRole getSecRole() {
		return getSecRolesMainCtrl().getSelectedSecRole();
	}

	public void setSecRole(SecRole selectedsecRoles) {
		getSecRolesMainCtrl().setSelectedSecRole(selectedsecRoles);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setSecRolesMainCtrl(SecRolesMainCtrl SecRolesMainCtrl) {
		this.SecRolesMainCtrl = SecRolesMainCtrl;
	}

	public SecRolesMainCtrl getSecRolesMainCtrl() {
		return this.SecRolesMainCtrl;
	}
}
