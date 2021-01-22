package com.herusantoso.utils;

import com.herusantoso.entities.User;
import com.herusantoso.services.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.SecurityContext;

@ApplicationScoped
public class CurrentUser {

    @Inject
    UserService userService;

    public User getUser(SecurityContext securityContext){
        String email = securityContext.getUserPrincipal().getName();
        return userService.findByEmail(email);
    }

}
