package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.M07UserroleCostingHDAO;
import solusi.hapis.backend.navbi.dao.M08UserroleCostingDDAO;
import solusi.hapis.backend.navbi.model.M07UserroleCostingH;
import solusi.hapis.backend.navbi.model.M08UserroleCostingD;
import solusi.hapis.backend.navbi.service.M07UserroleCostingHService;
import solusi.hapis.common.CommonUtils;

public class M07UserroleCostingHServiceImpl implements M07UserroleCostingHService{
	private M07UserroleCostingHDAO m07UserroleCostingHDAO;
	private M08UserroleCostingDDAO m08UserroleCostingDDAO;
	
	public M07UserroleCostingHDAO getM07UserroleCostingHDAO() {
		return m07UserroleCostingHDAO;
	}
	
	public void setM07UserroleCostingHDAO(
			M07UserroleCostingHDAO m07UserroleCostingHDAO) {
		this.m07UserroleCostingHDAO = m07UserroleCostingHDAO;
	}
	
	public M08UserroleCostingDDAO getM08UserroleCostingDDAO() {
		return m08UserroleCostingDDAO;
	}
	
	public void setM08UserroleCostingDDAO(
			M08UserroleCostingDDAO m08UserroleCostingDDAO) {
		this.m08UserroleCostingDDAO = m08UserroleCostingDDAO;
	}

	@Override
	public void insert(M07UserroleCostingH m07UserroleCostingH) {
		m07UserroleCostingHDAO.save(m07UserroleCostingH);
		
		M07UserroleCostingH aHeader = m07UserroleCostingHDAO.getM07UserroleCostingHByUsername(m07UserroleCostingH.getUsername());
		
		if(CommonUtils.isNotEmpty(m07UserroleCostingH.getM08UserroleCostingDs())){
			for (M08UserroleCostingD m08 : m07UserroleCostingH.getM08UserroleCostingDs()) {
				m08.setM07UserroleCostingH(aHeader);
				m08UserroleCostingDDAO.save(m08);
			}
		}
		
	}

	@Override
	public void update(M07UserroleCostingH m07UserroleCostingH,
			List<M08UserroleCostingD> listDetailDelete) {
		// Update Header
		m07UserroleCostingHDAO.update(m07UserroleCostingH);
		
		// Update Detail 
		// - Hapus yang sudah tidak ada
		// - tambahkan yang baru 
		// - update jika sudah ada
		
		if (CommonUtils.isNotEmpty(listDetailDelete)) {
			for (M08UserroleCostingD deleteM03 : listDetailDelete) {
				m08UserroleCostingDDAO.delete(deleteM03);
				m08UserroleCostingDDAO.flush();
			}
		}
		
		if (CommonUtils.isNotEmpty(m07UserroleCostingH.getM08UserroleCostingDs())){
			for (M08UserroleCostingD m08 : m07UserroleCostingH.getM08UserroleCostingDs()) {
				m08.setM07UserroleCostingH(m07UserroleCostingH);
				if (m08.getM08Id() > 0) {
					m08UserroleCostingDDAO.update(m08);
				} else {
					m08UserroleCostingDDAO.save(m08);
				}			
			}
		}
		
	}

	@Override
	public void delete(M07UserroleCostingH m07UserroleCostingH) {
		m07UserroleCostingHDAO.delete(m07UserroleCostingH);
		
	}

	@Override
	public List<M07UserroleCostingH> getListM07UserroleCostingH(
			Map<Object, Object> parameterInput) {
		return m07UserroleCostingHDAO.getListM07UserroleCostingH(parameterInput);
	}

	@Override
	public List<M08UserroleCostingD> getListM08UserroleCostingD(
			Map<Object, Object> parameterInput) {
		return m08UserroleCostingDDAO.getListM08UserroleCostingD(parameterInput);
	}
	
	
	
}
