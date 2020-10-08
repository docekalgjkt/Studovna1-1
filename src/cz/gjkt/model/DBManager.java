package cz.gjkt.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tato třída řídí připojení k databázi
 */
public class DBManager {

    private static final String DB_PATH = "res/";
    private static final String DB_NAME = "Studovna1";
    private Connection connection;

    public DBManager(String dbname){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+DB_PATH+dbname+".db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DBManager(){
        this(DB_NAME);
    }

    public Connection getConnection(){return connection;}

    public int insert(String table, String[] columns, String[] values){
        int vysledek = -1;
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO " + table + " ");
        if (columns != null) {
            sql.append("(");
            for (String column : columns) {
                sql.append(column+",");
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
            sql.append(") ");
        }

        if (values != null) {
            sql.append(" VALUES (");
            for (String value : values) {
                sql.append("?,");
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
            sql.append("); ");
        }

        try {
            PreparedStatement statement = connection.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            for (String value : values ) {
                statement.setObject(index++, value);
            }
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){vysledek = rs.getInt(1);}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vysledek;

    }

    public int insert(String table, List<String> columns, List<String> values){
        String[] c = columns.toArray(new String[0]);
        String[] v = values.toArray(new String[0]);
        return insert(table, c, v);
    }

    public List insert(String table, String[] columns, List<String[]> values){
        List vysledek = new ArrayList<Integer>();
        int index = 0;
        for (String[] value : values){
            vysledek.add(insert(table, columns, value));
        }
        return vysledek;
    }

    public int delete(String table, String[] ids){
        int vysledek = -1;
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM " + table + " ");
        sql.append("WHERE id IN (");
        for ( String id : ids ) {
            sql.append("?,");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(");");
        try {
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            int index = 1;
            for ( String id : ids ) {
                statement.setObject(index++,id);
            }
            vysledek = statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return vysledek;
    }

    public int update(String table, int id, String[] columns, String[] values){
        int vysledek = -1;
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + table + " SET ");
        for (String column : columns) {
            sql.append(column + "=?");
            sql.append(",");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        if (id > 0) sql.append("WHERE id = ?;");
        try {
            PreparedStatement statement = connection.prepareStatement(sql.toString());
            int index = 1;
            for (String value : values ) {
                statement.setObject(index++, value);
            }
            if (id > 0) statement.setObject(index,id);
            vysledek = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vysledek;
    }

    public int update(String table, int[] ids, String[] columns, List<String[]> values){
        int vysledek = -1;
        int index=0;
        for (int id:ids){
            vysledek = update(table, id, columns, values.get(index++));
        }
        return vysledek;
    }

    public  List<List<Object>> select(String table, String[] columns, String[] andConditions, String[] orConditions){
        StringBuilder query = new StringBuilder();
        List<List<Object>> recordSet = null;
        query.append("SELECT ");
        if (columns != null){
            for (String col : columns){
                query.append(col + ", ");
            }
            query.deleteCharAt(query.lastIndexOf(","));
        } else {
            query.append("* ");
        }
        query.append("FROM " + table + " ");
        if ((andConditions != null)||(orConditions != null)){
            query.append("WHERE ");
            if(andConditions!=null) {
                for (String cond : andConditions) {
                    query.append(cond);//query.append("?");
                    query.append(" AND ");
                }
                query.delete(query.lastIndexOf("AND"),query.lastIndexOf("AND")+4);
            }
            if(orConditions!=null){
                if (andConditions != null){query.append(" OR ");}
                for (String cond : orConditions) {
                    query.append(cond);//query.append("?");
                    query.append(" OR ");
                }
                query.delete(query.lastIndexOf("OR"),query.lastIndexOf("OR")+4);
            }
        }
        query.append(";");
        System.out.println(query.toString());
        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {

            ResultSet resultSet = statement.executeQuery();
            recordSet = new ArrayList<>();
            while (resultSet.next()){
                List<Object> row = new ArrayList<>();
                for(int i = 1; i<resultSet.getMetaData().getColumnCount()+1;i++){
                    row.add(resultSet.getObject(i));
                }
                recordSet.add(row);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordSet;
    }


    public void close(){
        try {
            if(connection!=null){connection.close();}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
