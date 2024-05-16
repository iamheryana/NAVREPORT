package solusi.hapis.backend.navbi.service.impl;


import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T16RekapCostingDAO;
import solusi.hapis.backend.navbi.model.T16RekapCosting;
import solusi.hapis.backend.navbi.service.T16RekapCostingService;

public class T16RekapCostingServiceImpl implements T16RekapCostingService {
	private T16RekapCostingDAO t16RekapCostingDAO;
	
	public T16RekapCostingDAO getT16RekapCostingDAO() {
		return t16RekapCostingDAO;
	}

	public void setT16RekapCostingDAO(T16RekapCostingDAO t16RekapCostingDAO) {
		this.t16RekapCostingDAO = t16RekapCostingDAO;
	}

	@Override
	public void saveOrUpdate(T16RekapCosting t16RekapCosting) {
		t16RekapCostingDAO.saveOrUpdate(t16RekapCosting);
		
	}

	@Override
	public void update(T16RekapCosting t16RekapCosting) {
		t16RekapCostingDAO.update(t16RekapCosting);
		
	}

	@Override
	public void save(T16RekapCosting t16RekapCosting) {
		t16RekapCostingDAO.save(t16RekapCosting);
		
	}

	@Override
	public void delete(T16RekapCosting t16RekapCosting) {
		t16RekapCostingDAO.delete(t16RekapCosting);
		
	}

	@Override
	public List<T16RekapCosting> getListT16RekapCosting(
			Map<Object, Object> parameterInput) {
		return t16RekapCostingDAO.getListT16RekapCosting(parameterInput);
	}
	
	
}
