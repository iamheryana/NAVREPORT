package solusi.hapis.backend.security.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.model.SecLog;

public interface SecLogDAO {

	public void save(SecLog entity);

	public List<SecLog> getListSecLog(Map<Object, Object> parameterInput);

}
