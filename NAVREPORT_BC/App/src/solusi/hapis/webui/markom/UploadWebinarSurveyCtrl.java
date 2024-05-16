package solusi.hapis.webui.markom;


import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.model.Temp03WebinarFeedback;
import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.navbi.service.Temp03WebinarFeedbackService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class UploadWebinarSurveyCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	


	
	protected Textbox lbl1;	
	
		
	private Temp03WebinarFeedbackService temp03WebinarFeedbackService;
	
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	

	private String vProsesId;
	private String vWebinarID;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		    	
    	vProsesId = String.valueOf(System.currentTimeMillis());
    	vWebinarID = "";
	}
	
	
	public void onClick$btnOK1(Event event) throws InterruptedException, ParseException {
		Media media = Fileupload.get("Please select a File", "Upload");
		
		
		if (CommonUtils.isNotEmpty(media)) {

			try {
				// Membaca Excel dari file yang di Upload
				Workbook workbook = Workbook.getWorkbook(media.getStreamData());
				Sheet sheet = workbook.getSheet(0);

				int vJmlData = sheet.getRows();
				int vJmlColumn = sheet.getColumns();
				// 4 karena pertanyaan di mulai di kolom ke 5 (colom E)
				int vMaxQst = vJmlColumn - 4;
				List<Temp03WebinarFeedback> listData = new ArrayList<Temp03WebinarFeedback>();
				
				for (int vRow = 0; vRow < vJmlData; vRow++){
					//Ambil Webinar ID
					if( vRow == 3) {
						if(CommonUtils.isNotEmpty(sheet.getCell(1, vRow).getContents())){
							vWebinarID = sheet.getCell(1, vRow).getContents();
						}
					}
					
					
					// Baris ke-5 adalah mengambil Judul dan Pertanyaan
					if( vRow == 5) {
						
						String[] vQst= new String[50];
						for(int vCol = 0 ; vCol < vMaxQst; vCol++){
							if(CommonUtils.isNotEmpty(sheet.getCell((vCol + 4),vRow).getContents())){
								vQst[vCol] = sheet.getCell((vCol + 4),vRow).getContents();
							}
						}
						
						
						Temp03WebinarFeedback anData = 
								new Temp03WebinarFeedback(
										"1", null, null, null, null, null, null, null, null, null, null, null, null, null, vMaxQst,
										vQst[0], vQst[1], vQst[2], vQst[3], vQst[4], vQst[5], vQst[6], vQst[7], vQst[8], vQst[9],
										vQst[10], vQst[11], vQst[12], vQst[13], vQst[14], vQst[15], vQst[16], vQst[17], vQst[18], vQst[19],
										vQst[20], vQst[21], vQst[22], vQst[23], vQst[24], vQst[25], vQst[26], vQst[27], vQst[28], vQst[29],
										vQst[30], vQst[31], vQst[32], vQst[33], vQst[34], vQst[35], vQst[36], vQst[37], vQst[38], vQst[39],
										vQst[40], vQst[41], vQst[42], vQst[43], vQst[44], vQst[45], vQst[46], vQst[47], vQst[48], vQst[49],
										vProsesId);
						
						
						listData.add(anData);
//						System.out.println("vWebinarID : "+ vWebinarID);
//						System.out.println("vMaxQst : "+ (vJmlColumn - 4));
//						for(int i = 0 ; i < 50; i++){
//							System.out.println("vQst - "+(i+1)+" : "+ vQst[i]);
//						}
					
						
					}
					
					if(vRow > 5){
						
						String vNamaLengkap  = "";
						if(CommonUtils.isNotEmpty(sheet.getCell(1,vRow).getContents())){
							vNamaLengkap = sheet.getCell(1,vRow).getContents();
					    }
						
						
					    String vEmail  = "";
					    if(CommonUtils.isNotEmpty(sheet.getCell(2,vRow).getContents())){
					    	vEmail = sheet.getCell(2,vRow).getContents();
					    }
						    
						Date vWaktuIsi = null;
					    if(CommonUtils.isNotEmpty(sheet.getCell(3,vRow).getContents())){
					    	DateCell dc = (DateCell) sheet.getCell(3,vRow); 
					    	Date dateData = dc.getDate(); 
					        
					    	TimeZone tz = TimeZone.getDefault();
					        Date dateDataOri = new Date( dateData.getTime() - tz.getRawOffset() );
					        
					    	vWaktuIsi = dateDataOri;
					    }
						
					   
					    
					    String[] vAns= new String[50];
						for(int vCol = 0 ; vCol < vMaxQst; vCol++){
							if(CommonUtils.isNotEmpty(sheet.getCell((vCol + 4),vRow).getContents())){
								vAns[vCol] = sheet.getCell((vCol + 4),vRow).getContents();
							}
						}
					    
						
						Temp03WebinarFeedback anData = 
								new Temp03WebinarFeedback(
										"2", vWaktuIsi, "", "", vNamaLengkap, 
										vEmail, "",	"",
										"", "", "",
										"", "", "",  vMaxQst,
										vAns[0], vAns[1], vAns[2], vAns[3], vAns[4], vAns[5], vAns[6], vAns[7], vAns[8], vAns[9],
										vAns[10], vAns[11], vAns[12], vAns[13], vAns[14], vAns[15], vAns[16], vAns[17], vAns[18], vAns[19],
										vAns[20], vAns[21], vAns[22], vAns[23], vAns[24], vAns[25], vAns[26], vAns[27], vAns[28], vAns[29],
										vAns[30], vAns[31], vAns[32], vAns[33], vAns[34], vAns[35], vAns[36], vAns[37], vAns[38], vAns[39],
										vAns[40], vAns[41], vAns[42], vAns[43], vAns[44], vAns[45], vAns[46], vAns[47], vAns[48], vAns[49],
										vProsesId);
						
						
						listData.add(anData);
						
//					    System.out.println("vWaktuIsi : "+vWaktuIsi);
//					    System.out.println("vNamaLengkap : "+vNamaLengkap);
//					    System.out.println("vEmail : "+vEmail);
//					    
//					    for(int i = 0 ; i < 50; i++){
//							System.out.println("vAns - "+(i+1)+" : "+ vAns[i]);
//						}
					    
					}

					
						
					
				}
				workbook.close();
				
				if(CommonUtils.isNotEmpty(listData)){
					temp03WebinarFeedbackService.save(listData);					
				}

				
				lbl1.setValue(media.getName()+ " Sudah berhasil terupload. Silahkan Click OK untuk proses selanjutnya.");


			} catch (BiffException e) {
				Messagebox.show("Not an Excel File : " + media.getName(),
						"Error", Messagebox.OK, Messagebox.ERROR);
			} catch (IOException e) {
				Messagebox.show("Error : " + e.getMessage(), "Error",
						Messagebox.OK, Messagebox.ERROR);

			}
			

		}
	
	}

	
	public void onClick$btnOK(Event event) {

		
		try {
			String vResult = callStoreProcOrFuncService.callUploadWebinarSurvey(vProsesId, vWebinarID, SecurityContextHolder.getContext().getAuthentication().getName());
			Messagebox.show(vResult, "Message",
					Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
 
}