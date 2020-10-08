package cz.gjkt.model;

import java.util.ArrayList;
import java.util.List;

public class SkolniRokyDAOJDBC implements IModelDao<SkolniRok> {

    private static final String TABLE = "SkolniRok";
    private static final String[] ALL_COLUMNS = {"id","nazev","zacatek","konec"};
    private static final String[] UPDATE_COMUNS = {"nazev","zacatek","konec"};



    @Override
    public int insert(SkolniRok object) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{object.getNazev(),object.getZacatek(),object.getKonec()};
        int id = dbManager.insert(TABLE,UPDATE_COMUNS,values);
        object.setId(id);
        dbManager.close();
        return id;
    }

    @Override
    public int update(SkolniRok object) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{object.getNazev(),object.getZacatek(),object.getKonec()};
        int vysledek = dbManager.update(TABLE, object.getId(),UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(SkolniRok object) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{String.valueOf(object.getId())};
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int[] insert(List<SkolniRok> objects) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        for (SkolniRok object : objects){
            String[] value = new String[]{object.getNazev(),object.getZacatek(),object.getKonec()};
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
    public int update(List<SkolniRok> objects) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        int[] ids = null;
        int i = 0;
        for(SkolniRok skolniRok : objects){
            values.add( new String[]{skolniRok.getNazev(),skolniRok.getZacatek(),skolniRok.getKonec()});
            ids[i++] = skolniRok.getId();
        }
        int vysledek = dbManager.update(TABLE,ids,UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(List<SkolniRok> objects) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{};
        int i = 0;
        for(SkolniRok skolniRok : objects){
            ids[i++] = String.valueOf(skolniRok.getId());
        }
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public SkolniRok getObject(int id) {
        DBManager dbManager = new DBManager();
        SkolniRok skolniRok = new SkolniRok();
        String[] ids = new String[]{"id="+id};
        List<List<Object>> rs = dbManager.select(TABLE,ALL_COLUMNS,ids,null);
        for(List<Object> row : rs ) {
            skolniRok.setId((int)row.get(0));
            skolniRok.setNazev((String)row.get(1));
            skolniRok.setZacatek((String) row.get(2));
            skolniRok.setKonec((String) row.get(3));
        }
        dbManager.close();
        return skolniRok;
    }

    @Override
    public List<SkolniRok> getObjects(int[] ids) {
        DBManager dbManager = new DBManager();
        List<SkolniRok> skolniRoky = new ArrayList<>();
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
            SkolniRok skolniRok = new SkolniRok();
            skolniRok.setId((int)row.get(0));
            skolniRok.setNazev((String)row.get(1));
            skolniRok.setZacatek((String) row.get(2));
            skolniRok.setKonec((String) row.get(3));
            skolniRoky.add(skolniRok);
        }
        return skolniRoky;
    }

    @Override
    public List<SkolniRok> getAll() {
        DBManager dbManager = new DBManager();
        List<SkolniRok> skolniRoky = new ArrayList<>();
        List<List<Object>> recordSet = dbManager.select(TABLE,ALL_COLUMNS,null,null);
        for (List<Object> row : recordSet){
            SkolniRok skolniRok = new SkolniRok();
            skolniRok.setId((int)row.get(0));
            skolniRok.setNazev((String)row.get(1));
            skolniRok.setZacatek((String) row.get(2));
            skolniRok.setKonec((String) row.get(3));
            skolniRoky.add(skolniRok);
        }
        return skolniRoky;
    }
}
