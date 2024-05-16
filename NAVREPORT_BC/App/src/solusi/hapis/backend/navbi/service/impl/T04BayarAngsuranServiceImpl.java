package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T04BayarAngsuranDAO;
import solusi.hapis.backend.navbi.model.T04BayarAngsuran;
import solusi.hapis.backend.navbi.service.T04BayarAngsuranService;

public class T04BayarAngsuranServiceImpl implements T04BayarAngsuranService{
	T04BayarAngsuranDAO t04BayarAngsuranDAO;

	public T04BayarAngsuranDAO getT04BayarAngsuranDAO() {
		return t04BayarAngsuranDAO;
	}

	public void setT04BayarAngsuranDAO(T04BayarAngsuranDAO t04BayarAngsuranDAO) {
		this.t04BayarAngsuranDAO = t04BayarAngsuranDAO;
	}

	@Override
	public void saveOrUpdate(T04BayarAngsuran t04BayarAngsuran) {
		t04BayarAngsuranDAO.saveOrUpdate(t04BayarAngsuran);		
	}

	@Override
	public void update(T04BayarAngsuran t04BayarAngsuran) {
		t04BayarAngsuranDAO.update(t04BayarAngsuran);
	}

	@Override
	public void save(T04BayarAngsuran t04BayarAngsuran) {
		t04BayarAngsuranDAO.save(t04BayarAngsuran);		
	}

	@Override
	public void delete(T04BayarAngsuran t04BayarAngsuran) {
		t04BayarAngsuranDAO.delete(t04BayarAngsuran);		
	}

	@Override
	public List<T04BayarAngsuran> getListT04BayarAngsuran(Map<Object, Object> parameterInput) {
		return t04BayarAngsuranDAO.getListT04BayarAngsuran(parameterInput);
	}
	
	
}
