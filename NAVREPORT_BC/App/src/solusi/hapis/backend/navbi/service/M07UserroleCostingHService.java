package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.M07UserroleCostingH;
import solusi.hapis.backend.navbi.model.M08UserroleCostingD;

public interface M07UserroleCostingHService {
	public void insert(M07UserroleCostingH m07UserroleCostingH);
	public void update(M07UserroleCostingH m07UserroleCostingH, List<M08UserroleCostingD> listDetailDelete);
	public void delete(M07UserroleCostingH m07UserroleCostingH);
	
	public List<M07UserroleCostingH> getListM07UserroleCostingH(Map<Object, Object> parameterInput);
	public List<M08UserroleCostingD> getListM08UserroleCostingD(Map<Object, Object> parameterInput);
}
