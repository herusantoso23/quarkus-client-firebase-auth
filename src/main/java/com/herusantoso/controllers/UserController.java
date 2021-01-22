package com.herusantoso.controllers;

import com.herusantoso.dtos.FirebaseTokenDTO;
import com.herusantoso.dtos.UserCreateDTO;
import com.herusantoso.services.UserService;
import com.herusantoso.utils.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@ApplicationScoped
@Tag(name = "user", description = "User Management")
@Path("api/v1/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class UserController {

    @Inject
    UserService userService;

    @Inject
    CurrentUser currentUser;

    @POST
    @Path("/admin")
    @PermitAll
    public Response createUser(@Valid FirebaseTokenDTO request){
        return Response.status(Response.Status.CREATED)
                .entity(userService.createAdmin(request))
                .build();
    }

    @POST
    @Path("/cashier")
    @RolesAllowed({"ADMIN"})
    public Response createUser(@Valid UserCreateDTO request){
        return Response.status(Response.Status.CREATED)
                .entity(userService.createCashier(request))
                .build();
    }

    @GET
    @RolesAllowed({"USER","ADMIN"})
    public Response getUser(@Context SecurityContext ctx){
        return Response.status(Response.Status.OK)
                .entity(currentUser.getUser(ctx))
                .build();
    }

}
