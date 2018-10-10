package model.entities;

import model.security.PasswordEncrypt;

/**
 * This class represents User entity.
 *
 * @author dyvakyurii@gmail.com
 */
public class User implements Identified {

    private int id;
    private String name;
    /**
     * Email field is used as login
     */
    private String email;
    private String passwordHash;
    boolean isAdmin;
    boolean isBlocked;

    public static class Builder {

        User instance=new User();

        public Builder setId(int id) {
            instance.id=id;
            return this;
        }

        public Builder setName(String name) {
            instance.name=name;
            return this;
        }

        public Builder setEmail(String email) {
            instance.email=email;
            return this;
        }

        public Builder setPasswordHash(String password) {
            instance.passwordHash=calcPasswordHash(password);
            return this;
        }

        public Builder setPasswordString(String password) {
            instance.passwordHash=password;
            return this;
        }

        public Builder setAdmin(boolean isAdmin) {
            instance.isAdmin=isAdmin;
            return this;
        }

        public Builder setBlocked(boolean isBlocked) {
            instance.isBlocked=isBlocked;
            return this;
        }

        public User build() {
            return instance;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash=passwordHash;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin=admin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked=blocked;
    }

    public static String calcPasswordHash(String password) {
        return PasswordEncrypt.encryptPassword(password);
    }

    public static Integer passwordEncryptor(String password) {
        Integer seed=131;
        Integer hash=12;
        hash=seed * hash + password.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user=(User) o;

        if (id != user.id) return false;
        if (isAdmin != user.isAdmin) return false;
        if (isBlocked != user.isBlocked) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return passwordHash != null ? passwordHash.equals(user.passwordHash) : user.passwordHash == null;
    }

    @Override
    public int hashCode() {
        int result=id;
        result=31 * result + (name != null ? name.hashCode() : 0);
        result=31 * result + (email != null ? email.hashCode() : 0);
        result=31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        result=31 * result + (isAdmin ? 1 : 0);
        result=31 * result + (isBlocked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", isAdmin=" + isAdmin +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
