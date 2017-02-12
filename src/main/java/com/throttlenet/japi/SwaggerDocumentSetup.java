/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.throttlenet.japi;

import io.swagger.jaxrs.config.BeanConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author andrewpalka
 */
public class SwaggerDocumentSetup extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    super.init(config);
    
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setVersion("1.0.0");
    beanConfig.setTitle("Throttlenet, General API's");
    beanConfig.setDescription("Our core API's that can be used for all sorts of stuff. Enjoy! :-)");
    
    beanConfig.setSchemes(new String[] {"http"});
    beanConfig.setHost("localhost:8080");
    beanConfig.setBasePath("/japi/services");
    
    beanConfig.setResourcePackage("com.throttlenet.japi.services");
    
    beanConfig.setScan(true);
}
    
}
