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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Button;

import solusi.hapis.UserWorkspace;

/**
 * Button controller for the CRUD buttons. <br>
 * <br>
 * Works by calling the setBtnStatus_xxx where xxx is the kind of pressed <br>
 * button action, i.e. new delete or save. After calling these methods <br>
 * all buttons are disabled/enabled or visible/not visible by <br>
 * param disableButtons. <br>
 * <br>
 * buttonsModeDisable = true --> Buttons are disabled/enabled <br>
 * buttonsModeDisable = false --> Buttons are visible/not visible <br>
 * 
 * @author bbruhns
 * @author Stephan Gerth
 * @changes 03/25/2009 sge Extended for security. So we need a right prefix.<br>
 *          That suppose that we have a convention in writing the prefix like <br>
 *          if (workspace.isAllowed(_rightPrefix + "_btnNew"))) {<br>
 *          means that the right have following name:
 *          "button_CustomerDialog_btnNew" <br>
 *          12/02/2009 sge Changed Buttons from Text to Images with Tooltext.<br>
 *          02/04/2010 sge added a Cancel Button.<br>
 *          07/01/2010 sge added a constructor parameter for let the CloseButton
 *          appears or not. (Dialogs=Yes / DetailView = No).<br>
 *          02/22/2011 sge Extended for disable(true/false) all buttons.<br>
 *          added a second constructor for working with/without CloseButton.<br>
 *          which is used in ModalWindows.<br>
 *          04/26/2011 sge Extended for let a button be null. UseCase is to
 *          working with a base record that exists only ONE time. i.e. for
 *          holding special parameters. So you need only the 'edit', 'save' and
 *          'cancel' button <br>
 */
public class ButtonStatusCtrl implements Serializable {

	private static final long serialVersionUID = -4907914938602465474L;

	private static enum ButtonEnum {
		New, Edit, Delete, Listing, Ok,
		Save, Cancel,
		Search, Clear,
		Approve, Disapprove,
		Other1, Other2, Other3, Other4, Other5,
		Close;
	}

	private final Map<ButtonEnum, Button> buttons = new HashMap<ButtonEnum, Button>(
			5);

	final private UserWorkspace workspace;

	/**
	 * rightName prefix
	 */
	private final String _rightPrefix;

	/**
	 * Var for disable/enable or visible/not visible mode of the butttons. <br>
	 * true = disable the button <br>
	 * false = make the button unvisible<br>
	 */
	private final boolean buttonsModeDisable = false;

	/**
	 * with close button
	 */
	private boolean closeButton = true;

	/**
	 * is the BtnController active ?
	 */
	private boolean active = true;

	/**
	 * is the security active ?
	 */
	// nanda edit, default : false
	private boolean securityActive = true;

	private String namaButtonOther1;
	private String namaButtonOther2;
	private String namaButtonOther3;
	private String namaButtonOther4;
	private String namaButtonOther5;
	
	/**
	 * Constructor Standart.
	 * 
	 * @param btnNew (New Button)
	 * @param btnEdit (Edit Button)
	 * @param btnDelete (Delete Button)
	 * @param btnSave (Save Button)
	 * @param btnCancel (Cancel Button)
	 * @param btnSearch (Search Button)
	 * @param btnSClear (Search Clear)
	 * 
	 */
	
