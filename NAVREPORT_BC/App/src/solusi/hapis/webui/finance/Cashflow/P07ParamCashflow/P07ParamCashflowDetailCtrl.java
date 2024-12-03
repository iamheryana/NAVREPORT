package solusi.hapis.webui.finance.Cashflow.P07ParamCashflow;


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
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.P07ParamCashflow;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 25-11-2024
 */

public class P07ParamCashflowDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowP07ParamCashflowDetail;
	
	protected Borderlayout borderlayout_P07ParamCashflowDetail;
	
	// Data Entry Component

	Decimalbox dcmKursUSD;
	Decimalbox dcmKursCNY;
	Decimalbox dcmKursEUR;
	Decimalbox dcmKursSGD;
	
	Decimalbox dcmPIBAJ;
	Decimalbox dcmPIBSP;
	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private P07ParamCashflowMainCtrl P07ParamCashflowMainCtrl;
	
	/**
	 * default constructor.<br>
	 */
	public P07ParamCashflowDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setP07ParamCashflowMainCtrl((P07ParamCashflowMainCtrl) arg
					.get("ModuleMainController"));

			getP07ParamCashflowMainCtrl().setP07ParamCashflowDetailCtrl(this);
		}
		
		windowP07ParamCashflowDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowP07ParamCashflowDetail(Event event) throws Exception {
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

		borderlayout_P07ParamCashflowDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowP07ParamCashflowDetail.invalidate();
	}
	
	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				
			}
			
			if(pMode.equals("New")){
							
			}
			
			if(pMode.equals("Edit")){				
				
				// set read only other

				
				dcmKursUSD.setReadonly(false);
				dcmKursCNY.setReadonly(false);
				dcmKursEUR.setReadonly(false);
				dcmKursSGD.setReadonly(false);
				
				dcmPIBAJ.setReadonly(false);
				dcmPIBSP.setReadonly(false);
				
				// set focus 
				//txtEmailFinance.setFocus(true);				
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
				getP07ParamCashflowMainCtrl().onClick$btnSave(event);
			}
		};
    }
	
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public P07ParamCashflow getP07ParamCashflow() {
		return getP07ParamCashflowMainCtrl().getSelectedP07ParamCashflow();
	}

	public void setP07ParamCashflow(P07ParamCashflow selectedP07ParamCashflow) {
		getP07ParamCashflowMainCtrl().setSelectedP07ParamCashflow(selectedP07ParamCashflow);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setP07ParamCashflowMainCtrl(P07ParamCashflowMainCtrl P07ParamCashflowMainCtrl) {
		this.P07ParamCashflowMainCtrl = P07ParamCashflowMainCtrl;
	}

	public P07ParamCashflowMainCtrl getP07ParamCashflowMainCtrl() {
		return this.P07ParamCashflowMainCtrl;
	}
}
