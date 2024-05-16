package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T26CetakSrosoDAO;
import solusi.hapis.backend.navbi.model.T26CetakSroso;
import solusi.hapis.backend.navbi.service.T26CetakSrosoService;

public class T26CetakSrosoServiceImpl implements T26CetakSrosoService{
	T26CetakSrosoDAO t26CetakSrosoDAO;

	@Override
	public List<T26CetakSroso> getListT26CetakSroso(
			Map<Object, Object> parameterInput) {
		return t26CetakSrosoDAO.getListT26CetakSroso(parameterInput);
	}

	public T26CetakSrosoDAO getT26CetakSrosoDAO() {
		return t26CetakSrosoDAO;
	}

	public void setT26CetakSrosoDAO(T26CetakSrosoDAO t26CetakSrosoDAO) {
		this.t26CetakSrosoDAO = t26CetakSrosoDAO;
	}

}
