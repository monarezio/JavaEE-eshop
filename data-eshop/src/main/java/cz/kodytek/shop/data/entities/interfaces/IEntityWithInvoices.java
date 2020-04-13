package cz.kodytek.shop.data.entities.interfaces;

import cz.kodytek.shop.data.entities.invoice.Invoice;

import java.util.List;

public interface IEntityWithInvoices {

    List<? extends Invoice> getInvoices();

}
