package solusi.hapis.webui.general.crossModule.P06ParamDefaultRpt;

import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.P06ParamDefaultRpt;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 07-10-2024
 */

public class P06ParamDefaultRptDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowP06ParamDefaultRptDetail;
	
	protected Borderlayout borderlayout_P06ParamDefaultRptDetail;
	
	// Data Entry Component
	protected Textbox txtPeridodeKolomCf;
	
	protected Radiogroup rdgJnsRpt;	 
	protected Radio rdN;
	protected Radio rdW;
	protected Radio rdC;
	protected Radio rdM;

	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private P06ParamDefaultRptMainCtrl P06ParamDefaultRptMainCtrl;
	
	/**
	 * default constructor.<br>
	 */
	public P06ParamDefaultRptDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setP06ParamDefaultRptMainCtrl((P06ParamDefaultRptMainCtrl) arg
					.get("ModuleMainController"));

			getP06ParamDefaultRptMainCtrl().setP06ParamDefaultRptDetailCtrl(this);
		}
		
		windowP06ParamDefaultRptDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowP06ParamDefaultRptDetail(Event event) throws Exception {
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

		borderlayout_P06ParamDefaultRptDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowP06ParamDefaultRptDetail.invalidate();
	}
	
	public void onCheck$rdgJnsRpt(Event event){
		String vJnsRpt = "N";
		
		if(rdN.isChecked() == true ) {
			vJnsRpt = "N";
		}
		
		if(rdW.isChecked() == true ) {
			vJnsRpt = "W";
		}
		
		if(rdC.isChecked() == true ) {
			vJnsRpt = "C";
		}
		
		if(rdM.isChecked() == true ) {
			vJnsRpt = "M";
		}
		
		//System.out.println("vJnsRpt : "+vJnsRpt);
		
		txtPeridodeKolomCf.setValue(vJnsRpt);
		getP06ParamDefaultRpt().setPeriodeKolomCf(vJnsRpt);
	}

	
	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				
			}
			
			if(pMode.equals("New")){
							
			}
			
			if(pMode.equals("Edit")){				
				
				// set read only other

				rdN.setDisabled(false);
				rdW.setDisabled(false);
				rdC.setDisabled(false);
				rdM.setDisabled(false);
				
					
					
			

				// set focus 
				//txtEmailFinance.setFocus(true);				
			}
		}
	}
	
	
	public void doRenderCheckBox(){
		if (getP06ParamDefaultRpt() != null){
			if (CommonUtils.isNotEmpty(getP06ParamDefaultRpt().getPeriodeKolomCf())){
				String vDefaultKolomCf = getP06ParamDefaultRpt().getPeriodeKolomCf();
				if (vDefaultKolomCf.equals("N") == true){
					rdN.setSelected(true);
				} else {
					if (vDefaultKolomCf.equals("W") == true){
						rdW.setSelected(true);
					} else {
						if (vDefaultKolomCf.equals("C") == true){
							rdC.setSelected(true);
						} else {
							if (vDefaultKolomCf.equals("M") == true){
								rdM.setSelected(true);
							}
						}
					}
				}
				
			}
		}
	}
	
	
	
	public String validasiBusinessRules(){		

		return null;
	}
	

    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getP06ParamDefaultRptMainCtrl().onClick$btnSave(event);
			}
		};
    }
	
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public P06ParamDefaultRpt getP06ParamDefaultRpt() {
		return getP06ParamDefaultRptMainCtrl().getSelectedP06ParamDefaultRpt();
	}

	public void setP06ParamDefaultRpt(P06ParamDefaultRpt selectedP06ParamDefaultRpt) {
		getP06ParamDefaultRptMainCtrl().setSelectedP06ParamDefaultRpt(selectedP06ParamDefaultRpt);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setP06ParamDefaultRptMainCtrl(P06ParamDefaultRptMainCtrl P06ParamDefaultRptMainCtrl) {
		this.P06ParamDefaultRptMainCtrl = P06ParamDefaultRptMainCtrl;
	}

	public P06ParamDefaultRptMainCtrl getP06ParamDefaultRptMainCtrl() {
		return this.P06ParamDefaultRptMainCtrl;
	}
}
