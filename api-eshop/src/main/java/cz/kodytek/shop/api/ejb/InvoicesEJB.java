package cz.kodytek.shop.api.ejb;

import cz.kodytek.shop.data.entities.interfaces.IEntityId;
import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.domain.api.interfaces.IInvoiceEJB;
import cz.kodytek.shop.domain.api.models.*;
import cz.kodytek.shop.domain.models.address.Address;
import cz.kodytek.shop.domain.models.company.Company;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.models.invoices.NewInvoice;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;
import cz.kodytek.shop.domain.services.interfaces.invoices.IInvoicesService;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class InvoicesEJB implements IInvoiceEJB, Serializable {

    private static int PER_PAGE = 20;

    @Inject
    private IInvoicesService invoicesService;

    @Inject
    private IGoodsService goodsService;

    @Override
    public InvoicesPage get(String search, int page) {
        IEntityPage<? extends IInvoice> ip = invoicesService.getInvoices(search == null ? "" : search, page, PER_PAGE);
        return new InvoicesPage(page, ip.getPagesCount(), ip.getAll().stream().map(i -> new CreatedInvoice((cz.kodytek.shop.data.entities.invoice.Invoice) i)).collect(Collectors.toList()));
    }

    @Override
    public CreatedInvoice get(long id) {
        IInvoice i = invoicesService.get(id);
        if (i == null)
            return null;
        return new CreatedInvoice((cz.kodytek.shop.data.entities.invoice.Invoice) i);
    }

    @Override
    public void destroy(long id) {
        invoicesService.delete(id);
    }

    @Override
    public ResultDTO<Boolean> edit(long id, EditedInvoice invoice) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Map<String, List<String>> errorMessages = new HashMap<>();

        errorMessages.put("invoice", validator.validate(invoice).stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));
        if (errorMessages.get("invoice").size() > 0)
            return new ResultDTO<Boolean>(errorMessages, false);

        if (invoicesService.edit(id, invoice.getFullName(), invoice.getEmail(), invoice.getPhone(), invoice.isPaid()))
            return new ResultDTO<>(new HashMap<>(), true);

        //Most probably not found
        return new ResultDTO<>(new HashMap<>(), false);
    }

    @Override
    public ResultDTO<Invoice> create(Invoice invoice) {
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
            return new ResultDTO<>(errorMessages, null);
        }

        List<IGood> goods = goodsService.getGoodsForIds(invoice.getCart().stream().map(Product::getId).collect(Collectors.toSet()));

        Set<Long> cartIds = invoice.getCart().stream().map(Product::getId).collect(Collectors.toSet());
        Stream<Long> goodIds = goods.stream().map(IEntityId::getId);

        if (!cartIds.stream().allMatch(i -> goodIds.anyMatch(j -> j.equals(i)))) {
            errorMessages.put("cart", Collections.singletonList("One of the products in the cart has an invalid ID."));
            return new ResultDTO<>(errorMessages, null);
        }

        for (IGood g : goods) {
            if (g.getAmount() < invoice.getCart().stream().filter(i -> i.getId() == g.getId()).findFirst().get().getUnitCount()) {
                errorMessages.put("cart", Collections.singletonList("Product with ID " + g.getId() + " has too many units."));
                return new ResultDTO<>(errorMessages, null);
            }
        }

        NewInvoice i = new NewInvoice();
        i.setFullName(invoice.getFullName());
        i.setPhone(invoice.getPhone());
        i.setEmail(invoice.getEmail());
        i.setDeliveryMethodId(invoice.getDeliveryMethodId());
        i.setPaymentMethodId(invoice.getPaymentMethodId());

        cz.kodytek.shop.domain.models.address.Address a = new Address();
        a.setCity(invoice.getDeliveryAddress().getCity());
        a.setStreet(invoice.getDeliveryAddress().getStreet());
        a.setPostCode(invoice.getDeliveryAddress().getPostCode());
        i.setAddress(a);

        if (invoice.getCompany() != null) {
            cz.kodytek.shop.domain.models.company.Company c = new Company();
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
            return new ResultDTO<>(errorMessages, null);
        }

        return new ResultDTO<>(errorMessages, invoice);
    }
}
