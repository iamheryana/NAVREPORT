package solusi.hapis.webui.accounting;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.tabel.model.Tmp02kertaskerja;
import solusi.hapis.backend.tabel.model.Tmp03hasilopname;
import solusi.hapis.backend.tabel.service.Tmp02kertaskerjaService;
import solusi.hapis.backend.tabel.service.Tmp03hasilopnameService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class HasilStockOpnameCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Datebox dbTglUpto;  
	protected Textbox txtLocation;    
	
	private PathReport pathRpt;
	private String timeStamp;
	
	protected Textbox lbl1;
	protected Textbox lbl2;
	
	private Tmp02kertaskerjaService tmp02kertaskerjaService;
	private Tmp03hasilopnameService tmp03hasilopnameService;
	
	List<Tmp02kertaskerja> listDataKK = new ArrayList<Tmp02kertaskerja>();
	List<Tmp03hasilopname> listDataHOpname1 = new ArrayList<Tmp03hasilopname>();
	List<Tmp03hasilopname> listDataHOpname2 = new ArrayList<Tmp03hasilopname>();
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

    	dbTglUpto.setValue((new Date()));  
    	
    	
    	timeStamp = String.valueOf(System.currentTimeMillis());
    	pathRpt = new PathReport(timeStamp);
    
	}
	
	public void onClick$btnOK1(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();

			
				if(CommonUtils.isNotEmpty(listDataHOpname1)){
					tmp03hasilopnameService.save(listDataHOpname1);		
					listDataHOpname1.clear();
				}
								
				for (int i = 1; i < vJmlData; i++){
					//System.out.println(sheet.getCell(0,i).getContents());
					
					Tmp03hasilopname anData = new Tmp03hasilopname(
							sheet.getCell(0,i).getContents(),
							sheet.getCell(1,i).getContents(),
							sheet.getCell(2,i).getContents(),
							sheet.getCell(3,i).getContents(),
							sheet.getCell(4,i).getContents(),
							"Terminal 1",
							timeStamp);
					listDataHOpname1.add(anData);
				}

				
				workbook.close();

				if(CommonUtils.isNotEmpty(listDataHOpname1)){
					tmp03hasilopnameService.save(listDataHOpname1);					
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
	
	public void onClick$btnOK2(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();

			
				if(CommonUtils.isNotEmpty(listDataHOpname2)){
					tmp03hasilopnameService.save(listDataHOpname2);		
					listDataHOpname2.clear();
				}
								
				for (int i = 1; i < vJmlData; i++){
					System.out.println(sheet.getCell(0,i).getContents());
					
					Tmp03hasilopname anData = new Tmp03hasilopname(
							sheet.getCell(0,i).getContents(),
							sheet.getCell(1,i).getContents(),
							sheet.getCell(2,i).getContents(),
							sheet.getCell(3,i).getContents(),
							sheet.getCell(4,i).getContents(),
							"Terminal 2",
							timeStamp);
					listDataHOpname2.add(anData);
				}

				
				workbook.close();

				if(CommonUtils.isNotEmpty(listDataHOpname2)){
					tmp03hasilopnameService.save(listDataHOpname2);					
				}
				
		
		        
				lbl2.setValue(media.getName()+ " Sudah berhasil terupload.");


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
	public void onClick$btnOK(Event event) throws InterruptedException, IOException, RowsExceededException, WriteException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date vTanggal = null;
			try {
				vTanggal = formatter.parse("1900-01-01");			
			} catch (ParseException e) {
				e.printStackTrace();
			}			


		Date vTglUpTo = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
		
						
		String vLocation = ".";
		if (StringUtils.isNotEmpty(txtLocation.getValue())) {
			vLocation = txtLocation.getValue();
		} 
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0107001");
			
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01027_KKSORekonsiliasi.jasper";
			
		param.put("Tanggal",  vTglUpTo); 
		param.put("Location",  vLocation.toUpperCase()); 

				
		File outputFile = new File(pathRpt.getKksoKonsolidasi());
		
		@SuppressWarnings("resource")
		OutputStream outStream = new FileOutputStream(outputFile);
        outStream.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "XLS"));
               
        try {
			// Membaca Excel dari file yang di Upload
			Workbook workbook = Workbook.getWorkbook(outputFile);
			
			Sheet sheet = workbook.getSheet(0);

			
			
			int vJmlData = sheet.getRows();
			
			for (int i = 1; i < vJmlData; i++){

				if(i >=6) {
					
					Tmp02kertaskerja anData = new Tmp02kertaskerja(
							sheet.getCell(1,i).getContents(),
							sheet.getCell(2,i).getContents(),
							sheet.getCell(3,i).getContents(),
							sheet.getCell(4,i).getContents(),
							sheet.getCell(5,i).getContents(),
							sheet.getCell(6,i).getContents(),
							sheet.getCell(7,i).getContents(),
							sheet.getCell(8,i).getContents(),
							sheet.getCell(9,i).getContents(),
							timeStamp);
					listDataKK.add(anData);
					
				}
			}
					
			workbook.close();	
			
			if(CommonUtils.isNotEmpty(listDataKK)){
				tmp02kertaskerjaService.save(listDataKK);					
			}
			
		} catch (BiffException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);

		} catch (IOException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);

		}
		
        outputFile.delete();
        
      
        //Untuk Penyimpanan Hasil Akhir yang akan di keluarkan di layar
		File outputFileHasilSOAkhir = new File(pathRpt.getHasilSOAkhir());

		
		WritableWorkbook outputFileWorkbookHasilSOAkhir = Workbook.createWorkbook(outputFileHasilSOAkhir);
		
	    // Sheet 2 - hasil TEMUAN stock Opname 
		WritableSheet sheetOutHasilSOAkhir2 = outputFileWorkbookHasilSOAkhir.createSheet("TEMUAN", 0);
	        
	        // MEMANGGIL HASIL TEMUAN
			String jasperRptHasilSOTemuan = "/solusi/hapis/webui/reports/accounting/01037_HasilStockOpnameTemuan.jasper";
			
			
			param.put("l_proses_id",  timeStamp);	
			
			
			File outputFileHasilSOTemuan = new File(pathRpt.getHasilSOTemuan());
			
	
			@SuppressWarnings("resource")
			OutputStream outStreamHasilSOTemuan = new FileOutputStream(outputFileHasilSOTemuan);
			outStreamHasilSOTemuan.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRptHasilSOTemuan, "XLS",1));
	               
	        try {
				// Membaca Excel dari file yang di Upload
				Workbook workbookHasilSOTemuan = Workbook.getWorkbook(outputFileHasilSOTemuan);
				
				Sheet sheetHasilSOTemuan = workbookHasilSOTemuan.getSheet(0);
	
				int vJmlDataHasilSOTemuan = sheetHasilSOTemuan.getRows();
				
				for (int i = 0; i < vJmlDataHasilSOTemuan; i++){
					
					WritableCellFormat numformat1 = new WritableCellFormat(new NumberFormat("#,###")); 
						
										
					sheetOutHasilSOAkhir2.addCell(new Label(0, i, sheetHasilSOTemuan.getCell(1,i).getContents()));
				
					if(i > 1){
						String vTempQty = "0.00";
						
						if (sheetHasilSOTemuan.getCell(2,i).getContents().replaceAll(",", "").trim().length() >0){
							vTempQty = sheetHasilSOTemuan.getCell(2,i).getContents().replaceAll(",", "");
						}
						
						sheetOutHasilSOAkhir2.addCell(new Number(1 ,i, Double.parseDouble(vTempQty), numformat1));
						
					
					} else {
						sheetOutHasilSOAkhir2.addCell(new Label(1 ,i, sheetHasilSOTemuan.getCell(2,i).getContents()));
					}
					
				}
						
	
				workbookHasilSOTemuan.close();	
	        } catch (BiffException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);
	
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);
	
			}
			
	        outputFileHasilSOTemuan.delete();

		// Sheet 1 - hasil stock Opname sesuai Kertas Kerja
		WritableSheet sheetOutHasilSOAkhir1 = outputFileWorkbookHasilSOAkhir.createSheet("HASIL", 0);
				
		
        
	        // MEMANGGIL HASIL PERBANDINGAN
			String jasperRptHasilSO = "/solusi/hapis/webui/reports/accounting/01036_HasilStockOpname.jasper";
			
			
			param.put("l_proses_id",  timeStamp);	
			param.put("Periode",  vTglUpTo);	
			param.put("Location",  vLocation.toUpperCase());	
			
			
			File outputFileHasilSO = new File(pathRpt.getHasilSO());
			
	
			@SuppressWarnings("resource")
			OutputStream outStreamHasilSO = new FileOutputStream(outputFileHasilSO);
			outStreamHasilSO.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRptHasilSO, "XLS",1));
	               
	        try {
				// Membaca Excel dari file yang di Upload
				Workbook workbookHasilSO = Workbook.getWorkbook(outputFileHasilSO);
				
				Sheet sheetHasilSO = workbookHasilSO.getSheet(0);
	
				int vJmlDataHasilSO = sheetHasilSO.getRows();
				
				for (int i = 0; i < vJmlDataHasilSO; i++){
					
					WritableCellFormat numformat1 = new WritableCellFormat(new NumberFormat("#,###")); 
					WritableCellFormat numformat2 = new WritableCellFormat(new NumberFormat("#,###")); 
					WritableCellFormat numformat3 = new WritableCellFormat(new NumberFormat("#,###")); 
						
										
					sheetOutHasilSOAkhir1.addCell(new Label(0, i, sheetHasilSO.getCell(1,i).getContents()));
					sheetOutHasilSOAkhir1.addCell(new Label(1, i, sheetHasilSO.getCell(2,i).getContents()));
					sheetOutHasilSOAkhir1.addCell(new Label(2 ,i, sheetHasilSO.getCell(3,i).getContents()));
					sheetOutHasilSOAkhir1.addCell(new Label(3 ,i, sheetHasilSO.getCell(4,i).getContents()));
					sheetOutHasilSOAkhir1.addCell(new Label(4 ,i, sheetHasilSO.getCell(5,i).getContents()));
					sheetOutHasilSOAkhir1.addCell(new Label(5 ,i, sheetHasilSO.getCell(6,i).getContents()));
					sheetOutHasilSOAkhir1.addCell(new Label(6 ,i, sheetHasilSO.getCell(7,i).getContents()));
					sheetOutHasilSOAkhir1.addCell(new Label(7 ,i, sheetHasilSO.getCell(8,i).getContents()));
					if(i > 5){
						String vTempQty = "0.00";
						String vTempQtyFisik = "0.00";
						String vTempQtySelisih = "0.00";
						
						if (sheetHasilSO.getCell(9,i).getContents().replaceAll(",", "").trim().length() >0){
							vTempQty = sheetHasilSO.getCell(9,i).getContents().replaceAll(",", "");
						}
												
						if (sheetHasilSO.getCell(10,i).getContents().replaceAll(",", "").trim().length() >0){
							vTempQtyFisik = sheetHasilSO.getCell(10,i).getContents().replaceAll(",", "");
						}
						
						if (sheetHasilSO.getCell(11,i).getContents().replaceAll(",", "").trim().length() >0){
							vTempQtySelisih = sheetHasilSO.getCell(11,i).getContents().replaceAll(",", "");
						}
						
						sheetOutHasilSOAkhir1.addCell(new Number(8 ,i, Double.parseDouble(vTempQty), numformat1));
						sheetOutHasilSOAkhir1.addCell(new Number(9 ,i, Double.parseDouble(vTempQtyFisik), numformat2));
						sheetOutHasilSOAkhir1.addCell(new Number(10 ,i, Double.parseDouble(vTempQtySelisih), numformat3));
					
					} else {
						sheetOutHasilSOAkhir1.addCell(new Label(8 ,i, sheetHasilSO.getCell(9,i).getContents()));
						sheetOutHasilSOAkhir1.addCell(new Label(9 ,i, sheetHasilSO.getCell(10,i).getContents()));
						sheetOutHasilSOAkhir1.addCell(new Label(10 ,i, sheetHasilSO.getCell(11,i).getContents()));
					}
						
					
					//System.out.println(sheetHasilSO.getCell(1,i).getContents()+ " - "+sheetHasilSO.getCell(2,i).getContents());

				
				}
						
	
				workbookHasilSO.close();	
	        } catch (BiffException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);
	
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);
	
			}
			
	        outputFileHasilSO.delete();
	    

	        
        outputFileWorkbookHasilSOAkhir.write();
    	
		InputStream mediais = new FileInputStream(outputFileHasilSOAkhir);
		AMedia amedia = new AMedia("HasilSOAkhir.xls", "xls", "application/vnd.ms-excel", mediais);
		
		Filedownload.save(amedia);
		
		outputFileWorkbookHasilSOAkhir.close();
		
		outputFileHasilSOAkhir.delete();
		//System.out.println("timeStamp : "+timeStamp);
		
        
        if(CommonUtils.isNotEmpty(listDataKK)){
			tmp02kertaskerjaService.delete(listDataKK);					
		}
        
        if(CommonUtils.isNotEmpty(listDataHOpname1)){
			tmp03hasilopnameService.delete(listDataHOpname1);					
		}			
        
        if(CommonUtils.isNotEmpty(listDataHOpname2)){
        	tmp03hasilopnameService.delete(listDataHOpname2);					
		}
			
        	
	}
 
	public void onClick$btnFormat(Event event) throws FileNotFoundException{
		PathReport pathReport = new PathReport();
		
		File outputFileExcel = new File(pathReport.getContohFormatHasilStockOpname());
		
		
		InputStream mediais = new FileInputStream(outputFileExcel);
		AMedia amedia = new AMedia("ContohFormatHasilStockOpname.xls", "xls", "application/vnd.ms-excel", mediais);
		
		Filedownload.save(amedia);
	}
	
}