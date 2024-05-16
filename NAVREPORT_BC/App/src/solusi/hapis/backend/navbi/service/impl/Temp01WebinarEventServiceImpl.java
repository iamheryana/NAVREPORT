package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.Temp01WebinarEventDAO;
import solusi.hapis.backend.navbi.model.Temp01WebinarEvent;
import solusi.hapis.backend.navbi.service.Temp01WebinarEventService;

public class Temp01WebinarEventServiceImpl  implements Temp01WebinarEventService {
	private Temp01WebinarEventDAO temp01WebinarEventDAO;

	public Temp01WebinarEventDAO getTemp01WebinarEventDAO() {
		return temp01WebinarEventDAO;
	}

	public void setTemp01WebinarEventDAO(Temp01WebinarEventDAO temp01WebinarEventDAO) {
		this.temp01WebinarEventDAO = temp01WebinarEventDAO;
	}

	@Override
	public void saveOrUpdate(Temp01WebinarEvent temp01WebinarEvent) {
		temp01WebinarEventDAO.saveOrUpdate(temp01WebinarEvent);		
	}

	@Override
	public void update(Temp01WebinarEvent temp01WebinarEvent) {
		temp01WebinarEventDAO.update(temp01WebinarEvent);			
	}

	@Override
	public void save(Temp01WebinarEvent temp01WebinarEvent) {
		temp01WebinarEventDAO.save(temp01WebinarEvent);	
	}

	@Override
	public void delete(Temp01WebinarEvent temp01WebinarEvent) {
		temp01WebinarEventDAO.delete(temp01WebinarEvent);	
	}

	public List<Temp01WebinarEvent> getListTemp01WebinarEvent(
			Map<Object, Object> parameterInput) {
		return temp01WebinarEventDAO.getListTemp01WebinarEvent(parameterInput);
	}

	@Override
	public void save(List<Temp01WebinarEvent> temp01WebinarEvents) {
		for(Temp01WebinarEvent aData : temp01WebinarEvents){
			temp01WebinarEventDAO.save(aData);
		}
		
	}

	@Override
	public void delete(List<Temp01WebinarEvent> temp01WebinarEvents) {
		for(Temp01WebinarEvent aData : temp01WebinarEvents){
			temp01WebinarEventDAO.delete(aData);
		}
	}	
	
}
