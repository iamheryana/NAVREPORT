package solusi.hapis.webui.reports.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

import solusi.hapis.common.config.report.ConnectionReport;
import solusi.hapis.common.config.report.ConnectionReportHesk;
import solusi.hapis.common.config.report.ConnectionReportOrig;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

public class JReportGeneratorWindow {

	// Hasil Report dalam bentuk Byte,
	// di gunakan apabila hasil Report akan di Kirim melalui Email sebagai Attachment
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static byte[] JReportGeneratorByte(Map paramReport, String jasperRpt, String format){
		JasperPrint jasperPrint;
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperRpt);
	    byte[] buf = null;
		try {
			 if("PDF".equalsIgnoreCase(format)) {
				 buf = JasperRunManager.runReportToPdf(is, paramReport, ConnectionReport.getConnection());
			 } else if("XLS".equalsIgnoreCase(format)) {
				 jasperPrint = JasperFillManager.fillReport(is, paramReport, ConnectionReport.getConnection());
				
				 ByteArrayOutputStream output = new ByteArrayOutputStream();
				 JExcelApiExporter exporterXLS = new JExcelApiExporter();
				 exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
				 exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
				 exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				 exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				 exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				 exporterXLS.exportReport();
				
				 buf =  output.toByteArray();
			 }
			
		} catch (JRException e) {
			e.printStackTrace();
		}
	    		
