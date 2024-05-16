package solusi.hapis.backend.parameter.service.impl;

import java.util.Map;

import solusi.hapis.backend.bean.ResultObject;
import solusi.hapis.backend.parameter.dao.VendorDAO;
import solusi.hapis.backend.parameter.service.VendorService;

public class VendorServiceImpl implements VendorService{
	private  VendorDAO  vendorDAO ;
	
	public VendorDAO getVendorDAO() {
		return vendorDAO;
	}



	public void setVendorDAO(VendorDAO vendorDAO) {
		this.vendorDAO = vendorDAO;
	}



	@Override
	public ResultObject getListVendorLOV(Map<Object, Object> parameterInput,
			int start, int pageSize) {
		return vendorDAO.getListVendorLOV(parameterInput, start, pageSize);
	}

}
