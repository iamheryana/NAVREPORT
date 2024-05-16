package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T19PiItemDAO;
import solusi.hapis.backend.navbi.model.T19PiItem;
import solusi.hapis.backend.navbi.service.T19PiItemService;

public class T19PiItemServiceImpl implements T19PiItemService{
	T19PiItemDAO t19PiItemDAO;

	public T19PiItemDAO getT19PiItemDAO() {
		return t19PiItemDAO;
	}

	public void setT19PiItemDAO(T19PiItemDAO t19PiItemDAO) {
		this.t19PiItemDAO = t19PiItemDAO;
	}

	@Override
	public void saveOrUpdate(T19PiItem t19PiItem) {
		t19PiItemDAO.saveOrUpdate(t19PiItem);		
	}

	@Override
	public void update(T19PiItem t19PiItem) {
		t19PiItemDAO.update(t19PiItem);
	}

	@Override
	public void save(T19PiItem t19PiItem) {
		t19PiItemDAO.save(t19PiItem);		
	}

	@Override
	public void delete(T19PiItem t19PiItem) {
		t19PiItemDAO.delete(t19PiItem);		
	}

	@Override
	public List<T19PiItem> getListT19PiItem(Map<Object, Object> parameterInput) {
		return t19PiItemDAO.getListT19PiItem(parameterInput);
	}
	
	
}
