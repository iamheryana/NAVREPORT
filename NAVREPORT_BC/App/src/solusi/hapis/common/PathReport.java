package solusi.hapis.common;




public class PathReport {
	//private final static String runningON = "TEST";
	//private final static String runningON = "LIVE";
	private final static String runningON = "LIVE-41";
	
	
	private String salesVSCOGS;
	private String salesVSCOGSLastYear;
	private String pnlKonsolidasi;
	private String neracaKonsolidasi;
	private String subRptAccounting;
	private String subRptAccountingPreprinted;
	private String subRptAccountingPenjualan;
	private String prosesRekonPPN;
	private String bankStatement;
	private String subRptLogitic;
	private String subRptFinance;
	private String subRptSales;
	private String SubRptSalesHistorical;
	private String subRptMarkom;
	private String principalInfoRebateHoneywell;
	private String hasilCompareInvoiceRebate;
	private String principalInfoRebateHoneywellFromDisti;
	private String kksoKonsolidasi;
	private String hasilSO;
	private String hasilSOTemuan;
	private String hasilSOAkhir;
	private String invoiceLunasNAV;
	private String PPNRegister ;
	private String rumusanOutPOCashFlow;
	private String contohFormatHasilStockOpname;
	private String preprintInvSATINDO;
	private String hasilMergeInvSATINDO;
	
	public PathReport(String timestamp){
		if(runningON.equals("LIVE")){
			setSalesVSCOGS("/home/hapis/SalesVSCOGS"+timestamp+".xls");
			setSalesVSCOGSLastYear("/home/hapis/SalesVSCOGSLastYear"+timestamp+".xls");
			setBankStatement("/home/hapis/BackStatement"+timestamp+".xls");
			setKksoKonsolidasi("/home/hapis/KKSOKonsolidasi"+timestamp+".xls");
			
			setPnlKonsolidasi("/home/hapis/PNLKonsolidasi.xls");
			setNeracaKonsolidasi("/home/hapis/NERACAKonsolidasi.xls");
			
			setPrincipalInfoRebateHoneywell("/home/hapis/PrincipalInfoRebateHoneywell"+timestamp+".xls");
			setPrincipalInfoRebateHoneywellFromDisti("/home/hapis/PrincipalInfoRebateHoneywellFromDisti"+timestamp+".xls");
			
			setHasilSO("/home/hapis/HasilSO"+timestamp+".xls");
			setHasilSOTemuan("/home/hapis/HasilSOTemuan"+timestamp+".xls");
			setHasilSOAkhir("/home/hapis/HasilSOAkhir"+timestamp+".xls");
			
			setInvoiceLunasNAV("/home/hapis/InvoiceLunasNAV"+timestamp+".xls");
			
			setPPNRegister("/home/hapis/PPNRegister"+timestamp+".xls");
			
			setPreprintInvSATINDO("/home/hapis/PrePrintSATINDO"+timestamp+".pdf");
			
			setHasilMergeInvSATINDO("/home/hapis/InvoiceFP"+timestamp+".pdf");
		} else {
			if(runningON.equals("LIVE-41")){
				setSalesVSCOGS("C:\\Tomcat\\Reports\\SalesVSCOGS"+timestamp+".xls");
				setSalesVSCOGSLastYear("C:\\Tomcat\\Reports\\SalesVSCOGSLastYear"+timestamp+".xls");
				setBankStatement("C:\\Tomcat\\Reports\\BackStatement"+timestamp+".xls");
				setKksoKonsolidasi("C:\\Tomcat\\Reports\\KKSOKonsolidasi"+timestamp+".xls");
				
				setPnlKonsolidasi("PNLKonsolidasi.xls");
				setNeracaKonsolidasi("NERACAKonsolidasi.xls");

				setPrincipalInfoRebateHoneywell("C:\\Tomcat\\Reports\\PrincipalInfoRebateHoneywell"+timestamp+".xls");
				setPrincipalInfoRebateHoneywellFromDisti("C:\\Tomcat\\Reports\\PrincipalInfoRebateHoneywellFromDisti"+timestamp+".xls");
				setHasilSO("C:\\Tomcat\\Reports\\HasilSO"+timestamp+".xls");
				setHasilSOTemuan("C:\\Tomcat\\Reports\\HasilSOTemuan"+timestamp+".xls");
				setHasilSOAkhir("C:\\Tomcat\\Reports\\HasilSOAkhir"+timestamp+".xls");
				
				setInvoiceLunasNAV("C:\\Tomcat\\Reports\\InvoiceLunasNAV"+timestamp+".xls");
				
				setPPNRegister("C:\\Tomcat\\Reports\\PPNRegister"+timestamp+".xls");
		
				setPreprintInvSATINDO("C:\\Tomcat\\Reports\\PrePrintSATINDO"+timestamp+".pdf");
		
				setHasilMergeInvSATINDO("C:\\Tomcat\\Reports\\InvoiceFP"+timestamp+".pdf");
				
				
			}else{
		
				setSalesVSCOGS("F:\\SalesVSCOGS"+timestamp+".xls");
				setSalesVSCOGSLastYear("F:\\SalesVSCOGSLastYear"+timestamp+".xls");
				setBankStatement("F:\\BackStatement"+timestamp+".xls");
				setKksoKonsolidasi("F:\\KKSOKonsolidasi"+timestamp+".xls");
				
				
				setPnlKonsolidasi("PNLKonsolidasi.xls");
				setNeracaKonsolidasi("NERACAKonsolidasi.xls");
			
				setPrincipalInfoRebateHoneywell("F:\\PrincipalInfoRebateHoneywell"+timestamp+".xls");
				setPrincipalInfoRebateHoneywellFromDisti("F:\\PrincipalInfoRebateHoneywellFromDisti"+timestamp+".xls");
				setHasilSO("F:\\HasilSO"+timestamp+".xls");
				setHasilSOTemuan("F:\\HasilSOTemuan"+timestamp+".xls");
				setHasilSOAkhir("F:\\HasilSOAkhir"+timestamp+".xls");
				
				setInvoiceLunasNAV("F:\\InvoiceLunasNAV"+timestamp+".xls");
				
				
				setPPNRegister("F:\\PPNRegister"+timestamp+".xls");
				
				setPreprintInvSATINDO("F:\\PrePrintSATINDO"+timestamp+".pdf");
				
				setHasilMergeInvSATINDO("F:\\InvoiceFP"+timestamp+".pdf");
			}
		}
	}
	
