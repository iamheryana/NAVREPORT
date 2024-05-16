package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T15SatindoAdjDAO;
import solusi.hapis.backend.navbi.model.T15SatindoAdj;
import solusi.hapis.backend.navbi.service.T15SatindoAdjService;

public class T15SatindoAdjServiceImpl implements T15SatindoAdjService{
	T15SatindoAdjDAO t15SatindoAdjDAO;

	public T15SatindoAdjDAO getT15SatindoAdjDAO() {
		return t15SatindoAdjDAO;
	}

	public void setT15SatindoAdjDAO(T15SatindoAdjDAO t15SatindoAdjDAO) {
		this.t15SatindoAdjDAO = t15SatindoAdjDAO;
	}

	@Override
	public void saveOrUpdate(T15SatindoAdj t15SatindoAdj) {
		t15SatindoAdjDAO.saveOrUpdate(t15SatindoAdj);		
	}

	@Override
	public void update(T15SatindoAdj t15SatindoAdj) {
		t15SatindoAdjDAO.update(t15SatindoAdj);
	}

	@Override
	public void save(T15SatindoAdj t15SatindoAdj) {
		t15SatindoAdjDAO.save(t15SatindoAdj);		
	}

	@Override
	public void delete(T15SatindoAdj t15SatindoAdj) {
		t15SatindoAdjDAO.delete(t15SatindoAdj);		
	}

	@Override
	public List<T15SatindoAdj> getListT15SatindoAdj(Map<Object, Object> parameterInput) {
		return t15SatindoAdjDAO.getListT15SatindoAdj(parameterInput);
	}
	
	
}
