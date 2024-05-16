package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T01SoAdj;

public interface T01SoAdjService {
	public void saveOrUpdate(T01SoAdj t01SoAdj);
	public void update(T01SoAdj t01SoAdj);
	public void save(T01SoAdj t01SoAdj);
	public void delete(T01SoAdj t01SoAdj);
	public List<T01SoAdj> getListT01SoAdj(Map<Object, Object> parameterInput);
}
