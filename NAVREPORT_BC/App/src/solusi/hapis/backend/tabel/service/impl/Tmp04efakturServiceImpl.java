package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.Tmp04efakturDAO;
import solusi.hapis.backend.tabel.model.Tmp04efaktur;
import solusi.hapis.backend.tabel.service.Tmp04efakturService;

public class Tmp04efakturServiceImpl implements Tmp04efakturService {
	private Tmp04efakturDAO tmp04efakturDAO;
	
	
	
	public Tmp04efakturDAO getTmp04efakturDAO() {
		return tmp04efakturDAO;
	}

	public void setTmp04efakturDAO(Tmp04efakturDAO tmp04efakturDAO) {
		this.tmp04efakturDAO = tmp04efakturDAO;
	}

	@Override
	public void saveOrUpdate(Tmp04efaktur tmp04efaktur) {
		tmp04efakturDAO.saveOrUpdate(tmp04efaktur);	
	}

	@Override
	public void update(Tmp04efaktur tmp04efaktur) {
		tmp04efakturDAO.update(tmp04efaktur);		
	}

	@Override
	public void save(Tmp04efaktur tmp04efaktur) {
		tmp04efakturDAO.save(tmp04efaktur);		
	}

	@Override
	public void save(List<Tmp04efaktur> tmp04efakturs) {
		for(Tmp04efaktur aData : tmp04efakturs){
			tmp04efakturDAO.save(aData);
		}
		
	}

	@Override
	public void delete(Tmp04efaktur tmp04efaktur) {
		tmp04efakturDAO.delete(tmp04efaktur);		
	}

	@Override
	public void delete(List<Tmp04efaktur> tmp04efakturs) {
		for(Tmp04efaktur aData : tmp04efakturs){
			tmp04efakturDAO.delete(aData);
		}
	}

	@Override
	public List<Tmp04efaktur> getListTmp04efaktur(
			Map<Object, Object> parameterInput) {
		return tmp04efakturDAO.getListTmp04efaktur(parameterInput);
	}

}
