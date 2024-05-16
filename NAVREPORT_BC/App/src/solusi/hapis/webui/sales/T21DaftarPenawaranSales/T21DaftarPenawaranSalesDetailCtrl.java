package solusi.hapis.webui.sales.T21DaftarPenawaranSales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T21DaftarPenawaranSales;
import solusi.hapis.backend.navbi.model.V01CustomerNav;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ListBoxUtil;

/**
 * User: Heryana
 * Date: 16-11-2021
 */

public class T21DaftarPenawaranSalesDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT21DaftarPenawaranSalesDetail;
	
	protected Borderlayout borderlayout_T21DaftarPenawaranSalesDetail;
	
	// Data Entry Component
	
	protected Bandbox cmbCompany;
	protected Listbox list_Company;
	
	protected Bandbox cmbCabang;	
	protected Listbox list_Cabang;
	
	protected Textbox txtSales;
	protected Datebox dbTglPenawaran;
	protected Textbox txtNoPenawaran;	
	protected Textbox txtCustomer;
	protected Textbox txtKeterangan;
	protected Decimalbox dcbNilai;
	
	protected Row rowTglAwarded;
	protected Datebox dbTglAwarded;
	
	protected Bandbox cmbStatus;
	protected Listbox list_Status;
	
	protected Bandbox cmbSektorIndustri;
	protected Listbox list_SektorIndustri;
	
	
	
	protected Button btnSearchCustomerLOV;
	
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T21DaftarPenawaranSalesMainCtrl T21DaftarPenawaranSalesMainCtrl;
	private SelectQueryService selectQueryService;
	

	/**
	 * default constructor.<br>
	 */
	public T21DaftarPenawaranSalesDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT21DaftarPenawaranSalesMainCtrl((T21DaftarPenawaranSalesMainCtrl) arg
					.get("ModuleMainController"));

			getT21DaftarPenawaranSalesMainCtrl().setT21DaftarPenawaranSalesDetailCtrl(this);
		}
		
		windowT21DaftarPenawaranSalesDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT21DaftarPenawaranSalesDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		
		doFitSize(event);
	}

	
	public void onClick$btnSearchCustomerLOV(Event event) {
		V01CustomerNav cust = V01CustomerNavLOV.show(windowT21DaftarPenawaranSalesDetail);
		
		if (cust != null) {
			txtCustomer.setValue(cust.getCustName());
			
			
			getT21DaftarPenawaranSales().setNamaCustomer(cust.getCustName());
			getT21DaftarPenawaranSales().setSektorIndustri(cust.getSectorCode());
			
			doRenderCombo();
			
			
			
			
			
		}
	}
	
	
	public void doRenderCombo() {
		ListBoxUtil.resetList(list_Company);
		ListBoxUtil.resetList(list_Cabang);
		ListBoxUtil.resetList(list_Status);
		
		T21DaftarPenawaranSales anT21 = getT21DaftarPenawaranSales();

		// Company ============================================================= START
		Listitem vListCompany = null;

		Listitem vListAUTOJAYA = list_Company.appendItem("Autojaya (AJ)", "AJ");
		Listitem vListSOLUSI = list_Company.appendItem("Solusi (SP)", "SP");
		
		if (anT21 != null) {
			if (anT21.getCompany() != null) {
				if (anT21.getCompany().equals("AJ")) {
					vListCompany = vListAUTOJAYA;
				} else {
					vListCompany = vListSOLUSI;
				}
			}
		}
		
		
		cmbCompany.setValue(vListCompany.getLabel());
		list_Company.setSelectedItem(vListCompany);
		// Company =============================================================== END
		
		// Cabang ============================================================== START	
		Listitem vListCabang = null;

		Listitem vListJKT = list_Cabang.appendItem("Jakarta (JKT)", "JKT");
		Listitem vListCKR = list_Cabang.appendItem("Cikarang (CKR)", "CKR");
		Listitem vListSMR = list_Cabang.appendItem("Semarang (SMR)", "SMR");
		Listitem vListSBY = list_Cabang.appendItem("Surabaya (SBY)", "SBY");
		Listitem vListDPS = list_Cabang.appendItem("Denpasar (DPS)", "DPS");
		
		
		if (anT21 != null) {
			if (anT21.getCabang() != null) {
				if (anT21.getCabang().equals("JKT")) {
					vListCabang = vListJKT;
				} else {
					if (anT21.getCabang().equals("CKR")) {
						vListCabang = vListCKR;
					} else {
						if (anT21.getCabang().equals("SMR")) {
							vListCabang = vListSMR;
						} else {
							if (anT21.getCabang().equals("SBY")) {
								vListCabang = vListSBY;
							} else {
								if (anT21.getCabang().equals("DPS")) {
									vListCabang = vListDPS;
								} else {
									vListCabang = vListJKT;
								}
							}
						}
					}
				}
			}
		}
		
		
		cmbCabang.setValue(vListCabang.getLabel());
		list_Cabang.setSelectedItem(vListCabang);
		// Cabang================================================================= END
		
		
		// Status ============================================================== START		
		Listitem vListStatus = null;	
		

		
		Listitem vList00 = list_Status.appendItem("Submitted", "0");
		Listitem vList01 = list_Status.appendItem("Awarded", "1");
		Listitem vList02 = list_Status.appendItem("Lost", "2");
		Listitem vList03 = list_Status.appendItem("Postponed", "3");
		Listitem vList04 = list_Status.appendItem("Canceled", "4");
		Listitem vList05 = list_Status.appendItem("Closed", "5");
		
		if (anT21 != null) {
			if (anT21.getStatusPenawaran() != null) {
				if (anT21.getStatusPenawaran().equals("0")) {
					vListStatus = vList00;
				} else {
					if (anT21.getStatusPenawaran().equals("1")) {
						vListStatus = vList01;
					} else {
						if (anT21.getStatusPenawaran().equals("2")) {
							vListStatus = vList02;
						} else {
							if (anT21.getStatusPenawaran().equals("3")) {
								vListStatus = vList03;
							} else {
								if (anT21.getStatusPenawaran().equals("4")) {
									vListStatus = vList04;
								} else {
									if (anT21.getStatusPenawaran().equals("5")) {
										vListStatus = vList05;
									} else {
										vListStatus = vList00;
									}
								}	
							}
						}
					}
				}
			}
		}
		
		
		cmbStatus.setValue(vListStatus.getLabel());
		list_Status.setSelectedItem(vListStatus);
		// Status ================================================================ END
		
		
		// Sektor Industri ===================================================== START
				
		ListBoxUtil.resetList(list_SektorIndustri);

		Listitem vListSektorIndustri = null;

		//int a = 0;
		List<Object[]> vResult = selectQueryService.QuerySektorIndustri();
		
		Listitem vListNew  = list_SektorIndustri.appendItem("<Please Select>","XXX");
		vListSektorIndustri = vListNew;
		
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				
				Listitem vList = list_SektorIndustri.appendItem(aRslt[0].toString(),aRslt[1].toString());
				//if (a == 0) {
				//	vListSektorIndustri = vList;
				//	a++;
				//}
				
				if (anT21 != null) {
					if (anT21.getSektorIndustri() != null) {
						if (anT21.getSektorIndustri().equals(aRslt[1].toString())) {
							vListSektorIndustri = vList;
						}
					}
				}
			}
		}
		
		cmbSektorIndustri.setValue(vListSektorIndustri.getLabel());
		list_SektorIndustri.setSelectedItem(vListSektorIndustri);
	
		// Sektor Industri ======================================================= END
	}
	
	public void onSelect$list_Company(Event event) throws Exception {
		T21DaftarPenawaranSales data = getT21DaftarPenawaranSales();

		data.setCompany(list_Company.getSelectedItem().getValue().toString());
		setT21DaftarPenawaranSales(data);
	}
	
	public void onSelect$list_Cabang(Event event) throws Exception {
		T21DaftarPenawaranSales data = getT21DaftarPenawaranSales();

		data.setCabang(list_Cabang.getSelectedItem().getValue().toString());
		setT21DaftarPenawaranSales(data);
	}
	
	public void onSelect$list_Status(Event event) throws Exception {
		T21DaftarPenawaranSales data = getT21DaftarPenawaranSales();
		
		data.setStatusPenawaran(list_Status.getSelectedItem().getValue().toString());
		setT21DaftarPenawaranSales(data);
	}
	
	public void onSelect$list_SektorIndustri(Event event) throws Exception {
		T21DaftarPenawaranSales data = getT21DaftarPenawaranSales();

		data.setSektorIndustri(list_SektorIndustri.getSelectedItem().getValue().toString());
		
		setT21DaftarPenawaranSales(data);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T21DaftarPenawaranSalesDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT21DaftarPenawaranSalesDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){		
				
				cmbCompany.setDisabled(true);
				cmbCabang.setDisabled(true);
				txtSales.setReadonly(true);	
				dbTglPenawaran.setDisabled(true);	
				
				txtNoPenawaran.setReadonly(true);	
				txtNoPenawaran.setVisible(true);
				
				txtCustomer.setReadonly(true);	
				cmbSektorIndustri.setDisabled(true);
				
				txtKeterangan.setReadonly(true);	
				dcbNilai.setReadonly(true);	
				cmbStatus.setDisabled(true);
				
				
				
				rowTglAwarded.setVisible(true);
				dbTglAwarded.setDisabled(true);	
				
				
				btnSearchCustomerLOV.setDisabled(true);
				
				
				
			}
			
			if(pMode.equals("New")){
				
				cmbCompany.setDisabled(false);
				cmbCabang.setDisabled(false);
				txtSales.setReadonly(false);	
				dbTglPenawaran.setDisabled(false);	
				
				txtNoPenawaran.setReadonly(true);	
				txtNoPenawaran.setVisible(false);
				
				txtCustomer.setReadonly(false);	
				cmbSektorIndustri.setDisabled(false);
				txtKeterangan.setReadonly(false);	
				dcbNilai.setReadonly(false);
				cmbStatus.setDisabled(false);
				
				
				rowTglAwarded.setVisible(false);
				dbTglAwarded.setDisabled(true);	
				
				btnSearchCustomerLOV.setDisabled(false);
				
				
				// set focus 
				txtCustomer.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				

				cmbCompany.setDisabled(true);
				cmbCabang.setDisabled(true);
				txtSales.setReadonly(false);	
				dbTglPenawaran.setDisabled(false);	
				
				txtNoPenawaran.setReadonly(true);	
				txtNoPenawaran.setVisible(true);
				
				txtCustomer.setReadonly(false);	
				cmbSektorIndustri.setDisabled(false);
				txtKeterangan.setReadonly(false);	
				dcbNilai.setReadonly(false);
				cmbStatus.setDisabled(false);
				
				
				rowTglAwarded.setVisible(false);
				dbTglAwarded.setDisabled(true);	
				
				btnSearchCustomerLOV.setDisabled(false);
				
				// set focus 
				txtCustomer.setFocus(true);						
			}

			if(pMode.equals("Awarded")){				

				cmbCompany.setDisabled(true);
				cmbCabang.setDisabled(true);
				txtSales.setReadonly(true);	
				dbTglPenawaran.setDisabled(true);	
				
				txtNoPenawaran.setReadonly(true);	
				txtNoPenawaran.setVisible(true);
				
				txtCustomer.setReadonly(true);	
				cmbSektorIndustri.setDisabled(true);
				txtKeterangan.setReadonly(true);	
				dcbNilai.setReadonly(true);
				cmbStatus.setDisabled(true);
				
				rowTglAwarded.setVisible(true);
				dbTglAwarded.setDisabled(false);	
				
				btnSearchCustomerLOV.setDisabled(true);
				
				// set focus 
				dbTglAwarded.setFocus(true);						
			}
		}
	}
	
	public String validasiBusinessRulesAwarded(){
		if(CommonUtils.isNotEmpty(dbTglAwarded.getValue()) == false){
			return "Silahkan masukan Tgl Awarded";			
		}
		
		return null;
	}
	
	public String validasiBusinessRules(){
		
		
		if(CommonUtils.isNotEmpty(txtSales.getValue()) == false){
			return "Silahkan masukan Kode Sales";			
		}
		
		if(CommonUtils.isNotEmpty(dbTglPenawaran.getValue()) == false){
			return "Silahkan masukan Tgl. Penawaran";			
		}
		
		if(CommonUtils.isNotEmpty(txtNoPenawaran.getValue()) == false){
			return "Silahkan masukan No Penawaran";			
		}
		
		if(CommonUtils.isNotEmpty(txtCustomer.getValue()) == false){
			return "Silahkan masukan Customer";			
		}
		
		if(CommonUtils.isNotEmpty(list_SektorIndustri.getSelectedItem().getValue()) == true){
			if (list_SektorIndustri.getSelectedItem().getValue().equals("XXX") == true){
				return "Silahkan masukan Sektor Industri";		
			}
		} else{
			return "Silahkan masukan Sektor Industri";		
		}
	
		
		if(CommonUtils.isNotEmpty(txtKeterangan.getValue()) == false){
			return "Silahkan masukan Subject";			
		}
		
		
		
		
		if(CommonUtils.isNotEmpty(dcbNilai.getValue()) == true){
			BigDecimal vNilai = dcbNilai.getValue();
			
			
			if (vNilai.compareTo(new BigDecimal(0)) <= 0){
			
				return "Silahkan masukan Total Nilai Penawaran";	
			}
		} else {
			return "Silahkan masukan Total Nilai Penawaran";
		}

		
		
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT21DaftarPenawaranSalesMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T21DaftarPenawaranSales getT21DaftarPenawaranSales() {
		return getT21DaftarPenawaranSalesMainCtrl().getSelectedT21DaftarPenawaranSales();
	}

	public void setT21DaftarPenawaranSales(T21DaftarPenawaranSales selectedT21DaftarPenawaranSales) {
		getT21DaftarPenawaranSalesMainCtrl().setSelectedT21DaftarPenawaranSales(selectedT21DaftarPenawaranSales);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT21DaftarPenawaranSalesMainCtrl(T21DaftarPenawaranSalesMainCtrl T21DaftarPenawaranSalesMainCtrl) {
		this.T21DaftarPenawaranSalesMainCtrl = T21DaftarPenawaranSalesMainCtrl;
	}

	public T21DaftarPenawaranSalesMainCtrl getT21DaftarPenawaranSalesMainCtrl() {
		return this.T21DaftarPenawaranSalesMainCtrl;
	}
	
	
}
