package solusi.hapis.backend.tabel.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.Tmp07invoicesatindo;

public interface Tmp07invoicesatindoService {
	public void saveOrUpdate(Tmp07invoicesatindo tmp07invoicesatindo);
	public void update(Tmp07invoicesatindo tmp07invoicesatindo);
	public void save(Tmp07invoicesatindo tmp07invoicesatindo);
	public void save(List<Tmp07invoicesatindo> tmp07invoicesatindos);
	public void delete(Tmp07invoicesatindo tmp07invoicesatindo);
	public void delete(List<Tmp07invoicesatindo> tmp07invoicesatindos);
	public List<Tmp07invoicesatindo> getListTmp07invoicesatindo(Map<Object, Object> parameterInput);
}
