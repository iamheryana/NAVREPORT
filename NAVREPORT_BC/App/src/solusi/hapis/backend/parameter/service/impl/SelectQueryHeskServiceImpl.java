package solusi.hapis.backend.parameter.service.impl;

import solusi.hapis.backend.parameter.dao.SelectQueryHeskDAO;
import solusi.hapis.backend.parameter.service.SelectQueryHeskService;

public class SelectQueryHeskServiceImpl implements SelectQueryHeskService{
	private SelectQueryHeskDAO selectQueryHeskDAO ;

	public SelectQueryHeskDAO getSelectQueryHeskDAO() {
		return selectQueryHeskDAO;
	}
	
	public void setSelectQueryHeskDAO(SelectQueryHeskDAO selectQueryHeskDAO) {
		this.selectQueryHeskDAO = selectQueryHeskDAO;
	}

	@Override
	public String callGetHistory(String processId) {
		return selectQueryHeskDAO.callGetHistory(processId);
	}
}
