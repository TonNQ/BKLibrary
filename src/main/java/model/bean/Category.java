package model.bean;

public class Category {
    private int Id;
    private String Name;
    private int TotalBook;
    public Category() {
    }
    public Category(int id, String name) {
        this.Id = id;
        this.Name = name;
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
    public int getTotalBook() {
        return TotalBook;
    }
    public void setTotalBook(int totalBook) {
        this.TotalBook = totalBook;
    }
}
