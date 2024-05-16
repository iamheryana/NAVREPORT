package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.Tmp03hasilopnameDAO;
import solusi.hapis.backend.tabel.model.Tmp03hasilopname;
import solusi.hapis.backend.tabel.service.Tmp03hasilopnameService;

public class Tmp03hasilopnameServiceImpl implements Tmp03hasilopnameService {
	private Tmp03hasilopnameDAO tmp03hasilopnameDAO;
	
	
	
	public Tmp03hasilopnameDAO getTmp03hasilopnameDAO() {
		return tmp03hasilopnameDAO;
	}

	public void setTmp03hasilopnameDAO(Tmp03hasilopnameDAO tmp03hasilopnameDAO) {
		this.tmp03hasilopnameDAO = tmp03hasilopnameDAO;
	}

	@Override
	public void saveOrUpdate(Tmp03hasilopname tmp03hasilopname) {
		tmp03hasilopnameDAO.saveOrUpdate(tmp03hasilopname);	
	}

	@Override
	public void update(Tmp03hasilopname tmp03hasilopname) {
		tmp03hasilopnameDAO.update(tmp03hasilopname);		
	}

	@Override
	public void save(Tmp03hasilopname tmp03hasilopname) {
		tmp03hasilopnameDAO.save(tmp03hasilopname);		
	}

	@Override
	public void save(List<Tmp03hasilopname> tmp03hasilopnames) {
		for(Tmp03hasilopname aData : tmp03hasilopnames){
			tmp03hasilopnameDAO.save(aData);
		}
		
	}

	@Override
	public void delete(Tmp03hasilopname tmp03hasilopname) {
		tmp03hasilopnameDAO.delete(tmp03hasilopname);		
	}

	@Override
	public void delete(List<Tmp03hasilopname> tmp03hasilopnames) {
		for(Tmp03hasilopname aData : tmp03hasilopnames){
			tmp03hasilopnameDAO.delete(aData);
		}
	}

	@Override
	public List<Tmp03hasilopname> getListTmp03hasilopname(
			Map<Object, Object> parameterInput) {
		return tmp03hasilopnameDAO.getListTmp03hasilopname(parameterInput);
	}

}
