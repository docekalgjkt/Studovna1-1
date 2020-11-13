package cz.gjkt.model;

public class Predmet {

    private int id;
    private String nazev;
    private String popis;
    private int rocnik;
    private String zkratka;

    public String toString(){return "Predmet: " + id + ", " + nazev + ", " + popis + ", " + rocnik + "," + zkratka;};

    public int getId(){return id;}
    public String getNazev(){return nazev;}
    public String getPopis(){return popis;}
    public int getRocnik(){return rocnik;}
    public String getZkratka() {return zkratka;}

    public void setId(int id){this.id = id;}
    public void setId(String id){this.id = Integer.parseInt(id);}
    public void setRocnik(int rocnik){this.rocnik = rocnik;}
    public void setRocnik(String rocnik){this.rocnik = Integer.parseInt(rocnik);}
    public void setNazev(String nazev){this.nazev = nazev;}
    public void setPopis(String popis){this.popis = popis;}
    public void setZkratka(String zkratka){this.zkratka = zkratka;}
}
