package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T03CetakSlip;

public interface T03CetakSlipService {
	public void saveOrUpdate(T03CetakSlip t03CetakSlip);
	public void update(T03CetakSlip t03CetakSlip);
	public void save(T03CetakSlip t03CetakSlip);
	public void delete(T03CetakSlip t03CetakSlip);
	public List<T03CetakSlip> getListT03CetakSlip(Map<Object, Object> parameterInput);
}
