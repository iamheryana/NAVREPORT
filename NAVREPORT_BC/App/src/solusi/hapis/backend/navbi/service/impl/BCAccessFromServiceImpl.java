package solusi.hapis.backend.navbi.service.impl;

import solusi.hapis.backend.navbi.dao.BCAccessFromDAO;
import solusi.hapis.backend.navbi.model.BCAccessFrom;
import solusi.hapis.backend.navbi.service.BCAccessFromService;

public class BCAccessFromServiceImpl  implements BCAccessFromService {
	BCAccessFromDAO bCAccessFromDAO;

	public BCAccessFromDAO getbCAccessFromDAO() {
		return bCAccessFromDAO;
	}

	public void setbCAccessFromDAO(BCAccessFromDAO bCAccessFromDAO) {
		this.bCAccessFromDAO = bCAccessFromDAO;
	}

	@Override
	public void saveOrUpdate(BCAccessFrom bCAccessFrom) {
		bCAccessFromDAO.saveOrUpdate(bCAccessFrom);
		
	}

	@Override
	public void update(BCAccessFrom bCAccessFrom) {
		bCAccessFromDAO.update(bCAccessFrom);
		
	}

	@Override
	public void save(BCAccessFrom bCAccessFrom) {
		bCAccessFromDAO.save(bCAccessFrom);
		
	}

	@Override
	public void delete(BCAccessFrom bCAccessFrom) {
		bCAccessFromDAO.delete(bCAccessFrom);
		
	}

	@Override
	public BCAccessFrom getLastSync() {
		return bCAccessFromDAO.getLastSync();
	}
	
	
}
