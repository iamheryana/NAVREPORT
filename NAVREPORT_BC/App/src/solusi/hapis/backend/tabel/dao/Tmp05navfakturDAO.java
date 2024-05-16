package solusi.hapis.backend.tabel.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.Tmp05navfaktur;

public interface Tmp05navfakturDAO {
	public void saveOrUpdate(Tmp05navfaktur tmp05navfaktur);
	public void update(Tmp05navfaktur tmp05navfaktur);
	public void save(Tmp05navfaktur tmp05navfaktur);
	public void delete(Tmp05navfaktur tmp05navfaktur);
	public List<Tmp05navfaktur> getListTmp05navfaktur(Map<Object, Object> parameterInput);
}
