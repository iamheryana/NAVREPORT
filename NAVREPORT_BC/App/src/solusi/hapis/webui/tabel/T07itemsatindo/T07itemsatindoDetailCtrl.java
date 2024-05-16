package solusi.hapis.webui.tabel.T07itemsatindo;

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

import solusi.hapis.backend.parameter.service.SelectQueryNavReportService;
import solusi.hapis.backend.tabel.model.T07itemsatindo;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 05-07-2018
 */

public class T07itemsatindoDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT07itemsatindoDetail;
	
	protected Borderlayout borderlayout_T07itemsatindoDetail;
	
	// Data Entry Component
	protected Datebox dbTglBerlaku;
	protected Textbox txtNoItem;
	protected Decimalbox dcbSatAmountKomisi; 
	protected Decimalbox dcbIdmrAmountKomisi;
	protected Decimalbox dcbSatAmountBns;
	protected Decimalbox dcbIdmrAmountBns;
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T07itemsatindoMainCtrl T07itemsatindoMainCtrl;
	private SelectQueryNavReportService selectQueryNavReportService;
	
	
	/**
	 * default constructor.<br>
	 */
	public T07itemsatindoDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT07itemsatindoMainCtrl((T07itemsatindoMainCtrl) arg
					.get("ModuleMainController"));

			getT07itemsatindoMainCtrl().setT07itemsatindoDetailCtrl(this);
		}
		
		windowT07itemsatindoDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT07itemsatindoDetail(Event event) throws Exception {
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

		borderlayout_T07itemsatindoDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT07itemsatindoDetail.invalidate();
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
				getT07itemsatindoMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T07itemsatindo getT07itemsatindo() {
		return getT07itemsatindoMainCtrl().getSelectedT07itemsatindo();
	}

	public void setT07itemsatindo(T07itemsatindo selectedT07itemsatindo) {
		getT07itemsatindoMainCtrl().setSelectedT07itemsatindo(selectedT07itemsatindo);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT07itemsatindoMainCtrl(T07itemsatindoMainCtrl T07itemsatindoMainCtrl) {
		this.T07itemsatindoMainCtrl = T07itemsatindoMainCtrl;
	}

	public T07itemsatindoMainCtrl getT07itemsatindoMainCtrl() {
		return this.T07itemsatindoMainCtrl;
	}


	
	
}
