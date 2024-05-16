package solusi.hapis.backend.tabel.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.T02rekapcosting;

public interface T02rekapcostingService {
	public void saveOrUpdate(T02rekapcosting t02rekapcosting);
	public void update(T02rekapcosting t02rekapcosting);
	public void save(T02rekapcosting t02rekapcosting);
	public void delete(T02rekapcosting t02rekapcosting);
	public List<T02rekapcosting> getListT02rekapcosting(Map<Object, Object> parameterInput);
}
