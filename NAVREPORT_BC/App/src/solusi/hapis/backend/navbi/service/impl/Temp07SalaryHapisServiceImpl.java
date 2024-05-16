package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.Temp07SalaryHapisDAO;
import solusi.hapis.backend.navbi.model.Temp07SalaryHapis;
import solusi.hapis.backend.navbi.service.Temp07SalaryHapisService;

public class Temp07SalaryHapisServiceImpl  implements Temp07SalaryHapisService {
	private Temp07SalaryHapisDAO temp07SalaryHapisDAO;

	public Temp07SalaryHapisDAO getTemp07SalaryHapisDAO() {
		return temp07SalaryHapisDAO;
	}

	public void setTemp07SalaryHapisDAO(Temp07SalaryHapisDAO temp07SalaryHapisDAO) {
		this.temp07SalaryHapisDAO = temp07SalaryHapisDAO;
	}

	@Override
	public void saveOrUpdate(Temp07SalaryHapis temp07SalaryHapis) {
		temp07SalaryHapisDAO.saveOrUpdate(temp07SalaryHapis);		
	}

	@Override
	public void update(Temp07SalaryHapis temp07SalaryHapis) {
		temp07SalaryHapisDAO.update(temp07SalaryHapis);			
	}

	@Override
	public void save(Temp07SalaryHapis temp07SalaryHapis) {
		temp07SalaryHapisDAO.save(temp07SalaryHapis);	
	}

	@Override
	public void delete(Temp07SalaryHapis temp07SalaryHapis) {
		temp07SalaryHapisDAO.delete(temp07SalaryHapis);	
	}

	public List<Temp07SalaryHapis> getListTemp07SalaryHapis(
			Map<Object, Object> parameterInput) {
		return temp07SalaryHapisDAO.getListTemp07SalaryHapis(parameterInput);
	}

	@Override
	public void save(List<Temp07SalaryHapis> temp07SalaryHapiss) {
		for(Temp07SalaryHapis aData : temp07SalaryHapiss){
			temp07SalaryHapisDAO.save(aData);
		}
		
	}

	@Override
	public void delete(List<Temp07SalaryHapis> temp07SalaryHapiss) {
		for(Temp07SalaryHapis aData : temp07SalaryHapiss){
			temp07SalaryHapisDAO.delete(aData);
		}
	}	
	
}
