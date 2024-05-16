package solusi.hapis.backend.tabel.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.T07itemsatindo;

public interface T07itemsatindoDAO {
	public void saveOrUpdate(T07itemsatindo t07itemsatindo);
	public void update(T07itemsatindo t07itemsatindo);
	public void save(T07itemsatindo t07itemsatindo);
	public void delete(T07itemsatindo t07itemsatindo);
	public List<T07itemsatindo> getListT07itemsatindo(Map<Object, Object> parameterInput);
	
}
