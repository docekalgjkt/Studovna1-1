package cz.gjkt.model;

import java.util.ArrayList;
import java.util.List;

public class KurzyDAOJDBC implements IModelDao<Kurz> {

    private static final String TABLE = "Kurz";
    private static final String[] ALL_COLUMNS = {"id","nazev","skolniRok","predmet"};
    private static final String[] UPDATE_COMUNS = {"nazev","skolniRok","predmet",};

    @Override
    public int insert(Kurz object) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{object.getNazev(), String.valueOf(object.getSkolniRok()), String.valueOf(object.getPredmet())};
        int id = dbManager.insert(TABLE,UPDATE_COMUNS,values);
        object.setId(id);
        dbManager.close();
        return id;
    }

    @Override
    public int update(Kurz object) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{object.getNazev(), String.valueOf(object.getSkolniRok()), String.valueOf(object.getPredmet())};
        int vysledek = dbManager.update(TABLE, object.getId(),UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(Kurz object) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{String.valueOf(object.getId())};
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int[] insert(List<Kurz> objects) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        for (Kurz object : objects){
            String[] value = new String[]{object.getNazev(), String.valueOf(object.getSkolniRok()), String.valueOf(object.getPredmet())};
            values.add(value);
        }
        List<Integer> ids = dbManager.insert(TABLE,UPDATE_COMUNS,values);
        int[] vysledek = new int[ids.size()];
        int i=0;
        for (int id : ids) {
            objects.get(i).setId(id);
            vysledek[i++] = id;
        }
        dbManager.close();
        return vysledek;
    }

    @Override
    public int update(List<Kurz> objects) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        int[] ids = null;
        int i = 0;
        for(Kurz kurz : objects){
            values.add( new String[]{kurz.getNazev(), String.valueOf(kurz.getSkolniRok()), String.valueOf(kurz.getPredmet())});
            ids[i++] = kurz.getId();
        }
        int vysledek = dbManager.update(TABLE,ids,UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(List<Kurz> objects) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{};
        int i = 0;
        for(Kurz kurz : objects){
            ids[i++] = String.valueOf(kurz.getId());
        }
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public Kurz getObject(int id) {
        DBManager dbManager = new DBManager();
        Kurz kurz = new Kurz();
        String[] ids = new String[]{"id="+id};
        List<List<Object>> rs = dbManager.select(TABLE,ALL_COLUMNS,ids,null);
        for(List<Object> row : rs ) {
            kurz.setId((int)row.get(0));
            kurz.setSkolniRok((int)row.get(1));
            kurz.setPredmet((int) row.get(2));
        }
        dbManager.close();
        return kurz;
    }

    @Override
    public List<Kurz> getObjects(int[] ids) {
        DBManager dbManager = new DBManager();
        List<Kurz> kurzy = new ArrayList<>();
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
            Kurz kurz = new Kurz();
            kurz.setId((int)row.get(0));
            kurz.setNazev((String) row.get(1));
            kurz.setSkolniRok((int)row.get(2));
            kurz.setPredmet((int) row.get(3));
            kurzy.add(kurz);
        }
        return kurzy;
    }

    @Override
    public List<Kurz> getAll() {
        DBManager dbManager = new DBManager();
        List<Kurz> kurzy = new ArrayList<>();
        List<List<Object>> recordSet = dbManager.select(TABLE,ALL_COLUMNS,null,null);
        for (List<Object> row : recordSet){
            Kurz kurz = new Kurz();
            kurz.setId((int)row.get(0));
            kurz.setNazev((String)row.get(1));
            kurz.setSkolniRok((int) row.get(2));
            kurz.setPredmet((int) row.get(3));
           kurzy.add(kurz);
        }
        return kurzy;
    }
}
