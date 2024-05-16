package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.T07itemsatindoDAO;
import solusi.hapis.backend.tabel.model.T07itemsatindo;
import solusi.hapis.backend.tabel.service.T07itemsatindoService;

public class T07itemsatindoServiceImpl implements T07itemsatindoService {
	private T07itemsatindoDAO t07itemsatindoDAO;
	
	public T07itemsatindoDAO getT07itemsatindoDAO() {
		return t07itemsatindoDAO;
	}

	public void setT07itemsatindoDAO(T07itemsatindoDAO t07itemsatindoDAO) {
		this.t07itemsatindoDAO = t07itemsatindoDAO;
	}

	@Override
	public void saveOrUpdate(T07itemsatindo t07itemsatindo) {
		t07itemsatindoDAO.saveOrUpdate(t07itemsatindo);
		
	}

	@Override
	public void update(T07itemsatindo t07itemsatindo) {
		t07itemsatindoDAO.update(t07itemsatindo);
		
	}

	@Override
	public void save(T07itemsatindo t07itemsatindo) {
		t07itemsatindoDAO.save(t07itemsatindo);
		
	}

	@Override
	public void delete(T07itemsatindo t07itemsatindo) {
		t07itemsatindoDAO.delete(t07itemsatindo);
		
	}

	@Override
	public List<T07itemsatindo> getListT07itemsatindo(
			Map<Object, Object> parameterInput) {
		return t07itemsatindoDAO.getListT07itemsatindo(parameterInput);
	}

	
	
}
