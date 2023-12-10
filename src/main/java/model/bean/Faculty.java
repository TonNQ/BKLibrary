package model.bean;

public class Faculty {
    private String Id;
    private String Name;
    private int TotalClass;

    public Faculty (String id, String name, int totalClass) {
        this.Id = id;
        this.Name = name;
        this.TotalClass = totalClass;
    }

    public Faculty (String id, String name) {
        this.Id = id;
        this.Name = name;
        this.TotalClass = 0;
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

    public int getTotalClass() {return this.TotalClass;}
    public void setTotalClass(int totalClass) {this.TotalClass = totalClass;}
}