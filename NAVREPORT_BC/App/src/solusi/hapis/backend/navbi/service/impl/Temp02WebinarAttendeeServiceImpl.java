package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.Temp02WebinarAttendeeDAO;
import solusi.hapis.backend.navbi.model.Temp02WebinarAttendee;
import solusi.hapis.backend.navbi.service.Temp02WebinarAttendeeService;

public class Temp02WebinarAttendeeServiceImpl  implements Temp02WebinarAttendeeService {
	private Temp02WebinarAttendeeDAO temp02WebinarAttendeeDAO;

	public Temp02WebinarAttendeeDAO getTemp02WebinarAttendeeDAO() {
		return temp02WebinarAttendeeDAO;
	}

	public void setTemp02WebinarAttendeeDAO(Temp02WebinarAttendeeDAO temp02WebinarAttendeeDAO) {
		this.temp02WebinarAttendeeDAO = temp02WebinarAttendeeDAO;
	}

	@Override
	public void saveOrUpdate(Temp02WebinarAttendee temp02WebinarAttendee) {
		temp02WebinarAttendeeDAO.saveOrUpdate(temp02WebinarAttendee);		
	}

	@Override
	public void update(Temp02WebinarAttendee temp02WebinarAttendee) {
		temp02WebinarAttendeeDAO.update(temp02WebinarAttendee);			
	}

	@Override
	public void save(Temp02WebinarAttendee temp02WebinarAttendee) {
		temp02WebinarAttendeeDAO.save(temp02WebinarAttendee);	
	}

	@Override
	public void delete(Temp02WebinarAttendee temp02WebinarAttendee) {
		temp02WebinarAttendeeDAO.delete(temp02WebinarAttendee);	
	}

	public List<Temp02WebinarAttendee> getListTemp02WebinarAttendee(
			Map<Object, Object> parameterInput) {
		return temp02WebinarAttendeeDAO.getListTemp02WebinarAttendee(parameterInput);
	}

	@Override
	public void save(List<Temp02WebinarAttendee> temp02WebinarAttendees) {
		for(Temp02WebinarAttendee aData : temp02WebinarAttendees){
			temp02WebinarAttendeeDAO.save(aData);
		}
		
	}

	@Override
	public void delete(List<Temp02WebinarAttendee> temp02WebinarAttendees) {
		for(Temp02WebinarAttendee aData : temp02WebinarAttendees){
			temp02WebinarAttendeeDAO.delete(aData);
		}
	}	
	
}
