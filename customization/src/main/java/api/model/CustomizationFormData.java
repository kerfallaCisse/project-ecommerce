package api.model;

import java.io.File;

import javax.ws.rs.FormParam;

public class CustomizationFormData {

    @FormParam("file")
    public File file;

}