	public PathReport(){
		if(runningON.equals("LIVE")){
			setPnlKonsolidasi("/home/hapis/PNLKonsolidasi.xls");
			setNeracaKonsolidasi("/home/hapis/NERACAKonsolidasi.xls");
			setProsesRekonPPN("/home/hapis/HasilRekonsiliasiPPNKeluaran.xls");
			setHasilCompareInvoiceRebate("/home/hapis/HasilCompareInvoiceRebate.xls");
			setPnlKonsolidasi("/home/hapis/PNLKonsolidasi.xls");
			
			setRumusanOutPOCashFlow("/var/lib/tomcat7/webapps/NAVREPORT/WEB-INF/pages/finance/RumusanOutPOCashFlow.xls");
			
			setContohFormatHasilStockOpname("/var/lib/tomcat7/webapps/NAVREPORT/WEB-INF/pages/accounting/ContohFormatHasilStockOpname.xls");
			
			
			setSubRptAccounting("/var/lib/tomcat7/webapps/NAVREPORT/WEB-INF/classes/solusi/hapis/webui/reports/accounting/");
			setSubRptAccountingPreprinted("/var/lib/tomcat7/webapps/NAVREPORT/WEB-INF/classes/solusi/hapis/webui/reports/accounting/preprinted");
			setSubRptAccountingPenjualan("/var/lib/tomcat7/webapps/NAVREPORT/WEB-INF/classes/solusi/hapis/webui/reports/accounting/penjualan");
			
			setSubRptFinance("/var/lib/tomcat7/webapps/NAVREPORT/WEB-INF/classes/solusi/hapis/webui/reports/finance/");			
			setSubRptLogitic("/var/lib/tomcat7/webapps/NAVREPORT/WEB-INF/classes/solusi/hapis/webui/reports/logistic/");
			setSubRptSales("/var/lib/tomcat7/webapps/NAVREPORT/WEB-INF/classes/solusi/hapis/webui/reports/sales/");
			setSubRptSalesHistorical("/var/lib/tomcat7/webapps/NAVREPORT/WEB-INF/classes/solusi/hapis/webui/reports/sales/Historical/");
			setSubRptMarkom("/var/lib/tomcat7/webapps/NAVREPORT/WEB-INF/classes/solusi/hapis/webui/reports/markom/");
			
		} else {
			if(runningON.equals("LIVE-41")){
				setPnlKonsolidasi("PNLKonsolidasi.xls");
				setNeracaKonsolidasi("NERACAKonsolidasi.xls");
				setProsesRekonPPN("HasilRekonsiliasiPPNKeluaran.xls");
				setHasilCompareInvoiceRebate("HasilCompareInvoiceRebate.xls");
				
				setRumusanOutPOCashFlow("C:\\Tomcat\\apache-tomcat-7.0.70-windows-x64\\apache-tomcat-7.0.70\\webapps\\NAVREPORT\\WEB-INF\\pages\\finance\\RumusanOutPOCashFlow.xls");
				setContohFormatHasilStockOpname("C:\\Tomcat\\apache-tomcat-7.0.70-windows-x64\\apache-tomcat-7.0.70\\webapps\\NAVREPORT\\WEB-INF\\pages\\accounting\\ContohFormatHasilStockOpname.xls");
				
				
				setSubRptAccounting("C:\\Tomcat\\apache-tomcat-7.0.70-windows-x64\\apache-tomcat-7.0.70\\webapps\\NAVREPORT\\WEB-INF\\classes\\solusi\\hapis\\webui\\reports\\accounting\\");
				setSubRptAccountingPreprinted("C:\\Tomcat\\apache-tomcat-7.0.70-windows-x64\\apache-tomcat-7.0.70\\webapps\\NAVREPORT\\WEB-INF\\classes\\solusi\\hapis\\webui\\reports\\accounting\\preprinted\\");
				setSubRptAccountingPenjualan("C:\\Tomcat\\apache-tomcat-7.0.70-windows-x64\\apache-tomcat-7.0.70\\webapps\\NAVREPORT\\WEB-INF\\classes\\solusi\\hapis\\webui\\reports\\accounting\\penjualan\\");
				
				setSubRptFinance("C:\\Tomcat\\apache-tomcat-7.0.70-windows-x64\\apache-tomcat-7.0.70\\webapps\\NAVREPORT\\WEB-INF\\classes\\solusi\\hapis\\webui\\reports\\finance\\");
				setSubRptLogitic("C:\\Tomcat\\apache-tomcat-7.0.70-windows-x64\\apache-tomcat-7.0.70\\webapps\\NAVREPORT\\WEB-INF\\classes\\solusi\\hapis\\webui\\reports\\logistic\\");
				setSubRptSales("C:\\Tomcat\\apache-tomcat-7.0.70-windows-x64\\apache-tomcat-7.0.70\\webapps\\NAVREPORT\\WEB-INF\\classes\\solusi\\hapis\\webui\\reports\\sales\\");
				setSubRptSalesHistorical("C:\\Tomcat\\apache-tomcat-7.0.70-windows-x64\\apache-tomcat-7.0.70\\webapps\\NAVREPORT\\WEB-INF\\classes\\solusi\\hapis\\webui\\reports\\sales\\Historical\\");
				setSubRptMarkom("C:\\Tomcat\\apache-tomcat-7.0.70-windows-x64\\apache-tomcat-7.0.70\\webapps\\NAVREPORT\\WEB-INF\\classes\\solusi\\hapis\\webui\\reports\\markom\\");
				
				
			} else {
			
				setPnlKonsolidasi("PNLKonsolidasi.xls");
				setNeracaKonsolidasi("NERACAKonsolidasi.xls");
				setProsesRekonPPN("HasilRekonsiliasiPPNKeluaran.xls");
				setHasilCompareInvoiceRebate("HasilCompareInvoiceRebate.xls");
				
				setRumusanOutPOCashFlow("F:\\ERP\\Kosong\\NAVREPORT\\NAVREPORT\\NAVREPORT_BC\\App\\web\\WEB-INF\\pages\\finance\\RumusanOutPOCashFlow.xls");
				setContohFormatHasilStockOpname("F:\\ERP\\Kosong\\NAVREPORT\\NAVREPORT\\NAVREPORT_BC\\App\\web\\WEB-INF\\pages\\accounting\\ContohFormatHasilStockOpname.xls");
				
				
				setSubRptAccounting("F:\\ERP\\Kosong\\NAVREPORT\\NAVREPORT\\NAVREPORT_BC\\App\\src\\solusi\\hapis\\webui\\reports\\accounting\\");
				setSubRptAccountingPreprinted("F:\\ERP\\Kosong\\NAVREPORT\\NAVREPORT\\NAVREPORT_BC\\App\\src\\solusi\\hapis\\webui\\reports\\accounting\\preprinted\\");
				setSubRptAccountingPenjualan("F:\\ERP\\Kosong\\NAVREPORT\\NAVREPORT\\NAVREPORT_BC\\App\\src\\solusi\\hapis\\webui\\reports\\accounting\\penjualan\\");
				
				setSubRptFinance("F:\\ERP\\Kosong\\NAVREPORT\\NAVREPORT\\NAVREPORT_BC\\App\\src\\solusi\\hapis\\webui\\reports\\finance\\");
				setSubRptLogitic("F:\\ERP\\Kosong\\NAVREPORT\\NAVREPORT\\NAVREPORT_BC\\App\\src\\solusi\\hapis\\webui\\reports\\logistic\\");
				setSubRptSales("F:\\ERP\\Kosong\\NAVREPORT\\NAVREPORT\\NAVREPORT_BC\\App\\src\\solusi\\hapis\\webui\\reports\\sales\\");
				setSubRptSalesHistorical("F:\\ERP\\Kosong\\NAVREPORT_BC\\NAVREPORT\\App\\src\\solusi\\hapis\\webui\\reports\\sales\\Historical\\");
				setSubRptMarkom("F:\\ERP\\Kosong\\NAVREPORT\\NAVREPORT\\NAVREPORT_BC\\App\\src\\solusi\\hapis\\webui\\reports\\markom\\");
				
			}
		}
	}
	
	
	
//
	



