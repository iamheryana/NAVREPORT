package solusi.hapis.backend.navbi.dao;

import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.model.V01CustomerNav;

public interface V01CustomerNavDAO {
	public V01CustomerNav getV01CustomerNavByKode(String kode);
	
	public ResultObject getListV01CustomerNavLOV(Map<Object, Object> parameterInput, int start, int pageSize);

}
