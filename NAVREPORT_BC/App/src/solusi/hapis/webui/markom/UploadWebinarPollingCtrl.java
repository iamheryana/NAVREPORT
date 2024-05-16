package solusi.hapis.webui.markom;


import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.Temp04WebinarPolling;
import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.navbi.service.Temp04WebinarPollingService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class UploadWebinarPollingCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowUploadWebinarPolling;


	
	protected Textbox lbl1;	
	protected Textbox txtWebinarID;	
	
		
	private Temp04WebinarPollingService temp04WebinarPollingService;
	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	

	private String vProsesId;
	
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		    	
    	vProsesId = String.valueOf(System.currentTimeMillis());
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
				
				
				List<Temp04WebinarPolling> listData = new ArrayList<Temp04WebinarPolling>();
				//String vPreviousQst = "";
				//int vSesi = 0;				
				String vWebinarID = "";
				String[] vPertanyaan = new String[15];
				
				boolean vControlOK = false;
				
				for (int vRow = 0; vRow < vJmlData; vRow++){
					


					if( vRow == 3) {
					
						if(CommonUtils.isNotEmpty(sheet.getCell(1,vRow).getContents())){
							vWebinarID = sheet.getCell(1,vRow).getContents();
							//System.out.println("vWebinarID : "+vWebinarID);
						}
					
					}
					
					
					
					if (vRow >= 5){
						
						
						String vTandaPagar = "#";
						if(CommonUtils.isNotEmpty(sheet.getCell(0,vRow).getContents())){
							vTandaPagar = sheet.getCell(0,vRow).getContents();
						}
						
						
						if (vTandaPagar.equals("#") == true){
							vControlOK = true;
							//System.out.println("Masuk Sini...");
							for (int vCol = 4 ; vCol < vJmlColumn ; vCol ++){
								if(CommonUtils.isNotEmpty(sheet.getCell(vCol, vRow).getContents())){
									vPertanyaan[vCol-4] = sheet.getCell(vCol, vRow).getContents();	
									
									//System.out.println("Pertanyaan : -->"+ vPertanyaan[vCol-4]);
								}
							}
						}
						
						if (vControlOK == true){
							if (vTandaPagar.equals("#") == false){
								String vSesi = "0";
								if(CommonUtils.isNotEmpty(sheet.getCell(0,vRow).getContents())){
									vSesi = sheet.getCell(0,vRow).getContents();
							    }
								
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
							    	SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yy HH:mm");
							    	vWaktuIsi = ft.parse(sheet.getCell(3,vRow).getContents());			    
							    }
							    
							    String[] vIsian = new String[15];
							    vIsian[0] = ""; 
							    for (int vCol = 4 ; vCol < vJmlColumn ; vCol ++){
							    	
							    	if(CommonUtils.isNotEmpty(sheet.getCell(vCol, vRow).getContents())){
							    		vIsian[vCol-4] = sheet.getCell(vCol, vRow).getContents();					    			
									}
							    }
							    
							    //if (vIsian[0].equals(vPreviousQst) == false) {
							    //	vSesi = vSesi + 1;
							    //	vPreviousQst = vIsian[0];
							    //}
							    
							    Temp04WebinarPolling anData = 
										new Temp04WebinarPolling(
												vWebinarID, vSesi, vNamaLengkap, vEmail, vWaktuIsi,
												vPertanyaan[0], vIsian[0], vPertanyaan[1], vIsian[1], vPertanyaan[2], vIsian[2], vPertanyaan[3], vIsian[3], vPertanyaan[4], vIsian[4],
												vPertanyaan[5], vIsian[5], vPertanyaan[6], vIsian[6], vPertanyaan[7], vIsian[7], vPertanyaan[8], vIsian[8], vPertanyaan[9], vIsian[9],
												vPertanyaan[10], vIsian[10], vPertanyaan[11], vIsian[11], vPertanyaan[12], vIsian[12], vPertanyaan[13], vIsian[13], vPertanyaan[14], vIsian[14],
												vProsesId);
							    
							    listData.add(anData);
							}
						   
	//					    System.out.println("vWebinarID : "+vWebinarID);
	//					    System.out.println("vNamaLengkap : "+vNamaLengkap);
	//					    System.out.println("vEmail : "+vEmail);
	//					    System.out.println("vWaktuIsi : "+vWaktuIsi);
	//					    System.out.println("vSesi : "+vSesi);
	//					   
	//					    for(int i = 0 ; i < 30; i++){
	//							System.out.println("vIsian - "+(i+1)+" : "+ vIsian[i]);
	//						}
						    
						    
						}				  
					}
				}
				
				workbook.close();
				
					if(CommonUtils.isNotEmpty(listData)){
						temp04WebinarPollingService.save(listData);					
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
			String vResult = callStoreProcOrFuncService.callUploadWebinarPolling(vProsesId, SecurityContextHolder.getContext().getAuthentication().getName());
			Messagebox.show(vResult, "Message",
					Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
 
}