	public String getKksoKonsolidasi() {
		return kksoKonsolidasi;
	}

	public String getInvoiceLunasNAV() {
		return invoiceLunasNAV;
	}

	public void setInvoiceLunasNAV(String invoiceLunasNAV) {
		this.invoiceLunasNAV = invoiceLunasNAV;
	}

	public void setKksoKonsolidasi(String kksoKonsolidasi) {
		this.kksoKonsolidasi = kksoKonsolidasi;
	}
	
	public String getSubRptSales() {
		return subRptSales;
	}

	public void setSubRptSales(String subRptSales) {
		this.subRptSales = subRptSales;
	}

	public String getSubRptLogitic() {
		return subRptLogitic;
	}

	public void setSubRptLogitic(String subRptLogitic) {
		this.subRptLogitic = subRptLogitic;
	}

	public String getBankStatement() {
		return bankStatement;
	}

	public void setBankStatement(String bankStatement) {
		this.bankStatement = bankStatement;
	}

	public String getProsesRekonPPN() {
		return prosesRekonPPN;
	}

	public void setProsesRekonPPN(String prosesRekonPPN) {
		this.prosesRekonPPN = prosesRekonPPN;
	}

	public String getSubRptAccounting() {
		return subRptAccounting;
	}

	public void setSubRptAccounting(String subRptAccounting) {
		this.subRptAccounting = subRptAccounting;
	}

