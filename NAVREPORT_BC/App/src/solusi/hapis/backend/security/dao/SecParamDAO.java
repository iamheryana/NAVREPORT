package solusi.hapis.backend.security.dao;

import solusi.hapis.backend.model.SecParam;

public interface SecParamDAO {
	public SecParam getSecParamByID(Long paramId);

	public void saveOrUpdate(SecParam entity);

}
