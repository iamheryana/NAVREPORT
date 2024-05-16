package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.model.M02Salesperson;

public interface M02SalespersonDAO {
	public void saveOrUpdate(M02Salesperson m02Salesperson);
	public void update(M02Salesperson m02Salesperson);
	public void save(M02Salesperson m02Salesperson);
	public void delete(M02Salesperson m02Salesperson);
	public List<M02Salesperson> getListM02Salesperson(Map<Object, Object> parameterInput);
	public void flush();
	public ResultObject getListM02SalespersonLOV(Map<Object, Object> parameterInput, int start, int pageSize);
	
	
	public ResultObject getListM02SalespersonLOVFilter(Map<Object, Object> parameterInput, int start, int pageSize);

}
