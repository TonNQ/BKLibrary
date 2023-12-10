package model.bean;

public class ClassEntity {
    private int Id;
    private String Name;
    private String FacultyId = "";
    private String FacultyName = "";
    private int TotalStudent = 0;

    public ClassEntity(int id, String name, String facultyName, int totalStudent) {
        this.Id = id;
        this.Name = name;
        this.FacultyName = facultyName;
        this.TotalStudent = totalStudent;
    }

    public ClassEntity(int id, String name, String facultyId) {
        this.Id = id;
        this.Name = name;
        this.FacultyId = facultyId;
    }

    public ClassEntity(int id, String name) {
        this.Id = id;
        this.Name = name;
    }

    public ClassEntity(String name, String facultyId) {
        this.Name = name;
        this.FacultyId = facultyId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getFacultyName() {
        return FacultyName;
    }

    public String getFacultyId() {
        return FacultyId;
    }

    public void setFacultyId(String facultyId) {
        this.FacultyId = facultyId;
    }

    public void setFacultyName(String FacultyName) {
        this.FacultyName = FacultyName;
    }

    public int getTotalStudent() {
        return TotalStudent;
    }

    public void setTotalStudent(int TotalStudent) {
        this.TotalStudent = TotalStudent;
    }
}