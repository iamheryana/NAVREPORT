package solusi.hapis.backend.navbi.service.impl;


import java.util.List;
import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.dao.M02SalespersonDAO;
import solusi.hapis.backend.navbi.dao.M03TargetsalesDAO;
import solusi.hapis.backend.navbi.model.M02Salesperson;
import solusi.hapis.backend.navbi.model.M03Targetsales;
import solusi.hapis.backend.navbi.service.M02SalespersonService;
import solusi.hapis.common.CommonUtils;

public class M02SalespersonServiceImpl implements M02SalespersonService {
	private M02SalespersonDAO m02SalespersonDAO;
	private M03TargetsalesDAO m03TargetsalesDAO;
	
	public M02SalespersonDAO getM02SalespersonDAO() {
		return m02SalespersonDAO;
	}

	public void setM02SalespersonDAO(M02SalespersonDAO m02SalespersonDAO) {
		this.m02SalespersonDAO = m02SalespersonDAO;
	}
	
	
	public M03TargetsalesDAO getM03TargetsalesDAO() {
		return m03TargetsalesDAO;
	}

	public void setM03TargetsalesDAO(M03TargetsalesDAO m03TargetsalesDAO) {
		this.m03TargetsalesDAO = m03TargetsalesDAO;
	}

	@Override
	public void delete(M02Salesperson m02Salesperson) {
		m02SalespersonDAO.delete(m02Salesperson);
		
	}

	@Override
	public List<M02Salesperson> getListM02Salesperson(
			Map<Object, Object> parameterInput) {
		return m02SalespersonDAO.getListM02Salesperson(parameterInput);
	}

	@Override
	public ResultObject getListM02SalespersonLOV(
			Map<Object, Object> parameterInput, int start, int pageSize) {

		return m02SalespersonDAO.getListM02SalespersonLOV(parameterInput, start, pageSize);
	}

	@Override
	public ResultObject getListM02SalespersonLOVFilter(
			Map<Object, Object> parameterInput, int start, int pageSize) {
		return m02SalespersonDAO.getListM02SalespersonLOVFilter(parameterInput, start, pageSize);
	}

	@Override
	public List<M03Targetsales> getListM03Targetsales(
			Map<Object, Object> parameterInput) {

		return m03TargetsalesDAO.getListM03Targetsales(parameterInput);
	}

	@Override
	public void insert(M02Salesperson m02Salesperson) {
		m02SalespersonDAO.save(m02Salesperson);
		
		M02Salesperson aHeader = m02SalespersonDAO.getM02SalespersonBySales(m02Salesperson.getSales());
		
		if(CommonUtils.isNotEmpty(m02Salesperson.getM03Targetsaless())){
			for (M03Targetsales m03 : m02Salesperson.getM03Targetsaless()) {
				m03.setM02Salesperson(aHeader);
				m03TargetsalesDAO.save(m03);
			}
		}
		
	}

	@Override
	public void update(M02Salesperson m02Salesperson,
			List<M03Targetsales> listDetailDelete) {
		// Update Header
		m02SalespersonDAO.update(m02Salesperson);
		
		// Update Detail 
		// - Hapus yang sudah tidak ada
		// - tambahkan yang baru 
		// - update jika sudah ada
		
		if (CommonUtils.isNotEmpty(listDetailDelete)) {
			for (M03Targetsales deleteM03 : listDetailDelete) {
				m03TargetsalesDAO.delete(deleteM03);
				m03TargetsalesDAO.flush();
			}
		}
		
		if (CommonUtils.isNotEmpty(m02Salesperson.getM03Targetsaless())){
			for (M03Targetsales m03 : m02Salesperson.getM03Targetsaless()) {
				m03.setM02Salesperson(m02Salesperson);
				if (m03.getM03Id() > 0) {
					m03TargetsalesDAO.update(m03);
				} else {
					m03TargetsalesDAO.save(m03);
				}			
			}
		}
		
	}
	
	
}
