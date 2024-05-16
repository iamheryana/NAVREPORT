package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.BCUserLocationDAO;
import solusi.hapis.backend.navbi.model.BCUserLocation;
import solusi.hapis.backend.navbi.service.BCUserLocationService;

public class BCUserLocationServiceImpl implements BCUserLocationService{
	BCUserLocationDAO bCUserLocationDAO;

	public BCUserLocationDAO getbCUserLocationDAO() {
		return bCUserLocationDAO;
	}

	public void setbCUserLocationDAO(BCUserLocationDAO bCUserLocationDAO) {
		this.bCUserLocationDAO = bCUserLocationDAO;
	}

	@Override
	public void saveOrUpdate(BCUserLocation bCUserLocation) {
		bCUserLocationDAO.saveOrUpdate(bCUserLocation);
		
	}

	@Override
	public void update(BCUserLocation bCUserLocation) {
		bCUserLocationDAO.update(bCUserLocation);
		
	}

	@Override
	public void save(BCUserLocation bCUserLocation) {
		bCUserLocationDAO.save(bCUserLocation);
		
	}

	@Override
	public void delete(BCUserLocation bCUserLocation) {
		bCUserLocationDAO.delete(bCUserLocation);
		
	}

	@Override
	public List<BCUserLocation> getListBCUserLocation(
			Map<Object, Object> parameterInput) {
		return bCUserLocationDAO.getListBCUserLocation(parameterInput);
	}
	
	
}
