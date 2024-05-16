package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T12PsAdjPriceDAO;
import solusi.hapis.backend.navbi.model.T12PsAdjPrice;
import solusi.hapis.backend.navbi.service.T12PsAdjPriceService;

public class T12PsAdjPriceServiceImpl implements T12PsAdjPriceService{
	T12PsAdjPriceDAO t12PsAdjPriceDAO;

	public T12PsAdjPriceDAO getT12PsAdjPriceDAO() {
		return t12PsAdjPriceDAO;
	}

	public void setT12PsAdjPriceDAO(T12PsAdjPriceDAO t12PsAdjPriceDAO) {
		this.t12PsAdjPriceDAO = t12PsAdjPriceDAO;
	}

	@Override
	public void saveOrUpdate(T12PsAdjPrice t12PsAdjPrice) {
		t12PsAdjPriceDAO.saveOrUpdate(t12PsAdjPrice);		
	}

	@Override
	public void update(T12PsAdjPrice t12PsAdjPrice) {
		t12PsAdjPriceDAO.update(t12PsAdjPrice);
	}

	@Override
	public void save(T12PsAdjPrice t12PsAdjPrice) {
		t12PsAdjPriceDAO.save(t12PsAdjPrice);		
	}

	@Override
	public void delete(T12PsAdjPrice t12PsAdjPrice) {
		t12PsAdjPriceDAO.delete(t12PsAdjPrice);		
	}

	@Override
	public List<T12PsAdjPrice> getListT12PsAdjPrice(Map<Object, Object> parameterInput) {
		return t12PsAdjPriceDAO.getListT12PsAdjPrice(parameterInput);
	}
	
	
}
