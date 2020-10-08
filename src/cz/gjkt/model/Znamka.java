package cz.gjkt.model;

public class Znamka {

    private int id;
    private int druhZnamky;
    private int student;
    private String datum;
    private int hodnota;
    private String popis;

    public String toString(){return "Znamka: " + id + ", " + druhZnamky + ", " + student + ", " + datum + ", " + hodnota + ", " + popis;}

    public int getId(){return id;}
    public int getDruhZnamky(){return druhZnamky;}
    public int getStudent(){return student;}
    public String getDatum(){return datum;}
    public int getHodnota(){return hodnota;}
    public String getPopis(){return popis;}

    public void setId(int id){this.id = id;}
    public void setId(String id){this.id = Integer.parseInt(id);}
    public void setDruhZnamky(int druhZnamky){this.druhZnamky = druhZnamky;}
    public void setDruhZnamky(String druhZnamky){this.druhZnamky = Integer.parseInt(druhZnamky);}
    public void setStudent(int student){this.student = student;}
    public void setStudent(String student){this.student = Integer.parseInt(student);}
    public void setDatum(String datum){this.datum = datum;}
    public void setHodnota(int hodnota){this.hodnota = hodnota;}
    public void setHodnota(String hodnota){this.hodnota = Integer.parseInt(hodnota);}
    public void setPopis(String popis){this.popis = popis;}

}
