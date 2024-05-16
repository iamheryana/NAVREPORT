package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T20PiVendor;

public interface T20PiVendorService {
	public void saveOrUpdate(T20PiVendor t20PiVendor);
	public void update(T20PiVendor t20PiVendor);
	public void save(T20PiVendor t20PiVendor);
	public void delete(T20PiVendor t20PiVendor);
	public List<T20PiVendor> getListT20PiVendor(Map<Object, Object> parameterInput);
}
