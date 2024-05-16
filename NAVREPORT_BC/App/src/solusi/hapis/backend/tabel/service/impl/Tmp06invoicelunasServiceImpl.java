package solusi.hapis.backend.tabel.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.tabel.dao.Tmp06invoicelunasDAO;
import solusi.hapis.backend.tabel.model.Tmp06invoicelunas;
import solusi.hapis.backend.tabel.service.Tmp06invoicelunasService;

public class Tmp06invoicelunasServiceImpl implements Tmp06invoicelunasService {
	private Tmp06invoicelunasDAO tmp06invoicelunasDAO;
	
	
	
	public Tmp06invoicelunasDAO getTmp06invoicelunasDAO() {
		return tmp06invoicelunasDAO;
	}

	public void setTmp06invoicelunasDAO(Tmp06invoicelunasDAO tmp06invoicelunasDAO) {
		this.tmp06invoicelunasDAO = tmp06invoicelunasDAO;
	}

	@Override
	public void saveOrUpdate(Tmp06invoicelunas tmp06invoicelunas) {
		tmp06invoicelunasDAO.saveOrUpdate(tmp06invoicelunas);	
	}

	@Override
	public void update(Tmp06invoicelunas tmp06invoicelunas) {
		tmp06invoicelunasDAO.update(tmp06invoicelunas);		
	}

	@Override
	public void save(Tmp06invoicelunas tmp06invoicelunas) {
		tmp06invoicelunasDAO.save(tmp06invoicelunas);		
	}

	@Override
	public void save(List<Tmp06invoicelunas> tmp06invoicelunass) {
		for(Tmp06invoicelunas aData : tmp06invoicelunass){
			tmp06invoicelunasDAO.save(aData);
		}
		
	}

	@Override
	public void delete(Tmp06invoicelunas tmp06invoicelunas) {
		tmp06invoicelunasDAO.delete(tmp06invoicelunas);		
	}

	@Override
	public void delete(List<Tmp06invoicelunas> tmp06invoicelunass) {
		for(Tmp06invoicelunas aData : tmp06invoicelunass){
			tmp06invoicelunasDAO.delete(aData);
		}
	}

	@Override
	public List<Tmp06invoicelunas> getListTmp06invoicelunas(
			Map<Object, Object> parameterInput) {
		return tmp06invoicelunasDAO.getListTmp06invoicelunas(parameterInput);
	}

}
