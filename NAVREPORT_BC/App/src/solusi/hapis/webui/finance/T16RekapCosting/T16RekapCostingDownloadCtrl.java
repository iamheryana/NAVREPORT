package solusi.hapis.webui.finance.T16RekapCosting;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 19-04-2021
 */

public class T16RekapCostingDownloadCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -6758438895356823735L;

	protected Window windowT16RekapCostingDownload;
	
	protected Borderlayout borderlayout_T16RekapCostingDownload;
	
	// Screen Parameter Components

	protected Datebox dbTglFrom;  
	protected Datebox dbTglUpto;

	
	@SuppressWarnings("unused")
	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	
	private T16RekapCostingMainCtrl T16RekapCostingMainCtrl;
	
	
    private CallStoreProcOrFuncService callStoreProcOrFuncService;

    
    /**
     * default constructor.<br>
     */
    public T16RekapCostingDownloadCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setT16RekapCostingMainCtrl((T16RekapCostingMainCtrl) arg.get("ModuleMainController"));
        	T16RekapCostingMainCtrl.setT16RekapCostingDownloadCtrl(this);
        }
        
        
        
        Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		//int monthTglFrom = cTglFrom.get(Calendar.MONTH);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		

		dbTglFrom.setValue(vTglFrom);
		dbTglUpto.setValue(new Date());
		
		
    }
	
	public void onCreate$windowT16RekapCostingDownload(Event event) throws Exception {
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T16RekapCostingDownload.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT16RekapCostingDownload.invalidate();
	}
	

	public void onClick$btnDownload(Event event) throws InterruptedException, ParseException, IOException {
    	  
    	Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		@SuppressWarnings("unused")
		int monthTglFrom = cTglFrom.get(Calendar.MONTH);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		if(dbTglFrom.getValue() != null){
			vTglFrom = dbTglFrom.getValue();			
		}
		
		Date vTglUpto  = new Date();	
		if(dbTglUpto.getValue() != null){
			vTglUpto = dbTglUpto.getValue();			
		}
		
		
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  
		String vStrTglUpto  = frmTgl.format(vTglUpto); 
		
		
		// Call Proses ----------------------------------------------------------------------------------------------------
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0204001");
		
		String vResultDownload = callStoreProcOrFuncService.callDownloadInvoiceLunas(vProsesId, vStrTglFrom, vStrTglUpto, "NORMAL", SecurityContextHolder.getContext().getAuthentication().getName());
		
		ZksampleMessageUtils.showInformationMessage(vResultDownload);
		return;
		  
		//-----------------------------------------------------------------------------------------------------------------  
		   

	}
	
	public void setT16RekapCostingMainCtrl(T16RekapCostingMainCtrl T16RekapCostingMainCtrl) {
		this.T16RekapCostingMainCtrl = T16RekapCostingMainCtrl;
	}

	public T16RekapCostingMainCtrl getT16RekapCostingMainCtrl() {
		return this.T16RekapCostingMainCtrl;
	}

}

