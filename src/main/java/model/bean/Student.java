package model.bean;

public class Student {
    private String Id;
    private String Name;
    private int ClassId;
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
}
