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

import java.util.HashSet;
import java.util.List;

import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;

import solusi.hapis.webui.util.pagging.PagedBindingListWrapper;
import solusi.hapis.webui.util.pagging.PagedListWrapper;

/**
 * Extended the GFCBase controller for a pagedListWrapper for a single type.
 *
 * @author bbruhns
 * @author sgerth
 * @changes 05/18/2010 sge extended for the Paged<b>Binding</b>ListWrapper that
 * can work with zk's databinding mechanism.
 */
public class GFCBaseListCtrl<T> extends GFCBaseCtrl {

    private static final long serialVersionUID = -3741197830243792411L;

    private PagedListWrapper<T> pagedListWrapper;

    private PagedBindingListWrapper<T> pagedBindingListWrapper;

    public PagedListWrapper<T> getPagedListWrapper() {
    	return pagedListWrapper;
    }

    public void setPagedListWrapper(PagedListWrapper<T> pagedListWrapper) {
    	this.pagedListWrapper = pagedListWrapper;
    }

    public void setPagedBindingListWrapper(PagedBindingListWrapper<T> pagedBindingListWrapper) {
    	this.pagedBindingListWrapper = pagedBindingListWrapper;
    }

    public PagedBindingListWrapper<T> getPagedBindingListWrapper() {
    	return pagedBindingListWrapper;
    }
    
    public HashSet<T> refresh(ListModelList lml, List<T> list, Listbox listBox, Paging paging, Integer start) {
    	lml.clear();
		if (list.size() > 0) {
			int end = 0;
			if(start + paging.getPageSize() < list.size()) {
				end = start+ paging.getPageSize();
			} else {
				end = list.size();
			}
			lml.addAll(list.subList(start, end));
			paging.setDetailed(true);
			paging.setTotalSize(list.size());
			
			listBox.setModel(lml);
			return new HashSet<T>(list);
		} else {
			paging.setDetailed(false);
			listBox.setModel(lml);
			return null;
		}
    }
    
    public List<?> refresh2(ListModelList lml, List<?> list, Listbox listBox, Paging paging, Integer start) {
    	lml.clear();
		if (list.size() > 0) {
			int end = 0;
			if(start + paging.getPageSize() < list.size()) {
				end = start+ paging.getPageSize();
			} else {
				end = list.size();
			}
			lml.addAll(list.subList(start, end));
			paging.setDetailed(true);
			paging.setTotalSize(list.size());
			
			listBox.setModel(lml);
			return list;
		} else {
			paging.setDetailed(false);
			listBox.setModel(lml);
			return null;
		}
    }

}
