package solusi.hapis.webui.finance.P04ParamKomisi;


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

import solusi.hapis.backend.navbi.model.P04ParamKomisi;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 13-04-2018
 */

public class P04ParamKomisiDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowP04ParamKomisiDetail;
	
	protected Borderlayout borderlayout_P04ParamKomisiDetail;
	
	// Data Entry Component
	protected Textbox txtFinance;
	protected Textbox txtFinance2;
	protected Textbox txtFinance3;
	protected Textbox txtFinance4;
	protected Textbox txtFinance5;
	protected Textbox txtSpvFinance;

	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private P04ParamKomisiMainCtrl P04ParamKomisiMainCtrl;
	
	/**
	 * default constructor.<br>
	 */
	public P04ParamKomisiDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setP04ParamKomisiMainCtrl((P04ParamKomisiMainCtrl) arg
					.get("ModuleMainController"));

			getP04ParamKomisiMainCtrl().setP04ParamKomisiDetailCtrl(this);
		}
		
		windowP04ParamKomisiDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowP04ParamKomisiDetail(Event event) throws Exception {
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

		borderlayout_P04ParamKomisiDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowP04ParamKomisiDetail.invalidate();
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
				txtFinance3.setReadonly(false);
				txtFinance4.setReadonly(false);
				txtFinance5.setReadonly(false);
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
				getP04ParamKomisiMainCtrl().onClick$btnSave(event);
			}
		};
    }
	
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public P04ParamKomisi getP04ParamKomisi() {
		return getP04ParamKomisiMainCtrl().getSelectedP04ParamKomisi();
	}

	public void setP04ParamKomisi(P04ParamKomisi selectedP04ParamKomisi) {
		getP04ParamKomisiMainCtrl().setSelectedP04ParamKomisi(selectedP04ParamKomisi);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setP04ParamKomisiMainCtrl(P04ParamKomisiMainCtrl P04ParamKomisiMainCtrl) {
		this.P04ParamKomisiMainCtrl = P04ParamKomisiMainCtrl;
	}

	public P04ParamKomisiMainCtrl getP04ParamKomisiMainCtrl() {
		return this.P04ParamKomisiMainCtrl;
	}
}
