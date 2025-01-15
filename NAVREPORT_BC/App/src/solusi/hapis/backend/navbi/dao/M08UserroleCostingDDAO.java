package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.M08UserroleCostingD;

public interface M08UserroleCostingDDAO {
	public void saveOrUpdate(M08UserroleCostingD m08UserroleCostingD);
	public void update(M08UserroleCostingD m08UserroleCostingD);
	public void save(M08UserroleCostingD m08UserroleCostingD);
	public void delete(M08UserroleCostingD m08UserroleCostingD);
	public void flush();
	public List<M08UserroleCostingD> getListM08UserroleCostingD(Map<Object, Object> parameterInput);
}
