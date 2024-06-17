package solusi.hapis.backend.navbi.service;

import solusi.hapis.backend.navbi.model.BCAccessFrom;

public interface BCAccessFromService {
	public void saveOrUpdate(BCAccessFrom bCAccessFrom);
	public void update(BCAccessFrom bCAccessFrom);
	public void save(BCAccessFrom bCAccessFrom);
	public void delete(BCAccessFrom bCAccessFrom);
	public BCAccessFrom getLastSync();
}
