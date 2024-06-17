package solusi.hapis.webui.accounting;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.common.PathReport;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class InvoiceSoftcopySATINDOCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtInvFrom;
	
	protected Textbox lbl1;	
	
	
	private PathReport pathRpt;
	private File FilePDF1;
	private File FilePDF2;
	
	
	protected Radiogroup rdgMaterai;	 
	protected Radio rdYes;
	protected Radio rdNo;		
	
	protected Radiogroup rdgNoPO;	 
	protected Radio rdYesPO;
	protected Radio rdNoPO;

	private Media amedia;

	private CallStoreProcOrFuncService callStoreProcOrFuncService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		   	
    	rdYes.setSelected(true); 
    	rdYesPO.setSelected(true); 
    	
	}
	
		
	public void onClick$btnSync(Event event) throws InterruptedException, SQLException, ParseException  {
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReportManual("0108003");
		
		Messagebox.show("Sync Sudah Selesai");
	}

	@SuppressWarnings("unchecked")
	public void onClick$btnOK(Event event) throws InterruptedException, IOException {
		
		

		
		String vInvFrom = ".";
		if (StringUtils.isNotEmpty(txtInvFrom.getValue())) {
			vInvFrom = txtInvFrom.getValue();
		} 
		
		
		String vPrintMaterai = "Y";
		if (StringUtils.isNotEmpty(rdgMaterai.getSelectedItem().getValue())) {
			vPrintMaterai = rdgMaterai.getSelectedItem().getValue();	
		} 
		
		String vPrintPO = "Y";
		if (StringUtils.isNotEmpty(rdgNoPO.getSelectedItem().getValue())) {
			vPrintPO = rdgNoPO.getSelectedItem().getValue();	
		} 
				
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108003");
		
		String jasperRpt = "/solusi/hapis/webui/reports/accounting/01059_00_InvoiceSoftcopy.jasper";
		
		PathReport pathReport = new PathReport();
		param.put("SUBREPORT_DIR",  pathReport.getSubRptAccounting());
		
    	param.put("InvoiceFrom",  vInvFrom); 
		param.put("InvoiceUpto",  vInvFrom);  
		param.put("PrintMaterai",  vPrintMaterai); 
		param.put("PrintPO",  vPrintPO); 
		
		
		

		
    	
    	
    	if (CommonUtils.isNotEmpty(amedia)) {
    		
    		String timeStamp = String.valueOf(System.currentTimeMillis());
        	pathRpt = new PathReport(timeStamp);
        	
			//FilePDF1 = new File(pathRpt.getPreprintInvSATINDO());
	    	
	    	FilePDF1 =  new File("PrePrintInvSATINDO.pdf");
			
		
			OutputStream osPDF1 = new FileOutputStream(FilePDF1);
			osPDF1.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "PDF"));
			osPDF1.close();
		
	
		
			FilePDF2 = new File(amedia.getName());
			
			OutputStream osPDF2 = new FileOutputStream(FilePDF2);
			InputStream isPDF2 = amedia.getStreamData();
			byte[] buffer = new byte[1024];
			for (int count; (count = isPDF2.read(buffer)) != -1;) {
				osPDF2.write(buffer, 0, count);
			}
			osPDF2.flush();
			osPDF2.close();
			isPDF2.close();
			
			
			
			PDDocument document1 = PDDocument.load(FilePDF1);   
			PDDocument document2 = PDDocument.load(FilePDF2);   
			 
		
			//Create PDFMergerUtility class object  
	        PDFMergerUtility PDFmerger = new PDFMergerUtility();  
	
	        //Setting the destination file path  
	        PDFmerger.setDestinationFileName(pathRpt.getHasilMergeInvSATINDO());  
	
	        //adding the source files  
	        PDFmerger.addSource(FilePDF1);  
	        PDFmerger.addSource(FilePDF2);  
	  
	        PDFmerger.mergeDocuments(null);  
	        
	        //System.out.println("PDF Documents merged to a single file successfully");  
	         
	        //Close documents  
	        document1.close();  
	        document2.close();  
	       
	        File fileHasil = new File(pathRpt.getHasilMergeInvSATINDO());
	        InputStream mediais = new FileInputStream(fileHasil);
			AMedia HasilMedia = new AMedia(vInvFrom+".pdf", "pdf", "application/pdf", mediais);
			
			Filedownload.save(HasilMedia);		
			
			fileHasil.delete();					
			FilePDF1.delete();
			FilePDF2.delete();
			
			fileHasil.deleteOnExit();	
			FilePDF1.deleteOnExit();	
			FilePDF2.deleteOnExit();	
		} else {
			new JReportGeneratorWindow(param, jasperRpt, "PDF", vInvFrom, "XXX"); 
				
		}
  
		
	}
	
	public void onClick$btnOK1(Event event) throws InterruptedException, ParseException, IOException {
		amedia = Fileupload.get("Please select a File", "Upload");

		if (CommonUtils.isNotEmpty(amedia)) {
			String FileType = amedia.getContentType();
		
			
			if (FileType.equals("application/pdf") == false ){
				Messagebox.show("File yang diupload bukan PDF ", "Message",
						Messagebox.OK, Messagebox.INFORMATION);
				amedia = null;
				lbl1.setValue("");
			} else {
			
				lbl1.setValue(amedia.getName()+ " Sudah berhasil terupload.");
			}
		}
		
	}
 
}

