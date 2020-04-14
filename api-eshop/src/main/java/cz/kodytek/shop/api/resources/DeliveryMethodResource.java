package cz.kodytek.shop.api.resources;

import cz.kodytek.shop.domain.api.interfaces.IDeliveryMethodEJB;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("deliveries")
public class DeliveryMethodResource {

    @EJB
    private IDeliveryMethodEJB deliveryMethodEJB;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response index() {
        return Response.ok(deliveryMethodEJB.getAll()).build();
    }

}
