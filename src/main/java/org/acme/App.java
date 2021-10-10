package org.acme;

import org.acme.domain.entity.User;
import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

/**
 * This class makes sure that the application is loaded by Quarkus.
 *
 */
@ApplicationPath("/hello")
@OpenAPIDefinition(
        info = @Info(version = "V0.1", title = "DevOps Engineer Test"),
        servers = {
                @Server(
                        description = "DevOps Engineer Test",
                        url = "/"
                )
        },
        components=@Components(
                securitySchemes = {
                        @SecurityScheme(description = "API key for authorization", apiKeyName = "accessToken", 
                                securitySchemeName = "ApiKeyAuth", type = SecuritySchemeType.APIKEY, 
                                in = SecuritySchemeIn.HEADER)
                },
                parameters= {
                        @Parameter(
                                name = "username",
                                in = ParameterIn.PATH,
                                required = true,
                                description = "The username",
                                example = "johndoe"
                        )
                },
                examples = {
                        @ExampleObject(
                                name = "userRequestBody",
                                summary = "User request body",
                                description = "User request body example",
                                value =     "{\n" 
                                        +   "    \"" + User.DATE_OF_BIRTH + "\": \"1978-09-02\"\n"
                                        +   "}"
                        ),
                        @ExampleObject(
                                name = "userResponseBody",
                                summary = "User response body",
                                description = "User response body example",
                                value =     "{\n" 
                                        +   "    \"" + User.DATE_OF_BIRTH + "\": \"1978-09-02\"\n"
                                        +   "}"
                        )
                },
                requestBodies = {
                        @RequestBody(
                                name = "userRequestBody",
                                required = true,
                                description = "User request body for create.",
                                content = @Content(
                                        mediaType = MediaType.APPLICATION_JSON,
                                        schema = @Schema(ref = "User"),
                                        examples = @ExampleObject(ref = "userRequestBody")
                                )
                        )
                },
                responses = {
                        @APIResponse(
                                name="userNotFound",
                                description = "Provided user does not exist."
                        ),
                        @APIResponse(
                                name="noContent",
                                description = "No Content."
                        ),
                        @APIResponse(
                                name="userResponse",
                                description = "User response body",
                                content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                        schema = @Schema(ref = "User"),
                                        examples = @ExampleObject(ref = "userResponseBody")
                                )
                        )
                }
        )
)
public class App extends Application {
    
}
