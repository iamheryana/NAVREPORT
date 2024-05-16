package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.M03Targetsales;

public interface M03TargetsalesDAO {
	public void saveOrUpdate(M03Targetsales m03Targetsales);
	public void update(M03Targetsales m03Targetsales);
	public void save(M03Targetsales m03Targetsales);
	public void delete(M03Targetsales m03Targetsales);
	public void flush();
	public List<M03Targetsales> getListM03Targetsales(Map<Object, Object> parameterInput);
}
