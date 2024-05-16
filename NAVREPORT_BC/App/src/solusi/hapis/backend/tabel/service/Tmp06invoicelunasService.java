package solusi.hapis.backend.tabel.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.Tmp06invoicelunas;

public interface Tmp06invoicelunasService {
	public void saveOrUpdate(Tmp06invoicelunas tmp06invoicelunas);
	public void update(Tmp06invoicelunas tmp06invoicelunas);
	public void save(Tmp06invoicelunas tmp06invoicelunas);
	public void save(List<Tmp06invoicelunas> tmp06invoicelunass);
	public void delete(Tmp06invoicelunas tmp06invoicelunas);
	public void delete(List<Tmp06invoicelunas> tmp06invoicelunass);
	public List<Tmp06invoicelunas> getListTmp06invoicelunas(Map<Object, Object> parameterInput);
}
