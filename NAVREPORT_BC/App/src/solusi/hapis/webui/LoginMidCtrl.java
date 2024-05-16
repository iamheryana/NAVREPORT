package solusi.hapis.webui;

import java.io.Serializable;
import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;

import solusi.hapis.backend.model.SecLog;
import solusi.hapis.backend.model.SecUser;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.backend.security.service.UserService;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.webui.util.GFCBaseCtrl;

public class LoginMidCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -3407055074703929527L;
	private UserService userService = (UserService) SpringUtil.getBean("userService");
	private SecurityService securityService = (SecurityService) SpringUtil.getBean("securityService");
	
	public void onCreate$loginMidWindow(Event event) throws Exception {
		
		SecUser user = userService.getUserByLoginname(SecurityContextHolder.getContext().getAuthentication().getName());
		//System.out.println("IP : "+);
		String setRedirectUrl = null;
    	if(user != null){
        	Date vToday = new Date();
        	Date vExpiredDate = user.getExpiredDate();
        	if (vExpiredDate.before(vToday)){
        		setRedirectUrl = 
                         "/ZkLoginDialog.zul?login_error=Password Expired!";
        	} else {
        		String vFlagAktif =".";
        		if (user.getFlagActiv() != null){
        			vFlagAktif = user.getFlagActiv() ;
        		}
        		
        		if(vFlagAktif.equals("Y") == false){
        			setRedirectUrl = 
                            "/ZkLoginDialog.zul?login_error=User Suspended!";
        		} else {
        			SecLog newLog = new SecLog("Login", user.getUsrLoginname(), new Date(), CommonUtils.convertClientAddress(SecurityContextHolder.getContext().getAuthentication()));
        					
        			securityService.save(newLog);
        			
        			setRedirectUrl = "/pages/IndexBar.zul";
        			
        			// untuk manggil menu yang lama.
        			//setRedirectUrl = "/pages/index.zul";
        		}
        	}
        } else {
        	setRedirectUrl = 
                    "/ZkLoginDialog.zul?login_error=Invalid User dan Password!";
        }

    	
		Executions.sendRedirect(setRedirectUrl);
		
	}
	
}
