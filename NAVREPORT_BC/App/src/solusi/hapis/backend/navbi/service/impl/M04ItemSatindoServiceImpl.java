package solusi.hapis.backend.navbi.service.impl;


import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.M04ItemSatindoDAO;
import solusi.hapis.backend.navbi.model.M04ItemSatindo;
import solusi.hapis.backend.navbi.service.M04ItemSatindoService;

public class M04ItemSatindoServiceImpl implements M04ItemSatindoService {
	private M04ItemSatindoDAO m04ItemSatindoDAO;
	
	public M04ItemSatindoDAO getM04ItemSatindoDAO() {
		return m04ItemSatindoDAO;
	}

	public void setM04ItemSatindoDAO(M04ItemSatindoDAO m04ItemSatindoDAO) {
		this.m04ItemSatindoDAO = m04ItemSatindoDAO;
	}

	@Override
	public void saveOrUpdate(M04ItemSatindo m04ItemSatindo) {
		m04ItemSatindoDAO.saveOrUpdate(m04ItemSatindo);
		
	}

	@Override
	public void update(M04ItemSatindo m04ItemSatindo) {
		m04ItemSatindoDAO.update(m04ItemSatindo);
		
	}

	@Override
	public void save(M04ItemSatindo m04ItemSatindo) {
		m04ItemSatindoDAO.save(m04ItemSatindo);
		
	}

	@Override
	public void delete(M04ItemSatindo m04ItemSatindo) {
		m04ItemSatindoDAO.delete(m04ItemSatindo);
		
	}

	@Override
	public List<M04ItemSatindo> getListM04ItemSatindo(
			Map<Object, Object> parameterInput) {
		return m04ItemSatindoDAO.getListM04ItemSatindo(parameterInput);
	}

	
	
}
