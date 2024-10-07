package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.P06ParamDefaultRptDAO;
import solusi.hapis.backend.navbi.model.P06ParamDefaultRpt;
import solusi.hapis.backend.navbi.service.P06ParamDefaultRptService;

public class P06ParamDefaultRptServiceImpl implements P06ParamDefaultRptService {
	private P06ParamDefaultRptDAO p06ParamDefaultRptDAO;
	
	public P06ParamDefaultRptDAO getP06ParamDefaultRptDAO() {
		return p06ParamDefaultRptDAO;
	}

	public void setP06ParamDefaultRptDAO(P06ParamDefaultRptDAO p06ParamDefaultRptDAO) {
		this.p06ParamDefaultRptDAO = p06ParamDefaultRptDAO;
	}

	@Override
	public void saveOrUpdate(P06ParamDefaultRpt p06ParamDefaultRpt) {
		p06ParamDefaultRptDAO.saveOrUpdate(p06ParamDefaultRpt);
		
	}

	@Override
	public void update(P06ParamDefaultRpt p06ParamDefaultRpt) {
		p06ParamDefaultRptDAO.update(p06ParamDefaultRpt);
		
	}

	@Override
	public void save(P06ParamDefaultRpt p06ParamDefaultRpt) {
		p06ParamDefaultRptDAO.save(p06ParamDefaultRpt);
		
	}

	@Override
	public void delete(P06ParamDefaultRpt p06ParamDefaultRpt) {
		p06ParamDefaultRptDAO.delete(p06ParamDefaultRpt);
		
	}

	@Override
	public List<P06ParamDefaultRpt> getListP06ParamDefaultRpt(
			Map<Object, Object> parameterInput) {
		return p06ParamDefaultRptDAO.getListP06ParamDefaultRpt(parameterInput);
	}

	@Override
	public P06ParamDefaultRpt getP06ParamDefaultRptByKode(String kode) {
		// TODO Auto-generated method stub
		return p06ParamDefaultRptDAO.getP06ParamDefaultRptByKode(kode);
	}
	
}
