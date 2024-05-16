package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.BCUserLocation;

public interface BCUserLocationDAO {
	public void saveOrUpdate(BCUserLocation bCUserLocation);
	public void update(BCUserLocation bCUserLocation);
	public void save(BCUserLocation bCUserLocation);
	public void delete(BCUserLocation bCUserLocation);
	public List<BCUserLocation> getListBCUserLocation(Map<Object, Object> parameterInput);
}