		return buf; 
	}
	
	public static byte[] JReportGeneratorByte(Map paramReport, String jasperRpt, String format, int Orig){
		JasperPrint jasperPrint;
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperRpt);
	    byte[] buf = null;
		try {
			 if("PDF".equalsIgnoreCase(format)) {
				 buf = JasperRunManager.runReportToPdf(is, paramReport, ConnectionReportOrig.getConnection());
			 } else if("XLS".equalsIgnoreCase(format)) {
				 jasperPrint = JasperFillManager.fillReport(is, paramReport, ConnectionReportOrig.getConnection());
				
				 ByteArrayOutputStream output = new ByteArrayOutputStream();
				 JExcelApiExporter exporterXLS = new JExcelApiExporter();
				 exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
				 exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
				 exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				 exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				 exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				 exporterXLS.exportReport();
				
				 buf =  output.toByteArray();
			 }
			
		} catch (JRException e) {
			e.printStackTrace();
		}
	    		
		return buf; 
	}
	
	// Hasil Report dalam bentuk Byte dan format hanya dalam bentuk PDF yang sudah diberikan Password,
	// di gunakan apabila hasil Report akan di Kirim melalui Email sebagai Attachment
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static byte[] JReportGeneratorBytePDFPasswd(Map paramReport, String jasperRpt, String passwordPDF){
		JasperPrint jasperPrint;
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperRpt);
	    byte[] buf = null;
		try {
			
			jasperPrint = JasperFillManager.fillReport(is, paramReport, ConnectionReport.getConnection());
			
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			
			JRPdfExporter exporter = new JRPdfExporter();       
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
			exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
			exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE);
			exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, passwordPDF);
			exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, passwordPDF);
			exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, new Integer(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING));
			exporter.exportReport();
						
			buf =  output.toByteArray();
						
		} catch (JRException e) {
			e.printStackTrace();
		}
	    		
		return buf; 
	}
	
	// Hasil Report dalam bentuk File PDF sudah diberikan Password dan bisa didownload, tanpa ditampilkan di layar.
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void JReportGeneratorPDFPasswd(Map paramReport, String jasperRpt, String fileNamePDF, String passwordPDF) throws JRException, DocumentException, IOException{
		JasperPrint jasperPrint;
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperRpt);

		jasperPrint = JasperFillManager.fillReport(is, paramReport, ConnectionReport.getConnection());
			
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		JRPdfExporter exporter = new JRPdfExporter();       
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
		exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
		exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE);
		exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, passwordPDF);
		exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, passwordPDF);
		exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, new Integer(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING));
		exporter.exportReport();
					
		InputStream mediais = new ByteArrayInputStream(output.toByteArray());
         	
        AMedia amedia = new AMedia(fileNamePDF+".pdf", "pdf", "application/pdf", mediais);

		Filedownload.save(amedia);
			
	    
	}
	
	// Hasil Report dalam bentuk File PDF sudah diberikan Password atau Excel tanpa Password dan Report ditampilkan di layar.
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JReportGeneratorWindow(Map paramReport, String jasperRpt, String format, String passwordPDF) {
		JasperPrint jasperPrint;
		try
		{
		    Map param = new HashMap();
		    param = paramReport;
	
		    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperRpt);
		    
		    Window win = (Window) Executions.createComponents("/WEB-INF/pages/report/report.zul", null, null);
		    
		    AMedia amedia = null;
		   
		   
		    
		    if("PDF".equalsIgnoreCase(format)) {
		    	
				jasperPrint = JasperFillManager.fillReport(is, paramReport, ConnectionReport.getConnection());
				
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				
				JRPdfExporter exporter = new JRPdfExporter();       
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
				exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
				exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE);
				exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, passwordPDF);
				exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, passwordPDF);
				exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, new Integer(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING));
				exporter.exportReport();
							
				InputStream mediais = new ByteArrayInputStream(output.toByteArray());
				amedia = new AMedia("ReportPDF.pdf", "pdf", "application/pdf", mediais);
				
		    } else if("XLS".equalsIgnoreCase(format)) {

		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReport.getConnection());
		    	
		    	ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JExcelApiExporter exporterXLS = new JExcelApiExporter();
	            exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
	            exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            exporterXLS.exportReport();
	            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	            amedia = new AMedia("ReportExcel", "xls", "application/vnd.ms-excel", mediais);
		    } else if("CSV".equalsIgnoreCase(format)) {
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReport.getConnection());

		    	
				ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JRCsvExporter exporter = new JRCsvExporter();
		    	exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
		    	exporter.exportReport();
	            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	            amedia = new AMedia("ReportCSV", "csv", "text/csv", mediais);	
		    }
		    Iframe iframe = (Iframe) win.getFellow("iframe");
            iframe.setContent(amedia);
		}
		catch (JRException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }
	}
	
	// Hasil Report dalam bentuk File PDF atau Excel tanpa Password dan Report ditampilkan di layar.
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JReportGeneratorWindow(Map paramReport, String jasperRpt, String format) {
		JasperPrint jasperPrint;
		try
		{
		    Map param = new HashMap();
		    param = paramReport;
	
		    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperRpt);
		    
		    Window win = (Window) Executions.createComponents("/WEB-INF/pages/reports/report.zul", null, null);
		    
		    AMedia amedia = null;
		   
		   
		    
		    if("PDF".equalsIgnoreCase(format)) {
		    	byte[] buf;

		    	buf = JasperRunManager.runReportToPdf(is, param, ConnectionReport.getConnection());
		    	
		    	InputStream mediais = new ByteArrayInputStream(buf);

		    	amedia = new AMedia("ReportPDF.pdf", "pdf", "application/pdf", mediais);
		    } else if("XLS".equalsIgnoreCase(format)) {
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReport.getConnection());
		    	
		    	ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JExcelApiExporter exporterXLS = new JExcelApiExporter();
	            exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
	            exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            exporterXLS.exportReport();
	            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	            amedia = new AMedia("ReportExcel", "xls", "application/vnd.ms-excel", mediais);
		    } else if("XLS2".equalsIgnoreCase(format)) {
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReport.getConnection());
		    	
		    	ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JExcelApiExporter exporterXLS = new JExcelApiExporter();
	            exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
	            exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            exporterXLS.exportReport();
	            
	            File targetFile = new File("Testing.xls");
	            @SuppressWarnings("resource")
				OutputStream outStream = new FileOutputStream(targetFile);
	            outStream.write(output.toByteArray());
	            
	           // InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	          
	            //amedia = new AMedia("ReportExcel", "xls", "application/vnd.ms-excel", mediais);
		    } else if("WRD".equalsIgnoreCase(format)){
		       jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReport.getConnection());
		    	
	    	
    		   JRDocxExporter exporter = new JRDocxExporter();
    		   ByteArrayOutputStream output = new ByteArrayOutputStream();    
    		   exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    		   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
    		   
    		   
    		   exporter.exportReport(); 
    		   InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	           amedia = new AMedia("ReportWord", "doc", "application/msword", mediais);

		    }else if("CSV".equalsIgnoreCase(format)) {
		    
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReport.getConnection());

		    	
				ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JRCsvExporter exporter = new JRCsvExporter();
		    	exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
		    	exporter.exportReport();
	            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	            amedia = new AMedia("ReportCSV", "csv", "text/csv", mediais);	
		    }else{
		    
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReport.getConnection());

		    	
				ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JRCsvExporter exporter = new JRCsvExporter();
		    	exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
		    	exporter.exportReport();
	            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	            amedia = new AMedia(format, "csv", "text/csv", mediais);	
		    }
		    Iframe iframe = (Iframe) win.getFellow("iframe");
            iframe.setContent(amedia);
		}
		catch (JRException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }
	}

	// Hasil Report dalam bentuk File PDF atau Excel tanpa Password 
	// dan Report ditampilkan di layar dengan nama File Khusus
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JReportGeneratorWindow(Map paramReport, String jasperRpt, String format, String namaFile, String temp) {
		JasperPrint jasperPrint;
		try
		{
		    Map param = new HashMap();
		    param = paramReport;
	
		    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperRpt);
		    
		    Window win = (Window) Executions.createComponents("/WEB-INF/pages/reports/report.zul", null, null);
		    
		    AMedia amedia = null;
		   
		   
		    
		    if("PDF".equalsIgnoreCase(format)) {
		    	byte[] buf;

		    	buf = JasperRunManager.runReportToPdf(is, param, ConnectionReport.getConnection());
		    	
		    	InputStream mediais = new ByteArrayInputStream(buf);

		    	amedia = new AMedia(namaFile+".pdf", "pdf", "application/pdf", mediais);
		    } else if("XLS".equalsIgnoreCase(format)) {
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReport.getConnection());
		    	
		    	ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JExcelApiExporter exporterXLS = new JExcelApiExporter();
	            exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
	            exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            exporterXLS.exportReport();
	            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	            amedia = new AMedia(namaFile, "xls", "application/vnd.ms-excel", mediais);
	            
	           
	            
		    } else if("XLS2".equalsIgnoreCase(format)) {
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReport.getConnection());
		    	
		    	ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JExcelApiExporter exporterXLS = new JExcelApiExporter();
	            exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
	            exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            exporterXLS.exportReport();
	            
	            File targetFile = new File("Testing.xls");
	            @SuppressWarnings("resource")
				OutputStream outStream = new FileOutputStream(targetFile);
	            outStream.write(output.toByteArray());
	            
	           // InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	          
	            //amedia = new AMedia("ReportExcel", "xls", "application/vnd.ms-excel", mediais);
		    } else if("WRD".equalsIgnoreCase(format)){
		       jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReport.getConnection());
		    	
	    	
    		   JRDocxExporter exporter = new JRDocxExporter();
    		   ByteArrayOutputStream output = new ByteArrayOutputStream();    
    		   exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    		   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
    		   
    		   
    		   exporter.exportReport(); 
    		   InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	           amedia = new AMedia(namaFile, "doc", "application/msword", mediais);

		    }else if("CSV".equalsIgnoreCase(format)) {
		    
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReport.getConnection());

		    	
				ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JRCsvExporter exporter = new JRCsvExporter();
		    	exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
		    	exporter.exportReport();
	            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	            amedia = new AMedia(namaFile, "csv", "text/csv", mediais);	
		    } else if("XML".equalsIgnoreCase(format)) {
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReport.getConnection());
		    	
		    	ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JRXmlExporter exporterXML = new JRXmlExporter();
		    
		    	exporterXML.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
	            exporterXML.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
	        
	            exporterXML.exportReport();
	            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	            
	            amedia = new AMedia(namaFile, "xml", "application/xml", mediais);
	            
		    }
		    Iframe iframe = (Iframe) win.getFellow("iframe");
            iframe.setContent(amedia);
		}
		catch (JRException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public JReportGeneratorWindow(List<Map> params, List<String> jasperRpts, String[] sheetName) {
		JasperPrint jasperPrint;
		try
		{		   
	
		    InputStream is ;//= Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperRpt[0]);
		    
		    Window win = (Window) Executions.createComponents("/WEB-INF/pages/reports/report.zul", null, null);
		    
		    AMedia amedia = null;
		    List<JasperPrint> printListS = new ArrayList<JasperPrint>();
		    
		    int vArrRptLenght = jasperRpts.size();
		    for (int i=0 ; i < vArrRptLenght ; i++){
		    	//System.out.println(jasperRpts.get(i));
		    	
		    	is = Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperRpts.get(i));
		    	printListS.add(JasperFillManager.fillReport(is, params.get(i), ConnectionReport.getConnection()));
		    }
	    	
	    	
	    	
	    	ByteArrayOutputStream output = new ByteArrayOutputStream();
	    	JExcelApiExporter exporterXLS = new JExcelApiExporter();
            exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT_LIST, printListS);
            exporterXLS.setParameter(JExcelApiExporterParameter.SHEET_NAMES, sheetName);
            exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
            exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.exportReport();
            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
            amedia = new AMedia("ReportExcel", "xls", "application/vnd.ms-excel", mediais);
            
            
            Iframe iframe = (Iframe) win.getFellow("iframe");
            iframe.setContent(amedia);

		}
		catch (JRException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }
	}
	public JReportGeneratorWindow(Map paramReport, String jasperRpt, String format, double hesk) {
		JasperPrint jasperPrint;
		try
		{
		    Map param = new HashMap();
		    param = paramReport;
	
		    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperRpt);
		    
		    Window win = (Window) Executions.createComponents("/WEB-INF/pages/reports/report.zul", null, null);
		    
		    AMedia amedia = null;
		   
		   
		    
		    if("PDF".equalsIgnoreCase(format)) {
		    	byte[] buf;

		    	buf = JasperRunManager.runReportToPdf(is, param, ConnectionReportHesk.getConnection());
		    	
		    	InputStream mediais = new ByteArrayInputStream(buf);

		    	amedia = new AMedia("ReportPDF.pdf", "pdf", "application/pdf", mediais);
		    } else if("XLS".equalsIgnoreCase(format)) {
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReportHesk.getConnection());
		    	
		    	ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JExcelApiExporter exporterXLS = new JExcelApiExporter();
	            exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
	            exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            exporterXLS.exportReport();
	            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	            amedia = new AMedia("ReportExcel", "xls", "application/vnd.ms-excel", mediais);
		    } else if("XLS2".equalsIgnoreCase(format)) {
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReportHesk.getConnection());
		    	
		    	ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JExcelApiExporter exporterXLS = new JExcelApiExporter();
	            exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
	            exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            exporterXLS.exportReport();
	            
	            File targetFile = new File("Testing.xls");
	            @SuppressWarnings("resource")
				OutputStream outStream = new FileOutputStream(targetFile);
	            outStream.write(output.toByteArray());
	            
	           // InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	          
	            //amedia = new AMedia("ReportExcel", "xls", "application/vnd.ms-excel", mediais);
		    } else if("WRD".equalsIgnoreCase(format)){
		       jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReportHesk.getConnection());
		    	
	    	
    		   JRDocxExporter exporter = new JRDocxExporter();
    		   ByteArrayOutputStream output = new ByteArrayOutputStream();    
    		   exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    		   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
    		   
    		   
    		   exporter.exportReport(); 
    		   InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	           amedia = new AMedia("ReportWord", "doc", "application/msword", mediais);

		    }else if("CSV".equalsIgnoreCase(format)) {
		    
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReportHesk.getConnection());

		    	
				ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JRCsvExporter exporter = new JRCsvExporter();
		    	exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
		    	exporter.exportReport();
	            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	            amedia = new AMedia("ReportCSV", "csv", "text/csv", mediais);	
		    }
		    Iframe iframe = (Iframe) win.getFellow("iframe");
            iframe.setContent(amedia);
		}
		catch (JRException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public JReportGeneratorWindow(Map paramReport, String jasperRpt, String format, int orig) {
		JasperPrint jasperPrint;
		try
		{
		    Map param = new HashMap();
		    param = paramReport;
	
		    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(jasperRpt);
		    
		    Window win = (Window) Executions.createComponents("/WEB-INF/pages/reports/report.zul", null, null);
		    
		    AMedia amedia = null;
		   
		   
		    
		    if("PDF".equalsIgnoreCase(format)) {
		    	byte[] buf;

		    	buf = JasperRunManager.runReportToPdf(is, param, ConnectionReportOrig.getConnection());
		    	
		    	InputStream mediais = new ByteArrayInputStream(buf);

		    	amedia = new AMedia("ReportPDF.pdf", "pdf", "application/pdf", mediais);
		    } else if("XLS".equalsIgnoreCase(format)) {
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReportOrig.getConnection());
		    	
		    	ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JExcelApiExporter exporterXLS = new JExcelApiExporter();
	            exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
	            exporterXLS.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, output);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	            exporterXLS.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            exporterXLS.exportReport();
	            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	            amedia = new AMedia("ReportExcel", "xls", "application/vnd.ms-excel", mediais);
		    } else if("WRD".equalsIgnoreCase(format)){
		       jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReportOrig.getConnection());
		    	
	    	
    		   JRDocxExporter exporter = new JRDocxExporter();
    		   ByteArrayOutputStream output = new ByteArrayOutputStream();    
    		   exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    		   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
    		   
    		   
    		   exporter.exportReport(); 
    		   InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	           amedia = new AMedia("ReportWord", "doc", "application/msword", mediais);

		    }else if("CSV".equalsIgnoreCase(format)) {
		    
		    	
		    	jasperPrint = JasperFillManager.fillReport(is, param, ConnectionReportOrig.getConnection());

		    	
				ByteArrayOutputStream output = new ByteArrayOutputStream();
		    	JRCsvExporter exporter = new JRCsvExporter();
		    	exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
		    	exporter.exportReport();
	            InputStream mediais = new ByteArrayInputStream(output.toByteArray());
	            amedia = new AMedia("ReportCSV", "csv", "text/csv", mediais);	
		    }
		    Iframe iframe = (Iframe) win.getFellow("iframe");
            iframe.setContent(amedia);
		}
		catch (JRException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }
	}
}
