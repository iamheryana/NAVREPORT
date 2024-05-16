package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.M04ItemSatindo;

public interface M04ItemSatindoService {
	public void saveOrUpdate(M04ItemSatindo m04ItemSatindo);
	public void update(M04ItemSatindo m04ItemSatindo);
	public void save(M04ItemSatindo m04ItemSatindo);
	public void delete(M04ItemSatindo m04ItemSatindo);
	public List<M04ItemSatindo> getListM04ItemSatindo(Map<Object, Object> parameterInput);
}
