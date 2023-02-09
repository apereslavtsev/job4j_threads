package ru.job4j.concurrent.cash;

import java.util.Objects;

public class Base {    
    private final int id;
    private final int version;
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    @Override
    public int hashCode() {
        return Objects.hash(id, name, version);
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
        Base other = (Base) obj;
        return id == other.id 
                && Objects.equals(name, other.name) 
                && version == other.version;
    }

    @Override
    protected Base clone() {
        Base rsl = new Base(id, version);
        rsl.setName(getName());
        return rsl;
    }
}
