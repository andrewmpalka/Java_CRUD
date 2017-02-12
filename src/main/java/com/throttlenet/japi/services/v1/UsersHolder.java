/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.throttlenet.japi.services.v1;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrewpalka
 */
@XmlRootElement(name = "users")
public class UsersHolder implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    @XmlElement(name = "id")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    
}
