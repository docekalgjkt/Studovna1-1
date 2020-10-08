package cz.gjkt.model;

import java.util.ArrayList;
import java.util.List;

public class ZnamkyDAOJDBC implements IModelDao<Znamka> {

    private static final String TABLE = "Znamka";
    private static final String[] ALL_COLUMNS = {"id","druhZnamky","student","datum","hodnota","popis"};
    private static final String[] UPDATE_COMUNS = {"druhZnamky","student","datum","hodnota","popis"};

    @Override
    public int insert(Znamka object) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{String.valueOf(object.getDruhZnamky()), String.valueOf(object.getStudent()),object.getDatum(), String.valueOf(object.getHodnota()),object.getPopis()};
        int id = dbManager.insert(TABLE,UPDATE_COMUNS,values);
        object.setId(id);
        dbManager.close();
        return id;
    }

    @Override
    public int update(Znamka object) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{String.valueOf(object.getDruhZnamky()), String.valueOf(object.getStudent()),object.getDatum(), String.valueOf(object.getHodnota()),object.getPopis()};
        int vysledek = dbManager.update(TABLE,object.getId(),UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(Znamka object) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{String.valueOf(object.getId())};
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int[] insert(List<Znamka> objects) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        for (Znamka object : objects){
            String[] value = new String[]{String.valueOf(object.getDruhZnamky()), String.valueOf(object.getStudent()),object.getDatum(), String.valueOf(object.getHodnota()),object.getPopis()};
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
    public int update(List<Znamka> objects) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        int[] ids = null;
        int i = 0;
        for(Znamka znamka : objects){
            values.add( new String[]{String.valueOf(znamka.getDruhZnamky()), String.valueOf(znamka.getStudent()),znamka.getDatum(), String.valueOf(znamka.getHodnota()),znamka.getPopis()});
            ids[i++] = znamka.getId();
        }
        int vysledek = dbManager.update(TABLE,ids,UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(List<Znamka> objects) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{};
        int i = 0;
        for(Znamka znamka : objects){
            ids[i++] = String.valueOf(znamka.getId());
        }
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public Znamka getObject(int id) {
        DBManager dbManager = new DBManager();
        Znamka znamka = new Znamka();
        String[] ids = new String[]{"id="+id};
        List<List<Object>> rs = dbManager.select(TABLE,ALL_COLUMNS,ids,null);
        for(List<Object> row : rs ) {
            znamka.setId((int)row.get(0));
            znamka.setDruhZnamky((int)row.get(1));
            znamka.setStudent((int) row.get(2));
            znamka.setDatum((String) row.get(3));
            znamka.setHodnota((int) row.get(4));
            znamka.setPopis((String) row.get(5));
        }
        dbManager.close();
        return znamka;
    }

    @Override
    public List<Znamka> getObjects(int[] ids) {
        DBManager dbManager = new DBManager();
        List<Znamka> znamky = new ArrayList<>();
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
            Znamka znamka = new Znamka();
            znamka.setId((int)row.get(0));
            znamka.setDruhZnamky((int)row.get(1));
            znamka.setStudent((int) row.get(2));
            znamka.setDatum((String) row.get(3));
            znamka.setHodnota((int) row.get(4));
            znamka.setPopis((String) row.get(5));
            znamky.add(znamka);
        }
        return znamky;
    }

    @Override
    public List<Znamka> getAll() {
        DBManager dbManager = new DBManager();
        List<Znamka> znamky = new ArrayList<>();
        List<List<Object>> recordSet = dbManager.select(TABLE,ALL_COLUMNS,null,null);
        for (List<Object> row : recordSet){
            Znamka znamka = new Znamka();
            znamka.setId((int)row.get(0));
            znamka.setDruhZnamky((int)row.get(1));
            znamka.setStudent((int) row.get(2));
            znamka.setDatum((String) row.get(3));
            znamka.setHodnota((int) row.get(4));
            znamka.setPopis((String) row.get(5));
            znamky.add(znamka);
        }
        return znamky;
    }
}
