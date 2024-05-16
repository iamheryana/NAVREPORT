package solusi.hapis.backend.tabel.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.Tmp07invoicesatindo;

public interface Tmp07invoicesatindoDAO {
	public void saveOrUpdate(Tmp07invoicesatindo tmp07invoicesatindo);
	public void update(Tmp07invoicesatindo tmp07invoicesatindo);
	public void save(Tmp07invoicesatindo tmp07invoicesatindo);
	public void delete(Tmp07invoicesatindo tmp07invoicesatindo);
	public List<Tmp07invoicesatindo> getListTmp07invoicesatindo(Map<Object, Object> parameterInput);
}
