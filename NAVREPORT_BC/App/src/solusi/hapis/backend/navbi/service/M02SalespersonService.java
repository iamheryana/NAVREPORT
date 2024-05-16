package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.model.M02Salesperson;
import solusi.hapis.backend.navbi.model.M03Targetsales;

public interface M02SalespersonService {
	
	public void insert(M02Salesperson m02Salesperson);
	public void update(M02Salesperson m02Salesperson, List<M03Targetsales> listDetailDelete);
	public void delete(M02Salesperson m02Salesperson);
	
	public List<M02Salesperson> getListM02Salesperson(Map<Object, Object> parameterInput);
	public List<M03Targetsales> getListM03Targetsales(Map<Object, Object> parameterInput);
	
	
	
	public ResultObject getListM02SalespersonLOV(Map<Object, Object> parameterInput, int start, int pageSize);
	public ResultObject getListM02SalespersonLOVFilter(Map<Object, Object> parameterInput, int start, int pageSize);

}
