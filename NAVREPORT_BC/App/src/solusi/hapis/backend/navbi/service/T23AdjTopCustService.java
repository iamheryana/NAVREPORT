package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T23AdjTopCust;

public interface T23AdjTopCustService {
	public void saveOrUpdate(T23AdjTopCust t23AdjTopCust);
	public void update(T23AdjTopCust t23AdjTopCust);
	public void save(T23AdjTopCust t23AdjTopCust);
	public void delete(T23AdjTopCust t23AdjTopCust);
	public List<T23AdjTopCust> getListT23AdjTopCust(Map<Object, Object> parameterInput);
}
