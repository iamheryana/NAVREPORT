package solusi.hapis.webui.accounting;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.model.Temp05EfakturRegister;
import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.navbi.service.Temp05EfakturRegisterService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ProsesRekonsiliasiPPNCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	private Temp05EfakturRegisterService temp05EfakturRegisterService;
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	

	List<Temp05EfakturRegister> listEfaktur = new ArrayList<Temp05EfakturRegister>();
	
	private String timeStamp;
	
	protected Textbox lbl1;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	protected Radiogroup rdgJnsPPN;	 
	protected Radio rdKeluaran;
	protected Radio rdMasukan;
	
	

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		timeStamp = String.valueOf(System.currentTimeMillis());
    	
		dbTglTo.setValue((new Date()));   
    	
    	rdAJ.setSelected(true); 
    	
    	rdKeluaran.setSelected(true); 
	}
	
	public void onClick$btnOK1(Event event) throws InterruptedException, ParseException {
		String vJenisPPN = "SALES";
		if (StringUtils.isNotEmpty(rdgJnsPPN.getSelectedItem().getValue())) {
			vJenisPPN = rdgJnsPPN.getSelectedItem().getValue();	
		} 
		
		
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();


				if(CommonUtils.isNotEmpty(listEfaktur)){
					temp05EfakturRegisterService.save(listEfaktur);		
					listEfaktur.clear();
				}

				
				if (vJenisPPN.equals("SALES") == true){
					for (int i = 1; i < vJmlData; i++){
						if(i > 0){
							if(		CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(7,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(8,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(10,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(14,i).getContents())){
								if (CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents())){
									String TempNoFP = sheet.getCell(2,i).getContents().substring(4,sheet.getCell(2,i).getContents().length());
									String efakturNoFP =  CommonUtils.convertNoFakturPajak(TempNoFP);
									Temp05EfakturRegister anData = new Temp05EfakturRegister(
																	sheet.getCell(1,i).getContents(), 
																	efakturNoFP,
																	sheet.getCell(2,i).getContents(),
																	sheet.getCell(3,i).getContents(),
																	sheet.getCell(4,i).getContents(),
																	sheet.getCell(5,i).getContents(),
																	sheet.getCell(6,i).getContents(), 
																	sheet.getCell(7,i).getContents(),
																	sheet.getCell(8,i).getContents(),
																	sheet.getCell(14,i).getContents(),
																	sheet.getCell(10,i).getContents(),
																	"1",
																	vJenisPPN,
																	timeStamp);
									listEfaktur.add(anData);
								}
							}
						
						}					
					}
				} else {
					for (int i = 1; i < vJmlData; i++){
						if(i > 0){
							if(		CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(4,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(5,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(7,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(8,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(9,i).getContents()) ||
									CommonUtils.isNotEmpty(sheet.getCell(11,i).getContents())){
								if (CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents())){
									String TempNoFP = sheet.getCell(2,i).getContents();
									String efakturNoFP =  CommonUtils.convertNoFakturPajak(TempNoFP);
									Temp05EfakturRegister anData = new Temp05EfakturRegister(
																	sheet.getCell(1,i).getContents(), 
																	efakturNoFP,
																	sheet.getCell(2,i).getContents(),
																	sheet.getCell(3,i).getContents(),
																	sheet.getCell(4,i).getContents(),
																	sheet.getCell(5,i).getContents(),
																	sheet.getCell(6,i).getContents(), 
																	sheet.getCell(8,i).getContents(),
																	sheet.getCell(9,i).getContents(),
																	"",
																	sheet.getCell(11,i).getContents(),
																	sheet.getCell(7,i).getContents(),
																	vJenisPPN,
																	timeStamp);
									listEfaktur.add(anData);
								}
							}
						
						}					
					}
				}
				workbook.close();
				
				if(CommonUtils.isNotEmpty(listEfaktur)){
					temp05EfakturRegisterService.save(listEfaktur);					
				}
				

				lbl1.setValue(media.getName()+ " Sudah berhasil terupload.");


			} catch (BiffException e) {
				Messagebox.show("Not an Excel File : " + media.getName(),
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}
			

		}
	
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException, IOException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date vTanggal = null;
			try {
				vTanggal = formatter.parse("1900-01-01");			
			} catch (ParseException e) {
				e.printStackTrace();
			}			
			
		Date vTglFrom = vTanggal;   
		String vStrTglFrom =  "1900-01-01";
		
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
			
			vStrTglFrom  = formatter.format(vTglFrom);  
		}   
		
				
		Date vTglTo = vTanggal;   
		String vStrTglUpto =  "1900-01-01";
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
			
			vStrTglUpto  = formatter.format(vTglTo);  
		}
				

		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		String vJenisPPN = "SALES";
		if (StringUtils.isNotEmpty(rdgJnsPPN.getSelectedItem().getValue())) {
			vJenisPPN = rdgJnsPPN.getSelectedItem().getValue();	
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0105001");
		
		String vResult = callStoreProcOrFuncService.callEFakturRekon(timeStamp, vCompany, vJenisPPN, vStrTglFrom, vStrTglUpto, SecurityContextHolder.getContext().getAuthentication().getName(), "INSERT");
		
		if (vResult.equals("UPLOAD DATA EFAKTUR BERHASIL") == true){
			
			String jasperRptFinal = "/solusi/hapis/webui/reports/accounting/01066_HasilRekonsilisasiPPN.jasper";
			
			param.put("TglInvFrom",  vTglFrom);	
			param.put("TglInvTo",  vTglTo);	
			param.put("Company",  vCompany);	
			param.put("Tipe",  vJenisPPN);	
			param.put("ProsesId",  timeStamp);	

			new JReportGeneratorWindow(param, jasperRptFinal, "XLS"); 
		}
		
		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callEFakturRekon(timeStamp, vCompany, vJenisPPN, vStrTglFrom, vStrTglUpto, SecurityContextHolder.getContext().getAuthentication().getName(), "DELETE");
//		
//		try {
//			String vResult = callStoreProcOrFuncService.callEFakturRekon(timeStamp, vCompany, vJenisPPN, vStrTglFrom, vStrTglUpto, SecurityContextHolder.getContext().getAuthentication().getName());
//			Messagebox.show(vResult, "Message",
//					Messagebox.OK, Messagebox.INFORMATION);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		String jasperRptFinal = "/solusi/hapis/webui/reports/accounting/01039_HasilPPNRekonsiliasi.jasper";
//		param.put("prosesId",  timeStamp);	
//		
//		
//		new JReportGeneratorWindow(param, jasperRptFinal, "XLS", 1); 
		
		

//        if(CommonUtils.isNotEmpty(listEfaktur)){
//			tmp04efakturService.delete(listEfaktur);					
//		}
//        
//        if(CommonUtils.isNotEmpty(listInvoiceNAV)){	
//			tmp05navfakturService.delete(listInvoiceNAV);					
//		}
        
        
		   
	}
}