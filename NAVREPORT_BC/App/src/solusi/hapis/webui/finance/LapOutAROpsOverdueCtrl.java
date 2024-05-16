package solusi.hapis.webui.finance;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapOutAROpsOverdueCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	protected Radio rdALL;

	protected Combobox  cmbCurrency;
	
	protected Intbox intOverdue;;
	

	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	

	private SelectQueryService selectQueryService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
			
		rdALL.setSelected(true); 
    	
    	cmbCurrency.setSelectedIndex(0);
   	

        
    	Bandpopup popup1 = new Bandpopup();
    		listCabang = new Listbox();
    		listCabang.setMold("paging");
    		listCabang.setAutopaging(true);
    		listCabang.setWidth("250px");
    		listCabang.addEventListener(Events.ON_SELECT, selectCabang());
    		listCabang.setParent(popup1);
    	popup1.setParent(cmbCab);
	        
    	listCabang.appendItem("ALL", "ALL");
    	
		List<Object[]> vResult = selectQueryService.QueryCabang();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listCabang.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
    	
    	
    	cmbCab.setValue(listCabang.getItemAtIndex(0).getLabel());
    	listCabang.setSelectedItem(listCabang.getItemAtIndex(0));

	}
	
	
	private EventListener selectCabang() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCab.setValue(listCabang.getSelectedItem().getLabel());
				vCabang = listCabang.getSelectedItem().getValue().toString();
				cmbCab.close();
			}
		};
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		String vCompany = "ALL";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		String vCurrency = "ALL";
		if (cmbCurrency.getSelectedItem().getValue() != null){
			vCurrency = (String) cmbCurrency.getSelectedItem().getValue();
		}
		
//		String vCabang = "ALL";
//		if (cmbCab.getSelectedItem().getValue() != null){
//			vCabang = (String) cmbCab.getSelectedItem().getValue();
//		}
		
		
		
		int vOverdue = 0;
		if(CommonUtils.isNotEmpty(intOverdue.getValue()) == true){
			vOverdue = intOverdue.getValue();
		}
		

		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0201004");
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02019_OutAROperationalOverdue.jasper";
		

		
		param.put("Company",  vCompany); 
		param.put("Overdue",  vOverdue); 		
		param.put("Currency",  vCurrency); 
		param.put("Cabang",  vCabang); 
		
			
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
 
}