package cz.kodytek.shop.presentation.api.resources;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;
import cz.kodytek.shop.presentation.api.models.Good;
import cz.kodytek.shop.presentation.api.models.GoodsPage;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("goods")
public class GoodsResource {

    private static int PER_PAGE = 20;

    @Inject
    private IGoodsService goodsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response index(@QueryParam("page") int page, @QueryParam("search") String search) {

        System.out.println("Search: " + search);

        IEntityPage<? extends IGood> gp = goodsService.getGoods(search == null ? "" : search, PER_PAGE, page);
        return Response.ok(new GoodsPage(page, gp.getCurrentPage(), gp.getAll().stream().map(i -> new Good(i.getId(), i.getTitle(), i.getDescription(), i.getCost(), i.getAmount())).collect(Collectors.toList()))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response detail(@PathParam("id") Long id) {
        IGood i = goodsService.get(id);
        if (i == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(new Good(i.getId(), i.getTitle(), i.getDescription(), i.getCost(), i.getAmount())).build();
    }

}
