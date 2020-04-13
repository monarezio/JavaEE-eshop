package cz.kodytek.shop.presentation.api.resources;

import cz.kodytek.shop.data.entities.interfaces.IEntityId;
import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.domain.models.address.Address;
import cz.kodytek.shop.domain.models.company.Company;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.models.invoices.NewInvoice;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;
import cz.kodytek.shop.domain.services.interfaces.invoices.IInvoicesService;
import cz.kodytek.shop.presentation.api.models.*;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("invoices")
public class InvoicesResource {

    private static int PER_PAGE = 20;

    @Inject
    private IInvoicesService invoicesService;

    @Inject
    private IGoodsService goodsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response index(@QueryParam("page") int page, @QueryParam("search") String search) {
        IEntityPage<? extends IInvoice> ip = invoicesService.getInvoices(search == null ? "" : search, page, PER_PAGE);

        return Response.ok(
                new InvoicesPage(page, ip.getPagesCount(), ip.getAll().stream().map(i -> new CreatedInvoice((cz.kodytek.shop.data.entities.invoice.Invoice) i)).collect(Collectors.toList()))
        ).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response detail(@PathParam("id") Long id) {
        IInvoice i = invoicesService.get(id);
        if (i == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(new CreatedInvoice((cz.kodytek.shop.data.entities.invoice.Invoice) i)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Invoice invoice) {
        if (invoice == null)
            Response.status(Response.Status.BAD_REQUEST);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Map<String, List<String>> errorMessages = new HashMap<>();

        if (invoice.getCompany() != null) {
            errorMessages.put("company", validator.validate(invoice.getCompany()).stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
            if (invoice.getCompany().getAddress() == null)
                errorMessages.put("company_address", Collections.singletonList("Company address cannot be null."));
            else
                errorMessages.put("company_address", validator.validate(invoice.getCompany().getAddress()).stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
        }
        if (invoice.getDeliveryAddress() == null)
            errorMessages.put("address", Collections.singletonList("Delivery address cannot be null."));
        else
            errorMessages.put("address", validator.validate(invoice.getDeliveryAddress()).stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));

        if (invoice.getCart() == null)
            errorMessages.put("cart", Collections.singletonList("Cart cannot be null."));
        else {
            errorMessages.put("cart", invoice.getCart().stream().map(i -> validator.validate(i).stream().map(ConstraintViolation::getMessage)).flatMap(j -> j).collect(Collectors.toList()));
        }

        errorMessages.put("contact", validator.validate(invoice).stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));

        if (errorMessages.keySet().stream().anyMatch(i -> !errorMessages.get(i).isEmpty())) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        List<IGood> goods = goodsService.getGoodsForIds(invoice.getCart().stream().map(Product::getId).collect(Collectors.toSet()));

        Set<Long> cartIds = invoice.getCart().stream().map(Product::getId).collect(Collectors.toSet());
        Stream<Long> goodIds = goods.stream().map(IEntityId::getId);

        if (!cartIds.stream().allMatch(i -> goodIds.anyMatch(j -> j.equals(i)))) {
            errorMessages.put("cart", Collections.singletonList("One of the products in the cart has an invalid ID."));
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        for (IGood g : goods) {
            if (g.getAmount() < invoice.getCart().stream().filter(i -> i.getId() == g.getId()).findFirst().get().getUnitCount()) {
                errorMessages.put("cart", Collections.singletonList("Product with ID " + g.getId() + " has too many units."));
                return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
            }
        }

        NewInvoice i = new NewInvoice();
        i.setFullName(invoice.getFullName());
        i.setPhone(invoice.getPhone());
        i.setEmail(invoice.getEmail());
        i.setDeliveryMethodId(invoice.getDeliveryMethodId());
        i.setPaymentMethodId(invoice.getPaymentMethodId());

        Address a = new Address();
        a.setCity(invoice.getDeliveryAddress().getCity());
        a.setStreet(invoice.getDeliveryAddress().getStreet());
        a.setPostCode(invoice.getDeliveryAddress().getPostCode());
        i.setAddress(a);

        if (invoice.getCompany() != null) {
            Company c = new Company();
            c.setName(invoice.getCompany().getName());
            c.setIdentificationNumber(invoice.getCompany().getIdentificationNumber());
            c.setTaxIdentificationNumber(invoice.getCompany().getTaxIdentificationNumber());

            cz.kodytek.shop.data.entities.Address ca = new cz.kodytek.shop.data.entities.Address();
            ca.setCity(invoice.getCompany().getAddress().getCity());
            ca.setPostCode(invoice.getCompany().getAddress().getPostCode());
            ca.setStreet(invoice.getCompany().getAddress().getStreet());
            c.setAddress(ca);
            i.setCompany(c);
        }

        HashMap<Long, Integer> cart = new HashMap<>();
        invoice.getCart().forEach(j -> cart.put(j.getId(), j.getUnitCount()));
        i.setCart(cart);

        IInvoice result = invoicesService.addInvoice(i);

        if (result == null) {
            errorMessages.put("methods", Collections.singletonList("Payment or delivery ids are invalid."));
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
        }

        return Response.ok(invoice).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response destroy(@PathParam("id") long id) {
        invoicesService.delete(id);
        return Response.ok().build();
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response edit(@PathParam("id") long id, EditedInvoice invoice) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Map<String, List<String>> errorMessages = new HashMap<>();

        errorMessages.put("invoice", validator.validate(invoice).stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
        if(errorMessages.get("invoice").size() > 0)
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();


        if(invoicesService.edit(id, invoice.getFullName(), invoice.getEmail(), invoice.getPhone(), invoice.isPaid()))
            return Response.ok().build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }


}
