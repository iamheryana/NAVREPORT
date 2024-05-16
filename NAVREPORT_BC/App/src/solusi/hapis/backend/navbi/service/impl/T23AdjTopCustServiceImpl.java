package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T23AdjTopCustDAO;
import solusi.hapis.backend.navbi.model.T23AdjTopCust;
import solusi.hapis.backend.navbi.service.T23AdjTopCustService;

public class T23AdjTopCustServiceImpl implements T23AdjTopCustService{
	T23AdjTopCustDAO t23AdjTopCustDAO;

	public T23AdjTopCustDAO getT23AdjTopCustDAO() {
		return t23AdjTopCustDAO;
	}

	public void setT23AdjTopCustDAO(T23AdjTopCustDAO t23AdjTopCustDAO) {
		this.t23AdjTopCustDAO = t23AdjTopCustDAO;
	}

	@Override
	public void saveOrUpdate(T23AdjTopCust t23AdjTopCust) {
		t23AdjTopCustDAO.saveOrUpdate(t23AdjTopCust);		
	}

	@Override
	public void update(T23AdjTopCust t23AdjTopCust) {
		t23AdjTopCustDAO.update(t23AdjTopCust);
	}

	@Override
	public void save(T23AdjTopCust t23AdjTopCust) {
		t23AdjTopCustDAO.save(t23AdjTopCust);		
	}

	@Override
	public void delete(T23AdjTopCust t23AdjTopCust) {
		t23AdjTopCustDAO.delete(t23AdjTopCust);		
	}

	@Override
	public List<T23AdjTopCust> getListT23AdjTopCust(Map<Object, Object> parameterInput) {
		return t23AdjTopCustDAO.getListT23AdjTopCust(parameterInput);
	}
	
	
}
