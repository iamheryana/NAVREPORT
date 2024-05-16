package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.dao.P02VendorNonnavDAO;
import solusi.hapis.backend.navbi.model.P02VendorNonnav;
import solusi.hapis.backend.navbi.service.P02VendorNonnavService;

public class P02VendorNonnavServiceImpl implements P02VendorNonnavService{
	P02VendorNonnavDAO p02VendorNonnavDAO;

	public P02VendorNonnavDAO getP02VendorNonnavDAO() {
		return p02VendorNonnavDAO;
	}

	public void setP02VendorNonnavDAO(P02VendorNonnavDAO p02VendorNonnavDAO) {
		this.p02VendorNonnavDAO = p02VendorNonnavDAO;
	}

	@Override
	public void saveOrUpdate(P02VendorNonnav p02VendorNonnav) {
		p02VendorNonnavDAO.saveOrUpdate(p02VendorNonnav);		
	}

	@Override
	public void update(P02VendorNonnav p02VendorNonnav) {
		p02VendorNonnavDAO.update(p02VendorNonnav);
	}

	@Override
	public void save(P02VendorNonnav p02VendorNonnav) {
		p02VendorNonnavDAO.save(p02VendorNonnav);		
	}

	@Override
	public void delete(P02VendorNonnav p02VendorNonnav) {
		p02VendorNonnavDAO.delete(p02VendorNonnav);		
	}

	@Override
	public List<P02VendorNonnav> getListP02VendorNonnav(Map<Object, Object> parameterInput) {
		return p02VendorNonnavDAO.getListP02VendorNonnav(parameterInput);
	}
	
	@Override
	public ResultObject getListP02VendorNonnavLOV(
			Map<Object, Object> parameterInput, int start, int pageSize) {
		return p02VendorNonnavDAO.getListP02VendorNonnavLOV(parameterInput, start, pageSize);
	}

	@Override
	public P02VendorNonnav getP02VendorNonnavByKode(String kode) {
		
		return p02VendorNonnavDAO.getP02VendorNonnavByKode(kode);
	}

}
