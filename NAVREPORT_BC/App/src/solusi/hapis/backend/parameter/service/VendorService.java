package solusi.hapis.backend.parameter.service;

import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;

public interface VendorService {
	public ResultObject getListVendorLOV(Map<Object, Object> parameterInput, int start, int pageSize);
}
