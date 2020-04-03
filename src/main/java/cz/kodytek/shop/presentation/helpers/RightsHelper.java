package cz.kodytek.shop.presentation.helpers;

import cz.kodytek.shop.data.entities.Right;
import cz.kodytek.shop.presentation.helpers.interfaces.IRightsHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class RightsHelper implements IRightsHelper {

    @Override
    public String parse(Right right) {
        if(right == Right.ADMIN) return "Admin";
        else if(right == Right.CLIENT) return "Client";

        return "None";
    }

}
