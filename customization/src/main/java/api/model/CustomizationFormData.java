package api.model;

import java.io.File;

import javax.ws.rs.FormParam;

public class CustomizationFormData {

    @FormParam("file")
    public File file;

    @FormParam("modelType")
    public String modelType;
    
    @FormParam("bagColor")
    public String bagColor;
    
    @FormParam("pocketColor")
    public String pocketColor;
    
    @FormParam("email")
    public String email;
    
    @FormParam("quantity")
    public int quantity;

}
