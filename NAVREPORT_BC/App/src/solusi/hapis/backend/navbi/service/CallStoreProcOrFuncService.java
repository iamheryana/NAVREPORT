package solusi.hapis.backend.navbi.service;

import java.math.BigDecimal;


public interface CallStoreProcOrFuncService {
	public String callUploadWebinarEvent(String processId, String UserId);
	public String callUploadWebinarFeedback(String processId, String webinarId, String UserId);
	public String callUploadWebinarPolling(String processId, String UserId);
	public String callUploadWebinarSurvey(String processId, String webinarId, String UserId);
	public String callUploadWebinarQA(String processId, String UserId);	
	public String callEFakturRekon(String processId, String Company, String JnsPPN, String TglFrom, String TglUpto, String UserId, String Action);
	public String callSalesRevenue(String processId, String tglFrom, String tglUpto,  String company, String action);	
	public String callGetPNLForNeraca(String processId, int yearPeriode, int monthPeriode,  String cabang, BigDecimal pembagi, String UserId, String action);	
	public String callSalesCOGSCorrection(String processId, String cabang, String tglFrom, String tglUpto,   String action);	
	public String callGrossSalesMargin(String processId, String tglAwalTahun, String tglFrom, String tglUpto,  String action);	
	public String callDownloadInvoiceLunas(String processId, String tglFrom, String tglUpto,  String jenisCosting, String UserId);	
	public String callProsesKomisiSatindo(String processId, String masa, String tahun,  String status);	
	public String callArusKasPiutang(String processId, String tglFrom, String tglUpto, String company, String action);	
	public String callReportPNLManagement(String processId, String tglFrom, String tglUpto, String cabang, BigDecimal pembagi,  String action);
	public String callReportNERACAManagement(String processId, String tglFrom, String tglUpto, String cabang, BigDecimal pembagi, String userID, String action);	
	public String callCOGSAnalysis(String processId, String cabang, String tglFrom, String tglUpto, String action);	
	public String callReportGeneratorPNL(String processId, String company, String tglFrom, String tglUpto, String namaReport, String tipeKolom,  String action);	
	public String callReportGeneratorNERACA(String processId, String company, String tglFrom, String tglUpto, String namaReport, String tipeKolom,  String action);	
	public String callCekStatusKomisi(String processId, String sales, String extDocNo, String customer, String noBSO, String noInvoice,  String action);	
	public String callSalesMarginAnalysis(String processId, String periodeFrom, String periodeUpto, String action);	
	public String callOutstandingSO(String processId, String tglFrom, String tglUpto, String company, String cabang, String action);	
	public String callOutstandingUM(String processId, String cabang, String action);	
	public String callSyncAReport(String kodeReport);
	public String callProsesGenerateSROSO(String modeReport);
	public String callGrossMarginSalesman(String processId, String tglFrom, String tglUpto,  String action);	
	public String callSyncAReportManual(String kodeReport);	
	
}
