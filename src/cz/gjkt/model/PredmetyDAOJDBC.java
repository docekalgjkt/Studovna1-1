package cz.gjkt.model;

import java.util.ArrayList;
import java.util.List;

public class PredmetyDAOJDBC implements IModelDao<Predmet> {

    private static final String TABLE = "Predmet";
    private static final String[] ALL_COLUMNS = {"id", "nazev", "popis", "rocnik", "zkratka"};
    private static final String[] UPDATE_COMUNS = {"nazev", "popis", "rocnik", "zkratka"};

    @Override
    public int insert(Predmet object) {
            DBManager dbManager = new DBManager();
            String[] values = new String[]{object.getNazev(), object.getPopis(), String.valueOf(object.getRocnik()), object.getZkratka()};
            int id = dbManager.insert(TABLE,UPDATE_COMUNS,values);
            object.setId(id);
            dbManager.close();
            return id;

    }

    @Override
    public int update(Predmet object) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{object.getNazev(), object.getPopis(), String.valueOf(object.getRocnik()), object.getZkratka()};
        int vysledek = dbManager.update(TABLE, object.getId(),UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(Predmet object) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{String.valueOf(object.getId())};
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int[] insert(List<Predmet> objects) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        for (Predmet object : objects){
            String[] value = new String[]{object.getNazev(), object.getPopis(), String.valueOf(object.getRocnik()), object.getZkratka()};
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
    public int update(List<Predmet> objects) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        int[] ids = null;
        int i = 0;
        for(Predmet predmet : objects){
            values.add( new String[]{predmet.getNazev(), predmet.getPopis(), String.valueOf(predmet.getRocnik()), predmet.getZkratka()});
            ids[i++] = predmet.getId();
        }
        int vysledek = dbManager.update(TABLE,ids,UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(List<Predmet> objects) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{};
        int i = 0;
        for(Predmet predmet : objects){
            ids[i++] = String.valueOf(predmet.getId());
        }
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public Predmet getObject(int id) {
        DBManager dbManager = new DBManager();
        Predmet predmet = new Predmet();
        String[] ids = new String[]{"id="+id};
        List<List<Object>> rs = dbManager.select(TABLE,ALL_COLUMNS,ids,null);
        for(List<Object> row : rs ) {
            predmet.setId((int) row.get(0));
            predmet.setNazev((String) row.get(1));
            predmet.setPopis((String) row.get(2));
            predmet.setRocnik((int) row.get(3));
            predmet.setZkratka((String) row.get(4));
        }
        dbManager.close();
        return predmet;
    }

    @Override
    public List<Predmet> getObjects(int[] ids) {
        DBManager dbManager = new DBManager();
        List<Predmet> predmety = new ArrayList<>();
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
            Predmet predmet = new Predmet();
            predmet.setId((int)row.get(0));
            predmet.setNazev((String)row.get(1));
            predmet.setPopis((String)row.get(2));
            predmet.setRocnik((int)row.get(3));
            predmet.setZkratka((String)row.get(4));
            predmety.add(predmet);
        }
        return predmety;
    }

    @Override
    public List<Predmet> getAll() {
        DBManager dbManager = new DBManager();
        List<Predmet> predmety = new ArrayList<>();
        List<List<Object>> recordSet = dbManager.select(TABLE,ALL_COLUMNS,null,null);
        for (List<Object> row : recordSet){
            Predmet predmet = new Predmet();
            predmet.setId((int)row.get(0));
            predmet.setNazev((String)row.get(1));
            predmet.setPopis((String)row.get(2));
            predmet.setRocnik((int)row.get(3));
            predmet.setZkratka((String)row.get(4));
            predmety.add(predmet);
        }
        return predmety;
    }
}
