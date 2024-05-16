package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.Tmp05navfakturDAO;
import solusi.hapis.backend.tabel.model.Tmp05navfaktur;
import solusi.hapis.backend.tabel.service.Tmp05navfakturService;

public class Tmp05navfakturServiceImpl implements Tmp05navfakturService {
	private Tmp05navfakturDAO tmp05navfakturDAO;
	
	
	
	public Tmp05navfakturDAO getTmp05navfakturDAO() {
		return tmp05navfakturDAO;
	}

	public void setTmp05navfakturDAO(Tmp05navfakturDAO tmp05navfakturDAO) {
		this.tmp05navfakturDAO = tmp05navfakturDAO;
	}

	@Override
	public void saveOrUpdate(Tmp05navfaktur tmp05navfaktur) {
		tmp05navfakturDAO.saveOrUpdate(tmp05navfaktur);	
	}

	@Override
	public void update(Tmp05navfaktur tmp05navfaktur) {
		tmp05navfakturDAO.update(tmp05navfaktur);		
	}

	@Override
	public void save(Tmp05navfaktur tmp05navfaktur) {
		tmp05navfakturDAO.save(tmp05navfaktur);		
	}

	@Override
	public void save(List<Tmp05navfaktur> tmp05navfakturs) {
		for(Tmp05navfaktur aData : tmp05navfakturs){
			tmp05navfakturDAO.save(aData);
		}
		
	}

	@Override
	public void delete(Tmp05navfaktur tmp05navfaktur) {
		tmp05navfakturDAO.delete(tmp05navfaktur);		
	}

	@Override
	public void delete(List<Tmp05navfaktur> tmp05navfakturs) {
		for(Tmp05navfaktur aData : tmp05navfakturs){
			tmp05navfakturDAO.delete(aData);
		}
	}

	@Override
	public List<Tmp05navfaktur> getListTmp05navfaktur(
			Map<Object, Object> parameterInput) {
		return tmp05navfakturDAO.getListTmp05navfaktur(parameterInput);
	}

}
