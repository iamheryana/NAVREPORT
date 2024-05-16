package solusi.hapis.backend.parameter.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.parameter.model.Cabang;

public interface CabangService {
	public List<Cabang> getCabang(Map<Object, Object> parameterInput);
}
