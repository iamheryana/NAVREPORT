package solusi.hapis.backend.tabel.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.Tmp04efaktur;

public interface Tmp04efakturService {
	public void saveOrUpdate(Tmp04efaktur tmp04efaktur);
	public void update(Tmp04efaktur tmp04efaktur);
	public void save(Tmp04efaktur tmp04efaktur);
	public void save(List<Tmp04efaktur> tmp04efakturs);
	public void delete(Tmp04efaktur tmp04efaktur);
	public void delete(List<Tmp04efaktur> tmp04efakturs);
	public List<Tmp04efaktur> getListTmp04efaktur(Map<Object, Object> parameterInput);
}
