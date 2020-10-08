package cz.gjkt.model;

public class StudentPersisterJDBC {

    private final String TABLE = "Student";
    private final String[] COLUMNS = {"jmeno","prijmeni","email","rokNastupu","fotka"};


    public void serialize(Student student){
        DBManager dbManager = new DBManager();
        String[] values = new String[5];
        values[0] = student.getJmeno();
        values[1] = student.getPrijmeni();
        values[2] = student.getEmail();
        values[3] = "" + student.getRokNastupu();
        values[4] = student.getFotka();

        if (student.getId()>0){
            dbManager.update(TABLE,student.getId(),COLUMNS,values);
        }else {
            student.setId(dbManager.insert(TABLE, COLUMNS, values));
        }
    }

    public Student deserialize(int id){
        return null;
    }
}
