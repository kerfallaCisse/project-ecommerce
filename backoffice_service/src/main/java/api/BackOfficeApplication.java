package api;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@OpenAPIDefinition(
    info = @Info(
        title = "Back office api",
        description = "This API allows administrators to access statistics such as the number of users who have created an account, the number of users who have placed an order, etc.",
        version = "1.0.0",
        license = @License(
            name = "MIT",
            url = "${HOST_API_URL}"
        )
    ),
    tags = {
        @Tag(name = "backoffice", description = "statistics")
    }
)
public class BackOfficeApplication extends Application{}
