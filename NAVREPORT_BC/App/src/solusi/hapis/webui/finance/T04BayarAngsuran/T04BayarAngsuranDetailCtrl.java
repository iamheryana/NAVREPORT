package solusi.hapis.webui.finance.T04BayarAngsuran;

import java.io.Serializable;

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

import solusi.hapis.backend.navbi.model.T04BayarAngsuran;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ListBoxUtil;

/**
 * User: Heryana
 * Date: 17-03-2020
 */

public class T04BayarAngsuranDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT04BayarAngsuranDetail;
	
	protected Borderlayout borderlayout_T04BayarAngsuranDetail;
	
	// Data Entry Component
	
	
	protected Textbox txtSuppCode;
	protected Textbox txtNoPo;	
	protected Textbox txtValutaPo;
	protected Decimalbox dcbNilaiPo;
	
	protected Intbox intJmlGiro;	
	protected Intbox intInterval;
	
	protected Bandbox cmbIntervalWaktu;	
	protected Bandbox cmbCompany;
	
	
	protected Datebox dbTMT;	
	
	protected Intbox intPrintCount;
	
	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T04BayarAngsuranMainCtrl T04BayarAngsuranMainCtrl;

	protected Listbox list_IntervalWaktu;
	protected Listbox list_Company;

	
