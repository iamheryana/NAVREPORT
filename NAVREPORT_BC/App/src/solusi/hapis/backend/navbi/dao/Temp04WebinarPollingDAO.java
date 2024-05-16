package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp04WebinarPolling;


public interface Temp04WebinarPollingDAO {
	public void saveOrUpdate(Temp04WebinarPolling temp04WebinarPolling);
	public void update(Temp04WebinarPolling temp04WebinarPolling);
	public void save(Temp04WebinarPolling temp04WebinarPolling);
	public void delete(Temp04WebinarPolling temp04WebinarPolling);
	public List<Temp04WebinarPolling> getListTemp04WebinarPolling(Map<Object, Object> parameterInput);

}
