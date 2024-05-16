package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T12PsAdjPrice;

public interface T12PsAdjPriceService {
	public void saveOrUpdate(T12PsAdjPrice t12PsAdjPrice);
	public void update(T12PsAdjPrice t12PsAdjPrice);
	public void save(T12PsAdjPrice t12PsAdjPrice);
	public void delete(T12PsAdjPrice t12PsAdjPrice);
	public List<T12PsAdjPrice> getListT12PsAdjPrice(Map<Object, Object> parameterInput);
}
