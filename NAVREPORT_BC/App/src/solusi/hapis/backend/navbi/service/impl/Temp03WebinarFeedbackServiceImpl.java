package solusi.hapis.backend.navbi.service.impl;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.dao.Temp03WebinarFeedbackDAO;
import solusi.hapis.backend.navbi.model.Temp03WebinarFeedback;
import solusi.hapis.backend.navbi.service.Temp03WebinarFeedbackService;

public class Temp03WebinarFeedbackServiceImpl  implements Temp03WebinarFeedbackService {
	private Temp03WebinarFeedbackDAO temp03WebinarFeedbackDAO;

	public Temp03WebinarFeedbackDAO getTemp03WebinarFeedbackDAO() {
		return temp03WebinarFeedbackDAO;
	}

	public void setTemp03WebinarFeedbackDAO(Temp03WebinarFeedbackDAO temp03WebinarFeedbackDAO) {
		this.temp03WebinarFeedbackDAO = temp03WebinarFeedbackDAO;
	}

	@Override
	public void saveOrUpdate(Temp03WebinarFeedback temp03WebinarFeedback) {
		temp03WebinarFeedbackDAO.saveOrUpdate(temp03WebinarFeedback);		
	}

	@Override
	public void update(Temp03WebinarFeedback temp03WebinarFeedback) {
		temp03WebinarFeedbackDAO.update(temp03WebinarFeedback);			
	}

	@Override
	public void save(Temp03WebinarFeedback temp03WebinarFeedback) {
		temp03WebinarFeedbackDAO.save(temp03WebinarFeedback);	
	}

	@Override
	public void delete(Temp03WebinarFeedback temp03WebinarFeedback) {
		temp03WebinarFeedbackDAO.delete(temp03WebinarFeedback);	
	}

	public List<Temp03WebinarFeedback> getListTemp03WebinarFeedback(
			Map<Object, Object> parameterInput) {
		return temp03WebinarFeedbackDAO.getListTemp03WebinarFeedback(parameterInput);
	}

	@Override
	public void save(List<Temp03WebinarFeedback> temp03WebinarFeedbacks) {
		for(Temp03WebinarFeedback aData : temp03WebinarFeedbacks){
			temp03WebinarFeedbackDAO.save(aData);
		}
		
	}

	@Override
	public void delete(List<Temp03WebinarFeedback> temp03WebinarFeedbacks) {
		for(Temp03WebinarFeedback aData : temp03WebinarFeedbacks){
			temp03WebinarFeedbackDAO.delete(aData);
		}
	}	
	
}
