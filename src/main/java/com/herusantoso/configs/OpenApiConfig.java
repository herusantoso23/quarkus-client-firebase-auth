package com.herusantoso.configs;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title="Quarkus Client Firebase Auth API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Heru Santoso",
                        url = "https://github.com/herusantoso23",
                        email = "herusantoso008@gmail.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class OpenApiConfig extends Application {
}
