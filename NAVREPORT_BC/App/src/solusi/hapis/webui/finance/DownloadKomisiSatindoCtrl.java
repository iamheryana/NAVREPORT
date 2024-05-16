package solusi.hapis.webui.finance;


import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class DownloadKomisiSatindoCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	//protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
 
//	private PathReport pathRpt;
//	
//	private Tmp07invoicesatindoService tmp07invoicesatindoService;
//	private SelectQueryNavReportService selectQueryNavReportService;
	 
	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
//		Calendar cTglFrom = Calendar.getInstance();		
//		cTglFrom.setTime(new Date());
//		//int monthTglFrom = cTglFrom.get(Calendar.MONTH);
//		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
//		String dRFrom = "1/1/"+yearTglFrom;
//		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
//		Date vTglFrom  = dfRFrom.parse(dRFrom);


		//dbTglFrom.setValue(vTglFrom);
		dbTglUpto.setValue(new Date());
   
    	
	}
	
		

	public void onClick$btnOK(Event event) throws InterruptedException, ParseException, IOException {
//		String timeStamp = String.valueOf(System.currentTimeMillis());
//    	pathRpt = new PathReport(timeStamp);
    	  
    	Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int monthTglFrom = cTglFrom.get(Calendar.MONTH);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		
		if (monthTglFrom < 5){
			yearTglFrom = yearTglFrom - 1;
		}
		
		if(yearTglFrom == 2018){
			monthTglFrom = 1;
		} else {
			monthTglFrom = 1;
		}
		
		String dRFrom = "1/"+monthTglFrom+"/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
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
		String vSync = callStoreProcOrFuncService.callSyncAReport("0205001");
		
		String vResultDownload = callStoreProcOrFuncService.callDownloadInvoiceLunas(vProsesId, vStrTglFrom, vStrTglUpto, "SATINDO", SecurityContextHolder.getContext().getAuthentication().getName());
		
		ZksampleMessageUtils.showInformationMessage(vResultDownload);
		return;
		  
		//-----------------------------------------------------------------------------------------------------------------  

		
/*
//		if(dbTglFrom.getValue() != null){
//			vTglFrom = dbTglFrom.getValue();			
//		}
		
		Date vTglUpto  = new Date();	
		if(dbTglUpto.getValue() != null){
			vTglUpto = dbTglUpto.getValue();			
		}
		
		
		//Ambil data Invoice Lunas dari NAV -------------------------------------------------------------------------------
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02040_DownloadKomisiSatindo.jasper";
				
		param.put("TglLunasFrom",  vTglFrom);
		param.put("TglLunasUpto",  vTglUpto);
				
		
		File outputFile = new File(pathRpt.getInvoiceLunasNAV());
		
		@SuppressWarnings("resource")
		OutputStream outStream = new FileOutputStream(outputFile);
        outStream.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "XLS"));
		//-----------------------------------------------------------------------------------------------------------------
        // Msukan data ke NAV Report --------------------------------------------------------------------------------------
        List<Tmp07invoicesatindo> listInvoiceLunasNAV = new ArrayList<Tmp07invoicesatindo>();
        try {
  			// Membaca Excel dari file yang di Upload
        	Workbook workbook = Workbook.getWorkbook(outputFile);
  			
  			Sheet sheet = workbook.getSheet(0);

  			int vJmlData = sheet.getRows();
  			
  			for (int i = 1; i < vJmlData; i++){

  				if(i > 1) {
  				//if(i == 3 ||i == 2000) {
  				
  					//System.out.println("Invoice --> "+sheet.getCell(5,i).getContents());	
  					
  					Date vTglInv = null;
  					String dRTglInv = "01-01-2000";
  					SimpleDateFormat dfRTglInv= new SimpleDateFormat("dd-MM-yyyy");
  					
  					Date vTglLunas = null;
  					String dRTglLunas = "01-01-2000";
  					SimpleDateFormat dfRTglLunas= new SimpleDateFormat("dd-MM-yyyy");
  					
  					

  					if(	(sheet.getCell(4,i).getContents()).equals("null") == false ){
  						if(	(sheet.getCell(4,i).getContents()).equals("") == false ){
  							dRTglInv = sheet.getCell(4,i).getContents();
  	  						vTglInv  = dfRTglInv.parse(dRTglInv);
  						}
  					}
  					

  					if(	(sheet.getCell(16,i).getContents()).equals("null") == false ){
  						if(	(sheet.getCell(16,i).getContents()).equals("") == false ){
  							dRTglLunas = sheet.getCell(16,i).getContents();
  	  						vTglLunas  = dfRTglLunas.parse(dRTglLunas);  		
  						}
  					}
  					String vNilaiInvoiceStr = sheet.getCell(14,i).getContents().replaceAll(",", "").replaceAll("-", "");
  					BigDecimal vNilaiInvoice = new BigDecimal(vNilaiInvoiceStr);
  					
  					
  					String vAmountStr = sheet.getCell(21,i).getContents().replaceAll(",", "").replaceAll("-", "");
  					BigDecimal vAmount = new BigDecimal(vAmountStr);
  					
  					String vQtyStr = sheet.getCell(20,i).getContents().replaceAll(",", "").replaceAll("-", "");
  					BigDecimal vQty = new BigDecimal(vQtyStr);
  					
  			
  					Tmp07invoicesatindo anTmp07 = new Tmp07invoicesatindo(
  						sheet.getCell(1,i).getContents(), 
						sheet.getCell(2,i).getContents(),
						sheet.getCell(3,i).getContents(),											
						sheet.getCell(5,i).getContents(),
						vTglInv,	
						sheet.getCell(6,i).getContents(), 
						sheet.getCell(7,i).getContents(), 
						sheet.getCell(8,i).getContents(), 
						sheet.getCell(9,i).getContents(),
						sheet.getCell(10,i).getContents(), 
						sheet.getCell(11,i).getContents(),
						vNilaiInvoice,
						vTglLunas, 
						sheet.getCell(17,i).getContents(),
						sheet.getCell(18,i).getContents(),
						sheet.getCell(19,i).getContents(),
						vQty, 
						vAmount,
						timeStamp);
					
					listInvoiceLunasNAV.add(anTmp07);
					

  	
 				}
  				
  			
  			}
  					
  			workbook.close();	
  			
  			if(CommonUtils.isNotEmpty(listInvoiceLunasNAV)){
  				tmp07invoicesatindoService.save(listInvoiceLunasNAV);					
			}
  			
  			
  		} catch (BiffException e) {
  			Messagebox.show("Error : " + e.getMessage(), "Error",
  					Messagebox.OK, Messagebox.ERROR);

  		} catch (IOException e) {
  			Messagebox.show("Error : " + e.getMessage(), "Error",
  					Messagebox.OK, Messagebox.ERROR);

  		}
  		
        outputFile.delete();	
        //-----------------------------------------------------------------------------------------------------------------

		
        // Call Proses ----------------------------------------------------------------------------------------------------
		String vResultDownload = selectQueryNavReportService.callDownloadInvoiceLunasSatindo(timeStamp);
		
		ZksampleMessageUtils.showInformationMessage(vResultDownload);
		return;
         
       //-----------------------------------------------------------------------------------------------------------------  
  
*/		
	
		
	}
 
}