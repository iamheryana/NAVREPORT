package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T01SoAdjDAO;
import solusi.hapis.backend.navbi.model.T01SoAdj;
import solusi.hapis.backend.navbi.service.T01SoAdjService;

public class T01SoAdjServiceImpl implements T01SoAdjService{
	T01SoAdjDAO t01SoAdjDAO;

	public T01SoAdjDAO getT01SoAdjDAO() {
		return t01SoAdjDAO;
	}

	public void setT01SoAdjDAO(T01SoAdjDAO t01SoAdjDAO) {
		this.t01SoAdjDAO = t01SoAdjDAO;
	}

	@Override
	public void saveOrUpdate(T01SoAdj t01SoAdj) {
		t01SoAdjDAO.saveOrUpdate(t01SoAdj);		
	}

	@Override
	public void update(T01SoAdj t01SoAdj) {
		t01SoAdjDAO.update(t01SoAdj);
	}

	@Override
	public void save(T01SoAdj t01SoAdj) {
		t01SoAdjDAO.save(t01SoAdj);		
	}

	@Override
	public void delete(T01SoAdj t01SoAdj) {
		t01SoAdjDAO.delete(t01SoAdj);		
	}

	@Override
	public List<T01SoAdj> getListT01SoAdj(Map<Object, Object> parameterInput) {
		return t01SoAdjDAO.getListT01SoAdj(parameterInput);
	}
	
	
}
