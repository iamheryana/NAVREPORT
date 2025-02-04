package solusi.hapis.webui.accounting.preprinted;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class PrePrintedTTFManualCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Textbox txtR1InvFrom;
	protected Textbox txtR1InvUpto;
	protected Textbox txtR2InvFrom;
	protected Textbox txtR2InvUpto;
	protected Textbox txtR3InvFrom;
	protected Textbox txtR3InvUpto;
	protected Textbox txtR4InvFrom;
	protected Textbox txtR4InvUpto;
	protected Textbox txtR5InvFrom;
	protected Textbox txtR5InvUpto;
	protected Textbox txtR6InvFrom;
	protected Textbox txtR6InvUpto;
	protected Textbox txtR7InvFrom;
	protected Textbox txtR7InvUpto;
	protected Textbox txtR8InvFrom;
	protected Textbox txtR8InvUpto;
	
	protected Textbox txtPenerima;
	
	protected Radiogroup rdgRefNo;	 
	protected Radio rdYes;
	protected Radio rdNo;	
	
	
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;		

	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
    	rdPDF.setSelected(true); 
    	
    	rdYes.setSelected(true); 
    	
	}
	
	public void onClick$btnSync(Event event) throws InterruptedException, SQLException, ParseException  {
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReportManual("0108007");
		
		Messagebox.show("Sync Sudah Selesai");
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		

		
		String vR1InvFrom = ".";
		if (StringUtils.isNotEmpty(txtR1InvFrom.getValue())) {
			vR1InvFrom = txtR1InvFrom.getValue();
		} 
	
		String vR1InvUpto = vR1InvFrom;
		if (StringUtils.isNotEmpty(txtR1InvUpto.getValue())) {
			vR1InvUpto = txtR1InvUpto.getValue();
		} 
		
		String vR2InvFrom = ".";
		if (StringUtils.isNotEmpty(txtR2InvFrom.getValue())) {
			vR2InvFrom = txtR2InvFrom.getValue();
		} 
	
		String vR2InvUpto = vR2InvFrom;
		if (StringUtils.isNotEmpty(txtR2InvUpto.getValue())) {
			vR2InvUpto = txtR2InvUpto.getValue();
		}
		
		String vR3InvFrom = ".";
		if (StringUtils.isNotEmpty(txtR3InvFrom.getValue())) {
			vR3InvFrom = txtR3InvFrom.getValue();
		} 
	
		String vR3InvUpto = vR3InvFrom;
		if (StringUtils.isNotEmpty(txtR3InvUpto.getValue())) {
			vR3InvUpto = txtR3InvUpto.getValue();
		}
		
		String vR4InvFrom = ".";
		if (StringUtils.isNotEmpty(txtR4InvFrom.getValue())) {
			vR4InvFrom = txtR4InvFrom.getValue();
		} 
	
		String vR4InvUpto = vR4InvFrom;
		if (StringUtils.isNotEmpty(txtR4InvUpto.getValue())) {
			vR4InvUpto = txtR4InvUpto.getValue();
		}
		
		String vR5InvFrom = ".";
		if (StringUtils.isNotEmpty(txtR5InvFrom.getValue())) {
			vR5InvFrom = txtR5InvFrom.getValue();
		} 
	
		String vR5InvUpto = vR5InvFrom;
		if (StringUtils.isNotEmpty(txtR5InvUpto.getValue())) {
			vR5InvUpto = txtR5InvUpto.getValue();
		}
		
		String vR6InvFrom = ".";
		if (StringUtils.isNotEmpty(txtR6InvFrom.getValue())) {
			vR6InvFrom = txtR6InvFrom.getValue();
		} 
	
		String vR6InvUpto = vR6InvFrom;
		if (StringUtils.isNotEmpty(txtR6InvUpto.getValue())) {
			vR6InvUpto = txtR6InvUpto.getValue();
		}
		
		String vR7InvFrom = ".";
		if (StringUtils.isNotEmpty(txtR7InvFrom.getValue())) {
			vR7InvFrom = txtR7InvFrom.getValue();
		} 
	
		String vR7InvUpto = vR7InvFrom;
		if (StringUtils.isNotEmpty(txtR7InvUpto.getValue())) {
			vR7InvUpto = txtR7InvUpto.getValue();
		}
		
		String vR8InvFrom = ".";
		if (StringUtils.isNotEmpty(txtR8InvFrom.getValue())) {
			vR8InvFrom = txtR8InvFrom.getValue();
		} 
	
		String vR8InvUpto = vR8InvFrom;
		if (StringUtils.isNotEmpty(txtR8InvUpto.getValue())) {
			vR8InvUpto = txtR8InvUpto.getValue();
		}
		
		String vPenerima = "";
		if (StringUtils.isNotEmpty(txtPenerima.getValue())) {
			vPenerima = txtPenerima.getValue();
		}
		
		String vCetakBTB = "Y";
		if (StringUtils.isNotEmpty(rdgRefNo.getSelectedItem().getValue())) {
			vCetakBTB = rdgRefNo.getSelectedItem().getValue();	
		} 
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108007");		
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/preprinted/010807_TTF_MANUAL.jasper";
		
		param.put("CetakBTB",  vCetakBTB); 
		param.put("R1NoInvFrom",  vR1InvFrom);
		param.put("R1NoInvUpto",  vR1InvUpto);
		
		param.put("R2NoInvFrom",  vR2InvFrom);
		param.put("R2NoInvUpto",  vR2InvUpto);
		
		param.put("R3NoInvFrom",  vR3InvFrom);
		param.put("R3NoInvUpto",  vR3InvUpto);
		
		param.put("R4NoInvFrom",  vR4InvFrom);
		param.put("R4NoInvUpto",  vR4InvUpto);
		
		param.put("R5NoInvFrom",  vR5InvFrom);
		param.put("R5NoInvUpto",  vR5InvUpto);
		
		param.put("R6NoInvFrom",  vR6InvFrom);
		param.put("R6NoInvUpto",  vR6InvUpto);
		
		param.put("R7NoInvFrom",  vR7InvFrom);
		param.put("R7NoInvUpto",  vR7InvUpto);
		
		param.put("R8NoInvFrom",  vR8InvFrom);
		param.put("R8NoInvUpto",  vR8InvUpto);
		
		param.put("Penerima",  vPenerima); 
		
		
	
		
		
		String vSaveAs = "PDF";
		if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
			vSaveAs = rdgSave.getSelectedItem().getValue();	
		} 
		
		if(vSaveAs.equals("PDF")){
			new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
		} else {
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		}

		
		 
		
	}
 
}

