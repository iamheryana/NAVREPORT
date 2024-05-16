package solusi.hapis.backend.tabel.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.tabel.model.T03salesperson;

public interface T03salespersonDAO {
	public void saveOrUpdate(T03salesperson t03salesperson);
	public void update(T03salesperson t03salesperson);
	public void save(T03salesperson t03salesperson);
	public void delete(T03salesperson t03salesperson);
	public List<T03salesperson> getListT03salesperson(Map<Object, Object> parameterInput);
	public void flush();
	public ResultObject getListT03salespersonLOV(Map<Object, Object> parameterInput, int start, int pageSize);
	
	
	public ResultObject getListT03salespersonLOVFilter(Map<Object, Object> parameterInput, int start, int pageSize);
}
