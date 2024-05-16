package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T06WebinarAttendee;

public interface T06WebinarAttendeeDAO {
	public void saveOrUpdate(T06WebinarAttendee t06WebinarAttendee);
	public void update(T06WebinarAttendee t06WebinarAttendee);
	public void save(T06WebinarAttendee t06WebinarAttendee);
	public void delete(T06WebinarAttendee t06WebinarAttendee);
	public void flush();
	public List<T06WebinarAttendee> getListT06WebinarAttendee(Map<Object, Object> parameterInput);

}
