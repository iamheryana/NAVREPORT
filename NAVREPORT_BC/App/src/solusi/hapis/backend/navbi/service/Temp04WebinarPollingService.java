package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp04WebinarPolling;

public interface Temp04WebinarPollingService {
	public void saveOrUpdate(Temp04WebinarPolling temp04WebinarPolling);
	public void update(Temp04WebinarPolling temp04WebinarPolling);
	public void save(Temp04WebinarPolling temp04WebinarPolling);
	public void save(List<Temp04WebinarPolling> temp04WebinarPollings);
	public void delete(Temp04WebinarPolling temp04WebinarPolling);
	public void delete(List<Temp04WebinarPolling> temp04WebinarPollings);
	public List<Temp04WebinarPolling> getListTemp04WebinarPolling(Map<Object, Object> parameterInput);

}
