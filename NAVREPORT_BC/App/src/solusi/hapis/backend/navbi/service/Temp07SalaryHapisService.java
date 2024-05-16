package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp07SalaryHapis;

public interface Temp07SalaryHapisService {
	public void saveOrUpdate(Temp07SalaryHapis temp07SalaryHapis);
	public void update(Temp07SalaryHapis temp07SalaryHapis);
	public void save(Temp07SalaryHapis temp07SalaryHapis);
	public void save(List<Temp07SalaryHapis> temp07SalaryHapiss);
	public void delete(Temp07SalaryHapis temp07SalaryHapis);
	public void delete(List<Temp07SalaryHapis> temp07SalaryHapiss);
	public List<Temp07SalaryHapis> getListTemp07SalaryHapis(Map<Object, Object> parameterInput);

}
