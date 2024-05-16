package solusi.hapis.backend.parameter.service.impl;

import java.util.List;

import solusi.hapis.backend.parameter.dao.SelectQueryDAO;
import solusi.hapis.backend.parameter.service.SelectQueryService;

public class SelectQueryServiceImpl implements SelectQueryService{
	private SelectQueryDAO selectQueryDAO ;
	
	
	public SelectQueryDAO getSelectQueryDAO() {
		return selectQueryDAO;
	}


	public void setSelectQueryDAO(SelectQueryDAO selectQueryDAO) {
		this.selectQueryDAO = selectQueryDAO;
	}


	@Override
	public List<Object[]> QueryCabang() {
		return selectQueryDAO.QueryCabang();
	}


	@Override
	public List<Object[]> QueryProductGroup() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryProductGroup();
	}


	@Override
	public List<Object[]> QueryPrincipal() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryPrincipal();
	}


	@Override
	public List<Object[]> QueryCabang2() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryCabang2();
	}


	@Override
	public List<Object[]> QueryItemCategory() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryItemCategory();
	}


	@Override
	public List<Object[]> QueryLocation() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryLocation();
	}


	@Override
	public List<Object[]> QuerySalesman() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QuerySalesman();
	}

	@Override
	public List<Object[]> QuerySalesmanName() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QuerySalesmanName();
	}
	
	@Override
	public List<Object[]> QueryPrincipalInfo() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryPrincipalInfo();
	}


	@Override
	public List<Object[]> QueryProductGroupLeadTime() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryProductGroupLeadTime();
	}


	@Override
	public List<Object[]> QueryVendorOTP() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryVendorOTP();
	}


	@Override
	public List<Object[]> QueryLocationSPB() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryLocationSPB();
	}


	@Override
	public List<Object[]> QueryResponsibilityTO() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryResponsibilityTO();
	}


	@Override
	public List<Object[]> QueryLocationFA() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryLocationFA();
	}


	@Override
	public List<Object[]> QueryProject() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryProject();
	}


	@Override
	public List<Object[]> QueryFAClass() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryFAClass();
	}


	@Override
	public List<Object[]> QuerySalesmanActive() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QuerySalesmanActive();
	}


	@Override
	public List<Object[]> QuerySalesmanCustomer() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QuerySalesmanCustomer();
	}


	@Override
	public List<Object[]> QuerySalesmanForContact() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QuerySalesmanForContact();
	}


	@Override
	public List<Object[]> QueryFeedbackPollingQst(long webinarID) {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryFeedbackPollingQst(webinarID);
	}


	@Override
	public List<Object[]> QueryKendaraan() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryKendaraan();
	}


	@Override
	public List<Object[]> QueryBankAccount() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryBankAccount();
	}


	@Override
	public List<Object[]> QuerySalepersonCosting() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QuerySalepersonCosting();
	}


	@Override
	public List<Object[]> QueryBulanForCosting() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryBulanForCosting();
	}


	@Override
	public List<Object[]> QueryBulanForTQS() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryBulanForTQS();
	}


	@Override
	public List<Object[]> QueryPeriodeClosingForCosting(String masa,
			String tahun) {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryPeriodeClosingForCosting(masa, tahun);
	}


	@Override
	public List<Object[]> QueryAssignUserIdPO() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryAssignUserIdPO();
	}


	@Override
	public List<Object[]> QueryNamaReportGeneratorPNL() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryNamaReportGeneratorPNL();
	}


	@Override
	public List<Object[]> QueryNamaReportGeneratorNERACA() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryNamaReportGeneratorNERACA();
	}


	@Override
	public List<Object[]> QueryNamaKaryawan() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryNamaKaryawan();
	}


	@Override
	public List<Object[]> QuerySektorIndustri() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QuerySektorIndustri();
	}


	@Override
	public List<Object[]> QueryApplication() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryApplication();
	}


	@Override
	public List<Object[]> QueryStaffFinanceUntukCosting() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryStaffFinanceUntukCosting();
	}


	@Override
	public List<Object[]> QueryDCName() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryDCName();
	}


	@Override
	public List<Object[]> QueryCustGroup() {
		// TODO Auto-generated method stub
		return selectQueryDAO.QueryCustGroup();
	}

}
