package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T30CostingDHw3ps;

public interface T30CostingDHw3psDAO {
	public void saveOrUpdate(T30CostingDHw3ps t30CostingDHw3ps);
	public void update(T30CostingDHw3ps t30CostingDHw3ps);
	public void save(T30CostingDHw3ps t30CostingDHw3ps);
	public void delete(T30CostingDHw3ps t30CostingDHw3ps);
	public void flush();
	public List<T30CostingDHw3ps> getListT30CostingDHw3ps(Map<Object, Object> parameterInput);
}
