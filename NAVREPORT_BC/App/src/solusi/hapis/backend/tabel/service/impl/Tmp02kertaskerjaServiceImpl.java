package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.Tmp02kertaskerjaDAO;
import solusi.hapis.backend.tabel.model.Tmp02kertaskerja;
import solusi.hapis.backend.tabel.service.Tmp02kertaskerjaService;

public class Tmp02kertaskerjaServiceImpl implements Tmp02kertaskerjaService {
	private Tmp02kertaskerjaDAO tmp02kertaskerjaDAO;
	
	
	
	public Tmp02kertaskerjaDAO getTmp02kertaskerjaDAO() {
		return tmp02kertaskerjaDAO;
	}

	public void setTmp02kertaskerjaDAO(Tmp02kertaskerjaDAO tmp02kertaskerjaDAO) {
		this.tmp02kertaskerjaDAO = tmp02kertaskerjaDAO;
	}

	@Override
	public void saveOrUpdate(Tmp02kertaskerja tmp02kertaskerja) {
		tmp02kertaskerjaDAO.saveOrUpdate(tmp02kertaskerja);	
	}

	@Override
	public void update(Tmp02kertaskerja tmp02kertaskerja) {
		tmp02kertaskerjaDAO.update(tmp02kertaskerja);		
	}

	@Override
	public void save(Tmp02kertaskerja tmp02kertaskerja) {
		tmp02kertaskerjaDAO.save(tmp02kertaskerja);		
	}

	@Override
	public void save(List<Tmp02kertaskerja> tmp02kertaskerjas) {
		for(Tmp02kertaskerja aData : tmp02kertaskerjas){
			tmp02kertaskerjaDAO.save(aData);
		}
		
	}

	@Override
	public void delete(Tmp02kertaskerja tmp02kertaskerja) {
		tmp02kertaskerjaDAO.delete(tmp02kertaskerja);		
	}

	@Override
	public void delete(List<Tmp02kertaskerja> tmp02kertaskerjas) {
		for(Tmp02kertaskerja aData : tmp02kertaskerjas){
			tmp02kertaskerjaDAO.delete(aData);
		}
	}

	@Override
	public List<Tmp02kertaskerja> getListTmp02kertaskerja(
			Map<Object, Object> parameterInput) {
		return tmp02kertaskerjaDAO.getListTmp02kertaskerja(parameterInput);
	}

}
