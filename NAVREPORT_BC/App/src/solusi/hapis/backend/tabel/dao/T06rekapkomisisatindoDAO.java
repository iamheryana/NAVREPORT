package solusi.hapis.backend.tabel.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.T06rekapkomisisatindo;

public interface T06rekapkomisisatindoDAO {
	public void saveOrUpdate(T06rekapkomisisatindo t06rekapkomisisatindo);
	public void update(T06rekapkomisisatindo t06rekapkomisisatindo);
	public void save(T06rekapkomisisatindo t06rekapkomisisatindo);
	public void delete(T06rekapkomisisatindo t06rekapkomisisatindo);
	public List<T06rekapkomisisatindo> getListT06rekapkomisisatindo(Map<Object, Object> parameterInput);
	
}
