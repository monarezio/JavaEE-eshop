package cz.kodytek.shop.api.resources;

import cz.kodytek.shop.domain.api.interfaces.IInvoiceEJB;
import cz.kodytek.shop.domain.api.models.CreatedInvoice;
import cz.kodytek.shop.domain.api.models.EditedInvoice;
import cz.kodytek.shop.domain.api.models.Invoice;
import cz.kodytek.shop.domain.api.models.ResultDTO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("invoices")
public class InvoicesResource {

    @EJB
    private IInvoiceEJB invoiceEJB;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response index(@QueryParam("page") int page, @QueryParam("search") String search) {
       return Response.ok(invoiceEJB.get(search == null ? "" : search, page)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response detail(@PathParam("id") Long id) {
        CreatedInvoice i = invoiceEJB.get(id);
        if (i == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(i).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Invoice invoice) {
        ResultDTO<Invoice> resultDTO = invoiceEJB.create(invoice);

        if(resultDTO.getData() == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(resultDTO.getErrors()).build();
        return Response.ok(resultDTO.getData()).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response destroy(@PathParam("id") long id) {
        invoiceEJB.destroy(id);
        return Response.ok().build();
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response edit(@PathParam("id") long id, EditedInvoice invoice) {
        ResultDTO<Boolean> resultDTO = invoiceEJB.edit(id, invoice);
        if(!resultDTO.getData())
            return Response.status(Response.Status.BAD_REQUEST).entity(resultDTO.getErrors()).build();
        return Response.ok().build();
    }


}
