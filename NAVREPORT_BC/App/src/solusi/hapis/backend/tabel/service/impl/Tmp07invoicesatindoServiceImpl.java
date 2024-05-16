package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.Tmp07invoicesatindoDAO;
import solusi.hapis.backend.tabel.model.Tmp07invoicesatindo;
import solusi.hapis.backend.tabel.service.Tmp07invoicesatindoService;

public class Tmp07invoicesatindoServiceImpl implements Tmp07invoicesatindoService {
	private Tmp07invoicesatindoDAO tmp07invoicesatindoDAO;
	
	
	
	public Tmp07invoicesatindoDAO getTmp07invoicesatindoDAO() {
		return tmp07invoicesatindoDAO;
	}

	public void setTmp07invoicesatindoDAO(Tmp07invoicesatindoDAO tmp07invoicesatindoDAO) {
		this.tmp07invoicesatindoDAO = tmp07invoicesatindoDAO;
	}

	@Override
	public void saveOrUpdate(Tmp07invoicesatindo tmp07invoicesatindo) {
		tmp07invoicesatindoDAO.saveOrUpdate(tmp07invoicesatindo);	
	}

	@Override
	public void update(Tmp07invoicesatindo tmp07invoicesatindo) {
		tmp07invoicesatindoDAO.update(tmp07invoicesatindo);		
	}

	@Override
	public void save(Tmp07invoicesatindo tmp07invoicesatindo) {
		tmp07invoicesatindoDAO.save(tmp07invoicesatindo);		
	}

	@Override
	public void save(List<Tmp07invoicesatindo> tmp07invoicesatindos) {
		for(Tmp07invoicesatindo aData : tmp07invoicesatindos){
			tmp07invoicesatindoDAO.save(aData);
		}
		
	}

	@Override
	public void delete(Tmp07invoicesatindo tmp07invoicesatindo) {
		tmp07invoicesatindoDAO.delete(tmp07invoicesatindo);		
	}

	@Override
	public void delete(List<Tmp07invoicesatindo> tmp07invoicesatindos) {
		for(Tmp07invoicesatindo aData : tmp07invoicesatindos){
			tmp07invoicesatindoDAO.delete(aData);
		}
	}

	@Override
	public List<Tmp07invoicesatindo> getListTmp07invoicesatindo(
			Map<Object, Object> parameterInput) {
		return tmp07invoicesatindoDAO.getListTmp07invoicesatindo(parameterInput);
	}

}
