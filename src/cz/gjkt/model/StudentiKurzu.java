package cz.gjkt.model;

import java.util.ArrayList;
import java.util.List;

public class StudentiKurzu {
    Kurz kurz;
    List<Student> studenti = new ArrayList<Student>();

    public StudentiKurzu(Kurz kurz){
        this.kurz=kurz;
        studenti = nactiStudenty();
    }

    public List<Student> getStudenti(){return studenti;}

    private List<Student> nactiStudenty(){
        StudentiKurzuDAO DAO = new StudentiKurzuDAO();
        return DAO.getAll();
    }
}