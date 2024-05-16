package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.Temp12WebinarQaDAO;
import solusi.hapis.backend.navbi.model.Temp12WebinarQa;
import solusi.hapis.backend.navbi.service.Temp12WebinarQaService;

public class Temp12WebinarQaServiceImpl implements Temp12WebinarQaService {
	private Temp12WebinarQaDAO temp12WebinarQaDAO;

	public Temp12WebinarQaDAO getTemp12WebinarQaDAO() {
		return temp12WebinarQaDAO;
	}

	public void setTemp12WebinarQaDAO(Temp12WebinarQaDAO temp12WebinarQaDAO) {
		this.temp12WebinarQaDAO = temp12WebinarQaDAO;
	}

	@Override
	public void saveOrUpdate(Temp12WebinarQa temp12WebinarQa) {
		temp12WebinarQaDAO.saveOrUpdate(temp12WebinarQa);		
	}

	@Override
	public void update(Temp12WebinarQa temp12WebinarQa) {
		temp12WebinarQaDAO.update(temp12WebinarQa);			
	}

	@Override
	public void save(Temp12WebinarQa temp12WebinarQa) {
		temp12WebinarQaDAO.save(temp12WebinarQa);	
	}

	@Override
	public void delete(Temp12WebinarQa temp12WebinarQa) {
		temp12WebinarQaDAO.delete(temp12WebinarQa);	
	}

	public List<Temp12WebinarQa> getListTemp12WebinarQa(
			Map<Object, Object> parameterInput) {
		return temp12WebinarQaDAO.getListTemp12WebinarQa(parameterInput);
	}

	@Override
	public void save(List<Temp12WebinarQa> temp12WebinarQas) {
		for(Temp12WebinarQa aData : temp12WebinarQas){
			temp12WebinarQaDAO.save(aData);
		}
		
	}

	@Override
	public void delete(List<Temp12WebinarQa> temp12WebinarQas) {
		for(Temp12WebinarQa aData : temp12WebinarQas){
			temp12WebinarQaDAO.delete(aData);
		}
	}	
	
}
