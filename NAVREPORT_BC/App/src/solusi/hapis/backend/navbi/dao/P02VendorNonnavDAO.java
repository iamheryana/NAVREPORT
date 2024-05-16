package solusi.hapis.backend.navbi.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.navbi.model.P02VendorNonnav;

public interface P02VendorNonnavDAO {
	public void saveOrUpdate(P02VendorNonnav p02VendorNonnav);
	public void update(P02VendorNonnav p02VendorNonnav);
	public void save(P02VendorNonnav p02VendorNonnav);
	public void delete(P02VendorNonnav p02VendorNonnav);
	public List<P02VendorNonnav> getListP02VendorNonnav(Map<Object, Object> parameterInput);
	
	public P02VendorNonnav getP02VendorNonnavByKode(String kode);
	
	public ResultObject getListP02VendorNonnavLOV(Map<Object, Object> parameterInput, int start, int pageSize);

}
