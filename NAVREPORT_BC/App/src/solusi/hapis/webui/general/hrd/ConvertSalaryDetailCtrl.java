package solusi.hapis.webui.general.hrd;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
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
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.model.Temp07SalaryHapis;
import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.navbi.service.Temp07SalaryHapisService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class ConvertSalaryDetailCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	private String timeStamp;
	
	protected Textbox lbl1;
	
	
	protected Radiogroup rdgJnsReport;	 
	protected Radio rd1;
	protected Radio rd2;
	


	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	List<Temp07SalaryHapis> listSalaryHapis = new ArrayList<Temp07SalaryHapis>();
	
	private Temp07SalaryHapisService temp07SalaryHapisService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		timeStamp = String.valueOf(System.currentTimeMillis());
    	
    	
		rd1.setSelected(true); 
    	
	}
	
	public void onClick$btnOK1(Event event) throws InterruptedException, ParseException {
		
		
		Media media = Fileupload.get("Please select a File", "Upload");
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();
				
				int vJmlKolom = sheet.getColumns();


				if(CommonUtils.isNotEmpty(listSalaryHapis)){
					//temp07SalaryHapisService.save(listSalaryHapis);		
					listSalaryHapis.clear();
				}
				
				int posArea = -1;
				int posJobGrade = -1;
				int posUnitUsaha = -1;
											
				int posD_BSAL = 0;							
				int posD_TJHP = 0;							
				int posD_AMED = 0;					
				int posD_TMED = 0;			
				int posD_TDNS = 0;
				int posD_TD24 = 0;							
				int posD_TOTX = 0;						
				int posD_TONX = 0;						
				int posD_LBR  = 0;					
				int posD_BNSA  = 0;					
				int posD_BNSN  = 0;	
				int posD_TJKS  = 0;	
				int posD_TKHS  = 0;						
				int posD_KOMS  = 0;											
				int posD_TotalTHR  = 0;							
				int posD_JKK  = 0;					
				int posD_JPK = 0;													
				int posD_CPSG = 0;
				int posP_PINJ = 0;
				int posP_ADVP = 0;
				int posP_PADV = 0;
				int posP_ASXT = 0;
				int posP_POTL = 0;
				int posP_PJK = 0;
				int posP_BPJK = 0;
				int posP_JKK = 0;
				int posD_JPS = 0;
				int posD_JHT = 0;

				String strPeriode = "000000";
				strPeriode = sheet.getCell(10,2).getContents();

				
				for (int i = 0; i < vJmlData; i++){
				
					if (i == 4 || i == 5){
						for (int j=0 ; j < vJmlKolom ; j++){
							// Posisi Kolom Area
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("Branch")==true){
								posArea = j;
							}
							
							// Posisi Kolom Job Grade
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("JobGrade")==true){
								posJobGrade = j;
							}
														
							// Posisi Kolom Unit Usaha
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("UnitUsaha")==true){
								posUnitUsaha = j;
							}		
							
							
							// Posisi Kolom Basic Salary
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("BasicSalary")==true){
								posD_BSAL = j;
							}

							//Tunjangan Handphone
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("TunjanganHandphone")==true){
								posD_TJHP = j;
							}
							
							//Tunjangan Asuransi
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("TunjanganAsuransi")==true){
								posD_AMED = j;
							}
							
							//Tunjangan Medical	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("TunjanganMedical")==true){
								posD_TMED = j;
							}
							
							//Tunjangan Dinas	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("TunjanganDinas")==true){
								posD_TDNS = j;
							}
							
							//Tunjangan Dinas 24 Jam
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("TunjanganDinas24Jam")==true){
								posD_TD24 = j;
							}
							
							//Tunjangan Other Allowance Tax	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("TunjanganOtherAllowanceTax")==true){
								posD_TOTX = j;
							}
							
							//Tunjangan Other Allowance No Tax	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("TunjanganOtherAllowanceNoTax")==true){
								posD_TONX = j;
							}
							
							//Overtime/Lembur
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("Overtime")==true){
								posD_LBR  = j;
							}
							
							//Bonus Sales	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("BonusSales")==true){
								posD_BNSA  = j;
							}
														
							//Bonus Non Sales	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("BonusNonSales")==true){
								posD_BNSN  = j;
							}
														
							//Tunjangan Suka Duka	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("TunjanganSukaDuka")==true){
								posD_TJKS = j;
							}

							//Tunjangan Khusus	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("TunjanganKhusus")==true){
								posD_TKHS  = j;
							}

							//Komisi	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("Komisi")==true){
								posD_KOMS  = j;
							}
							
							//THR
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("THR")==true){
								posD_TotalTHR  = j;
							}
							
							//JKK/JKM
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("JKK/JKM")==true){
								posD_JKK  = j;
							}
							
							//BPJS Kes
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("BPJSKes")==true){
								posD_JPK   = j;
							}
							
							//Pesangon
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("Pesangon")==true){
								posD_CPSG   = j;
							}
							
							// Potongan -------------------------------------------------------------
							//Piutang Karyawan	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("PiutangKaryawan")==true){
								posP_PINJ   = j;
							}
							
							//Potongan Petty Cash
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("PotonganPettyCash")==true){
								posP_ADVP   = j;
							}
							
							//Potongan Advance Payroll
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("PotonganAdvancePayroll")==true){
								posP_PADV   = j;
							}
							
							//Potongan Asuransi Ekstra	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("PotonganAsuransiEkstra")==true){
								posP_ASXT   = j;
							}
							
							//Potongan Lain-lain	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("PotonganLain-lain")==true){
								posP_POTL   = j;
							}
							
							//Tax PPH21	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("TaxPPH21")==true){
								posP_PJK   = j;
							}
														
							//BPJS Kesehatan		
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("BPJSKesehatan")==true){
								posP_BPJK   = j;
							}
							
							//JKK / JKM								
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("JKK/JKM")==true){
								posP_JKK   = j;
							}
							
							//Jaminan Pensiun			
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("JaminanPensiun")==true){
								posD_JPS   = j;
							}
							
							//Jaminan Hari Tua	
							if ((sheet.getCell(j,i).getContents().replaceAll("\\s+","")).equals("JaminanHariTua")==true){
								posD_JHT  = j;
							}
						}
					}
					
					
					if (i >= 5){
						if(sheet.getCell(4,i).getContents().equals("") == false) {
							
							//System.out.println("Isi Data : "+strD_BSAL + " - "+strD_CPSG + " - "+strD_JHT);
											
									
						
							String strArea = sheet.getCell(posArea,i).getContents();
							String strJobGrade = sheet.getCell(posJobGrade,i).getContents();							
							String strUnitUsaha = sheet.getCell(posUnitUsaha,i).getContents();
							
							//Basic Salary
							String strD_BSAL = sheet.getCell(posD_BSAL,i).getContents();
							strD_BSAL = strD_BSAL.replaceAll(",", "");
							
							
							//Tunjangan Handphone
							String strD_TJHP = sheet.getCell(posD_TJHP,i).getContents();
							strD_TJHP = strD_TJHP.replaceAll(",", "");
							
							//Tunjangan Asuransi
							String strD_AMED = sheet.getCell(posD_AMED,i).getContents();
							strD_AMED = strD_AMED.replaceAll(",", "");
							
							//Tunjangan Medical	
							String strD_TMED = sheet.getCell(posD_TMED,i).getContents();
							strD_TMED = strD_TMED.replaceAll(",", "");
							
							//Tunjangan Dinas							
							String strD_TDNS = sheet.getCell(posD_TDNS,i).getContents();
							strD_TDNS = strD_TDNS.replaceAll(",", "");
							
							//Tunjangan Dinas 24 Jam	
							String strD_TD24 = sheet.getCell(posD_TD24,i).getContents();
							strD_TD24 = strD_TD24.replaceAll(",", "");
							
							//Tunjangan Other Allowance Tax	
							String strD_TOTX = sheet.getCell(posD_TOTX,i).getContents();
							strD_TOTX = strD_TOTX.replaceAll(",", "");
							
							//Tunjangan Other Allowance No Tax	
							String strD_TONX = sheet.getCell(posD_TONX,i).getContents();
							strD_TONX = strD_TONX.replaceAll(",", "");
							
							//Overtime/Lembur
							String strD_LBR = sheet.getCell(posD_LBR,i).getContents();
							strD_LBR = strD_LBR.replaceAll(",", "");
							
							//Bonus Sales	
							String strD_BNSA = sheet.getCell(posD_BNSA,i).getContents();
							strD_BNSA = strD_BNSA.replaceAll(",", "");
							
							//Bonus Non Sales	
							String strD_BNSN = sheet.getCell(posD_BNSN,i).getContents();
							strD_BNSN = strD_BNSN.replaceAll(",", "");
							
							//Tunjangan Suka Duka	
							String strD_TJKS = sheet.getCell(posD_TJKS,i).getContents();
							strD_TJKS = strD_TJKS.replaceAll(",", "");
							
							//Tunjangan Khusus	
							String strD_TKHS = sheet.getCell(posD_TKHS,i).getContents();
							strD_TKHS = strD_TKHS.replaceAll(",", "");
							
							//Komisi	
							String strD_KOMS = sheet.getCell(posD_KOMS,i).getContents();
							strD_KOMS = strD_KOMS.replaceAll(",", "");
							
							//THR
							String strD_TotalTHR = sheet.getCell(posD_TotalTHR,i).getContents();
							strD_TotalTHR = strD_TotalTHR.replaceAll(",", "");
							
							//JKK/JKM
							String strD_JKK = sheet.getCell(posD_JKK,i).getContents();
							strD_JKK = strD_JKK.replaceAll(",", "");
							String strD_JKM = "0.00"; 
							
							//BPJS Kes
							String strD_JPK = sheet.getCell(posD_JPK,i).getContents();
							strD_JPK = strD_JPK.replaceAll(",", "");
						
							//Pesangon
							String strD_CPSG = sheet.getCell(posD_CPSG,i).getContents();
							strD_CPSG = strD_CPSG.replaceAll(",", "");
							
								
							
							// # POTONGAN MULAI SINI =============================================
							//Piutang Karyawan	
							String strP_PINJ = sheet.getCell(posP_PINJ,i).getContents();
							strP_PINJ = strP_PINJ.replaceAll(",", "");
							
							//Potongan Petty Cash
							String strP_ADVP = sheet.getCell(posP_ADVP,i).getContents();
							strP_ADVP = strP_ADVP.replaceAll(",", "");
							
							//Potongan Advance Payroll
							String strP_PADV = sheet.getCell(posP_PADV,i).getContents();
							strP_PADV = strP_PADV.replaceAll(",", "");
							
							//Potongan Asuransi Ekstra	
							String strP_ASXT = sheet.getCell(posP_ASXT,i).getContents();
							strP_ASXT = strP_ASXT.replaceAll(",", "");
							
							//Potongan Lain-lain	
							String strP_POTL = sheet.getCell(posP_POTL,i).getContents();
							strP_POTL = strP_POTL.replaceAll(",", "");
							
							//Tax PPH21	
							String strP_PJK = sheet.getCell(posP_PJK,i).getContents();
							strP_PJK = strP_PJK.replaceAll(",", "");
							
							
							//BPJS Kesehatan		
							String strP_BPJK = sheet.getCell(posP_BPJK,i).getContents();
							strP_BPJK = strP_BPJK.replaceAll(",", "");
							
							
							//BPJS Family	
							//String strP_TBPJ = sheet.getCell(34,i).getContents();
							//strP_TBPJ = strP_TBPJ.replaceAll(",", "");
							
							
							//JKK / JKM	
							String strP_JKK = sheet.getCell(posP_JKK,i).getContents();
							strP_JKK = strP_JKK.replaceAll(",", "");
							String strP_JKM = "0.00";
							
							//Jaminan Pensiun			
							String strD_JPS = sheet.getCell(posD_JPS,i).getContents();
							strD_JPS = strD_JPS.replaceAll(",", "");
							
							
							//Jaminan Hari Tua	
							String strD_JHT = sheet.getCell(posD_JHT,i).getContents();
							strD_JHT = strD_JHT.replaceAll(",", "");
							
							
							
//							if (i == 6){
//								System.out.println("strP_BPJK : "+strP_BPJK);
//								System.out.println("strP_TBPJ : "+strP_TBPJ);
//								System.out.println("strD_JPS : "+strD_JPS);
//								System.out.println("strD_JHT : "+strD_JHT);
//							}
							
															
							
							
							// TIDAK TERPAKAI
												
							String strD_PESA = "0.00"; 
							String strD_PJK = "0.00"; 
							String strD_RPGP = "0.00";						
							String strD_TLMB = "0.00";
														
							
							String strP_TBPJ = "0.00";							
							String strP_JPK = "0.00";							
							String strP_PKK = "0.00";							
							String strP_SPSI = "0.00";							
							String strP_UPDL = "0.00";
							//System.out.println("Isi Data : "+sheet.getCell(2,i).getContents() + " - "+strArea+ " - "+strUnitUsaha);
							
							Temp07SalaryHapis anData = new Temp07SalaryHapis(
									strArea,
									strJobGrade,
									strUnitUsaha,
									strPeriode,
									strD_AMED, strD_BNSA, strD_BNSN, strD_BSAL, strD_CPSG, strD_JHT,
									strD_JKK, strD_JKM, strD_JPK, strD_JPS, strD_KOMS,
									strD_LBR, strD_PESA, strD_PJK, strD_RPGP, strD_TD24,
									strD_TDNS, strD_TotalTHR, strD_TJHP, strD_TJKS, strD_TKHS,
									strD_TLMB, strD_TMED, strD_TOTX, strD_TONX,
									strP_ADVP, strP_ASXT, strP_BPJK,
									strP_JKK, strP_JKM, strP_JPK, strP_PADV, strP_PINJ,
									strP_PJK, strP_PKK, strP_POTL, strP_SPSI, strP_TBPJ,
									strP_UPDL,
									timeStamp);
							listSalaryHapis.add(anData);
							
						}
					
					}
				}
						


				workbook.close();
				
				if(CommonUtils.isNotEmpty(listSalaryHapis)){
					temp07SalaryHapisService.save(listSalaryHapis);					
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

		String vJenisRpt = "SJ";
		if (CommonUtils.isNotEmpty(rdgJnsReport.getSelectedItem().getValue())) {
			vJenisRpt = rdgJnsReport.getSelectedItem().getValue();	
		} 
		
		
		
		String jasperRptFinal = "/solusi/hapis/webui/reports/general/hrd/080201_SalaryJournal.jasper";
		if (vJenisRpt.equals("SJ") == true){
			
			@SuppressWarnings("unused")
			String vSync = callStoreProcOrFuncService.callSyncAReport("0803001");
			
			jasperRptFinal = "/solusi/hapis/webui/reports/general/hrd/080201_SalaryJournal.jasper";
		} else {
			jasperRptFinal = "/solusi/hapis/webui/reports/general/hrd/080202_LapPPH21perCabang.jasper";
		}
		
		param.put("ProsesId",  timeStamp);	

		new JReportGeneratorWindow(param, jasperRptFinal, "XLS"); 
		
		
		Map<Object, Object> parameterInput = new HashMap<Object, Object>();		
		
		
		parameterInput.put("prosesId", timeStamp);
		
		List<Temp07SalaryHapis> tempListTemp07SalaryHapis = temp07SalaryHapisService.getListTemp07SalaryHapis(parameterInput);
				
		if (CommonUtils.isNotEmpty(tempListTemp07SalaryHapis)) {
			
			temp07SalaryHapisService.delete(tempListTemp07SalaryHapis);
			
		}
        
		   
	}
}