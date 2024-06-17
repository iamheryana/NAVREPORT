package solusi.hapis.backend.navbi.dao;

import solusi.hapis.backend.navbi.model.BCAccessFrom;

public interface BCAccessFromDAO {
	public void saveOrUpdate(BCAccessFrom bCAccessFrom);
	public void update(BCAccessFrom bCAccessFrom);
	public void save(BCAccessFrom bCAccessFrom);
	public void delete(BCAccessFrom bCAccessFrom);
	public BCAccessFrom getLastSync();
	
}
