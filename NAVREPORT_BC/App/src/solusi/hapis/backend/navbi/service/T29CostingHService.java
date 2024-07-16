package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T29CostingH;
import solusi.hapis.backend.navbi.model.T30CostingDHw3ps;
import solusi.hapis.backend.navbi.model.T31CostingDAcsps;
import solusi.hapis.backend.navbi.model.T32CostingDOwnsw;
import solusi.hapis.backend.navbi.model.T33CostingDOther;

public interface T29CostingHService {
	public void insert(T29CostingH t29CostingH);
	public void update(T29CostingH t29CostingH, 
			List<T30CostingDHw3ps> listDetail1Delete,
			List<T31CostingDAcsps> listDetail2Delete, 
			List<T32CostingDOwnsw> listDetail3Delete, 
			List<T33CostingDOther> listDetail4Delete);
	public void delete(T29CostingH t29CostingH);
	
	public List<T29CostingH> getListT29CostingH(Map<Object, Object> parameterInput);
	public List<T30CostingDHw3ps> getListT30CostingDHw3ps(Map<Object, Object> parameterInput);
	public List<T31CostingDAcsps> getListT31CostingDAcsps(Map<Object, Object> parameterInput);
	public List<T32CostingDOwnsw> getListT32CostingDOwnsw(Map<Object, Object> parameterInput);
	public List<T33CostingDOther> getListT33CostingDOther(Map<Object, Object> parameterInput);
}
