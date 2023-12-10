package model.bean;

public class Book {
    private String Id;
    private String Name;
    private String Author;
    private int Quantity;
    private int CategoryId;
    private String CategoryName;

    public Book() { }
    public Book(String id, String name, String author, int quantity, int categoryId) {
        this.Id = id;
        this.Name = name;
        this.Author = author;
        this.Quantity = quantity;
        this.CategoryId = categoryId;
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
    public String getAuthor() {
        return Author;
    }
    public void setAuthor(String author) {
        this.Author = author;
    }
    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }
    public int getCategoryId() {
        return CategoryId;
    }
    public void setCategoryId(int categoryId) {
        this.CategoryId = categoryId;
    }
    public String getCategoryName() {
        return CategoryName;
    }
    public void setCategoryName(String categoryName) {
        this.CategoryName = categoryName;
    }
}
