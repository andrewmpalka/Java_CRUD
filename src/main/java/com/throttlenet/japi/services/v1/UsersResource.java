/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.throttlenet.japi.services.v1;

import com.throttlenet.japi.BusinessManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author andrewpalka
 */

@Path("/v1/users")
@Api(value = "/users", description = "Manage Users")


public class UsersResource {
    
    private static final Logger log = Logger.getLogger(UsersResource.class
            .getName());

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Find User", notes = 
            "This API retrieves the public information for the user (Private"
                    + " info of this is the auth user)" +
    "<p><u>Input Parameters</u><ul><li><b>userId</b> is required </li></ul>")
    
    @ApiResponses(value = {
        @ApiResponse(code = 200, message =
                "Success: { user profile }"),
        @ApiResponse(code = 400, message =
                "Failed: {\"error\":\"error description\", \"status\":\"FAIL\"}")
    })
    public Response getUserById(@ApiParam(value = "userId", required = true,
            defaultValue = "23456", allowableValues = "", allowMultiple = false)
    @PathParam("userId") String userId
    ) {
        
    log.info("UsersRecouce :: getUserById :: started");
        
        if (userId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Empty userId\"}")
                    .build();
        } 
        
        
        try {
            
        User user = BusinessManager.getInstance().findUser(userId);
        
        return Response.status(Response.Status.OK).entity(user).build();
        
        } catch (Exception e) { 
            
        }
        
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Could not find user\","
                        + " \"status\":\"FAIL\"}")
                .build();
        
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Find All Users", notes = "This API retrieves all users")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success: { users: [] }"),
        @ApiResponse(code = 400, 
                message = "Failed: {\"error\":\"error description\","
                        + " \"status\":\"FAIL\"}")
    })
    public Response getUsers() {
        
        try {
            List<User> users = BusinessManager.getInstance().findUsers();
            UsersHolder userHolder = new UsersHolder();
        
            userHolder.setUsers(users);
            
            return Response.status(Response.Status.OK).entity(userHolder).build();
        
        } catch (Exception e) {
            
        }
        
              return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Could not find user\","
                        + " \"status\":\"FAIL\"}")
                .build();
    }
    
    @POST
    @Path("/")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Create User", notes = "This API creates a new user")
    @ApiResponses(value = {@ApiResponse(code = 201,
            message = "Success: { user profile }"),
        @ApiResponse(code = 400, message =
                "Failed: {\"error\":\"error description\", \"status\":\"FAIL\"}")})
    public Response createUser(
            @ApiParam(value = "New User",
                    required = true, defaultValue = "",
                    allowableValues = "", allowMultiple = false) User user) {
        
        try {
            User newUser = BusinessManager.getInstance().addUser(user);
            
            return Response.status(Response.Status.CREATED).entity(newUser).build();
            
        } catch (Exception e) {
            
        }
        
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Could not create user\","
                        + " \"status\":\"FAIL\"}")
                .build();
    }
    @PUT
    @Path("/{userId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Update User", notes = "This API updates the user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success: { user profile }")
            , @ApiResponse(code = 400, message =
                "Failed: {\"error\":\"error description\", \"status\":\"FAIL\"}")})
    public Response upateUser(@PathParam("userId") String userId, String jsonString ) {
        
        String name;
        
        try {
            
            Object obj = JSONValue.parse(jsonString);
            
            JSONObject jsonObject = (JSONObject) obj;
            
            name = (String) jsonObject.get("name");
            
        } catch (Exception e) {
            
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Could not update user\","
                        + " \"status\":\"FAIL\"}")
                .build();
        }
        
        try {
            
            User updatedUser = BusinessManager.getInstance()
                    .updateUserAttribute(userId, "name", name);
            
            return Response.status(Response.Status.OK)
                    .entity(updatedUser).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Could not update user\","
                        + " \"status\":\"FAIL\"}")
                .build();
        }
        
    }
    
    @DELETE
    @Path("/{userId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiOperation(value = "Delete User", notes = "This API deletes an existing user")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success: { }")
            , @ApiResponse(code = 400, message =
                "Failed: {\"error\":\"error description\", \"status\":\"FAIL\"}")})
    public Response DeleteUser(@PathParam("userId") String userId) {
        
        
        try {
            
            BusinessManager.getInstance().deleteUser(userId);
            
            return Response.status(Response.Status.OK).entity("{ }").build();
            
        } catch (Exception e) {
            
        }
        
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Could not delete user\","
                        + " \"status\":\"FAIL\"}")
                .build();
    }
    
}

