package solusi.hapis.webui.tabel.T04paramSlipTransferBCA;


import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.tabel.model.T04paramKomisi;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 05-12-2019
 */

public class T04paramSlipTransferBCADetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT04paramSlipTransferBCADetail;
	
	protected Borderlayout borderlayout_T04paramSlipTransferBCADetail;
	
	// Data Entry Component
	protected Textbox txtNamaPIC;
	protected Textbox txtHpPIC;
	protected Textbox txtBerita;

	protected Decimalbox dcmProvisiUSDtoUSD;
	protected Decimalbox dcmProvisiIDRtoUSD;
	protected Decimalbox dcmChargeUSDtoUSD;
	protected Decimalbox dcmChargeIDRtoUSD;	
	protected Decimalbox dcmProvisiIDRtoIDRNonBCABawah;
	protected Decimalbox dcmProvisiIDRtoIDRNonBCAAtas;
	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T04paramSlipTransferBCAMainCtrl T04paramSlipTransferBCAMainCtrl;
	
	/**
	 * default constructor.<br>
	 */
	public T04paramSlipTransferBCADetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT04paramSlipTransferBCAMainCtrl((T04paramSlipTransferBCAMainCtrl) arg
					.get("ModuleMainController"));

			getT04paramSlipTransferBCAMainCtrl().setT04paramSlipTransferBCADetailCtrl(this);
		}
		
		windowT04paramSlipTransferBCADetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT04paramSlipTransferBCADetail(Event event) throws Exception {
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

		borderlayout_T04paramSlipTransferBCADetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT04paramSlipTransferBCADetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				
			}
			
			if(pMode.equals("New")){
							
			}
			
			if(pMode.equals("Edit")){				
			
				// set read only other
				txtNamaPIC.setReadonly(false);
				txtHpPIC.setReadonly(false);
				txtBerita.setReadonly(false);
				
				dcmProvisiUSDtoUSD.setReadonly(false);
				dcmProvisiIDRtoUSD.setReadonly(false);
				dcmChargeUSDtoUSD.setReadonly(false);
				dcmChargeIDRtoUSD.setReadonly(false);
				dcmProvisiIDRtoIDRNonBCABawah.setReadonly(false);
				dcmProvisiIDRtoIDRNonBCAAtas.setReadonly(false);

				// set focus 
				txtNamaPIC.setFocus(true);				
			}
		}
	}
	
	
	public void doRenderCheckBox(){

	}
	
	
	
	public String validasiBusinessRules(){		

		return null;
	}
	

    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT04paramSlipTransferBCAMainCtrl().onClick$btnSave(event);
			}
		};
    }
	
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T04paramKomisi getT04paramKomisi() {
		return getT04paramSlipTransferBCAMainCtrl().getSelectedT04paramKomisi();
	}

	public void setT04paramKomisi(T04paramKomisi selectedT04paramKomisi) {
		getT04paramSlipTransferBCAMainCtrl().setSelectedT04paramKomisi(selectedT04paramKomisi);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT04paramSlipTransferBCAMainCtrl(T04paramSlipTransferBCAMainCtrl T04paramSlipTransferBCAMainCtrl) {
		this.T04paramSlipTransferBCAMainCtrl = T04paramSlipTransferBCAMainCtrl;
	}

	public T04paramSlipTransferBCAMainCtrl getT04paramSlipTransferBCAMainCtrl() {
		return this.T04paramSlipTransferBCAMainCtrl;
	}
}
