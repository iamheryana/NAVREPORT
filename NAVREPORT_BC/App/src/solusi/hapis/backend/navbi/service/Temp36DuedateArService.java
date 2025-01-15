package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp36DuedateAr;

public interface Temp36DuedateArService {
	public void saveOrUpdate(Temp36DuedateAr temp36DuedateAr);
	public void update(Temp36DuedateAr temp36DuedateAr);
	public void save(Temp36DuedateAr temp36DuedateAr);
	public void save(List<Temp36DuedateAr> temp36DuedateArs);
	public void delete(Temp36DuedateAr temp36DuedateAr);
	public void delete(List<Temp36DuedateAr> temp36DuedateArs);
	public List<Temp36DuedateAr> getListTemp36DuedateAr(Map<Object, Object> parameterInput);
}
