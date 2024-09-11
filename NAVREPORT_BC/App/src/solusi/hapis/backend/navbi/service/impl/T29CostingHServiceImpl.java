package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.T29CostingHDAO;
import solusi.hapis.backend.navbi.dao.T30CostingDHw3psDAO;
import solusi.hapis.backend.navbi.dao.T31CostingDAcspsDAO;
import solusi.hapis.backend.navbi.dao.T32CostingDOwnswDAO;
import solusi.hapis.backend.navbi.dao.T33CostingDOtherDAO;
import solusi.hapis.backend.navbi.model.T29CostingH;
import solusi.hapis.backend.navbi.model.T30CostingDHw3ps;
import solusi.hapis.backend.navbi.model.T31CostingDAcsps;
import solusi.hapis.backend.navbi.model.T32CostingDOwnsw;
import solusi.hapis.backend.navbi.model.T33CostingDOther;
import solusi.hapis.backend.navbi.service.T29CostingHService;
import solusi.hapis.common.CommonUtils;

public class T29CostingHServiceImpl implements T29CostingHService {
	T29CostingHDAO t29CostingHDAO;
	T30CostingDHw3psDAO t30CostingDHw3psDAO;
	T31CostingDAcspsDAO t31CostingDAcspsDAO;
	T32CostingDOwnswDAO t32CostingDOwnswDAO;
	T33CostingDOtherDAO t33CostingDOtherDAO;
	
	@Override
	public String insert(T29CostingH t29CostingH) {
		
		t29CostingHDAO.save(t29CostingH);
		
		String vNoCosting = t29CostingHDAO.callGetNoCosting(t29CostingH.getNoCosting());
		
		T29CostingH aHeader = t29CostingHDAO.getT29CostingHByNoCosting(vNoCosting);		
		
		if(CommonUtils.isNotEmpty(t29CostingH.getT30CostingDHw3pss())){
			for (T30CostingDHw3ps t30 : t29CostingH.getT30CostingDHw3pss()) {
				t30.setT29CostingH(aHeader);
				t30CostingDHw3psDAO.save(t30);
			}
		}
		
		if(CommonUtils.isNotEmpty(t29CostingH.getT31CostingDAcspss())){
			for (T31CostingDAcsps t31: t29CostingH.getT31CostingDAcspss()) {
				t31.setT29CostingH(aHeader);
				t31CostingDAcspsDAO.save(t31);
			}
		}
		
		if(CommonUtils.isNotEmpty(t29CostingH.getT32CostingDOwnsws())){
			for (T32CostingDOwnsw t32 : t29CostingH.getT32CostingDOwnsws()) {
				t32.setT29CostingH(aHeader);
				t32CostingDOwnswDAO.save(t32);
			}
		}
		
		if(CommonUtils.isNotEmpty(t29CostingH.getT33CostingDOthers())){
			for (T33CostingDOther t33 : t29CostingH.getT33CostingDOthers()) {
				t33.setT29CostingH(aHeader);
				t33CostingDOtherDAO.save(t33);
			}
		}
		
		return vNoCosting;
		
	}

