package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.P07ParamCashflowDAO;
import solusi.hapis.backend.navbi.model.P07ParamCashflow;
import solusi.hapis.backend.navbi.service.P07ParamCashflowService;

public class P07ParamCashflowServiceImpl implements P07ParamCashflowService {
private P07ParamCashflowDAO p07ParamCashflowDAO;
	
	public P07ParamCashflowDAO getP07ParamCashflowDAO() {
		return p07ParamCashflowDAO;
	}

	public void setP07ParamCashflowDAO(P07ParamCashflowDAO p07ParamCashflowDAO) {
		this.p07ParamCashflowDAO = p07ParamCashflowDAO;
	}

	@Override
	public void saveOrUpdate(P07ParamCashflow p07ParamCashflow) {
		p07ParamCashflowDAO.saveOrUpdate(p07ParamCashflow);
		
	}

	@Override
	public void update(P07ParamCashflow p07ParamCashflow) {
		p07ParamCashflowDAO.update(p07ParamCashflow);
		
	}

	@Override
	public void save(P07ParamCashflow p07ParamCashflow) {
		p07ParamCashflowDAO.save(p07ParamCashflow);
		
	}

	@Override
	public void delete(P07ParamCashflow p07ParamCashflow) {
		p07ParamCashflowDAO.delete(p07ParamCashflow);
		
	}

	@Override
	public List<P07ParamCashflow> getListP07ParamCashflow(
			Map<Object, Object> parameterInput) {
		return p07ParamCashflowDAO.getListP07ParamCashflow(parameterInput);
	}

	@Override
	public P07ParamCashflow getP07ParamCashflowByKode(String kode) {
		// TODO Auto-generated method stub
		return p07ParamCashflowDAO.getP07ParamCashflowByKode(kode);
	}
	
}
