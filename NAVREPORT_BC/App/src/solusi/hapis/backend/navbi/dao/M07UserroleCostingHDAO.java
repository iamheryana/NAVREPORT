package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.M07UserroleCostingH;

public interface M07UserroleCostingHDAO {
	public void saveOrUpdate(M07UserroleCostingH m07UserroleCostingH);
	public void update(M07UserroleCostingH m07UserroleCostingH);
	public void save(M07UserroleCostingH m07UserroleCostingH);
	public void delete(M07UserroleCostingH m07UserroleCostingH);
	public void flush();
	public List<M07UserroleCostingH> getListM07UserroleCostingH(Map<Object, Object> parameterInput);
	public M07UserroleCostingH getM07UserroleCostingHByUsername (String Username);
}
