package api.model;

import java.awt.image.BufferedImage;
import javax.ws.rs.FormParam;

public class CustomizationFormData {

    @FormParam("FormLogo")
    public static BufferedImage newLogo;

    @FormParam("bagImage")
    public static BufferedImage bagImage;
}
