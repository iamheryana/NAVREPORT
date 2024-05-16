package solusi.hapis.backend.navbi.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.navbi.model.T19PiItem;

public interface T19PiItemService {
	public void saveOrUpdate(T19PiItem t19PiItem);
	public void update(T19PiItem t19PiItem);
	public void save(T19PiItem t19PiItem);
	public void delete(T19PiItem t19PiItem);
	public List<T19PiItem> getListT19PiItem(Map<Object, Object> parameterInput);

}
