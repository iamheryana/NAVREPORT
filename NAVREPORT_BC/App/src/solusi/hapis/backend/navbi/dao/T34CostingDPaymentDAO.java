package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T34CostingDPayment;

public interface T34CostingDPaymentDAO {
	public void saveOrUpdate(T34CostingDPayment t34CostingDPayment);
	public void update(T34CostingDPayment t34CostingDPayment);
	public void save(T34CostingDPayment t34CostingDPayment);
	public void delete(T34CostingDPayment t34CostingDPayment);
	public void flush();
	public List<T34CostingDPayment> getListT34CostingDPayment(Map<Object, Object> parameterInput);
}
