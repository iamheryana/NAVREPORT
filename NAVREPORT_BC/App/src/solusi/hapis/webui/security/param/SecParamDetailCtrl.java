package solusi.hapis.webui.security.param;



import java.io.Serializable;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.model.SecParam;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 03-09-2013
 */

public class SecParamDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowSecParamDetail;
	
	protected Borderlayout borderlayout_SecParamDetail;
	
	// Data Entry Component
	protected Intbox intWarningDay;
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private SecParamMainCtrl SecParamMainCtrl;
	
	/**
	 * default constructor.<br>
	 */
	public SecParamDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setSecParamMainCtrl((SecParamMainCtrl) arg
					.get("ModuleMainController"));

			getSecParamMainCtrl().setSecParamDetailCtrl(this);
		}
		
		windowSecParamDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowSecParamDetail(Event event) throws Exception {
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

		borderlayout_SecParamDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowSecParamDetail.invalidate();
	}

	

	
	
	public String validasiBusinessRules(){		
		if(CommonUtils.isNotEmpty(intWarningDay.getValue()) == false){
			return "Warning Password "+Labels.getLabel("message.error.mandatory");
		}
		
		
		return null;
	}
	

    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getSecParamMainCtrl().onClick$btnSave(event);
			}
		};
    }
	
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public SecParam getSecParam() {
		return getSecParamMainCtrl().getSelectedSecParam();
	}

	public void setSecParam(SecParam selectedSecParam) {
		getSecParamMainCtrl().setSelectedSecParam(selectedSecParam);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setSecParamMainCtrl(SecParamMainCtrl SecParamMainCtrl) {
		this.SecParamMainCtrl = SecParamMainCtrl;
	}

	public SecParamMainCtrl getSecParamMainCtrl() {
		return this.SecParamMainCtrl;
	}
}
