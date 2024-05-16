package solusi.hapis.backend.security.dao.impl;

import solusi.hapis.backend.model.SecParam;
import solusi.hapis.backend.security.dao.SecParamDAO;

public class SecParamDAOImpl extends BasisDAO<SecParam> implements SecParamDAO{

	@Override
	public SecParam getSecParamByID(Long paramId) {
	    return get(SecParam.class, paramId);
	}

}
