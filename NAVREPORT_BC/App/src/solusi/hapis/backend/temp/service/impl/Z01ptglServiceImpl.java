package solusi.hapis.backend.temp.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.temp.dao.Z01ptglDAO;
import solusi.hapis.backend.temp.model.Z01ptgl;
import solusi.hapis.backend.temp.service.Z01ptglService;

public class Z01ptglServiceImpl implements Z01ptglService {
	private Z01ptglDAO z01ptglDAO;
    	
	public Z01ptglDAO getZ01ptglDAO() {
		return z01ptglDAO;
	}

	public void setZ01ptglDAO(Z01ptglDAO z01ptglDAO) {
		this.z01ptglDAO = z01ptglDAO;
	}

	@Override
	public void saveOrUpdate(Z01ptgl z01ptgl) {
		z01ptglDAO.saveOrUpdate(z01ptgl);		
	}

	@Override
	public void update(Z01ptgl z01ptgl) {
		z01ptglDAO.update(z01ptgl);		
	}

	@Override
	public void save(Z01ptgl z01ptgl) {
		z01ptglDAO.save(z01ptgl);	
	}

	@Override
	public void delete(Z01ptgl z01ptgl) {
		z01ptglDAO.delete(z01ptgl);			
	}

	@Override
	public List<Z01ptgl> getListZ01ptgl(Map<Object, Object> parameterInput) {
		return z01ptglDAO.getListZ01ptgl(parameterInput);
	}

}
