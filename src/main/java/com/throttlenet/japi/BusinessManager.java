/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.throttlenet.japi;

import com.throttlenet.japi.services.v1.User;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author andrewpalka
 */
public class BusinessManager {

    private static final Logger log = Logger.getLogger(BusinessManager.class.getName());
    private static BusinessManager INSTANCE = new BusinessManager();

    public static BusinessManager getInstance() {
        return INSTANCE;
    }

    private BusinessManager() {

    }

    public User findUser(String userId) throws Exception {

        User user = DataManager.getInstance().findUserById(userId);
        
        if (user == null) {
            throw new Exception("No such user found");
        }
        
        return user;
    }

    public List<User> findUsers() {
        

        return DataManager.getInstance().findAllUsers();
    }

    public User addUser(User user) {

        User newUser = DataManager.getInstance().insertUser(user);

        return newUser;
    }

    public User updateUserAttribute(String userId, String attribute, String value) {


        return DataManager.getInstance()
                .updateUserAttribute(userId, attribute, value);
    }

    public void deleteUser(String userId) {

        DataManager.getInstance().deleteUser(userId);

    }

}
