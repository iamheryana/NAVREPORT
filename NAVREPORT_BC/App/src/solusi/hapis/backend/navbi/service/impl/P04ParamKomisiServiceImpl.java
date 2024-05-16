package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.P04ParamKomisiDAO;
import solusi.hapis.backend.navbi.model.P04ParamKomisi;
import solusi.hapis.backend.navbi.service.P04ParamKomisiService;

public class P04ParamKomisiServiceImpl implements P04ParamKomisiService {
	private P04ParamKomisiDAO p04ParamKomisiDAO;
	
	public P04ParamKomisiDAO getP04ParamKomisiDAO() {
		return p04ParamKomisiDAO;
	}

	public void setP04ParamKomisiDAO(P04ParamKomisiDAO p04ParamKomisiDAO) {
		this.p04ParamKomisiDAO = p04ParamKomisiDAO;
	}

	@Override
	public void saveOrUpdate(P04ParamKomisi p04ParamKomisi) {
		p04ParamKomisiDAO.saveOrUpdate(p04ParamKomisi);
		
	}

	@Override
	public void update(P04ParamKomisi p04ParamKomisi) {
		p04ParamKomisiDAO.update(p04ParamKomisi);
		
	}

	@Override
	public void save(P04ParamKomisi p04ParamKomisi) {
		p04ParamKomisiDAO.save(p04ParamKomisi);
		
	}

	@Override
	public void delete(P04ParamKomisi p04ParamKomisi) {
		p04ParamKomisiDAO.delete(p04ParamKomisi);
		
	}

	@Override
	public List<P04ParamKomisi> getListP04ParamKomisi(
			Map<Object, Object> parameterInput) {
		return p04ParamKomisiDAO.getListP04ParamKomisi(parameterInput);
	}

	@Override
	public P04ParamKomisi getP04ParamKomisiByKode(String kode) {
		// TODO Auto-generated method stub
		return p04ParamKomisiDAO.getP04ParamKomisiByKode(kode);
	}
	
}
