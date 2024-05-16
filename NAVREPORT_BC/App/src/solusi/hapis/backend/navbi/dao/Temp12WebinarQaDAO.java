package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp12WebinarQa;

public interface Temp12WebinarQaDAO {
	public void saveOrUpdate(Temp12WebinarQa temp12WebinarQa);
	public void update(Temp12WebinarQa temp12WebinarQa);
	public void save(Temp12WebinarQa temp12WebinarQa);
	public void delete(Temp12WebinarQa temp12WebinarQa);
	public List<Temp12WebinarQa> getListTemp12WebinarQa(Map<Object, Object> parameterInput);

}
