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

import org.apache.log4j.Logger;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Space;
import org.zkoss.zul.Window;

import solusi.hapis.backend.navbi.model.BCAccessFrom;
import solusi.hapis.backend.navbi.service.BCAccessFromService;
import solusi.hapis.common.CommonUtils;

import java.io.Serializable;

/**
 * =======================================================================<br>
 * StatusBarController. <br>
 * =======================================================================<br>
 * Works with the EventQueues mechanism of zk. ALl needed components are created
 * in this class. In the zul-template declare only this controller with 'apply'
 * to a winStatusBar window component.<br>
 * <p/>
 * Declaration in the zul-file:<br>
 * <p/>
 * <pre>
 * < borderlayout >
 *   . . .
 *    < !-- STATUS BAR AREA -- >
 *    < south id="south" border="none" margins="1,0,0,0"
 * 		height="20px" splittable="false" flex="true" >
 * 	      < div id="divSouth" >
 *
 *          < !-- The StatusBar. Comps are created in the Controller -- >
 *          < window id="winStatusBar" apply="${statusBarCtrl}"
 *                   border="none" width="100%" height="100%" />
 *
 *        < /div >
 *    < /south >
 *  < /borderlayout >
 * </pre>
 * <p/>
 * call in java to actualize a columns label:
 * <p/>
 * <pre>
 * EventQueues.lookup(&quot;userNameEventQueue&quot;, EventQueues.DESKTOP, true).publish(new Event(&quot;onChangeSelectedObject&quot;, null, &quot;new Value&quot;));
 * </pre>
 * <p/>
 * Spring bean declaration:
 * <p/>
 * <pre>
 * < !-- StatusBarController -->
 * < bean id="statusBarCtrl" class="solusi.hapis.webui.util.StatusBarCtrl"
 *    scope="prototype">
 * < /bean>
 * </pre>
 * <p/>
 * since: zk 5.0.0
 *
 * @author sgerth
 */
public class UserBarCtrl extends GenericForwardComposer implements Serializable {

    private static final long serialVersionUID = 1L;
    private final static Logger logger = Logger.getLogger(UserBarCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window winUserBar; // autowired

    // Used Labels
    private Label userLabel;
    private Label tenantLabel;
    private Label officeLabel;

    // Localized labels for the columns
    private final String _UserLabel = Labels.getLabel("common.User") + ": ";
    private final String _TenantIdLabel = Labels.getLabel("common.Tenant.ID") + ": ";
    private final String _OfficeIdLabel = Labels.getLabel("common.Office.ID") + ": ";

    // Used Labels
    private Label userLabelText;
    private Label tenantLabelText;
    private Label officeLabelText;
    private Label lastSyncText;

    private String _UserText = "";
    private String _TenantIdText = "";
    private String _OfficeIdText = "";
    
    private BCAccessFromService bCAccessFromService = (BCAccessFromService) SpringUtil.getBean("bCAccessFromService");

    /**
     * Default constructor.
     */
    public UserBarCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        // Listener for user
        EventQueues.lookup("userNameEventQueue", EventQueues.DESKTOP, true).subscribe(new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                final String msg = (String) event.getData();
                set_UserText(msg);
                doShowLabel();
            }
        });

        // Listener for TenantId
        EventQueues.lookup("tenantIdEventQueue", EventQueues.DESKTOP, true).subscribe(new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                final String msg = (String) event.getData();
                set_TenantIdText(msg);
                doShowLabel();
            }
        });

        // Listener for OfficeId
        EventQueues.lookup("officeIdEventQueue", EventQueues.DESKTOP, true).subscribe(new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                final String msg = (String) event.getData();
                set_OfficeIdText(msg);
                doShowLabel();
            }
        });

    }

    /**
     * Automatically called method from zk.
     *
     * @param event
     */
    public void onCreate$winUserBar(Event event) {

        Space space;
        Space space2;

        winUserBar.setBorder("none");

      

       
//        tenantLabel = new Label();
//        tenantLabel.setStyle("text-align: right; font-size: 10px;");
//        tenantLabel.setParent(hbox);
//        tenantLabelText = new Label();
//        tenantLabelText.setStyle("padding-left: 2px; text-align: right; color: blue; font-size: 10px;");
//        tenantLabelText.setParent(hbox);
//
//        officeLabel = new Label();
//        officeLabel.setStyle("text-align: right; font-size: 10px;");
//        officeLabel.setParent(hbox);
//        officeLabelText = new Label();
//        officeLabelText.setStyle("padding-left: 2px; text-align: right; color: blue; font-size: 10px;");
//        officeLabelText.setParent(hbox);


        

        

        Hbox hbox2 = new Hbox();
        hbox2.setParent(winUserBar);

        lastSyncText= new Label();
        lastSyncText.setStyle("text-align: right; font-size: 15px;color: red;");
        lastSyncText.setParent(hbox2);
        
        space2 = new Space();
        space2.setWidth("5px");
        space2.setParent(hbox2);
      
        
        
        Hbox hbox = new Hbox();
        hbox.setParent(winUserBar);

        userLabel = new Label();
        userLabel.setStyle("text-align: right; font-size: 10px;");
        userLabel.setParent(hbox);
        userLabelText = new Label();
        userLabelText.setStyle("padding-left: 2px; text-align: right; color: blue; font-size: 10px;");
        userLabelText.setParent(hbox);
    }

    /**
     * Shows the labels with values.<br>
     */
    private void doShowLabel() {

    	 BCAccessFrom vAccFrom = bCAccessFromService.getLastSync();
         
        
         
        this.userLabel.setValue(this._UserLabel);
        this.userLabelText.setValue(get_UserText());
        if(vAccFrom != null){
        	 this.lastSyncText.setValue("Last 'Data Sync' : "+CommonUtils.convertDateToString(vAccFrom.getLastSync(),CommonUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
         }

//        this.tenantLabel.setValue(" / " + this._TenantIdLabel);
//        this.tenantLabelText.setValue(get_TenantIdText());
//
//        this.officeLabel.setValue(" / " + this._OfficeIdLabel);
//        this.officeLabelText.setValue(get_OfficeIdText());

    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    // ++++++++++++++++++ getter / setter +++++++++++++++++++//
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//

    public void set_UserText(String _UserText) {
        this._UserText = _UserText;
    }

    public String get_UserText() {
        return this._UserText;
    }

    public void set_TenantIdText(String _TenantIdText) {
        this._TenantIdText = _TenantIdText;
    }

    public String get_TenantIdText() {
        return this._TenantIdText;
    }

    public void set_OfficeIdText(String _OfficeIdText) {
        this._OfficeIdText = _OfficeIdText;
    }

    public String get_OfficeIdText() {
        return this._OfficeIdText;
    }

}
