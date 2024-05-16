package solusi.hapis.webui.sales.T21DaftarPenawaranSales;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 17-11-2021
 */

public class T21DaftarPenawaranSalesPrintCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -6758438895356823735L;

	protected Window windowT21DaftarPenawaranSalesPrint;
	
	protected Borderlayout borderlayout_T21DaftarPenawaranSalesPrint;
	
	// Screen Parameter Components
	
	protected Combobox cmbCompany;
//	protected Radiogroup rdgCompany;	 
//	protected Radio rdSP;
//	protected Radio rdAJ;
//	protected Radio rdALL;	
	
	protected Combobox cmbCabang;
//	protected Radiogroup rdgCabang;	 
//	protected Radio rdCabangALL;
//	protected Radio rdCabangJKT;
//	protected Radio rdCabangCKR;
//	protected Radio rdCabangSMR;
//	protected Radio rdCabangSBY;
//	protected Radio rdCabangDPS;

	protected Textbox txtSales;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglUpto;
	
	protected Textbox txtNoFrom;
	protected Textbox txtNoUpto;
	
	protected Combobox cmbStatus;
	
//	protected Radiogroup rdgStatus;	 
//	protected Radio rdStatusALL;
//	protected Radio rdStatus0;
//	protected Radio rdStatus1;
//	protected Radio rdStatus2;
	

	protected Bandbox  cmbSektorIndustri;
	protected Listbox listSektorIndustri;
	protected String vSektorIndustri = "ALL";
	
	private SelectQueryService selectQueryService;
		
		
	@SuppressWarnings("unused")
	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	
	private T21DaftarPenawaranSalesMainCtrl T21DaftarPenawaranSalesMainCtrl;
	
    /**
     * default constructor.<br>
     */
    public T21DaftarPenawaranSalesPrintCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setT21DaftarPenawaranSalesMainCtrl((T21DaftarPenawaranSalesMainCtrl) arg.get("ModuleMainController"));
        	T21DaftarPenawaranSalesMainCtrl.setT21DaftarPenawaranSalesPrintCtrl(this);
        }
        
        
		Bandpopup popup2 = new Bandpopup();
			listSektorIndustri = new Listbox();
			listSektorIndustri.setMold("paging");
			listSektorIndustri.setAutopaging(false);
			listSektorIndustri.setWidth("400px");
			listSektorIndustri.addEventListener(Events.ON_SELECT, selectSektorIndustri());
			listSektorIndustri.setParent(popup2);
		popup2.setParent(cmbSektorIndustri);
	        
		listSektorIndustri.appendItem("All", "ALL");
		
		List<Object[]> vResultSektorIndustri = selectQueryService.QuerySektorIndustri();
		if(CommonUtils.isNotEmpty(vResultSektorIndustri)){
			for(Object[] aRslt : vResultSektorIndustri){
				listSektorIndustri.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbSektorIndustri.setValue(listSektorIndustri.getItemAtIndex(0).getLabel());
		listSektorIndustri.setSelectedItem(listSektorIndustri.getItemAtIndex(0));
	}    
	
	private EventListener selectSektorIndustri() {
		return new EventListener() {
	
			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbSektorIndustri.setValue(listSektorIndustri.getSelectedItem().getLabel());
				vSektorIndustri = listSektorIndustri.getSelectedItem().getValue().toString();
				cmbSektorIndustri.close();
			}
		};
	}
	
	public void onCreate$windowT21DaftarPenawaranSalesPrint(Event event) throws Exception {
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T21DaftarPenawaranSalesPrint.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT21DaftarPenawaranSalesPrint.invalidate();
	}
	
	public void doReadOnlyMode(boolean b) throws ParseException {
		// Set Default		

 
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		if (b == false) {
			dbTglFrom.setValue(vTglFrom);
			dbTglUpto.setValue(new Date());
		} else {
			dbTglFrom.setValue(vTglFrom);
			dbTglUpto.setValue(new Date());
		}
		
		
		dbTglFrom.setReadonly(b);
		dbTglFrom.setDisabled(b);
		dbTglUpto.setReadonly(b);
		dbTglUpto.setDisabled(b);
		
//		rdALL.setSelected(true);
//		rdCabangALL.setSelected(true);
//		rdStatusALL.setSelected(true);
		
//		rdALL.setDisabled(b);
//		rdAJ.setDisabled(b);
//		rdSP.setDisabled(b);
//		rdCabangALL.setDisabled(b);
//		rdCabangJKT.setDisabled(b);
//		rdCabangCKR.setDisabled(b);
//		rdCabangSMR.setDisabled(b);
//		rdCabangSBY.setDisabled(b);
//		rdCabangDPS.setDisabled(b);
//		rdStatusALL.setDisabled(b);
//		rdStatus0.setDisabled(b);
//		rdStatus1.setDisabled(b);
//		rdStatus2.setDisabled(b);
		
		cmbCompany.setDisabled(b);
		
		cmbCabang.setDisabled(b);
		
		cmbStatus.setDisabled(b);
		
		txtSales.setValue("ALL");
		txtSales.setReadonly(b);
		txtSales.setDisabled(b);
		
		txtNoFrom.setValue(".");
		txtNoFrom.setReadonly(b);
		txtNoFrom.setDisabled(b);
		
		
		txtNoUpto.setValue("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
		txtNoUpto.setReadonly(b);
		txtNoUpto.setDisabled(b);
		
		cmbSektorIndustri.setDisabled(b);
	}
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getParameterReport() throws ParseException {
		
		
		
		String vCompany = "ALL";
		if (cmbCompany.getSelectedIndex() != 0) {
			vCompany= (String) cmbCompany.getSelectedItem().getValue();
		}
		
		String vCabang = "ALL";
		if (cmbCabang.getSelectedIndex() != 0) {
			vCabang= (String) cmbCabang.getSelectedItem().getValue();
		}
		
		
		String vSales = "ALL";
		if (StringUtils.isNotEmpty(txtSales.getValue())) {
			vSales = txtSales.getValue();	
		} 
		
		String vNoFrom = ".";
		if (StringUtils.isNotEmpty(txtNoFrom.getValue())) {
			vNoFrom = txtNoFrom.getValue();	
		} 
		
		String vNoUpto = "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtNoUpto.getValue())) {
			vNoUpto = txtNoUpto.getValue();	
		} 
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);		
			

		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		Date vTglTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglTo = dbTglUpto.getValue();
		}
		
		
		String vStatus= "ALL";
		if (cmbStatus.getSelectedIndex() != 0) {
			vStatus= (String) cmbStatus.getSelectedItem().getValue();
		}
		
		
		param.put("Company", vCompany);
		param.put("Cabang", vCabang);
		param.put("Sales", vSales);
		param.put("TglFrom", vTglFrom);
		param.put("TglUpto", vTglTo);
		param.put("NoFrom", vNoFrom);
		param.put("NoUpto", vNoUpto);
		param.put("SektorIndustri", vSektorIndustri);
		param.put("Status", vStatus);
		
		
		return param;
	}
    
    
	public void setT21DaftarPenawaranSalesMainCtrl(T21DaftarPenawaranSalesMainCtrl T21DaftarPenawaranSalesMainCtrl) {
		this.T21DaftarPenawaranSalesMainCtrl = T21DaftarPenawaranSalesMainCtrl;
	}

	public T21DaftarPenawaranSalesMainCtrl getT21DaftarPenawaranSalesMainCtrl() {
		return this.T21DaftarPenawaranSalesMainCtrl;
	}

}

