package solusi.hapis.webui.sales.Costing.T29Costing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
//import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
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
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import solusi.hapis.backend.navbi.model.T29CostingH;
import solusi.hapis.backend.navbi.model.T30CostingDHw3ps;
import solusi.hapis.backend.navbi.model.T31CostingDAcsps;
import solusi.hapis.backend.navbi.model.T32CostingDOwnsw;
import solusi.hapis.backend.navbi.model.T33CostingDOther;
import solusi.hapis.backend.navbi.model.T34CostingDPayment;
import solusi.hapis.backend.navbi.model.V01CustomerNav;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.sales.T21DaftarPenawaranSales.V01CustomerNavLOV;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ListBoxUtil;
import solusi.hapis.webui.util.MultiLineMessageBox;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 24-07-2024
 */

public class T29CostingDetailCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -8352659530536077973L;
	
	protected Window windowT29CostingDetail;
	
	protected Borderlayout borderlayout_T29CostingDetail;
	
	// Data Entry Component

	protected Bandbox cmbCompany;	
	protected Bandbox cmbSalesman;
	
	protected Textbox txtNoCosting;
	protected Textbox txtNoBso;	
	protected Textbox txtNoSo;	
	protected Textbox txtNoPoCustomer;
	protected Textbox txtCustomer;
	protected Textbox txtNoPenawaran;
	
	
	protected Decimalbox dcbSalesHW3PS;	
	protected Decimalbox dcbCOGSHW3PS;
	
	protected Decimalbox dcbsalesACSPS;
	protected Decimalbox dcbsalesOWNSW;
	protected Decimalbox dcbcogsOTHERS;
	
	protected Decimalbox dcbSalesHW3PS_2;
	
	protected Decimalbox dcbTotalCOGS_2;
	protected Decimalbox dcbMarginPcnNonacsps;
	
	
	protected Decimalbox dcbTotalSales;
	protected Decimalbox dcbTotalCOGS;
	protected Decimalbox dcbMarginPcn;
	
	protected Decimalbox dcbIncentiveFormulaHw3ps;
	protected Decimalbox dcbIncentiveApproveHw3ps;	
	protected Decimalbox dcbIncentiveNonsalesHw3ps;
	protected Decimalbox dcbAmountNonsalesHw3ps	;	
	protected Decimalbox dcbIncentiveKomisiHw3ps;
	protected Decimalbox dcbAmountKomisiHw3ps;	
	protected Decimalbox dcbIncentiveSbonusHw3ps;
	protected Decimalbox dcbAmountSbonusHw3ps;
	
	protected Decimalbox dcbsalesACSPS_2;
	protected Decimalbox dcbTotalCOGSACSPS;
	protected Decimalbox dcbMarginPcnAcsps;
	
	protected Decimalbox dcbIncentiveFormulaAcsps;
	protected Decimalbox dcbIncentiveApproveAcsps;	
	protected Decimalbox dcbIncentiveNonsalesAcsps;
	protected Decimalbox dcbAmountNonsalesAcsps	;	
	protected Decimalbox dcbIncentiveKomisiAcsps;
	protected Decimalbox dcbAmountKomisiAcsps;	
	protected Decimalbox dcbIncentiveSbonusAcsps;
	protected Decimalbox dcbAmountSbonusAcsps;
	
	protected Decimalbox dcbsalesOWNSW_2;
	protected Decimalbox dcbTotalCOGSOwnsw;
	protected Decimalbox dcbMarginPcnOwnsw;
	
	protected Decimalbox dcbIncentiveFormulaOwnsw;
	protected Decimalbox dcbIncentiveApproveOwnsw;	
	protected Decimalbox dcbIncentiveNonsalesOwnsw;
	protected Decimalbox dcbAmountNonsalesOwnsw	;	
	protected Decimalbox dcbIncentiveKomisiOwnsw;
	protected Decimalbox dcbAmountKomisiOwnsw;	
	protected Decimalbox dcbIncentiveSbonusOwnsw;
	protected Decimalbox dcbAmountSbonusOwnsw;
	
	protected Decimalbox dcbAmountTqsHw3ps;
	protected Decimalbox dcbAmountTqsAcsps;
	protected Decimalbox dcbAmountTqsOwnsw;
	
	protected Decimalbox dcbAmountKomisi;
	protected Decimalbox dcbAmountKomisi_2;
	protected Decimalbox dcbAmountSbonus;
	protected Decimalbox dcbAmountSbonus_2;
	protected Decimalbox dcbAmountNonsales;
	protected Decimalbox dcbAmountNonsales_2;
	
	protected Decimalbox dcbAmountSalesTqs;
	
	
	protected Decimalbox dcbAmountInvoice;
	protected Decimalbox dcbAmountLunas;
	
	

	protected Decimalbox dcbCalculateTotalMarginPcn;
	protected Decimalbox dcbCalculateHw3PSMarginPcn;
	protected Decimalbox dcbCalculateTotalMarginOnlyPcn;
	protected Decimalbox dcbCalculateHw3PSMarginOnlyPcn;
	protected Decimalbox dcbCalculateHwMarginOnlyPcn;
 
	protected Datebox dbTglCosting;
	
	protected Label PeriodeKomisi;
	protected Label PeriodeKomisi2;
	protected Textbox txtMasaKomisi;
	protected Textbox txtTahunKomisi;
	protected Label PeriodeTqs;
	protected Label PeriodeTqs2;
	protected Textbox txtMasaTqs;
	protected Textbox txtTahunTqs;
	   
	protected Button btnSearchCustomerLOV;
	
	// Button tabel Detail 1 - T30CostingD_HW3PS
	protected Button btnNew;
	protected Button btnView;
	protected Button btnEdit;
	protected Button btnDelete;
	protected Button btnExport;
	protected Button btnImport;
	
	// Button tabel Detail 2 - T31CostingD_ACSPS
	protected Button btnNew_B;
	protected Button btnView_B;
	protected Button btnEdit_B;
	protected Button btnDelete_B;
	protected Button btnExport_B;
	protected Button btnImport_B;
	
	
	// Button tabel Detail 3 - T32CostingD_OWNSW
	protected Button btnNew_C;
	protected Button btnView_C;
	protected Button btnEdit_C;
	protected Button btnDelete_C;
	protected Button btnExport_C;
	protected Button btnImport_C;
	
	// Button tabel Detail 4 - T33CostingD_OTHER
	protected Button btnNew_D;
	protected Button btnView_D;
	protected Button btnEdit_D;
	protected Button btnDelete_D;
	protected Button btnExport_D;
	protected Button btnImport_D;
	
	
	// Button tabel Detail 5 - T34CostingD_PAYMENT
	protected Button btnNew_E;
	protected Button btnView_E;
	protected Button btnEdit_E;
	protected Button btnDelete_E;
		
	protected Button btnUploadBSO;
	protected Button btnDownloadBSO;
	
	protected Label lblFileBSO;
	protected Label lblFileInfoPrice;
	protected Label lblFilePoCustomer;
	
	protected Button btnUploadInfoPrice;
	protected Button btnUploadFilePOCustomer;
	
	
	protected Button btnRecalculate;
	protected Button btnRecalculate1;
	protected Button btnRecalculate2;
	protected Button btnRecalculate3;
	
	// Paging and list Detail 1 - T30CostingD_HW3PS
	protected Listheader listheader_T30CostingD_HW3PS_ItemDesc;
	protected Listheader listheader_T30CostingD_HW3PS_ItemNo;	
	protected Listheader listheader_T30CostingD_HW3PS_ItemCategory;
	protected Listheader listheader_T30CostingD_HW3PS_Qty;
	protected Listheader listheader_T30CostingD_HW3PS_SalesSatuan;
	protected Listheader listheader_T30CostingD_HW3PS_SalesTotal;
	protected Listheader listheader_T30CostingD_HW3PS_CogsSatuan;
	protected Listheader listheader_T30CostingD_HW3PS_CogsTotal;
	protected Listheader listheader_T30CostingD_HW3PS_Catatan;
	
	// Paging and list Detail 2 - T31CostingD_ACSPS
	protected Listheader listheader_T31CostingD_ACSPS_ItemDesc;
	protected Listheader listheader_T31CostingD_ACSPS_ItemNo;	
	protected Listheader listheader_T31CostingD_ACSPS_Qty;
	protected Listheader listheader_T31CostingD_ACSPS_SalesSatuan;
	protected Listheader listheader_T31CostingD_ACSPS_SalesTotal;
	protected Listheader listheader_T31CostingD_ACSPS_Catatan;
	
	// Paging and list Detail 3 - T32CostingD_OWNSW
	protected Listheader listheader_T32CostingD_OWNSW_ItemDesc;
	protected Listheader listheader_T32CostingD_OWNSW_ItemNo;	
	protected Listheader listheader_T32CostingD_OWNSW_Qty;
	protected Listheader listheader_T32CostingD_OWNSW_SalesSatuan;
	protected Listheader listheader_T32CostingD_OWNSW_SalesTotal;
	protected Listheader listheader_T32CostingD_OWNSW_Catatan;
	
	// Paging and list Detail 4 - T33CostingD_OTHER
	protected Listheader listheader_T33CostingD_OTHER_ItemDesc;
	protected Listheader listheader_T33CostingD_OTHER_ItemNo;	
	protected Listheader listheader_T33CostingD_OTHER_Qty;
	protected Listheader listheader_T33CostingD_OTHER_CogsSatuan;
	protected Listheader listheader_T33CostingD_OTHER_CogsTotal;
	protected Listheader listheader_T33CostingD_OTHER_Catatan;
		
	// Paging and list Detail 5 - T34CostingD_PAYMENT
	protected Listheader listheader_T34CostingD_PAYMENT_NoInvoice;	
	protected Listheader listheader_T34CostingD_PAYMENT_TglInvoice;
	protected Listheader listheader_T34CostingD_PAYMENT_NilaiInvoice;
	protected Listheader listheader_T34CostingD_PAYMENT_NoLunas;
	protected Listheader listheader_T34CostingD_PAYMENT_TglLunas;
	protected Listheader listheader_T34CostingD_PAYMENT_NilaiLunas;
	
	
	protected Paging paging_T30CostingD_HW3PS_List;
	private int start_T30CostingD_HW3PS_List;
	private List<T30CostingDHw3ps> list_T30CostingD_HW3PS_List = new ArrayList<T30CostingDHw3ps>();	
	private ListModelList modelList_T30CostingD_HW3PS_List = new ListModelList();
	protected Listbox listBox_T30CostingD_HW3PS;
	private List<T30CostingDHw3ps> list_Deleted_T30CostingD_HW3PS_List = new ArrayList<T30CostingDHw3ps>();
	
	protected Paging paging_T31CostingD_ACSPS_List;
	private int start_T31CostingD_ACSPS_List;
	private List<T31CostingDAcsps> list_T31CostingD_ACSPS_List = new ArrayList<T31CostingDAcsps>();
	private ListModelList modelList_T31CostingD_ACSPS_List = new ListModelList();
	protected Listbox listBox_T31CostingD_ACSPS;
	private List<T31CostingDAcsps> list_Deleted_T31CostingD_ACSPS_List = new ArrayList<T31CostingDAcsps>();
	
	protected Paging paging_T32CostingD_OWNSW_List;
	private int start_T32CostingD_OWNSW_List;
	private List<T32CostingDOwnsw> list_T32CostingD_OWNSW_List = new ArrayList<T32CostingDOwnsw>();
	private ListModelList modelList_T32CostingD_OWNSW_List = new ListModelList();
	protected Listbox listBox_T32CostingD_OWNSW;
	private List<T32CostingDOwnsw> list_Deleted_T32CostingD_OWNSW_List = new ArrayList<T32CostingDOwnsw>();

	protected Paging paging_T33CostingD_OTHER_List;
	private int start_T33CostingD_OTHER_List;
	private List<T33CostingDOther> list_T33CostingD_OTHER_List = new ArrayList<T33CostingDOther>();
	private ListModelList modelList_T33CostingD_OTHER_List = new ListModelList();
	protected Listbox listBox_T33CostingD_OTHER;
	private List<T33CostingDOther> list_Deleted_T33CostingD_OTHER_List = new ArrayList<T33CostingDOther>();
	
	protected Paging paging_T34CostingD_PAYMENT_List;
	private int start_T34CostingD_PAYMENT_List;
	private List<T34CostingDPayment> list_T34CostingD_PAYMENT_List = new ArrayList<T34CostingDPayment>();
	private ListModelList modelList_T34CostingD_PAYMENT_List = new ListModelList();
	protected Listbox listBox_T34CostingD_PAYMENT;
	private List<T34CostingDPayment> list_Deleted_T34CostingD_PAYMENT_List = new ArrayList<T34CostingDPayment>();
	
	
	private List<T30CostingDHw3ps> list_T30CostingD_HW3PS_List_Temp = new ArrayList<T30CostingDHw3ps>();
	private List<T31CostingDAcsps> list_T31CostingD_ACSPS_List_Temp = new ArrayList<T31CostingDAcsps>();
	private List<T32CostingDOwnsw> list_T32CostingD_OWNSW_List_Temp = new ArrayList<T32CostingDOwnsw>();
	
	protected Listbox list_Company;
	protected Listbox list_Sales;
	
	private SelectQueryService selectQueryService;
	
	//MathContext vPembulatanPcn = new MathContext(5);
	//MathContext vPembulatanAmount = new MathContext(5);
	
	// Databinding
	protected transient AnnotateDataBinder binder;
	private T29CostingMainCtrl T29CostingMainCtrl;

	/**
	 * default constructor.<br>
	 */
	public T29CostingDetailCtrl() {
		super();
	}

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);
		


		this.self.setAttribute("controller", this, false);
		
		// Paging and list Detail 1 - T30CostingD_HW3PS
		paging_T30CostingD_HW3PS_List.setPageSize(CommonUtils.PAGE_SIZE_DETAIL);
		paging_T30CostingD_HW3PS_List.setDetailed(true);
		paging_T30CostingD_HW3PS_List.addEventListener("onPaging",
				onPaging_T30CostingD_HW3PS_List());
		listBox_T30CostingD_HW3PS.setItemRenderer(renderTable_T30CostingD_HW3PS());

		// Paging and list Detail 2 - T31CostingD_ACSPS
		paging_T31CostingD_ACSPS_List.setPageSize(CommonUtils.PAGE_SIZE_DETAIL);
		paging_T31CostingD_ACSPS_List.setDetailed(true);
		paging_T31CostingD_ACSPS_List.addEventListener("onPaging",
				onPaging_T31CostingD_ACSPS_List());
		listBox_T31CostingD_ACSPS.setItemRenderer(renderTable_T31CostingD_ACSPS());
		
		// Paging and list Detail 3 - T32CostingD_OWNSW
		paging_T32CostingD_OWNSW_List.setPageSize(CommonUtils.PAGE_SIZE_DETAIL);
		paging_T32CostingD_OWNSW_List.setDetailed(true);
		paging_T32CostingD_OWNSW_List.addEventListener("onPaging",
				onPaging_T32CostingD_OWNSW_List());
		listBox_T32CostingD_OWNSW.setItemRenderer(renderTable_T32CostingD_OWNSW());
		
		// Paging and list Detail 4 - T33CostingD_OTHER
		paging_T33CostingD_OTHER_List.setPageSize(CommonUtils.PAGE_SIZE_DETAIL);
		paging_T33CostingD_OTHER_List.setDetailed(true);
		paging_T33CostingD_OTHER_List.addEventListener("onPaging",
				onPaging_T33CostingD_OTHER_List());
		listBox_T33CostingD_OTHER.setItemRenderer(renderTable_T33CostingD_OTHER());
		
		// Paging and list Detail 5 - T34CostingD_PAYMENT
		paging_T34CostingD_PAYMENT_List.setPageSize(CommonUtils.PAGE_SIZE_DETAIL);
		paging_T34CostingD_PAYMENT_List.setDetailed(true);
		paging_T34CostingD_PAYMENT_List.addEventListener("onPaging",
				onPaging_T34CostingD_PAYMENT_List());
		listBox_T34CostingD_PAYMENT.setItemRenderer(renderTable_T34CostingD_PAYMENT());
		

		if (arg.containsKey("ModuleMainController")) {
			setT29CostingMainCtrl((T29CostingMainCtrl) arg
					.get("ModuleMainController"));

			getT29CostingMainCtrl().setT29CostingDetailCtrl(this);
		}
		
		windowT29CostingDetail.addEventListener(Events.ON_OK, onEnterForm());
	}

   public void TampilkanPaidOn() {
	  String vStatus = this.getT29CostingH().getFlagStatus();
	  if (vStatus.equals("PAID")) {
	     this.PeriodeKomisi.setVisible(true);
	     this.PeriodeKomisi2.setVisible(true);
	     this.txtMasaKomisi.setVisible(true);
	     this.txtTahunKomisi.setVisible(true);
	     this.PeriodeTqs.setVisible(true);
	     this.PeriodeTqs2.setVisible(true);
	     this.txtMasaTqs.setVisible(true);
	     this.txtTahunTqs.setVisible(true);
	  } else {
	     this.PeriodeKomisi.setVisible(false);
	     this.PeriodeKomisi2.setVisible(false);
	     this.txtMasaKomisi.setVisible(false);
	     this.txtTahunKomisi.setVisible(false);
	     this.PeriodeTqs.setVisible(false);
	     this.PeriodeTqs2.setVisible(false);
	     this.txtMasaTqs.setVisible(false);
	     this.txtTahunTqs.setVisible(false);
	  }

   	}
	   
	private EventListener onPaging_T30CostingD_HW3PS_List() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				start_T30CostingD_HW3PS_List = pageNo
						* paging_T30CostingD_HW3PS_List.getPageSize();
				setModel_T30CostingD_HW3PS_List();
			}
		};
	}
	
	private EventListener onPaging_T31CostingD_ACSPS_List() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				start_T31CostingD_ACSPS_List = pageNo
						* paging_T31CostingD_ACSPS_List.getPageSize();
				setModel_T31CostingD_ACSPS_List();
			}
		};
	}
	
	private EventListener onPaging_T32CostingD_OWNSW_List() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				start_T32CostingD_OWNSW_List = pageNo
						* paging_T32CostingD_OWNSW_List.getPageSize();
				setModel_T32CostingD_OWNSW_List();
			}
		};
	}

	private EventListener onPaging_T33CostingD_OTHER_List() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				start_T33CostingD_OTHER_List = pageNo
						* paging_T33CostingD_OTHER_List.getPageSize();
				setModel_T33CostingD_OTHER_List();
			}
		};
	}
	
	private EventListener onPaging_T34CostingD_PAYMENT_List() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				PagingEvent pe = (PagingEvent) event;
				int pageNo = pe.getActivePage();
				start_T34CostingD_PAYMENT_List = pageNo
						* paging_T34CostingD_PAYMENT_List.getPageSize();
				setModel_T34CostingD_PAYMENT_List();
			}
		};
	}

	private void setModel_T30CostingD_HW3PS_List() {
		modelList_T30CostingD_HW3PS_List.clear();

		if (CommonUtils.isNotEmpty(list_T30CostingD_HW3PS_List)) {

			int end_T30CostingD_HW3PS_List = 0;
			if (start_T30CostingD_HW3PS_List + paging_T30CostingD_HW3PS_List.getPageSize() < list_T30CostingD_HW3PS_List
					.size()) {
				end_T30CostingD_HW3PS_List = start_T30CostingD_HW3PS_List
						+ paging_T30CostingD_HW3PS_List.getPageSize();
			} else {
				end_T30CostingD_HW3PS_List = list_T30CostingD_HW3PS_List.size();
			}

			if (start_T30CostingD_HW3PS_List > end_T30CostingD_HW3PS_List) {
				start_T30CostingD_HW3PS_List = 0;
				paging_T30CostingD_HW3PS_List.setActivePage(0);
			}

			modelList_T30CostingD_HW3PS_List.addAll(list_T30CostingD_HW3PS_List.subList(
					start_T30CostingD_HW3PS_List, end_T30CostingD_HW3PS_List));

			paging_T30CostingD_HW3PS_List.setDetailed(true);
			paging_T30CostingD_HW3PS_List
					.setTotalSize(list_T30CostingD_HW3PS_List.size());

			listBox_T30CostingD_HW3PS.setModel(modelList_T30CostingD_HW3PS_List);
			listBox_T30CostingD_HW3PS.setSelectedIndex(0);

			getT29CostingH().setT30CostingDHw3pss(new HashSet<T30CostingDHw3ps>(list_T30CostingD_HW3PS_List));
			
		} else {
			paging_T30CostingD_HW3PS_List.setDetailed(false);
			listBox_T30CostingD_HW3PS.setModel(modelList_T30CostingD_HW3PS_List);
			paging_T30CostingD_HW3PS_List.setTotalSize(0);
			
			getT29CostingH().setT30CostingDHw3pss(null);
			
		}

	}

	private void setModel_T31CostingD_ACSPS_List() {
		modelList_T31CostingD_ACSPS_List.clear();

		if (CommonUtils.isNotEmpty(list_T31CostingD_ACSPS_List)) {

			int end_T31CostingD_ACSPS_List = 0;
			if (start_T31CostingD_ACSPS_List + paging_T31CostingD_ACSPS_List.getPageSize() < list_T31CostingD_ACSPS_List
					.size()) {
				end_T31CostingD_ACSPS_List = start_T31CostingD_ACSPS_List
						+ paging_T31CostingD_ACSPS_List.getPageSize();
			} else {
				end_T31CostingD_ACSPS_List = list_T31CostingD_ACSPS_List.size();
			}

			if (start_T31CostingD_ACSPS_List > end_T31CostingD_ACSPS_List) {
				start_T31CostingD_ACSPS_List = 0;
				paging_T31CostingD_ACSPS_List.setActivePage(0);
			}

			modelList_T31CostingD_ACSPS_List.addAll(list_T31CostingD_ACSPS_List.subList(
					start_T31CostingD_ACSPS_List, end_T31CostingD_ACSPS_List));

			paging_T31CostingD_ACSPS_List.setDetailed(true);
			paging_T31CostingD_ACSPS_List
					.setTotalSize(list_T31CostingD_ACSPS_List.size());

			listBox_T31CostingD_ACSPS.setModel(modelList_T31CostingD_ACSPS_List);
			listBox_T31CostingD_ACSPS.setSelectedIndex(0);

			getT29CostingH().setT31CostingDAcspss(new HashSet<T31CostingDAcsps>(list_T31CostingD_ACSPS_List));
			

		} else {
			paging_T31CostingD_ACSPS_List.setDetailed(false);
			listBox_T31CostingD_ACSPS.setModel(modelList_T31CostingD_ACSPS_List);
			paging_T31CostingD_ACSPS_List.setTotalSize(0);
			
			
			getT29CostingH().setT31CostingDAcspss(null);

		}

	}
	
	private void setModel_T32CostingD_OWNSW_List() {
		modelList_T32CostingD_OWNSW_List.clear();

		if (CommonUtils.isNotEmpty(list_T32CostingD_OWNSW_List)) {

			int end_T32CostingD_OWNSW_List = 0;
			if (start_T32CostingD_OWNSW_List + paging_T32CostingD_OWNSW_List.getPageSize() < list_T32CostingD_OWNSW_List
					.size()) {
				end_T32CostingD_OWNSW_List = start_T32CostingD_OWNSW_List
						+ paging_T32CostingD_OWNSW_List.getPageSize();
			} else {
				end_T32CostingD_OWNSW_List = list_T32CostingD_OWNSW_List.size();
			}

			if (start_T32CostingD_OWNSW_List > end_T32CostingD_OWNSW_List) {
				start_T32CostingD_OWNSW_List = 0;
				paging_T32CostingD_OWNSW_List.setActivePage(0);
			}

			modelList_T32CostingD_OWNSW_List.addAll(list_T32CostingD_OWNSW_List.subList(
					start_T32CostingD_OWNSW_List, end_T32CostingD_OWNSW_List));

			paging_T32CostingD_OWNSW_List.setDetailed(true);
			paging_T32CostingD_OWNSW_List
					.setTotalSize(list_T32CostingD_OWNSW_List.size());

			listBox_T32CostingD_OWNSW.setModel(modelList_T32CostingD_OWNSW_List);
			listBox_T32CostingD_OWNSW.setSelectedIndex(0);

			getT29CostingH().setT32CostingDOwnsws(new HashSet<T32CostingDOwnsw>(list_T32CostingD_OWNSW_List));
			
		} else {
			paging_T32CostingD_OWNSW_List.setDetailed(false);
			listBox_T32CostingD_OWNSW.setModel(modelList_T32CostingD_OWNSW_List);
			paging_T32CostingD_OWNSW_List.setTotalSize(0);
			
			

			getT29CostingH().setT32CostingDOwnsws(null);
		}

	}
	
	private void setModel_T33CostingD_OTHER_List() {
		modelList_T33CostingD_OTHER_List.clear();

		if (CommonUtils.isNotEmpty(list_T33CostingD_OTHER_List)) {

			int end_T33CostingD_OTHER_List = 0;
			if (start_T33CostingD_OTHER_List + paging_T33CostingD_OTHER_List.getPageSize() < list_T33CostingD_OTHER_List
					.size()) {
				end_T33CostingD_OTHER_List = start_T33CostingD_OTHER_List
						+ paging_T33CostingD_OTHER_List.getPageSize();
			} else {
				end_T33CostingD_OTHER_List = list_T33CostingD_OTHER_List.size();
			}

			if (start_T33CostingD_OTHER_List > end_T33CostingD_OTHER_List) {
				start_T33CostingD_OTHER_List = 0;
				paging_T33CostingD_OTHER_List.setActivePage(0);
			}

			modelList_T33CostingD_OTHER_List.addAll(list_T33CostingD_OTHER_List.subList(
					start_T33CostingD_OTHER_List, end_T33CostingD_OTHER_List));

			paging_T33CostingD_OTHER_List.setDetailed(true);
			paging_T33CostingD_OTHER_List
					.setTotalSize(list_T33CostingD_OTHER_List.size());

			listBox_T33CostingD_OTHER.setModel(modelList_T33CostingD_OTHER_List);
			listBox_T33CostingD_OTHER.setSelectedIndex(0);

			getT29CostingH().setT33CostingDOthers(new HashSet<T33CostingDOther>(list_T33CostingD_OTHER_List));
			
		} else {
			paging_T33CostingD_OTHER_List.setDetailed(false);
			listBox_T33CostingD_OTHER.setModel(modelList_T33CostingD_OTHER_List);
			paging_T33CostingD_OTHER_List.setTotalSize(0);
			
			getT29CostingH().setT33CostingDOthers(null);
		}

	}	

	private void setModel_T34CostingD_PAYMENT_List() {
		modelList_T34CostingD_PAYMENT_List.clear();

		if (CommonUtils.isNotEmpty(list_T34CostingD_PAYMENT_List)) {

			int end_T34CostingD_PAYMENT_List = 0;
			if (start_T34CostingD_PAYMENT_List + paging_T34CostingD_PAYMENT_List.getPageSize() < list_T34CostingD_PAYMENT_List
					.size()) {
				end_T34CostingD_PAYMENT_List = start_T34CostingD_PAYMENT_List
						+ paging_T34CostingD_PAYMENT_List.getPageSize();
			} else {
				end_T34CostingD_PAYMENT_List = list_T34CostingD_PAYMENT_List.size();
			}

			if (start_T34CostingD_PAYMENT_List > end_T34CostingD_PAYMENT_List) {
				start_T34CostingD_PAYMENT_List = 0;
				paging_T34CostingD_PAYMENT_List.setActivePage(0);
			}

			modelList_T34CostingD_PAYMENT_List.addAll(list_T34CostingD_PAYMENT_List.subList(
					start_T34CostingD_PAYMENT_List, end_T34CostingD_PAYMENT_List));

			paging_T34CostingD_PAYMENT_List.setDetailed(true);
			paging_T34CostingD_PAYMENT_List
					.setTotalSize(list_T34CostingD_PAYMENT_List.size());

			listBox_T34CostingD_PAYMENT.setModel(modelList_T34CostingD_PAYMENT_List);
			listBox_T34CostingD_PAYMENT.setSelectedIndex(0);

			getT29CostingH().setT34CostingDPayments(new HashSet<T34CostingDPayment>(list_T34CostingD_PAYMENT_List));
			
		} else {
			paging_T34CostingD_PAYMENT_List.setDetailed(false);
			listBox_T34CostingD_PAYMENT.setModel(modelList_T34CostingD_PAYMENT_List);
			paging_T34CostingD_PAYMENT_List.setTotalSize(0);
			
			getT29CostingH().setT34CostingDPayments(null);
		}

	}	
	
	private ListitemRenderer renderTable_T30CostingD_HW3PS() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				final T30CostingDHw3ps rec = (T30CostingDHw3ps) data;

				Listcell lc;

				lc = new Listcell(rec.getItemDesc());
				lc.setParent(item);
				
				lc = new Listcell(rec.getItemNo());
				lc.setParent(item);
				
				lc = new Listcell(rec.getItemCategory());
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getQty(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getSalesSatuan(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getSalesTotal(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				

				lc = new Listcell(CommonUtils.formatNumberManual(rec.getCogsSatuan(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getCogsTotal(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(rec.getCatatan());
				lc.setParent(item);
				
				item.setValue(data);
				item.setAttribute("data", data);
			}
		};
	}
	
	private ListitemRenderer renderTable_T31CostingD_ACSPS() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				final T31CostingDAcsps rec = (T31CostingDAcsps) data;

				Listcell lc;

				lc = new Listcell(rec.getItemDesc());
				lc.setParent(item);
				
				lc = new Listcell(rec.getItemNo());
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getQty(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getSalesSatuan(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getSalesTotal(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(rec.getCatatan());
				lc.setParent(item);
				
				item.setValue(data);
				item.setAttribute("data", data);
			}
		};
	}
	
	
	private ListitemRenderer renderTable_T32CostingD_OWNSW() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				final T32CostingDOwnsw rec = (T32CostingDOwnsw) data;

				Listcell lc;

				lc = new Listcell(rec.getItemDesc());
				lc.setParent(item);
				
				lc = new Listcell(rec.getItemNo());
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getQty(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getSalesSatuan(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getSalesTotal(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(rec.getCatatan());
				lc.setParent(item);
				
				item.setValue(data);
				item.setAttribute("data", data);
			}
		};
	}
	
	
	private ListitemRenderer renderTable_T33CostingD_OTHER() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				final T33CostingDOther rec = (T33CostingDOther) data;

				Listcell lc;

				lc = new Listcell(rec.getItemDesc());
				lc.setParent(item);
				
				lc = new Listcell(rec.getItemNo());
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getQty(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getCogsSatuan(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getCogsTotal(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(rec.getCatatan());
				lc.setParent(item);
				
				item.setValue(data);
				item.setAttribute("data", data);
			}
		};
	}
	
	private ListitemRenderer renderTable_T34CostingD_PAYMENT() {
		return new ListitemRenderer() {

			@Override
			public void render(Listitem item, Object data) throws Exception {

				final T34CostingDPayment rec = (T34CostingDPayment) data;

				Listcell lc;

				lc = new Listcell(rec.getNoInvoice());
				lc.setParent(item);				
				
				lc = new Listcell(CommonUtils.convertDateToString(rec.getTglInvoice()));
                lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getNilaiInvoice(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				lc = new Listcell(rec.getNoLunas());
				lc.setParent(item);				
				
				lc = new Listcell(CommonUtils.convertDateToString(rec.getTglLunas()));
                lc.setParent(item);
				
				lc = new Listcell(CommonUtils.formatNumberManual(rec.getNilaiLunas(),"#,##0"));
				lc.setStyle("text-align:right");
				lc.setParent(item);
				
				item.setValue(data);
				item.setAttribute("data", data);
			}
		};
	}
	
    private EventListener onEnterForm(){
    	return new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				getT29CostingMainCtrl().onClick$btnSave(event);
			}
		};
    }
    
	public void onCreate$windowT29CostingDetail(Event event) throws Exception {
		binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder",
				true);

		
		
		doFillListbox();
		doRenderCombo(); 
		doFitSize(event);
		binder.loadAll();
	}

	
	public void doRenderCombo() {
		

		dcbCalculateTotalMarginPcn.setValue(new BigDecimal(0));
		dcbCalculateHw3PSMarginPcn.setValue(new BigDecimal(0));
		dcbCalculateTotalMarginOnlyPcn.setValue(new BigDecimal(0));
		dcbCalculateHwMarginOnlyPcn.setValue(new BigDecimal(0));
	    dcbCalculateHw3PSMarginOnlyPcn.setValue(new BigDecimal(0));
	    
		
		
		T29CostingH anT29 = getT29CostingH();
		
		ListBoxUtil.resetList(list_Company);		

		Listitem vListCompany = null;

		Listitem vListAUTOJAYA = list_Company.appendItem("AUTOJAYA", "AUTOJAYA");
		Listitem vListSOLUSI = list_Company.appendItem("SOLUSI", "SOLUSI");
		
		if (anT29 != null) {
			if (anT29.getCompany() != null) {
				if (anT29.getCompany().equals("AUTOJAYA")) {
					vListCompany = vListAUTOJAYA;
				} else {
					vListCompany = vListSOLUSI;
				}
			}
		}
		
		cmbCompany.setValue(vListCompany.getLabel());
		list_Company.setSelectedItem(vListCompany);



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
				
				if (anT29 != null) {
					if (anT29.getSalesman() != null) {
						if (anT29.getSalesman().equals(aRslt[1].toString())) {
							vListSales = vList;
						}
					}
				}
			}
		}
		
		cmbSalesman.setValue(vListSales.getLabel());
		list_Sales.setSelectedItem(vListSales);
	}
		
	public void onSelect$list_Company(Event event) throws Exception {
		T29CostingH data = getT29CostingH();

		data.setCompany(list_Company.getSelectedItem().getValue().toString());
		setT29CostingH(data);
	}
	
	public void onSelect$list_Sales(Event event) throws Exception {
		T29CostingH data = getT29CostingH();

		data.setSalesman(list_Sales.getSelectedItem().getValue().toString());
		setT29CostingH(data);
	}
	
	
	public void doFillListbox() {
		
		
		//Detail 1 - T30CostingD_HW3PS
		
		listheader_T30CostingD_HW3PS_ItemDesc.setSortAscending(new T30CostingD_HW3PS_Comparator(true, T30CostingD_HW3PS_Comparator.COMPARE_BY_ITEMDESC));
		listheader_T30CostingD_HW3PS_ItemDesc.setSortDescending(new T30CostingD_HW3PS_Comparator(false, T30CostingD_HW3PS_Comparator.COMPARE_BY_ITEMDESC));        
        
		listheader_T30CostingD_HW3PS_ItemNo.setSortAscending(new T30CostingD_HW3PS_Comparator(true, T30CostingD_HW3PS_Comparator.COMPARE_BY_ITEMNO));
		listheader_T30CostingD_HW3PS_ItemNo.setSortDescending(new T30CostingD_HW3PS_Comparator(false, T30CostingD_HW3PS_Comparator.COMPARE_BY_ITEMNO));        
        
		listheader_T30CostingD_HW3PS_ItemCategory.setSortAscending(new T30CostingD_HW3PS_Comparator(true, T30CostingD_HW3PS_Comparator.COMPARE_BY_ITEMCATEGORY));
		listheader_T30CostingD_HW3PS_ItemCategory.setSortDescending(new T30CostingD_HW3PS_Comparator(false, T30CostingD_HW3PS_Comparator.COMPARE_BY_ITEMCATEGORY));        
        
		listheader_T30CostingD_HW3PS_Qty.setSortAscending(new T30CostingD_HW3PS_Comparator(true, T30CostingD_HW3PS_Comparator.COMPARE_BY_QTY));
		listheader_T30CostingD_HW3PS_Qty.setSortDescending(new T30CostingD_HW3PS_Comparator(false, T30CostingD_HW3PS_Comparator.COMPARE_BY_QTY));        
        
		listheader_T30CostingD_HW3PS_SalesSatuan.setSortAscending(new T30CostingD_HW3PS_Comparator(true, T30CostingD_HW3PS_Comparator.COMPARE_BY_SALESSATUAN));
		listheader_T30CostingD_HW3PS_SalesSatuan.setSortDescending(new T30CostingD_HW3PS_Comparator(false, T30CostingD_HW3PS_Comparator.COMPARE_BY_SALESSATUAN));        
        
		listheader_T30CostingD_HW3PS_SalesTotal.setSortAscending(new T30CostingD_HW3PS_Comparator(true, T30CostingD_HW3PS_Comparator.COMPARE_BY_SALESTOTAL));
		listheader_T30CostingD_HW3PS_SalesTotal.setSortDescending(new T30CostingD_HW3PS_Comparator(false, T30CostingD_HW3PS_Comparator.COMPARE_BY_SALESTOTAL));        
        
		listheader_T30CostingD_HW3PS_CogsSatuan.setSortAscending(new T30CostingD_HW3PS_Comparator(true, T30CostingD_HW3PS_Comparator.COMPARE_BY_COGSSATUAN));
		listheader_T30CostingD_HW3PS_CogsSatuan.setSortDescending(new T30CostingD_HW3PS_Comparator(false, T30CostingD_HW3PS_Comparator.COMPARE_BY_COGSSATUAN));        
        
		listheader_T30CostingD_HW3PS_CogsTotal.setSortAscending(new T30CostingD_HW3PS_Comparator(true, T30CostingD_HW3PS_Comparator.COMPARE_BY_COGSTOTAL));
		listheader_T30CostingD_HW3PS_CogsTotal.setSortDescending(new T30CostingD_HW3PS_Comparator(false, T30CostingD_HW3PS_Comparator.COMPARE_BY_COGSTOTAL));        
        
		listheader_T30CostingD_HW3PS_Catatan.setSortAscending(new T30CostingD_HW3PS_Comparator(true, T30CostingD_HW3PS_Comparator.COMPARE_BY_CATATAN));
		listheader_T30CostingD_HW3PS_Catatan.setSortDescending(new T30CostingD_HW3PS_Comparator(false, T30CostingD_HW3PS_Comparator.COMPARE_BY_CATATAN));        
        

		//Detail 2 - T31CostingD_ACSPS		
		listheader_T31CostingD_ACSPS_ItemDesc.setSortAscending(new T31CostingD_ACSPS_Comparator(true, T31CostingD_ACSPS_Comparator.COMPARE_BY_ITEMDESC));
		listheader_T31CostingD_ACSPS_ItemDesc.setSortDescending(new T31CostingD_ACSPS_Comparator(false, T31CostingD_ACSPS_Comparator.COMPARE_BY_ITEMDESC));        
        
		listheader_T31CostingD_ACSPS_ItemNo.setSortAscending(new T31CostingD_ACSPS_Comparator(true, T31CostingD_ACSPS_Comparator.COMPARE_BY_ITEMNO));
		listheader_T31CostingD_ACSPS_ItemNo.setSortDescending(new T31CostingD_ACSPS_Comparator(false, T31CostingD_ACSPS_Comparator.COMPARE_BY_ITEMNO));        
            
		listheader_T31CostingD_ACSPS_Qty.setSortAscending(new T31CostingD_ACSPS_Comparator(true, T31CostingD_ACSPS_Comparator.COMPARE_BY_QTY));
		listheader_T31CostingD_ACSPS_Qty.setSortDescending(new T31CostingD_ACSPS_Comparator(false, T31CostingD_ACSPS_Comparator.COMPARE_BY_QTY));        
        
		listheader_T31CostingD_ACSPS_SalesSatuan.setSortAscending(new T31CostingD_ACSPS_Comparator(true, T31CostingD_ACSPS_Comparator.COMPARE_BY_SALESSATUAN));
		listheader_T31CostingD_ACSPS_SalesSatuan.setSortDescending(new T31CostingD_ACSPS_Comparator(false, T31CostingD_ACSPS_Comparator.COMPARE_BY_SALESSATUAN));        
        
		listheader_T31CostingD_ACSPS_SalesTotal.setSortAscending(new T31CostingD_ACSPS_Comparator(true, T31CostingD_ACSPS_Comparator.COMPARE_BY_SALESTOTAL));
		listheader_T31CostingD_ACSPS_SalesTotal.setSortDescending(new T31CostingD_ACSPS_Comparator(false, T31CostingD_ACSPS_Comparator.COMPARE_BY_SALESTOTAL));        
                
		listheader_T31CostingD_ACSPS_Catatan.setSortAscending(new T31CostingD_ACSPS_Comparator(true, T31CostingD_ACSPS_Comparator.COMPARE_BY_CATATAN));
		listheader_T31CostingD_ACSPS_Catatan.setSortDescending(new T31CostingD_ACSPS_Comparator(false, T31CostingD_ACSPS_Comparator.COMPARE_BY_CATATAN));        

		
		//Detail 3 - T32CostingD_OWNSW		
		listheader_T32CostingD_OWNSW_ItemDesc.setSortAscending(new T32CostingD_OWNSW_Comparator(true, T32CostingD_OWNSW_Comparator.COMPARE_BY_ITEMDESC));
		listheader_T32CostingD_OWNSW_ItemDesc.setSortDescending(new T32CostingD_OWNSW_Comparator(false, T32CostingD_OWNSW_Comparator.COMPARE_BY_ITEMDESC));        
        
		listheader_T32CostingD_OWNSW_ItemNo.setSortAscending(new T32CostingD_OWNSW_Comparator(true, T32CostingD_OWNSW_Comparator.COMPARE_BY_ITEMNO));
		listheader_T32CostingD_OWNSW_ItemNo.setSortDescending(new T32CostingD_OWNSW_Comparator(false, T32CostingD_OWNSW_Comparator.COMPARE_BY_ITEMNO));        
            
		listheader_T32CostingD_OWNSW_Qty.setSortAscending(new T32CostingD_OWNSW_Comparator(true, T32CostingD_OWNSW_Comparator.COMPARE_BY_QTY));
		listheader_T32CostingD_OWNSW_Qty.setSortDescending(new T32CostingD_OWNSW_Comparator(false, T32CostingD_OWNSW_Comparator.COMPARE_BY_QTY));        
        
		listheader_T32CostingD_OWNSW_SalesSatuan.setSortAscending(new T32CostingD_OWNSW_Comparator(true, T32CostingD_OWNSW_Comparator.COMPARE_BY_SALESSATUAN));
		listheader_T32CostingD_OWNSW_SalesSatuan.setSortDescending(new T32CostingD_OWNSW_Comparator(false, T32CostingD_OWNSW_Comparator.COMPARE_BY_SALESSATUAN));        
        
		listheader_T32CostingD_OWNSW_SalesTotal.setSortAscending(new T32CostingD_OWNSW_Comparator(true, T32CostingD_OWNSW_Comparator.COMPARE_BY_SALESTOTAL));
		listheader_T32CostingD_OWNSW_SalesTotal.setSortDescending(new T32CostingD_OWNSW_Comparator(false, T32CostingD_OWNSW_Comparator.COMPARE_BY_SALESTOTAL));        
                
		listheader_T32CostingD_OWNSW_Catatan.setSortAscending(new T32CostingD_OWNSW_Comparator(true, T32CostingD_OWNSW_Comparator.COMPARE_BY_CATATAN));
		listheader_T32CostingD_OWNSW_Catatan.setSortDescending(new T32CostingD_OWNSW_Comparator(false, T32CostingD_OWNSW_Comparator.COMPARE_BY_CATATAN));        

		//Detail 4 - T33CostingD_OTHER		
		listheader_T33CostingD_OTHER_ItemDesc.setSortAscending(new T33CostingD_OTHER_Comparator(true, T33CostingD_OTHER_Comparator.COMPARE_BY_ITEMDESC));
		listheader_T33CostingD_OTHER_ItemDesc.setSortDescending(new T33CostingD_OTHER_Comparator(false, T33CostingD_OTHER_Comparator.COMPARE_BY_ITEMDESC));        
        
		listheader_T33CostingD_OTHER_ItemNo.setSortAscending(new T33CostingD_OTHER_Comparator(true, T33CostingD_OTHER_Comparator.COMPARE_BY_ITEMNO));
		listheader_T33CostingD_OTHER_ItemNo.setSortDescending(new T33CostingD_OTHER_Comparator(false, T33CostingD_OTHER_Comparator.COMPARE_BY_ITEMNO));        
            
		listheader_T33CostingD_OTHER_Qty.setSortAscending(new T33CostingD_OTHER_Comparator(true, T33CostingD_OTHER_Comparator.COMPARE_BY_QTY));
		listheader_T33CostingD_OTHER_Qty.setSortDescending(new T33CostingD_OTHER_Comparator(false, T33CostingD_OTHER_Comparator.COMPARE_BY_QTY));        
        
		listheader_T33CostingD_OTHER_CogsSatuan.setSortAscending(new T33CostingD_OTHER_Comparator(true, T33CostingD_OTHER_Comparator.COMPARE_BY_COGSSATUAN));
		listheader_T33CostingD_OTHER_CogsSatuan.setSortDescending(new T33CostingD_OTHER_Comparator(false, T33CostingD_OTHER_Comparator.COMPARE_BY_COGSSATUAN));        
        
		listheader_T33CostingD_OTHER_CogsTotal.setSortAscending(new T33CostingD_OTHER_Comparator(true, T33CostingD_OTHER_Comparator.COMPARE_BY_COGSTOTAL));
		listheader_T33CostingD_OTHER_CogsTotal.setSortDescending(new T33CostingD_OTHER_Comparator(false, T33CostingD_OTHER_Comparator.COMPARE_BY_COGSTOTAL));        
                
		listheader_T33CostingD_OTHER_Catatan.setSortAscending(new T33CostingD_OTHER_Comparator(true, T33CostingD_OTHER_Comparator.COMPARE_BY_CATATAN));
		listheader_T33CostingD_OTHER_Catatan.setSortDescending(new T33CostingD_OTHER_Comparator(false, T33CostingD_OTHER_Comparator.COMPARE_BY_CATATAN));        

		//Detail 4 - T34CostingD_PAYMENT		
		listheader_T34CostingD_PAYMENT_NoInvoice.setSortAscending(new T34CostingD_PAYMENT_Comparator(true, T34CostingD_PAYMENT_Comparator.COMPARE_BY_NOINVOICE));
		listheader_T34CostingD_PAYMENT_NoInvoice.setSortDescending(new T34CostingD_PAYMENT_Comparator(false, T34CostingD_PAYMENT_Comparator.COMPARE_BY_NOINVOICE));        

		listheader_T34CostingD_PAYMENT_TglInvoice.setSortAscending(new T34CostingD_PAYMENT_Comparator(true, T34CostingD_PAYMENT_Comparator.COMPARE_BY_TGLINVOICE));
		listheader_T34CostingD_PAYMENT_TglInvoice.setSortDescending(new T34CostingD_PAYMENT_Comparator(false, T34CostingD_PAYMENT_Comparator.COMPARE_BY_TGLINVOICE));        
		
		listheader_T34CostingD_PAYMENT_NilaiInvoice.setSortAscending(new T34CostingD_PAYMENT_Comparator(true, T34CostingD_PAYMENT_Comparator.COMPARE_BY_NILAIINVOICE));
		listheader_T34CostingD_PAYMENT_NilaiInvoice.setSortDescending(new T34CostingD_PAYMENT_Comparator(false, T34CostingD_PAYMENT_Comparator.COMPARE_BY_NILAIINVOICE));        

		listheader_T34CostingD_PAYMENT_NoLunas.setSortAscending(new T34CostingD_PAYMENT_Comparator(true, T34CostingD_PAYMENT_Comparator.COMPARE_BY_NOLUNAS));
		listheader_T34CostingD_PAYMENT_NoLunas.setSortDescending(new T34CostingD_PAYMENT_Comparator(false, T34CostingD_PAYMENT_Comparator.COMPARE_BY_NOLUNAS));        

		listheader_T34CostingD_PAYMENT_TglLunas.setSortAscending(new T34CostingD_PAYMENT_Comparator(true, T34CostingD_PAYMENT_Comparator.COMPARE_BY_TGLLUNAS));
		listheader_T34CostingD_PAYMENT_TglLunas.setSortDescending(new T34CostingD_PAYMENT_Comparator(false, T34CostingD_PAYMENT_Comparator.COMPARE_BY_TGLLUNAS));        
		
		listheader_T34CostingD_PAYMENT_NilaiLunas.setSortAscending(new T34CostingD_PAYMENT_Comparator(true, T34CostingD_PAYMENT_Comparator.COMPARE_BY_NILAILUNAS));
		listheader_T34CostingD_PAYMENT_NilaiLunas.setSortDescending(new T34CostingD_PAYMENT_Comparator(false, T34CostingD_PAYMENT_Comparator.COMPARE_BY_NILAILUNAS));   

		
	}

	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T29CostingDetail.setHeight(String.valueOf(maxListBoxHeight)
				+ "px");

		windowT29CostingDetail.invalidate();
	}



	
	public void doRenderMode(String pMode, String pPosisiCosting) {		
		if(CommonUtils.isNotEmpty(pMode)){
			if(pMode.equals("View")){
				
				cmbCompany.setDisabled(true);
				cmbSalesman.setDisabled(true);		
				dbTglCosting.setDisabled(true);	
			
				txtNoCosting.setReadonly(true);
				txtNoCosting.setVisible(true);
				
				txtNoBso.setReadonly(true);
				txtNoSo.setReadonly(true);
				txtNoPoCustomer.setReadonly(true);
				txtCustomer.setReadonly(true);
				txtNoPenawaran.setReadonly(true);
				
				btnRecalculate.setVisible(false);
				btnRecalculate1.setVisible(false);
				btnRecalculate2.setVisible(false);
				btnRecalculate3.setVisible(false);
				
				btnNew.setVisible(false);
				btnView.setVisible(true);
				btnEdit.setVisible(false);
				btnDelete.setVisible(false);
				btnExport.setVisible(false);
				btnImport.setVisible(false);
				
				btnNew_B.setVisible(false);
				btnView_B.setVisible(true);
				btnEdit_B.setVisible(false);
				btnDelete_B.setVisible(false);
				btnExport_B.setVisible(false);
				btnImport_B.setVisible(false);
				
				btnNew_C.setVisible(false);
				btnView_C.setVisible(true);
				btnEdit_C.setVisible(false);
				btnDelete_C.setVisible(false);
				btnExport_C.setVisible(false);
				btnImport_C.setVisible(false);
				
				btnNew_D.setVisible(false);
				btnView_D.setVisible(true);
				btnEdit_D.setVisible(false);
				btnDelete_D.setVisible(false);
				btnExport_D.setVisible(false);
				btnImport_D.setVisible(false);
				
				btnNew_E.setVisible(false);
				btnView_E.setVisible(true);
				btnEdit_E.setVisible(false);
				btnDelete_E.setVisible(false);
				
				btnUploadBSO.setVisible(false);
				btnUploadInfoPrice.setVisible(false);
				btnUploadFilePOCustomer.setVisible(false);

				dcbIncentiveApproveHw3ps.setReadonly(true);	
				dcbIncentiveNonsalesHw3ps.setReadonly(true);
				dcbIncentiveKomisiHw3ps.setReadonly(true);
				//dcbIncentiveSbonusHw3ps.setReadonly(true);
			
				dcbIncentiveApproveAcsps.setReadonly(true);	
				dcbIncentiveNonsalesAcsps.setReadonly(true);
				dcbIncentiveKomisiAcsps.setReadonly(true);
				//dcbIncentiveSbonusAcsps.setReadonly(true);
				
				dcbIncentiveApproveOwnsw.setReadonly(true);	
				dcbIncentiveNonsalesOwnsw.setReadonly(true);
				dcbIncentiveKomisiOwnsw.setReadonly(true);
				//dcbIncentiveSbonusOwnsw.setReadonly(true);
				
				
				
				dcbCalculateTotalMarginPcn.setReadonly(true);
				dcbCalculateHw3PSMarginPcn.setReadonly(true);
				dcbCalculateTotalMarginOnlyPcn.setReadonly(true);
				dcbCalculateHwMarginOnlyPcn.setReadonly(true);
				dcbCalculateHw3PSMarginOnlyPcn.setReadonly(true);
				
				
				btnSearchCustomerLOV.setDisabled(true);	

			}
			
			if(pMode.equals("New")){
				cmbCompany.setDisabled(false);
				cmbSalesman.setDisabled(false);		
				dbTglCosting.setDisabled(false);	
			
				txtNoCosting.setReadonly(true);
				txtNoCosting.setVisible(false);
				
				
				txtNoBso.setReadonly(false);
				txtNoSo.setReadonly(false);
				txtNoPoCustomer.setReadonly(false);
				txtCustomer.setReadonly(false);
				txtNoPenawaran.setReadonly(false);
				
				btnRecalculate.setVisible(true);
				btnRecalculate1.setVisible(true);
				btnRecalculate2.setVisible(true);
				btnRecalculate3.setVisible(true);
				
				btnNew.setVisible(true);
				btnView.setVisible(false);
				btnEdit.setVisible(true);
				btnDelete.setVisible(true);
				btnExport.setVisible(true);
				btnImport.setVisible(true);
				
				btnNew_B.setVisible(true);
				btnView_B.setVisible(false);
				btnEdit_B.setVisible(true);
				btnDelete_B.setVisible(true);
				btnExport_B.setVisible(true);
				btnImport_B.setVisible(true);
				
				btnNew_C.setVisible(true);
				btnView_C.setVisible(false);
				btnEdit_C.setVisible(true);
				btnDelete_C.setVisible(true);
				btnExport_C.setVisible(true);
				btnImport_C.setVisible(true);
				
				btnNew_D.setVisible(true);
				btnView_D.setVisible(false);
				btnEdit_D.setVisible(true);
				btnDelete_D.setVisible(true);
				btnExport_D.setVisible(true);
				btnImport_D.setVisible(true);
				
				btnNew_E.setVisible(false);
				btnView_E.setVisible(false);
				btnEdit_E.setVisible(false);
				btnDelete_E.setVisible(false);
				
				btnUploadBSO.setVisible(true);
				btnUploadInfoPrice.setVisible(true);
				btnUploadFilePOCustomer.setVisible(true);
				
				dcbIncentiveApproveHw3ps.setReadonly(false);	
				dcbIncentiveNonsalesHw3ps.setReadonly(false);
				dcbIncentiveKomisiHw3ps.setReadonly(false);
				//dcbIncentiveSbonusHw3ps.setReadonly(false);
			
				dcbIncentiveApproveAcsps.setReadonly(false);	
				dcbIncentiveNonsalesAcsps.setReadonly(false);
				dcbIncentiveKomisiAcsps.setReadonly(false);
				//dcbIncentiveSbonusAcsps.setReadonly(false);
				
				dcbIncentiveApproveOwnsw.setReadonly(false);	
				dcbIncentiveNonsalesOwnsw.setReadonly(false);
				dcbIncentiveKomisiOwnsw.setReadonly(false);
				//dcbIncentiveSbonusOwnsw.setReadonly(false);
				
				dcbCalculateTotalMarginPcn.setReadonly(false);
				dcbCalculateHw3PSMarginPcn.setReadonly(false);
				dcbCalculateTotalMarginOnlyPcn.setReadonly(false);
				dcbCalculateHwMarginOnlyPcn.setReadonly(false);
				dcbCalculateHw3PSMarginOnlyPcn.setReadonly(false);
				
				btnSearchCustomerLOV.setDisabled(false);	
				
				// set focus 
				cmbCompany.setFocus(true);	
			}
			
			if(pMode.equals("Edit")){		
				//String vUser = SecurityContextHolder.getContext().getAuthentication().getName();
				//String vRoleUser = selectQueryService.QueryRoleUserCosting(vUser);
				
				if (pPosisiCosting.equals("SALES") == true) {
					// set read only key
					cmbCompany.setDisabled(true);
					cmbSalesman.setDisabled(true);		
					dbTglCosting.setDisabled(true);	
				
					txtNoCosting.setReadonly(true);
					txtNoCosting.setVisible(true);
					
					txtNoBso.setReadonly(false);
					txtNoSo.setReadonly(false);
					txtNoPoCustomer.setReadonly(false);
					txtCustomer.setReadonly(false);
					txtNoPenawaran.setReadonly(false);
					
					btnRecalculate.setVisible(true);
					btnRecalculate1.setVisible(true);
					btnRecalculate2.setVisible(true);
					btnRecalculate3.setVisible(true);
					
					btnNew.setVisible(true);
					btnView.setVisible(false);
					btnEdit.setVisible(true);
					btnDelete.setVisible(true);
					btnExport.setVisible(true);
					btnImport.setVisible(true);
					
					btnNew_B.setVisible(true);
					btnView_B.setVisible(false);
					btnEdit_B.setVisible(true);
					btnDelete_B.setVisible(true);
					btnExport_B.setVisible(true);
					btnImport_B.setVisible(true);
					
					btnNew_C.setVisible(true);
					btnView_C.setVisible(false);
					btnEdit_C.setVisible(true);
					btnDelete_C.setVisible(true);
					btnExport_C.setVisible(true);
					btnImport_C.setVisible(true);
					
					btnNew_D.setVisible(true);
					btnView_D.setVisible(false);
					btnEdit_D.setVisible(true);
					btnDelete_D.setVisible(true);
					btnExport_D.setVisible(true);
					btnImport_D.setVisible(true);
					
					btnNew_E.setVisible(false);
					btnView_E.setVisible(false);
					btnEdit_E.setVisible(false);
					btnDelete_E.setVisible(false);
					
					btnUploadBSO.setVisible(true);
					btnUploadInfoPrice.setVisible(true);
					btnUploadFilePOCustomer.setVisible(true);
					
					dcbIncentiveApproveHw3ps.setReadonly(false);	
					dcbIncentiveNonsalesHw3ps.setReadonly(false);
					dcbIncentiveKomisiHw3ps.setReadonly(false);
					//dcbIncentiveSbonusHw3ps.setReadonly(false);
				
					dcbIncentiveApproveAcsps.setReadonly(false);	
					dcbIncentiveNonsalesAcsps.setReadonly(false);
					dcbIncentiveKomisiAcsps.setReadonly(false);
					//dcbIncentiveSbonusAcsps.setReadonly(false);
					
					dcbIncentiveApproveOwnsw.setReadonly(false);	
					dcbIncentiveNonsalesOwnsw.setReadonly(false);
					dcbIncentiveKomisiOwnsw.setReadonly(false);
					//dcbIncentiveSbonusOwnsw.setReadonly(false);
					
					dcbCalculateTotalMarginPcn.setReadonly(false);
					dcbCalculateHw3PSMarginPcn.setReadonly(false);
					dcbCalculateTotalMarginOnlyPcn.setReadonly(false);
					dcbCalculateHwMarginOnlyPcn.setReadonly(false);
					dcbCalculateHw3PSMarginOnlyPcn.setReadonly(false);
					

					btnSearchCustomerLOV.setDisabled(false);	
					
					// set focus 
					cmbCompany.setFocus(true);	
				} else {
					if (pPosisiCosting.equals("SAO") == true) {
						// set read only key
						cmbCompany.setDisabled(true);
						cmbSalesman.setDisabled(true);		
						dbTglCosting.setDisabled(true);	
					
						txtNoCosting.setReadonly(true);
						txtNoCosting.setVisible(true);
						
						txtNoBso.setReadonly(true);
						txtNoSo.setReadonly(true);
						txtNoPoCustomer.setReadonly(true);
						txtCustomer.setReadonly(true);
						txtNoPenawaran.setReadonly(true);
						
						btnRecalculate.setVisible(false);
						btnRecalculate1.setVisible(false);
						btnRecalculate2.setVisible(false);
						btnRecalculate3.setVisible(false);
						
						btnNew.setVisible(false);
						btnView.setVisible(true);
						btnEdit.setVisible(false);
						btnDelete.setVisible(false);
						btnExport.setVisible(false);
						btnImport.setVisible(false);
						
						btnNew_B.setVisible(false);
						btnView_B.setVisible(true);
						btnEdit_B.setVisible(false);
						btnDelete_B.setVisible(false);
						btnExport_B.setVisible(false);
						btnImport_B.setVisible(false);
						
						btnNew_C.setVisible(false);
						btnView_C.setVisible(true);
						btnEdit_C.setVisible(false);
						btnDelete_C.setVisible(false);
						btnExport_C.setVisible(false);
						btnImport_C.setVisible(false);
						
						btnNew_D.setVisible(false);
						btnView_D.setVisible(true);
						btnEdit_D.setVisible(false);
						btnDelete_D.setVisible(false);
						btnExport_D.setVisible(false);
						btnImport_D.setVisible(false);
						
						btnNew_E.setVisible(false);
						btnView_E.setVisible(false);
						btnEdit_E.setVisible(false);
						btnDelete_E.setVisible(false);
						
						btnUploadBSO.setVisible(false);
						btnUploadInfoPrice.setVisible(false);
						btnUploadFilePOCustomer.setVisible(false);
						
						dcbIncentiveApproveHw3ps.setReadonly(true);	
						dcbIncentiveNonsalesHw3ps.setReadonly(true);
						dcbIncentiveKomisiHw3ps.setReadonly(true);
						//dcbIncentiveSbonusHw3ps.setReadonly(false);
					
						dcbIncentiveApproveAcsps.setReadonly(true);	
						dcbIncentiveNonsalesAcsps.setReadonly(true);
						dcbIncentiveKomisiAcsps.setReadonly(true);
						//dcbIncentiveSbonusAcsps.setReadonly(false);
						
						dcbIncentiveApproveOwnsw.setReadonly(true);	
						dcbIncentiveNonsalesOwnsw.setReadonly(true);
						dcbIncentiveKomisiOwnsw.setReadonly(true);
						//dcbIncentiveSbonusOwnsw.setReadonly(false);
						
						dcbCalculateTotalMarginPcn.setReadonly(true);
						dcbCalculateHw3PSMarginPcn.setReadonly(true);
						dcbCalculateTotalMarginOnlyPcn.setReadonly(true);
						dcbCalculateHwMarginOnlyPcn.setReadonly(true);
						dcbCalculateHw3PSMarginOnlyPcn.setReadonly(true);
						
						btnSearchCustomerLOV.setDisabled(true);	
						
						// set focus 
						//cmbCompany.setFocus(true);
						
					} else {
						if (pPosisiCosting.equals("LOGISTIC") == true) {
							// set read only key
							cmbCompany.setDisabled(true);
							cmbSalesman.setDisabled(true);		
							dbTglCosting.setDisabled(true);	
						
							txtNoCosting.setReadonly(true);
							txtNoCosting.setVisible(true);
							
							txtNoBso.setReadonly(true);
							txtNoSo.setReadonly(true);
							txtNoPoCustomer.setReadonly(true);
							txtCustomer.setReadonly(true);
							txtNoPenawaran.setReadonly(true);
							
							btnRecalculate.setVisible(false);
							btnRecalculate1.setVisible(false);
							btnRecalculate2.setVisible(false);
							btnRecalculate3.setVisible(false);
							
							btnNew.setVisible(false);
							btnView.setVisible(true);
							btnEdit.setVisible(true);
							btnDelete.setVisible(false);
							btnExport.setVisible(false);
							btnImport.setVisible(false);
							
							btnNew_B.setVisible(false);
							btnView_B.setVisible(true);
							btnEdit_B.setVisible(false);
							btnDelete_B.setVisible(false);
							btnExport_B.setVisible(false);
							btnImport_B.setVisible(false);
							
							btnNew_C.setVisible(false);
							btnView_C.setVisible(true);
							btnEdit_C.setVisible(false);
							btnDelete_C.setVisible(false);
							btnExport_C.setVisible(false);
							btnImport_C.setVisible(false);
							
							btnNew_D.setVisible(false);
							btnView_D.setVisible(true);
							btnEdit_D.setVisible(true);
							btnDelete_D.setVisible(false);
							btnExport_D.setVisible(false);
							btnImport_D.setVisible(false);
							
							btnNew_E.setVisible(false);
							btnView_E.setVisible(false);
							btnEdit_E.setVisible(false);
							btnDelete_E.setVisible(false);
							
							btnUploadBSO.setVisible(false);
							btnUploadInfoPrice.setVisible(false);
							btnUploadFilePOCustomer.setVisible(false);
							
							dcbIncentiveApproveHw3ps.setReadonly(true);	
							dcbIncentiveNonsalesHw3ps.setReadonly(true);
							dcbIncentiveKomisiHw3ps.setReadonly(true);
							//dcbIncentiveSbonusHw3ps.setReadonly(false);
						
							dcbIncentiveApproveAcsps.setReadonly(true);	
							dcbIncentiveNonsalesAcsps.setReadonly(true);
							dcbIncentiveKomisiAcsps.setReadonly(true);
							//dcbIncentiveSbonusAcsps.setReadonly(false);
							
							dcbIncentiveApproveOwnsw.setReadonly(true);	
							dcbIncentiveNonsalesOwnsw.setReadonly(true);
							dcbIncentiveKomisiOwnsw.setReadonly(true);
							//dcbIncentiveSbonusOwnsw.setReadonly(false);
							
							dcbCalculateTotalMarginPcn.setReadonly(true);
							dcbCalculateHw3PSMarginPcn.setReadonly(true);
							dcbCalculateTotalMarginOnlyPcn.setReadonly(true);
							dcbCalculateHwMarginOnlyPcn.setReadonly(true);
							dcbCalculateHw3PSMarginOnlyPcn.setReadonly(true);
							
							btnSearchCustomerLOV.setDisabled(true);	
							
							// set focus 
							//cmbCompany.setFocus(true);
							
						} else {
							if (pPosisiCosting.equals("FINANCE 1") == true) {
								// set read only key
								cmbCompany.setDisabled(true);
								cmbSalesman.setDisabled(true);		
								dbTglCosting.setDisabled(true);	
							
								txtNoCosting.setReadonly(true);
								txtNoCosting.setVisible(true);
								
								txtNoBso.setReadonly(true);
								txtNoSo.setReadonly(true);
								txtNoPoCustomer.setReadonly(true);
								txtCustomer.setReadonly(true);
								txtNoPenawaran.setReadonly(true);
								
								btnRecalculate.setVisible(false);
								btnRecalculate1.setVisible(false);
								btnRecalculate2.setVisible(false);
								btnRecalculate3.setVisible(false);
								
								btnNew.setVisible(false);
								btnView.setVisible(true);
								btnEdit.setVisible(false);
								btnDelete.setVisible(false);
								btnExport.setVisible(false);
								btnImport.setVisible(false);
								
								btnNew_B.setVisible(false);
								btnView_B.setVisible(true);
								btnEdit_B.setVisible(false);
								btnDelete_B.setVisible(false);
								btnExport_B.setVisible(false);
								btnImport_B.setVisible(false);
								
								btnNew_C.setVisible(false);
								btnView_C.setVisible(true);
								btnEdit_C.setVisible(false);
								btnDelete_C.setVisible(false);
								btnExport_C.setVisible(false);
								btnImport_C.setVisible(false);
								
								btnNew_D.setVisible(false);
								btnView_D.setVisible(true);
								btnEdit_D.setVisible(false);
								btnDelete_D.setVisible(false);
								btnExport_D.setVisible(false);
								btnImport_D.setVisible(false);
								
								btnNew_E.setVisible(true);
								btnView_E.setVisible(true);
								btnEdit_E.setVisible(true);
								btnDelete_E.setVisible(true);
								
								btnUploadBSO.setVisible(false);
								btnUploadInfoPrice.setVisible(false);
								btnUploadFilePOCustomer.setVisible(false);
								
								dcbIncentiveApproveHw3ps.setReadonly(true);	
								dcbIncentiveNonsalesHw3ps.setReadonly(true);
								dcbIncentiveKomisiHw3ps.setReadonly(true);
								//dcbIncentiveSbonusHw3ps.setReadonly(false);
							
								dcbIncentiveApproveAcsps.setReadonly(true);	
								dcbIncentiveNonsalesAcsps.setReadonly(true);
								dcbIncentiveKomisiAcsps.setReadonly(true);
								//dcbIncentiveSbonusAcsps.setReadonly(false);
								
								dcbIncentiveApproveOwnsw.setReadonly(true);	
								dcbIncentiveNonsalesOwnsw.setReadonly(true);
								dcbIncentiveKomisiOwnsw.setReadonly(true);
								//dcbIncentiveSbonusOwnsw.setReadonly(false);
								
								dcbCalculateTotalMarginPcn.setReadonly(true);
								dcbCalculateHw3PSMarginPcn.setReadonly(true);
								dcbCalculateTotalMarginOnlyPcn.setReadonly(true);
								dcbCalculateHwMarginOnlyPcn.setReadonly(true);
								dcbCalculateHw3PSMarginOnlyPcn.setReadonly(true);
								
								btnSearchCustomerLOV.setDisabled(true);	
								
								// set focus 
								cmbCompany.setFocus(true);	
							} else {
								if (pPosisiCosting.equals("SM") == true) {
									// set read only key
									cmbCompany.setDisabled(true);
									cmbSalesman.setDisabled(true);		
									dbTglCosting.setDisabled(true);	
								
									txtNoCosting.setReadonly(true);
									txtNoCosting.setVisible(true);
									
									txtNoBso.setReadonly(false);
									txtNoSo.setReadonly(false);
									txtNoPoCustomer.setReadonly(false);
									txtCustomer.setReadonly(false);
									txtNoPenawaran.setReadonly(false);
									
									btnRecalculate.setVisible(false);
									btnRecalculate1.setVisible(false);
									btnRecalculate2.setVisible(false);
									btnRecalculate3.setVisible(false);
									
									btnNew.setVisible(false);
									btnView.setVisible(true);
									btnEdit.setVisible(false);
									btnDelete.setVisible(false);
									btnExport.setVisible(false);
									btnImport.setVisible(false);
									
									btnNew_B.setVisible(false);
									btnView_B.setVisible(true);
									btnEdit_B.setVisible(false);
									btnDelete_B.setVisible(false);
									btnExport_B.setVisible(false);
									btnImport_B.setVisible(false);
									
									btnNew_C.setVisible(false);
									btnView_C.setVisible(true);
									btnEdit_C.setVisible(false);
									btnDelete_C.setVisible(false);
									btnExport_C.setVisible(false);
									btnImport_C.setVisible(false);
									
									btnNew_D.setVisible(false);
									btnView_D.setVisible(true);
									btnEdit_D.setVisible(false);
									btnDelete_D.setVisible(false);
									btnExport_D.setVisible(false);
									btnImport_D.setVisible(false);
									
									btnNew_E.setVisible(false);
									btnView_E.setVisible(false);
									btnEdit_E.setVisible(false);
									btnDelete_E.setVisible(false);
									
									btnUploadBSO.setVisible(true);
									btnUploadInfoPrice.setVisible(true);
									btnUploadFilePOCustomer.setVisible(true);
									
									dcbIncentiveApproveHw3ps.setReadonly(false);	
									dcbIncentiveNonsalesHw3ps.setReadonly(false);
									dcbIncentiveKomisiHw3ps.setReadonly(false);
									//dcbIncentiveSbonusHw3ps.setReadonly(false);
								
									dcbIncentiveApproveAcsps.setReadonly(false);	
									dcbIncentiveNonsalesAcsps.setReadonly(false);
									dcbIncentiveKomisiAcsps.setReadonly(false);
									//dcbIncentiveSbonusAcsps.setReadonly(false);
									
									dcbIncentiveApproveOwnsw.setReadonly(false);	
									dcbIncentiveNonsalesOwnsw.setReadonly(false);
									dcbIncentiveKomisiOwnsw.setReadonly(false);
									//dcbIncentiveSbonusOwnsw.setReadonly(false);
									
									dcbCalculateTotalMarginPcn.setReadonly(true);
									dcbCalculateHw3PSMarginPcn.setReadonly(true);
									dcbCalculateTotalMarginOnlyPcn.setReadonly(true);
									dcbCalculateHwMarginOnlyPcn.setReadonly(true);
									dcbCalculateHw3PSMarginOnlyPcn.setReadonly(true);
									
									btnSearchCustomerLOV.setDisabled(false);	
									
									// set focus 
									cmbCompany.setFocus(true);	
								}
							}
						}
					}
				}
			}
		}
	}

	
	public void onClick$btnSearchCustomerLOV(Event event) {
		V01CustomerNav cust = V01CustomerNavLOV.show(windowT29CostingDetail);
		
		if (cust != null) {
			txtCustomer.setValue(cust.getCustName());
			
			
			getT29CostingH().setCustomer(cust.getCustName());			
		}
	}
	public String validasiBusinessRules(){
		
		if(CommonUtils.isNotEmpty(dbTglCosting.getValue()) == false){
			return "Tanggal Costing "+Labels.getLabel("message.error.mandatory");
		}	
		
		

		
		
		return null;
	}
    

	public void displayDetail(	List<T30CostingDHw3ps> dataT30CostingDHw3ps, 
								List<T31CostingDAcsps> dataT31CostingDAcsps, 
								List<T32CostingDOwnsw> dataT32CostingDOwnsw,
								List<T33CostingDOther> dataT33CostingDOther,
								List<T34CostingDPayment> dataT34CostingDPayment
								) {
		
		// Detail 1 - T30CostingD_HW3PS
		list_Deleted_T30CostingD_HW3PS_List.clear();
		list_T30CostingD_HW3PS_List.clear();

		if (CommonUtils.isNotEmpty(dataT30CostingDHw3ps)) {
			list_T30CostingD_HW3PS_List.addAll(dataT30CostingDHw3ps);
		}

		setModel_T30CostingD_HW3PS_List();
		
		
		// Detail 2 - T31CostingD_ACSPS
		list_Deleted_T31CostingD_ACSPS_List.clear();
		list_T31CostingD_ACSPS_List.clear();

		if (CommonUtils.isNotEmpty(dataT31CostingDAcsps)) {
			list_T31CostingD_ACSPS_List.addAll(dataT31CostingDAcsps);
		}

		setModel_T31CostingD_ACSPS_List();
		
		// Detail 3 - T32CostingD_OWNSW
		list_Deleted_T32CostingD_OWNSW_List.clear();
		list_T32CostingD_OWNSW_List.clear();

		if (CommonUtils.isNotEmpty(dataT32CostingDOwnsw)) {
			list_T32CostingD_OWNSW_List.addAll(dataT32CostingDOwnsw);
		}

		setModel_T32CostingD_OWNSW_List();
		
		// Detail 4 - T33CostingD_OTHER
		list_Deleted_T33CostingD_OTHER_List.clear();
		list_T33CostingD_OTHER_List.clear();

		if (CommonUtils.isNotEmpty(dataT33CostingDOther)) {
			list_T33CostingD_OTHER_List.addAll(dataT33CostingDOther);
		}

		setModel_T33CostingD_OTHER_List();
		
		// Detail 4 - T34CostingD_PAYMENT
		list_Deleted_T34CostingD_PAYMENT_List.clear();
		list_T34CostingD_PAYMENT_List.clear();

		if (CommonUtils.isNotEmpty(dataT34CostingDPayment)) {
			list_T34CostingD_PAYMENT_List.addAll(dataT34CostingDPayment);
		}

		setModel_T34CostingD_PAYMENT_List();
	}
	
	private void HitDetailTotal(){		
		
		BigDecimal vTotalSales = new BigDecimal(0);
		BigDecimal vSalesHw3ps = new BigDecimal(0);
		BigDecimal vSalesAcsps = new BigDecimal(0);
		BigDecimal vSalesOwnsw = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbSalesHW3PS.getValue())){
			vSalesHw3ps = dcbSalesHW3PS.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbsalesACSPS.getValue())){
			vSalesAcsps = dcbsalesACSPS.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbsalesOWNSW.getValue())){
			vSalesOwnsw = dcbsalesOWNSW.getValue();
		}
		
		vTotalSales = (vSalesHw3ps.add(vSalesAcsps)).add(vSalesOwnsw);
		//vTotalSales = vTotalSales.round(vPembulatanAmount);
		
		this.dcbTotalSales.setValue(vTotalSales);
		getT29CostingH().setTotalSales(vTotalSales);
		
		BigDecimal vTotalCOGS = new BigDecimal(0);
		BigDecimal vCogsHw3ps = new BigDecimal(0);
		BigDecimal vCogsOthers = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbCOGSHW3PS.getValue())){
			vCogsHw3ps = dcbCOGSHW3PS.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbcogsOTHERS.getValue())){
			vCogsOthers = dcbcogsOTHERS.getValue();
		}
		
		vTotalCOGS = vCogsHw3ps.add(vCogsOthers);
		//vTotalCOGS = vTotalCOGS.round(vPembulatanAmount);
		
		this.dcbTotalCOGS.setValue(vTotalCOGS);
		this.dcbTotalCOGS_2.setValue(vTotalCOGS);
		getT29CostingH().setTotalCogs(vTotalCOGS);

		
		BigDecimal vTotalMarginNonAcsPs = new BigDecimal(0);
		BigDecimal vTotalMarginNonAcsPsPcn = new BigDecimal(0);
		
		vTotalMarginNonAcsPs = vSalesHw3ps.subtract(vTotalCOGS);
		
		if (vSalesHw3ps.compareTo(new BigDecimal(0)) > 0){
			vTotalMarginNonAcsPsPcn = (vTotalMarginNonAcsPs.multiply(new BigDecimal(100.00))).divide(vSalesHw3ps, 2, RoundingMode.HALF_UP);	
		} else {
			vTotalMarginNonAcsPsPcn = new BigDecimal(0);
		}
		
		//vTotalMarginNonAcsPsPcn = vTotalMarginNonAcsPsPcn.round(vPembulatanPcn);
		
		this.dcbMarginPcnNonacsps.setValue(vTotalMarginNonAcsPsPcn);
		getT29CostingH().setMarginPcnNonacsps(vTotalMarginNonAcsPsPcn);
		
		
		BigDecimal vTotalMargin = new BigDecimal(0);
		BigDecimal vTotalMarginPcn = new BigDecimal(0);
		
		vTotalMargin = vTotalSales.subtract(vTotalCOGS);
		
		
		if (vTotalSales.compareTo(new BigDecimal(0)) > 0){
			vTotalMarginPcn = (vTotalMargin.multiply(new BigDecimal(100.00))).divide(vTotalSales, 2, RoundingMode.HALF_UP);	
		} else {
			vTotalMarginPcn = new BigDecimal(0);
		}
		
		//vTotalMarginPcn = vTotalMarginPcn.round(vPembulatanPcn);
		
		this.dcbMarginPcn.setValue(vTotalMarginPcn);
		getT29CostingH().setMarginPcn(vTotalMarginPcn);
		
		
		
		// Parameter perhitungan Incentive untuk Hardware dan 3 Party Service
		
		BigDecimal vPengali = new BigDecimal(0.1);
		BigDecimal vBatasI = new BigDecimal(20.00);
		BigDecimal vPengurangBatasI = new BigDecimal(7.5);
		BigDecimal vBatasII = new BigDecimal(10.00);
		BigDecimal vPengurangBatasII = new BigDecimal(10.00);
		
		BigDecimal vIncentiveACSPS = new BigDecimal(5);
		BigDecimal vIncentiveOWNSW = new BigDecimal(5);
		
		
		
		
		BigDecimal vPcnIncentiveFormula = new BigDecimal(0);
		
		
		
		if (vTotalMarginNonAcsPsPcn.compareTo(vBatasI) >= 0){
			
			vPcnIncentiveFormula = (vTotalMarginNonAcsPsPcn.subtract(vPengurangBatasI)).multiply(vPengali);
	
		} else {
			if (vTotalMarginNonAcsPsPcn.compareTo(vBatasII) >= 0){
				vPcnIncentiveFormula = (vTotalMarginNonAcsPsPcn.subtract(vPengurangBatasII)).multiply(vPengali);

			} else {
				vPcnIncentiveFormula = new BigDecimal(0);
			}
		}
		
		
		vPcnIncentiveFormula = vPcnIncentiveFormula.divide(new BigDecimal(1), 2, RoundingMode.HALF_UP);

		this.dcbIncentiveFormulaHw3ps.setValue(vPcnIncentiveFormula);
		this.dcbIncentiveApproveHw3ps.setValue(vPcnIncentiveFormula);
		
		getT29CostingH().setIncentiveFormulaHw3ps(vPcnIncentiveFormula);
		getT29CostingH().setIncentiveApproveHw3ps(vPcnIncentiveFormula);		
		
		vIncentiveACSPS = vIncentiveACSPS.divide(new BigDecimal(1), 2, RoundingMode.HALF_UP);
		
		this.dcbIncentiveFormulaAcsps.setValue(vIncentiveACSPS);		
		this.dcbIncentiveApproveAcsps.setValue(vIncentiveACSPS);	
		
		getT29CostingH().setIncentiveFormulaAcsps(vIncentiveACSPS);
		getT29CostingH().setIncentiveApproveAcsps(vIncentiveACSPS);		
		
		vIncentiveOWNSW = vIncentiveOWNSW.divide(new BigDecimal(1), 2, RoundingMode.HALF_UP);
		
		this.dcbIncentiveFormulaOwnsw.setValue(vIncentiveOWNSW);		
		this.dcbIncentiveApproveOwnsw.setValue(vIncentiveOWNSW);		
		
		getT29CostingH().setIncentiveFormulaOwnsw(vIncentiveOWNSW);
		getT29CostingH().setIncentiveApproveOwnsw(vIncentiveOWNSW);		
				
		// Hardware & 3rd party Services
		HitAmountIncentiveHw3ps();
		
		// ACS Profesional Services
		HitAmountIncentiveAcsps();
		
		// ACS Own Software
		HitAmountIncentiveOwnsw();
	}
	
	private void HitAmountTotalIncentive(){
		// Non Sales Bonus
		BigDecimal vAmountNonsalesHw3ps = new BigDecimal(0);
		BigDecimal vAmountNonsalesAcsps = new BigDecimal(0);
		BigDecimal vAmountNonsalesOwnsw = new BigDecimal(0);
		BigDecimal vAmountNonsales = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbAmountNonsalesHw3ps.getValue())){
			vAmountNonsalesHw3ps = dcbAmountNonsalesHw3ps.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbAmountNonsalesAcsps.getValue())){
			vAmountNonsalesAcsps = dcbAmountNonsalesAcsps.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbAmountNonsalesOwnsw.getValue())){
			vAmountNonsalesOwnsw = dcbAmountNonsalesOwnsw.getValue();
		}
		
		vAmountNonsales = (vAmountNonsalesHw3ps.add(vAmountNonsalesAcsps)).add(vAmountNonsalesOwnsw);
		this.dcbAmountNonsales.setValue(vAmountNonsales);
		this.dcbAmountNonsales_2.setValue(vAmountNonsales);
		getT29CostingH().setAmountNonsales(vAmountNonsales);
		
		// Komisi
		BigDecimal vAmountKomisiHw3ps = new BigDecimal(0);
		BigDecimal vAmountKomisiAcsps = new BigDecimal(0);
		BigDecimal vAmountKomisiOwnsw = new BigDecimal(0);
		BigDecimal vAmountKomisi = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbAmountKomisiHw3ps.getValue())){
			vAmountKomisiHw3ps = dcbAmountKomisiHw3ps.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbAmountKomisiAcsps.getValue())){
			vAmountKomisiAcsps = dcbAmountKomisiAcsps.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbAmountKomisiOwnsw.getValue())){
			vAmountKomisiOwnsw = dcbAmountKomisiOwnsw.getValue();
		}
		
		vAmountKomisi = (vAmountKomisiHw3ps.add(vAmountKomisiAcsps)).add(vAmountKomisiOwnsw);
		this.dcbAmountKomisi.setValue(vAmountKomisi);
		this.dcbAmountKomisi_2.setValue(vAmountKomisi);
		getT29CostingH().setAmountKomisi(vAmountKomisi);
		
		// Sales Bonus
		BigDecimal vAmountSbonusHw3ps = new BigDecimal(0);
		BigDecimal vAmountSbonusAcsps = new BigDecimal(0);
		BigDecimal vAmountSbonusOwnsw = new BigDecimal(0);
		BigDecimal vAmountSbonus = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbAmountSbonusHw3ps.getValue())){
			vAmountSbonusHw3ps = dcbAmountSbonusHw3ps.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbAmountSbonusAcsps.getValue())){
			vAmountSbonusAcsps = dcbAmountSbonusAcsps.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbAmountSbonusOwnsw.getValue())){
			vAmountSbonusOwnsw = dcbAmountSbonusOwnsw.getValue();
		}
		
		vAmountSbonus = (vAmountSbonusHw3ps.add(vAmountSbonusAcsps)).add(vAmountSbonusOwnsw);
		this.dcbAmountSbonus.setValue(vAmountSbonus);
		this.dcbAmountSbonus_2.setValue(vAmountSbonus);
		getT29CostingH().setAmountSbonus(vAmountSbonus);
				
		// TSQ Amount
				
		BigDecimal vAmountTqsHw3ps = new BigDecimal(0);
		BigDecimal vAmountTqsAcsps = new BigDecimal(0);
		BigDecimal vAmountTqsOwnsw = new BigDecimal(0);
		BigDecimal vAmountSalesTqs = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbAmountTqsHw3ps.getValue())){
			vAmountTqsHw3ps = dcbAmountTqsHw3ps.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbAmountTqsAcsps.getValue())){
			vAmountTqsAcsps = dcbAmountTqsAcsps.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbAmountTqsOwnsw.getValue())){
			vAmountTqsOwnsw = dcbAmountTqsOwnsw.getValue();
		}
		
		vAmountSalesTqs = (vAmountTqsHw3ps.add(vAmountTqsAcsps)).add(vAmountTqsOwnsw);
		this.dcbAmountSalesTqs.setValue(vAmountSalesTqs);
		getT29CostingH().setAmountSalesTqs(vAmountSalesTqs);
		
	}
	private void HitAmountIncentiveHw3ps (){
		BigDecimal vSalesHw3ps = new BigDecimal(0);
		
		BigDecimal vPcnNonSalesHw3ps = new BigDecimal(0);
		BigDecimal vAmountNonSalesHw3ps = new BigDecimal(0);
		
		BigDecimal vPcnKomisiHw3ps = new BigDecimal(0);
		BigDecimal vAmountKomisiHw3ps = new BigDecimal(0);
		
		BigDecimal vPcnSBonusHw3ps = new BigDecimal(0);
		BigDecimal vAmountSBonusHw3ps = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbSalesHW3PS.getValue())){
			vSalesHw3ps = dcbSalesHW3PS.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbIncentiveNonsalesHw3ps.getValue())){
			vPcnNonSalesHw3ps = dcbIncentiveNonsalesHw3ps.getValue();
		}
		
		vAmountNonSalesHw3ps = (vPcnNonSalesHw3ps.multiply(vSalesHw3ps)).divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP);		
		//vAmountNonSalesHw3ps = vAmountNonSalesHw3ps.round(vPembulatanAmount);
		
		this.dcbAmountNonsalesHw3ps.setValue(vAmountNonSalesHw3ps);
		getT29CostingH().setAmountNonsalesHw3ps(vAmountNonSalesHw3ps);	
		
		if (CommonUtils.isNotEmpty(dcbIncentiveKomisiHw3ps.getValue())){
			vPcnKomisiHw3ps = dcbIncentiveKomisiHw3ps.getValue();
		}
		
		vAmountKomisiHw3ps = (vPcnKomisiHw3ps.multiply(vSalesHw3ps)).divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP);
		//vAmountKomisiHw3ps = vAmountKomisiHw3ps.round(vPembulatanAmount);		
		
		this.dcbAmountKomisiHw3ps.setValue(vAmountKomisiHw3ps);
		getT29CostingH().setAmountKomisiHw3ps(vAmountKomisiHw3ps);	
		
		
		BigDecimal vIncentiveApproveHw3ps = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcbIncentiveApproveHw3ps.getValue())){
			vIncentiveApproveHw3ps = dcbIncentiveApproveHw3ps.getValue();
		}
		
		
		vPcnSBonusHw3ps = vIncentiveApproveHw3ps.subtract(vPcnNonSalesHw3ps.add(vPcnKomisiHw3ps));
		vPcnSBonusHw3ps = vPcnSBonusHw3ps.divide(new BigDecimal(1), 2, RoundingMode.HALF_UP);
		this.dcbIncentiveSbonusHw3ps.setValue(vPcnSBonusHw3ps);
		getT29CostingH().setIncentiveSbonusHw3ps(vPcnSBonusHw3ps);	
		
		vAmountSBonusHw3ps = (vPcnSBonusHw3ps.multiply(vSalesHw3ps)).divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP);
		//vAmountSBonusHw3ps = vAmountSBonusHw3ps.round(vPembulatanAmount);
		
		this.dcbAmountSbonusHw3ps.setValue(vAmountSBonusHw3ps);
		getT29CostingH().setAmountSbonusHw3ps(vAmountSBonusHw3ps);	
		
		
		BigDecimal vAmountTqsHw3ps = new BigDecimal(0);
		if (vAmountSBonusHw3ps.compareTo(new BigDecimal (0))> 0){
			vAmountTqsHw3ps = vSalesHw3ps;
		} 
		
		this.dcbAmountTqsHw3ps.setValue(vAmountTqsHw3ps);
		getT29CostingH().setAmountTqsHw3ps(vAmountTqsHw3ps);	
		
		HitAmountTotalIncentive();
		
		
	}
	
	private void HitAmountIncentiveAcsps (){		
		BigDecimal vSalesAcsps = new BigDecimal(0);
		
		BigDecimal vPcnNonSalesAcsps = new BigDecimal(0);
		BigDecimal vAmountNonSalesAcsps = new BigDecimal(0);
		
		BigDecimal vPcnKomisiAcsps = new BigDecimal(0);
		BigDecimal vAmountKomisiAcsps = new BigDecimal(0);
		
		BigDecimal vPcnSBonusAcsps = new BigDecimal(0);
		BigDecimal vAmountSBonusAcsps = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbsalesACSPS.getValue())){
			vSalesAcsps = dcbsalesACSPS.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbIncentiveNonsalesAcsps.getValue())){
			vPcnNonSalesAcsps = dcbIncentiveNonsalesAcsps.getValue();
		}
		
		vAmountNonSalesAcsps = (vPcnNonSalesAcsps.multiply(vSalesAcsps)).divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP);	
		//vAmountNonSalesAcsps = vAmountNonSalesAcsps.round(vPembulatanAmount);
		
		this.dcbAmountNonsalesAcsps.setValue(vAmountNonSalesAcsps);
		getT29CostingH().setAmountNonsalesAcsps(vAmountNonSalesAcsps);	
		
		if (CommonUtils.isNotEmpty(dcbIncentiveKomisiAcsps.getValue())){
			vPcnKomisiAcsps = dcbIncentiveKomisiAcsps.getValue();
		}
		
		vAmountKomisiAcsps = (vPcnKomisiAcsps.multiply(vSalesAcsps)).divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP);
		//vAmountKomisiAcsps = vAmountKomisiAcsps.round(vPembulatanAmount);
		
		this.dcbAmountKomisiAcsps.setValue(vAmountKomisiAcsps);
		getT29CostingH().setAmountKomisiAcsps(vAmountKomisiAcsps);	
		
		
		BigDecimal vIncentiveApproveAcsps = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcbIncentiveApproveAcsps.getValue())){
			vIncentiveApproveAcsps = dcbIncentiveApproveAcsps.getValue();
		}
		
		
		vPcnSBonusAcsps = vIncentiveApproveAcsps.subtract(vPcnNonSalesAcsps.add(vPcnKomisiAcsps));
		vPcnSBonusAcsps = vPcnSBonusAcsps.divide(new BigDecimal(1), 2, RoundingMode.HALF_UP);
		this.dcbIncentiveSbonusAcsps.setValue(vPcnSBonusAcsps);
		getT29CostingH().setIncentiveSbonusAcsps(vPcnSBonusAcsps);	
		
		vAmountSBonusAcsps = (vPcnSBonusAcsps.multiply(vSalesAcsps)).divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP);
		//vAmountSBonusAcsps = vAmountSBonusAcsps.round(vPembulatanAmount);
		
		this.dcbAmountSbonusAcsps.setValue(vAmountSBonusAcsps);
		getT29CostingH().setAmountSbonusAcsps(vAmountSBonusAcsps);	
		
		BigDecimal vAmountTqsAcsps = new BigDecimal(0);
		if (vAmountSBonusAcsps.compareTo(new BigDecimal (0))> 0){
			vAmountTqsAcsps = vSalesAcsps;
		} 
		
		this.dcbAmountTqsAcsps.setValue(vAmountTqsAcsps);
		getT29CostingH().setAmountTqsAcsps(vAmountTqsAcsps);	
		
		HitAmountTotalIncentive();
		
	}
	
	private void HitAmountIncentiveOwnsw (){
		
		BigDecimal vSalesOwnsw = new BigDecimal(0);
		
		BigDecimal vPcnNonSalesOwnsw = new BigDecimal(0);
		BigDecimal vAmountNonSalesOwnsw = new BigDecimal(0);
		
		BigDecimal vPcnKomisiOwnsw = new BigDecimal(0);
		BigDecimal vAmountKomisiOwnsw = new BigDecimal(0);
		
		BigDecimal vPcnSBonusOwnsw = new BigDecimal(0);
		BigDecimal vAmountSBonusOwnsw = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbsalesOWNSW.getValue())){
			vSalesOwnsw = dcbsalesOWNSW.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbIncentiveNonsalesOwnsw.getValue())){
			vPcnNonSalesOwnsw = dcbIncentiveNonsalesOwnsw.getValue();
		}
		
		vAmountNonSalesOwnsw = (vPcnNonSalesOwnsw.multiply(vSalesOwnsw)).divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP);		
		//vAmountNonSalesOwnsw = vAmountNonSalesOwnsw.round(vPembulatanAmount);
		
		this.dcbAmountNonsalesOwnsw.setValue(vAmountNonSalesOwnsw);
		getT29CostingH().setAmountNonsalesOwnsw(vAmountNonSalesOwnsw);	
		
		if (CommonUtils.isNotEmpty(dcbIncentiveKomisiOwnsw.getValue())){
			vPcnKomisiOwnsw = dcbIncentiveKomisiOwnsw.getValue();
		}
		
		vAmountKomisiOwnsw = (vPcnKomisiOwnsw.multiply(vSalesOwnsw)).divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP);
		//vAmountKomisiOwnsw = vAmountKomisiOwnsw.round(vPembulatanAmount);		
		
		this.dcbAmountKomisiOwnsw.setValue(vAmountKomisiOwnsw);
		getT29CostingH().setAmountKomisiOwnsw(vAmountKomisiOwnsw);	
		
		
		BigDecimal vIncentiveApproveOwnsw = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcbIncentiveApproveOwnsw.getValue())){
			vIncentiveApproveOwnsw = dcbIncentiveApproveOwnsw.getValue();
		}
		
		
		vPcnSBonusOwnsw = vIncentiveApproveOwnsw.subtract(vPcnNonSalesOwnsw.add(vPcnKomisiOwnsw));
		vPcnSBonusOwnsw = vPcnSBonusOwnsw.divide(new BigDecimal(1), 2, RoundingMode.HALF_UP);
		this.dcbIncentiveSbonusOwnsw.setValue(vPcnSBonusOwnsw);
		getT29CostingH().setIncentiveSbonusOwnsw(vPcnSBonusOwnsw);	
		
		vAmountSBonusOwnsw = (vPcnSBonusOwnsw.multiply(vSalesOwnsw)).divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP);
		//vAmountSBonusOwnsw = vAmountSBonusOwnsw.round(vPembulatanAmount);
		
		this.dcbAmountSbonusOwnsw.setValue(vAmountSBonusOwnsw);
		getT29CostingH().setAmountSbonusOwnsw(vAmountSBonusOwnsw);	
		
		BigDecimal vAmountTqsOwnsw = new BigDecimal(0);
		if (vAmountSBonusOwnsw.compareTo(new BigDecimal (0))> 0){
			vAmountTqsOwnsw = vSalesOwnsw;
		} 
		
		this.dcbAmountTqsOwnsw.setValue(vAmountTqsOwnsw);
		getT29CostingH().setAmountTqsOwnsw(vAmountTqsOwnsw);
		
		HitAmountTotalIncentive();
		
	}

	public void onChange$dcbIncentiveApproveHw3ps(Event event) {
		HitAmountIncentiveHw3ps();
	}
	
	public void onChange$dcbIncentiveNonsalesHw3ps(Event event) {
		HitAmountIncentiveHw3ps();
	}
	
	public void onChange$dcbIncentiveKomisiHw3ps(Event event) {
		HitAmountIncentiveHw3ps();
	}
	
	public void onChange$dcbIncentiveApproveAcsps(Event event) {
		HitAmountIncentiveAcsps();
	}
	
	public void onChange$dcbIncentiveNonsalesAcsps(Event event) {
		HitAmountIncentiveAcsps();
	}
	
	public void onChange$dcbIncentiveKomisiAcsps(Event event) {
		HitAmountIncentiveAcsps();
	}
	
	public void onChange$dcbIncentiveApproveOwnsw(Event event) {
		HitAmountIncentiveOwnsw();
	}
	
	public void onChange$dcbIncentiveNonsalesOwnsw(Event event) {
		HitAmountIncentiveOwnsw();
	}
	
	public void onChange$dcbIncentiveKomisiOwnsw(Event event) {
		HitAmountIncentiveOwnsw();
	}
	
	private void HitDetailT30CostingDHw3ps (){
		
		BigDecimal vTotalSalesHw3ps = new BigDecimal(0);
		BigDecimal vTotalCOGSHw3ps = new BigDecimal(0);
		
		for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List) {
			if (CommonUtils.isNotEmpty(aData.getSalesTotal())){
				vTotalSalesHw3ps = vTotalSalesHw3ps.add(aData.getSalesTotal());
			}
			
			if (CommonUtils.isNotEmpty(aData.getCogsTotal())){
				vTotalCOGSHw3ps = vTotalCOGSHw3ps.add(aData.getCogsTotal());
			}
		}
		
		
		//vTotalSalesHw3ps = vTotalSalesHw3ps.round(vPembulatanAmount);
		//vTotalCOGSHw3ps = vTotalCOGSHw3ps.round(vPembulatanAmount);
		
		this.dcbSalesHW3PS.setValue(vTotalSalesHw3ps);
		this.dcbSalesHW3PS_2.setValue(vTotalSalesHw3ps);
		getT29CostingH().setSalesHw3ps(vTotalSalesHw3ps);
		
		
		this.dcbCOGSHW3PS.setValue(vTotalCOGSHw3ps);
		getT29CostingH().setCogsHw3ps(vTotalCOGSHw3ps);
		
		
		HitDetailTotal();
	}
	
	private void HitDetailT31CostingDAcsps(){
		BigDecimal vTotalSalesAcsps = new BigDecimal(0);
		
		for (T31CostingDAcsps aData : list_T31CostingD_ACSPS_List) {
			if (CommonUtils.isNotEmpty(aData.getSalesTotal())){
				vTotalSalesAcsps = vTotalSalesAcsps.add(aData.getSalesTotal());
			}
		}
		
		//vTotalSalesAcsps = vTotalSalesAcsps.round(vPembulatanAmount);
		
		this.dcbsalesACSPS.setValue(vTotalSalesAcsps);
		this.dcbsalesACSPS_2.setValue(vTotalSalesAcsps);
		getT29CostingH().setSalesAcsps(vTotalSalesAcsps);
		
		HitDetailTotal();
	}
	
	private void HitDetailT32CostingDOwnsw(){
		BigDecimal vTotalSalesOwnsw = new BigDecimal(0);
		
		for (T32CostingDOwnsw aData : list_T32CostingD_OWNSW_List) {
			if (CommonUtils.isNotEmpty(aData.getSalesTotal())){
				vTotalSalesOwnsw = vTotalSalesOwnsw.add(aData.getSalesTotal());
			}
		}
		
		//vTotalSalesOwnsw = vTotalSalesOwnsw.round(vPembulatanAmount);
		
		this.dcbsalesOWNSW.setValue(vTotalSalesOwnsw);
		this.dcbsalesOWNSW_2.setValue(vTotalSalesOwnsw);
		
		getT29CostingH().setSalesOwnsw(vTotalSalesOwnsw);
		
		HitDetailTotal();
	}
	
	private void HitDetailT33CostingDOther(){
		BigDecimal vTotalCogsOther = new BigDecimal(0);
		
		for (T33CostingDOther aData : list_T33CostingD_OTHER_List) {
			if (CommonUtils.isNotEmpty(aData.getCogsTotal())){
				vTotalCogsOther = vTotalCogsOther.add(aData.getCogsTotal());
			}
		}
		
		//vTotalCogsOther = vTotalCogsOther.round(vPembulatanAmount);
		
		this.dcbcogsOTHERS.setValue(vTotalCogsOther);
		
		getT29CostingH().setCogsOthers(vTotalCogsOther);
		
		HitDetailTotal();
	}

	
	private void HitDetailT34CostingDPayment(){
		BigDecimal vAmountInvoice = new BigDecimal(0);
		BigDecimal vAmountLunas = new BigDecimal(0);
		
		int vAdaInvoice = 0;
		int vAdaPayment = 0;
		
		for (T34CostingDPayment aData : list_T34CostingD_PAYMENT_List) {
			if (CommonUtils.isNotEmpty(aData.getNoInvoice())){
				vAdaInvoice = vAdaInvoice +1;
			}
			
			if (CommonUtils.isNotEmpty(aData.getNilaiInvoice())){
				vAmountInvoice = vAmountInvoice.add(aData.getNilaiInvoice());
			}
			
			if (CommonUtils.isNotEmpty(aData.getNoLunas())){
				vAdaPayment = vAdaPayment +1;
			}
			
			if (CommonUtils.isNotEmpty(aData.getNilaiLunas())){
				vAmountLunas = vAmountLunas.add(aData.getNilaiLunas());
			}
		}
		
		//vTotalCogsOther = vTotalCogsOther.round(vPembulatanAmount);
		
		this.dcbAmountInvoice.setValue(vAmountInvoice);
		this.dcbAmountLunas.setValue(vAmountLunas);
		
		if (vAdaInvoice > 0) {
			getT29CostingH().setFlagInvoice("Y");
		} else {
			getT29CostingH().setFlagInvoice("N");
		}
		
		
		if (vAmountInvoice.compareTo(vAmountLunas) <= 0) {
			getT29CostingH().setFlagLunas("Y");
		} else {
			getT29CostingH().setFlagLunas("N");
		}
		
		getT29CostingH().setAmountInvoice(vAmountInvoice);
		getT29CostingH().setAmountLunas(vAmountLunas);
		
	}
	
	
	// Action Button Detail 1 - T30CostingD_HW3PS -------------------------------------------------------------------------------------
	public void onClick$btnNew(Event event) {
		T30CostingDHw3ps newDetail = new T30CostingDHw3ps();
		newDetail.setT29CostingH(getT29CostingH());
		
		newDetail.setQty(new BigDecimal(0));
		newDetail.setSalesSatuan(new BigDecimal(0));
		newDetail.setSalesTotal(new BigDecimal(0));
		newDetail.setCogsSatuan(new BigDecimal(0));
		newDetail.setCogsTotal(new BigDecimal(0));

		T30CostingDHw3ps newValue = T30CostingD_HW3PS_AddWindow.show(
				windowT29CostingDetail, newDetail, getT29CostingH(), "New", selectQueryService);

		if (newValue != null) {			
			list_T30CostingD_HW3PS_List.add(newValue);
			setModel_T30CostingD_HW3PS_List();
			
			HitDetailT30CostingDHw3ps ();
			 
			onClick$btnNew(event);
		}
	}
	
	public void onClick$btnView(Event event) {
		if (listBox_T30CostingD_HW3PS.getSelectedItem() != null) {
			T30CostingDHw3ps selectedItem = (T30CostingDHw3ps) listBox_T30CostingD_HW3PS
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_T30CostingD_HW3PS_List.indexOf(selectedItem);

				T30CostingDHw3ps viewValue = T30CostingD_HW3PS_AddWindow.show(
						windowT29CostingDetail, selectedItem, getT29CostingH(), "View", selectQueryService);
				if (viewValue != null) {
					list_T30CostingD_HW3PS_List.set(index, viewValue);
					setModel_T30CostingD_HW3PS_List();
					HitDetailT30CostingDHw3ps ();
				}
			}
		}
	}
	
	public void onClick$btnEdit(Event event) {
		if (listBox_T30CostingD_HW3PS.getSelectedItem() != null) {
			T30CostingDHw3ps selectedItem = (T30CostingDHw3ps) listBox_T30CostingD_HW3PS
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_T30CostingD_HW3PS_List.indexOf(selectedItem);

				T30CostingDHw3ps editValue = T30CostingD_HW3PS_AddWindow.show(
						windowT29CostingDetail, selectedItem, getT29CostingH(), "Edit", selectQueryService);
				if (editValue != null) {
					list_T30CostingD_HW3PS_List.set(index, editValue);
					setModel_T30CostingD_HW3PS_List();
					HitDetailT30CostingDHw3ps ();
				}
			}
		}
	}
	
	public void onClick$btnDelete(Event event) throws InterruptedException {
		if (listBox_T30CostingD_HW3PS.getSelectedItem() != null) {
			final T30CostingDHw3ps selectedItem = (T30CostingDHw3ps) listBox_T30CostingD_HW3PS
					.getSelectedItem().getValue();
			if (selectedItem != null) {

				// Show a confirm box
				String deleteRecord = selectedItem.getItemDesc();
				
				final String msg = Labels
						.getLabel("message.Question.Are_you_sure_to_delete_this_record")
						+ "\n\n --> " + deleteRecord;
				final String title = Labels.getLabel("message.Deleting.Record");

				MultiLineMessageBox.doSetTemplate();
				if (MultiLineMessageBox.show(msg, title, Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION, true,
						new EventListener() {
							@Override
							public void onEvent(Event evt) {
								switch (((Integer) evt.getData()).intValue()) {
								case MultiLineMessageBox.YES:
									try {
										deleteBean();
										HitDetailT30CostingDHw3ps ();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									break;
								case MultiLineMessageBox.NO:
									break;
								}
							}

							private void deleteBean()
									throws InterruptedException {
								if (selectedItem.getT30Id() > 0) {
									list_Deleted_T30CostingD_HW3PS_List
											.add(selectedItem);
								}
								list_T30CostingD_HW3PS_List.remove(selectedItem);
								setModel_T30CostingD_HW3PS_List();
							}
						}) == MultiLineMessageBox.YES) {
				}

			}
		}
	}
	
	public void onClick$btnExport(Event event) throws InterruptedException{
		try {
			String vTimeStamp = String.valueOf(System.currentTimeMillis());
			PathReport pathReport = new PathReport(vTimeStamp);
			
			File outputFile = new File(pathReport.getFormatDetailCosting1());
			
			
			WritableWorkbook outputFileWorkbook = Workbook.createWorkbook(outputFile);
			
			WritableSheet sheet1= outputFileWorkbook.createSheet("Sheet1", 0);
			
			sheet1.addCell(new jxl.write.Label(0, 0, "Product Desc."));
			sheet1.addCell(new jxl.write.Label(1, 0, "Product No."));
			sheet1.addCell(new jxl.write.Label(2, 0, "Item Category"));
			sheet1.addCell(new jxl.write.Label(3, 0, "Item Product Grp."));
			sheet1.addCell(new jxl.write.Label(4, 0, "Qty"));
			sheet1.addCell(new jxl.write.Label(5, 0, "Unit Price"));
			sheet1.addCell(new jxl.write.Label(6, 0, "Unit COGS"));
			sheet1.addCell(new jxl.write.Label(7, 0, "Catatan"));
			
			
			if(getT29CostingH() != null){
				if(CommonUtils.isNotEmpty(getT29CostingH().getT30CostingDHw3pss())){
					int vRow = 1;
					for (T30CostingDHw3ps t30 : getT29CostingH().getT30CostingDHw3pss()) {
						
						sheet1.addCell(new jxl.write.Label(0, vRow, t30.getItemDesc()));
						sheet1.addCell(new jxl.write.Label(1, vRow, t30.getItemNo()));
						sheet1.addCell(new jxl.write.Label(2, vRow, t30.getItemCategory()));
						sheet1.addCell(new jxl.write.Label(3, vRow, t30.getProduct()));
						sheet1.addCell(new jxl.write.Number(4, vRow, t30.getQty().doubleValue()));
						sheet1.addCell(new jxl.write.Number(5, vRow, t30.getSalesSatuan().doubleValue()));
						sheet1.addCell(new jxl.write.Number(6, vRow, t30.getCogsSatuan().doubleValue()));
						sheet1.addCell(new jxl.write.Label(7, vRow, t30.getCatatan()));
						vRow = vRow +1;
					}
				}	else {
					sheet1.addCell(new jxl.write.Label(0, 1, "Nama Product"));
					sheet1.addCell(new jxl.write.Label(1, 1, "Part No"));
					sheet1.addCell(new jxl.write.Label(2, 1, "HW01/PS01/PS03/PS04/PS05"));
					sheet1.addCell(new jxl.write.Label(3, 1, "H001/../S503"));
					sheet1.addCell(new jxl.write.Number(4, 1, 9999));
					sheet1.addCell(new jxl.write.Number(5, 1, 999999999));
					sheet1.addCell(new jxl.write.Number(6, 1, 999999999));
					sheet1.addCell(new jxl.write.Label(7, 1, "Catatan Jika Ada"));
				}
				
			} else {
				sheet1.addCell(new jxl.write.Label(0, 1, "Nama Product"));
				sheet1.addCell(new jxl.write.Label(1, 1, "Part No"));
				sheet1.addCell(new jxl.write.Label(2, 1, "HW01/PS01/PS03/PS04/PS05"));
				sheet1.addCell(new jxl.write.Label(3, 1, "H001/../S503"));
				sheet1.addCell(new jxl.write.Number(4, 1, 9999));
				sheet1.addCell(new jxl.write.Number(5, 1, 999999999));
				sheet1.addCell(new jxl.write.Number(6, 1, 999999999));
				sheet1.addCell(new jxl.write.Label(7, 1, "Catatan Jika Ada"));
			}
			
			
			outputFileWorkbook.write();
			
			InputStream mediais = new FileInputStream(outputFile);
			AMedia amedia = new AMedia("HardwareAndThirdPartyService.xls", "xls", "application/vnd.ms-excel", mediais);
				
			Filedownload.save(amedia);
			
			outputFileWorkbook.close();
			
			outputFile.delete();
			
		} catch (WriteException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);
		} catch (IOException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);
		}		
	}
	
	public void onClick$btnImport(Event event) throws InterruptedException{
		
		
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				if(getT29CostingH() != null){
					if(CommonUtils.isNotEmpty(getT29CostingH().getT30CostingDHw3pss())){
					
						for (T30CostingDHw3ps t30 : getT29CostingH().getT30CostingDHw3pss()) {
							if (t30.getT30Id() > 0) {
								list_Deleted_T30CostingD_HW3PS_List
										.add(t30);
							}
							list_T30CostingD_HW3PS_List.remove(t30);
							
							//setModel_T30CostingD_HW3PS_List();
						}
					}
				}
				
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();				
				
				for (int i = 1; i < vJmlData; i++){
					
					String vProductDesc = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(0,i).getContents())){
						vProductDesc = sheet.getCell(0,i).getContents();
					}
					
					String vPartNo = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents())){
						vPartNo = sheet.getCell(1,i).getContents();
					}
					
					String vItemCat = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents())){
						vItemCat = sheet.getCell(2,i).getContents();
					}
					
					String vProductGrp = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents())){
						vProductGrp = sheet.getCell(3,i).getContents();
					}
					
					BigDecimal vQty = new BigDecimal(0);
					if (CommonUtils.isNotEmpty(sheet.getCell(4,i).getContents())){
						vQty = new BigDecimal(sheet.getCell(4,i).getContents());
					}
					
					BigDecimal vUnitPrice = new BigDecimal(0);
					if (CommonUtils.isNotEmpty(sheet.getCell(5,i).getContents())){
						vUnitPrice = new BigDecimal(sheet.getCell(5,i).getContents());
					}
					
					BigDecimal vSalesTotal = vQty.multiply(vUnitPrice);
					
					
					BigDecimal vUnitCOGS = new BigDecimal(0);
					if (CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents())){
						vUnitCOGS = new BigDecimal(sheet.getCell(6,i).getContents());
					}
					
					
					BigDecimal vCOGSTotal = vQty.multiply(vUnitCOGS);
					
					String vCatatan = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(7,i).getContents())){
						vCatatan = sheet.getCell(7,i).getContents();
					}
					
					
					T30CostingDHw3ps newDetail = new T30CostingDHw3ps();
					newDetail.setT29CostingH(getT29CostingH());
					
					newDetail.setItemDesc(vProductDesc);
					newDetail.setItemNo(vPartNo);
					newDetail.setItemCategory(vItemCat);
					newDetail.setProduct(vProductGrp);
					newDetail.setQty(vQty);
					newDetail.setSalesSatuan(vUnitPrice);
					newDetail.setSalesTotal(vSalesTotal);
					newDetail.setCogsSatuan(vUnitCOGS);
					newDetail.setCogsTotal(vCOGSTotal);
					newDetail.setCatatan(vCatatan);
					
					
					list_T30CostingD_HW3PS_List.add(newDetail);

				
				}

				workbook.close();

				//lbl1.setValue(media.getName()+ " Sudah berhasil terupload.");

				setModel_T30CostingD_HW3PS_List();
				
				HitDetailT30CostingDHw3ps ();
				
			} catch (BiffException e) {
				Messagebox.show("Not an Excel File : " + media.getName(),
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}

		}

		
	}
	
	
	// Action Button Detail 2 - T31CostingD_ACSPS -------------------------------------------------------------------------------------
	public void onClick$btnNew_B(Event event) {
		T31CostingDAcsps newDetail = new T31CostingDAcsps();
		newDetail.setT29CostingH(getT29CostingH());
		newDetail.setQty(new BigDecimal(0));
		newDetail.setSalesSatuan(new BigDecimal(0));
		newDetail.setSalesTotal(new BigDecimal(0));
		newDetail.setItemCategory("PS04");
		newDetail.setProduct("S404");

		T31CostingDAcsps newValue = T31CostingD_ACSPS_AddWindow.show(
				windowT29CostingDetail, newDetail, getT29CostingH(), "New", selectQueryService);

		if (newValue != null) {			
			list_T31CostingD_ACSPS_List.add(newValue);
			setModel_T31CostingD_ACSPS_List();
			
			HitDetailT31CostingDAcsps();
			 
			onClick$btnNew_B(event);
		}
	}
	
	public void onClick$btnView_B(Event event) {
		if (listBox_T31CostingD_ACSPS.getSelectedItem() != null) {
			T31CostingDAcsps selectedItem = (T31CostingDAcsps) listBox_T31CostingD_ACSPS
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_T31CostingD_ACSPS_List.indexOf(selectedItem);

				T31CostingDAcsps viewValue = T31CostingD_ACSPS_AddWindow.show(
						windowT29CostingDetail, selectedItem, getT29CostingH(), "View", selectQueryService);
				if (viewValue != null) {
					list_T31CostingD_ACSPS_List.set(index, viewValue);
					setModel_T31CostingD_ACSPS_List();
					HitDetailT31CostingDAcsps();
				}
			}
		}
	}
	
	
	public void onClick$btnEdit_B(Event event) {
		if (listBox_T31CostingD_ACSPS.getSelectedItem() != null) {
			T31CostingDAcsps selectedItem = (T31CostingDAcsps) listBox_T31CostingD_ACSPS
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_T31CostingD_ACSPS_List.indexOf(selectedItem);

				T31CostingDAcsps editValue = T31CostingD_ACSPS_AddWindow.show(
						windowT29CostingDetail, selectedItem, getT29CostingH(), "Edit", selectQueryService);
				if (editValue != null) {
					list_T31CostingD_ACSPS_List.set(index, editValue);
					setModel_T31CostingD_ACSPS_List();
					HitDetailT31CostingDAcsps();
				}
			}
		}
	}
	
	public void onClick$btnDelete_B(Event event) throws InterruptedException {
		if (listBox_T31CostingD_ACSPS.getSelectedItem() != null) {
			final T31CostingDAcsps selectedItem = (T31CostingDAcsps) listBox_T31CostingD_ACSPS
					.getSelectedItem().getValue();
			if (selectedItem != null) {

				// Show a confirm box
				String deleteRecord = selectedItem.getItemDesc();
				
				final String msg = Labels
						.getLabel("message.Question.Are_you_sure_to_delete_this_record")
						+ "\n\n --> " + deleteRecord;
				final String title = Labels.getLabel("message.Deleting.Record");

				MultiLineMessageBox.doSetTemplate();
				if (MultiLineMessageBox.show(msg, title, Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION, true,
						new EventListener() {
							@Override
							public void onEvent(Event evt) {
								switch (((Integer) evt.getData()).intValue()) {
								case MultiLineMessageBox.YES:
									try {
										deleteBean();
										HitDetailT31CostingDAcsps();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									break;
								case MultiLineMessageBox.NO:
									break;
								}
							}

							private void deleteBean()
									throws InterruptedException {
								if (selectedItem.getT31Id() > 0) {
									list_Deleted_T31CostingD_ACSPS_List
											.add(selectedItem);
								}
								list_T31CostingD_ACSPS_List.remove(selectedItem);
								setModel_T31CostingD_ACSPS_List();
							}
						}) == MultiLineMessageBox.YES) {
				}

			}
		}
	}
	
	public void onClick$btnExport_B(Event event) throws InterruptedException{
		try {
			String vTimeStamp = String.valueOf(System.currentTimeMillis());
			PathReport pathReport = new PathReport(vTimeStamp);
			
			File outputFile = new File(pathReport.getFormatDetailCosting1());
			
			
			WritableWorkbook outputFileWorkbook = Workbook.createWorkbook(outputFile);
			
			WritableSheet sheet1= outputFileWorkbook.createSheet("Sheet1", 0);
			
			sheet1.addCell(new jxl.write.Label(0, 0, "Product Desc."));
			sheet1.addCell(new jxl.write.Label(1, 0, "Product No."));
			sheet1.addCell(new jxl.write.Label(2, 0, "Qty"));
			sheet1.addCell(new jxl.write.Label(3, 0, "Unit Price"));
			sheet1.addCell(new jxl.write.Label(4, 0, "Catatan"));
			
			
			if(getT29CostingH() != null){
				if(CommonUtils.isNotEmpty(getT29CostingH().getT31CostingDAcspss())){
					int vRow = 1;
					for (T31CostingDAcsps t31 : getT29CostingH().getT31CostingDAcspss()) {
						
						sheet1.addCell(new jxl.write.Label(0, vRow, t31.getItemDesc()));
						sheet1.addCell(new jxl.write.Label(1, vRow, t31.getItemNo()));
						sheet1.addCell(new jxl.write.Number(2, vRow, t31.getQty().doubleValue()));
						sheet1.addCell(new jxl.write.Number(3, vRow, t31.getSalesSatuan().doubleValue()));
						sheet1.addCell(new jxl.write.Label(4, vRow, t31.getCatatan()));
						vRow = vRow +1;
					}
				}	else {
					sheet1.addCell(new jxl.write.Label(0, 1, "Nama Product"));
					sheet1.addCell(new jxl.write.Label(1, 1, "Part No"));
					sheet1.addCell(new jxl.write.Number(2, 1, 9999));
					sheet1.addCell(new jxl.write.Number(3, 1, 999999999));
					sheet1.addCell(new jxl.write.Label(4, 1, "Catatan Jika Ada"));
				}
				
			} else {
				sheet1.addCell(new jxl.write.Label(0, 1, "Nama Product"));
				sheet1.addCell(new jxl.write.Label(1, 1, "Part No"));
				sheet1.addCell(new jxl.write.Number(2, 1, 9999));
				sheet1.addCell(new jxl.write.Number(3, 1, 999999999));
				sheet1.addCell(new jxl.write.Label(4, 1, "Catatan Jika Ada"));
			}
			
			
			outputFileWorkbook.write();
			
			InputStream mediais = new FileInputStream(outputFile);
			AMedia amedia = new AMedia("ACSPs.xls", "xls", "application/vnd.ms-excel", mediais);
				
			Filedownload.save(amedia);
			
			outputFileWorkbook.close();
			
			outputFile.delete();
			
		} catch (WriteException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);
		} catch (IOException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);
		}		
	}
	
	public void onClick$btnImport_B(Event event) throws InterruptedException{
		
		
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				if(getT29CostingH() != null){
					if(CommonUtils.isNotEmpty(getT29CostingH().getT31CostingDAcspss())){
					
						for (T31CostingDAcsps t31 : getT29CostingH().getT31CostingDAcspss()) {
							if (t31.getT31Id() > 0) {
								list_Deleted_T31CostingD_ACSPS_List
										.add(t31);
							}
							list_T31CostingD_ACSPS_List.remove(t31);
							
							//setModel_T31CostingD_ACSPS_List();
						}
					}
				}
				
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();				
				
				for (int i = 1; i < vJmlData; i++){
					
					String vProductDesc = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(0,i).getContents())){
						vProductDesc = sheet.getCell(0,i).getContents();
					}
					
					String vPartNo = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents())){
						vPartNo = sheet.getCell(1,i).getContents();
					}
					
					BigDecimal vQty = new BigDecimal(0);
					if (CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents())){
						vQty = new BigDecimal(sheet.getCell(2,i).getContents());
					}
					
					BigDecimal vUnitPrice = new BigDecimal(0);
					if (CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents())){
						vUnitPrice = new BigDecimal(sheet.getCell(3,i).getContents());
					}
					
					BigDecimal vSalesTotal = vQty.multiply(vUnitPrice);
					
					
					String vCatatan = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(4,i).getContents())){
						vCatatan = sheet.getCell(4,i).getContents();
					}
					
					
					T31CostingDAcsps newDetail = new T31CostingDAcsps();
					newDetail.setT29CostingH(getT29CostingH());
					
					newDetail.setItemDesc(vProductDesc);
					newDetail.setItemNo(vPartNo);
					newDetail.setItemCategory("PS04");
					newDetail.setProduct("S404");
					newDetail.setQty(vQty);
					newDetail.setSalesSatuan(vUnitPrice);
					newDetail.setSalesTotal(vSalesTotal);
					newDetail.setCatatan(vCatatan);
					
					
					list_T31CostingD_ACSPS_List.add(newDetail);

				
				}

				workbook.close();

				//lbl1.setValue(media.getName()+ " Sudah berhasil terupload.");

				setModel_T31CostingD_ACSPS_List();
				
				HitDetailT31CostingDAcsps();
				
			} catch (BiffException e) {
				Messagebox.show("Not an Excel File : " + media.getName(),
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}

		}

		
	}
	
	
	// Action Button Detail 3 - T32CostingD_OWNSW -------------------------------------------------------------------------------------
	public void onClick$btnNew_C(Event event) {
		T32CostingDOwnsw newDetail = new T32CostingDOwnsw();
		newDetail.setT29CostingH(getT29CostingH());
		newDetail.setQty(new BigDecimal(0));
		newDetail.setSalesSatuan(new BigDecimal(0));
		newDetail.setSalesTotal(new BigDecimal(0));
		newDetail.setItemCategory("PS02");


		T32CostingDOwnsw newValue = T32CostingD_OWNSW_AddWindow.show(
				windowT29CostingDetail, newDetail, getT29CostingH(), "New", selectQueryService);

		if (newValue != null) {			
			list_T32CostingD_OWNSW_List.add(newValue);
			setModel_T32CostingD_OWNSW_List();
			
			HitDetailT32CostingDOwnsw();
			 
			onClick$btnNew_C(event);
		}
	}
	
	public void onClick$btnView_C(Event event) {
		if (listBox_T32CostingD_OWNSW.getSelectedItem() != null) {
			T32CostingDOwnsw selectedItem = (T32CostingDOwnsw) listBox_T32CostingD_OWNSW
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_T32CostingD_OWNSW_List.indexOf(selectedItem);

				T32CostingDOwnsw viewValue = T32CostingD_OWNSW_AddWindow.show(
						windowT29CostingDetail, selectedItem, getT29CostingH(), "View", selectQueryService);
				if (viewValue != null) {
					list_T32CostingD_OWNSW_List.set(index, viewValue);
					setModel_T32CostingD_OWNSW_List();
					HitDetailT32CostingDOwnsw();
				}
			}
		}
	}
	
	
	public void onClick$btnEdit_C(Event event) {
		if (listBox_T32CostingD_OWNSW.getSelectedItem() != null) {
			T32CostingDOwnsw selectedItem = (T32CostingDOwnsw) listBox_T32CostingD_OWNSW
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_T32CostingD_OWNSW_List.indexOf(selectedItem);

				T32CostingDOwnsw editValue = T32CostingD_OWNSW_AddWindow.show(
						windowT29CostingDetail, selectedItem, getT29CostingH(), "Edit", selectQueryService);
				if (editValue != null) {
					list_T32CostingD_OWNSW_List.set(index, editValue);
					setModel_T32CostingD_OWNSW_List();
					HitDetailT32CostingDOwnsw();
				}
			}
		}
	}
	
	public void onClick$btnDelete_C(Event event) throws InterruptedException {
		if (listBox_T32CostingD_OWNSW.getSelectedItem() != null) {
			final T32CostingDOwnsw selectedItem = (T32CostingDOwnsw) listBox_T32CostingD_OWNSW
					.getSelectedItem().getValue();
			if (selectedItem != null) {

				// Show a confirm box
				String deleteRecord = selectedItem.getItemDesc();
				
				final String msg = Labels
						.getLabel("message.Question.Are_you_sure_to_delete_this_record")
						+ "\n\n --> " + deleteRecord;
				final String title = Labels.getLabel("message.Deleting.Record");

				MultiLineMessageBox.doSetTemplate();
				if (MultiLineMessageBox.show(msg, title, Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION, true,
						new EventListener() {
							@Override
							public void onEvent(Event evt) {
								switch (((Integer) evt.getData()).intValue()) {
								case MultiLineMessageBox.YES:
									try {
										deleteBean();
										HitDetailT32CostingDOwnsw();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									break;
								case MultiLineMessageBox.NO:
									break;
								}
							}

							private void deleteBean()
									throws InterruptedException {
								if (selectedItem.getT32Id() > 0) {
									list_Deleted_T32CostingD_OWNSW_List
											.add(selectedItem);
								}
								list_T32CostingD_OWNSW_List.remove(selectedItem);
								setModel_T32CostingD_OWNSW_List();
							}
						}) == MultiLineMessageBox.YES) {
				}

			}
		}
	}

	public void onClick$btnExport_C(Event event) throws InterruptedException{
		try {
			String vTimeStamp = String.valueOf(System.currentTimeMillis());
			PathReport pathReport = new PathReport(vTimeStamp);
			
			File outputFile = new File(pathReport.getFormatDetailCosting1());
			
			
			WritableWorkbook outputFileWorkbook = Workbook.createWorkbook(outputFile);
			
			WritableSheet sheet1= outputFileWorkbook.createSheet("Sheet1", 0);
			
			sheet1.addCell(new jxl.write.Label(0, 0, "Product Desc."));
			sheet1.addCell(new jxl.write.Label(1, 0, "Product No."));
			sheet1.addCell(new jxl.write.Label(2, 0, "Item Product Grp."));
			sheet1.addCell(new jxl.write.Label(3, 0, "Qty"));
			sheet1.addCell(new jxl.write.Label(4, 0, "Unit Price"));
			sheet1.addCell(new jxl.write.Label(5, 0, "Catatan"));
			
			
			if(getT29CostingH() != null){
				if(CommonUtils.isNotEmpty(getT29CostingH().getT32CostingDOwnsws())){
					int vRow = 1;
					for (T32CostingDOwnsw t32 : getT29CostingH().getT32CostingDOwnsws()) {
						
						sheet1.addCell(new jxl.write.Label(0, vRow, t32.getItemDesc()));
						sheet1.addCell(new jxl.write.Label(1, vRow, t32.getItemNo()));
						sheet1.addCell(new jxl.write.Label(2, vRow, t32.getProduct()));
						sheet1.addCell(new jxl.write.Number(3, vRow, t32.getQty().doubleValue()));
						sheet1.addCell(new jxl.write.Number(4, vRow, t32.getSalesSatuan().doubleValue()));
						sheet1.addCell(new jxl.write.Label(5, vRow, t32.getCatatan()));
						vRow = vRow +1;
					}
				}	else {
					sheet1.addCell(new jxl.write.Label(0, 1, "Nama Product"));
					sheet1.addCell(new jxl.write.Label(1, 1, "Part No"));
					sheet1.addCell(new jxl.write.Label(2, 1, "S201/S202"));
					sheet1.addCell(new jxl.write.Number(3, 1, 9999));
					sheet1.addCell(new jxl.write.Number(4, 1, 999999999));
					sheet1.addCell(new jxl.write.Label(5, 1, "Catatan Jika Ada"));
				}
				
			} else {
				sheet1.addCell(new jxl.write.Label(0, 1, "Nama Product"));
				sheet1.addCell(new jxl.write.Label(1, 1, "Part No"));
				sheet1.addCell(new jxl.write.Label(2, 1, "S201/S202"));
				sheet1.addCell(new jxl.write.Number(3, 1, 9999));
				sheet1.addCell(new jxl.write.Number(4, 1, 999999999));
				sheet1.addCell(new jxl.write.Label(5, 1, "Catatan Jika Ada"));
			}
			
			
			outputFileWorkbook.write();
			
			InputStream mediais = new FileInputStream(outputFile);
			AMedia amedia = new AMedia("ACSOwnSoftware.xls", "xls", "application/vnd.ms-excel", mediais);
				
			Filedownload.save(amedia);
			
			outputFileWorkbook.close();
			
			outputFile.delete();
			
		} catch (WriteException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);
		} catch (IOException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);
		}		
	}
	
	public void onClick$btnImport_C(Event event) throws InterruptedException{
		
		
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				if(getT29CostingH() != null){
					if(CommonUtils.isNotEmpty(getT29CostingH().getT32CostingDOwnsws())){
					
						for (T32CostingDOwnsw t32 : getT29CostingH().getT32CostingDOwnsws()) {
							if (t32.getT32Id() > 0) {
								list_Deleted_T32CostingD_OWNSW_List
										.add(t32);
							}
							list_T32CostingD_OWNSW_List.remove(t32);
							
							//setModel_T32CostingD_OWNSW_List();
						}
					}
				}
				
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();				
				
				for (int i = 1; i < vJmlData; i++){
					
					String vProductDesc = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(0,i).getContents())){
						vProductDesc = sheet.getCell(0,i).getContents();
					}
					
					String vPartNo = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents())){
						vPartNo = sheet.getCell(1,i).getContents();
					}
					
					String vProductGrp = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents())){
						vProductGrp = sheet.getCell(2,i).getContents();
					}
					
					BigDecimal vQty = new BigDecimal(0);
					if (CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents())){
						vQty = new BigDecimal(sheet.getCell(3,i).getContents());
					}
					
					BigDecimal vUnitPrice = new BigDecimal(0);
					if (CommonUtils.isNotEmpty(sheet.getCell(4,i).getContents())){
						vUnitPrice = new BigDecimal(sheet.getCell(4,i).getContents());
					}
					
					BigDecimal vSalesTotal = vQty.multiply(vUnitPrice);
					
					
					String vCatatan = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(5,i).getContents())){
						vCatatan = sheet.getCell(5,i).getContents();
					}
					
					
					T32CostingDOwnsw newDetail = new T32CostingDOwnsw();
					newDetail.setT29CostingH(getT29CostingH());
					
					newDetail.setItemDesc(vProductDesc);
					newDetail.setItemNo(vPartNo);
					newDetail.setItemCategory("PS02");
					newDetail.setProduct(vProductGrp);
					newDetail.setQty(vQty);
					newDetail.setSalesSatuan(vUnitPrice);
					newDetail.setSalesTotal(vSalesTotal);
					newDetail.setCatatan(vCatatan);
					
					
					list_T32CostingD_OWNSW_List.add(newDetail);

				
				}

				workbook.close();

				//lbl1.setValue(media.getName()+ " Sudah berhasil terupload.");

				setModel_T32CostingD_OWNSW_List();
				
				HitDetailT32CostingDOwnsw();
				
			} catch (BiffException e) {
				Messagebox.show("Not an Excel File : " + media.getName(),
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}

		}

		
	}
	// Action Button Detail 4 - T33CostingD_OTHER -------------------------------------------------------------------------------------
	public void onClick$btnNew_D(Event event) {
		T33CostingDOther newDetail = new T33CostingDOther();
		
		newDetail.setT29CostingH(getT29CostingH());
		
		newDetail.setQty(new BigDecimal(0));
		newDetail.setCogsSatuan(new BigDecimal(0));
		newDetail.setCogsTotal(new BigDecimal(0));


		T33CostingDOther newValue = T33CostingD_OTHER_AddWindow.show(
				windowT29CostingDetail, newDetail, getT29CostingH(), "New", selectQueryService);

		if (newValue != null) {			
			list_T33CostingD_OTHER_List.add(newValue);
			
			setModel_T33CostingD_OTHER_List();
			
			HitDetailT33CostingDOther();
			 
			onClick$btnNew_D(event);
		}
	}
	
	public void onClick$btnView_D(Event event) {
		if (listBox_T33CostingD_OTHER.getSelectedItem() != null) {
			T33CostingDOther selectedItem = (T33CostingDOther) listBox_T33CostingD_OTHER
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_T33CostingD_OTHER_List.indexOf(selectedItem);

				T33CostingDOther viewValue = T33CostingD_OTHER_AddWindow.show(
						windowT29CostingDetail, selectedItem, getT29CostingH(), "View", selectQueryService);
				if (viewValue != null) {
					list_T33CostingD_OTHER_List.set(index, viewValue);
					setModel_T33CostingD_OTHER_List();
					HitDetailT33CostingDOther();
				}
			}
		}
	}
	
	
	public void onClick$btnEdit_D(Event event) {
		if (listBox_T33CostingD_OTHER.getSelectedItem() != null) {
			T33CostingDOther selectedItem = (T33CostingDOther) listBox_T33CostingD_OTHER
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_T33CostingD_OTHER_List.indexOf(selectedItem);

				T33CostingDOther editValue = T33CostingD_OTHER_AddWindow.show(
						windowT29CostingDetail, selectedItem, getT29CostingH(), "Edit", selectQueryService);
				if (editValue != null) {
					list_T33CostingD_OTHER_List.set(index, editValue);
					setModel_T33CostingD_OTHER_List();
					HitDetailT33CostingDOther();
				}
			}
		}
	}
	
	public void onClick$btnDelete_D(Event event) throws InterruptedException {
		if (listBox_T33CostingD_OTHER.getSelectedItem() != null) {
			final T33CostingDOther selectedItem = (T33CostingDOther) listBox_T33CostingD_OTHER
					.getSelectedItem().getValue();
			if (selectedItem != null) {

				// Show a confirm box
				String deleteRecord = selectedItem.getItemDesc();
				
				final String msg = Labels
						.getLabel("message.Question.Are_you_sure_to_delete_this_record")
						+ "\n\n --> " + deleteRecord;
				final String title = Labels.getLabel("message.Deleting.Record");

				MultiLineMessageBox.doSetTemplate();
				if (MultiLineMessageBox.show(msg, title, Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION, true,
						new EventListener() {
							@Override
							public void onEvent(Event evt) {
								switch (((Integer) evt.getData()).intValue()) {
								case MultiLineMessageBox.YES:
									try {
										deleteBean();
										HitDetailT33CostingDOther();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									break;
								case MultiLineMessageBox.NO:
									break;
								}
							}

							private void deleteBean()
									throws InterruptedException {
								if (selectedItem.getT33Id() > 0) {
									list_Deleted_T33CostingD_OTHER_List
											.add(selectedItem);
								}
								list_T33CostingD_OTHER_List.remove(selectedItem);
								setModel_T33CostingD_OTHER_List();
							}
						}) == MultiLineMessageBox.YES) {
				}

			}
		}
	}
	
	public void onClick$btnExport_D(Event event) throws InterruptedException{
		try {
			String vTimeStamp = String.valueOf(System.currentTimeMillis());
			PathReport pathReport = new PathReport(vTimeStamp);
			
			File outputFile = new File(pathReport.getFormatDetailCosting1());
			
			
			WritableWorkbook outputFileWorkbook = Workbook.createWorkbook(outputFile);
			
			WritableSheet sheet1= outputFileWorkbook.createSheet("Sheet1", 0);
			
			sheet1.addCell(new jxl.write.Label(0, 0, "Product Desc."));
			sheet1.addCell(new jxl.write.Label(1, 0, "Product No."));
			sheet1.addCell(new jxl.write.Label(2, 0, "Qty"));
			sheet1.addCell(new jxl.write.Label(3, 0, "Unit COGS"));
			sheet1.addCell(new jxl.write.Label(4, 0, "Catatan"));
			
			
			if(getT29CostingH() != null){
				if(CommonUtils.isNotEmpty(getT29CostingH().getT33CostingDOthers())){
					int vRow = 1;
					for (T33CostingDOther t33 : getT29CostingH().getT33CostingDOthers()) {
						
						sheet1.addCell(new jxl.write.Label(0, vRow, t33.getItemDesc()));
						sheet1.addCell(new jxl.write.Label(1, vRow, t33.getItemNo()));
						sheet1.addCell(new jxl.write.Number(2, vRow, t33.getQty().doubleValue()));
						sheet1.addCell(new jxl.write.Number(3, vRow, t33.getCogsSatuan().doubleValue()));
						sheet1.addCell(new jxl.write.Label(4, vRow, t33.getCatatan()));
						vRow = vRow +1;
					}
				}	else {
					sheet1.addCell(new jxl.write.Label(0, 1, "Nama Product"));
					sheet1.addCell(new jxl.write.Label(1, 1, "Part No"));
					sheet1.addCell(new jxl.write.Number(2, 1, 9999));
					sheet1.addCell(new jxl.write.Number(3, 1, 999999999));
					sheet1.addCell(new jxl.write.Label(4, 1, "Catatan Jika Ada"));
				}
				
			} else {
				sheet1.addCell(new jxl.write.Label(0, 1, "Nama Product"));
				sheet1.addCell(new jxl.write.Label(1, 1, "Part No"));
				sheet1.addCell(new jxl.write.Number(2, 1, 9999));
				sheet1.addCell(new jxl.write.Number(3, 1, 999999999));
				sheet1.addCell(new jxl.write.Label(4, 1, "Catatan Jika Ada"));
			}
			
			
			outputFileWorkbook.write();
			
			InputStream mediais = new FileInputStream(outputFile);
			AMedia amedia = new AMedia("OtherProjectExpenses.xls", "xls", "application/vnd.ms-excel", mediais);
				
			Filedownload.save(amedia);
			
			outputFileWorkbook.close();
			
			outputFile.delete();
			
		} catch (WriteException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);
		} catch (IOException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);
		}		
	}
	
	public void onClick$btnImport_D(Event event) throws InterruptedException{
		
		
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				if(getT29CostingH() != null){
					if(CommonUtils.isNotEmpty(getT29CostingH().getT33CostingDOthers())){
					
						for (T33CostingDOther t33 : getT29CostingH().getT33CostingDOthers()) {
							if (t33.getT33Id() > 0) {
								list_Deleted_T33CostingD_OTHER_List
										.add(t33);
							}
							list_T33CostingD_OTHER_List.remove(t33);
							
							//setModel_T33CostingD_OTHER_List();
						}
					}
				}
				
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();				
				
				for (int i = 1; i < vJmlData; i++){
					
					String vProductDesc = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(0,i).getContents())){
						vProductDesc = sheet.getCell(0,i).getContents();
					}
					
					String vPartNo = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents())){
						vPartNo = sheet.getCell(1,i).getContents();
					}					
					
					BigDecimal vQty = new BigDecimal(0);
					if (CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents())){
						vQty = new BigDecimal(sheet.getCell(2,i).getContents());
					}
					
					BigDecimal vUnitCOGS = new BigDecimal(0);
					if (CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents())){
						vUnitCOGS = new BigDecimal(sheet.getCell(3,i).getContents());
					}
					
					BigDecimal vCOGSTotal = vQty.multiply(vUnitCOGS);
					
					String vCatatan = "";
					if (CommonUtils.isNotEmpty(sheet.getCell(4,i).getContents())){
						vCatatan = sheet.getCell(4,i).getContents();
					}
					
					
					T33CostingDOther newDetail = new T33CostingDOther();
					newDetail.setT29CostingH(getT29CostingH());
					
					newDetail.setItemDesc(vProductDesc);
					newDetail.setItemNo(vPartNo);
					newDetail.setQty(vQty);
					newDetail.setCogsSatuan(vUnitCOGS);
					newDetail.setCogsTotal(vCOGSTotal);
					newDetail.setCatatan(vCatatan);
					
					
					list_T33CostingD_OTHER_List.add(newDetail);

				
				}

				workbook.close();

				//lbl1.setValue(media.getName()+ " Sudah berhasil terupload.");

				setModel_T33CostingD_OTHER_List();
				
				HitDetailT33CostingDOther();
				
			} catch (BiffException e) {
				Messagebox.show("Not an Excel File : " + media.getName(),
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}

		}

		
	}
	// Action Button Detail 5 - T34CostingD_PAYMENT -------------------------------------------------------------------------------------
	public void onClick$btnNew_E(Event event) {
		T34CostingDPayment newDetail = new T34CostingDPayment();
		
		newDetail.setT29CostingH(getT29CostingH());
		
		newDetail.setNilaiInvoice(new BigDecimal(0));
		newDetail.setNilaiLunas(new BigDecimal(0));



		T34CostingDPayment newValue = T34CostingD_PAYMENT_AddWindow.show(
				windowT29CostingDetail, newDetail, getT29CostingH(), "New", selectQueryService);

		if (newValue != null) {			
			list_T34CostingD_PAYMENT_List.add(newValue);
			
			setModel_T34CostingD_PAYMENT_List();
			
			HitDetailT34CostingDPayment();
			
			onClick$btnNew_E(event);
			
			
		}
	}
	
	public void onClick$btnView_E(Event event) {
		if (listBox_T34CostingD_PAYMENT.getSelectedItem() != null) {
			T34CostingDPayment selectedItem = (T34CostingDPayment) listBox_T34CostingD_PAYMENT
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_T34CostingD_PAYMENT_List.indexOf(selectedItem);

				T34CostingDPayment viewValue = T34CostingD_PAYMENT_AddWindow.show(
						windowT29CostingDetail, selectedItem, getT29CostingH(), "View", selectQueryService);
				if (viewValue != null) {
					list_T34CostingD_PAYMENT_List.set(index, viewValue);
					setModel_T34CostingD_PAYMENT_List();
					HitDetailT34CostingDPayment();
				}
			}
		}
	}
	
	
	public void onClick$btnEdit_E(Event event) {
		if (listBox_T34CostingD_PAYMENT.getSelectedItem() != null) {
			T34CostingDPayment selectedItem = (T34CostingDPayment) listBox_T34CostingD_PAYMENT
					.getSelectedItem().getValue();

			if (selectedItem != null) {
				int index = list_T34CostingD_PAYMENT_List.indexOf(selectedItem);

				T34CostingDPayment editValue = T34CostingD_PAYMENT_AddWindow.show(
						windowT29CostingDetail, selectedItem, getT29CostingH(), "Edit", selectQueryService);
				if (editValue != null) {
					list_T34CostingD_PAYMENT_List.set(index, editValue);
					setModel_T34CostingD_PAYMENT_List();
					HitDetailT34CostingDPayment();
				}
			}
		}
	}
	
	public void onClick$btnDelete_E(Event event) throws InterruptedException {
		if (listBox_T34CostingD_PAYMENT.getSelectedItem() != null) {
			final T34CostingDPayment selectedItem = (T34CostingDPayment) listBox_T34CostingD_PAYMENT
					.getSelectedItem().getValue();
			if (selectedItem != null) {

				// Show a confirm box
				String deleteRecord = selectedItem.getNoInvoice();
				
				final String msg = Labels
						.getLabel("message.Question.Are_you_sure_to_delete_this_record")
						+ "\n\n --> " + deleteRecord;
				final String title = Labels.getLabel("message.Deleting.Record");

				MultiLineMessageBox.doSetTemplate();
				if (MultiLineMessageBox.show(msg, title, Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION, true,
						new EventListener() {
							@Override
							public void onEvent(Event evt) {
								switch (((Integer) evt.getData()).intValue()) {
								case MultiLineMessageBox.YES:
									try {
										deleteBean();
										HitDetailT34CostingDPayment();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									break;
								case MultiLineMessageBox.NO:
									break;
								}
							}

							private void deleteBean()
									throws InterruptedException {
								if (selectedItem.getT34Id() > 0) {
									list_Deleted_T34CostingD_PAYMENT_List
											.add(selectedItem);
								}
								list_T34CostingD_PAYMENT_List.remove(selectedItem);
								setModel_T34CostingD_PAYMENT_List();
							}
						}) == MultiLineMessageBox.YES) {
				}

			}
		}
	}	

	public void onClick$btnUploadBSO() throws InterruptedException {
		try {
			Media media = Fileupload.get("Please select File ",
					"Upload File");

			if (CommonUtils.isNotEmpty(media)) {
				getT29CostingH().setUploadBSO(media);
				lblFileBSO.setValue("File berhasil di upload");
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void onClick$btnDownloadBSO() throws IOException, InterruptedException{
		if(getT29CostingH().getUploadBSO() != null){
			Filedownload.save(getT29CostingH().getUploadBSO());
		} else {
		
			if (CommonUtils.isNotEmpty(getT29CostingH().getFileBso()) == true){
				File output = new File(new PathReport().getPathFileCosting() + getT29CostingH().getFileBso());
				
				String vContenType = Files.probeContentType(output.toPath());
				
				Filedownload.save(output, vContenType);
			}  else {
				ZksampleMessageUtils.showErrorMessage("File BSO belum di Upload !");
			}
		}
	}
	
	public void onClick$btnUploadInfoPrice() throws InterruptedException {
		try {
			Media media = Fileupload.get("Please select File ",
					"Upload File");

			if (CommonUtils.isNotEmpty(media)) {
				getT29CostingH().setUploadInfoPrice(media);
				lblFileInfoPrice.setValue("File berhasil di upload");
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void onClick$btnDownloadInfoPrice() throws IOException, InterruptedException{
		
		
		if (CommonUtils.isNotEmpty(getT29CostingH().getFileInfoPrice()) == true){
			File output = new File(new PathReport().getPathFileCosting() + getT29CostingH().getFileInfoPrice());
			
			String vContenType = Files.probeContentType(output.toPath());
			
			Filedownload.save(output, vContenType);
		}  else {
			ZksampleMessageUtils.showErrorMessage("File Info Price belum di Upload !");
		}
	}
	
	public void onClick$btnUploadFilePOCustomer() throws InterruptedException {
		try {
			Media media = Fileupload.get("Please select File ",
					"Upload File");

			if (CommonUtils.isNotEmpty(media)) {
				getT29CostingH().setUploadPoCustomer(media);
				lblFilePoCustomer.setValue("File berhasil di upload");
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void onClick$btnDownloadFilePOCustomer() throws IOException, InterruptedException{
		
		
		if (CommonUtils.isNotEmpty(getT29CostingH().getFilePoCustomer()) == true){
			File output = new File(new PathReport().getPathFileCosting() + getT29CostingH().getFilePoCustomer());
			
			String vContenType = Files.probeContentType(output.toPath());
			
			Filedownload.save(output, vContenType);
		}  else {
			ZksampleMessageUtils.showErrorMessage("File PO Customer belum di Upload !");
		}
	}

/*	
	public void onClick$btnRecalculate() throws InterruptedException{
		
		BigDecimal vCalculateTotalMarginPcn = new BigDecimal(0);
		BigDecimal vCalculateHwMarginPcn = new BigDecimal(0);
		BigDecimal vCalculate3rdPSMarginPcn = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbCalculateMarginPcn.getValue())){
			vCalculateTotalMarginPcn = dcbCalculateMarginPcn.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbCalculateHwMarginPcn.getValue())){
			vCalculateHwMarginPcn = dcbCalculateHwMarginPcn.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbCalculate3rdPSMarginPcn.getValue())){
			vCalculate3rdPSMarginPcn = dcbCalculate3rdPSMarginPcn.getValue();
		}

		BigDecimal vCalTotalSales = new BigDecimal(0);
		
		BigDecimal vTotalSales = new BigDecimal(0);
		BigDecimal vTotalCOGS = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbTotalSales.getValue())){
			vTotalSales = dcbTotalSales.getValue();
		}
		
		if (CommonUtils.isNotEmpty(dcbTotalCOGS_2.getValue())){
			vTotalCOGS = dcbTotalCOGS_2.getValue();
		}
		
		
		BigDecimal vSatu = new BigDecimal(1);
		
		BigDecimal vCalculateTotalMargin = vCalculateTotalMarginPcn.divide((new BigDecimal(100)), 5, RoundingMode.HALF_UP);
		//System.out.println(" vCalculateTotalMargin : "+vCalculateTotalMargin);
		//System.out.println(" vCalculateTotalMargin.compareTo(new BigDecimal(1)) : "+vCalculateTotalMargin.compareTo(new BigDecimal(1)));
		
		if (vTotalCOGS.compareTo(new BigDecimal(0)) == 0) {
			ZksampleMessageUtils.showErrorMessage("Tidak ada Cost sama sekali, tidak perlu Calculate Margin");
    		return;
			
		} else {
			if (vCalculateTotalMargin.compareTo(new BigDecimal(1))>= 0) {
				vCalTotalSales = vTotalCOGS;
				ZksampleMessageUtils.showErrorMessage("Margin tidak bisa "+vCalculateTotalMarginPcn+" % karena ada Cost");
	    		return;
			} else {
				vCalTotalSales = vTotalCOGS.divide((vSatu.subtract(vCalculateTotalMargin)), 5, RoundingMode.HALF_UP);
			}
		}
		
		System.out.println("vCalTotalSales --> "+vCalTotalSales);
		
		
		
		BigDecimal vTotalSalesHw3ps = new BigDecimal(0);
		BigDecimal vTotalCOGSHw3ps = new BigDecimal(0);
		
//		int vJmlDtlHw3ps = list_T30CostingD_HW3PS_List.size();
//		
//		if (vJmlDtlHw3ps > 0){
//			BigDecimal[] vCOGSLine = new BigDecimal[vJmlDtlHw3ps];
//			BigDecimal[] vSalesLine = new BigDecimal[vJmlDtlHw3ps];
//			// deklarasi array untuk menampung COGS dari Detail
//			// Hitung harga Sales sesuai dengan margin yang diinput, simpan dalam array jg
//			//  buat 1 variable untuk menampung sisa sales yang terpakai
//			// jika varieable tersebut minus maka keluarkan error tidak bisa dihitung
//			
//			
//		}
		
		BigDecimal vCalculateHwMargin = vCalculateHwMarginPcn.divide((new BigDecimal(100)), 5, RoundingMode.HALF_UP);
		BigDecimal vCalculate3rdPSMargin = vCalculate3rdPSMarginPcn.divide((new BigDecimal(100)), 5, RoundingMode.HALF_UP);
		
		BigDecimal vCalTotalSalesRemaning = vCalTotalSales;
		
		list_T30CostingD_HW3PS_List_Temp.clear();
		
		
		for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List) {
			if (CommonUtils.isNotEmpty(aData)){
				
				BigDecimal vCOGSLine = aData.getCogsTotal();
				BigDecimal vCalSalesLine = new BigDecimal(0);
				
				
				if (vCOGSLine.compareTo(new BigDecimal(0)) == 0) {
					vCalSalesLine =  new BigDecimal(0);						
				} else {
					if (aData.getItemCategory().equals("HW01") == true) {
						
						if (vCalculateHwMargin.compareTo(new BigDecimal(1))>= 0) {
							vCalSalesLine = vTotalCOGS;
							ZksampleMessageUtils.showErrorMessage("Hardware Margin tidak bisa "+vCalculateHwMarginPcn+" % karena ada Cost");
				    		return;
						} else {
							vCalSalesLine = vCOGSLine.divide((vSatu.subtract(vCalculateHwMargin)), 5, RoundingMode.HALF_UP);
						}
					} else {
						if (vCalculate3rdPSMargin.compareTo(new BigDecimal(1))>= 0) {
							vCalSalesLine = vTotalCOGS;
							ZksampleMessageUtils.showErrorMessage("3rd Party Services Margin tidak bisa "+vCalculate3rdPSMarginPcn+" % karena ada Cost");
				    		return;
						} else {
							vCalSalesLine = vCOGSLine.divide((vSatu.subtract(vCalculate3rdPSMargin)), 5, RoundingMode.HALF_UP);
						}
					}
				}
				
				vCalTotalSalesRemaning = vCalTotalSalesRemaning.subtract(vCalSalesLine);
				
				aData.setSalesTotal(vCalSalesLine);
				BigDecimal vQty = new BigDecimal(0);		
				if (CommonUtils.isNotEmpty(aData.getQty())){
					vQty = aData.getQty();
				}
				
				if (vQty.compareTo(new BigDecimal(0)) != 0) {
					vCalSalesLine = vCalSalesLine.divide(vQty, 5, RoundingMode.HALF_UP);
				}
				
				if (vQty.compareTo(new BigDecimal(0)) == 0) {
					aData.setSalesSatuan(vCalSalesLine);
				} else {					
					aData.setSalesSatuan(vCalSalesLine);
				}
				
				if (vCalTotalSalesRemaning.compareTo(new BigDecimal(0)) > 0) {
					list_T30CostingD_HW3PS_List_Temp.add(aData);
				}  else {
					ZksampleMessageUtils.showErrorMessage("Recalculate Margin tidak bisa dengan Margin Total "+vCalculateTotalMarginPcn+" ,"
									+ "	Margin Hardware "+vCalculateHwMarginPcn+" ,"
									+ " dan 3rd Party Services Margin "+vCalculate3rdPSMarginPcn+" tidak memungkinkan untuk dilakukan");
		    		return;
				}
			}
		}
		
		
		System.out.println("vCalTotalSalesRemaning --> "+vCalTotalSalesRemaning);
		
		
		
		int vJmlACSCare = list_T31CostingD_ACSPS_List.size();
		int vJmlOwnSoftware = list_T32CostingD_OWNSW_List.size();
		
		list_T31CostingD_ACSPS_List_Temp.clear();
		list_T32CostingD_OWNSW_List_Temp.clear();
		
		if (vJmlACSCare > 0 && vJmlOwnSoftware > 0){
			System.out.println("Masuk Sini --> detail 2 ");
			BigDecimal vTotalSalesFullMargin = new BigDecimal (0);
			
			
			if (CommonUtils.isNotEmpty(dcbsalesACSPS.getValue())){
				vTotalSalesFullMargin = dcbsalesACSPS.getValue();
			}
			
			if (CommonUtils.isNotEmpty(dcbsalesOWNSW.getValue())){
				vTotalSalesFullMargin = vTotalSalesFullMargin.add(dcbsalesOWNSW.getValue());
			}
			
			
			for (T31CostingDAcsps aData : list_T31CostingD_ACSPS_List) {
				if (CommonUtils.isNotEmpty(aData)){
					BigDecimal vCalSalesLine = new BigDecimal(0);	
					
					BigDecimal vSalesLine = new BigDecimal(0);		
					if (CommonUtils.isNotEmpty(aData.getQty())){
						vSalesLine = aData.getSalesTotal();
					}
					vCalSalesLine = (vSalesLine.divide(vTotalSalesFullMargin, 5, RoundingMode.HALF_UP)).multiply(vCalTotalSalesRemaning);
					
					aData.setSalesTotal(vCalSalesLine);
					
					BigDecimal vQty = new BigDecimal(0);		
					if (CommonUtils.isNotEmpty(aData.getQty())){
						vQty = aData.getQty();
					}
					
					if (vQty.compareTo(new BigDecimal(0)) != 0) {
						vCalSalesLine = vCalSalesLine.divide(vQty, 5, RoundingMode.HALF_UP);
					}					
					if (vQty.compareTo(new BigDecimal(0)) == 0) {
						aData.setSalesSatuan(vCalSalesLine);
					} else {					
						aData.setSalesSatuan(vCalSalesLine);
					}
					
					list_T31CostingD_ACSPS_List_Temp.add(aData);
				}
			}
			
			for (T32CostingDOwnsw aData : list_T32CostingD_OWNSW_List) {
				if (CommonUtils.isNotEmpty(aData)){
					BigDecimal vCalSalesLine = new BigDecimal(0);	
					
					BigDecimal vSalesLine = new BigDecimal(0);		
					if (CommonUtils.isNotEmpty(aData.getQty())){
						vSalesLine = aData.getSalesTotal();
					}
					vCalSalesLine = (vSalesLine.divide(vTotalSalesFullMargin, 5, RoundingMode.HALF_UP)).multiply(vCalTotalSalesRemaning);
					
					aData.setSalesTotal(vCalSalesLine);
					
					BigDecimal vQty = new BigDecimal(0);		
					if (CommonUtils.isNotEmpty(aData.getQty())){
						vQty = aData.getQty();
					}
					
					if (vQty.compareTo(new BigDecimal(0)) != 0) {
						vCalSalesLine = vCalSalesLine.divide(vQty, 5, RoundingMode.HALF_UP);
					}					
					if (vQty.compareTo(new BigDecimal(0)) == 0) {
						aData.setSalesSatuan(vCalSalesLine);
					} else {					
						aData.setSalesSatuan(vCalSalesLine);
					}
					
					list_T32CostingD_OWNSW_List_Temp.add(aData);
				}
			}
			
			vCalTotalSalesRemaning = new BigDecimal(0);
		} else {
			// Jika hanya ada ACS Care yang ada detail
			if (vJmlACSCare > 0 ){
				
				System.out.println("Masuk Sini --> detail 1.A");
				
				BigDecimal vTotalSalesFullMargin = new BigDecimal (0);				
				
				if (CommonUtils.isNotEmpty(dcbsalesACSPS.getValue())){
					vTotalSalesFullMargin = dcbsalesACSPS.getValue();
				}				
				
				for (T31CostingDAcsps aData : list_T31CostingD_ACSPS_List) {
					if (CommonUtils.isNotEmpty(aData)){
						BigDecimal vCalSalesLine = new BigDecimal(0);	
						
						BigDecimal vSalesLine = new BigDecimal(0);		
						if (CommonUtils.isNotEmpty(aData.getQty())){
							vSalesLine = aData.getSalesTotal();
						}
						vCalSalesLine = (vSalesLine.divide(vTotalSalesFullMargin, 5, RoundingMode.HALF_UP)).multiply(vCalTotalSalesRemaning);
						
						aData.setSalesTotal(vCalSalesLine);
						
						BigDecimal vQty = new BigDecimal(0);		
						if (CommonUtils.isNotEmpty(aData.getQty())){
							vQty = aData.getQty();
						}
						
						if (vQty.compareTo(new BigDecimal(0)) != 0) {
							vCalSalesLine = vCalSalesLine.divide(vQty, 5, RoundingMode.HALF_UP);
						}					
						if (vQty.compareTo(new BigDecimal(0)) == 0) {
							aData.setSalesSatuan(vCalSalesLine);
						} else {					
							aData.setSalesSatuan(vCalSalesLine);
						}
						
						list_T31CostingD_ACSPS_List_Temp.add(aData);
					}
				}
				
				vCalTotalSalesRemaning = new BigDecimal(0);
			}
			
			// Jika hanya Own Software yang ada detail
			if (vJmlOwnSoftware > 0 ){
				System.out.println("Masuk Sini --> detail 1.B");
				
				BigDecimal vTotalSalesFullMargin = new BigDecimal (0);				
				
				if (CommonUtils.isNotEmpty(dcbsalesOWNSW.getValue())){
					vTotalSalesFullMargin = dcbsalesOWNSW.getValue();
				}				
				
				for (T32CostingDOwnsw aData : list_T32CostingD_OWNSW_List) {
					if (CommonUtils.isNotEmpty(aData)){
						BigDecimal vCalSalesLine = new BigDecimal(0);	
						
						BigDecimal vSalesLine = new BigDecimal(0);		
						if (CommonUtils.isNotEmpty(aData.getQty())){
							vSalesLine = aData.getSalesTotal();
						}
						vCalSalesLine = (vSalesLine.divide(vTotalSalesFullMargin, 5, RoundingMode.HALF_UP)).multiply(vCalTotalSalesRemaning);
						
						aData.setSalesTotal(vCalSalesLine);
						
						BigDecimal vQty = new BigDecimal(0);		
						if (CommonUtils.isNotEmpty(aData.getQty())){
							vQty = aData.getQty();
						}
						
						if (vQty.compareTo(new BigDecimal(0)) != 0) {
							vCalSalesLine = vCalSalesLine.divide(vQty, 5, RoundingMode.HALF_UP);
						}					
						if (vQty.compareTo(new BigDecimal(0)) == 0) {
							aData.setSalesSatuan(vCalSalesLine);
						} else {					
							aData.setSalesSatuan(vCalSalesLine);
						}
						
						list_T32CostingD_OWNSW_List_Temp.add(aData);
					}
				}
				
				vCalTotalSalesRemaning = new BigDecimal(0);
			}
			
			
		}
			
			
		if (vCalTotalSalesRemaning.compareTo(new BigDecimal(0)) != 0) {
			ZksampleMessageUtils.showErrorMessage("Recalculate Margin tidak bisa dengan Margin Total "+vCalculateTotalMarginPcn+" ,"
					+ "	Margin Hardware "+vCalculateHwMarginPcn+" ,"
					+ " dan 3rd Party Services Margin "+vCalculate3rdPSMarginPcn+" tidak memungkinkan untuk dilakukan");
			return;
		} else {
			if (CommonUtils.isNotEmpty(list_T30CostingD_HW3PS_List_Temp)){
				list_T30CostingD_HW3PS_List.clear();
				for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List_Temp) {
					list_T30CostingD_HW3PS_List.add(aData);
				}
				setModel_T30CostingD_HW3PS_List();
				HitDetailT30CostingDHw3ps();
			}
			
			if (CommonUtils.isNotEmpty(list_T31CostingD_ACSPS_List_Temp)){
				list_T31CostingD_ACSPS_List.clear();
				for (T31CostingDAcsps aData : list_T31CostingD_ACSPS_List_Temp) {
					list_T31CostingD_ACSPS_List.add(aData);
				}
				setModel_T31CostingD_ACSPS_List();
				HitDetailT31CostingDAcsps();
			}
			
			if (CommonUtils.isNotEmpty(list_T32CostingD_OWNSW_List_Temp)){
				list_T32CostingD_OWNSW_List.clear();
				for (T32CostingDOwnsw aData : list_T32CostingD_OWNSW_List_Temp) {
					list_T32CostingD_OWNSW_List.add(aData);
				}
				setModel_T32CostingD_OWNSW_List();
				HitDetailT32CostingDOwnsw();
			}
			
			
			
		}
		
		
		
	}
*/
/*	
	public void onClick$btnRecalculate() throws InterruptedException{
		
		BigDecimal vCalculateTotalMarginPcn = new BigDecimal(0);
		BigDecimal vCalculateHw3psMarginPcn = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbCalculateTotalMarginPcn.getValue())){
			vCalculateTotalMarginPcn = dcbCalculateTotalMarginPcn.getValue();
		}
		BigDecimal vCalculateTotalMargin = vCalculateTotalMarginPcn.divide((new BigDecimal(100)), 5, RoundingMode.HALF_UP);

		
		if (CommonUtils.isNotEmpty(dcbCalculateHw3PSMarginPcn.getValue())){
			vCalculateHw3psMarginPcn = dcbCalculateHw3PSMarginPcn.getValue();
		}
		BigDecimal vCalculateHw3psMargin = vCalculateHw3psMarginPcn.divide((new BigDecimal(100)), 5, RoundingMode.HALF_UP);

		
		BigDecimal vSatu = new BigDecimal(1);
		
		BigDecimal vCalTotalSales = new BigDecimal(0);
		BigDecimal vCalSalesHW3PS = new BigDecimal(0);
		
		BigDecimal vTotalCOGS = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbTotalCOGS_2.getValue())){
			vTotalCOGS = dcbTotalCOGS_2.getValue();
		}
		
		if (vTotalCOGS.compareTo(new BigDecimal(0)) == 0) {
			ZksampleMessageUtils.showErrorMessage("Tidak ada Cost sama sekali, tidak perlu Calculate Margin");
    		return;
			
		} else {
			if (vCalculateTotalMargin.compareTo(new BigDecimal(1))>= 0) {
				vCalTotalSales = vTotalCOGS;
				ZksampleMessageUtils.showErrorMessage("Total Margin tidak bisa "+vCalculateTotalMarginPcn+" % karena ada Cost");
	    		return;
			} else {
				vCalTotalSales = vTotalCOGS.divide((vSatu.subtract(vCalculateTotalMargin)), 5, RoundingMode.HALF_UP);
			}
		}
		
		
		BigDecimal vCOGSHW3PS = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbCOGSHW3PS.getValue())){
			vCOGSHW3PS = dcbCOGSHW3PS.getValue();
		}		
		
		BigDecimal vCalHw3psWithoutOtherExpMargin = new BigDecimal(0);
		if (vCOGSHW3PS.compareTo(new BigDecimal(0)) == 0) {
			ZksampleMessageUtils.showErrorMessage("Tidak ada Cost pada Hardware and 3rd Party Service, tidak bisa dilakukan Calculate Margin");
    		return;
			
		} else {			
			if (vCalculateHw3psMargin.compareTo(new BigDecimal(1))>= 0) {
				vCalSalesHW3PS = vTotalCOGS;
				ZksampleMessageUtils.showErrorMessage("Hardware and 3rd Party Service Margin tidak bisa "+vCalculateHw3psMarginPcn+" % karena ada Cost");
	    		return;
			} else {
				vCalSalesHW3PS = vTotalCOGS.divide((vSatu.subtract(vCalculateHw3psMargin)), 5, RoundingMode.HALF_UP);
				vCalHw3psWithoutOtherExpMargin = vCalSalesHW3PS.subtract(vCOGSHW3PS);				
				vCalHw3psWithoutOtherExpMargin = vCalHw3psWithoutOtherExpMargin.divide(vCalSalesHW3PS, 5, RoundingMode.HALF_UP);				
			}
		}
		
		//System.out.println("vCalTotalSales --> "+vCalTotalSales);
		//System.out.println("vCalSalesHW3PS --> "+vCalSalesHW3PS);
		//System.out.println("vCalHw3psMarginWithoutOtherExp --> "+vCalHw3psWithoutOtherExpMargin);
		
		BigDecimal vCalTotalSalesRemaning = vCalTotalSales;
		
		//System.out.println("vCalTotalSalesRemaning 1--> "+vCalTotalSalesRemaning);
		
		list_T30CostingD_HW3PS_List_Temp.clear();
		
		for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List) {
			if (CommonUtils.isNotEmpty(aData)){
				
				BigDecimal vCOGSLine = aData.getCogsTotal();
				BigDecimal vCalSalesLine = new BigDecimal(0);
				
				
				if (vCOGSLine.compareTo(new BigDecimal(0)) == 0) {
					vCalSalesLine =  new BigDecimal(0);						
				} else {						
					vCalSalesLine = vCOGSLine.divide((vSatu.subtract(vCalHw3psWithoutOtherExpMargin)), 5, RoundingMode.HALF_UP);
		
				}
				
				vCalTotalSalesRemaning = vCalTotalSalesRemaning.subtract(vCalSalesLine);
				
				aData.setSalesTotal(vCalSalesLine);
				BigDecimal vQty = new BigDecimal(0);		
				if (CommonUtils.isNotEmpty(aData.getQty())){
					vQty = aData.getQty();
				}
				
				if (vQty.compareTo(new BigDecimal(0)) != 0) {
					vCalSalesLine = vCalSalesLine.divide(vQty, 5, RoundingMode.HALF_UP);
				}
				
				if (vQty.compareTo(new BigDecimal(0)) == 0) {
					aData.setSalesSatuan(vCalSalesLine);
				} else {					
					aData.setSalesSatuan(vCalSalesLine);
				}
				
				if (vCalTotalSalesRemaning.compareTo(new BigDecimal(0)) >= 0) {
					list_T30CostingD_HW3PS_List_Temp.add(aData);
				}  else {
					ZksampleMessageUtils.showErrorMessage("Recalculate Margin tidak bisa dengan Margin Total "+vCalculateTotalMarginPcn+" ,"
									+ "	Margin Hardware dan 3rd Party Services "+vCalculateHw3psMarginPcn+" tidak memungkinkan untuk dilakukan");
		    		return;
				}
			}
		}
		
		//System.out.println("vCalTotalSalesRemaning 2--> "+vCalTotalSalesRemaning);
		
		int vJmlACSCare = list_T31CostingD_ACSPS_List.size();
		int vJmlOwnSoftware = list_T32CostingD_OWNSW_List.size();
		
		if (vJmlACSCare > 0 || vJmlOwnSoftware > 0){	
			if (vCalTotalSalesRemaning.compareTo(new BigDecimal(0)) == 0) {
				ZksampleMessageUtils.showErrorMessage("Recalculate Margin tidak bisa dengan Margin Total "+vCalculateTotalMarginPcn+" ,"
								+ "	Margin Hardware dan 3rd Party Services "+vCalculateHw3psMarginPcn+" tidak memungkinkan untuk dilakukan");
	    		return;
			} else {
				list_T31CostingD_ACSPS_List_Temp.clear();
				list_T32CostingD_OWNSW_List_Temp.clear();
				
				if (vJmlACSCare > 0 && vJmlOwnSoftware > 0){
					
					
					BigDecimal vTotalSalesFullMargin = new BigDecimal (0);					
					
					if (CommonUtils.isNotEmpty(dcbsalesACSPS.getValue())){
						vTotalSalesFullMargin = dcbsalesACSPS.getValue();
					}
					
					if (CommonUtils.isNotEmpty(dcbsalesOWNSW.getValue())){
						vTotalSalesFullMargin = vTotalSalesFullMargin.add(dcbsalesOWNSW.getValue());
					}
					
					
					for (T31CostingDAcsps aData : list_T31CostingD_ACSPS_List) {
						if (CommonUtils.isNotEmpty(aData)){
							BigDecimal vCalSalesLine = new BigDecimal(0);	
							
							BigDecimal vSalesLine = new BigDecimal(0);		
							if (CommonUtils.isNotEmpty(aData.getQty())){
								vSalesLine = aData.getSalesTotal();
							}
							vCalSalesLine = (vSalesLine.divide(vTotalSalesFullMargin, 5, RoundingMode.HALF_UP)).multiply(vCalTotalSalesRemaning);
							
							aData.setSalesTotal(vCalSalesLine);
							
							BigDecimal vQty = new BigDecimal(0);		
							if (CommonUtils.isNotEmpty(aData.getQty())){
								vQty = aData.getQty();
							}
							
							if (vQty.compareTo(new BigDecimal(0)) != 0) {
								vCalSalesLine = vCalSalesLine.divide(vQty, 5, RoundingMode.HALF_UP);
							}					
							if (vQty.compareTo(new BigDecimal(0)) == 0) {
								aData.setSalesSatuan(vCalSalesLine);
							} else {					
								aData.setSalesSatuan(vCalSalesLine);
							}
							
							list_T31CostingD_ACSPS_List_Temp.add(aData);
						}
					}
					
					for (T32CostingDOwnsw aData : list_T32CostingD_OWNSW_List) {
						if (CommonUtils.isNotEmpty(aData)){
							BigDecimal vCalSalesLine = new BigDecimal(0);	
							
							BigDecimal vSalesLine = new BigDecimal(0);		
							if (CommonUtils.isNotEmpty(aData.getQty())){
								vSalesLine = aData.getSalesTotal();
							}
							vCalSalesLine = (vSalesLine.divide(vTotalSalesFullMargin, 5, RoundingMode.HALF_UP)).multiply(vCalTotalSalesRemaning);
							
							aData.setSalesTotal(vCalSalesLine);
							
							BigDecimal vQty = new BigDecimal(0);		
							if (CommonUtils.isNotEmpty(aData.getQty())){
								vQty = aData.getQty();
							}
							
							if (vQty.compareTo(new BigDecimal(0)) != 0) {
								vCalSalesLine = vCalSalesLine.divide(vQty, 5, RoundingMode.HALF_UP);
							}					
							if (vQty.compareTo(new BigDecimal(0)) == 0) {
								aData.setSalesSatuan(vCalSalesLine);
							} else {					
								aData.setSalesSatuan(vCalSalesLine);
							}
							
							list_T32CostingD_OWNSW_List_Temp.add(aData);
						}
					}
					
					vCalTotalSalesRemaning = new BigDecimal(0);
				} else {
					// Jika hanya ada ACS Care yang ada detail
					if (vJmlACSCare > 0 ){
						BigDecimal vTotalSalesFullMargin = new BigDecimal (0);				
						
						if (CommonUtils.isNotEmpty(dcbsalesACSPS.getValue())){
							vTotalSalesFullMargin = dcbsalesACSPS.getValue();
						}				
						
						for (T31CostingDAcsps aData : list_T31CostingD_ACSPS_List) {
							if (CommonUtils.isNotEmpty(aData)){
								BigDecimal vCalSalesLine = new BigDecimal(0);	
								
								BigDecimal vSalesLine = new BigDecimal(0);		
								if (CommonUtils.isNotEmpty(aData.getQty())){
									vSalesLine = aData.getSalesTotal();
								}
								vCalSalesLine = (vSalesLine.divide(vTotalSalesFullMargin, 5, RoundingMode.HALF_UP)).multiply(vCalTotalSalesRemaning);
								
								aData.setSalesTotal(vCalSalesLine);
								
								BigDecimal vQty = new BigDecimal(0);		
								if (CommonUtils.isNotEmpty(aData.getQty())){
									vQty = aData.getQty();
								}
								
								if (vQty.compareTo(new BigDecimal(0)) != 0) {
									vCalSalesLine = vCalSalesLine.divide(vQty, 5, RoundingMode.HALF_UP);
								}					
								if (vQty.compareTo(new BigDecimal(0)) == 0) {
									aData.setSalesSatuan(vCalSalesLine);
								} else {					
									aData.setSalesSatuan(vCalSalesLine);
								}
								
								list_T31CostingD_ACSPS_List_Temp.add(aData);
							}
						}
						
						vCalTotalSalesRemaning = new BigDecimal(0);
					}
					
					// Jika hanya Own Software yang ada detail
					if (vJmlOwnSoftware > 0 ){						
						BigDecimal vTotalSalesFullMargin = new BigDecimal (0);				
						
						if (CommonUtils.isNotEmpty(dcbsalesOWNSW.getValue())){
							vTotalSalesFullMargin = dcbsalesOWNSW.getValue();
						}				
						
						for (T32CostingDOwnsw aData : list_T32CostingD_OWNSW_List) {
							if (CommonUtils.isNotEmpty(aData)){
								BigDecimal vCalSalesLine = new BigDecimal(0);	
								
								BigDecimal vSalesLine = new BigDecimal(0);		
								if (CommonUtils.isNotEmpty(aData.getQty())){
									vSalesLine = aData.getSalesTotal();
								}
								vCalSalesLine = (vSalesLine.divide(vTotalSalesFullMargin, 5, RoundingMode.HALF_UP)).multiply(vCalTotalSalesRemaning);
								
								aData.setSalesTotal(vCalSalesLine);
								
								BigDecimal vQty = new BigDecimal(0);		
								if (CommonUtils.isNotEmpty(aData.getQty())){
									vQty = aData.getQty();
								}
								
								if (vQty.compareTo(new BigDecimal(0)) != 0) {
									vCalSalesLine = vCalSalesLine.divide(vQty, 5, RoundingMode.HALF_UP);
								}					
								if (vQty.compareTo(new BigDecimal(0)) == 0) {
									aData.setSalesSatuan(vCalSalesLine);
								} else {					
									aData.setSalesSatuan(vCalSalesLine);
								}
								
								list_T32CostingD_OWNSW_List_Temp.add(aData);
							}
						}
						
						vCalTotalSalesRemaning = new BigDecimal(0);
					}
				}
			}
		}
		
		if (vCalTotalSalesRemaning.compareTo(new BigDecimal(0)) != 0) {
			ZksampleMessageUtils.showErrorMessage("Recalculate Margin tidak bisa dengan Margin Total "+vCalculateTotalMarginPcn+" ,"
					+ "	Margin Hardware dan 3rd Party Services "+vCalculateHw3psMarginPcn+" tidak memungkinkan untuk dilakukan");
			return;
		} else {
			if (CommonUtils.isNotEmpty(list_T30CostingD_HW3PS_List_Temp)){
				list_T30CostingD_HW3PS_List.clear();
				for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List_Temp) {
					list_T30CostingD_HW3PS_List.add(aData);
				}
				setModel_T30CostingD_HW3PS_List();
				HitDetailT30CostingDHw3ps();
			}
			
			if (CommonUtils.isNotEmpty(list_T31CostingD_ACSPS_List_Temp)){
				list_T31CostingD_ACSPS_List.clear();
				for (T31CostingDAcsps aData : list_T31CostingD_ACSPS_List_Temp) {
					list_T31CostingD_ACSPS_List.add(aData);
				}
				setModel_T31CostingD_ACSPS_List();
				HitDetailT31CostingDAcsps();
			}
			
			if (CommonUtils.isNotEmpty(list_T32CostingD_OWNSW_List_Temp)){
				list_T32CostingD_OWNSW_List.clear();
				for (T32CostingDOwnsw aData : list_T32CostingD_OWNSW_List_Temp) {
					list_T32CostingD_OWNSW_List.add(aData);
				}
				setModel_T32CostingD_OWNSW_List();
				HitDetailT32CostingDOwnsw();
			}
		}
		
	}
*/
	
	public void onClick$btnRecalculate() throws InterruptedException {
		BigDecimal vCalculateTotalMarginPcn = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcbCalculateTotalMarginPcn.getValue())) {
			vCalculateTotalMarginPcn = dcbCalculateTotalMarginPcn.getValue();
		}
		BigDecimal vCalculateTotalMargin = vCalculateTotalMarginPcn.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
		
		
		BigDecimal vCalculateHw3psMarginPcn = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcbCalculateHw3PSMarginPcn.getValue())) {
			vCalculateHw3psMarginPcn = dcbCalculateHw3PSMarginPcn.getValue();
		}
		BigDecimal vCalculateHw3psMargin = vCalculateHw3psMarginPcn.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
		
		BigDecimal vSatu = new BigDecimal(1);
		
		BigDecimal vTotalCOGS = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcbTotalCOGS_2.getValue())) {
			vTotalCOGS = dcbTotalCOGS_2.getValue();
		}

		if (vTotalCOGS.compareTo(new BigDecimal(0)) == 0) {
			ZksampleMessageUtils.showErrorMessage("Tidak ada Cost sama sekali, tidak perlu Calculate Margin");
		} else {
			
			if (vCalculateTotalMargin.compareTo(new BigDecimal(1)) >= 0) {
				ZksampleMessageUtils.showErrorMessage("Total Margin tidak bisa " + vCalculateTotalMarginPcn + " % karena ada Cost");
			} else {
				
				BigDecimal vCalTotalSales = vTotalCOGS.divide(vSatu.subtract(vCalculateTotalMargin), 2, RoundingMode.HALF_UP);
				
				vCalTotalSales = vCalTotalSales.divide(vSatu, 0, RoundingMode.HALF_UP);
				
				if (vCalculateHw3psMargin.compareTo(new BigDecimal(1)) >= 0) {
					ZksampleMessageUtils.showErrorMessage("Hardware and 3rd Party Service Margin tidak bisa " + vCalculateHw3psMarginPcn + " % karena ada Cost");
				} else {
					
					BigDecimal vCalSalesHW3PS = vTotalCOGS.divide(vSatu.subtract(vCalculateHw3psMargin), 2, RoundingMode.HALF_UP);
					vCalSalesHW3PS = vCalSalesHW3PS.divide(vSatu, 0, RoundingMode.HALF_UP);
					
					BigDecimal vCalTotalSalesRemaning = vCalTotalSales.subtract(vCalSalesHW3PS);
					
					int vJmlHw3PS = list_T30CostingD_HW3PS_List.size();
					int vJmlACSCare = list_T31CostingD_ACSPS_List.size();
					int vJmlOwnSoftware = list_T32CostingD_OWNSW_List.size();
    
					if (vCalTotalSalesRemaning.compareTo(new BigDecimal(0)) < 0) {
						ZksampleMessageUtils.showErrorMessage("Recalculate Margin tidak bisa dengan Margin Total " + vCalculateTotalMarginPcn + " ," + "\tMargin Hardware dan 3rd Party Services " + vCalculateHw3psMarginPcn + " tidak memungkinkan untuk dilakukan");
					} else {
						
						BigDecimal vTotalSalesHW3PS = new BigDecimal(0);						
						if (CommonUtils.isNotEmpty(dcbSalesHW3PS.getValue())) {
							vTotalSalesHW3PS = dcbSalesHW3PS.getValue();
						}
						
						if (vJmlHw3PS > 0) {
							list_T30CostingD_HW3PS_List_Temp.clear();
							int i = 1;
							
							BigDecimal vCalSalesHW3PSRunning = new BigDecimal(0);
							for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List) {
								BigDecimal vCalSalesHW3PSLine = new BigDecimal(0);	
								
								if (CommonUtils.isNotEmpty(aData)) {
									BigDecimal vSalesHW3PSLine = new BigDecimal(0);									
									if (CommonUtils.isNotEmpty(aData.getSalesTotal())) {
										vSalesHW3PSLine = aData.getSalesTotal();
									}
				
									if (i == vJmlHw3PS) {
										vCalSalesHW3PSLine = vCalSalesHW3PS.subtract(vCalSalesHW3PSRunning);
									} else {
										vCalSalesHW3PSLine = vSalesHW3PSLine.divide(vTotalSalesHW3PS, 2, RoundingMode.HALF_UP).multiply(vCalSalesHW3PS);
										vCalSalesHW3PSLine = vCalSalesHW3PSLine.divide(vSatu, 0, RoundingMode.HALF_UP);
										vCalSalesHW3PSRunning = vCalSalesHW3PSRunning.add(vCalSalesHW3PSLine);
									}
				
									aData.setSalesTotal(vCalSalesHW3PSLine);
									
									i = i+1;
									
									BigDecimal vQty = new BigDecimal(0);
									if (CommonUtils.isNotEmpty(aData.getQty())) {
										vQty = aData.getQty();
									}
				
									if (vQty.compareTo(new BigDecimal(0)) != 0) {
										vCalSalesHW3PSLine = vCalSalesHW3PSLine.divide(vQty, 2, RoundingMode.HALF_UP);
										vCalSalesHW3PSLine = vCalSalesHW3PSLine.divide(vSatu, 0, RoundingMode.HALF_UP);
									}
				
									if (vQty.compareTo(new BigDecimal(0)) == 0) {
										aData.setSalesSatuan(vCalSalesHW3PSLine);
									} else {
										aData.setSalesSatuan(vCalSalesHW3PSLine);
									}
				
									list_T30CostingD_HW3PS_List_Temp.add(aData);
								}
							}
						}
					}


					if (vJmlACSCare > 0 || vJmlOwnSoftware > 0) {
						
						if (vCalTotalSalesRemaning.compareTo(new BigDecimal(0)) <= 0) {
							ZksampleMessageUtils.showErrorMessage("Recalculate Margin tidak bisa dengan Margin Total " + vCalculateTotalMarginPcn + " ," + "\tMargin Hardware dan 3rd Party Services " + vCalculateHw3psMarginPcn + " tidak memungkinkan untuk dilakukan");
							return;
						}		

						BigDecimal vTotalSalesACSPS = new BigDecimal(0);
						if (CommonUtils.isNotEmpty(dcbsalesACSPS.getValue())) {
							vTotalSalesACSPS = dcbsalesACSPS.getValue();
						}

						BigDecimal vTotalSalesOWNSW = new BigDecimal(0);
						if (CommonUtils.isNotEmpty(dcbsalesOWNSW.getValue())) {
							vTotalSalesOWNSW = dcbsalesOWNSW.getValue();
						}


						BigDecimal vCalTotalSalesACSPS = new BigDecimal(0);
						BigDecimal vCalTotalSalesOWNSW = new BigDecimal(0);
						
						if (vJmlACSCare > 0 && vJmlOwnSoftware > 0) {
							
							BigDecimal vTotalSalesFullMargin = vTotalSalesACSPS.add(vTotalSalesOWNSW);
							vCalTotalSalesACSPS = vTotalSalesACSPS.divide(vTotalSalesFullMargin, 2, RoundingMode.HALF_UP).multiply(vCalTotalSalesRemaning);
							vCalTotalSalesACSPS = vCalTotalSalesACSPS.divide(vSatu, 0, RoundingMode.HALF_UP);
							vCalTotalSalesOWNSW = vCalTotalSalesRemaning.subtract(vCalTotalSalesACSPS);
							
							list_T31CostingD_ACSPS_List_Temp.clear();
							list_T32CostingD_OWNSW_List_Temp.clear();
							
							int i = 1;
             
							BigDecimal vCalSalesACSPSRunning = new BigDecimal(0);
							
							for (T31CostingDAcsps aData : list_T31CostingD_ACSPS_List) {
								BigDecimal vCalSalesACSPSLine = new BigDecimal(0);
								
								if (CommonUtils.isNotEmpty(aData)) {
									BigDecimal vSalesACSPSLine = new BigDecimal(0);											
									if (CommonUtils.isNotEmpty(aData.getSalesTotal())) {
										vSalesACSPSLine = aData.getSalesTotal();
									}

									if (i == vJmlACSCare) {
										vCalSalesACSPSLine = vCalTotalSalesACSPS.subtract(vCalSalesACSPSRunning);
									} else {
										vCalSalesACSPSLine = vSalesACSPSLine.divide(vTotalSalesACSPS, 2, RoundingMode.HALF_UP).multiply(vCalTotalSalesACSPS);
										vCalSalesACSPSLine = vCalSalesACSPSLine.divide(vSatu, 0, RoundingMode.HALF_UP);
										vCalSalesACSPSRunning = vCalSalesACSPSRunning.add(vCalSalesACSPSLine);
									}

									aData.setSalesTotal(vCalSalesACSPSLine);

									i = i + 1;
								}
								
								BigDecimal vQty = new BigDecimal(0);
								if (CommonUtils.isNotEmpty(aData.getQty())) {
									vQty = aData.getQty();
								}

								if (vQty.compareTo(new BigDecimal(0)) != 0) {
									vCalSalesACSPSLine = vCalSalesACSPSLine.divide(vQty, 2, RoundingMode.HALF_UP);
									vCalSalesACSPSLine = vCalSalesACSPSLine.divide(vSatu, 0, RoundingMode.HALF_UP);
								}

								if (vQty.compareTo(new BigDecimal(0)) == 0) {
									aData.setSalesSatuan(vCalSalesACSPSLine);
								} else {
									aData.setSalesSatuan(vCalSalesACSPSLine);
								}

								list_T31CostingD_ACSPS_List_Temp.add(aData);
							}
							
							int i2 = 1;
							BigDecimal vCalSalesOWNSWRunning = new BigDecimal(0);	
								
							for (T32CostingDOwnsw aData : list_T32CostingD_OWNSW_List) {
								BigDecimal vCalSalesOWNSWLine = new BigDecimal(0);
								

								if (CommonUtils.isNotEmpty(aData)) {

									BigDecimal vSalesOWNSWLine = new BigDecimal(0);
									if (CommonUtils.isNotEmpty(aData.getSalesTotal())) {
										vSalesOWNSWLine = aData.getSalesTotal();
									}

									if (i2 == vJmlOwnSoftware) {
										vCalSalesOWNSWLine = vCalTotalSalesOWNSW.subtract(vCalSalesOWNSWRunning);
									} else {
										vCalSalesOWNSWLine = vSalesOWNSWLine.divide(vTotalSalesOWNSW, 2, RoundingMode.HALF_UP).multiply(vCalTotalSalesOWNSW);
										vCalSalesOWNSWLine = vCalSalesOWNSWLine.divide(vSatu, 0, RoundingMode.HALF_UP);
										vCalSalesOWNSWRunning = vCalSalesOWNSWRunning.add(vCalSalesOWNSWLine);
									}

									aData.setSalesTotal(vCalSalesOWNSWLine);
									++i2;
                  
									BigDecimal vQty = new BigDecimal(0);
									if (CommonUtils.isNotEmpty(aData.getQty())) {
										vQty = aData.getQty();
									}

									if (vQty.compareTo(new BigDecimal(0)) != 0) {
										vCalSalesOWNSWLine = vCalSalesOWNSWLine.divide(vQty, 2, RoundingMode.HALF_UP);
										vCalSalesOWNSWLine = vCalSalesOWNSWLine.divide(vSatu, 0, RoundingMode.HALF_UP);
									}

									if (vQty.compareTo(new BigDecimal(0)) == 0) {
										aData.setSalesSatuan(vCalSalesOWNSWLine);
									} else {
										aData.setSalesSatuan(vCalSalesOWNSWLine);
									}

									list_T32CostingD_OWNSW_List_Temp.add(aData);
									
								}
							}

							vCalTotalSalesRemaning = new BigDecimal(0);
						} else {
							
							int i;
							
             				if (vJmlACSCare > 0) {
             					vCalTotalSalesACSPS = vCalTotalSalesRemaning;
             					list_T31CostingD_ACSPS_List_Temp.clear();
             					
             					i = 1;
             					BigDecimal vCalSalesACSPSRunning = new BigDecimal(0);
             					
             					for (T31CostingDAcsps aData : list_T31CostingD_ACSPS_List) {
             						BigDecimal vCalSalesLine = new BigDecimal(0);
             						if (CommonUtils.isNotEmpty(aData)) {
             							BigDecimal vSalesLine = new BigDecimal(0);
             							if (CommonUtils.isNotEmpty(aData.getSalesTotal())) {
             								vSalesLine = aData.getSalesTotal();
             							}

             							if (i == vJmlACSCare) {
             								vCalSalesLine = vCalTotalSalesACSPS.subtract(vCalSalesACSPSRunning);
             							} else {
             								vCalSalesLine = vSalesLine.divide(vTotalSalesACSPS, 2, RoundingMode.HALF_UP).multiply(vCalTotalSalesACSPS);
             								vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
             								vCalSalesACSPSRunning = vCalSalesACSPSRunning.add(vCalSalesLine);
             							}

             							aData.setSalesTotal(vCalSalesLine);

             							i = i + 1 ;
             							
             							BigDecimal vQty = new BigDecimal(0);
             							if (CommonUtils.isNotEmpty(aData.getQty())) {
             								vQty = aData.getQty();
             							}

             							if (vQty.compareTo(new BigDecimal(0)) != 0) {
             								vCalSalesLine = vCalSalesLine.divide(vQty, 2, RoundingMode.HALF_UP);
             								vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
             							}

             							if (vQty.compareTo(new BigDecimal(0)) == 0) {
             								aData.setSalesSatuan(vCalSalesLine);
             							} else {
             								aData.setSalesSatuan(vCalSalesLine);
             							}

             							list_T31CostingD_ACSPS_List_Temp.add(aData);
             						}
             					}

             					vCalTotalSalesRemaning = new BigDecimal(0);
             				}

             				if (vJmlOwnSoftware > 0) {
             					vCalTotalSalesOWNSW = vCalTotalSalesRemaning;
             					list_T32CostingD_OWNSW_List_Temp.clear();
             					
             					i = 1;             					
             					BigDecimal vCalSalesOwnSoftwareRunning = new BigDecimal(0);
             					
             					for (T32CostingDOwnsw aData : list_T32CostingD_OWNSW_List) {
             						BigDecimal vCalSalesLine = new BigDecimal(0);
             						if (CommonUtils.isNotEmpty(aData)) {
             							BigDecimal vSalesLine = new BigDecimal(0);
             							if (CommonUtils.isNotEmpty(aData.getSalesTotal())) {
             								vSalesLine = aData.getSalesTotal();
             							}

             							if (i == vJmlOwnSoftware) {
             								vCalSalesLine = vCalTotalSalesOWNSW.subtract(vCalSalesOwnSoftwareRunning);
             							} else {
             								vCalSalesLine = vSalesLine.divide(vTotalSalesOWNSW, 2, RoundingMode.HALF_UP).multiply(vCalTotalSalesOWNSW);
             								vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
             								vCalSalesOwnSoftwareRunning = vCalSalesOwnSoftwareRunning.add(vCalSalesLine);
             							}

             							aData.setSalesTotal(vCalSalesLine);
             							i = i + 1;
             							
             							BigDecimal vQty = new BigDecimal(0);
             							if (CommonUtils.isNotEmpty(aData.getQty())) {
             								vQty = aData.getQty();
             							}

             							if (vQty.compareTo(new BigDecimal(0)) != 0) {
             								vCalSalesLine = vCalSalesLine.divide(vQty, 2, RoundingMode.HALF_UP);
             								vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
             							}

             							if (vQty.compareTo(new BigDecimal(0)) == 0) {
             								aData.setSalesSatuan(vCalSalesLine);
             							} else {
             								aData.setSalesSatuan(vCalSalesLine);
             							}

             							list_T32CostingD_OWNSW_List_Temp.add(aData);
             						}
             					}

             					vCalTotalSalesRemaning = new BigDecimal(0);
             				}
						}
					}
					

					if (vCalTotalSalesRemaning.compareTo(new BigDecimal(0)) != 0) {
						ZksampleMessageUtils.showErrorMessage("Recalculate Margin tidak bisa dengan Margin Total " + vCalculateTotalMarginPcn + " ," + "\tMargin Hardware dan 3rd Party Services " + vCalculateHw3psMarginPcn + " tidak memungkinkan untuk dilakukan");
					} else {
	                  	if (CommonUtils.isNotEmpty(list_T30CostingD_HW3PS_List_Temp)) {                  		
	                  		list_T30CostingD_HW3PS_List.clear();
	                  		for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List_Temp) {
	                  			list_T30CostingD_HW3PS_List.add(aData);
	                  		}	
	                  		setModel_T30CostingD_HW3PS_List();
	                  		HitDetailT30CostingDHw3ps();
	                  	}
	                  	
	                  	if (CommonUtils.isNotEmpty(list_T31CostingD_ACSPS_List_Temp)) {                  		
	                  		list_T31CostingD_ACSPS_List.clear();
	                  		for (T31CostingDAcsps aData : list_T31CostingD_ACSPS_List_Temp) {
	                  			list_T31CostingD_ACSPS_List.add(aData);
	                  		}	
	                  		setModel_T31CostingD_ACSPS_List();
	                  		HitDetailT31CostingDAcsps();
	                  	}
	                  	
	                  	if (CommonUtils.isNotEmpty(list_T32CostingD_OWNSW_List_Temp)) {                  		
	                  		list_T32CostingD_OWNSW_List.clear();
	                  		for (T32CostingDOwnsw aData : list_T32CostingD_OWNSW_List_Temp) {
	                  			list_T32CostingD_OWNSW_List.add(aData);
	                  		}	
	                  		setModel_T32CostingD_OWNSW_List();
	                  		HitDetailT32CostingDOwnsw();
	                  	}

					}
				}
			}
		}
	}

	public void onClick$btnRecalculate1() throws InterruptedException {
		
		BigDecimal vCalculateTotalMarginPcn = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcbCalculateTotalMarginOnlyPcn.getValue())) {
			vCalculateTotalMarginPcn = dcbCalculateTotalMarginOnlyPcn.getValue();
		}

		BigDecimal vCalculateTotalMargin = vCalculateTotalMarginPcn.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
		BigDecimal vSatu = new BigDecimal(1);

		BigDecimal vTotalCOGS = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcbTotalCOGS_2.getValue())) {
			vTotalCOGS = dcbTotalCOGS_2.getValue();
		}

		if (vTotalCOGS.compareTo(new BigDecimal(0)) == 0) {
			ZksampleMessageUtils.showErrorMessage("Tidak ada Cost sama sekali, tidak perlu Calculate Margin");
		} else if (vCalculateTotalMargin.compareTo(new BigDecimal(1)) >= 0) {
			ZksampleMessageUtils.showErrorMessage("Total Margin tidak bisa " + vCalculateTotalMarginPcn + " % karena ada Cost");
		} else {
			BigDecimal vCalTotalSales = vTotalCOGS.divide(vSatu.subtract(vCalculateTotalMargin), 2, RoundingMode.HALF_UP);
			vCalTotalSales = vCalTotalSales.divide(vSatu, 0, RoundingMode.HALF_UP);
			BigDecimal vTotalALLSales = new BigDecimal(0);
			
			if (CommonUtils.isNotEmpty(dcbTotalSales.getValue())) {
				vTotalALLSales = dcbTotalSales.getValue();
			}

			BigDecimal vTotalSalesHW3PS = new BigDecimal(0);
			BigDecimal vCalTotalSalesHW3PS = new BigDecimal(0);
			if (CommonUtils.isNotEmpty(dcbSalesHW3PS.getValue())) {
				vTotalSalesHW3PS = dcbSalesHW3PS.getValue();
			}

			BigDecimal vTotalSalesACSPS = new BigDecimal(0);
			BigDecimal vCalTotalSalesACSPS = new BigDecimal(0);
			if (CommonUtils.isNotEmpty(dcbsalesACSPS.getValue())) {
				vTotalSalesACSPS = dcbsalesACSPS.getValue();
			}

			BigDecimal vTotalSalesOWNSW = new BigDecimal(0);
			BigDecimal vCalTotalSalesOWNSW = new BigDecimal(0);
			if (CommonUtils.isNotEmpty(dcbsalesOWNSW.getValue())) {
				vTotalSalesOWNSW = dcbsalesOWNSW.getValue();
			}

			int vJmlHw3PS = list_T30CostingD_HW3PS_List.size();
			int vJmlACSCare = list_T31CostingD_ACSPS_List.size();
			int vJmlOwnSoftware = list_T32CostingD_OWNSW_List.size();
			
			if (vTotalSalesHW3PS.compareTo(new BigDecimal(0)) != 0) {
				if (vJmlACSCare == 0 && vJmlOwnSoftware == 0) {
					vCalTotalSalesHW3PS = vCalTotalSales;
				} else {
					vCalTotalSalesHW3PS = vTotalSalesHW3PS.divide(vTotalALLSales, 2, RoundingMode.HALF_UP).multiply(vCalTotalSales);
					vCalTotalSalesHW3PS = vCalTotalSalesHW3PS.divide(vSatu, 0, RoundingMode.HALF_UP);
				}
			}

			if (vTotalSalesACSPS.compareTo(new BigDecimal(0)) != 0) {
				if (vJmlHw3PS == 0 && vJmlOwnSoftware == 0) {
					vCalTotalSalesACSPS = vCalTotalSales;
				} else {
					if (vJmlOwnSoftware > 0) {				
						vCalTotalSalesACSPS = vTotalSalesACSPS.divide(vTotalALLSales, 2, RoundingMode.HALF_UP).multiply(vCalTotalSales);
						vCalTotalSalesACSPS = vCalTotalSalesACSPS.divide(vSatu, 0, RoundingMode.HALF_UP);
					} else {
						vCalTotalSalesACSPS = vCalTotalSales.subtract(vCalTotalSalesHW3PS);
					}
				}
			}

			if (vTotalSalesOWNSW.compareTo(new BigDecimal(0)) != 0) {
				if (vJmlHw3PS == 0 && vJmlACSCare == 0) {
					vCalTotalSalesOWNSW = vCalTotalSales;
				} else {
					vCalTotalSalesOWNSW = vCalTotalSales.subtract(vCalTotalSalesHW3PS.add(vCalTotalSalesACSPS));
				}
			}

			list_T30CostingD_HW3PS_List_Temp.clear();
			if (vJmlHw3PS > 0) {
				int i = 1;
				BigDecimal vCalSalesRunning = new BigDecimal(0);
				
				for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List) {					
					BigDecimal vCalSalesLine = new BigDecimal(0);
					if (CommonUtils.isNotEmpty(aData)) {
						
						BigDecimal vSalesLine = new BigDecimal(0);
						if (CommonUtils.isNotEmpty(aData.getSalesTotal())) {
							vSalesLine = aData.getSalesTotal();
						}

						if (i == vJmlHw3PS) {
							vCalSalesLine = vCalTotalSalesHW3PS.subtract(vCalSalesRunning);
						} else {
							vCalSalesLine = vSalesLine.divide(vTotalSalesHW3PS, 2, RoundingMode.HALF_UP).multiply(vCalTotalSalesHW3PS);
							vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
							vCalSalesRunning = vCalSalesRunning.add(vCalSalesLine);
						}

						aData.setSalesTotal(vCalSalesLine);
						i = i + 1;
						
						BigDecimal vQty = new BigDecimal(0);
						if (CommonUtils.isNotEmpty(aData.getQty())) {
							vQty = aData.getQty();
						}

						if (vQty.compareTo(new BigDecimal(0)) != 0) {
							vCalSalesLine = vCalSalesLine.divide(vQty, 2, RoundingMode.HALF_UP);
							vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
						}

						if (vQty.compareTo(new BigDecimal(0)) == 0) {
							aData.setSalesSatuan(vCalSalesLine);
						} else {
							aData.setSalesSatuan(vCalSalesLine);
						}

						list_T30CostingD_HW3PS_List_Temp.add(aData);
					}
				}
			}

			list_T31CostingD_ACSPS_List_Temp.clear();
			if (vJmlACSCare > 0) {
				int i = 1;
				BigDecimal vCalSalesRunning = new BigDecimal(0);
				for (T31CostingDAcsps aData : list_T31CostingD_ACSPS_List) {					
					BigDecimal vCalSalesLine = new BigDecimal(0);
					if (CommonUtils.isNotEmpty(aData)) {
						BigDecimal vSalesLine = new BigDecimal(0);
						if (CommonUtils.isNotEmpty(aData.getSalesTotal())) {
							vSalesLine = aData.getSalesTotal();
						}

						if (i == vJmlACSCare) {
							vCalSalesLine = vCalTotalSalesACSPS.subtract(vCalSalesRunning);
						} else {
							vCalSalesLine = vSalesLine.divide(vTotalSalesACSPS, 2, RoundingMode.HALF_UP).multiply(vCalTotalSalesACSPS);
							vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
							vCalSalesRunning = vCalSalesRunning.add(vCalSalesLine);
						}

						aData.setSalesTotal(vCalSalesLine);
						i = i + 1;
                  
						BigDecimal vQty = new BigDecimal(0);
						if (CommonUtils.isNotEmpty(aData.getQty())) {
							vQty = aData.getQty();
						}

						if (vQty.compareTo(new BigDecimal(0)) != 0) {
							vCalSalesLine = vCalSalesLine.divide(vQty, 2, RoundingMode.HALF_UP);
							vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
						}

						if (vQty.compareTo(new BigDecimal(0)) == 0) {
							aData.setSalesSatuan(vCalSalesLine);
						} else {
							aData.setSalesSatuan(vCalSalesLine);
						}

						list_T31CostingD_ACSPS_List_Temp.add(aData);
					}
				}
			}

			list_T32CostingD_OWNSW_List_Temp.clear();
			if (vJmlOwnSoftware > 0) {
				int i = 1;
				BigDecimal vCalSalesRunning = new BigDecimal(0);
				for (T32CostingDOwnsw aData : list_T32CostingD_OWNSW_List) {					
					BigDecimal vCalSalesLine = new BigDecimal(0);
        		 
					if (CommonUtils.isNotEmpty(aData)) { 
						BigDecimal vSalesLine = new BigDecimal(0);
						if (CommonUtils.isNotEmpty(aData.getSalesTotal())) {
							vSalesLine = aData.getSalesTotal();
						}

						if (i == vJmlOwnSoftware) {
							vCalSalesLine = vCalTotalSalesOWNSW.subtract(vCalSalesRunning);
						} else {
							vCalSalesLine = vSalesLine.divide(vTotalSalesOWNSW, 2, RoundingMode.HALF_UP).multiply(vCalTotalSalesOWNSW);
							vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
							vCalSalesRunning = vCalSalesRunning.add(vCalSalesLine);
						}

						aData.setSalesTotal(vCalSalesLine);
						i = i + 1;
                  
						BigDecimal vQty = new BigDecimal(0);
						if (CommonUtils.isNotEmpty(aData.getQty())) {
							vQty = aData.getQty();
						}

						if (vQty.compareTo(new BigDecimal(0)) != 0) {
							vCalSalesLine = vCalSalesLine.divide(vQty, 2, RoundingMode.HALF_UP);
							vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
						}

						if (vQty.compareTo(new BigDecimal(0)) == 0) {
							aData.setSalesSatuan(vCalSalesLine);
						} else {
							aData.setSalesSatuan(vCalSalesLine);
						}

						list_T32CostingD_OWNSW_List_Temp.add(aData);
					}
				}
			}
         
			if (CommonUtils.isNotEmpty(list_T30CostingD_HW3PS_List_Temp)) {                  		
				list_T30CostingD_HW3PS_List.clear();
				for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List_Temp) {
					list_T30CostingD_HW3PS_List.add(aData);
				}	
				setModel_T30CostingD_HW3PS_List();
				HitDetailT30CostingDHw3ps();
			}
      	
			if (CommonUtils.isNotEmpty(list_T31CostingD_ACSPS_List_Temp)) {                  		
				list_T31CostingD_ACSPS_List.clear();
				for (T31CostingDAcsps aData : list_T31CostingD_ACSPS_List_Temp) {
					list_T31CostingD_ACSPS_List.add(aData);
				}	
				setModel_T31CostingD_ACSPS_List();
				HitDetailT31CostingDAcsps();
			}
      	
			if (CommonUtils.isNotEmpty(list_T32CostingD_OWNSW_List_Temp)) {                  		
				list_T32CostingD_OWNSW_List.clear();
				for (T32CostingDOwnsw aData : list_T32CostingD_OWNSW_List_Temp) {
					list_T32CostingD_OWNSW_List.add(aData);
				}	
				setModel_T32CostingD_OWNSW_List();
				HitDetailT32CostingDOwnsw();
			}
		}
	}
	
	
	public void onClick$btnRecalculate2() throws InterruptedException {
		BigDecimal vCalculateHw3psMarginPcn = new BigDecimal(0);
		
		if (CommonUtils.isNotEmpty(dcbCalculateHw3PSMarginOnlyPcn.getValue())) {
			vCalculateHw3psMarginPcn = dcbCalculateHw3PSMarginOnlyPcn.getValue();
		}

		BigDecimal vCalculateHw3psMargin = vCalculateHw3psMarginPcn.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
		BigDecimal vSatu = new BigDecimal(1);
		
      	BigDecimal vTotalCOGS = new BigDecimal(0);
      	if (CommonUtils.isNotEmpty(dcbTotalCOGS_2.getValue())) {
      		vTotalCOGS = dcbTotalCOGS_2.getValue();
      	}

      	if (vTotalCOGS.compareTo(new BigDecimal(0)) == 0) {
      		ZksampleMessageUtils.showErrorMessage("Tidak ada Cost sama sekali, tidak perlu Calculate Margin");
      	} else if (vCalculateHw3psMargin.compareTo(new BigDecimal(1)) >= 0) {
      		ZksampleMessageUtils.showErrorMessage("Hardware and 3rd Party Service Margin tidak bisa " + vCalculateHw3psMarginPcn + " % karena ada Cost");
      	} else {
      		BigDecimal vCalSalesHW3PS = vTotalCOGS.divide(vSatu.subtract(vCalculateHw3psMargin), 2, RoundingMode.HALF_UP);
      		vCalSalesHW3PS = vCalSalesHW3PS.divide(vSatu, 0, RoundingMode.HALF_UP);
      		
      		int vJmlHw3PS = list_T30CostingD_HW3PS_List.size();
      		
      		BigDecimal vTotalSalesHW3PS = new BigDecimal(0);
      		if (CommonUtils.isNotEmpty(dcbSalesHW3PS.getValue())) {
      			vTotalSalesHW3PS = dcbSalesHW3PS.getValue();
      		}

      		if (vJmlHw3PS > 0) {
      			list_T30CostingD_HW3PS_List_Temp.clear();
      			
      			int i = 1;
      			BigDecimal vCalSalesHW3PSRunning = new BigDecimal(0);
      			for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List) {					
					BigDecimal vCalSalesLine = new BigDecimal(0);
        		 
					if (CommonUtils.isNotEmpty(aData)) { 
						BigDecimal vSalesLine = new BigDecimal(0);
						if (CommonUtils.isNotEmpty(aData.getSalesTotal())) {
							vSalesLine = aData.getSalesTotal();
						}

						if (i == vJmlHw3PS) {
							vCalSalesLine = vCalSalesHW3PS.subtract(vCalSalesHW3PSRunning);
						} else {
							vCalSalesLine = vSalesLine.divide(vTotalSalesHW3PS, 2, RoundingMode.HALF_UP).multiply(vCalSalesHW3PS);
							vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
							vCalSalesHW3PSRunning = vCalSalesHW3PSRunning.add(vCalSalesLine);
						}

						aData.setSalesTotal(vCalSalesLine);
						i = i + 1;
                 
						BigDecimal vQty = new BigDecimal(0);
						if (CommonUtils.isNotEmpty(aData.getQty())) {
							vQty = aData.getQty();
						}

						if (vQty.compareTo(new BigDecimal(0)) != 0) {
							vCalSalesLine = vCalSalesLine.divide(vQty, 2, RoundingMode.HALF_UP);
							vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
						}

						if (vQty.compareTo(new BigDecimal(0)) == 0) {
							aData.setSalesSatuan(vCalSalesLine);
						} else {
							aData.setSalesSatuan(vCalSalesLine);
						}

						list_T30CostingD_HW3PS_List_Temp.add(aData);
					}
      			}
      		}
      		
      		if (CommonUtils.isNotEmpty(list_T30CostingD_HW3PS_List_Temp)) {                  		
          		list_T30CostingD_HW3PS_List.clear();
          		for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List_Temp) {
          			list_T30CostingD_HW3PS_List.add(aData);
          		}	
          		setModel_T30CostingD_HW3PS_List();
          		HitDetailT30CostingDHw3ps();
          	}
      	}
      	
      	
	}

	public void onClick$btnRecalculate3() throws InterruptedException {
		
		BigDecimal vCalculateHwMarginOnlyPcn = new BigDecimal(0);
		if (CommonUtils.isNotEmpty(dcbCalculateHwMarginOnlyPcn.getValue())) {
			vCalculateHwMarginOnlyPcn = dcbCalculateHwMarginOnlyPcn.getValue();
		}

		BigDecimal vCalculateHwMarginOnly = vCalculateHwMarginOnlyPcn.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
		BigDecimal vSatu = new BigDecimal(1);
		
		int vJmlHw3PS = list_T30CostingD_HW3PS_List.size();
		
		if (vJmlHw3PS > 0) {
			list_T30CostingD_HW3PS_List_Temp.clear();
         
			for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List) {					
				if (CommonUtils.isNotEmpty(aData)) { 
					String vItemCategory = ".";
					if (CommonUtils.isNotEmpty(aData.getItemCategory())) {
						vItemCategory = aData.getItemCategory();
					}

					if (vItemCategory.equals("HW01")) {
                
						BigDecimal vCogsLine = new BigDecimal(0);
						if (CommonUtils.isNotEmpty(aData.getCogsTotal())) {
							vCogsLine = aData.getCogsTotal();
						}

                  		BigDecimal vCalSalesLine = vCogsLine.divide(vSatu.subtract(vCalculateHwMarginOnly), 2, RoundingMode.HALF_UP);
                  		vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
                  		aData.setSalesTotal(vCalSalesLine);
                  		
                  		BigDecimal vQty = new BigDecimal(0);
                  		if (CommonUtils.isNotEmpty(aData.getQty())) {
                  			vQty = aData.getQty();
                  		}

                  		if (vQty.compareTo(new BigDecimal(0)) != 0) {
                  			vCalSalesLine = vCalSalesLine.divide(vQty, 2, RoundingMode.HALF_UP);
                  			vCalSalesLine = vCalSalesLine.divide(vSatu, 0, RoundingMode.HALF_UP);
                  		}

                  		if (vQty.compareTo(new BigDecimal(0)) == 0) {
                  			aData.setSalesSatuan(vCalSalesLine);
                  		} else {
                  			aData.setSalesSatuan(vCalSalesLine);
                  		}

                  		list_T30CostingD_HW3PS_List_Temp.add(aData);
					}
				}
			}
		}

		if (CommonUtils.isNotEmpty(list_T30CostingD_HW3PS_List_Temp)) {
			list_T30CostingD_HW3PS_List.clear();
			for (T30CostingDHw3ps aData : list_T30CostingD_HW3PS_List_Temp) {					
				if (CommonUtils.isNotEmpty(aData)) { 
					list_T30CostingD_HW3PS_List.add(aData);
				}
			}
			setModel_T30CostingD_HW3PS_List();
			HitDetailT30CostingDHw3ps();
		}
		
	}	

	// Sorting Detail 1 - T30CostingD_HW3PS
	
    public void onSort$listheader_T30CostingD_HW3PS_ItemDesc(Event event) {
    	sorting_T30CostingD_HW3PS(listheader_T30CostingD_HW3PS_ItemDesc, T30CostingD_HW3PS_Comparator.COMPARE_BY_ITEMDESC);
    }

    public void onSort$listheader_T30CostingD_HW3PS_ItemNo(Event event) {
    	sorting_T30CostingD_HW3PS(listheader_T30CostingD_HW3PS_ItemNo, T30CostingD_HW3PS_Comparator.COMPARE_BY_ITEMNO);
    }
    
    public void onSort$listheader_T30CostingD_HW3PS_ItemCategory(Event event) {
    	sorting_T30CostingD_HW3PS(listheader_T30CostingD_HW3PS_ItemCategory, T30CostingD_HW3PS_Comparator.COMPARE_BY_ITEMCATEGORY);
    }
    
    public void onSort$listheader_T30CostingD_HW3PS_Qty(Event event) {
    	sorting_T30CostingD_HW3PS(listheader_T30CostingD_HW3PS_Qty, T30CostingD_HW3PS_Comparator.COMPARE_BY_QTY);
    }
    
    public void onSort$listheader_T30CostingD_HW3PS_SalesSatuan(Event event) {
    	sorting_T30CostingD_HW3PS(listheader_T30CostingD_HW3PS_SalesSatuan, T30CostingD_HW3PS_Comparator.COMPARE_BY_SALESSATUAN);
    }
    
    public void onSort$listheader_T30CostingD_HW3PS_SalesTotal(Event event) {
    	sorting_T30CostingD_HW3PS(listheader_T30CostingD_HW3PS_SalesTotal, T30CostingD_HW3PS_Comparator.COMPARE_BY_SALESTOTAL);
    }
    
    public void onSort$listheader_T30CostingD_HW3PS_CogsSatuan(Event event) {
    	sorting_T30CostingD_HW3PS(listheader_T30CostingD_HW3PS_CogsSatuan, T30CostingD_HW3PS_Comparator.COMPARE_BY_COGSSATUAN);
    }
    
    public void onSort$listheader_T30CostingD_HW3PS_CogsTotal(Event event) {
    	sorting_T30CostingD_HW3PS(listheader_T30CostingD_HW3PS_CogsTotal, T30CostingD_HW3PS_Comparator.COMPARE_BY_COGSTOTAL);
    }
    
    public void onSort$listheader_T30CostingD_HW3PS_Catatan(Event event) {
    	sorting_T30CostingD_HW3PS(listheader_T30CostingD_HW3PS_Catatan, T30CostingD_HW3PS_Comparator.COMPARE_BY_CATATAN);
    }

    private void sorting_T30CostingD_HW3PS(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T30CostingD_HW3PS_List, new T30CostingD_HW3PS_Comparator(false, sortBy));
    		} else {
    			Collections.sort(list_T30CostingD_HW3PS_List, new T30CostingD_HW3PS_Comparator(true, sortBy));
    		}
    	}
    	
    	setModel_T30CostingD_HW3PS_List();    
    }
	
    // Sorting Detail 2 - T31CostingD_ACSPS
    
    public void onSort$listheader_T31CostingD_ACSPS_ItemDesc(Event event) {
    	sorting_T31CostingD_ACSPS(listheader_T31CostingD_ACSPS_ItemDesc, T31CostingD_ACSPS_Comparator.COMPARE_BY_ITEMDESC);
    }

    public void onSort$listheader_T31CostingD_ACSPS_ItemNo(Event event) {
    	sorting_T31CostingD_ACSPS(listheader_T31CostingD_ACSPS_ItemNo, T31CostingD_ACSPS_Comparator.COMPARE_BY_ITEMNO);
    }
    
    public void onSort$listheader_T31CostingD_ACSPS_Qty(Event event) {
    	sorting_T31CostingD_ACSPS(listheader_T31CostingD_ACSPS_Qty, T31CostingD_ACSPS_Comparator.COMPARE_BY_QTY);
    }
    
    public void onSort$listheader_T31CostingD_ACSPS_SalesSatuan(Event event) {
    	sorting_T31CostingD_ACSPS(listheader_T31CostingD_ACSPS_SalesSatuan, T31CostingD_ACSPS_Comparator.COMPARE_BY_SALESSATUAN);
    }
    
    public void onSort$listheader_T31CostingD_ACSPS_SalesTotal(Event event) {
    	sorting_T31CostingD_ACSPS(listheader_T31CostingD_ACSPS_SalesTotal, T31CostingD_ACSPS_Comparator.COMPARE_BY_SALESTOTAL);
    }
    
    public void onSort$listheader_T31CostingD_ACSPS_Catatan(Event event) {
    	sorting_T31CostingD_ACSPS(listheader_T31CostingD_ACSPS_Catatan, T31CostingD_ACSPS_Comparator.COMPARE_BY_CATATAN);
    }

    private void sorting_T31CostingD_ACSPS(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T31CostingD_ACSPS_List, new T31CostingD_ACSPS_Comparator(false, sortBy));
    		} else {
    			Collections.sort(list_T31CostingD_ACSPS_List, new T31CostingD_ACSPS_Comparator(true, sortBy));
    		}
    	}
    	
    	setModel_T31CostingD_ACSPS_List();    
    }
    
    // Sorting Detail 3 - T32CostingD_OWNSW
    
    public void onSort$listheader_T32CostingD_OWNSW_ItemDesc(Event event) {
    	sorting_T32CostingD_OWNSW(listheader_T32CostingD_OWNSW_ItemDesc, T32CostingD_OWNSW_Comparator.COMPARE_BY_ITEMDESC);
    }

    public void onSort$listheader_T32CostingD_OWNSW_ItemNo(Event event) {
    	sorting_T32CostingD_OWNSW(listheader_T32CostingD_OWNSW_ItemNo, T32CostingD_OWNSW_Comparator.COMPARE_BY_ITEMNO);
    }
    
    public void onSort$listheader_T32CostingD_OWNSW_Qty(Event event) {
    	sorting_T32CostingD_OWNSW(listheader_T32CostingD_OWNSW_Qty, T32CostingD_OWNSW_Comparator.COMPARE_BY_QTY);
    }
    
    public void onSort$listheader_T32CostingD_OWNSW_SalesSatuan(Event event) {
    	sorting_T32CostingD_OWNSW(listheader_T32CostingD_OWNSW_SalesSatuan, T32CostingD_OWNSW_Comparator.COMPARE_BY_SALESSATUAN);
    }
    
    public void onSort$listheader_T32CostingD_OWNSW_SalesTotal(Event event) {
    	sorting_T32CostingD_OWNSW(listheader_T32CostingD_OWNSW_SalesTotal, T32CostingD_OWNSW_Comparator.COMPARE_BY_SALESTOTAL);
    }
    
    public void onSort$listheader_T32CostingD_OWNSW_Catatan(Event event) {
    	sorting_T32CostingD_OWNSW(listheader_T32CostingD_OWNSW_Catatan, T32CostingD_OWNSW_Comparator.COMPARE_BY_CATATAN);
    }

    private void sorting_T32CostingD_OWNSW(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T32CostingD_OWNSW_List, new T32CostingD_OWNSW_Comparator(false, sortBy));
    		} else {
    			Collections.sort(list_T32CostingD_OWNSW_List, new T32CostingD_OWNSW_Comparator(true, sortBy));
    		}
    	}
    	
    	setModel_T32CostingD_OWNSW_List();    
    }
    
    // Sorting Detail 4 - T33CostingD_OTHER
    
    public void onSort$listheader_T33CostingD_OTHER_ItemDesc(Event event) {
    	sorting_T33CostingD_OTHER(listheader_T33CostingD_OTHER_ItemDesc, T33CostingD_OTHER_Comparator.COMPARE_BY_ITEMDESC);
    }

    public void onSort$listheader_T33CostingD_OTHER_ItemNo(Event event) {
    	sorting_T33CostingD_OTHER(listheader_T33CostingD_OTHER_ItemNo, T33CostingD_OTHER_Comparator.COMPARE_BY_ITEMNO);
    }
    
    public void onSort$listheader_T33CostingD_OTHER_Qty(Event event) {
    	sorting_T33CostingD_OTHER(listheader_T33CostingD_OTHER_Qty, T33CostingD_OTHER_Comparator.COMPARE_BY_QTY);
    }
    
    public void onSort$listheader_T33CostingD_OTHER_CogsSatuan(Event event) {
    	sorting_T33CostingD_OTHER(listheader_T33CostingD_OTHER_CogsSatuan, T33CostingD_OTHER_Comparator.COMPARE_BY_COGSSATUAN);
    }
    
    public void onSort$listheader_T33CostingD_OTHER_CogsTotal(Event event) {
    	sorting_T33CostingD_OTHER(listheader_T33CostingD_OTHER_CogsTotal, T33CostingD_OTHER_Comparator.COMPARE_BY_COGSTOTAL);
    }
    
    public void onSort$listheader_T33CostingD_OTHER_Catatan(Event event) {
    	sorting_T33CostingD_OTHER(listheader_T33CostingD_OTHER_Catatan, T33CostingD_OTHER_Comparator.COMPARE_BY_CATATAN);
    }

    private void sorting_T33CostingD_OTHER(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T33CostingD_OTHER_List, new T33CostingD_OTHER_Comparator(false, sortBy));
    		} else {
    			Collections.sort(list_T33CostingD_OTHER_List, new T33CostingD_OTHER_Comparator(true, sortBy));
    		}
    	}
    	
    	setModel_T33CostingD_OTHER_List();    
    }
    
    // Sorting Detail 5 - T34CostingD_PAYMENT
    public void onSort$listheader_T34CostingD_PAYMENT_NoInvoice(Event event) {
    	sorting_T34CostingD_PAYMENT(listheader_T34CostingD_PAYMENT_NoInvoice, T34CostingD_PAYMENT_Comparator.COMPARE_BY_NOINVOICE);
    }

    public void onSort$listheader_T34CostingD_PAYMENT_TglInvoice(Event event) {
    	sorting_T34CostingD_PAYMENT(listheader_T34CostingD_PAYMENT_TglInvoice, T34CostingD_PAYMENT_Comparator.COMPARE_BY_TGLINVOICE);
    }
    
    public void onSort$listheader_T34CostingD_PAYMENT_NilaiInvoice(Event event) {
    	sorting_T34CostingD_PAYMENT(listheader_T34CostingD_PAYMENT_NilaiInvoice, T34CostingD_PAYMENT_Comparator.COMPARE_BY_NILAIINVOICE);
    }
    
    public void onSort$listheader_T34CostingD_PAYMENT_NoLunas(Event event) {
    	sorting_T34CostingD_PAYMENT(listheader_T34CostingD_PAYMENT_NoLunas, T34CostingD_PAYMENT_Comparator.COMPARE_BY_NOLUNAS);
    }
    
    public void onSort$listheader_T34CostingD_PAYMENT_TglLunas(Event event) {
    	sorting_T34CostingD_PAYMENT(listheader_T34CostingD_PAYMENT_TglLunas, T34CostingD_PAYMENT_Comparator.COMPARE_BY_TGLLUNAS);
    }
    
    public void onSort$listheader_T34CostingD_PAYMENT_NilaiLunas(Event event) {
    	sorting_T34CostingD_PAYMENT(listheader_T34CostingD_PAYMENT_NilaiLunas, T34CostingD_PAYMENT_Comparator.COMPARE_BY_NILAILUNAS);
    }

    private void sorting_T34CostingD_PAYMENT(Listheader listHeader, int sortBy){
    	if(CommonUtils.isNotEmpty(listHeader.getSortDirection())){
    		if(listHeader.getSortDirection().equals("ascending")){
    			Collections.sort(list_T34CostingD_PAYMENT_List, new T34CostingD_PAYMENT_Comparator(false, sortBy));
    		} else {
    			Collections.sort(list_T34CostingD_PAYMENT_List, new T34CostingD_PAYMENT_Comparator(true, sortBy));
    		}
    	}
    	
    	setModel_T34CostingD_PAYMENT_List();    
    }
    
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //
	// ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
	// +++++++++++++++++++++++++++++++++++++++++++++++++ //



	public T29CostingH getT29CostingH() {
		return getT29CostingMainCtrl().getSelectedT29Costing();
	}

	public void setT29CostingH(T29CostingH selectedT29Costing) {
		getT29CostingMainCtrl().setSelectedT29Costing(selectedT29Costing);
	}

	public AnnotateDataBinder getBinder() {
		return this.binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setT29CostingMainCtrl(T29CostingMainCtrl T29CostingMainCtrl) {
		this.T29CostingMainCtrl = T29CostingMainCtrl;
	}

	public T29CostingMainCtrl getT29CostingMainCtrl() {
		return this.T29CostingMainCtrl;
	}

	public List<T30CostingDHw3ps> getList_Deleted_T30CostingD_HW3PS_List() {
		return list_Deleted_T30CostingD_HW3PS_List;
	}

	public void setList_Deleted_T30CostingD_HW3PS_List(
			List<T30CostingDHw3ps> list_Deleted_T30CostingD_HW3PS_List) {
		this.list_Deleted_T30CostingD_HW3PS_List = list_Deleted_T30CostingD_HW3PS_List;
	}

	public List<T31CostingDAcsps> getList_Deleted_T31CostingD_ACSPS_List() {
		return list_Deleted_T31CostingD_ACSPS_List;
	}

	public void setList_Deleted_T31CostingD_ACSPS_List(
			List<T31CostingDAcsps> list_Deleted_T31CostingD_ACSPS_List) {
		this.list_Deleted_T31CostingD_ACSPS_List = list_Deleted_T31CostingD_ACSPS_List;
	}

	public List<T32CostingDOwnsw> getList_Deleted_T32CostingD_OWNSW_List() {
		return list_Deleted_T32CostingD_OWNSW_List;
	}

	public void setList_Deleted_T32CostingD_OWNSW_List(
			List<T32CostingDOwnsw> list_Deleted_T32CostingD_OWNSW_List) {
		this.list_Deleted_T32CostingD_OWNSW_List = list_Deleted_T32CostingD_OWNSW_List;
	}

	public List<T33CostingDOther> getList_Deleted_T33CostingD_OTHER_List() {
		return list_Deleted_T33CostingD_OTHER_List;
	}

	public void setList_Deleted_T33CostingD_OTHER_List(
			List<T33CostingDOther> list_Deleted_T33CostingD_OTHER_List) {
		this.list_Deleted_T33CostingD_OTHER_List = list_Deleted_T33CostingD_OTHER_List;
	}

	public List<T34CostingDPayment> getList_Deleted_T34CostingD_PAYMENT_List() {
		return list_Deleted_T34CostingD_PAYMENT_List;
	}

	public void setList_Deleted_T34CostingD_PAYMENT_List(
			List<T34CostingDPayment> list_Deleted_T34CostingD_PAYMENT_List) {
		this.list_Deleted_T34CostingD_PAYMENT_List = list_Deleted_T34CostingD_PAYMENT_List;
	}
	
	
	
}
