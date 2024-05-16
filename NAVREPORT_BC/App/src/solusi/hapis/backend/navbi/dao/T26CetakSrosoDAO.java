package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T23AdjTopCust;
import solusi.hapis.backend.navbi.model.T26CetakSroso;

public interface T26CetakSrosoDAO {
	public void saveOrUpdate(T26CetakSroso t26CetakSroso);
	public void update(T26CetakSroso t26CetakSroso);
	public void save(T26CetakSroso t26CetakSroso);
	public void delete(T26CetakSroso t26CetakSroso);
	public List<T26CetakSroso> getListT26CetakSroso(Map<Object, Object> parameterInput);

}
