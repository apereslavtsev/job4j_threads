package ru.job4j.concurrent.threadPool;

import java.util.Objects;

public class User {
    
    private String username;
    
    private String email;
    
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
            
        if (obj == null) {
            return false;
        }
            
        if (getClass() != obj.getClass()) {
            return false;
        }
            
        User other = (User) obj;
        return Objects.equals(email, other.email) 
                && Objects.equals(username, other.username);
    }
    
    
    
}
