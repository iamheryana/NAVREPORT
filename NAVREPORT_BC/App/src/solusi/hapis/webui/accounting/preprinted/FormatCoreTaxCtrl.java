package solusi.hapis.webui.accounting.preprinted;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Textbox;

import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;
import solusi.hapis.webui.reports.util.JReportGeneratorWindow;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class FormatCoreTaxCtrl extends GFCBaseCtrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047990821516249794L;
	
	protected Textbox txtInvFrom;
	protected Textbox txtInvUpto;

	

	private CallStoreProcOrFuncService callStoreProcOrFuncService;


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);  

    	
	}
	
	
	public void onClick$btnSync(Event event) throws InterruptedException, SQLException, ParseException  {
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReportManual("0108011");
		
		Messagebox.show("Sync Sudah Selesai");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onClick$btnOK(Event event) throws InterruptedException {
		
		

		
		String vInvFrom = ".";
		if (StringUtils.isNotEmpty(txtInvFrom.getValue())) {
			vInvFrom = txtInvFrom.getValue();
		} 
		
		String vInvUpto = "ZZZZZZZZZZZZ";
		if (StringUtils.isNotEmpty(txtInvUpto.getValue())) {
			vInvUpto = txtInvUpto.getValue();
		} 
		
		
	
		
		@SuppressWarnings("unused")
		String vSync = callStoreProcOrFuncService.callSyncAReport("0108011");
		
		List<Map> params = new ArrayList<Map>();
		List<String> jasperRpts = new ArrayList<String>();
		String [] vSheetName = new String[10]; 
		
		
		jasperRpts.add("/solusi/hapis/webui/reports/accounting/preprinted/010808_FormatCoreTax.jasper");
		 
	    Map param_HEADER = new HashMap();	
	    param_HEADER.put("InvoiceFrom",  vInvFrom); 
	    param_HEADER.put("InvoiceUpto",  vInvUpto);   		
	    param_HEADER.put("JnsRpt",  "HEADER"); 
	    params.add(param_HEADER);
	    vSheetName[0] = "Faktur";
	    
	    
	    jasperRpts.add("/solusi/hapis/webui/reports/accounting/preprinted/010808_FormatCoreTax.jasper");
		 
	    Map param_DETAIL = new HashMap();	
	    param_DETAIL.put("InvoiceFrom",  vInvFrom); 
	    param_DETAIL.put("InvoiceUpto",  vInvUpto);   		
	    param_DETAIL.put("JnsRpt",  "DETAIL"); 
	    params.add(param_DETAIL);
	    vSheetName[1] = "DetailFaktur";

	    new JReportGeneratorWindow(params, jasperRpts, vSheetName); 
		 
		
	}
	
	public void onClick$btnXML(Event event) throws InterruptedException, TransformerException, ParserConfigurationException  {
		
		//Root Element
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		// Create a new Document
		Document document = builder.newDocument();
		
		// Create root element
		Element root = document.createElement("TaxInvoiceBulk");
		root.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		document.appendChild(root);
		
		Element Lvl_1 = document.createElement("TIN");
		Lvl_1.appendChild(document.createTextNode("0015857733027000"));
		
		root.appendChild(Lvl_1);
		
		
		Element Lvl_InvList = document.createElement("ListOfTaxInvoice");
		root.appendChild(Lvl_InvList);

		
		Element Lvl_AnInv = document.createElement("TaxInvoice");
		Lvl_InvList.appendChild(Lvl_AnInv);
		
		Element Lvl_InvDtl1 = document.createElement("TaxInvoiceDate");
		Lvl_InvDtl1.appendChild(document.createTextNode("2025-01-06"));
		
		Lvl_AnInv.appendChild(Lvl_InvDtl1);
//		    <>
//		      <TaxInvoiceDate>2025-01-06</>
//
//		Element book2 = document.createElement("Program2");
//		book2.appendChild(document.createTextNode("Python Programming"));
//		Element book3 = document.createElement("Program3");
//		book3.appendChild(document.createTextNode("JavaScript"));
//		Element book4 = document.createElement("Program4");
//		book4.appendChild(document.createTextNode("C Programming"));
//		
//		root.appendChild(book2);
//		root.appendChild(book3);
//		root.appendChild(book4);
		/*
document        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Create a new Document
        Document document = builder.newDocument();

        // Create root element
        Element root = document.createElement("library");
        document.appendChild(root);

        // Create book elements and add text content
        Element book1 = document.createElement("Program1");
        book1.appendChild(document.createTextNode("Java Programming"));
        Element book2 = document.createElement("Program2");
        book2.appendChild(document.createTextNode("Python Programming"));
        Element book3 = document.createElement("Program3");
        book3.appendChild(document.createTextNode("JavaScript"));
        Element book4 = document.createElement("Program4");
        book4.appendChild(document.createTextNode("C Programming"));
        root.appendChild(book1);
        root.appendChild(book2);
        root.appendChild(book3);
        root.appendChild(book4);
        
        https://www.geeksforgeeks.org/read-and-write-xml-files-in-java/
		 */
		
		// Write to XML file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		
		DOMSource source = new DOMSource(document);
		
		// Specify your local file path
		StreamResult result = new StreamResult("F:/output.xml");
		transformer.transform(source, result);
		
		System.out.println("XML file created successfully!");
	}
	 
}

