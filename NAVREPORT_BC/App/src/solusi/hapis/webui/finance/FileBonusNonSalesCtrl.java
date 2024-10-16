package solusi.hapis.webui.finance;



import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class FileBonusNonSalesCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Radiogroup rdgJenis;	 
	protected Radio rdSUM;
	protected Radio rdDTL;
 
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
//	private PathReport pathRpt;
//	
//	private Tmp07invoicesatindoService tmp07invoicesatindoService;
//	private SelectQueryNavReportService selectQueryNavReportService;
	 
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		//int monthTglFrom = cTglFrom.get(Calendar.MONTH);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);


		dbTglFrom.setValue(vTglFrom);
		dbTglUpto.setValue(new Date());
   
		rdSUM.setSelected(true);
    	
	}
	
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException, IOException {
//		String timeStamp = String.valueOf(System.currentTimeMillis());
//    	pathRpt = new PathReport(timeStamp);
//    	  
    	Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		//int monthTglFrom = cTglFrom.get(Calendar.MONTH);
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
		
		String vJenisLap = "SUM";
		if (StringUtils.isNotEmpty(rdgJenis.getSelectedItem().getValue())) {
			vJenisLap = rdgJenis.getSelectedItem().getValue();	
		} 
		
//		
//		//Ambil data Invoice Lunas dari NAV -------------------------------------------------------------------------------
//		String jasperRpt = "/solusi/hapis/webui/reports/finance/02042_DownloadBonusNSSatindo.jasper";
//				
//		param.put("TglFrom",  vTglFrom);
//		param.put("TglUpto",  vTglUpto);
//				
//		
//		File outputFile = new File(pathRpt.getInvoiceLunasNAV());
//		
//		@SuppressWarnings("resource")
//		OutputStream outStream = new FileOutputStream(outputFile);
//        outStream.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "XLS"));
//		//-----------------------------------------------------------------------------------------------------------------
//        // Msukan data ke NAV Report --------------------------------------------------------------------------------------
//        List<Tmp07invoicesatindo> listInvoiceLunasNAV = new ArrayList<Tmp07invoicesatindo>();
//        try {
//  			// Membaca Excel dari file yang di Upload
//        	Workbook workbook = Workbook.getWorkbook(outputFile);
//  			
//  			Sheet sheet = workbook.getSheet(0);
//
//  			int vJmlData = sheet.getRows();
//  			
//  			for (int i = 1; i < vJmlData; i++){
//
//  				if(i > 1) {
//  				//if(i == 3 ||i == 2000) {
//  					
//  							
//  					Date vTglOrder = null;
//  					String dRTglOrder = "01-01-2000";
//  					SimpleDateFormat dfRTglOrder= new SimpleDateFormat("dd-MM-yyyy");
//  					
//  				
//  					
//
//  					if(	(sheet.getCell(2,i).getContents()).equals("null") == false ){
//  						if(	(sheet.getCell(2,i).getContents()).equals("") == false ){
//  							dRTglOrder = sheet.getCell(2,i).getContents();
//  	  						vTglOrder  = dfRTglOrder.parse(dRTglOrder);
//  						}
//  					}
//  					
//  					
//  					String vQtyStr = sheet.getCell(4,i).getContents().replaceAll(",", "").replaceAll("-", "");
//  					BigDecimal vQty = new BigDecimal(vQtyStr);
//  					
//  			
//  					Tmp07invoicesatindo anTmp07 = new Tmp07invoicesatindo(
//  						"AUTOJAYA",
//  						sheet.getCell(1,i).getContents(), 
//  						vTglOrder,	
//						sheet.getCell(3,i).getContents(), 
//						vQty,
//						timeStamp);
//					
//					listInvoiceLunasNAV.add(anTmp07);
//					
//
//  	
// 				}
//  				
//  			
//  			}
//  					
//  			workbook.close();	
//  			
//  			if(CommonUtils.isNotEmpty(listInvoiceLunasNAV)){
//  				tmp07invoicesatindoService.save(listInvoiceLunasNAV);					
//			}
//  			
//  			
//  		} catch (BiffException e) {
//  			Messagebox.show("Error : " + e.getMessage(), "Error",
//  					Messagebox.OK, Messagebox.ERROR);
//
//  		} catch (IOException e) {
//  			Messagebox.show("Error : " + e.getMessage(), "Error",
//  					Messagebox.OK, Messagebox.ERROR);
//
//  		}
//  		
//        outputFile.delete();	
        
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0205002");
        
        String jasperRptFinal = "/solusi/hapis/webui/reports/finance/02043_FileBonusNonSalesSatindo_New.jasper";
		
        param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpto);
		param.put("JenisLap",  vJenisLap);  
	
		
		
		new JReportGeneratorWindow(param, jasperRptFinal, "XLS"); 
		
//        //-----------------------------------------------------------------------------------------------------------------
//
//		
//        // Call Proses ----------------------------------------------------------------------------------------------------
//		selectQueryNavReportService.callDeleteInvoiceSatindoTemp(timeStamp);
//         
//       //-----------------------------------------------------------------------------------------------------------------  
//  
//		
	
		
	}
 
}