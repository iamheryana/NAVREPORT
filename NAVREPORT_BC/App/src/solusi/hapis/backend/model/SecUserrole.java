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
package solusi.hapis.backend.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * EN: Model class for <b>SecUserrole</b>.<br>
 * DE: Model Klasse fuer <b>UserRolle</b>.<br>
 *
 * @author bbruhns
 * @author sgerth
 */
public class SecUserrole implements java.io.Serializable, Entity {

    private static final long serialVersionUID = 6613720067926409622L;

    private long id = Long.MIN_VALUE;
    private int version;
    private SecUser secUser;
    private SecRole secRole;

    public boolean isNew() {
        return (getId() == Long.MIN_VALUE);
    }

    public SecUserrole() {
    }

    public SecUserrole(long id) {
        this.setId(id);
    }

    public SecUserrole(long id, SecUser secUser, SecRole secRole) {
        this.setId(id);
        this.secUser = secUser;
        this.secRole = secRole;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    /**
     * EN: Hibernate version field. Do not touch this!.<br>
     * DE: Hibernate Versions Info. Bitte nicht benutzen!<br>
     */
    public int getVersion() {
        return this.version;
    }

    /**
     * EN: Hibernate version field. Do not touch this!.<br>
     * DE: Hibernate Versions Info. Bitte nicht benutzen!<br>
     */
    public void setVersion(int version) {
        this.version = version;
    }

    public SecUser getSecUser() {
        return this.secUser;
    }

    public void setSecUser(SecUser secUser) {
        this.secUser = secUser;
    }

    public SecRole getSecRole() {
        return this.secRole;
    }

    public void setSecRole(SecRole secRole) {
        this.secRole = secRole;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    public boolean equals(SecUserrole secUserrole) {
        return getId() == secUserrole.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SecUserrole) {
            SecUserrole secUserrole = (SecUserrole) obj;
            return equals(secUserrole);
        }

        return false;
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }

}
