package solusi.hapis.backend.tabel.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.T04paramKomisi;

public interface T04paramKomisiService {
	public void saveOrUpdate(T04paramKomisi t04paramKomisi);
	public void update(T04paramKomisi t04paramKomisi);
	public void save(T04paramKomisi t04paramKomisi);
	public void delete(T04paramKomisi t04paramKomisi);
	
	public List<T04paramKomisi> getListT04paramKomisi(Map<Object, Object> parameterInput);
	
	
	public T04paramKomisi getT04paramKomisiByKode(String kode);
}
