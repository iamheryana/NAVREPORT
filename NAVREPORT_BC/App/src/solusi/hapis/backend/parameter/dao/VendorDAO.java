package solusi.hapis.backend.parameter.dao;

import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;

public interface VendorDAO {
	public ResultObject getListVendorLOV(Map<Object, Object> parameterInput, int start, int pageSize);
}
