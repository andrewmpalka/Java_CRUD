/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.throttlenet.japi;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.throttlenet.japi.services.v1.User;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

/**
 *
 * @author andrewpalka
 */
public class DataManager {

    private static final Logger log = Logger.getLogger(DataManager.class
            .getName());

    private static DB japiDB;
    private static DBCollection userCollection;

    private static DataManager INSTANCE;

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }

        return INSTANCE;
    }

    private DataManager() {
        try {

            MongoClient mongoClient = new MongoClient(new ServerAddress(
                    "localhost", 27017));

            japiDB = mongoClient.getDB("japi");

            userCollection = japiDB.getCollection("users");

        } catch (Exception e) {
            
            log.error("db connection error: ", e);
        }

    }
    
    public User insertUser(User user) {
        
        BasicDBObject doc = new BasicDBObject();
        
        doc.put("name", user.getName());
        
        userCollection.insert(doc);
        
        // Put new id into user object
        user.setId(doc.get("_id").toString());
        
        return user;
    }
    
    public User findUserById(String userId) {
        
        if (userId == null) {
            return null;
        }
        
        try {
            
         DBObject searchById = new BasicDBObject("_id", new ObjectId(userId));
         
         DBObject userObject = userCollection.findOne(searchById);
         
         if (userObject != null) {
             return mapUserFromDBObject(userObject);
         } else {
             return null;
         }
         
        } catch (Exception e) {
            return null;
        }
        
        
    }

    private User mapUserFromDBObject(DBObject dbObject) {
        User user = new User();
        
        user.setId(dbObject.get("_id").toString());
        
        user.setName(dbObject.get("name").toString());
        
        
        return user;
    }

    List<User> findAllUsers() {
        
        List<User> users = new ArrayList<User>();
        
        try {
            
            DBCursor cursor = userCollection.find();
            
            if (cursor != null) {
                
                while (cursor.hasNext()) {
                    
                    BasicDBObject doc = (BasicDBObject) cursor.next();
                    
                    User item = mapUserFromDBObject(doc);
                    
                    users.add(item);
                }
                
                return users;
            }
            
            return null;
            
        } catch (Exception e) {
            
            return null;
            
        }
    }

    User updateUserAttribute(String userId, String attribute, String value) {
        
        BasicDBObject doc = new BasicDBObject();
        
        doc.append("$set", new BasicDBObject().append(attribute, value));
        
        DBObject searchById = new BasicDBObject("_id", new ObjectId(userId));
        
        userCollection.update(searchById, doc);
        
        return findUserById(userId);
    }

    void deleteUser(String userId) {
        
        
        try {
            
         DBObject searchById = new BasicDBObject("_id", new ObjectId(userId));
         
         userCollection.remove(searchById);
         
        } catch (Exception e) {
        
    }
        
    }

}
