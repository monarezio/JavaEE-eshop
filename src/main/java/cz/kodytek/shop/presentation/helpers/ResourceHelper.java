package cz.kodytek.shop.presentation.helpers;

import cz.kodytek.shop.data.entities.interfaces.reousrce.IResource;
import cz.kodytek.shop.presentation.helpers.interfaces.IResourceHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ResourceHelper implements IResourceHelper {
    @Override
    public String getMiniature(IResource resource) {
        return resource.getPath().replaceFirst("[.][^.]+$", "") + "_miniature.jpg";
    }

    @Override
    public String getHd(IResource resource) {
        return resource.getPath().replaceFirst("[.][^.]+$", "") + "_hd.jpg";
    }
}
