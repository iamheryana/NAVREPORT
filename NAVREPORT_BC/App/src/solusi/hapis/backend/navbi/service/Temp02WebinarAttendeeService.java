package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp02WebinarAttendee;

public interface Temp02WebinarAttendeeService {
	public void saveOrUpdate(Temp02WebinarAttendee temp02WebinarAttendee);
	public void update(Temp02WebinarAttendee temp02WebinarAttendee);
	public void save(Temp02WebinarAttendee temp02WebinarAttendee);
	public void save(List<Temp02WebinarAttendee> temp02WebinarAttendees);
	public void delete(Temp02WebinarAttendee temp02WebinarAttendee);
	public void delete(List<Temp02WebinarAttendee> temp02WebinarAttendees);
	public List<Temp02WebinarAttendee> getListTemp02WebinarAttendee(Map<Object, Object> parameterInput);

}
