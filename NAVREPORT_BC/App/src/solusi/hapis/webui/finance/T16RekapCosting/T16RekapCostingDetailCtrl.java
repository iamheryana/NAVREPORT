package solusi.hapis.webui.finance.T16RekapCosting;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.T16RekapCosting;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ListBoxUtil;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 19-04-2021
 */

public class T16RekapCostingDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT16RekapCostingDetail;
	
	protected Borderlayout borderlayout_T16RekapCostingDetail;
	
	// Data Entry Component
	
	protected Bandbox cmbCompany;	
	protected Bandbox cmbSales;
	protected Textbox txtNoBso;	
	protected Textbox txtNoSo;	
	protected Datebox dbTglSo;
	protected Textbox txtCustomer;	
	protected Textbox txtNoPoCust;	
	protected Decimalbox dcbAmount;	
	protected Textbox txtNoInvoice;	
	protected Datebox dbTglInvoice;	
	protected Datebox dbTglLunas;	
	protected Decimalbox dcbPcnKomisi;	
	protected Decimalbox dcbAmountKomisi;
	//protected Datebox dbTglByrKomisi;
	
	
	protected Bandbox cmbMasaKomisi;
	protected Textbox txtTahunKomisi;
	
	protected Textbox txtFlagKomisi;
	protected Checkbox chbFlagKomisi;	
	protected Decimalbox dcbPcnTqs;
	protected Decimalbox dcbAmountTqs;
	
	//protected Datebox dbTglByrTqs;
	
	protected Bandbox cmbMasaTqs;
	protected Textbox txtTahunTqs;
	
	protected Textbox txtFlagTqs;
	protected Checkbox chbFlagTqs;
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T16RekapCostingMainCtrl T16RekapCostingMainCtrl;

	protected Listbox list_Company;
	protected Listbox list_Sales;
	protected Listbox list_MasaKomisi;
	protected Listbox list_MasaTqs;
	
	private SelectQueryService selectQueryService;
	
	/**
	 * default constructor.<br>
	 */
	public T16RekapCostingDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		this.self.setAttribute("controller", this, false);


		if (arg.containsKey("ModuleMainController")) {
			setT16RekapCostingMainCtrl((T16RekapCostingMainCtrl) arg
					.get("ModuleMainController"));

			getT16RekapCostingMainCtrl().setT16RekapCostingDetailCtrl(this);
		}
		
		windowT16RekapCostingDetail.addEventListener(Events.ON_OK, onEnterForm());
	}


	public void onCreate$windowT16RekapCostingDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		binder.loadAll();
		doRenderCombo();
		doFitSize(event);
	}

	public void doRenderCombo() {
		ListBoxUtil.resetList(list_Company);
		
		T16RekapCosting anT02 = getT16RekapCosting();

		Listitem vListCompany = null;

		Listitem vListAUTOJAYA = list_Company.appendItem("AUTOJAYA", "AUTOJAYA");
		Listitem vListSOLUSI = list_Company.appendItem("SOLUSI", "SOLUSI");
		
		if (anT02 != null) {
			if (anT02.getCompany() != null) {
				if (anT02.getCompany().equals("AUTOJAYA")) {
					vListCompany = vListAUTOJAYA;
				} else {
					vListCompany = vListSOLUSI;
				}
			}
		}
		
		
		cmbCompany.setValue(vListCompany.getLabel());
		list_Company.setSelectedItem(vListCompany);


		if (anT02 != null) {
			if(CommonUtils.isNotEmpty(anT02.getFlagKomisi()) == true){
				if(anT02.getFlagKomisi().equals("Y") == true)
					chbFlagKomisi.setChecked(true);
				else
					chbFlagKomisi.setChecked(false);
			} else {
				chbFlagKomisi.setChecked(false);
			}
			
			
			if(CommonUtils.isNotEmpty(anT02.getFlagTqs()) == true){
				if(anT02.getFlagTqs().equals("Y") == true)
					chbFlagTqs.setChecked(true);
				else
					chbFlagTqs.setChecked(false);
			} else {
				chbFlagTqs.setChecked(false);
			}
		}

		//Render Combo Sales
		ListBoxUtil.resetList(list_Sales);

		Listitem vListSales = null;

		int a = 0;
		List<Object[]> vResult = selectQueryService.QuerySalepersonCosting();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				
				Listitem vList = list_Sales.appendItem(aRslt[0].toString(),aRslt[1].toString());
				if (a == 0) {
					vListSales = vList;
					a++;
				}
				
				if (getT16RekapCosting() != null) {
					if (getT16RekapCosting().getSales() != null) {
						if (getT16RekapCosting().getSales().equals(aRslt[1].toString())) {
							vListSales = vList;
						}
					}
				}
			}
		}
		
		cmbSales.setValue(vListSales.getLabel());
		list_Sales.setSelectedItem(vListSales);
		//===================================================================================================
		
		//Render Combo Masa Komisi
		ListBoxUtil.resetList(list_MasaKomisi);

		Listitem vListMasaKomisi = null;

		int aMK = 0;
		List<Object[]> vResultMasaKomisi = selectQueryService.QueryBulanForCosting();
		if(CommonUtils.isNotEmpty(vResultMasaKomisi)){
			for(Object[] aRsltMasaKomisi : vResultMasaKomisi){
				//System.out.println("aRsltMasaKomisi 0 : "+aRsltMasaKomisi[0].toString());
				//System.out.println("aRsltMasaKomisi 1 : "+aRsltMasaKomisi[1].toString());
				Listitem vListMK = list_MasaKomisi.appendItem(aRsltMasaKomisi[0].toString(),aRsltMasaKomisi[1].toString());
				if (aMK == 0) {
					vListMasaKomisi = vListMK;
					aMK++;
				}
				
				if (getT16RekapCosting() != null) {
					if (getT16RekapCosting().getMasaKomisi() != null) {
						if (getT16RekapCosting().getMasaKomisi().equals(aRsltMasaKomisi[1].toString())) {
							vListMasaKomisi = vListMK;
						}
					}
				}
			}
		}
		
		cmbMasaKomisi.setValue(vListMasaKomisi.getLabel());
		list_MasaKomisi.setSelectedItem(vListMasaKomisi);
		//===================================================================================================
		
		//Render Combo Masa TQS
		ListBoxUtil.resetList(list_MasaTqs);

		Listitem vListMasaTqs = null;

		int aMTqs = 0;
		List<Object[]> vResultMasaTqs = selectQueryService.QueryBulanForCosting();
		if(CommonUtils.isNotEmpty(vResultMasaTqs)){
			for(Object[] aRsltMasaTqs : vResultMasaTqs){
				Listitem vListMTqs = list_MasaTqs.appendItem(aRsltMasaTqs[0].toString(),aRsltMasaTqs[1].toString());
				if (aMTqs == 0) {
					vListMasaTqs = vListMTqs;
					aMTqs++;
				}
				
				if (getT16RekapCosting() != null) {
					if (getT16RekapCosting().getMasaTqs() != null) {
						if (getT16RekapCosting().getMasaTqs().equals(aRsltMasaTqs[1].toString())) {
							vListMasaTqs = vListMTqs;
						}
					}
				}
			}
		}
		
		cmbMasaTqs.setValue(vListMasaTqs.getLabel());
		list_MasaTqs.setSelectedItem(vListMasaTqs);
		//===================================================================================================
	}
	
