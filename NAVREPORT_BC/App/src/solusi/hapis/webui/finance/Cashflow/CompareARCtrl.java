package solusi.hapis.webui.finance.Cashflow;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.model.Temp36DuedateAr;
import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.navbi.service.Temp36DuedateArService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class CompareARCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	private String timeStamp;
	
	protected Textbox lbl1;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdAJ;
	protected Radio rdSP;
	

	protected Datebox dbTglFrom;  
	

	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	List<Temp36DuedateAr> listDuedateAR = new ArrayList<Temp36DuedateAr>();
	
	private Temp36DuedateArService temp36DuedateArService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		dbTglFrom.setValue((new Date()));  

		
		timeStamp = String.valueOf(System.currentTimeMillis());
    	
    	
		rdAJ.setSelected(true); 
    	
	}
	
	public void onClick$btnOK1(Event event) throws InterruptedException, ParseException {
		
		
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();
				
				//int vJmlKolom = sheet.getColumns();


				if(CommonUtils.isNotEmpty(listDuedateAR)){
					//temp07SalaryHapisService.save(listSalaryHapis);		
					listDuedateAR.clear();
				}
				


				for (int i = 8; i < vJmlData; i++){					
					
					String vSellTo = sheet.getCell(2,i).getContents();
					
					if (vSellTo.equals("Sell-to Code") == false && vSellTo.equals("") == false){
						
						String vSellToName =  sheet.getCell(3,i).getContents();
						String vBillTo =  sheet.getCell(4,i).getContents();
						String vBillToName =  sheet.getCell(5,i).getContents();
						String vCabang =  sheet.getCell(7,i).getContents();
						String vNoInvoice =  sheet.getCell(11,i).getContents();
						String vTglInvoice =  sheet.getCell(13,i).getContents();
						String vDuedateAr =  sheet.getCell(14,i).getContents();
						String vAmountAr =  sheet.getCell(16,i).getContents();
						vAmountAr =	vAmountAr.replaceAll(",", "");
						
						Temp36DuedateAr anData = new Temp36DuedateAr(
								vSellTo,
								vSellToName,
								vBillTo,
								vBillToName,
								vCabang,
								vNoInvoice,
								vTglInvoice,
								vDuedateAr,
								vAmountAr,
								timeStamp);
						
						listDuedateAR.add(anData);	
					}							

				}
						


				workbook.close();
				
				if(CommonUtils.isNotEmpty(listDuedateAR)){
					temp36DuedateArService.save(listDuedateAR);					
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

		String vCompany = "AUTOJAYA";
		if (CommonUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		Date vTglMulai = new Date();
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglMulai = dbTglFrom.getValue();
		}   
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglMulai  = frmTgl.format(vTglMulai);  	
		
		
		@SuppressWarnings("unused")
		String vResult = callStoreProcOrFuncService.callCompareAR(timeStamp, vCompany, vStrTglMulai, "CETAK");
				
		
		String jasperRpt = "/solusi/hapis/webui/reports/finance/Cashflow/0102009_CompareAR.jasper";
		
		
		param.put("ProsesId",  timeStamp);	
		param.put("Company",  vCompany);	
		

		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 


		@SuppressWarnings("unused")
		String vDelete = callStoreProcOrFuncService.callCompareAR(timeStamp, vCompany, vStrTglMulai, "DELETE");
		
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		parameterInput.put("prosesId", timeStamp);		
		List<Temp36DuedateAr> tempListTempTemp36DuedateAr = temp36DuedateArService.getListTemp36DuedateAr(parameterInput);
		if (CommonUtils.isNotEmpty(tempListTempTemp36DuedateAr)) {			
			temp36DuedateArService.delete(tempListTempTemp36DuedateAr);			
		}
        
		   
	}
}