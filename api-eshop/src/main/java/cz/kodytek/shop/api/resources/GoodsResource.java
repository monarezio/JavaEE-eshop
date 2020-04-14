package cz.kodytek.shop.api.resources;

import cz.kodytek.shop.domain.api.interfaces.IGoodsEJB;
import cz.kodytek.shop.domain.api.models.Good;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("goods")
public class GoodsResource {

    @EJB
    private IGoodsEJB goodsEJB;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response index(@QueryParam("page") int page, @QueryParam("search") String search) {
        return Response.ok(goodsEJB.get(search == null ? "" : search, page)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response detail(@PathParam("id") Long id) {
        Good i = goodsEJB.get(id);
        if (i == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(i).build();
    }

}
