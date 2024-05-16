package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.T05periodecostingDAO;
import solusi.hapis.backend.tabel.model.T05periodecosting;
import solusi.hapis.backend.tabel.service.T05periodecostingService;

public class T05periodecostingServiceImpl implements T05periodecostingService {
	private T05periodecostingDAO t05periodecostingDAO;
	
	public T05periodecostingDAO getT05periodecostingDAO() {
		return t05periodecostingDAO;
	}

	public void setT05periodecostingDAO(T05periodecostingDAO t05periodecostingDAO) {
		this.t05periodecostingDAO = t05periodecostingDAO;
	}

	@Override
	public void saveOrUpdate(T05periodecosting t05periodecosting) {
		t05periodecostingDAO.saveOrUpdate(t05periodecosting);
		
	}

	@Override
	public void update(T05periodecosting t05periodecosting) {
		t05periodecostingDAO.update(t05periodecosting);
		
	}

	@Override
	public void save(T05periodecosting t05periodecosting) {
		t05periodecostingDAO.save(t05periodecosting);
		
	}

	@Override
	public void delete(T05periodecosting t05periodecosting) {
		t05periodecostingDAO.delete(t05periodecosting);
		
	}

	@Override
	public List<T05periodecosting> getListT05periodecosting(
			Map<Object, Object> parameterInput) {
		return t05periodecostingDAO.getListT05periodecosting(parameterInput);
	}

	
	
}
