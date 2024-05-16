package solusi.hapis.webui.sc;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class ZebraMCCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowZebraMC;

	protected Textbox lbl1;	

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		

    	
	}
		
	

	public void onClick$btnOK1(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		//String vProsesId = String.valueOf(System.currentTimeMillis());
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();

//				List<Tmp01rebate> listData = new ArrayList<Tmp01rebate>();
				
				for (int i = 1; i < vJmlData; i++){

					if(i >= 1){
//						if(		CommonUtils.isNotEmpty(sheet.getCell(0,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(5,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheet.getCell(8,i).getContents())){
							
//					System.out.println("sheet.getCell(0,"+i+")  --> "+(sheet.getCell(0,i).getContents()));
//					System.out.println("sheet.getCell(1,"+i+")  --> "+(sheet.getCell(1,i).getContents()));
//					System.out.println("sheet.getCell(2,"+i+")  --> "+(sheet.getCell(2,i).getContents()));
						
						SimpleDateFormat dfTgl = new SimpleDateFormat("d-MMM-yy");
						
						String vQuoteNo = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(0,i).getContents())){
							vQuoteNo = sheet.getCell(0,i).getContents();
						}
												
						String vExpireContractNo = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents())){
							vExpireContractNo = sheet.getCell(1,i).getContents();
						}
						
						Date vContractExpireDate = null;
						if (CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents())){
							vContractExpireDate = dfTgl.parse(sheet.getCell(2,i).getContents());
						}
						
						String vExpireBillingFreq = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents())){
							vExpireBillingFreq = sheet.getCell(3,i).getContents();
						}
						
						String vRenewalBillingFreq = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(4,i).getContents())){
							vRenewalBillingFreq = sheet.getCell(4,i).getContents();
						}
						
						String vDistributorName = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(5,i).getContents())){
							vDistributorName = sheet.getCell(5,i).getContents();
						}
						
						String vResellerName = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents())){
							vResellerName = sheet.getCell(6,i).getContents();
						}
						
						String vEndUserName = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(7,i).getContents())){
							vEndUserName = sheet.getCell(7,i).getContents();
						}
						
						Date vRenewalStartDate = null;
						if (CommonUtils.isNotEmpty(sheet.getCell(8,i).getContents())){
							vRenewalStartDate = dfTgl.parse(sheet.getCell(8,i).getContents());
						}
						
						Date vRenewalEndDate = null;
						if (CommonUtils.isNotEmpty(sheet.getCell(9,i).getContents())){
							vRenewalEndDate = dfTgl.parse(sheet.getCell(9,i).getContents());
						}
						
						Date vQuoteValidUntil = null;
						if (CommonUtils.isNotEmpty(sheet.getCell(10,i).getContents())){
							vQuoteValidUntil = dfTgl.parse(sheet.getCell(10,i).getContents());
						}
						
						int vLineNo = 0;
						if (CommonUtils.isNotEmpty(sheet.getCell(11,i).getContents())){
							vLineNo = Integer.valueOf(sheet.getCell(11,i).getContents());
						}
						
						int vLineQty = 0;
						if (CommonUtils.isNotEmpty(sheet.getCell(12,i).getContents())){
							vLineQty = Integer.valueOf(sheet.getCell(12,i).getContents());
						}
						
						String vServiceSKU = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(13,i).getContents())){
							vServiceSKU = sheet.getCell(13,i).getContents();
						}
						
						String vSKUDescription = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(14,i).getContents())){
							vSKUDescription = sheet.getCell(14,i).getContents();
						}
						
						String vItemType = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(15,i).getContents())){
							vItemType = sheet.getCell(15,i).getContents();
						}
						
						int vQtyRequested = 0;
						if (CommonUtils.isNotEmpty(sheet.getCell(16,i).getContents())){
							vQtyRequested = Integer.valueOf(sheet.getCell(16,i).getContents());
						}
						
						String vProduct = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(17,i).getContents())){
							vProduct = sheet.getCell(17,i).getContents();
						}
						
						String vSerialNo = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(18,i).getContents())){
							vSerialNo = sheet.getCell(18,i).getContents();
						}
						
						
						Date vLineStartDate = null;
						if (CommonUtils.isNotEmpty(sheet.getCell(19,i).getContents())){
							vLineStartDate = dfTgl.parse(sheet.getCell(19,i).getContents());
						}
						
						Date vLineEndDate = null;
						if (CommonUtils.isNotEmpty(sheet.getCell(20,i).getContents())){
							vLineEndDate = dfTgl.parse(sheet.getCell(20,i).getContents());
						}
						
						Date vEOSL = null;
						if (CommonUtils.isNotEmpty(sheet.getCell(21,i).getContents())){
							vEOSL = dfTgl.parse(sheet.getCell(21,i).getContents());
						}
			
						BigDecimal vLastPrice = new BigDecimal(0.00);
						if (CommonUtils.isNotEmpty(sheet.getCell(22,i).getContents())){
							vLastPrice = new BigDecimal (sheet.getCell(22,i).getContents());
						}
						
						BigDecimal vProRataListPrice = new BigDecimal(0.00);
						if (CommonUtils.isNotEmpty(sheet.getCell(23,i).getContents())){
							vProRataListPrice = new BigDecimal (sheet.getCell(23,i).getContents());
						}
						
						BigDecimal vTotalDiscount = new BigDecimal(0);
						if (CommonUtils.isNotEmpty(sheet.getCell(24,i).getContents())){
							vTotalDiscount = new BigDecimal (sheet.getCell(24,i).getContents());
						}
						
						BigDecimal vUnitPriceAfterDiscount = new BigDecimal(0.00);
						if (CommonUtils.isNotEmpty(sheet.getCell(25,i).getContents())){
							vUnitPriceAfterDiscount = new BigDecimal (sheet.getCell(25,i).getContents());
						}
								
						BigDecimal vSCFperUnit = new BigDecimal(0.00);
						if (CommonUtils.isNotEmpty(sheet.getCell(26,i).getContents())){
							vSCFperUnit = new BigDecimal (sheet.getCell(26,i).getContents());
						}

						String vCurrencyCode = "";
						if (CommonUtils.isNotEmpty(sheet.getCell(27,i).getContents())){
							vCurrencyCode = sheet.getCell(27,i).getContents();
						}
						
						
						Date vSCFAppliesAfter = null;
						if (CommonUtils.isNotEmpty(sheet.getCell(28,i).getContents())){
							vSCFAppliesAfter = dfTgl.parse(sheet.getCell(28,i).getContents());
						}
								
								
						
						System.out.println("A. vQuoteNo Row :"+i+")  --> "+vQuoteNo);
						System.out.println("B. vExpireContractNo Row :"+i+")  --> "+vExpireContractNo);
						System.out.println("C. vContractExpireDate Row :"+i+")  --> "+vContractExpireDate);
						System.out.println("D. vExpireBillingFreq Row :"+i+")  --> "+vExpireBillingFreq);
						System.out.println("E. vRenewalBillingFreq Row :"+i+")  --> "+vRenewalBillingFreq);
						System.out.println("F. vDistributorName Row :"+i+")  --> "+vDistributorName);						
						System.out.println("G. vResellerName Row :"+i+")  --> "+vResellerName);
						System.out.println("H. vEndUserName Row :"+i+")  --> "+vEndUserName);						
						System.out.println("I. vRenewalStartDate Row :"+i+")  --> "+vRenewalStartDate);
						System.out.println("J. vRenewalEndDate Row :"+i+")  --> "+vRenewalEndDate);						
						System.out.println("K. vQuoteValidUntil Row :"+i+")  --> "+vQuoteValidUntil);
						System.out.println("L. vLineNo Row :"+i+")  --> "+vLineNo);
						System.out.println("M. vLineQty Row :"+i+")  --> "+vLineQty);
						System.out.println("N. vServiceSKU Row :"+i+")  --> "+vServiceSKU);
						System.out.println("O. vSKUDescription Row :"+i+")  --> "+vSKUDescription);
						System.out.println("P. vItemType Row :"+i+")  --> "+vItemType);
						System.out.println("Q. vQtyRequested Row :"+i+")  --> "+vQtyRequested);						
						System.out.println("R. vProduct Row :"+i+")  --> "+vProduct);
						System.out.println("S. vSerialNo Row :"+i+")  --> "+vSerialNo);
						System.out.println("T. vLineStartDate Row :"+i+")  --> "+vLineStartDate);
						System.out.println("U. vLineEndDate Row :"+i+")  --> "+vLineEndDate);
						System.out.println("V. vEOSL Row :"+i+")  --> "+vEOSL);
						System.out.println("W. vLastPrice Row :"+i+")  --> "+vLastPrice);
						System.out.println("X. vProRataListPrice Row :"+i+")  --> "+vProRataListPrice);
						System.out.println("Y. vTotalDiscount Row :"+i+")  --> "+vTotalDiscount);
						System.out.println("Z. vUnitPriceAfterDiscount Row :"+i+")  --> "+vUnitPriceAfterDiscount);
						System.out.println("AA. vSCFperUnit Row :"+i+")  --> "+vSCFperUnit);
						System.out.println("AB. vCurrencyCode Row :"+i+")  --> "+vCurrencyCode);
						System.out.println("AC. vSCFAppliesAfter Row :"+i+")  --> "+vSCFAppliesAfter);
						
						System.out.println("======================================================================================");

					
//						}
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

	
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException, IOException, RowsExceededException, WriteException {
			

	}
 
}