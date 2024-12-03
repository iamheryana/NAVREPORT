package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.P07ParamCashflow;

public interface P07ParamCashflowService {
	public void saveOrUpdate(P07ParamCashflow p07ParamCashflow);
	public void update(P07ParamCashflow p07ParamCashflow);
	public void save(P07ParamCashflow p07ParamCashflow);
	public void delete(P07ParamCashflow p07ParamCashflow);
	
	public List<P07ParamCashflow> getListP07ParamCashflow(Map<Object, Object> parameterInput);

	public P07ParamCashflow getP07ParamCashflowByKode(String kode);
}
