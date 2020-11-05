package cz.gjkt.model;

public class Predmet {

    private int id;
    private String nazev;
    private int rocnik;
    private String zkratka;

    public String toString(){return "Predmet: " + id + ", " + nazev + ", " + rocnik + "," + zkratka;};

    public int getId(){return id;}
    public String getNazev(){return nazev;}
    public int getRocnik(){return rocnik;}
    public String getZkratka() {return zkratka;}

    public void setId(int id){this.id = id;}
    public void setId(String id){this.id = Integer.parseInt(id);}
    public void setRocnik(int rocnik){this.rocnik = rocnik;}
    public void setRocnik(String rocnik){this.rocnik = Integer.parseInt(rocnik);}
    public void setNazev(String nazev){this.nazev = nazev;}
    public void setZkratka(String zkratka){this.zkratka  = zkratka;}
}
