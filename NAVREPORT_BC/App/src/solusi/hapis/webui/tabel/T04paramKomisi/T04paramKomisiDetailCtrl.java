package solusi.hapis.webui.tabel.T04paramKomisi;


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

import solusi.hapis.backend.tabel.model.T04paramKomisi;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 03-04-2018
 */

public class T04paramKomisiDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT04paramKomisiDetail;
	
	protected Borderlayout borderlayout_T04paramKomisiDetail;
	
	// Data Entry Component
	protected Textbox txtFinance;
	protected Textbox txtFinance2;
	protected Textbox txtSpvFinance;

	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T04paramKomisiMainCtrl T04paramKomisiMainCtrl;
	
	/**
	 * default constructor.<br>
	 */
	public T04paramKomisiDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT04paramKomisiMainCtrl((T04paramKomisiMainCtrl) arg
					.get("ModuleMainController"));

			getT04paramKomisiMainCtrl().setT04paramKomisiDetailCtrl(this);
		}
		
		windowT04paramKomisiDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT04paramKomisiDetail(Event event) throws Exception {
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

		borderlayout_T04paramKomisiDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT04paramKomisiDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				
			}
			
			if(pMode.equals("New")){
							
			}
			
			if(pMode.equals("Edit")){				
				
				// set read only other
				txtFinance.setReadonly(false);
				txtFinance2.setReadonly(false);
				txtSpvFinance.setReadonly(false);

				// set focus 
				txtFinance.setFocus(true);				
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
				getT04paramKomisiMainCtrl().onClick$btnSave(event);
			}
		};
    }
	
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T04paramKomisi getT04paramKomisi() {
		return getT04paramKomisiMainCtrl().getSelectedT04paramKomisi();
	}

	public void setT04paramKomisi(T04paramKomisi selectedT04paramKomisi) {
		getT04paramKomisiMainCtrl().setSelectedT04paramKomisi(selectedT04paramKomisi);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT04paramKomisiMainCtrl(T04paramKomisiMainCtrl T04paramKomisiMainCtrl) {
		this.T04paramKomisiMainCtrl = T04paramKomisiMainCtrl;
	}

	public T04paramKomisiMainCtrl getT04paramKomisiMainCtrl() {
		return this.T04paramKomisiMainCtrl;
	}
}
