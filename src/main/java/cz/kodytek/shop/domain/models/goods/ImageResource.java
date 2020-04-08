package cz.kodytek.shop.domain.models.goods;

import cz.kodytek.shop.data.entities.Resource;

public class ImageResource {

    private String originalPath;
    private String miniaturePath;
    private String hdPath;

    public ImageResource(String path) {
        this.originalPath = path;
        this.miniaturePath = path.substring(0, path.lastIndexOf('.')) + "_miniature.jpg";
        this.hdPath = path.substring(0, path.lastIndexOf('.')) + "_hd.jpg";
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public String getMiniaturePath() {
        return miniaturePath;
    }

    public String getHdPath() {
        return hdPath;
    }
}
