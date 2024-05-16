package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.tabel.dao.T03salespersonDAO;
import solusi.hapis.backend.tabel.dao.T08targetsalesDAO;
import solusi.hapis.backend.tabel.model.T03salesperson;
import solusi.hapis.backend.tabel.model.T08targetsales;
import solusi.hapis.backend.tabel.service.T03salespersonService;
import solusi.hapis.common.CommonUtils;

public class T03salespersonServiceImpl implements T03salespersonService {
	private T03salespersonDAO t03salespersonDAO;
	private T08targetsalesDAO t08targetsalesDAO;
	
	public T03salespersonDAO getT03salespersonDAO() {
		return t03salespersonDAO;
	}

	public void setT03salespersonDAO(T03salespersonDAO t03salespersonDAO) {
		this.t03salespersonDAO = t03salespersonDAO;
	}
	
	
	public T08targetsalesDAO getT08targetsalesDAO() {
		return t08targetsalesDAO;
	}

	public void setT08targetsalesDAO(T08targetsalesDAO t08targetsalesDAO) {
		this.t08targetsalesDAO = t08targetsalesDAO;
	}

//	@Override
//	public void saveOrUpdate(T03salesperson t03salesperson) {
//		t03salespersonDAO.saveOrUpdate(t03salesperson);
//		
//	}
//
//	@Override
//	public void update(T03salesperson t03salesperson) {
//		t03salespersonDAO.update(t03salesperson);
//		
//	}
//
//	@Override
//	public void save(T03salesperson t03salesperson) {
//		t03salespersonDAO.save(t03salesperson);
//		
//	}

	@Override
	public void delete(T03salesperson t03salesperson) {
		t03salespersonDAO.delete(t03salesperson);
		
	}

	@Override
	public List<T03salesperson> getListT03salesperson(
			Map<Object, Object> parameterInput) {
		return t03salespersonDAO.getListT03salesperson(parameterInput);
	}

	@Override
	public ResultObject getListT03salespersonLOV(
			Map<Object, Object> parameterInput, int start, int pageSize) {

		return t03salespersonDAO.getListT03salespersonLOV(parameterInput, start, pageSize);
	}

	@Override
	public ResultObject getListT03salespersonLOVFilter(
			Map<Object, Object> parameterInput, int start, int pageSize) {
		return t03salespersonDAO.getListT03salespersonLOVFilter(parameterInput, start, pageSize);
	}

	@Override
	public List<T08targetsales> getListT08targetsales(
			Map<Object, Object> parameterInput) {
		// TODO Auto-generated method stub
		return t08targetsalesDAO.getListT08targetsales(parameterInput);
	}

	@Override
	public void insert(T03salesperson t03salesperson) {
		t03salespersonDAO.save(t03salesperson);
		if(CommonUtils.isNotEmpty(t03salesperson.getT08targetsaless())){
			for (T08targetsales t08 : t03salesperson.getT08targetsaless()) {
				t08targetsalesDAO.save(t08);
			}
		}
		
	}

	@Override
	public void update(T03salesperson t03salesperson,
			List<T08targetsales> listDetailDelete) {
		// Update Header
		t03salespersonDAO.update(t03salesperson);
		
		// Update Detail 
		// - Hapus yang sudah tidak ada
		// - tambahkan yang baru 
		// - update jika sudah ada
		
		if (CommonUtils.isNotEmpty(listDetailDelete)) {
			for (T08targetsales deleteT08 : listDetailDelete) {
				t08targetsalesDAO.delete(deleteT08);
				t08targetsalesDAO.flush();
			}
		}
		
		if (CommonUtils.isNotEmpty(t03salesperson.getT08targetsaless())){
			for (T08targetsales t08 : t03salesperson.getT08targetsaless()) {
				t08.setT03salesperson(t03salesperson);
				if (t08.getT08Id() > 0) {
					t08targetsalesDAO.update(t08);
				} else {
					t08targetsalesDAO.save(t08);
				}			
			}
		}
		
	}
	
	
}
