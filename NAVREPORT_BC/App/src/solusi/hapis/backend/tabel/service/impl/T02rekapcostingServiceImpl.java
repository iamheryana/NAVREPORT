package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.T02rekapcostingDAO;
import solusi.hapis.backend.tabel.model.T02rekapcosting;
import solusi.hapis.backend.tabel.service.T02rekapcostingService;

public class T02rekapcostingServiceImpl implements T02rekapcostingService {
	private T02rekapcostingDAO t02rekapcostingDAO;
	
	public T02rekapcostingDAO getT02rekapcostingDAO() {
		return t02rekapcostingDAO;
	}

	public void setT02rekapcostingDAO(T02rekapcostingDAO t02rekapcostingDAO) {
		this.t02rekapcostingDAO = t02rekapcostingDAO;
	}

	@Override
	public void saveOrUpdate(T02rekapcosting t02rekapcosting) {
		t02rekapcostingDAO.saveOrUpdate(t02rekapcosting);
		
	}

	@Override
	public void update(T02rekapcosting t02rekapcosting) {
		t02rekapcostingDAO.update(t02rekapcosting);
		
	}

	@Override
	public void save(T02rekapcosting t02rekapcosting) {
		t02rekapcostingDAO.save(t02rekapcosting);
		
	}

	@Override
	public void delete(T02rekapcosting t02rekapcosting) {
		t02rekapcostingDAO.delete(t02rekapcosting);
		
	}

	@Override
	public List<T02rekapcosting> getListT02rekapcosting(
			Map<Object, Object> parameterInput) {
		return t02rekapcostingDAO.getListT02rekapcosting(parameterInput);
	}
	
	
}
