package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T20PiVendorDAO;
import solusi.hapis.backend.navbi.model.T20PiVendor;
import solusi.hapis.backend.navbi.service.T20PiVendorService;

public class T20PiVendorServiceImpl implements T20PiVendorService{
	T20PiVendorDAO t20PiVendorDAO;

	public T20PiVendorDAO getT20PiVendorDAO() {
		return t20PiVendorDAO;
	}

	public void setT20PiVendorDAO(T20PiVendorDAO t20PiVendorDAO) {
		this.t20PiVendorDAO = t20PiVendorDAO;
	}

	@Override
	public void saveOrUpdate(T20PiVendor t20PiVendor) {
		t20PiVendorDAO.saveOrUpdate(t20PiVendor);		
	}

	@Override
	public void update(T20PiVendor t20PiVendor) {
		t20PiVendorDAO.update(t20PiVendor);
	}

	@Override
	public void save(T20PiVendor t20PiVendor) {
		t20PiVendorDAO.save(t20PiVendor);		
	}

	@Override
	public void delete(T20PiVendor t20PiVendor) {
		t20PiVendorDAO.delete(t20PiVendor);		
	}

	@Override
	public List<T20PiVendor> getListT20PiVendor(Map<Object, Object> parameterInput) {
		return t20PiVendorDAO.getListT20PiVendor(parameterInput);
	}
	
	
}
