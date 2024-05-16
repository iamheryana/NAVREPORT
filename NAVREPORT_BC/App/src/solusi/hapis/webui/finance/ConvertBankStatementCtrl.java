package solusi.hapis.webui.finance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;

import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ConvertBankStatementCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	

	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
    	
    	
	}
	
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		if (CommonUtils.isNotEmpty(media)) {


			try {
				List<BankStatement> vData = new ArrayList<BankStatement>();
				String vPeriode = "";
				
				String strSatuBaris = "";  
				
				BufferedReader br = new BufferedReader(new InputStreamReader(media.getStreamData()));  
				strSatuBaris = br.readLine();
				
				String[] strIsiBaris = strSatuBaris.split(";"); 
				//System.out.println(strIsiBaris[0]);
				if (strIsiBaris[0].equals("AccountNo") == true) {
					// CSV untuk Mandiri
					vPeriode = "Mandiri";
					int iterData = 1;
					while ((strSatuBaris = br.readLine()) != null){  
						String[] strDetailSatuBaris = strSatuBaris.split(";"); 						
						
						SimpleDateFormat dfTempTgl = null;
						if (strDetailSatuBaris[2].length() > 11)  {
							dfTempTgl = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
						} else {
						 dfTempTgl = new SimpleDateFormat("dd/MM/yyyy");
						}
						Date masukTgl = dfTempTgl.parse(strDetailSatuBaris[2]);
						
						if(iterData == 1){
							SimpleDateFormat frmPeriod = new SimpleDateFormat("MMyyyy");  
			                String strPeriod = frmPeriod.format(masukTgl);  
							vPeriode = vPeriode+"-"+ strPeriod;
						}
						
						String masukKet = strDetailSatuBaris[3].replaceAll("\\s{2,}", " ");
						
						
						Double masukAmount = new Double(0);
						String DbCr = strDetailSatuBaris[5];
						if (DbCr.equals("0.00") == true){
							DbCr = strDetailSatuBaris[6];
							
							masukAmount = new Double(DbCr) * -1;
						} else {
							masukAmount = new Double(DbCr) ;
						}
						
						
						
						vData.add(new BankStatement(masukTgl, masukKet, masukAmount));
						
						iterData = iterData +1;
					}
					
				} else {
					// CSV untuk BCA
			
					int brs = 0;
					boolean vDone = false;
				    Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(new InputStreamReader(media.getStreamData()));
				    String vTgl = "";
				    String vKet = "";
				    String vTahun = "";
				    
				    for (CSVRecord record : records) {
				    	brs = brs+1;
				    	
				    	if (brs == 5){
				    		vTahun = record.get(0).substring(record.get(0).length()-2, record.get(0).length());
				    		vPeriode ="BCA"+record.get(0).substring(record.get(0).length()-5, record.get(0).length()-3) + vTahun;
						}
						
				    	   	
						if(brs >= 9) {
							String row1 = record.get(0);
							String checkRow1 = ".";
							if(row1 != null && row1.length() > 13 ){
								checkRow1 = row1.substring(0, 12);
							}
							
							if(checkRow1.equals("Saldo Awal :")){
								vDone = true;
							}
							
							if(!vDone){
															
								if(record.get(0).length() == 5 && record.size() == 5){
									String vTempTgl = record.get(0) +"/"+vTahun;
									SimpleDateFormat dfTempTgl = new SimpleDateFormat("dd/MM/yy");
									Date masukTgl = dfTempTgl.parse(vTempTgl);
									
									
									String masukKet = "";
									if(record.get(1).length() > 50){
										masukKet = record.get(1).substring(0,50);
									} else {
										masukKet = record.get(1);
									}
									
									String DbCr = record.get(3).substring(record.get(3).length() - 2, record.get(3).length());
									String nominal = record.get(3).substring(0,record.get(3).length() - 2);
									Double masukAmount = new Double( nominal.replaceAll(",", ""));
									
									if(DbCr.equals("DB")){
										masukAmount = masukAmount * -1;
									}
											
									vData.add(new BankStatement(masukTgl, masukKet, masukAmount));
								} else {
									if(record.get(0).length() == 5){
										if(record.size() == 2){
											vTgl = record.get(0);
											vKet = record.get(1);
										}
										
									} else {
										if(record.size() == 5){
											String vTempTgl = vTgl +"/"+vTahun;
											SimpleDateFormat dfTempTgl = new SimpleDateFormat("dd/MM/yy");
											Date masukTgl = dfTempTgl.parse(vTempTgl);
											
											vKet = vKet+record.get(1);
											
											String masukKet = "";
											if(vKet.length() > 50){
												masukKet = vKet.substring(0,50);
											} else {
												masukKet = vKet;
											}
											
											String DbCr = record.get(3).substring(record.get(3).length() - 2, record.get(3).length());
											String nominal = record.get(3).substring(0,record.get(3).length() - 2);
											Double masukAmount = new Double( nominal.replaceAll(",", ""));
											
											if(DbCr.equals("DB")){
												masukAmount = masukAmount * -1;
											}
											
											vData.add(new BankStatement(masukTgl, masukKet, masukAmount));
										} else {
											vKet = vKet+record.get(1);
										}
									}
								}	
							}
						}
				    	
				    }
				}
				    
			    //for (BankStatement bs : vData) {
			    //	System.out.println(bs.getTanggal()+" === "+bs.getKeterangan()+" === "+bs.getAmount());
			    //}
			    
				if (CommonUtils.isNotEmpty(vData)) {
					try {
						
						//String timeStamp = String.valueOf(System.currentTimeMillis());
						PathReport pathReport = new PathReport(vPeriode);
						File outputFile = new File(pathReport.getBankStatement());

						WritableWorkbook outputFileWorkbook = Workbook
								.createWorkbook(outputFile);

						WritableSheet sheetOut = outputFileWorkbook.createSheet(
								"Sheet", 0);
						
						sheetOut.addCell(new Label(0, 0, "TANGGAL"));
						sheetOut.addCell(new Label(1, 0, "KETERANGAN"));
						sheetOut.addCell(new Label(2, 0, "AMOUNT"));

						WritableCellFormat dateFormat = new jxl.write.WritableCellFormat (new jxl.write.DateFormat("dd-MM-yyyy"));
						WritableCellFormat numformat = new WritableCellFormat(new NumberFormat("#,##0.00")); 
						int vNumRow = 1;
						 for (BankStatement bs : vData) {
							 
							sheetOut.addCell(new DateTime(0, vNumRow, bs.getTanggal(), dateFormat));
							sheetOut.addCell(new Label(1, vNumRow, bs.getKeterangan()));									
							sheetOut.addCell(new Number(2, vNumRow, bs.getAmount(),numformat));
							vNumRow ++;
						}
						
						outputFileWorkbook.write();

						InputStream mediais = new FileInputStream(outputFile);
						AMedia amedia = new AMedia("BackStatement"+vPeriode+".xls", "xls", "application/vnd.ms-excel", mediais);
						
						Filedownload.save(amedia);
						
						outputFileWorkbook.close();
						
						outputFile.delete();

					} catch (WriteException e) {
						Messagebox.show("Error : " + e.getMessage(), "Error",
								Messagebox.OK, Messagebox.ERROR);
					}
					
				} else {
					Messagebox.show("Proses Upload Selesai ...",
							"Information", Messagebox.OK, Messagebox.INFORMATION);
				}
	
			    ////
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);
			}
		}
	
	}
}