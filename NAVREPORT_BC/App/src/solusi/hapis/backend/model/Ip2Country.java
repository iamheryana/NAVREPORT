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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * EN: Model class for <b>Ip2Country</b>.<br>
 * DE: Model Klasse fuer <b>Ip2Country</b>.<br>
 *
 * @author bbruhns
 * @author sgerth
 */
public class Ip2Country implements Serializable, Entity {

    private static final long serialVersionUID = -8799762516266595746L;

    private long id = Long.MIN_VALUE;
    private CountryCode countryCode;
    private String i2cCity;
    private Float i2cLatitude;
    private Float i2cLongitude;
    private int version;
    private Set<SecLoginlog> secLoginlogs = new HashSet<SecLoginlog>(0);

    public boolean isNew() {
        return (getId() == Long.MIN_VALUE);
    }

    public Ip2Country() {
    }

    public Ip2Country(long id) {
        this.setId(id);
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

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setI2cCity(String i2cCity) {
        this.i2cCity = i2cCity;
    }

    public String getI2cCity() {
        return i2cCity;
    }

    public void setI2cLatitude(Float i2cLatitude) {
        this.i2cLatitude = i2cLatitude;
    }

    public Float getI2cLatitude() {
        return i2cLatitude;
    }

    public void setI2cLongitude(Float i2cLongitude) {
        this.i2cLongitude = i2cLongitude;
    }

    public Float getI2cLongitude() {
        return i2cLongitude;
    }

    public void setSecLoginlogs(Set<SecLoginlog> secLoginlogs) {
        this.secLoginlogs = secLoginlogs;
    }

    public Set<SecLoginlog> getSecLoginlogs() {
        return secLoginlogs;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    public boolean equals(Ip2Country ip2Country) {
        return getId() == ip2Country.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Ip2Country) {
            Ip2Country ip2Country = (Ip2Country) obj;
            return equals(ip2Country);
        }

        return false;
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }

}
