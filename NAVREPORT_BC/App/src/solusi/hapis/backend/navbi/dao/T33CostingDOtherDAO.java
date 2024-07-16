package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T33CostingDOther;

public interface T33CostingDOtherDAO {
	public void saveOrUpdate(T33CostingDOther t33CostingDOther);
	public void update(T33CostingDOther t33CostingDOther);
	public void save(T33CostingDOther t33CostingDOther);
	public void delete(T33CostingDOther t33CostingDOther);
	public void flush();
	public List<T33CostingDOther> getListT33CostingDOther(Map<Object, Object> parameterInput);
}
