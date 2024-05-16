package solusi.hapis.backend.tabel.dao;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.model.Tmp06invoicelunas;

public interface Tmp06invoicelunasDAO {
	public void saveOrUpdate(Tmp06invoicelunas tmp06invoicelunas);
	public void update(Tmp06invoicelunas tmp06invoicelunas);
	public void save(Tmp06invoicelunas tmp06invoicelunas);
	public void delete(Tmp06invoicelunas tmp06invoicelunas);
	public List<Tmp06invoicelunas> getListTmp06invoicelunas(Map<Object, Object> parameterInput);
}
