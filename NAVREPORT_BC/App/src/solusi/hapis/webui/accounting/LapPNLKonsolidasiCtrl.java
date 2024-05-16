package solusi.hapis.webui.accounting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.parameter.service.SelectQueryService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LapPNLKonsolidasiCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	private  List<PNLList>  listPNLAJ;
	private  List<PNLList>  listPNLAdjAJ;

	private  List<PNLList>  listPNLSP;
	private  List<PNLList>  listPNLAdjSP;
	
	private  List<PNLList>  listNERACAAJ;
	private  List<PNLList>  listNERACAAdjAJ;

	private  List<PNLList>  listNERACASP;
	private  List<PNLList>  listNERACAAdjSP;
	

	protected Textbox lbl1;
	protected Textbox lbl2;
	protected Textbox lbl3;
	protected Textbox lbl4;
	
	protected Textbox lbl1Neraca;
	protected Textbox lbl2Neraca;
	protected Textbox lbl3Neraca;
	protected Textbox lbl4Neraca;
	
	private Double vSalesAJ; 
	private Double vSalesSP;
	private Double vCOGSAJ;
	private Double vCOGSSP;
	private Double vRebateAJ;
	private Double vRebateSP;
	
	protected Radiogroup rdgAmount;	 
	protected Radio rdAmt1;
	protected Radio rdAmt2;
	protected Radio rdAmt3;
	
	protected Datebox dbPeriode;
	
	protected Bandbox  cmbCab;
	protected Listbox listCabang;
	protected String vCabang = "ALL";
	
	private PathReport pathRpt;
	private PathReport pathRptLastYear;
	
	private String timeStamp;
	
	private SelectQueryService selectQueryService;
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		listPNLAJ = new ArrayList<PNLList>();
		listPNLSP = new ArrayList<PNLList>();
		listPNLAdjAJ = new ArrayList<PNLList>();
		listPNLAdjSP = new ArrayList<PNLList>();
		
		listNERACAAJ = new ArrayList<PNLList>();
		listNERACASP = new ArrayList<PNLList>();
		listNERACAAdjAJ = new ArrayList<PNLList>();
		listNERACAAdjSP = new ArrayList<PNLList>();
		
		
		vSalesAJ = new Double(0);
		vSalesSP = new Double(0);
		vCOGSAJ = new Double(0);
		vCOGSSP = new Double(0);
		
		vRebateAJ = new Double(0);
		vRebateSP = new Double(0);
		
    	rdAmt2.setSelected(true); 
    	
    	dbPeriode.setValue((new Date()));   
    	
    	Bandpopup popup1 = new Bandpopup();
			listCabang = new Listbox();
			listCabang.setMold("paging");
			listCabang.setAutopaging(true);
			listCabang.setWidth("250px");
			listCabang.addEventListener(Events.ON_SELECT, selectCabang());
			listCabang.setParent(popup1);
		popup1.setParent(cmbCab);
	        
		listCabang.appendItem("ALL", "ALL");
		
		List<Object[]> vResult = selectQueryService.QueryCabang2();
		if(CommonUtils.isNotEmpty(vResult)){
			for(Object[] aRslt : vResult){
				listCabang.appendItem(aRslt[0].toString(),aRslt[1].toString());
			}
		}
		
		
		cmbCab.setValue(listCabang.getItemAtIndex(0).getLabel());
		listCabang.setSelectedItem(listCabang.getItemAtIndex(0));
    	
    	
    	
    	timeStamp = String.valueOf(System.currentTimeMillis());
    	pathRpt = new PathReport(timeStamp);
    	
	}

	private EventListener selectCabang() {
		return new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				cmbCab.setValue(listCabang.getSelectedItem().getLabel());
				vCabang = listCabang.getSelectedItem().getValue().toString();
				cmbCab.close();
			}
		};
	}
	
	public void onClick$btnOK1(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				//System.out.println("listPNLAJ = "+CommonUtils.isNotEmpty(listPNLAJ));
				//System.out.println("Clear =====================>>>");
				listPNLAJ.clear();
				//System.out.println("listPNLAJ = "+CommonUtils.isNotEmpty(listPNLAJ));				
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();

				
/*NAV 2015				
				for (int i = 1; i < vJmlData; i++){
					if(i > 26){
								listPNLAJ.add(new PNLList(	sheet.getCell(2,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(13,i).getContents()
																));					
					}
				
				}
*/
	
				for (int i = 1; i < vJmlData; i++){
					if(i > 22){
								listPNLAJ.add(new PNLList(	sheet.getCell(1,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(15,i).getContents()
																));		
							
								
					}
				
				}
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
	
	public void onClick$btnOK2(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				listPNLAdjAJ.clear();
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();

				
/* NAV 2015				
				for (int i = 1; i < vJmlData; i++){
					if(i > 26){
							listPNLAdjAJ.add(new PNLList(	sheet.getCell(2,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(13,i).getContents()
																));					
					}
				
				}
*/
				
				for (int i = 1; i < vJmlData; i++){
					if(i > 22){
							listPNLAdjAJ.add(new PNLList(	sheet.getCell(1,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(15,i).getContents()
																));
						
					}
				
				}
				
				workbook.close();

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
	
	
	public void onClick$btnOK3(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				listPNLSP.clear();
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();

				
/* NAV 2015			
				for (int i = 1; i < vJmlData; i++){
					if(i > 26){
								listPNLSP.add(new PNLList(	sheet.getCell(2,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(13,i).getContents()
																));					
					}
				
				}
*/
				
				for (int i = 1; i < vJmlData; i++){
					if(i > 22){
								listPNLSP.add(new PNLList(	sheet.getCell(1,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(15,i).getContents()
																));				
								
							
					}
				
				}				
				workbook.close();

			   lbl3.setValue(media.getName()+ " Sudah berhasil terupload.");


			} catch (BiffException e) {
				Messagebox.show("Not an Excel File : " + media.getName(),
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}
			

		}
	
	}
	
	public void onClick$btnOK4(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				listPNLAdjSP.clear();
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();

				
/* NAV 2015				
				for (int i = 1; i < vJmlData; i++){
					if(i > 26){
								listPNLAdjSP.add(new PNLList(	sheet.getCell(2,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(13,i).getContents()
																));					
					}
				
				}
*/
				
				for (int i = 1; i < vJmlData; i++){
					if(i > 22){
							listPNLAdjSP.add(new PNLList(	sheet.getCell(1,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(15,i).getContents()
																));			
							
					}
				
				}
				workbook.close();

			   lbl4.setValue(media.getName()+ " Sudah berhasil terupload.");


			} catch (BiffException e) {
				Messagebox.show("Not an Excel File : " + media.getName(),
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}
			

		}
	
	}
	
	public void onClick$btnOK1Neraca(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				listNERACAAJ.clear();
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();

				
/*				
				for (int i = 1; i < vJmlData; i++){
					if(i > 26){
								listNERACAAJ.add(new PNLList(	sheet.getCell(2,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(12,i).getContents()
																));					
					}
				
				}
*/
				
				for (int i = 1; i < vJmlData; i++){
					if(i > 22){
								listNERACAAJ.add(new PNLList(	sheet.getCell(1,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(12,i).getContents()
																));					
					}
				
				}
				workbook.close();

			   lbl1Neraca.setValue(media.getName()+ " Sudah berhasil terupload.");


			} catch (BiffException e) {
				Messagebox.show("Not an Excel File : " + media.getName(),
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}
			

		}
	
	}
	
	public void onClick$btnOK2Neraca(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				listNERACAAdjAJ.clear();
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();

/* NAV 2015				
				
				for (int i = 1; i < vJmlData; i++){
					if(i > 26){
							listNERACAAdjAJ.add(new PNLList(	sheet.getCell(2,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(12,i).getContents()
																));					
					}
				
				}
*/

				for (int i = 1; i < vJmlData; i++){
					if(i > 22){
							listNERACAAdjAJ.add(new PNLList(	sheet.getCell(1,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(12,i).getContents()
																));					
					}
				
				}
				
				workbook.close();

			   lbl2Neraca.setValue(media.getName()+ " Sudah berhasil terupload.");


			} catch (BiffException e) {
				Messagebox.show("Not an Excel File : " + media.getName(),
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}
			

		}
	}
	
	
	public void onClick$btnOK3Neraca(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				listNERACASP.clear();
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();

				
/* NAV 2015				
				for (int i = 1; i < vJmlData; i++){
					if(i > 26){
								listNERACASP.add(new PNLList(	sheet.getCell(2,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(12,i).getContents()
																));					
					}
				
				}
*/
				
				for (int i = 1; i < vJmlData; i++){
					if(i > 22){
							listNERACASP.add(new PNLList(	sheet.getCell(1,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(12,i).getContents()
																));					
					}
				
				}
				
				
				
				workbook.close();

			   lbl3Neraca.setValue(media.getName()+ " Sudah berhasil terupload.");


			} catch (BiffException e) {
				Messagebox.show("Not an Excel File : " + media.getName(),
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}
			

		}
	
	}
	
	public void onClick$btnOK4Neraca(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				listNERACAAdjSP.clear();
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();

				
/* NAV 2015				
				for (int i = 1; i < vJmlData; i++){
					if(i > 26){
								listNERACAAdjSP.add(new PNLList(	sheet.getCell(2,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(12,i).getContents()
																));					
					}
				
				}
*/
				
				for (int i = 1; i < vJmlData; i++){
					if(i > 22){
						listNERACAAdjSP.add(new PNLList(	sheet.getCell(1,i).getContents(), 
															sheet.getCell(3,i).getContents(),
															sheet.getCell(12,i).getContents()
																));					
					}
				
				}
				
				workbook.close();

			   lbl4Neraca.setValue(media.getName()+ " Sudah berhasil terupload.");


			} catch (BiffException e) {
				Messagebox.show("Not an Excel File : " + media.getName(),
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}
			

		}
	
	}

	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	@SuppressWarnings("unchecked")
	public void onClick$btnCOGS(Event event) throws InterruptedException, ParseException {
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
	
		
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
		
	
//		String vCab = "10";
//		if (cmbCab.getSelectedItem().getValue() != null){
//			vCab = (String) cmbCab.getSelectedItem().getValue();
//		}
		
		
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		
		BigDecimal vPembagi = new BigDecimal(String.valueOf(vAmountIn));
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01001_COGSAnalysis.jasper";

    	param.put("PeriodeFrom",  vTglFrom); 
		param.put("PeriodeUpto",  vTglUpto);  		
		param.put("Cabang",  vCabang); 
		param.put("Pembagi",  vPembagi); 

		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
	
	@SuppressWarnings("unchecked")
	public void onClick$btnCOGS2(Event event) throws InterruptedException, ParseException {
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
	
		
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
		
	
//		String vCab = "10";
//		if (cmbCab.getSelectedItem().getValue() != null){
//			vCab = (String) cmbCab.getSelectedItem().getValue();
//		}
		
		
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		
		BigDecimal vPembagi = new BigDecimal(String.valueOf(vAmountIn));
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01001_02_COGSAnalysis.jasper";

    	param.put("PeriodeFrom",  vTglFrom); 
		param.put("PeriodeUpto",  vTglUpto);  		
		param.put("Cabang",  vCabang); 
		param.put("Pembagi",  vPembagi); 

		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public void onClick$btnSalesCOGS(Event event) throws InterruptedException, ParseException {
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
			
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
		
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  		
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
	
//		String vCab = "10";
//		if (cmbCab.getSelectedItem().getValue() != null){
//			vCab = (String) cmbCab.getSelectedItem().getValue();
//		}
		
		
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		String ProsesID_SalesCOGSCorr = String.valueOf(System.currentTimeMillis());
		String vCetak = callStoreProcOrFuncService.callSalesCOGSCorrection(ProsesID_SalesCOGSCorr, vCabang , vStrTglFrom, vStrTglUpto, "CETAK");

		
		BigDecimal vPembagi = new BigDecimal(String.valueOf(vAmountIn));
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01002_SalesVSCOGSCorrection.jasper";

    	param.put("PeriodeFrom",  vTglFrom); 
		param.put("PeriodeUpto",  vTglUpto);  		
		param.put("Cabang",  vCabang); 
		param.put("Pembagi",  vPembagi); 
		param.put("ProsesId",  ProsesID_SalesCOGSCorr); 

		
		new JReportGeneratorWindow(param, jasperRpt, "XLS"); 
		
		 
		String vDelete = callStoreProcOrFuncService.callSalesCOGSCorrection(ProsesID_SalesCOGSCorr, vCabang , vStrTglFrom, vStrTglUpto, "DELETE");

	}


	@SuppressWarnings({ "unchecked", "unused" })
	public void onClick$btnOK(Event event) throws InterruptedException, ParseException, IOException {
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
		
		
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
			
		SimpleDateFormat frmTgl = new SimpleDateFormat("yyyy-MM-dd");
		String vStrTglFrom  = frmTgl.format(vTglFrom);  		
		String vStrTglUpto  = frmTgl.format(vTglUpto);  
		
		String ProsesID_PNLProcess = String.valueOf(System.currentTimeMillis());
		String vCetak = callStoreProcOrFuncService.callSalesCOGSCorrection(ProsesID_PNLProcess, vCabang , vStrTglFrom, vStrTglUpto, "CETAK");

		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01002_SalesVSCOGSCorrection.jasper";
		BigDecimal vPembagi_report = new BigDecimal(String.valueOf(vAmountIn));
  
		param.put("PeriodeFrom",  vTglFrom); 
		param.put("PeriodeUpto",  vTglUpto);  		
		param.put("Cabang",  vCabang); 
		param.put("Pembagi",  vPembagi_report);		
		param.put("ProsesId",  ProsesID_PNLProcess);		
		
		
		File outputFile = new File(pathRpt.getSalesVSCOGS());
		
		OutputStream outStream = new FileOutputStream(outputFile);
        outStream.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "XLS"));
               
        try {
			// Membaca Excel dari file yang di Upload
			Workbook workbook = Workbook.getWorkbook(outputFile);
			
			Sheet sheet = workbook.getSheet(0);

			int vJmlData = sheet.getRows();
			
			for (int i = 1; i < vJmlData; i++){

				if (CommonUtils.isNotEmpty(sheet.getCell(5,i).getContents()) ||
						CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents())){
					if(i == 50 ){						
						vSalesAJ = new Double( sheet.getCell(5,i).getContents().replaceAll(",", ""));
						vSalesSP = new Double( sheet.getCell(6,i).getContents().replaceAll(",", ""));		
					}
					
					if(i == 84 ){
						vRebateAJ = new Double( sheet.getCell(5,i).getContents().replaceAll(",", ""));
						vRebateSP = new Double( sheet.getCell(6,i).getContents().replaceAll(",", ""));				
					}
					
					if(i == 93 ){
						vCOGSAJ = new Double( sheet.getCell(5,i).getContents().replaceAll(",", ""));
						vCOGSSP = new Double( sheet.getCell(6,i).getContents().replaceAll(",", ""));	
					}
				}
			
			}
					
			workbook.close();	
			
			
		} catch (BiffException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);

		} catch (IOException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);

		}
		
        outputFile.delete();
		
        String vDelete = callStoreProcOrFuncService.callSalesCOGSCorrection(ProsesID_PNLProcess, vCabang , vStrTglFrom, vStrTglUpto, "DELETE");

		Double vPembagi = new Double(String.valueOf(vAmountIn));
		
		if (CommonUtils.isNotEmpty(listPNLAJ) && 
				CommonUtils.isNotEmpty(listPNLAdjAJ) &&
				CommonUtils.isNotEmpty(listPNLSP) &&
				CommonUtils.isNotEmpty(listPNLAdjSP)) {
			
			
			
			
			try {
				
				File outputFileExcel = new File(pathRpt.getPnlKonsolidasi());
				
				WritableWorkbook outputFileWorkbook = Workbook
						.createWorkbook(outputFileExcel);

				WritableSheet sheetOut = outputFileWorkbook.createSheet(
						"Sheet", 0);
				
				sheetOut.addCell(new Label(0, 0, "Row"));
				sheetOut.addCell(new Label(1, 0, "Keterangan"));
				sheetOut.addCell(new Label(2, 0, "Amount - AJ"));
				sheetOut.addCell(new Label(3, 0, "Amount - AJ Adj"));
				sheetOut.addCell(new Label(4, 0, "Amount - Total AJ"));
				sheetOut.addCell(new Label(5, 0, "Amount - SP"));
				sheetOut.addCell(new Label(6, 0, "Amount - SP Adj"));
				sheetOut.addCell(new Label(7, 0, "Amount - Total SP"));
				sheetOut.addCell(new Label(8, 0, "Amount - Total Konsolidasi"));
				
				WritableCellFormat numformat = new WritableCellFormat(new NumberFormat("#,##0.00")); 
				
				int vIndex = 0;
				int vNumRow = 1;
				
				Double vT1AJ = new Double(0);
				Double vT1AJAdj = new Double(0);
				Double vT1SP = new Double(0);
				Double vT1SPAdj = new Double(0);
				
				Double vT2AJ = new Double(0);
				Double vT2AJAdj = new Double(0);
				Double vT2SP = new Double(0);
				Double vT2SPAdj = new Double(0);
				
				Double vTM0AJ = new Double(0);
				Double vTM0AJAdj = new Double(0);
				Double vTM0SP = new Double(0);
				Double vTM0SPAdj = new Double(0);
				
				Double vTM1AJ = new Double(0);
				Double vTM1AJAdj = new Double(0);
				Double vTM1SP = new Double(0);
				Double vTM1SPAdj = new Double(0);
				
				Double vTM2AJ = new Double(0);
				Double vTM2AJAdj = new Double(0);
				Double vTM2SP = new Double(0);
				Double vTM2SPAdj = new Double(0);

				
				for (PNLList bs : listPNLAJ) {
					PNLList listAdjAJ = listPNLAdjAJ.get(vIndex);
					PNLList listSP = listPNLSP.get(vIndex);
					PNLList listAdjSP = listPNLAdjSP.get(vIndex);
									
					Double AmountAJ = new Double(0);
					if(CommonUtils.isNotEmpty(bs.getAmount())){
						AmountAJ = new Double( bs.getAmount().replaceAll(",", ""));
					}
					
					Double AmountAdjAJ = new Double(0);
					if(CommonUtils.isNotEmpty(listAdjAJ.getAmount())){
						AmountAdjAJ = new Double( listAdjAJ.getAmount().replaceAll(",", ""));
					}
					
					Double AmountSP = new Double(0);
					if(CommonUtils.isNotEmpty(listSP.getAmount())){
						AmountSP = new Double( listSP.getAmount().replaceAll(",", ""));
					}
					
					Double AmountAdjSP = new Double(0);
					if(CommonUtils.isNotEmpty(listAdjSP.getAmount())){
						AmountAdjSP = new Double( listAdjSP.getAmount().replaceAll(",", ""));
					}
					
							
					AmountAJ = round((AmountAJ / vPembagi),0);
					AmountAdjAJ = round((AmountAdjAJ / vPembagi),0);
					AmountSP = round((AmountSP / vPembagi),0);
					AmountAdjSP = round((AmountAdjSP / vPembagi),0);
					
					Double AmountTotalAJ =  AmountAJ+AmountAdjAJ;
					Double AmountTotalSP = AmountSP+AmountAdjSP;
					Double AmountTotal = AmountAJ+AmountAdjAJ+AmountSP+AmountAdjSP;
					
					
					
							
					sheetOut.addCell(new Label(0, vNumRow, bs.getNoUrut()));
					sheetOut.addCell(new Label(1, vNumRow, bs.getKeterangan()));
					if (bs.getNoUrut().equals("1001")){
						vT1AJ = vT1AJ + vSalesAJ;
						vT1AJAdj = vT1AJAdj + 0;
						vT1SP = vT1SP + vSalesAJ;
						vT1SPAdj = vT1SPAdj + 0;
						
						
						sheetOut.addCell(new Number(2, vNumRow, vSalesAJ, numformat));
						sheetOut.addCell(new Number(3, vNumRow, new Double(0), numformat));
						sheetOut.addCell(new Number(4, vNumRow, vSalesAJ, numformat));
						sheetOut.addCell(new Number(5, vNumRow, vSalesSP, numformat));
						sheetOut.addCell(new Number(6, vNumRow, new Double(0), numformat));
						sheetOut.addCell(new Number(7, vNumRow, vSalesSP, numformat));
						sheetOut.addCell(new Number(8, vNumRow, vSalesAJ+vSalesSP,numformat));
						
					} else {
						if (bs.getNoUrut().equals("1002")){
							
							vT1AJ = vT1AJ + (vCOGSAJ + (vRebateAJ * -1));
							vT1AJAdj = vT1AJAdj + 0;
							vT1SP = vT1SP + (vCOGSSP + (vRebateSP * -1));
							vT1SPAdj = vT1SPAdj + 0;
							
							sheetOut.addCell(new Number(2, vNumRow, (vCOGSAJ + (vRebateAJ * -1)), numformat));
							sheetOut.addCell(new Number(3, vNumRow, new Double(0), numformat));
							sheetOut.addCell(new Number(4, vNumRow, (vCOGSAJ + (vRebateAJ * -1)), numformat));
							sheetOut.addCell(new Number(5, vNumRow, (vCOGSSP + (vRebateSP * -1)), numformat));
							sheetOut.addCell(new Number(6, vNumRow, new Double(0), numformat));
							sheetOut.addCell(new Number(7, vNumRow, (vCOGSSP + (vRebateSP * -1)), numformat));
							sheetOut.addCell(new Number(8, vNumRow, (vCOGSAJ + (vRebateAJ * -1))+(vCOGSSP + (vRebateSP * -1)),numformat));
						} else {
							if(bs.getNoUrut().equals("1003")){
								vT1AJ = vT1AJ +  (vRebateAJ * -1);
								vT1AJAdj = vT1AJAdj + 0;
								vT1SP = vT1SP +  (vRebateSP * -1);
								vT1SPAdj = vT1SPAdj + 0;
								
								sheetOut.addCell(new Number(2, vNumRow,  (vRebateAJ * -1), numformat));
								sheetOut.addCell(new Number(3, vNumRow, new Double(0), numformat));
								sheetOut.addCell(new Number(4, vNumRow,  (vRebateAJ * -1), numformat));
								sheetOut.addCell(new Number(5, vNumRow, (vRebateSP * -1), numformat));
								sheetOut.addCell(new Number(6, vNumRow, new Double(0), numformat));
								sheetOut.addCell(new Number(7, vNumRow, (vRebateSP * -1), numformat));
								sheetOut.addCell(new Number(8, vNumRow, (vRebateAJ * -1)+(vRebateSP * -1),numformat));
							}else {
								if (bs.getNoUrut().equals("TM0")){
									vTM0AJ = vSalesAJ - vCOGSAJ;
									vTM0AJAdj = new Double(0);
									vTM0SP = vSalesSP - vCOGSSP;
									vTM0SPAdj = new Double(0);
									
									sheetOut.addCell(new Number(2, vNumRow, vTM0AJ, numformat));
									sheetOut.addCell(new Number(3, vNumRow, vTM0AJAdj, numformat));
									sheetOut.addCell(new Number(4, vNumRow, (vTM0AJ+vTM0AJAdj), numformat));
									sheetOut.addCell(new Number(5, vNumRow, vTM0SP, numformat));
									sheetOut.addCell(new Number(6, vNumRow, vTM0SPAdj, numformat));
									sheetOut.addCell(new Number(7, vNumRow, (vTM0SP+vTM0SPAdj), numformat));
									sheetOut.addCell(new Number(8, vNumRow, (vTM0AJ+vTM0AJAdj+vTM0SP+vTM0SPAdj),numformat));
									
									vTM1AJ = vTM0AJ;
									vTM2AJAdj = vTM0AJAdj;
									vTM1SP = vTM0SP;
									vTM1SPAdj = vTM0SPAdj;
								} else {
									if(bs.getNoUrut().equals("T2")){
										sheetOut.addCell(new Number(2, vNumRow, vT2AJ, numformat));
										sheetOut.addCell(new Number(3, vNumRow, vT2AJAdj, numformat));
										sheetOut.addCell(new Number(4, vNumRow, (vT2AJ+vT2AJAdj), numformat));
										sheetOut.addCell(new Number(5, vNumRow, vT2SP, numformat));
										sheetOut.addCell(new Number(6, vNumRow, vT2SPAdj));
										sheetOut.addCell(new Number(7, vNumRow, (vT2SP+vT2SPAdj), numformat));
										sheetOut.addCell(new Number(8, vNumRow, (vT2AJ+vT2AJAdj+vT2SP+vT2SPAdj),numformat));
	
										
										vT1AJ = new Double(0);
										vT1AJAdj = new Double(0);
										vT1SP = new Double(0);
										vT1SPAdj = new Double(0);
										
									} else {
										if(bs.getNoUrut().equals("T1")){
											sheetOut.addCell(new Number(2, vNumRow, vT1AJ, numformat));
											sheetOut.addCell(new Number(3, vNumRow, vT1AJAdj, numformat));
											sheetOut.addCell(new Number(4, vNumRow, (vT1AJ+vT1AJAdj), numformat));
											sheetOut.addCell(new Number(5, vNumRow, vT1SP, numformat));
											sheetOut.addCell(new Number(6, vNumRow, vT1SPAdj));
											sheetOut.addCell(new Number(7, vNumRow, (vT1SP+vT1SPAdj), numformat));
											sheetOut.addCell(new Number(8, vNumRow, (vT1AJ+vT1AJAdj+vT1SP+vT1SPAdj),numformat));
											
											vT1AJ = new Double(0);
											vT1AJAdj = new Double(0);
											vT1SP = new Double(0);
											vT1SPAdj = new Double(0);
											
										} else {
											if(bs.getNoUrut().equals("TM1")){
												vTM1AJ = vTM1AJ - vT2AJ;
												vTM1AJAdj =vTM1AJAdj - vT2AJAdj;
												vTM1SP = vTM1SP - vT2SP;
												vTM1SPAdj = vTM1SPAdj - vT2SPAdj;
												
												sheetOut.addCell(new Number(2, vNumRow, vTM1AJ, numformat));
												sheetOut.addCell(new Number(3, vNumRow, vTM1AJAdj, numformat));
												sheetOut.addCell(new Number(4, vNumRow, (vTM1AJ+vTM1AJAdj), numformat));
												sheetOut.addCell(new Number(5, vNumRow, vTM1SP, numformat));
												sheetOut.addCell(new Number(6, vNumRow, vTM1SPAdj, numformat));
												sheetOut.addCell(new Number(7, vNumRow, (vTM1SP+vTM1SPAdj), numformat));
												sheetOut.addCell(new Number(8, vNumRow, (vTM1AJ+vTM1AJAdj+vTM1SP+vTM1SPAdj),numformat));
												
												vT1AJ = new Double(0);
												vT1AJAdj = new Double(0);
												vT1SP = new Double(0);
												vT1SPAdj = new Double(0);
												
												vT2AJ = new Double(0);
												vT2AJAdj = new Double(0);
												vT2SP = new Double(0);
												vT2SPAdj = new Double(0);
												
												vTM2AJ = vTM1AJ;
												vTM2AJAdj = vTM1AJAdj;
												vTM2SP = vTM1SP;
												vTM2SPAdj = vTM1SPAdj;
												
											} else {
												if(bs.getNoUrut().equals("TM2")){
													vTM2AJ = vTM2AJ + vT2AJ;
													vTM2AJAdj =vTM2AJAdj + vT2AJAdj;
													vTM2SP = vTM2SP + vT2SP;
													vTM2SPAdj = vTM2SPAdj + vT2SPAdj;
													
													sheetOut.addCell(new Number(2, vNumRow, vTM2AJ, numformat));
													sheetOut.addCell(new Number(3, vNumRow, vTM2AJAdj, numformat));
													sheetOut.addCell(new Number(4, vNumRow, (vTM2AJ+vTM2AJAdj), numformat));
													sheetOut.addCell(new Number(5, vNumRow, vTM2SP, numformat));
													sheetOut.addCell(new Number(6, vNumRow, vTM2SPAdj, numformat));
													sheetOut.addCell(new Number(7, vNumRow, (vTM2SP+vTM2SPAdj), numformat));
													sheetOut.addCell(new Number(8, vNumRow, (vTM2AJ+vTM2AJAdj+vTM2SP+vTM2SPAdj),numformat));
													
													vT1AJ = new Double(0);
													vT1AJAdj = new Double(0);
													vT1SP = new Double(0);
													vT1SPAdj = new Double(0);
													
													vT2AJ = new Double(0);
													vT2AJAdj = new Double(0);
													vT2SP = new Double(0);
													vT2SPAdj = new Double(0);
													
													vTM1AJ = vTM2AJ;
													vTM1AJAdj = vTM2AJAdj;
													vTM1SP = vTM2SP;
													vTM1SPAdj = vTM2SPAdj;
													
												} else {
													if(AmountAJ != 0){
														sheetOut.addCell(new Number(2, vNumRow, AmountAJ,numformat));
														vT1AJ = vT1AJ + AmountAJ;
														vT2AJ = vT2AJ + AmountAJ;
													}
													
													if(AmountAdjAJ != 0){
														sheetOut.addCell(new Number(3, vNumRow, AmountAdjAJ,numformat));
														vT1AJAdj = vT1AJAdj + AmountAdjAJ;
														vT2AJAdj = vT2AJAdj + AmountAdjAJ;
													}
													
													if(AmountTotalAJ != 0){
														sheetOut.addCell(new Number(4, vNumRow, AmountTotalAJ,numformat));
													}
													
													
													if(AmountSP != 0){
														sheetOut.addCell(new Number(5, vNumRow, AmountSP,numformat));
														vT1SP = vT1SP + AmountSP;
														vT2SP = vT2SP + AmountSP;
													}											
													
													
													if(AmountAdjSP != 0){
														sheetOut.addCell(new Number(6, vNumRow, AmountAdjSP,numformat));
														vT1SPAdj = vT1SPAdj + AmountAdjSP;
														vT2SPAdj = vT2SPAdj + AmountAdjSP;
													}
													
													if(AmountTotalSP != 0){
														sheetOut.addCell(new Number(7, vNumRow, AmountTotalSP,numformat));
													}
													
													if(AmountTotal != 0){
														sheetOut.addCell(new Number(8, vNumRow, AmountTotal,numformat));
													}
												}
											}
										}
									}
								}
							}
						}
					}
					
					
					//sheetOut.addCell(new Label(2, vNumRow, bs.getAmount()));
					//sheetOut.addCell(new Label(3, vNumRow, listSP.getAmount()));
					vNumRow ++;
					
					vIndex = vIndex +1;
				}
				
				outputFileWorkbook.write();

				InputStream mediais = new FileInputStream(outputFileExcel);
				AMedia amedia = new AMedia("PNLKonsolidasi.xls", "xls", "application/vnd.ms-excel", mediais);
				
				Filedownload.save(amedia);
				
				outputFileWorkbook.close();
				
				outputFileExcel.delete();

			} catch (WriteException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);
			}
		}

	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public void onClick$btnOKNeraca(Event event) throws InterruptedException, ParseException, IOException {
		String vAmountIn = "1000";
		if (StringUtils.isNotEmpty(rdgAmount.getSelectedItem().getValue())) {
			vAmountIn = rdgAmount.getSelectedItem().getValue();	
		} 
		
		Date vTgl = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vTgl = dbPeriode.getValue();
		}   
		
		Calendar cTglFrom = Calendar.getInstance();		
		cTglFrom.setTime(vTgl);
		int yearTglFrom = cTglFrom.get(Calendar.YEAR);		
		int monthTglFrom = cTglFrom.get(Calendar.MONTH) + 1;
		int dayTglFrom = cTglFrom.get(Calendar.DAY_OF_MONTH);
		
				
		String dRTglPeriode = dayTglFrom+"/"+monthTglFrom+"/"+yearTglFrom;		
		SimpleDateFormat dfRTglPeriode= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglPriode  = dfRTglPeriode.parse(dRTglPeriode);
		
		
		String dRFrom = "1/1/"+yearTglFrom;		
		SimpleDateFormat dfRFrom= new SimpleDateFormat("dd/MM/yyyy");
		Date vTglFrom  = dfRFrom.parse(dRFrom);
			
		Calendar cTglUpto = Calendar.getInstance();
		cTglUpto.setTime(vTglPriode);
		cTglUpto.set(Calendar.DAY_OF_MONTH, cTglUpto.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date vTglUpto = cTglUpto.getTime();
			
//		int yearTglFromLastYear =yearTglFrom - 1;
//		
//		String dRFromLastYear = "1/1/2016";		
//		SimpleDateFormat dfRFromLastYear= new SimpleDateFormat("dd/MM/yyyy");
//		Date vTglFromLastYear  = dfRFromLastYear.parse(dRFromLastYear);
//		
//		String dRUptoLastYear = "31/12/"+yearTglFromLastYear;		
//		SimpleDateFormat dfRUptoLastYear= new SimpleDateFormat("dd/MM/yyyy");
//		Date vTglUptoLastYear  = dfRUptoLastYear.parse(dRUptoLastYear);
		
		
        Double vAccuredSalesAJLastYear = new Double(0);
        Double vAccuredCOGSAJLastYear = new Double(0);
        
        Double vAccuredSalesSPLastYear = new Double(0);
        Double vAccuredCOGSSPLastYear = new Double(0);
        
        Double vPiutangIC_AJLastYear = new Double(0);
        Double vPiutangIC_SPLastYear = new Double(0);
        
        Double vHutangIC_AJLastYear = new Double(0);
        Double vHutangIC_SPLastYear = new Double(0);
        
        Double vKoreksiIntercoySales_AJLastYear = new Double(0);
        Double vKoreksiIntercoySales_SPLastYear = new Double(0);
        
        Double vKoreksiIntercoyCOGS_AJLastYear = new Double(0);
        Double vKoreksiIntercoyCOGS_SPLastYear = new Double(0);
        
        
        
        Double vAccuredSalesAJ = new Double(0);
        Double vAccuredCOGSAJ = new Double(0);
        
        Double vAccuredSalesSP = new Double(0);
        Double vAccuredCOGSSP = new Double(0);
        
        Double vPiutangIC_AJ = new Double(0);
        Double vPiutangIC_SP = new Double(0);
        
        Double vHutangIC_AJ = new Double(0);
        Double vHutangIC_SP = new Double(0);
        
        Double vKoreksiIntercoySales_AJ = new Double(0);
        Double vKoreksiIntercoySales_SP = new Double(0);
        
        Double vKoreksiIntercoyCOGS_AJ = new Double(0);
        Double vKoreksiIntercoyCOGS_SP = new Double(0);
        
        pathRptLastYear = new PathReport(timeStamp);
        
        BigDecimal vBDPembagi = new BigDecimal(vAmountIn);
        
        String vResult = callStoreProcOrFuncService.callGetPNLForNeraca(timeStamp, yearTglFrom, monthTglFrom, vCabang, vBDPembagi, SecurityContextHolder.getContext().getAuthentication().getName(), "CETAK");
        
        String jasperRpt = "/solusi/hapis/webui/reports/accounting/01070_KoreksiSalesCOGSForNeraca.jasper";
        param.put("ProsesId",  timeStamp);	
        param.put("YearPeriode",  yearTglFrom);
        
        
        File outputFileLastYear = new File(pathRptLastYear.getSalesVSCOGSLastYear());	
    	
    	
		OutputStream outStreamLastYear = new FileOutputStream(outputFileLastYear);
        outStreamLastYear.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "XLS"));
          
        try {
			// Membaca Excel dari file yang di Upload
			Workbook workbookLastYear = Workbook.getWorkbook(outputFileLastYear);
			
			Sheet sheetLastYear = workbookLastYear.getSheet(0);

			int vJmlDataLastYear = sheetLastYear.getRows();
			
			for (int i = 2; i < vJmlDataLastYear; i++){
				if (	CommonUtils.isNotEmpty(sheetLastYear.getCell(1,i).getContents()) ||
						CommonUtils.isNotEmpty(sheetLastYear.getCell(2,i).getContents()) ||
						CommonUtils.isNotEmpty(sheetLastYear.getCell(3,i).getContents()) ||
						CommonUtils.isNotEmpty(sheetLastYear.getCell(4,i).getContents())){
					
					//System.out.println(sheetLastYear.getCell(0,i).getContents()+ " - AJ --> "+sheetLastYear.getCell(1,i).getContents());
					//System.out.println(sheetLastYear.getCell(0,i).getContents()+ " - SP --> "+sheetLastYear.getCell(2,i).getContents());
					//System.out.println("---");
					
					//Koreksi Sales untuk masuk ke Accrued Sales dan PNL This Year
					if (i == 2){						
						vAccuredSalesAJLastYear = vAccuredSalesAJLastYear + new Double( sheetLastYear.getCell(1,i).getContents().replaceAll(",", ""));
						vAccuredSalesSPLastYear = vAccuredSalesSPLastYear + new Double( sheetLastYear.getCell(2,i).getContents().replaceAll(",", ""));		

						vAccuredSalesAJ = vAccuredSalesAJ + new Double( sheetLastYear.getCell(3,i).getContents().replaceAll(",", ""));
						vAccuredSalesSP = vAccuredSalesSP + new Double( sheetLastYear.getCell(4,i).getContents().replaceAll(",", ""));		
					}
					
					//Koreksi COGS untuk masuk ke Accrued COGS dan PNL This Year
					if(i == 3 ){
						vAccuredCOGSAJLastYear = vAccuredCOGSAJLastYear + new Double( sheetLastYear.getCell(1,i).getContents().replaceAll(",", ""));
						vAccuredCOGSSPLastYear = vAccuredCOGSSPLastYear + new Double( sheetLastYear.getCell(2,i).getContents().replaceAll(",", ""));	
						
						vAccuredCOGSAJ = vAccuredCOGSAJ + new Double( sheetLastYear.getCell(3,i).getContents().replaceAll(",", ""));
						vAccuredCOGSSP = vAccuredCOGSSP + new Double( sheetLastYear.getCell(4,i).getContents().replaceAll(",", ""));		
					}
						
					//Koreksi Intercoy Total Sales untuk masuk ke PNL This Year
					if(i == 4){
						vKoreksiIntercoySales_AJLastYear = vKoreksiIntercoySales_AJLastYear + new Double( sheetLastYear.getCell(1,i).getContents().replaceAll(",", ""));
						vKoreksiIntercoySales_SPLastYear = vKoreksiIntercoySales_SPLastYear + new Double( sheetLastYear.getCell(2,i).getContents().replaceAll(",", ""));	
						
						vKoreksiIntercoySales_AJ = vKoreksiIntercoySales_AJ + new Double( sheetLastYear.getCell(3,i).getContents().replaceAll(",", ""));
						vKoreksiIntercoySales_SP = vKoreksiIntercoySales_SP + new Double( sheetLastYear.getCell(4,i).getContents().replaceAll(",", ""));		
					}
					
					//Koreksi Intercoy Total COGS untuk masuk ke PNL This Year
					if(i == 5){
						vKoreksiIntercoyCOGS_AJLastYear = vKoreksiIntercoyCOGS_AJLastYear + new Double( sheetLastYear.getCell(1,i).getContents().replaceAll(",", ""));
						vKoreksiIntercoyCOGS_SPLastYear = vKoreksiIntercoyCOGS_SPLastYear + new Double( sheetLastYear.getCell(2,i).getContents().replaceAll(",", ""));		
					
						vKoreksiIntercoyCOGS_AJ = vKoreksiIntercoyCOGS_AJ + new Double( sheetLastYear.getCell(3,i).getContents().replaceAll(",", ""));
						vKoreksiIntercoyCOGS_SP = vKoreksiIntercoyCOGS_SP + new Double( sheetLastYear.getCell(4,i).getContents().replaceAll(",", ""));	
					}		
					
					//Koreksi Management Intercoy COGS untuk masuk ke Hutang Dagang dan PNL This Year
					if(i == 6){
						vHutangIC_AJLastYear = vHutangIC_AJLastYear + new Double( sheetLastYear.getCell(1,i).getContents().replaceAll(",", ""));
						vHutangIC_SPLastYear = vHutangIC_SPLastYear + new Double( sheetLastYear.getCell(2,i).getContents().replaceAll(",", ""));	
						
						vHutangIC_AJ = vHutangIC_AJ + new Double( sheetLastYear.getCell(3,i).getContents().replaceAll(",", ""));
						vHutangIC_SP = vHutangIC_SP + new Double( sheetLastYear.getCell(4,i).getContents().replaceAll(",", ""));		
					}
					
				}
			}
			
			workbookLastYear.close();	
			
			
		} catch (BiffException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);

		} catch (IOException e) {
			Messagebox.show("Error : " + e.getMessage(), "Error",
					Messagebox.OK, Messagebox.ERROR);

		}
		
        outputFileLastYear.delete();
		
        String vDelete = callStoreProcOrFuncService.callGetPNLForNeraca(timeStamp, yearTglFrom, monthTglFrom, vCabang, vBDPembagi, SecurityContextHolder.getContext().getAuthentication().getName(), "DELETE");
        
