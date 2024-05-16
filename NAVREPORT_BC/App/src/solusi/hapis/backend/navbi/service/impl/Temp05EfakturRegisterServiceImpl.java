package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.Temp05EfakturRegisterDAO;
import solusi.hapis.backend.navbi.model.Temp05EfakturRegister;
import solusi.hapis.backend.navbi.service.Temp05EfakturRegisterService;

public class Temp05EfakturRegisterServiceImpl  implements Temp05EfakturRegisterService {
	private Temp05EfakturRegisterDAO temp05EfakturRegisterDAO;

	public Temp05EfakturRegisterDAO getTemp05EfakturRegisterDAO() {
		return temp05EfakturRegisterDAO;
	}

	public void setTemp05EfakturRegisterDAO(Temp05EfakturRegisterDAO temp05EfakturRegisterDAO) {
		this.temp05EfakturRegisterDAO = temp05EfakturRegisterDAO;
	}

	@Override
	public void saveOrUpdate(Temp05EfakturRegister temp05EfakturRegister) {
		temp05EfakturRegisterDAO.saveOrUpdate(temp05EfakturRegister);		
	}

	@Override
	public void update(Temp05EfakturRegister temp05EfakturRegister) {
		temp05EfakturRegisterDAO.update(temp05EfakturRegister);			
	}

	@Override
	public void save(Temp05EfakturRegister temp05EfakturRegister) {
		temp05EfakturRegisterDAO.save(temp05EfakturRegister);	
	}

	@Override
	public void delete(Temp05EfakturRegister temp05EfakturRegister) {
		temp05EfakturRegisterDAO.delete(temp05EfakturRegister);	
	}

	public List<Temp05EfakturRegister> getListTemp05EfakturRegister(
			Map<Object, Object> parameterInput) {
		return temp05EfakturRegisterDAO.getListTemp05EfakturRegister(parameterInput);
	}

	@Override
	public void save(List<Temp05EfakturRegister> temp05EfakturRegisters) {
		for(Temp05EfakturRegister aData : temp05EfakturRegisters){
			temp05EfakturRegisterDAO.save(aData);
		}
		
	}

	@Override
	public void delete(List<Temp05EfakturRegister> temp05EfakturRegisters) {
		for(Temp05EfakturRegister aData : temp05EfakturRegisters){
			temp05EfakturRegisterDAO.delete(aData);
		}
	}	
	
}
