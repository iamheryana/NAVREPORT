package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.P01BiayaTrans;

public interface P01BiayaTransDAO {
	public void saveOrUpdate(P01BiayaTrans p01BiayaTrans);
	public void update(P01BiayaTrans p01BiayaTrans);
	public void save(P01BiayaTrans p01BiayaTrans);
	public void delete(P01BiayaTrans p01BiayaTrans);
	
	public List<P01BiayaTrans> getListP01BiayaTrans(Map<Object, Object> parameterInput);

	public P01BiayaTrans getP01BiayaTransByKode(String kode);
}
