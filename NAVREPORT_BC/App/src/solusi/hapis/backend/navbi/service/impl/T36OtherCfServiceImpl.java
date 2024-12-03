package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T36OtherCfDAO;
import solusi.hapis.backend.navbi.model.T36OtherCf;
import solusi.hapis.backend.navbi.service.T36OtherCfService;

public class T36OtherCfServiceImpl implements T36OtherCfService{
	T36OtherCfDAO t36OtherCfDAO;

	public T36OtherCfDAO getT36OtherCfDAO() {
		return t36OtherCfDAO;
	}

	public void setT36OtherCfDAO(T36OtherCfDAO t36OtherCfDAO) {
		this.t36OtherCfDAO = t36OtherCfDAO;
	}

	@Override
	public void saveOrUpdate(T36OtherCf t36OtherCf) {
		t36OtherCfDAO.saveOrUpdate(t36OtherCf);		
	}

	@Override
	public void update(T36OtherCf t36OtherCf) {
		t36OtherCfDAO.update(t36OtherCf);
	}

	@Override
	public void save(T36OtherCf t36OtherCf) {
		t36OtherCfDAO.save(t36OtherCf);		
	}

	@Override
	public void delete(T36OtherCf t36OtherCf) {
		t36OtherCfDAO.delete(t36OtherCf);		
	}

	@Override
	public List<T36OtherCf> getListT36OtherCf(Map<Object, Object> parameterInput) {
		return t36OtherCfDAO.getListT36OtherCf(parameterInput);
	}

	
}
