package solusi.hapis.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
public class MergePdfDocuments {  
        public static void main(String[] args)throws IOException {  
                  
//        //Loading an existing PDF document  
//          File file1 = new File("D:\\FromDesktop\\SalesRequest.pdf");  
//          PDDocument document1 = PDDocument.load(file1);   
//          File file2 = new File("D:\\FromDesktop\\SalesRequest.pdf");  
//          PDDocument document2 = PDDocument.load(file2);    
//          File file3 = new File("D:\\FromDesktop\\PostedSalesInvoice.pdf");  
//          PDDocument document3 = PDDocument.load(file3);   
//      
//    //Create PDFMergerUtility class object  
//          PDFMergerUtility PDFmerger = new PDFMergerUtility();  
//  
//    //Setting the destination file path  
//    PDFmerger.setDestinationFileName("D:\\FromDesktop\\merged.pdf");  
//  
//    //adding the source files  
//    PDFmerger.addSource(file1);  
//    PDFmerger.addSource(file2);  
//    PDFmerger.addSource(file3);  
//  
//    //Merging the documents  
//    PDFmerger.mergeDocuments(null);  
//  
//     System.out.println("PDF Documents merged to a single file successfully");  
//      
////Close documents  
//    document1.close();  
//    document2.close();  
//    document3.close();  
//    
    
        	
        	try {
        		 
        		   File aPdf = new File("D:\\FromDesktop\\8SIAT200002.pdf");
        		 
        		   File aPdfOut = new File("D:\\FromDesktop\\8SIAT200002.pdf");
        		   
        		   
        		   System.out.println("panjang : "+aPdf.length());
        		   
        		   PDDocument pdDocument = new PDDocument();
        		   PDDocument oDocument = PDDocument.load(aPdf);
        		   PDFRenderer pdfRenderer = new PDFRenderer(oDocument);
        		   int numberOfPages = oDocument.getNumberOfPages();
        		   PDPage page = null;
        		 
        		  
        		  
        		   
        		   for (int i = 0; i < numberOfPages; i++) {
        			//page =  oDocument.getPage(i); //new PDPage(PDRectangle.LETTER);
        			   
        			//   PDRectangle
        			   
        		    page = new PDPage(oDocument.getPage(i).getMediaBox());//(PDRectangle.LETTER);
        		    
        		    
        		    BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 100, ImageType.RGB);
        		    PDImageXObject pdImage = JPEGFactory.createFromImage(pdDocument, bim);
        		    PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page);
        		    float newHeight =  ((page.getMediaBox().getHeight())) ;//PDRectangle.LETTER.getHeight();
        		    float newWidth = ((page.getMediaBox().getWidth()));//PDRectangle.LETTER.getWidth();
        		    contentStream.drawImage(pdImage, 0, 0, newWidth, newHeight);
        		    contentStream.close();
        		 
        		    pdDocument.addPage(page);
        		   }
        		 
        		   pdDocument.save(aPdfOut);
        		   pdDocument.close();
        		 
        		   System.out.println("Selesai : "+aPdfOut.length());
        		 
        		  } catch (IOException e) {
        		   e.printStackTrace();
        		  }
        		 
        		 
   	
        	

        	
    }  
}  