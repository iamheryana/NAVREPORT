package solusi.hapis.backend.tabel.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.T05periodecosting;

public interface T05periodecostingService {
	public void saveOrUpdate(T05periodecosting t05periodecosting);
	public void update(T05periodecosting t05periodecosting);
	public void save(T05periodecosting t05periodecosting);
	public void delete(T05periodecosting t05periodecosting);
	public List<T05periodecosting> getListT05periodecosting(Map<Object, Object> parameterInput);

}
