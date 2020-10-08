package cz.gjkt.model;

public class DruhZnamky {

    private static int id;
    private String nazev;
    private String datum;
    private String popis;
    private int typZnamky;
    private int kurz;

    public String toString(){return "Druh Znamky: " + id + ", " + nazev + ", " + datum + ", " + popis + ", " + typZnamky + ", " + kurz;}

    public int getId(){return id;}
    public String getNazev(){return nazev;}
    public String getDatum(){return datum;}
    public String getPopis(){return popis;}
    public int getTypZnamky(){return typZnamky;}
    public int getKurz(){return kurz;}

    public void setId(int id){this.id = id;}
    public void setId(String id){this.id = Integer.parseInt(id);}
    public void setNazev(String nazev){this.nazev = nazev;}
    public void setDatum(String datum){this.datum = datum;}
    public void setPopis(String popis){this.popis = popis;}
    public void setTypZnamky(int typZnamky){this.typZnamky = typZnamky;}
    public void setTypZnamky(String typZnamky){this.typZnamky = Integer.parseInt(typZnamky);}
    public void setKurz(int kurz){this.kurz = kurz;}
    public void setKurz(String kurz){this.kurz = Integer.parseInt(kurz);}
}
