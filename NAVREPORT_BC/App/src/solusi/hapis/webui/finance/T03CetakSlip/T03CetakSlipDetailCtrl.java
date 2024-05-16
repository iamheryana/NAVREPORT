package solusi.hapis.webui.finance.T03CetakSlip;

import java.io.Serializable;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.P02VendorNonnav;
import solusi.hapis.backend.navbi.model.T03CetakSlip;
import solusi.hapis.backend.navbi.service.P02VendorNonnavService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ListBoxUtil;

/**
 * User: Heryana
 * Date: 23-01-2020
 */

public class T03CetakSlipDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT03CetakSlipDetail;
	
	protected Borderlayout borderlayout_T03CetakSlipDetail;
	
	// Data Entry Component
	

	protected Bandbox cmbJenisTrans;
	protected Bandbox cmbCompany;
	
	
	protected Intbox intPrintCount;
	protected Textbox txtNoVoucher;
	protected Textbox txtNoCheque;
	
	protected Decimalbox dcmKurs;
	protected Decimalbox dcmProvisiIdrToCny;
	
	protected Textbox txtKodeVendorNonnav;
	protected Textbox txtKodeValuta;
	
	protected Textbox txtNoRekPenerima;
	protected Textbox txtNamaPenerima;
	protected Textbox txtAlamatPenerima1;
	protected Textbox txtAlamatPenerima2;
	protected Textbox txtCountryPenerima;
	
	protected Textbox txtNamaBank;
	protected Textbox txtAlamatBank1;
	protected Textbox txtAlamatBank2;
	protected Textbox txtCountryBank;
	protected Textbox txtSwiftCode;
	
	protected Textbox txtNamaPenerima2;
	
	protected Textbox txtBerita;
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T03CetakSlipMainCtrl T03CetakSlipMainCtrl;

	protected Listbox list_JenisTrans;
	protected Listbox list_Company;
	

	protected Row row01;
	protected Row row02;
	protected Row row03;
	protected Row row04;
	protected Row row05;
	protected Row row06;
	protected Row row07;
	
	protected Row rowChequeOnly;

	protected Button btnSearchVendorLOV;
	
	private transient P02VendorNonnavService p02VendorNonnavService;

	
