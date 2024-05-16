package solusi.hapis.webui.general.pembelian.T20PiVendor;


import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T20PiVendor;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 02-07-2021
 */

public class T20PiVendorDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT20PiVendorDetail;
	
	protected Borderlayout borderlayout_T20PiVendorDetail;
	
	// Data Entry Component
	
	
	
	protected Datebox dbTglMulai;
	protected Textbox txtPrincipalCode;
	protected Textbox txtVendorCode;
	
	protected Textbox txtBerlaku;
	protected Checkbox chbBerlaku;	
	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T20PiVendorMainCtrl T20PiVendorMainCtrl;

	

	/**
	 * default constructor.<br>
	 */
	public T20PiVendorDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT20PiVendorMainCtrl((T20PiVendorMainCtrl) arg
					.get("ModuleMainController"));

			getT20PiVendorMainCtrl().setT20PiVendorDetailCtrl(this);
		}
		
		windowT20PiVendorDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT20PiVendorDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		
		doFitSize(event);
	}

	public void doRenderCombo() {
		T20PiVendor anT19 = getT20PiVendor();

		

		if (anT19 != null) {
			if(CommonUtils.isNotEmpty(anT19.getBerlaku()) == true){
				if(anT19.getBerlaku().equals("Y") == true)
					chbBerlaku.setChecked(true);
				else
					chbBerlaku.setChecked(false);
			} else {
				chbBerlaku.setChecked(false);
			}
			
		}

	}
	
	public void onCheck$chbBerlaku(Event event) throws Exception {
		T20PiVendor data = getT20PiVendor();
		data.setBerlaku(chbBerlaku.isChecked()?"Y":"N");
		
		txtBerlaku.setValue(data.getBerlaku());
		
		setT20PiVendor(data);
	}
	
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T20PiVendorDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT20PiVendorDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){		
				dbTglMulai.setDisabled(true);					
				txtPrincipalCode.setReadonly(true);	
				txtVendorCode.setReadonly(true);	
							
				txtBerlaku.setReadonly(true);
				chbBerlaku.setDisabled(true);	
			}
			
			if(pMode.equals("New")){
				
				dbTglMulai.setDisabled(false);					
				txtPrincipalCode.setReadonly(false);	
				txtVendorCode.setReadonly(false);	
							
				txtBerlaku.setReadonly(false);
				chbBerlaku.setDisabled(false);
				
				// set focus 
				dbTglMulai.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				

				dbTglMulai.setDisabled(false);					
				txtPrincipalCode.setReadonly(false);	
				txtVendorCode.setReadonly(false);	
							
				txtBerlaku.setReadonly(false);
				chbBerlaku.setDisabled(false);
				
				// set focus 
				dbTglMulai.setFocus(true);						
			}

		}
	}
	

	
	public String validasiBusinessRules(){
		
		if(dbTglMulai.getValue() == null){
			return "Silahkan masukan Tgl. Mulai";			
		}
		
	
		if(CommonUtils.isNotEmpty(txtPrincipalCode.getValue()) == false){
			return "Silahkan masukan Principal Code";	
		}

		if(CommonUtils.isNotEmpty(txtVendorCode.getValue()) == false){
			return "Silahkan masukan Vendor Code";			
		}
		
		
			
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT20PiVendorMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T20PiVendor getT20PiVendor() {
		return getT20PiVendorMainCtrl().getSelectedT20PiVendor();
	}

	public void setT20PiVendor(T20PiVendor selectedT20PiVendor) {
		getT20PiVendorMainCtrl().setSelectedT20PiVendor(selectedT20PiVendor);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT20PiVendorMainCtrl(T20PiVendorMainCtrl T20PiVendorMainCtrl) {
		this.T20PiVendorMainCtrl = T20PiVendorMainCtrl;
	}

	public T20PiVendorMainCtrl getT20PiVendorMainCtrl() {
		return this.T20PiVendorMainCtrl;
	}
	
	
}
