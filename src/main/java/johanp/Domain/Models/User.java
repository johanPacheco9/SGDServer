/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package johanp.Domain.Models;

import java.io.Serializable;

/**
 *
 * @author johan
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String password;
    private int role;

    // Constructor
    public User( int id,String name, int role) {
        this.name = name;
        this.id = id ;
        this.role = role;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getRole()
    {
        return role;
    }

    public void setRole(int role)
    {
        this.role = role;
    }
    

    public long getId() {
        return id;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "File{"
                + "id =" + id
                + "name='" + name + '\''
                + "role" + role
                + '}';
    }
}
