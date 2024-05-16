package solusi.hapis.webui.ps.T12PsAdjPrice;


import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T12PsAdjPrice;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 28-01-2021
 */

public class T12PsAdjPriceDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT12PsAdjPriceDetail;
	
	protected Borderlayout borderlayout_T12PsAdjPriceDetail;
	
	// Data Entry Component
	
	protected Datebox dbTglBerlaku;
	protected Textbox txtCustNo;	
	protected Textbox txtItemNo;
	protected Textbox txtCurrCode;
	protected Decimalbox dcbPrice;	
	
	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T12PsAdjPriceMainCtrl T12PsAdjPriceMainCtrl;

	protected Listbox list_JenisPayment;
	

	/**
	 * default constructor.<br>
	 */
	public T12PsAdjPriceDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT12PsAdjPriceMainCtrl((T12PsAdjPriceMainCtrl) arg
					.get("ModuleMainController"));

			getT12PsAdjPriceMainCtrl().setT12PsAdjPriceDetailCtrl(this);
		}
		
		windowT12PsAdjPriceDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT12PsAdjPriceDetail(Event event) throws Exception {
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

		borderlayout_T12PsAdjPriceDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT12PsAdjPriceDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){

				dbTglBerlaku.setDisabled(true);				
				txtCustNo.setReadonly(true);		
				txtItemNo.setReadonly(true);
				txtCurrCode.setReadonly(true);
				dcbPrice.setReadonly(true);
				
			}
			
			if(pMode.equals("New")){
				dbTglBerlaku.setDisabled(false);				
				txtCustNo.setReadonly(false);		
				txtItemNo.setReadonly(false);
				txtCurrCode.setReadonly(false);
				dcbPrice.setReadonly(false);	
				
				// set focus 
				dbTglBerlaku.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				
				dbTglBerlaku.setDisabled(false);				
				txtCustNo.setReadonly(false);		
				txtItemNo.setReadonly(false);
				txtCurrCode.setReadonly(false);
				dcbPrice.setReadonly(false);	
				
				// set focus 
				dbTglBerlaku.setFocus(true);					
			}

		}
	}
	

	
	public String validasiBusinessRules(){
		
		
		if(dbTglBerlaku.getValue() == null){
			return "Silahkan masukan Tgl. Berlaku";			
		}
		
		if(txtCustNo.getValue() == null){
			return "Silahkan masukan Customer No.";			
		}
		
		if(txtItemNo.getValue() == null){
			return "Silahkan masukan Item No. (PN)";			
		}
		
		if(txtCurrCode.getValue() == null){
			return "Silahkan masukan Currency Code";			
		}
		
		if(dcbPrice.getValue() == null){
			return "Silahkan masukan Price";			
		}
		
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT12PsAdjPriceMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T12PsAdjPrice getT12PsAdjPrice() {
		return getT12PsAdjPriceMainCtrl().getSelectedT12PsAdjPrice();
	}

	public void setT12PsAdjPrice(T12PsAdjPrice selectedT12PsAdjPrice) {
		getT12PsAdjPriceMainCtrl().setSelectedT12PsAdjPrice(selectedT12PsAdjPrice);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT12PsAdjPriceMainCtrl(T12PsAdjPriceMainCtrl T12PsAdjPriceMainCtrl) {
		this.T12PsAdjPriceMainCtrl = T12PsAdjPriceMainCtrl;
	}

	public T12PsAdjPriceMainCtrl getT12PsAdjPriceMainCtrl() {
		return this.T12PsAdjPriceMainCtrl;
	}
	
	
}
