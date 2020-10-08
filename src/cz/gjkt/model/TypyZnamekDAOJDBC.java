package cz.gjkt.model;

import java.util.ArrayList;
import java.util.List;

public class TypyZnamekDAOJDBC implements IModelDao<TypZnamky> {

    private static final String TABLE = "TypZnamky";
    private static final String[] ALL_COLUMNS = {"id","nazev","popis","min","vaha","rocnik", "pololeti", "predmet"};
    private static final String[] UPDATE_COMUNS = {"nazev","popis","min","vaha","rocnik", "pololeti", "predmet"};

    @Override
    public int insert(TypZnamky object) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{object.getNazev(),object.getPopis(), String.valueOf(object.getMin()), String.valueOf(object.getVaha()), String.valueOf(object.getRocnik()), String.valueOf(object.getPololeti()), String.valueOf(object.getPredmet())};
        int id = dbManager.insert(TABLE,UPDATE_COMUNS,values);
        object.setId(id);
        dbManager.close();
        return id;
    }

    @Override
    public int update(TypZnamky object) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{object.getNazev(),object.getPopis(), String.valueOf(object.getMin()), String.valueOf(object.getVaha()), String.valueOf(object.getRocnik()), String.valueOf(object.getPololeti()), String.valueOf(object.getPredmet())};
        int vysledek = dbManager.update(TABLE, object.getId(),UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(TypZnamky object) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{String.valueOf(object.getId())};
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int[] insert(List<TypZnamky> objects) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        for (TypZnamky object : objects){
            String[] value = new String[]{object.getNazev(),object.getPopis(), String.valueOf(object.getMin()), String.valueOf(object.getVaha()), String.valueOf(object.getRocnik()), String.valueOf(object.getPololeti()), String.valueOf(object.getPredmet())};
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
    public int update(List<TypZnamky> objects) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        int[] ids = null;
        int i = 0;
        for(TypZnamky typZnamky : objects){
            values.add( new String[]{typZnamky.getNazev(),typZnamky.getPopis(), String.valueOf(typZnamky.getMin()), String.valueOf(typZnamky.getVaha()), String.valueOf(typZnamky.getRocnik()), String.valueOf(typZnamky.getPololeti()), String.valueOf(typZnamky.getPredmet())});
            ids[i++] = typZnamky.getId();
        }
        int vysledek = dbManager.update(TABLE,ids,UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(List<TypZnamky> objects) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{};
        int i = 0;
        for(TypZnamky typZnamky : objects){
            ids[i++] = String.valueOf(typZnamky.getId());
        }
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public TypZnamky getObject(int id) {
        DBManager dbManager = new DBManager();
        TypZnamky typZnamky = new TypZnamky();
        String[] ids = new String[]{"id="+id};
        List<List<Object>> rs = dbManager.select(TABLE,ALL_COLUMNS,ids,null);
        for(List<Object> row : rs ) {
            typZnamky.setId((int)row.get(0));
            typZnamky.setNazev((String)row.get(1));
            typZnamky.setPopis((String) row.get(2));
            typZnamky.setMin((int) row.get(3));
            typZnamky.setVaha((int) row.get(4));
            typZnamky.setRocnik((int) row.get(5));
            typZnamky.setPololeti((int) row.get(6));
            typZnamky.setPredmet((int) row.get(7));
        }
        dbManager.close();
        return typZnamky;
    }

    @Override
    public List<TypZnamky> getObjects(int[] ids) {
        DBManager dbManager = new DBManager();
        List<TypZnamky> typyZnamek = new ArrayList<>();
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
            TypZnamky typZnamky = new TypZnamky();
            typZnamky.setId((int)row.get(0));
            typZnamky.setNazev((String)row.get(1));
            typZnamky.setPopis((String) row.get(2));
            typZnamky.setMin((int) row.get(3));
            typZnamky.setVaha((int) row.get(4));
            typZnamky.setRocnik((int) row.get(5));
            typZnamky.setPololeti((int) row.get(6));
            typZnamky.setPredmet((int) row.get(7));
            typyZnamek.add(typZnamky);
        }
        return typyZnamek;
    }

    @Override
    public List<TypZnamky> getAll() {
        DBManager dbManager = new DBManager();
        List<TypZnamky> typyZnamek = new ArrayList<>();
        List<List<Object>> recordSet = dbManager.select(TABLE,ALL_COLUMNS,null,null);
        for (List<Object> row : recordSet){
            TypZnamky typZnamky = new TypZnamky();
            typZnamky.setId((int)row.get(0));
            typZnamky.setNazev((String)row.get(1));
            typZnamky.setPopis((String) row.get(2));
            typZnamky.setMin((int) row.get(3));
            typZnamky.setVaha((int) row.get(4));
            typZnamky.setRocnik((int) row.get(5));
            typZnamky.setPololeti((int) row.get(6));
            typZnamky.setPredmet((int) row.get(7));
            typyZnamek.add(typZnamky);
        }
        return typyZnamek;
    }
}
