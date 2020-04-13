package cz.kodytek.shop.presentation.api.resources;

import cz.kodytek.shop.domain.services.interfaces.invoices.IDeliveryMethodService;
import cz.kodytek.shop.domain.services.interfaces.invoices.IPaymentMethodService;
import cz.kodytek.shop.presentation.api.models.Method;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("deliveries")
public class DeliveryMethodResource {

    @Inject
    private IDeliveryMethodService deliveryMethodService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response index() {
        return Response.ok(deliveryMethodService.getAll().stream().map(i -> new Method(i.getId(), i.getName(), i.getCost())).toArray(Method[]::new)).build();
    }

}
