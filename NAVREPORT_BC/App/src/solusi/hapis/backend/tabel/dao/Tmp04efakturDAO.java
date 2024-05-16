package solusi.hapis.backend.tabel.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.Tmp04efaktur;

public interface Tmp04efakturDAO {
	public void saveOrUpdate(Tmp04efaktur tmp04efaktur);
	public void update(Tmp04efaktur tmp04efaktur);
	public void save(Tmp04efaktur tmp04efaktur);
	public void delete(Tmp04efaktur tmp04efaktur);
	public List<Tmp04efaktur> getListTmp04efaktur(Map<Object, Object> parameterInput);
}