    public ButtonStatusCtrl(UserWorkspace userWorkspace, String rightPrefix, 
    			Button btnNew, Button btnEdit, Button btnDelete, 
    			Button btnListing, Button btnOk,
    			Button btnSave, Button btnCancel,
    			Button btnSearch, Button btnClear) {
        super();
        this.workspace = userWorkspace;
        this._rightPrefix = rightPrefix + "btn";

        if (btnNew != null) {
        	buttons.put(ButtonEnum.New, btnNew);
        	buttons.get(ButtonEnum.New).setLabel(Labels.getLabel("common.button.new"));
        }
        
        if (btnEdit != null) {
        	buttons.put(ButtonEnum.Edit, btnEdit);
        	buttons.get(ButtonEnum.Edit).setLabel(Labels.getLabel("common.button.edit"));
        }
        
        if (btnDelete != null) {
        	buttons.put(ButtonEnum.Delete, btnDelete);
        	buttons.get(ButtonEnum.Delete).setLabel(Labels.getLabel("common.button.delete"));
        }
        
        if (btnListing != null) {
        	buttons.put(ButtonEnum.Listing, btnListing);
        	buttons.get(ButtonEnum.Listing).setLabel(Labels.getLabel("common.button.listing"));
        }
           
        if (btnSave != null) {
        	buttons.put(ButtonEnum.Save, btnSave);
        	buttons.get(ButtonEnum.Save).setLabel(Labels.getLabel("common.button.save"));
        	buttons.get(ButtonEnum.Save).setVisible(false);
        }
        
        if (btnCancel != null) {
        	buttons.put(ButtonEnum.Cancel, btnCancel);
        	buttons.get(ButtonEnum.Cancel).setLabel(Labels.getLabel("common.button.cancel"));
        	buttons.get(ButtonEnum.Cancel).setVisible(false);
        }
        
        if (btnOk != null) {
        	buttons.put(ButtonEnum.Ok, btnOk);
        	buttons.get(ButtonEnum.Ok).setLabel(Labels.getLabel("common.button.ok"));
        	buttons.get(ButtonEnum.Ok).setVisible(false);
        }
        
        if (btnSearch != null) {
        	buttons.put(ButtonEnum.Search, btnSearch);
        	buttons.get(ButtonEnum.Search).setLabel(Labels.getLabel("common.button.search"));
        	buttons.get(ButtonEnum.Search).setVisible(true);
        }
        
        if (btnClear != null) {
        	buttons.put(ButtonEnum.Clear, btnClear);
        	buttons.get(ButtonEnum.Clear).setLabel(Labels.getLabel("common.button.clear"));
        	buttons.get(ButtonEnum.Clear).setVisible(true);
        }
        
    }
    
    public ButtonStatusCtrl(UserWorkspace userWorkspace, String rightPrefix, 
    		Button btnEdit, Button btnListing, Button btnOk,
			Button btnSave, Button btnCancel,
			Button btnSearch, Button btnClear) {
    super();
    this.workspace = userWorkspace;
    this._rightPrefix = rightPrefix + "btn";
    
    if (btnEdit != null) {
    	buttons.put(ButtonEnum.Edit, btnEdit);
    	buttons.get(ButtonEnum.Edit).setLabel(Labels.getLabel("common.button.edit"));
    }
    
    if (btnListing != null) {
    	buttons.put(ButtonEnum.Listing, btnListing);
    	buttons.get(ButtonEnum.Listing).setLabel(Labels.getLabel("common.button.listing"));
    }
       
    if (btnSave != null) {
    	buttons.put(ButtonEnum.Save, btnSave);
    	buttons.get(ButtonEnum.Save).setLabel(Labels.getLabel("common.button.save"));
    	buttons.get(ButtonEnum.Save).setVisible(false);
    }
    
    if (btnCancel != null) {
    	buttons.put(ButtonEnum.Cancel, btnCancel);
    	buttons.get(ButtonEnum.Cancel).setLabel(Labels.getLabel("common.button.cancel"));
    	buttons.get(ButtonEnum.Cancel).setVisible(false);
    }
    
    if (btnOk != null) {
    	buttons.put(ButtonEnum.Ok, btnOk);
    	buttons.get(ButtonEnum.Ok).setLabel(Labels.getLabel("common.button.ok"));
    	buttons.get(ButtonEnum.Ok).setVisible(false);
    }
    
    if (btnSearch != null) {
    	buttons.put(ButtonEnum.Search, btnSearch);
    	buttons.get(ButtonEnum.Search).setLabel(Labels.getLabel("common.button.search"));
    	buttons.get(ButtonEnum.Search).setVisible(true);
    }
    
    if (btnClear != null) {
    	buttons.put(ButtonEnum.Clear, btnClear);
    	buttons.get(ButtonEnum.Clear).setLabel(Labels.getLabel("common.button.clear"));
    	buttons.get(ButtonEnum.Clear).setVisible(true);
    }
    
}
        

