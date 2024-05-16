package solusi.hapis.webui.accounting;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.tabel.model.Tmp04efaktur;
import solusi.hapis.backend.tabel.model.Tmp05navfaktur;
import solusi.hapis.backend.tabel.service.Tmp04efakturService;
import solusi.hapis.backend.tabel.service.Tmp05navfakturService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ProsesRekonPPNCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	private Tmp04efakturService tmp04efakturService;
	private Tmp05navfakturService tmp05navfakturService;
	

	List<Tmp04efaktur> listEfaktur = new ArrayList<Tmp04efaktur>();
	List<Tmp05navfaktur> listInvoiceNAV = new ArrayList<Tmp05navfaktur>();
	
	private PathReport pathRpt;
	private String timeStamp;
	
	protected Textbox lbl1;
	protected Textbox lbl2;
	
	protected Datebox dbTglFrom;  
	protected Datebox dbTglTo;
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		timeStamp = String.valueOf(System.currentTimeMillis());
    	
		dbTglTo.setValue((new Date()));   
    	
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


				if(CommonUtils.isNotEmpty(listEfaktur)){
					tmp04efakturService.save(listEfaktur);		
					listEfaktur.clear();
				}

				
				
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
								
								Tmp04efaktur anData = new Tmp04efaktur(
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
																timeStamp);
								listEfaktur.add(anData);
							}
						}
					
					}
				
				}

				workbook.close();
				
				if(CommonUtils.isNotEmpty(listEfaktur)){
					tmp04efakturService.save(listEfaktur);					
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
	
//	public void onClick$btnOK2(Event event) throws InterruptedException, ParseException {
//		Media media = Fileupload.get("Please select a File", "Upload");
//		
//		if (CommonUtils.isNotEmpty(media)) {
//
//			try {
//				
//				
//				// Membaca Excel dari file yang di Upload
//				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
//				Sheet sheet = workbook.getSheet(0);
//
//				int vJmlData = sheet.getRows();
//				
//				if(CommonUtils.isNotEmpty(listInvoiceNAV)){
//					tmp05navfakturService.save(listInvoiceNAV);		
//					listInvoiceNAV.clear();
//				}
//				
//				// Looping untuk memasukan Invoice
//				for (int i = 1; i < vJmlData; i++){
//					if(i > 6){
//						if(		CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(4,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(8,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(9,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(10,i).getContents())||
//								CommonUtils.isNotEmpty(sheet.getCell(15,i).getContents())||
//								CommonUtils.isNotEmpty(sheet.getCell(16,i).getContents())||
//								CommonUtils.isNotEmpty(sheet.getCell(17,i).getContents())
//								){
//							String TempNoFP = sheet.getCell(8,i).getContents();
//							String navNoFP =  CommonUtils.convertNoFakturPajak(TempNoFP);
//							
//							String navDpp = sheet.getCell(9,i).getContents().replaceAll(",", "").replaceAll("-", "");
//							String navPpn = sheet.getCell(10,i).getContents().replaceAll(",", "").replaceAll("-", "");
//							
//							Tmp05navfaktur anData = new Tmp05navfaktur(
//																sheet.getCell(1,i).getContents(), 
//																sheet.getCell(2,i).getContents(),
//																sheet.getCell(4,i).getContents(),
//																sheet.getCell(3,i).getContents(),																
//																sheet.getCell(6,i).getContents(), 
//																navNoFP,
//																sheet.getCell(8,i).getContents(),
//																navDpp,
//																navPpn,
//																sheet.getCell(15,i).getContents(),
//																sheet.getCell(16,i).getContents(),
//																sheet.getCell(17,i).getContents(),
//																timeStamp);
//							listInvoiceNAV.add(anData);
//						}
//					
//					}
//				}
//				
//			
////				Looping untuk memasukan Credit Memo yang Invoicenya berbeda periode
////				for (int i = 1; i < vJmlData; i++){
////					if(i > 6){
////						if(		CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents()) ||
////								CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents()) ||
////								CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents()) ||
////								CommonUtils.isNotEmpty(sheet.getCell(4,i).getContents()) ||
////								CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents()) ||
////								CommonUtils.isNotEmpty(sheet.getCell(8,i).getContents()) ||
////								CommonUtils.isNotEmpty(sheet.getCell(9,i).getContents()) ||
////								CommonUtils.isNotEmpty(sheet.getCell(10,i).getContents())||
////								CommonUtils.isNotEmpty(sheet.getCell(15,i).getContents())||
////								CommonUtils.isNotEmpty(sheet.getCell(16,i).getContents())||
////								CommonUtils.isNotEmpty(sheet.getCell(17,i).getContents())
////								){
////							if (sheet.getCell(3,i).getContents().equals("CREDIT MEMO")){
////								boolean addCreditMemo = true;
////								
////								for (InvoiceNAVList inv : listInvoiceNAV){
////									if(CommonUtils.isNotEmpty(inv.getNoCreditMemo())){
////										if(inv.getNoCreditMemo().equals(sheet.getCell(2,i).getContents())){
////											addCreditMemo = false;
////											break;
////										}
////									}
////								}
////								
////								if(addCreditMemo){
////									listInvoiceNAV.add(new InvoiceNAVList(	
////																	sheet.getCell(1,i).getContents(), 
////																	sheet.getCell(2,i).getContents(),
////																	sheet.getCell(4,i).getContents(),
////																	sheet.getCell(3,i).getContents(),																
////																	sheet.getCell(6,i).getContents(), 
////																	sheet.getCell(8,i).getContents(),
////																	sheet.getCell(9,i).getContents(),
////																	sheet.getCell(10,i).getContents(),
////																	sheet.getCell(15,i).getContents(),
////																	sheet.getCell(16,i).getContents(),
////																	sheet.getCell(17,i).getContents()
////											));
////								}
////							}
////						}
////					}
////				}
//				
//				
//				workbook.close();
//
//				if(CommonUtils.isNotEmpty(listInvoiceNAV)){
//					tmp05navfakturService.save(listInvoiceNAV);					
//				}
//				
//				
//				lbl2.setValue(media.getName()+ " Sudah berhasil terupload.");
//
//
//			} catch (BiffException e) {
//				Messagebox.show("Not an Excel File : " + media.getName(),
//						"Error", Messagebox.OK, Messagebox.ERROR);
//			} catch (IOException e) {
//				Messagebox.show("Error : " + e.getMessage(), "Error",
//						Messagebox.OK, Messagebox.ERROR);
//
//			}
//			
//
//		}
//	
//	}
	
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
		if(CommonUtils.isNotEmpty(dbTglFrom.getValue()) == true){  
			vTglFrom = dbTglFrom.getValue();
		}   
		
		Date vTglTo = vTanggal;   
		if(CommonUtils.isNotEmpty(dbTglTo.getValue()) == true){  
			vTglTo = dbTglTo.getValue();
		}
				

		
		String vCompany = "AUTOJAYA";
		if (StringUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01019_ReconPajak.jasper";
		
		
    	param.put("TglInvFrom",  vTglFrom); 
		param.put("TglInvTo",  vTglTo);  
		param.put("Company",  vCompany); 
		param.put("Tipe",  "SALES"); 
		
		pathRpt = new PathReport(timeStamp);
		
		File outputFile = new File(pathRpt.getPPNRegister());
		
		@SuppressWarnings("resource")
		OutputStream outStream = new FileOutputStream(outputFile);
        outStream.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "XLS"));
        
		if (CommonUtils.isNotEmpty(outputFile)) {

			try {
				
				
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(outputFile);
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();
				
				if(CommonUtils.isNotEmpty(listInvoiceNAV)){
					tmp05navfakturService.save(listInvoiceNAV);		
					listInvoiceNAV.clear();
				}
				
				// Looping untuk memasukan Invoice
				for (int i = 1; i < vJmlData; i++){
					if(i > 6){
						if(		CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(4,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(8,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(9,i).getContents()) ||
								CommonUtils.isNotEmpty(sheet.getCell(10,i).getContents())||
								CommonUtils.isNotEmpty(sheet.getCell(15,i).getContents())||
								CommonUtils.isNotEmpty(sheet.getCell(16,i).getContents())||
								CommonUtils.isNotEmpty(sheet.getCell(17,i).getContents())
								){
							String TempNoFP = sheet.getCell(8,i).getContents();
							String navNoFP =  CommonUtils.convertNoFakturPajak(TempNoFP);
							
							String navDpp = sheet.getCell(9,i).getContents().replaceAll(",", "").replaceAll("-", "");
							String navPpn = sheet.getCell(10,i).getContents().replaceAll(",", "").replaceAll("-", "");
							
							Tmp05navfaktur anData = new Tmp05navfaktur(
																sheet.getCell(1,i).getContents(), 
																sheet.getCell(2,i).getContents(),
																sheet.getCell(4,i).getContents(),
																sheet.getCell(3,i).getContents(),																
																sheet.getCell(6,i).getContents(), 
																navNoFP,
																sheet.getCell(8,i).getContents(),
																navDpp,
																navPpn,
																sheet.getCell(15,i).getContents(),
																sheet.getCell(16,i).getContents(),
																sheet.getCell(17,i).getContents(),
																timeStamp);
							listInvoiceNAV.add(anData);
						}
					
					}
				}
				
				workbook.close();

				if(CommonUtils.isNotEmpty(listInvoiceNAV)){
					tmp05navfakturService.save(listInvoiceNAV);					
				}
				
			} catch (BiffException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}
			
	        outputFile.delete();	

		}
		
        
		String jasperRptFinal = "/solusi/hapis/webui/reports/accounting/01039_HasilPPNRekonsiliasi.jasper";
		param.put("prosesId",  timeStamp);	
		
		
		new JReportGeneratorWindow(param, jasperRptFinal, "XLS", 1); 
		
		

        if(CommonUtils.isNotEmpty(listEfaktur)){
			tmp04efakturService.delete(listEfaktur);					
		}
        
        if(CommonUtils.isNotEmpty(listInvoiceNAV)){	
			tmp05navfakturService.delete(listInvoiceNAV);					
		}
        
        
//		System.out.println("efakturNoFP");
//		//for (EFakturList bs : listEfaktur) {
//		//	System.out.println(bs.getNamaCust()+" === "+bs.getNoFakturPajak()+" === "+bs.getTanggal()+" === "+bs.getStatus()+" === "+bs.getDpp()+" === "+bs.getPpn());
//		//}
//		
//		//for (InvoiceNAVList bs : listInvoiceNAV) {
//		//	System.out.println(bs.getTglInvoice()+" === "+bs.getNoInvoice()+" === "+bs.getNoCreditMemo()+" === "+bs.getTipeDok()+" === "+bs.getNoFakturPajak()+" === "+bs.getCustName()+" === "+bs.getDpp()+" === "+bs.getPpn());
//		//}
//		if (CommonUtils.isNotEmpty(listEfaktur) && CommonUtils.isNotEmpty(listInvoiceNAV) ) {
//
//				List<EFakturList>  listEfakturSama = new ArrayList<EFakturList>();
//				List<EFakturList>  listEfakturTidakSama = new ArrayList<EFakturList>();
//				
//				for (EFakturList anEFaktur : listEfaktur) {
//					//System.out.println("    -->>>>>"+anEFaktur.getNoFakturPajak());
//					String noFP = anEFaktur.getNoFakturPajak().substring(4, anEFaktur.getNoFakturPajak().length());
//					boolean NoFPSama = false; 
//					boolean StatusSama = false; 
//					boolean PpnSama = false; 
//					boolean DataSama = false; 
//					for (InvoiceNAVList anInvoiceNAV : listInvoiceNAV) {
//						String efakturNoFP =  CommonUtils.convertNoFakturPajak(noFP);
//						String navNoFP =  CommonUtils.convertNoFakturPajak(anInvoiceNAV.getNoFakturPajak());
//
//						
//						
//						
//						if (efakturNoFP.equals(navNoFP)){							
//							NoFPSama = true;							
//						}
//						
//						if( ((anEFaktur.getStatus().equals("Batal") && anInvoiceNAV.getNoCreditMemo().trim().length() > 0) ||
//							(anEFaktur.getStatus().equals("Normal") && anInvoiceNAV.getNoCreditMemo().trim().length() <= 0))
//							&& anEFaktur.getStatusApproval().equals("Approval Sukses")){
//							StatusSama = true;
//						}
//						
//						String navPpn = anInvoiceNAV.getPpn().replaceAll(",", "").replaceAll("-", "");
//						if(anEFaktur.getPpn().equals(navPpn)){
//							PpnSama = true;
//						}
//						
//						if(NoFPSama && StatusSama && PpnSama){
//							System.out.println("efakturNoFP = "+efakturNoFP);
//							System.out.println("navNoFP = "+navNoFP);
//							System.out.println("anEFaktur.getStatus() = "+anEFaktur.getStatus());
//							System.out.println("anInvoiceNAV.getNoCreditMemo().trim().length() = "+anInvoiceNAV.getNoCreditMemo().trim().length());
//							System.out.println("=============");
//							
//							DataSama = true;
//							break;
//						}
//					}
//					
//					if (DataSama){
//						listEfakturSama.add(new EFakturList(anEFaktur.getNamaCust(), anEFaktur.getNoFakturPajak(), anEFaktur.getTanggal(), anEFaktur.getStatus(), anEFaktur.getDpp(), anEFaktur.getPpn(), anEFaktur.getReferansi(), anEFaktur.getStatusApproval()));
//					} else {
//						listEfakturTidakSama.add(new EFakturList(anEFaktur.getNamaCust(), anEFaktur.getNoFakturPajak(), anEFaktur.getTanggal(), anEFaktur.getStatus(), anEFaktur.getDpp(), anEFaktur.getPpn(), anEFaktur.getReferansi(), anEFaktur.getStatusApproval()));
//					}
//					
//				}
//				
//				List<InvoiceNAVList>  listNAVInvoiceTidakSama = new ArrayList<InvoiceNAVList>();
//				
//				for (InvoiceNAVList anInvoiceNAV : listInvoiceNAV) {
//					boolean NoFPSama = false; 
//					boolean StatusSama = false; 
//					boolean PpnSama = false; 
//					boolean DataSama = false; 
//					
//					System.out.println("Test = "+anInvoiceNAV.getNoInvoice());
//					
//					for (EFakturList anEFaktur : listEfaktur) {
//						String noFP = anEFaktur.getNoFakturPajak().substring(4, anEFaktur.getNoFakturPajak().length());
//						
//						String efakturNoFP =  CommonUtils.convertNoFakturPajak(noFP);
//						String navNoFP =  CommonUtils.convertNoFakturPajak(anInvoiceNAV.getNoFakturPajak());
//						if (efakturNoFP.equals(navNoFP)){
//							
//							NoFPSama = true;
//						}
//						
//						if( ((anEFaktur.getStatus().equals("Batal") && anInvoiceNAV.getNoCreditMemo().trim().length() > 0) ||
//							(anEFaktur.getStatus().equals("Normal") && anInvoiceNAV.getNoCreditMemo().trim().length() <= 0))
//							&& anEFaktur.getStatusApproval().equals("Approval Sukses")){
//							StatusSama = true;
//						}
//						
//						String navPpn = anInvoiceNAV.getPpn().replaceAll(",", "").replaceAll("-", "");
//						if(anEFaktur.getPpn().equals(navPpn)){
//							PpnSama = true;
//						}
//						
//						if(NoFPSama && StatusSama && PpnSama){
//							System.out.println("Masuk Sini 1");
//							DataSama = true;
//							break;
//						}
//					}
//					
//					if (DataSama == false){
//						System.out.println("Masuk Sini 2");
//						listNAVInvoiceTidakSama.add(new InvoiceNAVList(anInvoiceNAV.getTglInvoice(), anInvoiceNAV.getNoInvoice(), anInvoiceNAV.getNoCreditMemo(), anInvoiceNAV.getTipeDok(), anInvoiceNAV.getCustName(), anInvoiceNAV.getNoFakturPajak(), anInvoiceNAV.getDpp(), anInvoiceNAV.getPpn(), anInvoiceNAV.getNoInvoiceCancel(), anInvoiceNAV.getTglInvoiceCancel(), anInvoiceNAV.getNoFakturPajakCancel()));
//					}
//				}
//			
//			try {
//				
//				PathReport pathReport = new PathReport();
//				
//				File outputFile = new File(pathReport.getProsesRekonPPN());
//
//				
//				WritableWorkbook outputFileWorkbook = Workbook.createWorkbook(outputFile);
//
//				// Tidak sama
//				WritableSheet sheetOut3 = outputFileWorkbook.createSheet("NAVAdaEfakturTidakAda", 0);
//
//					
//				sheetOut3.addCell(new Label(0, 0, "Tanggal Invoice"));
//				sheetOut3.addCell(new Label(1, 0, "No Invoice"));
//				sheetOut3.addCell(new Label(2, 0, "No Credit Memo"));
//				sheetOut3.addCell(new Label(3, 0, "Tipe Dokumen"));
//				sheetOut3.addCell(new Label(4, 0, "No Invoice Cancel"));
//				sheetOut3.addCell(new Label(5, 0, "Tgl Invoice Cancel"));
//				sheetOut3.addCell(new Label(6, 0, "No Faktur Pajak Cancel"));				
//				sheetOut3.addCell(new Label(7, 0, "Customer"));
//				sheetOut3.addCell(new Label(8, 0, "No Faktur Pajak"));
//				sheetOut3.addCell(new Label(9, 0, "Dpp"));
//				sheetOut3.addCell(new Label(10, 0, "Ppn"));
//
//				 
//				int vNumRow3 = 1;
//				for (InvoiceNAVList data : listNAVInvoiceTidakSama) {					 
//					sheetOut3.addCell(new Label(0, vNumRow3, data.getTglInvoice()));
//					sheetOut3.addCell(new Label(1, vNumRow3, data.getNoInvoice()));									
//					sheetOut3.addCell(new Label(2, vNumRow3, data.getNoCreditMemo()));
//					sheetOut3.addCell(new Label(3, vNumRow3, data.getTipeDok()));					
//					sheetOut3.addCell(new Label(4, vNumRow3, data.getNoInvoiceCancel()));
//					sheetOut3.addCell(new Label(5, vNumRow3, data.getTglInvoiceCancel()));
//					sheetOut3.addCell(new Label(6, vNumRow3, data.getNoFakturPajakCancel()));					
//					sheetOut3.addCell(new Label(7, vNumRow3, data.getCustName()));
//					sheetOut3.addCell(new Label(8, vNumRow3, data.getNoFakturPajak()));
//					sheetOut3.addCell(new Label(9, vNumRow3, data.getDpp()));
//					sheetOut3.addCell(new Label(10, vNumRow3, data.getPpn()));
//					vNumRow3 ++;
//				}
//				
//				// Tidak sama 
//				WritableSheet sheetOut2 = outputFileWorkbook.createSheet("EfakturAdaNAVTidakAda", 0);
//					
//				sheetOut2.addCell(new Label(0, 0, "Nama Customer"));
//				sheetOut2.addCell(new Label(1, 0, "No Faktur Pajak"));
//				sheetOut2.addCell(new Label(2, 0, "Tanggal Faktur Pajak"));
//				sheetOut2.addCell(new Label(3, 0, "Status"));
//				sheetOut2.addCell(new Label(4, 0, "DPP"));
//				sheetOut2.addCell(new Label(5, 0, "PPN"));
//				sheetOut2.addCell(new Label(6, 0, "Referensi"));
//				sheetOut2.addCell(new Label(7, 0, "Status Approval"));
//				
//				int vNumRow2 = 1;
//				for (EFakturList data : listEfakturTidakSama) {
//					sheetOut2.addCell(new Label(0, vNumRow2, data.getNamaCust()));
//					sheetOut2.addCell(new Label(1, vNumRow2, data.getNoFakturPajak()));									
//					sheetOut2.addCell(new Label(2, vNumRow2, data.getTanggal()));
//					sheetOut2.addCell(new Label(3, vNumRow2, data.getStatus()));
//					sheetOut2.addCell(new Label(4, vNumRow2, data.getDpp()));
//					sheetOut2.addCell(new Label(5, vNumRow2, data.getPpn()));
//					sheetOut2.addCell(new Label(6, vNumRow2, data.getReferansi()));
//					sheetOut2.addCell(new Label(7, vNumRow2, data.getStatusApproval()));
//					vNumRow2 ++;
//				}
//		
//
//
//				// Sheet yang Sama
//				WritableSheet sheetOut = outputFileWorkbook.createSheet("EFakturSama", 0);
//				
//				sheetOut.addCell(new Label(0, 0, "Nama Customer"));
//				sheetOut.addCell(new Label(1, 0, "No Faktur Pajak"));
//				sheetOut.addCell(new Label(2, 0, "Tanggal Faktur Pajak"));
//				sheetOut.addCell(new Label(3, 0, "Status"));
//				sheetOut.addCell(new Label(4, 0, "DPP"));
//				sheetOut.addCell(new Label(5, 0, "PPN"));
//				sheetOut.addCell(new Label(6, 0, "Referensi"));
//				sheetOut.addCell(new Label(7, 0, "Status Approval"));
//				
//				int vNumRow = 1;
//				for (EFakturList data : listEfakturSama) {
//					sheetOut.addCell(new Label(0, vNumRow, data.getNamaCust()));
//					sheetOut.addCell(new Label(1, vNumRow, data.getNoFakturPajak()));									
//					sheetOut.addCell(new Label(2, vNumRow, data.getTanggal()));
//					sheetOut.addCell(new Label(3, vNumRow, data.getStatus()));
//					sheetOut.addCell(new Label(4, vNumRow, data.getDpp()));
//					sheetOut.addCell(new Label(5, vNumRow, data.getPpn()));
//					sheetOut.addCell(new Label(6, vNumRow, data.getReferansi()));
//					sheetOut.addCell(new Label(7, vNumRow, data.getStatusApproval()));
//					vNumRow ++;
//				}
//					 
//				outputFileWorkbook.write();
//
//				InputStream mediais = new FileInputStream(outputFile);
//				AMedia amedia = new AMedia("HasilRekonsiliasiPPNKeluaran.xls", "xls", "application/vnd.ms-excel", mediais);
//				
//				Filedownload.save(amedia);
//				
//				outputFileWorkbook.close();
//				
//				outputFile.delete();
//
//			} catch (WriteException e) {
//				Messagebox.show("Error : " + e.getMessage(), "Error",
//						Messagebox.OK, Messagebox.ERROR);
//			} catch (IOException e) {
//				Messagebox.show("Error : " + e.getMessage(), "Error",
//						Messagebox.OK, Messagebox.ERROR);
//			}		
//		} 		   
	}
}