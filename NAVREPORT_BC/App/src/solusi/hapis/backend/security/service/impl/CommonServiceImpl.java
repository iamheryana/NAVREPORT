package solusi.hapis.backend.security.service.impl;


import java.util.HashMap;
import java.util.Map;

import solusi.hapis.backend.security.dao.HibernateStatisticsDao;
import solusi.hapis.backend.security.dao.SecGroupDAO;
import solusi.hapis.backend.security.dao.SecGrouprightDAO;
import solusi.hapis.backend.security.dao.SecLoginlogDAO;
import solusi.hapis.backend.security.dao.SecRightDAO;
import solusi.hapis.backend.security.dao.SecRoleDAO;
import solusi.hapis.backend.security.dao.SecRolegroupDAO;
import solusi.hapis.backend.security.dao.SecTypDAO;
import solusi.hapis.backend.security.dao.SecUserroleDAO;
import solusi.hapis.backend.security.dao.UserDAO;
import solusi.hapis.backend.security.service.CommonService;

/**
 * Service implementation for methods that depends on <b>all DAO methods</b>.<br>
 * Mainly used for get back the size for every table.
 *
 * @author bbruhns
 * @author sgerth
 */
public class CommonServiceImpl implements CommonService {
   
    private UserDAO userDAO;
    private SecUserroleDAO secUserroleDAO;
    private SecRoleDAO secRoleDAO;
    private SecRolegroupDAO secRolegroupDAO;
    private SecGrouprightDAO secGrouprightDAO;
    private SecGroupDAO secGroupDAO;
    private SecRightDAO secRightDAO;
    private SecTypDAO secTypDAO;
    private SecLoginlogDAO secLoginlogDAO;
  

    private HibernateStatisticsDao hibernateStatisticsDao;


    public void setHibernateStatisticsDao(HibernateStatisticsDao hibernateStatisticsDao) {
        this.hibernateStatisticsDao = hibernateStatisticsDao;
    }

    public HibernateStatisticsDao getHibernateStatisticsDao() {
        return hibernateStatisticsDao;
    }

   
    public SecTypDAO getSecTypDAO() {
        return secTypDAO;
    }

    public void setSecTypDAO(SecTypDAO secTypDAO) {
        this.secTypDAO = secTypDAO;
    }

    public SecRightDAO getSecRightDAO() {
        return secRightDAO;
    }

    public void setSecRightDAO(SecRightDAO secRightDAO) {
        this.secRightDAO = secRightDAO;
    }

    public SecGroupDAO getSecGroupDAO() {
        return secGroupDAO;
    }

    public void setSecGroupDAO(SecGroupDAO secGroupDAO) {
        this.secGroupDAO = secGroupDAO;
    }

    public SecGrouprightDAO getSecGrouprightDAO() {
        return secGrouprightDAO;
    }

    public void setSecGrouprightDAO(SecGrouprightDAO secGrouprightDAO) {
        this.secGrouprightDAO = secGrouprightDAO;
    }

    public SecRolegroupDAO getSecRolegroupDAO() {
        return secRolegroupDAO;
    }

    public void setSecRolegroupDAO(SecRolegroupDAO secRolegroupDAO) {
        this.secRolegroupDAO = secRolegroupDAO;
    }

    public SecUserroleDAO getSecUserroleDAO() {
        return secUserroleDAO;
    }

    public void setSecUserroleDAO(SecUserroleDAO secUserroleDAO) {
        this.secUserroleDAO = secUserroleDAO;
    }

    public SecRoleDAO getSecRoleDAO() {
        return secRoleDAO;
    }

    public void setSecRoleDAO(SecRoleDAO secRoleDAO) {
        this.secRoleDAO = secRoleDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setSecLoginlogDAO(SecLoginlogDAO secLoginlogDAO) {
        this.secLoginlogDAO = secLoginlogDAO;
    }

    public SecLoginlogDAO getSecLoginlogDAO() {
        return secLoginlogDAO;
    }

    @Override
    public Map<String, Object> getAllTablesRecordCounts() {

        Map<String, Object> map = new HashMap<String, Object>();


        map.put("SecGroup", getSecGroupDAO().getCountAllSecGroups());
        map.put("SecGroupright", getSecGrouprightDAO().getCountAllSecGrouprights());
        map.put("SecRight", getSecRightDAO().getCountAllSecRights());
        map.put("SecRole", getSecRoleDAO().getCountAllSecRoles());
        map.put("SecRolegroup", getSecRolegroupDAO().getCountAllSecRolegroups());
        map.put("SecUser", getUserDAO().getCountAllSecUser());
        map.put("SecUserrole", getSecUserroleDAO().getCountAllSecUserroles());
        map.put("SecLoginlog", getSecLoginlogDAO().getCountAllSecLoginlogs());
        map.put("HibernateStatistics", getHibernateStatisticsDao().getCountAllHibernateStatistics());

        return map;
    }

}
