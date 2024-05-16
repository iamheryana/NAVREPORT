package solusi.hapis.webui.tabel.T01managementadj;

import java.io.Serializable;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.backend.tabel.model.T01managementadj;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ListBoxUtil;

/**
 * User: Heryana
 * Date: 07-09-2016
 */

public class T01managementadjDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT01managementadjDetail;
	
	protected Borderlayout borderlayout_T01managementadjDetail;
	
	// Data Entry Component
	protected Datebox dbTanggal;
	protected Bandbox cmbCabang;
	protected Textbox txtSales;
	protected Textbox txtKeterangan;	
	protected Decimalbox dcbAmounthw01;
	protected Decimalbox dcbAmountps01;
	protected Decimalbox dcbAmountps02;
	protected Decimalbox dcbAmountps03;
	protected Decimalbox dcbAmountps04;
	protected Decimalbox dcbAmountps05;

	protected Listbox list_Cabang;
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T01managementadjMainCtrl T01managementadjMainCtrl;

	private SelectQueryService selectQueryService;
	
	/**
	 * default constructor.<br>
	 */
	public T01managementadjDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT01managementadjMainCtrl((T01managementadjMainCtrl) arg
					.get("ModuleMainController"));

			getT01managementadjMainCtrl().setT01managementadjDetailCtrl(this);
		}
		
		windowT01managementadjDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT01managementadjDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		doRenderCombo();
		doFitSize(event);
	}

	public void doRenderCombo() {
		ListBoxUtil.resetList(list_Cabang);

		Listitem vListCabang = null;

		int a = 0;
		List<Object[]> vResult = selectQueryService.QueryCabang2();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				
				Listitem vList = list_Cabang.appendItem(aRslt[0].toString(),aRslt[1].toString());
				if (a == 0) {
					vListCabang = vList;
					a++;
				}
				
				if (getT01managementadj() != null) {
					if (getT01managementadj().getCabang() != null) {
						if (getT01managementadj().getCabang().equals(aRslt[1].toString())) {
							vListCabang = vList;
						}
					}
				}
			}
		}
		
		
//		for (Cabang cabs : Cabang.values()) {
//			Listitem vList = list_Cabang.appendItem(cabs.getLabel(),
//					cabs.getValue());
//			if (a == 0) {
//				vListCabang = vList;
//				a++;
//			}
//			if (getT01managementadj() != null) {
//				if (getT01managementadj().getCabang() != null) {
//					if (getT01managementadj().getCabang().equals(cabs.getValue())) {
//						vListCabang = vList;
//					}
//				}
//			}
//		}

		
		cmbCabang.setValue(vListCabang.getLabel());
		list_Cabang.setSelectedItem(vListCabang);


	}

	public void onSelect$list_Cabang(Event event) throws Exception {
		T01managementadj data = getT01managementadj();

		data.setCabang(list_Cabang.getSelectedItem().getValue().toString());
		setT01managementadj(data);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T01managementadjDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT01managementadjDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				
			
				dbTanggal.setDisabled(true);
				cmbCabang.setDisabled(true);				
				txtSales.setReadonly(true);
				txtKeterangan.setReadonly(true);				
				dcbAmounthw01.setReadonly(true);	
				dcbAmountps01.setReadonly(true);
				dcbAmountps02.setReadonly(true);
				dcbAmountps03.setReadonly(true);
				dcbAmountps04.setReadonly(true);
				dcbAmountps05.setReadonly(true);
			}
			
			if(pMode.equals("New")){
				dbTanggal.setDisabled(false);
				cmbCabang.setDisabled(false);				
				txtSales.setReadonly(false);
				txtKeterangan.setReadonly(false);				
				dcbAmounthw01.setReadonly(false);	
				dcbAmountps01.setReadonly(false);
				dcbAmountps02.setReadonly(false);
				dcbAmountps03.setReadonly(false);
				dcbAmountps04.setReadonly(false);
				dcbAmountps05.setReadonly(false);
				
				// set focus 
				dbTanggal.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				
				// set read only key
				dbTanggal.setDisabled(false);
				cmbCabang.setDisabled(false);				
				txtSales.setReadonly(false);
				txtKeterangan.setReadonly(false);				
				dcbAmounthw01.setReadonly(false);	
				dcbAmountps01.setReadonly(false);
				dcbAmountps02.setReadonly(false);
				dcbAmountps03.setReadonly(false);
				dcbAmountps04.setReadonly(false);
				dcbAmountps05.setReadonly(false);
				
				// set focus 
				dbTanggal.setFocus(true);				
			}
		}
	}

	

	
	public String validasiBusinessRules(){
		
		if(CommonUtils.isNotEmpty(dbTanggal.getValue()) == false){
			return "Tanggal "+Labels.getLabel("message.error.mandatory");
		}	
		
		if(CommonUtils.isNotEmpty(cmbCabang.getValue()) == false){
			return "Cabang "+Labels.getLabel("message.error.mandatory");
		}	
		
		if(CommonUtils.isNotEmpty(txtSales.getValue()) == false){
			return "Sales "+Labels.getLabel("message.error.mandatory");
		}	
				
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT01managementadjMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T01managementadj getT01managementadj() {
		return getT01managementadjMainCtrl().getSelectedT01managementadj();
	}

	public void setT01managementadj(T01managementadj selectedT01managementadj) {
		getT01managementadjMainCtrl().setSelectedT01managementadj(selectedT01managementadj);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT01managementadjMainCtrl(T01managementadjMainCtrl T01managementadjMainCtrl) {
		this.T01managementadjMainCtrl = T01managementadjMainCtrl;
	}

	public T01managementadjMainCtrl getT01managementadjMainCtrl() {
		return this.T01managementadjMainCtrl;
	}


	
	
}
