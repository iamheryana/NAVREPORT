package solusi.hapis.backend.model;
// Generated 06 Mar 12 10:03:27 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Kunde generated by hbm2java
 */
public class Kunde implements java.io.Serializable {


    private int kunId;
    private int version;
    private Filiale filiale;
    private String kunNr;
    private String kunMatchcode;
    private String kunName1;
    private String kunName2;
    private String kunOrt;
    private Short kunMahnsperre;
    private Set<Auftrag> auftrags = new HashSet<Auftrag>(0);

    public Kunde() {
    }


    public Kunde(int kunId, Filiale filiale, String kunNr) {
        this.kunId = kunId;
        this.filiale = filiale;
        this.kunNr = kunNr;
    }

    public Kunde(int kunId, Filiale filiale, String kunNr, String kunMatchcode, String kunName1, String kunName2, String kunOrt, Short kunMahnsperre, Set<Auftrag> auftrags) {
        this.kunId = kunId;
        this.filiale = filiale;
        this.kunNr = kunNr;
        this.kunMatchcode = kunMatchcode;
        this.kunName1 = kunName1;
        this.kunName2 = kunName2;
        this.kunOrt = kunOrt;
        this.kunMahnsperre = kunMahnsperre;
        this.auftrags = auftrags;
    }

    public int getKunId() {
        return this.kunId;
    }

    public void setKunId(int kunId) {
        this.kunId = kunId;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Filiale getFiliale() {
        return this.filiale;
    }

    public void setFiliale(Filiale filiale) {
        this.filiale = filiale;
    }


    public String getKunNr() {
        return this.kunNr;
    }

    public void setKunNr(String kunNr) {
        this.kunNr = kunNr;
    }

    public String getKunMatchcode() {
        return this.kunMatchcode;
    }

    public void setKunMatchcode(String kunMatchcode) {
        this.kunMatchcode = kunMatchcode;
    }

    public String getKunName1() {
        return this.kunName1;
    }

    public void setKunName1(String kunName1) {
        this.kunName1 = kunName1;
    }

    public String getKunName2() {
        return this.kunName2;
    }

    public void setKunName2(String kunName2) {
        this.kunName2 = kunName2;
    }

    public String getKunOrt() {
        return this.kunOrt;
    }

    public void setKunOrt(String kunOrt) {
        this.kunOrt = kunOrt;
    }

    public Short getKunMahnsperre() {
        return this.kunMahnsperre;
    }

    public void setKunMahnsperre(Short kunMahnsperre) {
        this.kunMahnsperre = kunMahnsperre;
    }

    public Set<Auftrag> getAuftrags() {
        return this.auftrags;
    }

    public void setAuftrags(Set<Auftrag> auftrags) {
        this.auftrags = auftrags;
    }


}


