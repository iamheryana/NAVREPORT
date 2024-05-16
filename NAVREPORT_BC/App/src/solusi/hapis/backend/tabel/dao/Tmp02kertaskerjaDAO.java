package solusi.hapis.backend.tabel.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.Tmp02kertaskerja;

public interface Tmp02kertaskerjaDAO {
	public void saveOrUpdate(Tmp02kertaskerja tmp02kertaskerja);
	public void update(Tmp02kertaskerja tmp02kertaskerja);
	public void save(Tmp02kertaskerja tmp02kertaskerja);
	public void delete(Tmp02kertaskerja tmp02kertaskerja);
	public List<Tmp02kertaskerja> getListTmp02kertaskerja(Map<Object, Object> parameterInput);
}
