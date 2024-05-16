package solusi.hapis.webui.logistic;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.model.Vendor;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.backend.tabel.model.Tmp01rebate;
import solusi.hapis.backend.tabel.service.Tmp01rebateService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.logistic.DataPendukung.RebateHoneywellNAV;
import solusi.hapis.webui.lov.VendorLOV;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class PrincipalInfoRebateCompareCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowPrincipalInfoRebateCompare;

	
	protected Datebox dbTglFrom;
	protected Datebox dbTglUpto;
	
	protected Textbox txtVendorNo;

	protected Bandbox  cmbPrincipal;
	protected Listbox listPrincipal;
	protected String vPrincipal = "ALL";
	
	protected Textbox lbl1;	
		
	protected Button btnSearchVendorLOV;
	
	private SelectQueryService selectQueryService;
	private Tmp01rebateService tmp01rebateService;
	
	private PathReport pathRpt;
	private String timeStamp;
	
	private  List<RebateHoneywellNAV>  listRebateHoneywellNAV;
	private  List<RebateHoneywellNAV>  listRebateHoneywellDISTI;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		listRebateHoneywellNAV = new ArrayList<RebateHoneywellNAV>();
		listRebateHoneywellDISTI = new ArrayList<RebateHoneywellNAV>();
		
		timeStamp = String.valueOf(System.currentTimeMillis());
    	pathRpt = new PathReport(timeStamp);
    	
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		
		dbTglFrom.setValue(vTglFrom);  
		
    	dbTglUpto.setValue((new Date()));   
    	    	

    	txtVendorNo.setValue("ALL");

		
		Bandpopup popup2 = new Bandpopup();
			listPrincipal = new Listbox();
			listPrincipal.setMold("paging");
			listPrincipal.setAutopaging(false);
			listPrincipal.setWidth("400px");
			listPrincipal.addEventListener(Events.ON_SELECT, selectPrincipal());
			listPrincipal.setParent(popup2);
		popup2.setParent(cmbPrincipal);
	        
		listPrincipal.appendItem("ALL", "ALL");
		
		List<Object[]> vResultPrincipal = selectQueryService.QueryPrincipal();
		if(CommonUtils.isNotEmpty(vResultPrincipal)){
			for(Object[] aRslt : vResultPrincipal){
				listPrincipal.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbPrincipal.setValue(listPrincipal.getItemAtIndex(0).getLabel());
		listPrincipal.setSelectedItem(listPrincipal.getItemAtIndex(0));
    	
	}
	
	private EventListener selectPrincipal() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbPrincipal.setValue(listPrincipal.getSelectedItem().getLabel());
				vPrincipal = listPrincipal.getSelectedItem().getValue().toString();
				cmbPrincipal.close();
			}
		};
	}
	
	
	public void onClick$btnSearchVendorLOV(Event event) {
		Vendor cust = VendorLOV.show(windowPrincipalInfoRebateCompare);

		if (cust != null) {
			txtVendorNo.setValue(cust.getCode());
		} else {
			txtVendorNo.setValue(null);
		}

	}
		
	@SuppressWarnings("unchecked")
	public void onClick$btnOK1(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		String vProsesId = String.valueOf(System.currentTimeMillis());
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();

				List<Tmp01rebate> listData = new ArrayList<Tmp01rebate>();
				
				for (int i = 1; i < vJmlData; i++){

					if(i >= 1){
						if(		CommonUtils.isNotEmpty(sheet.getCell(0,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(5,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(8,i).getContents())){
							
							String vTempAmount = "0.00";
							if (sheet.getCell(8,i).getContents().replaceAll(",", "").trim().length() >0){
								vTempAmount = sheet.getCell(8,i).getContents().replaceAll(",", "");
							}
							
							Tmp01rebate anData = new Tmp01rebate(sheet.getCell(0,i).getContents(),
									sheet.getCell(1,i).getContents(),
									sheet.getCell(2,i).getContents(),
									sheet.getCell(3,i).getContents(),
									sheet.getCell(5,i).getContents(),
									sheet.getCell(6,i).getContents(),
									"",
									vTempAmount,
									vProsesId);
							listData.add(anData);
							
//							listRebateHoneywellDISTI.add(new RebateHoneywellNAV(	sheet.getCell(0,i).getContents(), 
//																sheet.getCell(1,i).getContents(),
//																sheet.getCell(2,i).getContents(),
//																sheet.getCell(3,i).getContents(), 
//																sheet.getCell(5,i).getContents(),
//																sheet.getCell(6,i).getContents(),
//																"",
//																Double.parseDouble(vTempAmount),
//																""
//																));
						}
					}
				
				}
					
//				for (RebateHoneywellNAV inv : listRebateHoneywellDISTI){
//				System.out.println("inv.getNoInvoice()  --> "+inv.getNoInvoice());
//				System.out.println("inv.getItemNo()  --> "+inv.getItemNo());
//				System.out.println("inv.getItemDesc()  --> "+inv.getItemDesc());
//				System.out.println("inv.getNoPO()  --> "+inv.getNoPO());
//				System.out.println("inv.getQty()  --> "+inv.getQty());
//				System.out.println("inv.getTanggal()  --> "+inv.getTanggal());
//				System.out.println("inv.getAmount()  --> "+inv.getAmount());
//				System.out.println("====================================================================================");
//				
//				}
				
				workbook.close();

				if(CommonUtils.isNotEmpty(listData)){
					tmp01rebateService.save(listData);					
				}
				
				String jasperRptDISTI = "/solusi/hapis/webui/reports/logistic/l_tmp01rebate.jasper";
				
				
				param.put("l_proses_id",  vProsesId);	
				
				
				File outputFileDISTI = new File(pathRpt.getPrincipalInfoRebateHoneywellFromDisti());
				

				@SuppressWarnings("resource")
				OutputStream outStreamDISTI = new FileOutputStream(outputFileDISTI);
				outStreamDISTI.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRptDISTI, "XLS",1));
		               
		        try {
					// Membaca Excel dari file yang di Upload
					Workbook workbookDISTI = Workbook.getWorkbook(outputFileDISTI);
					
					Sheet sheetDISTI = workbookDISTI.getSheet(0);

					int vJmlDataDISTI = sheetDISTI.getRows();
					
					for (int i = 1; i < vJmlDataDISTI; i++){

						if(i > 0){
							
							if(		CommonUtils.isNotEmpty(sheetDISTI.getCell(1,i).getContents()) ||
									CommonUtils.isNotEmpty(sheetDISTI.getCell(2,i).getContents()) ||
									CommonUtils.isNotEmpty(sheetDISTI.getCell(3,i).getContents()) ||
									CommonUtils.isNotEmpty(sheetDISTI.getCell(4,i).getContents()) ||
									CommonUtils.isNotEmpty(sheetDISTI.getCell(5,i).getContents()) ||
									CommonUtils.isNotEmpty(sheetDISTI.getCell(6,i).getContents()) ||
									CommonUtils.isNotEmpty(sheetDISTI.getCell(7,i).getContents()) ||
									CommonUtils.isNotEmpty(sheetDISTI.getCell(8,i).getContents())){
								String vTempAmountDISTI = "0.00";
								if (sheetDISTI.getCell(8,i).getContents().replaceAll(",", "").trim().length() >0){
									vTempAmountDISTI = sheetDISTI.getCell(8,i).getContents().replaceAll(",", "");
								}
								
								listRebateHoneywellDISTI.add(new RebateHoneywellNAV(	sheetDISTI.getCell(1,i).getContents(), 
																						sheetDISTI.getCell(2,i).getContents(),
																						sheetDISTI.getCell(3,i).getContents(),
																						sheetDISTI.getCell(4,i).getContents(), 
																						sheetDISTI.getCell(5,i).getContents(),
																						sheetDISTI.getCell(6,i).getContents(),
																						sheetDISTI.getCell(7,i).getContents(),
																						Double.parseDouble(vTempAmountDISTI),
																						""
																						));
							}
						}
					
					}
							

					workbookDISTI.close();	
		        } catch (BiffException e) {
					Messagebox.show("Error : " + e.getMessage(), "Error",
							Messagebox.OK, Messagebox.ERROR);

				} catch (IOException e) {
					Messagebox.show("Error : " + e.getMessage(), "Error",
							Messagebox.OK, Messagebox.ERROR);

				}
				
		        outputFileDISTI.delete();
				
		        if(CommonUtils.isNotEmpty(listData)){
					tmp01rebateService.delete(listData);					
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
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException, IOException, RowsExceededException, WriteException {
			
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(new Date());
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);
		String dRFrom = "1/1/"+yearTglFrom;
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
		

		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   		
		
		Date vTglUpTo = new Date();   
		if(CommonUtils.isNotEmpty(dbTglUpto.getValue()) == true){  
			vTglUpTo = dbTglUpto.getValue();
		}
			

		String vVendorNo = "ALL";
		if (StringUtils.isNotEmpty(txtVendorNo.getValue())) {
			vVendorNo = txtVendorNo.getValue();
		}
		
		
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0302001");
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/logistic/03006_PrincipalInfoRebateCompare.jasper";
				
		
		param.put("TglFrom",  vTglFrom);
		param.put("TglUpto",  vTglUpTo);
		param.put("Vendor",  vVendorNo);
		param.put("Principal",  vPrincipal); 
		
		
		
		File outputFile = new File(pathRpt.getPrincipalInfoRebateHoneywell());
		

		@SuppressWarnings("resource")
		OutputStream outStream = new FileOutputStream(outputFile);
        outStream.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "XLS"));
               
        try {
			// Membaca Excel dari file yang di Upload
			Workbook workbook = Workbook.getWorkbook(outputFile);
			
			Sheet sheet = workbook.getSheet(0);

			int vJmlData = sheet.getRows();
			
			for (int i = 1; i < vJmlData; i++){

				if(i > 8){
					String vTempAmountNAV = "0.00";
					if (sheet.getCell(9,i).getContents().replaceAll(",", "").trim().length() >0){
						vTempAmountNAV = sheet.getCell(9,i).getContents().replaceAll(",", "");
					}
	
					if(		CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents()) ||
							CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents()) ||
							CommonUtils.isNotEmpty(sheet.getCell(5,i).getContents()) ||
							CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents()) ||
							CommonUtils.isNotEmpty(sheet.getCell(7,i).getContents()) ||
							CommonUtils.isNotEmpty(sheet.getCell(8,i).getContents()) ||
							CommonUtils.isNotEmpty(sheet.getCell(9,i).getContents())){
						listRebateHoneywellNAV.add(new RebateHoneywellNAV(	sheet.getCell(2,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(5,i).getContents(),
															sheet.getCell(6,i).getContents(), 
															sheet.getCell(7,i).getContents(),
															sheet.getCell(8,i).getContents(),
															"",
															Double.parseDouble(vTempAmountNAV),
															""
															));
					}
				}
			
			}
					
			
