package com.herusantoso.filters;

import com.herusantoso.entities.User;
import com.herusantoso.services.FirebaseService;
import com.herusantoso.services.UserService;
import com.google.firebase.auth.UserRecord;
import io.quarkus.security.UnauthorizedException;
import org.jboss.resteasy.core.ResourceMethodInvoker;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class FirebaseAuthenticationFilter implements ContainerRequestFilter {

    @Inject
    UserService userService;

    @Inject
    FirebaseService firebaseService;

    public void filter(ContainerRequestContext context) {
        final ResourceMethodInvoker resourceMethodInvoker = (ResourceMethodInvoker) context.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        final Method method = resourceMethodInvoker.getMethod();

        UserRecord userRecord = null;
        User user = null;
        if (!method.isAnnotationPresent(PermitAll.class)) {
            if (method.isAnnotationPresent(DenyAll.class)) {
                throw new ForbiddenException(Response.Status.FORBIDDEN.toString());
            }

            String authorization = context.getHeaderString(HttpHeaders.AUTHORIZATION);
            if (Objects.isNull(authorization) || !authorization.startsWith("Bearer ")) {
                throw new UnauthorizedException(Response.Status.UNAUTHORIZED.toString());
            }

            String token = authorization.replace("Bearer", "").trim();
            if (token.isEmpty()) {
                throw new UnauthorizedException(Response.Status.UNAUTHORIZED.toString());
            }

            userRecord = firebaseService.getUserFromTokenId(token);
            user = userService.findByEmail(userRecord.getEmail());

            roleChecked(method, user);
        }

        if(Objects.nonNull(userRecord)) context.setSecurityContext(createSecurityContext(userRecord, user));
    }

    private void roleChecked(Method method, User user){
        Set<String> userRoles = getRoles(user);

        if (method.isAnnotationPresent(RolesAllowed.class)) {
            final RolesAllowed rolesAllowedAnnotation = method.getAnnotation(RolesAllowed.class);
            final String[] rolesAllowed = rolesAllowedAnnotation.value();

            boolean valid = false;
            for (String role : rolesAllowed) {
                if (userRoles.contains(role)) {
                    valid = true;
                    break;
                }
            }
            if(!valid) throw new UnauthorizedException(Response.Status.UNAUTHORIZED.toString());
        }
    }

    private SecurityContext createSecurityContext(UserRecord userRecord, User user){
        return new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return userRecord::getEmail;
            }

            @Override
            public boolean isUserInRole(String role) {
                return getRoles(user).contains(role);
            }

            @Override
            public boolean isSecure() {
                return true;
            }

            @Override
            public String getAuthenticationScheme() {
                return "FirebaseAuth";
            }
        };
    }

    private Set<String> getRoles(User user){
        return user.getRoles().stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toSet());
    }
}
