package cz.gjkt.model;

public class Student {

    private int id;
    private String jmeno;
    private String prijmeni;
    private int rokNastupu;
    private String email;

    public String toString(){return "Student: " + id + ", " + jmeno + ", " + prijmeni + ", " + email + ", " + rokNastupu;}

    public int getId(){return id;}
    public String getJmeno(){return jmeno;}
    public String getPrijmeni(){return prijmeni;}
    public int getRokNastupu(){return rokNastupu;}
    public String getEmail() {return email;}

    public void setId(int id){this.id = id;}
    public void setId(String id){this.id = Integer.parseInt(id);}
    public void setJmeno(String jmeno){this.jmeno = jmeno;}
    public void setPrijmeni(String prijmeni) {this.prijmeni = prijmeni;}
    public void setRokNastupu(int rokNastupu) {this.rokNastupu = rokNastupu;}
    public void setRokNastupu(String rokNastupu){this.rokNastupu = Integer.parseInt(rokNastupu);}
    public void setEmail(String email){this.email = email;}

}
