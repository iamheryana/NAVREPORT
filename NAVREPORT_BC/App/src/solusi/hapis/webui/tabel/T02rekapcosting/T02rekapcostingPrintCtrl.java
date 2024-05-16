package solusi.hapis.webui.tabel.T02rekapcosting;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.parameter.service.SelectQueryNavReportService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 13-03-2018
 */

public class T02rekapcostingPrintCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -6758438895356823735L;

	protected Window windowT02rekapcostingPrint;
	
	protected Borderlayout borderlayout_T02rekapcostingPrint;
	
	// Screen Parameter Components

	protected Datebox dbTglInvFrom;  
	protected Datebox dbTglInvTo;
	protected Datebox dbTglInvLunasFrom;  
	protected Datebox dbTglInvLunasTo;
	
	protected Bandbox  cmbSales;
	protected Listbox listSales;
	protected String vSales = "ALL";
	
	
	private T02rekapcostingMainCtrl T02rekapcostingMainCtrl;
	
	private SelectQueryNavReportService selectQueryNavReportService;
	
    /**
     * default constructor.<br>
     */
    public T02rekapcostingPrintCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setT02rekapcostingMainCtrl((T02rekapcostingMainCtrl) arg.get("ModuleMainController"));
        	T02rekapcostingMainCtrl.setT02rekapcostingPrintCtrl(this);
        }
    }
	
	public void onCreate$windowT02rekapcostingPrint(Event event) throws Exception {
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T02rekapcostingPrint.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT02rekapcostingPrint.invalidate();
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
			dbTglInvFrom.setValue(vTglFrom);
			dbTglInvTo.setValue(new Date());
			
			dbTglInvLunasFrom.setValue(vTglFrom);
			dbTglInvLunasTo.setValue(new Date());
		} else {
			dbTglInvFrom.setValue(vTglFrom);
			dbTglInvTo.setValue(new Date());
			
			dbTglInvLunasFrom.setValue(vTglFrom);
			dbTglInvLunasTo.setValue(new Date());
		}
		
		
		Bandpopup popup1 = new Bandpopup();
			listSales = new Listbox();
			listSales.setMold("paging");
			listSales.setAutopaging(true);
			listSales.setWidth("250px");
			listSales.addEventListener(Events.ON_SELECT, selectSales());
			listSales.setParent(popup1);
		popup1.setParent(cmbSales);
	        
		listSales.appendItem("ALL", "ALL");
		
		List<Object[]> vResult = selectQueryNavReportService.QuerySalesperson();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listSales.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbSales.setValue(listSales.getItemAtIndex(0).getLabel());
		listSales.setSelectedItem(listSales.getItemAtIndex(0));

		
		
		dbTglInvFrom.setReadonly(b);
		dbTglInvFrom.setDisabled(b);
		dbTglInvTo.setReadonly(b);
		dbTglInvTo.setDisabled(b);
		
		dbTglInvLunasFrom.setReadonly(b);
		dbTglInvLunasFrom.setDisabled(b);
		dbTglInvLunasTo.setReadonly(b);
		dbTglInvLunasTo.setDisabled(b);
		
		cmbSales.setDisabled(b);
		
	}
	
	private EventListener selectSales() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbSales.setValue(listSales.getSelectedItem().getLabel());
				vSales = listSales.getSelectedItem().getValue().toString();
				cmbSales.close();
			}
		};
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getParameterReport() throws ParseException {
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglInvFrom  = dfRFrom.parse(dRFrom);		
		Date vTglInvLunasFrom  = dfRFrom.parse(dRFrom);		
			

		if(CommonUtils.isNotEmpty(dbTglInvFrom.getValue()) == true){  
			vTglInvFrom = dbTglInvFrom.getValue();
		}   
		
		Date vTglInvTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglInvTo.getValue()) == true){  
			vTglInvTo = dbTglInvTo.getValue();
		}
		
		if(CommonUtils.isNotEmpty(dbTglInvLunasFrom.getValue()) == true){  
			vTglInvLunasFrom = dbTglInvLunasFrom.getValue();
		}   
		
		Date vTglInvLunasTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglInvLunasTo.getValue()) == true){  
			vTglInvLunasTo = dbTglInvLunasTo.getValue();
		}
		
		param.put("TglInvoiceFrom", vTglInvFrom);
		param.put("TglInvoiceUpto", vTglInvTo);
		param.put("TglLunasFrom", vTglInvLunasFrom);
		param.put("TglLunasUpto", vTglInvLunasTo);
		param.put("Sales", vSales);

		
		
		return param;
	}
    
    
	public void setT02rekapcostingMainCtrl(T02rekapcostingMainCtrl T02rekapcostingMainCtrl) {
		this.T02rekapcostingMainCtrl = T02rekapcostingMainCtrl;
	}

	public T02rekapcostingMainCtrl getT02rekapcostingMainCtrl() {
		return this.T02rekapcostingMainCtrl;
	}

}

