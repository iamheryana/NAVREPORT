package solusi.hapis.backend.temp.service;

import java.util.List;
import java.util.Map;

import solusi.hapis.backend.temp.model.Z01ptgl;


public interface Z01ptglService {
  public void saveOrUpdate(Z01ptgl z01ptgl);
  public void update(Z01ptgl z01ptgl);
  public void save(Z01ptgl z01ptgl);
  public void delete(Z01ptgl z01ptgl);
  public List<Z01ptgl> getListZ01ptgl(Map<Object, Object> parameterInput);
}