	public String getSalesVSCOGS() {
		return salesVSCOGS;
	}

	public void setSalesVSCOGS(String salesVSCOGS) {
		this.salesVSCOGS = salesVSCOGS;
	}

	public String getPnlKonsolidasi() {
		return pnlKonsolidasi;
	}
	
	public void setPnlKonsolidasi(String pnlKonsolidasi) {
		this.pnlKonsolidasi = pnlKonsolidasi;
	}
	
	public String getNeracaKonsolidasi() {
		return neracaKonsolidasi;
	}
	
	public void setNeracaKonsolidasi(String neracaKonsolidasi) {
		this.neracaKonsolidasi = neracaKonsolidasi;
	}

	public String getSalesVSCOGSLastYear() {
		return salesVSCOGSLastYear;
	}

	public void setSalesVSCOGSLastYear(String salesVSCOGSLastYear) {
		this.salesVSCOGSLastYear = salesVSCOGSLastYear;
	}

	public String getPrincipalInfoRebateHoneywell() {
		return principalInfoRebateHoneywell;
	}

	public void setPrincipalInfoRebateHoneywell(String principalInfoRebateHoneywell) {
		this.principalInfoRebateHoneywell = principalInfoRebateHoneywell;
	}

	public String getHasilCompareInvoiceRebate() {
		return hasilCompareInvoiceRebate;
	}

