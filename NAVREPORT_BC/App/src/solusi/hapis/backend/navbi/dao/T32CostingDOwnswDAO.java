package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T32CostingDOwnsw;

public interface T32CostingDOwnswDAO {
	public void saveOrUpdate(T32CostingDOwnsw t32CostingDOwnsw);
	public void update(T32CostingDOwnsw t32CostingDOwnsw);
	public void save(T32CostingDOwnsw t32CostingDOwnsw);
	public void delete(T32CostingDOwnsw t32CostingDOwnsw);
	public void flush();
	public List<T32CostingDOwnsw> getListT32CostingDOwnsw(Map<Object, Object> parameterInput);
}
