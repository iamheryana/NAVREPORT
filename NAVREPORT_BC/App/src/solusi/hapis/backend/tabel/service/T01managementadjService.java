package solusi.hapis.backend.tabel.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.T01managementadj;

public interface T01managementadjService {
	public void saveOrUpdate(T01managementadj t01managementadj);
	public void update(T01managementadj t01managementadj);
	public void save(T01managementadj t01managementadj);
	public void delete(T01managementadj t01managementadj);
	public List<T01managementadj> getListT01managementadj(Map<Object, Object> parameterInput);
}
