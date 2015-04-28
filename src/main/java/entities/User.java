package entities;

public class User {
    private String login;
    private String password;
    private String role;
    private String fullName;
    private String email;
    private String mobilePhone;

    public User(String login,
                String password,
                String role,
                String fullName,
                String email,
                String mobilePhone) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.email = email;
        this.mobilePhone = mobilePhone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
