package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T21DaftarPenawaranSales;

public interface T21DaftarPenawaranSalesService {
	public void saveOrUpdate(T21DaftarPenawaranSales t21DaftarPenawaranSales);
	public void update(T21DaftarPenawaranSales t21DaftarPenawaranSales);
	public String insert(T21DaftarPenawaranSales t21DaftarPenawaranSales);
	public void save(T21DaftarPenawaranSales t21DaftarPenawaranSales);
	public void delete(T21DaftarPenawaranSales t21DaftarPenawaranSales);
	public List<T21DaftarPenawaranSales> getListT21DaftarPenawaranSales(Map<Object, Object> parameterInput);
}