	@Override
	public void update(T29CostingH t29CostingH,
			List<T30CostingDHw3ps> listDetail1Delete,
			List<T31CostingDAcsps> listDetail2Delete,
			List<T32CostingDOwnsw> listDetail3Delete,
			List<T33CostingDOther> listDetail4Delete) {
		
		// Update Header
		t29CostingHDAO.update(t29CostingH);
		
		// Update Detail 
		// - Hapus yang sudah tidak ada
		// - tambahkan yang baru 
		// - update jika sudah ada
		
		// Detail 1 
		if (CommonUtils.isNotEmpty(listDetail1Delete)) {
			for (T30CostingDHw3ps deleteT30 : listDetail1Delete) {
				t30CostingDHw3psDAO.delete(deleteT30);
				t30CostingDHw3psDAO.flush();
			}
		}
		
		if (CommonUtils.isNotEmpty(t29CostingH.getT30CostingDHw3pss())){
			for (T30CostingDHw3ps t30 : t29CostingH.getT30CostingDHw3pss()) {
				t30.setT29CostingH(t29CostingH);
				if (t30.getT30Id() > 0) {
					t30CostingDHw3psDAO.update(t30);
				} else {
					t30CostingDHw3psDAO.save(t30);
				}			
			}
		}
		
		// Detail 2 
		if (CommonUtils.isNotEmpty(listDetail2Delete)) {
			for (T31CostingDAcsps deleteT31 : listDetail2Delete) {
				t31CostingDAcspsDAO.delete(deleteT31);
				t31CostingDAcspsDAO.flush();
			}
		}
		
		if (CommonUtils.isNotEmpty(t29CostingH.getT31CostingDAcspss())){
			for (T31CostingDAcsps t31 : t29CostingH.getT31CostingDAcspss()) {
				t31.setT29CostingH(t29CostingH);
				if (t31.getT31Id() > 0) {
					t31CostingDAcspsDAO.update(t31);
				} else {
					t31CostingDAcspsDAO.save(t31);
				}			
			}
		}
		

		// Detail 3 
		if (CommonUtils.isNotEmpty(listDetail3Delete)) {
			for (T32CostingDOwnsw deleteT32 : listDetail3Delete) {
				t32CostingDOwnswDAO.delete(deleteT32);
				t32CostingDOwnswDAO.flush();
			}
		}
		
		if (CommonUtils.isNotEmpty(t29CostingH.getT32CostingDOwnsws())){
			for (T32CostingDOwnsw t32 : t29CostingH.getT32CostingDOwnsws()) {
				t32.setT29CostingH(t29CostingH);
				if (t32.getT32Id() > 0) {
					t32CostingDOwnswDAO.update(t32);
				} else {
					t32CostingDOwnswDAO.save(t32);
				}			
			}
		}
		
		// Detail 4 
		if (CommonUtils.isNotEmpty(listDetail4Delete)) {
			for (T33CostingDOther deleteT33 : listDetail4Delete) {
				t33CostingDOtherDAO.delete(deleteT33);
				t33CostingDOtherDAO.flush();
			}
		}
		
		if (CommonUtils.isNotEmpty(t29CostingH.getT33CostingDOthers())){
			for (T33CostingDOther t33 : t29CostingH.getT33CostingDOthers()) {
				t33.setT29CostingH(t29CostingH);
				if (t33.getT33Id() > 0) {
					t33CostingDOtherDAO.update(t33);
				} else {
					t33CostingDOtherDAO.save(t33);
				}			
			}
		}
		
	}

	
	@Override
	public void delete(T29CostingH t29CostingH) {
		t29CostingHDAO.delete(t29CostingH);		
	}

	@Override
	public List<T29CostingH> getListT29CostingH(
			Map<Object, Object> parameterInput) {
		return t29CostingHDAO.getListT29CostingH(parameterInput);		
	}

	@Override
	public List<T30CostingDHw3ps> getListT30CostingDHw3ps(
			Map<Object, Object> parameterInput) {
		return t30CostingDHw3psDAO.getListT30CostingDHw3ps(parameterInput);
	}

	@Override
	public List<T31CostingDAcsps> getListT31CostingDAcsps(
			Map<Object, Object> parameterInput) {
		return t31CostingDAcspsDAO.getListT31CostingDAcsps(parameterInput);
	}

	@Override
	public List<T32CostingDOwnsw> getListT32CostingDOwnsw(
			Map<Object, Object> parameterInput) {
		return t32CostingDOwnswDAO.getListT32CostingDOwnsw(parameterInput);
	}

	@Override
	public List<T33CostingDOther> getListT33CostingDOther(
			Map<Object, Object> parameterInput) {
		return t33CostingDOtherDAO.getListT33CostingDOther(parameterInput);
	}


	@Override
	public void updateNamaFile(T29CostingH t29CostingH) {
		t29CostingHDAO.update(t29CostingH);
		
	}
	
	public T29CostingHDAO getT29CostingHDAO() {
		return t29CostingHDAO;
	}

	public void setT29CostingHDAO(T29CostingHDAO t29CostingHDAO) {
		this.t29CostingHDAO = t29CostingHDAO;
	}

	public T30CostingDHw3psDAO getT30CostingDHw3psDAO() {
		return t30CostingDHw3psDAO;
	}

	public void setT30CostingDHw3psDAO(T30CostingDHw3psDAO t30CostingDHw3psDAO) {
		this.t30CostingDHw3psDAO = t30CostingDHw3psDAO;
	}

	public T31CostingDAcspsDAO getT31CostingDAcspsDAO() {
		return t31CostingDAcspsDAO;
	}

	public void setT31CostingDAcspsDAO(T31CostingDAcspsDAO t31CostingDAcspsDAO) {
		this.t31CostingDAcspsDAO = t31CostingDAcspsDAO;
	}

	public T32CostingDOwnswDAO getT32CostingDOwnswDAO() {
		return t32CostingDOwnswDAO;
	}

	public void setT32CostingDOwnswDAO(T32CostingDOwnswDAO t32CostingDOwnswDAO) {
		this.t32CostingDOwnswDAO = t32CostingDOwnswDAO;
	}

	public T33CostingDOtherDAO getT33CostingDOtherDAO() {
		return t33CostingDOtherDAO;
	}

	public void setT33CostingDOtherDAO(T33CostingDOtherDAO t33CostingDOtherDAO) {
		this.t33CostingDOtherDAO = t33CostingDOtherDAO;
	}



	
	
}
