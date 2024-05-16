package solusi.hapis.webui.general.pembelian.BCUserLocation;


import java.io.Serializable;

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

import solusi.hapis.backend.navbi.model.BCUserLocation;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 07-09-2023
 */

public class BCUserLocationDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowBCUserLocationDetail;
	
	protected Borderlayout borderlayout_BCUserLocationDetail;
	
	// Data Entry Component
	
	

	protected Textbox txtUserId;
	protected Textbox txtBranches;
	protected Textbox txtGroupName;

	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private BCUserLocationMainCtrl BCUserLocationMainCtrl;

	

	/**
	 * default constructor.<br>
	 */
	public BCUserLocationDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setBCUserLocationMainCtrl((BCUserLocationMainCtrl) arg
					.get("ModuleMainController"));

			getBCUserLocationMainCtrl().setBCUserLocationDetailCtrl(this);
		}
		
		windowBCUserLocationDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowBCUserLocationDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		
		doFitSize(event);
	}

	public void doRenderCombo() {
//		BCUserLocation anT19 = getBCUserLocation();
//
//		
//
//		if (anT19 != null) {
//			if(CommonUtils.isNotEmpty(anT19.getBerlaku()) == true){
//				if(anT19.getBerlaku().equals("Y") == true)
//					chbBerlaku.setChecked(true);
//				else
//					chbBerlaku.setChecked(false);
//			} else {
//				chbBerlaku.setChecked(false);
//			}
//			
//		}

	}
	
//	public void onCheck$chbBerlaku(Event event) throws Exception {
//		BCUserLocation data = getBCUserLocation();
//		data.setBerlaku(chbBerlaku.isChecked()?"Y":"N");
//		
//		txtBerlaku.setValue(data.getBerlaku());
//		
//		setBCUserLocation(data);
//	}
//	
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_BCUserLocationDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowBCUserLocationDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){		
								
				txtUserId.setReadonly(true);	
				txtBranches.setReadonly(true);	
				txtGroupName.setReadonly(true);	
							

			}
			
			if(pMode.equals("New")){
				
				txtUserId.setReadonly(false);					
				txtBranches.setReadonly(false);	
				txtGroupName.setReadonly(false);	
				
				
				// set focus 
				txtUserId.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				

				txtUserId.setReadonly(true);					
				txtBranches.setReadonly(false);	
				txtGroupName.setReadonly(false);	
				
				
				// set focus 
				txtBranches.setFocus(true);						
			}

		}
	}
	

	
	public String validasiBusinessRules(){
		
		
		if(CommonUtils.isNotEmpty(txtUserId.getValue()) == false){
			return "Silahkan masukan User Id";			
		}
		
		if(CommonUtils.isNotEmpty(txtBranches.getValue()) == false){
			return "Silahkan masukan Branches";			
		}
		
		
		
			
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getBCUserLocationMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public BCUserLocation getBCUserLocation() {
		return getBCUserLocationMainCtrl().getSelectedBCUserLocation();
	}

	public void setBCUserLocation(BCUserLocation selectedBCUserLocation) {
		getBCUserLocationMainCtrl().setSelectedBCUserLocation(selectedBCUserLocation);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setBCUserLocationMainCtrl(BCUserLocationMainCtrl BCUserLocationMainCtrl) {
		this.BCUserLocationMainCtrl = BCUserLocationMainCtrl;
	}

	public BCUserLocationMainCtrl getBCUserLocationMainCtrl() {
		return this.BCUserLocationMainCtrl;
	}
	
	
}
