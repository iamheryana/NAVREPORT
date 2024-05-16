package solusi.hapis.webui.sales;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;

public class CekStatusKomisiCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtBSO;	
	protected Textbox txtInvoice;
	protected Textbox txtExtDocNo;
	protected Textbox txtCustomer;
	
	
	
	protected Radiogroup rdgSave;	 
	protected Radio rdPDF;
	protected Radio rdXLS;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	private String vProsesId;
	
//	private PathReport pathRpt;
//	private Tmp06invoicelunasService tmp06invoicelunasService;
//	private SelectQueryNavReportService selectQueryNavReportService;
//	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		 
     	    	
    	

    	rdPDF.setSelected(true); 
    	
	}
	

	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, IOException, ParseException {
		if(StringUtils.isNotEmpty(txtBSO.getValue()) == false &&
				StringUtils.isNotEmpty(txtInvoice.getValue()) == false &&
				StringUtils.isNotEmpty(txtExtDocNo.getValue()) == false &&
				StringUtils.isNotEmpty(txtCustomer.getValue()) == false 				
				){
			ZksampleMessageUtils.showErrorMessage("Silahkan masukan salah satu kriteria pencarian !!!");

		} else {

//			String timeStamp = String.valueOf(System.currentTimeMillis());
//	    	pathRpt = new PathReport(timeStamp);
		
			String vNoBSO = "ALL";
			if (StringUtils.isNotEmpty(txtBSO.getValue())) {
				vNoBSO = txtBSO.getValue();
			} 
			
			String vInvoice = "ALL";
			if (StringUtils.isNotEmpty(txtInvoice.getValue())) {
				vInvoice = txtInvoice.getValue();
			} 
			
			String vExtDocNo = "ALL";
			if (StringUtils.isNotEmpty(txtExtDocNo.getValue())) {
				vExtDocNo = txtExtDocNo.getValue();
			} 
			
			String vCustomer = "ALL";
			if (StringUtils.isNotEmpty(txtCustomer.getValue())) {
				vCustomer = txtCustomer.getValue();
			} 
			
	//		System.out.println("vSO : "+vSO);
	//		System.out.println("vInvoice : "+vInvoice);
	//		System.out.println("vExtDocNo : "+vExtDocNo);
	//		System.out.println("vCustomer : "+vCustomer);
			
			
//			//Ambil data Invoice Lunas dari NAV -------------------------------------------------------------------------------
//			String jasperRpt = "/solusi/hapis/webui/reports/sales/04033_CekStatusKomisiAwal.jasper";
//					
//			param.put("NoBSO",  vNoBSO.toUpperCase());
//			param.put("NoInvoice",  vInvoice.toUpperCase());
//			param.put("ExtDocNo",  vExtDocNo.toUpperCase());
//			param.put("Customer",  vCustomer.toUpperCase());
//			
//			File outputFile = new File(pathRpt.getInvoiceLunasNAV());
//			
//	
//			@SuppressWarnings("resource")
//			OutputStream outStream = new FileOutputStream(outputFile);
//	        outStream.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "XLS"));
//			//-----------------------------------------------------------------------------------------------------------------
//	        // Msukan data ke NAV Report --------------------------------------------------------------------------------------
//	        List<Tmp06invoicelunas> listInvoiceLunasNAV = new ArrayList<Tmp06invoicelunas>();
//	        try {
//	  			// Membaca Excel dari file yang di Upload
//	        	Workbook workbook = Workbook.getWorkbook(outputFile);
//	  			
//	  			Sheet sheet = workbook.getSheet(0);
//	
//	  			int vJmlData = sheet.getRows();
//	  			
//	  			for (int i = 1; i < vJmlData; i++){
//	
//	  				if(i > 2) {
//	  				//if(i == 3 ||i == 2000) {
//	  					
//	  					
//	  					Date vTglSO = null;
//	  					String dRTglSO = "01-01-2000";
//	  					SimpleDateFormat dfRTglSO= new SimpleDateFormat("dd-MM-yyyy");
//	  					
//	  					Date vTglInv = null;
//	  					String dRTglInv = "01-01-2000";
//	  					SimpleDateFormat dfRTglInv= new SimpleDateFormat("dd-MM-yyyy");
//	  					
//	  					Date vTglLunas = null;
//	  					String dRTglLunas = "01-01-2000";
//	  					SimpleDateFormat dfRTglLunas= new SimpleDateFormat("dd-MM-yyyy");
//	  					
//	  					
//	  					if(	(sheet.getCell(7,i).getContents()).equals("null") == false ){
//	  						if(	(sheet.getCell(7,i).getContents()).equals("") == false ){
//	  							dRTglSO = sheet.getCell(7,i).getContents();
//	  	  						vTglSO  = dfRTglSO.parse(dRTglSO);
//	  						}
//	  					}
//	  					
//	
//	  					if(	(sheet.getCell(12,i).getContents()).equals("null") == false ){
//	  						if(	(sheet.getCell(12,i).getContents()).equals("") == false ){
//	  							dRTglInv = sheet.getCell(12,i).getContents();
//	  	  						vTglInv  = dfRTglInv.parse(dRTglInv);
//	  						}
//	  					}
//	  					
//	
//	  					if(	(sheet.getCell(13,i).getContents()).equals("null") == false ){
//	  						if(	(sheet.getCell(13,i).getContents()).equals("") == false ){
//	  							dRTglLunas = sheet.getCell(13,i).getContents();
//	  	  						vTglLunas  = dfRTglLunas.parse(dRTglLunas);  		
//	  						}
//	  					}
//	  					
//	  					String vAmountStr = sheet.getCell(10,i).getContents().replaceAll(",", "").replaceAll("-", "");
//	  					BigDecimal vAmount = new BigDecimal(vAmountStr);
//	  					
//	  			
//						Tmp06invoicelunas anTmp06 = new Tmp06invoicelunas(sheet.getCell(1,i).getContents(), 
//							sheet.getCell(2,i).getContents(),
//							sheet.getCell(3,i).getContents(),
//							sheet.getCell(4,i).getContents(),
//							sheet.getCell(5,i).getContents(),
//							sheet.getCell(6,i).getContents(), 
//							vTglSO, 
//							sheet.getCell(8,i).getContents(), 
//							sheet.getCell(9,i).getContents(),
//							vAmount, 
//							sheet.getCell(11,i).getContents(), 
//							vTglInv, 
//							vTglLunas, 
//							timeStamp);
//						
//						listInvoiceLunasNAV.add(anTmp06);
//	  	
//	 				}
//	  				
//	  			
//	  			}
//	  					
//	  			workbook.close();	
//	  			
//	  			if(CommonUtils.isNotEmpty(listInvoiceLunasNAV)){
//					tmp06invoicelunasService.save(listInvoiceLunasNAV);					
//				}
//	  			
//	  			
//	  		} catch (BiffException e) {
//	  			Messagebox.show("Error : " + e.getMessage(), "Error",
//	  					Messagebox.OK, Messagebox.ERROR);
//	
//	  		} catch (IOException e) {
//	  			Messagebox.show("Error : " + e.getMessage(), "Error",
//	  					Messagebox.OK, Messagebox.ERROR);
//	
//	  		}
//	  		
//	        outputFile.delete();	
//	        //-----------------------------------------------------------------------------------------------------------------
//			
			vProsesId = String.valueOf(System.currentTimeMillis());
			
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0502001");
			
			
			@SuppressWarnings("unused")
			String vResult = callStoreProcOrFuncService.callCekStatusKomisi(vProsesId, 
								
								SecurityContextHolder.getContext().getAuthentication().getName(),
								vExtDocNo.toUpperCase(), 
								vCustomer.toUpperCase(),
								vNoBSO.toUpperCase(),
								vInvoice.toUpperCase(),
							 	"CETAK");

			
			String jasperRptAkhir = "/solusi/hapis/webui/reports/sales/04034_CekStatusKomisiAkhir.jasper";
					
			param.put("ProsesId",  vProsesId); 		
			param.put("Sales",  SecurityContextHolder.getContext().getAuthentication().getName()); 
			param.put("ExtDocNo",  vExtDocNo.toUpperCase()); 
			param.put("Customer",  vCustomer.toUpperCase()); 		
			param.put("NoBSO",  vNoBSO.toUpperCase()); 
			param.put("NoInvoice",  vInvoice.toUpperCase()); 

			
			
			String vSaveAs = "PDF";
			if (StringUtils.isNotEmpty(rdgSave.getSelectedItem().getValue())) {
				vSaveAs = rdgSave.getSelectedItem().getValue();	
			} 
			
			if(vSaveAs.equals("PDF")){
				new JReportGeneratorWindow(param, jasperRptAkhir, "PDF"); 
			} else {
				new JReportGeneratorWindow(param, jasperRptAkhir, "XLS"); 
			}
			
			@SuppressWarnings("unused")
			String vDelete= callStoreProcOrFuncService.callCekStatusKomisi(vProsesId, SecurityContextHolder.getContext().getAuthentication().getName(),
					vExtDocNo.toUpperCase(), 
					vCustomer.toUpperCase(),
					vNoBSO.toUpperCase(),
					vInvoice.toUpperCase(),
				 	"DELETE");

			
//			// Call Proses ----------------------------------------------------------------------------------------------------
//	        selectQueryNavReportService.callDeleteInvoiceLunasTemp(timeStamp);
//	
//	         
//	        //-----------------------------------------------------------------------------------------------------------------  
		}
		
	}
 
}