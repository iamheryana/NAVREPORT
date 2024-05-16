package solusi.hapis.backend.navbi.service.impl;

import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.dao.V01CustomerNavDAO;
import solusi.hapis.backend.navbi.model.V01CustomerNav;
import solusi.hapis.backend.navbi.service.V01CustomerNavService;

public class V01CustomerNavServiceImpl implements  V01CustomerNavService{
	V01CustomerNavDAO v01CustomerNavDAO;
	
	
	

	public V01CustomerNavDAO getV01CustomerNavDAO() {
		return v01CustomerNavDAO;
	}

	public void setV01CustomerNavDAO(V01CustomerNavDAO v01CustomerNavDAO) {
		this.v01CustomerNavDAO = v01CustomerNavDAO;
	}

	@Override
	public V01CustomerNav getV01CustomerNavByKode(String kode) {
		
		return v01CustomerNavDAO.getV01CustomerNavByKode(kode);
	}

	@Override
	public ResultObject getListV01CustomerNavLOV(
			Map<Object, Object> parameterInput, int start, int pageSize) {
		
		return v01CustomerNavDAO.getListV01CustomerNavLOV(parameterInput, start, pageSize);
	}

}
