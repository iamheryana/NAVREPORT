package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp01WebinarEvent;


public interface Temp01WebinarEventDAO {
	public void saveOrUpdate(Temp01WebinarEvent temp01WebinarEvent);
	public void update(Temp01WebinarEvent temp01WebinarEvent);
	public void save(Temp01WebinarEvent temp01WebinarEvent);
	public void delete(Temp01WebinarEvent temp01WebinarEvent);
	public List<Temp01WebinarEvent> getListTemp01WebinarEvent(Map<Object, Object> parameterInput);

}
