package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T36OtherCf;

public interface T36OtherCfService {
	public void saveOrUpdate(T36OtherCf t36OtherCf);
	public void update(T36OtherCf t36OtherCf);
	public void save(T36OtherCf t36OtherCf);
	public void delete(T36OtherCf t36OtherCf);
	public List<T36OtherCf> getListT36OtherCf(Map<Object, Object> parameterInput);
}
