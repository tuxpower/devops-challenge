package org.acme.api;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.domain.entity.User;
import org.acme.domain.service.UserCrud;
import org.acme.domain.validator.DateOfBirth;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@ApplicationScoped
@Path("hello")
public class UserApi {
    
    @Inject
    UserCrud userCrud;
    
    /**
     * Create user related information.
     * 
     * @param username          The username of the user
     * @param user              The user birthday
     * 
     * @return {@link Response}
     */
    @PUT
    @Path("{username}")
    @Operation(summary = "Saves/updates the given user’s name and date of birth in the database")
    @APIResponse(responseCode = "204", ref = "noContent")
    public Response putUser(
        @NotNull
        @Parameter(ref="username")
        @PathParam("username")
        @Pattern(regexp = "^[A-Za-z]*$", message="Username must contain only letters")
        final String username,
    
        @NotNull
        @DateOfBirth
        @RequestBody(ref="userRequestBody")
        final User user) {
        
        userCrud.persist(username, user);
    
    return Response.noContent().build();
    }

    /**
     * Get user related information.
     * 
     * @param username          The username of the user
     * 
     * @return {@link Response}
     */
    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Returns hello birthday message for the given user")
    @APIResponse(responseCode = "200", ref = "userResponse")
    @APIResponse(responseCode = "404", ref = "userNotFound")
    public Response getUser(
        @NotNull
        @Parameter(ref="username")
        @PathParam("username")
        @Pattern(regexp = "^[A-Za-z]*$", message="Username must contain only letters")
        final String username) {
    
    return Response.ok(userCrud.read(username)).build();
    }
}
