package cz.kodytek.shop.domain.models.goods;

public class ImageResource {

    private long id;
    private String originalPath;
    private String miniaturePath;
    private String hdPath;

    public ImageResource(long id, String path) {
        this.id = id;
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

    public long getId() {
        return id;
    }
}
