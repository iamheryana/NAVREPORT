/**
 * Copyright 2010 the original author or authors.
 *
 * This file is part of openTruuls™. http://www.opentruuls.org/ and 
 * have the permission to be integrated in the zksample2 demo application.
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
package solusi.hapis.webui.dashboard.module;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.*;

import java.io.Serializable;

/**
 * EN: <b>Calendar</b> controller for the dashboard.<br>
 * Sample skeleton.
 * <hr>
 * DE: <b>Kalender</b> Controller fuer die SystemUebersicht.<br>
 * Beispiel Struktur.
 * <p/>
 * <pre>
 * call: Div div = DashboardCalendarCtrl.show(200);
 * </pre>
 *
 * @author Stephan Gerth
 */
public class DashboardCalendarCtrl extends Div implements Serializable {

    private static final long serialVersionUID = 1L;

    // the height of this dashboard module
    private int modulHeight;

    /**
     * The static call method.
     *
     * @param modulHeight The height of this dashboard module
     * @return the module as DIV.
     */
    public static Div show(int modulHeight) {
        return new DashboardCalendarCtrl(modulHeight);
    }

    /**
     * Private Constructor. So it can only be created with the static show()
     * method.<br>
     *
     * @param modulHeight The height of this dashboard module
     */
    private DashboardCalendarCtrl(int modulHeight) {
        super();

        setModulHeight(modulHeight);
        createComponents();
    }

    /**
     * Creates the components.<br>
     */
    private void createComponents() {

        /**
         * !! Windows as NameSpaceContainer to prevent not unique id's error
         * from other dashboard module buttons or other used components.
         */
        Window win = new Window();
        win.setBorder("none");
        win.setParent(this);

        Groupbox gb = new Groupbox();
        gb.setMold("3d");
        gb.setClosable(false);
        gb.setParent(win);
        Caption cap = new Caption();
        cap.setImage("/images/icons/calendar1_16x16.gif");
        cap.setLabel(Labels.getLabel("common.Calendar"));
        cap.setStyle("padding: 0px;");
        cap.setParent(gb);

        // Buttons Toolbar
        Div div = new Div();
        div.setSclass("z-toolbar");
        div.setStyle("padding: 0px");
        div.setParent(cap);
        Hbox hbox = new Hbox();
        hbox.setPack("stretch");
        hbox.setSclass("hboxRemoveWhiteStrips");
        hbox.setWidth("100%");
        hbox.setParent(div);
        Toolbar toolbarRight = new Toolbar();
        toolbarRight.setAlign("end");
        toolbarRight.setStyle("float:right; border-style: none;");
        toolbarRight.setParent(hbox);

        // body
        Borderlayout bl = new Borderlayout();
        bl.setHeight(getModulHeight() + "px");
        bl.setParent(gb);
        Center ct = new Center();
        ct.setSclass("FDCenterNoBorder");
        ct.setStyle("background-color: white");
        ct.setParent(bl);

        // Module dependend
        Calendar calendar = new Calendar();
        calendar.setId("cal");
        calendar.setStyle("border: none;");
        calendar.setWidth("98%");
        calendar.setParent(ct);

        doReadData();
    }

    /**
     * Reads the data.
     */
    public void doReadData() {

    }

    /**
     * Inner onBtnClick Listener class.<br>
     *
     * @author sGerth
     */
    private final class BtnClickListener implements EventListener {
        @Override
        public void onEvent(Event event) throws Exception {

            // check which button is pressed
            if (event.getTarget().getId().equalsIgnoreCase("btnRefresh")) {
                doReadData();
            }
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setModulHeight(int modulHeight) {
        this.modulHeight = modulHeight;
    }

    public int getModulHeight() {
        return modulHeight;
    }

}