//	private SelectQueryNavReportService selectQueryNavReportService;
	
	/**
	 * default constructor.<br>
	 */
	public T04BayarAngsuranDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT04BayarAngsuranMainCtrl((T04BayarAngsuranMainCtrl) arg
					.get("ModuleMainController"));

			getT04BayarAngsuranMainCtrl().setT04BayarAngsuranDetailCtrl(this);
		}
		
		windowT04BayarAngsuranDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT04BayarAngsuranDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		doRenderCombo();
		doFitSize(event);
	}

	
	
	public void doRenderCombo() {
		
		
		
		ListBoxUtil.resetList(list_IntervalWaktu);
		ListBoxUtil.resetList(list_Company);
		
		
		T04BayarAngsuran anT04 = getT04BayarAngsuran();

		Listitem vListIntervalWaktu = null;
		
		Listitem vList1 = list_IntervalWaktu.appendItem("BULAN", "BULAN");
		Listitem vList2 = list_IntervalWaktu.appendItem("TAHUN", "TAHUN");
			

		if (anT04 != null) {
			if (anT04.getIntervalWaktu() != null) {
				if (anT04.getIntervalWaktu().equals("BULAN")) {
					vListIntervalWaktu = vList1;
				} else if (anT04.getIntervalWaktu().equals("TAHUN")) {
					vListIntervalWaktu = vList2;
				} 
			}
		}
		
		if(vListIntervalWaktu != null){
			cmbIntervalWaktu.setValue(vListIntervalWaktu.getLabel());
			list_IntervalWaktu.setSelectedItem(vListIntervalWaktu);
		} else {
			cmbIntervalWaktu.setValue("");
		}
	

		Listitem vListCompany = null;
		
		Listitem vListAJ = list_Company.appendItem("Autojaya", "AUTOJAYA");
		Listitem vListSP = list_Company.appendItem("Solusi", "SOLUSI");
				

		if (anT04 != null) {
			if (anT04.getCompany() != null) {
				if (anT04.getCompany().equals("AUTOJAYA")) {
					vListCompany = vListAJ;
				} else if (anT04.getCompany().equals("SOLUSI")) {
					vListCompany = vListSP;
				} 
			}
		}
		
		if(vListCompany != null){
			cmbCompany.setValue(vListCompany.getLabel());
			list_Company.setSelectedItem(vListCompany);
		} else {
			cmbCompany.setValue("");
		}
		
	}
	
	
	public void onSelect$list_IntervalWaktu(Event event) throws Exception {
		T04BayarAngsuran data = getT04BayarAngsuran();

		data.setIntervalWaktu(list_IntervalWaktu.getSelectedItem().getValue().toString());
		setT04BayarAngsuran(data);
		
	}
	
	public void onSelect$list_Company(Event event) throws Exception {
		T04BayarAngsuran data = getT04BayarAngsuran();

		data.setCompany(list_Company.getSelectedItem().getValue().toString());
		setT04BayarAngsuran(data);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T04BayarAngsuranDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT04BayarAngsuranDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				intPrintCount.setReadonly(true);		
				
				cmbCompany.setDisabled(true);
				txtSuppCode.setReadonly(true);	
				txtNoPo.setReadonly(true);	
				txtValutaPo.setReadonly(true);					
				dcbNilaiPo.setReadonly(true);	
				
				intJmlGiro.setReadonly(true);
				intInterval.setReadonly(true);	
				cmbIntervalWaktu.setDisabled(true);
				dbTMT.setDisabled(true);
			}
			
			if(pMode.equals("New")){
				intPrintCount.setReadonly(true);		
				
				cmbCompany.setDisabled(false);
				txtSuppCode.setReadonly(false);	
				txtNoPo.setReadonly(false);	
				txtValutaPo.setReadonly(false);					
				dcbNilaiPo.setReadonly(false);	
				
				intJmlGiro.setReadonly(false);
				intInterval.setReadonly(false);	
				cmbIntervalWaktu.setDisabled(false);
				dbTMT.setDisabled(false);
				
				// set focus 
				txtSuppCode.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				

				intPrintCount.setReadonly(true);		
				
				cmbCompany.setDisabled(false);
				txtSuppCode.setReadonly(false);	
				txtNoPo.setReadonly(false);	
				txtValutaPo.setReadonly(false);					
				dcbNilaiPo.setReadonly(false);	
				
				intJmlGiro.setReadonly(false);
				intInterval.setReadonly(false);	
				cmbIntervalWaktu.setDisabled(false);
				dbTMT.setDisabled(false);
				
				// set focus 
				txtSuppCode.setFocus(true);						
			}

		}
	}
	

	
	
	public String validasiBusinessRules(){		
		
		if(CommonUtils.isNotEmpty(txtSuppCode.getValue()) == false){
			return "Silahkan masukan Kode Supllier NAV";				
		}
		
		if(CommonUtils.isNotEmpty(txtNoPo.getValue()) == false){
			return "Silahkan masukan No. PO";				
		}
		
		
		if(CommonUtils.isNotEmpty(txtValutaPo.getValue()) == false){
			return "Silahkan masukan Currency";				
		}
		
		
		if(CommonUtils.isNotEmpty(dcbNilaiPo.getValue()) == false){
			return "Silahkan masukan Nilai DPP PO";				
		}
		
		
		if(CommonUtils.isNotEmpty(intJmlGiro.getValue()) == false){
			return "Silahkan masukan Jumlah Giro";				
		}
		
		
		if(CommonUtils.isNotEmpty(intInterval.getValue()) == false){
			return "Silahkan masukan Durasi Pengulangan Giro";				
		}
		
		
		if(CommonUtils.isNotEmpty(cmbIntervalWaktu.getValue()) == false){
			return "Silahkan masukan Durasi Pengulangan Giro";				
		}
		
		if(CommonUtils.isNotEmpty(dbTMT.getValue()) == false){
			return "Silahkan masukan Tgl. Giro Pertama";				
		}
		
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT04BayarAngsuranMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T04BayarAngsuran getT04BayarAngsuran() {
		return getT04BayarAngsuranMainCtrl().getSelectedT04BayarAngsuran();
	}

	public void setT04BayarAngsuran(T04BayarAngsuran selectedT04BayarAngsuran) {
		getT04BayarAngsuranMainCtrl().setSelectedT04BayarAngsuran(selectedT04BayarAngsuran);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT04BayarAngsuranMainCtrl(T04BayarAngsuranMainCtrl T04BayarAngsuranMainCtrl) {
		this.T04BayarAngsuranMainCtrl = T04BayarAngsuranMainCtrl;
	}

	public T04BayarAngsuranMainCtrl getT04BayarAngsuranMainCtrl() {
		return this.T04BayarAngsuranMainCtrl;
	}
	
	
}
