package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T31CostingDAcsps;

public interface T31CostingDAcspsDAO {
	public void saveOrUpdate(T31CostingDAcsps t31CostingDAcsps);
	public void update(T31CostingDAcsps t31CostingDAcsps);
	public void save(T31CostingDAcsps t31CostingDAcsps);
	public void delete(T31CostingDAcsps t31CostingDAcsps);
	public void flush();
	public List<T31CostingDAcsps> getListT31CostingDAcsps(Map<Object, Object> parameterInput);
}
