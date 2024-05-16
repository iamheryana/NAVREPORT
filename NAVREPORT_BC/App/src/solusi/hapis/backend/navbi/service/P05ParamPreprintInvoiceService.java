package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.P05ParamPreprintInvoice;

public interface P05ParamPreprintInvoiceService {
	public void saveOrUpdate(P05ParamPreprintInvoice p05ParamPreprintInvoice);
	public void update(P05ParamPreprintInvoice p05ParamPreprintInvoice);
	public void save(P05ParamPreprintInvoice p05ParamPreprintInvoice);
	public void delete(P05ParamPreprintInvoice p05ParamPreprintInvoice);
	
	public List<P05ParamPreprintInvoice> getListP05ParamPreprintInvoice(Map<Object, Object> parameterInput);
	
	
	public P05ParamPreprintInvoice getP05ParamPreprintInvoiceByKode(String kode);
}
