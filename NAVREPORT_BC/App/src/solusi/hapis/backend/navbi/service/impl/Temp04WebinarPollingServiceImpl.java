package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.Temp04WebinarPollingDAO;
import solusi.hapis.backend.navbi.model.Temp04WebinarPolling;
import solusi.hapis.backend.navbi.service.Temp04WebinarPollingService;

public class Temp04WebinarPollingServiceImpl  implements Temp04WebinarPollingService {
	private Temp04WebinarPollingDAO temp04WebinarPollingDAO;

	public Temp04WebinarPollingDAO getTemp04WebinarPollingDAO() {
		return temp04WebinarPollingDAO;
	}

	public void setTemp04WebinarPollingDAO(Temp04WebinarPollingDAO temp04WebinarPollingDAO) {
		this.temp04WebinarPollingDAO = temp04WebinarPollingDAO;
	}

	@Override
	public void saveOrUpdate(Temp04WebinarPolling temp04WebinarPolling) {
		temp04WebinarPollingDAO.saveOrUpdate(temp04WebinarPolling);		
	}

	@Override
	public void update(Temp04WebinarPolling temp04WebinarPolling) {
		temp04WebinarPollingDAO.update(temp04WebinarPolling);			
	}

	@Override
	public void save(Temp04WebinarPolling temp04WebinarPolling) {
		temp04WebinarPollingDAO.save(temp04WebinarPolling);	
	}

	@Override
	public void delete(Temp04WebinarPolling temp04WebinarPolling) {
		temp04WebinarPollingDAO.delete(temp04WebinarPolling);	
	}

	public List<Temp04WebinarPolling> getListTemp04WebinarPolling(
			Map<Object, Object> parameterInput) {
		return temp04WebinarPollingDAO.getListTemp04WebinarPolling(parameterInput);
	}

	@Override
	public void save(List<Temp04WebinarPolling> temp04WebinarPollings) {
		for(Temp04WebinarPolling aData : temp04WebinarPollings){
			temp04WebinarPollingDAO.save(aData);
		}
		
	}

	@Override
	public void delete(List<Temp04WebinarPolling> temp04WebinarPollings) {
		for(Temp04WebinarPolling aData : temp04WebinarPollings){
			temp04WebinarPollingDAO.delete(aData);
		}
	}	
	
}
