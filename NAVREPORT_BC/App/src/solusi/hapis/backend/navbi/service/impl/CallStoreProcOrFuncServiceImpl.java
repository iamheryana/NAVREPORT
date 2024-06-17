package solusi.hapis.backend.navbi.service.impl;

import java.math.BigDecimal;

import solusi.hapis.backend.navbi.dao.CallStoreProcOrFuncDAO;
import solusi.hapis.backend.navbi.service.CallStoreProcOrFuncService;

public class CallStoreProcOrFuncServiceImpl implements  CallStoreProcOrFuncService{
	CallStoreProcOrFuncDAO callStoreProcOrFuncDAO;
	
	
	public CallStoreProcOrFuncDAO getCallStoreProcOrFuncDAO() {
		return callStoreProcOrFuncDAO;
	}


	public void setCallStoreProcOrFuncDAO(
			CallStoreProcOrFuncDAO callStoreProcOrFuncDAO) {
		this.callStoreProcOrFuncDAO = callStoreProcOrFuncDAO;
	}


	@Override
	public String callUploadWebinarEvent(String processId, String UserId) {
		
		return callStoreProcOrFuncDAO.callUploadWebinarEvent(processId, UserId);
	}


	@Override
	public String callUploadWebinarFeedback(String processId, String webinarId,
			String UserId) {
		
		return callStoreProcOrFuncDAO.callUploadWebinarFeedback(processId, webinarId, UserId);
	}


	@Override
	public String callUploadWebinarPolling(String processId, String UserId) {
		return callStoreProcOrFuncDAO.callUploadWebinarPolling(processId, UserId);
		
	}


	@Override
	public String callEFakturRekon(String processId, String Company,
			String JnsPPN, String TglFrom, String TglUpto, String UserId, String Action) {
		return callStoreProcOrFuncDAO.callEFakturRekon(processId, Company, JnsPPN, TglFrom, TglUpto, UserId, Action);
	}


	@Override
	public String callSalesRevenue(String processId, String tglFrom,
			String tglUpto, String company, String action) {
		return callStoreProcOrFuncDAO.callSalesRevenue(processId, tglFrom, tglUpto, company, action);
	}


	@Override
	public String callUploadWebinarSurvey(String processId, String webinarId,
			String UserId) {
		return callStoreProcOrFuncDAO.callUploadWebinarSurvey(processId, webinarId, UserId);
	}


	@Override
	public String callGetPNLForNeraca(String processId, int yearPeriode,
			int monthPeriode, String cabang, BigDecimal pembagi, String UserId,
			String action) {
		return callStoreProcOrFuncDAO.callGetPNLForNeraca(processId, yearPeriode, monthPeriode, cabang, pembagi, UserId, action);
	}


	@Override
	public String callSalesCOGSCorrection(String processId, String cabang,
			String tglFrom, String tglUpto, String action) {
		return callStoreProcOrFuncDAO.callSalesCOGSCorrection(processId, cabang, tglFrom, tglUpto, action);
	}


	@Override
	public String callGrossSalesMargin(String processId, String tglAwalTahun,
			String tglFrom, String tglUpto, String action) {
		return callStoreProcOrFuncDAO.callGrossSalesMargin(processId, tglAwalTahun, tglFrom, tglUpto, action);
	}


	@Override
	public String callUploadWebinarQA(String processId, String UserId) {
		return callStoreProcOrFuncDAO.callUploadWebinarQA(processId, UserId);
	}


	@Override
	public String callDownloadInvoiceLunas(String processId, String tglFrom,
			String tglUpto, String jenisCosting, String UserId) {
		return callStoreProcOrFuncDAO.callDownloadInvoiceLunas(processId, tglFrom, tglUpto, jenisCosting, UserId);
	}


	@Override
	public String callProsesKomisiSatindo(String processId, String masa,
			String tahun, String status) {
		return callStoreProcOrFuncDAO.callProsesKomisiSatindo(processId, masa, tahun, status);
	}


	@Override
	public String callArusKasPiutang(String processId, String tglFrom,
			String tglUpto, String company, String action) {
		return callStoreProcOrFuncDAO.callArusKasPiutang(processId, tglFrom, tglUpto, company, action);
	}

	@Override
	public String callReportPNLManagement(String processId, String tglFrom,
			String tglUpto, String cabang, BigDecimal pembagi, String action) {
		return callStoreProcOrFuncDAO.callReportPNLManagement(processId, tglFrom, tglUpto, cabang, pembagi, action);
	}


	@Override
	public String callReportNERACAManagement(String processId, String tglFrom,
			String tglUpto, String cabang, BigDecimal pembagi, String userID,
			String action) {
		return callStoreProcOrFuncDAO.callReportNERACAManagement(processId, tglFrom, tglUpto, cabang, pembagi, userID, action);
	}


	@Override
	public String callCOGSAnalysis(String processId, String cabang,
			String tglFrom, String tglUpto, String action) {
		return callStoreProcOrFuncDAO.callCOGSAnalysis(processId, cabang, tglFrom, tglUpto, action);
	}
	
	@Override
	public String callReportGeneratorPNL(String processId, String company,
			String tglFrom, String tglUpto, String namaReport,
			String tipeKolom, String action) {
		return callStoreProcOrFuncDAO.callReportGeneratorPNL(processId, company,
			tglFrom, tglUpto, namaReport,
			tipeKolom, action);
	}

	@Override
	public String callReportGeneratorNERACA(String processId, String company,
			String tglFrom, String tglUpto, String namaReport,
			String tipeKolom, String action) {
		return callStoreProcOrFuncDAO.callReportGeneratorNERACA(processId, company,
			tglFrom, tglUpto, namaReport,
			tipeKolom, action);
	}


	@Override
	public String callCekStatusKomisi(String processId, String sales,
			String extDocNo, String customer, String noBSO, String noInvoice,
			String action) {
		return callStoreProcOrFuncDAO.callCekStatusKomisi(processId, sales, extDocNo, customer, noBSO, noInvoice, action);
	}


	@Override
	public String callSalesMarginAnalysis(String processId, String periodeFrom,
			String periodeUpto, String action) {
		
		return callStoreProcOrFuncDAO.callSalesMarginAnalysis(processId, periodeFrom, periodeUpto, action);
	}


	@Override
	public String callOutstandingSO(String processId, String tglFrom,
			String tglUpto, String company, String cabang, String action) {
		return callStoreProcOrFuncDAO.callOutstandingSO(processId, tglFrom, tglUpto, company, cabang, action);
	}


	@Override
	public String callOutstandingUM(String processId, String cabang,
			String action) {
		return callStoreProcOrFuncDAO.callOutstandingUM(processId, cabang, action);
	}


	@Override
	public String callSyncAReport(String kodeReport) {
		return callStoreProcOrFuncDAO.callSyncAReport(kodeReport);
	}


	@Override
	public String callProsesGenerateSROSO(String modeReport) {

		return callStoreProcOrFuncDAO.callProsesGenerateSROSO(modeReport);	
	}


	@Override
	public String callGrossMarginSalesman(String processId, String tglFrom,
			String tglUpto, String action) {
		return callStoreProcOrFuncDAO.callGrossMarginSalesman(processId, tglFrom, tglUpto, action);
	}


	@Override
	public String callSyncAReportManual(String kodeReport) {
		return callStoreProcOrFuncDAO.callSyncAReportManual(kodeReport);
	}


}
