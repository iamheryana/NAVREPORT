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

import solusi.hapis.backend.navbi.model.Temp01WebinarEvent;
import solusi.hapis.backend.navbi.model.Temp02WebinarAttendee;
import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.backend.navbi.service.Temp01WebinarEventService;
import solusi.hapis.backend.navbi.service.Temp02WebinarAttendeeService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;


public class UploadWebinarAttendeeCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Window windowUploadWebinarAttendee;


	
	protected Textbox lbl1;	
		
	private Temp01WebinarEventService temp01WebinarEventService;
	private Temp02WebinarAttendeeService temp02WebinarAttendeeService;
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

				List<Temp01WebinarEvent> listData = new ArrayList<Temp01WebinarEvent>();
				List<Temp02WebinarAttendee> listDataAttendee = new ArrayList<Temp02WebinarAttendee>();
				
				int vStartCollectAttendeeDetails = 0;
				int vStopCollectAttendeeDetails = 0;
				

				
				for (int i = 3; i < vJmlData; i++){
					if (i == 3){
						String vTopic  = "";
					    if(CommonUtils.isNotEmpty(sheet.getCell(0,i).getContents())){
					    	vTopic = sheet.getCell(0,i).getContents();
					    }

					    String vWebinarID = "";
					    if(CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents())){
					    	vWebinarID = sheet.getCell(1,i).getContents();
					    }
					    
					    Date vActualStartTime = null;
					    if(CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents())){
					    	
					    	if (sheet.getCell(2,i).getContents().length() < 17){
					    		SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yy HH:mm");
					    		vActualStartTime = ft.parse(sheet.getCell(2,i).getContents());
					    	} else {
					    		SimpleDateFormat ft = new SimpleDateFormat ("MMM dd, yyyy HH:mm");
					    		vActualStartTime = ft.parse(sheet.getCell(2,i).getContents());
					    	}
						    
					    }
					    					    
					    int vActualDuration = 0;
					    if(CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents())){
					    	vActualDuration = Integer.parseInt(sheet.getCell(3,i).getContents());
					    }
					    
					    int vNumRegistered = 0;
					    if(CommonUtils.isNotEmpty(sheet.getCell(4,i).getContents())){
					    	vNumRegistered = Integer.parseInt(sheet.getCell(4,i).getContents());
					    }
					    
					    int vNumCancelled = 0;
					    if(CommonUtils.isNotEmpty(sheet.getCell(5,i).getContents())){
					    	vNumCancelled = Integer.parseInt(sheet.getCell(5,i).getContents());
					    }
					    
					    int vUniqueViewers = 0;
					    if(CommonUtils.isNotEmpty(sheet.getCell(5,i).getContents())){
					    	vUniqueViewers = Integer.parseInt(sheet.getCell(5,i).getContents());
					    }
					    
					    int vTotalUsers = 0;	
					    if(CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents())){
					    	vTotalUsers = Integer.parseInt(sheet.getCell(6,i).getContents());
					    }

					    int vMaxConcurrentView = 0;	
					    if(CommonUtils.isNotEmpty(sheet.getCell(7,i).getContents())){
					    	vMaxConcurrentView = Integer.parseInt(sheet.getCell(7,i).getContents());
					    }
						Temp01WebinarEvent anData = 
								new Temp01WebinarEvent( vTopic, vWebinarID, vActualStartTime,
														vActualDuration, vNumRegistered, vNumCancelled,
														vUniqueViewers, vTotalUsers, vMaxConcurrentView,
														vProsesId);
						listData.add(anData);
					    
