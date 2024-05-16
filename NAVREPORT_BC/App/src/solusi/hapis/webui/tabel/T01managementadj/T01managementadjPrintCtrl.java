package solusi.hapis.webui.tabel.T01managementadj;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Window;

import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

/**
 * User: Heryana
 * Date: 07-09-2016
 */

public class T01managementadjPrintCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -6758438895356823735L;

	protected Window windowT01managementadjPrint;
	
	protected Borderlayout borderlayout_T01managementadjPrint;
	
	// Screen Parameter Components

	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	
	@SuppressWarnings("unused")
	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	
	private T01managementadjMainCtrl T01managementadjMainCtrl;
	
    /**
     * default constructor.<br>
     */
    public T01managementadjPrintCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setT01managementadjMainCtrl((T01managementadjMainCtrl) arg.get("ModuleMainController"));
        	T01managementadjMainCtrl.setT01managementadjPrintCtrl(this);
        }
    }
	
	public void onCreate$windowT01managementadjPrint(Event event) throws Exception {
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T01managementadjPrint.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT01managementadjPrint.invalidate();
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
			dbTglTo.setValue(new Date());
		} else {
			dbTglFrom.setValue(vTglFrom);
			dbTglTo.setValue(new Date());
		}
		
		
		dbTglFrom.setReadonly(b);
		dbTglFrom.setDisabled(b);
		dbTglTo.setReadonly(b);
		dbTglTo.setDisabled(b);
		
		
		
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getParameterReport() throws ParseException {
		
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
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
		}
		
		param.put("l_tglfr", vTglFrom);
		param.put("l_tglto", vTglTo);
		
		
		return param;
	}
    
    
	public void setT01managementadjMainCtrl(T01managementadjMainCtrl T01managementadjMainCtrl) {
		this.T01managementadjMainCtrl = T01managementadjMainCtrl;
	}

	public T01managementadjMainCtrl getT01managementadjMainCtrl() {
		return this.T01managementadjMainCtrl;
	}

}