//			for (RebateHoneywellNAV inv : listRebateHoneywellNAV){
//				System.out.println("inv.getNoInvoice()  --> "+inv.getNoInvoice());
//				System.out.println("inv.getItemNo()  --> "+inv.getItemNo());
//				System.out.println("inv.getItemDesc()  --> "+inv.getItemDesc());
//				System.out.println("inv.getNoPO()  --> "+inv.getNoPO());
//				System.out.println("inv.getQty()  --> "+inv.getQty());
//				System.out.println("inv.getTanggal()  --> "+inv.getTanggal());
//				System.out.println("inv.getAmount()  --> "+inv.getAmount());
//				System.out.println("====================================================================================");
//				
//			}
			
			workbook.close();	
			
			
		} catch (BiffException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);

		} catch (IOException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);

		}
		
        outputFile.delete();	
        
        if (CommonUtils.isNotEmpty(listRebateHoneywellDISTI) && CommonUtils.isNotEmpty(listRebateHoneywellNAV) ) {
        	
        	
        	List<RebateHoneywellNAV>  listSama = new ArrayList<RebateHoneywellNAV>();
			List<RebateHoneywellNAV>  listNAVTidakSama = new ArrayList<RebateHoneywellNAV>();
			List<RebateHoneywellNAV>  listDISTITidakSama = new ArrayList<RebateHoneywellNAV>();
			
			
			for (RebateHoneywellNAV anNAV : listRebateHoneywellNAV){
				boolean noInvoiceSama = false;
				boolean noItemSama = false;
				boolean qtySama = false;
				boolean amountSama = false;
				boolean dataSama = false;
				
				for (RebateHoneywellNAV anDISTI : listRebateHoneywellDISTI){
					if(	anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
						anNAV.getItemNo().equals(anDISTI.getItemNo()) &&
						anNAV.getQty().equals(anDISTI.getQty()) &&
						anNAV.getAmount() == anDISTI.getAmount() ) {
						dataSama = true;
						break;
					} else {
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) &&
								anNAV.getQty().equals(anDISTI.getQty()) &&
								anNAV.getAmount() != anDISTI.getAmount()){
								noInvoiceSama = true;
								noItemSama = true;
								qtySama = true;
						}
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) &&
								anNAV.getQty().equals(anDISTI.getQty()) == false &&
								anNAV.getAmount() == anDISTI.getAmount()){
								noInvoiceSama = true;
								noItemSama = true;
								amountSama = true;
						}
						
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) == false &&
								anNAV.getQty().equals(anDISTI.getQty()) &&
								anNAV.getAmount() == anDISTI.getAmount()){
								noInvoiceSama = true;
								qtySama = true;
								amountSama = true;
						}
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) &&
								anNAV.getQty().equals(anDISTI.getQty()) == false &&
								anNAV.getAmount() != anDISTI.getAmount()){
								noInvoiceSama = true;
								noItemSama = true;
						}
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) == false &&
								anNAV.getQty().equals(anDISTI.getQty()) == false &&
								anNAV.getAmount() == anDISTI.getAmount() ){
								noInvoiceSama = true;
								amountSama = true;
						}
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) == false &&
								anNAV.getQty().equals(anDISTI.getQty()) &&
								anNAV.getAmount() != anDISTI.getAmount()){
								noInvoiceSama = true;
								qtySama = true;
						}
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) == false &&
								anNAV.getQty().equals(anDISTI.getQty()) == false &&
								anNAV.getAmount() != anDISTI.getAmount()){
								noInvoiceSama = true;
						}
						
											
					}
										
				}
				
				if (dataSama){
					listSama.add(new RebateHoneywellNAV(anNAV.getTanggal(), anNAV.getNoInvoice(), anNAV.getNoPO(), anNAV.getItemNo(), anNAV.getItemDesc(), anNAV.getQty(),"", anNAV.getAmount(), ""));
				} else {
					if (noInvoiceSama == true){
						if (noItemSama == true){
							if(qtySama == true){
								if (amountSama == false){
									listNAVTidakSama.add(new RebateHoneywellNAV(anNAV.getTanggal(), anNAV.getNoInvoice(), anNAV.getNoPO(), anNAV.getItemNo(), anNAV.getItemDesc(), anNAV.getQty(),"Berbeda Amount, di List DISTI.", anNAV.getAmount(), "Amount Tidak Sama"));
								}
							} else {
								if (amountSama == true){
									listNAVTidakSama.add(new RebateHoneywellNAV(anNAV.getTanggal(), anNAV.getNoInvoice(), anNAV.getNoPO(), anNAV.getItemNo(), anNAV.getItemDesc(), anNAV.getQty(),"Berbeda Qty, di List DISTI.", anNAV.getAmount(), "Amount Sama"));
								} else {
									listNAVTidakSama.add(new RebateHoneywellNAV(anNAV.getTanggal(), anNAV.getNoInvoice(), anNAV.getNoPO(), anNAV.getItemNo(), anNAV.getItemDesc(), anNAV.getQty(),"Berbeda Qty dan Amount, di List DISTI.", anNAV.getAmount(), "Amount Tidak Sama"));
								}
							}
							
						} else {
							if(qtySama == true){
								if  (amountSama == true){
									listNAVTidakSama.add(new RebateHoneywellNAV(anNAV.getTanggal(), anNAV.getNoInvoice(), anNAV.getNoPO(), anNAV.getItemNo(), anNAV.getItemDesc(), anNAV.getQty(),"Berbeda No Item, di List DISTI.", anNAV.getAmount(), "Amount Sama"));	
								} else {
									listNAVTidakSama.add(new RebateHoneywellNAV(anNAV.getTanggal(), anNAV.getNoInvoice(), anNAV.getNoPO(), anNAV.getItemNo(), anNAV.getItemDesc(), anNAV.getQty(),"Berbeda No Item dan Amount, di List DISTI.", anNAV.getAmount(), "Amount Tidak Sama"));	
								}
							} else {
								if  (amountSama == true){
									listNAVTidakSama.add(new RebateHoneywellNAV(anNAV.getTanggal(), anNAV.getNoInvoice(), anNAV.getNoPO(), anNAV.getItemNo(), anNAV.getItemDesc(), anNAV.getQty(),"Berbeda No Item dan Qty, di List DISTI.", anNAV.getAmount(), "Amount Sama"));										
								} else {
									listNAVTidakSama.add(new RebateHoneywellNAV(anNAV.getTanggal(), anNAV.getNoInvoice(), anNAV.getNoPO(), anNAV.getItemNo(), anNAV.getItemDesc(), anNAV.getQty(),"Berbeda No Item, Qty dan Amount, di List DISTI.", anNAV.getAmount(), "Amount Tidak Sama"));	
								}
							}
						}
					} else {
						listNAVTidakSama.add(new RebateHoneywellNAV(anNAV.getTanggal(), anNAV.getNoInvoice(), anNAV.getNoPO(), anNAV.getItemNo(), anNAV.getItemDesc(), anNAV.getQty(),"di NAV ada, di DISTI List tidak ada", anNAV.getAmount(), ""));
					}
				}

			}
			
			for (RebateHoneywellNAV anDISTI : listRebateHoneywellDISTI){
				boolean noInvoiceSama = false;
//				boolean noItemSama = false;
//				boolean qtySama = false;
				boolean dataSama = false;
//				boolean amountSama = false;
				
				
				for (RebateHoneywellNAV anNAV : listRebateHoneywellNAV){
					if(	anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
						anNAV.getItemNo().equals(anDISTI.getItemNo()) &&
						anNAV.getQty().equals(anDISTI.getQty())	&&
						anNAV.getAmount() == anDISTI.getAmount() ) {
						dataSama = true;
						break;
					} else {
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) &&
								anNAV.getQty().equals(anDISTI.getQty()) &&
								anNAV.getAmount() != anDISTI.getAmount()){
								noInvoiceSama = true;
//								noItemSama = true;
//								qtySama = true;
						}
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) &&
								anNAV.getQty().equals(anDISTI.getQty()) == false &&
								anNAV.getAmount() == anDISTI.getAmount() ){
								noInvoiceSama = true;
//								noItemSama = true;
//								amountSama = true;
						}
						
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) == false &&
								anNAV.getQty().equals(anDISTI.getQty()) &&
								anNAV.getAmount() == anDISTI.getAmount() ){
								noInvoiceSama = true;
//								qtySama = true;
//								amountSama = true;
						}
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) &&
								anNAV.getQty().equals(anDISTI.getQty()) == false &&
								anNAV.getAmount() != anDISTI.getAmount() ){
								noInvoiceSama = true;
//								noItemSama = true;
						}
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) == false &&
								anNAV.getQty().equals(anDISTI.getQty()) == false &&
								anNAV.getAmount() == anDISTI.getAmount() ){
								noInvoiceSama = true;
//								amountSama = true;
						}
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) == false &&
								anNAV.getQty().equals(anDISTI.getQty()) &&
								anNAV.getAmount() != anDISTI.getAmount() ){
								noInvoiceSama = true;
//								qtySama = true;
						}
						
						if (anNAV.getNoInvoice().equals(anDISTI.getNoInvoice()) && 
								anNAV.getItemNo().equals(anDISTI.getItemNo()) == false &&
								anNAV.getQty().equals(anDISTI.getQty()) == false &&
								anNAV.getAmount() != anDISTI.getAmount() ){
								noInvoiceSama = true;
						}
						
					}
										
				}
				
				
				if (dataSama == false){
					if (noInvoiceSama == false){
						listDISTITidakSama.add(new RebateHoneywellNAV(anDISTI.getTanggal(), anDISTI.getNoInvoice(), anDISTI.getNoPO(), anDISTI.getItemNo(), anDISTI.getItemDesc(), anDISTI.getQty(),"di DISTI ada, di NAV tidak ada", anDISTI.getAmount(), ""));
					}
				}

			}
        
        
                
        
			PathReport pathReportHasil = new PathReport();
			File outputFileHasil = new File(pathReportHasil.getHasilCompareInvoiceRebate());
	
			
			WritableWorkbook outputFileWorkbookHasil = Workbook.createWorkbook(outputFileHasil);
	
			// Tidak sama
			WritableSheet sheetOut3 = outputFileWorkbookHasil.createSheet("DISTICompareToNAV", 0);
					
			sheetOut3.addCell(new Label(0, 0, "Tanggal Invoice"));
			sheetOut3.addCell(new Label(1, 0, "No Invoice"));
			sheetOut3.addCell(new Label(2, 0, "No PO"));
			sheetOut3.addCell(new Label(3, 0, "Item No"));
			sheetOut3.addCell(new Label(4, 0, "Item Description"));
			sheetOut3.addCell(new Label(5, 0, "Qty"));
			sheetOut3.addCell(new Label(6, 0, "Amount"));
			sheetOut3.addCell(new Label(7, 0, "Keterangan"));
			sheetOut3.addCell(new Label(8, 0, "Keterangan Amount"));
			
			WritableCellFormat numformat = new WritableCellFormat(new NumberFormat("#,##0.00")); 
				
			int vNumRow3 = 1;
			for (RebateHoneywellNAV data : listDISTITidakSama) {					 
				sheetOut3.addCell(new Label(0, vNumRow3, data.getTanggal()));
				sheetOut3.addCell(new Label(1, vNumRow3, data.getNoInvoice()));									
				sheetOut3.addCell(new Label(2, vNumRow3, data.getNoPO()));
				sheetOut3.addCell(new Label(3, vNumRow3, data.getItemNo()));					
				sheetOut3.addCell(new Label(4, vNumRow3, data.getItemDesc()));
				sheetOut3.addCell(new Label(5, vNumRow3, data.getQty()));
				sheetOut3.addCell(new Number(6, vNumRow3, data.getAmount(), numformat));
				sheetOut3.addCell(new Label(7, vNumRow3, data.getKeterangan()));
				sheetOut3.addCell(new Label(8, vNumRow3, data.getKeterangan2()));
				vNumRow3 ++;
			}
			
			// Tidak sama 
			WritableSheet sheetOut2 = outputFileWorkbookHasil.createSheet("NAVCompareToDISTI", 0);
				
			sheetOut2.addCell(new Label(0, 0, "Tanggal Invoice"));
			sheetOut2.addCell(new Label(1, 0, "No Invoice"));
			sheetOut2.addCell(new Label(2, 0, "No PO"));
			sheetOut2.addCell(new Label(3, 0, "Item No"));
			sheetOut2.addCell(new Label(4, 0, "Item Description"));
			sheetOut2.addCell(new Label(5, 0, "Qty"));
			sheetOut2.addCell(new Label(6, 0, "Amount"));
			sheetOut2.addCell(new Label(7, 0, "Keterangan"));
			sheetOut2.addCell(new Label(8, 0, "Keterangan Amount"));
			
			int vNumRow2 = 1;
			for (RebateHoneywellNAV data : listNAVTidakSama) {
				sheetOut2.addCell(new Label(0, vNumRow2, data.getTanggal()));
				sheetOut2.addCell(new Label(1, vNumRow2, data.getNoInvoice()));									
				sheetOut2.addCell(new Label(2, vNumRow2, data.getNoPO()));
				sheetOut2.addCell(new Label(3, vNumRow2, data.getItemNo()));					
				sheetOut2.addCell(new Label(4, vNumRow2, data.getItemDesc()));
				sheetOut2.addCell(new Label(5, vNumRow2, data.getQty()));
				sheetOut2.addCell(new Number(6, vNumRow2, data.getAmount(), numformat));
				sheetOut2.addCell(new Label(7, vNumRow2, data.getKeterangan()));
				sheetOut2.addCell(new Label(8, vNumRow2, data.getKeterangan2()));
				vNumRow2 ++;
			}
	
	
	
			// Sheet yang Sama
			WritableSheet sheetOut = outputFileWorkbookHasil.createSheet("Sama", 0);
			
			sheetOut.addCell(new Label(0, 0, "Tanggal Invoice"));
			sheetOut.addCell(new Label(1, 0, "No Invoice"));
			sheetOut.addCell(new Label(2, 0, "No PO"));
			sheetOut.addCell(new Label(3, 0, "Item No"));
			sheetOut.addCell(new Label(4, 0, "Item Description"));
			sheetOut.addCell(new Label(5, 0, "Qty"));
			sheetOut.addCell(new Label(6, 0, "Amount"));
			sheetOut.addCell(new Label(7, 0, "Keterangan"));
			sheetOut.addCell(new Label(8, 0, "Keterangan Amount"));
			
			
			int vNumRow = 1;
			for (RebateHoneywellNAV data : listSama) {
				sheetOut.addCell(new Label(0, vNumRow, data.getTanggal()));
				sheetOut.addCell(new Label(1, vNumRow, data.getNoInvoice()));									
				sheetOut.addCell(new Label(2, vNumRow, data.getNoPO()));
				sheetOut.addCell(new Label(3, vNumRow, data.getItemNo()));					
				sheetOut.addCell(new Label(4, vNumRow, data.getItemDesc()));
				sheetOut.addCell(new Label(5, vNumRow, data.getQty()));
				sheetOut.addCell(new Number(6, vNumRow, data.getAmount(), numformat));
				sheetOut.addCell(new Label(7, vNumRow3, data.getKeterangan()));
				sheetOut.addCell(new Label(8, vNumRow3, data.getKeterangan2()));
				vNumRow ++;
			}
				 
			outputFileWorkbookHasil.write();
	
			InputStream mediais = new FileInputStream(outputFileHasil);
			AMedia amedia = new AMedia("HasilCompareInvoiceRebate.xls", "xls", "application/vnd.ms-excel", mediais);
			
			Filedownload.save(amedia);
			
			outputFileWorkbookHasil.close();
			
			outputFileHasil.delete();
        }
		
	}
 
}