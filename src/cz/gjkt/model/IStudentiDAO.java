package cz.gjkt.model;

import java.util.List;

public interface IStudentiDAO {

    public int insert(Student student);
    public int update(Student student);
    public int delete(Student student);

    public int update(List<Student> studenti);
    public int delete(List<Student> studenti);

    public Student getStudent(int id);
    public List<Student> getStudenti(int[] studenti);
    public List<Student> getAll();

}
