package solusi.hapis.webui.finance.M04ItemSatindo;


import java.io.Serializable;
import java.util.Date;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.M04ItemSatindo;
import solusi.hapis.backend.parameter.service.SelectQueryNavReportService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 29-04-2021
 */

public class M04ItemSatindoDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowM04ItemSatindoDetail;
	
	protected Borderlayout borderlayout_M04ItemSatindoDetail;
	
	// Data Entry Component
	protected Datebox dbTglBerlaku;
	protected Textbox txtNoItem;
	protected Decimalbox dcbSatAmountKomisi; 
	protected Decimalbox dcbIdmrAmountKomisi;
	protected Decimalbox dcbSatAmountBns;
	protected Decimalbox dcbIdmrAmountBns;
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private M04ItemSatindoMainCtrl M04ItemSatindoMainCtrl;
	private SelectQueryNavReportService selectQueryNavReportService;
	
	
	/**
	 * default constructor.<br>
	 */
	public M04ItemSatindoDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setM04ItemSatindoMainCtrl((M04ItemSatindoMainCtrl) arg
					.get("ModuleMainController"));

			getM04ItemSatindoMainCtrl().setM04ItemSatindoDetailCtrl(this);
		}
		
		windowM04ItemSatindoDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowM04ItemSatindoDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		doRenderCombo();
		doFitSize(event);
	}

	public void doRenderCombo() {
		


	}

	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_M04ItemSatindoDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowM04ItemSatindoDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
		
				dbTglBerlaku.setDisabled(true);				
				txtNoItem.setReadonly(true);				
				dcbSatAmountKomisi.setReadonly(true);
				dcbIdmrAmountKomisi.setReadonly(true);
				dcbSatAmountBns.setReadonly(true);
				dcbIdmrAmountBns.setReadonly(true);

			}
			
			if(pMode.equals("New")){
				dbTglBerlaku.setDisabled(false);				
				txtNoItem.setReadonly(false);				
				dcbSatAmountKomisi.setReadonly(false);
				dcbIdmrAmountKomisi.setReadonly(false);
				dcbSatAmountBns.setReadonly(false);
				dcbIdmrAmountBns.setReadonly(false);
				
				// set focus 
				dbTglBerlaku.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				
				// set read only key
				dbTglBerlaku.setDisabled(false);				
				txtNoItem.setReadonly(false);				
				dcbSatAmountKomisi.setReadonly(false);
				dcbIdmrAmountKomisi.setReadonly(false);
				dcbSatAmountBns.setReadonly(false);
				dcbIdmrAmountBns.setReadonly(false);
				
				// set focus 
				dbTglBerlaku.setFocus(true);					
			}
		}
	}

	
	
	public String validasiBusinessRules(){
		
		if(CommonUtils.isNotEmpty(dbTglBerlaku.getValue()) == false){
			return "Tgl. Mulai Berlaku "+Labels.getLabel("message.error.mandatory");
		}	else {
			
			Date vTglResign = dbTglBerlaku.getValue();
			
			String vResultDownload = selectQueryNavReportService.callCekPeriodeClosing(vTglResign);
			
			
			if(vResultDownload.equals("OK") == false){
				return vResultDownload;
			}
				
			
		}
		
		if(CommonUtils.isNotEmpty(txtNoItem.getValue()) == false){
			return "No. Item "+Labels.getLabel("message.error.mandatory");
		}	
		
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getM04ItemSatindoMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public M04ItemSatindo getM04ItemSatindo() {
		return getM04ItemSatindoMainCtrl().getSelectedM04ItemSatindo();
	}

	public void setM04ItemSatindo(M04ItemSatindo selectedM04ItemSatindo) {
		getM04ItemSatindoMainCtrl().setSelectedM04ItemSatindo(selectedM04ItemSatindo);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setM04ItemSatindoMainCtrl(M04ItemSatindoMainCtrl M04ItemSatindoMainCtrl) {
		this.M04ItemSatindoMainCtrl = M04ItemSatindoMainCtrl;
	}

	public M04ItemSatindoMainCtrl getM04ItemSatindoMainCtrl() {
		return this.M04ItemSatindoMainCtrl;
	}


	
	
}
