package solusi.hapis.webui.accounting;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
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

public class InvoiceSoftcopyOtherCtrl extends GFCBaseCtrl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtInvFrom;
	
		
	protected Textbox lbl1;	
	protected Textbox lbl2;
	protected Textbox lbl3;
	protected Textbox lbl4;
	protected Textbox lbl5;
	protected Textbox lbl6;
	
	private PathReport pathRpt;
	private File FilePDF0;
	private File FilePDF1;
	private File FilePDF2;
	private File FilePDF3;
	private File FilePDF4;
	private File FilePDF5;
	private File FilePDF6;
	
	protected Radiogroup rdgMaterai;	 
	protected Radio rdYes;
	protected Radio rdNo;		

	private Media amedia1;
	private Media amedia2;
	private Media amedia3;
	private Media amedia4;
	private Media amedia5;
	private Media amedia6;
	
	
	protected Radiogroup rdgNoPO;	 
	protected Radio rdYesPO;
	protected Radio rdNoPO;
	
	private String NamaFileAkhir ;

	private CallStoreProcOrFuncService callStoreProcOrFuncService;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  
		   	
    	rdYes.setSelected(true); 
    	
    	rdYesPO.setSelected(true); 
    	
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
		
		
		

		
    	
    	
    	if (	CommonUtils.isNotEmpty(amedia1)==false &&
    			CommonUtils.isNotEmpty(amedia2)==false &&
    			CommonUtils.isNotEmpty(amedia3)==false &&
    			CommonUtils.isNotEmpty(amedia4)==false &&
    			CommonUtils.isNotEmpty(amedia5)==false &&
    			CommonUtils.isNotEmpty(amedia6)==false
    			
    			) {
    		new JReportGeneratorWindow(param, jasperRpt, "PDF"); 
    	} else {
    		
    		String timeStamp = String.valueOf(System.currentTimeMillis());
        	pathRpt = new PathReport(timeStamp);
			
			PDDocument document0 = null; 
			PDDocument document1 = null;
			PDDocument document2 = null;
			PDDocument document3 = null;
			PDDocument document4 = null;
			PDDocument document5 = null;
			PDDocument document6 = null;
			
			if (vInvFrom.equals(".") == false){
				FilePDF0 =  new File("PrePrintInvoice.pdf");		
				OutputStream osPDF0 = new FileOutputStream(FilePDF0);
				osPDF0.write(JReportGeneratorWindow.JReportGeneratorByte(param, jasperRpt, "PDF"));
				osPDF0.close();
				
				document0 = PDDocument.load(FilePDF0);  
			}
			
			if (CommonUtils.isNotEmpty(amedia1)==true){
				FilePDF1 = new File(amedia1.getName());			
				OutputStream osPDF1 = new FileOutputStream(FilePDF1);
				InputStream isPDF1 = amedia1.getStreamData();
				byte[] buffer = new byte[1024];
				for (int count; (count = isPDF1.read(buffer)) != -1;) {
					osPDF1.write(buffer, 0, count);
				}
				osPDF1.flush();
				osPDF1.close();
				isPDF1.close();
				
				document1 = PDDocument.load(FilePDF1);  
			}
			
			if (CommonUtils.isNotEmpty(amedia2)==true){
				FilePDF2 = new File(amedia2.getName());			
				OutputStream osPDF2 = new FileOutputStream(FilePDF2);
				InputStream isPDF2 = amedia2.getStreamData();
				byte[] buffer = new byte[1024];
				for (int count; (count = isPDF2.read(buffer)) != -1;) {
					osPDF2.write(buffer, 0, count);
				}
				osPDF2.flush();
				osPDF2.close();
				isPDF2.close();
				
				document2 = PDDocument.load(FilePDF2);  
			}
			
			
			if (CommonUtils.isNotEmpty(amedia3)==true){
				FilePDF3 = new File(amedia3.getName());			
				OutputStream osPDF3 = new FileOutputStream(FilePDF3);
				InputStream isPDF3 = amedia3.getStreamData();
				byte[] buffer = new byte[1024];
				for (int count; (count = isPDF3.read(buffer)) != -1;) {
					osPDF3.write(buffer, 0, count);
				}
				osPDF3.flush();
				osPDF3.close();
				isPDF3.close();
				
				document3 = PDDocument.load(FilePDF3);  
			}
			 
			if (CommonUtils.isNotEmpty(amedia4)==true){
				FilePDF4 = new File(amedia4.getName());			
				OutputStream osPDF4 = new FileOutputStream(FilePDF4);
				InputStream isPDF4 = amedia4.getStreamData();
				byte[] buffer = new byte[1024];
				for (int count; (count = isPDF4.read(buffer)) != -1;) {
					osPDF4.write(buffer, 0, count);
				}
				osPDF4.flush();
				osPDF4.close();
				isPDF4.close();
				
				document4 = PDDocument.load(FilePDF4);  
			}
			
			if (CommonUtils.isNotEmpty(amedia5)==true){
				FilePDF5 = new File(amedia5.getName());			
				OutputStream osPDF5 = new FileOutputStream(FilePDF5);
				InputStream isPDF5 = amedia5.getStreamData();
				byte[] buffer = new byte[1024];
				for (int count; (count = isPDF5.read(buffer)) != -1;) {
					osPDF5.write(buffer, 0, count);
				}
				osPDF5.flush();
				osPDF5.close();
				isPDF5.close();
				
				document5 = PDDocument.load(FilePDF5);  
			}
			
			if (CommonUtils.isNotEmpty(amedia6)==true){
				FilePDF6 = new File(amedia6.getName());			
				OutputStream osPDF6 = new FileOutputStream(FilePDF6);
				InputStream isPDF6 = amedia6.getStreamData();
				byte[] buffer = new byte[1024];
				for (int count; (count = isPDF6.read(buffer)) != -1;) {
					osPDF6.write(buffer, 0, count);
				}
				osPDF6.flush();
				osPDF6.close();
				isPDF6.close();
				
				document6 = PDDocument.load(FilePDF6);  
			}
			 
		
			//Create PDFMergerUtility class object  
	        PDFMergerUtility PDFmerger = new PDFMergerUtility();  
	
	        //Setting the destination file path  
	        PDFmerger.setDestinationFileName(pathRpt.getHasilMergeInvSATINDO());  
	
	        //adding the source files  

	        
	        if (vInvFrom.equals(".") == false){
	        	PDFmerger.addSource(FilePDF0);
	        }
	        
	        
	        if (CommonUtils.isNotEmpty(amedia1)==true){
	        	PDFmerger.addSource(FilePDF1);  
	        }
	        
	        if (CommonUtils.isNotEmpty(amedia2)==true){
	        	PDFmerger.addSource(FilePDF2);  
	        }
	        
	        if (CommonUtils.isNotEmpty(amedia3)==true){
	        	PDFmerger.addSource(FilePDF3);  
	        }
	        
	        if (CommonUtils.isNotEmpty(amedia4)==true){
	        	PDFmerger.addSource(FilePDF4);  
	        }
	        
	        if (CommonUtils.isNotEmpty(amedia5)==true){
	        	PDFmerger.addSource(FilePDF5);  
	        }
	        
	        if (CommonUtils.isNotEmpty(amedia6)==true){
	        	PDFmerger.addSource(FilePDF6);  
	        }
	        
	        PDFmerger.mergeDocuments(null);  
	        
	            
	        //Close documents  
	        
	        if (vInvFrom.equals(".") == false){
	        	document0.close();   
		        FilePDF0.delete();
	        	FilePDF0.deleteOnExit();
	        	
	        	NamaFileAkhir = vInvFrom;
	        } else {
	        	NamaFileAkhir = "MergePDF";
	        }
	        
	        
	        if (CommonUtils.isNotEmpty(amedia1)==true){
	        	document1.close();  
	        	FilePDF1.delete();
	        	FilePDF1.deleteOnExit();
	        }
	        
	        if (CommonUtils.isNotEmpty(amedia2)==true){
	        	document2.close();  
	        	FilePDF2.delete();
	        	FilePDF2.deleteOnExit();
	        }
	        
	        if (CommonUtils.isNotEmpty(amedia3)==true){
	        	document3.close();  
	        	FilePDF3.delete();
	        	FilePDF3.deleteOnExit();
	        }
	        
	        if (CommonUtils.isNotEmpty(amedia4)==true){
	        	document4.close();  
	        	FilePDF4.delete();
	        	FilePDF4.deleteOnExit();
	        }
	        
	        if (CommonUtils.isNotEmpty(amedia5)==true){
	        	document5.close();  
	        	FilePDF5.delete();
	        	FilePDF5.deleteOnExit();	
	        }
	        
	        if (CommonUtils.isNotEmpty(amedia6)==true){
	        	document6.close();  
	        	FilePDF6.delete();
	        	FilePDF6.deleteOnExit();	
	        }
	       
	        
	       
	        File fileHasilAwal = new File(pathRpt.getHasilMergeInvSATINDO());
	        long vSizeAwal = fileHasilAwal.length();
	        
	        // Jika lebih dari 2 MB coba di Resize
	        if (vSizeAwal > (2*1024*1024))	{
		        File fileHasilRezise = new File("HasilReziseInvoice.pdf");
		        
		        PDDocument pdDocument = new PDDocument();
		        PDDocument oDocument = PDDocument.load(fileHasilAwal);
		        PDFRenderer pdfRenderer = new PDFRenderer(oDocument);
		        int numberOfPages = oDocument.getNumberOfPages();
		        PDPage page = null;
	 		 
		        for (int i = 0; i < numberOfPages; i++) {
		        	page = new PDPage(oDocument.getPage(i).getMediaBox());
	    		    
	    		    BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 100, ImageType.RGB);
	    		    PDImageXObject pdImage = JPEGFactory.createFromImage(pdDocument, bim);
	    		    PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
	    		    float newHeight =  ((page.getMediaBox().getHeight())) ;//PDRectangle.LETTER.getHeight();
	    		    float newWidth = ((page.getMediaBox().getWidth()));//PDRectangle.LETTER.getWidth();
	    		    contentStream.drawImage(pdImage, 0, 0, newWidth, newHeight);
	    		    contentStream.close();
	    		 
	    		    pdDocument.addPage(page);
		        }
	    		pdDocument.save(fileHasilRezise);
	    		pdDocument.close();
		        
		        
	    		long vSizeRezise = fileHasilRezise.length();
		        
		        if (vSizeRezise < vSizeAwal){
		        	InputStream mediais = new FileInputStream(fileHasilRezise);
		 			AMedia amedia = new AMedia(NamaFileAkhir+".pdf", "pdf", "application/pdf", mediais);
		 			Filedownload.save(amedia);	
		        } else {
		        	InputStream mediais = new FileInputStream(fileHasilAwal);
		 			AMedia amedia = new AMedia(NamaFileAkhir+".pdf", "pdf", "application/pdf", mediais);
		 			Filedownload.save(amedia);	
		        }
		        
		        fileHasilAwal.delete();				
		        fileHasilAwal.deleteOnExit();
		        
		        fileHasilRezise.delete();				
		        fileHasilRezise.deleteOnExit();
	        } else {
	        	InputStream mediais = new FileInputStream(fileHasilAwal);
	 			AMedia amedia = new AMedia(NamaFileAkhir+".pdf", "pdf", "application/pdf", mediais);
	 			Filedownload.save(amedia);	
	 			
	 			fileHasilAwal.delete();				
		        fileHasilAwal.deleteOnExit();
	        }
	       			
			
	       
			
				
		}
  
		
	}
	
	public void onClick$btnOK1(Event event) throws InterruptedException, ParseException, IOException {
		amedia1 = Fileupload.get("Please select a File", "Upload");

		if (CommonUtils.isNotEmpty(amedia1)) {
			String FileType = amedia1.getContentType();
		
			
			if (FileType.equals("application/pdf") == false ){
				Messagebox.show("File yang diupload bukan PDF ", "Message",
						Messagebox.OK, Messagebox.INFORMATION);
				amedia1 = null;
				lbl1.setValue("");
			} else {
			
				lbl1.setValue(amedia1.getName()+ " Sudah berhasil terupload.");
			}
		}		
	}
	
	public void onClick$btnOK2(Event event) throws InterruptedException, ParseException, IOException {
		amedia2 = Fileupload.get("Please select a File", "Upload");

		if (CommonUtils.isNotEmpty(amedia2)) {
			String FileType = amedia2.getContentType();
		
			
			if (FileType.equals("application/pdf") == false ){
				Messagebox.show("File yang diupload bukan PDF ", "Message",
						Messagebox.OK, Messagebox.INFORMATION);
				amedia2 = null;
				lbl2.setValue("");
			} else {
			
				lbl2.setValue(amedia2.getName()+ " Sudah berhasil terupload.");
			}
		}		
	}
 
	public void onClick$btnOK3(Event event) throws InterruptedException, ParseException, IOException {
		amedia3 = Fileupload.get("Please select a File", "Upload");

		if (CommonUtils.isNotEmpty(amedia3)) {
			String FileType = amedia3.getContentType();
		
			
			if (FileType.equals("application/pdf") == false ){
				Messagebox.show("File yang diupload bukan PDF ", "Message",
						Messagebox.OK, Messagebox.INFORMATION);
				amedia3 = null;
				lbl3.setValue("");
			} else {
			
				lbl3.setValue(amedia3.getName()+ " Sudah berhasil terupload.");
			}
		}		
	}
	
	
	public void onClick$btnOK4(Event event) throws InterruptedException, ParseException, IOException {
		amedia4 = Fileupload.get("Please select a File", "Upload");

		if (CommonUtils.isNotEmpty(amedia4)) {
			String FileType = amedia4.getContentType();
		
			
			if (FileType.equals("application/pdf") == false ){
				Messagebox.show("File yang diupload bukan PDF ", "Message",
						Messagebox.OK, Messagebox.INFORMATION);
				amedia4 = null;
				lbl4.setValue("");
			} else {
			
				lbl4.setValue(amedia4.getName()+ " Sudah berhasil terupload.");
			}
		}		
	}

	
	public void onClick$btnOK5(Event event) throws InterruptedException, ParseException, IOException {
		amedia5 = Fileupload.get("Please select a File", "Upload");

		if (CommonUtils.isNotEmpty(amedia5)) {
			String FileType = amedia5.getContentType();
		
			
			if (FileType.equals("application/pdf") == false ){
				Messagebox.show("File yang diupload bukan PDF ", "Message",
						Messagebox.OK, Messagebox.INFORMATION);
				amedia5 = null;
				lbl5.setValue("");
			} else {
			
				lbl5.setValue(amedia5.getName()+ " Sudah berhasil terupload.");
			}
		}		
	}
	
	
	public void onClick$btnOK6(Event event) throws InterruptedException, ParseException, IOException {
		amedia6 = Fileupload.get("Please select a File", "Upload");

		if (CommonUtils.isNotEmpty(amedia6)) {
			String FileType = amedia6.getContentType();
		
			
			if (FileType.equals("application/pdf") == false ){
				Messagebox.show("File yang diupload bukan PDF ", "Message",
						Messagebox.OK, Messagebox.INFORMATION);
				amedia6 = null;
				lbl6.setValue("");
			} else {
			
				lbl6.setValue(amedia6.getName()+ " Sudah berhasil terupload.");
			}
		}		
	}
}

