/**
 * Copyright 2010 the original author or authors.
 *
 * This file is part of Zksample2. http://zksample2.sourceforge.net/
 *
 * Zksample2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Zksample2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zksample2.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package solusi.hapis.webui.util;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import solusi.hapis.UserWorkspace;
import solusi.hapis.backend.util.HibernateSearchObject;
import solusi.hapis.common.CommonUtils;
import solusi.hapis.util.Codec.StatusDokumen;

import com.trg.search.Filter;

/**
 * Base controller for creating the controllers of the zul files with the spring
 * framework.
 *
 * @author bbruhns
 * @author sgerth
 * @changes 05/18/2010 sge cleaned up from old stuff.
 */
abstract public class GFCBaseCtrl extends GenericForwardComposer implements Serializable {

    private static final long serialVersionUID = -1171206258809472640L;

    protected transient Map<String, Object> args;

    /**
     * Get the params map that are overhanded at creation time. <br>
     * Reading the params that are binded to the createEvent.<br>
     *
     * @param event
     * @return params map
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getCreationArgsMap(Event event) {
        CreateEvent ce = (CreateEvent) ((ForwardEvent) event).getOrigin();
        return ce.getArg();
    }

    @SuppressWarnings("unchecked")
    public void doOnCreateCommon(Window w, Event fe) throws Exception {
        CreateEvent ce = (CreateEvent) ((ForwardEvent) fe).getOrigin();
        args = ce.getArg();
    }

    private transient UserWorkspace userWorkspace;

    /**
     * Workaround! Do not use it otherwise!
     */
    @Override
    public void onEvent(Event evt) throws Exception {
        final Object controller = getController();
        final Method mtd = ComponentsCtrl.getEventMethod(controller.getClass(), evt.getName());

        if (mtd != null) {
            isAllowed(mtd);
        }
        super.onEvent(evt);
    }

