package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.M01PeriodeCosting;

public interface M01PeriodeCostingService {
	public void saveOrUpdate(M01PeriodeCosting m01PeriodeCosting);
	public void update(M01PeriodeCosting m01PeriodeCosting);
	public void save(M01PeriodeCosting m01PeriodeCosting);
	public void delete(M01PeriodeCosting m01PeriodeCosting);
	public List<M01PeriodeCosting> getListM01PeriodeCosting(Map<Object, Object> parameterInput);

}
