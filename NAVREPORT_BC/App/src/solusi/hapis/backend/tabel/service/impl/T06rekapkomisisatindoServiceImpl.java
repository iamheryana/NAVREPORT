package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.T06rekapkomisisatindoDAO;
import solusi.hapis.backend.tabel.model.T06rekapkomisisatindo;
import solusi.hapis.backend.tabel.service.T06rekapkomisisatindoService;

public class T06rekapkomisisatindoServiceImpl implements T06rekapkomisisatindoService {
	private T06rekapkomisisatindoDAO t06rekapkomisisatindoDAO;
	
	public T06rekapkomisisatindoDAO getT06rekapkomisisatindoDAO() {
		return t06rekapkomisisatindoDAO;
	}

	public void setT06rekapkomisisatindoDAO(T06rekapkomisisatindoDAO t06rekapkomisisatindoDAO) {
		this.t06rekapkomisisatindoDAO = t06rekapkomisisatindoDAO;
	}

	@Override
	public void saveOrUpdate(T06rekapkomisisatindo t06rekapkomisisatindo) {
		t06rekapkomisisatindoDAO.saveOrUpdate(t06rekapkomisisatindo);
		
	}

	@Override
	public void update(T06rekapkomisisatindo t06rekapkomisisatindo) {
		t06rekapkomisisatindoDAO.update(t06rekapkomisisatindo);
		
	}

	@Override
	public void save(T06rekapkomisisatindo t06rekapkomisisatindo) {
		t06rekapkomisisatindoDAO.save(t06rekapkomisisatindo);
		
	}

	@Override
	public void delete(T06rekapkomisisatindo t06rekapkomisisatindo) {
		t06rekapkomisisatindoDAO.delete(t06rekapkomisisatindo);
		
	}

	@Override
	public List<T06rekapkomisisatindo> getListT06rekapkomisisatindo(
			Map<Object, Object> parameterInput) {
		return t06rekapkomisisatindoDAO.getListT06rekapkomisisatindo(parameterInput);
	}

	
	
}
