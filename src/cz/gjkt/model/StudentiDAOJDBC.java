package cz.gjkt.model;

import java.util.ArrayList;
import java.util.List;

public class StudentiDAOJDBC implements IStudentiDAO {

    private static final String TABLE = "Student";
    private static final String[] ALL_COLUMNS = {"id","jmeno","prijmeni","email","rokNastupu","fotka"};
    private static final String[] UPDATE_COMUNS = {"jmeno","prijmeni","email","rokNastupu","fotka"};

    @Override
    public int insert(Student student) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{student.getJmeno(),student.getPrijmeni(),student.getEmail(),String.valueOf(student.getRokNastupu()),student.getFotka()};
        int id = dbManager.insert(TABLE,UPDATE_COMUNS,values);
        student.setId(id);
        dbManager.close();
        return id;
    }

    @Override
    public int update(Student student) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{student.getJmeno(),student.getPrijmeni(),student.getEmail(),String.valueOf(student.getRokNastupu()),student.getFotka()};
        int vysledek = dbManager.update(TABLE,student.getId(),UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(Student student) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{String.valueOf(student.getId())};
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int update(List<Student> studenti) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        int[] ids = null;
        int i = 0;
        for(Student student : studenti){
            values.add( new String[]{student.getJmeno(),student.getPrijmeni(),student.getEmail(),String.valueOf(student.getRokNastupu()),student.getFotka()});
            ids[i++] = student.getId();
        }
        int vysledek = dbManager.update(TABLE,ids,UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(List<Student> studenti) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{};
        int i = 0;
        for(Student student : studenti){
            ids[i++] = String.valueOf(student.getId());
        }
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public Student getStudent(int id) {
        DBManager dbManager = new DBManager();
        Student student = new Student();
        String[] ids = new String[]{"id="+id};
        List<List<Object>> rs = dbManager.select(TABLE,ALL_COLUMNS,ids,null);
        for(List<Object> row : rs ) {
            student.setId((int)row.get(0));
            student.setJmeno((String)row.get(1));
            student.setPrijmeni((String) row.get(2));
            student.setEmail((String) row.get(3));
            student.setRokNastupu((int) row.get(4));
            student.setFotka((String) row.get(5));
        }
        dbManager.close();
        return student;
    }

    @Override
    public List<Student> getStudenti(int[] ids) {
        DBManager dbManager = new DBManager();
        List<Student> studenti = new ArrayList<>();
        StringBuilder cond = new StringBuilder();
        cond.append("id in (");
        for (int id : ids){
            cond.append(id);
            cond.append(",");
        }
        cond.deleteCharAt(cond.lastIndexOf(","));
        cond.append(")");
        String[] condition = new String[]{cond.toString()};
        List<List<Object>> recordSet = dbManager.select(TABLE,ALL_COLUMNS,condition,null);
        for (List<Object> row : recordSet){
            Student student = new Student();
            student.setId((int)row.get(0));
            student.setJmeno((String)row.get(1));
            student.setPrijmeni((String) row.get(2));
            student.setEmail((String) row.get(3));
            student.setRokNastupu((int) row.get(4));
            student.setFotka((String) row.get(5));
            studenti.add(student);
        }
        return studenti;
    }

    @Override
    public List<Student> getAll() {
        DBManager dbManager = new DBManager();
        List<Student> studenti = new ArrayList<>();
        List<List<Object>> recordSet = dbManager.select(TABLE,ALL_COLUMNS,null,null);
        for (List<Object> row : recordSet){
            Student student = new Student();
            student.setId((int)row.get(0));
            student.setJmeno((String)row.get(1));
            student.setPrijmeni((String) row.get(2));
            student.setEmail((String) row.get(3));
            student.setRokNastupu((int) row.get(4));
            student.setFotka((String) row.get(5));
            studenti.add(student);
        }
        return studenti;
    }
}
