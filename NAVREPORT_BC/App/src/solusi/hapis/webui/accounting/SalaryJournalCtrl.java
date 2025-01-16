package solusi.hapis.webui.accounting;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
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

import solusi.hapis.backend.navbi.model.Temp07SalaryHapis;
import solusi.hapis.backend.navbi.service.Temp07SalaryHapisService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class SalaryJournalCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	
	private String timeStamp;
	
	protected Textbox lbl1;
	
	protected Datebox dbPeriode;  
	
	protected Radiogroup rdgCompany;	 
	protected Radio rdSP;
	protected Radio rdAJ;
	
	
	List<Temp07SalaryHapis> listSalaryHapis = new ArrayList<Temp07SalaryHapis>();
	
	private Temp07SalaryHapisService temp07SalaryHapisService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		
		timeStamp = String.valueOf(System.currentTimeMillis());
    	
		dbPeriode.setValue((new Date()));   
    	
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
				
				int vJmlKolom = sheet.getColumns();


				if(CommonUtils.isNotEmpty(listSalaryHapis)){
					temp07SalaryHapisService.save(listSalaryHapis);		
					listSalaryHapis.clear();
				}

				int posArea = -1;
				int posUnitUsaha = -1;
				

				int posD_AMED = 0;
				int posD_BNSA = 0;
				int posD_BNSN = 0;
				int posD_BSAL = 0;
				int posD_CPSG = 0;
				int posD_JHT = 0;
				int posD_JKK = 0;
				int posD_JKM = 0;
				int posD_JPK = 0;
				int posD_JPS = 0;
				int posD_KOMS = 0;
				int posD_LBR = 0;
				int posD_PESA = 0;
				int posD_PJK = 0;
				int posD_RPGP = 0;
				int posD_TD24 = 0;
				int posD_TDNS = 0;
				int posD_THR = 0;
				int posD_ETHR = 0;
				int posD_TJHP = 0;
				int posD_TJKS = 0;
				int posD_TKHS = 0;
				int posD_TLMB = 0;
				int posD_TMED = 0;
				int posP_ADVP = 0;
				int posP_ASXT = 0;
				int posP_BPJK = 0;
				int posP_JKK = 0;
				int posP_JKM = 0;
				int posP_JPK = 0;
				int posP_PADV = 0;
				int posP_PINJ = 0;
				int posP_PJK = 0;
				int posP_PKK = 0;
				int posP_POTL = 0;
				int posP_SPSI = 0;
				int posP_TBPJ = 0;
				int posP_UPDL = 0;
				

				for (int i = 3; i < vJmlData; i++){
					if (i == 3){
						for (int j=0 ; j < vJmlKolom ; j++){
							//System.out.println("Kolom - "+(j+1) +" : "+sheet.getCell(j,i).getContents());
							// Posisi Kolom Area
							if (sheet.getCell(j,i).getContents().equals("Area")==true){
								posArea = j;
							}
							
							// Posisi Kolom Unit Usaha
							if (sheet.getCell(j,i).getContents().equals("Unit Usaha")==true){
								posUnitUsaha = j;
							}
						}
					}
					
					if (i == 4){
						for (int j=0 ; j < vJmlKolom ; j++){
							
							// Asuransi Kesehatan Medicare	(D-AMED)
							if (sheet.getCell(j,i).getContents().equals("(D-AMED)")==true){
								posD_AMED = j;
							}
							
							// Sales Bonus	(D-BNSA)
							if (sheet.getCell(j,i).getContents().equals("(D-BNSA)")==true){
								posD_BNSA = j;
							}
							
							// Bonus Non Sales	(D-BNSN)
							if (sheet.getCell(j,i).getContents().equals("(D-BNSN)")==true){
								posD_BNSN = j;
							}
							
							// GAJI POKOK	(D-BSAL)
							if (sheet.getCell(j,i).getContents().equals("(D-BSAL)")==true){
									posD_BSAL = j;		
							}
							
							// Cicilan Pesangon (D-CPSG)
							if (sheet.getCell(j,i).getContents().equals("(D-CPSG)")==true){
								posD_CPSG = j;		
							}
						
														
							// IURAN J.H.T	(D-JHT)
							if (sheet.getCell(j,i).getContents().equals("(D-JHT)")==true){
								posD_JHT = j;
							}
							
							// IURAN J.K.K	(D-JKK)
							if (sheet.getCell(j,i).getContents().equals("(D-JKK)")==true){
								posD_JKK = j;
							}
							
							// IURAN J.K.M	(D-JKM)
							if (sheet.getCell(j,i).getContents().equals("(D-JKM)")==true){
								posD_JKM= j;
							}
							
							
							// IURAN BPJS Kesehatan	(D-JPK)
							if (sheet.getCell(j,i).getContents().equals("(D-JPK)")==true){
								posD_JPK= j;
							}
														
							// IURAN JAMINAN PENSIUN	(D-JPS)
							if (sheet.getCell(j,i).getContents().equals("(D-JPS)")==true){
								posD_JPS= j;
							}
														
							// Komisi Sales	(D-KOMS)
							if (sheet.getCell(j,i).getContents().equals("(D-KOMS)")==true){
								posD_KOMS = j;
							}
							
							// Tunjangan lembur	(D-LBR)
							if (sheet.getCell(j,i).getContents().equals("(D-LBR)")==true){
								posD_LBR = j;
							}
							
							// PESANGON/TEBUSAN PENSIUN/THT	(D-PESA)
							if (sheet.getCell(j,i).getContents().equals("(D-PESA)")==true){
								posD_PESA = j;
							}
							
							// TUNJANGAN PAJAK	(D-PJK)
							if (sheet.getCell(j,i).getContents().equals("(D-PJK)")==true){
								posD_PJK = j;
							}
							
							// Rapel Gaji POKOK	(D-RPGP)
							if (sheet.getCell(j,i).getContents().equals("(D-RPGP)")==true){
								posD_RPGP = j;
							}
							
							// Tunjangan Dinas standby 24 jam	(D-TD24)
							if (sheet.getCell(j,i).getContents().equals("(D-TD24)")==true){
								posD_TD24 = j;
							}
							
							// Tunjangan Dinas 	(D-TDNS)
							if (sheet.getCell(j,i).getContents().equals("(D-TDNS)")==true){
								posD_TDNS = j;
							}
							
							// THR	(D-THR)
							if (sheet.getCell(j,i).getContents().equals("(D-THR)")==true){
								posD_THR = j;
							}
							
							// ETHR	(D-ETHR)
							if (sheet.getCell(j,i).getContents().equals("(D-ETHR)")==true){
								posD_ETHR = j;
							}
							
							//Tunjangan Hand Phone	(D-TJHP)
							if (sheet.getCell(j,i).getContents().equals("(D-TJHP)")==true){
								posD_TJHP = j;
							}
							
							// Tunjangan khusus	(D-TJKS)
							if (sheet.getCell(j,i).getContents().equals("(D-TJKS)")==true){
								posD_TJKS = j;
							}
							
							// Tunjangan Suka/Duka	(D-TKHS)
							if (sheet.getCell(j,i).getContents().equals("(D-TKHS)")==true){
								posD_TKHS = j;
							}
							
							// Tunjangan Lembur	(D-TLMB)
							if (sheet.getCell(j,i).getContents().equals("(D-TLMB)")==true){
								posD_TLMB = j;
							}
							
							// Tujangan Medical	(D-TMED)
							if (sheet.getCell(j,i).getContents().equals("(D-TMED)")==true){
								posD_TMED = j;
							}
							
							// ADVANCE PAYROLL	(P-ADVP)
							if (sheet.getCell(j,i).getContents().equals("(P-ADVP)")==true){
								posP_ADVP = j;
							}
							
							// Asuransi Extra	(P-ASXT)
							if (sheet.getCell(j,i).getContents().equals("(P-ASXT)")==true){
								posP_ASXT = j;
							}
							
							// BPJS Kesehatan	(P-BPJK)
							if (sheet.getCell(j,i).getContents().equals("(P-BPJK)")==true){
								posP_BPJK = j;
							}
							
							// POTONGAN  J.K.K	(P-JKK)
							if (sheet.getCell(j,i).getContents().equals("(P-JKK)")==true){
								posP_JKK = j;
							}
							
							// POTONGAN J.K.M	(P-JKM)
							if (sheet.getCell(j,i).getContents().equals("(P-JKM)")==true){
								posP_JKM = j;
							}
							
							// POTONGAN  BPJS Kesehatan	(P-JPK)
							if (sheet.getCell(j,i).getContents().equals("(P-JPK)")==true){
								posP_JPK = j;
							}
							
							// PAYROLL ADVANCE	(P-PADV)
							if (sheet.getCell(j,i).getContents().equals("(P-PADV)")==true){
								posP_PADV = j;
							}
							
							// Piutang Karyawan	(P-PINJ)
							if (sheet.getCell(j,i).getContents().equals("(P-PINJ)")==true){
								posP_PINJ = j;
							}
							
							// PAJAK	(P-PJK)
							if (sheet.getCell(j,i).getContents().equals("(P-PJK)")==true){
								posP_PJK = j;
							}
							
							// P. Kesejateraan Kryw	(P-PKK)
							if (sheet.getCell(j,i).getContents().equals("(P-PKK)")==true){
								posP_PKK = j;
							}
							
							// Potongan Lain-lain	(P-POTL)
							if (sheet.getCell(j,i).getContents().equals("(P-POTL)")==true){
								posP_POTL= j;
							}
							
							// IURAN SPSI	(P-SPSI)
							if (sheet.getCell(j,i).getContents().equals("(P-SPSI)")==true){
								posP_SPSI = j;
							}
							
							// Premi Tanggungan BPJS	(P-TBPJ)
							if (sheet.getCell(j,i).getContents().equals("(P-TBPJ)")==true){
								posP_TBPJ = j;
							}
							
							// UNPAID LEAVE (cuti khusus)	(P-UPDL)	
							if (sheet.getCell(j,i).getContents().equals("(P-UPDL)")==true){
								posP_UPDL = j;
							}
							
							
							//System.out.println(j+" --> "+sheet.getCell(j,i).getContents()); 
						}
					}
					
					if (i > 4){
						
						String strArea = "X";
						if (posArea > -1){
							strArea = sheet.getCell(posArea,i).getContents();
						}
												
						String strUnitUsaha = "Y";
						if (posUnitUsaha > 0){
							strUnitUsaha = sheet.getCell(posUnitUsaha,i).getContents();
						}
						
						String strD_AMED = "0";
						if (posD_AMED > 0){
							strD_AMED = sheet.getCell(posD_AMED,i).getContents();
						}
												
						String strD_BNSA = "0";
						if (posD_BNSA > 0){
							strD_BNSA = sheet.getCell(posD_BNSA,i).getContents();
						}
						
						String strD_BNSN = "0";
						if (posD_BNSN > 0){
							strD_BNSN = sheet.getCell(posD_BNSN,i).getContents();
						}
						
						String strD_BSAL = "0";
						if (posD_BSAL > 0){
							strD_BSAL = sheet.getCell(posD_BSAL,i).getContents();
						}
						
						String strD_CPSG = "0";
						if (posD_CPSG > 0){
							strD_CPSG = sheet.getCell(posD_CPSG,i).getContents();
						}
						
						String strD_JHT = "0";
						if (posD_JHT > 0){
							strD_JHT = sheet.getCell(posD_JHT,i).getContents();
						}
						
						String strD_JKK = "0";
						if (posD_JKK > 0){
							strD_JKK = sheet.getCell(posD_JKK,i).getContents();
						}
						
						String strD_JKM = "0";
						if (posD_JKM > 0){
							strD_JKM = sheet.getCell(posD_JKM,i).getContents();
						}
						
						String strD_JPK = "0";
						if (posD_JPK > 0){
							strD_JPK = sheet.getCell(posD_JPK,i).getContents();
						}
						
						String strD_JPS = "0";
						if (posD_JPS > 0){
							strD_JPS = sheet.getCell(posD_JPS,i).getContents();
						}
						
						String strD_KOMS = "0";
						if (posD_KOMS > 0){
							strD_KOMS = sheet.getCell(posD_KOMS,i).getContents();
						}
						
						String strD_LBR = "0";
						if (posD_LBR > 0){
							strD_LBR = sheet.getCell(posD_LBR,i).getContents();
						}
						
						String strD_PESA = "0";
						if (posD_PESA > 0){
							strD_PESA = sheet.getCell(posD_PESA,i).getContents();
						}
						
						String strD_PJK = "0";
						if (posD_PJK > 0){
							strD_PJK = sheet.getCell(posD_PJK,i).getContents();
						}
						
						String strD_RPGP = "0";
						if (posD_RPGP > 0){
							strD_RPGP = sheet.getCell(posD_RPGP,i).getContents();
						}
						
						String strD_TD24 = "0";
						if (posD_TD24 > 0){
							strD_TD24 = sheet.getCell(posD_TD24,i).getContents();
						}
						
						String strD_TDNS = "0";
						if (posD_TDNS > 0){
							strD_TDNS = sheet.getCell(posD_TDNS,i).getContents();
						}
						
						String strD_THR = "0";
						if (posD_THR > 0){
							strD_THR = sheet.getCell(posD_THR,i).getContents();
						}
						
						String strD_ETHR = "0";
						if (posD_ETHR > 0){
							strD_ETHR = sheet.getCell(posD_ETHR,i).getContents();
						}
						
						String strD_TotalTHR = "0";
						long vTHR = new Long(strD_THR.replaceAll(",", "").replaceAll("-", ""));
						long vETHR = new Long(strD_ETHR.replaceAll(",", "").replaceAll("-", ""));
						long vTotalTHR = vTHR+vETHR;
						
						strD_TotalTHR = String.valueOf(vTotalTHR);
						
						
						String strD_TJHP = "0";
						if (posD_TJHP > 0){
							strD_TJHP = sheet.getCell(posD_TJHP,i).getContents();
						}
						
						String strD_TJKS = "0";
						if (posD_TJKS > 0){
							strD_TJKS = sheet.getCell(posD_TJKS,i).getContents();
						}
						
						String strD_TKHS = "0";
						if (posD_TKHS > 0){
							strD_TKHS = sheet.getCell(posD_TKHS,i).getContents();
						}
						
						String strD_TLMB = "0";
						if (posD_TLMB > 0){
							strD_TLMB = sheet.getCell(posD_TLMB,i).getContents();
						}
						
						String strD_TMED = "0";
						if (posD_TMED > 0){
							strD_TMED = sheet.getCell(posD_TMED,i).getContents();
						}
						
						String strP_ADVP = "0";
						if (posP_ADVP > 0){
							strP_ADVP = sheet.getCell(posP_ADVP,i).getContents();
						}
						
						String strP_ASXT = "0";
						if (posP_ASXT > 0){
							strP_ASXT = sheet.getCell(posP_ASXT,i).getContents();
						}
						
						String strP_BPJK = "0";
						if (posP_BPJK > 0){
							strP_BPJK = sheet.getCell(posP_BPJK,i).getContents();
						}
						
						String strP_JKK = "0";
						if (posP_JKK > 0){
							strP_JKK = sheet.getCell(posP_JKK,i).getContents();
						}
						
						String strP_JKM = "0";
						if (posP_JKM > 0){
							strP_JKM = sheet.getCell(posP_JKM,i).getContents();
						}
						
						String strP_JPK = "0";
						if (posP_JPK > 0){
							strP_JPK = sheet.getCell(posP_JPK,i).getContents();
						}
						
						String strP_PADV = "0";
						if (posP_PADV > 0){
							strP_PADV = sheet.getCell(posP_PADV,i).getContents();
						}
						
						String strP_PINJ = "0";
						if (posP_PINJ > 0){
							strP_PINJ = sheet.getCell(posP_PINJ,i).getContents();
						}
						
						String strP_PJK = "0";
						if (posP_PJK > 0){
							strP_PJK = sheet.getCell(posP_PJK,i).getContents();
						}
						
						String strP_PKK = "0";
						if (posP_PKK > 0){
							strP_PKK = sheet.getCell(posP_PKK,i).getContents();
						}
						
						String strP_POTL = "0";
						if (posP_POTL > 0){
							strP_POTL = sheet.getCell(posP_POTL,i).getContents();
						}
						
						String strP_SPSI = "0";
						if (posP_SPSI > 0){
							strP_SPSI = sheet.getCell(posP_SPSI,i).getContents();
						}
						
						String strP_TBPJ = "0";
						if (posP_TBPJ > 0){
							strP_TBPJ = sheet.getCell(posP_TBPJ,i).getContents();
						}
						
						String strP_UPDL = "0";
						if (posP_UPDL > 0){
							strP_UPDL = sheet.getCell(posP_UPDL,i).getContents();
						}
						
						
						
//						if(CommonUtils.isNotEmpty(sheet.getCell(0,i).getContents())){
//							Temp07SalaryHapis anData = new Temp07SalaryHapis(
//									strArea,
//									strUnitUsaha,
//									"000000",
//									strD_AMED, strD_BNSA, strD_BNSN, strD_BSAL, strD_CPSG, strD_JHT,
//									strD_JKK, strD_JKM, strD_JPK, strD_JPS, strD_KOMS,
//									strD_LBR, strD_PESA, strD_PJK, strD_RPGP, strD_TD24,
//									strD_TDNS, strD_TotalTHR, strD_TJHP, strD_TJKS, strD_TKHS,
//									strD_TLMB, strD_TMED, "0", "0", strP_ADVP, strP_ASXT, strP_BPJK,
//									strP_JKK, strP_JKM, strP_JPK, strP_PADV, strP_PINJ,
//									strP_PJK, strP_PKK, strP_POTL, strP_SPSI, strP_TBPJ,
//									strP_UPDL,
//									timeStamp);
//							listSalaryHapis.add(anData);
//						}

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
		Date vPeriode = new Date();   
		if(CommonUtils.isNotEmpty(dbPeriode.getValue()) == true){  
			vPeriode = dbPeriode.getValue();
		}   
		
		String vCompany = "AUTOJAYA";
		if (CommonUtils.isNotEmpty(rdgCompany.getSelectedItem().getValue())) {
			vCompany = rdgCompany.getSelectedItem().getValue();	
		} 
		
		
		String jasperRptFinal = "/solusi/hapis/webui/reports/accounting/01067_SalaryJournal.jasper";
		
		param.put("Periode",  vPeriode);	
		param.put("Company",  vCompany);	
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