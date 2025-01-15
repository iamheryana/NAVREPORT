package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp36DuedateAr;

public interface Temp36DuedateArDAO {
	public void saveOrUpdate(Temp36DuedateAr temp36DuedateAr);
	public void update(Temp36DuedateAr temp36DuedateAr);
	public void save(Temp36DuedateAr temp36DuedateAr);
	public void delete(Temp36DuedateAr temp36DuedateAr);
	public List<Temp36DuedateAr> getListTemp36DuedateAr(Map<Object, Object> parameterInput);
}
