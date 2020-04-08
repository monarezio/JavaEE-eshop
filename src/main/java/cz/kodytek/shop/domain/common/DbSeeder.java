package cz.kodytek.shop.domain.common;

import com.github.dhiraj072.randomwordgenerator.RandomWordGenerator;
import cz.kodytek.shop.data.entities.Category;
import cz.kodytek.shop.data.entities.Good;
import cz.kodytek.shop.data.entities.Right;
import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;
import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.data.entities.interfaces.goods.cateogry.ICategory;
import cz.kodytek.shop.domain.exceptions.InvalidFileTypeException;
import cz.kodytek.shop.domain.models.address.Address;
import cz.kodytek.shop.domain.models.company.AddressCreationType;
import cz.kodytek.shop.domain.models.company.Company;
import cz.kodytek.shop.domain.models.users.RegisteredUser;
import cz.kodytek.shop.domain.services.interfaces.address.IAddressService;
import cz.kodytek.shop.domain.services.interfaces.categories.ICategoryService;
import cz.kodytek.shop.domain.services.interfaces.company.ICompanyService;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;
import cz.kodytek.shop.domain.services.interfaces.users.IUserAuthenticationService;
import cz.kodytek.shop.domain.services.interfaces.users.IUserService;
import org.javamoney.moneta.Money;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
@Named
public class DbSeeder { // TODO: Figure out a better way

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

    private boolean didSeed = false;

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

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try {
                InputStream is = classLoader.getResourceAsStream("/images/bed0.jpg");
                Good g = new Good();
                g.setAmount(100);
                g.setCost(Money.of(100, "CZK"));
                g.setTitle("Bed 1");

                goodsService.create(g, Arrays.asList(is), bed.getId());
            } catch (InvalidFileTypeException e) {
                e.printStackTrace();
            }
        }
    }

}
