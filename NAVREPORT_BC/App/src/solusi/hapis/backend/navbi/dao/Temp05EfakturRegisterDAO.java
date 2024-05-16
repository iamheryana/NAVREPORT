package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp05EfakturRegister;

public interface Temp05EfakturRegisterDAO {
	public void saveOrUpdate(Temp05EfakturRegister temp05EfakturRegister);
	public void update(Temp05EfakturRegister temp05EfakturRegister);
	public void save(Temp05EfakturRegister temp05EfakturRegister);
	public void delete(Temp05EfakturRegister temp05EfakturRegister);
	public List<Temp05EfakturRegister> getListTemp05EfakturRegister(Map<Object, Object> parameterInput);

}
