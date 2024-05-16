package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.dao.T05WebinarEventDAO;
import solusi.hapis.backend.navbi.dao.T06WebinarAttendeeDAO;
import solusi.hapis.backend.navbi.model.T05WebinarEvent;
import solusi.hapis.backend.navbi.model.T06WebinarAttendee;
import solusi.hapis.backend.navbi.service.T05WebinarEventService;
import solusi.hapis.common.CommonUtils;

public class T05WebinarEventServiceImpl implements T05WebinarEventService {
	private T05WebinarEventDAO t05WebinarEventDAO;
	private T06WebinarAttendeeDAO t06WebinarAttendeeDAO;
	
	public T05WebinarEventDAO getT05WebinarEventDAO() {
		return t05WebinarEventDAO;
	}

	public void setT05WebinarEventDAO(T05WebinarEventDAO t05WebinarEventDAO) {
		this.t05WebinarEventDAO = t05WebinarEventDAO;
	}
	
	
	public T06WebinarAttendeeDAO getT06WebinarAttendeeDAO() {
		return t06WebinarAttendeeDAO;
	}

	public void setT06WebinarAttendeeDAO(T06WebinarAttendeeDAO t06WebinarAttendeeDAO) {
		this.t06WebinarAttendeeDAO = t06WebinarAttendeeDAO;
	}


	@Override
	public void delete(T05WebinarEvent t05WebinarEvent) {
		t05WebinarEventDAO.delete(t05WebinarEvent);
		
	}

	@Override
	public List<T05WebinarEvent> getListT05WebinarEvent(
			Map<Object, Object> parameterInput) {
		return t05WebinarEventDAO.getListT05WebinarEvent(parameterInput);
	}

	@Override
	public List<T06WebinarAttendee> getListT06WebinarAttendee(
			Map<Object, Object> parameterInput) {
		// TODO Auto-generated method stub
		return t06WebinarAttendeeDAO.getListT06WebinarAttendee(parameterInput);
	}

	@Override
	public void insert(T05WebinarEvent t05WebinarEvent) {
		t05WebinarEventDAO.save(t05WebinarEvent);
		if(CommonUtils.isNotEmpty(t05WebinarEvent.getT06WebinarAttendees())){
			for (T06WebinarAttendee t08 : t05WebinarEvent.getT06WebinarAttendees()) {
				t06WebinarAttendeeDAO.save(t08);
			}
		}
		
	}

	@Override
	public void update(T05WebinarEvent t05WebinarEvent,
			List<T06WebinarAttendee> listDetailDelete) {
		// Update Header
		t05WebinarEventDAO.update(t05WebinarEvent);
		
		// Update Detail 
		// - Hapus yang sudah tidak ada
		// - tambahkan yang baru 
		// - update jika sudah ada
		
		if (CommonUtils.isNotEmpty(listDetailDelete)) {
			for (T06WebinarAttendee deleteT08 : listDetailDelete) {
				t06WebinarAttendeeDAO.delete(deleteT08);
				t06WebinarAttendeeDAO.flush();
			}
		}
		
		if (CommonUtils.isNotEmpty(t05WebinarEvent.getT06WebinarAttendees())){
			for (T06WebinarAttendee aDtl : t05WebinarEvent.getT06WebinarAttendees()) {
				aDtl.setT05WebinarEvent(t05WebinarEvent);
				if (aDtl.getT06Id() > 0) {
					t06WebinarAttendeeDAO.update(aDtl);
				} else {
					t06WebinarAttendeeDAO.save(aDtl);
				}			
			}
		}
		
	}

	@Override
	public ResultObject getListT05WebinarEventLOV(
			Map<Object, Object> parameterInput, int start, int pageSize) {
		// TODO Auto-generated method stub
		return t05WebinarEventDAO.getListT05WebinarEventLOV(parameterInput, start, pageSize);
	}
	
	
}

