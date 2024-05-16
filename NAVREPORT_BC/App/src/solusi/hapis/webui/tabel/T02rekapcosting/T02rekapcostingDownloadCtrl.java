package solusi.hapis.webui.tabel.T02rekapcosting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.parameter.service.SelectQueryNavReportService;
import solusi.hapis.backend.tabel.model.Tmp06invoicelunas;
import solusi.hapis.backend.tabel.service.Tmp06invoicelunasService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;
import solusi.hapis.webui.util.ZksampleMessageUtils;

/**
 * User: Heryana
 * Date: 29-03-2018
 */

public class T02rekapcostingDownloadCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -6758438895356823735L;

	protected Window windowT02rekapcostingDownload;
	
	protected Borderlayout borderlayout_T02rekapcostingDownload;
	
	// Screen Parameter Components

	protected Datebox dbTglFrom;  
	protected Datebox dbTglUpto;
	
	private PathReport pathRpt;
	
	@SuppressWarnings("unused")
	private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	
	private T02rekapcostingMainCtrl T02rekapcostingMainCtrl;
	
	
    private SelectQueryNavReportService selectQueryNavReportService;
    private Tmp06invoicelunasService tmp06invoicelunasService;
    
    /**
     * default constructor.<br>
     */
    public T02rekapcostingDownloadCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);
        this.self.setAttribute("controller", this, false);

        if (arg.containsKey("ModuleMainController")) {
        	setT02rekapcostingMainCtrl((T02rekapcostingMainCtrl) arg.get("ModuleMainController"));
        	T02rekapcostingMainCtrl.setT02rekapcostingDownloadCtrl(this);
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
	
	public void onCreate$windowT02rekapcostingDownload(Event event) throws Exception {
		doFitSize(event);
	}
	
	public void doFitSize(Event event) {
		final int height = ((Intbox) Path
				.getComponent("/outerIndexWindow/currentDesktopHeight"))
				.getValue().intValue();
		final int maxListBoxHeight = height - 148;

		borderlayout_T02rekapcostingDownload.setHeight(String
				.valueOf(maxListBoxHeight) + "px");

		windowT02rekapcostingDownload.invalidate();
	}
	

	
	
	@SuppressWarnings("unchecked")
	public void onClick$btnDownload(Event event) throws InterruptedException, ParseException, IOException {
    	
    	String timeStamp = String.valueOf(System.currentTimeMillis());
    	pathRpt = new PathReport(timeStamp);
    	  
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
		
		
		//Ambil data Invoice Lunas dari NAV -------------------------------------------------------------------------------
		String jasperRpt = "/solusi/hapis/webui/reports/finance/02035_InvoiceLunasCosting.jasper";
				
		param.put("TglLunasFrom",  vTglFrom);
		param.put("TglLunasUpto",  vTglUpto);
				
		
		File outputFile = new File(pathRpt.getInvoiceLunasNAV());
		

		@SuppressWarnings("resource")
		OutputStream outStream = new FileOutputStream(outputFile);
        outStream.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "XLS"));
		//-----------------------------------------------------------------------------------------------------------------
        // Msukan data ke NAV Report --------------------------------------------------------------------------------------
        List<Tmp06invoicelunas> listInvoiceLunasNAV = new ArrayList<Tmp06invoicelunas>();
        try {
  			// Membaca Excel dari file yang di Upload
        	Workbook workbook = Workbook.getWorkbook(outputFile);
  			
  			Sheet sheet = workbook.getSheet(0);

  			int vJmlData = sheet.getRows();
  			
  			for (int i = 1; i < vJmlData; i++){

  				if(i > 2) {
  				//if(i == 3 ||i == 2000) {
  					
  					
  					Date vTglSO = null;
  					String dRTglSO = "01-01-2000";
  					SimpleDateFormat dfRTglSO= new SimpleDateFormat("dd-MM-yyyy");
  					
  					Date vTglInv = null;
  					String dRTglInv = "01-01-2000";
  					SimpleDateFormat dfRTglInv= new SimpleDateFormat("dd-MM-yyyy");
  					
  					Date vTglLunas = null;
  					String dRTglLunas = "01-01-2000";
  					SimpleDateFormat dfRTglLunas= new SimpleDateFormat("dd-MM-yyyy");
  					
  					
  					if(	(sheet.getCell(7,i).getContents()).equals("null") == false ){
  						if(	(sheet.getCell(7,i).getContents()).equals("") == false ){
  							dRTglSO = sheet.getCell(7,i).getContents();
  	  						vTglSO  = dfRTglSO.parse(dRTglSO);
  						}
  					}
  					

  					if(	(sheet.getCell(12,i).getContents()).equals("null") == false ){
  						if(	(sheet.getCell(12,i).getContents()).equals("") == false ){
  							dRTglInv = sheet.getCell(12,i).getContents();
  	  						vTglInv  = dfRTglInv.parse(dRTglInv);
  						}
  					}
  					

  					if(	(sheet.getCell(13,i).getContents()).equals("null") == false ){
  						if(	(sheet.getCell(13,i).getContents()).equals("") == false ){
  							dRTglLunas = sheet.getCell(13,i).getContents();
  	  						vTglLunas  = dfRTglLunas.parse(dRTglLunas);  		
  						}
  					}
  					
  					String vAmountStr = sheet.getCell(10,i).getContents().replaceAll(",", "").replaceAll("-", "");
  					BigDecimal vAmount = new BigDecimal(vAmountStr);
  					
  			
					Tmp06invoicelunas anTmp06 = new Tmp06invoicelunas(sheet.getCell(1,i).getContents(), 
						sheet.getCell(2,i).getContents(),
						sheet.getCell(3,i).getContents(),
						sheet.getCell(4,i).getContents(),
						sheet.getCell(5,i).getContents(),
						sheet.getCell(6,i).getContents(), 
						vTglSO, 
						sheet.getCell(8,i).getContents(), 
						sheet.getCell(9,i).getContents(),
						vAmount, 
						sheet.getCell(11,i).getContents(), 
						vTglInv, 
						vTglLunas, 
						timeStamp);
					
					listInvoiceLunasNAV.add(anTmp06);
  	
 				}
  				
  			
  			}
  					
  			workbook.close();	
  			
  			if(CommonUtils.isNotEmpty(listInvoiceLunasNAV)){
				tmp06invoicelunasService.save(listInvoiceLunasNAV);					
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
         String vResultDownload = selectQueryNavReportService.callDownloadInvoiceLunas(timeStamp);
         
         ZksampleMessageUtils.showInformationMessage(vResultDownload);
 		 return;
          
        //-----------------------------------------------------------------------------------------------------------------  
   

	}
	
	public void setT02rekapcostingMainCtrl(T02rekapcostingMainCtrl T02rekapcostingMainCtrl) {
		this.T02rekapcostingMainCtrl = T02rekapcostingMainCtrl;
	}

	public T02rekapcostingMainCtrl getT02rekapcostingMainCtrl() {
		return this.T02rekapcostingMainCtrl;
	}

}