    /**
     * With this method we get the @Secured Annotation for a method.<br>
     * Captured the method call and check if it's allowed. <br>
     * sample: @Secured({"rightName"})
     *
     * @param mtd
     */
    private void isAllowed(Method mtd) {
        Annotation[] annotations = mtd.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Secured) {
                Secured secured = (Secured) annotation;
                for (String rightName : secured.value()) {
                    if (!userWorkspace.isAllowed(rightName)) {
                        throw new SecurityException("Call of this method is not allowed! Missing right: \n\n" + "needed RightName: " + rightName + "\n\n" + "Method: " + mtd);
                    }
                }
                return;
            }
        }
    }

    final protected UserWorkspace getUserWorkspace() {
        return userWorkspace;
    }

    public void setUserWorkspace(UserWorkspace userWorkspace) {
        this.userWorkspace = userWorkspace;
    }
    
    public void setSorting(Listheader listHeader, String orderBy) {
    	listHeader.setSortAscending(new FieldComparator(orderBy, true));
    	listHeader.setSortDescending(new FieldComparator(orderBy, false));
    }
    
    public void addListCellInfo(Window window, Listitem listItem, String createdBy, Date createdOn, 
    		String updatedBy, Date updatedOn, Integer version, String approvedBy, Date approvedOn) {
    	Listcell lc; lc = new Listcell();
		lc.setImage("/images/icons/information_icon.jpg");
		lc.setStyle("cursor: help");
		Popup info = new Popup();
		Vbox vbox = new Vbox();
		Label lblCreateBy = new Label("Dibuat Oleh = " + createdBy);
		Label lblCreateOn = new Label("Dibuat Pada = " + 
				CommonUtils.convertDateToString(createdOn, CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
		Label lblUpdateBy = new Label("Dimodifikasi Oleh = " + updatedBy);
		Label lblUpdateOn = new Label("Dimodifikasi Pada = " +
				CommonUtils.convertDateToString(updatedOn, CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
		Label lblVersion = new Label("Version = " + version);
		Label lblApproveBy = new Label("Diapprove Oleh = " + approvedBy);
		Label lblApproveOn = new Label("Diapprove Pada = " +
				CommonUtils.convertDateToString(approvedOn, CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
		
		lblCreateBy.setParent(vbox);
		lblCreateOn.setParent(vbox);
		lblUpdateBy.setParent(vbox);
		lblUpdateOn.setParent(vbox);
		lblVersion.setParent(vbox);
		lblApproveBy.setParent(vbox);
		lblApproveOn.setParent(vbox);
		vbox.setParent(info);
		
		info.setParent(window);
		lc.setTooltip(info);
		lc.setParent(listItem);
    }
    
    public void addListCell(Listitem listItem, String label) {
    	Listcell lc = new Listcell(label);
		lc.setParent(listItem);
    }
    
    public void addListCell(Listitem listItem, Date label) {
    	Listcell lc = new Listcell(CommonUtils.convertDateToString(label));
		lc.setParent(listItem);
    }

    public void addListCell(Listitem listItem, Long label) {
    	Listcell lc = new Listcell(CommonUtils.formatNumberNonDecimal(label));
		lc.setStyle("text-align:right;");
		lc.setParent(listItem);
    }
    
    public void addListCell(Listitem listItem, Integer label) {
    	Listcell lc = new Listcell(CommonUtils.formatNumberNonDecimal(label));
		lc.setStyle("text-align:right;");
		lc.setParent(listItem);
    }
    
    public void addListCell(Listitem listItem, Short label) {
    	Listcell lc = new Listcell(CommonUtils.formatNumberNonDecimal(label));
		lc.setStyle("text-align:right;");
		lc.setParent(listItem);
    }
    
    public void addListCell(Listitem listItem, BigDecimal label) {
    	Listcell lc = new Listcell(CommonUtils.formatNumberNonDecimal(label));
		lc.setStyle("text-align:right;");
		lc.setParent(listItem);
    }
    
    public void addFilter(HibernateSearchObject<?> hso, String property, Object value) {
    	if (CommonUtils.isNotEmpty(value)) {
        	hso.addFilter(new Filter(property,"%"+String.valueOf(value)+"%", Filter.OP_ILIKE));
		}
    }
    
    public void addDateFilter(HibernateSearchObject<?> hso, String property, String value) {
    	if (CommonUtils.isValidDateFormat(value)) {
	    	Date duedate = CommonUtils.convertStringToDate(value);
			Date duedate2 = CommonUtils.createSecondParameterForSearch(value);
			hso.addFilter(new Filter(property, duedate, Filter.OP_GREATER_OR_EQUAL));
			hso.addFilter(new Filter(property, duedate2, Filter.OP_LESS_THAN));
    	}
    }
    
    public void addEntityFilter(HibernateSearchObject<?> hso, String property, Object value) {
    	if (CommonUtils.isNotEmpty(value)) {
    		hso.addFilter(new Filter(property, value));
    	}
    }
    
    public void addFetchFilter(HibernateSearchObject<?> hso, String entityName, String property, Object value) {
    	if (CommonUtils.isNotEmpty(value)) {
	    	addFilter(hso, property, value);
	    	hso.addFetch(entityName);
    	}
    }
    
    public boolean validateStatus(String validationType, String status) throws InterruptedException {
    	if(status != null) {
	    	if(validationType.equals("Aktif") == true){
	    		if(status.equals(StatusDokumen.AKTIF.getValue()) == false){
	    			ZksampleMessageUtils.showErrorMessage("Status Dokumen tidak Aktif");
					return false;
	    		}
	    	}
	    	return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean compareStatus(String comparator, String status, String errorMessage) throws InterruptedException {
    	if(status != null && comparator != null) {
	    	if(status.equals(comparator) == false){
	    		if(errorMessage != null) {
	    			ZksampleMessageUtils.showErrorMessage(errorMessage);
	    		} else {
	    			ZksampleMessageUtils.showErrorMessage("Status Dokumen tidak " + comparator);
	    		}
				return false;
    		}
	    	return true;
    	} else {
    		return false;
    	}
    }
}
