package solusi.hapis.backend.tabel.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.Tmp02kertaskerja;

public interface Tmp02kertaskerjaService {
	public void saveOrUpdate(Tmp02kertaskerja tmp02kertaskerja);
	public void update(Tmp02kertaskerja tmp02kertaskerja);
	public void save(Tmp02kertaskerja tmp02kertaskerja);
	public void save(List<Tmp02kertaskerja> tmp02kertaskerjas);
	public void delete(Tmp02kertaskerja tmp02kertaskerja);
	public void delete(List<Tmp02kertaskerja> tmp02kertaskerjas);
	public List<Tmp02kertaskerja> getListTmp02kertaskerja(Map<Object, Object> parameterInput);
}
