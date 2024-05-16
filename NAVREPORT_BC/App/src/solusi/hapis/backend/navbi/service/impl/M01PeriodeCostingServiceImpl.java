package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.M01PeriodeCostingDAO;
import solusi.hapis.backend.navbi.model.M01PeriodeCosting;
import solusi.hapis.backend.navbi.service.M01PeriodeCostingService;

public class M01PeriodeCostingServiceImpl implements M01PeriodeCostingService {
	private M01PeriodeCostingDAO m01PeriodeCostingDAO;
	
	public M01PeriodeCostingDAO getM01PeriodeCostingDAO() {
		return m01PeriodeCostingDAO;
	}

	public void setM01PeriodeCostingDAO(M01PeriodeCostingDAO m01PeriodeCostingDAO) {
		this.m01PeriodeCostingDAO = m01PeriodeCostingDAO;
	}

	@Override
	public void saveOrUpdate(M01PeriodeCosting m01PeriodeCosting) {
		m01PeriodeCostingDAO.saveOrUpdate(m01PeriodeCosting);
		
	}

	@Override
	public void update(M01PeriodeCosting m01PeriodeCosting) {
		m01PeriodeCostingDAO.update(m01PeriodeCosting);
		
	}

	@Override
	public void save(M01PeriodeCosting m01PeriodeCosting) {
		m01PeriodeCostingDAO.save(m01PeriodeCosting);
		
	}

	@Override
	public void delete(M01PeriodeCosting m01PeriodeCosting) {
		m01PeriodeCostingDAO.delete(m01PeriodeCosting);
		
	}

	@Override
	public List<M01PeriodeCosting> getListM01PeriodeCosting(
			Map<Object, Object> parameterInput) {
		return m01PeriodeCostingDAO.getListM01PeriodeCosting(parameterInput);
	}

	
	
}

