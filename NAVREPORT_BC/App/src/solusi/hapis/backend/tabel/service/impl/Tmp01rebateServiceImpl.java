package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.Tmp01rebateDAO;
import solusi.hapis.backend.tabel.model.Tmp01rebate;
import solusi.hapis.backend.tabel.service.Tmp01rebateService;

public class Tmp01rebateServiceImpl  implements Tmp01rebateService {
	private Tmp01rebateDAO tmp01rebateDAO;
	
	public Tmp01rebateDAO getTmp01rebateDAO() {
		return tmp01rebateDAO;
	}

	public void setTmp01rebateDAO(Tmp01rebateDAO tmp01rebateDAO) {
		this.tmp01rebateDAO = tmp01rebateDAO;
	}

	@Override
	public void saveOrUpdate(Tmp01rebate tmp01rebate) {
		tmp01rebateDAO.saveOrUpdate(tmp01rebate);		
	}

	@Override
	public void update(Tmp01rebate tmp01rebate) {
		tmp01rebateDAO.update(tmp01rebate);			
	}

	@Override
	public void save(Tmp01rebate tmp01rebate) {
		tmp01rebateDAO.save(tmp01rebate);	
	}

	@Override
	public void delete(Tmp01rebate tmp01rebate) {
		tmp01rebateDAO.delete(tmp01rebate);	
	}

	public List<Tmp01rebate> getListTmp01rebate(
			Map<Object, Object> parameterInput) {
		return tmp01rebateDAO.getListTmp01rebate(parameterInput);
	}

	@Override
	public void save(List<Tmp01rebate> tmp01rebates) {
		for(Tmp01rebate aData : tmp01rebates){
			tmp01rebateDAO.save(aData);
		}
		
	}

	@Override
	public void delete(List<Tmp01rebate> tmp01rebates) {
		for(Tmp01rebate aData : tmp01rebates){
			tmp01rebateDAO.delete(aData);
		}
	}

}
