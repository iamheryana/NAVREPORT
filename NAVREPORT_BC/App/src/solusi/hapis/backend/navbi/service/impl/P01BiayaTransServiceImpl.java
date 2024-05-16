package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.P01BiayaTransDAO;
import solusi.hapis.backend.navbi.model.P01BiayaTrans;
import solusi.hapis.backend.navbi.service.P01BiayaTransService;


public class P01BiayaTransServiceImpl implements P01BiayaTransService {
	private P01BiayaTransDAO p01BiayaTransDAO;
	
	public P01BiayaTransDAO getP01BiayaTransDAO() {
		return p01BiayaTransDAO;
	}

	public void setP01BiayaTransDAO(P01BiayaTransDAO p01BiayaTransDAO) {
		this.p01BiayaTransDAO = p01BiayaTransDAO;
	}

	@Override
	public void saveOrUpdate(P01BiayaTrans p01BiayaTrans) {
		p01BiayaTransDAO.saveOrUpdate(p01BiayaTrans);
		
	}

	@Override
	public void update(P01BiayaTrans p01BiayaTrans) {
		p01BiayaTransDAO.update(p01BiayaTrans);
		
	}

	@Override
	public void save(P01BiayaTrans p01BiayaTrans) {
		p01BiayaTransDAO.save(p01BiayaTrans);
		
	}

	@Override
	public void delete(P01BiayaTrans p01BiayaTrans) {
		p01BiayaTransDAO.delete(p01BiayaTrans);
		
	}

	@Override
	public List<P01BiayaTrans> getListP01BiayaTrans(
			Map<Object, Object> parameterInput) {
		return p01BiayaTransDAO.getListP01BiayaTrans(parameterInput);
	}

	@Override
	public P01BiayaTrans getP01BiayaTransByKode(String kode) {
		return p01BiayaTransDAO.getP01BiayaTransByKode(kode);
	}
	
}
