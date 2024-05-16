package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T15SatindoAdj;

public interface T15SatindoAdjDAO {
	public void saveOrUpdate(T15SatindoAdj t15SatindoAdj);
	public void update(T15SatindoAdj t15SatindoAdj);
	public void save(T15SatindoAdj t15SatindoAdj);
	public void delete(T15SatindoAdj t15SatindoAdj);
	public List<T15SatindoAdj> getListT15SatindoAdj(Map<Object, Object> parameterInput);

}
