package solusi.hapis.webui.general.T15SatindoAdj;


import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T15SatindoAdj;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 28-01-2021
 */

public class T15SatindoAdjDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT15SatindoAdjDetail;
	
	protected Borderlayout borderlayout_T15SatindoAdjDetail;
	
	// Data Entry Component
	
	
	protected Textbox txtNoInvoice;	
	protected Datebox dbOrderDate;
	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T15SatindoAdjMainCtrl T15SatindoAdjMainCtrl;

	

	/**
	 * default constructor.<br>
	 */
	public T15SatindoAdjDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT15SatindoAdjMainCtrl((T15SatindoAdjMainCtrl) arg
					.get("ModuleMainController"));

			getT15SatindoAdjMainCtrl().setT15SatindoAdjDetailCtrl(this);
		}
		
		windowT15SatindoAdjDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT15SatindoAdjDetail(Event event) throws Exception {
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

		borderlayout_T15SatindoAdjDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT15SatindoAdjDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){		
				
				txtNoInvoice.setReadonly(true);	
				dbOrderDate.setDisabled(true);				
				
			}
			
			if(pMode.equals("New")){
				
				txtNoInvoice.setReadonly(false);	
				dbOrderDate.setDisabled(false);	
				
				// set focus 
				txtNoInvoice.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				

				txtNoInvoice.setReadonly(false);	
				dbOrderDate.setDisabled(false);		
				
				// set focus 
				txtNoInvoice.setFocus(true);					
			}

		}
	}
	

	
	public String validasiBusinessRules(){
		
		
		if(txtNoInvoice.getValue() == null){
			return "Silahkan masukan No. Invoice";			
		}
		
		if(dbOrderDate.getValue() == null){
			return "Silahkan masukan Order Date";			
		}
		
			
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT15SatindoAdjMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T15SatindoAdj getT15SatindoAdj() {
		return getT15SatindoAdjMainCtrl().getSelectedT15SatindoAdj();
	}

	public void setT15SatindoAdj(T15SatindoAdj selectedT15SatindoAdj) {
		getT15SatindoAdjMainCtrl().setSelectedT15SatindoAdj(selectedT15SatindoAdj);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT15SatindoAdjMainCtrl(T15SatindoAdjMainCtrl T15SatindoAdjMainCtrl) {
		this.T15SatindoAdjMainCtrl = T15SatindoAdjMainCtrl;
	}

	public T15SatindoAdjMainCtrl getT15SatindoAdjMainCtrl() {
		return this.T15SatindoAdjMainCtrl;
	}
	
	
}
