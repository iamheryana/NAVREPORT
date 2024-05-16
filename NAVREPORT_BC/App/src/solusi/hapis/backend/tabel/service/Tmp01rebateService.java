package solusi.hapis.backend.tabel.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.Tmp01rebate;

public interface Tmp01rebateService {
	public void saveOrUpdate(Tmp01rebate tmp01rebate);
	public void update(Tmp01rebate tmp01rebate);
	public void save(Tmp01rebate tmp01rebate);
	public void save(List<Tmp01rebate> tmp01rebates);
	public void delete(Tmp01rebate tmp01rebate);
	public void delete(List<Tmp01rebate> tmp01rebates);
	public List<Tmp01rebate> getListTmp01rebate(Map<Object, Object> parameterInput);
}
