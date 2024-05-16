package solusi.hapis.webui.sc;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class CertificateCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	protected Textbox txtCertNo;
	protected Textbox txtNoDO;
	protected Textbox txtItem1;
	protected Textbox txtItem2;
	protected Textbox txtItem3;
	
	protected Textbox txtNoPo;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdCr;
	protected Radio rdAt;
	
	protected Checkbox  chSec1;
	protected Checkbox  chSec2;
	protected Checkbox  chSec3;
	protected Checkbox  chSec4;
	protected Checkbox  chSec5;
	protected Checkbox  chSec6;
	
	
	protected  Combobox cmbYear;
	protected  Combobox cmbTAT;
	
	protected Label lblTAT;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		lblTAT.setVisible(true);
		cmbTAT.setVisible(true);
		
	   	rdAJ.setSelected(true); 
	   	rdCr.setSelected(true); 
	   	
	   	
    	chSec1.setChecked(false);
    	chSec2.setChecked(false);
    	chSec3.setChecked(false);
    	chSec4.setChecked(false);
    	chSec5.setChecked(false);
    	chSec6.setChecked(false);
    	
    	cmbYear.setSelectedIndex(2);
    	cmbTAT.setSelectedIndex(1);
    	    	
	}
	public void onSelect$cmbYear(){
		if (cmbYear.getSelectedItem().getValue() != null){
			String vPilihan = (String) cmbYear.getSelectedItem().getValue();
			
			if (vPilihan.equals("0")==true){
				lblTAT.setVisible(false);
				cmbTAT.setVisible(false);
			} else {
				lblTAT.setVisible(true);
				cmbTAT.setVisible(true);
			}
		}

	}
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException {
		String vCompany = "AJ";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
			
		String vCertNo = "";
		if (StringUtils.isNotEmpty(txtCertNo.getValue())) {
			vCertNo = txtCertNo.getValue();
		} 
		
		
		
		String vNoDO = ".";
		if (StringUtils.isNotEmpty(txtNoDO.getValue())) {
			vNoDO = txtNoDO.getValue();
		} 
		
		String vItem1 = ".";
		if (StringUtils.isNotEmpty(txtItem1.getValue())) {
			vItem1 = txtItem1.getValue();
		} 
		
		String vItem2 = ".";
		if (StringUtils.isNotEmpty(txtItem2.getValue())) {
			vItem2 = txtItem2.getValue();
		} 
		
		String vItem3 = ".";
		if (StringUtils.isNotEmpty(txtItem3.getValue())) {
			vItem3 = txtItem3.getValue();
		} 
		
		String vNoPo = "";
		if (StringUtils.isNotEmpty(txtNoPo.getValue())) {
			vNoPo = txtNoPo.getValue();
		} 
		
		String vSec1 = "N";
		if(chSec1.isChecked() == true){
			vSec1 ="Y";
		} else {
			vSec1 ="N";
		}
		
		String vSec2 = "N";
		if(chSec2.isChecked() == true){
			vSec2 ="Y";
		} else {
			vSec2 ="N";
		}
		
		String vSec3 = "N";
		if(chSec3.isChecked() == true){
			vSec3 ="Y";
		} else {
			vSec3 ="N";
		}
		
		String vSec4 = "N";
		if(chSec4.isChecked() == true){
			vSec4 ="Y";
		} else {
			vSec4 ="N";
		}
		
		String vSec5 = "N";
		if(chSec5.isChecked() == true){
			vSec5 ="Y";
		} else {
			vSec5 ="N";
		}
		
		String vSec6 = "N";
		if(chSec6.isChecked() == true){
			vSec6 ="Y";
		} else {
			vSec6 ="N";
		}
		
		
		String vYears = "1";
		if (cmbYear.getSelectedItem().getValue() != null){
			vYears = (String) cmbYear.getSelectedItem().getValue();
		}
		
		String vJenis= "CR";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
		} 
		
		String vTAT = "14";
		if (cmbTAT.getSelectedItem().getValue() != null){
			vTAT = (String) cmbTAT.getSelectedItem().getValue();
		}
		
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0401001");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/sc/Certificate_v3.jasper";
		
		
		if(vJenis.equals("CR")){			
			jasperRpt ="/solusi/hapis/webui/reports/sc/Certificate_v3.jasper";
			
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/sc/Certificate_attachment.jasper";
		}

		
		param.put("NoDo",  vNoDO); 
		
		
		param.put("Item1",  vItem1); 
		param.put("Item2",  vItem2); 
		param.put("Item3",  vItem3); 
		
		param.put("Company",  vCompany);
		
		if(vJenis.equals("CR")){
			
			param.put("CertNo",  vCertNo); 
			
			param.put("sec1",  vSec1); 
			param.put("sec2",  vSec2);
			param.put("sec3",  vSec3);
			param.put("sec4",  vSec4);
			param.put("sec5",  vSec5);
			param.put("sec6",  vSec6);	
			
			
			param.put("Period",  vYears);
			param.put("TAT",  vTAT);
			
			
			
			new JReportGeneratorWindow(param, jasperRpt, "WRD"); 
		} else {
			param.put("NoPo",  vNoPo);
			
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		}

		 
		
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOKOld(Event event) throws InterruptedException {
		String vCompany = "AJ";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
			
		String vCertNo = "";
		if (StringUtils.isNotEmpty(txtCertNo.getValue())) {
			vCertNo = txtCertNo.getValue();
		} 
		
		
		
		String vNoDO = ".";
		if (StringUtils.isNotEmpty(txtNoDO.getValue())) {
			vNoDO = txtNoDO.getValue();
		} 
		
		String vItem1 = ".";
		if (StringUtils.isNotEmpty(txtItem1.getValue())) {
			vItem1 = txtItem1.getValue();
		} 
		
		String vItem2 = ".";
		if (StringUtils.isNotEmpty(txtItem2.getValue())) {
			vItem2 = txtItem2.getValue();
		} 
		
		String vItem3 = ".";
		if (StringUtils.isNotEmpty(txtItem3.getValue())) {
			vItem3 = txtItem3.getValue();
		} 
		
		String vNoPo = "";
		if (StringUtils.isNotEmpty(txtNoPo.getValue())) {
			vNoPo = txtNoPo.getValue();
		} 
		
		String vSec1 = "N";
		if(chSec1.isChecked() == true){
			vSec1 ="Y";
		} else {
			vSec1 ="N";
		}
		
		String vSec2 = "N";
		if(chSec2.isChecked() == true){
			vSec2 ="Y";
		} else {
			vSec2 ="N";
		}
		
		String vSec3 = "N";
		if(chSec3.isChecked() == true){
			vSec3 ="Y";
		} else {
			vSec3 ="N";
		}
		
		String vSec4 = "N";
		if(chSec4.isChecked() == true){
			vSec4 ="Y";
		} else {
			vSec4 ="N";
		}
		
		String vSec5 = "N";
		if(chSec5.isChecked() == true){
			vSec5 ="Y";
		} else {
			vSec5 ="N";
		}
		
		String vSec6 = "N";
		if(chSec6.isChecked() == true){
			vSec6 ="Y";
		} else {
			vSec6 ="N";
		}
		
		
		String vYears = "1";
		if (cmbYear.getSelectedItem().getValue() != null){
			vYears = (String) cmbYear.getSelectedItem().getValue();
		}
		
		String vJenis= "CR";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenis = rdgJenis.getSelectedItem().getValue();	
		} 
		
		String vTAT = "14";
		if (cmbTAT.getSelectedItem().getValue() != null){
			vTAT = (String) cmbTAT.getSelectedItem().getValue();
		}
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0401001");
		
		String jasperRpt = "/solusi/hapis/webui/reports/sc/Certificate_v3_BAK.jasper";
		
		
		if(vJenis.equals("CR")){			
			jasperRpt ="/solusi/hapis/webui/reports/sc/Certificate_v3_BAK.jasper";
			
		} else {
			jasperRpt = "/solusi/hapis/webui/reports/sc/Certificate_attachment_BAK.jasper";
		}

		
		param.put("NoDo",  vNoDO); 
		
		
		param.put("Item1",  vItem1); 
		param.put("Item2",  vItem2); 
		param.put("Item3",  vItem3); 
		
		param.put("Company",  vCompany);
		
		if(vJenis.equals("CR")){
			
			param.put("CertNo",  vCertNo); 
			
			param.put("sec1",  vSec1); 
			param.put("sec2",  vSec2);
			param.put("sec3",  vSec3);
			param.put("sec4",  vSec4);
			param.put("sec5",  vSec5);
			param.put("sec6",  vSec6);	
			
			
			param.put("Period",  vYears);
			param.put("TAT",  vTAT);
			
			
			
			new JReportGeneratorWindow(param, jasperRpt, "WRD"); 
		} else {
			param.put("NoPo",  vNoPo);
			
			new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		}

		 
		
	}
 
}