package solusi.hapis.webui.general;



import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class OutPOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Combobox  cmbGrpOwner;
	
	
	protected Radiogroup rdgJnsLap;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
	
	protected Radiogroup rdgJnsStatus;	 
	protected Radio rdS1;
	protected Radio rdS2;
	protected Radio rdS3;

	protected Decimalbox dcmAge;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		

    	cmbGrpOwner.setSelectedIndex(0);
    	rdDTL.setSelected(true); 
    	rdS1.setSelected(true); 
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		String vJnsLap = "DTL";
		if (StringUtils.isNotEmpty(rdgJnsLap.getSelectedItem().getValue())) {
			vJnsLap = rdgJnsLap.getSelectedItem().getValue();	
		} 
		
		String vGrpOwner = "ALL";
		if (cmbGrpOwner.getSelectedItem().getValue() != null){
			vGrpOwner = (String) cmbGrpOwner.getSelectedItem().getValue();
		}
		
		String vStatus = "ALL";
		if (StringUtils.isNotEmpty(rdgJnsStatus.getSelectedItem().getValue())) {
			vStatus = rdgJnsStatus.getSelectedItem().getValue();	
		} 
		
		BigDecimal vAgedPO = new BigDecimal(-1000);
		if (CommonUtils.isNotEmpty(dcmAge.getValue())) {
			vAgedPO = dcmAge.getValue();
		} 
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0801001");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/general/08001_OutPO.jasper";

		param.put("OwnerPosibility",  vGrpOwner); 
		param.put("jnsRpt",  vJnsLap); 
		param.put("Status",  vStatus); 
		param.put("AgedPO",  vAgedPO); 
		
		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 

		 
		
	}
 
}