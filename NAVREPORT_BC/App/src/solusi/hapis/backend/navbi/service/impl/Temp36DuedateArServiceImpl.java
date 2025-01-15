package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.Temp36DuedateArDAO;
import solusi.hapis.backend.navbi.model.Temp36DuedateAr;
import solusi.hapis.backend.navbi.service.Temp36DuedateArService;

public class Temp36DuedateArServiceImpl  implements Temp36DuedateArService {
	private Temp36DuedateArDAO temp36DuedateArDAO;

	public Temp36DuedateArDAO getTemp36DuedateArDAO() {
		return temp36DuedateArDAO;
	}

	public void setTemp36DuedateArDAO(Temp36DuedateArDAO temp36DuedateArDAO) {
		this.temp36DuedateArDAO = temp36DuedateArDAO;
	}

	@Override
	public void saveOrUpdate(Temp36DuedateAr temp36DuedateAr) {
		temp36DuedateArDAO.saveOrUpdate(temp36DuedateAr);		
	}

	@Override
	public void update(Temp36DuedateAr temp36DuedateAr) {
		temp36DuedateArDAO.update(temp36DuedateAr);			
	}

	@Override
	public void save(Temp36DuedateAr temp36DuedateAr) {
		temp36DuedateArDAO.save(temp36DuedateAr);	
	}

	@Override
	public void delete(Temp36DuedateAr temp36DuedateAr) {
		temp36DuedateArDAO.delete(temp36DuedateAr);	
	}

	public List<Temp36DuedateAr> getListTemp36DuedateAr(
			Map<Object, Object> parameterInput) {
		return temp36DuedateArDAO.getListTemp36DuedateAr(parameterInput);
	}

	@Override
	public void save(List<Temp36DuedateAr> temp36DuedateArs) {
		for(Temp36DuedateAr aData : temp36DuedateArs){
			temp36DuedateArDAO.save(aData);
		}
		
	}

	@Override
	public void delete(List<Temp36DuedateAr> temp36DuedateArs) {
		for(Temp36DuedateAr aData : temp36DuedateArs){
			temp36DuedateArDAO.delete(aData);
		}
	}	
	
}
