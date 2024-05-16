package solusi.hapis.backend.navbi.service.impl;


import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.M06TargetPipelineDAO;
import solusi.hapis.backend.navbi.model.M06TargetPipeline;
import solusi.hapis.backend.navbi.service.M06TargetPipelineService;

public class M06TargetPipelineServiceImpl implements M06TargetPipelineService {
	private M06TargetPipelineDAO m06TargetPipelineDAO;
	
	public M06TargetPipelineDAO getM06TargetPipelineDAO() {
		return m06TargetPipelineDAO;
	}

	public void setM06TargetPipelineDAO(M06TargetPipelineDAO m06TargetPipelineDAO) {
		this.m06TargetPipelineDAO = m06TargetPipelineDAO;
	}

	@Override
	public void saveOrUpdate(M06TargetPipeline m06TargetPipeline) {
		m06TargetPipelineDAO.saveOrUpdate(m06TargetPipeline);
		
	}

	@Override
	public void update(M06TargetPipeline m06TargetPipeline) {
		m06TargetPipelineDAO.update(m06TargetPipeline);
		
	}

	@Override
	public void save(M06TargetPipeline m06TargetPipeline) {
		m06TargetPipelineDAO.save(m06TargetPipeline);
		
	}

	@Override
	public void delete(M06TargetPipeline m06TargetPipeline) {
		m06TargetPipelineDAO.delete(m06TargetPipeline);
		
	}

	@Override
	public List<M06TargetPipeline> getListM06TargetPipeline(
			Map<Object, Object> parameterInput) {
		return m06TargetPipelineDAO.getListM06TargetPipeline(parameterInput);
	}

	
	
}
