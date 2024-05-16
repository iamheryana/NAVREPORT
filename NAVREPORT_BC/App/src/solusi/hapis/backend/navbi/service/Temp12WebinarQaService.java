package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp12WebinarQa;

public interface Temp12WebinarQaService {
	public void saveOrUpdate(Temp12WebinarQa temp12WebinarQa);
	public void update(Temp12WebinarQa temp12WebinarQa);
	public void save(Temp12WebinarQa temp12WebinarQa);
	public void save(List<Temp12WebinarQa> temp12WebinarQas);
	public void delete(Temp12WebinarQa temp12WebinarQa);
	public void delete(List<Temp12WebinarQa> temp12WebinarQas);
	public List<Temp12WebinarQa> getListTemp12WebinarQa(Map<Object, Object> parameterInput);

}
