package solusi.hapis.backend.parameter.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.parameter.dao.CabangDAO;
import solusi.hapis.backend.parameter.model.Cabang;
import solusi.hapis.backend.parameter.service.CabangService;

public class CabangServiceImpl implements CabangService{
	private CabangDAO cabangDAO;
	
	
	public CabangDAO getCabangDAO() {
		return cabangDAO;
	}


	public void setCabangDAO(CabangDAO cabangDAO) {
		this.cabangDAO = cabangDAO;
	}


	@Override
	public List<Cabang> getCabang(Map<Object, Object> parameterInput) {
		return cabangDAO.getListCabang(parameterInput);
	}
	

}