//		//Koreksi untuk Neraca 2 dari Tahun sebelumnya 
//        //Di mulai dari Tahun 2017 karena Awal mulai menggunakan di tahun 2016 dan di tahun 2016 sudah di buat saldo awal
//		if(yearTglFrom >= 2017){
//			for (int vTahun = 2016 ; vTahun < yearTglFrom; vTahun ++){
//				
//				pathRptLastYear = new PathReport(timeStamp+"_"+vTahun);
//				
//				String dRFromLastYear = "1/1/"+vTahun;		
//				SimpleDateFormat dfRFromLastYear= new SimpleDateFormat("dd/MM/yyyy");
//				Date vTglFromLastYear  = dfRFromLastYear.parse(dRFromLastYear);
//				
//				String dRUptoLastYear = "31/12/"+vTahun;		
//				SimpleDateFormat dfRUptoLastYear= new SimpleDateFormat("dd/MM/yyyy");
//				Date vTglUptoLastYear  = dfRUptoLastYear.parse(dRUptoLastYear);
//				
//				//System.out.println("Masuk Sini =======================>");
//				
//				String jasperRpt = "/solusi/hapis/webui/reports/accounting/01002_SalesVSCOGSCorrection.jasper";
//				BigDecimal vPembagi_report = new BigDecimal(String.valueOf(vAmountIn));
//		  
//				param.put("PeriodeFrom",  vTglFromLastYear); 
//				param.put("PeriodeUpto",  vTglUptoLastYear);  		
//				param.put("Cabang",  vCabang); 
//				param.put("Pembagi",  vPembagi_report);		
//				
//			
//				File outputFileLastYear = new File(pathRptLastYear.getSalesVSCOGSLastYear());	
//	
//	
//				OutputStream outStreamLastYear = new FileOutputStream(outputFileLastYear);
//		        outStreamLastYear.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "XLS"));
//		          
//	
//		        
//		        try {
//					// Membaca Excel dari file yang di Upload
//					Workbook workbookLastYear = Workbook.getWorkbook(outputFileLastYear);
//					
//					Sheet sheetLastYear = workbookLastYear.getSheet(0);
//	
//					int vJmlDataLastYear = sheetLastYear.getRows();
//					
//					for (int i = 1; i < vJmlDataLastYear; i++){
//	
//						if (CommonUtils.isNotEmpty(sheetLastYear.getCell(5,i).getContents()) ||
//								CommonUtils.isNotEmpty(sheetLastYear.getCell(6,i).getContents())){
//							//Koreksi Sales untuk masuk ke Accrued Sales dan PNL This Year
//							if(i == 10 || i == 11 || i == 15 || i == 16 || i == 20 || i == 21|| i == 25 || i == 26
//								|| i == 30 || i == 31 || i == 35 || i == 36 ){
//								vAccuredSalesAJLastYear = vAccuredSalesAJLastYear + new Double( sheetLastYear.getCell(5,i).getContents().replaceAll(",", ""));
//								vAccuredSalesSPLastYear = vAccuredSalesSPLastYear + new Double( sheetLastYear.getCell(6,i).getContents().replaceAll(",", ""));		
//								//System.out.println("XXX --->" +sheetLastYear.getCell(4,i).getContents());
//							}
//							
//	//						if(i == 44){
//	//							vPiutangIC_AJLastYear = vPiutangIC_AJLastYear + new Double( sheet.getCell(5,i).getContents().replaceAll(",", ""));
//	//							vPiutangIC_SPLastYear = vPiutangIC_SPLastYear + new Double( sheet.getCell(6,i).getContents().replaceAll(",", ""));		
//	//						}
//							
//							//Koreksi COGS untuk masuk ke Accrued COGS dan PNL This Year
//							if(i == 55 || i == 56 || i == 60 || i == 61 || i == 65 || i == 66 || i == 70 || i == 71
//									|| i == 75 || i == 76 || i == 80 || i == 81 ){
//								vAccuredCOGSAJLastYear = vAccuredCOGSAJLastYear + new Double( sheetLastYear.getCell(5,i).getContents().replaceAll(",", ""));
//								vAccuredCOGSSPLastYear = vAccuredCOGSSPLastYear + new Double( sheetLastYear.getCell(6,i).getContents().replaceAll(",", ""));		
//							}
//							
//							//Koreksi Management Intercoy COGS untuk masuk ke Hutang Dagang dan PNL This Year
//							if(i == 90){
//								vHutangIC_AJLastYear = vHutangIC_AJLastYear + new Double( sheetLastYear.getCell(5,i).getContents().replaceAll(",", ""));
//								vHutangIC_SPLastYear = vHutangIC_SPLastYear + new Double( sheetLastYear.getCell(6,i).getContents().replaceAll(",", ""));		
//							}
//							
//							//Koreksi Intercoy Total Sales untuk masuk ke PNL This Year
//							if(i == 48){
//								vKoreksiIntercoySales_AJLastYear = vKoreksiIntercoySales_AJLastYear + new Double( sheetLastYear.getCell(5,i).getContents().replaceAll(",", ""));
//								vKoreksiIntercoySales_SPLastYear = vKoreksiIntercoySales_SPLastYear + new Double( sheetLastYear.getCell(6,i).getContents().replaceAll(",", ""));		
//							}
//							//Koreksi Intercoy Total COGS untuk masuk ke PNL This Year
//							if(i == 91){
//								vKoreksiIntercoyCOGS_AJLastYear = vKoreksiIntercoyCOGS_AJLastYear + new Double( sheetLastYear.getCell(5,i).getContents().replaceAll(",", ""));
//								vKoreksiIntercoyCOGS_SPLastYear = vKoreksiIntercoyCOGS_SPLastYear + new Double( sheetLastYear.getCell(6,i).getContents().replaceAll(",", ""));		
//							}
//						}
//					
//					}
//							
//					workbookLastYear.close();	
//					
//					
//				} catch (BiffException e) {
//					Messagebox.show("Error : " + e.getMessage(), "Error",
//							Messagebox.OK, Messagebox.ERROR);
//	
//				} catch (IOException e) {
//					Messagebox.show("Error : " + e.getMessage(), "Error",
//							Messagebox.OK, Messagebox.ERROR);
//	
//				}
//				
//		        outputFileLastYear.delete();
//			}
//		}
//		
//		//System.out.println("Keluar Sini =======================>");
//		
//		
//		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01002_SalesVSCOGSCorrection.jasper";
//		BigDecimal vPembagi_report = new BigDecimal(String.valueOf(vAmountIn));
//  
//		param.put("PeriodeFrom",  vTglFrom); 
//		param.put("PeriodeUpto",  vTglUpto);  		
//		param.put("Cabang",  vCabang); 
//		param.put("Pembagi",  vPembagi_report);		
//		
//		
//
//		File outputFile = new File(pathRpt.getSalesVSCOGS());	
//
//		OutputStream outStream = new FileOutputStream(outputFile);
//        outStream.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "XLS"));
//          
//
//        
//        try {
//			// Membaca Excel dari file yang di Upload
//			Workbook workbook = Workbook.getWorkbook(outputFile);
//			
//			Sheet sheet = workbook.getSheet(0);
//
//			int vJmlData = sheet.getRows();
//			
//			for (int i = 1; i < vJmlData; i++){
//
//				if (CommonUtils.isNotEmpty(sheet.getCell(5,i).getContents()) ||
//						CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents())){
//					//Koreksi Sales untuk masuk ke Accrued Sales dan PNL This Year
//					if(i == 10 || i == 11 || i == 15 || i == 16 || i == 20 || i == 21|| i == 25 || i == 26
//						|| i == 30 || i == 31 || i == 35 || i == 36 ){
//						vAccuredSalesAJ = vAccuredSalesAJ + new Double( sheet.getCell(5,i).getContents().replaceAll(",", ""));
//						vAccuredSalesSP = vAccuredSalesSP + new Double( sheet.getCell(6,i).getContents().replaceAll(",", ""));		
//					}
//					
////					if(i == 44){
////						vPiutangIC_AJ = vPiutangIC_AJ + new Double( sheet.getCell(5,i).getContents().replaceAll(",", ""));
////						vPiutangIC_SP = vPiutangIC_SP + new Double( sheet.getCell(6,i).getContents().replaceAll(",", ""));		
////					}
//					
//					//Koreksi COGS untuk masuk ke Accrued COGS dan PNL This Year
//					if(i == 55 || i == 56 || i == 60 || i == 61 || i == 65 || i == 66 || i == 70 || i == 71
//							|| i == 75 || i == 76 || i == 80 || i == 81 ){
//						vAccuredCOGSAJ = vAccuredCOGSAJ + new Double( sheet.getCell(5,i).getContents().replaceAll(",", ""));
//						vAccuredCOGSSP = vAccuredCOGSSP + new Double( sheet.getCell(6,i).getContents().replaceAll(",", ""));		
//					}
//					
//					//Koreksi Management Intercoy COGS untuk masuk ke Hutang Dagang dan PNL This Year
//					if(i == 90){
//						vHutangIC_AJ = vHutangIC_AJ + new Double( sheet.getCell(5,i).getContents().replaceAll(",", ""));
//						vHutangIC_SP = vHutangIC_SP + new Double( sheet.getCell(6,i).getContents().replaceAll(",", ""));		
//					}
//					
//					//Koreksi Intercoy Total Sales untuk masuk ke PNL This Year
//					if(i == 48){
//						vKoreksiIntercoySales_AJ = vKoreksiIntercoySales_AJ + new Double( sheet.getCell(5,i).getContents().replaceAll(",", ""));
//						vKoreksiIntercoySales_SP = vKoreksiIntercoySales_SP + new Double( sheet.getCell(6,i).getContents().replaceAll(",", ""));		
//					}
//					//Koreksi Intercoy Total COGS untuk masuk ke PNL This Year
//					if(i == 91){
//						vKoreksiIntercoyCOGS_AJ = vKoreksiIntercoyCOGS_AJ + new Double( sheet.getCell(5,i).getContents().replaceAll(",", ""));
//						vKoreksiIntercoyCOGS_SP = vKoreksiIntercoyCOGS_SP + new Double( sheet.getCell(6,i).getContents().replaceAll(",", ""));		
//					}
//				}
//			
//			}
//					
//			workbook.close();	
//			
//			
//		} catch (BiffException e) {
//			Messagebox.show("Error : " + e.getMessage(), "Error",
//					Messagebox.OK, Messagebox.ERROR);
//
//		} catch (IOException e) {
//			Messagebox.show("Error : " + e.getMessage(), "Error",
//					Messagebox.OK, Messagebox.ERROR);
//
//		}
//		
//        outputFile.delete();
		
		
		Double vPembagi = new Double(String.valueOf(vAmountIn));
		
		if (CommonUtils.isNotEmpty(listNERACAAJ) && 
				CommonUtils.isNotEmpty(listNERACAAdjAJ) &&
				CommonUtils.isNotEmpty(listNERACASP) &&
				CommonUtils.isNotEmpty(listNERACAAdjSP)) {
			
			
			
			
			try {
				
				File outputFileExcel = new File(pathRpt.getNeracaKonsolidasi());
				
				WritableWorkbook outputFileWorkbook = Workbook
						.createWorkbook(outputFileExcel);

				WritableSheet sheetOut = outputFileWorkbook.createSheet(
						"Sheet", 0);
				
				sheetOut.addCell(new Label(0, 0, "Row"));
				sheetOut.addCell(new Label(1, 0, "Keterangan"));
				sheetOut.addCell(new Label(2, 0, "Amount - AJ"));
				sheetOut.addCell(new Label(3, 0, "Amount - AJ Adj"));
				sheetOut.addCell(new Label(4, 0, "Amount - Total AJ"));
				sheetOut.addCell(new Label(5, 0, "Amount - SP"));
				sheetOut.addCell(new Label(6, 0, "Amount - SP Adj"));
				sheetOut.addCell(new Label(7, 0, "Amount - Total SP"));
				sheetOut.addCell(new Label(8, 0, "Amount - Total Konsolidasi"));
				
				WritableCellFormat numformat = new WritableCellFormat(new NumberFormat("#,##0.00")); 
				
				int vIndex = 0;
				int vNumRow = 1;
				
				Double vTM0AJ = new Double(0);
				Double vTM0AJAdj = new Double(0);
				Double vTM0SP = new Double(0);
				Double vTM0SPAdj = new Double(0);				
				
				Double vTM1AJ = new Double(0);
				Double vTM1AJAdj = new Double(0);
				Double vTM1SP = new Double(0);
				Double vTM1SPAdj = new Double(0);
				
				Double vTM2AJ = new Double(0);
				Double vTM2AJAdj = new Double(0);
				Double vTM2SP = new Double(0);
				Double vTM2SPAdj = new Double(0);

				
				for (PNLList bs : listNERACAAJ) {
					PNLList listAdjAJ = listNERACAAdjAJ.get(vIndex);
					PNLList listSP = listNERACASP.get(vIndex);
					PNLList listAdjSP = listNERACAAdjSP.get(vIndex);
					
										
					Double AmountAJ = new Double(0);
					if(CommonUtils.isNotEmpty(bs.getAmount())){
						AmountAJ = new Double( bs.getAmount().replaceAll(",", ""));
					}
					
					Double AmountAdjAJ = new Double(0);
					if(CommonUtils.isNotEmpty(listAdjAJ.getAmount())){
						AmountAdjAJ = new Double( listAdjAJ.getAmount().replaceAll(",", ""));
					}
					
					Double AmountSP = new Double(0);
					if(CommonUtils.isNotEmpty(listSP.getAmount())){
						AmountSP = new Double( listSP.getAmount().replaceAll(",", ""));
					}
					
					Double AmountAdjSP = new Double(0);
					if(CommonUtils.isNotEmpty(listAdjSP.getAmount())){
						AmountAdjSP = new Double( listAdjSP.getAmount().replaceAll(",", ""));
					}
					
							
					AmountAJ = round((AmountAJ / vPembagi),0);
					AmountAdjAJ = round((AmountAdjAJ / vPembagi),0);
					AmountSP = round((AmountSP / vPembagi),0);
					AmountAdjSP = round((AmountAdjSP / vPembagi),0);
					
					Double AmountTotalAJ =  AmountAJ+AmountAdjAJ;
					Double AmountTotalSP = AmountSP+AmountAdjSP;
					Double AmountTotal = AmountAJ+AmountAdjAJ+AmountSP+AmountAdjSP;
					
					
					
							
					sheetOut.addCell(new Label(0, vNumRow, bs.getNoUrut()));
					sheetOut.addCell(new Label(1, vNumRow, bs.getKeterangan()));
					//AR / Piutang dagang
					if (bs.getNoUrut().equals("1102")){
						sheetOut.addCell(new Number(2, vNumRow, AmountAJ, numformat));
						sheetOut.addCell(new Number(3, vNumRow, (AmountAdjAJ+(vPiutangIC_AJ+vPiutangIC_AJLastYear)), numformat));
						sheetOut.addCell(new Number(4, vNumRow, (AmountAJ+(AmountAdjAJ+(vPiutangIC_AJ+vPiutangIC_AJLastYear))), numformat));
						sheetOut.addCell(new Number(5, vNumRow, AmountSP, numformat));
						sheetOut.addCell(new Number(6, vNumRow, (AmountAdjSP-(vPiutangIC_SP+vPiutangIC_SPLastYear)), numformat));
						sheetOut.addCell(new Number(7, vNumRow, (AmountSP+(AmountAdjSP+(vPiutangIC_SP+vPiutangIC_SPLastYear))), numformat));
						sheetOut.addCell(new Number(8, vNumRow, (AmountAJ+(AmountAdjAJ+(vPiutangIC_AJ+vPiutangIC_AJLastYear))+AmountSP+(AmountAdjSP+(vPiutangIC_SP+vPiutangIC_SPLastYear))),numformat));
						
						vTM1AJ = vTM1AJ+AmountAJ;
						vTM1AJAdj = vTM1AJAdj+(AmountAdjAJ+(vPiutangIC_AJ+vPiutangIC_AJLastYear));
						vTM1SP = vTM1SP+AmountSP;
						vTM1SPAdj = vTM1SPAdj+(AmountAdjSP+(vPiutangIC_SP+vPiutangIC_SPLastYear));
						
						vTM2AJ = vTM2AJ+AmountAJ;
						vTM2AJAdj = vTM2AJAdj+(AmountAdjAJ+(vPiutangIC_AJ+vPiutangIC_AJLastYear));
						vTM2SP = vTM2SP+AmountSP;
						vTM2SPAdj = vTM2SPAdj+(AmountAdjSP+(vPiutangIC_SP+vPiutangIC_SPLastYear));
						
					} else {		
						// Accured COGS
						if (bs.getNoUrut().equals("1105")){
							sheetOut.addCell(new Number(2, vNumRow, AmountAJ, numformat));
							sheetOut.addCell(new Number(3, vNumRow, (AmountAdjAJ-(vAccuredCOGSAJ+vAccuredCOGSAJLastYear)), numformat));
							sheetOut.addCell(new Number(4, vNumRow, (AmountAJ+(AmountAdjAJ-(vAccuredCOGSAJ+vAccuredCOGSAJLastYear))), numformat));
							sheetOut.addCell(new Number(5, vNumRow, AmountSP, numformat));
							sheetOut.addCell(new Number(6, vNumRow, (AmountAdjSP-(vAccuredCOGSSP+vAccuredCOGSSPLastYear)), numformat));
							sheetOut.addCell(new Number(7, vNumRow, (AmountSP+(AmountAdjSP-(vAccuredCOGSSP+vAccuredCOGSSPLastYear))), numformat));
							sheetOut.addCell(new Number(8, vNumRow, (AmountAJ+(AmountAdjAJ-(vAccuredCOGSAJ+vAccuredCOGSAJLastYear))+AmountSP+(AmountAdjSP-(vAccuredCOGSSP+vAccuredCOGSSPLastYear))),numformat));
							
							vTM1AJ = vTM1AJ+AmountAJ;
							vTM1AJAdj = vTM1AJAdj+(AmountAdjAJ-(vAccuredCOGSAJ+vAccuredCOGSAJLastYear));
							vTM1SP = vTM1SP+AmountSP;
							vTM1SPAdj = vTM1SPAdj+(AmountAdjSP-(vAccuredCOGSSP+vAccuredCOGSSPLastYear));
							
							vTM2AJ = vTM2AJ+AmountAJ;
							vTM2AJAdj = vTM2AJAdj+(AmountAdjAJ-(vAccuredCOGSAJ+vAccuredCOGSAJLastYear));
							vTM2SP = vTM2SP+AmountSP;
							vTM2SPAdj = vTM2SPAdj+(AmountAdjSP-(vAccuredCOGSSP+vAccuredCOGSSPLastYear));
							
						} else {
							// Hutang Dagang
							if (bs.getNoUrut().equals("2102")){
								sheetOut.addCell(new Number(2, vNumRow, AmountAJ, numformat));
								sheetOut.addCell(new Number(3, vNumRow, (AmountAdjAJ-(vHutangIC_AJ+vHutangIC_AJLastYear)), numformat));
								sheetOut.addCell(new Number(4, vNumRow, (AmountAJ+(AmountAdjAJ-(vHutangIC_AJ+vHutangIC_AJLastYear))), numformat));
								sheetOut.addCell(new Number(5, vNumRow, AmountSP, numformat));
								sheetOut.addCell(new Number(6, vNumRow, (AmountAdjSP-(vHutangIC_SP+vHutangIC_SPLastYear)), numformat));
								sheetOut.addCell(new Number(7, vNumRow, (AmountSP+(AmountAdjSP-(vHutangIC_SP+vHutangIC_SPLastYear))), numformat));
								sheetOut.addCell(new Number(8, vNumRow, (AmountAJ+(AmountAdjAJ-(vHutangIC_AJ+vHutangIC_AJLastYear))+AmountSP+(AmountAdjSP-(vHutangIC_SP+vHutangIC_SPLastYear))),numformat));
								
								vTM1AJ = vTM1AJ+AmountAJ;
								vTM1AJAdj = vTM1AJAdj+(AmountAdjAJ-(vHutangIC_AJ+vHutangIC_AJLastYear));
								vTM1SP = vTM1SP+AmountSP;
								vTM1SPAdj = vTM1SPAdj+(AmountAdjSP-(vHutangIC_SP+vHutangIC_SPLastYear));
								
								vTM2AJ = vTM2AJ+AmountAJ;
								vTM2AJAdj = vTM2AJAdj+(AmountAdjAJ-(vHutangIC_AJ+vHutangIC_AJLastYear));
								vTM2SP = vTM2SP+AmountSP;
								vTM2SPAdj = vTM2SPAdj+(AmountAdjSP-(vHutangIC_SP+vHutangIC_SPLastYear));
								
							} else {
								//Accured Sales
								if (bs.getNoUrut().equals("2107")){
									sheetOut.addCell(new Number(2, vNumRow, AmountAJ, numformat));
									sheetOut.addCell(new Number(3, vNumRow, (AmountAdjAJ-(vAccuredSalesAJ+vAccuredSalesAJLastYear)), numformat));
									sheetOut.addCell(new Number(4, vNumRow, (AmountAJ+(AmountAdjAJ-(vAccuredSalesAJ+vAccuredSalesAJLastYear))), numformat));
									sheetOut.addCell(new Number(5, vNumRow, AmountSP, numformat));
									sheetOut.addCell(new Number(6, vNumRow, (AmountAdjSP-(vAccuredSalesSP+vAccuredSalesSPLastYear)), numformat));
									sheetOut.addCell(new Number(7, vNumRow, (AmountSP+(AmountAdjSP-(vAccuredSalesSP+vAccuredSalesSPLastYear))), numformat));
									sheetOut.addCell(new Number(8, vNumRow, (AmountAJ+(AmountAdjAJ-(vAccuredSalesAJ+vAccuredSalesAJLastYear))+AmountSP+(AmountAdjSP-(vAccuredSalesSP+vAccuredSalesSPLastYear))),numformat));
									
									vTM1AJ = vTM1AJ+AmountAJ;
									vTM1AJAdj = vTM1AJAdj+(AmountAdjAJ-(vAccuredSalesAJ+vAccuredSalesAJLastYear));
									vTM1SP = vTM1SP+AmountSP;
									vTM1SPAdj = vTM1SPAdj+(AmountAdjSP-(vAccuredSalesSP+vAccuredSalesSPLastYear));
									
									vTM2AJ = vTM2AJ+AmountAJ;
									vTM2AJAdj = vTM2AJAdj+(AmountAdjAJ-(vAccuredSalesAJ+vAccuredSalesAJLastYear));
									vTM2SP = vTM2SP+AmountSP;
									vTM2SPAdj = vTM2SPAdj+(AmountAdjSP-(vAccuredSalesSP+vAccuredSalesSPLastYear));
									
								} else {
									//Retain Erning
									if (bs.getNoUrut().equals("2302")){
										sheetOut.addCell(new Number(2, vNumRow, AmountAJ, numformat));
										sheetOut.addCell(new Number(3, vNumRow, (AmountAdjAJ + (vAccuredSalesAJLastYear) - (vAccuredCOGSAJLastYear) + (vHutangIC_AJLastYear) + ((vKoreksiIntercoySales_AJLastYear)-(vKoreksiIntercoyCOGS_AJLastYear))), numformat));
										sheetOut.addCell(new Number(4, vNumRow, (((AmountAJ+AmountAdjAJ) + (vAccuredSalesAJLastYear) - (vAccuredCOGSAJLastYear) + (vHutangIC_AJLastYear) + ((vKoreksiIntercoySales_AJLastYear)-(vKoreksiIntercoyCOGS_AJLastYear)))), numformat));
										sheetOut.addCell(new Number(5, vNumRow, AmountSP, numformat));
										sheetOut.addCell(new Number(6, vNumRow, (AmountAdjSP + (vAccuredSalesSPLastYear) - (vAccuredCOGSSPLastYear) + (vHutangIC_SPLastYear) + ((vKoreksiIntercoySales_SPLastYear)-(vKoreksiIntercoyCOGS_SPLastYear))), numformat));
										sheetOut.addCell(new Number(7, vNumRow, (((AmountSP+AmountAdjSP) + (vAccuredSalesSPLastYear) - (vAccuredCOGSSPLastYear) + (vHutangIC_SPLastYear) + ((vKoreksiIntercoySales_SPLastYear)-(vKoreksiIntercoyCOGS_SPLastYear)))), numformat));
										sheetOut.addCell(new Number(8, vNumRow, ( ((AmountAJ+AmountAdjAJ) + (vAccuredSalesAJLastYear) - (vAccuredCOGSAJLastYear) + (vHutangIC_AJLastYear) + ((vKoreksiIntercoySales_AJLastYear)-(vKoreksiIntercoyCOGS_AJLastYear))) + ((AmountSP+AmountAdjSP) + (vAccuredSalesSPLastYear) - (vAccuredCOGSSPLastYear) + (vHutangIC_SPLastYear) + ((vKoreksiIntercoySales_SPLastYear)-(vKoreksiIntercoyCOGS_SPLastYear))) ),numformat));
			
//										System.out.println("AJ : "+AmountAJ);
//										System.out.println("AJ aDJ : "+AmountAdjAJ);
//										
//										//System.out.println("vAccuredSalesAJ : "+vAccuredSalesAJ);
//										System.out.println("vAccuredSalesAJLastYear : "+vAccuredSalesAJLastYear);
//										
//										//System.out.println("vAccuredCOGSAJ : "+vAccuredCOGSAJ);
//										System.out.println("vAccuredCOGSAJLastYear : "+vAccuredCOGSAJLastYear);
//										
//										//System.out.println("vHutangIC_AJ : "+vHutangIC_AJ);
//										System.out.println("vHutangIC_AJLastYear : "+vHutangIC_AJLastYear);
//										
//										//System.out.println("vKoreksiIntercoySales_AJ : "+vKoreksiIntercoySales_AJ);
//										System.out.println("vKoreksiIntercoySales_AJLastYear : "+vKoreksiIntercoySales_AJLastYear);
//										
//										//System.out.println("vKoreksiIntercoyCOGS_AJ : "+vKoreksiIntercoyCOGS_AJ);
//										System.out.println("vKoreksiIntercoyCOGS_AJLastYear : "+vKoreksiIntercoyCOGS_AJLastYear);
//								
//										System.out.println(" =======================================================");
//
//										System.out.println("SP : "+AmountSP);
//										System.out.println("SP aDJ : "+AmountAdjSP);
//										
//										//System.out.println("vAccuredSalesSP : "+vAccuredSalesSP);
//										System.out.println("vAccuredSalesSPLastYear : "+vAccuredSalesSPLastYear);
//										
//										//System.out.println("vAccuredCOGSSP : "+vAccuredCOGSSP);
//										System.out.println("vAccuredCOGSSPLastYear : "+vAccuredCOGSSPLastYear);
//										
//										//System.out.println("vHutangIC_SP : "+vHutangIC_SP);
//										System.out.println("vHutangIC_SPLastYear : "+vHutangIC_SPLastYear);
//										
//										//System.out.println("vKoreksiIntercoySales_SP : "+vKoreksiIntercoySales_SP);
//										System.out.println("vKoreksiIntercoySales_SPLastYear : "+vKoreksiIntercoySales_SPLastYear);
//										
//										//System.out.println("vKoreksiIntercoyCOGS_SP : "+vKoreksiIntercoyCOGS_SP);
//										System.out.println("vKoreksiIntercoyCOGS_SPLastYear : "+vKoreksiIntercoyCOGS_SPLastYear);											
										
										vTM1AJ = vTM1AJ+AmountAJ;
										vTM1AJAdj = ((vTM1AJAdj+AmountAdjAJ) + (vAccuredSalesAJLastYear) - (vAccuredCOGSAJLastYear) + (vHutangIC_AJLastYear) + ((vKoreksiIntercoySales_AJLastYear) - (vKoreksiIntercoyCOGS_AJLastYear)));
										vTM1SP = vTM1SP+AmountSP;
										vTM1SPAdj = ((vTM1SPAdj+AmountAdjSP) + (vAccuredSalesSPLastYear) - (vAccuredCOGSSPLastYear) + (vHutangIC_SPLastYear) + ((vKoreksiIntercoySales_SPLastYear) - (vKoreksiIntercoyCOGS_SPLastYear)));
										
										vTM2AJ = vTM2AJ+AmountAJ;
										vTM2AJAdj = ((vTM2AJAdj+AmountAdjAJ) + (vAccuredSalesAJLastYear) - (vAccuredCOGSAJLastYear) + (vHutangIC_AJLastYear) + ((vKoreksiIntercoySales_AJLastYear)-(vKoreksiIntercoyCOGS_AJLastYear)));
										vTM2SP = vTM2SP+AmountSP;
										vTM2SPAdj = ((vTM2SPAdj+AmountAdjSP) + (vAccuredSalesSPLastYear) - (vAccuredCOGSSPLastYear) + (vHutangIC_SPLastYear) + ((vKoreksiIntercoySales_SPLastYear)-(vKoreksiIntercoyCOGS_SPLastYear)));
										
									
										
									} else {
										//Profit and Sales
										if (bs.getNoUrut().equals("2303")){
											sheetOut.addCell(new Number(2, vNumRow, AmountAJ, numformat));
											sheetOut.addCell(new Number(3, vNumRow, (AmountAdjAJ + (vAccuredSalesAJ) - (vAccuredCOGSAJ) + (vHutangIC_AJ) + ((vKoreksiIntercoySales_AJ)-(vKoreksiIntercoyCOGS_AJ))), numformat));
											sheetOut.addCell(new Number(4, vNumRow, (((AmountAJ+AmountAdjAJ) + (vAccuredSalesAJ) - (vAccuredCOGSAJ) + (vHutangIC_AJ) + ((vKoreksiIntercoySales_AJ)-(vKoreksiIntercoyCOGS_AJ)))), numformat));
											sheetOut.addCell(new Number(5, vNumRow, AmountSP, numformat));
											sheetOut.addCell(new Number(6, vNumRow, (AmountAdjSP + (vAccuredSalesSP) - (vAccuredCOGSSP) + (vHutangIC_SP) + ((vKoreksiIntercoySales_SP)-(vKoreksiIntercoyCOGS_SP))), numformat));
											sheetOut.addCell(new Number(7, vNumRow, (((AmountSP+AmountAdjSP) + (vAccuredSalesSP) - (vAccuredCOGSSP) + (vHutangIC_SP) + ((vKoreksiIntercoySales_SP)-(vKoreksiIntercoyCOGS_SP)))), numformat));
											sheetOut.addCell(new Number(8, vNumRow, ( ((AmountAJ+AmountAdjAJ) + (vAccuredSalesAJ) - (vAccuredCOGSAJ) + (vHutangIC_AJ) + ((vKoreksiIntercoySales_AJ)-(vKoreksiIntercoyCOGS_AJ))) + ((AmountSP+AmountAdjSP) + (vAccuredSalesSP) - (vAccuredCOGSSP) + (vHutangIC_SP) + ((vKoreksiIntercoySales_SP)-(vKoreksiIntercoyCOGS_SP))) ),numformat));
										
//											System.out.println("AJ : "+AmountAJ);
//											System.out.println("AJ aDJ : "+AmountAdjAJ);
//											
//											System.out.println("vAccuredSalesAJ : "+vAccuredSalesAJ);
//											System.out.println("vAccuredSalesAJLastYear : "+vAccuredSalesAJLastYear);
//											
//											System.out.println("vAccuredCOGSAJ : "+vAccuredCOGSAJ);
//											System.out.println("vAccuredCOGSAJLastYear : "+vAccuredCOGSAJLastYear);
//											
//											System.out.println("vHutangIC_AJ : "+vHutangIC_AJ);
//											System.out.println("vHutangIC_AJLastYear : "+vHutangIC_AJLastYear);
//											
//											System.out.println("vKoreksiIntercoySales_AJ : "+vKoreksiIntercoySales_AJ);
//											System.out.println("vKoreksiIntercoySales_AJLastYear : "+vKoreksiIntercoySales_AJLastYear);
//											
//											System.out.println("vKoreksiIntercoyCOGS_AJ : "+vKoreksiIntercoyCOGS_AJ);
//											System.out.println("vKoreksiIntercoyCOGS_AJLastYear : "+vKoreksiIntercoyCOGS_AJLastYear);
//									
//											System.out.println(" =======================================================");
//	
//											System.out.println("SP : "+AmountSP);
//											System.out.println("SP aDJ : "+AmountAdjSP);
//											
//											System.out.println("vAccuredSalesSP : "+vAccuredSalesSP);
//											System.out.println("vAccuredSalesSPLastYear : "+vAccuredSalesSPLastYear);
//											
//											System.out.println("vAccuredCOGSSP : "+vAccuredCOGSSP);
//											System.out.println("vAccuredCOGSSPLastYear : "+vAccuredCOGSSPLastYear);
//											
//											System.out.println("vHutangIC_SP : "+vHutangIC_SP);
//											System.out.println("vHutangIC_SPLastYear : "+vHutangIC_SPLastYear);
//											
//											System.out.println("vKoreksiIntercoySales_SP : "+vKoreksiIntercoySales_SP);
//											System.out.println("vKoreksiIntercoySales_SPLastYear : "+vKoreksiIntercoySales_SPLastYear);
//											
//											System.out.println("vKoreksiIntercoyCOGS_SP : "+vKoreksiIntercoyCOGS_SP);
//											System.out.println("vKoreksiIntercoyCOGS_SPLastYear : "+vKoreksiIntercoyCOGS_SPLastYear);
											
											vTM1AJ = vTM1AJ+AmountAJ;
											vTM1AJAdj = ((vTM1AJAdj+AmountAdjAJ) + (vAccuredSalesAJ) - (vAccuredCOGSAJ) + (vHutangIC_AJ) + ((vKoreksiIntercoySales_AJ) - (vKoreksiIntercoyCOGS_AJ)));
											vTM1SP = vTM1SP+AmountSP;
											vTM1SPAdj = ((vTM1SPAdj+AmountAdjSP) + (vAccuredSalesSP) - (vAccuredCOGSSP) + (vHutangIC_SP) + ((vKoreksiIntercoySales_SP) - (vKoreksiIntercoyCOGS_SP)));
											
											vTM2AJ = vTM2AJ+AmountAJ;
											vTM2AJAdj = ((vTM2AJAdj+AmountAdjAJ) + (vAccuredSalesAJ) - (vAccuredCOGSAJ) + (vHutangIC_AJ) + ((vKoreksiIntercoySales_AJ)-(vKoreksiIntercoyCOGS_AJ)));
											vTM2SP = vTM2SP+AmountSP;
											vTM2SPAdj = ((vTM2SPAdj+AmountAdjSP) + (vAccuredSalesSP) - (vAccuredCOGSSP) + (vHutangIC_SP) + ((vKoreksiIntercoySales_SP)-(vKoreksiIntercoyCOGS_SP)));
											
										} else {
											if(bs.getNoUrut().equals("TM1")){
														
												sheetOut.addCell(new Number(2, vNumRow, vTM1AJ, numformat));
												sheetOut.addCell(new Number(3, vNumRow, vTM1AJAdj, numformat));
												sheetOut.addCell(new Number(4, vNumRow, (vTM1AJ+vTM1AJAdj), numformat));
												sheetOut.addCell(new Number(5, vNumRow, vTM1SP, numformat));
												sheetOut.addCell(new Number(6, vNumRow, vTM1SPAdj, numformat));
												sheetOut.addCell(new Number(7, vNumRow, (vTM1SP+vTM1SPAdj), numformat));
												sheetOut.addCell(new Number(8, vNumRow, (vTM1AJ+vTM1AJAdj+vTM1SP+vTM1SPAdj),numformat));
												
												vTM0AJ = new Double(0);
												vTM0AJAdj = new Double(0);
												vTM0SP = new Double(0);
												vTM0SPAdj = new Double(0);
												
												vTM1AJ = new Double(0);
												vTM1AJAdj = new Double(0);
												vTM1SP = new Double(0);
												vTM1SPAdj = new Double(0);
												
												
											} else {
												if(bs.getNoUrut().equals("TM2")){										
													sheetOut.addCell(new Number(2, vNumRow, vTM2AJ, numformat));
													sheetOut.addCell(new Number(3, vNumRow, vTM2AJAdj, numformat));
													sheetOut.addCell(new Number(4, vNumRow, (vTM2AJ+vTM2AJAdj), numformat));
													sheetOut.addCell(new Number(5, vNumRow, vTM2SP, numformat));
													sheetOut.addCell(new Number(6, vNumRow, vTM2SPAdj, numformat));
													sheetOut.addCell(new Number(7, vNumRow, (vTM2SP+vTM2SPAdj), numformat));
													sheetOut.addCell(new Number(8, vNumRow, (vTM2AJ+vTM2AJAdj+vTM2SP+vTM2SPAdj),numformat));
													
													vTM0AJ = new Double(0);
													vTM0AJAdj = new Double(0);
													vTM0SP = new Double(0);
													vTM0SPAdj = new Double(0);
													
													vTM1AJ = new Double(0);
													vTM1AJAdj = new Double(0);
													vTM1SP = new Double(0);
													vTM1SPAdj = new Double(0);
													
													vTM2AJ = new Double(0);
													vTM2AJAdj = new Double(0);
													vTM2SP = new Double(0);
													vTM2SPAdj = new Double(0);	
												} else {
													if(bs.getNoUrut().equals("TM0")){										
														sheetOut.addCell(new Number(2, vNumRow, vTM0AJ, numformat));
														sheetOut.addCell(new Number(3, vNumRow, vTM0AJAdj, numformat));
														sheetOut.addCell(new Number(4, vNumRow, (vTM0AJ+vTM0AJAdj), numformat));
														sheetOut.addCell(new Number(5, vNumRow, vTM0SP, numformat));
														sheetOut.addCell(new Number(6, vNumRow, vTM0SPAdj, numformat));
														sheetOut.addCell(new Number(7, vNumRow, (vTM0SP+vTM0SPAdj), numformat));
														sheetOut.addCell(new Number(8, vNumRow, (vTM0AJ+vTM0AJAdj+vTM0SP+vTM0SPAdj),numformat));
													
														vTM0AJ = new Double(0);
														vTM0AJAdj = new Double(0);
														vTM0SP = new Double(0);
														vTM0SPAdj = new Double(0);
														
													} else {
														if(AmountAJ != 0){
															sheetOut.addCell(new Number(2, vNumRow, AmountAJ,numformat));
															vTM0AJ = vTM0AJ + AmountAJ;
															vTM1AJ = vTM1AJ + AmountAJ;
															vTM2AJ = vTM2AJ + AmountAJ;
														}
														
														if(AmountAdjAJ != 0){
															sheetOut.addCell(new Number(3, vNumRow, AmountAdjAJ,numformat));
															vTM0AJAdj = vTM0AJAdj + AmountAdjAJ;
															vTM1AJAdj = vTM1AJAdj + AmountAdjAJ;
															vTM2AJAdj = vTM2AJAdj + AmountAdjAJ;
														}
														
														if(AmountTotalAJ != 0){
															sheetOut.addCell(new Number(4, vNumRow, AmountTotalAJ,numformat));
														}
														
														
														if(AmountSP != 0){
															sheetOut.addCell(new Number(5, vNumRow, AmountSP,numformat));
															vTM0SP = vTM0SP + AmountSP;
															vTM1SP = vTM1SP + AmountSP;
															vTM2SP = vTM2SP + AmountSP;
														}											
														
														
														if(AmountAdjSP != 0){
															sheetOut.addCell(new Number(6, vNumRow, AmountAdjSP,numformat));
															vTM0SPAdj = vTM0SPAdj + AmountAdjSP;
															vTM1SPAdj = vTM1SPAdj + AmountAdjSP;
															vTM2SPAdj = vTM2SPAdj + AmountAdjSP;
														}
														
														if(AmountTotalSP != 0){
															sheetOut.addCell(new Number(7, vNumRow, AmountTotalSP,numformat));
														}
														
														if(AmountTotal != 0){
															sheetOut.addCell(new Number(8, vNumRow, AmountTotal,numformat));
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					
					
					//sheetOut.addCell(new Label(2, vNumRow, bs.getAmount()));
					//sheetOut.addCell(new Label(3, vNumRow, listSP.getAmount()));
					vNumRow ++;
					
					vIndex = vIndex +1;
				}
				
				outputFileWorkbook.write();

				InputStream mediais = new FileInputStream(outputFileExcel);
				AMedia amedia = new AMedia("NERACAKonsolidasi.xls", "xls", "application/vnd.ms-excel", mediais);
				
				Filedownload.save(amedia);
				
				outputFileWorkbook.close();
				
				outputFileExcel.delete();

			} catch (WriteException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);
			}
		}

	}
	
}