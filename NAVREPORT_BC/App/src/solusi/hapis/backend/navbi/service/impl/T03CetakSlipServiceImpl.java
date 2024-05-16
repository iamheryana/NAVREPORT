package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T03CetakSlipDAO;
import solusi.hapis.backend.navbi.model.T03CetakSlip;
import solusi.hapis.backend.navbi.service.T03CetakSlipService;

public class T03CetakSlipServiceImpl implements T03CetakSlipService{
	T03CetakSlipDAO t03CetakSlipDAO;

	public T03CetakSlipDAO getT03CetakSlipDAO() {
		return t03CetakSlipDAO;
	}

	public void setT03CetakSlipDAO(T03CetakSlipDAO t03CetakSlipDAO) {
		this.t03CetakSlipDAO = t03CetakSlipDAO;
	}

	@Override
	public void saveOrUpdate(T03CetakSlip t03CetakSlip) {
		t03CetakSlipDAO.saveOrUpdate(t03CetakSlip);		
	}

	@Override
	public void update(T03CetakSlip t03CetakSlip) {
		t03CetakSlipDAO.update(t03CetakSlip);
	}

	@Override
	public void save(T03CetakSlip t03CetakSlip) {
		t03CetakSlipDAO.save(t03CetakSlip);		
	}

	@Override
	public void delete(T03CetakSlip t03CetakSlip) {
		t03CetakSlipDAO.delete(t03CetakSlip);		
	}

	@Override
	public List<T03CetakSlip> getListT03CetakSlip(Map<Object, Object> parameterInput) {
		return t03CetakSlipDAO.getListT03CetakSlip(parameterInput);
	}
	
	
}
