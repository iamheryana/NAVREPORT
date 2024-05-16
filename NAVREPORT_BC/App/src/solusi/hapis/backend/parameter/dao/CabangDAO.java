package solusi.hapis.backend.parameter.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.parameter.model.Cabang;

public interface CabangDAO {
	public List<Cabang> getListCabang(Map<Object, Object> parameterInput);
}
