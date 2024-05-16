package solusi.hapis.webui.finance.P01BiayaTrans;

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

import solusi.hapis.backend.navbi.model.P01BiayaTrans;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 30-01-2020
 */

public class P01BiayaTransDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowP01BiayaTransDetail;
	
	protected Borderlayout borderlayout_P01BiayaTransDetail;
	
	// Data Entry Component
	protected Textbox txtNamaPIC;
	protected Textbox txtHpPIC;
	protected Textbox txtBerita;

	protected Decimalbox dcmProvisiUSDtoUSD;
	protected Decimalbox dcmProvisiIDRtoUSD;
	protected Decimalbox dcmProvisiIDRtoEUR;
	protected Decimalbox dcmChargeUSDtoUSD;
	protected Decimalbox dcmChargeIDRtoUSD;	
	protected Decimalbox dcmChargeIDRtoEUR;	
	protected Decimalbox dcmChargeIDRtoCNY;	
	
	protected Decimalbox dcmProvisiIDRtoIDRNonBCABawah;
	protected Decimalbox dcmProvisiIDRtoIDRNonBCAAtas;
	
	
	protected Decimalbox dcmChargeIdrBcaLk;
	protected Decimalbox dcmChargeIdrBcaVa;
	
	
	protected Decimalbox dcmChargeMandiriBawah;
	protected Decimalbox dcmChargeMandiriAtas;

	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private P01BiayaTransMainCtrl P01BiayaTransMainCtrl;
	
	/**
	 * default constructor.<br>
	 */
	public P01BiayaTransDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setP01BiayaTransMainCtrl((P01BiayaTransMainCtrl) arg
					.get("ModuleMainController"));

			getP01BiayaTransMainCtrl().setP01BiayaTransDetailCtrl(this);
		}
		
		windowP01BiayaTransDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowP01BiayaTransDetail(Event event) throws Exception {
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

		borderlayout_P01BiayaTransDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowP01BiayaTransDetail.invalidate();
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
				dcmProvisiIDRtoEUR.setReadonly(false);
				dcmChargeUSDtoUSD.setReadonly(false);
				dcmChargeIDRtoUSD.setReadonly(false);
				dcmChargeIDRtoEUR.setReadonly(false);
				dcmChargeIDRtoCNY.setReadonly(false);
				dcmProvisiIDRtoIDRNonBCABawah.setReadonly(false);
				dcmProvisiIDRtoIDRNonBCAAtas.setReadonly(false);

				dcmChargeIdrBcaLk.setReadonly(false);
				dcmChargeIdrBcaVa.setReadonly(false);
				
				
				dcmChargeMandiriBawah.setReadonly(false);
				dcmChargeMandiriAtas.setReadonly(false);
				
				
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
				getP01BiayaTransMainCtrl().onClick$btnSave(event);
			}
		};
    }
	
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public P01BiayaTrans getP01BiayaTrans() {
		return getP01BiayaTransMainCtrl().getSelectedP01BiayaTrans();
	}

	public void setP01BiayaTrans(P01BiayaTrans selectedP01BiayaTrans) {
		getP01BiayaTransMainCtrl().setSelectedP01BiayaTrans(selectedP01BiayaTrans);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setP01BiayaTransMainCtrl(P01BiayaTransMainCtrl P01BiayaTransMainCtrl) {
		this.P01BiayaTransMainCtrl = P01BiayaTransMainCtrl;
	}

	public P01BiayaTransMainCtrl getP01BiayaTransMainCtrl() {
		return this.P01BiayaTransMainCtrl;
	}
}
