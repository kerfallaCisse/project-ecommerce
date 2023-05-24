package api.model;

import java.io.InputStream;

public class CustomizationFormData {

    private InputStream bagImage;
    private InputStream newLogo;

    public InputStream getBagImage() {
        return bagImage;
    }

    public void setBagImage(InputStream bagImage) {
        this.bagImage = bagImage;
    }

    public InputStream getNewLogo() {
        return newLogo;
    }

    public void setNewLogo(InputStream newLogo) {
        this.newLogo = newLogo;
    }
}