//	private SelectQueryNavReportService selectQueryNavReportService;
	
	/**
	 * default constructor.<br>
	 */
	public T03CetakSlipDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT03CetakSlipMainCtrl((T03CetakSlipMainCtrl) arg
					.get("ModuleMainController"));

			getT03CetakSlipMainCtrl().setT03CetakSlipDetailCtrl(this);
		}
		
		windowT03CetakSlipDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT03CetakSlipDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		doRenderCombo();
		doFitSize(event);
	}

	public void doRenderLayout(){
		T03CetakSlip anT03 = getT03CetakSlip();
		
		if (anT03.getJenisTrans() != null){
			if(anT03.getJenisTrans().equals("VR-NNAV") == true){
				row01.setVisible(true);
				row02.setVisible(true);
				row03.setVisible(true);
				row04.setVisible(true);
				row05.setVisible(true);
				row06.setVisible(true);
				row07.setVisible(true);
				rowChequeOnly.setVisible(false);
			} else {	
				row01.setVisible(false);
				row02.setVisible(false);
				row03.setVisible(false);
				row04.setVisible(false);
				row05.setVisible(false);
				row06.setVisible(false);
				row07.setVisible(false);
				if(anT03.getJenisTrans().equals("VR-NAV") == true){
					rowChequeOnly.setVisible(false);
				} else {
					rowChequeOnly.setVisible(true);
				}
			}
		} else {
			row01.setVisible(false);
			row02.setVisible(false);
			row03.setVisible(false);
			row04.setVisible(false);
			row05.setVisible(false);
			row06.setVisible(false);
			row07.setVisible(false);
		}
		
	}
	
	
	
	public void doRenderCombo() {
		
		
		
		ListBoxUtil.resetList(list_JenisTrans);
		ListBoxUtil.resetList(list_Company);
		
		T03CetakSlip anT03 = getT03CetakSlip();

		Listitem vListJenisTrans = null;
		
		Listitem vList1 = list_JenisTrans.appendItem("Vendor NAV", "VR-NAV");
		Listitem vList2 = list_JenisTrans.appendItem("Vendor NON-NAV", "VR-NNAV");
		Listitem vList3 = list_JenisTrans.appendItem("No Slip / Cheque Only", "CASH");
			

		if (anT03 != null) {
			if (anT03.getJenisTrans() != null) {
				if (anT03.getJenisTrans().equals("VR-NAV")) {
					vListJenisTrans = vList1;
				} else if (anT03.getJenisTrans().equals("VR-NNAV")) {
					vListJenisTrans = vList2;
				} else if (anT03.getJenisTrans().equals("CASH")) {
					vListJenisTrans = vList3;
				} 
			}
		}
		
		if(vListJenisTrans != null){
			cmbJenisTrans.setValue(vListJenisTrans.getLabel());
			list_JenisTrans.setSelectedItem(vListJenisTrans);
		} else {
			cmbJenisTrans.setValue("");
		}
	
	
		Listitem vListCompany = null;
		
		Listitem vListAJ = list_Company.appendItem("Autojaya", "AUTOJAYA");
		Listitem vListSP = list_Company.appendItem("Solusi", "SOLUSI");
				

		if (anT03 != null) {
			if (anT03.getCompany() != null) {
				if (anT03.getCompany().equals("AUTOJAYA")) {
					vListCompany = vListAJ;
				} else if (anT03.getCompany().equals("SOLUSI")) {
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
	
		
		doRenderLayout();
	}
	
	
	public void onSelect$list_JenisTrans(Event event) throws Exception {
		T03CetakSlip data = getT03CetakSlip();

		data.setJenisTrans(list_JenisTrans.getSelectedItem().getValue().toString());
		setT03CetakSlip(data);
		
		doRenderLayout();
	}
	
	public void onSelect$list_Company(Event event) throws Exception {
		T03CetakSlip data = getT03CetakSlip();

		data.setCompany(list_Company.getSelectedItem().getValue().toString());
		setT03CetakSlip(data);
	}
	
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T03CetakSlipDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT03CetakSlipDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				
				cmbJenisTrans.setDisabled(true);
				cmbCompany.setDisabled(true);
				
				intPrintCount.setReadonly(true);		
				
				txtNoVoucher.setReadonly(true);	
				txtNoCheque.setReadonly(true);	
				dcmKurs.setReadonly(true);	
				dcmProvisiIdrToCny.setReadonly(true);	
				
				txtKodeVendorNonnav.setReadonly(true);	
				txtKodeValuta.setReadonly(true);	
				txtNoRekPenerima.setReadonly(true);	
				txtNamaPenerima.setReadonly(true);	
				txtAlamatPenerima1.setReadonly(true);	
				txtAlamatPenerima2.setReadonly(true);	
				txtCountryPenerima.setReadonly(true);	
				txtNamaBank.setReadonly(true);	
				txtAlamatBank1.setReadonly(true);	
				txtAlamatBank2.setReadonly(true);	
				txtCountryBank.setReadonly(true);	
				txtSwiftCode.setReadonly(true);
				
				txtNamaPenerima2.setReadonly(true);
				txtBerita.setReadonly(true);
				
				btnSearchVendorLOV.setDisabled(true);
			}
			
			if(pMode.equals("New")){
				cmbJenisTrans.setDisabled(false);
				cmbCompany.setDisabled(false);
				
				intPrintCount.setReadonly(true);		
				
				txtNoVoucher.setReadonly(false);	
				txtNoCheque.setReadonly(false);	
				dcmKurs.setReadonly(false);	
				dcmProvisiIdrToCny.setReadonly(false);	
				
				txtKodeVendorNonnav.setReadonly(false);	
				txtKodeValuta.setReadonly(false);	
				txtNoRekPenerima.setReadonly(false);	
				txtNamaPenerima.setReadonly(false);	
				txtAlamatPenerima1.setReadonly(false);	
				txtAlamatPenerima2.setReadonly(false);	
				txtCountryPenerima.setReadonly(false);	
				txtNamaBank.setReadonly(false);	
				txtAlamatBank1.setReadonly(false);	
				txtAlamatBank2.setReadonly(false);	
				txtCountryBank.setReadonly(false);	
				txtSwiftCode.setReadonly(false);
				
				txtNamaPenerima2.setReadonly(false);
				txtBerita.setReadonly(false);
				
				btnSearchVendorLOV.setDisabled(false);
				
				// set focus 
				cmbJenisTrans.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				
				cmbJenisTrans.setDisabled(false);
				cmbCompany.setDisabled(false);
				
				intPrintCount.setReadonly(true);		
				
				txtNoVoucher.setReadonly(false);	
				txtNoCheque.setReadonly(false);	
				dcmKurs.setReadonly(false);	
				dcmProvisiIdrToCny.setReadonly(false);	
				
				txtKodeVendorNonnav.setReadonly(false);	
				txtKodeValuta.setReadonly(false);	
				txtNoRekPenerima.setReadonly(false);	
				txtNamaPenerima.setReadonly(false);	
				txtAlamatPenerima1.setReadonly(false);	
				txtAlamatPenerima2.setReadonly(false);	
				txtCountryPenerima.setReadonly(false);	
				txtNamaBank.setReadonly(false);	
				txtAlamatBank1.setReadonly(false);	
				txtAlamatBank2.setReadonly(false);	
				txtCountryBank.setReadonly(false);	
				txtSwiftCode.setReadonly(false);
				
				txtNamaPenerima2.setReadonly(false);
				txtBerita.setReadonly(false);
				
				btnSearchVendorLOV.setDisabled(false);
				
				// set focus 
				cmbJenisTrans.setFocus(true);							
			}

		}
	}
	
	public void onClick$btnSearchVendorLOV(Event event) {
		P02VendorNonnav vend = P02VendorNonnavLOV.show(windowT03CetakSlipDetail);
		
		if (vend != null) {
			txtKodeVendorNonnav.setValue(vend.getKode());
			txtKodeValuta.setValue(vend.getValutaTrans());
			txtNoRekPenerima.setValue(vend.getNoRekPenerima());
			txtNamaPenerima.setValue(vend.getNamaPenerima());
			txtAlamatPenerima1.setValue(vend.getAlamatPenerima1());
			txtAlamatPenerima2.setValue(vend.getAlamatPenerima2());
			txtCountryPenerima.setValue(vend.getCountryPenerima());
			txtNamaBank.setValue(vend.getNamaBank());
			txtAlamatBank1.setValue(vend.getAlamatBank1());
			txtAlamatBank2.setValue(vend.getAlamatBank2());
			txtCountryBank.setValue(vend.getCountryBank());
			txtSwiftCode.setValue(vend.getSwiftCode());		
			
		} else {
			txtKodeVendorNonnav.setValue(null);
			txtKodeValuta.setValue(null);
			txtNoRekPenerima.setValue(null);
			txtNamaPenerima.setValue(null);
			txtAlamatPenerima1.setValue(null);
			txtAlamatPenerima2.setValue(null);
			txtCountryPenerima.setValue(null);
			txtNamaBank.setValue(null);
			txtAlamatBank1.setValue(null);
			txtAlamatBank2.setValue(null);
			txtCountryBank.setValue(null);
			txtSwiftCode.setValue(null);
		}

	}
	
	public void onChange$txtKodeVendorNonnav(Event event) {

		if (CommonUtils.isNotEmpty(txtKodeVendorNonnav.getValue())) {

			P02VendorNonnav vend = p02VendorNonnavService
					.getP02VendorNonnavByKode(CommonUtils.toUpperCase(txtKodeVendorNonnav.getValue()));

			if (vend != null) {
				getT03CetakSlip().setValutaTrans(vend.getValutaTrans());							
				getT03CetakSlip().setNoRekPenerima(vend.getNoRekPenerima());
				getT03CetakSlip().setNamaPenerima(vend.getNamaPenerima());
				getT03CetakSlip().setAlamatPenerima1(vend.getAlamatPenerima1());
				getT03CetakSlip().setAlamatPenerima2(vend.getAlamatPenerima2());
				getT03CetakSlip().setCountryPenerima(vend.getCountryPenerima());
				getT03CetakSlip().setNamaBank(vend.getNamaBank());
				getT03CetakSlip().setAlamatBank1(vend.getAlamatBank1());
				getT03CetakSlip().setAlamatBank2(vend.getAlamatBank2());
				getT03CetakSlip().setCountryBank(vend.getCountryBank());
				getT03CetakSlip().setSwiftCode(vend.getSwiftCode());		
			} else {
				getT03CetakSlip().setValutaTrans(null);							
				getT03CetakSlip().setNoRekPenerima(null);
				getT03CetakSlip().setNamaPenerima(null);
				getT03CetakSlip().setAlamatPenerima1(null);
				getT03CetakSlip().setAlamatPenerima2(null);
				getT03CetakSlip().setCountryPenerima(null);
				getT03CetakSlip().setNamaBank(null);
				getT03CetakSlip().setAlamatBank1(null);
				getT03CetakSlip().setAlamatBank2(null);
				getT03CetakSlip().setCountryBank(null);
				getT03CetakSlip().setSwiftCode(null);	
			}

		} else {
			getT03CetakSlip().setValutaTrans(null);							
			getT03CetakSlip().setNoRekPenerima(null);
			getT03CetakSlip().setNamaPenerima(null);
			getT03CetakSlip().setAlamatPenerima1(null);
			getT03CetakSlip().setAlamatPenerima2(null);
			getT03CetakSlip().setCountryPenerima(null);
			getT03CetakSlip().setNamaBank(null);
			getT03CetakSlip().setAlamatBank1(null);
			getT03CetakSlip().setAlamatBank2(null);
			getT03CetakSlip().setCountryBank(null);
			getT03CetakSlip().setSwiftCode(null);	
		}
	}
	
	
	public String validasiBusinessRules(){
		if(CommonUtils.isNotEmpty(txtNoVoucher.getValue()) == false){
			return "Silahkan masukan No. Voucher";				
		}
		
		if(CommonUtils.isNotEmpty(txtNoCheque.getValue()) == false){
			return "Silahkan masukan No. Cheque";				
		}
		
		
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT03CetakSlipMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T03CetakSlip getT03CetakSlip() {
		return getT03CetakSlipMainCtrl().getSelectedT03CetakSlip();
	}

	public void setT03CetakSlip(T03CetakSlip selectedT03CetakSlip) {
		getT03CetakSlipMainCtrl().setSelectedT03CetakSlip(selectedT03CetakSlip);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT03CetakSlipMainCtrl(T03CetakSlipMainCtrl T03CetakSlipMainCtrl) {
		this.T03CetakSlipMainCtrl = T03CetakSlipMainCtrl;
	}

	public T03CetakSlipMainCtrl getT03CetakSlipMainCtrl() {
		return this.T03CetakSlipMainCtrl;
	}
	
	
}
