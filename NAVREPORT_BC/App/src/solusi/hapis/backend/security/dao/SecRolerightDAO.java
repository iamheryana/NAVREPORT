package solusi.hapis.backend.security.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.model.SecRoleright;

public interface SecRolerightDAO {

	public void save(SecRoleright entity);

	public void delete(SecRoleright entity);
	
	public List<SecRoleright> getListSecRoleright(Map<Object, Object> parameterInput);
	
}
