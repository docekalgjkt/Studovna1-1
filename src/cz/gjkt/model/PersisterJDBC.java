package cz.gjkt.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PersisterJDBC<T> {

    public void serialize(T object){

        String table = object.getClass().getSimpleName();
        List<String> columns = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field: fields){
            if (field.getName() != "id") {
                columns.add(field.getName());
                try {
                    Method m = object.getClass().getDeclaredMethod(getMethodName(field.getName(), "get"));
                    values.add("" + m.invoke(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        DBManager dbManager = new DBManager();
        dbManager.insert(table,columns,values);
        dbManager.close();
    }

    private String getMethodName(String fieldname, String type){
        String prvni = fieldname.substring(0,1);
        String zbytek = fieldname.substring(1);
        String novy = type + prvni.toUpperCase() + zbytek;
        return novy;
    }

}
