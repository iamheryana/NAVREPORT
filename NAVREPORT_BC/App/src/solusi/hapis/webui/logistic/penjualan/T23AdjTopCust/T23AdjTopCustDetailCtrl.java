package solusi.hapis.webui.logistic.penjualan.T23AdjTopCust;



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

import solusi.hapis.backend.navbi.model.T23AdjTopCust;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 29-08-2022
 */

public class T23AdjTopCustDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT23AdjTopCustDetail;
	
	protected Borderlayout borderlayout_T23AdjTopCustDetail;
	
	// Data Entry Component
	

	protected Textbox txtCustNO;	
	protected Intbox intTopAdj;	

	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T23AdjTopCustMainCtrl T23AdjTopCustMainCtrl;


//	private SelectQueryNavReportService selectQueryNavReportService;
	
	/**
	 * default constructor.<br>
	 */
	public T23AdjTopCustDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT23AdjTopCustMainCtrl((T23AdjTopCustMainCtrl) arg
					.get("ModuleMainController"));

			getT23AdjTopCustMainCtrl().setT23AdjTopCustDetailCtrl(this);
		}
		
		windowT23AdjTopCustDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT23AdjTopCustDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		//doRenderCombo();
		doFitSize(event);
	}


	

	
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T23AdjTopCustDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT23AdjTopCustDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				

				txtCustNO.setReadonly(true);			
				intTopAdj.setReadonly(true);		
				
			}
			
			if(pMode.equals("New")){
				
				txtCustNO.setReadonly(false);				
				intTopAdj.setReadonly(false);		
				
				// set focus 
				txtCustNO.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				
				
				txtCustNO.setReadonly(false);			
				intTopAdj.setReadonly(false);		
				
				// set focus 
				txtCustNO.setFocus(true);					
			}

		}
	}
	

	
	public String validasiBusinessRules(){
		if(txtCustNO.getValue() == null){
			return "Silahkan masukan Customer No./Code";			
		}
		
		
		if(CommonUtils.isNotEmpty(intTopAdj.getValue()) == false){
			return "Silahkan masukan TOP Adjustment";			
		}
		
		
				
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT23AdjTopCustMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T23AdjTopCust getT23AdjTopCust() {
		return getT23AdjTopCustMainCtrl().getSelectedT23AdjTopCust();
	}

	public void setT23AdjTopCust(T23AdjTopCust selectedT23AdjTopCust) {
		getT23AdjTopCustMainCtrl().setSelectedT23AdjTopCust(selectedT23AdjTopCust);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT23AdjTopCustMainCtrl(T23AdjTopCustMainCtrl T23AdjTopCustMainCtrl) {
		this.T23AdjTopCustMainCtrl = T23AdjTopCustMainCtrl;
	}

	public T23AdjTopCustMainCtrl getT23AdjTopCustMainCtrl() {
		return this.T23AdjTopCustMainCtrl;
	}
	
	
}
