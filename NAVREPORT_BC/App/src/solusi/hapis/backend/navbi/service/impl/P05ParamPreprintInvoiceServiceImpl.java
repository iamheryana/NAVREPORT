package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.P05ParamPreprintInvoiceDAO;
import solusi.hapis.backend.navbi.model.P05ParamPreprintInvoice;
import solusi.hapis.backend.navbi.service.P05ParamPreprintInvoiceService;

public class P05ParamPreprintInvoiceServiceImpl implements P05ParamPreprintInvoiceService {
	private P05ParamPreprintInvoiceDAO p05ParamPreprintInvoiceDAO;
	
	public P05ParamPreprintInvoiceDAO getP05ParamPreprintInvoiceDAO() {
		return p05ParamPreprintInvoiceDAO;
	}

	public void setP05ParamPreprintInvoiceDAO(P05ParamPreprintInvoiceDAO p05ParamPreprintInvoiceDAO) {
		this.p05ParamPreprintInvoiceDAO = p05ParamPreprintInvoiceDAO;
	}

	@Override
	public void saveOrUpdate(P05ParamPreprintInvoice p05ParamPreprintInvoice) {
		p05ParamPreprintInvoiceDAO.saveOrUpdate(p05ParamPreprintInvoice);
		
	}

	@Override
	public void update(P05ParamPreprintInvoice p05ParamPreprintInvoice) {
		p05ParamPreprintInvoiceDAO.update(p05ParamPreprintInvoice);
		
	}

	@Override
	public void save(P05ParamPreprintInvoice p05ParamPreprintInvoice) {
		p05ParamPreprintInvoiceDAO.save(p05ParamPreprintInvoice);
		
	}

	@Override
	public void delete(P05ParamPreprintInvoice p05ParamPreprintInvoice) {
		p05ParamPreprintInvoiceDAO.delete(p05ParamPreprintInvoice);
		
	}

	@Override
	public List<P05ParamPreprintInvoice> getListP05ParamPreprintInvoice(
			Map<Object, Object> parameterInput) {
		return p05ParamPreprintInvoiceDAO.getListP05ParamPreprintInvoice(parameterInput);
	}

	@Override
	public P05ParamPreprintInvoice getP05ParamPreprintInvoiceByKode(String kode) {
		// TODO Auto-generated method stub
		return p05ParamPreprintInvoiceDAO.getP05ParamPreprintInvoiceByKode(kode);
	}
	
}
