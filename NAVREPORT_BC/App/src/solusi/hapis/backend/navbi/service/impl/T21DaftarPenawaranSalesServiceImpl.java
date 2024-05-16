package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T21DaftarPenawaranSalesDAO;
import solusi.hapis.backend.navbi.model.T21DaftarPenawaranSales;
import solusi.hapis.backend.navbi.service.T21DaftarPenawaranSalesService;

public class T21DaftarPenawaranSalesServiceImpl implements T21DaftarPenawaranSalesService{
	T21DaftarPenawaranSalesDAO t21DaftarPenawaranSalesDAO;

	public T21DaftarPenawaranSalesDAO getT21DaftarPenawaranSalesDAO() {
		return t21DaftarPenawaranSalesDAO;
	}

	public void setT21DaftarPenawaranSalesDAO(T21DaftarPenawaranSalesDAO t21DaftarPenawaranSalesDAO) {
		this.t21DaftarPenawaranSalesDAO = t21DaftarPenawaranSalesDAO;
	}

	@Override
	public void saveOrUpdate(T21DaftarPenawaranSales t21DaftarPenawaranSales) {
		t21DaftarPenawaranSalesDAO.saveOrUpdate(t21DaftarPenawaranSales);		
	}

	@Override
	public void update(T21DaftarPenawaranSales t21DaftarPenawaranSales) {
		t21DaftarPenawaranSalesDAO.update(t21DaftarPenawaranSales);
	}

	@Override
	public void save(T21DaftarPenawaranSales t21DaftarPenawaranSales) {
		t21DaftarPenawaranSalesDAO.save(t21DaftarPenawaranSales);		
	}

	@Override
	public void delete(T21DaftarPenawaranSales t21DaftarPenawaranSales) {
		t21DaftarPenawaranSalesDAO.delete(t21DaftarPenawaranSales);		
	}

	@Override
	public List<T21DaftarPenawaranSales> getListT21DaftarPenawaranSales(Map<Object, Object> parameterInput) {
		return t21DaftarPenawaranSalesDAO.getListT21DaftarPenawaranSales(parameterInput);
	}

	@Override
	public String insert(T21DaftarPenawaranSales t21DaftarPenawaranSales) {
		t21DaftarPenawaranSalesDAO.save(t21DaftarPenawaranSales);		
		
		String vNoPenawaran = t21DaftarPenawaranSalesDAO.callGetNoPenawaran(t21DaftarPenawaranSales.getNoPenawaran());
			
		return vNoPenawaran;
	}
	
	
}
