package solusi.hapis.webui.sales.Costing.T29Costing;


import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 23-12-2024
 */

public class T29CostingProsesFinalCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -6758438895356823735L;

	protected Window windowT29CostingProsesFinal;
	
	protected Borderlayout borderlayout_T29CostingProsesFinal;
	
	// Screen Parameter Components

	protected Textbox txtTahun;
	protected Combobox cmbPeriode;

	
	private T29CostingMainCtrl T29CostingMainCtrl;
	
	
    private CallStoreProcOrFuncService callStoreProcOrFuncService;

    
    /**
     * default constructor.<br>
     */
    public T29CostingProsesFinalCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setT29CostingMainCtrl((T29CostingMainCtrl) arg.get("ModuleMainController"));
        	T29CostingMainCtrl.setT29CostingProsesFinalCtrl(this);
        	

        }
        
        
        
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglFrom = cTgl.get(Calendar.YEAR);
		int monthTglCurr = cTgl.get(Calendar.MONTH);
		monthTglCurr = monthTglCurr+1;
		
		txtTahun.setValue(String.valueOf(yearTglFrom));
		
		cmbPeriode.setSelectedIndex(monthTglCurr-1);
		
		
    }
	
	public void onCreate$windowT29CostingProsesFinal(Event event) throws Exception {
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T29CostingProsesFinal.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT29CostingProsesFinal.invalidate();
	}
	

	public void onClick$btnProses(Event event) throws InterruptedException, ParseException, IOException {
    	  
		Calendar cTgl = Calendar.getInstance();		
		cTgl.setTime(new Date());
		int yearTglFrom = cTgl.get(Calendar.YEAR);
		int monthTglCurr = cTgl.get(Calendar.MONTH);
		
		monthTglCurr = monthTglCurr+1;
		
		String vTahun = String.valueOf(yearTglFrom);				
		if(CommonUtils.isNotEmpty(txtTahun.getValue()) == true){  
			vTahun = txtTahun.getValue();
		}
		
		String vMasa = String.valueOf(monthTglCurr);	
		if (cmbPeriode.getSelectedIndex() != -1) {
			vMasa =  (String) cmbPeriode.getSelectedItem().getValue();
		}
		
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
        // Call Proses ----------------------------------------------------------------------------------------------------
		String vResultDownload = callStoreProcOrFuncService.callProsesCostingFinance2(vProsesId, vMasa, vTahun);
		
		ZksampleMessageUtils.showInformationMessage(vResultDownload);
		return;
	}

	public T29CostingMainCtrl getT29CostingMainCtrl() {
		return T29CostingMainCtrl;
	}

	public void setT29CostingMainCtrl(T29CostingMainCtrl t29CostingMainCtrl) {
		T29CostingMainCtrl = t29CostingMainCtrl;
	}
	


}