    // Pada saat Layar Inquiry
    
    public void setInitInquiryButton() {
    	if (buttons.get(ButtonEnum.New) != null) {
    		buttons.get(ButtonEnum.New).setVisible(workspace.isAllowed(_rightPrefix + ButtonEnum.New.name()));
    	}
    	if (buttons.get(ButtonEnum.Edit) != null) {
    		buttons.get(ButtonEnum.Edit).setVisible(workspace.isAllowed(_rightPrefix + ButtonEnum.Edit.name()));
    	}
    	if (buttons.get(ButtonEnum.Delete) != null) {
    		buttons.get(ButtonEnum.Delete).setVisible(workspace.isAllowed(_rightPrefix + ButtonEnum.Delete.name()));
    	}
    	if (buttons.get(ButtonEnum.Listing) != null) {
    		buttons.get(ButtonEnum.Listing).setVisible(workspace.isAllowed(_rightPrefix + ButtonEnum.Listing.name()));
    	}
    	
    	if (buttons.get(ButtonEnum.Approve) != null) {
    		buttons.get(ButtonEnum.Approve).setVisible(workspace.isAllowed(_rightPrefix + ButtonEnum.Approve.name()));
    	}

    	if (buttons.get(ButtonEnum.Disapprove) != null) {
    		buttons.get(ButtonEnum.Disapprove).setVisible(workspace.isAllowed(_rightPrefix + ButtonEnum.Disapprove.name()));
    	}

    	if (buttons.get(ButtonEnum.Save) != null) {
    		buttons.get(ButtonEnum.Save).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Cancel) != null) {
    		buttons.get(ButtonEnum.Cancel).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Ok) != null) {
    		buttons.get(ButtonEnum.Ok).setVisible(false);
    	}

    	if (buttons.get(ButtonEnum.Search) != null) {
    		buttons.get(ButtonEnum.Search).setVisible(true);
    	}
    	
    	if (buttons.get(ButtonEnum.Clear) != null) {
    		buttons.get(ButtonEnum.Clear).setVisible(true);
    	}
    	
    	if (buttons.get(ButtonEnum.Other1) != null) {
    		buttons.get(ButtonEnum.Other1).setVisible(workspace.isAllowed(_rightPrefix + namaButtonOther1));
    	}
    	
    	if (buttons.get(ButtonEnum.Other2) != null) {
    		buttons.get(ButtonEnum.Other2).setVisible(workspace.isAllowed(_rightPrefix + namaButtonOther2));
    	}
    	
    	if (buttons.get(ButtonEnum.Other3) != null) {
    		buttons.get(ButtonEnum.Other3).setVisible(workspace.isAllowed(_rightPrefix + namaButtonOther3));
    	}
    	
    	if (buttons.get(ButtonEnum.Other4) != null) {
    		buttons.get(ButtonEnum.Other4).setVisible(workspace.isAllowed(_rightPrefix + namaButtonOther4));
    	}
    	
    	if (buttons.get(ButtonEnum.Other5) != null) {
    		buttons.get(ButtonEnum.Other5).setVisible(workspace.isAllowed(_rightPrefix + namaButtonOther5));
    	}
    }

    // Pada saat Layar Form
    public void setInitFormButton() {
    	if (buttons.get(ButtonEnum.New) != null) {
    		buttons.get(ButtonEnum.New).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Edit) != null) {
    		buttons.get(ButtonEnum.Edit).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Delete) != null) {
    		buttons.get(ButtonEnum.Delete).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Listing) != null) {
    		buttons.get(ButtonEnum.Listing).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Ok) != null) {
    		buttons.get(ButtonEnum.Ok).setVisible(false);
    	}

    	if (buttons.get(ButtonEnum.Approve) != null) {
    		buttons.get(ButtonEnum.Approve).setVisible(false);
    	}

    	if (buttons.get(ButtonEnum.Disapprove) != null) {
    		buttons.get(ButtonEnum.Disapprove).setVisible(false);
    	}

    	if (buttons.get(ButtonEnum.Save) != null) {
    		buttons.get(ButtonEnum.Save).setVisible(true);
    	}
    	if (buttons.get(ButtonEnum.Cancel) != null) {
    		buttons.get(ButtonEnum.Cancel).setVisible(true);
    	}

    	if (buttons.get(ButtonEnum.Search) != null) {
    		buttons.get(ButtonEnum.Search).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Clear) != null) {
    		buttons.get(ButtonEnum.Clear).setVisible(false);
    	}    	
    	
    	if (buttons.get(ButtonEnum.Other1) != null) {
    		buttons.get(ButtonEnum.Other1).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other2) != null) {
    		buttons.get(ButtonEnum.Other2).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other3) != null) {
    		buttons.get(ButtonEnum.Other3).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other4) != null) {
    		buttons.get(ButtonEnum.Other4).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other5) != null) {
    		buttons.get(ButtonEnum.Other5).setVisible(false);
    	}
    	
    }
    
 // Pada saat Layar Listing
    public void setInitPrintButton() {
    	if (buttons.get(ButtonEnum.New) != null) {
    		buttons.get(ButtonEnum.New).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Edit) != null) {
    		buttons.get(ButtonEnum.Edit).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Delete) != null) {
    		buttons.get(ButtonEnum.Delete).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Listing) != null) {
    		buttons.get(ButtonEnum.Listing).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Approve) != null) {
    		buttons.get(ButtonEnum.Approve).setVisible(false);
    	}

    	if (buttons.get(ButtonEnum.Disapprove) != null) {
    		buttons.get(ButtonEnum.Disapprove).setVisible(false);
    	}
  
    	if (buttons.get(ButtonEnum.Save) != null) {
    		buttons.get(ButtonEnum.Save).setVisible(false);
    	}
    	if (buttons.get(ButtonEnum.Cancel) != null) {
    		buttons.get(ButtonEnum.Cancel).setVisible(true);
    	}
    	
    	if (buttons.get(ButtonEnum.Ok) != null) {
    		buttons.get(ButtonEnum.Ok).setVisible(true);
    	}
    	
    	if (buttons.get(ButtonEnum.Search) != null) {
    		buttons.get(ButtonEnum.Search).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Clear) != null) {
    		buttons.get(ButtonEnum.Clear).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other1) != null) {
    		buttons.get(ButtonEnum.Other1).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other2) != null) {
    		buttons.get(ButtonEnum.Other2).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other3) != null) {
    		buttons.get(ButtonEnum.Other3).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other4) != null) {
    		buttons.get(ButtonEnum.Other4).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other5) != null) {
    		buttons.get(ButtonEnum.Other5).setVisible(false);
    	}

    }
    
 // Pada saat Layar Custom
    public void setInitCustomButton() {
    	if (buttons.get(ButtonEnum.New) != null) {
    		buttons.get(ButtonEnum.New).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Edit) != null) {
    		buttons.get(ButtonEnum.Edit).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Delete) != null) {
    		buttons.get(ButtonEnum.Delete).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Listing) != null) {
    		buttons.get(ButtonEnum.Listing).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Approve) != null) {
    		buttons.get(ButtonEnum.Approve).setVisible(false);
    	}

    	if (buttons.get(ButtonEnum.Disapprove) != null) {
    		buttons.get(ButtonEnum.Disapprove).setVisible(false);
    	}
  
    	if (buttons.get(ButtonEnum.Save) != null) {
    		buttons.get(ButtonEnum.Save).setVisible(false);
    	}
    	if (buttons.get(ButtonEnum.Cancel) != null) {
    		buttons.get(ButtonEnum.Cancel).setVisible(true);
    	}
    	
    	if (buttons.get(ButtonEnum.Ok) != null) {
    		buttons.get(ButtonEnum.Ok).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Search) != null) {
    		buttons.get(ButtonEnum.Search).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Clear) != null) {
    		buttons.get(ButtonEnum.Clear).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other1) != null) {
    		buttons.get(ButtonEnum.Other1).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other2) != null) {
    		buttons.get(ButtonEnum.Other2).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other3) != null) {
    		buttons.get(ButtonEnum.Other3).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other4) != null) {
    		buttons.get(ButtonEnum.Other4).setVisible(false);
    	}
    	
    	if (buttons.get(ButtonEnum.Other5) != null) {
    		buttons.get(ButtonEnum.Other5).setVisible(false);
    	}

    }
    public void addButtonApprove(Button btnApprove) {        
        if (btnApprove != null) {
        	buttons.put(ButtonEnum.Approve, btnApprove);
        	buttons.get(ButtonEnum.Approve).setLabel(Labels.getLabel("common.button.approve"));
        }
    }
    
    public void addButtonDisapprove(Button btnDiapprove) {        
        if (btnDiapprove != null) {
        	buttons.put(ButtonEnum.Disapprove, btnDiapprove);
        	buttons.get(ButtonEnum.Disapprove).setLabel(Labels.getLabel("common.button.dispprove"));
        }
    }
    
    public void addButtonOther1(Button btnOther1, String labelButton) {   
    	if(labelButton != null){
    		this.namaButtonOther1 = labelButton;
    	}
        if (btnOther1 != null) {
        	buttons.put(ButtonEnum.Other1, btnOther1);
        	buttons.get(ButtonEnum.Other1).setLabel(labelButton);
        }
    }
    
    public void addButtonOther2(Button btnOther2, String labelButton) {   
    	if(labelButton != null){
    		this.namaButtonOther2 = labelButton;
    	}
        if (btnOther2 != null) {
        	buttons.put(ButtonEnum.Other2, btnOther2);
        	buttons.get(ButtonEnum.Other2).setLabel(labelButton);
        }
    }
    
    public void addButtonOther3(Button btnOther3, String labelButton) {   
    	if(labelButton != null){
    		this.namaButtonOther3 = labelButton;
    	}
        if (btnOther3 != null) {
        	buttons.put(ButtonEnum.Other3, btnOther3);
        	buttons.get(ButtonEnum.Other3).setLabel(labelButton);
        }
    }
    
    public void addButtonOther4(Button btnOther4, String labelButton) {   
    	if(labelButton != null){
    		this.namaButtonOther4 = labelButton;
    	}
        if (btnOther4 != null) {
        	buttons.put(ButtonEnum.Other4, btnOther4);
        	buttons.get(ButtonEnum.Other4).setLabel(labelButton);
        }
    }
    
    public void addButtonOther5(Button btnOther5, String labelButton) {   
    	if(labelButton != null){
    		this.namaButtonOther5 = labelButton;
    	}
        if (btnOther5 != null) {
        	buttons.put(ButtonEnum.Other5, btnOther5);
        	buttons.get(ButtonEnum.Other5).setLabel(labelButton);
        }
    }
    
	/**
	 * Sets all buttons disabled/visible.<br>
	 * 
	 * @param activate
	 *            True or False
	 */
	@SuppressWarnings({ "rawtypes" })
	public void setActivateAll(boolean activate) {

		Iterator it = buttons.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			// System.out.println(pairs.getKey() + " = " + pairs.getValue());

			if (buttonsModeDisable)
				((Button) pairs.getValue()).setDisabled(activate);
			else if (!buttonsModeDisable)
				((Button) pairs.getValue()).setVisible(activate);

		}

		setActive(activate);

	}

	/**
	 * Set this ButtonController active. <br>
	 * Means show the Buttons.<br>
	 * 
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Is this ButtonController active? <br>
	 * Means does it shows the Buttons? <br>
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	public void setSecurityActive(boolean securityActive) {
		this.securityActive = securityActive;
	}

	/**
	 * Is this ButtonController security active? <br>
	 * Means does it checks for rights? <br>
	 * 
	 * @return
	 */
	public boolean isSecurityActive() {
		return securityActive;
	}
	
	//= AKAN DI DELETE ===============================================================
	/**
	 * Constructor without CLOSE button.
	 * 
	 * @param btnNew
	 *            (New Button)
	 * @param btnEdit
	 *            (Edit Button)
	 * @param btnDelete
	 *            (Delete Button)
	 * @param btnSave
	 *            (Save Button)
	 * @param btnCancel
	 *            (Cancel Button)
	 */
	public ButtonStatusCtrl(UserWorkspace userWorkspace, String rightPrefix,
			Button btnNew, Button btnEdit, Button btnDelete, Button btnSave,
			Button btnCancel) {
		super();
		this.workspace = userWorkspace;
		this._rightPrefix = rightPrefix + "btn";
		this.closeButton = false;

//		buttons.put(ButtonEnum.New, btnNew);
//		buttons.put(ButtonEnum.Edit, btnEdit);
//		buttons.put(ButtonEnum.Delete, btnDelete);
//		buttons.put(ButtonEnum.Save, btnSave);
//		buttons.put(ButtonEnum.Cancel, btnCancel);

//		setBtnImages();
		
		if (btnNew != null) {
        	buttons.put(ButtonEnum.New, btnNew);
        	buttons.get(ButtonEnum.New).setLabel(Labels.getLabel("common.button.new"));
        }
        
        if (btnEdit != null) {
        	buttons.put(ButtonEnum.Edit, btnEdit);
        	buttons.get(ButtonEnum.Edit).setLabel(Labels.getLabel("common.button.edit"));
        }
        
        if (btnDelete != null) {
        	buttons.put(ButtonEnum.Delete, btnDelete);
        	buttons.get(ButtonEnum.Delete).setLabel(Labels.getLabel("common.button.delete"));
        }        
           
        if (btnSave != null) {
        	buttons.put(ButtonEnum.Save, btnSave);
        	buttons.get(ButtonEnum.Save).setLabel(Labels.getLabel("common.button.save"));
        	buttons.get(ButtonEnum.Save).setVisible(false);
        }
        
        if (btnCancel != null) {
        	buttons.put(ButtonEnum.Cancel, btnCancel);
        	buttons.get(ButtonEnum.Cancel).setLabel(Labels.getLabel("common.button.cancel"));
        	buttons.get(ButtonEnum.Cancel).setVisible(false);
        }
        
        

		// nanda edit, default : please comment
		setInitEdit();
	}

	/**
	 * Constructor with CLOSE button.
	 * 
	 * @param btnNew
	 *            (New Button)
	 * @param btnEdit
	 *            (Edit Button)
	 * @param btnDelete
	 *            (Delete Button)
	 * @param btnSave
	 *            (Save Button)
	 * @param btnCancel
	 *            (Cancel Button)
	 * @param btnClose
	 *            (Close Button)
	 */
	public ButtonStatusCtrl(UserWorkspace userWorkspace, String rightPrefix,
			Button btnNew, Button btnEdit, Button btnDelete, Button btnSave,
			Button btnCancel, Button btnClose) {
		super();
		this.workspace = userWorkspace;
		this._rightPrefix = rightPrefix + "btn";
		this.closeButton = true;

		buttons.put(ButtonEnum.New, btnNew);
		buttons.put(ButtonEnum.Edit, btnEdit);
		buttons.put(ButtonEnum.Delete, btnDelete);
		buttons.put(ButtonEnum.Save, btnSave);
		buttons.put(ButtonEnum.Cancel, btnCancel);
		buttons.put(ButtonEnum.Close, btnClose);

		setBtnImages();
	}
	
