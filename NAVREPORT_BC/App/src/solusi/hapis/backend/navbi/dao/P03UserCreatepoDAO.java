package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.P03UserCreatepo;

public interface P03UserCreatepoDAO {
	public void saveOrUpdate(P03UserCreatepo p03UserCreatepo);
	public void update(P03UserCreatepo p03UserCreatepo);
	public void save(P03UserCreatepo p03UserCreatepo);
	public void delete(P03UserCreatepo p03UserCreatepo);
	public List<P03UserCreatepo> getListP03UserCreatepo(Map<Object, Object> parameterInput);

}
