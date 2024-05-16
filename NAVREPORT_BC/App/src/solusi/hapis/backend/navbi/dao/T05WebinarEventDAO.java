package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.model.T05WebinarEvent;

public interface T05WebinarEventDAO {
	public void saveOrUpdate(T05WebinarEvent t05WebinarEvent);
	public void update(T05WebinarEvent t05WebinarEvent);
	public void save(T05WebinarEvent t05WebinarEvent);
	public void delete(T05WebinarEvent t05WebinarEvent);
	public void flush();
	public List<T05WebinarEvent> getListT05WebinarEvent(Map<Object, Object> parameterInput);

	public ResultObject getListT05WebinarEventLOV(Map<Object, Object> parameterInput, int start, int pageSize);

}
