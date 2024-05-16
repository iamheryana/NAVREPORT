package solusi.hapis.backend.tabel.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.tabel.model.T03salesperson;
import solusi.hapis.backend.tabel.model.T08targetsales;

public interface T03salespersonService {
//	public void saveOrUpdate(T03salesperson t03salesperson);
//	public void update(T03salesperson t03salesperson);
//	public void save(T03salesperson t03salesperson);
//	public void delete(T03salesperson t03salesperson);
	
	public void insert(T03salesperson t03salesperson);
	public void update(T03salesperson t03salesperson, List<T08targetsales> listDetailDelete);
	public void delete(T03salesperson t03salesperson);
	
	public List<T03salesperson> getListT03salesperson(Map<Object, Object> parameterInput);
	public List<T08targetsales> getListT08targetsales(Map<Object, Object> parameterInput);
	
	
	
	public ResultObject getListT03salespersonLOV(Map<Object, Object> parameterInput, int start, int pageSize);
	public ResultObject getListT03salespersonLOVFilter(Map<Object, Object> parameterInput, int start, int pageSize);

}
