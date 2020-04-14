package cz.kodytek.shop.api.resources;

import cz.kodytek.shop.domain.api.interfaces.IPaymentMethodEJB;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("payments")
public class PaymentMethodResource {

    @EJB
    private IPaymentMethodEJB paymentMethodEJB;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response index() {
        return Response.ok(paymentMethodEJB.getAll()).build();
    }

}
