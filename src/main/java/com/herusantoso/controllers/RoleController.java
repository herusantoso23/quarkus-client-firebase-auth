package com.herusantoso.controllers;

import com.herusantoso.enums.RoleEnum;
import com.herusantoso.services.RoleService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Tag(name = "role", description = "Role Management")
@Path("api/v1/role")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoleController {

    @Inject
    RoleService roleService;

    @POST
    @Path("/{name}")
    @PermitAll
    public Response createUser(@PathParam(value = "name") RoleEnum name){
        return Response.status(Response.Status.CREATED)
                .entity(roleService.createOrUpdate(name))
                .build();
    }

}
