package cz.gjkt.model;

import java.util.List;

public class StudentiKurzuDAO extends StudentiDAOJDBC {

    private Kurz kurz;

    public StudentiKurzuDAO() {
        this.kurz = kurz;
    }

    public StudentiKurzuDAO(int kurzID){
        KurzyDAOJDBC kurzyDAOJDBC = new KurzyDAOJDBC();
        Kurz kurz = kurzyDAOJDBC.getObject(kurzID);
        this.kurz = kurz;

    }


    @Override
    public List<Student> getAll() {
        List<Student> studenti = getStudenti(getIDsStudentu());
        return studenti;
    }

    private List<List<Object>> getIdsStudentu() {
        DBManager dbManager = new DBManager();
        String table = "Navstevuje";
        String[] columns = {"student"};
        String[] aCon = {"kurz="+kurz.getId()};
        String[] oCon = null;
        List<List<Object>> studentiIds = dbManager.select(table,columns,aCon,oCon);
        return studentiIds;
    }

    private int[] getIDsStudentu() {
        List<List<Object>> studentiIds = getIdsStudentu();
        int[] studentiIDs = new int [studentiIds.size()];
        int i = 0;
        for(List<Object> id : studentiIds) {
            studentiIDs[i++] = (int)id.get(0);
        }
        return studentiIDs;
    }


/*      public List<Student> getObjects(int[] ids) {
        DBManager dbManager = new DBManager();
        String table = "Navstevuje";
        String[] columns = {"student"};
        String[] aCon = {"kurz="+ids[0]};
        String[] oCon = null;
        List<List<Object>> studentiIds = dbManager.select(table,columns,aCon,oCon);
        int[] studentiIDs = new int[studentiIds.size()];
        int i = 0;
        for(List<Object> id : studentiIds){
            studentiIDs[i++] = (int)id.get(0);
        }
        StudentiDAOJDBC studentiDAOJDBC = new StudentiDAOJDBC();
        List<Student> studenti = studentiDAOJDBC.getStudenti(studentiIDs);
        return studenti;
    }*/


}