//	public ButtonStatusCtrl(UserWorkspace userWorkspace, String rightPrefix, 
//			Button btnEdit, Button btnListing, Button btnOk, Button btnSave,
//			Button btnCancel, Button btnSearch, Button btnClear) {
//		super();
//		this.workspace = userWorkspace;
//		this._rightPrefix = rightPrefix + "btn";
//		this.closeButton = true;
//
//		buttons.put(ButtonEnum.Edit, btnEdit);
//		buttons.put(ButtonEnum.Listing, btnListing);
//		buttons.put(ButtonEnum.Ok, btnOk);
//		buttons.put(ButtonEnum.Save, btnSave);
//		buttons.put(ButtonEnum.Cancel, btnCancel);
//		buttons.put(ButtonEnum.Search, btnSearch);
//		buttons.put(ButtonEnum.Clear, btnClear);
//
//		setBtnImages();
//	}

	/**
	 * Set the images for the buttons.<br>
	 */
	private void setBtnImages() {
		String imagePath = "/images/icons/";

		setImage(ButtonEnum.New, imagePath + "btn_new2_16x16.gif");
		setImage(ButtonEnum.Edit, imagePath + "btn_edit2_16x16.gif");
		setImage(ButtonEnum.Delete, imagePath + "btn_delete2_16x16.gif");
		setImage(ButtonEnum.Save, imagePath + "btn_save2_16x16.gif");
		setImage(ButtonEnum.Cancel, imagePath + "btn_cancel2_16x16.gif");

		if (closeButton) {
			setImage(ButtonEnum.Close, imagePath + "btn_exitdoor2_16x16.gif");
		}

	}

	/**
	 * Set all Buttons for the Mode NEW is pressed. <br>
	 */
	public void setBtnStatus_New() {
		if (buttonsModeDisable) {
			setDisabled(ButtonEnum.New, true);
			setDisabled(ButtonEnum.Edit, true);
			setDisabled(ButtonEnum.Delete, true);
			setDisabled(ButtonEnum.Save, false);
			setDisabled(ButtonEnum.Cancel, false);
			if (closeButton) {
				setDisabled(ButtonEnum.Close, false);
			}

		} else {
			setVisible(ButtonEnum.New, false);
			setVisible(ButtonEnum.Edit, false);
			setVisible(ButtonEnum.Delete, false);
			setVisible(ButtonEnum.Save, true);
			setVisible(ButtonEnum.Cancel, true);
			if (closeButton) {
				setVisible(ButtonEnum.Close, true);
			}
		}
	}

	/**
	 * Set all Buttons for the Mode EDIT is pressed. <br>
	 */
	public void setBtnStatus_Edit() {
		if (buttonsModeDisable) {
			setDisabled(ButtonEnum.New, true);
			setDisabled(ButtonEnum.Edit, true);
			setDisabled(ButtonEnum.Delete, true);
			setDisabled(ButtonEnum.Save, false);
			setDisabled(ButtonEnum.Cancel, false);
			if (closeButton) {
				setDisabled(ButtonEnum.Close, false);
			}
		} else {
			setVisible(ButtonEnum.New, false);
			setVisible(ButtonEnum.Edit, false);
			setVisible(ButtonEnum.Delete, false);
			setVisible(ButtonEnum.Save, true);
			setVisible(ButtonEnum.Cancel, true);
			if (closeButton) {
				setVisible(ButtonEnum.Close, true);
			}
		}
	}

	/**
	 * Not needed yet, because after pressed the delete button <br>
	 * the window is closing. <br>
	 */
	public void setBtnStatus_Delete() {
	}

	/**
	 * Set all Buttons for the Mode SAVE is pressed. <br>
	 */
	public void setBtnStatus_Save() {
		setInitEdit();
	}

	/**
	 * Set all Buttons for the Mode init in EDIT mode. <br>
	 * This means that the Dialog window is opened and <br>
	 * shows data. <br>
	 */
	public void setInitEdit() {
		if (buttonsModeDisable) {
			setDisabled(ButtonEnum.New, false);
			setDisabled(ButtonEnum.Edit, false);
			setDisabled(ButtonEnum.Delete, false);
			setDisabled(ButtonEnum.Save, true);
			setDisabled(ButtonEnum.Cancel, true);
			if (closeButton) {
				setDisabled(ButtonEnum.Close, false);
			}
		} else {
			setVisible(ButtonEnum.New, true);
			setVisible(ButtonEnum.Edit, true);
			setVisible(ButtonEnum.Delete, true);
			setVisible(ButtonEnum.Save, false);
			setVisible(ButtonEnum.Cancel, false);
			if (closeButton) {
				setVisible(ButtonEnum.Close, true);
			}
		}
	}

	/**
	 * Set all Buttons for the Mode init in NEW mode. <br>
	 * This means that the Dialog window is freshly new <br>
	 * and have no data. <br>
	 */
	public void setInitNew() {
		if (buttonsModeDisable) {
			setDisabled(ButtonEnum.New, true);
			setDisabled(ButtonEnum.Edit, true);
			setDisabled(ButtonEnum.Delete, true);
			setDisabled(ButtonEnum.Save, false);
			setDisabled(ButtonEnum.Cancel, false);
			if (closeButton) {
				setDisabled(ButtonEnum.Close, false);
			}
		} else {
			setVisible(ButtonEnum.New, false);
			setVisible(ButtonEnum.Edit, false);
			setVisible(ButtonEnum.Delete, false);
			setVisible(ButtonEnum.Save, true);
			setVisible(ButtonEnum.Cancel, true);
			if (closeButton) {
				setVisible(ButtonEnum.Close, true);
			}
		}
	}

	/**
	 * Sets the image of a button.<br>
	 * 
	 * @param b
	 * @param imagePath
	 *            path and image name
	 */
	private void setImage(ButtonEnum b, String imagePath) {
		if (buttons.get(b) != null) {
			buttons.get(b).setImage(imagePath);
		}
	}

	/**
	 * Set the button visible.<br>
	 * 
	 * @param b
	 * @param visible
	 *            True or False
	 */
	private void setVisible(ButtonEnum b, boolean visible) {
		// log.debug("button name : "+_rightPrefix+b.name());
		// check first if the ButtonController is active
		if (isActive()) {
			// check if the button is declared
			if (buttons.get(b) != null) {
				if (visible) {
					if (isSecurityActive()) {
						if (workspace.isAllowed(_rightPrefix + b.name())) {
							buttons.get(b).setVisible(visible);
						}
					} else {
						buttons.get(b).setVisible(visible);
					}
				} else {
					buttons.get(b).setVisible(visible);
				}
			}
		}
	}

	/**
	 * Sets the button disabled.<br>
	 * 
	 * @param b
	 * @param disabled
	 *            True or False
	 */
	private void setDisabled(ButtonEnum b, boolean disabled) {

		// check first if the ButtonController is active
		if (isActive()) {

			// check if the button is declared
			if (buttons.get(b) != null) {

				if (disabled) {
					buttons.get(b).setDisabled(disabled);
				} else {
					if (isSecurityActive()) {
						if (workspace.isAllowed(_rightPrefix + b.name())) {
							buttons.get(b).setDisabled(disabled);
						}
					} else
						buttons.get(b).setDisabled(disabled);
				}
			}
		}
	}



}
