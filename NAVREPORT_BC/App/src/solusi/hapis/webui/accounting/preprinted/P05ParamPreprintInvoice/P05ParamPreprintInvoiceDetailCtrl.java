package solusi.hapis.webui.accounting.preprinted.P05ParamPreprintInvoice;

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

import solusi.hapis.backend.navbi.model.P05ParamPreprintInvoice;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 13-04-2018
 */

public class P05ParamPreprintInvoiceDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowP05ParamPreprintInvoiceDetail;
	
	protected Borderlayout borderlayout_P05ParamPreprintInvoiceDetail;
	
	// Data Entry Component
	protected Textbox txtEmailFinance;

	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private P05ParamPreprintInvoiceMainCtrl P05ParamPreprintInvoiceMainCtrl;
	
	/**
	 * default constructor.<br>
	 */
	public P05ParamPreprintInvoiceDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setP05ParamPreprintInvoiceMainCtrl((P05ParamPreprintInvoiceMainCtrl) arg
					.get("ModuleMainController"));

			getP05ParamPreprintInvoiceMainCtrl().setP05ParamPreprintInvoiceDetailCtrl(this);
		}
		
		windowP05ParamPreprintInvoiceDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowP05ParamPreprintInvoiceDetail(Event event) throws Exception {
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

		borderlayout_P05ParamPreprintInvoiceDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowP05ParamPreprintInvoiceDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				
			}
			
			if(pMode.equals("New")){
							
			}
			
			if(pMode.equals("Edit")){				
				
				// set read only other
				txtEmailFinance.setReadonly(false);
			

				// set focus 
				txtEmailFinance.setFocus(true);				
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
				getP05ParamPreprintInvoiceMainCtrl().onClick$btnSave(event);
			}
		};
    }
	
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public P05ParamPreprintInvoice getP05ParamPreprintInvoice() {
		return getP05ParamPreprintInvoiceMainCtrl().getSelectedP05ParamPreprintInvoice();
	}

	public void setP05ParamPreprintInvoice(P05ParamPreprintInvoice selectedP05ParamPreprintInvoice) {
		getP05ParamPreprintInvoiceMainCtrl().setSelectedP05ParamPreprintInvoice(selectedP05ParamPreprintInvoice);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setP05ParamPreprintInvoiceMainCtrl(P05ParamPreprintInvoiceMainCtrl P05ParamPreprintInvoiceMainCtrl) {
		this.P05ParamPreprintInvoiceMainCtrl = P05ParamPreprintInvoiceMainCtrl;
	}

	public P05ParamPreprintInvoiceMainCtrl getP05ParamPreprintInvoiceMainCtrl() {
		return this.P05ParamPreprintInvoiceMainCtrl;
	}
}
