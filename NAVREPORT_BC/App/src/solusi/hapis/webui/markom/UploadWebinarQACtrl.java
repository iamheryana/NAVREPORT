package solusi.hapis.webui.markom;



import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
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

import solusi.hapis.backend.navbi.model.Temp12WebinarQa;
import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.navbi.service.Temp12WebinarQaService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class UploadWebinarQACtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowUploadWebinarPolling;


	
	protected Textbox lbl1;	
	protected Textbox txtWebinarID;	
	
		
	private Temp12WebinarQaService temp12WebinarQaService;
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
				
				List<Temp12WebinarQa> listData = new ArrayList<Temp12WebinarQa>();		
				String vWebinarID = "";
				
				for (int vRow = 0; vRow < vJmlData; vRow++){
					


					if( vRow == 3) {
					
						if(CommonUtils.isNotEmpty(sheet.getCell(1,vRow).getContents())){
							vWebinarID = sheet.getCell(1,vRow).getContents();
							//System.out.println("vWebinarID : "+vWebinarID);
						}
					
					}
					
					
					if (vRow >= 6){
						String vNoQst  = "";
					    if(CommonUtils.isNotEmpty(sheet.getCell(0,vRow).getContents())){
					    	vNoQst = sheet.getCell(0,vRow).getContents();
					    }
					    
					    String vQuestion = "";
					    if(CommonUtils.isNotEmpty(sheet.getCell(1,vRow).getContents())){
					    	vQuestion = sheet.getCell(1,vRow).getContents();
					    }
					    
						String vAskerName  = "";
					    if(CommonUtils.isNotEmpty(sheet.getCell(2,vRow).getContents())){
					    	vAskerName = sheet.getCell(2,vRow).getContents();
					    }
						
					    String vAskerEmail  = "";
					    if(CommonUtils.isNotEmpty(sheet.getCell(3,vRow).getContents())){
					    	vAskerEmail = sheet.getCell(3,vRow).getContents();
					    }
					    
					   
					    String[] vAnswer = new String[10];
					    vAnswer[0] = ""; 
					    String vTempAnswer = "";
					    if(CommonUtils.isNotEmpty(sheet.getCell(4, vRow).getContents())){					    	
					    	vTempAnswer = sheet.getCell(4, vRow).getContents();		
					    }
					    
					    int vJmlKolomMax = 0;
					    if (vTempAnswer.length() > 750){
					    	int vPecahKolom = (int) Math.ceil(vTempAnswer.length() / 750.0);
					    	
					    	//System.out.println("vTempAnswer : "+vTempAnswer.length());
					    	//System.out.println("xxx : "+vTempAnswer.length() / 750);
					    	//System.out.println("vPecahKolom : "+vPecahKolom);
					    	vJmlKolomMax = vPecahKolom;
					    	if (vPecahKolom + ((vJmlColumn-1) - (4)) > 10){
					    		vJmlKolomMax = vPecahKolom - ((vJmlColumn-1) - (4));
					    	}
					    } else {
					    	vJmlKolomMax = 0;
					    }
					    
					    if (vJmlKolomMax == 0) {
					    	vAnswer[0] = vTempAnswer;
					    } else {
					    
						    for (int vClm = 0 ; vClm < vJmlKolomMax ; vClm ++){
						    	int vMinChar = (vClm * 750);
						    	if (vMinChar > vTempAnswer.length()){
						    		vMinChar = vTempAnswer.length();
						    	}
						    	
						    	if (vClm > 0){
						    		vMinChar = vMinChar - 1 ;
						    	}
						    	
						    	int vMaxChar = ((vClm + 1) * 750) - 1;
						    	if (vMaxChar > vTempAnswer.length()){
						    		vMaxChar = vTempAnswer.length();
						    	}
						    	vAnswer[vClm] = vTempAnswer.substring(vMinChar, vMaxChar);
						    }
					    }
					    
					    if (vJmlKolomMax == 0) {
						    for (int vCol = vJmlKolomMax ; vCol < vJmlKolomMax+(vJmlColumn - 5) ; vCol ++){
						    	//System.out.println(" Iter : "+(5 + (vCol-vJmlKolomMax)));
						    	if(CommonUtils.isNotEmpty(sheet.getCell((5 + (vCol-vJmlKolomMax)), vRow).getContents())){
						    		vAnswer[vCol+1] = sheet.getCell((5 + (vCol-vJmlKolomMax)), vRow).getContents();					    			
								}
						    	
						    }
					    } else {

						    //System.out.println("vJmlKolomMax : "+vJmlKolomMax);
						    for (int vCol = vJmlKolomMax ; vCol < vJmlKolomMax+(vJmlColumn - 5) ; vCol ++){
						    	//System.out.println(" Iter : "+(5 + (vCol-vJmlKolomMax)));
						    	if(CommonUtils.isNotEmpty(sheet.getCell((5 + (vCol-vJmlKolomMax)), vRow).getContents())){
						    		vAnswer[vCol] = sheet.getCell((5 + (vCol-vJmlKolomMax)), vRow).getContents();					    			
								}
						    	
						    }
					    }

					    
//					    for(int i =0; i< 10 ;i++ ){
//					    	System.out.println("vAnswer - "+ i+ " : "+vAnswer[i]);
//					    }
					    
					    Temp12WebinarQa anData = 
								new Temp12WebinarQa(vWebinarID, vNoQst, vQuestion, vAskerName, vAskerEmail,
										vAnswer[0], vAnswer[1], vAnswer[2], vAnswer[3], vAnswer[4],
										vAnswer[5], vAnswer[6], vAnswer[7], vAnswer[8], vAnswer[9], vProsesId);
					    
					    listData.add(anData);

					    
					    
					}				   
				}
				
				workbook.close();
				
					if(CommonUtils.isNotEmpty(listData)){
						temp12WebinarQaService.save(listData);					
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
			String vResult = callStoreProcOrFuncService.callUploadWebinarQA(vProsesId, SecurityContextHolder.getContext().getAuthentication().getName());
			Messagebox.show(vResult, "Message",
					Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
 
}