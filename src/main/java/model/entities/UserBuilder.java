package model.entities;

public class UserBuilder {

    private int id;
    private String name;
    private String email;
    private String password;
    private boolean isAdmin;
    private boolean isBlocked;

    public UserBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    public UserBuilder setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
        return this;
    }

    public User build() {
        return new User(id, name, email, password, isAdmin, isBlocked);
    }
}

