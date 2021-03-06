package cz.kodytek.shop.domain.common;

import com.github.dhiraj072.randomwordgenerator.RandomWordGenerator;
import cz.kodytek.shop.data.entities.Category;
import cz.kodytek.shop.data.entities.Good;
import cz.kodytek.shop.data.entities.Right;
import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;
import cz.kodytek.shop.data.entities.interfaces.goods.cateogry.ICategory;
import cz.kodytek.shop.data.entities.invoice.method.DeliveryMethod;
import cz.kodytek.shop.data.entities.invoice.method.PaymentMethod;
import cz.kodytek.shop.domain.exceptions.InvalidFileTypeException;
import cz.kodytek.shop.domain.models.address.Address;
import cz.kodytek.shop.domain.models.company.AddressCreationType;
import cz.kodytek.shop.domain.models.company.Company;
import cz.kodytek.shop.domain.models.users.RegisteredUser;
import cz.kodytek.shop.domain.services.interfaces.address.IAddressService;
import cz.kodytek.shop.domain.services.interfaces.categories.ICategoryService;
import cz.kodytek.shop.domain.services.interfaces.company.ICompanyService;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;
import cz.kodytek.shop.domain.services.interfaces.invoices.IDeliveryMethodService;
import cz.kodytek.shop.domain.services.interfaces.invoices.IPaymentMethodService;
import cz.kodytek.shop.domain.services.interfaces.users.IUserAuthenticationService;
import cz.kodytek.shop.domain.services.interfaces.users.IUserService;
import org.javamoney.moneta.Money;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class DbSeeder implements Serializable { // TODO: Figure out a better way

    @Inject
    private IUserAuthenticationService userAuthenticationService;

    @Inject
    private IAddressService addressService;

    @Inject
    private ICompanyService companyService;

    @Inject
    private IUserService userService;

    @Inject
    private ICategoryService categoryService;

    @Inject
    private IGoodsService goodsService;

    @Inject
    private IPaymentMethodService paymentMethodService;

    @Inject
    private IDeliveryMethodService deliveryMethodService;

    private boolean didSeed = false;

    @PostConstruct
    public void seed() {
        if (!didSeed) {
            didSeed = true;
            userAuthenticationService.register(new RegisteredUser("samuel@kodytek.cz", "Samuel Kodytek", "abcabc"));
            userService.addRights(1, Right.ADMIN);

            for (int i = 0; i < 10; i++) {
                Address a = new Address();
                a.setCity(RandomWordGenerator.getRandomWord());
                a.setStreet(RandomWordGenerator.getRandomWord() + " " + (int) (Math.random() * 100));
                a.setPostCode(String.valueOf((int) (Math.random() * 100000)));

                IAddressWithId addedAddress = addressService.create(1, a);

                if (((int) ((Math.random() * 10)) % 3) == 0) {
                    System.out.println("Generating company");
                    Company c = new Company();
                    c.setAddress(addedAddress);
                    c.setName(RandomWordGenerator.getRandomWord());
                    c.setIdentificationNumber((int) (Math.random() * 1000000));
                    c.setTaxIdentificationNumber("CZ" + (int) (Math.random() * 1000000));
                    c.setAddressCreationType(AddressCreationType.REUSE);
                    companyService.create(1, c);
                }
            }

            for (int i = 0; i < 100; i++) {
                String email = "user" + (int) ((Math.random() * 10000)) + "@" + "gmail.com";
                String name = RandomWordGenerator.getRandomWord() + " " + RandomWordGenerator.getRandomWord();
                userAuthenticationService.register(new RegisteredUser(email, name, "abcabc"));
            }

            Category beds = new Category();
            beds.setOrder(1);
            beds.setTitle("Beds");

            Category kitchens = new Category();
            kitchens.setOrder(2);
            kitchens.setTitle("Kitchens");

            Category sinks = new Category();
            sinks.setOrder(3);
            sinks.setTitle("Sinks");

            categoryService.create(beds);
            categoryService.create(kitchens);
            categoryService.create(sinks);

            List<ICategory> categoryList = categoryService.getAll();
            ICategory bed = categoryList.stream().filter(i -> i.getTitle().equals("Beds")).findFirst().get();

            //Beds
            List<String> imageNames = Arrays.asList(
                    "/images/bed0.jpg", "/images/bed1.jpg",
                    "/images/bed2.jpg", "/images/bed3.jpg",
                    "/images/bed4.jpg", "/images/bed5.jpg"
            );

            //Clear the old images, HDD capacity is small...
            File resourceFolder = new File("resources/photos/goods");
            if (resourceFolder.exists()) {
                for (File file : Objects.requireNonNull(resourceFolder.listFiles())) {
                    file.delete();
                }
            }

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            for (String imagePath : imageNames) {
                try {
                    InputStream is = classLoader.getResourceAsStream(imagePath);
                    Good g = new Good();
                    g.setAmount((int) (Math.random() * 100));
                    g.setCost(Money.of((int) Math.ceil(Math.random() * 1000), "CZK"));
                    g.setTitle(StringUtils.capitalize(RandomWordGenerator.getRandomWord()) + " " + (int) (Math.random() * 100));

                    String descrpition = "**" + g.getTitle() + "** ";
                    descrpition += "lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et lectus eget odio scelerisque feugiat. Aliquam luctus velit laoreet massa vehicula, rhoncus eleifend nibh consequat. Sed vel feugiat ipsum. Praesent enim ex, tempus porttitor ex a, luctus aliquam mi. Nulla condimentum faucibus ex at euismod. Nam egestas sem ut leo fermentum tempus. In diam neque, varius venenatis ullamcorper ut, pharetra vitae est. Suspendisse sollicitudin vestibulum scelerisque. Donec iaculis massa risus, eget iaculis tellus cursus a. Praesent vitae efficitur ligula, et volutpat est. Etiam sed nisi non dui molestie pretium. Donec dignissim elit dui, eget scelerisque mi finibus sit amet. Suspendisse feugiat luctus arcu, placerat dapibus enim sodales sed. Morbi velit ex, aliquet at posuere at, semper id nunc. Vivamus et orci fermentum, vestibulum tortor vel, auctor nulla. Nunc et elit id mauris aliquam mattis.";

                    g.setDescription(descrpition);
                    goodsService.create(g, Collections.singletonList(is), bed.getId());
                } catch (InvalidFileTypeException e) {
                    e.printStackTrace();
                }
            }

            PaymentMethod bankTransfer = new PaymentMethod();
            bankTransfer.setName("Bank transfer");
            bankTransfer.setCost(Money.of(0, "CZK"));

            PaymentMethod cashPayment = new PaymentMethod();
            cashPayment.setName("Cash");
            cashPayment.setCost(Money.of(20, "CZK"));

            paymentMethodService.add(bankTransfer);
            paymentMethodService.add(cashPayment);

            DeliveryMethod postOffice = new DeliveryMethod();
            postOffice.setCost(Money.of(50, "CZK"));
            postOffice.setName("Post office");

            DeliveryMethod dhl = new DeliveryMethod();
            dhl.setCost(Money.of(100, "CZK"));
            dhl.setName("DHL");

            deliveryMethodService.add(postOffice);
            deliveryMethodService.add(dhl);
        }
    }

}
