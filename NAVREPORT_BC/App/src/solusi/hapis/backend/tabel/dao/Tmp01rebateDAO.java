package solusi.hapis.backend.tabel.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.Tmp01rebate;

public interface Tmp01rebateDAO {
	public void saveOrUpdate(Tmp01rebate tmp01rebate);
	public void update(Tmp01rebate tmp01rebate);
	public void save(Tmp01rebate tmp01rebate);
	public void delete(Tmp01rebate tmp01rebate);
	public List<Tmp01rebate> getListTmp01rebate(Map<Object, Object> parameterInput);
}
