package solusi.hapis.backend.parameter.service;

import java.util.Date;
import java.util.List;

public interface SelectQueryNavReportService {
	public List<Object[]> QuerySalesperson();
	
	public List<Object[]> QueryBulan();
	public List<Object[]> QueryBulanTQS();
	
	public List<Object[]> CekPeriodeClosingCosting(String masa, String tahun);
	
	public String callDownloadInvoiceLunas(String processId);
	
	public String callDeleteInvoiceLunasTemp(String processId);
	
	public String callDownloadInvoiceLunasSatindo(String processId);
	
	public String callProsesKomisiSatindo(String masa, String tahun, String status);
	
	public String callDeleteInvoiceSatindoTemp(String processId);
	
	public String callCekPeriodeClosing(Date pTanggal);
}
