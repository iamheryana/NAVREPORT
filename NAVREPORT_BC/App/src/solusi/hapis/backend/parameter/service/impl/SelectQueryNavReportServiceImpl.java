package solusi.hapis.backend.parameter.service.impl;

import java.util.Date;
import java.util.List;

import solusi.hapis.backend.parameter.dao.SelectQueryNavReportDAO;
import solusi.hapis.backend.parameter.service.SelectQueryNavReportService;

public class SelectQueryNavReportServiceImpl implements SelectQueryNavReportService{
	private SelectQueryNavReportDAO selectQueryNavReportDAO ;
	
	
	public SelectQueryNavReportDAO getSelectQueryNavReportDAO() {
		return selectQueryNavReportDAO;
	}



	public void setSelectQueryNavReportDAO(
			SelectQueryNavReportDAO selectQueryNavReportDAO) {
		this.selectQueryNavReportDAO = selectQueryNavReportDAO;
	}



	@Override
	public List<Object[]> QuerySalesperson() {
		return selectQueryNavReportDAO.QuerySalesperson();
	}



	@Override
	public String callDownloadInvoiceLunas(String processId) {
		return selectQueryNavReportDAO.callDownloadInvoiceLunas(processId);
	}



	@Override
	public List<Object[]> QueryBulan() {
		return selectQueryNavReportDAO.QueryBulan();
	}



	@Override
	public List<Object[]> QueryBulanTQS() {
		return selectQueryNavReportDAO.QueryBulanTQS();
	}



	@Override
	public List<Object[]> CekPeriodeClosingCosting(String masa, String tahun) {
		return selectQueryNavReportDAO.CekPeriodeClosingCosting(masa, tahun);
	}



	@Override
	public String callDeleteInvoiceLunasTemp(String processId) {		
		return selectQueryNavReportDAO.callDeleteInvoiceLunasTemp(processId);
	}



	@Override
	public String callDownloadInvoiceLunasSatindo(String processId) {
		return selectQueryNavReportDAO.callDownloadInvoiceLunasSatindo(processId);
	}



	@Override
	public String callProsesKomisiSatindo(String masa, String tahun,
			String status) {
		return  selectQueryNavReportDAO.callProsesKomisiSatindo(masa, tahun, status);
	}



	@Override
	public String callDeleteInvoiceSatindoTemp(String processId) {
		return selectQueryNavReportDAO.callDeleteInvoiceSatindoTemp(processId);
	}



	@Override
	public String callCekPeriodeClosing(Date pTanggal) {
		return selectQueryNavReportDAO.callCekPeriodeClosing(pTanggal);
	}

}
