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

import java.util.Date;

/**
 * EN: Model class for <b>SecLoginlog</b>.<br>
 * DE: Model Klasse fuer <b>Login Log</b>.<br>
 *
 * @author bbruhns
 * @author sgerth
 */
public class SecLoginlog implements java.io.Serializable, Entity {

    private static final long serialVersionUID = -2628240632347849393L;

    private long id = Long.MIN_VALUE;
    private Ip2Country ip2Country;
    private String lglLoginname;
    private Date lglLogtime;
    private String lglSessionid;
    private String lglIp;
    private String browserType;
    private int lglStatusid;
    private int version;

    @Override
    public boolean isNew() {
        return (getId() == Long.MIN_VALUE);
    }

    public SecLoginlog() {
    }

    public SecLoginlog(long id, String lglLoginname, Date lglLogin, int lglStatusid) {
        this.setId(id);
        this.lglLoginname = lglLoginname;
        this.lglLogtime = lglLogin;
        this.lglStatusid = lglStatusid;
    }

    public SecLoginlog(long id, String lglLoginname, Date lglLogtime, String lglSessionid, String lglIp, int lglStatusid, String browserType) {
        this.setId(id);
        this.lglLoginname = lglLoginname;
        this.lglLogtime = lglLogtime;
        this.lglSessionid = lglSessionid;
        this.lglIp = lglIp;
        this.lglStatusid = lglStatusid;
        this.browserType = browserType;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    /**
     * EN: Hibernate version field. Do not touch this!.<br>
     * DE: Hibernate Versions Info. Bitte nicht benutzen!<br>
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * EN: Hibernate version field. Do not touch this!.<br>
     * DE: Hibernate Versions Info. Bitte nicht benutzen!<br>
     */
    public int getVersion() {
        return version;
    }

    public void setIp2Country(Ip2Country ip2Country) {
        this.ip2Country = ip2Country;
    }

    public Ip2Country getIp2Country() {
        return ip2Country;
    }

    public String getLglLoginname() {
        return this.lglLoginname;
    }

    public void setLglLoginname(String lglLoginname) {
        this.lglLoginname = lglLoginname;
    }

    public Date getLglLogtime() {
        return this.lglLogtime;
    }

    public void setLglLogtime(Date lglLogtime) {
        this.lglLogtime = lglLogtime;
    }

    public String getLglSessionid() {
        return this.lglSessionid;
    }

    public void setLglSessionid(String lglSessionid) {
        this.lglSessionid = lglSessionid;
    }

    public String getLglIp() {
        return this.lglIp;
    }

    public void setLglIp(String lglIp) {
        this.lglIp = lglIp;
    }

    public int getLglStatusid() {
        return this.lglStatusid;
    }

    public void setLglStatusid(int lglStatusid) {
        this.lglStatusid = lglStatusid;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getBrowserType() {
        return browserType;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    public boolean equals(SecLoginlog secLoginlog) {
        return getId() == secLoginlog.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SecLoginlog) {
            SecLoginlog secLoginlog = (SecLoginlog) obj;
            return equals(secLoginlog);
        }

        return false;
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }

}