	public void setHasilCompareInvoiceRebate(String hasilCompareInvoiceRebate) {
		this.hasilCompareInvoiceRebate = hasilCompareInvoiceRebate;
	}

	public String getPrincipalInfoRebateHoneywellFromDisti() {
		return principalInfoRebateHoneywellFromDisti;
	}

	public void setPrincipalInfoRebateHoneywellFromDisti(
			String principalInfoRebateHoneywellFromDisti) {
		this.principalInfoRebateHoneywellFromDisti = principalInfoRebateHoneywellFromDisti;
	}

	public String getHasilSO() {
		return hasilSO;
	}

	public void setHasilSO(String hasilSO) {
		this.hasilSO = hasilSO;
	}

	public String getHasilSOTemuan() {
		return hasilSOTemuan;
	}

	public void setHasilSOTemuan(String hasilSOTemuan) {
		this.hasilSOTemuan = hasilSOTemuan;
	}

	public String getHasilSOAkhir() {
		return hasilSOAkhir;
	}

	public void setHasilSOAkhir(String hasilSOAkhir) {
		this.hasilSOAkhir = hasilSOAkhir;
	}

	public String getSubRptFinance() {
		return subRptFinance;
	}

	public void setSubRptFinance(String subRptFinance) {
		this.subRptFinance = subRptFinance;
	}

	public String getPPNRegister() {
		return PPNRegister;
	}

	public void setPPNRegister(String pPNRegister) {
		PPNRegister = pPNRegister;
	}

	public String getRumusanOutPOCashFlow() {
		return rumusanOutPOCashFlow;
	}

	public void setRumusanOutPOCashFlow(String rumusanOutPOCashFlow) {
		this.rumusanOutPOCashFlow = rumusanOutPOCashFlow;
	}

	public String getContohFormatHasilStockOpname() {
		return contohFormatHasilStockOpname;
	}

	public void setContohFormatHasilStockOpname(String contohFormatHasilStockOpname) {
		this.contohFormatHasilStockOpname = contohFormatHasilStockOpname;
	}

	public String getSubRptMarkom() {
		return subRptMarkom;
	}

	public void setSubRptMarkom(String subRptMarkom) {
		this.subRptMarkom = subRptMarkom;
	}

	public String getPreprintInvSATINDO() {
		return preprintInvSATINDO;
	}

	public void setPreprintInvSATINDO(String preprintInvSATINDO) {
		this.preprintInvSATINDO = preprintInvSATINDO;
	}

	public String getHasilMergeInvSATINDO() {
		return hasilMergeInvSATINDO;
	}

	public void setHasilMergeInvSATINDO(String hasilMergeInvSATINDO) {
		this.hasilMergeInvSATINDO = hasilMergeInvSATINDO;
	}

	public String getSubRptAccountingPreprinted() {
		return subRptAccountingPreprinted;
	}

	public void setSubRptAccountingPreprinted(String subRptAccountingPreprinted) {
		this.subRptAccountingPreprinted = subRptAccountingPreprinted;
	}

	public String getSubRptSalesHistorical() {
		return SubRptSalesHistorical;
	}

	public void setSubRptSalesHistorical(String subRptSalesHistorical) {
		SubRptSalesHistorical = subRptSalesHistorical;
	}

	public String getSubRptAccountingPenjualan() {
		return subRptAccountingPenjualan;
	}

	public void setSubRptAccountingPenjualan(String subRptAccountingPenjualan) {
		this.subRptAccountingPenjualan = subRptAccountingPenjualan;
	}
	
	
	

	
}
