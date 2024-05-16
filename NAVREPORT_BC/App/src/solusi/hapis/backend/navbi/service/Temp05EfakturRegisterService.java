package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp05EfakturRegister;

public interface Temp05EfakturRegisterService {
	public void saveOrUpdate(Temp05EfakturRegister temp05EfakturRegister);
	public void update(Temp05EfakturRegister temp05EfakturRegister);
	public void save(Temp05EfakturRegister temp05EfakturRegister);
	public void save(List<Temp05EfakturRegister> temp05EfakturRegisters);
	public void delete(Temp05EfakturRegister temp05EfakturRegister);
	public void delete(List<Temp05EfakturRegister> temp05EfakturRegisters);
	public List<Temp05EfakturRegister> getListTemp05EfakturRegister(Map<Object, Object> parameterInput);

}