//	public void onChange$txtSales(Event event) {
//		if(CommonUtils.isNotEmpty(txtSales.getValue())){
//			List<Object[]> vResult = selectQueryService.QuerySalesmanName();
//			
//			String vSalesName = "";
//			String vKodeSales = txtSales.getValue().toUpperCase();
//			if(CommonUtils.isNotEmpty(vResult)){
//				for(Object[] aRslt : vResult){
//					if (aRslt[1].toString().equals(vKodeSales)){
//						vSalesName = aRslt[0].toString();
//					}					
//				}
//			}
//			
//			txtSalesName.setValue(vSalesName);
//			getT16RekapCosting().setSalesName(vSalesName);
//			
//		}
//	}
	
//	public void onChange$dbTglByrKomisi(Event event) {
//		if(CommonUtils.isNotEmpty(dbTglByrKomisi.getValue())){
//			chbFlagKomisi.setChecked(true);
//			
//			T16RekapCosting data = getT16RekapCosting();
//			data.setFlagKomisi("Y");			
//			txtFlagKomisi.setValue(data.getFlagKomisi());
//			
//			setT16RekapCosting(data);
//		} else {
//			chbFlagKomisi.setChecked(false);
//			
//			T16RekapCosting data = getT16RekapCosting();
//			data.setFlagKomisi("N");			
//			txtFlagKomisi.setValue(data.getFlagKomisi());
//			
//			setT16RekapCosting(data);
//		}
//	}
	
	
//	public void onChange$dbTglByrTqs(Event event) {
//		if(CommonUtils.isNotEmpty(dbTglByrTqs.getValue())){
//			chbFlagTqs.setChecked(true);
//			
//			T16RekapCosting data = getT16RekapCosting();
//			data.setFlagTqs("Y");			
//			txtFlagTqs.setValue(data.getFlagTqs());
//			
//			setT16RekapCosting(data);
//		} else {
//			chbFlagTqs.setChecked(false);
//			
//			T16RekapCosting data = getT16RekapCosting();
//			data.setFlagTqs("N");			
//			txtFlagTqs.setValue(data.getFlagTqs());
//			
//			setT16RekapCosting(data);
//		}
//	}
	
	
	public void onChange$dcbPcnKomisi(Event event) {
		if (CommonUtils.isNotEmpty(dcbPcnKomisi.getValue()) && CommonUtils.isNotEmpty(dcbAmount.getValue())) {
			
			
			BigDecimal AmtKomisi = dcbAmount.getValue();
			BigDecimal PcnKomisi = dcbPcnKomisi.getValue();
			
			AmtKomisi = (PcnKomisi.multiply(AmtKomisi)).divide(new BigDecimal(100));
			
			getT16RekapCosting().setAmountKomisi(AmtKomisi);
			dcbAmountKomisi.setValue(AmtKomisi);
			
		}
	}
		

	public void onChange$dcbPcnTqs(Event event) {
		if (CommonUtils.isNotEmpty(dcbPcnTqs.getValue()) && CommonUtils.isNotEmpty(dcbAmount.getValue())) {
			
			
			BigDecimal AmtTqs = dcbAmount.getValue();
			BigDecimal PcnTqs = dcbPcnTqs.getValue();
			
			AmtTqs = (PcnTqs.multiply(AmtTqs)).divide(new BigDecimal(100));
			
			getT16RekapCosting().setAmountTqs(AmtTqs);
			dcbAmountTqs.setValue(AmtTqs);
			
		}
	}
	
	public void onCheck$chbFlagKomisi(Event event) throws Exception {
		T16RekapCosting data = getT16RekapCosting();
		data.setFlagKomisi(chbFlagKomisi.isChecked()?"Y":"N");
		
		txtFlagKomisi.setValue(data.getFlagKomisi());
		
		setT16RekapCosting(data);
	}
	
	public void onCheck$chbFlagTqs(Event event) throws Exception {
		T16RekapCosting data = getT16RekapCosting();
		data.setFlagTqs(chbFlagTqs.isChecked()?"Y":"N");
		
		txtFlagKomisi.setValue(data.getFlagTqs());
		
		setT16RekapCosting(data);
	}
	

	public void onSelect$list_Sales(Event event) throws Exception {
		T16RekapCosting data = getT16RekapCosting();

		data.setSales(list_Sales.getSelectedItem().getValue().toString());
		setT16RekapCosting(data);
	}
	
	public void onSelect$list_Company(Event event) throws Exception {
		T16RekapCosting data = getT16RekapCosting();

		data.setCompany(list_Company.getSelectedItem().getValue().toString());
		setT16RekapCosting(data);
	}
	
	
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T16RekapCostingDetail.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT16RekapCostingDetail.invalidate();
	}

	
	public void doRenderMode(String pMode) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				

				cmbCompany.setDisabled(true);
				cmbSales.setDisabled(true);
					
				txtNoBso.setReadonly(true);	
				txtNoSo.setReadonly(true);	
				dbTglSo.setDisabled(true);				
				txtCustomer.setReadonly(true);		
				txtNoPoCust.setReadonly(true);				
				dcbAmount.setReadonly(true);	
				txtNoInvoice.setReadonly(true);	
				dbTglInvoice.setDisabled(true);	
				dbTglLunas.setDisabled(true);	
				dcbPcnKomisi.setReadonly(true);	
				dcbAmountKomisi.setReadonly(true);
				//dbTglByrKomisi.setDisabled(true);
				
				cmbMasaKomisi.setDisabled(true);
				txtTahunKomisi.setReadonly(true);
				
				txtFlagKomisi.setReadonly(true);
				chbFlagKomisi.setDisabled(true);	
				dcbPcnTqs.setReadonly(true);
				dcbAmountTqs.setReadonly(true);
				//dbTglByrTqs.setDisabled(true);	
				
				cmbMasaTqs.setDisabled(true);
				txtTahunTqs.setReadonly(true);
				
				txtFlagTqs.setReadonly(true);
				//chbFlagTqs.setDisabled(true);	
				
					
				

			}
			
			if(pMode.equals("New")){
				cmbCompany.setDisabled(false);
				cmbSales.setDisabled(false);	
				txtNoBso.setReadonly(false);
				txtNoSo.setReadonly(false);	
				dbTglSo.setDisabled(false);				
				txtCustomer.setReadonly(false);		
				txtNoPoCust.setReadonly(false);				
				dcbAmount.setReadonly(false);	
				txtNoInvoice.setReadonly(false);	
				dbTglInvoice.setDisabled(false);	
				dbTglLunas.setDisabled(false);	
				dcbPcnKomisi.setReadonly(false);	
				dcbAmountKomisi.setReadonly(false);
				//dbTglByrKomisi.setDisabled(false);	
				
				cmbMasaKomisi.setDisabled(false);
				txtTahunKomisi.setReadonly(false);
				
				txtFlagKomisi.setReadonly(false);
				//chbFlagKomisi.setDisabled(false);	
				dcbPcnTqs.setReadonly(false);
				dcbAmountTqs.setReadonly(false);
				//dbTglByrTqs.setDisabled(false);	
				
				cmbMasaTqs.setDisabled(false);
				txtTahunTqs.setReadonly(false);
				
				txtFlagTqs.setReadonly(false);
				//chbFlagTqs.setDisabled(false);
				
				// set focus 
				cmbSales.setFocus(true);				
			}
			
			if(pMode.equals("Edit")){				
				// set read only key
				cmbCompany.setDisabled(false);
				cmbSales.setDisabled(false);
		
				txtNoBso.setReadonly(false);
				txtNoSo.setReadonly(false);	
				dbTglSo.setDisabled(false);				
				txtCustomer.setReadonly(false);		
				txtNoPoCust.setReadonly(false);				
				dcbAmount.setReadonly(false);	
				txtNoInvoice.setReadonly(false);	
				dbTglInvoice.setDisabled(false);	
				dbTglLunas.setDisabled(false);	
				dcbPcnKomisi.setReadonly(true);	
				dcbAmountKomisi.setReadonly(true);
				//dbTglByrKomisi.setDisabled(true);	
				txtFlagKomisi.setReadonly(true);
				
				cmbMasaKomisi.setDisabled(true);
				txtTahunKomisi.setReadonly(true);
				
				//chbFlagKomisi.setDisabled(true);	
				dcbPcnTqs.setReadonly(true);
				dcbAmountTqs.setReadonly(true);
				//dbTglByrTqs.setDisabled(true);	
				txtFlagTqs.setReadonly(true);
				cmbMasaTqs.setDisabled(true);
				txtTahunTqs.setReadonly(true);
				//chbFlagTqs.setDisabled(true);
				
				// set focus 
				cmbSales.setFocus(true);					
			}
			
			if(pMode.equals("EditKomisi")){				
				// set read only key
				cmbCompany.setDisabled(true);
				cmbSales.setDisabled(true);
				
				txtNoBso.setReadonly(true);
				txtNoSo.setReadonly(true);	
				dbTglSo.setDisabled(true);				
				txtCustomer.setReadonly(true);		
				txtNoPoCust.setReadonly(true);				
				dcbAmount.setReadonly(true);	
				txtNoInvoice.setReadonly(true);	
				dbTglInvoice.setDisabled(true);	
				dbTglLunas.setDisabled(true);	
				dcbPcnKomisi.setReadonly(false);	
				dcbAmountKomisi.setReadonly(false);
				//dbTglByrKomisi.setDisabled(false);	
				txtFlagKomisi.setReadonly(false);
				
				cmbMasaKomisi.setDisabled(false);
				txtTahunKomisi.setReadonly(false);
				
				//chbFlagKomisi.setDisabled(false);	
				dcbPcnTqs.setReadonly(false);
				dcbAmountTqs.setReadonly(false);
				//dbTglByrTqs.setDisabled(false);	
				txtFlagTqs.setReadonly(false);
				
				cmbMasaTqs.setDisabled(false);
				txtTahunTqs.setReadonly(false);
				//chbFlagTqs.setDisabled(true);
				
				// set focus 
				dcbPcnKomisi.setFocus(true);					
			}
			

		}
	}
	
	public void doRenderDefaultEditKomis(){
		if(CommonUtils.isNotEmpty(txtTahunKomisi.getValue())){
			
		} else {
			
			Calendar cTgl = Calendar.getInstance();		
			cTgl.setTime(new Date());
			int yearTglFrom = cTgl.get(Calendar.YEAR);
			
			T16RekapCosting data = getT16RekapCosting();
			data.setTahunKomisi(Integer.toString(yearTglFrom));	
			txtTahunKomisi.setValue(Integer.toString(yearTglFrom));
			
			if(CommonUtils.isNotEmpty(list_MasaKomisi.getSelectedItem().getValue()) &&
					CommonUtils.isNotEmpty(txtTahunKomisi.getValue())){
				chbFlagKomisi.setChecked(true);
				
				
				data.setFlagKomisi("Y");			
				txtFlagKomisi.setValue(data.getFlagKomisi());
				
				setT16RekapCosting(data);
			} else {
				chbFlagKomisi.setChecked(false);
				
				data.setFlagKomisi("N");			
				txtFlagKomisi.setValue(data.getFlagKomisi());
				
				setT16RekapCosting(data);
				
			}
		}
		
		
		if(CommonUtils.isNotEmpty(txtTahunTqs.getValue())){
			
		} else {
			
			Calendar cTgl = Calendar.getInstance();		
			cTgl.setTime(new Date());
			int yearTglCurr = cTgl.get(Calendar.YEAR);
			int monthTglCurr = cTgl.get(Calendar.MONTH);
			
			monthTglCurr = monthTglCurr + 1;
			
			
			T16RekapCosting data = getT16RekapCosting();
			
			
			Date vTglInv  = data.getTglInvoice();
			Calendar cTglInv = Calendar.getInstance();		
			cTglInv.setTime(vTglInv);
			int yearTglInv = cTglInv.get(Calendar.YEAR);
			
		
			
			int yearTQS = yearTglCurr + 1;
			if(yearTglInv < yearTglCurr){
				if(monthTglCurr < 4){
					yearTQS = yearTglCurr;
				} else {
					yearTQS = yearTglCurr + 1;
				}
			} else {
				yearTQS = yearTglCurr + 1;
			}
			
//			System.out.println("yearTglCurr : "+yearTglCurr);
//			System.out.println("yearTglInv : "+yearTglInv);
//			System.out.println("monthTglCurr : "+monthTglCurr);
//			System.out.println("yearTQS : "+yearTQS);

			
			data.setTahunTqs(Integer.toString(yearTQS));	
			txtTahunTqs.setValue(Integer.toString(yearTQS));
			
			if(CommonUtils.isNotEmpty(list_MasaTqs.getSelectedItem().getValue()) &&
					CommonUtils.isNotEmpty(txtTahunTqs.getValue())){
				chbFlagTqs.setChecked(true);
				
				
				data.setFlagTqs("Y");			
				txtFlagTqs.setValue(data.getFlagTqs());
				
				setT16RekapCosting(data);
			} else {
				chbFlagTqs.setChecked(false);
				
				data.setFlagTqs("N");			
				txtFlagTqs.setValue(data.getFlagTqs());
				
				setT16RekapCosting(data);
				
			}
		}
	}
	
	public void onSelect$list_MasaKomisi(Event event) throws Exception {
		T16RekapCosting data = getT16RekapCosting();

		data.setMasaKomisi(list_MasaKomisi.getSelectedItem().getValue().toString());
		setT16RekapCosting(data);
		
		if(CommonUtils.isNotEmpty(list_MasaKomisi.getSelectedItem().getValue()) &&
				CommonUtils.isNotEmpty(txtTahunKomisi.getValue())){
			
							
			List<Object[]> vStatusClose = selectQueryService.QueryPeriodeClosingForCosting(list_MasaKomisi.getSelectedItem().getValue().toString(), txtTahunKomisi.getValue());
			String vCloseKomisi = "N";
			
			if(CommonUtils.isNotEmpty(vStatusClose)){
				for(Object[] aRsltStatus : vStatusClose){
					vCloseKomisi = aRsltStatus[0].toString();
					
				}
			}
			
			if (vCloseKomisi.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage("Periode Komisi "+cmbMasaKomisi.getValue()+" "+txtTahunKomisi.getValue()+" sudah closing");
				
				cmbMasaKomisi.setValue("<blank>");
				list_MasaKomisi.setSelectedIndex(0);
				data.setMasaKomisi(list_MasaKomisi.getSelectedItem().getValue().toString());
				
				chbFlagKomisi.setChecked(false);
				
				data.setFlagKomisi("N");			
				txtFlagKomisi.setValue(data.getFlagKomisi());
				
				setT16RekapCosting(data);
			} else {
			
			
				chbFlagKomisi.setChecked(true);
				
				
				data.setFlagKomisi("Y");			
				txtFlagKomisi.setValue(data.getFlagKomisi());
				
				setT16RekapCosting(data);
			}
		} else {
			chbFlagKomisi.setChecked(false);
			
			data.setFlagKomisi("N");			
			txtFlagKomisi.setValue(data.getFlagKomisi());
			
			setT16RekapCosting(data);
			
		}
	}
	
	public void onChange$txtTahunKomisi(Event event) throws WrongValueException, InterruptedException {		
		T16RekapCosting data = getT16RekapCosting();
		
		if(CommonUtils.isNotEmpty(list_MasaKomisi.getSelectedItem().getValue()) &&
				CommonUtils.isNotEmpty(txtTahunKomisi.getValue())){
			List<Object[]> vStatusClose = selectQueryService.QueryPeriodeClosingForCosting(list_MasaKomisi.getSelectedItem().getValue().toString(), txtTahunKomisi.getValue());
			String vCloseKomisi = "N";
			
			if(CommonUtils.isNotEmpty(vStatusClose)){
				for(Object[] aRsltStatus : vStatusClose){
					vCloseKomisi = aRsltStatus[0].toString();
					
				}
			}
			
			if (vCloseKomisi.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage("Periode Komisi "+cmbMasaKomisi.getValue()+" "+txtTahunKomisi.getValue()+" sudah closing");
				
				cmbMasaKomisi.setValue("<blank>");
				list_MasaKomisi.setSelectedIndex(0);
				data.setMasaKomisi(list_MasaKomisi.getSelectedItem().getValue().toString());
				
				chbFlagKomisi.setChecked(false);
				
				data.setFlagKomisi("N");			
				txtFlagKomisi.setValue(data.getFlagKomisi());
				
				setT16RekapCosting(data);
			} else {
				chbFlagKomisi.setChecked(true);
				
				data.setFlagKomisi("Y");			
				txtFlagKomisi.setValue(data.getFlagKomisi());
				
				setT16RekapCosting(data);
				
			}
			
			
		} else {
			chbFlagKomisi.setChecked(false);
			
			data.setFlagKomisi("N");			
			txtFlagKomisi.setValue(data.getFlagKomisi());
			
			setT16RekapCosting(data);
			
		}
	}
	
	public void onSelect$list_MasaTqs(Event event) throws Exception {
		T16RekapCosting data = getT16RekapCosting();

		data.setMasaTqs(list_MasaTqs.getSelectedItem().getValue().toString());
		setT16RekapCosting(data);
		
		if(CommonUtils.isNotEmpty(list_MasaTqs.getSelectedItem().getValue()) &&
				CommonUtils.isNotEmpty(txtTahunTqs.getValue())){
			
			List<Object[]> vStatusClose = selectQueryService.QueryPeriodeClosingForCosting(list_MasaTqs.getSelectedItem().getValue().toString(), txtTahunTqs.getValue());
			String vCloseTqs = "N";
			
			if(CommonUtils.isNotEmpty(vStatusClose)){
				for(Object[] aRsltStatus : vStatusClose){
					vCloseTqs = aRsltStatus[1].toString();
					
				}
			}
			
			if (vCloseTqs.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage("Periode TQS "+cmbMasaTqs.getValue()+" "+txtTahunTqs.getValue()+" sudah closing");
				
				cmbMasaTqs.setValue("<blank>");
				list_MasaTqs.setSelectedIndex(0);
				data.setMasaTqs(list_MasaTqs.getSelectedItem().getValue().toString());
				
				chbFlagTqs.setChecked(false);
				
				data.setFlagTqs("N");			
				txtFlagTqs.setValue(data.getFlagTqs());
				
				setT16RekapCosting(data);
			} else {
			
				chbFlagTqs.setChecked(true);
				
				data.setFlagKomisi("Y");			
				txtFlagTqs.setValue(data.getFlagTqs());
				
				setT16RekapCosting(data);				
			
			}
			
			
			
			
			
		} else {
			chbFlagTqs.setChecked(false);
			
			data.setFlagTqs("N");			
			txtFlagTqs.setValue(data.getFlagTqs());
			
			setT16RekapCosting(data);
			
		}
	}
	
	public void onChange$txtTahunTqs(Event event) throws WrongValueException, InterruptedException {		
		T16RekapCosting data = getT16RekapCosting();

		
		if(CommonUtils.isNotEmpty(list_MasaTqs.getSelectedItem().getValue()) &&
				CommonUtils.isNotEmpty(txtTahunTqs.getValue())){

			List<Object[]> vStatusClose = selectQueryService.QueryPeriodeClosingForCosting(list_MasaTqs.getSelectedItem().getValue().toString(), txtTahunTqs.getValue());
			String vCloseTqs = "N";
			
			if(CommonUtils.isNotEmpty(vStatusClose)){
				for(Object[] aRsltStatus : vStatusClose){
					vCloseTqs = aRsltStatus[1].toString();
					
				}
			}
			
			if (vCloseTqs.equals("Y") == true){
				ZksampleMessageUtils.showErrorMessage("Periode TQS "+cmbMasaTqs.getValue()+" "+txtTahunTqs.getValue()+" sudah closing");
				
				cmbMasaTqs.setValue("<blank>");
				list_MasaTqs.setSelectedIndex(0);
				data.setMasaTqs(list_MasaTqs.getSelectedItem().getValue().toString());
				
				chbFlagTqs.setChecked(false);
				
				data.setFlagTqs("N");			
				txtFlagTqs.setValue(data.getFlagTqs());
				
				setT16RekapCosting(data);
			} else {
			
				chbFlagTqs.setChecked(true);
				
				data.setFlagTqs("Y");			
				txtFlagTqs.setValue(data.getFlagTqs());
				
				setT16RekapCosting(data);				
			
			}
			
			// ============================================
			
		} else {
			chbFlagTqs.setChecked(false);
			
			data.setFlagTqs("N");			
			txtFlagTqs.setValue(data.getFlagTqs());
			
			setT16RekapCosting(data);
			
		}
	}
	
	public String validasiBusinessRules(){
/*		

		if(	(CommonUtils.isNotEmpty(list_MasaKomisi.getSelectedItem().getValue()) == true) 	&&
			(CommonUtils.isNotEmpty(txtTahunKomisi.getValue()) == true)){
			
			
			List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(list_MasaKomisi.getSelectedItem().getValue().toString(), txtTahunKomisi.getValue());
			String vCloseKomisi = "N";
			
			if(CommonUtils.isNotEmpty(vStatusClose)){
				for(Object[] aRsltStatus : vStatusClose){
					vCloseKomisi = aRsltStatus[0].toString();
					
				}
			}
			
			//System.out.println("vCloseKomisi = "+vCloseKomisi);
			if (vCloseKomisi.equals("Y") == true){
				return "Periode Komisi "+cmbMasaKomisi.getValue()+" "+txtTahunKomisi.getValue()+" sudah closing";
			}
		} 
		
		if(	(CommonUtils.isNotEmpty(list_MasaTqs.getSelectedItem().getValue()) == true) 	&&
				(CommonUtils.isNotEmpty(txtTahunTqs.getValue()) == true)){
				
				
				List<Object[]> vStatusClose = selectQueryNavReportService.CekPeriodeClosingCosting(list_MasaTqs.getSelectedItem().getValue().toString(), txtTahunTqs.getValue());
				String vCloseTqs = "N";
				
				if(CommonUtils.isNotEmpty(vStatusClose)){
					for(Object[] aRsltStatus : vStatusClose){
						vCloseTqs = aRsltStatus[1].toString();
					}
				}
				
				
				if (vCloseTqs.equals("Y") == true){
					return "Periode TQS "+cmbMasaTqs.getValue()+" "+txtTahunTqs.getValue()+" sudah closing";
				}
			} 
*/
		return null;
	}
    
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT16RekapCostingMainCtrl().onClick$btnSave(event);
			}
		};
    }


	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T16RekapCosting getT16RekapCosting() {
		return getT16RekapCostingMainCtrl().getSelectedT16RekapCosting();
	}

	public void setT16RekapCosting(T16RekapCosting selectedT16RekapCosting) {
		getT16RekapCostingMainCtrl().setSelectedT16RekapCosting(selectedT16RekapCosting);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT16RekapCostingMainCtrl(T16RekapCostingMainCtrl T16RekapCostingMainCtrl) {
		this.T16RekapCostingMainCtrl = T16RekapCostingMainCtrl;
	}

	public T16RekapCostingMainCtrl getT16RekapCostingMainCtrl() {
		return this.T16RekapCostingMainCtrl;
	}


	
	
}
