package solusi.hapis.webui.general.pembelian.T19PiItem;


import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T19PiItem;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 02-07-2021
 */

public class T19PiItemDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT19PiItemDetail;
	
	protected Borderlayout borderlayout_T19PiItemDetail;
	
	// Data Entry Component
	
	
	
	protected Datebox dbTglMulai;
	protected Textbox txtPrincipalCode;
	protected Textbox txtItemCatCode;
	protected Textbox txtProductCode;
	
	protected Textbox txtBerlaku;
	protected Checkbox chbBerlaku;	
	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T19PiItemMainCtrl T19PiItemMainCtrl;

	

	/**
	 * default constructor.<br>
	 */
	public T19PiItemDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT19PiItemMainCtrl((T19PiItemMainCtrl) arg
					.get("ModuleMainController"));

			getT19PiItemMainCtrl().setT19PiItemDetailCtrl(this);
		}
		
		windowT19PiItemDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT19PiItemDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		
		doFitSize(event);
	}

	public void doRenderCombo() {
		T19PiItem anT19 = getT19PiItem();

		

		if (anT19 != null) {
			if(CommonUtils.isNotEmpty(anT19.getBerlaku()) == true){
				if(anT19.getBerlaku().equals("Y") == true)
					chbBerlaku.setChecked(true);
				else
					chbBerlaku.setChecked(false);
			} else {
				chbBerlaku.setChecked(false);
			}
			
		}

	}
	
	public void onCheck$chbBerlaku(Event event) throws Exception {
		T19PiItem data = getT19PiItem();
		data.setBerlaku(chbBerlaku.isChecked()?"Y":"N");
		
		txtBerlaku.setValue(data.getBerlaku());
		
		setT19PiItem(data);
	}
	
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T19PiItemDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT19PiItemDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){		
				dbTglMulai.setDisabled(true);					
				txtPrincipalCode.setReadonly(true);	
				txtItemCatCode.setReadonly(true);	
				txtProductCode.setReadonly(true);	
							
				txtBerlaku.setReadonly(true);
				chbBerlaku.setDisabled(true);	
			}
			
			if(pMode.equals("New")){
				
				dbTglMulai.setDisabled(false);					
				txtPrincipalCode.setReadonly(false);	
				txtItemCatCode.setReadonly(false);	
				txtProductCode.setReadonly(false);	
							
				txtBerlaku.setReadonly(false);
				chbBerlaku.setDisabled(false);
				
				// set focus 
				dbTglMulai.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				

				dbTglMulai.setDisabled(false);					
				txtPrincipalCode.setReadonly(false);	
				txtItemCatCode.setReadonly(false);	
				txtProductCode.setReadonly(false);	
							
				txtBerlaku.setReadonly(false);
				chbBerlaku.setDisabled(false);
				
				// set focus 
				dbTglMulai.setFocus(true);						
			}

		}
	}
	

	
	public String validasiBusinessRules(){
		
		if(dbTglMulai.getValue() == null){
			return "Silahkan masukan Tgl. Mulai";			
		}
		
		if(CommonUtils.isNotEmpty(txtPrincipalCode.getValue()) == false){
			return "Silahkan masukan Principal Code";			
		}
		
		if(CommonUtils.isNotEmpty(txtItemCatCode.getValue()) == false){
			return "Silahkan masukan Item Category Code";			
		}
		
		if(CommonUtils.isNotEmpty(txtProductCode.getValue()) == false){
			return "Silahkan masukan Product Code";			
		}
		
			
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT19PiItemMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T19PiItem getT19PiItem() {
		return getT19PiItemMainCtrl().getSelectedT19PiItem();
	}

	public void setT19PiItem(T19PiItem selectedT19PiItem) {
		getT19PiItemMainCtrl().setSelectedT19PiItem(selectedT19PiItem);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT19PiItemMainCtrl(T19PiItemMainCtrl T19PiItemMainCtrl) {
		this.T19PiItemMainCtrl = T19PiItemMainCtrl;
	}

	public T19PiItemMainCtrl getT19PiItemMainCtrl() {
		return this.T19PiItemMainCtrl;
	}
	
	
}
