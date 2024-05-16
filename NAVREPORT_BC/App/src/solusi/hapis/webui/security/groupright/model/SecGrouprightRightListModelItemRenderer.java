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
package solusi.hapis.webui.security.groupright.model;


import org.apache.log4j.Logger;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import solusi.hapis.backend.model.SecGroup;
import solusi.hapis.backend.model.SecRight;
import solusi.hapis.backend.model.SecTyp;
import solusi.hapis.backend.security.service.SecurityService;
import solusi.hapis.webui.util.SelectionCtrl;

import java.io.Serializable;

/**
 * Item renderer for listitems in the listbox.
 *
 * @author bbruhns
 * @author sgerth
 */
public class SecGrouprightRightListModelItemRenderer implements ListitemRenderer, Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(SecGrouprightRightListModelItemRenderer.class);

    private final SelectionCtrl<SecGroup> parentController;
    private transient SecurityService securityService;

    public SecurityService getSecurityService() {
        if (this.securityService == null) {
            this.securityService = (SecurityService) SpringUtil.getBean("securityService");
            setSecurityService(this.securityService);
        }
        return this.securityService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public SecGrouprightRightListModelItemRenderer(SelectionCtrl<SecGroup> ctrl) {
        this.parentController = ctrl;
    }

    @Override
    public void render(Listitem item, Object data) throws Exception {

        final SecRight right = (SecRight) data;

        Listcell lc = new Listcell();
        final Checkbox cb = new Checkbox();

        // get the role for which we pull the data
        final SecGroup group = this.parentController.getSelected();

        if (group != null) {
            if (getSecurityService().isRightinGroup(right, group)) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
        } else {
            cb.setChecked(false);
        }

        lc.appendChild(cb);
        lc.setParent(item);

        lc = new Listcell(right.getRigName());
        lc.setParent(item);

        final SecTyp typ = getSecurityService().getTypById(right.getRigType().intValue());
        lc = new Listcell(String.valueOf(typ.getStpTypname()));
        lc.setParent(item);

        // lc = new Listcell();
        // Image img = new Image();
        // img.setSrc("/images/icons/page_detail.gif");
        // lc.appendChild(img);
        // lc.setParent(item);

        item.setAttribute("data", data);
        // ComponentsCtrl.applyForward(img, "onClick=onImageClicked");
        // ComponentsCtrl.applyForward(item, "onClick=onClicked");
        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClicked");

    }

}
