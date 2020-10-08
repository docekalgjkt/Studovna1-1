package cz.gjkt.model;

import java.util.List;

public interface IModelDao<T> {

    public int insert(T object);
    public int update(T object);
    public int delete(T object);

    public int[] insert(List<T> objects);
    public int update(List<T> objects);
    public int delete(List<T> objects);

    public T getObject(int id);
    public List<T> getObjects(int[] ids);
    public List<T> getAll();

}
