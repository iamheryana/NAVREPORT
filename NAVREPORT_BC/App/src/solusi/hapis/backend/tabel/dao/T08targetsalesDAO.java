package solusi.hapis.backend.tabel.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.T08targetsales;


public interface T08targetsalesDAO {
	public void saveOrUpdate(T08targetsales t08targetsales);
	public void update(T08targetsales t08targetsales);
	public void save(T08targetsales t08targetsales);
	public void delete(T08targetsales t08targetsales);
	public void flush();
	public List<T08targetsales> getListT08targetsales(Map<Object, Object> parameterInput);
}
