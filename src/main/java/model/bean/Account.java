package model.bean;

public class Account {
    private int Id;
    private String Username;
    private String Password;
    private boolean IsAdmin;

    public Account() {
    }

    public Account(int id, String username, String password, boolean isAdmin) {
        this.Id = id;
        this.Username = username;
        this.Password = password;
        this.IsAdmin = isAdmin;
    }

    public Account(String username, String password, boolean isAdmin) {
        this.Username = username;
        this.Password = password;
        this.IsAdmin = isAdmin;
    }

    public int getId() {
        return Id;
    }
    public void setId(int id) {
        this.Id = id;
    }
    public String getUsername() {
        return Username;
    }
    public void setUsername(String username) {
        this.Username = username;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        this.Password = password;
    }
    public boolean isAdmin() {
        return IsAdmin;
    }
    public void setIsAdmin(boolean isAdmin) {
        this.IsAdmin = isAdmin;
    }
}