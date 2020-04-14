package cz.kodytek.shop.ejb.client;

import cz.kodytek.shop.domain.api.models.*;

import javax.naming.NamingException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner sc;
    private static EJBFactory factory;

    public static void main(String[] args) throws NamingException {
        factory = new EJBFactory();
        sc = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("---- EShop Remote Client ----");
            System.out.println("1) View all payment methods");
            System.out.println("2) View all delivery methods");
            System.out.println("3) View goods");
            System.out.println("4) Invoices management");
            System.out.println("5) Exit");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    renderPayments(factory.getPaymentMethodEJB().getAll());
                    break;
                case 2:
                    renderDeliveries(factory.getDeliveryMethodEJB().getAll());
                    break;
                case 3:
                    renderGoodsMenu();
                    break;
                case 4:
                    renderInvoicesMenu();
                    break;
                case 5:
                    exit = true;
                default:
            }
        }
    }

    private static void renderInvoicesMenu() throws NamingException {
        boolean end = false;

        while (!end) {
            System.out.println();
            System.out.println("-- Invoices management --");
            System.out.println("1) View multiple invoices");
            System.out.println("2) View an invoice detail");
            System.out.println("3) Edit an invoice");
            //System.out.println("4) Create a new invoice"); //TODO: Too many data to input
            System.out.println("5) Back");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    renderInvoicesPage(0);
                    break;
                case 2:
                    System.out.println("Please enter the Invoice ID");
                    renderInvoiceDetail(factory.getInvoiceEJB().get(sc.nextLong()));
                    break;
                case 3:
                    editInvoice();
                    break;
                case 5:
                    end = true;
            }
        }
    }

    private static void editInvoice() throws NamingException {
        EditedInvoice editedInvoice = new EditedInvoice();
        long invoiceId = 0;

        String tmp;

        System.out.println();
        System.out.println("Please enter the following values");
        System.out.println("ID: ");
        invoiceId = sc.nextLong();
        System.out.println("Full name: ");
        tmp = sc.nextLine();
        editedInvoice.setFullName(tmp);
        System.out.println("Email: ");
        tmp = sc.nextLine();
        editedInvoice.setEmail(tmp);
        System.out.println("Phone: " + sc.nextLine());
        tmp = sc.nextLine();
        editedInvoice.setPhone(tmp);
        System.out.println("Did pay? (true/false)");
        editedInvoice.setPaid(sc.nextLine().equals("true"));

        ResultDTO<Boolean> result = factory.getInvoiceEJB().edit(invoiceId, editedInvoice);

        if (!result.getData()) {
            System.out.println("There were errors editing invoice");
            result.getErrors().forEach((key, item) -> {
                System.out.println(key);
                item.forEach(i -> System.out.println("\t" + i));
            });
        }
    }

    private static void renderInvoiceDetail(CreatedInvoice invoice) {
        System.out.println();
        System.out.println("Detail of chosen invoice: ");
        System.out.println("Date issued: " + invoice.getIssued().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        System.out.println("Date paid: " + (invoice.getPaid() == null ? "null" : invoice.getPaid().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"))));
        System.out.println("ID: " + invoice.getId());
        System.out.println("Invoice number: " + invoice.getInvoiceNumber());
        System.out.println("Variable number: " + invoice.getVariableSymbol());
        System.out.println("Full name: " + invoice.getFullName());
        System.out.println("Email: " + invoice.getEmail());
        System.out.println("Phone number: " + invoice.getPhone());
        if (invoice.getCompany() != null) {
            System.out.println("Company:");
            System.out.println("\tName: " + invoice.getCompany().getName());
            System.out.println("\tIdentification number: " + invoice.getCompany().getIdentificationNumber());
            System.out.println("\tTax identification number: " + invoice.getCompany().getTaxIdentificationNumber());
            System.out.println("\tAddress: " + invoice.getCompany().getAddress().getCity() + " " + invoice.getCompany().getAddress().getStreet() + " " + invoice.getCompany().getAddress().getPostCode());
        }
        System.out.println("Delivery address: " + invoice.getDeliveryAddress().getCity() + " " + invoice.getDeliveryAddress().getStreet() + " " + invoice.getDeliveryAddress().getPostCode());
        System.out.println();
    }

    private static void renderInvoicesPage(int page) throws NamingException {
        InvoicesPage ip = factory.getInvoiceEJB().get("", page);
        System.out.println();
        System.out.println("Rendering page " + page + " of " + ip.getPageCount() + " possible pages");
        System.out.println("ID;\tInvoice number;\t;Full name");
        for (CreatedInvoice ci : ip.getInvoices())
            System.out.println(ci.getId() + ";\t" + ci.getInvoiceNumber() + ";\t" + ci.getFullName());

        System.out.println();
        if (page != ip.getPageCount())
            System.out.println("1) Add page");
        if (page != 0)
            System.out.println("2) Remove page");
        System.out.println("3) Back");

        int choice = sc.nextInt();
        if (choice == 1 && page != ip.getPageCount())
            renderInvoicesPage(page + 1);
        else if (choice == 2 && page != 0)
            renderInvoicesPage(page - 1);
        else if (choice != 3)
            renderInvoicesPage(page);
    }

    private static void renderGoodsMenu() throws NamingException {
        boolean end = false;

        while (!end) {
            System.out.println();
            System.out.println("-- Goods management --");
            System.out.println("1) View multiple goods");
            System.out.println("2) View a good detail");
            System.out.println("3) Back");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    renderGoodsPage(0);
                    break;
                case 2:
                    System.out.println("Please enter the Good ID");
                    renderGoodDetail(factory.getGoodsEJB().get(sc.nextLong()));
                case 3:
                    end = true;
            }
        }
    }

    private static void renderGoodsPage(int page) throws NamingException {

        GoodsPage gp = factory.getGoodsEJB().get("", page);
        System.out.println();
        System.out.println("Rendering page " + page + " of " + gp.getPageCount() + " possible pages");
        System.out.println("ID;\tName;\t;Units on stock;\nCost");
        for (Good g : gp.getGoods())
            System.out.println(g.getId() + ";\t" + g.getName() + ";\t" + g.getUnitCount() + ";\t" + g.getCost());
        System.out.println();
        if (page != gp.getPageCount())
            System.out.println("1) Add page");
        if (page != 0)
            System.out.println("2) Remove page");
        System.out.println("3) Back");

        int choice = sc.nextInt();
        if (choice == 1 && page != gp.getPageCount())
            renderGoodsPage(page + 1);
        else if (choice == 2 && page != 0)
            renderGoodsPage(page - 1);
        else if (choice != 3)
            renderGoodsPage(page);
    }

    private static void renderGoodDetail(Good good) {
        System.out.println();
        System.out.println("Detail of chosen good: ");
        System.out.println("ID: " + good.getId());
        System.out.println("Name: " + good.getName());
        System.out.println("Units on stock: " + good.getUnitCount());
        System.out.println("Cost: " + good.getCost());
        System.out.println();
    }

    private static void renderPayments(List<Method> methods) {
        System.out.println();
        System.out.println("Payments are rendered as follows");
        System.out.println("ID;\tName;\tCost");

        for (Method m : methods)
            System.out.println(m.getId() + ";\t" + m.getName() + ";\t" + m.getCost());
        System.out.println();
    }

    private static void renderDeliveries(List<Method> methods) {
        System.out.println();
        System.out.println("Delivery methods are rendered as follows");
        System.out.println("ID;\tName;\tCost");

        for (Method m : methods)
            System.out.println(m.getId() + ";\t" + m.getName() + ";\t" + m.getCost());
        System.out.println();
    }

}
