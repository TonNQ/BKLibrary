package model.bean;

public class Student {
    private String Id;
    private String Name;
    private String ClassName;
    private int ClassId;

    public Student(String id, String name, String className, int classId) {
        this.Id = id;
        this.Name = name;
        this.ClassName = className;
        this.ClassId = classId;
    }

    public Student(String id, String name, int classId) {
        this.Id = id;
        this.Name = name;
        this.ClassId = classId;
    }

    public Student(String id, String name, String className) {
        this.Id = id;
        this.Name = name;
        this.ClassName = className;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getClassId() {
        return ClassId;
    }

    public void setClassId(int classId) {
        this.ClassId = classId;
    }

    public String getClassName() {
        return ClassName;
    }
    public void setClassName(String className) {
        this.ClassName = className;
    }
}
