package cz.gjkt.model;

public class SkolniRok {

    private static int id;
    private String nazev;
    private String zacatek;
    private String konec;

    public String toString(){return "Skolni Rok: " + id + ", " + nazev + ",  " + zacatek + ", " + konec;}

    public int getId(){return id;}
    public String getNazev(){return nazev;}
    public String getZacatek(){return zacatek;}
    public String getKonec() {return konec;}

    public void setId(int id){this.id = id;}
    public void setId(String id){this.id = Integer.parseInt(id);}
    public void setNazev(String nazev){this.nazev = nazev;}
    public void setZacatek(String zacatek) {this.zacatek = zacatek;}
    public void setKonec(String konec){this.konec = konec;}

}
