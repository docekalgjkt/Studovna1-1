package cz.gjkt.model;

import java.util.ArrayList;
import java.util.List;

public class DruhyZnamekDAOJDBC implements IModelDao<DruhZnamky> {

    private static final String TABLE = "DruhZnamky";
    private static final String[] ALL_COLUMNS = {"id","nazev","datum","popis","typZnamky","kurz"};
    private static final String[] UPDATE_COMUNS = {"nazev","datum","popis","typZnamky","kurz"};


    @Override
    public int insert(DruhZnamky object) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{object.getNazev(),object.getDatum(),object.getPopis(),String.valueOf(object.getTypZnamky()), String.valueOf(object.getKurz())};
        int id = dbManager.insert(TABLE,UPDATE_COMUNS,values);
        object.setId(id);
        dbManager.close();
        return id;
    }

    @Override
    public int update(DruhZnamky object) {
        DBManager dbManager = new DBManager();
        String[] values = new String[]{object.getNazev(),object.getDatum(),object.getPopis(),String.valueOf(object.getTypZnamky()), String.valueOf(object.getKurz())};
        int vysledek = dbManager.update(TABLE, object.getId(),UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(DruhZnamky object) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{String.valueOf(object.getId())};
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int[] insert(List<DruhZnamky> objects) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        for (DruhZnamky object : objects){
            String[] value = new String[]{object.getNazev(),object.getDatum(),object.getPopis(),String.valueOf(object.getTypZnamky()), String.valueOf(object.getKurz())};
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
    public int update(List<DruhZnamky> objects) {
        DBManager dbManager = new DBManager();
        List<String[]> values = new ArrayList<>();
        int[] ids = null;
        int i = 0;
        for(DruhZnamky druhZnamky : objects){
            values.add( new String[]{druhZnamky.getNazev(),druhZnamky.getDatum(),druhZnamky.getPopis(),String.valueOf(druhZnamky.getTypZnamky()), String.valueOf(druhZnamky.getKurz())});
            ids[i++] = druhZnamky.getId();
        }
        int vysledek = dbManager.update(TABLE,ids,UPDATE_COMUNS,values);
        dbManager.close();
        return vysledek;
    }

    @Override
    public int delete(List<DruhZnamky> objects) {
        DBManager dbManager = new DBManager();
        String[] ids = new String[]{};
        int i = 0;
        for(DruhZnamky druhZnamky : objects){
            ids[i++] = String.valueOf(druhZnamky.getId());
        }
        int vysledek = dbManager.delete(TABLE,ids);
        dbManager.close();
        return vysledek;
    }

    @Override
    public DruhZnamky getObject(int id) {
        DBManager dbManager = new DBManager();
        DruhZnamky druhZnamky = new DruhZnamky();
        String[] ids = new String[]{"id="+id};
        List<List<Object>> rs = dbManager.select(TABLE,ALL_COLUMNS,ids,null);
        for(List<Object> row : rs ) {
            druhZnamky.setId((int)row.get(0));
            druhZnamky.setNazev((String)row.get(1));
            druhZnamky.setDatum((String) row.get(2));
            druhZnamky.setPopis((String) row.get(3));
            druhZnamky.setTypZnamky((int) row.get(4));
            druhZnamky.setKurz((int) row.get(5));
        }
        dbManager.close();
        return druhZnamky;
    }

    @Override
    public List<DruhZnamky> getObjects(int[] ids) {
        DBManager dbManager = new DBManager();
        List<DruhZnamky> druhyZnamek = new ArrayList<>();
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
            DruhZnamky druhZnamky = new DruhZnamky();
            druhZnamky.setId((int)row.get(0));
            druhZnamky.setNazev((String)row.get(1));
            druhZnamky.setDatum((String) row.get(2));
            druhZnamky.setPopis((String) row.get(3));
            druhZnamky.setTypZnamky((int) row.get(4));
            druhZnamky.setKurz((int) row.get(5));
            druhyZnamek.add(druhZnamky);
        }
        return druhyZnamek;
    }

    @Override
    public List<DruhZnamky> getAll() {
        DBManager dbManager = new DBManager();
        List<DruhZnamky> druhyZnamek = new ArrayList<>();
        List<List<Object>> recordSet = dbManager.select(TABLE,ALL_COLUMNS,null,null);
        for (List<Object> row : recordSet){
            DruhZnamky druhZnamky = new DruhZnamky();
            druhZnamky.setId((int)row.get(0));
            druhZnamky.setNazev((String)row.get(1));
            druhZnamky.setDatum((String) row.get(2));
            druhZnamky.setPopis((String) row.get(3));
            druhZnamky.setTypZnamky((int) row.get(4));
            druhZnamky.setKurz((int) row.get(5));
            druhyZnamek.add(druhZnamky);
        }
        return druhyZnamek;
    }
}