//					    System.out.println("vTopic : "+vTopic);
//					    System.out.println("vWebinarID : "+vWebinarID);
//					    System.out.println("vActualStartTime : "+vActualStartTime);
//					    System.out.println("vActualDuration : "+vActualDuration);
//					    System.out.println("vNumRegistered : "+vNumRegistered);
//					    System.out.println("vNumCancelled : "+vNumCancelled);
//					    System.out.println("vUniqueViewers : "+vUniqueViewers);
//					    System.out.println("vTotalUsers : "+vTotalUsers);
//					    System.out.println("vMaxConcurrentView : "+vMaxConcurrentView);
					}
					
					if(i > 3){
						
						if(CommonUtils.isNotEmpty(sheet.getCell(0,i).getContents())){
							if ((sheet.getCell(0,i).getContents()).equals("Attendee Details") == true){
								vStartCollectAttendeeDetails = i + 2;
							}
					    }
						//System.out.println("vStartCollectAttendeeDetails : "+vStartCollectAttendeeDetails);
						if(		vStartCollectAttendeeDetails !=0 && 
								vStopCollectAttendeeDetails == 0 &&
								i >= vStartCollectAttendeeDetails){
							String vFirstColumn ="";
							if(CommonUtils.isNotEmpty(sheet.getCell(0,i).getContents())){
								vFirstColumn =sheet.getCell(0,i).getContents();
							}
							
							if(vFirstColumn.equals("Yes") == true || vFirstColumn.equals("No") == true){
								String vAttended = "";
								if(CommonUtils.isNotEmpty(sheet.getCell(0,i).getContents())){
									vAttended = sheet.getCell(0,i).getContents();
							    }								
								
								
								String vUserName = "";
								if(CommonUtils.isNotEmpty(sheet.getCell(1,i).getContents())){
									vUserName = sheet.getCell(1,i).getContents();
							    }	
																
								String vFirstName = "";
								if(CommonUtils.isNotEmpty(sheet.getCell(2,i).getContents())){
									vFirstName = sheet.getCell(2,i).getContents();
							    }		
								
								String vLastName = "";
								if(CommonUtils.isNotEmpty(sheet.getCell(3,i).getContents())){
									vLastName = sheet.getCell(3,i).getContents();
							    }	
																
								String vEmail = "";
								if(CommonUtils.isNotEmpty(sheet.getCell(4,i).getContents())){
									vEmail = sheet.getCell(4,i).getContents();
							    }	
								
								String vJobTitle = "";
								if(CommonUtils.isNotEmpty(sheet.getCell(5,i).getContents())){
									vJobTitle = sheet.getCell(5,i).getContents();
							    }
								
								Date vRegistrationTime = null;
								if(CommonUtils.isNotEmpty(sheet.getCell(6,i).getContents())){
									
									
									if (sheet.getCell(6,i).getContents().length() < 17){
							    		SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yy HH:mm");
							    		vRegistrationTime = ft.parse(sheet.getCell(6,i).getContents());
							    	} else {
							    		SimpleDateFormat ft = new SimpleDateFormat ("MMM dd, yyyy HH:mm");
							    		vRegistrationTime = ft.parse(sheet.getCell(6,i).getContents());
							    	}
									
							    }	
								
								
								String vApprovalStatus = "";
								if(CommonUtils.isNotEmpty(sheet.getCell(7,i).getContents())){
									vApprovalStatus = sheet.getCell(7,i).getContents();
							    }
								
								Date vJoinTime = null;
								if(CommonUtils.isNotEmpty(sheet.getCell(8,i).getContents())){
									if((sheet.getCell(8,i).getContents()).equals("--")== false) {
										
										if (sheet.getCell(8,i).getContents().length() < 17){
								    		SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yy HH:mm");
								    		vJoinTime = ft.parse(sheet.getCell(8,i).getContents());
								    	} else {
								    		SimpleDateFormat ft = new SimpleDateFormat ("MMM dd, yyyy HH:mm");
								    		vJoinTime = ft.parse(sheet.getCell(8,i).getContents());
								    	}
										
									}
							    }								
								
								Date vLeaveTime = null;
								if(CommonUtils.isNotEmpty(sheet.getCell(9,i).getContents())){
									if((sheet.getCell(9,i).getContents()).equals("--")== false) {
										
										if (sheet.getCell(9,i).getContents().length() < 17){
								    		SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yy HH:mm");
								    		vLeaveTime = ft.parse(sheet.getCell(9,i).getContents());
								    	} else {
								    		SimpleDateFormat ft = new SimpleDateFormat ("MMM dd, yyyy HH:mm");
								    		vLeaveTime = ft.parse(sheet.getCell(9,i).getContents());
								    	}
										
									}
							    }
								
								
								int vTimeInSession = 0;
								if(CommonUtils.isNotEmpty(sheet.getCell(10,i).getContents())){
									if((sheet.getCell(10,i).getContents()).equals("--")== false) {
										vTimeInSession = Integer.parseInt(sheet.getCell(10,i).getContents());
							   
									}
								}
								
								// Company Name 11
								String vOrganization = "";
								
								//Diubah Jadi "Kota" 12
								String vStateProvince = "";
								
								
								
								//Diubah Jadi "Whatsapp No" 13
								String vPhone = "";
								
								//Diubah Jadi "GOPAY No" 14
								String vNoGopayOvo = "";
								
								//Diubah Jadi "GOPAY No" 15
								String vEmailCorporate = "";
								
								String vIndustry = "";
								
								String vCountryRegionName = "";
// Remark Karen posisi berubah lagi								
//								if (vFirstColumn.equals("Yes") == true) {	
									
									if(CommonUtils.isNotEmpty(sheet.getCell(11,i).getContents())){
										vOrganization = sheet.getCell(11,i).getContents();
								    }	
									
									if(CommonUtils.isNotEmpty(sheet.getCell(12,i).getContents())){
										vStateProvince = sheet.getCell(12,i).getContents();
								    }
									
									if(CommonUtils.isNotEmpty(sheet.getCell(13,i).getContents())){
										vPhone = sheet.getCell(13,i).getContents();
								    }
									
									// Gopay di cabut
									vNoGopayOvo = "";
									//if(CommonUtils.isNotEmpty(sheet.getCell(14,i).getContents())){
									//	vNoGopayOvo = sheet.getCell(14,i).getContents();
								    //}
									
									if(CommonUtils.isNotEmpty(sheet.getCell(14,i).getContents())){
										vEmailCorporate = sheet.getCell(14,i).getContents();
								    }
									
									if(CommonUtils.isNotEmpty(sheet.getCell(15,i).getContents())){
										vIndustry = sheet.getCell(15,i).getContents();
								    }
									
									String vInvitedBy = "";
									if(CommonUtils.isNotEmpty(sheet.getCell(16,i).getContents())){
										vInvitedBy = sheet.getCell(16,i).getContents();
								    }
									
									String vSalesInvitedBy = "";									
									if(CommonUtils.isNotEmpty(sheet.getCell(17,i).getContents())){
										vSalesInvitedBy = sheet.getCell(17,i).getContents();
								    }
									
									
									if(CommonUtils.isNotEmpty(sheet.getCell(18,i).getContents())){
										vCountryRegionName = sheet.getCell(18,i).getContents();
								    }
									
									
//								} else {
//										
//									
//									if(CommonUtils.isNotEmpty(sheet.getCell(12,i).getContents())){
//										vOrganization = sheet.getCell(12,i).getContents();
//								    }	
//									
//									if(CommonUtils.isNotEmpty(sheet.getCell(13,i).getContents())){
//										vStateProvince = sheet.getCell(13,i).getContents();
//								    }
//									
//									if(CommonUtils.isNotEmpty(sheet.getCell(14,i).getContents())){
//										vPhone = sheet.getCell(14,i).getContents();
//								    }
//									
//									if(CommonUtils.isNotEmpty(sheet.getCell(15,i).getContents())){
//										vNoGopayOvo = sheet.getCell(15,i).getContents();
//								    }
//									
//									if(CommonUtils.isNotEmpty(sheet.getCell(16,i).getContents())){
//										vEmailCorporate = sheet.getCell(16,i).getContents();
//								    }
//									
//									if(CommonUtils.isNotEmpty(sheet.getCell(17,i).getContents())){
//										vIndustry = sheet.getCell(17,i).getContents();
//								    }
//									
//									//if(CommonUtils.isNotEmpty(sheet.getCell(16,i).getContents())){
//									//	vCountryRegionName = sheet.getCell(16,i).getContents();
//								    //}
//								}
								

																						
								
								String vCountryRegion = "";							
							
								String vQuestionAndComment = "";
								
								
								Temp02WebinarAttendee anDataTemp02 =
										new Temp02WebinarAttendee(vAttended, vUserName, vFirstName, vLastName, vEmail, 
												vEmailCorporate,vCountryRegion, 
												vStateProvince, vPhone, vIndustry, vOrganization, 
												vJobTitle, vQuestionAndComment, vRegistrationTime, 
												vApprovalStatus, vJoinTime, vLeaveTime, vTimeInSession, 
												vNoGopayOvo, vInvitedBy,  vSalesInvitedBy, vCountryRegionName, vProsesId);
								
								listDataAttendee.add(anDataTemp02);
								
//								System.out.println("vAttended : "+vAttended);
//								System.out.println("vFirstName : "+vFirstName);
//								System.out.println("vLastName : "+vLastName);
//								System.out.println("vEmail : "+vEmail);
//								System.out.println("vCountryRegion : "+vCountryRegion);
//								System.out.println("vStateProvince : "+vStateProvince);
//								System.out.println("vPhone : "+vPhone);
//								System.out.println("vIndustry : "+vIndustry);
//								System.out.println("vOrganization : "+vOrganization);
//								System.out.println("vJobTitle : "+vJobTitle);
//								System.out.println("vQuestionAndComment : "+vQuestionAndComment);
//								System.out.println("vRegistrationTime : "+vRegistrationTime);
//								System.out.println("vApprovalStatus : "+vApprovalStatus);
//								System.out.println("vJoinTime : "+vJoinTime);
//								System.out.println("vLeaveTime : "+vLeaveTime);
//								System.out.println("vTimeInSession : "+vTimeInSession);
//								System.out.println("vNoGopayOvo : "+vNoGopayOvo);
//								System.out.println("vCountryRegionName : "+vCountryRegionName);
								
							} else {
//								System.out.println("Tidak Masuk Attended : "+vFirstColumn);
								vStopCollectAttendeeDetails = 1;
							}
							
						}
							
						
					}
				}
				workbook.close();
				
				if(CommonUtils.isNotEmpty(listData)){
					temp01WebinarEventService.save(listData);					
				}
				if(CommonUtils.isNotEmpty(listDataAttendee)){
					temp02WebinarAttendeeService.save(listDataAttendee);					
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
			String vResult = callStoreProcOrFuncService.callUploadWebinarEvent(vProsesId, SecurityContextHolder.getContext().getAuthentication().getName());
			Messagebox.show(vResult, "Message",
					Messagebox.OK, Messagebox.INFORMATION);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
 
}