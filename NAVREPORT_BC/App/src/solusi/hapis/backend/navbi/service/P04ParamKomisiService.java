package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.P04ParamKomisi;

public interface P04ParamKomisiService {
	public void saveOrUpdate(P04ParamKomisi p04ParamKomisi);
	public void update(P04ParamKomisi p04ParamKomisi);
	public void save(P04ParamKomisi p04ParamKomisi);
	public void delete(P04ParamKomisi p04ParamKomisi);
	
	public List<P04ParamKomisi> getListP04ParamKomisi(Map<Object, Object> parameterInput);
	
	
	public P04ParamKomisi getP04ParamKomisiByKode(String kode);
}
