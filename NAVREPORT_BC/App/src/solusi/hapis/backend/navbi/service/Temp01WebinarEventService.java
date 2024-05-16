package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp01WebinarEvent;

public interface Temp01WebinarEventService {
	public void saveOrUpdate(Temp01WebinarEvent temp01WebinarEvent);
	public void update(Temp01WebinarEvent temp01WebinarEvent);
	public void save(Temp01WebinarEvent temp01WebinarEvent);
	public void save(List<Temp01WebinarEvent> temp01WebinarEvents);
	public void delete(Temp01WebinarEvent temp01WebinarEvent);
	public void delete(List<Temp01WebinarEvent> temp01WebinarEvents);
	public List<Temp01WebinarEvent> getListTemp01WebinarEvent(Map<Object, Object> parameterInput);

}
