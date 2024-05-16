package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T04BayarAngsuran;

public interface T04BayarAngsuranService {
	public void saveOrUpdate(T04BayarAngsuran t04BayarAngsuran);
	public void update(T04BayarAngsuran t04BayarAngsuran);
	public void save(T04BayarAngsuran t04BayarAngsuran);
	public void delete(T04BayarAngsuran t04BayarAngsuran);
	public List<T04BayarAngsuran> getListT04BayarAngsuran(Map<Object, Object> parameterInput);

}
