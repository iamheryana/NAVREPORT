package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.P06ParamDefaultRpt;

public interface P06ParamDefaultRptService {
	public void saveOrUpdate(P06ParamDefaultRpt p06ParamDefaultRpt);
	public void update(P06ParamDefaultRpt p06ParamDefaultRpt);
	public void save(P06ParamDefaultRpt p06ParamDefaultRpt);
	public void delete(P06ParamDefaultRpt p06ParamDefaultRpt);
	
	public List<P06ParamDefaultRpt> getListP06ParamDefaultRpt(Map<Object, Object> parameterInput);

	public P06ParamDefaultRpt getP06ParamDefaultRptByKode(String kode);
}
