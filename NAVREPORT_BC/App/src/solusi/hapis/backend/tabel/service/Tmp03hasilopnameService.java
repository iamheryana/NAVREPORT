package solusi.hapis.backend.tabel.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.Tmp03hasilopname;

public interface Tmp03hasilopnameService {
	public void saveOrUpdate(Tmp03hasilopname tmp03hasilopname);
	public void update(Tmp03hasilopname tmp03hasilopname);
	public void save(Tmp03hasilopname tmp03hasilopname);
	public void save(List<Tmp03hasilopname> tmp03hasilopnames);
	public void delete(Tmp03hasilopname tmp03hasilopname);
	public void delete(List<Tmp03hasilopname> tmp03hasilopnames);
	public List<Tmp03hasilopname> getListTmp03hasilopname(Map<Object, Object> parameterInput);
}
