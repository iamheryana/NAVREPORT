package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T29CostingH;

public interface T29CostingHDAO {
	public void saveOrUpdate(T29CostingH t29CostingH);
	public void update(T29CostingH t29CostingH);
	public void save(T29CostingH t29CostingH);
	public void delete(T29CostingH t29CostingH);
	public List<T29CostingH> getListT29CostingH(Map<Object, Object> parameterInput);
	public void flush();
	
	public T29CostingH getT29CostingHByNoCosting(String noCosting);
	public String callGetNoCosting(String noCosting);
	
}
