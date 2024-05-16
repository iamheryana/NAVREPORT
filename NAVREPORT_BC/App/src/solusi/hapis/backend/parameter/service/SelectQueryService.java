package solusi.hapis.backend.parameter.service;

import java.util.List;

public interface SelectQueryService {
	public List<Object[]> QueryCabang();
	public List<Object[]> QueryCabang2();
	public List<Object[]> QueryProductGroup();
	public List<Object[]> QueryPrincipal();
	public List<Object[]> QueryPrincipalInfo();
	public List<Object[]> QueryItemCategory();
	public List<Object[]> QueryLocation();
	public List<Object[]> QuerySalesman();
	public List<Object[]> QuerySalesmanCustomer();
	public List<Object[]> QuerySalesmanActive();
	public List<Object[]> QuerySalesmanName();
	public List<Object[]> QueryProductGroupLeadTime();
	public List<Object[]> QueryVendorOTP();
	public List<Object[]> QueryLocationSPB();
	public List<Object[]> QueryResponsibilityTO();
	public List<Object[]> QueryLocationFA();
	public List<Object[]> QueryProject();
	public List<Object[]> QueryFAClass();
	public List<Object[]> QuerySalesmanForContact();
	public List<Object[]> QueryFeedbackPollingQst(long webinarID);
	public List<Object[]> QueryKendaraan();
	public List<Object[]> QueryBankAccount();
	public List<Object[]> QuerySalepersonCosting();	
	public List<Object[]> QueryBulanForCosting();
	public List<Object[]> QueryBulanForTQS();
	public List<Object[]> QueryPeriodeClosingForCosting(String masa, String tahun);	
	public List<Object[]> QueryAssignUserIdPO();
	public List<Object[]> QueryNamaReportGeneratorPNL();
	public List<Object[]> QueryNamaReportGeneratorNERACA();
	public List<Object[]> QueryNamaKaryawan();
	public List<Object[]> QuerySektorIndustri();
	public List<Object[]> QueryApplication();
	public List<Object[]> QueryStaffFinanceUntukCosting();
	public List<Object[]> QueryDCName();
	public List<Object[]> QueryCustGroup();
	
	
}
