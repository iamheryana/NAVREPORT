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
package solusi.hapis.backend.security.service.impl;

import solusi.hapis.backend.security.dao.HibernateSearchSupport;
import solusi.hapis.backend.security.service.PagedListService;
import solusi.hapis.backend.util.HibernateSearchObject;
import com.trg.search.SearchResult;


import java.util.List;

/**
 * EN: Service implementation for methods that depends on
 * <b>PagedListWrapper</b>.<br>
 * DE: Service Methoden Implementierung betreffend <b>PagedListWrapper</b>.<br>
 *
 * @author bbruhns
 * @author sgerth
 */
public class PagedListServiceImpl implements PagedListService {
    private HibernateSearchSupport hibernateSearchSupport;

    public HibernateSearchSupport getHibernateSearchSupport() {
        return hibernateSearchSupport;
    }

    public void setHibernateSearchSupport(HibernateSearchSupport hibernateSearchSupport) {
        this.hibernateSearchSupport = hibernateSearchSupport;
    }

    @SuppressWarnings("unused")
    private <T> void initSearchObject(HibernateSearchObject<T> so, int start, int pageSize) {
        so.setFirstResult(start);
        so.setMaxResults(pageSize);
    }

    @Override
    public <T> List<T> getBySearchObject(HibernateSearchObject<T> so) {
        return getHibernateSearchSupport().search(so);
    }

    @Override
    public <T> SearchResult<T> getSRBySearchObject(HibernateSearchObject<T> so) {
        return getHibernateSearchSupport().searchAndCount(so);
    }

}
