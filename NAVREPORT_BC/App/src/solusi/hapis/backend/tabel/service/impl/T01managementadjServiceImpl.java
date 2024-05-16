package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.T01managementadjDAO;
import solusi.hapis.backend.tabel.model.T01managementadj;
import solusi.hapis.backend.tabel.service.T01managementadjService;

public class T01managementadjServiceImpl implements T01managementadjService {
	private T01managementadjDAO t01managementadjDAO;

	public T01managementadjDAO getT01managementadjDAO() {
		return t01managementadjDAO;
	}

	public void setT01managementadjDAO(T01managementadjDAO t01managementadjDAO) {
		this.t01managementadjDAO = t01managementadjDAO;
	}

	@Override
	public void saveOrUpdate(T01managementadj t01managementadj) {
		t01managementadjDAO.saveOrUpdate(t01managementadj);
		
	}

	@Override
	public void update(T01managementadj t01managementadj) {
		t01managementadjDAO.update(t01managementadj);
		
	}

	@Override
	public void save(T01managementadj t01managementadj) {
		t01managementadjDAO.save(t01managementadj);
		
	}

	@Override
	public void delete(T01managementadj t01managementadj) {
		t01managementadjDAO.delete(t01managementadj);
		
	}

	@Override
	public List<T01managementadj> getListT01managementadj(
			Map<Object, Object> parameterInput) {
		return t01managementadjDAO.getListT01managementadj(parameterInput);
	}
	
	
}
