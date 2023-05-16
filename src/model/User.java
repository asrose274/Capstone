package model;

/**
 * Methods for User Class
 */
public class User {

    /**
     * Variables for User
     */
    private int user_Id;
    private String user_Name;
    private String password;

    /**
     * Constructor for User
     * @param user_Id User ID
     * @param user_Name Username
     * @param password Password
     */
    public User(int user_Id, String user_Name, String password){
        this.user_Id = user_Id;
        this.user_Name = user_Name;
        this.password = password;
    }

    /**
     * getters and setters for User
     */
    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * override for combobox display
     * @return
     */
    @Override
    public String toString(){
        return (user_Name);
    }
}
