package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.model.T05WebinarEvent;
import solusi.hapis.backend.navbi.model.T06WebinarAttendee;
import solusi.hapis.backend.navbi.model.Temp01WebinarEvent;

public interface T05WebinarEventService {
	public void insert(T05WebinarEvent t05WebinarEvent);
	public void update(T05WebinarEvent t05WebinarEvent, List<T06WebinarAttendee> listDetailDelete);
	public void delete(T05WebinarEvent t05WebinarEvent);
	
	public List<T05WebinarEvent> getListT05WebinarEvent(Map<Object, Object> parameterInput);
	public List<T06WebinarAttendee> getListT06WebinarAttendee(Map<Object, Object> parameterInput);

	public ResultObject getListT05WebinarEventLOV(Map<Object, Object> parameterInput, int start, int pageSize);

}
