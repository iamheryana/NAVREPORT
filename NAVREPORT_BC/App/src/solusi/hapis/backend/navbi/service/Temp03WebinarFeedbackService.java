package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.Temp03WebinarFeedback;

public interface Temp03WebinarFeedbackService {
	public void saveOrUpdate(Temp03WebinarFeedback temp03WebinarFeedback);
	public void update(Temp03WebinarFeedback temp03WebinarFeedback);
	public void save(Temp03WebinarFeedback temp03WebinarFeedback);
	public void save(List<Temp03WebinarFeedback> temp03WebinarFeedbacks);
	public void delete(Temp03WebinarFeedback temp03WebinarFeedback);
	public void delete(List<Temp03WebinarFeedback> temp03WebinarFeedbacks);
	public List<Temp03WebinarFeedback> getListTemp03WebinarFeedback(Map<Object, Object> parameterInput);

}
