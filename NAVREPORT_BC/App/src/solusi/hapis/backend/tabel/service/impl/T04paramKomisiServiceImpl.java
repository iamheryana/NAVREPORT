package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.T04paramKomisiDAO;
import solusi.hapis.backend.tabel.model.T04paramKomisi;
import solusi.hapis.backend.tabel.service.T04paramKomisiService;

public class T04paramKomisiServiceImpl implements T04paramKomisiService {
	private T04paramKomisiDAO t04paramKomisiDAO;
	
	public T04paramKomisiDAO getT04paramKomisiDAO() {
		return t04paramKomisiDAO;
	}

	public void setT04paramKomisiDAO(T04paramKomisiDAO t04paramKomisiDAO) {
		this.t04paramKomisiDAO = t04paramKomisiDAO;
	}

	@Override
	public void saveOrUpdate(T04paramKomisi t04paramKomisi) {
		t04paramKomisiDAO.saveOrUpdate(t04paramKomisi);
		
	}

	@Override
	public void update(T04paramKomisi t04paramKomisi) {
		t04paramKomisiDAO.update(t04paramKomisi);
		
	}

	@Override
	public void save(T04paramKomisi t04paramKomisi) {
		t04paramKomisiDAO.save(t04paramKomisi);
		
	}

	@Override
	public void delete(T04paramKomisi t04paramKomisi) {
		t04paramKomisiDAO.delete(t04paramKomisi);
		
	}

	@Override
	public List<T04paramKomisi> getListT04paramKomisi(
			Map<Object, Object> parameterInput) {
		return t04paramKomisiDAO.getListT04paramKomisi(parameterInput);
	}

	@Override
	public T04paramKomisi getT04paramKomisiByKode(String kode) {
		// TODO Auto-generated method stub
		return t04paramKomisiDAO.getT04paramKomisiByKode(kode);
	}
	
}
