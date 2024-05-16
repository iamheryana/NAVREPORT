package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T16RekapCosting;

public interface T16RekapCostingDAO {
	public void saveOrUpdate(T16RekapCosting t16RekapCosting);
	public void update(T16RekapCosting t16RekapCosting);
	public void save(T16RekapCosting t16RekapCosting);
	public void delete(T16RekapCosting t16RekapCosting);
	public List<T16RekapCosting> getListT16RekapCosting(Map<Object, Object> parameterInput);

}
