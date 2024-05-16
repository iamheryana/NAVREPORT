package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.M06TargetPipeline;

public interface M06TargetPipelineDAO {
	public void saveOrUpdate(M06TargetPipeline m06TargetPipeline);
	public void update(M06TargetPipeline m06TargetPipeline);
	public void save(M06TargetPipeline m06TargetPipeline);
	public void delete(M06TargetPipeline m06TargetPipeline);
	public List<M06TargetPipeline> getListM06TargetPipeline(Map<Object, Object> parameterInput);
}
